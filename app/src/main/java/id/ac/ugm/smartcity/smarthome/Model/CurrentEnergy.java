package id.ac.ugm.smartcity.smarthome.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dito on 20/04/17.
 */

public class CurrentEnergy {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("pwr")
    @Expose
    private Double power;
    @SerializedName("cA")
    @Expose
    private Double current;
    @SerializedName("vA")
    @Expose
    private Double voltage;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("value")
    @Expose
    private double value;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPower() {
        return power;
    }

    public void setPower(Double power) {
        this.power = power;
    }

    public Double getCurrent() {
        return current;
    }

    public void setCurrent(Double current) {
        this.current = current;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }
}
