package com.techelevator;

public class Site {

	private Long siteId;
	public Long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}
	private Long campgroundId;
	private Long siteNumber;
	private Long maxOccupancy;
	private boolean isAccessilbe;
	private Long maxRvLength;
	private boolean utilities;
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public Long getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(Long siteNumber) {
		this.siteNumber = siteNumber;
	}
	public Long getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(Long maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public boolean isAccessilbe() {
		return isAccessilbe;
	}
	public void setAccessilbe(boolean isAccessilbe) {
		this.isAccessilbe = isAccessilbe;
	}
	public Long getMaxRvLength() {
		return maxRvLength;
	}
	public void setMaxRvLength(Long maxRvLength) {
		this.maxRvLength = maxRvLength;
	}
	public boolean isUtilities() {
		return utilities;
	}
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	
	
}
