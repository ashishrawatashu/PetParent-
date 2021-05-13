package com.cynoteck.petofyparents.response.appointmentResponse;

import com.cynoteck.petofyparents.response.Header;
import com.cynoteck.petofyparents.response.Response;


import java.util.ArrayList;

public class GetAppointmentResponse {
    private ArrayList<AppointmentList> data;

    private Response response;

    private Header header;

    public ArrayList<AppointmentList> getData ()
    {
        return data;
    }

    public void setData (ArrayList<AppointmentList> data)
    {
        this.data = data;
    }

    public Response getResponse ()
    {
        return response;
    }

    public void setResponse (Response response)
    {
        this.response = response;
    }

    public Header getHeader ()
    {
        return header;
    }

    public void setHeader (Header header)
    {
        this.header = header;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+", response = "+response+", header = "+header+"]";
    }
}

			
			