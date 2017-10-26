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

@Controller
@RequestMapping("/")
public class WebController{
	
@RequestMapping(method = RequestMethod.GET)
	public String print(ModelMap model){
		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		RecordJdbcTemplate recordJdbcTemplate = (RecordJdbcTemplate) context.getBean("recordJdbcTemplate");
		ArrayList<Record> list = recordJdbcTemplate.getRecords();
		model.addAttribute("records", list);
		return "result";
	}

}