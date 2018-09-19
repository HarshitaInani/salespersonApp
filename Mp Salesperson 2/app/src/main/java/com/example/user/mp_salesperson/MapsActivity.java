package com.example.user.mp_salesperson;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.adapters.MyBidAdapter;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private GoogleMap mMap;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    LocationRequest mLocationRequest;
    Dialog mDialog;
    AnimationDrawable animation;
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    String peopleId;
    double Currentlat ;
    double Currentlng ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);


        peopleId =  mRetailerBean.getCustomerId();
        callAqueryAllBeat();


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
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        }
        else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {

        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Currentlat = location.getLatitude();
        Currentlng = location.getLongitude();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
       // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.current));
        mCurrLocationMarker = mMap.addMarker(markerOptions);

        //move map camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
       /* googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);*/
        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
      /*  mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener()
        {

            @Override
            public boolean onMarkerClick(Marker arg0) {
                System.out.println("MYMAP");
                if(arg0.getTitle().equals("MyHome")) // if marker source is clicked
                    Toast.makeText(MapsActivity.this, arg0.getTitle(), Toast.LENGTH_SHORT).show();// display toast
                return true;
            }

        });*/


    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    public boolean checkLocationPermission(){
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                //Prompt the user once explanation has been shown
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted. Do the
                    // contacts-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {
                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            // You can add here other case statements according to your requirement.
        }
    }

    public void callAqueryAllBeat() {

        String urlRemote = Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=";
        System.out.println("urlRemote::"+urlRemote);
        showLoading();

        new AQuery(getApplicationContext()).ajax(urlRemote, null, JSONArray.class, new AjaxCallback<JSONArray>() {

            @Override
            public void callback(String url, JSONArray json, AjaxStatus status) {

                System.out.println("json::"+json);

                if (json == null) {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }


                    Toast.makeText(MapsActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();


                } else {

                    //  Toast.makeText(MyBeatActivity.this, json.toString(), Toast.LENGTH_SHORT).show();

                    try {
                        jsonArray = new JSONArray(json.toString());

                        for (int i=0; i < jsonArray.length(); i++) {

                            jsonObject = jsonArray.getJSONObject(i);

                            double nearLat= Double.parseDouble(jsonObject.getString("lat"));
                            double nearLong=Double.parseDouble(jsonObject.getString("lg"));
                            String Address=jsonObject.getString("BillingAddress");
                            String ShopName=jsonObject.getString("ShopName");
                            //  Toast.makeText(DaysBidActivity.this, jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();
                           // getNearesLocation(nearLat,nearLong);
                            getLocationNearestAddress(getApplicationContext(), Address,ShopName);
                        }

                        //   Toast.makeText(MyBeatActivity.this, "Json Array " + jsonArray.toString() , Toast.LENGTH_SHORT).show();



                        // Toast

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                  //  myBidAdapter = new MyBidAdapter(context, jsonArray, jsonArray.length());
                  //  recyclerView.setAdapter(myBidAdapter);

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }


                }


            }
        });


    }
    public LatLng getLocationNearestAddress(Context context, String strAddress,String shopName) {

        Geocoder coder = new Geocoder(context);
        List<android.location.Address> address;
        LatLng p1 = null;
        LatLng p2 = null;
        LatLng p3 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            System.out.println("Latitude is::::"+ location.getLatitude());
            //  latLng1 = new LatLng(location.getLatitude(),location.getLongitude());
            System.out.println("Currentlat::"+Currentlat);
            System.out.println("Currentlng::"+Currentlng);
            System.out.println("Serverlat::"+location.getLatitude());
            System.out.println("Serverlng::"+location.getLongitude());

                 // 22.7195687, 75.8577258
            // lat1 and lng1 are the values of a previously stored location
            if (distance(location.getLatitude(), location.getLongitude(), Currentlat, Currentlng) < 0.7) { // if distance < 0.1 miles we take locations as equal(1.12654 KM)

                System.out.println("nearlat::"+location.getLatitude());
                System.out.println("nearlng::"+location.getLongitude());
                     LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title(shopName);
           // markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.shop_icon));
            mCurrLocationMarker = mMap.addMarker(markerOptions);
                if (mDialog.isShowing()) {
                    animation.stop();
                    mDialog.dismiss();
                }

                // Toast.makeText(NoteActivity.this, "Event this place", Toast.LENGTH_SHORT).show();
            }else
            {
                if (mDialog.isShowing()) {
                    animation.stop();
                    mDialog.dismiss();
                }
                 Toast.makeText(MapsActivity.this, "No Nearest Outlets", Toast.LENGTH_SHORT).show();
            }
            //    LatLng latLng = new LatLng(jsonArray.getJSONObject(i).getDouble("Latitude"),jsonArray.getJSONObject(i).getDouble("Longitude"));

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
    public void showLoading() {


        mDialog = new Dialog(MapsActivity.this);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.progress_dialog);
        ((TextView) mDialog.findViewById(R.id.progressText)).setText("Logging in please wait...");
        ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
        la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
        animation = (AnimationDrawable) la.getBackground();
        animation.start();
        mDialog.setCancelable(true);
        mDialog.show();

    }


    public void getNearesLocation( double nearLatitude, double nearLongitude) {

        System.out.println("Currentlat::"+Currentlat);
        System.out.println("Currentlng::"+Currentlng);
        System.out.println("nearlat::"+nearLatitude);
        System.out.println("nearlat::"+nearLongitude);

        if (distance(nearLatitude, nearLongitude, Currentlat, Currentlng) < 0.1) { // if distance < 0.1 miles we take locations as equal
            System.out.println("nearlat::"+nearLatitude);
            System.out.println("nearlat::"+nearLongitude);

            //Place current location marker
        /*    LatLng latLng = new LatLng(nearLatitude, nearLongitude);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mCurrLocationMarker = mMap.addMarker(markerOptions);*/
            // Toast.makeText(NoteActivity.this, "Event this place", Toast.LENGTH_SHORT).show();
        }else
        {
            // Toast.makeText(NoteActivity.this, "No Event this Place", Toast.LENGTH_SHORT).show();
        }


    }

    /** calculates the distance between two locations in MILES */
    private double distance(double lat1, double lng1, double lat2, double lng2) {

        double earthRadius = 3958.75; // in miles, change to 6371 for kilometer output

        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);

        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);

        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        double dist = earthRadius * c;

        return dist; // output distance, in MILES
    }
}
