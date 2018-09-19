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
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.adapters.DialListAdapter;
import com.example.user.mp_salesperson.bean.DialCustomerItem;
import com.example.user.mp_salesperson.bean.DialItem;
import com.example.user.mp_salesperson.bean.MyTopList;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.ItemList;
import com.example.user.mp_salesperson.customClasses.Utility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MyDialListActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
   // private RecyclerView.Adapter adapter;
    private ProgressDialog progressDialog;
    private List<MyTopList> listTopList;
    MyTopList topList;
    TextView title_toolbar;
    Dialog mDialog;
    AnimationDrawable animation;
    SearchView editsearch;
    DialListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dial_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);
        title_toolbar=(TextView)findViewById(R.id.title_toolbar) ;
        title_toolbar.setText("Dial List");
        listTopList = new ArrayList<>();
        //Finally initializing our adapter
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView = (RecyclerView)findViewById(R.id.top_brand_list);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        if(isNetworkAvailable())
        {
            callAqueryDialAvailable();
        }else
        {
            Toast.makeText(this, "Please connect to internet", Toast.LENGTH_SHORT).show();
        }

        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialListActivity.this.finish();
                startActivity(new Intent(MyDialListActivity.this ,HomeActivity.class));

            }
        });


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialListActivity.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDialListActivity.this.finish();
                startActivity(new Intent(MyDialListActivity.this, HomeActivity.class));
            }
        });



        ((ImageView) toolbar.findViewById(R.id.my_order_more_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.my_order_more_iv);
                PopupMenu popup = new PopupMenu(MyDialListActivity.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();
//                inflate.inflate(R.menu.my_order_option_menu, popup.getMenu());
                inflate.inflate(R.menu.home, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(MyDialListActivity.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(MyDialListActivity.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        }else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(MyDialListActivity.this, MyWallet.class));
                            return true;
                        }
                        else if (id == R.id.myDial) {
                            startActivity(new Intent(MyDialListActivity.this, MyDialListActivity.class));
                            return true;
                        }
                        else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(MyDialListActivity.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(MyDialListActivity.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(MyDialListActivity.this, RequestBrandActivity.class));
                            return true;
                        } else if (id == R.id.action_feedback) {
                            startActivity(new Intent(MyDialListActivity.this, FeedbackActivity.class));
                            return true;

                        } else if (id == R.id.action_logout) {


                     /*       ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(BeatOrderActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();
                            startActivity(new Intent(BeatOrderActivity.this, LoginActivity_Nav.class));
                            BeatOrderActivity.this.finish();
                     */




                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyDialListActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();


                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(MyDialListActivity.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);


                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();




                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(MyDialListActivity.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);


                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();



                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(MyDialListActivity.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);


                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();





                            Utility.setStringSharedPreference(MyDialListActivity.this, "Bidmonday", "");
                            Utility.setStringSharedPreference(MyDialListActivity.this, "Bidtuesday", "");
                            Utility.setStringSharedPreference(MyDialListActivity.this, "Bidwednesday", "");
                            Utility.setStringSharedPreference(MyDialListActivity.this, "Bidthursday", "");
                            Utility.setStringSharedPreference(MyDialListActivity.this, "Bidfriday", "");
                            Utility.setStringSharedPreference(MyDialListActivity.this, "Bidsaturday", "");
                            Utility.setStringSharedPreference(MyDialListActivity.this, "Bidsunday", "");


                            Utility.setStringSharedPreference(MyDialListActivity.this,"BeatSkCode","");
                            Utility.setStringSharedPreference(MyDialListActivity.this, "ItemQJson" ,"");

                            Utility.setStringSharedPreference(MyDialListActivity.this, "CompanyId", "");



                            clearCartData();
                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();



                            MyDialListActivity.this.finish();


                            startActivity(new Intent(MyDialListActivity.this, LoginActivity_Nav.class));

                            return true;


                        }

                        else if (id == R.id.setting) {
                            startActivity(new Intent(MyDialListActivity.this, SettingActivity.class));
                            //   HomeActivity.this.finish();
                            return true;
                        }



                        else if (id == R.id.mysales) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(MyDialListActivity.this, MySalesActivity.class));

                            //    HomeActivity.this.finish();
                            return true;
                        }


                        else if (id == R.id.mybid) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(MyDialListActivity.this, DaysBidActivity.class));

                            //     HomeActivity.this.finish();

                            //HomeActivity.this.finish();
                            return true;
                        }

                        else if (id == R.id.redeem_point) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(MyDialListActivity.this, ReedemPointActivity.class));

                            //           HomeActivity.this.finish();
                            return true;
                        }

                        else if (id == R.id.reward_point_menu) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(MyDialListActivity.this, RewardItemActivity.class));

                            //     HomeActivity.this.finish();
                            return true;
                        }




                        else if (id == R.id.action_task) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(MyDialListActivity.this, ActionTaskMultiActivity.class));

                            //           HomeActivity.this.finish();
                            return true;
                        }


                        else
                            return false;
                    }
                });
            }
        });


        // Locate the EditText in listview_main.xml
        editsearch = (SearchView) findViewById(R.id.search);

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void callAqueryDialAvailable() {
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyDialListActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        String url=Constant.BASE_URL+"DialPoint/SalesmanDial?SalesManId="+mRetailerBean.getCustomerId();
      // String url=Constant.BASE_URL+"DialPoint/SalesmanDial?SalesManId=8";
        System.out.println("UrlDial::"+url);
        showLoading();
        new AQuery(getApplicationContext()).ajax(url, null, JSONArray.class, new AjaxCallback<JSONArray>() {

            @Override
            public void callback(String url, JSONArray json, AjaxStatus status) {


                System.out.println("JsonData"+json);
                if (json.toString().equalsIgnoreCase("[]")) {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    Toast.makeText(MyDialListActivity.this, "No dial available", Toast.LENGTH_SHORT).show();


                } else {
                    ArrayList<DialCustomerItem> dialItemArrayList = new ArrayList<>();
                    ArrayList<DialCustomerItem> AllItemArrayList = new ArrayList<>();
                    //    Toast.makeText(BeatOrderActivity.this, ""+ json.toString(), Toast.LENGTH_SHORT).show();
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    try {
                        for (int i = 0; i < json.length(); i++) {
                            JSONObject mJsonObject = json.getJSONObject(i);
                            String id= mJsonObject.getString("Id");
                            String customerId= mJsonObject.getString("CustomerId");
                            String Mobile= mJsonObject.getString("Mobile");
                            String orderAmount= mJsonObject.getString("OrderAmount");
                            int point= mJsonObject.getInt("point");
                            String usedUnused= mJsonObject.getString("UsedUnused");
                            String createdDate= mJsonObject.getString("CreatedDate");
                           // String CustomerName= mJsonObject.getString("CustomerName");
                            String ShopName= mJsonObject.getString("ShopName");
                            String Skcode= mJsonObject.getString("Skcode");
//                            String BillingAddress= mJsonObject.getString("BillingAddress");
                           // dialItemArrayList.add(new DialCustomerItem(id,customerId,orderId,orderAmount,String.valueOf(point),usedUnused,createdDate,CustomerName,ShopName,Skcode,BillingAddress));
                            AllItemArrayList.add(new DialCustomerItem(id,"ss",ShopName,Skcode,Mobile));
                            if(dialItemArrayList.size()==0)
                            {
                                dialItemArrayList.add(new DialCustomerItem(id,"ss",ShopName,Skcode,Mobile));

                            }else
                            {
                                boolean ispresent=false;
                                for (int j = 0; j <dialItemArrayList.size() ; j++) {
                                    if(dialItemArrayList.get(j).getSkcode().equalsIgnoreCase(Skcode))
                                    {
                                        ispresent=true;
                                        break;
                                    }
                                }
                                if(!ispresent)
                                {
                                    dialItemArrayList.add(new DialCustomerItem(id,"ss",ShopName,Skcode,Mobile));

                                }else
                                {

                                }
                            }
                        }
                        System.out.println("dialItemArrayList::"+dialItemArrayList.size());

                        adapter = new DialListAdapter(AllItemArrayList,dialItemArrayList, MyDialListActivity.this);
                        recyclerView.setAdapter(adapter);
                        editsearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                            @Override
                            public boolean onQueryTextSubmit(String query) {
                                return false;
                            }

                            @Override
                            public boolean onQueryTextChange(String newText) {

                                String text = newText;

                                //  adapter.filter(text);
                                adapter.getFilter().filter(newText);
                                return true;
                                //adapter.getFilter().filter(newText);
                                //return false;

                            }
                        });



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }
    public void showLoading() {
        mDialog = new Dialog(MyDialListActivity.this);
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
    @Override
    public void onBackPressed() {
        //  super.onBackPressed()
        MyDialListActivity.this.finish();
        startActivity(new Intent(MyDialListActivity.this ,HomeActivity.class));

    }
    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(MyDialListActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }
}
