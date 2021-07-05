package com.cynoteck.petofy.parameter.updateDonation;

public class UpdateDonationRequest {
    private UpdatedonationParamter data;

    public UpdatedonationParamter getData() {
        return data;
    }

    public void setData(UpdatedonationParamter data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "data=" + data +
                "]";
    }
}
