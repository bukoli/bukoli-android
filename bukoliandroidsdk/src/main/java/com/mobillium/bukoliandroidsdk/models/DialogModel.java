package com.mobillium.bukoliandroidsdk.models;

/**
 * Created by oguzhandongul on 03/05/16.
 */
public class DialogModel {
    private String title;
    private String desc;
    private String btPositive;
    private String btNegative;
    private int iconResId;
    private String hoursss;
    private String url;
    private String name;
    private String brandName;
    private String brandName2;
    private int buttonTextColor;
    private int buttonBackgroundColor;

    public DialogModel(String title, String desc, String btPositive, String btNegative, int iconResId) {
        this.title = title;
        this.desc = desc;
        this.btPositive = btPositive;
        this.btNegative = btNegative;
        this.iconResId = iconResId;
    }

    public DialogModel(String title, String desc, String btPositive, String btNegative, int iconResId, String hoursss, String url, String name) {
        this.title = title;
        this.desc = desc;
        this.btPositive = btPositive;
        this.btNegative = btNegative;
        this.iconResId = iconResId;
        this.hoursss = hoursss;
        this.url = url;
        this.name = name;
    }



    public String getTitle() {
        return title;
    }

    public DialogModel(String brandName, String brandName2, int buttonTextColor, int buttonBackgroundColor) {
        this.brandName = brandName;
        this.brandName2 = brandName2;
        this.buttonTextColor = buttonTextColor;
        this.buttonBackgroundColor = buttonBackgroundColor;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBtPositive() {
        return btPositive;
    }

    public void setBtPositive(String btPositive) {
        this.btPositive = btPositive;
    }

    public String getBtNegative() {
        return btNegative;
    }

    public void setBtNegative(String btNedative) {
        this.btNegative = btNedative;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getHours() {
        return hoursss;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHoursss() {
        return hoursss;
    }

    public void setHoursss(String hoursss) {
        this.hoursss = hoursss;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName2() {
        return brandName2;
    }

    public void setBrandName2(String brandName2) {
        this.brandName2 = brandName2;
    }

    public int getButtonTextColor() {
        return buttonTextColor;
    }

    public void setButtonTextColor(int buttonTextColor) {
        this.buttonTextColor = buttonTextColor;
    }

    public int getButtonBackgroundColor() {
        return buttonBackgroundColor;
    }

    public void setButtonBackgroundColor(int buttonBackgroundColor) {
        this.buttonBackgroundColor = buttonBackgroundColor;
    }
}
