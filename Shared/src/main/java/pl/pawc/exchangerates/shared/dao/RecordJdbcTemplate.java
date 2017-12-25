package pl.pawc.exchangerates.shared.dao;

import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import pl.pawc.exchangerates.shared.model.RateDate;
import pl.pawc.exchangerates.shared.model.Record;
import pl.pawc.exchangerates.shared.model.Result;
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

	public Result getResult(String targetCurrency, String baseCurrency, String dateStart, String dateEnd) {
		Result result = new Result();
		ArrayList<RateDate> list = new ArrayList<RateDate>();
		
		result.setTargetCurrency(targetCurrency);
		result.setBaseCurrency(baseCurrency);
		
		if(baseCurrency.equals(targetCurrency)){
			list = getRateDates("EUR", dateStart, dateEnd);
			list = Util.fillWithOnes(list);
		}
		else if(targetCurrency.equals("PLN")){
			list = getRateDates(baseCurrency, dateStart, dateEnd);
			list = Util.inverseRates(list);
		}
		else if(!"PLN".equals(targetCurrency) && !"PLN".equals(baseCurrency)){
			list = getRateDates(targetCurrency, baseCurrency, dateStart, dateEnd);
		}
		else{
			list = getRateDates(targetCurrency, dateStart, dateEnd);
		}
		
		result.setRateDate(list);
		
		double[] minMax = Util.getMinMax(list);
		
		result.setMin(minMax[0]);
		result.setMax(minMax[1]);
		
		return result;
	}
	
	public ArrayList<RateDate> getRateDates(String targetCurrency, String dateStart, String dateEnd){
		String SQL = "select exchangeRate,date from records where targetCurrency='"+targetCurrency+"' "
			+ "and date between '"+dateStart+"' and '"+dateEnd+"';";
		ArrayList<RateDate> results = (ArrayList<RateDate>) jdbcTemplateObject.query(SQL, new RateDateMapper()); 
		return results;
	}
	
	public ArrayList<RateDate> getRateDates(String targetCurrency, String baseCurrency, String dateStart, String dateEnd){
		String SQL = "select rate1/rate2 as exchangeRate, d1 as date from "+
			"(select exchangeRate as rate1, date as d1 from records where targetCurrency='"+targetCurrency+"') as tab1 "+
			"join (select exchangeRate as rate2,  date as d2 from records where targetCurrency='"+baseCurrency+"') as tab2 on d1=d2 "+
			"where d1 and d2 between '"+dateStart+"' and '"+dateEnd+"' "+
			"order by date asc;";
		ArrayList<RateDate> results = (ArrayList<RateDate>) jdbcTemplateObject.query(SQL, new RateDateMapper()); 
		return results;
	}

}