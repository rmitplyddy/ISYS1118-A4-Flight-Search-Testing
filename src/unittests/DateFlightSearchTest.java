package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import flight.FlightSearch;

import java.time.format.DateTimeFormatter;


//	Condition 6. The departure date cannot be in the past (this is based on the current date when runFlightSearch method is called).

//	Condition 7. All dates must be formatted in the format DD/MM/YYYY eg "23/11/2025" 
//	and must be validated to ensure that the combination is correct (eg "29/02/2026" 
//	would be invalid as 2026 is not a leap year). Ensure that STRICT date validation is applied.

public class DateFlightSearchTest {

    private FlightSearch fs;
	// private String  departureDate;
   	private String  departureAirportCode = "syd";
   	private boolean emergencyRowSeating = false;
   	// private String  returnDate;
   	private String  destinationAirportCode = "mel"; 
   	private String  seatingClass = "economy";
   	private int adultPassengerCount = 1;
	private int childPassengerCount = 0;
	private int infantPassengerCount = 0;


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
    	// Test with a past departure date
        String testDepDate = LocalDate.now().minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Set a past date
        String testRetDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Set a future 

        // throw an exception for invalid date
        assertThrows(IllegalArgumentException.class, () -> searchDates(testDepDate, testRetDate));
    }

    // test invalid dates - YYYY/MM/DD, MM/DD/YYYY, YYYYMMDD, YYYYDDMM, etc
    // ensure strict date validation is applied
    @Test
    public void testRejectInvalidDateFormat() {
        LocalDate baseDepDate = LocalDate.now().plusDays(1);
        LocalDate baseRetDate = LocalDate.now().plusDays(7);
        // test various invalid date formats
        String testDepDate1 = baseDepDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")); 
        String testDepDate2 = baseDepDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        String testDepDate3 = baseDepDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")); 
        String testDepDate4 = baseDepDate.format(DateTimeFormatter.ofPattern("yyyyddMM")); 

        int year = Integer.valueOf(LocalDate.now().getYear());
        // if current year is a leap year, test with 2023 (not a leap year)
        String testDepDate5 = isLeapYear(year) ? "29/02/" + year : "29/02/" + year + 1;
        String testRetDate5 = isLeapYear(year) ? "06/03/" + year : "05/03/" + year;
        
        String testRetDate1 = baseRetDate.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        String testRetDate2 = baseRetDate.format(DateTimeFormatter.ofPattern("MM/dd/yyyy")); 
        String testRetDate3 = baseRetDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")); 
        String testRetDate4 = baseRetDate.format(DateTimeFormatter.ofPattern("yyyyddMM")); 

        // throw an exception for invalid date
        assertThrows(IllegalArgumentException.class, () -> searchDates(testDepDate1, testRetDate1));
        assertThrows(IllegalArgumentException.class, () -> searchDates(testDepDate2, testRetDate2));
        assertThrows(IllegalArgumentException.class, () -> searchDates(testDepDate3, testRetDate3));
        assertThrows(IllegalArgumentException.class, () -> searchDates(testDepDate4, testRetDate4));
        assertThrows(IllegalArgumentException.class, () -> searchDates(testDepDate5, testRetDate5));
        // assertThrows(IllegalArgumentException.class, () -> searchDates(testDepDate5, testRetDate5));
    

        searchDates(testDepDate1, testRetDate1);
        assertNull(fs.getDepartureDate());
        assertNull(fs.getReturnDate());

        searchDates(testDepDate2, testRetDate2);
        assertNull(fs.getDepartureDate());
        assertNull(fs.getReturnDate());

        searchDates(testDepDate3, testRetDate3);
        assertNull(fs.getDepartureDate());
        assertNull(fs.getReturnDate());

        searchDates(testDepDate4, testRetDate4);
        assertNull(fs.getDepartureDate());
        assertNull(fs.getReturnDate());

        searchDates(testDepDate5, testRetDate5);
        assertNull(fs.getDepartureDate());
        assertNull(fs.getReturnDate());
    }


    Boolean isLeapYear(int year) {
        // A year is a leap year if it is divisible by 4
        // but not divisible by 100, unless it is also divisible by 400
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                return year % 400 == 0;
            }
            return true;
        }
        return false;
    }
    
}
