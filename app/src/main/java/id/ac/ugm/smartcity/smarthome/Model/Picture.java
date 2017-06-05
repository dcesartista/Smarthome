package id.ac.ugm.smartcity.smarthome.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by dito on 05/06/17.
 */

public class Picture implements Serializable {
    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Picture(String url) {
        this.url = url;
    }
}
