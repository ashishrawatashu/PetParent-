package com.cynoteck.petofy.parameter.getServiceProvidersListRequest;

public class GetServiceProviderListHeader {
    private Integer PageNumber;

    private Integer pageSize;

    public Integer getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        PageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
