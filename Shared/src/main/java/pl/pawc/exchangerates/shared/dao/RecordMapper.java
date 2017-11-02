package pl.pawc.exchangerates.shared.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.jdbc.core.RowMapper;

import pl.pawc.exchangerates.shared.model.Record;

public class RecordMapper implements RowMapper<Record>{

	public Record mapRow(ResultSet resultSet, int rowNum) throws SQLException{
		
		Record record = new Record();
		
		record.setBaseCurrency(resultSet.getString("baseCurrency"));
		record.setTargetCurrency(resultSet.getString("targetCurrency"));
		record.setExchangeRate(resultSet.getDouble("exchangeRate"));
		GregorianCalendar calendar = new GregorianCalendar(new Locale("pl"));
		record.setDate(resultSet.getDate("date", calendar));
		
		return record;
		
	}
	
}