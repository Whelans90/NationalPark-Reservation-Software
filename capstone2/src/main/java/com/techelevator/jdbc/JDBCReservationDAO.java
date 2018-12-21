package com.techelevator.jdbc;

import java.time.LocalDate;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.Reservation;
import com.techelevator.ReservationDAO;
import com.techelevator.Site;

public class JDBCReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JDBCReservationDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Long searchReservationByName(String name) {
		String sql = "SELECT reservation_id FROM reservation WHERE name = ?;";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sql, name);
		results.next();
		Long resId = results.getLong("reservation_id");
		return resId;
	}

	@Override
	public void insertReservation(Long siteId, String name, LocalDate fromDate, LocalDate toDate) {
		String sql = "INSERT INTO reservation (site_id, name, from_date, to_date)"
				+ "VALUES (?, ?, ?, ?);";
		jdbcTemplate.update(sql, siteId, name, fromDate, toDate);
		
	}

	@Override
	public void deleteReservation() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Reservation searchReservationBySiteId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reservation searchReservationById() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Reservation> searchReservationsByCampground() {
		// TODO Auto-generated method stub
		return null;
	}

}
