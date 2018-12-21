package com.techelevator;

import java.util.List;

public interface CampgroundDAO {

	/**
	 * method to return all open campsites, time of year availability, not reservations open to close
	 */
	public List<Campground> searchAvailableCampgrounds(int searchMonth);
	
	/**
	 * displays all campgrounds in a park, 
	 */
	public String [] displayAllCampgroundsInPark(Long parkId);
	
	/**
	 * search campgrounds by name
	 */
	public Campground searchCampgroundByName(String searchName);
	
	/**
	 * search campgrounds by fee, pass in max fee and return matching campgrounds
	 */
	public List<Campground> searchCampgroundByFee(Long maxFee);
	
	/**
	 * display all campgrounds
	 */
	public Campground[] displayAllCampgrounds(Long park_id);
	
	/**
	 * display campground by id
	 */
	public Campground searchCampgroundById(int id);
}
