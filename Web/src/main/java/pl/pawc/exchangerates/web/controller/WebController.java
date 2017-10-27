package pl.pawc.exchangerates.web.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import pl.pawc.exchangerates.shared.dao.RecordJdbcTemplate;
import pl.pawc.exchangerates.shared.model.Record;

import org.springframework.ui.ModelMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/")
public class WebController{
	
@RequestMapping(method = RequestMethod.GET)
	public ModelAndView print(HttpServletRequest request, HttpServletResponse response){
		request.getSession().setAttribute("targetCurrency", "RON");
		String targetCurrency = (String) request.getSession().getAttribute("targetCurrency");
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		RecordJdbcTemplate recordJdbcTemplate = (RecordJdbcTemplate) context.getBean("recordJdbcTemplate");
		ArrayList<Record> list = recordJdbcTemplate.getRecords(targetCurrency);
		
		ModelMap model = new ModelMap();
		model.addAttribute("records", list);
		model.addAttribute("targetCurrency", targetCurrency);
		
		ObjectMapper objectMapper = new ObjectMapper();
		try{
			model.addAttribute("recordsJackson", objectMapper.writeValueAsString(list));
			
			Collections.sort(list, new Comparator<Record>(){
				public int compare(Record r1, Record r2) {
						return Double.compare(r1.getExchangeRate(), r2.getExchangeRate());		
				}
				
			});
			
			double min = list.get(0).getExchangeRate();
			double max = list.get(list.size()-1).getExchangeRate();
			
			model.addAttribute("minVal", objectMapper.writeValueAsString(min));
			model.addAttribute("maxVal", objectMapper.writeValueAsString(max));
		} 
		catch(JsonProcessingException e){
			e.printStackTrace();
		}
		
		return new ModelAndView("result", "model", model);
	}

}