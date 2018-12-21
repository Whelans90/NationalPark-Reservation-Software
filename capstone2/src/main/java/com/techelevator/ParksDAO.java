package com.techelevator;

import java.util.List;
import java.util.Queue;

public interface ParksDAO {

	
	/**
	 * Gets all parks from datastore, needs to return in alphabetical order, trying to use a queue
	 * 
	 */
	public String[] returnAllParks();
	
	/**
	 * Lookup a park by it's ID. should return one park
	 */
	public Park searchParkById(Long i);
	
	/**
	 * lookup a park by it's location
	 */
	public Park searchParkByLocation(String location);
	
	
	public Park searchParkByName(String name);
	
	

	
	
}
