package com.cynoteck.petofy.parameter.adoptionRequest;

public class AdoptionRequestParameter {
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "id=" + id +
                "]";
    }
}
