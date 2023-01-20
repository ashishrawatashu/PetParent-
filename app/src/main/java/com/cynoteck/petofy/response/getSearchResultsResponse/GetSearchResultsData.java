
package com.cynoteck.petofy.response.getSearchResultsResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetSearchResultsData {

    @SerializedName("resultList")
    @Expose
    private ResultList resultList;
    @SerializedName("searchResultStatus")
    @Expose
    private Boolean searchResultStatus;
    @SerializedName("searchText")
    @Expose
    private String searchText;
    @SerializedName("currentPage")
    @Expose
    private Integer currentPage;
    @SerializedName("pageCount")
    @Expose
    private Integer pageCount;
    @SerializedName("leftMostPage")
    @Expose
    private Integer leftMostPage;
    @SerializedName("pageRange")
    @Expose
    private Integer pageRange;
    @SerializedName("paging")
    @Expose
    private Object paging;
    @SerializedName("categoryFilter")
    @Expose
    private Object categoryFilter;
    @SerializedName("amenityFilter")
    @Expose
    private Object amenityFilter;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("cityId")
    @Expose
    private Integer cityId;
    @SerializedName("providerList")
    @Expose
    private List<Provider> providerList = null;

    public ResultList getResultList() {
        return resultList;
    }

    public void setResultList(ResultList resultList) {
        this.resultList = resultList;
    }

    public Boolean getSearchResultStatus() {
        return searchResultStatus;
    }

    public void setSearchResultStatus(Boolean searchResultStatus) {
        this.searchResultStatus = searchResultStatus;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getLeftMostPage() {
        return leftMostPage;
    }

    public void setLeftMostPage(Integer leftMostPage) {
        this.leftMostPage = leftMostPage;
    }

    public Integer getPageRange() {
        return pageRange;
    }

    public void setPageRange(Integer pageRange) {
        this.pageRange = pageRange;
    }

    public Object getPaging() {
        return paging;
    }

    public void setPaging(Object paging) {
        this.paging = paging;
    }

    public Object getCategoryFilter() {
        return categoryFilter;
    }

    public void setCategoryFilter(Object categoryFilter) {
        this.categoryFilter = categoryFilter;
    }

    public Object getAmenityFilter() {
        return amenityFilter;
    }

    public void setAmenityFilter(Object amenityFilter) {
        this.amenityFilter = amenityFilter;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public List<Provider> getProviderList() {
        return providerList;
    }

    public void setProviderList(List<Provider> providerList) {
        this.providerList = providerList;
    }

}
