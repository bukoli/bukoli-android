package com.mobillium.bukoliandroidsdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.mobillium.bukoliandroidsdk.callback.AuthorizeCallback;
import com.mobillium.bukoliandroidsdk.callback.InfoCallback;
import com.mobillium.bukoliandroidsdk.callback.PointStatusCallback;
import com.mobillium.bukoliandroidsdk.callback.SelectPointCallBack;
import com.mobillium.bukoliandroidsdk.models.BukoliLocation;
import com.mobillium.bukoliandroidsdk.models.BukoliPoint;
import com.mobillium.bukoliandroidsdk.models.DialogModel;
import com.mobillium.bukoliandroidsdk.models.ResponseAuthorize;
import com.mobillium.bukoliandroidsdk.utils.BukoliException;
import com.mobillium.bukoliandroidsdk.utils.BukoliSdkNotInitializedException;
import com.mobillium.bukoliandroidsdk.utils.DialogHelper;
import com.mobillium.bukoliandroidsdk.utils.MyVolley;
import com.mobillium.bukoliandroidsdk.webservice.ServiceCallback;
import com.mobillium.bukoliandroidsdk.webservice.ServiceException;

import java.util.HashMap;
import java.util.Map;

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

    static final String SHARED_PREF = "BUKOLI";

    static String url = "https://api.bukoli.com/integration/";
    static Bukoli instance = null;
    static String bukoliapiKey;
    static String bukoliapiToken;
    static String userCode;
    static String phone;
    static String email;
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
    int darkThemeColor = 0xFF3E3E3E; //Default Bukoli Grey
    int lockerColor = 0xFF31AADE; //Default Bukoli Locker Blue
    boolean showPhoneDialog = false; //Default false
    boolean isDebugEnabled = false; //Default false

    SelectPointCallBack callBack;
    InfoCallback infoCallback;
    PointStatusCallback pointStatusCallback;

    public PointStatusCallback getPointStatusCallback() {
        return pointStatusCallback;
    }

    public void setPointStatusCallback(PointStatusCallback pointStatusCallback) {
        this.pointStatusCallback = pointStatusCallback;
    }

    private Bukoli() {
        myVolley = new MyVolley(applicationContext);
        gson = new Gson();
        mSharedPrefs = applicationContext.getSharedPreferences("bukoliSDK", MODE_PRIVATE);
        mPrefsEditor = mSharedPrefs.edit();
    }

    /**
     * This function creates an instance of Bukoli
     * Must be called after "sdkInitialize" method otherwise will throw an exception
     */
    public static Bukoli getInstance() {
        try {
            if (instance == null) {
                instance = new Bukoli();
            }
            return instance;
        } catch (BukoliSdkNotInitializedException ex) {
            throw new BukoliSdkNotInitializedException("You must initialize the Bukoli SDK first");
        }
    }

    public String getPackageName() {
        if (applicationContext == null) {
            throw new BukoliSdkNotInitializedException("You must initialize the Bukoli SDK first");
        }
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

    /**
     * This function sets user data and makes request for getting token from webservice
     *
     * @param userCode The uniqe identification value for each user. Cannot be null
     * @param email    String value includes e-mail address of user. Can be null.
     * @param phone    String value includes phone number of user. Can be null.
     */
    public void setUser(@NonNull String userCode, @Nullable String email, @Nullable String phone) {
        if (!sdkInitialized) {
            throw new BukoliSdkNotInitializedException("You must initialize the Bukoli SDK first");
        }

        if (TextUtils.isEmpty(userCode)) {
            throw new BukoliException("UserCode must not be null!");
        }

        this.userCode = userCode;
        this.email = email;
        this.phone = phone;


    }


    /**
     * This function creates and shows info dialog
     *
     * @param activity The activity context
     * @param callback The callback that will be called when the dialog is opened and closed. Can be null.
     */
    public void showInfoDialog(Activity activity, @Nullable final InfoCallback callback) {
        if (!sdkInitialized) {
            throw new BukoliSdkNotInitializedException("You must initialize the Bukoli SDK first");
        }
        this.infoCallback = callback;
        DialogModel modelLocation = new DialogModel(getBrandName(), getBrandName2(), getButtonTextColor(), getButtonBackgroundColor());
        DialogHelper.showInfoDialog(activity, modelLocation, callback);

    }

    /**
     * This function makes a request for checking the Bukoli Point is active or not and return its current status
     *
     * @param code The TDR Code wanted to be checked
     * @param callback The callback that will be called when webservice respond to request. Can be null.
     */
    public void checkPointStatus(String code, @NonNull final PointStatusCallback callback) {
        if (!sdkInitialized) {
            throw new BukoliSdkNotInitializedException("You must initialize the Bukoli SDK first");
        }

        if(callback==null){
            throw new BukoliException("Callback cannot be null");
        }

        this.pointStatusCallback = callback;

        if (!TextUtils.isEmpty(code)) {
            makeIsActiveRequest(code, new ServiceCallback() {
                @Override
                public void done(String result, ServiceException e) {
                    if (e == null) {

                        BukoliPoint selectedPoint = Bukoli.getInstance().getGson().fromJson(result, BukoliPoint.class);
                        if (selectedPoint.isActive()) {
                            pointStatusCallback.active(selectedPoint);

                        } else {
                            pointStatusCallback.passive(selectedPoint);

                        }
                    } else {
                        pointStatusCallback.onError();
                    }
                }
            });
        } else {
            throw new BukoliException("You must enter a TDR Code");
        }


    }


    /**
     * This function opens the Point selection activity
     *
     * @param activity The activity context
     * @param callback The callback that will be called when the point is selected, an error happened or selection cancelled. Cannot be null.
     */
    public void showPointSelection(Activity activity, @NonNull final SelectPointCallBack callback) {
        if (!sdkInitialized) {
            throw new BukoliSdkNotInitializedException("You must initialize the Bukoli SDK first");
        }
        this.callBack = callback;
        activity.startActivity(new Intent(activity, ActivitySelectPoint.class));

    }

    /**
     * This function returns current API key
     */
    public static String getApiKey() {
        return bukoliapiKey;
    }

    public static Gson getGson() {
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

    SelectPointCallBack getCallBack() {
        return callBack;
    }

    InfoCallback getInfoCallback() {
        return infoCallback;
    }

    public String getBrandName() {
        return brandName;
    }

    /**
     * This function sets the brand name and returns the Instance of Bukoli
     *
     * @param brandName the brand name
     */
    public Bukoli setBrandName(String brandName) {
        this.brandName = brandName;
        return getInstance();
    }

    /**
     * This function sets the brand name and returns the Instance of Bukoli
     *
     * @param brandNameResID the brand name resource ID
     */
    public Bukoli setBrandName(int brandNameResID) {
        return setBrandName(applicationContext.getString(brandNameResID));
    }

    public String getBrandName2() {
        return brandName2;
    }

    /**
     * This function sets ablative form of the brand name and returns the Instance of Bukoli
     *
     * @param brandName2 ablative form of the brand name
     */
    public Bukoli setBrandName2(String brandName2) {
        this.brandName2 = brandName2;
        return getInstance();
    }

    /**
     * This function sets ablative form of the brand name and returns the Instance of Bukoli
     *
     * @param brandName2ResID ablative form of the brand name resource ID
     */
    public Bukoli setBrandName2(int brandName2ResID) {
        return setBrandName2(applicationContext.getString(brandName2ResID));
    }

    public int getButtonTextColor() {
        return buttonTextColor;
    }

    /**
     * This function sets the secondary theme color of Button Texts and icons and returns the Instance of Bukoli
     *
     * @param buttonTextColor the secondary color integer value
     */
    public Bukoli setButtonTextColor(int buttonTextColor) {
        this.buttonTextColor = buttonTextColor;
        return getInstance();
    }

    public int getButtonBackgroundColor() {
        return buttonBackgroundColor;
    }

    /**
     * This function sets the primary theme color of UI(such as Buttons and Toolbar background) and returns the Instance of Bukoli
     *
     * @param buttonBackgroundColor the primary color integer value
     */
    public Bukoli setButtonBackgroundColor(int buttonBackgroundColor) {
        this.buttonBackgroundColor = buttonBackgroundColor;
        return getInstance();
    }

    public int getLockerColor() {
        return lockerColor;
    }

    public int getDarkThemeColor() {
        return darkThemeColor;
    }


    /**
     * This function sets the dark theme color of dark icons(such as close, map center, target etc.) and returns the Instance of Bukoli
     *
     * @param darkThemeColor the dark color integer value
     */
    public Bukoli setDarkThemeColor(int darkThemeColor) {
        this.darkThemeColor = darkThemeColor;
        return getInstance();
    }

    public boolean isShowPhoneDialog() {
        return showPhoneDialog;
    }

    /**
     * This function sets the Phone dialog value and returns the Instance of Bukoli
     * If it is true, Phone number dialog will be shown after selection of the Point
     * If it is false Phone number dialog will not be shown after selection of the Point
     *
     * @param showPhoneDialog the dark color integer value
     */
    public Bukoli setShowPhoneDialog(boolean showPhoneDialog) {
        this.showPhoneDialog = showPhoneDialog;
        return getInstance();
    }

    public boolean isDebugEnabled() {
        return isDebugEnabled;
    }

    /**
     * This function sets the debug state of Bukoli SDK
     * If it is true, Logs will appear on Android Monitor / LogCat
     * If it is false Logs will not appear on Android Monitor / LogCat
     *
     * @param debugEnabled the boolean value of debug is enabled or not
     */
    public void setDebugEnabled(boolean debugEnabled) {
        isDebugEnabled = debugEnabled;
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

    public void saveSharedPref() {
        SharedPreferences saveData = applicationContext.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        saveData.edit().putBoolean("swipe", true).commit();
    }

    public boolean getSharedPref() {
        SharedPreferences saveData = applicationContext.getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        return saveData.getBoolean("swipe", false);
    }


    private PackageInfo getPackageInfo() {
        try {
            return applicationContext.getPackageManager().getPackageInfo(applicationContext.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException ex) {
            throw new BukoliException("Package Name Not found");
        }
    }

    String getApiToken() {
        if (TextUtils.isEmpty(bukoliapiToken)) {
            return "";
        }
        return bukoliapiToken;
    }


    void getTokenRequest(final AuthorizeCallback authorizeCallback) {
        Map<String, String> params = new HashMap<>();
        params.put("user_code", userCode);
        params.put("os_version", Build.VERSION.RELEASE);
        params.put("app_version", getPackageInfo().versionName);
        params.put("app_build", "" + getPackageInfo().versionCode);
        params.put("sdk_version", BukoliSdkVersion.BUILD);
        params.put("brand", Build.BRAND);
        params.put("model", Build.MODEL);


        if (!TextUtils.isEmpty(email)) {
            params.put("email", email);

        }
        if (!TextUtils.isEmpty(phone)) {
            params.put("phone", phone);

        }


        ServiceOperations.serviceReq(applicationContext, Request.Method.POST, "authorize", params, new ServiceCallback() {
            @Override
            public void done(String result, ServiceException e) {
                if (e == null) {
                    ResponseAuthorize responseAuthorize = getGson().fromJson(result, ResponseAuthorize.class);
                    bukoliapiToken = responseAuthorize.getToken();
                    authorizeCallback.onSuccess();
                } else {
                    authorizeCallback.onFailure();
                    throw new BukoliException("Cannot aouthorize, check your parameters!");
                }
            }
        });
    }


    void makeIsActiveRequest(String id, ServiceCallback serviceCallback) {
        Map<String, String> params = new HashMap<>();
        ServiceOperations.serviceReq(applicationContext, Request.Method.GET, "point/" + id, params, serviceCallback);
    }
}


