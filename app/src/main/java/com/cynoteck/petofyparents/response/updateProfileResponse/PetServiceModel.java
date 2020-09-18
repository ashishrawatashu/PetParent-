package com.cynoteck.petofyparents.response.updateProfileResponse;

public class PetServiceModel {
    private String id;
    private String serviceType1;
    private String isActive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceType1() {
        return serviceType1;
    }

    public void setServiceType1(String serviceType1) {
        this.serviceType1 = serviceType1;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", serviceType1 = "+serviceType1+", isActive = "+isActive+"]";
    }
}
