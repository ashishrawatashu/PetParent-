package com.cynoteck.petofyparents.response.adoptionResponse;

import java.util.List;

public class RequestStatus {
    private String id;
    private String status;
    private String statusCode;
    private List<String> petAdoption = null;
    private List<String> petDonation = null;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<String> getPetAdoption() {
        return petAdoption;
    }

    public void setPetAdoption(List<String> petAdoption) {
        this.petAdoption = petAdoption;
    }

    public List<String> getPetDonation() {
        return petDonation;
    }

    public void setPetDonation(List<String> petDonation) {
        this.petDonation = petDonation;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "id=" + id + 
                ", status=" + status + 
                ", statusCode=" + statusCode + 
                ", petAdoption=" + petAdoption +
                ", petDonation=" + petDonation +
                "]";
    }
}
