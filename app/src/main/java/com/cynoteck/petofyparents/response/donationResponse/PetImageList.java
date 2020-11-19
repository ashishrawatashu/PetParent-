package com.cynoteck.petofyparents.response.donationResponse;

public class PetImageList {
    private Double id;
    private Double petId;
    private String petImageUrl;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public Double getPetId() {
        return petId;
    }

    public void setPetId(Double petId) {
        this.petId = petId;
    }

    public String getPetImageUrl() {
        return petImageUrl;
    }

    public void setPetImageUrl(String petImageUrl) {
        this.petImageUrl = petImageUrl;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "id=" + id +
                ", petId=" + petId +
                ", petImageUrl='" + petImageUrl +
                "]";
    }
}
