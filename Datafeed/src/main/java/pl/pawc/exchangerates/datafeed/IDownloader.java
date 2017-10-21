package pl.pawc.exchangerates.datafeed;

import java.io.File;

public interface IDownloader{
	
	public File download(String baseCurrency);

}