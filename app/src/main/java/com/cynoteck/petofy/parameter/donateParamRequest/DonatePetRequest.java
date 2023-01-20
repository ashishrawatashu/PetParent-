package com.cynoteck.petofy.parameter.donateParamRequest;

public class DonatePetRequest {

    private DonatePetParameter data;

    public DonatePetParameter getData() {
        return data;
    }

    public void setData(DonatePetParameter data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "data=" + data +
                "]";
    }
}
