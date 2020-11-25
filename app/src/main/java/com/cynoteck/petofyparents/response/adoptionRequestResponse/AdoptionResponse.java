package com.cynoteck.petofyparents.response.adoptionRequestResponse;

import com.cynoteck.petofyparents.response.Header;
import com.cynoteck.petofyparents.response.Response;

public class AdoptionResponse {
    private Header header;
    private AdoptionModel data;
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public AdoptionModel getData() {
        return data;
    }

    public void setData(AdoptionModel data) {
        this.data = data;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "header=" + header +
                ", data=" + data +
                ", response=" + response +
                "]";
    }
}
