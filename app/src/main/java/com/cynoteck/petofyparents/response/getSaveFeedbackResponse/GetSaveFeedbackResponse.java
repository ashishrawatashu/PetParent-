
package com.cynoteck.petofyparents.response.getSaveFeedbackResponse;

import com.cynoteck.petofyparents.response.Header;
import com.cynoteck.petofyparents.response.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSaveFeedbackResponse {

    @SerializedName("header")
    @Expose
    private Header header;
    @SerializedName("data")
    @Expose
    private GetSaveFeedbackData data;
    @SerializedName("response")
    @Expose
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public GetSaveFeedbackData getData() {
        return data;
    }

    public void setData(GetSaveFeedbackData data) {
        this.data = data;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
