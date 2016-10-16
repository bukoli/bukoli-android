package com.mobillium.bukoliandroidsdk.ui.adapter;

/**
 * Created by Oguzhan on 05.04.2016.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mobillium.bukoliandroidsdk.R;
import com.mobillium.bukoliandroidsdk.ui.customview.CVSearchItem;
import com.mobillium.bukoliandroidsdk.models.AutoCompleteItem;

import java.util.ArrayList;

public class AdapterSearchItems extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String LOG_TAG = "AdapterSearch";
    private static final int CONTENT_TYPE = 1;
    private Context context;
    private ArrayList<AutoCompleteItem> mDataset;


    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        CVSearchItem cvSearchItem;

        public DataObjectHolder(View itemView) {
            super(itemView);
            cvSearchItem = (CVSearchItem) itemView.findViewById(R.id.cvSearch);
        }

    }


    public AdapterSearchItems(ArrayList<AutoCompleteItem> myDataset, Context context) {
        this.context = context;
        mDataset = myDataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_search_items, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((DataObjectHolder) holder).cvSearchItem.setKayitData(mDataset.get(position));


    }


    public void addItem(AutoCompleteItem dataObj, int index) {
        mDataset.add(dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mDataset.remove(index);
        notifyItemRemoved(index);
    }
    public void clearItems(){
        mDataset.clear();
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    @Override
    public int getItemViewType(int position) {

        return CONTENT_TYPE;
    }


}