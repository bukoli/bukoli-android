package com.mobillium.bukoliandroidsdk.callback;

import com.mobillium.bukoliandroidsdk.models.AutoCompleteItem;

import java.util.ArrayList;

/**
 * Created by oguzhandongul on 13/10/2016.
 */

public interface SearchPointCallback {
    public void onSearch(ArrayList<AutoCompleteItem> autoCompleteItems);
}
