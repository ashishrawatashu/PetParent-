package com.cynoteck.petofy.parameter.immunizationRequest;

public class ImmunizationRequest {
    private ImmunizationParams data;

    public ImmunizationParams getData ()
    {
        return data;
    }

    public void setData (ImmunizationParams data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}
