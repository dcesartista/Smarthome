package id.ac.ugm.smartcity.smarthome.Utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dito on 09/03/17.
 */

public class DateFormatter {
    public static Date formatDate(String date, String pattern) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date dateTime;
        try {
            dateTime = format.parse(date);
        } catch (Exception e){
            dateTime = null;
        }


        return dateTime;
    }

    public static String formatDateToString(Date date, String pattern){
        DateFormat formatter = new SimpleDateFormat(pattern);

        String time = formatter.format(date);
        return time;
    }
}
