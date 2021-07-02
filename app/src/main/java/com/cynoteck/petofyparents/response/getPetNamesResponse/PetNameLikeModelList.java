package com.cynoteck.petofyparents.response.getPetNamesResponse;

public class PetNameLikeModelList {
    private String petNameModel;

    private String nameId;

    private String likeOnDate;

    private String id;

    private String userId;

    private String status;

    public String getPetNameModel ()
    {
        return petNameModel;
    }

    public void setPetNameModel (String petNameModel)
    {
        this.petNameModel = petNameModel;
    }

    public String getNameId ()
    {
        return nameId;
    }

    public void setNameId (String nameId)
    {
        this.nameId = nameId;
    }

    public String getLikeOnDate ()
    {
        return likeOnDate;
    }

    public void setLikeOnDate (String likeOnDate)
    {
        this.likeOnDate = likeOnDate;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getUserId ()
    {
        return userId;
    }

    public void setUserId (String userId)
    {
        this.userId = userId;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [petNameModel = "+petNameModel+", nameId = "+nameId+", likeOnDate = "+likeOnDate+", id = "+id+", userId = "+userId+", status = "+status+"]";
    }
}
		
	
