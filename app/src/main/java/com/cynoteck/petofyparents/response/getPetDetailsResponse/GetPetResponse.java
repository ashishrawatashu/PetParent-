package com.cynoteck.petofyparents.response.getPetDetailsResponse;


import com.cynoteck.petofyparents.response.Header;
import com.cynoteck.petofyparents.response.Response;

public class GetPetResponse {
    private GetPetData data;

    private Response response;

    private Header header;

    public GetPetData getData ()
    {
        return data;
    }

    public void setData (GetPetData data)
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
