package com.cynoteck.petofy.parameter.petReportsRequest;

public class PetClinicVistsDetailsParams {
    private String id;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+"]";
    }
}