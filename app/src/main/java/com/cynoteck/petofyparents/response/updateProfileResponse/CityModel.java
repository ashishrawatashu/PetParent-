package com.cynoteck.petofyparents.response.updateProfileResponse;

public class CityModel {
    private String id;
    private String city1;
    private String isActive;
    private String stateId;
    private String stateList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity1() {
        return city1;
    }

    public void setCity1(String city1) {
        this.city1 = city1;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public Object getStateList() {
        return stateList;
    }

    public void setStateList(String stateList) {
        this.stateList = stateList;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", city1 = "+city1+", isActive = "+isActive+", stateId = "+stateId+", stateList = "+stateList+"]";
    }

}
