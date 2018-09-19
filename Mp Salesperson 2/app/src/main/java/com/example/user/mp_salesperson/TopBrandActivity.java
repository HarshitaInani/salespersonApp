package com.example.user.mp_salesperson;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.adapters.TopBrandAdapter;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.bean.TopList;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.ItemList;
import com.example.user.mp_salesperson.customClasses.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TopBrandActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private ProgressDialog progressDialog;
    private List<TopList> listTopList;
    TopList topList;
    TextView title_toolbar;
    Dialog mDialog;
    AnimationDrawable animation;
    String peopleId;
    JSONArray jsonArray = new JSONArray();
    JSONObject jsonObject = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_brand);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);
        title_toolbar=(TextView)findViewById(R.id.title_toolbar) ;
        title_toolbar.setText("Top Brand");
        listTopList = new ArrayList<>();

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView)findViewById(R.id.top_brand_list);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);


        peopleId =  mRetailerBean.getCustomerId();
        if(isNetworkAvailable())
        {
           callAqueryTopBrand();
        }else
        {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TopBrandActivity.this.finish();
                startActivity(new Intent(TopBrandActivity.this ,BeatOrderActivity.class));

            }
        });





        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TopBrandActivity.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TopBrandActivity.this.finish();
                startActivity(new Intent(TopBrandActivity.this, HomeActivity.class));
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
                PopupMenu popup = new PopupMenu(TopBrandActivity.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();
//                inflate.inflate(R.menu.my_order_option_menu, popup.getMenu());
                inflate.inflate(R.menu.home, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(TopBrandActivity.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(TopBrandActivity.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        }else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(TopBrandActivity.this, MyWallet.class));
                            return true;
                        }
                        else if (id == R.id.myDial) {
                            startActivity(new Intent(TopBrandActivity.this, MyDialListActivity.class));
                            return true;
                        }
                        else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(TopBrandActivity.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(TopBrandActivity.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(TopBrandActivity.this, RequestBrandActivity.class));
                            return true;
                        } else if (id == R.id.action_feedback) {
                            startActivity(new Intent(TopBrandActivity.this, FeedbackActivity.class));
                            return true;



                        } else if (id == R.id.action_logout) {


                     /*       ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(BeatOrderActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();
                            startActivity(new Intent(BeatOrderActivity.this, LoginActivity_Nav.class));
                            BeatOrderActivity.this.finish();
                     */




                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(TopBrandActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();


                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(TopBrandActivity.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);


                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();




                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(TopBrandActivity.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);


                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();



                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(TopBrandActivity.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);


                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();





                            Utility.setStringSharedPreference(TopBrandActivity.this, "Bidmonday", "");
                            Utility.setStringSharedPreference(TopBrandActivity.this, "Bidtuesday", "");
                            Utility.setStringSharedPreference(TopBrandActivity.this, "Bidwednesday", "");
                            Utility.setStringSharedPreference(TopBrandActivity.this, "Bidthursday", "");
                            Utility.setStringSharedPreference(TopBrandActivity.this, "Bidfriday", "");
                            Utility.setStringSharedPreference(TopBrandActivity.this, "Bidsaturday", "");
                            Utility.setStringSharedPreference(TopBrandActivity.this, "Bidsunday", "");


                            Utility.setStringSharedPreference(TopBrandActivity.this,"BeatSkCode","");
                            Utility.setStringSharedPreference(TopBrandActivity.this, "ItemQJson" ,"");

                            Utility.setStringSharedPreference(TopBrandActivity.this, "CompanyId", "");



                            clearCartData();
                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();



                            TopBrandActivity.this.finish();


                            startActivity(new Intent(TopBrandActivity.this, LoginActivity_Nav.class));








                            return true;


                        }

                        else if (id == R.id.setting) {
                            startActivity(new Intent(TopBrandActivity.this, SettingActivity.class));
                            //   HomeActivity.this.finish();
                            return true;
                        }



                        else if (id == R.id.mysales) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(TopBrandActivity.this, MySalesActivity.class));

                            //    HomeActivity.this.finish();
                            return true;
                        }


                        else if (id == R.id.mybid) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(TopBrandActivity.this, DaysBidActivity.class));

                            //     HomeActivity.this.finish();

                            //HomeActivity.this.finish();
                            return true;
                        }

                        else if (id == R.id.redeem_point) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(TopBrandActivity.this, ReedemPointActivity.class));

                            //           HomeActivity.this.finish();
                            return true;
                        }

                        else if (id == R.id.reward_point_menu) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(TopBrandActivity.this, RewardItemActivity.class));

                            //     HomeActivity.this.finish();
                            return true;
                        }




                        else if (id == R.id.action_task) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(TopBrandActivity.this, ActionTaskMultiActivity.class));

                            //           HomeActivity.this.finish();
                            return true;
                        }


                        else
                            return false;
                    }
                });
            }
        });
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void callAqueryTopBrand() {

        String urlRemote = Constant.BASE_URL+"target/CustomerPurchaseData?day=3month&CustomerId="+peopleId+"&top=1";
        //String urlRemote = Constant.BASE_URL+"target/CustomerPurchaseData?day=3month&CustomerId=2047&top=1";
        System.out.println("urlRemote::"+urlRemote);
        showLoading();

        new AQuery(getApplicationContext()).ajax(urlRemote, null, JSONArray.class, new AjaxCallback<JSONArray>() {

            @Override
            public void callback(String url, JSONArray json, AjaxStatus status) {

                System.out.println("json::"+json);

                if (json.toString().equalsIgnoreCase("[]")) {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }


                    Toast.makeText(TopBrandActivity.this, "No Purchase brands!", Toast.LENGTH_SHORT).show();


                } else {

                    //  Toast.makeText(MyBeatActivity.this, json.toString(), Toast.LENGTH_SHORT).show();

                    try {
                        ArrayList<TopList> mItemListArrayList = new ArrayList<>();
                        jsonArray = new JSONArray(json.toString());

                        for (int i=0; i < jsonArray.length(); i++) {

                            jsonObject = jsonArray.getJSONObject(i);


                            String CompanyId= jsonObject.getString("CompanyId");
                            String WarehouseId= jsonObject.getString("WarehouseId");
                            String name= jsonObject.getString("name");
                            String TotalAmt= jsonObject.getString("TotalAmt");
                            String CreatedDate= jsonObject.getString("CreatedDate");
                            String Status= jsonObject.getString("status");
                            String SubsubcategoryName= jsonObject.getString("SubsubcategoryName");
                            String CategoryName= jsonObject.getString("CategoryName");

                            //  Toast.makeText(DaysBidActivity.this, jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();

                            mItemListArrayList.add(new TopList(CompanyId,WarehouseId,name,TotalAmt,CreatedDate,Status,SubsubcategoryName,CategoryName));

                        }

                        //   Toast.makeText(MyBeatActivity.this, "Json Array " + jsonArray.toString() , Toast.LENGTH_SHORT).show();

                        //Finally initializing our adapter
                        adapter = new TopBrandAdapter(mItemListArrayList, TopBrandActivity.this);
                        recyclerView.setAdapter(adapter);


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
    @Override
    public void onBackPressed() {
        //  super.onBackPressed()

        TopBrandActivity.this.finish();
        startActivity(new Intent(TopBrandActivity.this ,BeatOrderActivity.class));

    }
    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(TopBrandActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }

    public void showLoading() {


        mDialog = new Dialog(TopBrandActivity.this);
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
}
