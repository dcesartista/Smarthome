package id.ac.ugm.smartcity.smarthome.Model.User_Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dito on 18/02/17.
 */

public class Data {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("provider")
    @Expose
    private String provider;
    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("nickname")
    @Expose
    private Object nickname;
    @SerializedName("image")
    @Expose
    private Object image;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getNickname() {
        return nickname;
    }

    public void setNickname(Object nickname) {
        this.nickname = nickname;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
        this.image = image;
    }

}
