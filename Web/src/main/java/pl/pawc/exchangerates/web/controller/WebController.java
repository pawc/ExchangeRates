package pl.pawc.exchangerates.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pl.pawc.exchangerates.shared.dao.RecordJdbcTemplate;
import pl.pawc.exchangerates.shared.model.Currency;
import pl.pawc.exchangerates.shared.model.Record;
import pl.pawc.exchangerates.shared.utils.Util;

import org.springframework.ui.ModelMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/")
public class WebController{
	
@RequestMapping("/ajaxTest")
public ModelAndView ajaxTest(HttpServletRequest request, HttpServletResponse response){		
	return new ModelAndView("ajaxTest");
}

 @RequestMapping("/ajax")  
 public @ResponseBody  
 String hello( @RequestParam(value = "targetCurrency") String targetCurrency) { 
  
	ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	RecordJdbcTemplate recordJdbcTemplate = (RecordJdbcTemplate) context.getBean("recordJdbcTemplate");
	
	ArrayList<Record> list = recordJdbcTemplate.getRecords(targetCurrency, "PLN");
  
	ObjectMapper objectMapper = new ObjectMapper();
	
	String str = null;
	try{
		str = objectMapper.writeValueAsString(list);
	} catch (JsonProcessingException e){
		e.printStackTrace();
	}
  
	return str;  
  
}  
	
@RequestMapping("/")
	public ModelAndView redirect(HttpServletRequest request, HttpServletResponse response){		
		return new ModelAndView("redirect:/result");
	}

@RequestMapping("invert")
public ModelAndView invert(HttpServletRequest request, HttpServletResponse response){
	
	String targetCurrency = (String) request.getSession().getAttribute("targetCurrency");
	String baseCurrency = (String) request.getSession().getAttribute("baseCurrency");
	request.getSession().setAttribute("baseCurrency", targetCurrency);
	request.getSession().setAttribute("targetCurrency", baseCurrency);

	return new ModelAndView("redirect:/result");
}

@RequestMapping("result")
public ModelAndView plot(HttpServletRequest request, HttpServletResponse response){
	
	String paramTargetCurrency = request.getParameter("targetCurrency");
	String paramBaseCurrency = request.getParameter("baseCurrency");
	String paramDateFrom = request.getParameter("dateFrom");
	String paramDateTo = request.getParameter("dateTo");
	
	List<String> currencies = Util.convertTo(Util.getCurrencies());

	if(paramTargetCurrency != null || paramBaseCurrency != null){
		if(currencies.contains(paramTargetCurrency) && currencies.contains(paramBaseCurrency)){
			request.getSession().setAttribute("targetCurrency", paramTargetCurrency);
			request.getSession().setAttribute("baseCurrency", paramBaseCurrency);
		}
		else{
			try {
				request.getSession().setAttribute("targetCurrency", "EUR");
				request.getSession().setAttribute("baseCurrency", "PLN");
				response.sendError(400, "invalid req params. Restoring to defaults");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	else {
		if(request.getSession().getAttribute("targetCurrency") == null) request.getSession().setAttribute("targetCurrency", "EUR");
		if(request.getSession().getAttribute("baseCurrency") == null) request.getSession().setAttribute("baseCurrency", "PLN");
	}
	
	String dateFrom = null;
	String dateTo = null;
	
	if(paramDateFrom != null && paramDateTo !=null) {
		if(Util.validateDates(paramDateFrom, paramDateTo)) {
			dateFrom = paramDateFrom;
			dateTo = paramDateTo;
			request.getSession().setAttribute("dateFrom", dateFrom);
			request.getSession().setAttribute("dateTo", dateTo);
		}
		else {
			request.getSession().setAttribute("dateFrom", "");
			request.getSession().setAttribute("dateTo", "");
			try {
				response.sendError(400, "invalid req params. Restoring to defaults");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	else {
		if(request.getSession().getAttribute("dateFrom") != null) {
			dateFrom = (String) request.getSession().getAttribute("dateFrom");
		}
		if(request.getSession().getAttribute("dateTo") != null) {
			dateTo = (String) request.getSession().getAttribute("dateTo");
		}
	}

	
	String targetCurrency = (String) request.getSession().getAttribute("targetCurrency");
	String baseCurrency = (String) request.getSession().getAttribute("baseCurrency");
	
	ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	RecordJdbcTemplate recordJdbcTemplate = (RecordJdbcTemplate) context.getBean("recordJdbcTemplate");
	
	ArrayList<Record> list = recordJdbcTemplate.getRecords(targetCurrency, baseCurrency);
	
	ModelMap model = new ModelMap();
	
	setAttributes(Util.getCurrencies(), targetCurrency, baseCurrency, dateFrom, dateTo, list, model);
	
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
	
	list = Util.removeRecordsBeyondDates(list, dateFrom, dateTo);
	
	list = Util.sortByRates(list);
	
	double min = list.get(0).getExchangeRate();
	double max = list.get(list.size()-1).getExchangeRate();
	
	list = Util.sortByDates(list);
	
	try{
		model.addAttribute("recordsJackson", objectMapper.writeValueAsString(list));
		model.addAttribute("currencies", objectMapper.writeValueAsString(listOfCurrencies));

		min = min-(max-min)*0.1;
		max = max+(max-min)*0.1;
		
		if(list.size()==1) {
			min = min-1;
			max = max+1;
		}
		
		model.addAttribute("minVal", objectMapper.writeValueAsString(min));
		model.addAttribute("maxVal", objectMapper.writeValueAsString(max));
	} 
	catch(JsonProcessingException e){
		e.printStackTrace();
	}

}

}