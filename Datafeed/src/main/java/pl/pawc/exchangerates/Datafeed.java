package pl.pawc.exchangerates;

import java.io.File;
import java.util.ArrayList;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pl.pawc.exchangerates.dao.RecordJdbcTemplate;
import pl.pawc.exchangerates.model.Record;

public class Datafeed{
    public static void main(String[] args){
    	
    	ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		
    	IDownloader downloader = (Downloader) context.getBean("downloader");
        File file = downloader.download("PLN");
        
        IParser parser = (Parser) context.getBean("parser");
        ArrayList<Record> list = parser.parse(file, "PLN");
        
        RecordJdbcTemplate recordJdbcTemplate = (RecordJdbcTemplate) context.getBean("recordJdbcTemplate");
        recordJdbcTemplate.insert(list);
        
    }
}