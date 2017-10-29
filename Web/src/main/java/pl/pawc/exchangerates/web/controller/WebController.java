package pl.pawc.exchangerates.web.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.pawc.exchangerates.shared.dao.RecordJdbcTemplate;
import pl.pawc.exchangerates.shared.model.Currency;
import pl.pawc.exchangerates.shared.model.Record;
import pl.pawc.exchangerates.shared.utils.Util;

import org.springframework.ui.ModelMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.EnumUtils;

@Controller
@RequestMapping("/")
public class WebController{
	
@RequestMapping("/")
	public ModelAndView redirect(HttpServletRequest request, HttpServletResponse response){		
		return new ModelAndView("redirect:/result");
	}

@RequestMapping("result")
public ModelAndView plot(HttpServletRequest request, HttpServletResponse response){
	
	String paramTargetCurrency = request.getParameter("targetCurrency");
	String paramBaseCurrency = request.getParameter("baseCurrency");
	String paramDateFrom = request.getParameter("dateFrom");
	String paramDateTo = request.getParameter("dateTo");
	
	List<Currency> listOfCurrencies = EnumUtils.getEnumList(Currency.class);
	List<String> listOfCurrenciesString = convert(listOfCurrencies);

	if(paramTargetCurrency == null || paramBaseCurrency == null){
		request.getSession().setAttribute("targetCurrency", "EUR");
		request.getSession().setAttribute("baseCurrency", "PLN");
	}
	else{
		if(listOfCurrenciesString.contains(paramTargetCurrency) && listOfCurrenciesString.contains(paramBaseCurrency)){
			request.getSession().setAttribute("targetCurrency", paramTargetCurrency);
			request.getSession().setAttribute("baseCurrency", paramBaseCurrency);
		}
		else{
			return new ModelAndView("redirect:/result");
		}
	}
	
	String targetCurrency = (String) request.getSession().getAttribute("targetCurrency");
	String baseCurrency = (String) request.getSession().getAttribute("baseCurrency");
	ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	RecordJdbcTemplate recordJdbcTemplate = (RecordJdbcTemplate) context.getBean("recordJdbcTemplate");
	
	ArrayList<Record> list = null;

	list = getResults(targetCurrency, baseCurrency, recordJdbcTemplate);
	
	ModelMap model = new ModelMap();
	
	setAttributes(listOfCurrencies, targetCurrency, baseCurrency, paramDateFrom, paramDateTo, list, model);
	
	return new ModelAndView("result", "model", model);
	
}

private void setAttributes(List<Currency> listOfCurrencies, String targetCurrency, String baseCurrency,
	String dateFrom, String dateTo, ArrayList<Record> list, ModelMap model){
	
	model.addAttribute("records", list);
	model.addAttribute("targetCurrency", targetCurrency);
	model.addAttribute("baseCurrency", baseCurrency);
	model.addAttribute("dateFrom", dateFrom);
	model.addAttribute("dateTo", dateTo);
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	try {
		list = removeRecordsOutsideDates(list, dateFrom, dateTo);
	} catch (ParseException e1) {
		e1.printStackTrace();
	}
	
	list = sortByRates(list);
	
	double min = list.get(0).getExchangeRate();
	double max = list.get(list.size()-1).getExchangeRate();
	
	list = sortByDates(list);
	
	try{
		model.addAttribute("recordsJackson", objectMapper.writeValueAsString(list));
		model.addAttribute("currencies", objectMapper.writeValueAsString(listOfCurrencies));

		min = min-(max-min)*0.1;
		max = max+(max-min)*0.1;
		
		model.addAttribute("minVal", objectMapper.writeValueAsString(min));
		model.addAttribute("maxVal", objectMapper.writeValueAsString(max));
	} 
	catch(JsonProcessingException e){
		e.printStackTrace();
	}

}

private ArrayList<Record> sortByDates(ArrayList<Record> list) {
	Collections.sort(list, new Comparator<Record>(){
		public int compare(Record r1, Record r2) {
			return r1.getDate().compareTo(r2.getDate());	
		}
		
	});
	
	return list;
}

private ArrayList<Record> sortByRates(ArrayList<Record> list) {
	Collections.sort(list, new Comparator<Record>(){
		public int compare(Record r1, Record r2) {
			return Double.compare(r1.getExchangeRate(), r2.getExchangeRate());		
		}
		
	});
	
	return list;
}

private ArrayList<Record> removeRecordsOutsideDates(ArrayList<Record> list, String dateFrom, String dateTo) throws ParseException{
	Date since = null;
	Date until = null;
	DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	if(dateFrom == null || dateFrom.equals("")) {
		since = new Date(Long.MIN_VALUE);
	}
	else {
		since = format.parse(dateFrom);
	}
		
	if(dateTo == null || dateTo.equals("")) {
		until = new Date(Long.MAX_VALUE);
	}
	else {
		until = format.parse(dateTo);
	}
	
	ArrayList<Record> boundedList = new ArrayList<Record>();
	
	for(Record record : list) {
		if(record.getDate().compareTo(since) >= 0 && record.getDate().compareTo(until) <= 0 ) boundedList.add(record); 
	}
	
	for(Record record : boundedList) {
		System.out.println(record.getDate().toString());
	}
	
	return boundedList;
	
}

private ArrayList<Record> getResults(String targetCurrency, String baseCurrency,
		RecordJdbcTemplate recordJdbcTemplate) {
	ArrayList<Record> list;
	if(baseCurrency.equals(targetCurrency)){
		list = recordJdbcTemplate.getRecords("EUR");
		list = Util.fillWithOnes(list);
	}
	else if(targetCurrency.equals("PLN")){
		list = recordJdbcTemplate.getRecords(baseCurrency);
		list = Util.inverseRates(list);
	}
	else if(!"PLN".equals(targetCurrency) && !"PLN".equals(baseCurrency)){
		ArrayList<Record> list1 = recordJdbcTemplate.getRecords(targetCurrency);
		ArrayList<Record> list2 = recordJdbcTemplate.getRecords(baseCurrency);
		list = Util.evaluate(list1, list2);
	}
	else{
		list = recordJdbcTemplate.getRecords(targetCurrency);
	}
	return list;
}

private List<String> convert(List<Currency> listOfCurrencies) {
	List<String> result = new ArrayList<String>();
	for(Currency currency : listOfCurrencies) {
		result.add(currency.toString());
	}
	return result;
}

}