package com.techelevator;

import java.time.LocalDate;
import java.util.Queue;

public class Park {

	private Long parkId;
	private String name;
	private String location;
	private LocalDate establishedDate;
	private Long area;
	private Long visitors;
	private String description;
	
	
	public Long getParkId() {
		return parkId;
	}
	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getEstablishedDate() {
		return establishedDate;
	}
	public void setEstablishedDate(LocalDate establishedDate) {
		this.establishedDate = establishedDate;
	}
	public Long getArea() {
		return area;
	}
	public void setArea(Long area) {
		this.area = area;
	}
	public Long getVisitors() {
		return visitors;
	}
	public void setVisitors(Long vistitors) {
		this.visitors = vistitors;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
//	public void printQueue(Queue<Park> parks) {
//		for(Park park : parks) {
//			System.out.format(park.getName() + park.getLocation() + park.getParkId() + park.getDescription() + 
//					park.getVisitors() + park.getEstablishedDate() + park.getArea() + "\n");
//			
//		} MOVE TO A MENU CLASS NOT THE PARK OBJECT
	}


