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
import com.mobillium.bukoliandroidsdk.callback.CheckPointCallback;
import com.mobillium.bukoliandroidsdk.callback.InfoCallback;
import com.mobillium.bukoliandroidsdk.callback.SelectPointCallBack;
import com.mobillium.bukoliandroidsdk.models.BukoliPoint;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btBukoliPoint, btBukoliInfo;
    Button btExample1Point, btExample1Info;
    Button btExample2Point, btExample2Info;
    LinearLayout llContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Bukoli SDK with Application context and API Key
        Bukoli.sdkInitialize(getApplicationContext(), "e6fa17c9-6168-4124-87c7-4b2310d1b4f9");
        Bukoli.getInstance().setUser("1234", "", "");

        //Set Debug mode enabled or disabled
        Bukoli.getInstance().setDebugEnabled(true); // or false

        btBukoliPoint = (Button) findViewById(R.id.btBukoliPoint);
        btBukoliInfo = (Button) findViewById(R.id.btBukoliInfo);
        btExample1Point = (Button) findViewById(R.id.btExample1Point);
        btExample1Info = (Button) findViewById(R.id.btExample1Info);
        btExample2Point = (Button) findViewById(R.id.btExample2Point);
        btExample2Info = (Button) findViewById(R.id.btExample2Info);
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


    //Show Info Dialog From Activity
    private void openInfoDialog() {

        Bukoli.getInstance().showInfoDialog(this, new InfoCallback() {
            @Override
            public void onDismiss() {
                Log.d("BUKOLI", "Dialog dismissed");
            }

            @Override
            public void onDisplay() {
                Log.d("BUKOLI", "Dialog displayed");

            }
        });


    }

    private void openCheckDialog() {
        Bukoli.getInstance().showIsActiveDialog(this, new CheckPointCallback() {
            @Override
            public void isActive(boolean active) {
                if (active) {
                    Toast.makeText(MainActivity.this, "Point is Active", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Point is deActive", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onDismiss() {

            }

            @Override
            public void onDisplay() {

            }

            @Override
            public void onError() {

            }
        });
    }

    //Start Point Selection From Activity
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

            @Override
            public void onAuthError() {
                Toast.makeText(MainActivity.this, "Authorization Error Occured", Toast.LENGTH_SHORT).show();
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
            case R.id.btBukoliCheck:
                setBukoliStyle();
                openCheckDialog();
                break;
            case R.id.btExample1Point:
                setExample1Style();
                startSelection();
                break;
            case R.id.btExample1Info:
                setExample1Style();
                openInfoDialog();
                break;
            case R.id.btExample2Point:
                setExample2Style();
                startSelection();
                break;
            case R.id.btExample2Info:
                setExample2Style();
                openInfoDialog();
                break;
        }
    }

    private void setBukoliStyle() {
        Bukoli.getInstance()
                .setBrandName("Marka")
                .setBrandName2("Marka'dan")
                .setButtonTextColor(0xFFFFFFFF)
                .setButtonBackgroundColor(0xFFF8BA1B)
                .setDarkThemeColor(0xFF3E3E3E)
                .setShowPhoneDialog(false);
    }

    private void setExample1Style() {
        Bukoli.getInstance()
                .setBrandName("Example 1")
                .setBrandName2("Example 1'den")
                .setButtonTextColor(0xFFFFFFFF)
                .setButtonBackgroundColor(0xFFAF005F)
                .setDarkThemeColor(0xFF3E3E3E)
                .setShowPhoneDialog(true);

    }

    private void setExample2Style() {

        Bukoli.getInstance()
                .setBrandName("Example 2")
                .setBrandName2("Example 2'den")
                .setButtonTextColor(0xFFFFFFFF)
                .setButtonBackgroundColor(0xFFF68B1E)
                .setDarkThemeColor(0xFF3E3E3E)
                .setShowPhoneDialog(false);


    }
}
