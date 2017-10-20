package pl.pawc.exchangerates.dao;

import java.util.ArrayList;

import javax.sql.DataSource;

import pl.pawc.exchangerates.model.Record;

public interface RecordDAO {

	public void setDataSource(DataSource dataSource);
	public void insert(Record record);
	public void insert(ArrayList<Record> records);
	public ArrayList<Record> getRecords();
	
}