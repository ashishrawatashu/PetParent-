
package com.cynoteck.petofy.parameter.saveFeedbackRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveFeedbackParams {

    @SerializedName("providerId")
    @Expose
    private Integer providerId;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("feedback")
    @Expose
    private String feedback;
    @SerializedName("feedbackDate")
    @Expose
    private String feedbackDate;
    @SerializedName("status")
    @Expose
    private Integer status;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

}
