
package com.cynoteck.petofy.response.insuranceAfterLoginResponses.getAllDetailsAfterLogin;

import java.util.List;

import com.cynoteck.petofy.response.getPetDetailsResponse.PetAgeList;
import com.cynoteck.petofy.response.getPetDetailsResponse.PetBreedList;
import com.cynoteck.petofy.response.getPetDetailsResponse.PetColorList;
import com.cynoteck.petofy.response.getPetDetailsResponse.PetSexList;
import com.cynoteck.petofy.response.getPetDetailsResponse.PetSizeList;
import com.cynoteck.petofy.response.getPetDetailsResponse.PetTypeList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllDetailAfterLoginData {

    @SerializedName("id")
    @Expose
    private Double id;
    @SerializedName("encUserId")
    @Expose
    private String encUserId;
    @SerializedName("encUserId2")
    @Expose
    private String encUserId2;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("petId")
    @Expose
    private String petId;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("petIdForUpdate")
    @Expose
    private String petIdForUpdate;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("email2")
    @Expose
    private String email2;
    @SerializedName("mobileNumber")
    @Expose
    private String mobileNumber;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("phoneNumber2")
    @Expose
    private String phoneNumber2;
    @SerializedName("ownerDob")
    @Expose
    private String ownerDob;
    @SerializedName("registrationPet")
    @Expose
    private Boolean registrationPet;
    @SerializedName("vaccinated")
    @Expose
    private Boolean vaccinated;
    @SerializedName("castrated")
    @Expose
    private Boolean castrated;
    @SerializedName("castratedReason")
    @Expose
    private String castratedReason;
    @SerializedName("plans")
    @Expose
    private String plans;
    @SerializedName("planType")
    @Expose
    private String planType;
    @SerializedName("microchipNumber")
    @Expose
    private String microchipNumber;
    @SerializedName("declaration")
    @Expose
    private String declaration;
    @SerializedName("features")
    @Expose
    private String features;
    @SerializedName("documents")
    @Expose
    private String documents;
    @SerializedName("premium")
    @Expose
    private String premium;
    @SerializedName("petImageUrl")
    @Expose
    private String petImageUrl;
    @SerializedName("petUniqueId")
    @Expose
    private String petUniqueId;
    @SerializedName("isNextStep")
    @Expose
    private Boolean isNextStep;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("homeNo")
    @Expose
    private String homeNo;
    @SerializedName("stepper")
    @Expose
    private String stepper;
    @SerializedName("cityId")
    @Expose
    private String cityId;
    @SerializedName("stateId")
    @Expose
    private String stateId;
    @SerializedName("pinCode")
    @Expose
    private String pinCode;
    @SerializedName("referralCode")
    @Expose
    private String referralCode;
    @SerializedName("purchaseProofUrl")
    @Expose
    private String purchaseProofUrl;
    @SerializedName("pedigreeCertificateUrl")
    @Expose
    private String pedigreeCertificateUrl;
    @SerializedName("vaccinationUrl")
    @Expose
    private String vaccinationUrl;
    @SerializedName("petImageUrl2")
    @Expose
    private String petImageUrl2;
    @SerializedName("petImageUrl3")
    @Expose
    private String petImageUrl3;
    @SerializedName("petImageUrl4")
    @Expose
    private String petImageUrl4;
    @SerializedName("petImageUrl5")
    @Expose
    private String petImageUrl5;
    @SerializedName("petImageUrl6")
    @Expose
    private String petImageUrl6;
    @SerializedName("petCategoryId")
    @Expose
    private String petCategoryId;
    @SerializedName("petSexId")
    @Expose
    private Double petSexId;
    @SerializedName("pedigreeLineage")
    @Expose
    private Boolean pedigreeLineage;
    @SerializedName("petAgeId")
    @Expose
    private Integer petAgeId;
    @SerializedName("petAgeNumber")
    @Expose
    private Double petAgeNumber;
    @SerializedName("petSizeId")
    @Expose
    private Double petSizeId;
    @SerializedName("petColorId")
    @Expose
    private Double petColorId;
    @SerializedName("petBreedId")
    @Expose
    private Double petBreedId;
    @SerializedName("petBreedId2")
    @Expose
    private Integer petBreedId2;
    @SerializedName("petName")
    @Expose
    private String petName;
    @SerializedName("petDateofBirth")
    @Expose
    private String petDateofBirth;
    @SerializedName("petGender")
    @Expose
    private String petGender;
    @SerializedName("petColor")
    @Expose
    private String petColor;
    @SerializedName("pageNavStatus")
    @Expose
    private Boolean pageNavStatus;
    @SerializedName("petAgeUnitList")
    @Expose
    private List<PetAgeUnit> petAgeUnitList = null;
    @SerializedName("insurancePlanList")
    @Expose
    private List<InsurancePlan> insurancePlanList = null;
    @SerializedName("diseasesList")
    @Expose
    private List<Diseases> diseasesList = null;
    @SerializedName("breedCategory")
    @Expose
    private String breedCategory;
    @SerializedName("petTypeList")
    @Expose
    private List<PetTypeList> petTypeList;
    @SerializedName("petSexList")
    @Expose
    private List<PetSexList> petSexList;
    @SerializedName("petAgeList")
    @Expose
    private List<PetAgeList> petAgeList;
    @SerializedName("petSizeList")
    @Expose
    private List<PetSizeList> petSizeList;
    @SerializedName("petColorList")
    @Expose
    private List<PetColorList> petColorList;
    @SerializedName("petBreedList")
    @Expose
    private List<PetBreedList> petBreedList;
    @SerializedName("cityList")
    @Expose
    private String cityList;
    @SerializedName("stateList")
    @Expose
    private String cityName;
    @SerializedName("cityName")
    @Expose
    private String stateList;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("petBreedName")
    @Expose
    private String petBreedName;

    @SerializedName("petColorName")
    @Expose
    private String petColorName;

    public String getPetColorName() {
        return petColorName;
    }

    public void setPetColorName(String petColorName) {
        this.petColorName = petColorName;
    }

    public String getPetBreedName() {
        return petBreedName;
    }

    public void setPetBreedName(String petBreedName) {
        this.petBreedName = petBreedName;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getEncUserId() {
        return encUserId;
    }

    public void setEncUserId(String encUserId) {
        this.encUserId = encUserId;
    }

    public String getEncUserId2() {
        return encUserId2;
    }

    public void setEncUserId2(String encUserId2) {
        this.encUserId2 = encUserId2;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPetId() {
        return petId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getPetIdForUpdate() {
        return petIdForUpdate;
    }

    public void setPetIdForUpdate(String petIdForUpdate) {
        this.petIdForUpdate = petIdForUpdate;
    }

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

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber2() {
        return phoneNumber2;
    }

    public void setPhoneNumber2(String phoneNumber2) {
        this.phoneNumber2 = phoneNumber2;
    }

    public String getOwnerDob() {
        return ownerDob;
    }

    public void setOwnerDob(String ownerDob) {
        this.ownerDob = ownerDob;
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

    public String getPlans() {
        return plans;
    }

    public void setPlans(String plans) {
        this.plans = plans;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
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

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public String getDocuments() {
        return documents;
    }

    public void setDocuments(String documents) {
        this.documents = documents;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public String getPetImageUrl() {
        return petImageUrl;
    }

    public void setPetImageUrl(String petImageUrl) {
        this.petImageUrl = petImageUrl;
    }

    public String getPetUniqueId() {
        return petUniqueId;
    }

    public void setPetUniqueId(String petUniqueId) {
        this.petUniqueId = petUniqueId;
    }

    public Boolean getIsNextStep() {
        return isNextStep;
    }

    public void setIsNextStep(Boolean isNextStep) {
        this.isNextStep = isNextStep;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHomeNo() {
        return homeNo;
    }

    public void setHomeNo(String homeNo) {
        this.homeNo = homeNo;
    }

    public String getStepper() {
        return stepper;
    }

    public void setStepper(String stepper) {
        this.stepper = stepper;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
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

    public String getPurchaseProofUrl() {
        return purchaseProofUrl;
    }

    public void setPurchaseProofUrl(String purchaseProofUrl) {
        this.purchaseProofUrl = purchaseProofUrl;
    }

    public String getPedigreeCertificateUrl() {
        return pedigreeCertificateUrl;
    }

    public void setPedigreeCertificateUrl(String pedigreeCertificateUrl) {
        this.pedigreeCertificateUrl = pedigreeCertificateUrl;
    }

    public String getVaccinationUrl() {
        return vaccinationUrl;
    }

    public void setVaccinationUrl(String vaccinationUrl) {
        this.vaccinationUrl = vaccinationUrl;
    }

    public String getPetImageUrl2() {
        return petImageUrl2;
    }

    public void setPetImageUrl2(String petImageUrl2) {
        this.petImageUrl2 = petImageUrl2;
    }

    public String getPetImageUrl3() {
        return petImageUrl3;
    }

    public void setPetImageUrl3(String petImageUrl3) {
        this.petImageUrl3 = petImageUrl3;
    }

    public String getPetImageUrl4() {
        return petImageUrl4;
    }

    public void setPetImageUrl4(String petImageUrl4) {
        this.petImageUrl4 = petImageUrl4;
    }

    public String getPetImageUrl5() {
        return petImageUrl5;
    }

    public void setPetImageUrl5(String petImageUrl5) {
        this.petImageUrl5 = petImageUrl5;
    }

    public String getPetImageUrl6() {
        return petImageUrl6;
    }

    public void setPetImageUrl6(String petImageUrl6) {
        this.petImageUrl6 = petImageUrl6;
    }

    public String getPetCategoryId() {
        return petCategoryId;
    }

    public void setPetCategoryId(String petCategoryId) {
        this.petCategoryId = petCategoryId;
    }

    public Double getPetSexId() {
        return petSexId;
    }

    public void setPetSexId(Double petSexId) {
        this.petSexId = petSexId;
    }

    public Boolean getPedigreeLineage() {
        return pedigreeLineage;
    }

    public void setPedigreeLineage(Boolean pedigreeLineage) {
        this.pedigreeLineage = pedigreeLineage;
    }

    public Integer getPetAgeId() {
        return petAgeId;
    }

    public void setPetAgeId(Integer petAgeId) {
        this.petAgeId = petAgeId;
    }

    public Double getPetAgeNumber() {
        return petAgeNumber;
    }

    public void setPetAgeNumber(Double petAgeNumber) {
        this.petAgeNumber = petAgeNumber;
    }

    public Double getPetSizeId() {
        return petSizeId;
    }

    public void setPetSizeId(Double petSizeId) {
        this.petSizeId = petSizeId;
    }

    public Double getPetColorId() {
        return petColorId;
    }

    public void setPetColorId(Double petColorId) {
        this.petColorId = petColorId;
    }

    public Double getPetBreedId() {
        return petBreedId;
    }

    public void setPetBreedId(Double petBreedId) {
        this.petBreedId = petBreedId;
    }

    public Integer getPetBreedId2() {
        return petBreedId2;
    }

    public void setPetBreedId2(Integer petBreedId2) {
        this.petBreedId2 = petBreedId2;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetDateofBirth() {
        return petDateofBirth;
    }

    public void setPetDateofBirth(String petDateofBirth) {
        this.petDateofBirth = petDateofBirth;
    }

    public String getPetGender() {
        return petGender;
    }

    public void setPetGender(String petGender) {
        this.petGender = petGender;
    }

    public String getPetColor() {
        return petColor;
    }

    public void setPetColor(String petColor) {
        this.petColor = petColor;
    }

    public Boolean getPageNavStatus() {
        return pageNavStatus;
    }

    public void setPageNavStatus(Boolean pageNavStatus) {
        this.pageNavStatus = pageNavStatus;
    }

    public List<PetAgeUnit> getPetAgeUnitList() {
        return petAgeUnitList;
    }

    public void setPetAgeUnitList(List<PetAgeUnit> petAgeUnitList) {
        this.petAgeUnitList = petAgeUnitList;
    }

    public List<InsurancePlan> getInsurancePlanList() {
        return insurancePlanList;
    }

    public void setInsurancePlanList(List<InsurancePlan> insurancePlanList) {
        this.insurancePlanList = insurancePlanList;
    }

    public List<Diseases> getDiseasesList() {
        return diseasesList;
    }

    public void setDiseasesList(List<Diseases> diseasesList) {
        this.diseasesList = diseasesList;
    }

    public String getBreedCategory() {
        return breedCategory;
    }

    public void setBreedCategory(String breedCategory) {
        this.breedCategory = breedCategory;
    }

    public List<PetTypeList> getPetTypeList() {
        return petTypeList;
    }

    public void setPetTypeList(List<PetTypeList> petTypeList) {
        this.petTypeList = petTypeList;
    }

    public List<PetSexList> getPetSexList() {
        return petSexList;
    }

    public void setPetSexList(List<PetSexList> petSexList) {
        this.petSexList = petSexList;
    }

    public List<PetAgeList> getPetAgeList() {
        return petAgeList;
    }

    public void setPetAgeList(List<PetAgeList> petAgeList) {
        this.petAgeList = petAgeList;
    }

    public List<PetSizeList> getPetSizeList() {
        return petSizeList;
    }

    public void setPetSizeList(List<PetSizeList> petSizeList) {
        this.petSizeList = petSizeList;
    }

    public List<PetColorList> getPetColorList() {
        return petColorList;
    }

    public void setPetColorList(List<PetColorList> petColorList) {
        this.petColorList = petColorList;
    }

    public List<PetBreedList> getPetBreedList() {
        return petBreedList;
    }

    public void setPetBreedList(List<PetBreedList> petBreedList) {
        this.petBreedList = petBreedList;
    }

    public String getCityList() {
        return cityList;
    }

    public void setCityList(String cityList) {
        this.cityList = cityList;
    }

    public String getStateList() {
        return stateList;
    }

    public void setStateList(String stateList) {
        this.stateList = stateList;
    }

    public Boolean getNextStep() {
        return isNextStep;
    }

    public void setNextStep(Boolean nextStep) {
        isNextStep = nextStep;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
