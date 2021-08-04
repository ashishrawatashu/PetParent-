package com.cynoteck.petofy.parameter.punchingPolicyPetRequest;

import com.cynoteck.petofy.parameter.punchingPolicyRequest.PunchingPolicyParams;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PunchingPolicyPetRequest {

    @SerializedName("data")
    @Expose
    private PunchingPolicyPetParams data;

    public PunchingPolicyPetParams getData() {
        return data;
    }

    public void setData(PunchingPolicyPetParams data) {
        this.data = data;
    }

}
