package com.cynoteck.petofy.response.InPetVeterian;

import com.cynoteck.petofy.response.Header;
import com.cynoteck.petofy.response.Response;

public class InPetVeterianResponse {
    private InPetModel data;

    private Response response;

    private Header header;

    public InPetModel getData ()
    {
        return data;
    }

    public void setData (InPetModel data)
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
