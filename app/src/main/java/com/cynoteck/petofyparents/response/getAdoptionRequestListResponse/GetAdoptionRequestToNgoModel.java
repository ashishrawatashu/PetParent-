
package com.cynoteck.petofyparents.response.getAdoptionRequestListResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAdoptionRequestToNgoModel {

    @SerializedName("ngoModel")
    @Expose
    private GetAdoptionNgoModel ngoModel;

    public GetAdoptionNgoModel getNgoModel() {
        return ngoModel;
    }

    public void setNgoModel(GetAdoptionNgoModel ngoModel) {
        this.ngoModel = ngoModel;
    }

}
