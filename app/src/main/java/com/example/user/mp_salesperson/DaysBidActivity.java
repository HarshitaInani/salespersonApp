package com.example.user.mp_salesperson;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.adapters.MyBidAdapter;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.customClasses.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DaysBidActivity extends AppCompatActivity {

    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();

    RecyclerView recyclerView;
    MyBidAdapter myBidAdapter;
    Context context;

    String peopleId;

    ProgressDialog progressDialog;

    Dialog mDialog;
    AnimationDrawable animation;


    EditText searchEt;

    private List<String> list = new ArrayList<String>();


    JSONArray searchJsonArray = new JSONArray();

    JSONObject searchJsonObject = new JSONObject();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_days_bid);

        context = this;

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading..");

        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        searchEt = (EditText) findViewById( R.id.search);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);

        ((TextView) findViewById(R.id.title_toolbar)).setText("My Beat");




        if (!Utility.isConnectingToInternet(DaysBidActivity.this)){
            Toast.makeText(this, "Please check Internet connection", Toast.LENGTH_SHORT).show();
        }





        ((ImageView) findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Utility.setStringSharedPreference(DaysBidActivity.this, "Bidmonday", "");
                Utility.setStringSharedPreference(DaysBidActivity.this, "Bidtuesday", "");
                Utility.setStringSharedPreference(DaysBidActivity.this, "Bidwednesday", "");
                Utility.setStringSharedPreference(DaysBidActivity.this, "Bidthursday", "");
                Utility.setStringSharedPreference(DaysBidActivity.this, "Bidfriday", "");
                Utility.setStringSharedPreference(DaysBidActivity.this, "Bidsaturday", "");
                Utility.setStringSharedPreference(DaysBidActivity.this, "Bidsunday", "");



                startActivity(new Intent(DaysBidActivity.this, DaysBidActivity.class));

                DaysBidActivity.this.finish();
            }
        });



        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaysBidActivity.this.finish();
            }
        });


        //  Toast.makeText(DaysBidActivity.this, dayOfTheWeek, Toast.LENGTH_SHORT).show();




        //  recyclerView = new RecyclerView(context);



//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //recyclerView.set

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(context, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);


       peopleId =  mRetailerBean.getCustomerId();


      //  addTextListener();



        if (dayOfTheWeek.trim().equals("Monday")) {

            clearTab();


            ((TextView) findViewById(R.id.monday)).setBackgroundColor(Color.WHITE);
            ((TextView) findViewById(R.id.monday)).setTextColor(Color.BLACK);

            callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=monday",
                    "monday");

        }

        if (dayOfTheWeek.trim().equals("Tuesday")) {

            clearTab();

            ((TextView) findViewById(R.id.tuesday)).setBackgroundColor(Color.WHITE);
            ((TextView) findViewById(R.id.tuesday)).setTextColor(Color.BLACK);

            callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=tuesday",
                    "tuesday");

        }

        if (dayOfTheWeek.trim().equals("Wednesday")) {

            clearTab();

            ((TextView) findViewById(R.id.wednesday)).setBackgroundColor(Color.WHITE);
            ((TextView) findViewById(R.id.wednesday)).setTextColor(Color.BLACK);

            callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=wednesday",
                    "wednesday");

        }

        if (dayOfTheWeek.trim().equals("Thursday")) {

            clearTab();

            ((TextView) findViewById(R.id.thrusday)).setBackgroundColor(Color.WHITE);
            ((TextView) findViewById(R.id.thrusday)).setTextColor(Color.BLACK);

            callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=thursday",
                    "thursday");

        }



        if (dayOfTheWeek.trim().equals("Friday")) {

            clearTab();


            ((TextView) findViewById(R.id.friday)).setBackgroundColor(Color.WHITE);
            ((TextView) findViewById(R.id.friday)).setTextColor(Color.BLACK);

            callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=friday",
                    "friday");

        }

        if (dayOfTheWeek.trim().equals("Saturday")) {

            clearTab();


            ((TextView) findViewById(R.id.saturday)).setBackgroundColor(Color.WHITE);
            ((TextView) findViewById(R.id.saturday)).setTextColor(Color.BLACK);

            callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=saturday",
                    "saturday");

        }

        if (dayOfTheWeek.trim().equals("Sunday")) {

            clearTab();


            ((TextView) findViewById(R.id.sunday)).setBackgroundColor(Color.WHITE);
            ((TextView) findViewById(R.id.sunday)).setTextColor(Color.BLACK);

            callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=sunday",
                    "sunday");

        }



//        String urlRemote = "http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/AsignDay/customer?id=2&day=";
//        String urlRemote = "http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/AsignDay/customer?id="+mRetailerBean.getCustomerId()+"&day=";

        String urlRemote = Constant.BASE_URL_MYBID+"/customer?id="+mRetailerBean.getCustomerId()+"&day=";

/*
        ((TextView) findViewById(R.id.allcustomer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(context, BeatOrderActivity.class));

            }
        });*/

        ((TextView) findViewById(R.id.allcustomer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=friday");


   /*             ((TextView) findViewById(R.id.monday)).setBackgroundColor(Color.BLACK);
                ((TextView) findViewById(R.id.monday)).setTextColor(Color.WHITE);


                ((TextView) findViewById(R.id.tuesday)).setBackgroundColor(Color.BLACK);
                ((TextView) findViewById(R.id.tuesday)).setTextColor(Color.WHITE);

                ((TextView) findViewById(R.id.wednesday)).setBackgroundColor(Color.BLACK);
                ((TextView) findViewById(R.id.wednesday)).setTextColor(Color.WHITE);

                ((TextView) findViewById(R.id.thrusday)).setBackgroundColor(Color.BLACK);
                ((TextView) findViewById(R.id.thrusday)).setTextColor(Color.WHITE);

                ((TextView) findViewById(R.id.friday)).setBackgroundColor(Color.BLACK);
                ((TextView) findViewById(R.id.friday)).setTextColor(Color.WHITE);


                ((TextView) findViewById(R.id.saturday)).setBackgroundColor(Color.BLACK);
                ((TextView) findViewById(R.id.saturday)).setTextColor(Color.WHITE);

                ((TextView) findViewById(R.id.sunday)).setBackgroundColor(Color.BLACK);
                ((TextView) findViewById(R.id.sunday)).setTextColor(Color.WHITE);
*/


                clearTab();
             callAqueryAllCustomer();

            }
        });





        ((TextView) findViewById(R.id.monday)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                clearTab();


                ((TextView) findViewById(R.id.monday)).setBackgroundColor(Color.WHITE);
                ((TextView) findViewById(R.id.monday)).setTextColor(Color.BLACK);

                callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=monday","monday");
            }
        });


        ((TextView) findViewById(R.id.tuesday)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                clearTab();

                ((TextView) findViewById(R.id.tuesday)).setBackgroundColor(Color.WHITE);
                ((TextView) findViewById(R.id.tuesday)).setTextColor(Color.BLACK);

                callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=tuesday",
                        "tuesday");
            }
        });


        ((TextView) findViewById(R.id.wednesday)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






                clearTab();

                ((TextView) findViewById(R.id.wednesday)).setBackgroundColor(Color.WHITE);
                ((TextView) findViewById(R.id.wednesday)).setTextColor(Color.BLACK);

                callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=wednesday",
                        "wednesday");
            }
        });



        ((TextView) findViewById(R.id.thrusday)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTab();
                ((TextView) findViewById(R.id.thrusday)).setBackgroundColor(Color.WHITE);
                ((TextView) findViewById(R.id.thrusday)).setTextColor(Color.BLACK);

                callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=thursday",
                        "thursday");
            }
        });


        ((TextView) findViewById(R.id.friday)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTab();
                ((TextView) findViewById(R.id.friday)).setBackgroundColor(Color.WHITE);
                ((TextView) findViewById(R.id.friday)).setTextColor(Color.BLACK);

                callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=friday", "friday");
            }
        });


        ((TextView) findViewById(R.id.saturday)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTab();
                ((TextView) findViewById(R.id.saturday)).setBackgroundColor(Color.WHITE);
                ((TextView) findViewById(R.id.saturday)).setTextColor(Color.BLACK);
                callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=saturday", "saturday");
            }
        });



        ((TextView) findViewById(R.id.sunday)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearTab();
                ((TextView) findViewById(R.id.sunday)).setBackgroundColor(Color.WHITE);
                ((TextView) findViewById(R.id.sunday)).setTextColor(Color.BLACK);

                callAquery(Constant.BASE_URL_MYBID+"/customer?id="+peopleId+"&day=sunday",
                        "sunday");
            }
        });




  /*      new AQuery(getApplicationContext()).ajax(urlRemote, null, JSONArray.class, new AjaxCallback<JSONArray>() {


            @Override
            public void callback(String url, JSONArray json, AjaxStatus status) {

                progressDialog.show();


                if (json == null) {


                    progressDialog.dismiss();
                    Toast.makeText(context, "Json is null \n"+status.getError().toString()+"\n"+status.getMessage().toString(), Toast.LENGTH_SHORT).show();


                } else {

                    progressDialog.dismiss();
                    //  Toast.makeText(MyBeatActivity.this, json.toString(), Toast.LENGTH_SHORT).show();

                    try {
                        jsonArray = new JSONArray(json.toString());

                        for (int i=0; i < jsonArray.length(); i++) {

                            jsonObject = jsonArray.getJSONObject(i);
                            //  Toast.makeText(MyBeatActivity.this, jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();

                        }
                        //   Toast.makeText(MyBeatActivity.this, "Json Array " + jsonArray.toString() , Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    myBidAdapter = new MyBidAdapter(context, jsonArray, jsonArray.length());
                    recyclerView.setAdapter(myBidAdapter);


                }


            }
        });

*/


    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();

        startActivity(new Intent(context, HomeActivity.class));
        DaysBidActivity.this.finish();
    }


    public void onclick() {


    }

    public void callAquery(String dayUrl, final String day) {

        showLoading();

        System.out.println("BitsDay:::"+dayUrl);

     //   searchJsonArray = new JSONArray();
      //  searchJsonObject = new JSONObject();




        String dataSaved = Utility.getStringSharedPreferences(DaysBidActivity.this, "Bid"+day);


        if (!dataSaved.isEmpty()) {
         //   Toast.makeText(DaysBidActivity.this, ""+dataSaved, Toast.LENGTH_SHORT).show();




            try {
                jsonArray = new JSONArray(dataSaved.toString());

                for (int i=0; i < jsonArray.length(); i++) {

                    jsonObject = jsonArray.getJSONObject(i);
                    //  Toast.makeText(MyBeatActivity.this, jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();

                }
                //   Toast.makeText(MyBeatActivity.this, "Json Array " + jsonArray.toString() , Toast.LENGTH_SHORT).show();


            } catch (JSONException e) {
                e.printStackTrace();
            }

            myBidAdapter = new MyBidAdapter(context, jsonArray, jsonArray.length());
            recyclerView.setAdapter(myBidAdapter);







            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }






            searchEt.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {



                    String query = s.toString();



                    if (query.length() == 0 ) {
                        myBidAdapter = new MyBidAdapter(context, jsonArray, jsonArray.length());
                        recyclerView.setAdapter(myBidAdapter);

                    }



                    if (query.length() >= 2) {

                    searchJsonArray = new JSONArray();
                    searchJsonObject = new JSONObject();



                        query = query.toString().toLowerCase();

                        final List<String> filteredList = new ArrayList<>();

/*


                    for (int i = 0; i < list.size(); i++) {

                        final String text = list.get(i).toLowerCase();
                        if (text.contains(query)) {

                            filteredList.add(list.get(i));
                        }
                    }


                    Toast.makeText(context, ""+filteredList.toString(), Toast.LENGTH_SHORT).show();


*/

                        for (int i = 0; i < jsonArray.length(); i++) {

                            try {
                                jsonObject = jsonArray.getJSONObject(i);

                                // final String text = list.get(i).toLowerCase();

                                final String ShopName = jsonObject.getString("ShopName").toLowerCase();
                                final String Skcode = jsonObject.getString("Skcode").toLowerCase();

                                if (ShopName.contains(query)) {

                                    filteredList.add(jsonObject.getString("ShopName"));
                                    searchJsonArray.put(jsonObject);
                                }else if(Skcode.contains(query))
                                {
                                    filteredList.add(jsonObject.getString("Skcode"));
                                    searchJsonArray.put(jsonObject);
                                }

                            } catch (JSONException e) {

                                Toast.makeText(context, "Json error", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }
                            //  Toast.makeText(MyBeatActivity.this, jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();


//                        Toast.makeText(context, ""+filteredList.toString(), Toast.LENGTH_SHORT).show();


                        }


                        if (!filteredList.isEmpty()) {

//                        Toast.makeText(context, "In Aquery " + filteredList.toString(), Toast.LENGTH_SHORT).show();


                       //     Toast.makeText(context, "In Aquery " + searchJsonArray.toString(), Toast.LENGTH_SHORT).show();


                    /*        myBidAdapter = new MyBidAdapter(context, searchJsonArray, searchJsonArray.length());
                            recyclerView.setAdapter(myBidAdapter);
                            myBidAdapter.notifyDataSetChanged();
*/


                        } else {



                           // Toast.makeText(context, "Not found", Toast.LENGTH_SHORT).show();
                        }



                        myBidAdapter = new MyBidAdapter(context, searchJsonArray, searchJsonArray.length());
                        recyclerView.setAdapter(myBidAdapter);
                        myBidAdapter.notifyDataSetChanged();


                    }




                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                public void onTextChanged(CharSequence query, int start, int before, int count) {

     /*               query = query.toString().toLowerCase();

                    final List<String> filteredList = new ArrayList<>();

*//*


                    for (int i = 0; i < list.size(); i++) {

                        final String text = list.get(i).toLowerCase();
                        if (text.contains(query)) {

                            filteredList.add(list.get(i));
                        }
                    }


                    Toast.makeText(context, ""+filteredList.toString(), Toast.LENGTH_SHORT).show();


*//*

                    for (int i=0; i < jsonArray.length(); i++) {

                        try {
                            jsonObject = jsonArray.getJSONObject(i);

                           // final String text = list.get(i).toLowerCase();

                            final String text = jsonObject.getString("ShopName").toLowerCase();


                            if (text.contains(query)) {

                                filteredList.add(jsonObject.getString("ShopName"));

                               searchJsonArray.put(jsonObject);
                            }

                        } catch (JSONException e) {

                            Toast.makeText(context, "Json error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        //  Toast.makeText(MyBeatActivity.this, jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();


//                        Toast.makeText(context, ""+filteredList.toString(), Toast.LENGTH_SHORT).show();


                    }


                    if (!filteredList.isEmpty()) {

//                        Toast.makeText(context, "In Aquery " + filteredList.toString(), Toast.LENGTH_SHORT).show();


                        Toast.makeText(context, "In Aquery " + searchJsonArray.toString(), Toast.LENGTH_SHORT).show();


                        myBidAdapter = new MyBidAdapter(context, searchJsonArray, searchJsonArray.length());
                        recyclerView.setAdapter(myBidAdapter);
                        myBidAdapter.notifyDataSetChanged();

                    }
*/


        /*        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mAdapter = new SimpleAdapter(filteredList, MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed
        */


                }




            });


        }


        else {


            new AQuery(getApplicationContext()).ajax(dayUrl, null, JSONArray.class, new AjaxCallback<JSONArray>() {

                @Override
                public void callback(String url, JSONArray json, AjaxStatus status) {


                    if (json == null) {


                        if (mDialog.isShowing()) {
                            animation.stop();
                            mDialog.dismiss();
                        }

                        Toast.makeText(context, "Please try again!", Toast.LENGTH_SHORT).show();


                    } else {

                        Toast.makeText(context, "Downloading data", Toast.LENGTH_SHORT).show();

                        Utility.setStringSharedPreference(DaysBidActivity.this, "Bid" + day, json.toString());
                        //  Toast.makeText(MyBeatActivity.this, json.toString(), Toast.LENGTH_SHORT).show();

                        try {
                            jsonArray = new JSONArray(json.toString());

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);
                                //  Toast.makeText(MyBeatActivity.this, jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();

                            }
                            //   Toast.makeText(MyBeatActivity.this, "Json Array " + jsonArray.toString() , Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        myBidAdapter = new MyBidAdapter(context, jsonArray, jsonArray.length());
                        recyclerView.setAdapter(myBidAdapter);



                        if (mDialog.isShowing()) {
                            animation.stop();
                            mDialog.dismiss();
                        }








                        searchEt.addTextChangedListener(new TextWatcher() {

                            public void afterTextChanged(Editable s) {



                                String query = s.toString();



                                if (query.length() == 0 ) {
                                    myBidAdapter = new MyBidAdapter(context, jsonArray, jsonArray.length());
                                    recyclerView.setAdapter(myBidAdapter);

                                }



                                if (query.length() >= 2) {

                                    searchJsonArray = new JSONArray();
                                    searchJsonObject = new JSONObject();



                                    query = query.toString().toLowerCase();

                                    final List<String> filteredList = new ArrayList<>();

/*


                    for (int i = 0; i < list.size(); i++) {

                        final String text = list.get(i).toLowerCase();
                        if (text.contains(query)) {

                            filteredList.add(list.get(i));
                        }
                    }


                    Toast.makeText(context, ""+filteredList.toString(), Toast.LENGTH_SHORT).show();


*/

                                    for (int i = 0; i < jsonArray.length(); i++) {

                                        try {
                                            jsonObject = jsonArray.getJSONObject(i);


                                            System.out.println("jsonObject::"+jsonObject);
                                            // final String text = list.get(i).toLowerCase();

                                            final String ShopName = jsonObject.getString("ShopName").toLowerCase();
                                            final String Skcode = jsonObject.getString("Skcode").toLowerCase();


                                            if (ShopName.contains(query)) {

                                                filteredList.add(jsonObject.getString("ShopName"));

                                                searchJsonArray.put(jsonObject);
                                            }else if(Skcode.contains(query)){

                                                filteredList.add(jsonObject.getString("Skcode"));

                                                searchJsonArray.put(jsonObject);
                                            }

                                        } catch (JSONException e) {

                                            Toast.makeText(context, "Json error", Toast.LENGTH_SHORT).show();
                                            e.printStackTrace();
                                        }
                                        //  Toast.makeText(MyBeatActivity.this, jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();


//                        Toast.makeText(context, ""+filteredList.toString(), Toast.LENGTH_SHORT).show();


                                    }


                                    if (!filteredList.isEmpty()) {

//                        Toast.makeText(context, "In Aquery " + filteredList.toString(), Toast.LENGTH_SHORT).show();


                                        //     Toast.makeText(context, "In Aquery " + searchJsonArray.toString(), Toast.LENGTH_SHORT).show();


                                        myBidAdapter = new MyBidAdapter(context, searchJsonArray, searchJsonArray.length());
                                        recyclerView.setAdapter(myBidAdapter);
                                        myBidAdapter.notifyDataSetChanged();

                                    }


                                }




                            }

                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                            public void onTextChanged(CharSequence query, int start, int before, int count) {

     /*               query = query.toString().toLowerCase();

                    final List<String> filteredList = new ArrayList<>();

*//*


                    for (int i = 0; i < list.size(); i++) {

                        final String text = list.get(i).toLowerCase();
                        if (text.contains(query)) {

                            filteredList.add(list.get(i));
                        }
                    }


                    Toast.makeText(context, ""+filteredList.toString(), Toast.LENGTH_SHORT).show();


*//*

                    for (int i=0; i < jsonArray.length(); i++) {

                        try {
                            jsonObject = jsonArray.getJSONObject(i);

                           // final String text = list.get(i).toLowerCase();

                            final String text = jsonObject.getString("ShopName").toLowerCase();


                            if (text.contains(query)) {

                                filteredList.add(jsonObject.getString("ShopName"));

                               searchJsonArray.put(jsonObject);
                            }

                        } catch (JSONException e) {

                            Toast.makeText(context, "Json error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                        //  Toast.makeText(MyBeatActivity.this, jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();


//                        Toast.makeText(context, ""+filteredList.toString(), Toast.LENGTH_SHORT).show();


                    }


                    if (!filteredList.isEmpty()) {

//                        Toast.makeText(context, "In Aquery " + filteredList.toString(), Toast.LENGTH_SHORT).show();


                        Toast.makeText(context, "In Aquery " + searchJsonArray.toString(), Toast.LENGTH_SHORT).show();


                        myBidAdapter = new MyBidAdapter(context, searchJsonArray, searchJsonArray.length());
                        recyclerView.setAdapter(myBidAdapter);
                        myBidAdapter.notifyDataSetChanged();

                    }
*/


        /*        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mAdapter = new SimpleAdapter(filteredList, MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed
        */


                            }




                        });





















                    }


                }

           /*     if (json == null) {


                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    Toast.makeText(context, "Please try again!", Toast.LENGTH_SHORT).show();


                } else {


                    Utility.setStringSharedPreference(DaysBidActivity.this, "Bid"+day, json.toString());
                    //  Toast.makeText(MyBeatActivity.this, json.toString(), Toast.LENGTH_SHORT).show();

                    try {
                        jsonArray = new JSONArray(json.toString());

                        for (int i=0; i < jsonArray.length(); i++) {

                            jsonObject = jsonArray.getJSONObject(i);
                            //  Toast.makeText(MyBeatActivity.this, jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();

                        }
                        //   Toast.makeText(MyBeatActivity.this, "Json Array " + jsonArray.toString() , Toast.LENGTH_SHORT).show();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    myBidAdapter = new MyBidAdapter(context, jsonArray, jsonArray.length());
                    recyclerView.setAdapter(myBidAdapter);


                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                }


           */


            });

        }

    }

    public void callAqueryAllCustomer() {

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


                    Toast.makeText(context, "Please try again!", Toast.LENGTH_SHORT).show();


                } else {

                    //  Toast.makeText(MyBeatActivity.this, json.toString(), Toast.LENGTH_SHORT).show();

                    try {
                        jsonArray = new JSONArray(json.toString());

                        for (int i=0; i < jsonArray.length(); i++) {

                            jsonObject = jsonArray.getJSONObject(i);

                          //  Toast.makeText(DaysBidActivity.this, jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();

                        }

                        //   Toast.makeText(MyBeatActivity.this, "Json Array " + jsonArray.toString() , Toast.LENGTH_SHORT).show();



                       // Toast

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    myBidAdapter = new MyBidAdapter(context, jsonArray, jsonArray.length());
                    recyclerView.setAdapter(myBidAdapter);

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }


                }


            }
        });










    }

    public void clearTab() {


        searchEt.setText("");
        ((TextView) findViewById(R.id.monday)).setBackgroundColor(Color.BLACK);
        ((TextView) findViewById(R.id.monday)).setTextColor(Color.WHITE);


        ((TextView) findViewById(R.id.tuesday)).setBackgroundColor(Color.BLACK);
        ((TextView) findViewById(R.id.tuesday)).setTextColor(Color.WHITE);


        ((TextView) findViewById(R.id.wednesday)).setBackgroundColor(Color.BLACK);
        ((TextView) findViewById(R.id.wednesday)).setTextColor(Color.WHITE);


        ((TextView) findViewById(R.id.thrusday)).setBackgroundColor(Color.BLACK);
        ((TextView) findViewById(R.id.thrusday)).setTextColor(Color.WHITE);

        ((TextView) findViewById(R.id.friday)).setBackgroundColor(Color.BLACK);
        ((TextView) findViewById(R.id.friday)).setTextColor(Color.WHITE);


        ((TextView) findViewById(R.id.saturday)).setBackgroundColor(Color.BLACK);
        ((TextView) findViewById(R.id.saturday)).setTextColor(Color.WHITE);

        ((TextView) findViewById(R.id.sunday)).setBackgroundColor(Color.BLACK);
        ((TextView) findViewById(R.id.sunday)).setTextColor(Color.WHITE);



    }


    public void showLoading() {


        mDialog = new Dialog(DaysBidActivity.this);
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





    public void addTextListener(){



        list.add("Afghanistan");
        list.add("Albania");
        list.add("Algeria");
        list.add("Bangladesh");
        list.add("Belarus");
        list.add("Canada");
        list.add("Cape Verde");
        list.add("Central African Republic");
        list.add("Denmark");
        list.add("Dominican Republic");
        list.add("Egypt");
        list.add("France");
        list.add("Germany");
        list.add("Hong Kong");
        list.add("India");
        list.add("Iceland");


        searchEt.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final List<String> filteredList = new ArrayList<>();

                for (int i = 0; i < list.size(); i++) {

                    final String text = list.get(i).toLowerCase();
                    if (text.contains(query)) {

                        filteredList.add(list.get(i));
                    }
                }


                Toast.makeText(context, ""+filteredList.toString(), Toast.LENGTH_SHORT).show();





        /*        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                mAdapter = new SimpleAdapter(filteredList, MainActivity.this);
                mRecyclerView.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();  // data set changed
        */


            }




        });
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




}
