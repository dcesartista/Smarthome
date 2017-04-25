package id.ac.ugm.smartcity.smarthome.Utils;

import java.text.DecimalFormat;

/**
 * Created by dito on 25/04/17.
 */

public class NumberFormatter {
    public static String formatWithDots(Double number){
        DecimalFormat df = new DecimalFormat("#,###,###.##");
        String n = df.format(number);
        return n.replaceAll("\\,","%c%").replaceAll("\\.","%d%")
                .replaceAll("%c%",".").replaceAll("%d%",",");
    }

    public static String formatWithDots(Integer number){
        DecimalFormat df = new DecimalFormat("#,###,###.##");
        String n = df.format(number);
        return n.replaceAll("\\,","%c%").replaceAll("\\.","%d%")
                .replaceAll("%c%",".").replaceAll("%d%",",");
    }

    public static String formatWithDots(Float number){
        DecimalFormat df = new DecimalFormat("#,###,###.##");
        String n = df.format(number);
        return n.replaceAll("\\,","%c%").replaceAll("\\.","%d%")
                .replaceAll("%c%",".").replaceAll("%d%",",");
    }
}
