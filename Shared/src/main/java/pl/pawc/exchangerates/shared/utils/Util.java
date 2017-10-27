package pl.pawc.exchangerates.shared.utils;

import java.util.ArrayList;

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
	
}