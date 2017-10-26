package pl.pawc.exchangerates.shared.dao;

import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import pl.pawc.exchangerates.shared.model.Record;

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

	public ArrayList<Record> getRecords(){
		String SQL = "select * from records where targetCurrency='EUR'";
		ArrayList<Record> results = (ArrayList<Record>) jdbcTemplateObject.query(SQL, new RecordMapper()); 
		return results;
	}

}