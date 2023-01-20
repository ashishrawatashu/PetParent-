
package com.cynoteck.petofy.response.getAdoptionRequestListResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAdoptionPetImage {

    @SerializedName("id")
    @Expose
    private Double id;
    @SerializedName("petId")
    @Expose
    private Double petId;
    @SerializedName("petImageUrl")
    @Expose
    private Object petImageUrl;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public Double getPetId() {
        return petId;
    }

    public void setPetId(Double petId) {
        this.petId = petId;
    }

    public Object getPetImageUrl() {
        return petImageUrl;
    }

    public void setPetImageUrl(Object petImageUrl) {
        this.petImageUrl = petImageUrl;
    }

}
