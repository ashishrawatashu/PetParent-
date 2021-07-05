
package com.cynoteck.petofy.response.getServiceProviderFullDetailsResponse;

import com.cynoteck.petofy.response.Header;
import com.cynoteck.petofy.response.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchProviderFullDetailResponse {

    @SerializedName("header")
    @Expose
    private Header header;
    @SerializedName("data")
    @Expose
    private ServiceProviderDetailData data;
    @SerializedName("response")
    @Expose
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public ServiceProviderDetailData getData() {
        return data;
    }

    public void setData(ServiceProviderDetailData data) {
        this.data = data;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
