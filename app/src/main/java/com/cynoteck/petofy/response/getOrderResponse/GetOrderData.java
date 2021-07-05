package com.cynoteck.petofy.response.getOrderResponse;

public class GetOrderData {
    private Attributes attributes;

    public Attributes getAttributes ()
    {
        return attributes;
    }

    public void setAttributes (Attributes attributes)
    {
        this.attributes = attributes;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [attributes = "+attributes+"]";
    }
}

