package com.mobillium.bukoliandroidsdk.ui.customview;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mobillium.bukoliandroidsdk.R;
import com.mobillium.bukoliandroidsdk.ActivitySelectPoint;
import com.mobillium.bukoliandroidsdk.models.AutoCompleteItem;


public class CVSearchItem extends ForegroundLinearLayout {
    Context mContext;
    AutoCompleteItem currentData;

    TextView tvTitle;

    public CVSearchItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
        setForeground(ContextCompat.getDrawable(mContext, R.drawable.flat_button_light));
        setClickable(true);
    }

    public CVSearchItem(Context context) {
        super(context);
    }

    public void init() {
        LayoutInflater.from(mContext).inflate(R.layout.customview_search_items, this);
        tvTitle = (TextView) findViewById(R.id.tvTitle);


    }

    public void setKayitData(final AutoCompleteItem rowData) {

        if (rowData != null) {
            currentData = rowData;

            tvTitle.setText(rowData.getName());


            this.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ActivitySelectPoint) mContext).setSearchData(rowData.getId(),rowData.getName());
                }
            });
        }
    }


}