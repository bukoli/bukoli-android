package com.mobillium.bukoliandroidsdk.models;

/**
 * Created by oguzhandongul on 13/06/16.
 */
public class BukoliWorkingHours {
    private BukoliDay weekdays;
    private BukoliDay saturday;
    private BukoliDay sunday;

    public BukoliWorkingHours(BukoliDay weekdays, BukoliDay saturday, BukoliDay sunday) {
        this.weekdays = weekdays;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public BukoliDay getWeekdays() {
        return weekdays;
    }

    public void setWeekdays(BukoliDay weekdays) {
        this.weekdays = weekdays;
    }

    public BukoliDay getSaturday() {
        return saturday;
    }

    public void setSaturday(BukoliDay saturday) {
        this.saturday = saturday;
    }

    public BukoliDay getSunday() {
        return sunday;
    }

    public void setSunday(BukoliDay sunday) {
        this.sunday = sunday;
    }
}
