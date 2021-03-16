package com.cynoteck.petofyparents.parameter.updatePArentDeatils;

public class UpdateParentDeatilsParams {
    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

    private String userId;

    private String email;

    public String getFirstName ()
    {
        return firstName;
    }

    public void setFirstName (String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName ()
    {
        return lastName;
    }

    public void setLastName (String lastName)
    {
        this.lastName = lastName;
    }

    public String getPhoneNumber ()
    {
        return phoneNumber;
    }

    public void setPhoneNumber (String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getuserId ()
    {
        return userId;
    }

    public void setuserId (String userId)
    {
        this.userId = userId;
    }

    public String getEmail ()
    {
        return email;
    }

    public void setEmail (String email)
    {
        this.email = email;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [firstName = "+firstName+", lastName = "+lastName+", phoneNumber = "+phoneNumber+", address = "+address+", userId = "+userId+", email = "+email+"]";
    }
}
