package com.cynoteck.petofyparents.parameter.appointmentParams;

public class UpdateAppointmentRequest {
    private UpdateAppointmentParams data;

    public UpdateAppointmentParams getData ()
    {
        return data;
    }

    public void setData (UpdateAppointmentParams data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}
