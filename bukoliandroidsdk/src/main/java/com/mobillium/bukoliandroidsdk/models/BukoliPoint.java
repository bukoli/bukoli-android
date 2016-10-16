package com.mobillium.bukoliandroidsdk.models;

import android.text.TextUtils;

/**
 * Created by oguzhandongul on 03/05/16.
 */
public class BukoliPoint {
    private String id;
    private String code;
    private String name;
    private String phone;
    private String address;
    private String district;
    private String county;
    private String city;
    private String distance;
    private BukoliLocation location;
    private String index;
    private String large_image_url;
    private String small_image_url;
    private boolean is_favorite;
    private boolean is_locker;
    private BukoliWorkingHours working_hours;

    //
    private DialogPointModel model;
    private boolean isAdded;

    public BukoliPoint(String id, String code, String name, String phone, String address, String district, String county, String city, String distance, BukoliLocation location, DialogPointModel model, boolean isAdded) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.district = district;
        this.county = county;
        this.city = city;
        this.distance = distance;
        this.location = location;
        this.model = model;
        this.isAdded = isAdded;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistance() {
        if (TextUtils.isEmpty(distance)) {
            return "";
        }

        try {
            int x = (int) Math.floor(Double.valueOf(distance));
            return "" + x + " metre";
        } catch (Exception ex) {
            ex.printStackTrace();
            return distance + " metre";
        }

    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public BukoliLocation getLocationBukoli() {
        return location;
    }

    public void setLocationBukoli(BukoliLocation location) {
        this.location = location;
    }

    public DialogPointModel getModel() {
        return model;
    }

    public void setModel(DialogPointModel model) {
        this.model = model;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }


    public String getFakeHours() {
        String week = "Hafta içi: ";
        String saturday = "Cumartesi: ";
        String sunday = "Pazar: ";

        if (getWorking_hours() != null) {
            if (getWorking_hours().getWeekdays() != null && getWorking_hours().getWeekdays().getIs_working() == 1) {
                week += getWorking_hours().getWeekdays().getStart_hour() + " - " + getWorking_hours().getWeekdays().getEnd_hour();
            } else {
                week += " -";
            }

            if (getWorking_hours().getWeekdays() != null && getWorking_hours().getWeekdays().getIs_working() == 1) {
                saturday += getWorking_hours().getSaturday().getStart_hour() + " - " + getWorking_hours().getSaturday().getEnd_hour();
            } else {
                saturday += " -";
            }

            if (getWorking_hours().getWeekdays() != null && getWorking_hours().getWeekdays().getIs_working() == 1) {
                sunday += getWorking_hours().getSunday().getStart_hour() + " - " + getWorking_hours().getSunday().getEnd_hour();
            } else {
                sunday += " -";
            }
            return week + "\n" + saturday + "\n" + sunday;
        } else {
            return "-";

        }
    }

    public boolean is_favorite() {
        return is_favorite;
    }

    public void setIs_favorite(boolean is_favorite) {
        this.is_favorite = is_favorite;
    }

    public boolean is_locker() {
        return is_locker;
    }

    public void setIs_locker(boolean is_locker) {
        this.is_locker = is_locker;
    }

    public BukoliWorkingHours getWorking_hours() {
        return working_hours;
    }

    public void setWorking_hours(BukoliWorkingHours working_hours) {
        this.working_hours = working_hours;
    }

    public String getLarge_image_url() {
        return large_image_url;
    }

    public void setLarge_image_url(String large_image_url) {
        this.large_image_url = large_image_url;
    }

    public String getSmall_image_url() {
        return small_image_url;
    }

    public void setSmall_image_url(String small_image_url) {
        this.small_image_url = small_image_url;
    }
}
