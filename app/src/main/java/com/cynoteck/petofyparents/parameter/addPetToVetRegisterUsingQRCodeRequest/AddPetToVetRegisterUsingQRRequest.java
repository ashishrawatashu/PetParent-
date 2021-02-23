package com.cynoteck.petofyparents.parameter.addPetToVetRegisterUsingQRCodeRequest;

public class AddPetToVetRegisterUsingQRRequest {
    private AddPetToVetRegisterUsingQRCodeParams data;

    public AddPetToVetRegisterUsingQRCodeParams getData ()
    {
        return data;
    }

    public void setData (AddPetToVetRegisterUsingQRCodeParams data)
    {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+data+"]";
    }
}

