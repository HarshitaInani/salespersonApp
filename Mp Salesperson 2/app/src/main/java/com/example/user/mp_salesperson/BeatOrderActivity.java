package com.example.user.mp_salesperson;

import android.*;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.bean.CartItemBean;
import com.example.user.mp_salesperson.bean.DialItem;
import com.example.user.mp_salesperson.bean.PopularBrandBean;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.customClasses.Utility;
import com.example.user.mp_salesperson.dial.DialWheelActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class BeatOrderActivity extends AppCompatActivity {

    Spinner spinner,spreason;
    ArrayList list = new ArrayList();
    ArrayList reason = new ArrayList();

    TextView tvSkCode, tvRewardPoint,txtcontact;
    EditText commentsEt;
     String SelectValueVisited="";
    String ReasonText,ResonVisit;
    Context context;
    SimpleDateFormat format2;
    Date newDate2;
    private RadioGroup radioGroup;
    private RadioButton radioSexButton;
    private Button btnDisplay;
    private TextView btnUpdate;
    private TextView top_outlets;
    private TextView top_purchase_brand;
    private TextView dial_availability;
    private TextView edit_customer_details;
    private TextView tvOrder;
    private TextView Customer_name;
    JSONArray jsonArray1;
    ArrayList<String> totalList = new ArrayList<>();
    ArrayList<String> StatusList = new ArrayList<>();
    ArrayList<String> CommentList = new ArrayList<>();
    ArrayList<String> dateList = new ArrayList<>();
    String skCode, rewardpoint, skCodeCId, skCodeCName,lat,longi,name;
    String latitude,longitude;
    Dialog mDialog;
    AnimationDrawable animation;
    String urlRemote = "";
    String mob;
    String CompanyId;
    LineChart lineChart;
    LocationManager locationManager;
     String Id,OrderId,Point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beat_order);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        tvSkCode = (TextView) findViewById(R.id.skCode);
        tvRewardPoint = (TextView) findViewById(R.id.rewards_point);
        txtcontact = (TextView) findViewById(R.id.contactnumber);
        top_outlets = (TextView) findViewById(R.id.top_outlets);
        top_purchase_brand = (TextView) findViewById(R.id.top_purchase_brand);
        dial_availability = (TextView) findViewById(R.id.dial_availability);
        edit_customer_details = (TextView) findViewById(R.id.edit_customer_details);
        tvOrder = (TextView) findViewById(R.id.Order);
        Customer_name = (TextView) findViewById(R.id.customer_name);

      //  Intent i = getIntent();
       // skCode = PreferenceManager.getDefaultSharedPreferences(BeatOrderActivity.this).getString("BeatSkCode", "");
       /*
       ;*/
       /*  skCode = i.getStringExtra("MyBeatSkCode");
        skCodeCId = i.getStringExtra("MyBeatSkCodeCustomerId");
        skCodeCName= i.getStringExtra("MyBeatSkCodeCustomerName");
        lat = i.getStringExtra("LATITUDE");
        longi = i.getStringExtra("LONGITUDE");
        rewardpoint = i.getStringExtra("MyRewardsPoint");
        mob = i.getStringExtra("Mobile");
        CompanyId = i.getStringExtra("CompanyId");
          */

        context = this;
        skCode =   Utility.getStringSharedPreferences(context,"BeatSkCode");
        skCodeCId =   Utility.getStringSharedPreferences(context,"BeatSkCodeCId");
        skCodeCName =   Utility.getStringSharedPreferences(context,"BeatSkCodeCName");
        rewardpoint=   Utility.getStringSharedPreferences(context,"MyRewardsPoint");
        CompanyId=   Utility.getStringSharedPreferences(context,"CompanyId");
        mob=   Utility.getStringSharedPreferences(context,"Mobile");
        lat=   Utility.getStringSharedPreferences(context,"LATITUDE");
        longi=   Utility.getStringSharedPreferences(context,"LONGITUDE");
        name=   Utility.getStringSharedPreferences(context,"NAME");




        System.out.println("skCode:::"+skCode);
        txtcontact.setText("Customer Number = (+91)"+mob);
        Customer_name.setText("Customer Name = "+name);
        urlRemote = Constant.BASE_URL_MY_WALLET+"?CustomerId="+skCodeCId;

       btnUpdate=(TextView)findViewById(R.id.update) ;

        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        top_outlets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeatOrderActivity.this,MapsActivity.class));
            }
        });
        top_purchase_brand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeatOrderActivity.this,TopBrandActivity.class));
            }
        });
        edit_customer_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BeatOrderActivity.this,EditCustomerDetails.class);
                intent.putExtra("CustomerMobile", mob);
                startActivity(intent);
            }
        });
        tvOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(context, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);


                //new UpdateAsyncTask().execute();
                showLoading();


                JSONObject jsonObject2 = new JSONObject();

                try {

                        jsonObject2.put("Visited", "true");
                        jsonObject2.put("status", "Price");


                    jsonObject2.put("CompanyId", CompanyId);
                    jsonObject2.put("SalesPersonId", mRetailerBean.getCustomerId());
                    jsonObject2.put("WarehouseId", mRetailerBean.getWarehouseId());
                    jsonObject2.put("SalespersonName", mRetailerBean.getPeopleFirstName());
                    jsonObject2.put("Skcode", skCode);
                    jsonObject2.put("ShopName", mRetailerBean.getShopName());
                    jsonObject2.put("lat", latitude);
                    jsonObject2.put("lg", longitude);
                    jsonObject2.put("Comment", commentsEt.getText().toString());


                } catch (Exception e) {

                }

                //http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/
//                http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/AsignDay/addBeat

                System.out.println("Send time:::"+jsonObject2);
                new AQuery(getApplicationContext()).post(Constant.BASE_URL_ORDER_NO_PLACED, jsonObject2, JSONObject.class, new AjaxCallback<JSONObject>() {


                    @Override
                    public void callback(String url, JSONObject json, AjaxStatus status) {
                        Log.e("beatOrder", json.toString());
                        if (json == null) {

                            if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }

                            Toast.makeText(BeatOrderActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                        } else {




                                Toast.makeText(BeatOrderActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(context, HomeActivity.class));
                                BeatOrderActivity.this.finish();

                            if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }


                            // Toast.makeText(BeatOrderActivity.this, json.toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });



            }
        });
        //callAqueryDialAvailable();
        dial_availability.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(BeatOrderActivity.this,DialWheelActivity.class);
                intent.putExtra("Id", Id);
                intent.putExtra("OrderId", OrderId);
                intent.putExtra("Point", Point);
                intent.putExtra("DialAvail", dial_availability.getText().toString());
               // System.out.println("Pointshfgfh::"+uu
                startActivity(intent);
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton  radioButton = (RadioButton) findViewById(checkedId);
                switch(checkedId){
                    case R.id.radio1:
                        spreason.setVisibility(View.GONE);
                        spinner.setVisibility(View.VISIBLE);
                       // SelectValueVisited=radioButton.getText().toString();
                        SelectValueVisited="true";
                       // Toast.makeText(getBaseContext(), SelectValueVisited, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio2:
                        spreason.setVisibility(View.VISIBLE);
                        spinner.setVisibility(View.GONE);
                       // SelectValueVisited=radioButton.getText().toString();
                        SelectValueVisited="false";
                     //   Toast.makeText(getBaseContext(), SelectValueVisited, Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
         lineChart = (LineChart) findViewById(R.id.chart);
        lineChart.getLegend().setEnabled(true);
            lineChart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

                @Override
                public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                    if (e == null)
                        return;
                   String Cancelreason= CommentList.get(e.getXIndex()).toString();
                   String Status= StatusList.get(e.getXIndex()).toString().trim();
                    Log.i("VAL SELECTED", "Value: " + e.getVal() + ", xIndex: " + e.getXIndex() + ", DataSet index: " + dataSetIndex);
                    //Toast.makeText(BeatOrderActivity.this,"Selected value is "+e.getVal(),Toast.LENGTH_SHORT).show();
                    if (Status.equalsIgnoreCase("Order Canceled")) {
                        Toast.makeText(BeatOrderActivity.this, "Reason is " + Cancelreason, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
            public void onNothingSelected() {

            }
        });

        if (!Utility.isConnectingToInternet(BeatOrderActivity.this)){
            Toast.makeText(this, "Please check Internet connection", Toast.LENGTH_SHORT).show();
        }



        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BeatOrderActivity.this.finish();
                startActivity(new Intent(BeatOrderActivity.this ,DaysBidActivity.class));

            }
        });





       /* ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BeatOrderActivity.this.finish();
            }
        });*/

        ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BeatOrderActivity.this.finish();
                startActivity(new Intent(BeatOrderActivity.this, HomeActivity.class));
            }
        });



     /*   ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BeatOrderActivity.this.finish();
            }
        });
*/

        ((ImageView) toolbar.findViewById(R.id.my_order_more_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.my_order_more_iv);
                PopupMenu popup = new PopupMenu(BeatOrderActivity.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();
//                inflate.inflate(R.menu.my_order_option_menu, popup.getMenu());
                inflate.inflate(R.menu.home, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(BeatOrderActivity.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(BeatOrderActivity.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        }else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(BeatOrderActivity.this, MyWallet.class));
                            return true;
                        }
                        else if (id == R.id.myDial) {
                            startActivity(new Intent(BeatOrderActivity.this, MyDialListActivity.class));
                            return true;
                        }
                        else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(BeatOrderActivity.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(BeatOrderActivity.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(BeatOrderActivity.this, RequestBrandActivity.class));
                            return true;
                        } else if (id == R.id.action_feedback) {
                            startActivity(new Intent(BeatOrderActivity.this, FeedbackActivity.class));
                            return true;



                        } else if (id == R.id.action_logout) {


                     /*       ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(BeatOrderActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();
                            startActivity(new Intent(BeatOrderActivity.this, LoginActivity_Nav.class));
                            BeatOrderActivity.this.finish();
                     */




                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(BeatOrderActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();


                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(BeatOrderActivity.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);


                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();




                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(BeatOrderActivity.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);


                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();



                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(BeatOrderActivity.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);


                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();





                            Utility.setStringSharedPreference(BeatOrderActivity.this, "Bidmonday", "");
                            Utility.setStringSharedPreference(BeatOrderActivity.this, "Bidtuesday", "");
                            Utility.setStringSharedPreference(BeatOrderActivity.this, "Bidwednesday", "");
                            Utility.setStringSharedPreference(BeatOrderActivity.this, "Bidthursday", "");
                            Utility.setStringSharedPreference(BeatOrderActivity.this, "Bidfriday", "");
                            Utility.setStringSharedPreference(BeatOrderActivity.this, "Bidsaturday", "");
                            Utility.setStringSharedPreference(BeatOrderActivity.this, "Bidsunday", "");


                            Utility.setStringSharedPreference(BeatOrderActivity.this,"BeatSkCode","");
                            Utility.setStringSharedPreference(BeatOrderActivity.this, "ItemQJson" ,"");

                            Utility.setStringSharedPreference(BeatOrderActivity.this, "CompanyId", "");



                            clearCartData();

                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();







                            BeatOrderActivity.this.finish();


                            startActivity(new Intent(BeatOrderActivity.this, LoginActivity_Nav.class));








                            return true;


                        }

                        else if (id == R.id.setting) {
                            startActivity(new Intent(BeatOrderActivity.this, SettingActivity.class));
                            //   HomeActivity.this.finish();
                            return true;
                        }



                        else if (id == R.id.mysales) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(BeatOrderActivity.this, MySalesActivity.class));

                            //    HomeActivity.this.finish();
                            return true;
                        }


                        else if (id == R.id.mybid) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(BeatOrderActivity.this, DaysBidActivity.class));

                            //     HomeActivity.this.finish();

                            //HomeActivity.this.finish();
                            return true;
                        }

                        else if (id == R.id.redeem_point) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(BeatOrderActivity.this, ReedemPointActivity.class));

                            //           HomeActivity.this.finish();
                            return true;
                        }

                        else if (id == R.id.reward_point_menu) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(BeatOrderActivity.this, RewardItemActivity.class));

                            //     HomeActivity.this.finish();
                            return true;
                        }




                        else if (id == R.id.action_task) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(BeatOrderActivity.this, ActionTaskMultiActivity.class));

                            //           HomeActivity.this.finish();
                            return true;
                        }


                        else
                            return false;
                    }
                });
            }
        });





        ((TextView)findViewById(R.id.title_toolbar)).setText("Beat Order");

        callAquery();
        //Toast.makeText(context, "Customer Id"+skCodeCId, Toast.LENGTH_SHORT).show();
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(context, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        // dataPoints = new DataPoint[200];
        list.add("select");
        list.add("Price");
        list.add("Shop Closed");
        list.add("Not satisfied");
        list.add("Other");


        reason.add("select");
        reason.add("Lack of time");
        reason.add("Not able to find ");
        reason.add("Wrong territory /beat");


        spinner = (Spinner) findViewById(R.id.bidOrderSpinner);
        spreason = (Spinner) findViewById(R.id.bidOrderSpinner1);
        commentsEt = (EditText) findViewById(R.id.commentEt);



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                BeatOrderActivity.this,
                android.R.layout.simple_list_item_1,
                list


        );

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                BeatOrderActivity.this,
                android.R.layout.simple_list_item_1,
                reason


        );

         spreason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,final int ii, long arg3) {

                ReasonText = arg0.getItemAtPosition(ii).toString();
                System.out.println("Reason::"+ReasonText);
            }

             @Override
             public void onNothingSelected(AdapterView<?> arg0) {
                 // TODO Auto-generated method stub
             }
         });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,final int ii, long arg3) {

                ResonVisit = arg0.getItemAtPosition(ii).toString();
                System.out.println("Reason::"+ResonVisit);
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });


        tvSkCode.setText("Customer Skcode = " + skCode);

        if (rewardpoint.isEmpty()) {
            rewardpoint = "0";
        }


        if (rewardpoint.equals("null")) {
            rewardpoint = "0";
        }


        tvRewardPoint.setText("Customer Wallet point = " + rewardpoint);

        spinner.setAdapter(adapter);
        spreason.setAdapter(adapter1);

        //   graphSet();

      //  setMpAndroidGraph("http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/target/Report?day=3 month&skcode=");

        setMpAndroidGraph(Constant.BASE_URL_REPORT_3_MONTHS);


        ((TextView) findViewById(R.id.placeOrder)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   Toast.makeText(context, "Sk code saved : "+skCode, Toast.LENGTH_LONG).show();
                startActivity(new Intent(context, HomeActivity.class));
                BeatOrderActivity.this.finish();


            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (SelectValueVisited.equalsIgnoreCase(""))
                {
                    Toast.makeText(context, "Please select visited or not visited", Toast.LENGTH_SHORT).show();
                }else if(spreason.getSelectedItem().toString().equalsIgnoreCase("select")&spinner.getSelectedItem().toString().equalsIgnoreCase("select")) {
                    Toast.makeText(context, "Please select  reason", Toast.LENGTH_SHORT).show();

                }


                else
                {
                    //new UpdateAsyncTask().execute();
                    showLoading();


                    JSONObject jsonObject2 = new JSONObject();

                    try {
                        if (SelectValueVisited.equalsIgnoreCase("true"))
                        {
                            jsonObject2.put("Visited", SelectValueVisited);
                            jsonObject2.put("status",ResonVisit);
                        }else
                        {
                            jsonObject2.put("Visited", SelectValueVisited);
                            jsonObject2.put("status", ReasonText);
                        }

                        jsonObject2.put("CompanyId", CompanyId);
                        jsonObject2.put("SalesPersonId", mRetailerBean.getCustomerId());
                        jsonObject2.put("WarehouseId", mRetailerBean.getWarehouseId());
                        jsonObject2.put("SalespersonName", mRetailerBean.getPeopleFirstName());
                        jsonObject2.put("Skcode", skCode);
                        jsonObject2.put("ShopName", mRetailerBean.getShopName());
                        jsonObject2.put("lat", latitude);
                        jsonObject2.put("lg", longitude);
                        jsonObject2.put("Comment", commentsEt.getText().toString());


                    } catch (Exception e) {

                    }

                    //http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/
//                http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/AsignDay/addBeat

                    System.out.println("Send time:::"+jsonObject2);
                    new AQuery(getApplicationContext()).post(Constant.BASE_URL_ORDER_NO_PLACED, jsonObject2, JSONObject.class, new AjaxCallback<JSONObject>() {


                        @Override
                        public void callback(String url, JSONObject json, AjaxStatus status) {
                            Log.e("beatOrder", json.toString());
                            if (json == null) {

                                if (mDialog.isShowing()) {
                                    animation.stop();
                                    mDialog.dismiss();
                                }

                                Toast.makeText(BeatOrderActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                            } else {


                         /*   if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }
*/
                                if (SelectValueVisited.equalsIgnoreCase("true")) {
                                   // Log.e("beatOrder", json.toString());
                                    Toast.makeText(BeatOrderActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(context, CartActivity.class));
                                    BeatOrderActivity.this.finish();
                                }else
                                {
                                    Toast.makeText(BeatOrderActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(context, HomeActivity.class));
                                    BeatOrderActivity.this.finish();
                                }
                                if (mDialog.isShowing()) {
                                    animation.stop();
                                    mDialog.dismiss();
                                }


                                // Toast.makeText(BeatOrderActivity.this, json.toString(), Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }

            }
        });

        ((TextView) findViewById(R.id.orderNotPlaced)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   Toast.makeText(context, "Sk code saved : "+skCode, Toast.LENGTH_LONG).show();
                //   startActivity(new Intent(context, HomeActivity.class));

                //  Toast.makeText(context, "" + commentsEt.getText().toString() + spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                // BeatOrderActivity.this.finish();



                showLoading();
                JSONObject jsonObject2 = new JSONObject();

                try {
                    jsonObject2.put("SalesPersonId", mRetailerBean.getCustomerId());
                    jsonObject2.put("Skcode", skCode);
                    jsonObject2.put("status", ResonVisit);
                    jsonObject2.put("Comment", commentsEt.getText().toString());

                } catch (Exception e) {

                }

                //http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/
//                http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/AsignDay/addBeat


                new AQuery(getApplicationContext()).post(Constant.BASE_URL_ORDER_NO_PLACED, jsonObject2, JSONObject.class, new AjaxCallback<JSONObject>() {


                    @Override
                    public void callback(String url, JSONObject json, AjaxStatus status) {

                        if (json == null) {

                            if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }

                            Toast.makeText(BeatOrderActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                        } else {


                         /*   if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }
*/
                            Log.e("beatOrder", json.toString());

                            Toast.makeText(BeatOrderActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(context, HomeActivity.class));

                            BeatOrderActivity.this.finish();

                            if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }


                            // Toast.makeText(BeatOrderActivity.this, json.toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            }
        });


        ((TextView) findViewById(R.id.days30)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //   Toast.makeText(context, "Sk code saved : "+skCode, Toast.LENGTH_LONG).show();

                //setMpAndroidGraph("http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/target/Report?day=30day&skcode=");

                setMpAndroidGraph(Constant.BASE_URL_REPORT_30_DAYS);

                ((TextView) findViewById(R.id.month3)).setTextColor(Color.GRAY);
                ((TextView) findViewById(R.id.days30)).setTextColor(Color.BLACK);

            }
        });


        ((TextView) findViewById(R.id.month3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


              //  setMpAndroidGraph("http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/target/Report?day=3 month&skcode=");

                setMpAndroidGraph(Constant.BASE_URL_REPORT_3_MONTHS);

                ((TextView) findViewById(R.id.month3)).setTextColor(Color.BLACK);
                ((TextView) findViewById(R.id.days30)).setTextColor(Color.GRAY);

            }
        });




        ((TextView) findViewById(R.id.my_cart_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(BeatOrderActivity.this, CartActivity.class));
                BeatOrderActivity.this.finish();


            }
        });

        ((TextView) findViewById(R.id.home_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BeatOrderActivity.this, HomeActivity.class));



                BeatOrderActivity.this.finish();
            }
        });

        ((TextView) findViewById(R.id.path)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latlong = new LatLng(Double.parseDouble(lat), Double.parseDouble(longi));
                //LatLng latlong = new LatLng(18.936932, 72.827253);

                Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=" +latlong.latitude+","+latlong.longitude));
                startActivity(navigation);
            }
        });



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
    public void onBackPressed() {
        //  super.onBackPressed();

       // startActivity(new Intent(context, DaysBidActivity.class));

        BeatOrderActivity.this.finish();

        startActivity(new Intent(BeatOrderActivity.this ,DaysBidActivity.class));

    }


    public void setEmptyGraph() {

    }


    public void setMpAndroidGraph(String url) {

//http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/target/Report?day=3 month&skcode=

        System.out.println("Url::"+url+skCode);
        new AQuery(getApplicationContext()).ajax(url + skCode, null, JSONArray.class, new AjaxCallback<JSONArray>() {


            @Override
            public void callback(String url, JSONArray jsonArray, AjaxStatus status) {
                //   Toast.makeText(LoginActivity_Nav.this, "Aquery call"+status.getError()+status.getMessage(), Toast.LENGTH_SHORT).show();

                System.out.println("Response:::"+jsonArray);
                if (jsonArray == null) {
                    Toast.makeText(BeatOrderActivity.this, "Json is null " + status.getError(), Toast.LENGTH_SHORT).show();


                } else {


                    try {

                        jsonArray1 = new JSONArray(jsonArray.toString());
                        JSONObject jsonObject = new JSONObject();


                        //            Toast.makeText(GraphWithApiActivity.this, "Json Lenght "+jsonArray1.length(), Toast.LENGTH_SHORT).show();


                        //            Toast.makeText(GraphWithApiActivity.this, "Json Array "+jsonArray1.toString(), Toast.LENGTH_SHORT).show();

                        totalList = new ArrayList<String>();
                        StatusList = new ArrayList<String>();
                        CommentList = new ArrayList<String>();
                        dateList = new ArrayList<String>();

                        for (int i = 0; i < jsonArray1.length(); i++) {


                            jsonObject = jsonArray1.getJSONObject(i);

                            String date = jsonObject.getString("createdDate");

                            format2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                            newDate2 = format2.parse(date);

                            //          Toast.makeText(GraphWithApiActivity.this, "Date :"+newDate2.toString(), Toast.LENGTH_SHORT).show();


                            //  String getDate = newDate2.getDate();

                            totalList.add(jsonObject.getString("TotalAmount"));
                            StatusList.add(jsonObject.getString("status"));
                            CommentList.add(jsonObject.getString("Comment"));


                            //   totalList.add(jsonObject.getString(newDate2.toString()));


//                            String dateGet = new SimpleDateFormat("dd-MM-yyyy").format(newDate2);


                            String dateGet = new SimpleDateFormat("dd-MM").format(newDate2);


                            //           Toast.makeText(GraphWithApiActivity.this, "Date 2 :"+dateGet, Toast.LENGTH_SHORT).show();

//                            dateList.add(jsonObject.getString(dateGet).toString());


                            dateList.add(dateGet);


                            //  dateList.add(jsonObject.getString("createdDate"));


                            //  Toast.makeText(BeatOrderActivity.this, "Total List : "+totalList.get(i), Toast.LENGTH_SHORT).show();

                            double value = Double.parseDouble(totalList.get(i));


                        }


                        getGraphData(totalList, dateList,StatusList);


                    } catch (Exception e) {

                        Log.e("Json error Beat Order", e.toString());
                        Toast.makeText(BeatOrderActivity.this, "Error json " + e.toString(), Toast.LENGTH_LONG).show();

                    }


                }
            }


        });


    }

    public void getGraphData(ArrayList totalList, ArrayList dateList,ArrayList statusList) {

        if (totalList.isEmpty()) {
         //   Toast.makeText(context, "No Data", Toast.LENGTH_LONG).show();
        }
        else {



            lineChart.setDescription("Amount");
            lineChart.setNoDataTextDescription("No data");

         //   lineChart.setTouchEnabled(false);

            ArrayList<Entry> entries = new ArrayList<>();
            ArrayList<Integer> colors = new ArrayList<Integer>();
            YAxis yAxisRight = lineChart.getAxisRight();
            yAxisRight.setEnabled(false);

            for (int i = 0; i < totalList.size(); i++) {


                //  double value = Double.parseDouble(totalList.get(i).toString());

                float value2 = Float.parseFloat(totalList.get(i).toString());

                entries.add(new Entry(value2, i));
              /*  if (statusList.get(i).toString().trim().equalsIgnoreCase("Order Canceled")) {
                    System.out.println("Cancel"+statusList.get(i).toString().trim());
                    colors.add(this.getResources().getColor(R.color.Red));
                }
                else if (statusList.get(i).toString().trim().equalsIgnoreCase("Delivered")) {
                    System.out.println("Cancel1" + statusList.get(i).toString().trim());
                    colors.add(this.getResources().getColor(R.color.Green));
                }
                else
                {
                    colors.add(this.getResources().getColor(R.color.Yellow));
                }*/


                if( statusList.get(i).toString().trim().equalsIgnoreCase("Order Canceled")||statusList.get(i).toString().trim().equalsIgnoreCase("Delivery Canceled"))
                {
                    System.out.println("Cancel"+statusList.get(i).toString().trim());
                    colors.add(this.getResources().getColor(R.color.Red));

                }else if( statusList.get(i).toString().trim().equalsIgnoreCase("Pending")|| statusList.get(i).toString().trim().equalsIgnoreCase("Ready to Dispatch")|| statusList.get(i).toString().trim().equalsIgnoreCase("Shipped")|| statusList.get(i).toString().trim().equalsIgnoreCase("Issued"))
                {
                   colors.add(this.getResources().getColor(R.color.Yellow));

                }
                else if( statusList.get(i).toString().trim().equalsIgnoreCase("Delivered")|| statusList.get(i).toString().trim().equalsIgnoreCase("sattled")|| statusList.get(i).toString().trim().equalsIgnoreCase("account sattle")|| statusList.get(i).toString().trim().equalsIgnoreCase("Bank sattle"))
                {
                  System.out.println("Cancel1" + statusList.get(i).toString().trim());
                    colors.add(this.getResources().getColor(R.color.Green));

                }





            }
            System.out.println("Amount:::::"+totalList);
            //colors.add( this.getResources().getColor( R.color.colorPrimary ) );

            LineDataSet dataset = new LineDataSet(entries, "Amount");

            ArrayList<String> labels = dateList;

            LineData data = new LineData(labels, dataset);

            dataset.setColors(ColorTemplate.COLORFUL_COLORS);
//
            dataset.setDrawCubic(true);
            dataset.setDrawFilled(true);
            dataset.setCircleColors(colors);
            dataset.setCircleSize(5);
            dataset.setValueTextSize(10);
            lineChart.setData(data);
            lineChart.animateY(1000);

        }

    }






    public void callAquery() {

        showLoading();
        new AQuery(getApplicationContext()).ajax(urlRemote, null, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {



                if (json == null) {


                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    Toast.makeText(BeatOrderActivity.this, "Please try again", Toast.LENGTH_SHORT).show();


                } else {

                //    Toast.makeText(BeatOrderActivity.this, ""+ json.toString(), Toast.LENGTH_SHORT).show();
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    try {

                        JSONObject  jsonObject = json.getJSONObject("wallet");
                        JSONObject rewardJson = json.getJSONObject("reward");
                        String amount = jsonObject.getString("TotalAmount");
                        tvRewardPoint.setText("Customer Wallet point = " + amount);


                        // amountTv.setText("Total Amount "+amount);

                /*        ((TextView) findViewById(R.id.credit)).setText("Credit "+jsonObject.getString("CreditAmount"));


                        ((TextView) findViewById(R.id.debit)).setText("Debit "+jsonObject.getString("DebitAmount"));
*/
                     /*   ((TextView) findViewById(R.id.total_point)).setText("Total point "+rewardJson.getString("TotalPoint"));

                        ((TextView) findViewById(R.id.earning_point)).setText("Earning Point "+rewardJson.getString("EarningPoint"));

                        ((TextView) findViewById(R.id.used_point)).setText("Used Point "+rewardJson.getString("UsedPoint"));

                        ((TextView) findViewById(R.id.milestone_point)).setText("Milestone Point "+rewardJson.getString("MilestonePoint"));

*/


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //   Toast.makeText(SkCodeActivity.this, json.toString(), Toast.LENGTH_SHORT).show();

                    //     Toast.makeText(MyWallet.this, json.toString(), Toast.LENGTH_SHORT).show();



                }


            }
        });


    }


    public void showLoading() {
        mDialog = new Dialog(BeatOrderActivity.this);
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






    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(BeatOrderActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
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
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                      //  showGPSNotStartAlert();
                       // Toast.makeText(LocationChangedActivity.this, "Please Enable GPS", Toast.LENGTH_LONG).show();

                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }

    public class UpdateAsyncTask extends AsyncTask<String, Void, JSONObject> {


        String emailString, addressString, nameString;




        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {

            mDialog = new Dialog(BeatOrderActivity.this);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Placing Order...");
            ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            mDialog.setCancelable(false);
            mDialog.show();

          //  emailString = etAddress.getText().toString();

        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObjectFromUrl = null;

            try {

                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(BeatOrderActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                JSONObject mRequesParamObj = new JSONObject();
                mRequesParamObj.put("CreatedDate", currentDateandTime);
                mRequesParamObj.put("lat", latitude);
                mRequesParamObj.put("long", longitude);
                mRequesParamObj.put("CustomerType", "");
                mRequesParamObj.put("SalesPersonId", mRetailerBean.getCustomerId());
                mRequesParamObj.put("ShippingAddress", SelectValueVisited);
                jsonObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonFromHttpUrlConnectionJsonRequest_Post(Constant.BASE_URL_PLACE_ORDER, mRequesParamObj);

                Log.e("ordererror", mRequesParamObj.toString());


            } catch (Exception e) {

                //  Toast.makeText(CheckOutActivity.this, "Json error", Toast.LENGTH_SHORT).show();

                Log.e("ordererror", e.toString());
                e.printStackTrace();
            }



            return jsonObjectFromUrl;
        }

        @Override
        protected void onPostExecute(JSONObject mJsonObject) {
            if (mJsonObject != null) {
                Toast.makeText(BeatOrderActivity.this, "updated successfully..", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BeatOrderActivity.this, HomeActivity.class));
            }

            else {

                Toast.makeText(BeatOrderActivity.this, "Unable to update, please try again", Toast.LENGTH_LONG).show();

            }

            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
        }
    }

    public void callAqueryDialAvailable() {
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(BeatOrderActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        String url=Constant.BASE_URL+"DialPoint?CustomerId="+skCodeCId;
        System.out.println("UrlDial::"+url);
        //showLoading();
        new AQuery(getApplicationContext()).ajax(url, null, JSONArray.class, new AjaxCallback<JSONArray>() {

            @Override
            public void callback(String url, JSONArray json, AjaxStatus status) {


                System.out.println("JsonData"+json);
                if (json.toString().equalsIgnoreCase("[]")) {

/*
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }*/
                    dial_availability.setEnabled(false);
                    Toast.makeText(BeatOrderActivity.this, "No dial available", Toast.LENGTH_SHORT).show();


                } else {

                    ArrayList<DialItem> dialItemArrayList = new ArrayList<>();
                    //    Toast.makeText(BeatOrderActivity.this, ""+ json.toString(), Toast.LENGTH_SHORT).show();
                /*    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }*/

                    try {
                        dial_availability.setEnabled(true);
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject mJsonObject = json.getJSONObject(i);
                            String id= mJsonObject.getString("Id");
                            String customerId= mJsonObject.getString("CustomerId");
                            String orderId= mJsonObject.getString("OrderId");
                            String orderAmount= mJsonObject.getString("OrderAmount");
                            int point= mJsonObject.getInt("point");
                            String usedUnused= mJsonObject.getString("UsedUnused");
                            String createdDate= mJsonObject.getString("CreatedDate");
                            Id=id;
                            OrderId=orderId;
                            Point= String.valueOf(point);
                            System.out.println("point:123::"+Point);
                           // Point=point;
                            dialItemArrayList.add(new DialItem(id,customerId,orderId,orderAmount,Point,usedUnused,createdDate));

                        }
                        System.out.println("dialItemArrayList::"+dialItemArrayList.size());
                       int Dialno= dialItemArrayList.size();
                        dial_availability.setText(String.valueOf(Dialno)+"  Dial Available");


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }


            }
        });


    }


}



