
package com.cynoteck.petofyparents.response.getAdoptionRequestListResponse;

import java.util.List;

import com.cynoteck.petofyparents.response.Header;
import com.cynoteck.petofyparents.response.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAdoptionRequestListResponse {

    @SerializedName("header")
    @Expose
    private Header header;
    @SerializedName("data")
    @Expose
    private List<GetAdoptionRequestListData> data = null;
    @SerializedName("response")
    @Expose
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<GetAdoptionRequestListData> getData() {
        return data;
    }

    public void setData(List<GetAdoptionRequestListData> data) {
        this.data = data;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
