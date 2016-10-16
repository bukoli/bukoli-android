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
import com.mobillium.bukoliandroidsdk.models.BukoliPoint;
import com.mobillium.bukoliandroidsdk.ui.customview.CVPointItem;

import java.util.ArrayList;

public class AdapterPoints extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static String LOG_TAG = "AdapterPoints";
    private static final int CONTENT_TYPE = 1;
    private Context context;
    private ArrayList<BukoliPoint> mDataset;
    private CVPointItem.OnButtonClickListener onButtonClickListener;


    public static class DataObjectHolder extends RecyclerView.ViewHolder {
        CVPointItem cvNoktalarimItem;

        public DataObjectHolder(View itemView) {
            super(itemView);
            cvNoktalarimItem = (CVPointItem) itemView.findViewById(R.id.cvNoktalarim);
        }

    }


    public AdapterPoints(ArrayList<BukoliPoint> myDataset, Context context, CVPointItem.OnButtonClickListener onButtonClickListener) {
        this.context = context;
        this.onButtonClickListener = onButtonClickListener;
        mDataset = myDataset;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_point_items, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        ((DataObjectHolder) holder).cvNoktalarimItem.setKayitData(mDataset.get(position),onButtonClickListener);


    }


    public void addItem(BukoliPoint dataObj, int index) {
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