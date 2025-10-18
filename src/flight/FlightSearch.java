package flight;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.ArrayList;
import java.util.List;

public class FlightSearch {

   private String departureDate;
   private String departureAirportCode;
   private boolean emergencyRowSeating;
   private String returnDate;
   private String destinationAirportCode; 
   private String seatingClass;
   private int adultPassengerCount;
   private int childPassengerCount;
   private int infantPassengerCount;
   private static final int MAX_PASSENGERS = 9;
   private static final int MIN_PASSENGERS = 1;
   private static final int MAX_CHILDREN_PER_ADULT = 2;
   private static final int SENTINAL_VALUE = -999;


   public FlightSearch() {
      // initialise to int attributes to a sentinal value
      // this will indicate an uninitialised state
      // java default for int is 0 - which is a valid passenger count
      this.adultPassengerCount = SENTINAL_VALUE;
      this.childPassengerCount = SENTINAL_VALUE;
      this.infantPassengerCount = SENTINAL_VALUE;
   }
   
   
   public boolean runFlightSearch(String departureDate, String departureAirportCode, 
		   				boolean emergencyRowSeating, String returnDate, String destinationAirportCode, 
		   				String seatingClass, int adultPassengerCount, int childPassengerCount, 
		   				int infantPassengerCount) {



      boolean valid = true;

      // error list to log all validation errors
      // allows for batch processing of all validation rules

      List<String> errors = new ArrayList<>();
      
      // validation processing based on 11 conditions

      // 1. total passenger validation
      validatePassengerCounts(adultPassengerCount, childPassengerCount, infantPassengerCount, errors);

      // 2. children seating validation
      validateChildrenSeating(childPassengerCount, emergencyRowSeating, seatingClass, errors);

      // 3. infant seating validation
      validateInfantSeating(infantPassengerCount, emergencyRowSeating, seatingClass, errors);

      // 4. children to adult ratio validation
      validateChildrenToAdultRatio(childPassengerCount, adultPassengerCount, errors);

      // 5. infant to adult ratio validation
      validateInfantToAdultRatio(infantPassengerCount, adultPassengerCount, errors);

      // 6, 7, 8. departure date, return date validation
      LocalDate depDate = parseDateString(departureDate, errors);
      if (depDate != null) {
         validateDepartureDate(depDate, errors);
      }
      LocalDate retDate = parseDateString(returnDate, errors);
      if (retDate != null && depDate != null) {
         validateReturnDate(depDate, retDate, errors);
      }

      // 9. seating class validation
      validateEmergencyRowSeating(emergencyRowSeating, seatingClass, errors);
      
      // 10. airport code validation
      validateAirportCode(departureAirportCode, destinationAirportCode, errors);

      // 11. seating class validation
      validateSeatingClass(seatingClass, errors);

      // if no errors then all validations passed
      if (errors.isEmpty()) {
         // set all attributes
         this.departureDate = departureDate;
         this.departureAirportCode = departureAirportCode;
         this.emergencyRowSeating = emergencyRowSeating;
         this.returnDate = returnDate;
         this.destinationAirportCode = destinationAirportCode;
         this.seatingClass = seatingClass;
         this.adultPassengerCount = adultPassengerCount;
         this.childPassengerCount = childPassengerCount;
         this.infantPassengerCount = infantPassengerCount;
      } 
      else {
         valid = false;
      }
      return valid;
   }

   // conditions 1: total passengers between 1 and 9

   public void validatePassengerCounts(int adultPassengerCount, 
                     int childPassengerCount, int infantPassengerCount, List<String> errors) {
      
      if (adultPassengerCount < 0 || childPassengerCount < 0 ||  
                                          infantPassengerCount < 0) {
         errors.add("Passenger counts cannot be negative");
      }
      else {
         int totalPassengers = adultPassengerCount + childPassengerCount + infantPassengerCount;

         if (totalPassengers < MIN_PASSENGERS || 
                                    totalPassengers > MAX_PASSENGERS) {
            errors.add("Total passengers must be between 1 and 9");
         }
      }
   }


   //	Condition 2. Children cannot be seated in emergency row seating or first class. (inclusive of infants - first class?)

   public void validateChildrenSeating(int childPassengerCount, 
                     boolean emergencyRowSeating, String seatingClass, List<String> errors) {
      if (childPassengerCount > 0 && (emergencyRowSeating || 
                     seatingClass.equalsIgnoreCase("first"))) {
         errors.add("Children cannot be seated in emergency row seating or first class");
      }
   }


   // condition 3. Infants cannot be seated in emergency row seating or business class.

   public void validateInfantSeating(int infantPassengerCount, 
                     boolean emergencyRowSeating, String seatingClass, List<String> errors) {
      if (infantPassengerCount > 0 && (emergencyRowSeating || 
                     seatingClass.equalsIgnoreCase("business"))) {
         errors.add("Infants cannot be seated in emergency row seating or business class");
      }
   }


   // condition 4. All children (aged 2-11 years old) must be seated immediately next to at least one adult passenger 

   public void validateChildrenToAdultRatio(int childPassengerCount, 
                     int adultPassengerCount, List<String> errors) {
      if (childPassengerCount > adultPassengerCount * MAX_CHILDREN_PER_ADULT) {
         errors.add("All children must be seated next to an adult");
      }
   }


   // condition 5. Each infant (<2 years old) must be seated on an accompanying adults lap (only one infant is allowed per adult)

   public void validateInfantToAdultRatio(int infantPassengerCount, 
                     int adultPassengerCount, List<String> errors) {
      if (infantPassengerCount > adultPassengerCount) {
         errors.add("Each infant must be seated on an accompanying adult's lap");
      }
   }

   // condition 6. The departure date cannot be in the past (this is based on the current date when runFlightSearch method is called).

   public void validateDepartureDate(LocalDate departureDate, List<String> errors) {
      if (departureDate.isBefore(LocalDate.now())) {
         errors.add("Departure date cannot be in the past");
      }
   }
   
   // condition 7. All dates must be formatted in the format DD/MM/YYYY eg "23/11/2025"

   

   public LocalDate parseDateString(String dateStr, List<String> errors) {

      // parse date string and validate format
      // return null if invalid format
      
      LocalDate validDate = null;
      try {
         // Parse the date string with strict resolver style
         // this method requires uuuu for the year for strict resolver style
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                  .withResolverStyle(ResolverStyle.STRICT);
         validDate = LocalDate.parse(dateStr, formatter);
         
      } catch (Exception e) {
         // If parsing fails, return null
         errors.add("Invalid date format: " + dateStr + ". Expected format is DD/MM/YYYY");
         validDate = null;
      }
      return validDate;
   }



   // condition 8. All flights are two way only (ie include return flights) and the return date cannot be before departure date.

   public void validateReturnDate(LocalDate departureDate, 
                     LocalDate returnDate, List<String> errors) {
      if (returnDate.isBefore(departureDate)) {
         errors.add("Return date cannot be before departure date");
      }
   }

   // condition 9. The seating class must be one of ("economy', "premium economy", "business", "first").

   public void validateSeatingClass(String seatingClass, List<String> errors) {
      ArrayList<String> validSeatingClasses = new ArrayList<>();
      validSeatingClasses.add("economy");
      validSeatingClasses.add("premium economy");
      validSeatingClasses.add("business");
      validSeatingClasses.add("first");

      if (!validSeatingClasses.contains(seatingClass.toLowerCase())) {
         errors.add("Invalid seating class");
      }
   }



   // condition 10. Only economy class flights have emergency row seating.

   public void validateEmergencyRowSeating(boolean emergencyRowSeating, 
                     String seatingClass, List<String> errors) {
      if (!seatingClass.equals("economy") && emergencyRowSeating) {
         errors.add("Only economy class flights have emergency row seating");
      }
   }


   // condition 11. Only the following airports are available: "syd" (Sydney), "mel" (Melbourne),

   public void validateAirportCode(String departureAirportCode, String arrivalAirportCode, List<String> errors) {

      ArrayList<String> validAirportCodes = new ArrayList<>();
      validAirportCodes.add("syd");
      validAirportCodes.add("mel");
      validAirportCodes.add("lax");
      validAirportCodes.add("cdg");
      validAirportCodes.add("del");
      validAirportCodes.add("pvg");
      validAirportCodes.add("doh");

      // check both airport codes are valid
      if (!(validAirportCodes.contains(departureAirportCode.toLowerCase()) && 
          validAirportCodes.contains(arrivalAirportCode.toLowerCase()))) {
         errors.add("Invalid airport code: " + departureAirportCode);
      } // check both airport codes are not the same
      else if (departureAirportCode.equalsIgnoreCase(arrivalAirportCode)) {
         errors.add("Departure and destination airport cannot be the same");
      }
   }



   public static int getSentinalValue() {
	   return SENTINAL_VALUE;
   }



   public int getAdultPassengerCount() {
       return this.adultPassengerCount;
   }

   public int getChildPassengerCount() {
       return this.childPassengerCount;
   }

   public int getInfantPassengerCount() {
       return infantPassengerCount;
   }

   public String getDepartureDate() {
       return this.departureDate;
   }

   public String getReturnDate() {
       return this.returnDate;
   }

   public String getDepartureAirportCode() {
       return this.departureAirportCode;
   }

   public String getDestinationAirportCode() {
       return this.destinationAirportCode;
   }

   public String getSeatingClass() {
       return this.seatingClass;
   }

   public boolean isEmergencyRowSeating() {
       return this.emergencyRowSeating;
   }
   

}