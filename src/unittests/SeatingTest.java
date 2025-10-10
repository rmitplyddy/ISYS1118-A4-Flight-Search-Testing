package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import flight.FlightSearch;

//	Condition 9. The seating class must be one of ("economy', "premium economy", "business", "first").
//
//	Condition 10. Only economy class flights have emergency row seating.
//


class SeatingTest {

	private FlightSearch fs;
	private String  departureDate = TestDataGenerator.generateValidDeptDate(); // Set to tomorrow's date
   	private String  departureAirportCode = "syd";
   	private boolean emergencyRowSeating = false; // default to false
   	private String  returnDate = TestDataGenerator.generateValidReturnDate(); // Set to the day after tomorrow's date
   	private String  destinationAirportCode = "mel"; 
   	private String  seatingClass = "economy"; // default to economy
   	private int adultPassengerCount = 1;
	private int childPassengerCount = 0;
	private int infantPassengerCount = 0;


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
	public void acceptValidSeatingClasses() {
		// Test with valid seating classes
		seatingClass = "economy";
		assertTrue(searchAirports());
		assertEquals(seatingClass, fs.getSeatingClass());

		seatingClass = "premium economy";
		assertTrue(searchAirports());
		assertEquals(seatingClass, fs.getSeatingClass());

		seatingClass = "business";
		assertTrue(searchAirports());
		assertEquals(seatingClass, fs.getSeatingClass());

		seatingClass = "first";
		assertTrue(searchAirports());
		assertEquals(seatingClass, fs.getSeatingClass());
	}

	@Test
	public void testRejectEmergencyRowForNonEconomy() {
		// Test with emergency row seating selected for non-economy class
		emergencyRowSeating = true;
		seatingClass = "business";
		assertFalse(searchAirports());

		seatingClass = "first";
		assertFalse(searchAirports());

		seatingClass = "premium economy";
		assertFalse(searchAirports());
	}

	@Test
	public void testAcceptEmergencyRowForEconomy() {
		// Test with emergency row seating selected for economy class
		emergencyRowSeating = true;
		seatingClass = "economy";
		assertTrue(searchAirports());
	}

}
