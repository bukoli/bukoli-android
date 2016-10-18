package com.mobillium.bukoliandroidsdk.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.StateSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobillium.bukoliandroidsdk.Bukoli;
import com.mobillium.bukoliandroidsdk.R;
import com.mobillium.bukoliandroidsdk.callback.InfoCallback;
import com.mobillium.bukoliandroidsdk.models.DialogModel;
import com.mobillium.bukoliandroidsdk.models.DialogPointModel;
import com.mobillium.bukoliandroidsdk.ui.customview.BukoliEditText;


/**
 * Created by oguzhandongul on 03/10/16.
 */
public class DialogHelper {
    private static int POSITIVE_BUTTON = 1;
    private static int NEGATIVE_BUTTON = 0;
    private static Dialog dialog;

    public static void showCommonDialog(final Context context, DialogModel model, final DialogCallback callback) {

        dismissCurrentDialog();
        //Initialise Dialog
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_template);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);

        //Create Views
        TextView btNegative = (TextView) dialog.findViewById(R.id.btNegative);
        TextView btPositive = (TextView) dialog.findViewById(R.id.btPositive);
        TextView tvDialogTitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);
        TextView tvDialogDesc = (TextView) dialog.findViewById(R.id.tvDialogDesc);
        ImageView icDialogTitle = (ImageView) dialog.findViewById(R.id.icDialogTitle);

        //Set data
        tvDialogTitle.setText(model.getTitle());
        tvDialogDesc.setText(model.getDesc());
        btPositive.setText(model.getBtPositive());
        btPositive.setBackgroundColor(Bukoli.getInstance().getButtonBackgroundColor());
        btPositive.setTextColor(Bukoli.getInstance().getButtonTextColor());
        btNegative.setText(model.getBtNegative());
        icDialogTitle.setImageResource(model.getIconResId());

        //Set ClickListeners
        btPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.pressed(POSITIVE_BUTTON);
                dialog.dismiss();
            }
        });
        btNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.pressed(NEGATIVE_BUTTON);
                dialog.dismiss();
            }
        });

        //Show Dialog
        dialog.show();

    }

    public static void dismissCurrentDialog() {

        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public static void showInfoDialog(final Context context, DialogModel model, final InfoCallback callback) {

        dismissCurrentDialog();
        //Initialise Dialog
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info);
        dialog.setCanceledOnTouchOutside(false);

        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        //Create Views
        TextView tvDialogDesc = (TextView) dialog.findViewById(R.id.tvDialogDesc);
        TextView tvRow1 = (TextView) dialog.findViewById(R.id.tvRow1);
        ImageView ivCloseButton = (ImageView) dialog.findViewById(R.id.ivCloseButton);
//        //Set data
        tvDialogDesc.setText(String.format(context.getString(R.string.dialog_description), model.getBrandName2()));
        tvRow1.setText(String.format(context.getString(R.string.dialog_row_1), model.getBrandName()));


        //Set ClickListeners
        ivCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                callback.onDismiss();
            }
        });
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                callback.onDisplay();
            }
        });
        //Show Dialog
        dialog.show();

    }


    public static void showNoktalarimDialog(final Context context, DialogPointModel model, final DialogCallback callback, String index, boolean selected) {

        dismissCurrentDialog();
        //Initialise Dialog
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_bukoli_point);
        dialog.setCanceledOnTouchOutside(false);


        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);


        //Create Views
        TextView btSend = (TextView) dialog.findViewById(R.id.btSend);
        TextView tvDialogTitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);
        TextView tvDialogNoktalarimAddress = (TextView) dialog.findViewById(R.id.tvDialogNoktalarimAddress);
        TextView tvDialogSaatler = (TextView) dialog.findViewById(R.id.tvDialogSaatler);
        ImageView ivIconItem = (ImageView) dialog.findViewById(R.id.ivIconItem);
        ImageView ivPoint = (ImageView) dialog.findViewById(R.id.ivPoint);
        //Set data
        tvDialogTitle.setText(model.getTitle());
        tvDialogSaatler.setText(model.getHours());
        tvDialogNoktalarimAddress.setText(model.getAddress());

        Glide.with(context).load(model.getUrl()).crossFade().into(ivPoint);

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
                callback.pressed(POSITIVE_BUTTON);
                dialog.dismiss();
            }
        });

        ivIconItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.pressed(NEGATIVE_BUTTON);
                dialog.dismiss();
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                callback.pressed(NEGATIVE_BUTTON);
            }
        });

        //Show Dialog
        dialog.show();


    }

    //
    public static void showPhoneNumberDialog(final Context context, DialogModel model, final DialogCallback callback) {

        dismissCurrentDialog();
        //Initialise Dialog
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_phone_number);
        dialog.setCanceledOnTouchOutside(false);

        //Grab the window of the dialog, and change the width
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();
        lp.copyFrom(window.getAttributes());
        //This makes the dialog take up the full width
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        //Create Views
        TextView btSend = (TextView) dialog.findViewById(R.id.btSend);
        TextView tvDialogTitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);
        TextView tvDialogDesc = (TextView) dialog.findViewById(R.id.tvDialogDesc);
        final BukoliEditText etDialogTakip = (BukoliEditText) dialog.findViewById(R.id.etDialogTakip);
        etDialogTakip.setListener();
        ImageView ivIconItem = (ImageView) dialog.findViewById(R.id.ivIconItem);

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
                if (!TextUtils.isEmpty(etDialogTakip.getOnlyNumberText()) && etDialogTakip.getOnlyNumberText().length() == 11) {
                    callback.pressed(POSITIVE_BUTTON, etDialogTakip.getOnlyNumberText(), "");
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, context.getString(R.string.sdk_number_missing_error), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivIconItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.pressed(NEGATIVE_BUTTON, "", "");
                dialog.dismiss();
            }
        });

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                callback.pressed(NEGATIVE_BUTTON, "", "");
            }
        });
        //Show Dialog
        dialog.show();

    }


}
