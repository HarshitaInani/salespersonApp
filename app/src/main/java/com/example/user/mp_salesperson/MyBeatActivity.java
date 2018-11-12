package com.example.user.mp_salesperson;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.adapters.MyBidAdapter;
import com.example.user.mp_salesperson.bean.RetailerBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyBeatActivity extends AppCompatActivity {


    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();

    RecyclerView recyclerView;
    MyBidAdapter myBidAdapter;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_beat);

        context = this;


        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((TextView) findViewById(R.id.title_toolbar)).setText("My Beat");

        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyBeatActivity.this.finish();
            }
        });

        //  recyclerView = new RecyclerView(context);



//        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //recyclerView.set

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyBeatActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);


        mRetailerBean.getCustomerId();


//        String urlRemote = "http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/AsignDay/customer?id=2&day=";
//        String urlRemote = "http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/AsignDay/customer?id="+mRetailerBean.getCustomerId()+"&day=";

        String urlRemote = Constant.BASE_URL_MYBID+"/customer?id="+mRetailerBean.getCustomerId()+"&day=";


        ((TextView) findViewById(R.id.myAllCustomer)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MyBeatActivity.this, BeatOrderActivity.class));

            }
        });


        new AQuery(getApplicationContext()).ajax(urlRemote, null, JSONArray.class, new AjaxCallback<JSONArray>() {

            @Override
            public void callback(String url, JSONArray json, AjaxStatus status) {



                if (json == null) {


                    Toast.makeText(MyBeatActivity.this, "Json is null \n"+status.getError().toString()+"\n"+status.getMessage().toString(), Toast.LENGTH_SHORT).show();


                } else {


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

                    myBidAdapter = new MyBidAdapter(context, jsonArray, 9 );
                    recyclerView.setAdapter(myBidAdapter);


                }


            }
        });


    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();

        startActivity(new Intent(context, HomeActivity.class));
    }
}
