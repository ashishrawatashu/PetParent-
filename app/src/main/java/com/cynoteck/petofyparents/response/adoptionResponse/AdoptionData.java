package com.cynoteck.petofyparents.response.adoptionResponse;

import com.cynoteck.petofyparents.response.donationResponse.RequestToNgoModel;
import com.cynoteck.petofyparents.response.getAppointmentsStatusResponse.User;

public class AdoptionData {

    private String encryptedId;
    private String id;
    private String userId;
    private String petId;
    private String requestDate;
    private String requestStatusId;
    private String requesterName;
    private String requestCurrentStatus;
    private String reasonToReject;
    private String requestUpdateDate;
    private Pet pet;
    private RequestStatus requestStatus;
    private RequestToNgoModel requestToNgoModel;
    private User user;

    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    public String getRequestStatusId() {
        return requestStatusId;
    }

    public void setRequestStatusId(String requestStatusId) {
        this.requestStatusId = requestStatusId;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public void setRequesterName(String requesterName) {
        this.requesterName = requesterName;
    }

    public String getRequestCurrentStatus() {
        return requestCurrentStatus;
    }

    public void setRequestCurrentStatus(String requestCurrentStatus) {
        this.requestCurrentStatus = requestCurrentStatus;
    }

    public String getReasonToReject() {
        return reasonToReject;
    }

    public void setReasonToReject(String reasonToReject) {
        this.reasonToReject = reasonToReject;
    }

    public String getRequestUpdateDate() {
        return requestUpdateDate;
    }

    public void setRequestUpdateDate(String requestUpdateDate) {
        this.requestUpdateDate = requestUpdateDate;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public RequestToNgoModel getRequestToNgoModel() {
        return requestToNgoModel;
    }

    public void setRequestToNgoModel(RequestToNgoModel requestToNgoModel) {
        this.requestToNgoModel = requestToNgoModel;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "encryptedId=" + encryptedId + 
                ", id=" + id + 
                ", userId=" + userId + 
                ", petId=" + petId + 
                ", requestDate=" + requestDate + 
                ", requestStatusId=" + requestStatusId + 
                ", requesterName=" + requesterName + 
                ", requestCurrentStatus=" + requestCurrentStatus + 
                ", reasonToReject=" + reasonToReject + 
                ", requestUpdateDate=" + requestUpdateDate + 
                ", pet=" + pet +
                ", requestStatus=" + requestStatus +
                ", requestToNgoModel=" + requestToNgoModel +
                ", user=" + user +
                "]";
    }
}
