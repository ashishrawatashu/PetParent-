package com.cynoteck.petofyparents.parameter.getVetListParams;

public class GetVetListParams {
    private String lattitude;

    private String viewMore;

    private String longitude;

    private String CityId;

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getLattitude ()
    {
        return lattitude;
    }

    public void setLattitude (String lattitude)
    {
        this.lattitude = lattitude;
    }

    public String getViewMore ()
    {
        return viewMore;
    }

    public void setViewMore (String viewMore)
    {
        this.viewMore = viewMore;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [lattitude = "+lattitude+", viewMore = "+viewMore+", longitude = "+longitude+", CityId = "+CityId+"]";
    }
}
