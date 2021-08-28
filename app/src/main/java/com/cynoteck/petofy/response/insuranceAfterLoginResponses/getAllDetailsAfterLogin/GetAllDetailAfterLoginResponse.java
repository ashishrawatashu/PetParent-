
package com.cynoteck.petofy.response.insuranceAfterLoginResponses.getAllDetailsAfterLogin;

import com.cynoteck.petofy.response.Header;
import com.cynoteck.petofy.response.Response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllDetailAfterLoginResponse {

    @SerializedName("header")
    @Expose
    private Header header;
    @SerializedName("data")
    @Expose
    private GetAllDetailAfterLoginData data;
    @SerializedName("response")
    @Expose
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public GetAllDetailAfterLoginData getData() {
        return data;
    }

    public void setData(GetAllDetailAfterLoginData data) {
        this.data = data;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

}
