package com.cynoteck.petofy.response.adoptionResponse;

import com.cynoteck.petofy.response.Header;
import com.cynoteck.petofy.response.Response;

import java.util.List;

public class AdoptionResponse {

    private Header header;
    private List<AdoptionData> data = null;
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<AdoptionData> getData() {
        return data;
    }

    public void setData(List<AdoptionData> data) {
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
