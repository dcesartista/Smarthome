package id.ac.ugm.smartcity.smarthome.Model.recycleritem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;

/**
 * Created by dito on 09/02/17.
 */

public class AlertOld implements DisplayableItem {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sensor_name")
    @Expose
    private String sensorName;
    @SerializedName("value")
    @Expose
    private Object value;
    @SerializedName("warning")
    @Expose
    private String warning;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("device_id")
    @Expose
    private Integer deviceId;

    /**
     * No args constructor for use in serialization
     *
     */
    public AlertOld() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param createdAt
     * @param value
     * @param deviceId
     * @param warning
     * @param sensorName
     */
    public AlertOld(Integer id, String sensorName, Object value, String warning, String createdAt, String updatedAt, Integer deviceId) {
        super();
        this.id = id;
        this.sensorName = sensorName;
        this.value = value;
        this.warning = warning;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deviceId = deviceId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getWarning() {
        return warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
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

}
