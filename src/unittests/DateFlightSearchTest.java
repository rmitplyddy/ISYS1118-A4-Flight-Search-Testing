package unittests;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void rejectPastDepartureDate() {
    	// check that the departure date is not in the past
        String testDepDate = LocalDate.now().minusDays(1).format
                (DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Set a 
                                                                // past date
        String testRetDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Set a future 

        assertFalse(searchDates(testDepDate, testRetDate));
        assertNull(fs.getDepartureDate());
        assertNull(fs.getReturnDate());
    }


    @Test
    public void acceptValidDepartureDate() {
    	// valid departure date is at least today
        String testDepDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Set to today's date
        String testRetDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Set to a week from today
        assertTrue(searchDates(testDepDate, testRetDate));
        assertEquals(testDepDate, fs.getDepartureDate());
        assertEquals(testRetDate, fs.getReturnDate());
    }


    @Test
    public void rejectLeapYearDate() {
        // test of strict date validation for leap year dates
        int year = Integer.valueOf(LocalDate.now().getYear());
        // if current year is a leap year, test with next year
        String testDepDate = "29/02/" + year;
        String testRetDate = "06/03/" + year;
        assertFalse(searchDates(testDepDate, testRetDate));
        assertNull(fs.getDepartureDate());
        assertNull(fs.getReturnDate());
    }

    @Test
    public void acceptValidLeapYearDate() {
        // test of strict date validation for leap year dates
        // valid 29/02 must be within the next leap year
        int year = Integer.valueOf(LocalDate.now().getYear());
        year = getNextLeapYear(year);

        String testDepDate = "29/02/" + year;
        String testRetDate = "06/03/" + year;
        assertTrue(searchDates(testDepDate, testRetDate));
        assertEquals(testDepDate, fs.getDepartureDate());
        assertEquals(testRetDate, fs.getReturnDate());
    }

    @Test
    public void rejectInvalidDayMonthDate() {
        // test of strict date validation for invalid day/month combinations
        String testDepDate = "31/04/" + LocalDate.now().getYear();
        String testRetDate = "06/05/" + LocalDate.now().getYear();
        assertFalse(searchDates(testDepDate, testRetDate));
        assertNull(fs.getDepartureDate());
        assertNull(fs.getReturnDate());
    }


    // ------ invalid date formats ------ //

    @Test
    public void rejectDateFormatYYYYMMDD() {
        LocalDate baseDepDate = LocalDate.now();
        LocalDate baseRetDate = baseDepDate.plusDays(7);
        // test YYYYMMDD format
        String testDepDate = baseDepDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")); 
        String testRetDate = baseRetDate.format(DateTimeFormatter.ofPattern("yyyyMMdd")); 
        searchDates(testDepDate, testRetDate);
        assertNull(fs.getDepartureDate());
        assertNull(fs.getReturnDate());
    }

    @Test
    public void rejectDateFormatMMDDYYYY() {
        LocalDate baseDepDate = LocalDate.now();
        LocalDate baseRetDate = baseDepDate.plusDays(7);
        // test MMDDYYYY format
        String testDepDate = baseDepDate.format(DateTimeFormatter.ofPattern("MMddyyyy")); 
        String testRetDate = baseRetDate.format(DateTimeFormatter.ofPattern("MMddyyyy")); 
        searchDates(testDepDate, testRetDate);
        assertNull(fs.getDepartureDate());
        assertNull(fs.getReturnDate());
    }

    @Test
    public void acceptValidDateFormatDDMMYYYY() {
        LocalDate baseDepDate = LocalDate.now().plusDays(1);
        LocalDate baseRetDate = baseDepDate.plusDays(7);
        // test valid DD/MM/YYYY format
        String testDepDate = baseDepDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); 
        String testRetDate = baseRetDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")); 
        searchDates(testDepDate, testRetDate);
        assertEquals(testDepDate, fs.getDepartureDate());
        assertEquals(testRetDate, fs.getReturnDate());
    }




    private int getNextLeapYear(int year) {
        int nextYear = year + 1;
        while (!isLeapYear(nextYear)) {
            nextYear++;
        }
        return nextYear;
    }


    private Boolean isLeapYear(int year) {
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
