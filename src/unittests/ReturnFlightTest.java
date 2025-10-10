package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

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
	public void rejectNullReturnFlights() {

		// Ensure that a valid return date is also set
		departureAirportCode = "syd"; 
		destinationAirportCode = "";
		assertNotNull(fs.getDestinationAirportCode()); // maybe an exception should be called instead?? this doesn't seem to be a viable test

	}

	@Test
	public void rejectReturnDateBeforeDepartureDate() {
		// Test with return date before departure date


}
