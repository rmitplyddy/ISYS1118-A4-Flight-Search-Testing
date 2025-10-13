package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import flight.FlightSearch;

//	Condition 8. All flights are two way only (ie include return flights) and the return date cannot be before departure date.


class ReturnFlightTest {

	private FlightSearch fs;
	private String  departureDate;
   	private String  departureAirportCode;
   	private boolean emergencyRowSeating = false; // default to false
   	private String  returnDate;
   	private String  destinationAirportCode; 
   	private String  seatingClass = "economy"; // default to economy
   	private int adultPassengerCount;
	private int childPassengerCount;
	private int infantPassengerCount;
	private static final int UNINITIALISED_INT = -99; // sentinel value for uninitialised int attributes

	@BeforeEach
	void setUp() throws Exception {
		fs = new FlightSearch();
	}

	public boolean searchAirports() {
		// Helper method to run flight search with given airport codes
		// sets up the fixed attributes for flight search to accept airport values
		return fs.runFlightSearch(departureDate, departureAirportCode, 
		emergencyRowSeating, returnDate, destinationAirportCode, seatingClass, 
		adultPassengerCount, childPassengerCount, infantPassengerCount);
	}

	@Test
	public void rejectReturnBeforeDeparture() {
		// Test with return date before departure date
		// Ensure that the return date is after the departure date
		departureDate = "25/12/2025";
		returnDate = "24/12/2025";
		assertFalse(searchAirports());
		assertNull(fs.getReturnDate());
		assertNull(fs.getDepartureDate());
		assertNull(fs.getDestinationAirportCode());
		assertNull(fs.getDepartureAirportCode());
		assertEquals(UNINITIALISED_INT, fs.getAdultPassengerCount());
		assertEquals(UNINITIALISED_INT, fs.getChildPassengerCount());
		assertEquals(UNINITIALISED_INT, fs.getInfantPassengerCount());
	}
	
	@Test
	public void rejectNullReturnFlights() {
		// Test with null return date
		// Ensure that a valid return date is also set
		departureAirportCode = "syd"; 
		destinationAirportCode = "";
		assertFalse(searchAirports());
		assertNull(fs.getDestinationAirportCode());
		assertNull(fs.getDepartureAirportCode());
		assertNull(fs.getReturnDate());
		assertNull(fs.getDepartureDate());
		assertEquals(UNINITIALISED_INT, fs.getAdultPassengerCount());
		assertEquals(UNINITIALISED_INT, fs.getChildPassengerCount());
		assertEquals(UNINITIALISED_INT, fs.getInfantPassengerCount());

	}


}
