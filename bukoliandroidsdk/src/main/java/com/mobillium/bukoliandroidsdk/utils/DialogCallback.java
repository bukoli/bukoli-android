
package com.mobillium.bukoliandroidsdk.utils;


public interface DialogCallback {

    public void pressed(int whichButton);

    public void pressed(int whichButton, String takip, String siparis);

    public void selected(int position);

    public void dismissed();
}
