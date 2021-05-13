package com.cynoteck.petofyparents.parameter.getPetParentAppointmentsParams;

import com.cynoteck.petofyparents.parameter.getPetListRequest.GetPetListParams;

public class GetAppointmentRequest {
    private GetAppointmentsParams data;

    public GetAppointmentsParams getData() {
        return data;
    }

    public void setData(GetAppointmentsParams data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }

}
