package com.cynoteck.petofyparents.response.getPetHospitalizationResponse.getHospitalizationDeatilsResponse;

import com.cynoteck.petofyparents.response.Header;
import com.cynoteck.petofyparents.response.Response;

;

public class GetHospitalizationDeatilsResponse {
    private GetHospitalizationDeatilsData data;

    private Response response;

    private Header header;

    public GetHospitalizationDeatilsData getData ()
    {
        return data;
    }

    public void setData (GetHospitalizationDeatilsData data)
    {
        this.data = data;
    }

    public Response getResponse ()
    {
        return response;
    }

    public void setResponse (Response response)
    {
        this.response = response;
    }

    public Header getHeader ()
    {
        return header;
    }

    public void setHeader (Header header)
    {
        this.header = header;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", response = "+response+", header = "+header+"]";
    }
}

