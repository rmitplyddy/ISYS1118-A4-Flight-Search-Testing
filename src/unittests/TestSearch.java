package unittests;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class TestSearch {

//	Condition 1. The total number of passenger per search must be at least 1 and cannot exceed 9.
//
//	Condition 2. Children cannot be seated in emergency row seating or first class.
//
//	Condition 3. Infants cannot be seated in emergency row seating or business class.
//
//	Condition 4. All children (aged 2-11 years old) must be seated immediately next to at least one adult passenger 
//	(ie up to 2 children per adult). eg if the adult passenger count is 2, then up to 4 child passengers are allowed.
//
//	Condition 5. Each infant (<2 years old) must be seated on an accompanying adults lap (only one infant is allowed per adult)
//
//	Condition 6. The departure date cannot be in the past (this is based on the current date when runFlightSearch method is called).
//
//	Condition 7. All dates must be formatted in the format DD/MM/YYYY eg "23/11/2025" 
//	and must be validated to ensure that the combination is correct (eg "29/02/2026" 
//	would be invalid as 2026 is not a leap year). Ensure that STRICT date validation is applied.
//
//	Condition 8. All flights are two way only (ie include return flights) and the return date cannot be before departure date.
//
//	Condition 9. The seating class must be one of ("economy', "premium economy", "business", "first").
//
//	Condition 10. Only economy class flights have emergency row seating.
//
//	Condition 11. Only the following airports are available: "syd" (Sydney), "mel" (Melbourne), 
//	"lax" (Los Angeles), "cdg" (Paris), "del" (Delhi), "pvg" (Shanghai) and "doh" (Doha). eg for a flight from Melbourne to Shanghai, 
//	the departure airport code would be "mel" and the destination airport code would be "pvg". 
//	Furthermore, the departure airport and destination airport cannot be the same.
	
	
	
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
