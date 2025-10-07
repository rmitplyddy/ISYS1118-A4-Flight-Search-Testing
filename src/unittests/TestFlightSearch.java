package unittests;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import flight.FlightSearch;


//	Condition 1. The total number of passenger per search must be at least 1 and cannot exceed 9.


class TotalPassengerTest {

	private FlightSearch fs;
	private String  departureDate = LocalDate.now().plusDays(1).toString(); // Set to tomorrow's date
   	private String  departureAirportCode = "syd";
   	private boolean emergencyRowSeating = false;
   	private String  returnDate = LocalDate.now().plusDays(7).toString(); // Set to the day after tomorrow's date
   	private String  destinationAirportCode = "mel"; 
   	private String  seatingClass = "economy";
   	private int adultPassengerCount;
	private int childPassengerCount;
	private int infantPassengerCount;


	@BeforeEach
	void setUp() throws Exception {
		fs = new FlightSearch();
	}

	
	public boolean searchPassengers(int adults, int children, int infants) {
		// Helper method to run flight search with given passenger counts
		return fs.runFlightSearch(departureDate, departureAirportCode, 
		emergencyRowSeating, returnDate, destinationAirportCode, seatingClass, 
		adults, children, infants);
	}


	@Test
	void rejectInvalidLeastPassengerCount() {
		// Test with 0 passengers
		// minimum acceptance case is one passenger and must be rejected
		adultPassengerCount = 0;
		childPassengerCount = 0;
		infantPassengerCount = 0;
		assertFalse(searchPassengers(adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
	}

	@Test
	void rejectInvalidMostPassengerCount() {
		// Test with 10 passengers
		adultPassengerCount = 4;
		childPassengerCount = 4;
		infantPassengerCount = 2;
		assertFalse(searchPassengers(adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
	}

	@Test
	void acceptValidMidPassengerCount() {
		// Test with 5 passengers
		adultPassengerCount = 2;
		childPassengerCount = 2;
		infantPassengerCount = 1;
		assertTrue(searchPassengers(adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
	}

	@Test
	void acceptValidLeastPassengerCount() {
		// Test with 1 passenger
		// minimum acceptance case is one passenger and must be accepted
		adultPassengerCount = 1;
		childPassengerCount = 0;
		infantPassengerCount = 0;
		assertTrue(searchPassengers(adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
	}

	@Test
	void acceptValidMostPassengerCount() {
		// Test with 9 passengers
		// maximum acceptance case is nine passengers and must be accepted
		adultPassengerCount = 5;
		childPassengerCount = 3;
		infantPassengerCount = 1;
		assertTrue(searchPassengers(adultPassengerCount, childPassengerCount, 
			infantPassengerCount));

	}
}
