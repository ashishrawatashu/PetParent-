package com.cynoteck.petofy.parameter.getServiceProvidersListRequest;

public class GetServiceProviderListRequest {
    private GetServiceProviderListParams data;
    private GetServiceProviderListHeader header;

    public GetServiceProviderListHeader getHeader() {
        return header;
    }

    public void setHeader(GetServiceProviderListHeader header) {
        this.header = header;
    }

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

