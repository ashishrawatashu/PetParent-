package com.cynoteck.petofyparents.response.donationResponse;

public class RequestToNgoModel {
    private NgoModel ngoModel;

    public NgoModel getNgoModel() {
        return ngoModel;
    }

    public void setNgoModel(NgoModel ngoModel) {
        this.ngoModel = ngoModel;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "ngoModel=" + ngoModel +
                "]";
    }
}
