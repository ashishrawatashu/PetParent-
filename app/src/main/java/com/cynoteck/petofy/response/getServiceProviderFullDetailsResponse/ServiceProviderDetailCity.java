
package com.cynoteck.petofy.response.getServiceProviderFullDetailsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceProviderDetailCity {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("city1")
    @Expose
    private String city1;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("latLong")
    @Expose
    private Object latLong;
    @SerializedName("stateId")
    @Expose
    private Double stateId;
    @SerializedName("cityName")
    @Expose
    private Object cityName;
    @SerializedName("stateList")
    @Expose
    private Object stateList;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Object getLatLong() {
        return latLong;
    }

    public void setLatLong(Object latLong) {
        this.latLong = latLong;
    }

    public Double getStateId() {
        return stateId;
    }

    public void setStateId(Double stateId) {
        this.stateId = stateId;
    }

    public Object getCityName() {
        return cityName;
    }

    public void setCityName(Object cityName) {
        this.cityName = cityName;
    }

    public Object getStateList() {
        return stateList;
    }

    public void setStateList(Object stateList) {
        this.stateList = stateList;
    }

}
