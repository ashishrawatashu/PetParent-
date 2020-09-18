package com.cynoteck.petofyparents.response.updateProfileResponse;


import com.cynoteck.petofyparents.response.Header;
import com.cynoteck.petofyparents.response.Response;

public class UserResponse {
    private UserModel data;

    private Header header;

    private Response response;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public UserModel getData() {
        return data;
    }

    public void setData(UserModel data) {
        this.data = data;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ data +", response = "+ response +", header = "+header+"]";
    }
}
