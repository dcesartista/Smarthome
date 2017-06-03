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

    public static String getAcBrandName(String code){
        switch (code){
            case Relay.PANASONIC:
                return "Panasonic";
            case Relay.SAMSUNG:
                return "Samsung";
            case Relay.DAIKIN:
                return "Daikin";
            case Relay.LG:
                return "LG";
            case Relay.SHARP:
                return "SHARP";
            case Relay.TOSHIBA:
                return "Toshiba";
            default:
                return "";
        }
    }

    public static String getAcMode(String code){
        switch (code){
            case Relay.MODE_AUTO:
                return "Mode Auto";
            case Relay.MODE_COLD:
                return "Mode Cold";
            case Relay.MODE_DRY:
                return "Mode Dry";
            case Relay.MODE_FAN:
                return "Mode Fan";
            case Relay.MODE_HOT:
                return "Mode Hot";
            default:
                return "";
        }
    }

    public static String getAcSpeed(String code){
        switch (code){
            case Relay.FAN_SPEED_AUTO:
                return "Speed Auto";
            case Relay.FAN_SPEED_HIGH:
                return "Speed High";
            case Relay.FAN_SPEED_MID_HIGH:
                return "Speed Mid High";
            case Relay.FAN_SPEED_MIDDLE:
                return "Speed Middle";
            case Relay.FAN_SPEED_MID_LOW:
                return "Speed Mid Low";
            case Relay.FAN_SPEED_LOW:
                return "Speed Low";
            default:
                return "";
        }
    }

    public static String getAcSwing(String code){
        switch (code){
            case Relay.SWING_AUTO:
                return "Auto";
            case Relay.SWING_VERTICAL:
                return "Vertical";
            case Relay.SWING_MID_VERTICAL:
                return "Mid Vertical";
            case Relay.SWING_MIDDLE:
                return "Middle";
            case Relay.SWING_MID_HORIZONTAL:
                return "Mid Horizontal";
            case Relay.SWING_HORIZONTAL:
                return "Horizontal";
            case Relay.SWING_FIXED:
                return "Fixed";
            default:
                return "";
        }
    }

    public static String changeMode(String brandCode, String currentMode){
        switch (brandCode){
            case Relay.PANASONIC:
                switch (currentMode){
                    case Relay.MODE_AUTO:
                        return Relay.MODE_DRY;
                    case Relay.MODE_DRY:
                        return Relay.MODE_COLD;
                    case Relay.MODE_COLD:
                        return Relay.MODE_AUTO;
                    default:
                        return "";
                }
            case Relay.LG:
                switch (currentMode){
                    case Relay.MODE_AUTO:
                        return Relay.MODE_DRY;
                    case Relay.MODE_DRY:
                        return Relay.MODE_COLD;
                    case Relay.MODE_COLD:
                        return Relay.MODE_HOT;
                    case Relay.MODE_HOT:
                        return Relay.MODE_AUTO;
                    default:
                        return "";
                }
            case Relay.TOSHIBA:
                switch (currentMode){
                    case Relay.MODE_AUTO:
                        return Relay.MODE_FAN;
                    case Relay.MODE_FAN:
                        return Relay.MODE_DRY;
                    case Relay.MODE_DRY:
                        return Relay.MODE_COLD;
                    case Relay.MODE_COLD:
                        return Relay.MODE_AUTO;
                    default:
                        return "";
                }
            default:
                switch (currentMode){
                    case Relay.MODE_AUTO:
                        return Relay.MODE_FAN;
                    case Relay.MODE_FAN:
                        return Relay.MODE_DRY;
                    case Relay.MODE_DRY:
                        return Relay.MODE_COLD;
                    case Relay.MODE_COLD:
                        return Relay.MODE_HOT;
                    case Relay.MODE_HOT:
                        return Relay.MODE_AUTO;
                    default:
                        return "";
                }
        }
    }

    public static String changeFanSpeed(String brandCode, String currentSpeed){
        switch (brandCode){
            case Relay.SAMSUNG:
                switch (currentSpeed){
                    case Relay.FAN_SPEED_AUTO:
                        return Relay.FAN_SPEED_HIGH;
                    case Relay.FAN_SPEED_HIGH:
                        return Relay.FAN_SPEED_MID_HIGH;
                    case Relay.FAN_SPEED_MID_HIGH:
                        return Relay.FAN_SPEED_MIDDLE;
                    case Relay.FAN_SPEED_MIDDLE:
                        return Relay.FAN_SPEED_MID_LOW;
                    case Relay.FAN_SPEED_MID_LOW:
                        return Relay.FAN_SPEED_LOW;
                    case Relay.FAN_SPEED_LOW:
                        return Relay.FAN_SPEED_AUTO;
                    default:
                        return "";
                }
            case Relay.SHARP:
                switch (currentSpeed){
                    case Relay.FAN_SPEED_AUTO:
                        return Relay.FAN_SPEED_HIGH;
                    case Relay.FAN_SPEED_HIGH:
                        return Relay.FAN_SPEED_MID_HIGH;
                    case Relay.FAN_SPEED_MID_HIGH:
                        return Relay.FAN_SPEED_MIDDLE;
                    case Relay.FAN_SPEED_MIDDLE:
                        return Relay.FAN_SPEED_MID_LOW;
                    case Relay.FAN_SPEED_MID_LOW:
                        return Relay.FAN_SPEED_LOW;
                    case Relay.FAN_SPEED_LOW:
                        return Relay.FAN_SPEED_AUTO;
                    default:
                        return "";
                }
            default:
                switch (currentSpeed){
                    case Relay.FAN_SPEED_AUTO:
                        return Relay.FAN_SPEED_HIGH;
                    case Relay.FAN_SPEED_HIGH:
                        return Relay.FAN_SPEED_MIDDLE;
                    case Relay.FAN_SPEED_MIDDLE:
                        return Relay.FAN_SPEED_LOW;
                    case Relay.FAN_SPEED_LOW:
                        return Relay.FAN_SPEED_AUTO;
                    default:
                        return "";
                }
        }
    }

    public static String changeSwing(String brandCode, String currentSwing){
        switch (brandCode){
            case Relay.PANASONIC:
                switch (currentSwing){
                    case Relay.SWING_AUTO:
                        return Relay.SWING_HORIZONTAL;
                    case Relay.SWING_HORIZONTAL:
                        return Relay.SWING_MID_HORIZONTAL;
                    case Relay.SWING_MID_HORIZONTAL:
                        return Relay.SWING_MIDDLE;
                    case Relay.SWING_MIDDLE:
                        return Relay.SWING_MID_VERTICAL;
                    case Relay.SWING_MID_VERTICAL:
                        return Relay.SWING_VERTICAL;
                    case Relay.SWING_VERTICAL:
                        return Relay.SWING_AUTO;
                    default:
                        return "";
                }
            case Relay.SHARP:
                switch (currentSwing){
                    case Relay.SWING_AUTO:
                        return Relay.SWING_HORIZONTAL;
                    case Relay.SWING_HORIZONTAL:
                        return Relay.SWING_MID_HORIZONTAL;
                    case Relay.SWING_MID_HORIZONTAL:
                        return Relay.SWING_MIDDLE;
                    case Relay.SWING_MIDDLE:
                        return Relay.SWING_MID_VERTICAL;
                    case Relay.SWING_MID_VERTICAL:
                        return Relay.SWING_VERTICAL;
                    case Relay.SWING_VERTICAL:
                        return Relay.SWING_AUTO;
                    default:
                        return "";
                }
            default:
                switch (currentSwing){
                    case Relay.SWING_AUTO:
                        return Relay.SWING_FIXED;
                    case Relay.SWING_FIXED:
                        return Relay.SWING_AUTO;
                    default:
                        return "";
                }
        }
    }
}
