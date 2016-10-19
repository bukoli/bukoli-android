package com.mobillium.bukoliandroidsdk.ui.customview;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mobillium.bukoliandroidsdk.Bukoli;
import com.mobillium.bukoliandroidsdk.R;
import com.mobillium.bukoliandroidsdk.models.BukoliPoint;
import com.mobillium.bukoliandroidsdk.ui.adapter.AdapterPointFragments;
import com.mobillium.bukoliandroidsdk.utils.DialogCallback;
import com.mobillium.bukoliandroidsdk.utils.PointCallback;

import java.util.ArrayList;

/**
 * Created by oguzhandongul on 23/09/2016.
 */

public class DialogPointFragment extends DialogFragment implements DialogInterface.OnCancelListener {

    DialogCallback callback;
    PointCallback pointCallback;
    BukoliPoint selectedPoint;
    ImageView ivSwipeItem;
    ArrayList<BukoliPoint> bukoliPoints = new ArrayList<>();
    int position = 0;
    private AdapterPointFragments adapterPointFragments;
    private ViewPager viewPager;


    public DialogPointFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static DialogPointFragment newInstance(BukoliPoint currentlySelectedPoint, int position, ArrayList<BukoliPoint> bukoliPoints, DialogCallback callbackAddDialog) {
        DialogPointFragment frag = new DialogPointFragment();
        frag.setBukoliPoints(bukoliPoints);
        frag.setCallback(callbackAddDialog);
        frag.setPosition(position);
        frag.setSelectedPoint(currentlySelectedPoint);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_point_fragment, container);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        viewPager = (ViewPager) view.findViewById(R.id.vpPoints);
        ivSwipeItem = (ImageView) view.findViewById(R.id.ivSwipeItem);

        adapterPointFragments = new AdapterPointFragments(getChildFragmentManager(), bukoliPoints, new PointCallback() {
            @Override
            public void selected(int position) {
                callback.selected(viewPager.getCurrentItem());
                DialogPointFragment.this.dismiss();
            }

            @Override
            public void dismissed() {
                callback.dismissed();
                DialogPointFragment.this.dismiss();
            }
        });
        viewPager.setAdapter(adapterPointFragments);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int pos) {
                if (position != pos) {
                    if (ivSwipeItem.getVisibility() == View.VISIBLE) {
                        ivSwipeItem.setVisibility(View.GONE);
                        Bukoli.getInstance().saveSharedPref();
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        if (Bukoli.getInstance().getSharedPref()) {
            ivSwipeItem.setVisibility(View.GONE);
        } else {
            ivSwipeItem.setVisibility(View.VISIBLE);
        }

        ivSwipeItem.setColorFilter(0xFFFFFFFF, PorterDuff.Mode.SRC_ATOP);

        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume() {
        makeFullScreen();
        super.onResume();
    }

    private void makeFullScreen() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x88000000));

    }


    public void setBukoliPoints(ArrayList<BukoliPoint> bukoliPoints) {
        this.bukoliPoints = bukoliPoints;
    }

    public void setSelectedPoint(BukoliPoint selectedPoint) {
        this.selectedPoint = selectedPoint;
    }

    public void setCallback(DialogCallback callback) {
        this.callback = callback;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    @Override
    public void onCancel(DialogInterface dialog) {
        callback.dismissed();
        super.onCancel(dialog);
    }
}