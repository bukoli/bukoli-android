package com.mobillium.bukoliandroidsdk.ui;


import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.StateSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobillium.bukoliandroidsdk.Bukoli;
import com.mobillium.bukoliandroidsdk.R;
import com.mobillium.bukoliandroidsdk.models.BukoliPoint;
import com.mobillium.bukoliandroidsdk.utils.PointCallback;
import com.mobillium.bukoliandroidsdk.utils.ShapeCreator;

public class FragmentPoint extends BaseFragment {

    private BukoliPoint bukoliPoint;
    PointCallback callback;
    int position;

    public static FragmentPoint newInstance(BukoliPoint bukoliPoint, int position, PointCallback callback) {
        FragmentPoint fragment = new FragmentPoint();
        fragment.setCallback(callback);
        fragment.setBukoliPoint(bukoliPoint);
        fragment.setPosition(position);
        return fragment;
    }

    public FragmentPoint() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.pager_bukoli_point, container, false);

        createViews(v);

        return v;
    }

    @Override
    protected void createViews(View v) {
        super.createViews(v);
        TextView btSend = (TextView) v.findViewById(R.id.btSend);
        TextView tvDialogTitle = (TextView) v.findViewById(R.id.tvDialogTitle);
        TextView tvDialogNumber = (TextView) v.findViewById(R.id.tvDialogNumber);
        TextView tvDialogNoktalarimAddress = (TextView) v.findViewById(R.id.tvDialogNoktalarimAddress);
        TextView tvDialogSaatler = (TextView) v.findViewById(R.id.tvDialogSaatler);
        TextView tvDistance = (TextView) v.findViewById(R.id.tvDistance);
        ImageView ivPoint = (ImageView) v.findViewById(R.id.ivPoint);
        ImageView ivIconItem = (ImageView) v.findViewById(R.id.ivIconItem);
        tvDialogTitle.setText(bukoliPoint.getName());
        tvDialogSaatler.setText(bukoliPoint.getFakeHours());
        tvDistance.setText(bukoliPoint.getDistance());
        tvDialogNoktalarimAddress.setText(bukoliPoint.getAddress());

        tvDistance.setBackground(ShapeCreator.createStrokeBg());

        tvDialogNumber.setText("" + (position + 1));
        Drawable markerLayer = ContextCompat.getDrawable(getContext(), R.drawable.oval_bg);
        markerLayer.mutate().setColorFilter(Bukoli.getInstance().getButtonBackgroundColor(), PorterDuff.Mode.SRC_ATOP);
        tvDialogNumber.setBackground(markerLayer);
        tvDialogNumber.setTextColor(Bukoli.getInstance().getButtonTextColor());

        Glide.with(getActivity()).load(bukoliPoint.getLarge_image_url()).crossFade().into(ivPoint);


        ivIconItem.setColorFilter(Bukoli.getInstance().getDarkThemeColor(), PorterDuff.Mode.SRC_ATOP);

        //Selector
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_pressed}, ShapeCreator.getInstance().createStrokeButtonPressed());
        stateListDrawable.addState(StateSet.WILD_CARD, ShapeCreator.getInstance().createStrokeButton());

        btSend.setBackground(stateListDrawable);
        btSend.setTextColor(Bukoli.getInstance().getButtonTextColor());
        //Set ClickListeners
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.selected(0);

            }
        });


        ivIconItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.dismissed();
            }
        });

    }

    public void setCallback(PointCallback callback) {
        this.callback = callback;
    }

    public void setBukoliPoint(BukoliPoint bukoliPoint) {
        this.bukoliPoint = bukoliPoint;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}

