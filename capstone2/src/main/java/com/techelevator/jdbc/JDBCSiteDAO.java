package com.techelevator.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Site;
import com.techelevator.SiteDAO;

public class JDBCSiteDAO implements SiteDAO {

	private JdbcTemplate jdbcTemplate;
	
	public JDBCSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		
	}
	
	
	
	@Override
	public Site searchSiteById(Long id) {
		String sqlGetSiteById = "SELECT * FROM site WHERE site_id=?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetSiteById, id);
		Site site = siteCreator(results);
		return site;
	}

	@Override
	public Site searchSiteBySiteNumber(Long campgroundId, Long siteNumber) {
		String sqlSearchBySiteNumber = "SELECT * FROM site WHERE site_number = ? AND campground_id = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchBySiteNumber, siteNumber, campgroundId);
		Site site = siteCreator(results);
		return site;
	}

	@Override
	public List<Site> searchSitesByMaxOccupancy(Long minimumOccupancy) {
		String sql = "SELECT * FROM site WHERE max_occupancy >= ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, minimumOccupancy);
		List<Site> outputList = listGenerator(results);
		return outputList;
	}

	@Override
	public List<Site> returnSitesWithUtilites() {
		String sql = "SELECT * FROM site WHERE utilities = true;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		List<Site> outputList = listGenerator(results);
		return outputList;
	}

	@Override
	public List<Site> returnSitesMaxRvLength(Long minimumLength) {
		String sql = "SELECT * FROM site WHERE max_rv_length >= ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql,minimumLength);
		List<Site> outputList = listGenerator(results);
		return outputList;
	}

	@Override
	public List<Site> searchByAccessible() {
		String sql = "SELECT * FROM site WHERE accessible = true;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
		List<Site> outputList = listGenerator(results);
		return outputList;
	}
	@Override
	public List<Site> returnAvailableSites(Long campgroundId, LocalDate startDate, LocalDate endDate) {
		String sql = "SELECT * FROM site " + 
				"JOIN campground " + 
				"ON campground.campground_id = site.campground_id " + 
				"WHERE campground.campground_id = ? " + 
				"AND site.site_id NOT IN " + 
				"(Select site_id FROM reservation " + 
				"WHERE ? > from_date AND ? < to_date " + 
				"OR ? > from_date AND ? < to_date " + 
				"UNION SELECT site_id FROM reservation WHERE " + 
				"? < from_date AND ? > to_date) LIMIT 5;";
		
//		String sql = "SELECT * FROM site JOIN campground ON campground.campground_id = site.campground_id WHERE campground.campground_id = ? AND site.site_id NOT IN (SELECT site_id FROM reservation WHERE ? > from_date AND ? < to_date OR ? > from_date AND ? < to_date UNION SELECT site_id FROM reservation WHERE ? < from_date AND ? > to_date) LIMIT 5;";
		
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, campgroundId, startDate, startDate, endDate, endDate, startDate, endDate);
		
		List<Site> availableSites = listGenerator(results);
		return availableSites;
	
	}

	public List<Site> listGenerator (SqlRowSet results){
		List<Site> siteList = new ArrayList<Site>();
		while (results.next()) {
			Site thisSite = siteCreator(results);	
			siteList.add(thisSite);
		}
		return siteList;
	}

	public Site siteCreator(SqlRowSet results) {
		Long siteId = results.getLong("site_id");
		Long campgroundId = results.getLong("campground_id");
		Long siteNumber = results.getLong("site_number");
		Long maxOccupancy = results.getLong("max_occupancy");
		boolean isAccessible = results.getBoolean("accessible");
		Long maxRvLength = results.getLong("max_rv_length");
		boolean hasUtilities = results.getBoolean("utilities");
		
		Site thisSite = new Site();
		thisSite.setSiteId(siteId);
		thisSite.setCampgroundId(campgroundId);
		thisSite.setSiteNumber(siteNumber);
		thisSite.setMaxOccupancy(maxOccupancy);
		thisSite.setAccessilbe(isAccessible);
		thisSite.setMaxRvLength(maxRvLength);
		thisSite.setUtilities(hasUtilities);
		return thisSite;
	}
	
}
