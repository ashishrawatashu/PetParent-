package com.cynoteck.petofy.parameter.appointmentParams;

public class CreateAppointRequest {
    private CreateAppointParams data;

    public CreateAppointParams getData ()
    {
        return data;
    }

    public void setData (CreateAppointParams data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}
