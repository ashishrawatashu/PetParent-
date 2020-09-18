package com.cynoteck.petofyparents.parameter.petReportsRequest;

public class PetDataParams {

    private String PageNumber;

    private String search_Data;

    private String pageSize;

    public String getPageNumber ()
    {
        return PageNumber;
    }

    public void setPageNumber (String PageNumber)
    {
        this.PageNumber = PageNumber;
    }

    public String getSearch_Data ()
    {
        return search_Data;
    }

    public void setSearch_Data (String search_Data)
    {
        this.search_Data = search_Data;
    }

    public String getPageSize ()
    {
        return pageSize;
    }

    public void setPageSize (String pageSize)
    {
        this.pageSize = pageSize;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [PageNumber = "+PageNumber+", search_Data = "+search_Data+", pageSize = "+pageSize+"]";
    }
}