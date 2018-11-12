package com.example.user.mp_salesperson;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.example.user.mp_salesperson.adapters.ActionTaskAdapter;
import com.example.user.mp_salesperson.bean.RetailerBean;

import org.json.JSONArray;

public class ActionTaskMultiActivity extends AppCompatActivity {


    ActionTaskAdapter adapter;
    RecyclerView recyclerView;

     RetailerBean mRetailerBean;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_task_multi);

        context = this;

        recyclerView = (RecyclerView) findViewById(R.id.recyeler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(ActionTaskMultiActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
         mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);


        ((TextView) findViewById(R.id.title_toolbar)).setText("My Action List");

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  startActivity(new Intent(ActionTaskActivity.this, CartActivity.class));
                ActionTaskMultiActivity.this.finish();
            }
        });




        callApi();
    }

    public void callApi() {

        new AQuery(context).ajax(Constant.BASE_URL_ACTION_TASK_MULTI+"?PeopleId="+mRetailerBean.getPeopleId(),
                null,
                JSONArray.class,
                 new AjaxCallback<JSONArray>(){


                     @Override
                     public void callback(String url, JSONArray jsonArray, AjaxStatus status) {


                         if (jsonArray != null) {
                          //   Toast.makeText(ActionTaskMultiActivity.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();

                             adapter = new ActionTaskAdapter(context, jsonArray, null, jsonArray.length());
                             recyclerView.setAdapter(adapter);


                         } else {
                             Toast.makeText(ActionTaskMultiActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                         }
                     }
                 }
                );


/*
        JSONArray jsonArray = new JSONArray();

        JSONObject jsonObject = new JSONObject();

        HashMap<String, String> stringStringHashMap = new HashMap<>();

  */


       /* new AQuery(context).ajax(Constant.BASE_URL_ACTION_TASK_MULTI+"?PeopleId=8",

                ,
                JSONArray.class,
                new AjaxCallback<JSONArray>(){


                    @Override
                    public void callback(String url, JSONArray jsonArray, AjaxStatus status) {


                        if (jsonArray != null) {
                            Toast.makeText(ActionTaskMultiActivity.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();

                            adapter = new ActionTaskAdapter(context, jsonArray, null, jsonArray.length());
                            recyclerView.setAdapter(adapter);


                        } else {
                            Toast.makeText(ActionTaskMultiActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
*/



    }
}
