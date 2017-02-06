package id.ac.ugm.smartcity.smarthome.Model;

/**
 * Created by dito on 06/02/17.
 */

public class Device {
    private int deviceId;
    private int sensorId;
    private int relayId;
    private int logId;
    private String deviceName;
    private String img;
    private int productId;

    @Override
    public String toString() {
        return "Device{" +
                "deviceId=" + deviceId +
                ", sensorId=" + sensorId +
                ", relayId=" + relayId +
                ", logId=" + logId +
                ", deviceName='" + deviceName + '\'' +
                ", img='" + img + '\'' +
                ", productId=" + productId +
                '}';
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getSensorId() {
        return sensorId;
    }

    public void setSensorId(int sensorId) {
        this.sensorId = sensorId;
    }

    public int getRelayId() {
        return relayId;
    }

    public void setRelayId(int relayId) {
        this.relayId = relayId;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
