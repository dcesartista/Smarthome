package id.ac.ugm.smartcity.smarthome.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dito on 29/05/17.
 */

public class Home {
    public static final String NAME = "name";
    public static final String GATEWAY = "gateway_id";
    public static final String ID = "devid";

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
    private Object lowertemp;
    @SerializedName("uppertemp")
    @Expose
    private Object uppertemp;
    @SerializedName("lowerhum")
    @Expose
    private Object lowerhum;
    @SerializedName("upperhum")
    @Expose
    private Object upperhum;
    @SerializedName("lowerco")
    @Expose
    private Object lowerco;
    @SerializedName("upperco")
    @Expose
    private Object upperco;
    @SerializedName("lowerflux")
    @Expose
    private Object lowerflux;
    @SerializedName("upperflux")
    @Expose
    private Object upperflux;
    @SerializedName("upperenergy")
    @Expose
    private Object upperenergy;
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

    public Object getLowertemp() {
        return lowertemp;
    }

    public void setLowertemp(Object lowertemp) {
        this.lowertemp = lowertemp;
    }

    public Object getUppertemp() {
        return uppertemp;
    }

    public void setUppertemp(Object uppertemp) {
        this.uppertemp = uppertemp;
    }

    public Object getLowerhum() {
        return lowerhum;
    }

    public void setLowerhum(Object lowerhum) {
        this.lowerhum = lowerhum;
    }

    public Object getUpperhum() {
        return upperhum;
    }

    public void setUpperhum(Object upperhum) {
        this.upperhum = upperhum;
    }

    public Object getLowerco() {
        return lowerco;
    }

    public void setLowerco(Object lowerco) {
        this.lowerco = lowerco;
    }

    public Object getUpperco() {
        return upperco;
    }

    public void setUpperco(Object upperco) {
        this.upperco = upperco;
    }

    public Object getLowerflux() {
        return lowerflux;
    }

    public void setLowerflux(Object lowerflux) {
        this.lowerflux = lowerflux;
    }

    public Object getUpperflux() {
        return upperflux;
    }

    public void setUpperflux(Object upperflux) {
        this.upperflux = upperflux;
    }

    public Object getUpperenergy() {
        return upperenergy;
    }

    public void setUpperenergy(Object upperenergy) {
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
}
