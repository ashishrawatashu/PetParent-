package com.cynoteck.petofy.response.getVetListResponse;

import com.cynoteck.petofy.response.getPetReportsResponse.PagingHeader;

import java.util.ArrayList;

public class VetListData {
    private PagingHeader pagingHeader;

    private ArrayList<ProviderList> providerList;

    private String viewMore;

    public PagingHeader getPagingHeader ()
    {
        return pagingHeader;
    }

    public void setPagingHeader (PagingHeader pagingHeader)
    {
        this.pagingHeader = pagingHeader;
    }

    public ArrayList<ProviderList> getProviderList ()
    {
        return providerList;
    }

    public void setProviderList (ArrayList<ProviderList> providerList)
    {
        this.providerList = providerList;
    }

    public String getViewMore ()
    {
        return viewMore;
    }

    public void setViewMore (String viewMore)
    {
        this.viewMore = viewMore;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [pagingHeader = "+pagingHeader+", providerList = "+providerList+", viewMore = "+viewMore+"]";
    }
}
	
