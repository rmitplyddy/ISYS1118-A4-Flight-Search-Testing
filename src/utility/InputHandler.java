package utility;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public class InputHandler {


    public static LocalDate parseDate(String dateStr) {

        try {
            // Parse the date string with strict resolver style
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu")
                    .withResolverStyle(ResolverStyle.STRICT);
            return LocalDate.parse(dateStr, formatter);
        } catch (Exception e) {
            // If parsing fails, return null
            return null;
        }
    }

}
