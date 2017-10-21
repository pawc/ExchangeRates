package pl.pawc.exchangerates.datafeed;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Downloader implements IDownloader{
	
	private URL url;
	private BufferedReader bfr;
	private FileWriter fw;

	public File download(String baseCurrency){
		
		File file = null;
		
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
		
		return file;
		
	}

}