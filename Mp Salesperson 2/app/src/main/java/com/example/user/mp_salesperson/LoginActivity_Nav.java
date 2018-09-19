package com.example.user.mp_salesperson;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.bean.BaseCatSubCatBean;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.customClasses.Utility;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity_Nav extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    EditText etMobileNumber, etPass;
    TextInputLayout tilMobileNo, tilPass;
    Button btnLogin, btnSignUp, btnForgetPass;
    AsyncTask<String, Void, JSONObject> mLoginTask;

    Dialog mDialog;
    AnimationDrawable animation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__nav);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setupTitelBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        drawer.setEnabled(false);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getIds();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobileNo = etMobileNumber.getText().toString().trim();
                String pass = etPass.getText().toString().trim();

                if (TextUtils.isNullOrEmpty(mobileNo) || !TextUtils.isValidMobileNo(mobileNo)) {
                    tilMobileNo.setError("Invalid mobile number");
                } else if (TextUtils.isNullOrEmpty(pass)) {
                    tilPass.setError("Password should not be empty");
                }

                else if(!Utility.isConnectingToInternet(LoginActivity_Nav.this)) {
                    Toast.makeText(LoginActivity_Nav.this, "Please check internet connection!", Toast.LENGTH_SHORT).show();
                }

                else {

                    callAquery(mobileNo, pass);

                    showLoading();

                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity_Nav.this, RegistrationActivity.class));
            }
        });
        btnForgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity_Nav.this, "Coming Soon..", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        finishAffinity();

    }

    @Override
    protected void onPause() {
        if (mLoginTask != null && !mLoginTask.isCancelled()) {

            mLoginTask.cancel(true);




        }
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login_activity__nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.rateUs) {

        } else if (id == R.id.my_offer) {

        } else if (id == R.id.rewards_point) {

        } else if (id == R.id.terms_conditions) {

        }
        Toast.makeText(LoginActivity_Nav.this, "Coming soon..", Toast.LENGTH_SHORT).show();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupTitelBar(Toolbar toolbar) {
        toolbar.setTitle("Sign In");
    }

    private void getIds() {

        btnLogin = (Button) findViewById(R.id.activity_login_btn_signin);
        btnSignUp = (Button) findViewById(R.id.activity_login_btn_signup);
        btnForgetPass = (Button) findViewById(R.id.activity_login_btn_forget_pass);
        etMobileNumber = (EditText) findViewById(R.id.activity_login_edt_mobile);
        etPass = (EditText) findViewById(R.id.activity_login_edt_pass);
        tilMobileNo = (TextInputLayout) findViewById(R.id.input_layout_mob);
        tilPass = (TextInputLayout) findViewById(R.id.input_layout_pass);


      //  etMobileNumber.setText("8871899084");
       // etPass.setText("123456");



    }
    public class LoginTask extends AsyncTask<String, Void, JSONObject> {
        /* Dialog mDialog;

         @Override
         protected void onPreExecute() {
             mDialog = new Dialog(LoginActivity_Nav.this);
             mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
             mDialog.setContentView(R.layout.progress_dialog);
             ((TextView) mDialog.findViewById(R.id.progressText)).setText("Logging in please wait...");
             mDialog.setCancelable(false);
             mDialog.show();
         }*/
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(LoginActivity_Nav.this);
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
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObjectFromUrl = null;
            try {
                JSONObject mRequestParamJson = new JSONObject();
                mRequestParamJson.put("Mobile", params[0]);
                mRequestParamJson.put("Password", params[1]);


//                mRequestParamJson.put("mob", params[0]);
//                mRequestParamJson.put("password", params[1]);


                jsonObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonFromHttpUrlConnectionJsonRequest_Post(Constant.BASE_URL_LOGIN, mRequestParamJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjectFromUrl;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {




            try {
                JSONObject jsonObject2=new JSONObject("sys");
               String c= jsonObject2.getString("country");
               String s= jsonObject2.getString("sunrise");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            System.out.println("Response::::::::"+jsonObject);
            if (jsonObject != null) {
                try {
                    if (TextUtils.isNullOrEmpty(jsonObject.getString("CustomerId"))) {
                        Toast.makeText(LoginActivity_Nav.this, "Wrong id or password", Toast.LENGTH_SHORT).show();
                    } else if ((jsonObject.getString("CustomerId").toString().equalsIgnoreCase("0"))) {
                        Toast.makeText(LoginActivity_Nav.this, "Wrong id or password", Toast.LENGTH_SHORT).show();
                    } else {
                        String customerId = jsonObject.getString("CustomerId");
                        String customerCategoryId = isNullOrEmpty(jsonObject, "CustomerCategoryId");
                        String Skcode = isNullOrEmpty(jsonObject, "Skcode");
                        String ShopName = isNullOrEmpty(jsonObject, "ShopName");
                        String Warehouseid = isNullOrEmpty(jsonObject, "WarehouseId");
                        String Mobile = isNullOrEmpty(jsonObject, "Mobile");
                        String WarehouseName = isNullOrEmpty(jsonObject, "WarehouseName");
                        String Name = isNullOrEmpty(jsonObject, "Name");
                        String Password = isNullOrEmpty(jsonObject, "Password");
                        String Description = isNullOrEmpty(jsonObject, "Description");
                        String CustomerType = isNullOrEmpty(jsonObject, "CustomerType");
                        String CustomerCategoryName = isNullOrEmpty(jsonObject, "CustomerCategoryName");
                        String CompanyId = isNullOrEmpty(jsonObject, "CompanyId");
                        String BillingAddress = isNullOrEmpty(jsonObject, "BillingAddress");
                        String TypeOfBuissness = isNullOrEmpty(jsonObject, "TypeOfBuissness");
                        String ShippingAddress = isNullOrEmpty(jsonObject, "ShippingAddress");
                        String LandMark = isNullOrEmpty(jsonObject, "LandMark");
                        String Country = isNullOrEmpty(jsonObject, "Country");
                        String State = isNullOrEmpty(jsonObject, "State");
                        String Cityid = isNullOrEmpty(jsonObject, "Cityid");
                        String City = isNullOrEmpty(jsonObject, "City");
                        String ZipCode = isNullOrEmpty(jsonObject, "ZipCode");
                        String BAGPSCoordinates = isNullOrEmpty(jsonObject, "BAGPSCoordinates");
                        String SAGPSCoordinates = isNullOrEmpty(jsonObject, "SAGPSCoordinates");
                        String RefNo = isNullOrEmpty(jsonObject, "RefNo");
                        String OfficePhone = isNullOrEmpty(jsonObject, "OfficePhone");
                        String Emailid = isNullOrEmpty(jsonObject, "Emailid");
                        String Familymember = isNullOrEmpty(jsonObject, "Familymember");
                        String CreatedBy = isNullOrEmpty(jsonObject, "CreatedBy");
                        String LastModifiedBy = isNullOrEmpty(jsonObject, "LastModifiedBy");
                        String CreatedDate = isNullOrEmpty(jsonObject, "CreatedDate");
                        String UpdatedDate = isNullOrEmpty(jsonObject, "UpdatedDate");
                        String imei = isNullOrEmpty(jsonObject, "imei");
                        String MonthlyTurnOver = isNullOrEmpty(jsonObject, "MonthlyTurnOver");
                        String ExecutiveId = isNullOrEmpty(jsonObject, "ExecutiveId");
                        String SizeOfShop = isNullOrEmpty(jsonObject, "SizeOfShop");
                        String Rating = isNullOrEmpty(jsonObject, "Rating");
                        String ClusterId = isNullOrEmpty(jsonObject, "ClusterId");
                        String Deleted = isNullOrEmpty(jsonObject, "Deleted");
                        String Active = isNullOrEmpty(jsonObject, "Active");
                        String GroupNotification = isNullOrEmpty(jsonObject, "GroupNotification");
                        String Notifications = isNullOrEmpty(jsonObject, "Notifications");
                        String Exception = isNullOrEmpty(jsonObject, "Exception");
                        String DivisionId = isNullOrEmpty(jsonObject, "DivisionId");
                        String Rewardspoints = isNullOrEmpty(jsonObject, "Rewardspoints");
                        System.out.println("warehouss"+Warehouseid);
                        if (Active.equalsIgnoreCase("true")) {

                            RetailerBean mRetailerBean = new RetailerBean(customerId, customerCategoryId, Skcode, ShopName, Warehouseid, Mobile, WarehouseName, Name, Password, Description, CustomerType, CustomerCategoryName, CompanyId, BillingAddress, TypeOfBuissness, ShippingAddress, LandMark, Country, State, Cityid, City, ZipCode, BAGPSCoordinates, SAGPSCoordinates, RefNo, OfficePhone, Emailid, Familymember, CreatedBy, LastModifiedBy, CreatedDate, UpdatedDate, imei, MonthlyTurnOver, ExecutiveId, SizeOfShop, Rating, ClusterId, Deleted, Active, GroupNotification, Notifications, Exception, DivisionId, Rewardspoints);

                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(LoginActivity_Nav.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.putObject(Constant.RETAILER_BEAN_PREF_OBJ, mRetailerBean);
                            mRetailerBeanPref.commit();

                            ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(LoginActivity_Nav.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                            BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);



                            if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {

                                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                startActivity(i);
                            } else {
                                startActivity(new Intent(LoginActivity_Nav.this, PreHomeActivity.class));
                                LoginActivity_Nav.this.finish();
                            }
                        } else {
                            startActivity(new Intent(LoginActivity_Nav.this, ActivationActivity.class));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(LoginActivity_Nav.this, "Improper response from server", Toast.LENGTH_SHORT).show();
            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
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
                Log.e("LoginActivity", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    String aqueryUrl = "http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/Peoples?mob=9039006314&password=123456";



    public void callAquery(String mobile, String pass) {
      //  Toast.makeText(LoginActivity_Nav.this, "Aquery call", Toast.LENGTH_SHORT).show();
        String mobileString = etMobileNumber.getText().toString();
        String passString = etPass.getText().toString();

        //http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/Peoples?mob=9039006314&password=123456
        //http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/Peoples?mob=

        new AQuery(getApplicationContext()).ajax(Constant.BASE_URL_SALES_LOGIN+"?mob="+mobileString+"&password="+passString, null, JSONObject.class, new AjaxCallback<JSONObject>() {


            @Override
            public void callback(String url, JSONObject jsonObject, AjaxStatus status) {
             //   Toast.makeText(LoginActivity_Nav.this, "Aquery call"+status.getError()+status.getMessage(), Toast.LENGTH_SHORT).show();

                if (jsonObject == null) {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }


                    // Toast.makeText(LoginActivity_Nav.this, "Json is null \n"+status.getError().toString()+"\n"+status.getMessage().toString(), Toast.LENGTH_SHORT).show();

                    Toast.makeText(LoginActivity_Nav.this, "Wrong password and Id", Toast.LENGTH_SHORT).show();


                } else {

                  //  Toast.makeText(LoginActivity_Nav.this, jsonObject.toString(), Toast.LENGTH_SHORT).show();

                    System.out.println("Response::::::::"+jsonObject);
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    try {
/*
                            {
                                "PeopleID": 2,
                                    "CompanyID": 1,
                                    "Warehouseid": 1,
                                    "PeopleFirstName": "Tanutejas",
                                    "PeopleLastName": "S",
                                    "Email": "tejas@shopkirana.com",
                                    "DisplayName": "Tanutejas S",
                                    "Country": null,
                                    "Stateid": 1,
                                    "state": "MP",
                                    "Cityid": 1,
                                    "city": "Indore",
                                    "Mobile": "9039006314",
                                    "Password": "123456",
                                    "RoleId": null,
                                    "Department": "Sales Executive",
                                    "BillableRate": 0,
                                    "CostRate": null,
                                    "Permissions": "Regular User",
                                    "Type": "Sales Executive",
                                    "ImageUrl": null,
                                    "Deleted": false,
                                    "EmailConfirmed": false,
                                    "Approved": false,
                                    "Active": true,
                                    "CreatedDate": "2016-12-28T19:38:20.457+05:30",
                                    "UpdatedDate": "2016-12-28T21:12:26.783+05:30",
                                    "CreatedBy": null,
                                    "UpdateBy": null,
                                    "VehicleId": 0,
                                    "VehicleName": null,
                                    "VehicleNumber": null,
                                    "VehicleCapacity": 0
                            }*/

/*

                            String peopleId = jsonObject.getString("PeopleID");
                            String companyId = jsonObject.getString("CompanyID");
                            String warehouseId = jsonObject.getString("Warehouseid");
                            String peopleFirstName = jsonObject.getString("PeopleFirstName");
                            String peopleLastName = jsonObject.getString("PeopleLastName");
                            String email = jsonObject.getString("Email");
                            String displayName = jsonObject.getString("DisplayName");
                            String country = jsonObject.getString("Country");
                            String stateId = jsonObject.getString("Stateid");
                            String state = jsonObject.getString("state");
                            String cityId = jsonObject.getString("Cityid");
                            String city = jsonObject.getString("city");
                            String mobile = jsonObject.getString("Mobile");
                            String password = jsonObject.getString("Password");
                            String roleId = jsonObject.getString("RoleId");
                            String department = jsonObject.getString("Department");
                            String billableRate = jsonObject.getString("BillableRate");
                            String costRate = jsonObject.getString("CostRate");
                            String permissons = jsonObject.getString("Permissions");
                            String type = jsonObject.getString("Type");
                            String imageUrl = jsonObject.getString("ImageUrl");
                            String deleted = jsonObject.getString("Deleted");
                            String emailConfirmed = jsonObject.getString("EmailConfirmed");
                            String approved = jsonObject.getString("Approved");
                            String active = jsonObject.getString("Active");
                            String createdDate = jsonObject.getString("CreatedDate");
                            String updatedDate = jsonObject.getString("UpdatedDate");
                            String createdBy = jsonObject.getString("CreatedBy");
                            String updatedBy = jsonObject.getString("UpdateBy");
                            String vehicleId = jsonObject.getString("VehicleId");;
                            String vehicleName = jsonObject.getString("VehicleName");
                            String vehicleNumber = jsonObject.getString("VehicleNumber");
                            String vehicleCapacity = jsonObject.getString("VehicleCapacity");

*/


                        String peopleId = isNullOrEmpty(jsonObject, "PeopleID");
                        String companyId = isNullOrEmpty(jsonObject, "CompanyId");
                        String warehouseId = isNullOrEmpty(jsonObject, "WarehouseId");
                        String peopleFirstName = isNullOrEmpty(jsonObject, "PeopleFirstName");
                        String peopleLastName = isNullOrEmpty(jsonObject, "PeopleLastName");
                        String email = isNullOrEmpty(jsonObject, "Email");
                        String displayName = isNullOrEmpty(jsonObject, "DisplayName");
                        String country = isNullOrEmpty(jsonObject, "Country");
                        String stateId = isNullOrEmpty(jsonObject, "Stateid");
                        String state = isNullOrEmpty(jsonObject, "state");
                        String cityId = isNullOrEmpty(jsonObject, "Cityid");
                        String city = isNullOrEmpty(jsonObject, "city");
                        String mobile = isNullOrEmpty(jsonObject, "Mobile");
                        String password = isNullOrEmpty(jsonObject, "Password");
                        String roleId = isNullOrEmpty(jsonObject, "RoleId");
                        String department = isNullOrEmpty(jsonObject, "Department");
                        String billableRate = isNullOrEmpty(jsonObject, "BillableRate");
                        String costRate = isNullOrEmpty(jsonObject, "CostRate");
                        String permissons = isNullOrEmpty(jsonObject, "Permissions");
                        String type = isNullOrEmpty(jsonObject, "Type");
                        String imageUrl = isNullOrEmpty(jsonObject, "ImageUrl");
                        String deleted = isNullOrEmpty(jsonObject, "Deleted");
                        String emailConfirmed = isNullOrEmpty(jsonObject, "EmailConfirmed");
                        String approved = isNullOrEmpty(jsonObject, "Approved");
                        String active = isNullOrEmpty(jsonObject, "Active");
                        String createdDate = isNullOrEmpty(jsonObject, "CreatedDate");
                        String updatedDate = isNullOrEmpty(jsonObject, "UpdatedDate");
                        String createdBy = isNullOrEmpty(jsonObject, "CreatedBy");
                        String updatedBy = isNullOrEmpty(jsonObject, "UpdateBy");
                        String vehicleId = isNullOrEmpty(jsonObject, "VehicleId");;
                        String vehicleName = isNullOrEmpty(jsonObject, "VehicleName");
                        String vehicleNumber = isNullOrEmpty(jsonObject, "VehicleNumber");
                        String vehicleCapacity = isNullOrEmpty(jsonObject, "VehicleCapacity");





//                            String customerId = jsonObject.getString("CustomerId");
//                            String customerCategoryId = isNullOrEmpty(jsonObject, "CustomerCategoryId");
//                            String Skcode = isNullOrEmpty(jsonObject, "Skcode");
//                            String ShopName = isNullOrEmpty(jsonObject, "ShopName");
//                            String Warehouseid = isNullOrEmpty(jsonObject, "Warehouseid");
//                            String Mobile = isNullOrEmpty(jsonObject, "Mobile");
//                            String WarehouseName = isNullOrEmpty(jsonObject, "WarehouseName");
//                            String Name = isNullOrEmpty(jsonObject, "Name");
//                            String Password = isNullOrEmpty(jsonObject, "Password");
//                            String Description = isNullOrEmpty(jsonObject, "Description");
//                            String CustomerType = isNullOrEmpty(jsonObject, "CustomerType");
//                            String CustomerCategoryName = isNullOrEmpty(jsonObject, "CustomerCategoryName");
//                            String CompanyId = isNullOrEmpty(jsonObject, "CompanyId");
//                            String BillingAddress = isNullOrEmpty(jsonObject, "BillingAddress");
//                            String TypeOfBuissness = isNullOrEmpty(jsonObject, "TypeOfBuissness");
//                            String ShippingAddress = isNullOrEmpty(jsonObject, "ShippingAddress");
//                            String LandMark = isNullOrEmpty(jsonObject, "LandMark");
//                            String Country = isNullOrEmpty(jsonObject, "Country");
//                            String State = isNullOrEmpty(jsonObject, "State");
//                            String Cityid = isNullOrEmpty(jsonObject, "Cityid");
//                            String City = isNullOrEmpty(jsonObject, "City");
//                            String ZipCode = isNullOrEmpty(jsonObject, "ZipCode");
//                            String BAGPSCoordinates = isNullOrEmpty(jsonObject, "BAGPSCoordinates");
//                            String SAGPSCoordinates = isNullOrEmpty(jsonObject, "SAGPSCoordinates");
//                            String RefNo = isNullOrEmpty(jsonObject, "RefNo");
//                            String OfficePhone = isNullOrEmpty(jsonObject, "OfficePhone");
//                            String Emailid = isNullOrEmpty(jsonObject, "Emailid");
//                            String Familymember = isNullOrEmpty(jsonObject, "Familymember");
//                            String CreatedBy = isNullOrEmpty(jsonObject, "CreatedBy");
//                            String LastModifiedBy = isNullOrEmpty(jsonObject, "LastModifiedBy");
//                            String CreatedDate = isNullOrEmpty(jsonObject, "CreatedDate");
//                            String UpdatedDate = isNullOrEmpty(jsonObject, "UpdatedDate");
//                            String imei = isNullOrEmpty(jsonObject, "imei");
//                            String MonthlyTurnOver = isNullOrEmpty(jsonObject, "MonthlyTurnOver");
//                            String ExecutiveId = isNullOrEmpty(jsonObject, "ExecutiveId");
//                            String SizeOfShop = isNullOrEmpty(jsonObject, "SizeOfShop");
//                            String Rating = isNullOrEmpty(jsonObject, "Rating");
//                            String ClusterId = isNullOrEmpty(jsonObject, "ClusterId");
//                            String Deleted = isNullOrEmpty(jsonObject, "Deleted");
//                            String Active = isNullOrEmpty(jsonObject, "Active");
//                            String GroupNotification = isNullOrEmpty(jsonObject, "GroupNotification");
//                            String Notifications = isNullOrEmpty(jsonObject, "Notifications");
//                            String Exception = isNullOrEmpty(jsonObject, "Exception");
//                            String DivisionId = isNullOrEmpty(jsonObject, "DivisionId");
//                            String Rewardspoints = isNullOrEmpty(jsonObject, "Rewardspoints");

                            if (active.equalsIgnoreCase("true")) {

                                Utility.setStringSharedPreference(LoginActivity_Nav.this, "MultiLaguage", "m");

//                              RetailerBean mRetailerBean = new RetailerBean(peopleId,"customerCategoryId",  "skcode", "shopName", "warehouseid", "mobile","warehouseName", " name", " password","description","customerType","customerCategoryName","companyId","billingAddress","typeOfBuissness","shippingAddress","landMark","country","state","cityid","city","zipCode","BAGPSCoordinates","SAGPSCoordinates","refNo","officePhone","emailid","familymember","createdBy","lastModifiedBy","createdDate","updatedDate","imei","monthlyTurnOver","executiveId","sizeOfShop","rating","clusterId","deleted","active","groupNotification","notifications","exception","divisionId","rewardspoints"
//                                      ,peopleId, companyId, warehouseId,  peopleFirstName,  peopleLastName,  email,  displayName,  country,  stateId, state,  cityId,  city,  mobile,  password,  roleId,  department,  billableRate,  costRate,  permissons,  type,  imageUrl,  deleted,  emailConfirmed,  approved,  active,  createdDate,  updatedDate,  createdBy,  updatedBy,  vehicleId,  vehicleName,  vehicleNumber,  vehicleCapacity);



                                RetailerBean mRetailerBean = new RetailerBean(peopleId,"customerCategoryId",  "skcode", "shopName", warehouseId, mobile,"warehouseName", " name", " password","description","customerType","customerCategoryName",companyId,"billingAddress","typeOfBuissness","shippingAddress","landMark","country","state","cityid","city","zipCode","BAGPSCoordinates","SAGPSCoordinates","refNo","officePhone","emailid","familymember","createdBy","lastModifiedBy","createdDate","updatedDate","imei","monthlyTurnOver","executiveId","sizeOfShop","rating","clusterId","deleted","active","groupNotification","notifications","exception","divisionId","rewardspoints"
                                        ,peopleId, companyId, warehouseId,  peopleFirstName,  peopleLastName,  email, displayName,  country,  stateId, state,  cityId,  city,  mobile,  password,  roleId,  department,  billableRate,  costRate,  permissons,  type,  imageUrl,  "deleted",  "emailConfirmed",  "approved",  "active",  "createdDate",  "updatedDate",  "createdBy",  "updatedBy",  "vehicleId",  "vehicleName",  "vehicleNumber",  "vehicleCapacity");



//                                RetailerBeanSeller mRetailerBeanSeller = new RetailerBeanSeller(peopleId, companyId, warehouseId, peopleFirstName, peopleLastName, email, displayName, country, stateId, state, cityId, city, mobile, password, roleId, department, billableRate, costRate, permissons,  type,  imageUrl,  deleted,  emailConfirmed,  approved,  active,  createdDate,  updatedDate,  createdBy,  updatedBy,  vehicleId,  vehicleName,  vehicleNumber,  vehicleCapacity);
                                Utility.setSharedPreferenceBoolean(LoginActivity_Nav.this, "APICALL", true);
                                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(LoginActivity_Nav.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                                mRetailerBeanPref.putObject(Constant.RETAILER_BEAN_PREF_OBJ, mRetailerBean);
                                mRetailerBeanPref.commit();

                                ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(LoginActivity_Nav.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
                                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {

                                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(i);
                                } else {


//                                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
//                                    startActivity(i);

                                    startActivity(new Intent(LoginActivity_Nav.this, PreHomeActivity.class));
                                    LoginActivity_Nav.this.finish();
                                }
                            } else {
                                startActivity(new Intent(LoginActivity_Nav.this, ActivationActivity.class));
                            }

                    } catch (JSONException e) {
                        Toast.makeText(LoginActivity_Nav.this, "Error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }



                    //  Utility.setStringSharedPreference(LoginActivity_Nav.this, "SkJson", json.toString());
                //    startActivity(new Intent(LoginActivity_Nav.this, CheckOutActivity.class));

                }

            }
        });

    }


    public void showLoading() {
        mDialog = new Dialog(LoginActivity_Nav.this);
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


}
