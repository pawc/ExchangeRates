package pl.pawc.exchangerates;

import java.util.ArrayList;

import pl.pawc.exchangerates.model.Record;

public interface IParser{
	
	public ArrayList<Record> parse(String baseCurrency);

}