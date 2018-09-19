package com.example.user.mp_salesperson;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.adapters.MyOrderRecyclerViewAdapter;
import com.example.user.mp_salesperson.bean.MyOderBean;
import com.example.user.mp_salesperson.bean.MyOrderDetailsBean;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.customClasses.Utility;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * Created by Krishna on 25-01-2017.
 */

public class MyOrders extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefreshLayout;
    boolean isItemListAvail = false;
    RecyclerView mMyOrderRecyclerView;
    AsyncTask<String, Void, JSONArray> mgetMyOrderAsyncTask;


    Dialog mDialog;
    AnimationDrawable animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_orders);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


      /*  HashMap map = new HashMap();
        map.put("test","test");

        ComplexPreferences mComplexPreferences = ComplexPreferences.getComplexPreferences(MyOrders.this,"testhe",MODE_PRIVATE);
        mComplexPreferences.putObject("asd",map);
*/


        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.my_order_swipe_refresh_layout);
        mMyOrderRecyclerView = (RecyclerView) findViewById(R.id.my_order_rv);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mMyOrderRecyclerView.setLayoutManager(llm);



        swipeRefreshLayout.setRefreshing( false );
        swipeRefreshLayout.setEnabled( false );


      /*swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                Toast.makeText(MyOrders.this, "Please write code to refresh", Toast.LENGTH_SHORT).show();
            }
        });*/

        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyOrders.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyOrders.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_refresh_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);


             //  Toast.makeText(MyOrders.this, " Mobile Number"+mRetailerBean.getMobile(), Toast.LENGTH_SHORT).show();

                if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
                    ComplexPreferences mMyOrderPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.MY_ORDER_PREF, MODE_PRIVATE);
                    Type typeMyOrderArrayList = new TypeToken<ArrayList<MyOderBean>>() {
                    }.getType();
                    ArrayList<MyOderBean> myOderBeanArrayList = mMyOrderPref.getArray(Constant.MY_ORDER_PREF_OBJ, typeMyOrderArrayList);
                    if (myOderBeanArrayList != null) {
                        MyOrderRecyclerViewAdapter myOrderRecyclerViewAdapter = new MyOrderRecyclerViewAdapter(MyOrders.this, myOderBeanArrayList);
                        mMyOrderRecyclerView.setAdapter(myOrderRecyclerViewAdapter);
                        isItemListAvail = true;

                   //     Toast.makeText(MyOrders.this, " Mobile Number"+mRetailerBean.getMobile(), Toast.LENGTH_SHORT).show();



//                        mgetMyOrderAsyncTask = new GetMyOrderAsyncTask().execute(mRetailerBean.getMobile());

                      //  mgetMyOrderAsyncTask = new GetMyOrderAsyncTask().execute(mRetailerBean.getCustomerId());

                        callAqueryOrderDetails(mRetailerBean.getCustomerId());



                    } else {


                   //     mgetMyOrderAsyncTask = new GetMyOrderAsyncTask().execute(mRetailerBean.getMobile());

                     //   mgetMyOrderAsyncTask = new GetMyOrderAsyncTask().execute(mRetailerBean.getCustomerId());

                        callAqueryOrderDetails(mRetailerBean.getCustomerId());


                        isItemListAvail = false;
                    }
                } else {
                    Intent i = new Intent(getApplicationContext(), LoginActivity_Nav.class);
                    startActivity(i);
                    MyOrders.this.finish();
                }
            }
        });

        ((ImageView) toolbar.findViewById(R.id.my_order_more_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.my_order_more_iv);
                PopupMenu popup = new PopupMenu(MyOrders.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();
                inflate.inflate(R.menu.my_order_option_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_profile) {
                          //  Toast.makeText(MyOrders.this, "My Profile Coming soon", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(MyOrders.this, MyProfile.class));

                            return true;
                        } else if (id == R.id.action_contact_us) {
                            //startActivity(new Intent(MyOrders.this, .class));

//                            Toast.makeText(MyOrders.this, "Contact us Coming soon", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(MyOrders.this, CartActivity.class));
                            return true;
                        }else if  (id == R.id.action_my_wallet) {
                            startActivity(new Intent(MyOrders.this, MyWallet.class));

                            // Toast.makeText(MyOrders.this, "MY Wallet Coming sooon", Toast.LENGTH_SHORT).show();
                            return true;
                        }
                        else if (id == R.id.myDial) {
                            startActivity(new Intent(MyOrders.this, MyDialListActivity.class));
                            return true;
                        }
                        else if (id == R.id.action_request_item) {
                            startActivity(new Intent(MyOrders.this, RequestBrandActivity.class));


                       //     Toast.makeText(MyOrders.this, "Request Item Coming soon", Toast.LENGTH_SHORT).show();
                            return true;
                        }



                        else if (id == R.id.action_mybeat) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(MyOrders.this, DaysBidActivity.class));

                            //     HomeActivity.this.finish();

                            //HomeActivity.this.finish();
                            return true;
                        }



                        else if (id == R.id.action_mysales) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(MyOrders.this, MySalesActivity.class));

                            //    HomeActivity.this.finish();
                            return true;
                        }




                        else if (id == R.id.action_settings) {
                            startActivity(new Intent(MyOrders.this, SettingActivity.class));
                            //   HomeActivity.this.finish();
                            return true;
                        }







                        else if (id == R.id.action_feedback) {

                            startActivity(new Intent(MyOrders.this, FeedbackActivity.class));

                            //   Toast.makeText(MyOrders.this, "My FeedBack Coming soon", Toast.LENGTH_SHORT).show();
                            return true;
                        } else if (id == R.id.action_logout) {

                     /*       ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();
                            startActivity(new Intent(MyOrders.this, LoginActivity_Nav.class));

                     */

                          //  HomeActivity.this.finish();


                          //  Toast.makeText(MyOrders.this, "Logout Coming soon", Toast.LENGTH_SHORT).show();




                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();


                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);


                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();




                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);


                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();



                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);


                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();





                            Utility.setStringSharedPreference(MyOrders.this, "Bidmonday", "");
                            Utility.setStringSharedPreference(MyOrders.this, "Bidtuesday", "");
                            Utility.setStringSharedPreference(MyOrders.this, "Bidwednesday", "");
                            Utility.setStringSharedPreference(MyOrders.this, "Bidthursday", "");
                            Utility.setStringSharedPreference(MyOrders.this, "Bidfriday", "");
                            Utility.setStringSharedPreference(MyOrders.this, "Bidsaturday", "");
                            Utility.setStringSharedPreference(MyOrders.this, "Bidsunday", "");


                            Utility.setStringSharedPreference(MyOrders.this,"BeatSkCode","");
                            Utility.setStringSharedPreference(MyOrders.this, "ItemQJson" ,"");

                            Utility.setStringSharedPreference(MyOrders.this, "CompanyId", "");



                            clearCartData();

                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();


                            MyOrders.this.finish();


                            startActivity(new Intent(MyOrders.this, LoginActivity_Nav.class));










                            return true;
                        } else
                            return false;
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
            ComplexPreferences mMyOrderPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.MY_ORDER_PREF, MODE_PRIVATE);
            Type typeMyOrderArrayList = new TypeToken<ArrayList<MyOderBean>>() {
            }.getType();
            ArrayList<MyOderBean> myOderBeanArrayList = mMyOrderPref.getArray(Constant.MY_ORDER_PREF_OBJ, typeMyOrderArrayList);
            if (myOderBeanArrayList != null) {
                MyOrderRecyclerViewAdapter myOrderRecyclerViewAdapter = new MyOrderRecyclerViewAdapter(MyOrders.this, myOderBeanArrayList);
                mMyOrderRecyclerView.setAdapter(myOrderRecyclerViewAdapter);
                isItemListAvail = true;



//                mgetMyOrderAsyncTask = new GetMyOrderAsyncTask().execute(mRetailerBean.getMobile());
             //   mgetMyOrderAsyncTask = new GetMyOrderAsyncTask().execute(mRetailerBean.getCustomerId());

                callAqueryOrderDetails(mRetailerBean.getCustomerId());




            } else {

//                mgetMyOrderAsyncTask = new GetMyOrderAsyncTask().execute(mRetailerBean.getMobile());
             //   mgetMyOrderAsyncTask = new GetMyOrderAsyncTask().execute(mRetailerBean.getCustomerId());


                callAqueryOrderDetails(mRetailerBean.getCustomerId());

                isItemListAvail = false;
            }
        } else {
            Intent i = new Intent(getApplicationContext(), LoginActivity_Nav.class);
            startActivity(i);
            MyOrders.this.finish();
        }
        super.onStart();
    }

  /*  @Override
    protected void onPause() {



        if (mgetMyOrderAsyncTask != null && !mgetMyOrderAsyncTask.isCancelled())
            mgetMyOrderAsyncTask.cancel(true);
        super.onPause();



    }
*/
    public class GetMyOrderAsyncTask extends AsyncTask<String, Void, JSONArray> {
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(MyOrders.this);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Getting order detail please wait...");
            ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            mDialog.setCancelable(false);
            mDialog.show();
        }


        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayObjectFromUrl = null;
            try {

//                jsonArrayObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_MY_ORDERS + "?Mobile=" + params[0], null, HttpUrlConnectionJSONParser.Http.GET);


                jsonArrayObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_MY_ORDERS + "?salespersonid=" + params[0], null, HttpUrlConnectionJSONParser.Http.GET);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayObjectFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

//            Toast.makeText(MyOrders.this, "Json Array "+jsonArray.toString(), Toast.LENGTH_SHORT).show();
            if (jsonArray != null) {
                try {
                    ArrayList<MyOderBean> myOderBeanArrayList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        JSONArray mJsonArrayOrderDetails = mJsonObject.getJSONArray("orderDetails");
                        ArrayList<MyOrderDetailsBean> myOrderDetailsBeanArrayList = new ArrayList<>();
                        for (int j = 0; j < mJsonArrayOrderDetails.length(); j++) {
                            JSONObject jsonObjectOrderDetail = mJsonArrayOrderDetails.getJSONObject(j);
                            String OrderDetailsId = isNullOrEmpty(jsonObjectOrderDetail, "OrderDetailsId");
                            String OrderId = isNullOrEmpty(jsonObjectOrderDetail, "OrderId");
                            String CustomerId = isNullOrEmpty(jsonObjectOrderDetail, "CustomerId");
                            String CustomerName = isNullOrEmpty(jsonObjectOrderDetail, "CustomerName");
                            String City = isNullOrEmpty(jsonObjectOrderDetail, "City");
                            String Mobile = isNullOrEmpty(jsonObjectOrderDetail, "Mobile");
                            String OrderDate = isNullOrEmpty(jsonObjectOrderDetail, "OrderDate");
                            String CompanyId = isNullOrEmpty(jsonObjectOrderDetail, "CompanyId");
                            String CityId = isNullOrEmpty(jsonObjectOrderDetail, "CityId");
                            String Warehouseid = isNullOrEmpty(jsonObjectOrderDetail, "Warehouseid");
                            String WarehouseName = isNullOrEmpty(jsonObjectOrderDetail, "WarehouseName");
                            String CategoryName = isNullOrEmpty(jsonObjectOrderDetail, "CategoryName");
                            String ItemId = isNullOrEmpty(jsonObjectOrderDetail, "ItemId");
                            String Itempic = isNullOrEmpty(jsonObjectOrderDetail, "Itempic");
                            String itemname = isNullOrEmpty(jsonObjectOrderDetail, "itemname");
                            String itemcode = isNullOrEmpty(jsonObjectOrderDetail, "itemcode");
                            String itemNumber = isNullOrEmpty(jsonObjectOrderDetail, "itemNumber");
                            String Barcode = isNullOrEmpty(jsonObjectOrderDetail, "Barcode");
                            String price = isNullOrEmpty(jsonObjectOrderDetail, "price");
                            String UnitPrice = isNullOrEmpty(jsonObjectOrderDetail, "UnitPrice");
                            String Purchaseprice = isNullOrEmpty(jsonObjectOrderDetail, "Purchaseprice");
                            String MinOrderQty = isNullOrEmpty(jsonObjectOrderDetail, "MinOrderQty");
                            String MinOrderQtyPrice = isNullOrEmpty(jsonObjectOrderDetail, "MinOrderQtyPrice");
                            String qty = isNullOrEmpty(jsonObjectOrderDetail, "qty");
                            String Noqty = isNullOrEmpty(jsonObjectOrderDetail, "Noqty");
                            String AmtWithoutTaxDisc = isNullOrEmpty(jsonObjectOrderDetail, "AmtWithoutTaxDisc");
                            String AmtWithoutAfterTaxDisc = isNullOrEmpty(jsonObjectOrderDetail, "AmtWithoutAfterTaxDisc");
                            String TotalAmountAfterTaxDisc = isNullOrEmpty(jsonObjectOrderDetail, "TotalAmountAfterTaxDisc");
                            String NetAmmount = isNullOrEmpty(jsonObjectOrderDetail, "NetAmmount");
                            String DiscountPercentage = isNullOrEmpty(jsonObjectOrderDetail, "DiscountPercentage");
                            String DiscountAmmount = isNullOrEmpty(jsonObjectOrderDetail, "DiscountAmmount");
                            String NetAmtAfterDis = isNullOrEmpty(jsonObjectOrderDetail, "NetAmtAfterDis");
                            String TaxPercentage = isNullOrEmpty(jsonObjectOrderDetail, "TaxPercentage");
                            String TaxAmmount = isNullOrEmpty(jsonObjectOrderDetail, "TaxAmmount");
                            String TotalAmt = isNullOrEmpty(jsonObjectOrderDetail, "TotalAmt");
                            String CreatedDate = isNullOrEmpty(jsonObjectOrderDetail, "CreatedDate");
                            String UpdatedDate = isNullOrEmpty(jsonObjectOrderDetail, "UpdatedDate");
                            String Deleted = isNullOrEmpty(jsonObjectOrderDetail, "Deleted");
                            String Status = isNullOrEmpty(jsonObjectOrderDetail, "Status");
                            String SizePerUnit = isNullOrEmpty(jsonObjectOrderDetail, "SizePerUnit");
                            String CurrentStock = isNullOrEmpty(jsonObjectOrderDetail, "CurrentStock");



                            MyOrderDetailsBean myOrderDetailsBean = new MyOrderDetailsBean(OrderDetailsId, OrderId, CustomerId, CustomerName, City, Mobile, OrderDate, CompanyId, CityId, Warehouseid, WarehouseName, CategoryName, ItemId, Itempic, itemname, itemcode, itemNumber, Barcode, price, UnitPrice, Purchaseprice, MinOrderQty, MinOrderQtyPrice, qty, Noqty, AmtWithoutTaxDisc, AmtWithoutAfterTaxDisc, TotalAmountAfterTaxDisc, NetAmmount, DiscountPercentage, DiscountAmmount, NetAmtAfterDis, TaxPercentage, TaxAmmount, TotalAmt, CreatedDate, UpdatedDate, Deleted, Status, SizePerUnit, CurrentStock);
                            myOrderDetailsBeanArrayList.add(myOrderDetailsBean);
                        }
                        String OrderId = isNullOrEmpty(mJsonObject, "OrderId");
                        String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                        String SalesPersonId = isNullOrEmpty(mJsonObject, "SalesPersonId");
                        String SalesPerson = isNullOrEmpty(mJsonObject, "SalesPerson");
                        String CustomerId = isNullOrEmpty(mJsonObject, "CustomerId");
                        String CustomerName = isNullOrEmpty(mJsonObject, "CustomerName");
                        String Skcode = isNullOrEmpty(mJsonObject, "Skcode");
                        String ShopName = isNullOrEmpty(mJsonObject, "ShopName");
                        String Status = isNullOrEmpty(mJsonObject, "Status");
                        String invoice_no = isNullOrEmpty(mJsonObject, "invoice_no");
                        String CustomerCategoryId = isNullOrEmpty(mJsonObject, "CustomerCategoryId");
                        String CustomerCategoryName = isNullOrEmpty(mJsonObject, "CustomerCategoryName");
                        String CustomerType = isNullOrEmpty(mJsonObject, "CustomerType");
                        String Customerphonenum = isNullOrEmpty(mJsonObject, "Customerphonenum");
                        String BillingAddress = isNullOrEmpty(mJsonObject, "BillingAddress");
                        String ShippingAddress = isNullOrEmpty(mJsonObject, "ShippingAddress");
                        String TotalAmount = isNullOrEmpty(mJsonObject, "TotalAmount");
                        String GrossAmount = isNullOrEmpty(mJsonObject, "GrossAmount");
                        String DiscountAmount = isNullOrEmpty(mJsonObject, "DiscountAmount");
                        String TaxAmount = isNullOrEmpty(mJsonObject, "TaxAmount");
                        String CityId = isNullOrEmpty(mJsonObject, "CityId");
                        String Warehouseid = isNullOrEmpty(mJsonObject, "Warehouseid");
                        String WarehouseName = isNullOrEmpty(mJsonObject, "WarehouseName");
                        String active = isNullOrEmpty(mJsonObject, "active");
                        String CreatedDate = isNullOrEmpty(mJsonObject, "CreatedDate");
                        String Deliverydate = isNullOrEmpty(mJsonObject, "Deliverydate");
                        String UpdatedDate = isNullOrEmpty(mJsonObject, "UpdatedDate");
                        String Deleted = isNullOrEmpty(mJsonObject, "Deleted");
                        String ReDispatchCount = isNullOrEmpty(mJsonObject, "ReDispatchCount");
                        String DivisionId = isNullOrEmpty(mJsonObject, "DivisionId");
                        String ReasonCancle = isNullOrEmpty(mJsonObject, "ReasonCancle");
                        String Cluster = isNullOrEmpty(mJsonObject, "Cluster");
                        String deliveryCharge = isNullOrEmpty(mJsonObject, "deliveryCharge");


                        String DreamPoints = isNullOrEmpty(mJsonObject, "RewardPoint");


                        if (DreamPoints.isEmpty()) {
                            DreamPoints = "0";
                        }

                        MyOderBean myOderBean = new MyOderBean(OrderId, CompanyId, SalesPersonId, SalesPerson, CustomerId, CustomerName, Skcode, ShopName, Status, invoice_no, CustomerCategoryId, CustomerCategoryName, CustomerType, Customerphonenum, BillingAddress, ShippingAddress, TotalAmount, GrossAmount, DiscountAmount, TaxAmount, CityId, Warehouseid, WarehouseName, active, CreatedDate, Deliverydate, UpdatedDate, Deleted, ReDispatchCount, DivisionId, ReasonCancle, Cluster, deliveryCharge, myOrderDetailsBeanArrayList, DreamPoints);
                        myOderBeanArrayList.add(myOderBean);
                    }
                    ComplexPreferences mMyOrderPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.MY_ORDER_PREF, MODE_PRIVATE);
                    mMyOrderPref.putObject(Constant.MY_ORDER_PREF_OBJ, myOderBeanArrayList);
                    mMyOrderPref.commit();
                    MyOrderRecyclerViewAdapter myOrderRecyclerViewAdapter = new MyOrderRecyclerViewAdapter(MyOrders.this, myOderBeanArrayList);
                    mMyOrderRecyclerView.setAdapter(myOrderRecyclerViewAdapter);

                } catch (JSONException e) {
                    Toast.makeText(MyOrders.this, "Error server"+e.toString(), Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            } else {
                Toast.makeText(MyOrders.this, "Improper response from server", Toast.LENGTH_SHORT).show();
            }
            swipeRefreshLayout.setRefreshing(false);
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
                Log.e("MyOrder", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public void callAqueryOrderDetails(String id) {

        System.out.println("Url:::"+Constant.BASE_URL_MY_ORDERS + "?salespersonid="+id);

        showLoading();


        new AQuery(MyOrders.this).ajax(Constant.BASE_URL_MY_ORDERS + "?salespersonid="+id,
                null,
                JSONArray.class
        ,new AjaxCallback<JSONArray>(){


                    @Override
                    public void callback(String url, JSONArray jsonArray, AjaxStatus status) {

                    //    showLoading();
                        if (jsonArray.toString() != null) {

                       //     Toast.makeText(MyOrders.this, ""+jsonArray.toString(), Toast.LENGTH_SHORT).show();


                            System.out.println("OrderListResponse::"+jsonArray.toString());

                            try {
                                ArrayList<MyOderBean> myOderBeanArrayList = new ArrayList<>();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject mJsonObject = jsonArray.getJSONObject(i);
                                    JSONArray mJsonArrayOrderDetails = mJsonObject.getJSONArray("orderDetails");
                                    ArrayList<MyOrderDetailsBean> myOrderDetailsBeanArrayList = new ArrayList<>();
                                    for (int j = 0; j < mJsonArrayOrderDetails.length(); j++) {
                                        JSONObject jsonObjectOrderDetail = mJsonArrayOrderDetails.getJSONObject(j);
                                        String OrderDetailsId = isNullOrEmpty(jsonObjectOrderDetail, "OrderDetailsId");
                                        String OrderId = isNullOrEmpty(jsonObjectOrderDetail, "OrderId");
                                        String CustomerId = isNullOrEmpty(jsonObjectOrderDetail, "CustomerId");
                                        String CustomerName = isNullOrEmpty(jsonObjectOrderDetail, "CustomerName");
                                        String City = isNullOrEmpty(jsonObjectOrderDetail, "City");
                                        String Mobile = isNullOrEmpty(jsonObjectOrderDetail, "Mobile");
                                        String OrderDate = isNullOrEmpty(jsonObjectOrderDetail, "OrderDate");
                                        String CompanyId = isNullOrEmpty(jsonObjectOrderDetail, "CompanyId");
                                        String CityId = isNullOrEmpty(jsonObjectOrderDetail, "CityId");
                                        String Warehouseid = isNullOrEmpty(jsonObjectOrderDetail, "Warehouseid");
                                        String WarehouseName = isNullOrEmpty(jsonObjectOrderDetail, "WarehouseName");
                                        String CategoryName = isNullOrEmpty(jsonObjectOrderDetail, "CategoryName");
                                        String ItemId = isNullOrEmpty(jsonObjectOrderDetail, "ItemId");
                                        String Itempic = isNullOrEmpty(jsonObjectOrderDetail, "Itempic");
                                        String itemname = isNullOrEmpty(jsonObjectOrderDetail, "itemname");
                                        String itemcode = isNullOrEmpty(jsonObjectOrderDetail, "itemcode");
                                        String itemNumber = isNullOrEmpty(jsonObjectOrderDetail, "itemNumber");
                                        String Barcode = isNullOrEmpty(jsonObjectOrderDetail, "Barcode");
                                        String price = isNullOrEmpty(jsonObjectOrderDetail, "price");
                                        String UnitPrice = isNullOrEmpty(jsonObjectOrderDetail, "UnitPrice");
                                        String Purchaseprice = isNullOrEmpty(jsonObjectOrderDetail, "Purchaseprice");
                                        String MinOrderQty = isNullOrEmpty(jsonObjectOrderDetail, "MinOrderQty");
                                        String MinOrderQtyPrice = isNullOrEmpty(jsonObjectOrderDetail, "MinOrderQtyPrice");
                                        String qty = isNullOrEmpty(jsonObjectOrderDetail, "qty");
                                        String Noqty = isNullOrEmpty(jsonObjectOrderDetail, "Noqty");
                                        String AmtWithoutTaxDisc = isNullOrEmpty(jsonObjectOrderDetail, "AmtWithoutTaxDisc");
                                        String AmtWithoutAfterTaxDisc = isNullOrEmpty(jsonObjectOrderDetail, "AmtWithoutAfterTaxDisc");
                                        String TotalAmountAfterTaxDisc = isNullOrEmpty(jsonObjectOrderDetail, "TotalAmountAfterTaxDisc");
                                        String NetAmmount = isNullOrEmpty(jsonObjectOrderDetail, "NetAmmount");
                                        String DiscountPercentage = isNullOrEmpty(jsonObjectOrderDetail, "DiscountPercentage");
                                        String DiscountAmmount = isNullOrEmpty(jsonObjectOrderDetail, "DiscountAmmount");
                                        String NetAmtAfterDis = isNullOrEmpty(jsonObjectOrderDetail, "NetAmtAfterDis");
                                        String TaxPercentage = isNullOrEmpty(jsonObjectOrderDetail, "TaxPercentage");
                                        String TaxAmmount = isNullOrEmpty(jsonObjectOrderDetail, "TaxAmmount");
                                        String TotalAmt = isNullOrEmpty(jsonObjectOrderDetail, "TotalAmt");
                                        String CreatedDate = isNullOrEmpty(jsonObjectOrderDetail, "CreatedDate");
                                        String UpdatedDate = isNullOrEmpty(jsonObjectOrderDetail, "UpdatedDate");
                                        String Deleted = isNullOrEmpty(jsonObjectOrderDetail, "Deleted");
                                        String Status = isNullOrEmpty(jsonObjectOrderDetail, "Status");
                                        String SizePerUnit = isNullOrEmpty(jsonObjectOrderDetail, "SizePerUnit");
                                        String CurrentStock = isNullOrEmpty(jsonObjectOrderDetail, "CurrentStock");


                                 //       String DreamPoints = isNullOrEmpty(jsonObjectOrderDetail, "RewardPoint");


                                        MyOrderDetailsBean myOrderDetailsBean = new MyOrderDetailsBean(OrderDetailsId, OrderId, CustomerId, CustomerName, City, Mobile, OrderDate, CompanyId, CityId, Warehouseid, WarehouseName, CategoryName, ItemId, Itempic, itemname, itemcode, itemNumber, Barcode, price, UnitPrice, Purchaseprice, MinOrderQty, MinOrderQtyPrice, qty, Noqty, AmtWithoutTaxDisc, AmtWithoutAfterTaxDisc, TotalAmountAfterTaxDisc, NetAmmount, DiscountPercentage, DiscountAmmount, NetAmtAfterDis, TaxPercentage, TaxAmmount, TotalAmt, CreatedDate, UpdatedDate, Deleted, Status, SizePerUnit, CurrentStock);
                                        myOrderDetailsBeanArrayList.add(myOrderDetailsBean);
                                    }
                                    String OrderId = isNullOrEmpty(mJsonObject, "OrderId");
                                    String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                                    String SalesPersonId = isNullOrEmpty(mJsonObject, "SalesPersonId");
                                    String SalesPerson = isNullOrEmpty(mJsonObject, "SalesPerson");
                                    String CustomerId = isNullOrEmpty(mJsonObject, "CustomerId");
                                    String CustomerName = isNullOrEmpty(mJsonObject, "CustomerName");
                                    String Skcode = isNullOrEmpty(mJsonObject, "Skcode");
                                    String ShopName = isNullOrEmpty(mJsonObject, "ShopName");
                                    String Status = isNullOrEmpty(mJsonObject, "Status");
                                    String invoice_no = isNullOrEmpty(mJsonObject, "invoice_no");
                                    String CustomerCategoryId = isNullOrEmpty(mJsonObject, "CustomerCategoryId");
                                    String CustomerCategoryName = isNullOrEmpty(mJsonObject, "CustomerCategoryName");
                                    String CustomerType = isNullOrEmpty(mJsonObject, "CustomerType");
                                    String Customerphonenum = isNullOrEmpty(mJsonObject, "Customerphonenum");
                                    String BillingAddress = isNullOrEmpty(mJsonObject, "BillingAddress");
                                    String ShippingAddress = isNullOrEmpty(mJsonObject, "ShippingAddress");
                                    String TotalAmount = isNullOrEmpty(mJsonObject, "TotalAmount");
                                    String GrossAmount = isNullOrEmpty(mJsonObject, "GrossAmount");
                                    String DiscountAmount = isNullOrEmpty(mJsonObject, "DiscountAmount");
                                    String TaxAmount = isNullOrEmpty(mJsonObject, "TaxAmount");
                                    String CityId = isNullOrEmpty(mJsonObject, "CityId");
                                    String Warehouseid = isNullOrEmpty(mJsonObject, "Warehouseid");
                                    String WarehouseName = isNullOrEmpty(mJsonObject, "WarehouseName");
                                    String active = isNullOrEmpty(mJsonObject, "active");
                                    String CreatedDate = isNullOrEmpty(mJsonObject, "CreatedDate");
                                    String Deliverydate = isNullOrEmpty(mJsonObject, "Deliverydate");
                                    String UpdatedDate = isNullOrEmpty(mJsonObject, "UpdatedDate");
                                    String Deleted = isNullOrEmpty(mJsonObject, "Deleted");
                                    String ReDispatchCount = isNullOrEmpty(mJsonObject, "ReDispatchCount");
                                    String DivisionId = isNullOrEmpty(mJsonObject, "DivisionId");
                                    String ReasonCancle = isNullOrEmpty(mJsonObject, "ReasonCancle");
                                    String Cluster = isNullOrEmpty(mJsonObject, "Cluster");
                                    String deliveryCharge = isNullOrEmpty(mJsonObject, "deliveryCharge");

                                    String DreamPoints = isNullOrEmpty(mJsonObject, "RewardPoint");

                                    if (DreamPoints.isEmpty()) {
                                        DreamPoints = "0";
                                    }


                                   // Toast.makeText(MyOrders.this, "Dream points : "+DreamPoints, Toast.LENGTH_SHORT).show();


                                    MyOderBean myOderBean = new MyOderBean(OrderId, CompanyId, SalesPersonId, SalesPerson, CustomerId, CustomerName, Skcode, ShopName, Status, invoice_no, CustomerCategoryId, CustomerCategoryName, CustomerType, Customerphonenum, BillingAddress, ShippingAddress, TotalAmount, GrossAmount, DiscountAmount, TaxAmount, CityId, Warehouseid, WarehouseName, active, CreatedDate, Deliverydate, UpdatedDate, Deleted, ReDispatchCount, DivisionId, ReasonCancle, Cluster, deliveryCharge, myOrderDetailsBeanArrayList, DreamPoints) ;
                                    myOderBeanArrayList.add(myOderBean);
                                }
                                ComplexPreferences mMyOrderPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.MY_ORDER_PREF, MODE_PRIVATE);
                                mMyOrderPref.putObject(Constant.MY_ORDER_PREF_OBJ, myOderBeanArrayList);
                                mMyOrderPref.commit();
                                MyOrderRecyclerViewAdapter myOrderRecyclerViewAdapter = new MyOrderRecyclerViewAdapter(MyOrders.this, myOderBeanArrayList);
                                mMyOrderRecyclerView.setAdapter(myOrderRecyclerViewAdapter);


                                if (mDialog.isShowing()) {
                                    animation.stop();
                                    mDialog.dismiss();
                                }


                            } catch (JSONException e) {
                                Toast.makeText(MyOrders.this, "Error server"+e.toString(), Toast.LENGTH_SHORT).show();

                                if (mDialog.isShowing()) {
                                    animation.stop();
                                    mDialog.dismiss();
                                }

                                e.printStackTrace();
                            }















                        }
                        else {

                            if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }

                            Toast.makeText(MyOrders.this, "Please try again", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }



    public void showLoading() {


        mDialog = new Dialog(MyOrders.this);
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
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(MyOrders.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }


}
