package com.cynoteck.petofy.parameter.getServiceProvidersListRequest;

public class GetServiceProviderListRequest {
    private GetServiceProviderListParams data;

    public GetServiceProviderListParams getData ()
    {
        return data;
    }

    public void setData (GetServiceProviderListParams data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}

