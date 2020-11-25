package com.cynoteck.petofyparents.response.adoptionRequestResponse;

import com.cynoteck.petofyparents.response.adoptionResponse.Pet;

public class AdoptionModel {
    private Double id;
    private String userId;
    private Double petId;
    private String requestDate;
    private Double requestStatusId;
    private Object reasonToReject;
    private Pet pet;
    private Object requestStatus;
    private Object user;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Double getPetId() {
        return petId;
    }

    public void setPetId(Double petId) {
        this.petId = petId;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public Double getRequestStatusId() {
        return requestStatusId;
    }

    public void setRequestStatusId(Double requestStatusId) {
        this.requestStatusId = requestStatusId;
    }

    public Object getReasonToReject() {
        return reasonToReject;
    }

    public void setReasonToReject(Object reasonToReject) {
        this.reasonToReject = reasonToReject;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Object getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Object requestStatus) {
        this.requestStatus = requestStatus;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "id=" + id +
                ", userId=" + userId + 
                ", petId=" + petId +
                ", requestDate=" + requestDate + 
                ", requestStatusId=" + requestStatusId +
                ", reasonToReject=" + reasonToReject +
                ", pet=" + pet +
                ", requestStatus=" + requestStatus +
                ", user=" + user +
                "]";
    }
}
