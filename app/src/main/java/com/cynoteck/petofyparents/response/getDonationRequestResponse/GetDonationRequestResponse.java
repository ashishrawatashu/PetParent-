
package com.cynoteck.petofyparents.response.getDonationRequestResponse;

import java.util.List;

import com.cynoteck.petofyparents.response.Header;
import com.cynoteck.petofyparents.response.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDonationRequestResponse {

    @SerializedName("header")
    @Expose
    private Header header;
    @SerializedName("data")
    @Expose
    private List<GetDonationRequestData> data = null;
    @SerializedName("response")
    @Expose
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<GetDonationRequestData> getData() {
        return data;
    }

    public void setData(List<GetDonationRequestData> data) {
        this.data = data;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
