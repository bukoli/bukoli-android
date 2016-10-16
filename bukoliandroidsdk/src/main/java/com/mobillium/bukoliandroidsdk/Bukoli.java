package com.mobillium.bukoliandroidsdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.TypedValue;

import com.google.gson.Gson;
import com.mobillium.bukoliandroidsdk.callback.InfoCallback;
import com.mobillium.bukoliandroidsdk.callback.SelectPointCallBack;
import com.mobillium.bukoliandroidsdk.models.BukoliLocation;
import com.mobillium.bukoliandroidsdk.models.DialogModel;
import com.mobillium.bukoliandroidsdk.utils.DialogHelper;
import com.mobillium.bukoliandroidsdk.utils.MyVolley;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by oguzhandongul on 15/07/16.
 */
public class Bukoli {

    static final int CODE_ERROR = 0;
    static final int CODE_SUCCESS = 1;
    static final int CODE_CANCEL = 2;

    static final int REQUEST_CODE_SELECT = 21;
    static final int REQUEST_CODE_INFO = 22;

    static final int TYPE_MAP = 1;
    static final int TYPE_LIST = 2;

    static String url = "http://bukoli.mobillium.com/integration/";
    static Bukoli instance = null;
    static String bukoliapiKey;
    static Context applicationContext;
    static MyVolley myVolley;
    static Gson gson;
    static Boolean sdkInitialized = false;

    static SharedPreferences mSharedPrefs;
    static SharedPreferences.Editor mPrefsEditor;


    String brandName = "";
    String brandName2 = "";
    int buttonTextColor = 0xFFFFFFFF; //Default White
    int buttonBackgroundColor = 0xFFF8BA1B; //Default Bukoli Yellow
    int darkThemeColor = 0xFF3E3E3E; //Default Bukoli Yellow
    boolean showPhoneDialog = false;

    SelectPointCallBack callBack;
    InfoCallback infoCallback;


    private Bukoli() {
        // Exists only to defeat instantiation.
        myVolley = new MyVolley(applicationContext);
        gson = new Gson();
        mSharedPrefs = applicationContext.getSharedPreferences("bukoliSDK", MODE_PRIVATE);
        mPrefsEditor = mSharedPrefs.edit();
    }

    public static Bukoli getInstance() {
        if (instance == null) {
            instance = new Bukoli();
        }
        return instance;
    }

    public String getPackageName() {

        return applicationContext.getPackageName();
    }

    /**
     * This function initializes the Bukoli SDK, the behavior of Bukoli SDK functions are
     * undetermined if this function is not called. It should be called as early as possible.
     *
     * @param applicationContext The application context
     */
    public static synchronized void sdkInitialize(Context applicationContext, String apiKey) {
        if (sdkInitialized == true) {
            return;
        }

        Bukoli.applicationContext = applicationContext.getApplicationContext();
        bukoliapiKey = apiKey;
        sdkInitialized = true;
    }


    public void showInfoDialog(Activity activity, final InfoCallback callback) {
        this.infoCallback = callback;
        DialogModel modelLocation = new DialogModel(getBrandName(), getBrandName2(), getButtonTextColor(), getButtonBackgroundColor());
        DialogHelper.showInfoDialog(activity, modelLocation, callback);

    }

    public void showPointSelection(Activity activity, final SelectPointCallBack callback) {
        this.callBack = callback;
        activity.startActivity(new Intent(activity, ActivitySelectPoint.class));

    }

    public static String getApiKey() {
        return bukoliapiKey;
    }

    static Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    static MyVolley getMyVolley(Context ctx) {
        if (myVolley == null) {
            myVolley = new MyVolley(ctx);
        }
        return myVolley;
    }

    public SelectPointCallBack getCallBack() {
        return callBack;
    }

    public InfoCallback getInfoCallback() {
        return infoCallback;
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

    public int getDarkThemeColor() {
        return darkThemeColor;
    }

    public void setDarkThemeColor(int darkThemeColor) {
        this.darkThemeColor = darkThemeColor;
    }

    public boolean isShowPhoneDialog() {
        return showPhoneDialog;
    }

    public void setShowPhoneDialog(boolean showPhoneDialog) {
        this.showPhoneDialog = showPhoneDialog;
    }

    public static int convertDpiToPixel(int dpi) {
        float pixel = 0;
        try {
            Resources r = applicationContext.getResources();
            pixel = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpi,
                    r.getDisplayMetrics());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (int) pixel;
    }

    static SharedPreferences getmSharedPrefs() {
        if (mSharedPrefs == null) {
            mSharedPrefs = applicationContext.getSharedPreferences("bukoliSDK",
                    MODE_PRIVATE);
        }
        return mSharedPrefs;
    }

    static SharedPreferences.Editor getmPrefsEditor() {

        if (mPrefsEditor == null || mSharedPrefs == null) {
            mSharedPrefs = applicationContext.getSharedPreferences("bukoliSDK",
                    MODE_PRIVATE);
            mPrefsEditor = mSharedPrefs.edit();
        }
        return mPrefsEditor;
    }

    static void setLastLocation(BukoliLocation bukoliLocation) {
        getmPrefsEditor().putString("bukoliLocation", getGson().toJson(bukoliLocation)).commit();
    }

    static void setLastReqTime(long milis) {
        getmPrefsEditor().putLong("lastReq", milis).commit();
    }

    static long getLastReqTime() {
        return getmSharedPrefs().getLong("lastReq", 0);
    }

    static BukoliLocation getLastLocation() {
        String loc = getmSharedPrefs().getString("location", "");
        if (!TextUtils.isEmpty(loc)) {
            return getGson().fromJson(loc, BukoliLocation.class);
        }
        return new BukoliLocation("0", "0");
    }
}


