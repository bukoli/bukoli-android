package com.mobillium.bukoliandroidsdk.callback;

import com.mobillium.bukoliandroidsdk.models.BukoliPoint;

/**
 * Created by oguzhandongul on 12/10/2016.
 */

public interface PointStatusCallback {
    public void active(BukoliPoint selectedPoint);
    public void passive(BukoliPoint selectedPoint);
    public void onError();

}
