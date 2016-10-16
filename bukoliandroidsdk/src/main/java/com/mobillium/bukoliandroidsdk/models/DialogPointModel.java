package com.mobillium.bukoliandroidsdk.models;

/**
 * Created by oguzhandongul on 03/05/16.
 */
public class DialogPointModel extends DialogModel {
    private String address;
    private String hours;

    public DialogPointModel(String title, String desc, String btPositive, String btNegative, int iconResId, String address, String hours, String url, String name) {
        super(title, desc, btPositive, btNegative, iconResId, hours, url, name);
        this.address = address;
        this.hours = hours;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

}
