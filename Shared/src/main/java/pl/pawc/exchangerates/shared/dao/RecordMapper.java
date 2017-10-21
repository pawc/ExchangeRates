package pl.pawc.exchangerates.shared.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import pl.pawc.exchangerates.shared.model.Record;

public class RecordMapper implements RowMapper<Record>{

	public Record mapRow(ResultSet resultSet, int rowNum) throws SQLException{
		
		Record record = new Record();
		
		record.setBaseCurrency(resultSet.getString("baseCurrency"));
		record.setTargetCurrency(resultSet.getString("targetCurrency"));
		record.setExchangeRate(resultSet.getDouble("exchangeRate"));
		record.setDate(resultSet.getDate("date"));
		
		return record;
		
	}
	
}