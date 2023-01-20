package com.cynoteck.petofy.parameter.getOrderDetailsParms;

public class GetOrderParams {
    private String veterinarianUserId;

    public String getVeterinarianId ()
    {
        return veterinarianUserId;
    }

    public void setVeterinarianId (String veterinarianId)
    {
        this.veterinarianUserId = veterinarianId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [veterinarianId = "+veterinarianUserId+"]";
    }
}
