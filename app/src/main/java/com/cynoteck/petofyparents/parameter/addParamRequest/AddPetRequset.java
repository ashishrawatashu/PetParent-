package com.cynoteck.petofyparents.parameter.addParamRequest;

public class AddPetRequset {
    private AddPetParams data;

    public AddPetParams getAddPetParams() {
        return data;
    }

    public void setAddPetParams(AddPetParams data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ data +"]";
    }

}
