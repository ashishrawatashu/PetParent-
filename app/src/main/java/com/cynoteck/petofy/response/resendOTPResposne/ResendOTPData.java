package com.cynoteck.petofy.response.resendOTPResposne;

public class ResendOTPData {
    private String petId;

    private String success;

    private String otp;

    private String petParentContactNumber;

    public String getPetId ()
    {
        return petId;
    }

    public void setPetId (String petId)
    {
        this.petId = petId;
    }

    public String getSuccess ()
    {
        return success;
    }

    public void setSuccess (String success)
    {
        this.success = success;
    }

    public String getOtp ()
    {
        return otp;
    }

    public void setOtp (String otp)
    {
        this.otp = otp;
    }

    public String getPetParentContactNumber ()
    {
        return petParentContactNumber;
    }

    public void setPetParentContactNumber (String petParentContactNumber)
    {
        this.petParentContactNumber = petParentContactNumber;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [petId = "+petId+", success = "+success+", otp = "+otp+", petParentContactNumber = "+petParentContactNumber+"]";
    }
}
			
		
