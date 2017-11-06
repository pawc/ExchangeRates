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
import pl.pawc.exchangerates.shared.model.RateDate;

public class Util {

	public static ArrayList<RateDate> inverseRates(ArrayList<RateDate> list){
		for(RateDate rateDate : list){
			rateDate.setExchangeRate(1/rateDate.getExchangeRate());
		}
		return list;
	}
	
	public static ArrayList<RateDate> fillWithOnes(ArrayList<RateDate> list){
		for(RateDate rateDate : list){
			rateDate.setExchangeRate(1);
		}
		return list;
	}
	
	public static ArrayList<RateDate> evaluate(ArrayList<RateDate> list1, ArrayList<RateDate> list2){
		
		ArrayList<RateDate> result = new ArrayList<RateDate>();
		
		double rate;
		
		for(int i=0; i<=list1.size()-1; i++){
		
			rate = list1.get(i).getExchangeRate()/list2.get(i).getExchangeRate();

			RateDate rateDate = new RateDate();
			rateDate.setExchangeRate(rate);
			rateDate.setDate(list1.get(i).getDate());
			
			result.add(rateDate);
		}
		
		return result;
	}
	
	public static ArrayList<RateDate> sortByDates(ArrayList<RateDate> list) {
		Collections.sort(list, new Comparator<RateDate>(){

			public int compare(RateDate r1, RateDate r2) {
				return r1.getDate().compareTo(r2.getDate());
			}
			
		});
		
		return list;
	}
	
	public static ArrayList<RateDate> sortByRates(ArrayList<RateDate> list) {
		Collections.sort(list, new Comparator<RateDate>(){
			public int compare(RateDate r1, RateDate r2) {
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

	public static double[] getMinMax(ArrayList<RateDate> list) {

		list = Util.sortByRates(list);
		
		double min = list.get(0).getExchangeRate();
		double max = list.get(list.size()-1).getExchangeRate();
		
		list = Util.sortByDates(list);
		
		double[] minMax = {min, max};
		
		return minMax;
		
	}
	
}