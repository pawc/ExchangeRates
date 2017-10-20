package pl.pawc.exchangerates;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import pl.pawc.exchangerates.model.Record;

public class Downloader implements IDownloader{
	
	private URL url;
	private BufferedReader bfr;
	private File file;
	private FileWriter fw;

	public void download(String baseCurrency){
		try {
			url = new URL("http://www.floatrates.com/daily/"+baseCurrency+".xml");
			bfr = new BufferedReader(new InputStreamReader(url.openStream()));
			file = new File(baseCurrency+".xml");
			fw = new FileWriter(file, false);
			
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