package com.example.user.mp_salesperson;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.customClasses.Utility;
import com.example.user.mp_salesperson.dial.DeliveryDialWheel;
import com.example.user.mp_salesperson.dial.DialWheelActivity;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

public class EditCustomerDetails extends AppCompatActivity {
    LocationManager locationManager;
    String latitude,longitude;
    TextView tvuploadRegistration,tvupdateShopPhoto,UpdateUploads;
    //Image request code

    String uploadFilePath;
    boolean isProfileImage = false;
    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
    //Bitmap to get image from gallery
    private Bitmap bitmap;
    //Uri to store the image uri
    private Uri filePath;
    String fname,fname1;
    private static final int CAMERA_REQUEST = 1;
    private static final int GALLERY_REQUST = 2;
    ImageView imageView;
    ImageView imUpload_Registration,im_Upload_S_Name;
    int UploadFlag=0;
    protected String imageFilePath;
      String CustomerMobile;
    String EMAIL,NAME,REN,DOB,PASS,UPREG,SHOP_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_customer_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);
        imUpload_Registration=(ImageView) findViewById(R.id.imUpload_Registration) ;
        im_Upload_S_Name=(ImageView) findViewById(R.id.im_Upload_S_Name) ;
        tvuploadRegistration=(TextView)findViewById(R.id.upload_registration) ;
        tvupdateShopPhoto=(TextView)findViewById(R.id.update_shop_photo) ;
        UpdateUploads=(TextView)findViewById(R.id.UpdateUploads) ;
        Intent intent = getIntent();

        //CustomerMobile = intent.getStringExtra("CustomerMobile");
        CustomerMobile=   Utility.getStringSharedPreferences(this,"Mobile");
        EMAIL=   Utility.getStringSharedPreferences(this,"EMAIL");
        NAME=   Utility.getStringSharedPreferences(this,"NAME");
        REN=   Utility.getStringSharedPreferences(this,"REN");
        DOB=   Utility.getStringSharedPreferences(this,"DOB");
        PASS=   Utility.getStringSharedPreferences(this,"PASS");
        UPREG=   Utility.getStringSharedPreferences(this,"UPREG");
        SHOP_NAME =   Utility.getStringSharedPreferences(this,"BeatSkCodeCName");
        tvuploadRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadFlag=1;
              //  showFileChooser();
                Utility.setIntSharedPreference(EditCustomerDetails.this, "UPLOADFLAG", UploadFlag);

                UploadImage();

            }
        });

        tvupdateShopPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadFlag=2;
              //  showFileChooser();
                Utility.setIntSharedPreference(EditCustomerDetails.this, "UPLOADFLAG", UploadFlag);
                UploadImage();
            }
        });
        UpdateUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageWS();
            }
        });
        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditCustomerDetails.this.finish();
                startActivity(new Intent(EditCustomerDetails.this ,BeatOrderActivity.class));

            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            //  Toast.makeText(this, "GPS is Enabled in your devide", Toast.LENGTH_SHORT).show();
        }else{
            showGPSDisabledAlertToUser();
        }
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                latitude = Double.toString(location.getLatitude());
                longitude = Double.toString(location.getLongitude());
                System.out.println("latitute is 1  ++++++++"+latitude);

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {

            }
        };
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }
    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("Call Activity");
        ImageShowRG();
        ImageShowShop();

    }
    private void showGPSDisabledAlertToUser(){
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
                               /* Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
                                intent.putExtra("enabled", true);
                                sendBroadcast(intent);*/
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        //  showGPSNotStartAlert();
                        // Toast.makeText(LocationChangedActivity.this, "Please Enable GPS", Toast.LENGTH_LONG).show();

                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, GALLERY_REQUST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            int UploadFlag =   Utility.getIntSharedPreferences(EditCustomerDetails.this,"UPLOADFLAG");
            if(UploadFlag==1)
            {
                fname = "Rg_Upload_"+CustomerMobile+".jpg";
            }else
            {
                fname = "Shop_Upload_"+CustomerMobile+".jpg";
                //fname1 = "Shop_Upload.jpg";

            }
            //String partFilename = "UploadImage";
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            File path = new File(Environment.getExternalStorageDirectory()+ "/ShopKirana");
            if (!path.exists()) path.mkdirs();
            uploadFilePath = String.format(Environment.getExternalStorageDirectory()+ "/ShopKirana/"+fname);
            System.out.println("Image upload::"+uploadFilePath);
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
                    isProfileImage = true;
                    imUpload_Registration.setImageBitmap(bitmap);
                    Toast.makeText(this, "Captured", Toast.LENGTH_LONG).show();
                    uploadMultipart();

                } else {
                    Toast.makeText(this,
                            "Failed to Capture the picture. kindly Try Again:",
                            Toast.LENGTH_LONG).show();}
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
            while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                    && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false;
            bm = BitmapFactory.decodeFile(selectedImagePath, options);
            uploadFilePath = SavedImages(bm);
            imUpload_Registration.setImageBitmap(bm);
            isProfileImage = true;
            if (isProfileImage){
                uploadMultipart();
            }
            System.out.println("Image Upload Gallery::"+uploadFilePath);
        }
    }
    private void UploadImage() {

        final CharSequence[] options = {"Take Photo","Choose from Library","Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(EditCustomerDetails.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, CAMERA_REQUEST);
                } else if (options[item].equals("Choose from Library")) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    startActivityForResult(i, GALLERY_REQUST);
                }

                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    // Code from StudyKloud
    public String SavedImages(Bitmap bm) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(Environment.getExternalStorageDirectory()+ "/ShopKirana");
        myDir.mkdirs();

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(EditCustomerDetails.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
       // fname = mRetailerBean.getCustomerId() + ".jpg";
       int UploadFlag =   Utility.getIntSharedPreferences(EditCustomerDetails.this,"UPLOADFLAG");
        if(UploadFlag==1)
        {
            fname = "Rg_Upload_"+CustomerMobile+".jpg";
        }else
        {
            fname = "Shop_Upload_"+CustomerMobile+".jpg";
            //fname1 = "Shop_Upload.jpg";

        }

        File file = new File(myDir, fname);
        if (file.exists())
            file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            //    isChosen = true;
            //profileImg = file.getName();

            uploadFilePath = root + "/ShopKirana/" + fname;
            //   new UploadImageTask().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return root + "/ShopKirana/" + fname;

    }
    public void uploadMultipart() {

        //String path = getPath(filePath);


        System.out.println("Path:::"+uploadFilePath);
        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Constant.UPLOAD_URL)
                    .addFileToUpload(uploadFilePath, "file") //Adding file
                    .addParameter("name", "image") //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload


            SharedPreferences sharedPreferences = getSharedPreferences("UPLOADFLAG", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.clear().commit();
        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void ImageShowRG() {

        imageFilePath = String.format(Environment.getExternalStorageDirectory() + "/ShopKirana/Rg_Upload_"+CustomerMobile+".jpg");
        Bitmap bmp = BitmapFactory.decodeFile(imageFilePath);
       // bmp = rotateImage(bmp, imageFilePath);
        if (bmp != null) {
           // CRightview.setVisibility(View.GONE);
            // t2.setVisibility(View.GONE);
          //  Rightview.setVisibility(View.VISIBLE);
            imUpload_Registration.setImageBitmap(bmp);
        }
        else{
            System.out.println("No image Set");

        }
    }
    private void ImageShowShop() {

        imageFilePath = String.format(Environment.getExternalStorageDirectory() + "/ShopKirana/Shop_Upload_"+CustomerMobile+".jpg");
        Bitmap bmp = BitmapFactory.decodeFile(imageFilePath);
        // bmp = rotateImage(bmp, imageFilePath);
        if (bmp != null) {
            // CRightview.setVisibility(View.GONE);
            // t2.setVisibility(View.GONE);
            //  Rightview.setVisibility(View.VISIBLE);
            im_Upload_S_Name.setImageBitmap(bmp);
        }
        else{
            System.out.println("No image Set");

        }
    }

    private void uploadImageWS(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Updating Images...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT, Constant.BASE_URL_SIGNUP_UPDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        System.out.println("Response::"+s);
                         startActivity(new Intent(EditCustomerDetails.this,BeatOrderActivity.class));
                       // File file = new File(uploadFilePath);
                      //  file.delete();
                        //Showing toast message of the response



                        Toast.makeText(EditCustomerDetails.this, "Update successfully done" , Toast.LENGTH_LONG).show();
                        File myDir = new File(Environment.getExternalStorageDirectory()+ "/ShopKirana");
                        File file1 = new File(myDir, "Rg_Upload_"+CustomerMobile+".jpg");
                        File file2 = new File(myDir, "Shop_Upload_"+CustomerMobile+".jpg");
                        file1.delete();
                        file2.delete();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        //Showing toast
                        Toast.makeText(EditCustomerDetails.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();
                //Adding parameters
                params.put("Mobile",CustomerMobile);
                params.put("Name", NAME);
                params.put("ShopName", SHOP_NAME);
                params.put("RefNo", REN);
                params.put("Password", PASS);
                params.put("DOB", DOB);
                params.put("Emailid", EMAIL);
                params.put("UploadRegistration", "Rg_Upload_"+CustomerMobile+".jpg");
                params.put("ResidenceAddressProof", "Shop_Upload_"+CustomerMobile+".jpg");
                System.out.println("params::"+params);
                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }
}
