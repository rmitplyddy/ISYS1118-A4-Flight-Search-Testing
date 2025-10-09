package unittests;



import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import flight.FlightSearch;


//	Condition 1. The total number of passenger per search must be at least 1 and cannot exceed 9.


// [NOTE]: set any other tests - characters in passenger count fields, negative numbers, zero, decimal points, special characters ??


class TotalPassengerTest {

	private FlightSearch fs;
	private String  departureDate = TestDataGenerator.generateValidDeptDate(); // Set to tomorrow's date
   	private String  departureAirportCode = "syd";
   	private boolean emergencyRowSeating = false;
   	private String  returnDate = TestDataGenerator.generateValidReturnDate(); // Set to the day after tomorrow's date
   	private String  destinationAirportCode = "mel"; 
   	private String  seatingClass = "economy";
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


	@Test
	void rejectInvalidLeastPassengerCount() {
		// Test with 0 passengers
		// minimum acceptance case is one passenger and must be rejected
		adultPassengerCount = 0;
		childPassengerCount = 0;
		infantPassengerCount = 0;
		assertFalse(searchPassengers());
	}

	@Test
	void rejectInvalidMostPassengerCount() {
		// Test with 10 passengers
		adultPassengerCount = 4;
		childPassengerCount = 4;
		infantPassengerCount = 2;
		assertFalse(searchPassengers());
	}

	@Test
	void acceptValidMidPassengerCount() {
		// Test with 5 passengers
		// variables will be set when valid
		adultPassengerCount = 2;
		childPassengerCount = 2;
		infantPassengerCount = 1;
		assertEquals(adultPassengerCount, fs.getAdultPassengerCount());
		assertEquals(childPassengerCount, fs.getChildPassengerCount());
		assertEquals(infantPassengerCount, fs.getInfantPassengerCount());
	}

	@Test
	void acceptValidLeastPassengerCount() {
		// Test with 1 passenger
		// minimum acceptance case is one passenger and must be accepted
		adultPassengerCount = 1;
		childPassengerCount = 0;
		infantPassengerCount = 0;
		assertTrue(searchPassengers());
	}

	@Test
	void acceptValidMostPassengerCount() {
		// Test with 9 passengers
		// maximum acceptance case is nine passengers and must be accepted
		// variables will be set when valid
		adultPassengerCount = 5;
		childPassengerCount = 3;
		infantPassengerCount = 1;
		searchPassengers();
		assertEquals(adultPassengerCount, fs.getAdultPassengerCount());
		assertEquals(childPassengerCount, fs.getChildPassengerCount());
		assertEquals(infantPassengerCount, fs.getInfantPassengerCount());


	}
}
