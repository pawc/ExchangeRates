package pl.pawc.exchangerates.datafeed;

import java.io.File;
import java.util.ArrayList;

import pl.pawc.exchangerates.shared.model.Record;


public interface IParser{

	public ArrayList<Record> parse(File file, String baseCurrency);
	
}