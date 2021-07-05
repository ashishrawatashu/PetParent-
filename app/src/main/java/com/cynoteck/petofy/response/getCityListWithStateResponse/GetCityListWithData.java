package com.cynoteck.petofy.response.getCityListWithStateResponse;

public class GetCityListWithData {
    private String city1;

    private String cityName;

    private String latLong;

    private String stateId;

    private String stateList;

    private String id;

    private String isActive;

    public String getCity1 ()
    {
        return city1;
    }

    public void setCity1 (String city1)
    {
        this.city1 = city1;
    }

    public String getCityName ()
    {
        return cityName;
    }

    public void setCityName (String cityName)
    {
        this.cityName = cityName;
    }

    public String getLatLong ()
    {
        return latLong;
    }

    public void setLatLong (String latLong)
    {
        this.latLong = latLong;
    }

    public String getStateId ()
    {
        return stateId;
    }

    public void setStateId (String stateId)
    {
        this.stateId = stateId;
    }

    public String getStateList ()
    {
        return stateList;
    }

    public void setStateList (String stateList)
    {
        this.stateList = stateList;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getIsActive ()
    {
        return isActive;
    }

    public void setIsActive (String isActive)
    {
        this.isActive = isActive;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [city1 = "+city1+", cityName = "+cityName+", latLong = "+latLong+", stateId = "+stateId+", stateList = "+stateList+", id = "+id+", isActive = "+isActive+"]";
    }
}
