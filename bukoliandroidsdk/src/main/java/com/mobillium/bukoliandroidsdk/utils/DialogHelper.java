package com.mobillium.bukoliandroidsdk.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.StateSet;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.mobillium.bukoliandroidsdk.Bukoli;
import com.mobillium.bukoliandroidsdk.R;
import com.mobillium.bukoliandroidsdk.callback.InfoCallback;
import com.mobillium.bukoliandroidsdk.callback.PointStatusCallback;
import com.mobillium.bukoliandroidsdk.models.BukoliPoint;
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

    public static void showInfoDialog(final Context context, DialogModel model, @Nullable final InfoCallback callback) {

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

//        ivCloseButton.setColorFilter(Bukoli.getInstance().getDarkThemeColor(), PorterDuff.Mode.SRC_ATOP);

        //Set ClickListeners
        ivCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (callback != null) {
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
        }
        //Show Dialog
        dialog.show();

    }


    public static void showNoktalarimDialog(final Context context, DialogPointModel model, @NonNull final DialogCallback callback, String index, boolean selected) {

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
        TextView tvDistance = (TextView) dialog.findViewById(R.id.tvDistance);
        ImageView ivIconItem = (ImageView) dialog.findViewById(R.id.ivIconItem);
        ImageView ivPoint = (ImageView) dialog.findViewById(R.id.ivPoint);
        //Set data
        tvDialogTitle.setText(model.getTitle());
        tvDialogSaatler.setText(model.getHours());
        tvDistance.setText(model.getDistance());
        tvDialogNoktalarimAddress.setText(model.getAddress());

        tvDistance.setBackground(ShapeCreator.createStrokeBg());

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


        ivIconItem.setColorFilter(Bukoli.getInstance().getDarkThemeColor(), PorterDuff.Mode.SRC_ATOP);
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
    public static void showPhoneNumberDialog(final Context context, final DialogCallback callback) {

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
                if (!TextUtils.isEmpty(etDialogTakip.getOnlyNumberText()) && etDialogTakip.getOnlyNumberText().length() == 10) {
                    callback.pressed(POSITIVE_BUTTON, etDialogTakip.getOnlyNumberText(), "");
                    dialog.dismiss();
                } else {
                    Toast.makeText(context, context.getString(R.string.sdk_number_missing_error), Toast.LENGTH_SHORT).show();
                }
            }
        });

        ivIconItem.setColorFilter(Bukoli.getInstance().getDarkThemeColor(), PorterDuff.Mode.SRC_ATOP);
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


    public static void showActivityCheckDialog(final Context context, final PointStatusCallback callback) {

        dismissCurrentDialog();
        //Initialise Dialog
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_point_active);
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
        TextView btRepeat = (TextView) dialog.findViewById(R.id.btRepeat);
        TextView btClose = (TextView) dialog.findViewById(R.id.btClose);
        TextView tvDialogTitle = (TextView) dialog.findViewById(R.id.tvDialogTitle);
        TextView tvDialogDesc = (TextView) dialog.findViewById(R.id.tvDialogDesc);
        final TextView tvDialogResult = (TextView) dialog.findViewById(R.id.tvDialogResult);
        final EditText etDialogTakip = (EditText) dialog.findViewById(R.id.etDialogTakip);
        ImageView ivIconItem = (ImageView) dialog.findViewById(R.id.ivIconItem);
        final RelativeLayout resultLayout = (RelativeLayout) dialog.findViewById(R.id.resultLayout);
        final FrameLayout progressLayout = (FrameLayout) dialog.findViewById(R.id.progressLayout);

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
                if (!TextUtils.isEmpty(etDialogTakip.getText().toString()) && etDialogTakip.getText().toString().length() == 8) {
                    Bukoli.getInstance().checkPointStatus(etDialogTakip.getText().toString(), new PointStatusCallback() {
                        @Override
                        public void active(BukoliPoint selectedPoint) {
                            progressLayout.setVisibility(View.GONE);
                            resultLayout.setVisibility(View.VISIBLE);
                            tvDialogResult.setText(selectedPoint.getName() +" adlı Bukoli noktası aktiftir.");
                            callback.active(selectedPoint);
                            Log.d("BUKOLI", "Point is active");
                        }

                        @Override
                        public void passive(BukoliPoint selectedPoint) {
                            progressLayout.setVisibility(View.GONE);
                            resultLayout.setVisibility(View.VISIBLE);
                            tvDialogResult.setText(selectedPoint.getName() +" adlı Bukoli noktası aktif değildir.");
                            callback.passive(selectedPoint);
                            Log.d("BUKOLI", "Point is not active");
                        }

                        @Override
                        public void onError() {
                            progressLayout.setVisibility(View.GONE);
                            resultLayout.setVisibility(View.VISIBLE);
                            tvDialogResult.setText("Nokta bulunamadı.");
                            callback.onError();
                            Log.d("BUKOLI", "Point not found");
                        }
                    });


                } else {
                    Toast.makeText(context, context.getString(R.string.sdk_code_missing_error), Toast.LENGTH_SHORT).show();
                }
            }
        });

        btRepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultLayout.setVisibility(View.GONE);
            }
        });

        btClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        ivIconItem.setColorFilter(Bukoli.getInstance().getDarkThemeColor(), PorterDuff.Mode.SRC_ATOP);
        ivIconItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        //Show Dialog
        dialog.show();

    }


}
