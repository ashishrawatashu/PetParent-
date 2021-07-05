
package com.cynoteck.petofy.response.getAdoptionRequestListResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAdoptionRequestStatus {

    @SerializedName("id")
    @Expose
    private Double id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusCode")
    @Expose
    private Double statusCode;
    @SerializedName("petAdoption")
    @Expose
    private List<Object> petAdoption = null;
    @SerializedName("petDonation")
    @Expose
    private List<Object> petDonation = null;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Double statusCode) {
        this.statusCode = statusCode;
    }

    public List<Object> getPetAdoption() {
        return petAdoption;
    }

    public void setPetAdoption(List<Object> petAdoption) {
        this.petAdoption = petAdoption;
    }

    public List<Object> getPetDonation() {
        return petDonation;
    }

    public void setPetDonation(List<Object> petDonation) {
        this.petDonation = petDonation;
    }

}
