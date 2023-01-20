
package com.cynoteck.petofy.response.getProviderFeedbackResponse;

import java.util.List;

import com.cynoteck.petofy.response.Header;
import com.cynoteck.petofy.response.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetProviderFeedbackResponse {

    @SerializedName("header")
    @Expose
    private Header header;
    @SerializedName("data")
    @Expose
    private List<GetProviderFeedbackData> data = null;
    @SerializedName("response")
    @Expose
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<GetProviderFeedbackData> getData() {
        return data;
    }

    public void setData(List<GetProviderFeedbackData> data) {
        this.data = data;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
