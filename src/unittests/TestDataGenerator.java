package unittests;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestDataGenerator {
	
	
	
	
	public static String generateValidDeptDate() {
		// return a string value of the expected valid departure date
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate depDate = LocalDate.now().plusDays(1);
		return df.format(depDate);
	}

	public static String generateValidReturnDate() {
		// return a string value of the expected valid return date
		DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		LocalDate retDate = LocalDate.now().plusDays(7);
		return df.format(retDate);
	}

}
