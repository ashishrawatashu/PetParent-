
package com.cynoteck.petofy.response.getSearchResultsResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Provider {

    @SerializedName("encryptedId")
    @Expose
    private String encryptedId;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("profileImageURL")
    @Expose
    private String profileImageURL;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("distance")
    @Expose
    private Double distance;
    @SerializedName("route")
    @Expose
    private String route;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("isExistingprovider")
    @Expose
    private Boolean isExistingprovider;
    @SerializedName("cityId")
    @Expose
    private String cityId;
    @SerializedName("vetQualifications")
    @Expose
    private String vetQualifications;
    @SerializedName("vetRegistrationNumber")
    @Expose
    private String vetRegistrationNumber;
    @SerializedName("enableOnlineAppointment")
    @Expose
    private Boolean enableOnlineAppointment;
    @SerializedName("onlineConsultationCharges")
    @Expose
    private String onlineConsultationCharges;
    @SerializedName("encryptedProviderId")
    @Expose
    private String encryptedProviderId;
    @SerializedName("hasLoginAccount")
    @Expose
    private Boolean hasLoginAccount;
    @SerializedName("operatingHourList")
    @Expose
    private String operatingHourList;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("isVeterinarian")
    @Expose
    private Boolean isVeterinarian;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("showAppointment")
    @Expose
    private Boolean showAppointment;
    @SerializedName("hasWorkingHours")
    @Expose
    private Boolean hasWorkingHours;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("hasOphrSubscription")
    @Expose
    private Boolean hasOphrSubscription;

    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfileImageURL() {
        return profileImageURL;
    }

    public void setProfileImageURL(String profileImageURL) {
        this.profileImageURL = profileImageURL;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Boolean getIsExistingprovider() {
        return isExistingprovider;
    }

    public void setIsExistingprovider(Boolean isExistingprovider) {
        this.isExistingprovider = isExistingprovider;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getVetQualifications() {
        return vetQualifications;
    }

    public void setVetQualifications(String vetQualifications) {
        this.vetQualifications = vetQualifications;
    }

    public String getVetRegistrationNumber() {
        return vetRegistrationNumber;
    }

    public void setVetRegistrationNumber(String vetRegistrationNumber) {
        this.vetRegistrationNumber = vetRegistrationNumber;
    }

    public Boolean getEnableOnlineAppointment() {
        return enableOnlineAppointment;
    }

    public void setEnableOnlineAppointment(Boolean enableOnlineAppointment) {
        this.enableOnlineAppointment = enableOnlineAppointment;
    }

    public String getOnlineConsultationCharges() {
        return onlineConsultationCharges;
    }

    public void setOnlineConsultationCharges(String onlineConsultationCharges) {
        this.onlineConsultationCharges = onlineConsultationCharges;
    }

    public String getEncryptedProviderId() {
        return encryptedProviderId;
    }

    public void setEncryptedProviderId(String encryptedProviderId) {
        this.encryptedProviderId = encryptedProviderId;
    }

    public Boolean getHasLoginAccount() {
        return hasLoginAccount;
    }

    public void setHasLoginAccount(Boolean hasLoginAccount) {
        this.hasLoginAccount = hasLoginAccount;
    }

    public String getOperatingHourList() {
        return operatingHourList;
    }

    public void setOperatingHourList(String operatingHourList) {
        this.operatingHourList = operatingHourList;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Boolean getIsVeterinarian() {
        return isVeterinarian;
    }

    public void setIsVeterinarian(Boolean isVeterinarian) {
        this.isVeterinarian = isVeterinarian;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getShowAppointment() {
        return showAppointment;
    }

    public void setShowAppointment(Boolean showAppointment) {
        this.showAppointment = showAppointment;
    }

    public Boolean getHasWorkingHours() {
        return hasWorkingHours;
    }

    public void setHasWorkingHours(Boolean hasWorkingHours) {
        this.hasWorkingHours = hasWorkingHours;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Boolean getHasOphrSubscription() {
        return hasOphrSubscription;
    }

    public void setHasOphrSubscription(Boolean hasOphrSubscription) {
        this.hasOphrSubscription = hasOphrSubscription;
    }

}
