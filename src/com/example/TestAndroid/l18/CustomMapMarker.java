package com.example.TestAndroid.l18;

public class CustomMapMarker
{
    private String mLabel;
    private String mIcon;
    private Double mLatitude;
    private Double mLongitude;

    public CustomMapMarker(String label, String icon, Double latitude, Double longitude)
    {
        this.mLabel = label;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mIcon = icon;
    }

    public String getLabel()
    {
        return mLabel;
    }

    public void setLabel(String mLabel)
    {
        this.mLabel = mLabel;
    }

    public String getIcon()
    {
        return mIcon;
    }

    public void setIcon(String icon)
    {
        this.mIcon = icon;
    }

    public Double getLatitude()
    {
        return mLatitude;
    }

    public void setLatitude(Double mLatitude)
    {
        this.mLatitude = mLatitude;
    }

    public Double getLongitude()
    {
        return mLongitude;
    }

    public void setLongitude(Double mLongitude)
    {
        this.mLongitude = mLongitude;
    }
}
