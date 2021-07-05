package com.cynoteck.petofy.parameter.adoptionListRequest;

public class AdoptionListHeader {
    private int pageNumber;
    private int pageSize;
    private String searchData;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
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
