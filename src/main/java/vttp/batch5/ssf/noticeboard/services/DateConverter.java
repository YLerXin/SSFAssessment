package vttp.batch5.ssf.noticeboard.services;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter {

    // This method converts a date in string format to a Date object.
    public static Date strToDate(String dateString) {
        // Create a formatter that defines the expected date format
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MM/dd/yyyy");

        try {
            // Try to parse the string into a Date object
            Date date = formatter.parse(dateString);
            return date; // Return the Date object if successful
        } catch (ParseException e) {
            // If there's an error in parsing, print the error message
            e.printStackTrace();
            return null; // Return null if parsing fails
        }
    }

    // This method converts a Date object into epoch milliseconds (long value)
    public static long dateToEpochMilliseconds(Date date) {
        // Get the time in milliseconds since January 1, 1970
        return date.getTime();
    }

    // This method converts epoch milliseconds back into a Date object
    public static Date epochMillisecondsToDate(long epoch) {
        // Create a new Date object using the epoch milliseconds
        return new Date(epoch);
    }

    // This method converts a Date object back into a string format
    public static String dateToString(Date date) {
        // Create a formatter for converting Date to string
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, MM/dd/yyyy");
        // Format the Date object into a string and return it
        return formatter.format(date);
    }
}