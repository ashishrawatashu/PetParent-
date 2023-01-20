package com.cynoteck.petofy.parameter.punchingPolicyRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PunchingPolicyRequest {
    @SerializedName("data")
    @Expose
    private PunchingPolicyParams data;

    public PunchingPolicyParams getData() {
        return data;
    }

    public void setData(PunchingPolicyParams data) {
        this.data = data;
    }
}
