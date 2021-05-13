
package com.cynoteck.petofyparents.response.getDonationRequestResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDonationRequestRequestToNgoModel {

    @SerializedName("ngoModel")
    @Expose
    private GetDonationRequestNgoModel ngoModel;

    public GetDonationRequestNgoModel getNgoModel() {
        return ngoModel;
    }

    public void setNgoModel(GetDonationRequestNgoModel ngoModel) {
        this.ngoModel = ngoModel;
    }

}
