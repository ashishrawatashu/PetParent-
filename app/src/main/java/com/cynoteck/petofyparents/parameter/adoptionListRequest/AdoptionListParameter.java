package com.cynoteck.petofyparents.parameter.adoptionListRequest;

public class AdoptionListParameter {
    
    private String petCategoryId;
    private String petSexId;
    private String petAgeId;
    private String petSizeId;
    private String petColorId;
    private String petBreedId;

    public String getPetCategoryId() {
        return petCategoryId;
    }

    public void setPetCategoryId(String petCategoryId) {
        this.petCategoryId = petCategoryId;
    }

    public String getPetSexId() {
        return petSexId;
    }

    public void setPetSexId(String petSexId) {
        this.petSexId = petSexId;
    }

    public String getPetAgeId() {
        return petAgeId;
    }

    public void setPetAgeId(String petAgeId) {
        this.petAgeId = petAgeId;
    }

    public String getPetSizeId() {
        return petSizeId;
    }

    public void setPetSizeId(String petSizeId) {
        this.petSizeId = petSizeId;
    }

    public String getPetColorId() {
        return petColorId;
    }

    public void setPetColorId(String petColorId) {
        this.petColorId = petColorId;
    }

    public String getPetBreedId() {
        return petBreedId;
    }

    public void setPetBreedId(String petBreedId) {
        this.petBreedId = petBreedId;
    }


    @Override
    public String toString() {
        return "ClassPojo[" +
                "petCategoryId=" + petCategoryId + 
                ", petSexId=" + petSexId + 
                ", petAgeId=" + petAgeId + 
                ", petSizeId=" + petSizeId + 
                ", petColorId=" + petColorId + 
                ", petBreedId=" + petBreedId + 
                "]";
    }
}
