package com.cynoteck.petofy.response.getpetbreedsResponse;

import com.cynoteck.petofy.response.Header;
import com.cynoteck.petofy.response.Response;

import java.util.List;

public class GetPetBreedsResponse {
    private List<GetPetbreedsData> data;

    private Response response;

    private Header header;

    public List<GetPetbreedsData> getData ()
    {
        return data;
    }

    public void setData (List<GetPetbreedsData> data)
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


