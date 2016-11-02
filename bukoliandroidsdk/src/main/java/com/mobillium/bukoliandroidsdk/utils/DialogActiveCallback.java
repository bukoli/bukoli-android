
package com.mobillium.bukoliandroidsdk.utils;


import com.mobillium.bukoliandroidsdk.webservice.ServiceCallback;

public interface DialogActiveCallback {

    public void pressed(int whichButton, String code, ServiceCallback serviceCallback);

    public void dismissed();

}
