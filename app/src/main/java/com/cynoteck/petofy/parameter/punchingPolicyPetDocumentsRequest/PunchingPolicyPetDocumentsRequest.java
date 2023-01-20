package com.cynoteck.petofy.parameter.punchingPolicyPetDocumentsRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PunchingPolicyPetDocumentsRequest {
    @SerializedName("data")
    @Expose
    private PunchingPolicyPetDocumentsParams data;

    public PunchingPolicyPetDocumentsParams getData() {
        return data;
    }

    public void setData(PunchingPolicyPetDocumentsParams data) {
        this.data = data;
    }
}
