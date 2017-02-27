package id.ac.ugm.smartcity.smarthome.Model.User_Model.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dito on 06/02/17.
 */

public class RegisterUser {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private RegisterData data;
    @SerializedName("errors")
    @Expose
    private RegisterErrors errors;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public RegisterData getData() {
        return data;
    }

    public void setData(RegisterData data) {
        this.data = data;
    }

    public RegisterErrors getErrors() {
        return errors;
    }

    public void setErrors(RegisterErrors errors) {
        this.errors = errors;
    }
}
