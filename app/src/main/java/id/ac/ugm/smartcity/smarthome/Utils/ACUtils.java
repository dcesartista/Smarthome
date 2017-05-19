package id.ac.ugm.smartcity.smarthome.Utils;

import android.widget.TextView;

import id.ac.ugm.smartcity.smarthome.Model.Relay;
import id.ac.ugm.smartcity.smarthome.R;

/**
 * Created by dito on 18/05/17.
 */

public class ACUtils {
    public static String setTemperature(int temp){
        switch (temp){
            case 16:
                return Relay.AC_TEMP_16;
            case 17:
                return Relay.AC_TEMP_17;
            case 18:
                return Relay.AC_TEMP_18;
            case 19:
                return Relay.AC_TEMP_19;
            case 20:
                return Relay.AC_TEMP_20;
            case 21:
                return Relay.AC_TEMP_21;
            case 22:
                return Relay.AC_TEMP_22;
            case 23:
                return Relay.AC_TEMP_23;
            case 24:
                return Relay.AC_TEMP_24;
            case 25:
                return Relay.AC_TEMP_26;
            case 26:
                return Relay.AC_TEMP_26;
            case 27:
                return Relay.AC_TEMP_27;
            case 28:
                return Relay.AC_TEMP_28;
            case 29:
                return Relay.AC_TEMP_29;
            case 30:
                return Relay.AC_TEMP_30;
            default:
                return "0000";
        }
    }

    public static int getTemperature(String code){
        switch (code){
            case Relay.AC_TEMP_16:
                return 16;
            case Relay.AC_TEMP_17:
                return 17;
            case Relay.AC_TEMP_18:
                return 18;
            case Relay.AC_TEMP_19:
                return 19;
            case Relay.AC_TEMP_20:
                return 20;
            case Relay.AC_TEMP_21:
                return 21;
            case Relay.AC_TEMP_22:
                return 22;
            case Relay.AC_TEMP_23:
                return 23;
            case Relay.AC_TEMP_24:
                return 24;
            case Relay.AC_TEMP_25:
                return 25;
            case Relay.AC_TEMP_26:
                return 26;
            case Relay.AC_TEMP_27:
                return 27;
            case Relay.AC_TEMP_28:
                return 28;
            case Relay.AC_TEMP_29:
                return 29;
            case Relay.AC_TEMP_30:
                return 30;
            default:
                return 0;
        }
    }

    public static int getAcBrandPosition(String code){
        switch (code){
            case Relay.PANASONIC:
                return 0;
            case Relay.SAMSUNG:
                return 1;
            case Relay.DAIKIN:
                return 2;
            case Relay.LG:
                return 3;
            case Relay.SHARP:
                return 4;
            case Relay.TOSHIBA:
                return 5;
            default:
                return 0;
        }
    }

}
