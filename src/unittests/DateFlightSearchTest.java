package unittests;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import flight.FlightSearch;


//	Condition 6. The departure date cannot be in the past (this is based on the current date when runFlightSearch method is called).

//	Condition 7. All dates must be formatted in the format DD/MM/YYYY eg "23/11/2025" 
//	and must be validated to ensure that the combination is correct (eg "29/02/2026" 
//	would be invalid as 2026 is not a leap year). Ensure that STRICT date validation is applied.

public class DateFlightSearchTest {

    private FlightSearch fs;
	private String  departureDate;
   	private String  departureAirportCode = "syd";
   	private boolean emergencyRowSeating = false;
   	private String  returnDate;
   	private String  destinationAirportCode = "mel"; 
   	private String  seatingClass = "economy";
   	private int adultPassengerCount;
	private int childPassengerCount;
	private int infantPassengerCount;


    @BeforeEach
	void setUp() throws Exception {
		fs = new FlightSearch();
	}

    public boolean searchDates(String depDate, String retDate) {
        // Helper method to run flight search with given passenger counts
        return fs.runFlightSearch(depDate, departureAirportCode, 
        emergencyRowSeating, retDate, destinationAirportCode, seatingClass, 
        adultPassengerCount, childPassengerCount, infantPassengerCount);
    }

    @Test
    public void testRejectPastDepartureDate() {

//        departureDate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Set a past date
//        returnDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Set a future return date
//        // assertFalse(rejectPastDepartureDate());
//        searchDates(departureDate, returnDate);
    }

    // test invalid dates - YYYY/MM/DD, MM/DD/YYYY, YYYYMMDD, YYYYDDMM, etc
    // ensure strict date validation is applied
    @Test
    public void testRejectInvalidDateFormat() {
//        departureDate = "invalid-date-format";
//        returnDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Set a future return date
//        searchDates(departureDate, returnDate);
    }
    
}
