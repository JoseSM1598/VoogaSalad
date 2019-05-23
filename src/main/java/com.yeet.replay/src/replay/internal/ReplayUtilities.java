package replay.internal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** Utility class for functionality related to the recording system
 *  @author bpx
 */
public class ReplayUtilities {

    private ReplayUtilities(){}

    public static String getCurrentTimeUsingCalendar() {
        String date = getDate();
        String time = getTime();
        String formattedDate=date.replace('/','.');
        String formattedTime = time.replace(':','_');
        return formattedDate+"@"+formattedTime;
    }

    public static String getDate(){
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat dateFormatter = DateFormat.getDateInstance(DateFormat.SHORT);
        return dateFormatter.format(date);
    }

    public static String getTime(){
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        return timeFormatter.format(date);
    }
}
