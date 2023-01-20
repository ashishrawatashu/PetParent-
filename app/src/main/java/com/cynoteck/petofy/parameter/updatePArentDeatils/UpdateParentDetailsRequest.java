package com.cynoteck.petofy.parameter.updatePArentDeatils;

public class UpdateParentDetailsRequest {
    private UpdateParentDeatilsParams data;

    public UpdateParentDeatilsParams getData ()
    {
        return data;
    }

    public void setData (UpdateParentDeatilsParams data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}


