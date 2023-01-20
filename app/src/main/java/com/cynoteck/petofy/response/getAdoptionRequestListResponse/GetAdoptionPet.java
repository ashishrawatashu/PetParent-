
package com.cynoteck.petofy.response.getAdoptionRequestListResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAdoptionPet {

    @SerializedName("id")
    @Expose
    private Double id;
    @SerializedName("userId")
    @Expose
    private Object userId;
    @SerializedName("petCategoryId")
    @Expose
    private Integer petCategoryId;
    @SerializedName("petSexId")
    @Expose
    private Double petSexId;
    @SerializedName("petAgeId")
    @Expose
    private Double petAgeId;
    @SerializedName("petSizeId")
    @Expose
    private Double petSizeId;
    @SerializedName("petColorId")
    @Expose
    private Double petColorId;
    @SerializedName("petBreedId")
    @Expose
    private Double petBreedId;
    @SerializedName("petName")
    @Expose
    private String petName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("cityId")
    @Expose
    private Object cityId;
    @SerializedName("requestStatusTitle")
    @Expose
    private Object requestStatusTitle;
    @SerializedName("stateId")
    @Expose
    private Object stateId;
    @SerializedName("requestStatusId")
    @Expose
    private Object requestStatusId;
    @SerializedName("requestDate")
    @Expose
    private Object requestDate;
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
    @SerializedName("encryptedId")
    @Expose
    private Object encryptedId;
    @SerializedName("donarName")
    @Expose
    private String donarName;
    @SerializedName("phoneNumber")
    @Expose
    private Object phoneNumber;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("reasonToReject")
    @Expose
    private Object reasonToReject;
    @SerializedName("petCategory")
    @Expose
    private Object petCategory;
    @SerializedName("requestStatus")
    @Expose
    private Object requestStatus;
    @SerializedName("user")
    @Expose
    private GetAdoptionUser user;
    @SerializedName("petAdoption")
    @Expose
    private List<Object> petAdoption = null;
    @SerializedName("petImageList")
    @Expose
    private List<GetAdoptionPetImage> petImageList = null;
    @SerializedName("petTypeList")
    @Expose
    private Object petTypeList;
    @SerializedName("petSexList")
    @Expose
    private Object petSexList;
    @SerializedName("petAgeList")
    @Expose
    private Object petAgeList;
    @SerializedName("petSizeList")
    @Expose
    private Object petSizeList;
    @SerializedName("petColorList")
    @Expose
    private Object petColorList;
    @SerializedName("petBreedList")
    @Expose
    private Object petBreedList;
    @SerializedName("cityList")
    @Expose
    private Object cityList;
    @SerializedName("stateList")
    @Expose
    private Object stateList;
    @SerializedName("petType")
    @Expose
    private String petType;
    @SerializedName("petSex")
    @Expose
    private String petSex;
    @SerializedName("petAge")
    @Expose
    private String petAge;
    @SerializedName("petSize")
    @Expose
    private String petSize;
    @SerializedName("petColor")
    @Expose
    private String petColor;
    @SerializedName("petBreed")
    @Expose
    private String petBreed;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("role")
    @Expose
    private Object role;
    @SerializedName("otherBreedName")
    @Expose
    private Object otherBreedName;
    @SerializedName("otherColorName")
    @Expose
    private Object otherColorName;
    @SerializedName("otherSizeName")
    @Expose
    private Object otherSizeName;
    @SerializedName("otherAgeName")
    @Expose
    private Object otherAgeName;
    @SerializedName("ngoUserId")
    @Expose
    private Object ngoUserId;
    @SerializedName("ngoList")
    @Expose
    private Object ngoList;
    @SerializedName("numberOfRecords")
    @Expose
    private Integer numberOfRecords;
    @SerializedName("pageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("requestUpdateDate")
    @Expose
    private String requestUpdateDate;
    @SerializedName("requestToNgoModel")
    @Expose
    private GetAdoptionRequestToNgoModel requestToNgoModel;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public Integer getPetCategoryId() {
        return petCategoryId;
    }

    public void setPetCategoryId(Integer petCategoryId) {
        this.petCategoryId = petCategoryId;
    }

    public Double getPetSexId() {
        return petSexId;
    }

    public void setPetSexId(Double petSexId) {
        this.petSexId = petSexId;
    }

    public Double getPetAgeId() {
        return petAgeId;
    }

    public void setPetAgeId(Double petAgeId) {
        this.petAgeId = petAgeId;
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

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getCityId() {
        return cityId;
    }

    public void setCityId(Object cityId) {
        this.cityId = cityId;
    }

    public Object getRequestStatusTitle() {
        return requestStatusTitle;
    }

    public void setRequestStatusTitle(Object requestStatusTitle) {
        this.requestStatusTitle = requestStatusTitle;
    }

    public Object getStateId() {
        return stateId;
    }

    public void setStateId(Object stateId) {
        this.stateId = stateId;
    }

    public Object getRequestStatusId() {
        return requestStatusId;
    }

    public void setRequestStatusId(Object requestStatusId) {
        this.requestStatusId = requestStatusId;
    }

    public Object getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Object requestDate) {
        this.requestDate = requestDate;
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

    public Object getEncryptedId() {
        return encryptedId;
    }

    public void setEncryptedId(Object encryptedId) {
        this.encryptedId = encryptedId;
    }

    public String getDonarName() {
        return donarName;
    }

    public void setDonarName(String donarName) {
        this.donarName = donarName;
    }

    public Object getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Object phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Object getReasonToReject() {
        return reasonToReject;
    }

    public void setReasonToReject(Object reasonToReject) {
        this.reasonToReject = reasonToReject;
    }

    public Object getPetCategory() {
        return petCategory;
    }

    public void setPetCategory(Object petCategory) {
        this.petCategory = petCategory;
    }

    public Object getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Object requestStatus) {
        this.requestStatus = requestStatus;
    }

    public GetAdoptionUser getUser() {
        return user;
    }

    public void setUser(GetAdoptionUser user) {
        this.user = user;
    }

    public List<Object> getPetAdoption() {
        return petAdoption;
    }

    public void setPetAdoption(List<Object> petAdoption) {
        this.petAdoption = petAdoption;
    }

    public List<GetAdoptionPetImage> getPetImageList() {
        return petImageList;
    }

    public void setPetImageList(List<GetAdoptionPetImage> petImageList) {
        this.petImageList = petImageList;
    }

    public Object getPetTypeList() {
        return petTypeList;
    }

    public void setPetTypeList(Object petTypeList) {
        this.petTypeList = petTypeList;
    }

    public Object getPetSexList() {
        return petSexList;
    }

    public void setPetSexList(Object petSexList) {
        this.petSexList = petSexList;
    }

    public Object getPetAgeList() {
        return petAgeList;
    }

    public void setPetAgeList(Object petAgeList) {
        this.petAgeList = petAgeList;
    }

    public Object getPetSizeList() {
        return petSizeList;
    }

    public void setPetSizeList(Object petSizeList) {
        this.petSizeList = petSizeList;
    }

    public Object getPetColorList() {
        return petColorList;
    }

    public void setPetColorList(Object petColorList) {
        this.petColorList = petColorList;
    }

    public Object getPetBreedList() {
        return petBreedList;
    }

    public void setPetBreedList(Object petBreedList) {
        this.petBreedList = petBreedList;
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

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getPetSex() {
        return petSex;
    }

    public void setPetSex(String petSex) {
        this.petSex = petSex;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public String getPetSize() {
        return petSize;
    }

    public void setPetSize(String petSize) {
        this.petSize = petSize;
    }

    public String getPetColor() {
        return petColor;
    }

    public void setPetColor(String petColor) {
        this.petColor = petColor;
    }

    public String getPetBreed() {
        return petBreed;
    }

    public void setPetBreed(String petBreed) {
        this.petBreed = petBreed;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getRole() {
        return role;
    }

    public void setRole(Object role) {
        this.role = role;
    }

    public Object getOtherBreedName() {
        return otherBreedName;
    }

    public void setOtherBreedName(Object otherBreedName) {
        this.otherBreedName = otherBreedName;
    }

    public Object getOtherColorName() {
        return otherColorName;
    }

    public void setOtherColorName(Object otherColorName) {
        this.otherColorName = otherColorName;
    }

    public Object getOtherSizeName() {
        return otherSizeName;
    }

    public void setOtherSizeName(Object otherSizeName) {
        this.otherSizeName = otherSizeName;
    }

    public Object getOtherAgeName() {
        return otherAgeName;
    }

    public void setOtherAgeName(Object otherAgeName) {
        this.otherAgeName = otherAgeName;
    }

    public Object getNgoUserId() {
        return ngoUserId;
    }

    public void setNgoUserId(Object ngoUserId) {
        this.ngoUserId = ngoUserId;
    }

    public Object getNgoList() {
        return ngoList;
    }

    public void setNgoList(Object ngoList) {
        this.ngoList = ngoList;
    }

    public Integer getNumberOfRecords() {
        return numberOfRecords;
    }

    public void setNumberOfRecords(Integer numberOfRecords) {
        this.numberOfRecords = numberOfRecords;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getRequestUpdateDate() {
        return requestUpdateDate;
    }

    public void setRequestUpdateDate(String requestUpdateDate) {
        this.requestUpdateDate = requestUpdateDate;
    }

    public GetAdoptionRequestToNgoModel getRequestToNgoModel() {
        return requestToNgoModel;
    }

    public void setRequestToNgoModel(GetAdoptionRequestToNgoModel requestToNgoModel) {
        this.requestToNgoModel = requestToNgoModel;
    }

}
