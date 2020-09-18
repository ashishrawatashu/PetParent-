package com.cynoteck.petofyparents.response.updateProfileResponse;


import com.cynoteck.petofyparents.response.Header;
import com.cynoteck.petofyparents.response.Response;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PetServiceResponse {

    private Header header;

    @SerializedName("data")
    private List<PetServiceModel> data = null;

    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<PetServiceModel> getData() {
        return data;
    }

    public void setData(List<PetServiceModel> data) {
        this.data = data;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [petservicemodel = "+ data +", response = "+ response +", header = "+header+"]";
    }
}
