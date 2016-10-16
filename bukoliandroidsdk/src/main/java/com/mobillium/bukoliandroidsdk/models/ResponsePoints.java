package com.mobillium.bukoliandroidsdk.models;

import java.util.ArrayList;

/**
 * Created by oguzhandongul on 06/05/16.
 */
public class ResponsePoints {
    ArrayList<BukoliPoint> data = new ArrayList<>();
    Pagination pagination;
    String address;

    public ResponsePoints(ArrayList<BukoliPoint> data, Pagination pagination) {
        this.data = data;
        this.pagination = pagination;
    }

    public ArrayList<BukoliPoint> getData() {
        return data;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setData(ArrayList<BukoliPoint> data) {
        this.data = data;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}