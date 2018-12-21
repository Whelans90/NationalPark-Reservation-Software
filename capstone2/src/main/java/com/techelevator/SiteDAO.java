package com.techelevator;

import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {

	/**
	 * get site by id, returns a site
	 */
	public Site searchSiteById(Long id);
	
	/**
	 * get site by sitenumber, returns a site
	 */
	public Site searchSiteBySiteNumber(Long campgroundId, Long siteNumber);
	
	/**
	 * get sites by max occupancy, probably returns a list of sites matching or exceeding requested number
	 */
	public List<Site> searchSitesByMaxOccupancy(Long minimumOccupancy);
	
	
	/**
	 * return sites with utilites, list
	 */
	public List<Site> returnSitesWithUtilites();
	
	/**
	 * search by max rv length
	 */
	public List<Site> returnSitesMaxRvLength(Long minimumLength);
	
	/**
	 * Is site accessible
	 */
	public List<Site> searchByAccessible();
	
	public List<Site> returnAvailableSites(Long campgroundId, LocalDate startDate, LocalDate endDate);


	
	

	
	
}
