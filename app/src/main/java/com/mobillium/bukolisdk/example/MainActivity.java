package com.mobillium.bukolisdk.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mobillium.bukoliandroidsdk.Bukoli;
import com.mobillium.bukoliandroidsdk.callback.InfoCallback;
import com.mobillium.bukoliandroidsdk.callback.SelectPointCallBack;
import com.mobillium.bukoliandroidsdk.models.BukoliPoint;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btBukoliPoint, btBukoliInfo;
    Button btMarkafoniPoint, btMarkafoniInfo;
    Button btKoctasPoint, btKoctasInfo;
    LinearLayout llContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bukoli.sdkInitialize(getApplicationContext(), "e6fa17c9-6168-4124-87c7-4b2310d1b4f9");


        btBukoliPoint = (Button) findViewById(R.id.btBukoliPoint);
        btBukoliInfo = (Button) findViewById(R.id.btBukoliInfo);
        btMarkafoniPoint = (Button) findViewById(R.id.btMarkafoniPoint);
        btMarkafoniInfo = (Button) findViewById(R.id.btMarkafoniInfo);
        btKoctasPoint = (Button) findViewById(R.id.btKoctasPoint);
        btKoctasInfo = (Button) findViewById(R.id.btKoctasInfo);

        llContainer = (LinearLayout) findViewById(R.id.llContainer);

        setClickListeners();

    }

    private void setClickListeners() {
        int childCount = llContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (llContainer.getChildAt(i) instanceof Button) {
                llContainer.getChildAt(i).setOnClickListener(this);
            }
        }
    }


    private void openInfoDialog() {
        Bukoli.getInstance().showInfoDialog(this, new InfoCallback() {
            @Override
            public void onDismiss() {
                Log.d("BUKOLI", "dialog dismissed");
            }

            @Override
            public void onDisplay() {
                Log.d("BUKOLI", "dialog displayed");

            }
        });
    }

    private void startSelection() {

        Bukoli.getInstance().showPointSelection(this, new SelectPointCallBack() {
            @Override
            public void onSuccess(BukoliPoint bukoliPoint, String phoneNumber) {
                if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(MainActivity.this, bukoliPoint.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, bukoliPoint.getName() + "  " + phoneNumber, Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "Selection Cancelled", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(BukoliPoint bukoliPoint) {
                Toast.makeText(MainActivity.this, "Phone Number Not Set", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btBukoliPoint:
                setBukoliStyle();
                startSelection();
                break;
            case R.id.btBukoliInfo:
                setBukoliStyle();
                openInfoDialog();
                break;
            case R.id.btMarkafoniPoint:
                setMarkafoniStyle();
                startSelection();
                break;
            case R.id.btMarkafoniInfo:
                setMarkafoniStyle();
                openInfoDialog();
                break;
            case R.id.btKoctasPoint:
                setKoctasStyle();
                startSelection();
                break;
            case R.id.btKoctasInfo:
                setKoctasStyle();
                openInfoDialog();
                break;
        }
    }

    private void setBukoliStyle() {
        Bukoli.getInstance().setBrandName("Marka");
        Bukoli.getInstance().setBrandName2("Marka'dan");
        Bukoli.getInstance().setButtonTextColor(0xFFFFFFFF);
        Bukoli.getInstance().setButtonBackgroundColor(0xFFF8BA1B);
        Bukoli.getInstance().setShowPhoneDialog(false);
    }

    private void setMarkafoniStyle() {
        Bukoli.getInstance().setBrandName("Markafoni");
        Bukoli.getInstance().setBrandName2("Markafoni'den");
        Bukoli.getInstance().setButtonTextColor(0xFFFFFFFF);
        Bukoli.getInstance().setButtonBackgroundColor(0xFFAF005F);
        Bukoli.getInstance().setShowPhoneDialog(true);
    }

    private void setKoctasStyle() {
        Bukoli.getInstance().setBrandName("Koçtaş");
        Bukoli.getInstance().setBrandName2("Koçtaş'tan");
        Bukoli.getInstance().setButtonTextColor(0xFFFFFFFF);
        Bukoli.getInstance().setButtonBackgroundColor(0xFFF68B1E);
        Bukoli.getInstance().setShowPhoneDialog(false);
    }
}
