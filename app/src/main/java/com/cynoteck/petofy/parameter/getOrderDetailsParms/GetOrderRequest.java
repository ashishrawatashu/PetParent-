package com.cynoteck.petofy.parameter.getOrderDetailsParms;

public class GetOrderRequest {
    private GetOrderParams data;

    public GetOrderParams getData ()
    {
        return data;
    }

    public void setData (GetOrderParams data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}
