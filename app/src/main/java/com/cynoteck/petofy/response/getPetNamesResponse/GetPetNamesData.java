package com.cynoteck.petofy.response.getPetNamesResponse;

import java.util.ArrayList;

public class GetPetNamesData {
    private String petSexList;

    private String petGenderId;

    private String petGender;

    private String petGenderModel;

    private String petTypeList;

    private String likesCountString;

    private String likesCount;

    private String meaning;

    private String name;

    private String petCategory;

    private String id;

    private String petCategoryId;

    private ArrayList<PetNameLikeModelList> petNameLikeModelList;

    private String petCategoryModel;

    public String getPetSexList ()
    {
        return petSexList;
    }

    public void setPetSexList (String petSexList)
    {
        this.petSexList = petSexList;
    }

    public String getPetGenderId ()
    {
        return petGenderId;
    }

    public void setPetGenderId (String petGenderId)
    {
        this.petGenderId = petGenderId;
    }

    public String getPetGender ()
    {
        return petGender;
    }

    public void setPetGender (String petGender)
    {
        this.petGender = petGender;
    }

    public String getPetGenderModel ()
    {
        return petGenderModel;
    }

    public void setPetGenderModel (String petGenderModel)
    {
        this.petGenderModel = petGenderModel;
    }

    public String getPetTypeList ()
    {
        return petTypeList;
    }

    public void setPetTypeList (String petTypeList)
    {
        this.petTypeList = petTypeList;
    }

    public String getLikesCountString ()
    {
        return likesCountString;
    }

    public void setLikesCountString (String likesCountString)
    {
        this.likesCountString = likesCountString;
    }

    public String getLikesCount ()
    {
        return likesCount;
    }

    public void setLikesCount (String likesCount)
    {
        this.likesCount = likesCount;
    }

    public String getMeaning ()
    {
        return meaning;
    }

    public void setMeaning (String meaning)
    {
        this.meaning = meaning;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getPetCategory ()
    {
        return petCategory;
    }

    public void setPetCategory (String petCategory)
    {
        this.petCategory = petCategory;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getPetCategoryId ()
    {
        return petCategoryId;
    }

    public void setPetCategoryId (String petCategoryId)
    {
        this.petCategoryId = petCategoryId;
    }

    public ArrayList<PetNameLikeModelList> getPetNameLikeModelList ()
    {
        return petNameLikeModelList;
    }

    public void setPetNameLikeModelList (ArrayList<PetNameLikeModelList> petNameLikeModelList)
    {
        this.petNameLikeModelList = petNameLikeModelList;
    }

    public String getPetCategoryModel ()
    {
        return petCategoryModel;
    }

    public void setPetCategoryModel (String petCategoryModel)
    {
        this.petCategoryModel = petCategoryModel;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [petSexList = "+petSexList+", petGenderId = "+petGenderId+", petGender = "+petGender+", petGenderModel = "+petGenderModel+", petTypeList = "+petTypeList+", likesCountString = "+likesCountString+", likesCount = "+likesCount+", meaning = "+meaning+", name = "+name+", petCategory = "+petCategory+", id = "+id+", petCategoryId = "+petCategoryId+", petNameLikeModelList = "+petNameLikeModelList+", petCategoryModel = "+petCategoryModel+"]";
    }
}
