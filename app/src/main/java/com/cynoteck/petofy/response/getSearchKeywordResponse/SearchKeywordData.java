
package com.cynoteck.petofy.response.getSearchKeywordResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchKeywordData {

    @SerializedName("id")
    @Expose
    private Double id;
    @SerializedName("searchKeyWord")
    @Expose
    private String searchKeyWord;
    @SerializedName("cityId")
    @Expose
    private Integer cityId;
    @SerializedName("searchedOn")
    @Expose
    private String searchedOn;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("city")
    @Expose
    private Object city;

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getSearchKeyWord() {
        return searchKeyWord;
    }

    public void setSearchKeyWord(String searchKeyWord) {
        this.searchKeyWord = searchKeyWord;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getSearchedOn() {
        return searchedOn;
    }

    public void setSearchedOn(String searchedOn) {
        this.searchedOn = searchedOn;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

}
