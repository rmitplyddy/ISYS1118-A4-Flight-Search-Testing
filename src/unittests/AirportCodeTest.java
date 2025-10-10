package unittests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
	void acceptValidAirportCodes() {

		// Test with valid departure and destination airport codes
		// each valid code is tested in combination with another valid code
		// in either departure or destination
		// not all combinations are tested - only to ensure valid codes are accepted
		// valid codes: syd, mel, lax, cdg, del, pvg, doh

		departureAirportCode = "syd"; 
		destinationAirportCode = "mel";
		assertTrue(searchAirports());

		departureAirportCode = "lax"; 
		destinationAirportCode = "cdg"; 
		assertTrue(searchAirports());

		departureAirportCode = "del"; 
		destinationAirportCode = "pvg";
		assertTrue(searchAirports());

		departureAirportCode = "doh";
		destinationAirportCode = "syd"; 
		assertTrue(searchAirports());

	}

	@Test
	public void rejectSameDepartureAndDestinationAirportCode() {
		// Test with the same departure and destination airport codes
		departureAirportCode = "mel";
		destinationAirportCode = "mel";
		assertFalse(searchAirports());

		// test with a different valid code
		// to ensure the function departure and destination variables are set
		// correctly.
		departureAirportCode = "lax"; // Valid code
		destinationAirportCode = "mel"; // Same as departure code
		assertNotEquals(fs.getDepartureAirportCode(), fs.getDestinationAirportCode());

	}

	@Test
	void rejectInvalidDepartureAirportCode() {
		// Test with an invalid departure airport code
		departureAirportCode = "abc"; // Invalid code
		destinationAirportCode = "mel"; // Valid code
		assertFalse(searchAirports());
	}

	@Test
	void rejectInvalidDestinationAirportCode() {
		// Test with an invalid destination airport code
		departureAirportCode = "syd"; // Valid code
		destinationAirportCode = "xyz"; // Invalid code
		assertFalse(searchAirports());
	}

}
