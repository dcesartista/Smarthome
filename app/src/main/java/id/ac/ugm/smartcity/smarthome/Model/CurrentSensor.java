package id.ac.ugm.smartcity.smarthome.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dito on 31/05/17.
 */

public class CurrentSensor {
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
    private Integer temperature;
    @SerializedName("motion")
    @Expose
    private Integer motion;
    @SerializedName("co2")
    @Expose
    private Integer co2;
    @SerializedName("flux")
    @Expose
    private Integer flux;

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

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getMotion() {
        return motion;
    }

    public void setMotion(Integer motion) {
        this.motion = motion;
    }

    public Integer getCo2() {
        return co2;
    }

    public void setCo2(Integer co2) {
        this.co2 = co2;
    }

    public Integer getFlux() {
        return flux;
    }

    public void setFlux(Integer flux) {
        this.flux = flux;
    }
}
