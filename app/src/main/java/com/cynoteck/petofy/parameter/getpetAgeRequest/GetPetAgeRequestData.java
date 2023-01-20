package com.cynoteck.petofy.parameter.getpetAgeRequest;

public class GetPetAgeRequestData {
    private GetPetAgeParameter data;

    public GetPetAgeParameter getData() {
        return data;
    }

    public void setData(GetPetAgeParameter data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ClassPojo{" +
                "data=" + data +
                "]";
    }
}
