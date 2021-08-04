package com.cynoteck.petofy.parameter.punchingPolicyRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PunchingPolicyParams {
    @SerializedName("FirstName")
    @Expose
    private String firstName;
    @SerializedName("LastName")
    @Expose
    private String lastName;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("MobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("OwnerDob")
    @Expose
    private String ownerDob;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("StateId")
    @Expose
    private String stateId;
    @SerializedName("CityId")
    @Expose
    private String cityId;
    @SerializedName("PinCode")
    @Expose
    private String pinCode;
    @SerializedName("ReferralCode")
    @Expose
    private String referralCode;
    @SerializedName("EncUserId")
    @Expose
    private String encUserId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getOwnerDob() {
        return ownerDob;
    }

    public void setOwnerDob(String ownerDob) {
        this.ownerDob = ownerDob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getReferralCode() {
        return referralCode;
    }

    public void setReferralCode(String referralCode) {
        this.referralCode = referralCode;
    }

    public String getEncUserId() {
        return encUserId;
    }

    public void setEncUserId(String encUserId) {
        this.encUserId = encUserId;
    }
}
