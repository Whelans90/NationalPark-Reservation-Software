package com.techelevator.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Campground;
import com.techelevator.CampgroundDAO;

public class JDBCCampgroundDAO implements CampgroundDAO {

private JdbcTemplate jdbcTemplate;
	
	public JDBCCampgroundDAO(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
		
	}
	
	
	@Override
	public List<Campground> searchAvailableCampgrounds(int searchMonth) {
		String sql = "SELECT * FROM campground WHERE ? >= CAST(open_from_mm AS INTEGER) AND ? <= CAST(open_to_mm AS INTEGER);";			
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, searchMonth);
		List<Campground> outputList = listGenerator(results);
		return outputList;
	}

	@Override
	public Campground [] displayAllCampgroundsInPark(Long parkId) {
		String sql = "SELECT * FROM campground WHERE park_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql,parkId);
		List<Campground> outputList = listGenerator(results);
		for (Campground each: outputList) {
			
		}
		return outputList;
	}

	@Override
	public Campground searchCampgroundByName(String searchName) {
		String sql = "SELECT * FROM campground WHERE name = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql,searchName);
		results.next();
		
		Campground output = campgroundCreator(results);
		return output;
	}

	@Override
	public List<Campground> searchCampgroundByFee(Long maxFee) {
		String sql = "SELECT * FROM campground WHERE daily_fee <= CAST(? AS MONEY);";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql,maxFee);
		List<Campground> outputList = listGenerator(results);
		return outputList;
	}

	@Override
	public List<Campground> displayAllCampgrounds() {
		String sql = "SELECT * FROM campground;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		List<Campground> outputList = listGenerator(results);
		return outputList;
	}

	@Override
	public Campground searchCampgroundById(int id) {
		String sql = "SELECT * FROM campground WHERE campground_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql,id);
		Campground output = campgroundCreator(results);
		return output;
	}






  private List<Campground> listGenerator (SqlRowSet results){
	List<Campground> campgroundList = new ArrayList<Campground>();
	while (results.next()) {
		Campground thisCampground = campgroundCreator(results);	
		campgroundList.add(thisCampground);
	}
	return campgroundList;
}

  private Campground campgroundCreator(SqlRowSet results) {

	  Long campgroundId = results.getLong("campground_id");
	  String name = results.getString("name");
	  String openMonth = results.getString("open_from_mm");
	  String closeMonth = results.getString("open_to_mm");
	  double fee = results.getDouble("daily_fee");

	  Campground thisCampground = new Campground();
	  thisCampground.setCampgroundId(campgroundId);
	  thisCampground.setName(name);
	  thisCampground.setOpenMonth(openMonth);
	  thisCampground.setCloseMonth(closeMonth);
	  thisCampground.setFee(fee);
	return thisCampground;
}
}
