package com.cynoteck.petofyparents.parameter.adoptionRequest;

public class AdoptionRequest {
    private AdoptionRequestParameter data;

    public AdoptionRequestParameter getData() {
        return data;
    }

    public void setData(AdoptionRequestParameter data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "data=" + data +
                "]";
    }
}
