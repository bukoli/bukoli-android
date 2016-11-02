package com.mobillium.bukoliandroidsdk.callback;

import com.mobillium.bukoliandroidsdk.models.BukoliPoint;

/**
 * Created by oguzhandongul on 12/10/2016.
 */

public interface SelectPointCallBack {
    public void onSuccess(BukoliPoint bukoliPoint, String phoneNumber);
    public void onCancel();
    public void onError(BukoliPoint bukoliPoint);
    public void onAuthError();
}
