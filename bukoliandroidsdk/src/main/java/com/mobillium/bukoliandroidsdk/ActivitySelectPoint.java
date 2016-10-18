package com.mobillium.bukoliandroidsdk;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.reflect.TypeToken;
import com.mobillium.bukoliandroidsdk.models.AutoCompleteItem;
import com.mobillium.bukoliandroidsdk.models.BukoliLocation;
import com.mobillium.bukoliandroidsdk.models.BukoliPoint;
import com.mobillium.bukoliandroidsdk.models.DialogModel;
import com.mobillium.bukoliandroidsdk.models.DialogPointModel;
import com.mobillium.bukoliandroidsdk.models.ResponsePoints;
import com.mobillium.bukoliandroidsdk.ui.BaseActivity;
import com.mobillium.bukoliandroidsdk.ui.FragmentSearchList;
import com.mobillium.bukoliandroidsdk.ui.adapter.AdapterPoints;
import com.mobillium.bukoliandroidsdk.ui.customview.CVPointItem;
import com.mobillium.bukoliandroidsdk.utils.BukoliLogger;
import com.mobillium.bukoliandroidsdk.utils.DialogCallback;
import com.mobillium.bukoliandroidsdk.utils.DialogHelper;
import com.mobillium.bukoliandroidsdk.webservice.ServiceCallback;
import com.mobillium.bukoliandroidsdk.webservice.ServiceException;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mobillium.bukoliandroidsdk.Bukoli.TYPE_LIST;
import static com.mobillium.bukoliandroidsdk.Bukoli.TYPE_MAP;


public class ActivitySelectPoint extends BaseActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, SearchView.OnQueryTextListener, com.google.android.gms.location.LocationListener {
    public static final int REQUEST_FINE_LOCATION = 1;
    ArrayList<BukoliPoint> bukoliPoints = new ArrayList<>();
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private GoogleApiClient mGoogleApiClient;
    double currentLatitude = 29.0092983;
    double currentLongitude = 41.043609;
    double targetLatitude = 29.0092983;
    double targetLongitude = 41.043609;
    private LocationRequest mLocationRequest;
    ProgressDialog progressDialog;

    AppBarLayout appBarLayout;
    Toolbar toolbar;
    String lastSearchId = "";

    ArrayList<MarkerOptions> markers = new ArrayList<>();
    ServiceCallback callbackPoints, callbackAutoComplete;
    DialogCallback callbackAddDialog;
    private boolean reqDone = false;


    private final Handler mHandler = new Handler();
    private final Runnable mDelayedLoad = new Runnable() {
        @Override
        public void run() {
            makeAutoCompleteReq();
        }
    };
    private String mQuery = "";
    EditText etSearchMain;
    private static final long SEARCH_DELAY_MS = 1000L;
    private ProgressBar progressBar;
    private LinearLayout rlSearchView;
    private ImageView search_icon, search_icon_left;
    private boolean canPinLocation = true;
    private boolean canFocusMap = true;

    CameraPosition lastPosition;
    Marker lastOpenned = null;
    FloatingActionButton fab, fab2;

    boolean canmakeSearch = true;

    BukoliPoint currentlySelectedPoint = null;
    int currentlySelectedPointIndex;

    DialogCallback callbackDialog;
    DialogCallback callbackDialogLoc;

    private List<DataUpdateListener> mListeners;

    //List
    RecyclerView recyclerView;
    AdapterPoints adapterNoktalarimItems;

    View marker, centerMarker;
    TextView markerText;
    ImageView markerImage, centerImage, ivCenter;
    RelativeLayout rlCenterContainer;
    Handler mapHandler;
    boolean canMakeReq = false;

    @Override
    protected void onResume() {
        super.onResume();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
            BukoliLogger.writeInfoLog(getString(R.string.sdk_map_services_disconnected));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_select);

        initializeToolbar();
        createCallBacks();
        createViews();
        initilizeMarkers();
        setListeners();
        createCallBacks();
        initCollapsingToolbar();


        if (isOnline()) {
            BukoliLogger.writeInfoLog(getString(R.string.sdk_connected));
            canMakeReq = true;
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();

            // Create the LocationRequest object
            mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                    .setFastestInterval(1 * 1000); // 1 second, in milliseconds

            getCurrentLocation();

            mapHandler = new Handler();

        } else {
            BukoliLogger.writeInfoLog(getString(R.string.sdk_not_connected));
        }


    }

    Runnable mapRunnable = new Runnable() {
        @Override
        public void run() {

            if (Math.abs(lastPosition.target.latitude - mMap.getCameraPosition().target.latitude) > 0.0001 && Math.abs(lastPosition.target.longitude - mMap.getCameraPosition().target.longitude) > 0.0001) {
                if (canMakeReq) {
                    LatLng center = mMap.getCameraPosition().target;
                    targetLongitude = center.longitude;
                    targetLatitude = center.latitude;
                    mMap.clear();
                    adapterNoktalarimItems.clearItems();
                    canFocusMap = false;
                    canMakeReq = false;
                    makePointsRequest();
                    lastPosition = mMap.getCameraPosition();
                }
            }
        }
    };

    @Override
    protected void createViews() {
        super.createViews();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        rlSearchView = (LinearLayout) findViewById(R.id.rlSearchView);
        ivCenter = (ImageView) findViewById(R.id.ivCenter);
        rlCenterContainer = (RelativeLayout) findViewById(R.id.rlCenterContainer);
        search_icon = (ImageView) findViewById(R.id.search_icon);
        search_icon_left = (ImageView) findViewById(R.id.search_icon_left);
        etSearchMain = (EditText) findViewById(R.id.etSearchMain);
        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //List Items
        adapterNoktalarimItems = new AdapterPoints(bukoliPoints, ActivitySelectPoint.this, onButtonClickListener);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ActivitySelectPoint.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapterNoktalarimItems);

        //SearchView Style
        Drawable searchLayer = ContextCompat.getDrawable(this, R.drawable.search_layer_pressed);
        searchLayer.mutate().setColorFilter(Bukoli.getInstance().getButtonTextColor(), PorterDuff.Mode.SRC_ATOP);
        rlSearchView.setBackground(searchLayer);
        search_icon.setColorFilter(Bukoli.getInstance().getButtonTextColor());
        search_icon_left.setColorFilter(Bukoli.getInstance().getButtonTextColor());
        etSearchMain.setTextColor(Bukoli.getInstance().getButtonTextColor());
        etSearchMain.setHintTextColor(Bukoli.getInstance().getButtonTextColor());
        setCursorDrawableColor(etSearchMain, Bukoli.getInstance().getButtonTextColor());


        //FAB List-Map Style
        fab.setBackgroundTintList(ColorStateList.valueOf(Bukoli.getInstance().getButtonBackgroundColor()));
        setFabIcon(TYPE_MAP);
        //FAB Current Location Style
        Drawable myFabSrc = ContextCompat.getDrawable(this, R.drawable.icon_target);
        Drawable willBeWhite = myFabSrc.getConstantState().newDrawable();
        willBeWhite.mutate().setColorFilter(Bukoli.getInstance().getDarkThemeColor(), PorterDuff.Mode.SRC_ATOP);
        fab2.setImageDrawable(willBeWhite);
        fab2.setBackgroundTintList(ColorStateList.valueOf(Bukoli.getInstance().getButtonTextColor()));

    }

    private void initializeToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Bukoli.getInstance().getButtonBackgroundColor()));
    }

    private void initilizeMarkers() {
        marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        markerText = (TextView) marker.findViewById(R.id.tvNumber);
        markerImage = (ImageView) marker.findViewById(R.id.markerImage);
        markerText.setText("1");
        markerImage.setColorFilter(Bukoli.getInstance().getButtonBackgroundColor(), PorterDuff.Mode.SRC_ATOP);

        Drawable markerLayer = ContextCompat.getDrawable(this, R.drawable.timer_circle_stroke);
        markerLayer.mutate().setColorFilter(Bukoli.getInstance().getButtonTextColor(), PorterDuff.Mode.SRC_ATOP);
        markerText.setBackground(markerLayer);

        markerText.setTextColor(Bukoli.getInstance().getButtonTextColor());

        centerMarker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_center_layout, null);
        centerImage = (ImageView) centerMarker.findViewById(R.id.centerImage);
        centerImage.setColorFilter(Bukoli.getInstance().getDarkThemeColor(), PorterDuff.Mode.SRC_ATOP);


    }

    private void setFabIcon(int type) {

        Drawable myFabSrc = null;

        switch (type) {
            case TYPE_MAP:
                myFabSrc = ContextCompat.getDrawable(this, R.drawable.icon_list);
                break;
            case TYPE_LIST:
                myFabSrc = ContextCompat.getDrawable(this, R.drawable.icon_map);
                break;
            default:
                myFabSrc = ContextCompat.getDrawable(this, R.drawable.icon_list);
        }

        Drawable willBeWhite = myFabSrc.getConstantState().newDrawable();
        willBeWhite.mutate().setColorFilter(Bukoli.getInstance().getButtonTextColor(), PorterDuff.Mode.SRC_ATOP);
        fab.setImageDrawable(willBeWhite);
    }

    @Override
    protected void setListeners() {
        super.setListeners();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (recyclerView.getVisibility() == View.VISIBLE) {
                    recyclerView.setVisibility(View.GONE);
                    rlCenterContainer.setVisibility(View.VISIBLE);
                    setFabIcon(TYPE_MAP);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                    rlCenterContainer.setVisibility(View.GONE);
                    setFabIcon(TYPE_LIST);
                }
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                canPinLocation = true;
                getCurrentLocation();
            }
        });

        etSearchMain.addTextChangedListener(mTextEditorWatcher);

        search_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                canFocusMap = true;
                search_icon.setVisibility(View.GONE);
                hideKeyboardIfOpen(etSearchMain);
                setSearchString("");
            }
        });

    }

    @Override
    protected void createCallBacks() {
        super.createCallBacks();
        mListeners = new ArrayList<>();

        callbackDialog = new DialogCallback() {
            @Override
            public void pressed(int whichButton) {
                finish();
            }

            @Override
            public void pressed(int whichButton, String takip, String siparis) {

            }
        };

        callbackDialogLoc = new DialogCallback() {
            @Override
            public void pressed(int whichButton) {
                switch (whichButton) {
                    case POSITIVE_BUTTON:
                        Intent callGPSSettingIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(callGPSSettingIntent, 1);
                        break;
                    case NEGATIVE_BUTTON:
                        canPinLocation = false;
                        handleNewLocation(null, true);
                        break;
                }
            }

            @Override
            public void pressed(int whichButton, String takip, String siparis) {

            }
        };

        callbackAddDialog = new DialogCallback() {
            @Override
            public void pressed(int whichButton) {
                switch (whichButton) {
                    case POSITIVE_BUTTON:
                        if (Bukoli.getInstance().isShowPhoneDialog()) {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    DialogModel dialogModel = new DialogModel("", "", "", "", R.drawable.icon_map);
                                    DialogHelper.showPhoneNumberDialog(ActivitySelectPoint.this, dialogModel, new DialogCallback() {
                                        @Override
                                        public void pressed(int whichButton) {

                                        }

                                        @Override
                                        public void pressed(int whichButton, String takip, String siparis) {

                                            if (TextUtils.isEmpty(takip)) {
                                                refreshPins();
                                            } else {
                                                Bukoli.getInstance().getCallBack().onSuccess(currentlySelectedPoint, takip);
                                                finish();
                                            }
                                        }
                                    });
                                }
                            }, 500);


                        } else {
                            Bukoli.getInstance().getCallBack().onSuccess(currentlySelectedPoint, null);
                            finish();

                        }
                        break;
                    case NEGATIVE_BUTTON:
                        refreshPins();
                        currentlySelectedPoint = null;
                        break;
                }
            }

            @Override
            public void pressed(int whichButton, String takip, String siparis) {

            }
        };


        callbackPoints = new ServiceCallback() {
            @Override
            public void done(String result, ServiceException e) {

                canMakeReq = true;
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }

                try {
                    if (e == null) {
                        ResponsePoints responsePoints = Bukoli.getInstance().getGson().fromJson(result, ResponsePoints.class);
                        markers.clear();

                        for (int i = 0; i < responsePoints.getData().size(); i++) {

                            BukoliPoint current = responsePoints.getData().get(i);
                            current.setIndex("" + (bukoliPoints.size() + 1));
                            DialogPointModel dialogPointModel = new DialogPointModel(current.getName(), "", "", "", R.drawable.icon_koli_blank, current.getAddress(), current.getFakeHours(), current.getLarge_image_url(), current.getName());
                            current.setModel(dialogPointModel);
                            adapterNoktalarimItems.addItem(current, bukoliPoints.size());
                            MarkerOptions tempMarker;
                            if (current.is_locker()) {
                                tempMarker = new MarkerOptions().position(new LatLng(Double.parseDouble(current.getLocationBukoli().getLatitude()), Double.parseDouble(current.getLocationBukoli().getLongitude()))).title(current.getName()).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(current.getIndex())));
                            } else {
                                tempMarker = new MarkerOptions().position(new LatLng(Double.parseDouble(current.getLocationBukoli().getLatitude()), Double.parseDouble(current.getLocationBukoli().getLongitude()))).title(current.getName()).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(current.getIndex())));
                            }
                            mMap.addMarker(tempMarker);
                            markers.add(tempMarker);
                        }

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        for (MarkerOptions m : markers) {
                            builder.include(m.getPosition());
                        }
                        LatLngBounds bounds = builder.build();


                        if (canFocusMap) {
                            int padding = Bukoli.getInstance().convertDpiToPixel(50); // offset from edges of the map in pixels
                            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
                        }
                        lastPosition = mMap.getCameraPosition();
                        mMap.setOnCameraIdleListener(cameraIdleListener);
                        mMap.setOnCameraMoveStartedListener(cameraMoveStartedListener);


                        setSearchString(responsePoints.getAddress());

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        };

        callbackAutoComplete = new ServiceCallback() {
            @Override
            public void done(String result, ServiceException e) {
                try {
                    progressBar.setVisibility(View.GONE);
                    progressBar.setIndeterminate(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                if (e == null) {

                    ArrayList<AutoCompleteItem> sorgulist = Bukoli.getInstance().getGson().fromJson(result, new TypeToken<List<AutoCompleteItem>>() {
                    }.getType());
                    ArrayList<AutoCompleteItem> list = new ArrayList<>();
                    list.addAll(sorgulist);
                    dataUpdated(list);
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        // Add a marker in Istanbul and move the camera

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 14);
        mMap.animateCamera(cameraUpdate);


        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            public boolean onMarkerClick(Marker marker) {
                // Check if there is an open info window
                if (lastOpenned != null) {
                    // Close the info window
                    lastOpenned.hideInfoWindow();

                    // Is the marker the same marker that was already open
                    if (lastOpenned.equals(marker)) {
                        // Nullify the lastOpened object
                        lastOpenned = null;
                        // Return so that the info window isn't opened again
                        return true;
                    }
                }

                // Open the info window for the marker
                marker.showInfoWindow();
                findPoint(marker);
                // Re-assign the last opened such that we can close it later
                lastOpenned = marker;
                // Event was handled by our code do not launch default behaviour.
                return true;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                findPoint(marker);

            }
        });

    }

    private void findPoint(Marker marker) {
        for (int i = 0; i < bukoliPoints.size(); i++) {
            currentlySelectedPoint = bukoliPoints.get(i);
            if (marker.getTitle().equals(currentlySelectedPoint.getName())) {
                currentlySelectedPointIndex = i;
                DialogHelper.showNoktalarimDialog(ActivitySelectPoint.this, currentlySelectedPoint.getModel(), callbackAddDialog, currentlySelectedPoint.getIndex(), currentlySelectedPoint.is_favorite());
                return;
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        BukoliLogger.writeInfoLog(getString(R.string.sdk_map_services_connected));
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
            } else {
                handleNewLocation(location, false);
            }
        } else {

        }
    }

    @Override
    public void onLocationChanged(Location location) {
        handleNewLocation(location, false);
    }


    @Override
    public void onConnectionSuspended(int i) {
        BukoliLogger.writeInfoLog(getString(R.string.sdk_map_services_suspended));
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this, CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        } else {
            BukoliLogger.writeErrorLog(getString(R.string.sdk_map_services_failed) + connectionResult.getErrorCode());
        }
    }

    private void handleNewLocation(Location location, boolean isFake) {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (!isFake) {
            currentLatitude = location.getLatitude();
            currentLongitude = location.getLongitude();
        }


        if (canPinLocation) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatitude, currentLongitude), 14);
            mMap.animateCamera(cameraUpdate);
            lastPosition = mMap.getCameraPosition();
            canPinLocation = false;

        }

        Bukoli.getInstance().setLastLocation(new BukoliLocation("" + currentLatitude, "" + currentLongitude));
        if (!reqDone) {
            reqDone = true;
            targetLongitude = currentLongitude;
            targetLatitude = currentLatitude;
            makePointsRequest();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void initCollapsingToolbar() {
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setContentScrimColor(getResources().getColor(R.color.bukoliYellow));
    }


    @Override
    public boolean onQueryTextChange(String query) {
        // Here is where we are going to implement our filter logic
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }


    private void makePointsRequest() {
        Map<String, String> params = new HashMap<>();
        params.put("geocode", "1");
        params.put("latitude", "" + targetLatitude);
        params.put("longitude", "" + targetLongitude);
        ServiceOperations.serviceReq(getApplicationContext(), Request.Method.GET, "point", params, callbackPoints);

        showProgress(getString(R.string.sdk_points_loading));


    }

    private void makePointsSearchRequest() {

        mMap.clear();
        adapterNoktalarimItems.clearItems();
        Map<String, String> params = new HashMap<>();
        params.put("page", "1");
        params.put("place_id", lastSearchId);
        ServiceOperations.serviceReq(getApplicationContext(), Request.Method.GET, "point", params, callbackPoints);

        showProgress(getString(R.string.sdk_points_loading));
    }

    private void makeAutoCompleteReq() {
        if (etSearchMain.getText().length() > 0) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
            Map<String, String> params = new HashMap<>();
            params.put("keyword", mQuery);
            ServiceOperations.serviceReq(getApplicationContext(), Request.Method.GET, "point/autocomplete", params, callbackAutoComplete);
        }
    }


    private boolean verifyLocation() {

        try {
            LocationManager locationManager = (LocationManager) getSystemService(
                    Context.LOCATION_SERVICE);
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                DialogModel modelLocation = new DialogModel(getString(R.string.dialog_gps_title), getString(R.string.dialog_gps_text), getString(R.string.dialog_gps_settings_button), getString(R.string.dialog_gps_cancel_button), R.drawable.icon_map);
                DialogHelper.showCommonDialog(ActivitySelectPoint.this, modelLocation, callbackDialogLoc);
            } else {
                if (mGoogleApiClient.isConnected()) {
                    if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            || ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                        if (location == null) {
                            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                        } else {
                            handleNewLocation(location, false);
                        }
                    }
                } else {
                    mGoogleApiClient.connect();

                }
                return true;
            }
        } catch (RuntimeException e) {
            e.printStackTrace();

        }
        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            switch (resultCode) {
                case 0:
                    LocationManager locationManager = (LocationManager) getSystemService(
                            Context.LOCATION_SERVICE);
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                        showProgress(getString(R.string.location_loading));
                    }
                    break;
            }
        }
    }

    private void showProgress(String message) {
        progressDialog = new ProgressDialog(ActivitySelectPoint.this);
        progressDialog.setMessage(message);
        try {
            Drawable drawable = new ProgressBar(ActivitySelectPoint.this).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(Bukoli.getInstance().getButtonBackgroundColor(), PorterDuff.Mode.SRC_IN);
            progressDialog.setIndeterminateDrawable(drawable);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
    }

    private void getCurrentLocation() {

        String locationPermission = Manifest.permission.ACCESS_FINE_LOCATION;
        int hasPermission = ContextCompat.checkSelfPermission(getApplicationContext(), locationPermission);
        String[] permissions = new String[]{locationPermission};
        if (hasPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ActivitySelectPoint.this, permissions, REQUEST_FINE_LOCATION);
        } else {
            // We already have permission
            verifyLocation();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted,

                    verifyLocation();
                } else {
                    // functionality that depends on this permission.
                    handleNewLocation(null, true);
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    // Convert a view to bitmap
    private Bitmap createDrawableFromView(String num) {

        markerText.setText(num);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (canmakeSearch) {
                if (s.length() > 0) {
                    showSearchFragment();
                    mHandler.removeCallbacks(mDelayedLoad);
                    if (!TextUtils.isEmpty(s.toString())) {
                        mQuery = s.toString();
                        mHandler.postDelayed(mDelayedLoad, SEARCH_DELAY_MS);
                    }
                } else if (s.length() == 0) {
                    hideSearchFragment();
                }
            }

        }
    };


    private void showSearchFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            FragmentSearchList fragmentSearchList = new FragmentSearchList();
            getSupportFragmentManager().beginTransaction().add(R.id.frSearchContainer, fragmentSearchList).addToBackStack(null).commit();
            search_icon.setVisibility(View.VISIBLE);
        }
    }

    private void hideSearchFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().popBackStack();

        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            getSupportFragmentManager().popBackStack();
            etSearchMain.setText("");
        } else {
            if (currentlySelectedPoint == null) {
                Bukoli.getInstance().getCallBack().onCancel();
            } else {
                Bukoli.getInstance().getCallBack().onError(currentlySelectedPoint);
            }
            super.onBackPressed();
        }

    }


    public void setSearchData(String id, String name) {
        canmakeSearch = false;
        lastSearchId = id;
        canFocusMap = true;
        makePointsSearchRequest();
        hideSearchFragment();
        hideKeyboardIfOpen(etSearchMain);
        etSearchMain.setText(name);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                canmakeSearch = true;
            }
        }, 1000);


    }

    public void setSearchString(String name) {
        canmakeSearch = false;
        canFocusMap = true;
        hideSearchFragment();
        hideKeyboardIfOpen(etSearchMain);
        etSearchMain.setText(name);
        search_icon.setVisibility(View.VISIBLE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                canmakeSearch = true;
            }
        }, 1000);


    }

    public void hideKeyboardIfOpen(View view) {

        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void refreshPins() {
        markers.clear();
        mMap.clear();
        for (int i = 0; i < bukoliPoints.size(); i++) {

            BukoliPoint current = bukoliPoints.get(i);
            MarkerOptions tempMarker;
            if (current.is_locker()) {
                tempMarker = new MarkerOptions().position(new LatLng(Double.parseDouble(current.getLocationBukoli().getLatitude()), Double.parseDouble(current.getLocationBukoli().getLongitude()))).title(current.getName()).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(current.getIndex())));
            } else {
                tempMarker = new MarkerOptions().position(new LatLng(Double.parseDouble(current.getLocationBukoli().getLatitude()), Double.parseDouble(current.getLocationBukoli().getLongitude()))).title(current.getName()).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(current.getIndex())));

            }
            mMap.addMarker(tempMarker);
            markers.add(tempMarker);
        }


        canPinLocation = false;

        adapterNoktalarimItems.notifyDataSetChanged();

    }

    GoogleMap.OnCameraIdleListener cameraIdleListener = new GoogleMap.OnCameraIdleListener() {
        @Override
        public void onCameraIdle() {
            mapHandler.removeCallbacks(mapRunnable);
            mapHandler.postDelayed(mapRunnable, 750);
        }
    };

    GoogleMap.OnCameraMoveStartedListener cameraMoveStartedListener = new GoogleMap.OnCameraMoveStartedListener() {
        @Override
        public void onCameraMoveStarted(int i) {
            mapHandler.removeCallbacks(mapRunnable);
        }
    };


    public interface DataUpdateListener {
        void onDataUpdate(ArrayList<AutoCompleteItem> autoCompleteItems);
    }

    public synchronized void registerDataUpdateListener(DataUpdateListener listener) {
        mListeners.add(listener);
    }

    public synchronized void unregisterDataUpdateListener(DataUpdateListener listener) {
        mListeners.remove(listener);
    }

    public synchronized void dataUpdated(ArrayList<AutoCompleteItem> autoCompleteItems) {
        for (DataUpdateListener listener : mListeners) {
            listener.onDataUpdate(autoCompleteItems);
        }
    }

    CVPointItem.OnButtonClickListener onButtonClickListener = new CVPointItem.OnButtonClickListener() {
        @Override
        public void onClick(BukoliPoint bukoliPoint) {

            currentlySelectedPoint = bukoliPoint;
            if (Bukoli.getInstance().isShowPhoneDialog()) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        DialogModel dialogModel = new DialogModel("", "", "", "", R.drawable.icon_map);
                        DialogHelper.showPhoneNumberDialog(ActivitySelectPoint.this, dialogModel, new DialogCallback() {
                            @Override
                            public void pressed(int whichButton) {

                            }

                            @Override
                            public void pressed(int whichButton, String takip, String siparis) {

                                if (TextUtils.isEmpty(takip)) {
                                    refreshPins();
                                } else {
                                    Bukoli.getInstance().getCallBack().onSuccess(currentlySelectedPoint, takip);
                                    finish();
                                }
                            }
                        });
                    }
                }, 500);
            } else {
                Bukoli.getInstance().getCallBack().onSuccess(currentlySelectedPoint, null);
                finish();
            }
        }
    };


    public static void setCursorDrawableColor(EditText editText, int color) {
        try {
            Field fCursorDrawableRes =
                    TextView.class.getDeclaredField("mCursorDrawableRes");
            fCursorDrawableRes.setAccessible(true);
            int mCursorDrawableRes = fCursorDrawableRes.getInt(editText);
            Field fEditor = TextView.class.getDeclaredField("mEditor");
            fEditor.setAccessible(true);
            Object editor = fEditor.get(editText);
            Class<?> clazz = editor.getClass();
            Field fCursorDrawable = clazz.getDeclaredField("mCursorDrawable");
            fCursorDrawable.setAccessible(true);

            Drawable[] drawables = new Drawable[2];
            Resources res = editText.getContext().getResources();
            drawables[0] = ResourcesCompat.getDrawable(res, mCursorDrawableRes, null);
            drawables[1] = ResourcesCompat.getDrawable(res, mCursorDrawableRes, null);
            drawables[0].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            drawables[1].setColorFilter(color, PorterDuff.Mode.SRC_IN);
            fCursorDrawable.set(editor, drawables);
        } catch (final Throwable ignored) {
        }
    }
}

