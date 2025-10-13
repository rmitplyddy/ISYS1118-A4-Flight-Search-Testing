package utility;

public class DateTestUtils {


    // Utility method to find the next leap year after a given year
	
	public static int getNextLeapYear(int year) {
        int nextYear = year + 1;
        while (!isLeapYear(nextYear)) {
            nextYear++;
        }
        return nextYear;
    }


    public static Boolean isLeapYear(int year) {
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
