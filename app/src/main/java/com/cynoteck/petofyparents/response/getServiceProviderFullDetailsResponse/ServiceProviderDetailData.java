
package com.cynoteck.petofyparents.response.getServiceProviderFullDetailsResponse;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceProviderDetailData {

    @SerializedName("encryptedId")
    @Expose
    private String encryptedId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("firstName")
    @Expose
    private Object firstName;
    @SerializedName("lastName")
    @Expose
    private Object lastName;
    @SerializedName("company")
    @Expose
    private String company;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("phone2")
    @Expose
    private Object phone2;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("providerEmail")
    @Expose
    private Object providerEmail;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("website")
    @Expose
    private Object website;
    @SerializedName("socialMediaUrl")
    @Expose
    private Object socialMediaUrl;
    @SerializedName("providerType")
    @Expose
    private Integer providerType;
    @SerializedName("cityId")
    @Expose
    private Double cityId;
    @SerializedName("stateId")
    @Expose
    private Double stateId;
    @SerializedName("countryId")
    @Expose
    private Double countryId;
    @SerializedName("postalCode")
    @Expose
    private String postalCode;
    @SerializedName("selectedPetTypeIds")
    @Expose
    private Object selectedPetTypeIds;
    @SerializedName("selectedServiceTypeIds")
    @Expose
    private Object selectedServiceTypeIds;
    @SerializedName("selectedCountryId")
    @Expose
    private Object selectedCountryId;
    @SerializedName("hasWorkingHours")
    @Expose
    private Boolean hasWorkingHours;
    @SerializedName("profileImageUrl")
    @Expose
    private String profileImageUrl;
    @SerializedName("serviceImageUrl")
    @Expose
    private Object serviceImageUrl;
    @SerializedName("serviceImages")
    @Expose
    private Object serviceImages;
    @SerializedName("firstServiceImageUrl")
    @Expose
    private Object firstServiceImageUrl;
    @SerializedName("secondServiceImageUrl")
    @Expose
    private Object secondServiceImageUrl;
    @SerializedName("thirdServiceImageUrl")
    @Expose
    private Object thirdServiceImageUrl;
    @SerializedName("fourthServiceImageUrl")
    @Expose
    private Object fourthServiceImageUrl;
    @SerializedName("fifthServiceImageUrl")
    @Expose
    private Object fifthServiceImageUrl;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("distance")
    @Expose
    private String distance;
    @SerializedName("services")
    @Expose
    private List<String> services = null;
    @SerializedName("categories")
    @Expose
    private List<String> categories = null;
    @SerializedName("imageUrl")
    @Expose
    private Object imageUrl;
    @SerializedName("route")
    @Expose
    private String route;
    @SerializedName("rating")
    @Expose
    private Integer rating;
    @SerializedName("userRating")
    @Expose
    private Integer userRating;
    @SerializedName("origin")
    @Expose
    private Object origin;
    @SerializedName("destination")
    @Expose
    private Object destination;
    @SerializedName("coverImageUrl")
    @Expose
    private String coverImageUrl;
    @SerializedName("isAllowPrescriptionAccess")
    @Expose
    private Boolean isAllowPrescriptionAccess;
    @SerializedName("hasOphrSubscription")
    @Expose
    private Boolean hasOphrSubscription;
    @SerializedName("isNGO")
    @Expose
    private Boolean isNGO;
    @SerializedName("hasLogIn")
    @Expose
    private Boolean hasLogIn;
    @SerializedName("isVeterinarian")
    @Expose
    private Boolean isVeterinarian;
    @SerializedName("isVetRegistration")
    @Expose
    private Boolean isVetRegistration;
    @SerializedName("isExistingProvider")
    @Expose
    private Boolean isExistingProvider;
    @SerializedName("isMobileNumberVerified")
    @Expose
    private Boolean isMobileNumberVerified;
    @SerializedName("isEmailVerified")
    @Expose
    private Boolean isEmailVerified;
    @SerializedName("enableOnlineAppointments")
    @Expose
    private Boolean enableOnlineAppointments;
    @SerializedName("registrationDate")
    @Expose
    private Object registrationDate;
    @SerializedName("clinicCode")
    @Expose
    private Object clinicCode;
    @SerializedName("enableTwoStepVerification")
    @Expose
    private Boolean enableTwoStepVerification;
    @SerializedName("providerUserId")
    @Expose
    private String providerUserId;
    @SerializedName("password")
    @Expose
    private Object password;
    @SerializedName("confirmPassword")
    @Expose
    private Object confirmPassword;
    @SerializedName("userEmail")
    @Expose
    private Object userEmail;
    @SerializedName("hasLogInAccount")
    @Expose
    private Boolean hasLogInAccount;
    @SerializedName("vetQualifications")
    @Expose
    private String vetQualifications;
    @SerializedName("vetRegistrationNumber")
    @Expose
    private String vetRegistrationNumber;
    @SerializedName("referredById")
    @Expose
    private Object referredById;
    @SerializedName("referredBy")
    @Expose
    private Object referredBy;
    @SerializedName("onlineConsultationCharges")
    @Expose
    private Object onlineConsultationCharges;
    @SerializedName("cityName")
    @Expose
    private Object cityName;
    @SerializedName("stateName")
    @Expose
    private Object stateName;
    @SerializedName("countryName")
    @Expose
    private Object countryName;
    @SerializedName("sharePrescriptionUrl")
    @Expose
    private Boolean sharePrescriptionUrl;
    @SerializedName("cityList")
    @Expose
    private Object cityList;
    @SerializedName("stateList")
    @Expose
    private Object stateList;
    @SerializedName("countryList")
    @Expose
    private Object countryList;
    @SerializedName("city")
    @Expose
    private ServiceProviderDetailCity city;
    @SerializedName("providerImageList")
    @Expose
    private List<Object> providerImageList = null;
    @SerializedName("providerLocationList")
    @Expose
    private List<Object> providerLocationList = null;
    @SerializedName("providerPetTypeList")
    @Expose
    private List<Object> providerPetTypeList = null;
    @SerializedName("providerRatingList")
    @Expose
    private ArrayList<ProviderRatingList> providerRatingList = null;
    @SerializedName("providerServiceTypeList")
    @Expose
    private List<Object> providerServiceTypeList = null;
    @SerializedName("operatingHourList")
    @Expose
    private List<ServiceProviderDetailOperatingHour> operatingHourList = null;
    @SerializedName("serviceTypeList")
    @Expose
    private List<Object> serviceTypeList = null;
    @SerializedName("petTypeList")
    @Expose
    private List<Object> petTypeList = null;
    @SerializedName("bankList")
    @Expose
    private List<Object> bankList = null;
    @SerializedName("subscription")
    @Expose
    private List<Object> subscription = null;

    public String getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(String encryptedId) {
        this.encryptedId = encryptedId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getPhone2() {
        return phone2;
    }

    public void setPhone2(Object phone2) {
        this.phone2 = phone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Object getProviderEmail() {
        return providerEmail;
    }

    public void setProviderEmail(Object providerEmail) {
        this.providerEmail = providerEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Object getWebsite() {
        return website;
    }

    public void setWebsite(Object website) {
        this.website = website;
    }

    public Object getSocialMediaUrl() {
        return socialMediaUrl;
    }

    public void setSocialMediaUrl(Object socialMediaUrl) {
        this.socialMediaUrl = socialMediaUrl;
    }

    public Integer getProviderType() {
        return providerType;
    }

    public void setProviderType(Integer providerType) {
        this.providerType = providerType;
    }

    public Double getCityId() {
        return cityId;
    }

    public void setCityId(Double cityId) {
        this.cityId = cityId;
    }

    public Double getStateId() {
        return stateId;
    }

    public void setStateId(Double stateId) {
        this.stateId = stateId;
    }

    public Double getCountryId() {
        return countryId;
    }

    public void setCountryId(Double countryId) {
        this.countryId = countryId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Object getSelectedPetTypeIds() {
        return selectedPetTypeIds;
    }

    public void setSelectedPetTypeIds(Object selectedPetTypeIds) {
        this.selectedPetTypeIds = selectedPetTypeIds;
    }

    public Object getSelectedServiceTypeIds() {
        return selectedServiceTypeIds;
    }

    public void setSelectedServiceTypeIds(Object selectedServiceTypeIds) {
        this.selectedServiceTypeIds = selectedServiceTypeIds;
    }

    public Object getSelectedCountryId() {
        return selectedCountryId;
    }

    public void setSelectedCountryId(Object selectedCountryId) {
        this.selectedCountryId = selectedCountryId;
    }

    public Boolean getHasWorkingHours() {
        return hasWorkingHours;
    }

    public void setHasWorkingHours(Boolean hasWorkingHours) {
        this.hasWorkingHours = hasWorkingHours;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public Object getServiceImageUrl() {
        return serviceImageUrl;
    }

    public void setServiceImageUrl(Object serviceImageUrl) {
        this.serviceImageUrl = serviceImageUrl;
    }

    public Object getServiceImages() {
        return serviceImages;
    }

    public void setServiceImages(Object serviceImages) {
        this.serviceImages = serviceImages;
    }

    public Object getFirstServiceImageUrl() {
        return firstServiceImageUrl;
    }

    public void setFirstServiceImageUrl(Object firstServiceImageUrl) {
        this.firstServiceImageUrl = firstServiceImageUrl;
    }

    public Object getSecondServiceImageUrl() {
        return secondServiceImageUrl;
    }

    public void setSecondServiceImageUrl(Object secondServiceImageUrl) {
        this.secondServiceImageUrl = secondServiceImageUrl;
    }

    public Object getThirdServiceImageUrl() {
        return thirdServiceImageUrl;
    }

    public void setThirdServiceImageUrl(Object thirdServiceImageUrl) {
        this.thirdServiceImageUrl = thirdServiceImageUrl;
    }

    public Object getFourthServiceImageUrl() {
        return fourthServiceImageUrl;
    }

    public void setFourthServiceImageUrl(Object fourthServiceImageUrl) {
        this.fourthServiceImageUrl = fourthServiceImageUrl;
    }

    public Object getFifthServiceImageUrl() {
        return fifthServiceImageUrl;
    }

    public void setFifthServiceImageUrl(Object fifthServiceImageUrl) {
        this.fifthServiceImageUrl = fifthServiceImageUrl;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        this.services = services;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public Object getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getUserRating() {
        return userRating;
    }

    public void setUserRating(Integer userRating) {
        this.userRating = userRating;
    }

    public Object getOrigin() {
        return origin;
    }

    public void setOrigin(Object origin) {
        this.origin = origin;
    }

    public Object getDestination() {
        return destination;
    }

    public void setDestination(Object destination) {
        this.destination = destination;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public Boolean getIsAllowPrescriptionAccess() {
        return isAllowPrescriptionAccess;
    }

    public void setIsAllowPrescriptionAccess(Boolean isAllowPrescriptionAccess) {
        this.isAllowPrescriptionAccess = isAllowPrescriptionAccess;
    }

    public Boolean getHasOphrSubscription() {
        return hasOphrSubscription;
    }

    public void setHasOphrSubscription(Boolean hasOphrSubscription) {
        this.hasOphrSubscription = hasOphrSubscription;
    }

    public Boolean getIsNGO() {
        return isNGO;
    }

    public void setIsNGO(Boolean isNGO) {
        this.isNGO = isNGO;
    }

    public Boolean getHasLogIn() {
        return hasLogIn;
    }

    public void setHasLogIn(Boolean hasLogIn) {
        this.hasLogIn = hasLogIn;
    }

    public Boolean getIsVeterinarian() {
        return isVeterinarian;
    }

    public void setIsVeterinarian(Boolean isVeterinarian) {
        this.isVeterinarian = isVeterinarian;
    }

    public Boolean getIsVetRegistration() {
        return isVetRegistration;
    }

    public void setIsVetRegistration(Boolean isVetRegistration) {
        this.isVetRegistration = isVetRegistration;
    }

    public Boolean getIsExistingProvider() {
        return isExistingProvider;
    }

    public void setIsExistingProvider(Boolean isExistingProvider) {
        this.isExistingProvider = isExistingProvider;
    }

    public Boolean getIsMobileNumberVerified() {
        return isMobileNumberVerified;
    }

    public void setIsMobileNumberVerified(Boolean isMobileNumberVerified) {
        this.isMobileNumberVerified = isMobileNumberVerified;
    }

    public Boolean getIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(Boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public Boolean getEnableOnlineAppointments() {
        return enableOnlineAppointments;
    }

    public void setEnableOnlineAppointments(Boolean enableOnlineAppointments) {
        this.enableOnlineAppointments = enableOnlineAppointments;
    }

    public Object getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Object registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Object getClinicCode() {
        return clinicCode;
    }

    public void setClinicCode(Object clinicCode) {
        this.clinicCode = clinicCode;
    }

    public Boolean getEnableTwoStepVerification() {
        return enableTwoStepVerification;
    }

    public void setEnableTwoStepVerification(Boolean enableTwoStepVerification) {
        this.enableTwoStepVerification = enableTwoStepVerification;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(Object confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Object getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(Object userEmail) {
        this.userEmail = userEmail;
    }

    public Boolean getHasLogInAccount() {
        return hasLogInAccount;
    }

    public void setHasLogInAccount(Boolean hasLogInAccount) {
        this.hasLogInAccount = hasLogInAccount;
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

    public Object getReferredById() {
        return referredById;
    }

    public void setReferredById(Object referredById) {
        this.referredById = referredById;
    }

    public Object getReferredBy() {
        return referredBy;
    }

    public void setReferredBy(Object referredBy) {
        this.referredBy = referredBy;
    }

    public Object getOnlineConsultationCharges() {
        return onlineConsultationCharges;
    }

    public void setOnlineConsultationCharges(Object onlineConsultationCharges) {
        this.onlineConsultationCharges = onlineConsultationCharges;
    }

    public Object getCityName() {
        return cityName;
    }

    public void setCityName(Object cityName) {
        this.cityName = cityName;
    }

    public Object getStateName() {
        return stateName;
    }

    public void setStateName(Object stateName) {
        this.stateName = stateName;
    }

    public Object getCountryName() {
        return countryName;
    }

    public void setCountryName(Object countryName) {
        this.countryName = countryName;
    }

    public Boolean getSharePrescriptionUrl() {
        return sharePrescriptionUrl;
    }

    public void setSharePrescriptionUrl(Boolean sharePrescriptionUrl) {
        this.sharePrescriptionUrl = sharePrescriptionUrl;
    }

    public Object getCityList() {
        return cityList;
    }

    public void setCityList(Object cityList) {
        this.cityList = cityList;
    }

    public Object getStateList() {
        return stateList;
    }

    public void setStateList(Object stateList) {
        this.stateList = stateList;
    }

    public Object getCountryList() {
        return countryList;
    }

    public void setCountryList(Object countryList) {
        this.countryList = countryList;
    }

    public ServiceProviderDetailCity getCity() {
        return city;
    }

    public void setCity(ServiceProviderDetailCity city) {
        this.city = city;
    }

    public List<Object> getProviderImageList() {
        return providerImageList;
    }

    public void setProviderImageList(List<Object> providerImageList) {
        this.providerImageList = providerImageList;
    }

    public List<Object> getProviderLocationList() {
        return providerLocationList;
    }

    public void setProviderLocationList(List<Object> providerLocationList) {
        this.providerLocationList = providerLocationList;
    }

    public List<Object> getProviderPetTypeList() {
        return providerPetTypeList;
    }

    public void setProviderPetTypeList(List<Object> providerPetTypeList) {
        this.providerPetTypeList = providerPetTypeList;
    }

    public ArrayList<ProviderRatingList> getProviderRatingList() {
        return providerRatingList;
    }

    public void setProviderRatingList(ArrayList<ProviderRatingList> providerRatingList) {
        this.providerRatingList = providerRatingList;
    }

    public List<Object> getProviderServiceTypeList() {
        return providerServiceTypeList;
    }

    public void setProviderServiceTypeList(List<Object> providerServiceTypeList) {
        this.providerServiceTypeList = providerServiceTypeList;
    }

    public List<ServiceProviderDetailOperatingHour> getOperatingHourList() {
        return operatingHourList;
    }

    public void setOperatingHourList(List<ServiceProviderDetailOperatingHour> operatingHourList) {
        this.operatingHourList = operatingHourList;
    }

    public List<Object> getServiceTypeList() {
        return serviceTypeList;
    }

    public void setServiceTypeList(List<Object> serviceTypeList) {
        this.serviceTypeList = serviceTypeList;
    }

    public List<Object> getPetTypeList() {
        return petTypeList;
    }

    public void setPetTypeList(List<Object> petTypeList) {
        this.petTypeList = petTypeList;
    }

    public List<Object> getBankList() {
        return bankList;
    }

    public void setBankList(List<Object> bankList) {
        this.bankList = bankList;
    }

    public List<Object> getSubscription() {
        return subscription;
    }

    public void setSubscription(List<Object> subscription) {
        this.subscription = subscription;
    }

}
