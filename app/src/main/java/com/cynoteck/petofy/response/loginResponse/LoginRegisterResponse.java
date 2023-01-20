package com.cynoteck.petofy.response.loginResponse;

import com.cynoteck.petofy.response.Header;
import com.cynoteck.petofy.response.Response;

public class LoginRegisterResponse {

    private LoginRegisterResponseData data;

    private Response response;

    private Header header;

    public LoginRegisterResponseData getData()
    {
        return data;
    }

    public void setData(LoginRegisterResponseData data)
    {
        this.data = data;
    }

    public Response getResponseLogin()
    {
        return response;
    }

    public void setResponseLogin(Response response)
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
        return "ClassPojo [data = "+ data +", response = "+ response +", header = "+header+"]";
    }

}
