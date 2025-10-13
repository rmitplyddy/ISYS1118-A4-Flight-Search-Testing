package unittests;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import flight.FlightSearch;

//	Condition 11. Only the following airports are available: "syd" (Sydney), "mel" (Melbourne), 
//	"lax" (Los Angeles), "cdg" (Paris), "del" (Delhi), "pvg" (Shanghai) and "doh" (Doha). eg for a flight from Melbourne to Shanghai, 
//	the departure airport code would be "mel" and the destination airport code would be "pvg". 
//	Furthermore, the departure airport and destination airport cannot be the same.


class AirportCodeTest {

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
	private List<String> errors;
	private String expectedError;

	@BeforeEach
	void setUp() throws Exception {
		fs = new FlightSearch();
		errors = new ArrayList<>();	
	}

	// public boolean searchAirports() {
	// 	// Helper method to run flight search with given airport codes
	// 	// sets up the fixed attributes for flight search to accept airport values
	// 	return fs.runFlightSearch(departureDate, departureAirportCode, 
	// 	emergencyRowSeating, returnDate, destinationAirportCode, seatingClass, 
	// 	adultPassengerCount, childPassengerCount, infantPassengerCount);
	// }

	@ParameterizedTest
    @CsvSource({"syd, mel", "lax, cdg", "del, pvg", "doh, syd"})
    public void acceptValidCombinations(String dep, String arr) {
        fs.validateAirportCode(dep, arr, errors);
        assertTrue(errors.isEmpty());
    }




	// @Test
	// public void acceptSydtoMel() {
	// 	// Test with valid departure and destination airport codes
	// 	departureAirportCode = "syd"; 
	// 	destinationAirportCode = "mel";
	// 	fs.validateAirportCode(departureAirportCode, errors);
	// 	assertTrue(errors.isEmpty());
	// }

	// @Test
	// public void acceptLaxtoCdg() {
	// 	// Test with valid departure and destination airport codes
	// 	departureAirportCode = "lax"; 
	// 	destinationAirportCode = "cdg"; 
	// 	fs.validateAirportCode(departureAirportCode, errors);
	// 	assertTrue(errors.isEmpty());
	// }

	// @Test
	// public void acceptDeltoPvg() {
	// 	// Test with valid departure and destination airport codes
	// 	departureAirportCode = "del"; 
	// 	destinationAirportCode = "pvg";
	// 	fs.validateAirportCode(departureAirportCode, errors);
	// 	assertTrue(errors.isEmpty());
	// }

	// @Test
	// public void acceptDohToSyd() {
	// 	// Test with valid departure and destination airport codes
	// 	departureAirportCode = "doh"; 
	// 	destinationAirportCode = "syd";
	// 	fs.validateAirportCode(departureAirportCode, errors);
	// 	assertTrue(errors.isEmpty());
	// }

	@Test
	public void rejectSameDepartureAndDestinationAirportCode() {
		// Test with the same departure and destination airport codes
		departureAirportCode = "mel";
		destinationAirportCode = "mel";
		fs.validateAirportCode(departureAirportCode, destinationAirportCode, errors);
		expectedError = "Departure and destination airport codes cannot be the same";
		assertEquals(expectedError, errors.get(0), "error message");

		// test with a different valid code
		// to ensure the function departure and destination variables are set
		// correctly.
		departureAirportCode = "lax"; // Valid code
		destinationAirportCode = "mel"; // Same as departure code
		fs.validateAirportCode(departureAirportCode, destinationAirportCode, errors);
		assertTrue(errors.isEmpty());

	}

	@Test
	void rejectInvalidDepartureAirportCode() {

		String departureAirportCode = "abc"; // Invalid code
		String arrivalAirportCode = "xyz"; // Invalid code
		expectedError = "Invalid airport code: " + departureAirportCode;
		// Test with an invalid departure airport code
		fs.validateAirportCode(departureAirportCode, arrivalAirportCode, errors);
		assertEquals(expectedError, errors.get(0));
	}
}
