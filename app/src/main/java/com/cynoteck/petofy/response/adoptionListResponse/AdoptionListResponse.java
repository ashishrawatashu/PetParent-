package com.cynoteck.petofy.response.adoptionListResponse;

import com.cynoteck.petofy.response.Header;
import com.cynoteck.petofy.response.Response;

public class AdoptionListResponse {

    private Header header;
    private AdoptionListData data;
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public AdoptionListData getData() {
        return data;
    }

    public void setData(AdoptionListData data) {
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
