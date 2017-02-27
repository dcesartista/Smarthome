package id.ac.ugm.smartcity.smarthome.Model.User_Model.Register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by dito on 24/02/17.
 */

public class RegisterErrors {
    @SerializedName("email")
    @Expose
    private List<String> email = null;
    @SerializedName("password_confirmation")
    @Expose
    private List<String> passwordConfirmation = null;
    @SerializedName("full_messages")
    @Expose
    private List<String> fullMessages = null;

    public List<String> getEmail() {
        return email;
    }

    public void setEmail(List<String> email) {
        this.email = email;
    }

    public List<String> getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(List<String> passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public List<String> getFullMessages() {
        return fullMessages;
    }

    public void setFullMessages(List<String> fullMessages) {
        this.fullMessages = fullMessages;
    }
}
