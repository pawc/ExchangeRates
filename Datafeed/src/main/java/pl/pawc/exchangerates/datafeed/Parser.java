package pl.pawc.exchangerates.datafeed;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import pl.pawc.exchangerates.shared.model.Record;

public class Parser implements IParser {

	public ArrayList<Record> parse(File file, String baseCurrency){
		
		ArrayList<Record> result = new ArrayList<Record>();
		
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = null;
		
		try {
			saxBuilder.setValidation(false);
			document = saxBuilder.build(file);
		} 
		catch (JDOMException e){
			e.printStackTrace();
		} 
		catch (IOException e){
			e.printStackTrace();
		}
		
		Element rootElement = document.getRootElement();
		
		List<Element> list = rootElement.getChildren("item");
		
		for(Element e : list){
			Record record = new Record();
			
			String targetCurrency = e.getChild("targetCurrency").getValue();
			String rate = e.getChild("exchangeRate").getValue();
			double exchangeRate = parseDouble(rate);
			Date date = new Date();
			
			record.setBaseCurrency(baseCurrency);
			record.setTargetCurrency(targetCurrency);
			record.setExchangeRate(exchangeRate);
			record.setDate(date);
			
			result.add(record);
		}
		
		return result;
		
	}

	private double parseDouble(String rate){
		rate = rate.replace(",", "");
		double result = 0;
			try{
			result = Double.parseDouble(rate);
			}
			catch(NumberFormatException e) {
				e.printStackTrace();
			}
		return result;
	}

}