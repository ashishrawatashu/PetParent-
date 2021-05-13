
package com.cynoteck.petofyparents.response.getAdoptionRequestListResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAdoptionRequestListData {

    @SerializedName("encryptedId")
    @Expose
    private String encryptedId;
    @SerializedName("id")
    @Expose
    private Double id;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("petId")
    @Expose
    private Object petId;
    @SerializedName("requestDate")
    @Expose
    private String requestDate;
    @SerializedName("requestStatusId")
    @Expose
    private Double requestStatusId;
    @SerializedName("requesterName")
    @Expose
    private String requesterName;
    @SerializedName("requestCurrentStatus")
    @Expose
    private String requestCurrentStatus;
    @SerializedName("reasonToReject")
    @Expose
    private Object reasonToReject;
    @SerializedName("requestStatusTitle")
    @Expose
    private Object requestStatusTitle;
    @SerializedName("requestUpdateDate")
    @Expose
    private String requestUpdateDate;
    @SerializedName("pet")
    @Expose
    private GetAdoptionPet pet;
    @SerializedName("requestStatus")
    @Expose
    private GetAdoptionRequestStatus requestStatus;
    @SerializedName("requestToNgoModel")
    @Expose
    private GetAdoptionRequestToNgoModelData requestToNgoModel;
    @SerializedName("user")
    @Expose
    private GetAdoptionUserData user;

    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }

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

    public Object getPetId() {
        return petId;
    }

    public void setPetId(Object petId) {
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

    public Object getReasonToReject() {
        return reasonToReject;
    }

    public void setReasonToReject(Object reasonToReject) {
        this.reasonToReject = reasonToReject;
    }

    public Object getRequestStatusTitle() {
        return requestStatusTitle;
    }

    public void setRequestStatusTitle(Object requestStatusTitle) {
        this.requestStatusTitle = requestStatusTitle;
    }

    public String getRequestUpdateDate() {
        return requestUpdateDate;
    }

    public void setRequestUpdateDate(String requestUpdateDate) {
        this.requestUpdateDate = requestUpdateDate;
    }

    public GetAdoptionPet getPet() {
        return pet;
    }

    public void setPet(GetAdoptionPet pet) {
        this.pet = pet;
    }

    public GetAdoptionRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(GetAdoptionRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public GetAdoptionRequestToNgoModelData getRequestToNgoModel() {
        return requestToNgoModel;
    }

    public void setRequestToNgoModel(GetAdoptionRequestToNgoModelData requestToNgoModel) {
        this.requestToNgoModel = requestToNgoModel;
    }

    public GetAdoptionUserData getUser() {
        return user;
    }

    public void setUser(GetAdoptionUserData user) {
        this.user = user;
    }

}
