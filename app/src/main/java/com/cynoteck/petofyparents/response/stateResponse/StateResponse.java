package com.cynoteck.petofyparents.response.stateResponse;

import com.cynoteck.petofyparents.response.Header;
import com.cynoteck.petofyparents.response.Response;

import java.util.List;

public class StateResponse {
    private Header header;
    private List<StateModel> data = null;
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<StateModel> getData() {
        return data;
    }

    public void setData(List<StateModel> data) {
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
