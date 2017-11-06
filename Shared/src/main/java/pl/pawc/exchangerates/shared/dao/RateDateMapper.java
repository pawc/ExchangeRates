package pl.pawc.exchangerates.shared.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.springframework.jdbc.core.RowMapper;

import pl.pawc.exchangerates.shared.model.RateDate;

public class RateDateMapper implements RowMapper<RateDate>{

	public RateDate mapRow(ResultSet resultSet, int rowNum) throws SQLException{
	
		RateDate rateDate = new RateDate();
		
		rateDate.setExchangeRate(resultSet.getDouble("exchangeRate"));
		GregorianCalendar calendar = new GregorianCalendar(new Locale("pl"));
		rateDate.setDate(resultSet.getDate("date", calendar));
		
		return rateDate;
		
	}
	
}