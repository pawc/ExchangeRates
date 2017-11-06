package pl.pawc.exchangerates.shared.dao;

import java.util.ArrayList;

import javax.sql.DataSource;

import pl.pawc.exchangerates.shared.model.RateDate;
import pl.pawc.exchangerates.shared.model.Record;
import pl.pawc.exchangerates.shared.model.Result;

public interface RecordDAO {

	public void setDataSource(DataSource dataSource);
	public void insert(Record record);
	public void insert(ArrayList<Record> records);
	public ArrayList<RateDate> getRateDates(String targetCurrency, String dateStart, String dateEnd);
	public Result getResult(String targetCurrency, String baseCurrency, String dateStart, String dateEnd);
	
}