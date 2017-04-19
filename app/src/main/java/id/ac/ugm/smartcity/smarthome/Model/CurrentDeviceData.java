package id.ac.ugm.smartcity.smarthome.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dito on 18/04/17.
 */

public class CurrentDeviceData {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("humidity")
    @Expose
    private Double humidity;
    @SerializedName("temperature")
    @Expose
    private Double temperature;
    @SerializedName("motion")
    @Expose
    private Double motion;
    @SerializedName("co2")
    @Expose
    private Double co2;
    @SerializedName("flux")
    @Expose
    private Double flux;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getMotion() {
        return motion;
    }

    public void setMotion(Double motion) {
        this.motion = motion;
    }

    public Double getCo2() {
        return co2;
    }

    public void setCo2(Double co2) {
        this.co2 = co2;
    }

    public Double getFlux() {
        return flux;
    }

    public void setFlux(Double flux) {
        this.flux = flux;
    }
}
