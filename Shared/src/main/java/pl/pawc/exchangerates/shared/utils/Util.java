package pl.pawc.exchangerates.shared.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import pl.pawc.exchangerates.shared.model.Currency;
import pl.pawc.exchangerates.shared.model.Record;

public class Util {

	public static ArrayList<Record> inverseRates(ArrayList<Record> list){
		for(Record record : list){
			String temp = record.getBaseCurrency();
			record.setBaseCurrency(record.getTargetCurrency());
			record.setTargetCurrency(temp);
			record.setExchangeRate(1/record.getExchangeRate());
		}
		return list;
	}
	
	public static ArrayList<Record> fillWithOnes(ArrayList<Record> list){
		for(Record record : list){
			record.setExchangeRate(1);
		}
		return list;
	}
	
	public static ArrayList<Record> evaluate(ArrayList<Record> list1, ArrayList<Record> list2){
		for(int i=0; i<=list1.size()-1; i++){
			list1.get(i).setBaseCurrency(list2.get(i).getTargetCurrency());
			double temp = list1.get(i).getExchangeRate()/list2.get(i).getExchangeRate();
			list1.get(i).setExchangeRate(temp);
		}
		return list1;
	}
	
	public static ArrayList<Record> sortByDates(ArrayList<Record> list) {
		Collections.sort(list, new Comparator<Record>(){
			public int compare(Record r1, Record r2) {
				return r1.getDate().compareTo(r2.getDate());	
			}
			
		});
		
		return list;
	}
	
	public static ArrayList<Record> sortByRates(ArrayList<Record> list) {
		Collections.sort(list, new Comparator<Record>(){
			public int compare(Record r1, Record r2) {
				return Double.compare(r1.getExchangeRate(), r2.getExchangeRate());		
			}
			
		});
		
		return list;
	}
	
	public static List<String> convertTo(List<Currency> listOfCurrencies) {
		List<String> result = new ArrayList<String>();
		for(Currency currency : listOfCurrencies) {
			result.add(currency.toString());
		}
		return result;
	}
	
	public static ArrayList<Record> removeRecordsOutsideDates(ArrayList<Record> list, String dateFrom, String dateTo){
		Date since = null;
		Date until = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		
		try {
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
		} 
		catch (ParseException e) {
			e.printStackTrace();
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
	
}