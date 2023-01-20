package com.cynoteck.petofy.parameter.punchingPolicyPetRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PunchingPolicyPetParams {

    @SerializedName("PetCategoryId")
    @Expose
    private String petCategoryId;
    @SerializedName("PetName")
    @Expose
    private String petName;
    @SerializedName("PetSexId")
    @Expose
    private String petSexId;
    @SerializedName("PetDateofBirth")
    @Expose
    private String petDateofBirth;
    @SerializedName("PetBreedId")
    @Expose
    private String petBreedId;
    @SerializedName("PetColorId")
    @Expose
    private String petColorId;
    @SerializedName("Plans")
    @Expose
    private String plans;
    @SerializedName("MicrochipNumber")
    @Expose
    private String microchipNumber;
    @SerializedName("Declaration")
    @Expose
    private String declaration;
    @SerializedName("RegistrationPet")
    @Expose
    private Boolean registrationPet;
    @SerializedName("Vaccinated")
    @Expose
    private Boolean vaccinated;
    @SerializedName("Castrated")
    @Expose
    private Boolean castrated;
    @SerializedName("CastratedReason")
    @Expose
    private String castratedReason;
    @SerializedName("Features")
    @Expose
    private String features;
    @SerializedName("PetId")
    @Expose
    private String PetId;

    public String getPetId() {
        return PetId;
    }

    public void setPetId(String petId) {
        PetId = petId;
    }

    public String getPetCategoryId() {
        return petCategoryId;
    }

    public void setPetCategoryId(String petCategoryId) {
        this.petCategoryId = petCategoryId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetSexId() {
        return petSexId;
    }

    public void setPetSexId(String petSexId) {
        this.petSexId = petSexId;
    }

    public String getPetDateofBirth() {
        return petDateofBirth;
    }

    public void setPetDateofBirth(String petDateofBirth) {
        this.petDateofBirth = petDateofBirth;
    }

    public String getPetBreedId() {
        return petBreedId;
    }

    public void setPetBreedId(String petBreedId) {
        this.petBreedId = petBreedId;
    }

    public String getPetColorId() {
        return petColorId;
    }

    public void setPetColorId(String petColorId) {
        this.petColorId = petColorId;
    }

    public String getPlans() {
        return plans;
    }

    public void setPlans(String plans) {
        this.plans = plans;
    }

    public String getMicrochipNumber() {
        return microchipNumber;
    }

    public void setMicrochipNumber(String microchipNumber) {
        this.microchipNumber = microchipNumber;
    }

    public String getDeclaration() {
        return declaration;
    }

    public void setDeclaration(String declaration) {
        this.declaration = declaration;
    }

    public Boolean getRegistrationPet() {
        return registrationPet;
    }

    public void setRegistrationPet(Boolean registrationPet) {
        this.registrationPet = registrationPet;
    }

    public Boolean getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(Boolean vaccinated) {
        this.vaccinated = vaccinated;
    }

    public Boolean getCastrated() {
        return castrated;
    }

    public void setCastrated(Boolean castrated) {
        this.castrated = castrated;
    }

    public String getCastratedReason() {
        return castratedReason;
    }

    public void setCastratedReason(String castratedReason) {
        this.castratedReason = castratedReason;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

}
