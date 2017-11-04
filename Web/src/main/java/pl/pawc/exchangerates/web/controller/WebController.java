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
	
	ModelMap model = new ModelMap();
	
	List<Currency> listOfCurrencies = Util.getCurrencies();
	ObjectMapper objectMapper = new ObjectMapper();
	
	try {
		model.addAttribute("currencies", objectMapper.writeValueAsString(listOfCurrencies));
	} catch (JsonProcessingException e) {
		e.printStackTrace();
	}
	
	return new ModelAndView("ajaxTest", "model", model);
}

 @RequestMapping("/ajax")  
 public @ResponseBody  
 String ajax( @RequestParam(value = "targetCurrency") String targetCurrency,
		@RequestParam(value = "baseCurrency") String baseCurrency,
		@RequestParam(value = "dateFrom") String dateFrom,
		@RequestParam(value = "dateTo") String dateTo
		 ) { 
  
	ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
	RecordJdbcTemplate recordJdbcTemplate = (RecordJdbcTemplate) context.getBean("recordJdbcTemplate");
	
	ArrayList<Record> list = recordJdbcTemplate.getRecords(targetCurrency, baseCurrency, dateFrom, dateTo);
	
	list = Util.sortByRates(list);
	
	double min = list.get(0).getExchangeRate();
	double max = list.get(list.size()-1).getExchangeRate();
	
	list = Util.sortByDates(list);
	
	Record recordMin = new Record();
	recordMin.setExchangeRate(min);
	
	Record recordMax = new Record();
	recordMax.setExchangeRate(max);
	
	list.add(recordMin);
	list.add(recordMax);
  
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
		return new ModelAndView("redirect:/ajaxTest");
	}

}