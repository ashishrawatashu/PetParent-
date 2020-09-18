package com.cynoteck.petofyparents.parameter.petReportsRequest;

public class PetDataRequest {
    private PetDataParams getPetDataParams;

    public PetDataParams getData ()
    {
        return getPetDataParams;
    }

    public void setData (PetDataParams data)
    {
        this.getPetDataParams = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+getPetDataParams+"]";
    }

}