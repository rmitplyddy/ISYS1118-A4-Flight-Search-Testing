package unittests;

import flight.FlightSearch;
import utility.DateTestUtils;

import static org.junit.jupiter.api.Assertions.*;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
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

		// initialise with valid values for each test
		// each test will modify as needed

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

	@AfterEach
	public void tearDown() throws Exception {
		fs = null;
	}




	
	public void checkAttributesUninitialised() {
		// method used to check that all attributes are uninitialised
		assertEquals(FlightSearch.getSentinalValue(), 
							fs.getAdultPassengerCount());
		assertEquals(FlightSearch.getSentinalValue(), 
							fs.getChildPassengerCount());
		assertEquals(FlightSearch.getSentinalValue(), 
							fs.getInfantPassengerCount());
		assertNull(fs.getDepartureAirportCode());
		assertNull(fs.getDestinationAirportCode());
		assertNull(fs.getSeatingClass());
		assertNull(fs.getDepartureDate());
		assertNull(fs.getReturnDate());
		// uninitialised primitive boolean is always false
		assertFalse(fs.isEmergencyRowSeating());
	}



					// -- Test case 1 --- // 
	// -- Check the function with invalid total number of passengers -- //

	@Test
	void checkInvalidMinimumNumberOfPassengers() {
		
		// test total 0 passengers

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		adultPassengerCount = 0; 
		childPassengerCount = 0;
		infantPassengerCount = 0;
		// check that the return value is false
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, seatingClass, 
			adultPassengerCount, childPassengerCount, infantPassengerCount));
		
		// POST-condition check that all values remain unchanged
		checkAttributesUninitialised();
	}

	@Test
	void checkInvalidMaximumNumberOfPassengers() {
		// Total 10 passengers
		// replace passenger counts only to make total 10
		// all else remains the same

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		adultPassengerCount = 5;
		childPassengerCount = 4;
		infantPassengerCount = 1; 
		// check that the return value is false
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, seatingClass, 
			adultPassengerCount, childPassengerCount, infantPassengerCount));
		
		// POST-condition check that all values remain unchanged
		checkAttributesUninitialised();
	}

					// -- End of Test case 1 -- //






					// --- Test case 2 --- //
		// --- Verify REJECTION of children seated in emergency row 
					// or first class --- //

	@Test
	void rejectChildrenInEmergencyRowSeating() {


		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// use before each values for children and seatingClass
		// set emergency row seating to true, and use economy class already
		// initialised.

		emergencyRowSeating = true;

		// check that the return value is false
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check all values remain unchanged
		checkAttributesUninitialised();
	}


	@Test
	void rejectChildrenInFirstClassSeating() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// change to first class seating
		// emergency row seating must be false for first class

		emergencyRowSeating = false; // reset to default
		seatingClass = "first"; // change to first class

		// check that the return value is false
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));

		// check that all values remain unchanged
		checkAttributesUninitialised();
	}

					// -- End of Test case 2 -- //


	

						// --- Test case 3 --- //
		// --- Verify REJECTION of infants in emergency row seating --- //

	@Test
	void rejectInfantsInEmergencyRowSeating() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// use before each values for infants and seatingClass
		// set emergency row seating to true, and use economy class already
		// initialised.

		emergencyRowSeating = true;
		infantPassengerCount = 1;

		// check that the return value is false
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check all values remain unchanged
		checkAttributesUninitialised();
	}

	@Test
	void rejectInfantsInBusinessClassSeating() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// change to business class seating
		// emergency row seating must be false for business class

		emergencyRowSeating = false; // reset to default
		seatingClass = "business"; // change to business class
		infantPassengerCount = 1; 

		// check that the return value is false
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));

		// check that all values remain unchanged
		checkAttributesUninitialised();
	}

					// -- End of Test case 3 -- //



					// --- Test Case 4 --- // 

		// --- Check number of children with an accompanying adult (do not exceed 2 children per adult) --- //

	@Test
	void rejectExcessChildrenWithoutAdult() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// use before each values for children and adults
		// set children to 3 and adults to 1

		adultPassengerCount = 1;
		childPassengerCount = 3;

		// check that the return value is false
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode,
			emergencyRowSeating, returnDate, destinationAirportCode,
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check that all values remain unchanged
		checkAttributesUninitialised();

	}

	@Test
	void rejectExcessChildrenWithAdult() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		adultPassengerCount = 0;
		childPassengerCount = 1;
		// check that the return value is false
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode,
			emergencyRowSeating, returnDate, destinationAirportCode,
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));

		// check that all values remain unchanged
		checkAttributesUninitialised();
	}

					// -- End of Test case 4 -- //




					// --- Test Case 5 --- //

		// 	Check number of infant passengers DO NOT exceed the
					// 	number of adult passengers

	@Test
	void rejectMoreInfantsThanAdults() {

		// use before each values for adults and infants
		// set infants to one more than adults

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		adultPassengerCount = 1;
		childPassengerCount = 0;
		infantPassengerCount = 2; // set to one more than adults

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check that all values remain unchanged
		checkAttributesUninitialised();
	}

	@Test
	void rejectInfantsWithNoAdults() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// use before each values for adults and infants
		// set adults to zero

		adultPassengerCount = 0; // set to zero
		infantPassengerCount = 1; // set to one infant

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check that all values remain unchanged
		checkAttributesUninitialised();
	}

					// -- End of Test case 5 -- //




					// --- Test Case 6 --- //

		// --- Check Departure date is NOT set in the past --- //

	@Test
	void rejectPastDepartureDate() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// use before each values for departure date
		// set departure date to past date

		departureDate = LocalDate.now()
						.minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check that all values remain unchanged
		checkAttributesUninitialised();
	}

				// -- End of Test case 6 -- //



					// --- Test Case 7 --- //
		// --- Check Departure and Return dates are in VALID format --- //

	@Test
	void rejectInvalidDateEntries() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// use before each values for departure and return date
		// set departure date to invalid date format

		// departureDate = "2023/10/10"; // invalid format
		String invalidDateFormat = "yyyy/MM/dd";

		departureDate = LocalDate.now().plusDays(adultPassengerCount)
				.plusDays(10).format(DateTimeFormatter.ofPattern(invalidDateFormat)); // invalid format

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check that all values remain unchanged
		checkAttributesUninitialised();
	}

	@Test
	void rejectInvalidLeapYearDate() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// set return date to invalid date format

		int year = LocalDate.now().getYear();
		year = DateTestUtils.getNextLeapYear(year);
		departureDate = "29/02/" + year; // valid leap year date

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check that all values remain unchanged
		checkAttributesUninitialised();
	}

				// -- End of Test case 7 -- //

	

	
					// --- Test Case 8 --- //

		// --- Check INVALID return flight entries --- //

	@Test
	void rejectInvalidReturnFlightEntries() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// check that the null destination airport code is rejected

		departureAirportCode = "mel";
		destinationAirportCode = "";

		// should return false
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));
		// check that all values remain unchanged
		checkAttributesUninitialised();
	}


	@Test
	void rejectReturnDateBeforeDepartureDate() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// set return date to before departure date
		// return date - today, departure date - 7 days from today
		destinationAirportCode = "syd";
		departureDate = LocalDate.now().plusDays(7)
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); 
		returnDate = LocalDate.now()
				.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		
		// should return false
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check that all values remain unchanged
		checkAttributesUninitialised();
	}

				// -- End of Test case 8 -- //


	
					// --- Test Case 9 --- //
			// --- Check seating class is INVALID --- //

	@Test
	void rejectInvalidSeatingClass() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// set seating class to invalid value

		seatingClass = ""; // blank seating class

		// check that the return value is false
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check that all values remain unchanged
		checkAttributesUninitialised();
	}

	@Test
	void rejectNonExistentSeatingClass() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		seatingClass = "second class"; // invalid seating class

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check that all values remain unchanged
		checkAttributesUninitialised();
	}

	// Test Case 10 - Check emergency row seating INVALID for NON-ECONOMY classes
	@Test
	void rejectEmergencyRowSeatingForNonEconomy() {
		
		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();
		
		// use before each values for seating class and emergency row seating
		// set seating class to non-economy class
		// set emergency row seating to true

		// child and infant passengers to zero - ensure no rejection
		// based on passenger type
		// infantPassengerCount = 0 at before each initialisation

		childPassengerCount = 0;
		emergencyRowSeating = true;
		seatingClass = "premium economy";

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode,
			emergencyRowSeating, returnDate, destinationAirportCode,
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check that all values remain unchanged
		checkAttributesUninitialised();
	}
		// check emergency row seating with first class

	@Test
	void rejectEmergencyRowSeatingForFirstClass() {
		
		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// use before each values for seating class and emergency row seating
		// set seating class to first class
		// set emergency row seating to true

		seatingClass = "first";
		emergencyRowSeating = true;

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode,
			emergencyRowSeating, returnDate, destinationAirportCode,
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check that all values remain unchanged
		checkAttributesUninitialised();

	}

	// Test Case 11 - Check INVALID airport codes entered for departure and destination
	@Test
	void rejectInvalidAirportCodes() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// use before each values for departure and destination airport codes
		// set departure airport code to invalid value

		departureAirportCode = "abc";
		destinationAirportCode = "xyz";

		// check that the function returns false
		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode,
			emergencyRowSeating, returnDate, destinationAirportCode,
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check that all values remain unchanged
		checkAttributesUninitialised();
	}

	@Test
	void rejectBlankAirportCodes() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();


		// set both departure and destination airport codes to blank

		departureAirportCode = "";
		destinationAirportCode = "";

		assertFalse(fs.runFlightSearch(departureDate, departureAirportCode,
			emergencyRowSeating, returnDate, destinationAirportCode,
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check that all values remain unchanged
		checkAttributesUninitialised();
	}


				// -- End of Test case 11 -- //


					// --- Test Case 12 --- //

	// --- Check the search flight function with VALID entries --- //

	void checkAttributesInitialised() {
		assertEquals(adultPassengerCount, fs.getAdultPassengerCount());
		assertEquals(childPassengerCount, fs.getChildPassengerCount());
		assertEquals(infantPassengerCount, fs.getInfantPassengerCount());
		assertEquals(departureAirportCode, fs.getDepartureAirportCode());
		assertEquals(destinationAirportCode, fs.getDestinationAirportCode());
		assertEquals(seatingClass, fs.getSeatingClass());
		assertEquals(departureDate, fs.getDepartureDate());
		assertEquals(returnDate, fs.getReturnDate());
		assertEquals(emergencyRowSeating, fs.isEmergencyRowSeating());
	}

	@Test
	void acceptValidFlightSearch() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();
		
		// test to check validity of parameter values on the flightSearch 
		// function at the boundaries

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

		// should return true
		assertTrue(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));

		// check that all the values are set correctly
		checkAttributesInitialised();

	}

	@Test
	void acceptVariousValidFlightSearches() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// change to business class, 9 adults, no children or infants
		// change departure and destination airport codes
		
		departureAirportCode = "lax";
		emergencyRowSeating = false;
		destinationAirportCode = "cdg";
		seatingClass = "business";
		adultPassengerCount = 9;
		childPassengerCount = 0;
		infantPassengerCount = 0;

		// should return true
		assertTrue(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));

		// check that all the values are set correctly
		checkAttributesInitialised();
	}

	@Test
	void acceptAnotherValidFlightSearch() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

		// change to premium economy, 4 adults, 3 children, 2 infants
		// change departure and destination airport codes

		departureAirportCode = "del";
		emergencyRowSeating = false;
		destinationAirportCode = "pvg";
		seatingClass = "premium economy";
		adultPassengerCount = 4;
		childPassengerCount = 3;
		infantPassengerCount = 2;
		
		// check that the return value is true
		assertTrue(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, 
			infantPassengerCount));
		
		// check that all the values are set correctly
		checkAttributesInitialised();
	}


	@Test
	void acceptFinalValidFlightSearch() {

		// PRE-condition check that all values are uninitialised
		checkAttributesUninitialised();

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
		
		// check that the return value is true
		assertTrue(fs.runFlightSearch(departureDate, departureAirportCode, 
			emergencyRowSeating, returnDate, destinationAirportCode, 
			seatingClass, adultPassengerCount, childPassengerCount, infantPassengerCount));
		
		// check that all the values are set correctly
		checkAttributesInitialised();

	}
}
