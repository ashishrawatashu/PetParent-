
package com.cynoteck.petofyparents.parameter.searchKeywordParams;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchKeywordParams {

    @SerializedName("searchkeyword")
    @Expose
    private String searchkeyword;
    @SerializedName("cityId")
    @Expose
    private Integer cityId;

    public String getSearchkeyword() {
        return searchkeyword;
    }

    public void setSearchkeyword(String searchkeyword) {
        this.searchkeyword = searchkeyword;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

}
