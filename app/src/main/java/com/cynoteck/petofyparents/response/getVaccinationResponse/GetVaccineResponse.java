package com.cynoteck.petofyparents.response.getVaccinationResponse;

import com.cynoteck.petofyparents.response.Header;
import com.cynoteck.petofyparents.response.Response;

import java.util.ArrayList;

public class GetVaccineResponse {
    
    private Header header;
    private ArrayList<GetVaccineResponseModel> data = null;
    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public ArrayList<GetVaccineResponseModel> getData() {
        return data;
    }

    public void setData(ArrayList<GetVaccineResponseModel> data) {
        this.data = data;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String toString() {
        return "ClassPojo[" +
                "header=" + header +
                ", data=" + data +
                ", response=" + response +
                "]";
    }
}
