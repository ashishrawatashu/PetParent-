package com.cynoteck.petofyparents.response.qrCodeResponse;

public class QrCOdeResponse {
    private String VeterinarianUserId;

    private String Key;

    public String getVeterinarianUserId ()
    {
        return VeterinarianUserId;
    }

    public void setVeterinarianUserId (String VeterinarianUserId)
    {
        this.VeterinarianUserId = VeterinarianUserId;
    }

    public String getKey ()
    {
        return Key;
    }

    public void setKey (String Key)
    {
        this.Key = Key;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [VeterinarianUserId = "+VeterinarianUserId+", Key = "+Key+"]";
    }
}
