package com.cynoteck.petofy.parameter.getServiceProvidersListRequest;

public class GetServiceProviderListParams {
    private String CityId;

    private String lattitude;

    private String serviceTypeId;

    private String longitude;



    public String getCityId() {
        return CityId;
    }

    public void setCityId(String CityId) {
        this.CityId = CityId;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public String getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(String serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "ClassPojo [CityId = " + CityId + ", lattitude = " + lattitude + ", serviceTypeId = " + serviceTypeId + ", longitude = " + longitude + "]";
    }
}




