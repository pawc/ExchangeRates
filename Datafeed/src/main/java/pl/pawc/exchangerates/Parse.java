package pl.pawc.exchangerates;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import pl.pawc.exchangerates.model.Record;

public class Parse implements IParser{
	
	private ArrayList<String> availableCurrencies;
	private File file;

	public ArrayList<Record> parse(String baseCurrency) {
	
		download(baseCurrency);
		
		return null;
		
	}

	private void download(String baseCurrency){
		try {
			URL url = new URL("http://www.floatrates.com/daily/"+baseCurrency+".xml");
			BufferedReader bfr = new BufferedReader(new InputStreamReader(url.openStream()));
			File file = new File(baseCurrency+".xml");
			FileWriter fw = new FileWriter(file, false);
			
			String inputLine;
			while((inputLine = bfr.readLine()) != null){
				fw.write(inputLine);
			}
			bfr.close();
			fw.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
	}

}