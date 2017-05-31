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

    public static String convertServerDate(String input) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String date = "";
        try {
            Date dateTime = formatter.parse(input);
            DateFormat formatter2 = new SimpleDateFormat("d/MM/yyyy");
            date = formatter2.format(dateTime);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return date;

    }
}
