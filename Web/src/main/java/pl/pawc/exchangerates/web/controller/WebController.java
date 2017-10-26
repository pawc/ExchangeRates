package pl.pawc.exchangerates.web.controller;

import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.pawc.exchangerates.shared.dao.RecordJdbcTemplate;
import pl.pawc.exchangerates.shared.model.Record;

import org.springframework.ui.ModelMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/")
public class WebController{
	
@RequestMapping(method = RequestMethod.GET)
	public String print(ModelMap model){
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		RecordJdbcTemplate recordJdbcTemplate = (RecordJdbcTemplate) context.getBean("recordJdbcTemplate");
		ArrayList<Record> list = recordJdbcTemplate.getRecords();
		model.addAttribute("records", list);
		
		ObjectMapper objectMapper = new ObjectMapper();
		try{
			model.addAttribute("recordsJackson", objectMapper.writeValueAsString(list));
			model.addAttribute("minVal", objectMapper.writeValueAsString(getExtrema(list)[0]));
			model.addAttribute("maxVal", objectMapper.writeValueAsString(getExtrema(list)[1]));
		} 
		catch(JsonProcessingException e){
			e.printStackTrace();
		}
		
		return "result";
	}

	private double[] getExtrema(ArrayList<Record> list){
		double[] result = new double[2];
		double first = list.get(0).getExchangeRate();
		result[0] = first;
		result[1] = first;
		for(Record record : list){
			double temp = record.getExchangeRate();
			if(temp < result[0]) result[0] = temp;
			if(temp > result[1]) result[1] = temp;
		}
		return result;
	}

}