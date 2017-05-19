package id.ac.ugm.smartcity.smarthome.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dito on 18/05/17.
 */

public class Relay {
    public static final String RELAY_1 = "relay1";
    public static final String RELAY_2 = "relay2";
    public static final String RELAY_3 = "relay3";
    public static final String RELAY_4 = "relay4";
    public static final String RELAY_5 = "relay5";
    public static final String RELAY_6 = "relay6";
    public static final String RELAY_7 = "relay7";
    public static final String RELAY_8 = "relay8";
    public static final String AC_BRAND = "ac_brand";
    public static final String AC_MODE = "ac_mode";
    public static final String AC_POWER = "ac_power";
    public static final String AC_SPEED = "ac_speed";
    public static final String AC_SWING = "ac_swing";
    public static final String AC_ENABLE = "ac_enable";
    public static final String AC_TEMP = "ac_temp";

    public static final String PANASONIC = "0000";
    public static final String SAMSUNG = "0001";
    public static final String DAIKIN = "0010";
    public static final String LG = "0011";
    public static final String SHARP = "0100";
    public static final String TOSHIBA = "0101";

    public static final String AC_ON = "1";
    public static final String AC_OFF = "0";

    public static final String MODE_AUTO = "0000";
    public static final String MODE_FAN = "0001";
    public static final String MODE_DRY = "0010";
    public static final String MODE_COLD = "0011";
    public static final String MODE_HOT = "0100";

    public static final String AC_TEMP_16 = "0000";
    public static final String AC_TEMP_17 = "0001";
    public static final String AC_TEMP_18 = "0010";
    public static final String AC_TEMP_19 = "0011";
    public static final String AC_TEMP_20 = "0100";
    public static final String AC_TEMP_21 = "0101";
    public static final String AC_TEMP_22 = "0110";
    public static final String AC_TEMP_23 = "0111";
    public static final String AC_TEMP_24 = "1000";
    public static final String AC_TEMP_25 = "1001";
    public static final String AC_TEMP_26 = "1010";
    public static final String AC_TEMP_27 = "1011";
    public static final String AC_TEMP_28 = "1100";
    public static final String AC_TEMP_29 = "1101";
    public static final String AC_TEMP_30 = "1110";

    public static final String FAN_SPEED_AUTO = "0000";
    public static final String FAN_SPEED_HIGH = "0001";
    public static final String FAN_SPEED_MID_HIGH = "0010";
    public static final String FAN_SPEED_MIDDLE = "0011";
    public static final String FAN_SPEED_MID_LOW = "0100";
    public static final String FAN_SPEED_LOW = "0101";

    public static final String SWING_AUTO = "0000";
    public static final String SWING_HORIZONTAL = "0001";
    public static final String SWING_MID_HORIZONTAL = "0010";
    public static final String SWING_MIDDLE = "0011";
    public static final String SWING_MID_VERTICAL = "0100";
    public static final String SWING_VERTICAL = "0101";
    public static final String SWING_FIXED = "0110";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("relay1")
    @Expose
    private Integer relay1;
    @SerializedName("relay2")
    @Expose
    private Integer relay2;
    @SerializedName("relay3")
    @Expose
    private Integer relay3;
    @SerializedName("relay4")
    @Expose
    private Integer relay4;
    @SerializedName("relay5")
    @Expose
    private Integer relay5;
    @SerializedName("relay6")
    @Expose
    private Integer relay6;
    @SerializedName("relay7")
    @Expose
    private Integer relay7;
    @SerializedName("relay8")
    @Expose
    private Integer relay8;
    @SerializedName("relay1name")
    @Expose
    private Object relay1name;
    @SerializedName("relay2name")
    @Expose
    private Object relay2name;
    @SerializedName("relay3name")
    @Expose
    private Object relay3name;
    @SerializedName("relay4name")
    @Expose
    private Object relay4name;
    @SerializedName("relay5name")
    @Expose
    private Object relay5name;
    @SerializedName("relay6name")
    @Expose
    private Object relay6name;
    @SerializedName("relay7name")
    @Expose
    private Object relay7name;
    @SerializedName("relay8name")
    @Expose
    private Object relay8name;
    @SerializedName("ac_brand")
    @Expose
    private String acBrand;
    @SerializedName("ac_mode")
    @Expose
    private String acMode;
    @SerializedName("ac_power")
    @Expose
    private String acPower;
    @SerializedName("ac_speed")
    @Expose
    private String acSpeed;
    @SerializedName("ac_swing")
    @Expose
    private String acSwing;
    @SerializedName("ac_enable")
    @Expose
    private Object acEnable;
    @SerializedName("ac_temp")
    @Expose
    private String acTemp;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("device_id")
    @Expose
    private Integer deviceId;
    @SerializedName("ac_state")
    @Expose
    private String acState;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRelay1() {
        return relay1;
    }

    public void setRelay1(Integer relay1) {
        this.relay1 = relay1;
    }

    public Integer getRelay2() {
        return relay2;
    }

    public void setRelay2(Integer relay2) {
        this.relay2 = relay2;
    }

    public Integer getRelay3() {
        return relay3;
    }

    public void setRelay3(Integer relay3) {
        this.relay3 = relay3;
    }

    public Integer getRelay4() {
        return relay4;
    }

    public void setRelay4(Integer relay4) {
        this.relay4 = relay4;
    }

    public Integer getRelay5() {
        return relay5;
    }

    public void setRelay5(Integer relay5) {
        this.relay5 = relay5;
    }

    public Integer getRelay6() {
        return relay6;
    }

    public void setRelay6(Integer relay6) {
        this.relay6 = relay6;
    }

    public Integer getRelay7() {
        return relay7;
    }

    public void setRelay7(Integer relay7) {
        this.relay7 = relay7;
    }

    public Integer getRelay8() {
        return relay8;
    }

    public void setRelay8(Integer relay8) {
        this.relay8 = relay8;
    }

    public Object getRelay1name() {
        return relay1name;
    }

    public void setRelay1name(Object relay1name) {
        this.relay1name = relay1name;
    }

    public Object getRelay2name() {
        return relay2name;
    }

    public void setRelay2name(Object relay2name) {
        this.relay2name = relay2name;
    }

    public Object getRelay3name() {
        return relay3name;
    }

    public void setRelay3name(Object relay3name) {
        this.relay3name = relay3name;
    }

    public Object getRelay4name() {
        return relay4name;
    }

    public void setRelay4name(Object relay4name) {
        this.relay4name = relay4name;
    }

    public Object getRelay5name() {
        return relay5name;
    }

    public void setRelay5name(Object relay5name) {
        this.relay5name = relay5name;
    }

    public Object getRelay6name() {
        return relay6name;
    }

    public void setRelay6name(Object relay6name) {
        this.relay6name = relay6name;
    }

    public Object getRelay7name() {
        return relay7name;
    }

    public void setRelay7name(Object relay7name) {
        this.relay7name = relay7name;
    }

    public Object getRelay8name() {
        return relay8name;
    }

    public void setRelay8name(Object relay8name) {
        this.relay8name = relay8name;
    }

    public String getAcBrand() {
        return acBrand;
    }

    public void setAcBrand(String acBrand) {
        this.acBrand = acBrand;
    }

    public String getAcMode() {
        return acMode;
    }

    public void setAcMode(String acMode) {
        this.acMode = acMode;
    }

    public String getAcPower() {
        return acPower;
    }

    public void setAcPower(String acPower) {
        this.acPower = acPower;
    }

    public String getAcSpeed() {
        return acSpeed;
    }

    public void setAcSpeed(String acSpeed) {
        this.acSpeed = acSpeed;
    }

    public String getAcSwing() {
        return acSwing;
    }

    public void setAcSwing(String acSwing) {
        this.acSwing = acSwing;
    }

    public Object getAcEnable() {
        return acEnable;
    }

    public void setAcEnable(Object acEnable) {
        this.acEnable = acEnable;
    }

    public String getAcTemp() {
        return acTemp;
    }

    public void setAcTemp(String acTemp) {
        this.acTemp = acTemp;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getAcState() {
        return acState;
    }

    public void setAcState(String acState) {
        this.acState = acState;
    }
}
