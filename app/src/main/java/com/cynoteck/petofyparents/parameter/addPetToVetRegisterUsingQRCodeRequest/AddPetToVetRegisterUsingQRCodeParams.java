package com.cynoteck.petofyparents.parameter.addPetToVetRegisterUsingQRCodeRequest;

public class AddPetToVetRegisterUsingQRCodeParams {
    private String veterinarianUserId;

    private String petUniqueId;

    public String getVeterinarianUserId ()
    {
        return veterinarianUserId;
    }

    public void setVeterinarianUserId (String veterinarianUserId)
    {
        this.veterinarianUserId = veterinarianUserId;
    }

    public String getPetUniqueId ()
    {
        return petUniqueId;
    }

    public void setPetUniqueId (String petUniqueId)
    {
        this.petUniqueId = petUniqueId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [veterinarianUserId = "+veterinarianUserId+", petUniqueId = "+petUniqueId+"]";
    }
}

