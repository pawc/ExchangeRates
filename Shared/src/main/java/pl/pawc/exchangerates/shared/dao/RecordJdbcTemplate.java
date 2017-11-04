package pl.pawc.exchangerates.shared.dao;

import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import pl.pawc.exchangerates.shared.model.Record;
import pl.pawc.exchangerates.shared.utils.Util;

public class RecordJdbcTemplate implements RecordDAO{
	
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplateObject;

	public void setDataSource(DataSource dataSource){
		this.dataSource = dataSource;
		this.jdbcTemplateObject = new JdbcTemplate(dataSource);
	}

	public void insert(ArrayList<Record> records){
		for(Record record : records){
			insert(record);
		}
	}
	
	public void insert(Record record){
		String SQL = "insert into records (baseCurrency, targetCurrency, exchangeRate, date) "
			+ "values (?, ?, ?, ?)";
		
		String baseCurrency = record.getBaseCurrency();
		String targetCurrency = record.getTargetCurrency();
		Double exchangeRate = record.getExchangeRate();
		Date date = record.getDate();
		
		jdbcTemplateObject.update(SQL, baseCurrency, targetCurrency, exchangeRate, date);
		
	}

	public ArrayList<Record> getRecords(String targetCurrency, String baseCurrency, String dateStart, String dateEnd) {
		ArrayList<Record> list;
		if(baseCurrency.equals(targetCurrency)){
			list = getRecords("EUR", dateStart, dateEnd);
			list = Util.fillWithOnes(list);
		}
		else if(targetCurrency.equals("PLN")){
			list = getRecords(baseCurrency, dateStart, dateEnd);
			list = Util.inverseRates(list);
		}
		else if(!"PLN".equals(targetCurrency) && !"PLN".equals(baseCurrency)){
			ArrayList<Record> list1 = getRecords(targetCurrency, dateStart, dateEnd);
			ArrayList<Record> list2 = getRecords(baseCurrency, dateStart, dateEnd);
			list = Util.evaluate(list1, list2);
		}
		else{
			list = getRecords(targetCurrency, dateStart, dateEnd);
		}
		return list;
	}
	
	public ArrayList<Record> getRecords(String targetCurrency, String dateStart, String dateEnd){
		String SQL = "select * from records where targetCurrency='"+targetCurrency+"' "
				+ "and date between '"+dateStart+"' and '"+dateEnd+"';";
		ArrayList<Record> results = (ArrayList<Record>) jdbcTemplateObject.query(SQL, new RecordMapper()); 
		return results;
	}

}