package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import flight.FlightSearch;






//	Condition 2. Children cannot be seated in emergency row seating or first class.
//
//	Condition 3. Infants cannot be seated in emergency row seating or business class.
//
//	Condition 4. All children (aged 2-11 years old) must be seated immediately next to at least one adult passenger 
//	(ie up to 2 children per adult). eg if the adult passenger count is 2, then up to 4 child passengers are allowed.
//
//	Condition 5. Each infant (<2 years old) must be seated on an accompanying adults lap (only one infant is allowed per adult)
//




class ChildrenPassengerTest {


	private FlightSearch fs;
	private String  departureDate = TestDataGenerator.generateValidDeptDate(); // Set to tomorrow's date
   	private String  departureAirportCode = "syd";
   	private boolean emergencyRowSeating = false; // default to false
   	private String  returnDate = TestDataGenerator.generateValidReturnDate(); // Set to the day after tomorrow's date
   	private String  destinationAirportCode = "mel"; 
   	private String  seatingClass = "economy"; // default to economy
   	private int adultPassengerCount;
	private int childPassengerCount;
	private int infantPassengerCount;


	@BeforeEach
	void setUp() throws Exception {
		fs = new FlightSearch();
	}
	

	public boolean searchPassengers() {
		// Helper method to run flight search with given passenger counts
		// sets up the fixed attributes for flight search to accept passenger values
		return fs.runFlightSearch(departureDate, departureAirportCode, 
		emergencyRowSeating, returnDate, destinationAirportCode, seatingClass, 
		adultPassengerCount, childPassengerCount, infantPassengerCount);
	}

	// --- condition 2 - test children cannot be seated in emergency row 
	// seating or first class ---

	@Test
	public void rejectChildrenInEmergencyRowSeating() {
		adultPassengerCount = 2;
		childPassengerCount = 1;
		infantPassengerCount = 0;
		emergencyRowSeating = true;
		assertFalse(searchPassengers());
	}

	@Test
	public void acceptChildrenInNonEmergencyRowSeating() {
		adultPassengerCount = 2;
		childPassengerCount = 1;
		infantPassengerCount = 0;
		emergencyRowSeating = false;
		assertTrue(searchPassengers());
	}
	
	@Test
	public void rejectChildrenInFirstClass() {
		adultPassengerCount = 2;
		childPassengerCount = 1;
		infantPassengerCount = 0;
		emergencyRowSeating = false;
		seatingClass = "first";
		assertFalse(searchPassengers());
	}

	@Test
	public void acceptChildrenInEconomyClass() {
		adultPassengerCount = 2;
		childPassengerCount = 1;
		infantPassengerCount = 0;
		emergencyRowSeating = false;
		seatingClass = "economy";
		assertTrue(searchPassengers());	
	}


	// --- condition 3 - test infants cannot be seated in emergency row

	@Test
	public void rejectInfantsInEmergencyRowSeating() {
		adultPassengerCount = 2;
		childPassengerCount = 0;
		infantPassengerCount = 1;
		emergencyRowSeating = true;
		assertFalse(searchPassengers());
	}

	@Test
	public void acceptInfantsInNonEmergencyRowSeating() {
		adultPassengerCount = 2;
		childPassengerCount = 0;
		infantPassengerCount = 1;
		emergencyRowSeating = false;
		assertTrue(searchPassengers());
	}

	@Test
	public void rejectInfantsInBusinessClass() {
		adultPassengerCount = 2;
		childPassengerCount = 0;
		infantPassengerCount = 1;
		emergencyRowSeating = false;
		seatingClass = "business";
		assertFalse(searchPassengers());
	}

	@Test
	public void acceptInfantsInEconomyClass() {
		adultPassengerCount = 2;
		childPassengerCount = 0;
		infantPassengerCount = 1;
		emergencyRowSeating = false;
		seatingClass = "economy";
		assertTrue(searchPassengers());
	}

	// --- condition 4 - test all children must be seated next to an adult ---

	@Test
	public void rejectTooManyChildrenPerAdult() {
		adultPassengerCount = 2;
		childPassengerCount = 5;
		infantPassengerCount = 0;
		assertFalse(searchPassengers());
	}
	
	@Test
	public void acceptValidChildrenPerAdult() {
		adultPassengerCount = 2;
		childPassengerCount = 4;
		infantPassengerCount = 0;
		assertTrue(searchPassengers());
	}

	// --- condition 5 - test each infant must be seated on an accompanying adult's lap ---

	@Test
	public void rejectTooManyInfantsPerAdult() {
		adultPassengerCount = 2;
		childPassengerCount = 0;
		infantPassengerCount = 3;
		assertFalse(searchPassengers());
	}

	@Test
	public void acceptValidInfantsPerAdult() {
		adultPassengerCount = 2;
		childPassengerCount = 0;
		infantPassengerCount = 2;
		assertTrue(searchPassengers());
	}
	

}
