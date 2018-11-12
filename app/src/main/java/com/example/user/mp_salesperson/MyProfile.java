package com.example.user.mp_salesperson;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.example.user.mp_salesperson.adapters.PlaceAutocompleteAdapter;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.customClasses.Utility;
import com.example.user.mp_salesperson.databinding.MyProfileBinding;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.Locale;


/**
 * Created by Krishna on 30-01-2017.
 */

public class MyProfile extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {



    public static final String TAG = "My_Profile_Activity";
    MyProfileBinding myProfileBinding;
    protected GoogleApiClient mGoogleApiClient;
    private PlaceAutocompleteAdapter mAdapter;
    private static final LatLngBounds BOUNDS_GREATER_INDIA = new LatLngBounds(new LatLng(23.63936, 68.14712), new LatLng(28.20453, 97.34466));

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(MyProfile.this.getResources().getColor(R.color.colorPrimaryDark));
            }
        }
        setContentView(R.layout.my_profile);


        Context context = MyProfile.this.getApplicationContext();

        Resources res = getLocalizedResources(context,new Locale("hi"));
        String s = res.getString(R.string.checkshippingaddress);


      /*  Button checkshippingadd1 = (Button) findViewById(R.id.btn);
        checkshippingadd1.setText(s);
*/


        myProfileBinding = DataBindingUtil.setContentView(this, R.layout.my_profile);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .build();
        myProfileBinding.myProfileEdtBillingAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final AutocompletePrediction item = mAdapter.getItem(position);
                final String placeId = item.getPlaceId();
                final CharSequence primaryText = item.getPrimaryText(null);

                Log.i(TAG, "Autocomplete item selected: " + primaryText);

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            // Request did not complete successfully
                            Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                            places.release();
                            return;
                        }
                        // Get the Place object from the buffer.
                        final Place place = places.get(0);
                        Log.i(TAG, "Place details received: " + place.getName());

                        places.release();
                    }
                });
                Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
            }
        });
        mAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient, BOUNDS_GREATER_INDIA,
                null);
        myProfileBinding.myProfileEdtBillingAdd.setAdapter(mAdapter);

        myProfileBinding.myProfileEdtShippingAdd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final AutocompletePrediction item = mAdapter.getItem(position);
                final String placeId = item.getPlaceId();
                final CharSequence primaryText = item.getPrimaryText(null);

                Log.i(TAG, "Autocomplete item selected: " + primaryText);

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, placeId);
                placeResult.setResultCallback(new ResultCallback<PlaceBuffer>() {
                    @Override
                    public void onResult(@NonNull PlaceBuffer places) {
                        if (!places.getStatus().isSuccess()) {
                            // Request did not complete successfully
                            Log.e(TAG, "Place query did not complete. Error: " + places.getStatus().toString());
                            places.release();
                            return;
                        }
                        // Get the Place object from the buffer.
                        final Place place = places.get(0);
                        Log.i(TAG, "Place details received: " + place.getName());

                        places.release();
                    }
                });
                Log.i(TAG, "Called getPlaceById to get Place details for " + placeId);
            }
        });
        myProfileBinding.myProfileEdtShippingAdd.setAdapter(mAdapter);


        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyProfile.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
            myProfileBinding.myProfileEdtName.setText("" + mRetailerBean.getName());
            myProfileBinding.myProfileEdtMobile.setText("" + mRetailerBean.getMobile());
            myProfileBinding.myProfileEdtPass.setText("" + mRetailerBean.getPassword());
            myProfileBinding.myProfileEdtEmail.setText("" + mRetailerBean.getEmailid());
            myProfileBinding.myProfileEdtBillingAdd.setText("" + mRetailerBean.getBillingAddress());
            myProfileBinding.myProfileEdtShippingAdd.setText("" + mRetailerBean.getShippingAddress());
            myProfileBinding.myProfileEdtLandmark.setText("" + mRetailerBean.getLandMark());
            myProfileBinding.myProfileEdtPincode.setText("" + mRetailerBean.getZipCode());
        } else {
            Toast.makeText(this, "You are not logged in please login first", Toast.LENGTH_SHORT).show();
            MyProfile.this.finish();
        }


        ((CheckBox) findViewById(R.id.addressCb)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    String billingAddress =  ((AutoCompleteTextView) findViewById(R.id.my_profile_edt_billing_add)).getText().toString();



                    ((AutoCompleteTextView) findViewById(R.id.my_profile_edt_shipping_add)).setText(billingAddress);



                }


            }
        });


        ((Button) findViewById(R.id.updateProfileBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = ((EditText) findViewById(R.id.my_profile_edt_name)).getText().toString();

                if(((EditText) findViewById(R.id.my_profile_edt_name)).getText().toString().isEmpty()) {
                    Toast.makeText(MyProfile.this, "Please enter name!", Toast.LENGTH_SHORT).show();
                }

                else if(((EditText) findViewById(R.id.my_profile_edt_mobile)).getText().toString().isEmpty()) {
                    Toast.makeText(MyProfile.this, "Please enter mobile number!", Toast.LENGTH_SHORT).show();
                }


                else if(((EditText) findViewById(R.id.my_profile_edt_pass)).getText().toString().isEmpty()) {
                    Toast.makeText(MyProfile.this, "Please enter password!", Toast.LENGTH_SHORT).show();
                }
                else  if(((EditText) findViewById(R.id.my_profile_edt_email)).getText().toString().isEmpty()) {
                    Toast.makeText(MyProfile.this, "Please enter email!", Toast.LENGTH_SHORT).show();
                }
                else if(((EditText) findViewById(R.id.my_profile_edt_billing_add)).getText().toString().isEmpty()) {
                    Toast.makeText(MyProfile.this, "Please enter billing current!", Toast.LENGTH_SHORT).show();
                }



                else if(((EditText) findViewById(R.id.my_profile_edt_shipping_add)).getText().toString().isEmpty()) {
                    Toast.makeText(MyProfile.this, "Please enter shipping current!", Toast.LENGTH_SHORT).show();
                }
                else if(((EditText) findViewById(R.id.my_profile_edt_pincode)).getText().toString().isEmpty()) {
                    Toast.makeText(MyProfile.this, "Please enter pin code!", Toast.LENGTH_SHORT).show();
                }


                else if (!Utility.emailValidator(((EditText) findViewById(R.id.my_profile_edt_email)).getText().toString())) {

                    Toast.makeText(MyProfile.this, "Please enter a valid email!", Toast.LENGTH_SHORT).show();

                }
                else  if(((EditText) findViewById(R.id.my_profile_edt_pincode)).getText().toString().length() <= 5 ) {

                    Toast.makeText(MyProfile.this, "Please enter a valid pin code!", Toast.LENGTH_SHORT).show();
                }


                else  if(((EditText) findViewById(R.id.my_profile_edt_mobile)).getText().toString().length() < 10) {

                    Toast.makeText(MyProfile.this, "Please enter a valid mobile number!", Toast.LENGTH_SHORT).show();
                }


                else if(!Utility.isConnectingToInternet(MyProfile.this)) {
                    Toast.makeText(MyProfile.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();
                }


                else {
                    Toast.makeText(MyProfile.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        });














    }

    @NonNull
    public Resources getLocalizedResources(Context context, Locale desiredLocale) {
        Configuration conf = context.getResources().getConfiguration();
        conf = new Configuration(conf);
        conf.setLocale(desiredLocale);
        Context localizedContext = context.createConfigurationContext(conf);
        localizedContext.getResources().updateConfiguration(conf, null);
        return localizedContext.getResources();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                + connectionResult.getErrorCode());

        // TODO(Developer): Check error code and notify the user of error state and resolution.
        Toast.makeText(this,
                "Could not connect to Google API Client: Error " + connectionResult.getErrorCode(),
                Toast.LENGTH_SHORT).show();
    }



}
