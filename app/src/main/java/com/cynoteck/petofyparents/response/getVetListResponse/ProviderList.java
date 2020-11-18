package com.cynoteck.petofyparents.response.getVetListResponse;

public class ProviderList {
    private String address;

    private String distance;

    private String latitude;

    private String rating;

    private String description;

    private String vetQualifications;

    private String cityId;

    private String isExistingprovider;

    private String route;

    private String phone;

    private String encryptedId;

    private String name;

    private String company;

    private String id;

    private String profileImageURL;

    private String email;

    private String longitude;

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getDistance ()
    {
        return distance;
    }

    public void setDistance (String distance)
    {
        this.distance = distance;
    }

    public String getLatitude ()
    {
        return latitude;
    }

    public void setLatitude (String latitude)
    {
        this.latitude = latitude;
    }

    public String getRating ()
    {
        return rating;
    }

    public void setRating (String rating)
    {
        this.rating = rating;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getVetQualifications ()
    {
        return vetQualifications;
    }

    public void setVetQualifications (String vetQualifications)
    {
        this.vetQualifications = vetQualifications;
    }

    public String getCityId ()
    {
        return cityId;
    }

    public void setCityId (String cityId)
    {
        this.cityId = cityId;
    }

    public String getIsExistingprovider ()
    {
        return isExistingprovider;
    }

    public void setIsExistingprovider (String isExistingprovider)
    {
        this.isExistingprovider = isExistingprovider;
    }

    public String getRoute ()
    {
        return route;
    }

    public void setRoute (String route)
    {
        this.route = route;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getEncryptedId ()
    {
        return encryptedId;
    }

    public void setEncryptedId (String encryptedId)
    {
        this.encryptedId = encryptedId;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCompany ()
    {
        return company;
    }

    public void setCompany (String company)
    {
        this.company = company;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getProfileImageURL ()
    {
        return profileImageURL;
    }

    public void setProfileImageURL (String profileImageURL)
    {
        this.profileImageURL = profileImageURL;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    public String getLongitude ()
    {
        return longitude;
    }

    public void setLongitude (String longitude)
    {
        this.longitude = longitude;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [address = "+address+", distance = "+distance+", latitude = "+latitude+", rating = "+rating+", description = "+description+", vetQualifications = "+vetQualifications+", cityId = "+cityId+", isExistingprovider = "+isExistingprovider+", route = "+route+", phone = "+phone+", encryptedId = "+encryptedId+", name = "+name+", company = "+company+", id = "+id+", profileImageURL = "+profileImageURL+", email = "+email+", longitude = "+longitude+"]";
    }
}
