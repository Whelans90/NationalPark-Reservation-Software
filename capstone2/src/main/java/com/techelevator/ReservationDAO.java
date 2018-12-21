package com.techelevator;

import java.time.LocalDate;
import java.util.List;

public interface ReservationDAO {

	
	/**
	 *  search reservation by campers name
	 */
	public Long searchReservationByName(String name);
	
	/**
	 * insert reservation to db
	 */
	public void insertReservation(Long siteId, String name, LocalDate fromDate, LocalDate toDate);
	
	/**
	 * cancel reservation
	 */
	public void deleteReservation();
	
	/**
	 * get reservation by site ID
	 */
	public Reservation searchReservationBySiteId();
	
	/**
	 * find by reservation Id
	 */
	public Reservation searchReservationById();
	
	/** 
	 * searchReservations by campgropunds
	 */
	public List<Reservation> searchReservationsByCampground();
}
