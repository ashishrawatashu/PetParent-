package com.cynoteck.petofy.parameter.updateRequest.getValue;

public class UpdateParams {
    private UpdateRequest data;

    public UpdateRequest getUpdateRequest() {
        return data;
    }

    public void setUpdateRequest(UpdateRequest data) {
        this.data = data;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [data = "+ data +"]";
    }
}
