package com.cynoteck.petofyparents.parameter.getPetParentAppointmentsParams;

public class GetAppointmentsParams {
    private String appointmentType;

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String id) {
        this.appointmentType = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [appointmentType = "+appointmentType+"]";
    }

}
