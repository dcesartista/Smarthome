package id.ac.ugm.smartcity.smarthome.Model.recycleritem;

import id.ac.ugm.smartcity.smarthome.Model.DisplayableItem;

/**
 * Created by dito on 09/02/17.
 */

public class AlertDay implements DisplayableItem {
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    private String day;

    public AlertDay(String day) {
        this.day = day;
    }
}
