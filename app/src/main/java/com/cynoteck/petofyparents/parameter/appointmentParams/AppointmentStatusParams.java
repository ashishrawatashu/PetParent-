package com.cynoteck.petofyparents.parameter.appointmentParams;

public class AppointmentStatusParams {
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

