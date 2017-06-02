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

    public static Date convertServerDateFormat(String input) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        Date dateTime = formatter.parse(input);

        return dateTime;

    }

    public static String convertDateToStringDate(String input) throws ParseException {
        String date = "";
        DateFormat formatter = new SimpleDateFormat("d/MM/yyyy");
        date = formatter.format(convertServerDateFormat(input));
        return date;

    }

    public static String convertDateToStringTime(String input) throws ParseException {
        String date = "";
        DateFormat formatter = new SimpleDateFormat("HH:mm");
        date = formatter.format(convertServerDateFormat(input));
        return date;

    }
}
