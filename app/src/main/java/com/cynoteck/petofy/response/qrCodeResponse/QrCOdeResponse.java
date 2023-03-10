package com.cynoteck.petofy.response.qrCodeResponse;

public class QrCOdeResponse {
    private String VeterinarianUserId;

    private String VeterinarianName;

    private String ClinicName;

    private String ProfileImageUrl;

    private String Key;

    private String IsInsurance;

    private String InsuranceUrl;


    private int Rating;


    public String getInsurance() {
        return IsInsurance;
    }

    public void setInsurance(String insurance) {
        IsInsurance = insurance;
    }

    public String getInsuranceUrl() {
        return InsuranceUrl;
    }

    public void setInsuranceUrl(String insuranceUrl) {
        InsuranceUrl = insuranceUrl;
    }

    public String getVeterinarianName() {
        return VeterinarianName;
    }

    public void setVeterinarianName(String veterinarianName) {
        VeterinarianName = veterinarianName;
    }

    public String getClinicName() {
        return ClinicName;
    }

    public void setClinicName(String clinicName) {
        ClinicName = clinicName;
    }

    public String getProfileImageUrl() {
        return ProfileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        ProfileImageUrl = profileImageUrl;
    }

    public int getRating() {
        return Rating;
    }

    public void setRating(int rating) {
        Rating = rating;
    }

    public String getVeterinarianUserId ()
    {
        return VeterinarianUserId;
    }

    public void setVeterinarianUserId (String VeterinarianUserId)
    {
        this.VeterinarianUserId = VeterinarianUserId;
    }

    public String getKey ()
    {
        return Key;
    }

    public void setKey (String Key)
    {
        this.Key = Key;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [VeterinarianUserId = "+VeterinarianUserId+", Key = "+Key+"]";
    }
}
