package pl.pawc.exchangerates;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Datafeed{
    public static void main(String[] args){
    	
    	ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		IDownloader downloader = (Downloader) context.getBean("downloader");
        downloader.download("PLN");
        
    }
}