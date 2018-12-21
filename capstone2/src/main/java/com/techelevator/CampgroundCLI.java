
// clean up formating on park info
// clean  up formatting on site info
// cleanup formatting on campground info

// calculate reservation costs

// general clean up, refactor, remove unused methods 

//advance search. maybe add in accessible and utilities bonuses






package com.techelevator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.jdbc.JDBCCampgroundDAO;
import com.techelevator.jdbc.JDBCParksDAO;
import com.techelevator.jdbc.JDBCReservationDAO;
import com.techelevator.jdbc.JDBCSiteDAO;
import com.techelevator.campground.view.Menu;

public class CampgroundCLI {

	private static final String MAIN_MENU_OPTION_VIEW_PARKS = "View Parks";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_OPTION_VIEW_PARKS,
			//
			MAIN_MENU_OPTION_EXIT };

	private static final String PARK_MENU_VIEW_CAMPGROUNDS = "View Campgrounds";
	private static final String PARK_MENU_MAKE_RESERVATION = "Make a reservation";
	private static final String PARK_MENU_OPTION_EXIT = "Return to previous screen";
	private static final String[] PARK_MENU_OPTIONS = new String[] { PARK_MENU_VIEW_CAMPGROUNDS,
			PARK_MENU_MAKE_RESERVATION, MAIN_MENU_OPTION_EXIT };

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
	Scanner userInput = new Scanner(System.in);
	private List<Site> siteList;
	private Menu menu;
	private SiteDAO siteDAO;
	private ParksDAO parksDAO;
	private ReservationDAO reservationDAO;
	private CampgroundDAO campgroundDAO;
	private Park currentPark;
	private Long selectedCampground;
	private LocalDate fromDate;
	private LocalDate toDate;

	public static void main(String[] args) {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundCLI application = new CampgroundCLI(dataSource);
		application.run();
	}

	public CampgroundCLI(DataSource datasource) {
		siteDAO = new JDBCSiteDAO(datasource);
		parksDAO = new JDBCParksDAO(datasource);
		reservationDAO = new JDBCReservationDAO(datasource);
		campgroundDAO = new JDBCCampgroundDAO(datasource);
		this.menu = new Menu(System.in, System.out);
	}

	public void run() {
		displayApplicationBanner();

		while (true) {
			printHeading("Main Menu");
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if (choice.equals(MAIN_MENU_OPTION_VIEW_PARKS)) {
				handleViewParks();

			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.exit(0);
			}
		}
	}

	private void displayParkInfo(Long i) {
		Park thispark = parksDAO.searchParkById(i);
		System.out.println(thispark.getDescription());
	}

	private void displayApplicationBanner() {
		System.out.println("Welcome to the National Parks!");
	}

	private void handleViewParks() {
		printHeading("Parks List");
		String[] parknames = parksDAO.returnAllParks();
		parknames[parknames.length - 1] = "Exit";

		String choice = "";

		do {
			choice = (String) menu.getChoiceFromOptions(parknames);
			if(!choice.equals("Exit")) {
				handleParkMenu(choice);
			}
		} while(!choice.equals("Exit"));
	}

	private void handleCampground() {
		printHeading("Campgrounds:");
		Campground[] campgrounds = campgroundDAO.displayAllCampgrounds(currentPark.getParkId());
		String[] campgroundInfo = campgroundInfo(campgrounds);

		// String choice = (String) menu.getChoiceFromOptions(campgroundInfo);
		int counter = 1;
		for (String each : campgroundInfo) {

			System.out.println("#" + counter + " " + each);
			counter++;

		}
		
		boolean done = false;
		
		while(!done) {
			String choice = (String) menu.getChoiceFromOptions(PARK_MENU_OPTIONS);		
			if (choice.equals(PARK_MENU_VIEW_CAMPGROUNDS)) {
				handleCampground();
			} else if (choice.equals(PARK_MENU_MAKE_RESERVATION)) {
				handleReservation();
			} else if (choice.equals(PARK_MENU_OPTION_EXIT)) {
				done = true;
			}
		}

	}

	private void handleParkMenu(String park) {
		printHeading("Park Detaiils");
		currentPark = parksDAO.searchParkByName(park);
		parkInfo(currentPark);

		String choice = (String) menu.getChoiceFromOptions(PARK_MENU_OPTIONS);
		if (choice.equals(PARK_MENU_VIEW_CAMPGROUNDS)) {
			handleCampground();
		} else if (choice.equals(PARK_MENU_MAKE_RESERVATION)) {
			handleReservation();
		} else if (choice.equals(PARK_MENU_OPTION_EXIT)) {
			run();
		}
	}

	public void handleReservation() {
		Campground[] campgrounds = campgroundDAO.displayAllCampgrounds(currentPark.getParkId());
		String[] campgroundInfo = campgroundInfo(campgrounds);
		int counter = 1;
		for (String each : campgroundInfo) {

			System.out.println("#" + counter + " " + each);
			counter++;

		}
		System.out.println("Which Campground (Enter 0 to cancel");
		selectedCampground = userInput.nextLong();

		System.out.println("What is the arrival date? __/__/____");
		String startDate = userInput.next();
		fromDate = LocalDate.parse(startDate, formatter);

		System.out.println("What is the departure date? __/__/____");
		String endDate = userInput.next();
		toDate = (LocalDate.parse(endDate, formatter));

		siteList = siteDAO.returnAvailableSites(selectedCampground, fromDate, toDate);
		// List<Site> siteList = siteDAO.returnSitesWithUtilites();
		printHeading("Site No. /t Max Occup. \t Accessible? \t RV Length \t Utility \t Cost");

		// TODO calculate cost of stay
//		for (Site each : siteList) {
//			System.out.println(each.getSiteNumber() + " " + each.getMaxOccupancy() + " " + each.isAccessilbe() + " "
//					+ each.getMaxRvLength() + " " + each.isUtilities() + " totalcost");
//		}

		handleAvailableSites();
	}

	public void handleAvailableSites() {
		Site [] top5Sites = siteList.toArray(new Site [siteList.size()]); 
		int counter = 1;
		for (Site each : top5Sites) {
			System.out.println("(" + counter + ") " +each.getSiteNumber() + " " + each.getMaxOccupancy() + " " + each.isAccessilbe() + " "
					+ each.getMaxRvLength() + " " + each.isUtilities() + " totalcost");
			counter++;
		}
		System.out.println("Which site should be reserved?");
		String chosenSiteId = userInput.next();
		Long parsedSite = Long.parseLong(chosenSiteId);
		Long siteId = top5Sites[(int)(parsedSite -1)].getSiteId();
		System.out.println("What name should the reservation be under?");
		String name = userInput.next();
		
		reservationDAO.insertReservation(siteId, name, fromDate, toDate);
		
		Long reservationNumber = reservationDAO.searchReservationByName(name);
		
		System.out.println("The reservation has been made. The confirmation number is " + reservationNumber);   //TODO add in reservation id
	}

	private void printHeading(String headingText) {
		System.out.println("\n" + headingText);
		for (int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}

	private Object[] listParks(Queue<Park> parks) {
		System.out.println();
		String exit = "Return To Main Menu";
		Object[] parksArray = parks.toArray(new Object[parks.size() + 1]);
		if (parks.size() > 0) {
			int counter = 0;
			for (Park park : parks) {

				String menuOption = park.getName();
				parksArray[counter] = menuOption;
				counter++;
			}
			parksArray[parksArray.length - 1] = exit;
		} else {
			System.out.println("\n*** No results ***");
		}
		return parksArray;
	}

	private void parkInfo(Park thisPark) {
		;
		System.out.println("Name: " + thisPark.getName());
		System.out.println("Location: " + thisPark.getLocation());
		System.out.println("Established " + thisPark.getEstablishedDate());
		System.out.println("Area: " + thisPark.getArea());
		System.out.println("Visitors: " + thisPark.getVisitors());
		System.out.println("\n" + "Description" + thisPark.getDescription());
		System.out.println("Name: " + thisPark.getName());
	}

	private String[] campgroundInfo(Campground[] campgrounds) {
		String[] campgroundInfo = new String[campgrounds.length];
		// int counter = 0;
		for (int i = 0; i < campgrounds.length; i++) {
			Campground camp = campgrounds[i];

			String index = camp.getName() + "\t" + camp.getOpenMonth() + "\t" + camp.getCloseMonth() + "\t$"
					+ Double.toString(camp.getFee());
			campgroundInfo[i] = index;
			// counter ++;
			// campgroundInfo[campgroundInfo.length -1] = "Exit"
		}
		return campgroundInfo;
	}
}
