package pl.pawc.exchangerates;

import java.io.File;

public interface IDownloader{
	
	public File download(String baseCurrency);

}