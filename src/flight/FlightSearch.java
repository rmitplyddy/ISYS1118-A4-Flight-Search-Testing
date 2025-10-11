package flight;


import java.time.LocalDate;
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
   private static int MAX_PASSENGERS = 9;
   private static int MIN_PASSENGERS = 1;
   private static int MAX_CHILDREN_PER_ADULT = 2;
   private static int MAX_INFANTS_PER_ADULT = 1;


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

//


   // conditions 1: total passengers between 1 and 9

   public void validateTotalPassengers(int adultPassengerCount, 
                     int childPassengerCount, int infantPassengerCount) 
                     throws IllegalArgumentException {
      int totalPassengers = adultPassengerCount + childPassengerCount + infantPassengerCount;

      if (totalPassengers < MIN_PASSENGERS || 
                                    totalPassengers > MAX_PASSENGERS) {
         throw new IllegalArgumentException("Total passengers must be between 1 and 9");
      }
   }




   public boolean runFlightSearch(String departureDate, String departureAirportCode, 
		   				boolean emergencyRowSeating, String returnDate, String destinationAirportCode, 
		   				String seatingClass, int adultPassengerCount, int childPassengerCount, 
		   				int infantPassengerCount) {
      
      boolean valid = true;


      // batch process errors
      List<String> errors = new ArrayList<>();
      
      
      // --- total passenger validation --- //

      // condition 1: total passengers between 1 and 9

      int totalPassengers = adultPassengerCount + childPassengerCount + infantPassengerCount;

      if (totalPassengers < MIN_PASSENGERS || totalPassengers > MAX_PASSENGERS) {
         errors.add("Total passengers must be between 1 and 9");
      } else {
    	 System.out.print("\nset var\n");
         this.adultPassengerCount = adultPassengerCount;
         this.childPassengerCount = childPassengerCount;
         this.infantPassengerCount = infantPassengerCount;
      }

      
      // --- children validation --- //
      
      
      //	Condition 2. Children cannot be seated in emergency row seating or first class. (inclusive of infants - first class?)
      if (childPassengerCount > 0 && (emergencyRowSeating || 
                     seatingClass.equalsIgnoreCase("first"))) {
         errors.add("Children cannot be seated in emergency row seating or first class");
      }

      //	Condition 3. Infants cannot be seated in emergency row seating or business class.
      if (infantPassengerCount > 0 && (emergencyRowSeating || 
                     seatingClass.equalsIgnoreCase("business"))) {
         errors.add("Infants cannot be seated in emergency row seating or business class");
      }

      //	Condition 4. All children (aged 2-11 years old) must be seated immediately next to at least one adult passenger 
      //	child passengers <= 2 * adult passengers
      if (childPassengerCount > adultPassengerCount * MAX_CHILDREN_PER_ADULT) {
         errors.add("All children must be seated next to an adult");
      }

      //	Condition 5. Each infant (<2 years old) must be seated on an accompanying adults lap (only one infant is allowed per adult)
      if (infantPassengerCount > adultPassengerCount * MAX_INFANTS_PER_ADULT) {
         errors.add("Each infant must be seated on an accompanying adult's lap");
      }
      

      //	Condition 7. All dates must be formatted in the format DD/MM/YYYY eg "23/11/2025" 
      //	and must be validated to ensure that the combination is correct (eg "29/02/2026" 
      //	would be invalid as 2026 is not a leap year). Ensure that STRICT date validation is applied.
      
      
      // -- Date validation -- //
     
     LocalDate depDate = utility.InputHandler.parseDate(departureDate);
     LocalDate retDate = utility.InputHandler.parseDate(returnDate);

     // check for condition 7 - valid date format first. return 
     if (depDate == null) {
        errors.add("Invalid departure date format");
     } else {
        //	Condition 6. The departure date cannot be in the past (this is based on the current date when runFlightSearch method is called).
        if (depDate.isBefore(LocalDate.now())) {
           errors.add("Departure date cannot be in the past");
        } else {
           this.departureDate = departureDate;
        }
     }

     if (retDate == null) {
        errors.add("Invalid return date format");
     } else {
        //	Condition 8. All flights are two way only (ie include return flights) and the return date cannot be before departure date.
        if (depDate != null && retDate.isBefore(depDate)) {
           errors.add("Return date cannot be before departure date");
        } else {
           this.returnDate = returnDate;
        }
     }


      // --- seating class validation --- //
      //	Condition 9. The seating class must be one of ("economy', "
      //	"premium economy", "business", "first").

      ArrayList<String> validSeatingClasses = new ArrayList<>();
      validSeatingClasses.add("economy");
      validSeatingClasses.add("premium economy");
      validSeatingClasses.add("business");
      validSeatingClasses.add("first");

      if (validSeatingClasses.contains(seatingClass.toLowerCase())) {
         this.seatingClass = seatingClass.toLowerCase();
         // this.emergencyRowSeating = emergencyRowSeating;
      } else {
         errors.add("Invalid seating class");
      }

      // --- emergency row seating validation --- //
      //	Condition 10. Only economy class flights have emergency row seating.
      if (this.seatingClass.equals("economy")) {
         this.emergencyRowSeating = emergencyRowSeating;
      } else {
         if (emergencyRowSeating) {
            errors.add("Only economy class flights have emergency row seating");
         } else {
            this.emergencyRowSeating = false; // ensure it's false if not economy
         }
      }

      // condition 11. Only the following airports are available: "syd" (Sydney), "mel" (Melbourne),

      ArrayList<String> validAirportCodes = new ArrayList<>();
      validAirportCodes.add("syd");
      validAirportCodes.add("mel");
      validAirportCodes.add("lax");
      validAirportCodes.add("cdg");
      validAirportCodes.add("del");
      validAirportCodes.add("pvg");
      validAirportCodes.add("doh");

      if (validAirportCodes.contains(departureAirportCode.toLowerCase())) {
         this.departureAirportCode = departureAirportCode.toLowerCase();
      } else {
         errors.add("Invalid departure airport code");
      }

      if (validAirportCodes.contains(destinationAirportCode.toLowerCase())) {
         this.destinationAirportCode = destinationAirportCode.toLowerCase();
      } else {
         errors.add("Invalid destination airport code");
      }

      if (this.departureAirportCode.equals(this.destinationAirportCode)) {
         errors.add("Departure and destination airport cannot be the same");
         // reset both to null
         this.departureAirportCode = null;
         this.destinationAirportCode = null;
      }
      // --- airport code validation --- //
      //	Condition 11. Only the following airports are available: "syd" (Sydney), "mel" (Melbourne),
      //	"lax" (Los Angeles), "cdg" (Paris), "del" (Delhi), "pvg" (Shanghai) and "doh" (Doha). 
      //	eg for a flight from Melbourne to Shanghai, the departure airport code would be "mel" and the destination airport code would be "pvg". 
      //	Furthermore, the departure airport and destination airport cannot be the same.






      //TODO: Validate all the provided parameters.
      //if the search parameters meets the given conditions, 
      //   the function should initialise all the class attributes and return true.
      //else 
      //   the function should return false

      // 
      if (!errors.isEmpty()) {
         valid = false;
         for (String error : errors) {
            System.out.println("Error: " + error);
         }
      }

      return valid;
   }

   public int getAdultPassengerCount() {
       return adultPassengerCount;
   }

   public int getChildPassengerCount() {
       return childPassengerCount;
   }

   public int getInfantPassengerCount() {
       return infantPassengerCount;
   }

   public String getDepartureDate() {
       return departureDate;
   }

   public String getReturnDate() {
       return returnDate;
   }

   public String getDepartureAirportCode() {
       return departureAirportCode;
   }

   public String getDestinationAirportCode() {
       return destinationAirportCode;
   }

   public String getSeatingClass() {
       return seatingClass;
   }

   public boolean isEmergencyRowSeating() {
       return emergencyRowSeating;
   }

   // public List<String> getErrors() {
   //     return new ArrayList<>(); // Return a copy of the errors list
   // }

   

}