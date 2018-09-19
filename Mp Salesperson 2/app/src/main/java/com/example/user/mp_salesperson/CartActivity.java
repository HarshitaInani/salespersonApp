package com.example.user.mp_salesperson;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.example.user.mp_salesperson.adapters.CartItemListAdapter;
import com.example.user.mp_salesperson.bean.CartItemBean;
import com.example.user.mp_salesperson.bean.CartItemInfo;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.ItemList;
import com.example.user.mp_salesperson.customClasses.Utility;
//import com.example.user.mp_salesperson.databinding.CartActivityBinding;
import com.example.user.mp_salesperson.databinding.CartActivityBinding;
import com.example.user.mp_salesperson.dial.DeliveryDialWheel;
import com.example.user.mp_salesperson.fragment.HomeFragItemList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Krishna on 22-01-2017.
 */

public class CartActivity extends AppCompatActivity {
    int rowitemImageHeight=77;
    int rowitemImageWidth=77;

    private TextView tvTotalItemPrice;
    private TextView tvTotalItemQty;
    private TextView tvGrandTotal;
    private TextView tvDeliveryCharges;
    private TextView tvDpGrandTotal;
    private TextView tvDialTotal;
    private TextView tvUseDial;

    Button btnAddMoreItems;
    RecyclerView mCartFragRecyclerView;
    RelativeLayout rlCheckOut;
    int deliveryCharges = 10;
    CartItemBean mCartItem;
    FrameLayout mFrameLayout;
    Fragment fragment;
    android.support.v4.app.FragmentManager fragmentManager;
String getPoint;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setStatusBarColor(CartActivity.this.getResources().getColor(R.color.colorPrimaryDark));
            }
        }
        CartActivityBinding cartActivityBinding = DataBindingUtil.setContentView(this, R.layout.cart_activity);
       // setImagesDynamicSize();
       /* Intent intent = getIntent();
        getPoint = intent.getStringExtra("GETPOINT");*/
       // Toast.makeText(CartActivity.this, "Cart Activity Checkout", Toast.LENGTH_SHORT).show();
        tvTotalItemPrice = cartActivityBinding.cartFragTotalAmountTv;
        tvTotalItemQty = cartActivityBinding.cartFragTotalItemTv;
        tvGrandTotal = cartActivityBinding.cartFragGrandTotalAmountTv;

        tvDpGrandTotal= cartActivityBinding.cartFragTotalDpPointTv;
      //  tvDpGrandTotal.setText("");
        tvDialTotal=(TextView)findViewById(R.id.dial_available) ;
        tvUseDial=(TextView)findViewById(R.id.dial_available_use) ;

        tvDeliveryCharges = cartActivityBinding.cartFragDeliveryChargesTv;
        btnAddMoreItems = cartActivityBinding.cartFragAddMoreItems;
        mCartFragRecyclerView = cartActivityBinding.cartFragRv;
        rlCheckOut = cartActivityBinding.cartFragCheckoutRl;
        mFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        fragmentManager = CartActivity.this.getSupportFragmentManager();
        btnAddMoreItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(i);
                CartActivity.this.finish();
            }
        });

/*
        tvUseDial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(tvDialTotal.getText().toString().equalsIgnoreCase("0")) {
                  Toast.makeText(CartActivity.this, "No Dial Available", Toast.LENGTH_SHORT).show();
              }else{
                  Intent i = new Intent(getApplicationContext(), DeliveryDialWheel.class);
                  i.putExtra("DIAL", tvDialTotal.getText().toString());
                  startActivity(i);
              }
            }
        });*/


        rlCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(CartActivity.this, "Cart Activity Checkout", Toast.LENGTH_SHORT).show();
                if (getCartItem().getTotalPrice() < 700) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setTitle("Your Order");
                    builder.setMessage(Html.fromHtml("Oh! Since your order amount is below <font color=#424242>&#8377; 700, Please add a bit more to your cart."));
                    builder.setPositiveButton("Yes! Add More Items", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            CartActivity.this.finish();
                        }
                    });
                    /*builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });*/
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    Utility.setStringSharedPreference(CartActivity.this, "AvailDial", tvDialTotal.getText().toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setTitle("Place Order");
                    builder.setMessage("Are you sure, want to checkout?");
                    builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(CartActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                            if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
                                if (mRetailerBean.getActive().equalsIgnoreCase("false")) {
                                    Intent i = new Intent(getApplicationContext(), ActivationActivity.class);
                                    dialog.dismiss();
                                    startActivity(i);
                                    CartActivity.this.finish();
                                } else {
                                    if (Utils.isInternetConnected(CartActivity.this)) {

                                  // calling api for buying

                                        startActivity(new Intent(CartActivity.this, SkCodeActivity.class));
                                        CartActivity.this.finish();

                                        //new PlaceORderAsyncTask().execute();
                                    } else {
                                        new AlertDialog.Builder(CartActivity.this)
                                                .setIcon(android.R.drawable.ic_dialog_alert)
                                                .setTitle("Error!")
                                                .setMessage("Internet connection is not available")
                                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {

                                                    }

                                                })
                                                //.setNegativeButton("No", null)
                                                .show();
                                    }
                                    dialog.dismiss();
                                }
                            } else {
                                Intent i = new Intent(getApplicationContext(), LoginActivity_Nav.class);
                                dialog.dismiss();
                                startActivity(i);
                                CartActivity.this.finish();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });


    }

    @Override
    public void onStart() {

        CartItemListAdapter cartItemListAdapter = new CartItemListAdapter(CartActivity.this, getCartItem(), rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty, tvGrandTotal, tvDeliveryCharges, deliveryCharges, tvDpGrandTotal, tvDialTotal);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        mCartFragRecyclerView.setLayoutManager(llm);
        mCartFragRecyclerView.setAdapter(cartItemListAdapter);

        super.onStart();
    }

    public class PlaceORderAsyncTask extends AsyncTask<String, Void, JSONObject> {


        /*Dialog mDialog;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(CartActivity.this);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Placing Order...");
            mDialog.setCancelable(false);
            mDialog.show();
        }*/



        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(CartActivity.this);
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
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObjectFromUrl = null;
            try {
                ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(CartActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
                CartItemBean cartItemBean = getCartItem();
                String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                JSONObject mRequesParamObj = new JSONObject();
                mRequesParamObj.put("CreatedDate", currentDateandTime);
                mRequesParamObj.put("CustomerName", mRetailerBean.getName());
                mRequesParamObj.put("CustomerType", mRetailerBean.getCustomerType());
                mRequesParamObj.put("Customerphonenum", mRetailerBean.getMobile());
                mRequesParamObj.put("SalesPersonId", 0/*mRetailerBean.getCustomerId()*/);
//                mRequesParamObj.put("CustomerId", mRetailerBean.getCustomerId());
                mRequesParamObj.put("ShippingAddress", mRetailerBean.getShippingAddress());
                mRequesParamObj.put("ShopName", mRetailerBean.getShopName());
                mRequesParamObj.put("Skcode", mRetailerBean.getSkcode());
                mRequesParamObj.put("TotalAmount", cartItemBean.getTotalPrice());
                mRequesParamObj.put("deliveryCharge", "10");
                JSONArray mItemArray = new JSONArray();
                for (int i = 0; i < cartItemBean.getmCartItemInfos().size(); i++) {
                    JSONObject mItemObj = new JSONObject();
                    mItemObj.put("ItemId", cartItemBean.getmCartItemInfos().get(i).getItemId());
                    mItemObj.put("qty", cartItemBean.getmCartItemInfos().get(i).getQty());
                    mItemArray.put(mItemObj);
                }
                mRequesParamObj.put("itemDetails", mItemArray);

                jsonObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonFromHttpUrlConnectionJsonRequest_Post(Constant.BASE_URL_PLACE_ORDER, mRequesParamObj);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjectFromUrl;
        }

        @Override
        protected void onPostExecute(JSONObject mJsonObject) {
            if (mJsonObject != null) {
                Toast.makeText(CartActivity.this, "Order placed successfully", Toast.LENGTH_SHORT).show();
                clearCartData();
                CartActivity.this.finish();
            } else {
                Toast.makeText(CartActivity.this, "Unable to place order, please try again", Toast.LENGTH_SHORT).show();
            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
        }
    }

    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(CartActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }

    public CartItemBean getCartItem() {
        if (mCartItem == null) {
            ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(CartActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0, "", "");
            }
        }
        return mCartItem;
    }

    public String addItemInCartItemArrayList(String itemId, int qty, double itemUnitPrice, ItemList selectedItem, double deliveryCarges, double totalDp, String warehouseId, String companyId,double Price) {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(CartActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        if (mCartItem == null) {
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0, "", "");
            }
        }
        String status = "Error";
        double tempTotalPrice = 0;
        double tempTotalQuantity = 0;
        double tempTotalDpPoint = 0;
        double TotalPrice = 0;
        double saveAmount = 0;


        if (mCartItem.getmCartItemInfos() != null && !mCartItem.getmCartItemInfos().isEmpty()) {
            boolean itemFound = false;
            int foundItemPosition = -1;
            for (int i = 0; i < mCartItem.getmCartItemInfos().size(); i++) {
                if (mCartItem.getmCartItemInfos().get(i).getItemId().equalsIgnoreCase(itemId)) {
                    itemFound = true;
                    foundItemPosition = i;
                    break;
                } else {
                    itemFound = false;
                }
            }
            if (itemFound && foundItemPosition != -1) {
                mCartItem.getmCartItemInfos().get(foundItemPosition).setQty(qty);
                mCartItem.getmCartItemInfos().get(foundItemPosition).setItemUnitPrice(itemUnitPrice);
                mCartItem.getmCartItemInfos().get(foundItemPosition).setItemPrice(Price);
                status = "Item Updated in Cart";
            } else {
                mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, 9, warehouseId, companyId,Price));
                status = "Item Added in Cart";
            }
            for (int i = 0; i < mCartItem.getmCartItemInfos().size(); i++) {
                tempTotalPrice += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemUnitPrice();
                TotalPrice += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemPrice();
                tempTotalDpPoint += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemDpPoint();


                tempTotalQuantity += mCartItem.getmCartItemInfos().get(i).getQty();



            }
            mCartItem.setDeliveryCharges(deliveryCarges);
        } else {
            mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, 9, warehouseId, companyId,Price));
            status = "Item Added in Cart";
            tempTotalPrice += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemUnitPrice();
            TotalPrice += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemPrice();
            tempTotalDpPoint += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemDpPoint();


            tempTotalQuantity += mCartItem.getmCartItemInfos().get(0).getQty();
            mCartItem.setDeliveryCharges(deliveryCarges);
        }

        System.out.println("tempTotalPrice:::"+tempTotalPrice);
        System.out.println("TotalPrice:::"+TotalPrice);
        saveAmount =TotalPrice-tempTotalPrice;
        System.out.println("SaveAmount:::"+saveAmount);

        mCartItem.setTotalPrice(tempTotalPrice);

        mCartItem.setTotalItemPrice(TotalPrice);
        mCartItem.setSavingAmount(saveAmount);

        mCartItem.setTotalQuantity(tempTotalQuantity);

        mCartItem.setTotalDpPoints(tempTotalDpPoint);



        mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
        mCartItemArraylistPref.commit();
        return status;
    }

    private void setImagesDynamicSize() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if (displaymetrics.widthPixels >= 480 && displaymetrics.widthPixels < 720) {
            rowitemImageHeight = 77;
            rowitemImageWidth = 77;
        } else if (displaymetrics.widthPixels >= 720 && displaymetrics.widthPixels < 1080) {
            rowitemImageHeight = 115;
            rowitemImageWidth = 115;
        } else if (displaymetrics.widthPixels >= 1080 && displaymetrics.widthPixels < 1440) {
            rowitemImageHeight = 173;
            rowitemImageWidth = 173;
        } else if (displaymetrics.widthPixels >= 1440) {
            rowitemImageHeight = 230;
            rowitemImageWidth = 230;
        } else {
            rowitemImageHeight = 173;
            rowitemImageWidth = 173;
        }
    }





    public void removeItemfromCart(String itemId) {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(CartActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);


      //  Toast.makeText(CartActivity.this, "item ID"+itemId, Toast.LENGTH_SHORT).show();


        if (mCartItem == null) {
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0, "", "");
            }
        }
        String status = "Error";
        double tempTotalPrice = 0;
        double tempTotalQuantity = 0;
        double tempTotalDpPoint = 0;




        if (mCartItem.getmCartItemInfos() != null && !mCartItem.getmCartItemInfos().isEmpty()) {
            boolean itemFound = false;
            int foundItemPosition = -1;
            for (int i = 0; i < mCartItem.getmCartItemInfos().size(); i++) {
                if (mCartItem.getmCartItemInfos().get(i).getItemId().equalsIgnoreCase(itemId)) {
                    itemFound = true;
                    foundItemPosition = i;
                    break;
                } else {
                    itemFound = false;
                }
            }
            if (itemFound && foundItemPosition != -1) {
                //    mCartItem.getmCartItemInfos().get(foundItemPosition).setQty(qty);
                //  mCartItem.getmCartItemInfos().get(foundItemPosition).setItemUnitPrice(itemUnitPrice);
                status = "Item Updated in Cart";


              //  Toast.makeText(CartActivity.this, "Item found"+itemId+"\n"+mCartItem.getTotalQuantity(), Toast.LENGTH_SHORT).show();

                mCartItem.getmCartItemInfos().remove(foundItemPosition);

                mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
                mCartItemArraylistPref.commit();



                CartItemListAdapter cartItemListAdapter = new CartItemListAdapter(CartActivity.this, getCartItem(), rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty, tvGrandTotal, tvDeliveryCharges, deliveryCharges, tvDpGrandTotal, tvDialTotal);
                LinearLayoutManager llm = new LinearLayoutManager(this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                mCartFragRecyclerView.setLayoutManager(llm);
                mCartFragRecyclerView.setAdapter(cartItemListAdapter);



            }



/*

            else {
                mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp));
                status = "Item Added in Cart";
            }
            for (int i = 0; i < mCartItem.getmCartItemInfos().size(); i++) {
                tempTotalPrice += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemUnitPrice();

                tempTotalDpPoint += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemDpPoint();

                tempTotalQuantity += mCartItem.getmCartItemInfos().get(i).getQty();
            }
            mCartItem.setDeliveryCharges(deliveryCharges);
        } else {
            mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp));
            status = "Item Added in Cart";

            tempTotalPrice += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemUnitPrice();

            tempTotalDpPoint += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemDpPoint();



            tempTotalQuantity += mCartItem.getmCartItemInfos().get(0).getQty();

            mCartItem.setDeliveryCharges(deliveryCharges);
        }


        mCartItem.setTotalPrice(tempTotalPrice);
        mCartItem.setTotalQuantity(tempTotalQuantity);

        mCartItem.setTotalDpPoints(tempTotalDpPoint);

        mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
        mCartItemArraylistPref.commit();

*/

            //  mCartItemArraylistPref.


        }
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(CartActivity.this, HomeActivity.class));

    }



}
