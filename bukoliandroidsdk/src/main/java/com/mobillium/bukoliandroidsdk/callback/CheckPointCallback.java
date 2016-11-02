package com.mobillium.bukoliandroidsdk.callback;

/**
 * Created by oguzhandongul on 12/10/2016.
 */

public interface CheckPointCallback {
    public void isActive(boolean active);

    public void onDismiss();

    public void onDisplay();

    public void onError();

}
