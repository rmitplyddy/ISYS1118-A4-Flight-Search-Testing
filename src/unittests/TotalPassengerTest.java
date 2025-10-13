/**
 * Test class to validate the total number of passengers per search.
 * 
 * This class contains unit tests to ensure that the flight search
 * functionality correctly handles various passenger count scenarios by checking
 * boundary conditions on the acceptable range of total passengers (1 to 9).
 */


package unittests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import flight.FlightSearch;


//	Condition 1. The total number of passenger per search must be at least 1 and cannot exceed 9.

class TotalPassengerTest {

	private FlightSearch fs;
	private static final String DEPARTURE_DATE = TestDataGenerator.generateValidDeptDate(); // Set to tomorrow's date
	private static final String  DEPARTURE_AIRPORT_CODE = "syd";
   	private static final boolean EMERGENCY_ROW_SEATING = false;
   	private static final String RETURN_DATE = 
			TestDataGenerator.generateValidReturnDate(); // Set to the day 
													// after tomorrow's date
   	private static final String DESTINATION_AIRPORT_CODE = "mel"; 
   	private static final String SEATING_CLASS = "economy";
   	private int adultPassengerCount;
	private int childPassengerCount;
	private int infantPassengerCount;
	private List<String> errors;
	private String expectedError;


	@BeforeEach
	void setUp() throws Exception {
		fs = new FlightSearch();
		errors = new ArrayList<>();
	}
	
	public boolean searchPassengers() {
		// Helper method to run flight search with given passenger counts
		// sets up the fixed attributes for flight search to accept passenger values
		return fs.runFlightSearch(DEPARTURE_DATE, DEPARTURE_AIRPORT_CODE, 
		EMERGENCY_ROW_SEATING, RETURN_DATE, DESTINATION_AIRPORT_CODE, SEATING_CLASS, 
		adultPassengerCount, childPassengerCount, infantPassengerCount);
	}


	// static Stream<Arguments> lowerBoundaryTestData() {
	// 	return Stream.of(
	// 		Arguments.of(0, 0, 0, false),
	// 		Arguments.of(1, 0, 0, true)
	// 	);
	// }

	// static Stream<Arguments> upperBoundaryTestData() {
	// 	return Stream.of(
	// 		Arguments.of(5, 3, 1, true), 
	// 		Arguments.of(5, 3, 2, false) 
	// 	);
	// }

	// @ParameterizedTest
	// @MethodSource("lowerBoundaryTestData")
	// void testPassengerCountLowerBoundary(int adults, int children, int infants, boolean shouldPass) {
	// 	List<String> errors = new ArrayList<>();
	// 	fs.validatePassengerCounts(adults, children, infants, errors);
		
	// 	if (shouldPass) {
	// 		assertTrue(errors.isEmpty(), "Should accept " + (adults + children + infants) + " passengers");
	// 	} else {
	// 		assertFalse(errors.isEmpty(), "Should reject " + (adults + children + infants) + " passengers");
	// 		assertEquals("Total passengers must be between 1 and 9", errors.get(0));
	// 	}
	// }

	// @ParameterizedTest
	// @MethodSource("upperBoundaryTestData")
	// void testPassengerCountUpperBoundary(int adults, int children, int infants, boolean shouldPass) {
	// 	List<String> errors = new ArrayList<>();
	// 	fs.validatePassengerCounts(adults, children, infants, errors);
		
	// 	if (shouldPass) {
	// 		assertTrue(errors.isEmpty(), "Should accept " + (adults + children + infants) + " passengers");
	// 	} else {
	// 		assertFalse(errors.isEmpty(), "Should reject " + (adults + children + infants) + " passengers");
	// 		assertEquals("Total passengers must be between 1 and 9", errors.get(0));
	// 	}
	// }

	@Test
	void rejectInvalidLeastPassengerCount() {

		// Test with 0 passengers
		// minimum acceptance case is one passenger and must be rejected

		expectedError = "Total passengers must be between 1 and 9";

		adultPassengerCount = 0;
		childPassengerCount = 0;
		infantPassengerCount = 0;
		fs.validatePassengerCounts(adultPassengerCount, childPassengerCount, infantPassengerCount, errors);
		assertEquals(expectedError, errors.get(0), "feedback message");
	}

	@Test
	void rejectInvalidMostPassengerCount() {
		// Test with 10 passengers
		// maximum acceptance case is nine passengers and must be rejected
		
		expectedError = "Total passengers must be between 1 and 9";

		adultPassengerCount = 4;
		childPassengerCount = 4;
		infantPassengerCount = 2;
		fs.validatePassengerCounts(adultPassengerCount, childPassengerCount, infantPassengerCount, errors);
		assertEquals(expectedError, errors.get(0));
	}

	@Test
	void acceptValidMidPassengerCount() {
		// Test with 5 passengers
		// variables will be set when valid
		adultPassengerCount = 2;
		childPassengerCount = 2;
		infantPassengerCount = 1;
		fs.validatePassengerCounts(adultPassengerCount, childPassengerCount, infantPassengerCount, errors);
		assertTrue(errors.isEmpty());
	}

	@Test
	void acceptValidLeastPassengerCount() {
		// Test with 1 passenger
		// minimum acceptance case is one passenger and must be accepted
		adultPassengerCount = 1;
		childPassengerCount = 0;
		infantPassengerCount = 0;
		fs.validatePassengerCounts(adultPassengerCount, childPassengerCount, infantPassengerCount, errors);
		assertTrue(errors.isEmpty());
	}

	@Test
	void acceptValidMostPassengerCount() {
		// Test with 9 passengers
		// maximum acceptance case is nine passengers and must be accepted
		// variables will be set when valid
		adultPassengerCount = 5;
		childPassengerCount = 3;
		infantPassengerCount = 1;
		fs.validatePassengerCounts(adultPassengerCount, childPassengerCount, infantPassengerCount, errors);
		assertTrue(errors.isEmpty());
	}
}
