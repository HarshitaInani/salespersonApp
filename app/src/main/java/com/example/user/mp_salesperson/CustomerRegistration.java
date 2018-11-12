package com.example.user.mp_salesperson;

import android.*;
import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.CustomMultipartEntity.AndroidMultiPartEntity;
import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.mp_salesperson.adapters.HomeFragPopularBrandAdapter;
import com.example.user.mp_salesperson.bean.BaseCatSubCatBean;
import com.example.user.mp_salesperson.bean.PopularBrandBean;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.fragment.HomeFragment;
import com.google.gson.reflect.TypeToken;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.UUID;


public class CustomerRegistration extends AppCompatActivity {
    static String currentcity = "";
    static String currentarea = "";
    EditText customer_name, shopname, password, area, city, altermobile, Billing_GPS_Address, Shipping_GPS_Address, shopaddress, Shipping_Adress, GSTIN_NO, FSAAI_NO, Mobile_no, Email_Id, Family_memeber, Monthly_TurnOver;
    ImageView licensensimage, shopimage;
    Button register;
    TextView license_image, shop_image;
    Spinner areaspinner, cityspinner;
    List<String> categories = new ArrayList<String>();
    List<String> areaname = new ArrayList<String>();
    List<String> spinnerArray = new ArrayList<String>();
    private static final int MY_PERMISSION_REQUEST_LOCATION = 1;
    private static final int STORAGE_PERMISSION_CODE = 2;
    private static final int CAMERA_PERMISSION_CODE = 3;
    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUST = 2;
    RadioButton size1, size2, size3, radioButton;
    String shopsize;
    String uploadFilePath;
    private Bitmap bitmap;
    boolean isProfileImage = false;
    AutoCompleteTextView Autocity, Autoarea;
    ArrayList<String> cityarray = new ArrayList<>();
    ArrayList<String> arearray = new ArrayList<>();
    RelativeLayout citylayout,arealayout;

    String fname;
    TextInputLayout mobileerror, FSAAIerror, shopaddresserror, areaerror, cityerror, shopnameerror, altermobileerror,passworderror;
    RadioGroup radioGroup;
    String url = "http://137.59.52.130/UploadedLogos/";

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_registration);
        customer_name = (EditText) findViewById(R.id.cutomername);
        shopname = (EditText) findViewById(R.id.shopname);
        password = (EditText) findViewById(R.id.passwordedittext);
       // city = (EditText) findViewById(R.id.selectcity);
       // area = (EditText) findViewById(R.id.selectarea);
        Billing_GPS_Address = (EditText) findViewById(R.id.billingaddressGPS);
        Shipping_GPS_Address = (EditText) findViewById(R.id.shippingaddressGPS);
        shopaddress = (EditText) findViewById(R.id.shopaddress);
        GSTIN_NO = (EditText) findViewById(R.id.Gstin);
        FSAAI_NO = (EditText) findViewById(R.id.FSAAI);
        Mobile_no = (EditText) findViewById(R.id.mobile);
        altermobile = (EditText) findViewById(R.id.altermobile);
        Monthly_TurnOver = (EditText) findViewById(R.id.turnover);
        licensensimage = (ImageView) findViewById(R.id.licenseimage);
        register = (Button) findViewById(R.id.register);
        license_image = (TextView) findViewById(R.id.license_image);
        mobileerror = (TextInputLayout) findViewById(R.id.mobileerror);
        FSAAIerror = (TextInputLayout) findViewById(R.id.FSAAIerror);
        shopaddresserror = (TextInputLayout) findViewById(R.id.shopaddresserror);
        areaerror = (TextInputLayout) findViewById(R.id.areaerror);
        cityerror = (TextInputLayout) findViewById(R.id.cityerror);
        shopnameerror = (TextInputLayout) findViewById(R.id.nameerror);
        altermobileerror = (TextInputLayout) findViewById(R.id.altermobileerror);
        passworderror = (TextInputLayout) findViewById(R.id.passworderror);
        Autocity = (AutoCompleteTextView) findViewById(R.id.Autocity);
        Autoarea = (AutoCompleteTextView) findViewById(R.id.Autoarea);
        citylayout = (RelativeLayout) findViewById(R.id.citylayout);
        arealayout = (RelativeLayout) findViewById(R.id.arealayout);
        size1 = (RadioButton) findViewById(R.id.size1);
        size2 = (RadioButton) findViewById(R.id.size2);
        size3 = (RadioButton) findViewById(R.id.size3);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(CustomerRegistration.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        new CityAPI().execute();
        new AreaAPI().execute();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.size1:
                        shopsize = size1.getText().toString();
                        break;
                    case R.id.size2:
                        shopsize = size2.getText().toString();
                        break;
                    case R.id.size3:
                        shopsize = size3.getText().toString();
                        break;
                }
            }
        });
        requestStoragePermission();

       // location();
        license_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String random = generateRandom(10);
                fname = "shopkiranaimage_" + mRetailerBean.getCustomerId() + " " + random + ".jpg";
                System.out.println("fname" + fname);
                UploadImage();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isNullOrEmpty(shopname.getText().toString().trim())) {
                    shopnameerror.setError("Enter Shop name");
                } else if (TextUtils.isNullOrEmpty(Autocity.getText().toString().trim())) {
                    cityerror.setError("Enter city name");
                } else if (TextUtils.isNullOrEmpty(password.getText().toString().trim())) {
                    passworderror.setError("Enter password");
                } else if (TextUtils.isNullOrEmpty(Autoarea.getText().toString().trim())) {
                    areaerror.setError("Enter area name");
                } else if (TextUtils.isNullOrEmpty(shopaddress.getText().toString().trim())) {
                    shopaddresserror.setError("Enter shop address");
                } else if (TextUtils.isNullOrEmpty(FSAAI_NO.getText().toString().trim())) {
                    FSAAIerror.setError("Enter  FSAAI/Ref/No/GumastaNo.");
                } else if (TextUtils.isNullOrEmpty(Mobile_no.getText().toString().trim())) {
                    mobileerror.setError("Enter Mobile No.");
                } else if (Mobile_no.length() < 10) {
                    mobileerror.setError("Number is not valid");
                } else if (uploadFilePath == null) {
                    Toast.makeText(CustomerRegistration.this, "Please upload license image", Toast.LENGTH_SHORT).show();
                } else {
                    uploadImageWS();

                }
            }
        });

        System.out.println("CustomerIDDDd" + mRetailerBean.getCustomerId());


    }

    /*public void location() {
        if (ContextCompat.checkSelfPermission(CustomerRegistration.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(CustomerRegistration.this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) {
                ActivityCompat.requestPermissions(CustomerRegistration.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
            } else {
                ActivityCompat.requestPermissions(CustomerRegistration.this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION}, MY_PERMISSION_REQUEST_LOCATION);
            }
        } else {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

            } else {
                showGPSDisabledAlertToUser();
            }
            //  Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    try {
                        String cityname = city(location.getLatitude(), location.getLongitude());
                        String areaname = Area(location.getLatitude(), location.getLongitude());
                        if (cityname != null && areaname != null) {
                            city.setText(cityname);
                            area.setText(areaname);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(CustomerRegistration.this, "Not found", Toast.LENGTH_SHORT).show();
                    }
                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                }

                public void onProviderEnabled(String provider) {
                }

                public void onProviderDisabled(String provider) {
                }
            };
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(CustomerRegistration.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        LocationListener locationListener = new LocationListener() {
                            public void onLocationChanged(Location location) {
                                try {
                                    String cityname = city(location.getLatitude(), location.getLongitude());
                                    String areaname = Area(location.getLatitude(), location.getLongitude());
                                    if (cityname != null && areaname != null) {
                                        //city.setText(cityname);
                                        //area.setText(areaname);
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(CustomerRegistration.this, "Not found", Toast.LENGTH_SHORT).show();
                                }
                            }

                            public void onStatusChanged(String provider, int status, Bundle extras) {
                            }

                            public void onProviderEnabled(String provider) {
                            }

                            public void onProviderDisabled(String provider) {
                            }
                        };
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                    }
                } else {
                    Toast.makeText(this, "No Permission Granted", Toast.LENGTH_SHORT).show();
                }
            }
            case STORAGE_PERMISSION_CODE: {
                if (requestCode == STORAGE_PERMISSION_CODE) {
                    //If permission is granted
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        //Displaying a toast
                        Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
                    } else {
                        //Displaying another toast if permission is not granted
                        Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

    public String city(double lat, double lon) {
        Geocoder geocoder = new Geocoder(CustomerRegistration.this, Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList.size() > 0) {
                currentcity = addressList.get(0).getLocality();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // System.out.println("currentcity" + currentcity);
        // System.out.println("SizeOfShop"+shopsize);
        return currentcity;
    }

    public String Area(double lat, double lon) {

        Geocoder geocoder = new Geocoder(CustomerRegistration.this, Locale.getDefault());
        List<Address> addressList;
        try {
            addressList = geocoder.getFromLocation(lat, lon, 1);
            if (addressList.size() > 0) {
                currentarea = addressList.get(0).getAddressLine(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //  System.out.println("currentarea" + currentarea);
        return currentarea;
    }

    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("GPS is disabled in your device. Would you like to enable it?")
                .setCancelable(false)
                .setPositiveButton("Goto Settings Page To Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  turnGPSOn(getBaseContext());
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);

                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        showGPSNotStartAlert();
                        Toast.makeText(CustomerRegistration.this, "Please Enable GPS", Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    private void showGPSNotStartAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CustomerRegistration.this);
        alertDialog.setTitle("Confirm Not Enable GPS...");
        alertDialog.setMessage("Are you sure you don't want to  Enable GPS?");
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setNegativeButton("Enable GPS", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent callGPSSettingIntent = new Intent(
                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(callGPSSettingIntent);

            }
        });
        alertDialog.show();
    }*/

    private void UploadImage() {
        final CharSequence[] options = {"Take Photo", "Choose from Library", "Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CustomerRegistration.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else if (options[item].equals("Choose from Library")) {
                    Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_REQUST);
                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            File path = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ShopKirana");
            if (!path.exists()) path.mkdirs();
            uploadFilePath = String.format(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ShopKirana/" + fname);
            try {
                Uri selectedImage = Uri.parse(uploadFilePath);
                File file = new File(uploadFilePath);
                String path1 = file.getAbsolutePath();
                FileOutputStream outStream = new FileOutputStream(file);
                photo.compress(Bitmap.CompressFormat.JPEG, 50, outStream);
                outStream.flush();
                outStream.close();
                if (path1 != null) {
                    if (path1.startsWith("content")) {
                        Bitmap bitmap;
                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), bitmapOptions);
                    } else {
                        bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                    }
                }
                if (bitmap != null) {
                    licensensimage.setImageBitmap(bitmap);
                    Toast.makeText(this, "Captured", Toast.LENGTH_LONG).show();
                    if (Utils.isInternetConnected(CustomerRegistration.this)) {
                        System.out.println("UploadMultipart" + fname);
                        uploadMultipart();
                    } else {
                        Toast.makeText(this, "Intrnaet is not Connected", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this,
                            "Failed to Capture the picture. kindly Try Again:",
                            Toast.LENGTH_LONG).show();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == GALLERY_REQUST && resultCode == RESULT_OK && null != data) {
            Uri selectedImageUri = data.getData();
            String[] projection = {MediaStore.MediaColumns.DATA};
            Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            cursor.moveToFirst();
            String selectedImagePath = cursor.getString(column_index);
            Bitmap bm;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(selectedImagePath, options);
            final int REQUIRED_SIZE = 200;
            int scale = 1;
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(selectedImagePath, options);
            uploadFilePath = SavedImages(bm);
            licensensimage.setImageBitmap(bm);
            isProfileImage = true;
            if (isProfileImage) {
                System.out.println("UploadMultipart" + fname);
                uploadMultipart();
            }
        }
    }

    public String SavedImages(Bitmap bm) {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ShopKirana");
        myDir.mkdirs();
        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            uploadFilePath = root + "/ShopKirana/" + fname;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root + "/ShopKirana/" + fname;
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            }
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            }
        }
        if (ContextCompat.checkSelfPermission(CustomerRegistration.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(CustomerRegistration.this, android.Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(CustomerRegistration.this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            } else {
                ActivityCompat.requestPermissions(CustomerRegistration.this, new String[]{android.Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
            }
        }
    }

    private void imageShow() {
        String imageFilePath = String.format(Environment.getExternalStorageDirectory().getAbsolutePath() + "/ShopKirana/" + fname);
        Bitmap bmp = BitmapFactory.decodeFile(imageFilePath);
        //bmp = rotateImage(bmp, imageFilePath);
        if (bmp != null) {
            licensensimage.setImageBitmap(bmp);
        } else {
            //   System.out.println("No image Set");
        }
    }

    private void uploadImageWS() {
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Updating Images...", "Please wait...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.BASE_URL_REGISTRATION,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        System.out.println("Response::" + s);
                        Toast.makeText(CustomerRegistration.this, "Registerd succesfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        //Showing toast
                        Toast.makeText(CustomerRegistration.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                //Creating parameters
                Map<String, String> mRequestParamJson = new Hashtable<String, String>();
                mRequestParamJson.put("Name", customer_name.getText().toString().trim());
                mRequestParamJson.put("ShopName", shopname.getText().toString().trim());
                mRequestParamJson.put("Password", password.getText().toString().trim());
                mRequestParamJson.put("LandMark", Autoarea.getText().toString().trim());
                mRequestParamJson.put("City", Autocity.getText().toString().trim());
                mRequestParamJson.put("Cityid", String.valueOf(1));
                mRequestParamJson.put("BAGPSCoordinates", Billing_GPS_Address.getText().toString().trim());
                mRequestParamJson.put("SAGPSCoordinates", Shipping_GPS_Address.getText().toString().trim());
                mRequestParamJson.put("BillingAddress", shopaddress.getText().toString().trim());
                mRequestParamJson.put("RefNo", GSTIN_NO.getText().toString().trim());
                mRequestParamJson.put("FSAAI", FSAAI_NO.getText().toString().trim());
                mRequestParamJson.put("Mobile", Mobile_no.getText().toString().trim());
                mRequestParamJson.put("MobileSecond", altermobile.getText().toString().trim());
                mRequestParamJson.put("MonthlyTurnOver", Monthly_TurnOver.getText().toString().trim());
                mRequestParamJson.put("UploadRegistration", fname.trim());
                mRequestParamJson.put("SizeOfShop", shopsize);
                return mRequestParamJson;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return String.valueOf(b);
    }

    public void uploadMultipart() {
        try {
            String uploadId = UUID.randomUUID().toString();
            System.out.println("fnameupload" + fname);
            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Constant.SIGN_UP_IMAGE_URL)
                    .addFileToUpload(uploadFilePath, "file") //Adding file
                    .addParameter("name", "image") //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Log.d("Exeption", exc.toString());
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    public String generateRandom(int length) {
        Random random = new Random();
        char[] digits = new char[length];
        digits[0] = (char) (random.nextInt(9) + '1');
        for (int i = 1; i < length; i++) {
            digits[i] = (char) (random.nextInt(10) + '0');
        }
        return new String(digits);
    }

    public class CityAPI extends AsyncTask<String, Void, JSONArray> {

        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(CustomerRegistration.this);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Logging in please wait...");
            ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.CITY_URL, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            Log.d("jsoncheck+++", String.valueOf(jsonArray));

            if (jsonArray != null && jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String CityName = isNullOrEmpty(mJsonObject, "CityName");
                        cityarray.add(CityName);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (CustomerRegistration.this, android.R.layout.select_dialog_item, cityarray);
                        Autocity.setThreshold(1); //will start working from first character
                        Autocity.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                if (mDialog.isShowing()) {
                    animation.stop();
                    mDialog.dismiss();
                }
            }
        }
    }

    public class AreaAPI extends AsyncTask<String, Void, JSONArray> {

        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(CustomerRegistration.this);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Logging in please wait...");
            ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.AREA_URL, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            Log.d("jsoncheck+++", String.valueOf(jsonArray));

            if (jsonArray != null && jsonArray.length() > 0) {

                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String AreaName = isNullOrEmpty(mJsonObject, "AreaName");
                        arearray.add(AreaName);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                                (CustomerRegistration.this, android.R.layout.select_dialog_item, arearray);
                        Autoarea.setThreshold(1); //will start working from first character
                        Autoarea.setAdapter(adapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


                if (mDialog.isShowing()) {
                    animation.stop();
                    mDialog.dismiss();
                }
            }
        }
    }
    private String isNullOrEmpty(JSONObject mJsonObject, String key) throws JSONException {
        try {
            if (mJsonObject.has(key)) {
                if (TextUtils.isNullOrEmpty(mJsonObject.getString(key))) {
                    return "";
                } else {
                    return mJsonObject.getString(key);
                }
            } else {
                Log.e("HomeFragApi", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}



