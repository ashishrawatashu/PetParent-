
package com.cynoteck.petofy.parameter.saveFeedbackRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SaveFeedbackRequest {

    @SerializedName("data")
    @Expose
    private SaveFeedbackParams data;

    public SaveFeedbackParams getData() {
        return data;
    }

    public void setData(SaveFeedbackParams data) {
        this.data = data;
    }

}
