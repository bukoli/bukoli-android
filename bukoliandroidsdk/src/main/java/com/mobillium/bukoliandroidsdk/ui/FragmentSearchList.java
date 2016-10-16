
package com.mobillium.bukoliandroidsdk.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.ViewGroup;

import com.mobillium.bukoliandroidsdk.ActivitySelectPoint;
import com.mobillium.bukoliandroidsdk.R;
import com.mobillium.bukoliandroidsdk.ui.adapter.AdapterSearchItems;
import com.mobillium.bukoliandroidsdk.models.AutoCompleteItem;

import java.util.ArrayList;

public class FragmentSearchList extends BaseFragment implements ActivitySelectPoint.DataUpdateListener {


    ArrayList<AutoCompleteItem> autoCompleteItems = new ArrayList<>();
    RecyclerView recyclerView;
    AdapterSearchItems adapterSearchItems;


    @Override
    public void onDestroy() {
        super.onDestroy();
        ((ActivitySelectPoint) getActivity()).unregisterDataUpdateListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((ActivitySelectPoint) getActivity()).registerDataUpdateListener(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_search_list, container, false);
        createCallBacks();
        createViews(view);
        setListeners();
        setFonts();

        return view;
    }

    @Override
    protected void createViews(View v) {
        super.createViews(v);
        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        adapterSearchItems = new AdapterSearchItems(autoCompleteItems, getActivity());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterSearchItems);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        return true;
                    }
                });
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onDataUpdate(ArrayList<AutoCompleteItem> autoCompleteItems) {
        // put your UI update logic here
        adapterSearchItems.clearItems();
        for (int i = 0; i < autoCompleteItems.size(); i++) {
            adapterSearchItems.addItem(autoCompleteItems.get(i), i);
        }
    }
}
