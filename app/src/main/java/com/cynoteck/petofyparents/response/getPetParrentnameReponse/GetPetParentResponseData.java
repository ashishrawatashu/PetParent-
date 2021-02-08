package com.cynoteck.petofyparents.response.getPetParrentnameReponse;

import com.cynoteck.petofyparents.response.Header;
import com.cynoteck.petofyparents.response.Response;

import java.util.List;

public class GetPetParentResponseData {
    private Header header;
    private List<GetPetParentModel> data = null;
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public List<GetPetParentModel> getData() {
        return data;
    }

    public void setData(List<GetPetParentModel> data) {
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