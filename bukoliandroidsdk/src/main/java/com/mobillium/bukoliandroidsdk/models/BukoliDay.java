package com.mobillium.bukoliandroidsdk.models;

import android.text.TextUtils;

/**
 * Created by oguzhandongul on 13/06/16.
 */
public class BukoliDay {
    private int is_working;
    private String start_hour;
    private String end_hour;

    public BukoliDay(int is_working, String start_hour, String end_hour) {
        this.is_working = is_working;
        this.start_hour = start_hour;
        this.end_hour = end_hour;
    }

    public int getIs_working() {
        return is_working;
    }

    public void setIs_working(int is_working) {
        this.is_working = is_working;
    }

    public String getStart_hour() {
        if(TextUtils.isEmpty(start_hour)){
            return "";
        }
        return start_hour;
    }

    public void setStart_hour(String start_hour) {
        this.start_hour = start_hour;
    }

    public String getEnd_hour() {
        if(TextUtils.isEmpty(end_hour)){
            return "";
        }
        return end_hour;
    }

    public void setEnd_hour(String end_hour) {
        this.end_hour = end_hour;
    }
}
