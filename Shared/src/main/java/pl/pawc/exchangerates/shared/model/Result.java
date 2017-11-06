package pl.pawc.exchangerates.shared.model;

import java.util.ArrayList;

public class Result{

	private String targetCurrency;
	private String baseCurrency;
	private ArrayList<RateDate> rateDate;
	private double min;
	private double max;
	
	public String getTargetCurrency() {
		return targetCurrency;
	}
	public void setTargetCurrency(String targetCurrency) {
		this.targetCurrency = targetCurrency;
	}
	public String getBaseCurrency() {
		return baseCurrency;
	}
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	public ArrayList<RateDate> getRateDate() {
		return rateDate;
	}
	public void setRateDate(ArrayList<RateDate> rateDate) {
		this.rateDate = rateDate;
	}
	public double getMin() {
		return min;
	}
	public void setMin(double min) {
		this.min = min;
	}
	public double getMax() {
		return max;
	}
	public void setMax(double max) {
		this.max = max;
	}

}