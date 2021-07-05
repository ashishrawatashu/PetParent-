
package com.cynoteck.petofy.response.getAdoptionRequestListResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAdoptionRequestToNgoModelData {

    @SerializedName("ngoModel")
    @Expose
    private GetAdoptionNgoModelData ngoModel;

    public GetAdoptionNgoModelData getNgoModel() {
        return ngoModel;
    }

    public void setNgoModel(GetAdoptionNgoModelData ngoModel) {
        this.ngoModel = ngoModel;
    }

}
