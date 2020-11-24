package com.cynoteck.petofyparents.parameter.adoptionListRequest;

public class AdoptionListHeader {
    private String pageNumber;
    private String pageSize;
    private String searchData;

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearchData() {
        return searchData;
    }

    public void setSearchData(String searchData) {
        this.searchData = searchData;
    }

    @Override
    public String toString() {
        return "ClassPojo[" +
                "pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", search-Data=" + searchData +
                "]";
    }
}
