package unittests;


import flight.FlightSearch;
import utility.DateTestUtils;

import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FlightSearchTest {

	private FlightSearch fs;
	private String departureDate;
	private String departureAirportCode;
	private boolean emergencyRowSeating = false; // default to false
	private String returnDate;
	private String destinationAirportCode; 
	private String seatingClass; // default to economy
	private int adultPassengerCount;
	private int childPassengerCount;
	private int infantPassengerCount;


	@BeforeEach
	void setUp() throws Exception {
		fs = new FlightSearch();
		departureDate = LocalDate.now()
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); 
		departureAirportCode = "syd";
		emergencyRowSeating = false; // default to false
		returnDate = LocalDate.now().plusDays(7)
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		destinationAirportCode = "mel"; 
		seatingClass = "economy"; // default to economy
		adultPassengerCount = 2;
		childPassengerCount = 1;
		infantPassengerCount = 0;
	}

	


	// Test case 1 - Check the function with invalid total number of passengers

	@Test
	void checkInvalidTotalNumberOfPassengers() {
		
		// test total 0 passengers

		adultPassengerCount = 0; 
		childPassengerCount = 0;
		infantPassengerCount = 0;
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, seatingClass, 
			adultPassengerCount, childPassengerCount, infantPassengerCount));

		// Total 10 passengers
		// replace passenger counts only to make total 10
		// all else remains the same

		adultPassengerCount = 5;
		childPassengerCount = 4;
		infantPassengerCount = 1; 
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, seatingClass, 
			adultPassengerCount, childPassengerCount, infantPassengerCount));
	}

	// Test case 2 - Verify REJECTION of children seated in emergency row 
	// or first class

	@Test
	void rejectChildrenInEmergencyRowSeating() {

		// use before each values for children and seatingClass
		// set emergency row seating to true, and use economy class already
		// initialised.

		emergencyRowSeating = true;

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));

		
		// change to first class seating
		// emergency row seating must be false for first class

		emergencyRowSeating = false; // reset to default
		seatingClass = "first"; // change to first class

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));
	}

	// Test case 3 - Verify REJECTION of infants in emergency row seating

	@Test
	void rejectInfantsInEmergencyRowSeating() {

		// use before each values for infants and seatingClass
		// set emergency row seating to true, and use economy class already
		// initialised.

		emergencyRowSeating = true;
		infantPassengerCount = 1; // set to one infant

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));

		// change to business class seating
		// emergency row seating must be false for business class

		emergencyRowSeating = false; // reset to default
		seatingClass = "business"; // change to business class

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));
	}

	// Test Case 4 - Check number of children with an accompanying adult (do not exceed 2 children per adult) 

	@Test
	void rejectExcessChildrenWithoutAdult() {

		// use before each values for children and adults
		// set children to 3 and adults to 1

		adultPassengerCount = 1;
		childPassengerCount = 3;
		
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode,
			emergencyRowSeating, returnDate, destinationAirportCode,
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));

		adultPassengerCount = 0;
		childPassengerCount = 1;

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode,
			emergencyRowSeating, returnDate, destinationAirportCode,
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));
	}

	// 	Test Case 5 - Check number of infant passengers DO NOT exceed the
	// 	number of adult passengers

	@Test
	void rejectMoreInfantsThanAdults() {

		// use before each values for adults and infants
		// set infants to one more than adults

		// (test negatives?)

		adultPassengerCount = 1;
		childPassengerCount = 0;
		infantPassengerCount = 2; // set to one more than adults

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));

		adultPassengerCount = 0; // set to zero
		infantPassengerCount = 1; // set to one infant

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));

	}

	// Test Case 6 - Check Departure date is NOT set in the past

	@Test
	void rejectPastDepartureDate() {

		// use before each values for departure date
		// set departure date to past date

		departureDate = LocalDate.now()
						.minusDays(1).toString(); 

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));
	}

	// Test Case 7 - Check function for INVALID date entries

	@Test
	void rejectInvalidDateEntries() {
		// use before each values for departure and return date
		// set departure date to invalid date format

		// departureDate = "2023/10/10"; // invalid format
		String invalidDateFormat = "yyyy/MM/dd";

		departureDate = LocalDate.now().plusDays(adultPassengerCount)
				.plusDays(10).format(DateTimeFormatter.ofPattern(invalidDateFormat)); // invalid format

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));

		// set return date to invalid date format

		int year = LocalDate.now().getYear();
		year = DateTestUtils.getNextLeapYear(year);
		departureDate = "29/02/" + year; // valid leap year date

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));
	}

	// Test Case 8 - Check INVALID return flight entries

	@Test
	void rejectInvalidReturnFlightEntries() {
		

		// check that the null destination airport code is rejected

		departureAirportCode = "mel";
		destinationAirportCode = "";

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));

		// set return date to before departure date
		// return date - today, departure date - 7 days from today
		destinationAirportCode = "syd";
		departureDate = LocalDate.now().plusDays(7)
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); 
		returnDate = LocalDate.now()
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));
	}

	// Test Case 9 - Check seating class is INVALID

	@Test
	void rejectInvalidSeatingClass() {

		// set seating class to invalid value

		seatingClass = ""; // blank seating class
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));

		seatingClass = "second class"; // invalid seating class

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));
	}

	// Test Case 10 - Check emergency row seating INVALID for NON-ECONOMY classes
	@Test
	void rejectEmergencyRowSeatingForNonEconomy() {
		
		// use before each values for seating class and emergency row seating
		// set seating class to non-economy class
		// set emergency row seating to true

		// child and infant passengers to zero - ensure no rejection
		// based on passenger type
		// infantPassengerCount = 0 at before each initialisation

		childPassengerCount = 0;
		emergencyRowSeating = true;
		seatingClass = "business"; // non-economy class

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode,
			emergencyRowSeating, returnDate, destinationAirportCode,
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));

		// check emergency row seating with first class

		seatingClass = "first";

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode,
			emergencyRowSeating, returnDate, destinationAirportCode,
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));
		
	}

	// Test Case 11 - Check INVALID airport codes entered for departure and destination
	@Test
	void rejectInvalidAirportCodes() {

		// use before each values for departure and destination airport codes
		// set departure airport code to invalid value

		departureAirportCode = "abc";
		destinationAirportCode = "xyz";

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode,
			emergencyRowSeating, returnDate, destinationAirportCode,
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));

		// set both departure and destination airport codes to blank

		departureAirportCode = "";
		destinationAirportCode = "";

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode,
			emergencyRowSeating, returnDate, destinationAirportCode,
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));
	}

	// Test Case 12 - Check the search flight function with VALID entries
	@Test
	void acceptValidFlightSearch() {
		
		// test to check validity of parameter values on the flightSearch function at the boundaries

		// use the beforeEach initialised values first
		// and then change values to test valid combinations

		// beforeEach values include 2 adults, 1 child, 0 infants
		// economy class, non-emergency row seating
		// departure date - today and return date - 7 days from today
		// departure airport - syd, destination airport - mel

		seatingClass = "first";
		adultPassengerCount = 1;
		childPassengerCount = 0;
		infantPassengerCount = 0;

		assertTrue(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));


		// change to business class, 9 adults, no children or infants
		// change departure and destination airport codes
		
		departureAirportCode = "lax";
		emergencyRowSeating = false;
		destinationAirportCode = "cdg";
		seatingClass = "business";
		adultPassengerCount = 9;
		childPassengerCount = 0;
		infantPassengerCount = 0;

		assertTrue(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));

		// change to premium economy, 4 adults, 3 children, 2 infants
		// change departure and destination airport codes

		departureAirportCode = "del";
		emergencyRowSeating = false;
		destinationAirportCode = "pvg";
		seatingClass = "premium economy";
		adultPassengerCount = 4;
		childPassengerCount = 3;
		infantPassengerCount = 2;
		
		assertTrue(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));

		// change to economy, 1 adult, no children or infants
		// change departure and destination airport codes
		// set emergency row seating to true

		departureAirportCode = "doh";
		emergencyRowSeating = true;
		destinationAirportCode = "mel";
		seatingClass = "economy";
		adultPassengerCount = 1;
		childPassengerCount = 0;
		infantPassengerCount = 0;
		
		assertTrue(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));
	}
}
