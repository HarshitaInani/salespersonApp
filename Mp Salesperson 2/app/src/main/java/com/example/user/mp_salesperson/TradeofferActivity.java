package com.example.user.mp_salesperson;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.example.user.mp_salesperson.adapters.TradeofferfragmentAdapter;
import com.example.user.mp_salesperson.bean.CartItemBean;
import com.example.user.mp_salesperson.bean.CartItemInfo;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.ItemList;
import com.example.user.mp_salesperson.customClasses.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;



public class TradeofferActivity extends AppCompatActivity {
    int rowitemImageHeight = 77;
    int rowitemImageWidth = 77;
    String bname;
    RecyclerView mItemListRecyclerView;
    RelativeLayout rl1;
    TextView tvTotalItemPrice;
    TextView tvTotalItemQty;
    TextView tvTotalDp;
    TextView tvBrandName;
    TradeofferfragmentAdapter mItemListAdapter;
     public static CartItemBean mCartItem;
    int mp = 0, pp = 0;
    private static final String TAG = "NewlyAddedItemDetails";
    String locale;
    int sliderIMageHeight;
    int sliderIMageWidth;
    String hindiLanguage = " हिन्दी (भारत)";

    SharedPreferences sharedpreferences;

    String languageCheck = "e";

    TextView show_popup;
    Dialog mDialog;
    AnimationDrawable animation;

    AsyncTask<String, Void, JSONArray> getNewlyAddedBrandTask;

    private ArrayList<ItemList> mNewlyAddeddArrayList;
    ArrayList<ItemList> mItemListAllValue;
    HomeActivity homeActivity = new HomeActivity();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tradeoffer);
        new GetMultipriceItem().execute();
        locale = TradeofferActivity.this.getResources().getConfiguration().locale.getDisplayName();

        Log.e("language", locale);

        sharedpreferences = TradeofferActivity.this.getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);

        languageCheck = sharedpreferences.getString("language", "");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(TradeofferActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mItemListRecyclerView = (RecyclerView) findViewById(R.id.item_list_rv);
        mItemListRecyclerView.setNestedScrollingEnabled(false);
        mItemListRecyclerView.setHasFixedSize(true);
        mItemListRecyclerView.setLayoutManager(linearLayoutManager);
        // mItemListRecyclerView.setAdapter(mItemListAdapter);

        show_popup = (TextView) findViewById(R.id.show_popup);
        rl1 = (RelativeLayout) findViewById(R.id.rl1);
        tvTotalItemPrice = (TextView) findViewById(R.id.item_list_total_amount_tv);
        tvTotalItemQty = (TextView) findViewById(R.id.item_list_total_item_tv);
        tvTotalDp = (TextView) findViewById(R.id.item_list_total_dp_tv);
        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                TradeofferActivity.this.startActivity(new Intent(TradeofferActivity.this, CartActivity.class));
            }
        });
        ImageView home = (ImageView) findViewById(R.id.home_icon_iv);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TradeofferActivity.this, HomeActivity.class));
            }
        });
        ((ImageView) toolbar.findViewById(R.id.home_more_iv)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.home_more_iv);
                PopupMenu popup = new PopupMenu(TradeofferActivity.this, menuItemView);


                MenuInflater inflate = popup.getMenuInflater();
                inflate.inflate(R.menu.home, popup.getMenu());
                popup.show();


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(TradeofferActivity.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(TradeofferActivity.this, MyWallet.class));
                            return true;

                        } else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(TradeofferActivity.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(TradeofferActivity.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(TradeofferActivity.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(TradeofferActivity.this, RequestBrandActivity.class));
                            return true;
                        } else if (id == R.id.action_feedback) {
                            startActivity(new Intent(TradeofferActivity.this, FeedbackActivity.class));
                            return true;

                        } else if (id == R.id.action_logout) {
                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();

                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();

                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();

                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();

                            ComplexPreferences mRetailerBeanPref5 = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.POPULAR_BRANDS_PREF1, MODE_PRIVATE);
                            mRetailerBeanPref5.clear();
                            mRetailerBeanPref5.commit();

                            ComplexPreferences mRetailerBeanPref6 = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.POPULAR_BRANDS_PREF2, MODE_PRIVATE);
                            mRetailerBeanPref6.clear();
                            mRetailerBeanPref6.commit();

                            ComplexPreferences mRetailerBeanPref7 = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.APP_PROMOTION_PREF, MODE_PRIVATE);
                            mRetailerBeanPref7.clear();
                            mRetailerBeanPref7.commit();

                            ComplexPreferences mRetailerBeanPref8 = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.NEWLY_ADDED_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref8.clear();
                            mRetailerBeanPref8.commit();

                            ComplexPreferences mRetailerBeanPref9 = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.ALL_TOP_ADDED_PREF, MODE_PRIVATE);
                            mRetailerBeanPref9.clear();
                            mRetailerBeanPref9.commit();

                            ComplexPreferences mRetailerBeanPref10 = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.TODAY_DHAMAKA_PREF, MODE_PRIVATE);
                            mRetailerBeanPref10.clear();
                            mRetailerBeanPref10.commit();

                            ComplexPreferences mRetailerBeanPref11 = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.EMPTY_STOCK_PREF, MODE_PRIVATE);
                            mRetailerBeanPref11.clear();
                            mRetailerBeanPref11.commit();

                            ComplexPreferences mRetailerBeanPref12 = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.BULK_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref12.clear();
                            mRetailerBeanPref12.commit();

                            ComplexPreferences mRetailerBeanPref13 = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.MOST_SELLED_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref13.clear();
                            mRetailerBeanPref13.commit();

                            clearCartData();

                            Utility.setStringSharedPreference(TradeofferActivity.this, "ItemQJson", "");
                            Utility.setStringSharedPreference(TradeofferActivity.this, "CompanyId", "");

//                            Utility.setStringSharedPreference(HomeActivity.this, "ItemFavJson", "");

                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();


                            TradeofferActivity.this.finish();

                            startActivity(new Intent(TradeofferActivity.this, LoginActivity_Nav.class));

                            return true;
                        } else
                            return false;
                    }
                });

                @SuppressLint("RestrictedApi") MenuPopupHelper menuHelper = new MenuPopupHelper(TradeofferActivity.this, (MenuBuilder) popup.getMenu(), menuItemView);
                menuHelper.setForceShowIcon(true);
                menuHelper.show();
            }
        });


        tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(((TradeofferActivity.this)).getCartItem().getTotalPrice()));
        tvTotalItemQty.setText("" + (int) (((TradeofferActivity.this)).getCartItem().getTotalPrice()));
        tvTotalDp.setText("Dp : " + new DecimalFormat("##.##").format(((TradeofferActivity.this)).getCartItem().getTotalDpPoints()));

        if (Utils.isInternetConnected(TradeofferActivity.this)) {

           /* getNewlyAddedBrandTask =*/
           /* new GetMultipriceItem().execute();*/

        } else {
            new AlertDialog.Builder(TradeofferActivity.this)
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

    }
    public boolean showPopup(int totalAmount) {

        if(totalAmount >= 3000 && totalAmount <= 4000) {
            return true;
        }else if(totalAmount >= 7000 & totalAmount <= 8000)
        {
            return true;
        }else if(totalAmount >= 11000 & totalAmount <= 12000)
        {
            return true;
        }else if(totalAmount >= 15000 & totalAmount <= 16000)
        {
            return true;
        }else if(totalAmount >= 19000 & totalAmount <= 20000)
        {
            return true;
        }else if(totalAmount >= 23000 & totalAmount <= 24000)
        {
            return true;
        }else if(totalAmount >= 27000 & totalAmount <= 28000)
        {
            return true;
        }else if(totalAmount >= 31000 & totalAmount <= 32000)
        {
            return true;
        }else if(totalAmount >= 35000 & totalAmount <= 36000)
        {
            return true;
        }else if(totalAmount >= 39000 & totalAmount <= 40000)
        {
            return true;
        }else if(totalAmount >= 43000 & totalAmount <= 44000)
        {
            return true;
        }else if(totalAmount >= 47000 & totalAmount <= 48000)
        {
            return true;
        }else{
            return false;
        }

        //return true;
    }

    public CartItemBean getCartItem() {
        if (mCartItem == null) {
            ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0,"","");
            }
        }
        return mCartItem;
    }

    public String addItemInCartItemArrayList(String itemId, int qty, double itemUnitPrice, ItemList selectedItem, double deliveryCharges, double totalDp, String wid, String coid, double Price) {



        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        if (mCartItem == null) {
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0,"","");
            }
        }
        String status = "Error";
        double tempTotalPrice = 0;
        double TotalPrice = 0;
        double tempTotalQuantity = 0;
        double tempTotalDpPoint = 0;
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




                //mCartItem.getmCartItemInfos().remove(foundItemPosition);


            } else {
                mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp,Price,"",""));
                status = "Item Added in Cart";
            }
            for (int i = 0; i < mCartItem.getmCartItemInfos().size(); i++) {
                tempTotalPrice += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemUnitPrice();

                TotalPrice += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemPrice();

                tempTotalDpPoint += mCartItem.getmCartItemInfos().get(i).getQty() * mCartItem.getmCartItemInfos().get(i).getItemDpPoint();

                tempTotalQuantity += mCartItem.getmCartItemInfos().get(i).getQty();
            }
            mCartItem.setDeliveryCharges(deliveryCharges);
        } else {
            mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp,Price,"",""));
            status = "Item Added in Cart";



            tempTotalPrice += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemUnitPrice();

            TotalPrice += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemPrice();

            tempTotalDpPoint += mCartItem.getmCartItemInfos().get(0).getQty() * mCartItem.getmCartItemInfos().get(0).getItemDpPoint();

            tempTotalQuantity += mCartItem.getmCartItemInfos().get(0).getQty();

            mCartItem.setDeliveryCharges(deliveryCharges);
        }
        System.out.println("tempTotalPrice:::"+tempTotalPrice);
        System.out.println("TotalPrice:::"+TotalPrice);
        saveAmount =TotalPrice-tempTotalPrice;
        System.out.println("SaveAmount:::"+saveAmount);

        mCartItem.setTotalItemPrice(TotalPrice);
        mCartItem.setSavingAmount(saveAmount);
        mCartItem.setTotalPrice(tempTotalPrice);
        mCartItem.setTotalQuantity(tempTotalQuantity);
        mCartItem.setTotalDpPoints(tempTotalDpPoint);

        mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
        mCartItemArraylistPref.commit();


        //  mCartItemArraylistPref.
        return status;


    }



    public  void removeData() {

        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);

        // mCartItemArraylistPref.re

    }




    public void removeItemfromCart(String itemId) {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);


        //  Toast.makeText(HomeActivity.this, "item ID"+itemId, Toast.LENGTH_SHORT).show();


        if (mCartItem == null) {
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0,"","");
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

                status = "Item Updated in Cart";
                //   Toast.makeText(HomeActivity.this, "Item found"+itemId+"\n"+mCartItem.getTotalQuantity(), Toast.LENGTH_SHORT).show();
                mCartItem.getmCartItemInfos().remove(foundItemPosition);
                mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
                mCartItemArraylistPref.commit();
            }

        }
    }


    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }
    public void showLoading() {
        mDialog = new Dialog(TradeofferActivity.this);
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

    public class GetMultipriceItem extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            showLoading();
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(TradeofferActivity.this, Constant.RETAILER_BEAN_PREF ,TradeofferActivity.MODE_PRIVATE);
            RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_MULTIMOQ+"?warehouseid="+mRetailerBean.getWarehouseid(), null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            mNewlyAddeddArrayList = new ArrayList<>();
            mNewlyAddeddArrayList.clear();
            mItemListAllValue = new ArrayList<>();
            mItemListAllValue.clear();
            System.out.println("Json11:::" + jsonArray);
            try {
                Set nonDuplicateset = new HashSet();
                Set Duplicateset = new HashSet();
                nonDuplicateset.clear();
                Duplicateset.clear();
                for (int i = 0; i < jsonArray.length(); i++) {

                    try {
                        JSONObject oneObject = (JSONObject) jsonArray.get(i);
                        String ItemId = oneObject.getString("ItemId");
                        String Categoryid = oneObject.getString("Categoryid");
                        String SubCategoryId = oneObject.getString("SubCategoryId");
                        String SubsubCategoryid = oneObject.getString("SubsubCategoryid");
                        String itemname = oneObject.getString("itemname");
                        String price = oneObject.getString("price");
                        String SellingUnitName = oneObject.getString("SellingUnitName");
                        String SellingSku = oneObject.getString("SellingSku");
                        String UnitPrice = oneObject.getString("UnitPrice");
                        String VATTax = oneObject.getString("VATTax");
                        String LogoUrl = oneObject.getString("LogoUrl");
                        String MinOrderQty = oneObject.getString("MinOrderQty");
                        String Discount = oneObject.getString("Discount");
                        String TotalTaxPercentage = oneObject.getString("TotalTaxPercentage");
                        String itemNumber = oneObject.getString("Number");
                        String HindiName = oneObject.getString("HindiName");
                        String DpPoint = "";
                        String PromoPoint = oneObject.getString("promoPerItems");
                        String MarginPoint = oneObject.getString("marginPoint");
                        String warehouseId = oneObject.getString("WarehouseId");
                        String companyId = oneObject.getString("CompanyId");
                        String Isoffer = "";


                        if (PromoPoint.trim().equals("null")) {
                            pp = 0;

                        }


                        if (PromoPoint.isEmpty()) {
                            pp = 0;

                        } else if (PromoPoint.trim().equals("null")) {
                            pp = 0;

                        } else {
                            pp = Integer.parseInt(PromoPoint);

                        }


                        if (MarginPoint.trim().equals("null")) {

                            mp = 0;
                        }


                        if (MarginPoint.isEmpty()) {

                            mp = 0;
                        } else if (MarginPoint.trim().equals("null")) {

                            mp = 0;
                        } else {
                            mp = Integer.parseInt(MarginPoint);

                        }

                        if (pp > 0 && mp > 0) {
                            int pp_mp = pp + mp;

                            DpPoint = "" + pp_mp;
                        } else if (mp > 0) {
                            DpPoint = "" + mp;
                        } else if (pp > 0) {
                            DpPoint = "" + pp;
                        } else {
                            DpPoint = "0";
                        }


                        mItemListAllValue.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId, itemNumber, Isoffer));

                        if (mItemListAllValue != null && !mItemListAllValue.isEmpty()) {

                            mItemListAdapter = new TradeofferfragmentAdapter(TradeofferActivity.this, mNewlyAddeddArrayList, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty, tvTotalDp, show_popup, mItemListAllValue);
                            mItemListRecyclerView.setAdapter(mItemListAdapter);
                            mItemListAdapter.notifyDataSetChanged();
                        }

                        if (mNewlyAddeddArrayList.size() == 0) {

                            mNewlyAddeddArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId, itemNumber, Isoffer));
                            Log.d("mNewlyAddeddArrayList", String.valueOf(mNewlyAddeddArrayList));
                        } else {
                            boolean ispresent = false;
                            for (int k = 0; k < mNewlyAddeddArrayList.size(); k++) {

                                if (mNewlyAddeddArrayList.get(k).getItemNumber().equalsIgnoreCase(itemNumber)) {
                                    ispresent = true;

                                    break;

                                }
                            }
                            if (!ispresent) {

                                mNewlyAddeddArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, warehouseId, companyId, itemNumber, Isoffer));

                            } else {

                            }

                        }
                        Collections.sort(mItemListAllValue, new GetMultipriceItem.ComparatorOfNumericString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
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
                    Log.e("HomeFragApi", key + " is not available which should come in response");
                    return "";
                }
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        public class ComparatorOfNumericString implements Comparator<ItemList> {
            @SuppressLint("NewApi")
            @Override
            public int compare(ItemList lhs, ItemList rhs) {
                int i1 = Integer.parseInt(lhs.getMinOrderQty());
                int i2 = Integer.parseInt(rhs.getMinOrderQty());
                //Log.d("Collection String::", String.valueOf(Integer.compare(i1, i2)));
                return Integer.compare(i1, i2);
            }

        }


    }
}