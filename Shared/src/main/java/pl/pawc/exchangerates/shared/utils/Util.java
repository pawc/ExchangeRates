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

import org.apache.commons.lang3.EnumUtils;

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
		ArrayList<Record> result = new ArrayList<Record>();
		for(int i=0; i<=list1.size()-1; i++){
			String targetCurrency = list1.get(i).getTargetCurrency();
			String baseCurrency = list2.get(i).getTargetCurrency();
			Date date = list1.get(i).getDate();
			double rate = list1.get(i).getExchangeRate()/list2.get(i).getExchangeRate();

			Record record = new Record();
			record.setTargetCurrency(targetCurrency);
			record.setBaseCurrency(baseCurrency);
			record.setDate(date);
			record.setExchangeRate(rate);
			
			result.add(record);
		}
		
		return result;
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
	
	public static boolean validateParams(String targetCurrency, String baseCurrency, String dateStart, String dateEnd) {
		return (validateCurrencies(targetCurrency, baseCurrency) && validateDates(dateStart, dateEnd));
	}
	
	public static List<Currency> getCurrencies(){
	List<Currency> listOfCurrencies = EnumUtils.getEnumList(Currency.class);
	
	return listOfCurrencies;
	}
	
	public static List<String> getCurrenciesString(){
		List<Currency> listOfCurrencies = getCurrencies();
		ArrayList<String> listOfCurrenciesString = new ArrayList<String>();
		
		for(Currency currency : listOfCurrencies) {
			listOfCurrenciesString.add(currency.toString());
		}
		
		return listOfCurrenciesString;
	}
	
	public static boolean validateCurrencies(String targetCurrency, String baseCurrency){
		List<String> listOfCurrencies = getCurrenciesString();
		return (listOfCurrencies.contains(targetCurrency) && listOfCurrencies.contains(baseCurrency));
		
	}

	public static boolean validateDates(String paramDateFrom, String paramDateTo) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		
		Date since;
		Date until;
		
		try{
			since = format.parse(paramDateFrom);
			until = format.parse(paramDateTo);
			if(!format.format(since).equals(paramDateFrom)) return false;
			if(!format.format(until).equals(paramDateTo)) return false;
			
		} catch (ParseException e){
			return false;
		}
		
		return true;
		
	}

	public static double[] getMinMax(ArrayList<Record> list) {

		list = Util.sortByRates(list);
		
		double min = list.get(0).getExchangeRate();
		double max = list.get(list.size()-1).getExchangeRate();
		
		list = Util.sortByDates(list);
		
		double[] minMax = {min, max};
		
		return minMax;
		
	}
	
}