package com.techelevator.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Park;
import com.techelevator.ParksDAO;

public class JDBCParksDAO implements ParksDAO {
	
private JdbcTemplate jdbcTemplate;
	
	public JDBCParksDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	
	}

	@Override
	public String [] returnAllParks() {
		String sql = "SELECT name FROM park ORDER BY name;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		Queue<String> outputQueue = generateQueue(results);
		String[] parksArray = outputQueue.toArray(new String[outputQueue.size()+1]);
		if(outputQueue.size() > 0) {
			int counter = 0;
			for(String park : outputQueue) {

				parksArray[counter]= park;
				counter++;
			}
		}
		return parksArray;
	}

	@Override
	public Park searchParkById(Long id) {
		String sql = "SELECT * FROM park WHERE park_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, id);
		results.next();
		Park thisPark = createPark(results);
		
		return thisPark;
	}

	@Override
	public Park searchParkByLocation(String location) {
		String sql = "SELECT * FROM park WHERE location = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, location);
		results.next();
		Park thisPark = createPark(results);
		
		return thisPark;
	}
	private Queue<String> generateQueue (SqlRowSet results) {
		Queue<String> parks = new LinkedList<String>();
				

		while(results.next()) {
			String name = results.getString("name");
			parks.add(name);
		}
		return parks;
		
		
	}

	private Park createPark(SqlRowSet results) {
		String name = results.getString("name");
		Long id = results.getLong("park_id");
		String location = results.getString("location");
		LocalDate established = results.getDate("establish_date").toLocalDate();
		Long area = results.getLong("area");
		Long visitors = results.getLong("visitors");
		String description = results.getString("description");
		
		Park thisPark = new Park();
		thisPark.setName(name);
		thisPark.setParkId(id);
		thisPark.setLocation(location);
		thisPark.setEstablishedDate(established);
		thisPark.setArea(area);
		thisPark.setVisitors(visitors);
		thisPark.setDescription(description);
		
		return thisPark;
	}

	@Override
	public Park searchParkByName(String name) {
		String sql = "SELECT * FROM park WHERE name = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
		results.next();
		Park thisPark = createPark(results);
		return thisPark;
	}

}
