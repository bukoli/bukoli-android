package com.mobillium.bukoliandroidsdk.models;

/**
 * Created by oguzhandongul on 17/03/16.
 */
public class BukoliLocation {
    private String latitude;
    private String longitude;

    public BukoliLocation(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
