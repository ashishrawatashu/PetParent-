package com.cynoteck.petofyparents.parameter.immunizationRequest;

public class ImmunizationRequestt {

    private ImmunizationParameter data;
    public ImmunizationParameter getImmunizationData()
    {
        return data;
    }

    public void setImmunizationData(ImmunizationParameter data)
    {
        this.data = data;
    }
    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ data +"]";
    }
}
