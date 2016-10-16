package com.mobillium.bukoliandroidsdk.webservice.models;

/**
 * Created by oguzhandongul on 15/07/16.
 */
public class BulkOrder {
    private String servicePassword;
    private String startDate;
    private String endDate;

    public BulkOrder(String servicePassword, String startDate, String endDate) {
        this.servicePassword = servicePassword;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getServicePassword() {
        return servicePassword;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }
}
