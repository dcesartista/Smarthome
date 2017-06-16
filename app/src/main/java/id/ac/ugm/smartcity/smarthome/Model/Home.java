package id.ac.ugm.smartcity.smarthome.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by dito on 29/05/17.
 */

public class Home implements Serializable, DisplayableItem {
    public static final String NAME = "name";
    public static final String GATEWAY = "gateway_id";
    public static final String ID = "devid";
    public static final String ENERGY = "upperenergy";
    public static final String COST = "cost_limit";
    public static final String UPPER_TEMPERATURE = "uppertemp";
    public static final String LOWER_TEMPERATURE = "lowertemp";
    public static final String UPPER_HUMIDITY = "upperhum";
    public static final String LOWER_HUMIDITY = "lowerhum";
    public static final String UPPER_CO2 = "upperco";
    public static final String LOWER_CO2 = "lowerco";
    public static final String UPPER_LIGHT = "upperflux";
    public static final String LOWER_LIGHT = "lowerflux";

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("lowertemp")
    @Expose
    private String lowertemp;
    @SerializedName("uppertemp")
    @Expose
    private String uppertemp;
    @SerializedName("lowerhum")
    @Expose
    private String lowerhum;
    @SerializedName("upperhum")
    @Expose
    private String upperhum;
    @SerializedName("lowerco")
    @Expose
    private String lowerco;
    @SerializedName("upperco")
    @Expose
    private String upperco;
    @SerializedName("lowerflux")
    @Expose
    private String lowerflux;
    @SerializedName("upperflux")
    @Expose
    private String upperflux;
    @SerializedName("upperenergy")
    @Expose
    private String upperenergy;
    @SerializedName("devid")
    @Expose
    private String devid;
    @SerializedName("gateway_id")
    @Expose
    private String gatewayId;
    @SerializedName("lowertemp_flag")
    @Expose
    private Object lowertempFlag;
    @SerializedName("uppertemp_flag")
    @Expose
    private Object uppertempFlag;
    @SerializedName("lowerhum_flag")
    @Expose
    private Object lowerhumFlag;
    @SerializedName("upperhum_flag")
    @Expose
    private Object upperhumFlag;
    @SerializedName("lowerco_flag")
    @Expose
    private Object lowercoFlag;
    @SerializedName("upperco_flag")
    @Expose
    private Object uppercoFlag;
    @SerializedName("upperenergy_flag")
    @Expose
    private Object upperenergyFlag;
    @SerializedName("lowerflux_flag")
    @Expose
    private Object lowerfluxFlag;
    @SerializedName("upperflux_flag")
    @Expose
    private Object upperfluxFlag;
    @SerializedName("cost_limit")
    @Expose
    private String costLimit;

    private List<Device> devices;

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

    public String getLowertemp() {
        return lowertemp;
    }

    public void setLowertemp(String lowertemp) {
        this.lowertemp = lowertemp;
    }

    public String getUppertemp() {
        return uppertemp;
    }

    public void setUppertemp(String uppertemp) {
        this.uppertemp = uppertemp;
    }

    public String getLowerhum() {
        return lowerhum;
    }

    public void setLowerhum(String lowerhum) {
        this.lowerhum = lowerhum;
    }

    public String getUpperhum() {
        return upperhum;
    }

    public void setUpperhum(String upperhum) {
        this.upperhum = upperhum;
    }

    public String getLowerco() {
        return lowerco;
    }

    public void setLowerco(String lowerco) {
        this.lowerco = lowerco;
    }

    public String getUpperco() {
        return upperco;
    }

    public void setUpperco(String upperco) {
        this.upperco = upperco;
    }

    public String getLowerflux() {
        return lowerflux;
    }

    public void setLowerflux(String lowerflux) {
        this.lowerflux = lowerflux;
    }

    public String getUpperflux() {
        return upperflux;
    }

    public void setUpperflux(String upperflux) {
        this.upperflux = upperflux;
    }

    public String getUpperenergy() {
        return upperenergy;
    }

    public void setUpperenergy(String upperenergy) {
        this.upperenergy = upperenergy;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public Object getLowertempFlag() {
        return lowertempFlag;
    }

    public void setLowertempFlag(Object lowertempFlag) {
        this.lowertempFlag = lowertempFlag;
    }

    public Object getUppertempFlag() {
        return uppertempFlag;
    }

    public void setUppertempFlag(Object uppertempFlag) {
        this.uppertempFlag = uppertempFlag;
    }

    public Object getLowerhumFlag() {
        return lowerhumFlag;
    }

    public void setLowerhumFlag(Object lowerhumFlag) {
        this.lowerhumFlag = lowerhumFlag;
    }

    public Object getUpperhumFlag() {
        return upperhumFlag;
    }

    public void setUpperhumFlag(Object upperhumFlag) {
        this.upperhumFlag = upperhumFlag;
    }

    public Object getLowercoFlag() {
        return lowercoFlag;
    }

    public void setLowercoFlag(Object lowercoFlag) {
        this.lowercoFlag = lowercoFlag;
    }

    public Object getUppercoFlag() {
        return uppercoFlag;
    }

    public void setUppercoFlag(Object uppercoFlag) {
        this.uppercoFlag = uppercoFlag;
    }

    public Object getUpperenergyFlag() {
        return upperenergyFlag;
    }

    public void setUpperenergyFlag(Object upperenergyFlag) {
        this.upperenergyFlag = upperenergyFlag;
    }

    public Object getLowerfluxFlag() {
        return lowerfluxFlag;
    }

    public void setLowerfluxFlag(Object lowerfluxFlag) {
        this.lowerfluxFlag = lowerfluxFlag;
    }

    public Object getUpperfluxFlag() {
        return upperfluxFlag;
    }

    public void setUpperfluxFlag(Object upperfluxFlag) {
        this.upperfluxFlag = upperfluxFlag;
    }

    public String getCostLimit() {
        return costLimit;
    }

    public void setCostLimit(String costLimit) {
        this.costLimit = costLimit;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
