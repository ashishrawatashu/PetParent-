package com.cynoteck.petofy.response.getServiceProviderFullDetailsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProviderRatingList {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("providerId")
    @Expose
    private Integer providerId;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("feedback")
    @Expose
    private String feedback;
    @SerializedName("feedbackDate")
    @Expose
    private String feedbackDate;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("profileImage")
    @Expose
    private String profileImage;
    @SerializedName("provider")
    @Expose
    private Object provider;
    @SerializedName("user")
    @Expose
    private Object user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProviderId() {
        return providerId;
    }

    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getFeedbackDate() {
        return feedbackDate;
    }

    public void setFeedbackDate(String feedbackDate) {
        this.feedbackDate = feedbackDate;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Object getProvider() {
        return provider;
    }

    public void setProvider(Object provider) {
        this.provider = provider;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

}
