package pl.pawc.exchangerates.model;

import java.sql.Date;

public class Record{
	
	private String baseCurrency;
	private String currency;
	private double rate;
	private Date date;
	
	public Record(String baseCurrency, String currency, double rate, Date date){
		super();
		this.baseCurrency = baseCurrency;
		this.currency = currency;
		this.rate = rate;
		this.date = date;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}	

}