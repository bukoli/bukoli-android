package com.mobillium.bukoliandroidsdk.ui.customview;

import android.content.Context;
import android.graphics.drawable.StateListDrawable;
import android.support.design.internal.ForegroundLinearLayout;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobillium.bukoliandroidsdk.Bukoli;
import com.mobillium.bukoliandroidsdk.R;
import com.mobillium.bukoliandroidsdk.models.BukoliPoint;
import com.mobillium.bukoliandroidsdk.utils.ShapeCreator;

public class CVPointItem extends ForegroundLinearLayout {
    Context mContext;
    BukoliPoint currentData;
    TextView tvTitle, btSend;
    ImageView ivPoint;

    public CVPointItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init();
        setForeground(ContextCompat.getDrawable(mContext, R.drawable.flat_button_light));
        setClickable(true);
    }

    public CVPointItem(Context context) {
        super(context);
    }

    public void init() {
        LayoutInflater.from(mContext).inflate(R.layout.customview_point_items, this);
        //
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        btSend = (TextView) findViewById(R.id.btSend);
        ivPoint = (ImageView) findViewById(R.id.ivPoint);

        //Selector
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, ShapeCreator.getInstance().createStrokeButtonPressed());
        stateListDrawable.addState(StateSet.WILD_CARD, ShapeCreator.getInstance().createStrokeButton());

        btSend.setBackground(stateListDrawable);
        btSend.setTextColor(Bukoli.getInstance().getButtonTextColor());

    }

    public void setKayitData(final BukoliPoint rowData, final OnButtonClickListener onButtonClickListener) {

        if (rowData != null) {
            currentData = rowData;

            tvTitle.setText(rowData.getName());
            Glide.with(getContext()).load(rowData.getModel().getUrl()).crossFade().into(ivPoint);

            btSend.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClickListener.onClick(rowData);
//                    if (rowData.getModel() != null) {
//                        DialogHelper.showNoktalarimDialog(getContext(), rowData.getModel(), callback, rowData.getIndex(), rowData.is_favorite());
//                    } else {
//                        //TODO something
//                    }
                }
            });
        }
    }


    public interface OnButtonClickListener {
        void onClick(BukoliPoint bukoliPoint);

    }

}