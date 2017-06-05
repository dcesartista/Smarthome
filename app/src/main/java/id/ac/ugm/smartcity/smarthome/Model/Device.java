package id.ac.ugm.smartcity.smarthome.Model;

/**
 * Created by dito on 06/02/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Device implements Serializable {
    public static final String NAME = "name";
    public static final String PRODUCT_ID = "productID";
    public static final String ID = "deviceID";
    public static final String PICTURE = "picture";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("productID")
    @Expose
    private String productID;
    @SerializedName("picture")
    @Expose
    private Picture picture;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("home_id")
    @Expose
    private Integer homeId;

    /**
     * No args constructor for use in serialization
     *
     */
    public Device() {
    }

    /**
     *
     * @param updatedAt
     * @param id
     * @param createdAt
     * @param name
     * @param picture
     * @param homeId
     * @param productID
     */
    public Device(Integer id, String name, String productID, Picture picture, String createdAt, String updatedAt, Integer homeId) {
        super();
        this.id = id;
        this.name = name;
        this.productID = productID;
        this.picture = picture;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.homeId = homeId;
    }

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

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public Picture getImg() {
        return picture;
    }

    public void setImg(Picture picture) {
        this.picture = picture;
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

    public Integer getHomeId() {
        return homeId;
    }

    public void setHomeId(Integer homeId) {
        this.homeId = homeId;
    }

}
