package id.ac.ugm.smartcity.smarthome.Model;

import java.util.Date;

/**
 * Created by dito on 06/02/17.
 */

public class Energy {
    private int energyId;
    private Date timestamp;
    private float power;
    private float total;
    private float energyDay;
    private float energyMonth;
    private float energyYear;

    public int getEnergyId() {
        return energyId;
    }

    public void setEnergyId(int energyId) {
        this.energyId = energyId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public float getEnergyDay() {
        return energyDay;
    }

    public void setEnergyDay(float energyDay) {
        this.energyDay = energyDay;
    }

    public float getEnergyMonth() {
        return energyMonth;
    }

    public void setEnergyMonth(float energyMonth) {
        this.energyMonth = energyMonth;
    }

    public float getEnergyYear() {
        return energyYear;
    }

    public void setEnergyYear(float energyYear) {
        this.energyYear = energyYear;
    }

    @Override
    public String toString() {
        return "Energy{" +
                "energyId=" + energyId +
                ", timestamp=" + timestamp +
                ", power=" + power +
                ", total=" + total +
                ", energyDay=" + energyDay +
                ", energyMonth=" + energyMonth +
                ", energyYear=" + energyYear +
                '}';
    }
}
