
package com.cynoteck.petofy.parameter.searchProviderRequest;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SearchProviderParameters {

    @SerializedName("searchText")
    @Expose
    private String searchText;
    @SerializedName("cityId")
    @Expose
    private Integer cityId;
    @SerializedName("page")
    @Expose
    private Integer page;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchkeyword) {
        this.searchText = searchkeyword;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

}
