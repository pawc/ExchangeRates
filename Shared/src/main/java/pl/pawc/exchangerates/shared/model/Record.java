package pl.pawc.exchangerates.shared.model;

import java.util.Calendar;
import java.util.Date;

public class Record{
	
	private String baseCurrency;
	private String targetCurrency;
	private double exchangeRate;
	private Date date;
	
	public Record() {
		super();
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(String currency) {
		this.targetCurrency = currency;
	}

	public double getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(double rate) {
		this.exchangeRate = rate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String toString(){
		return baseCurrency+"/"+targetCurrency+"="+exchangeRate+" on "+dateToString(date);
	}
	
	public String dateToString(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String year = convert(calendar.get(Calendar.YEAR));
		String month = convert(calendar.get(Calendar.MONTH)+1);
		String day = convert(calendar.get(Calendar.DAY_OF_MONTH));
		return year+"-"+month+"-"+day;
	}
	
	public String convert(int i) {
		return String.valueOf(i);
	}

}