package com.example.user.mp_salesperson.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.example.user.mp_salesperson.CartActivity;
import com.example.user.mp_salesperson.Constant;
import com.example.user.mp_salesperson.HomeActivity;
import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.Utils;
import com.example.user.mp_salesperson.adapters.ItemListSubSubCatAdapter;
import com.example.user.mp_salesperson.adapters.PopularItemListAdapter;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.ItemList;

import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.SubSubCategoriesBean;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by gole on 03-01-2017.
 */

public class HomeFragItemPopularItemList extends Fragment {
    int selectedCategoryId = -1;
    int selectedWarId = -1;
    int itemId = -1;
    int rowitemImageHeight=77;
    int rowitemImageWidth=77;



    TextView itemListSelectdSubSubCatTv;
    RecyclerView mItemListRecyclerView;
    RelativeLayout rl1;
    AsyncTask<String, Void, ArrayList<ItemList>> mItemListAsyncTask;
    AsyncTask<String, Void, ArrayList<SubSubCategoriesBean>> mSubSubCatFilterTask;

    TextView tvTotalItemPrice;
    TextView tvTotalItemQty;

    TextView tvTotalDp;


    boolean showDialog = true;
    ArrayList<ItemList> mItemListArrayList = new ArrayList<>();


    PopularItemListAdapter mItemListAdapter;
    ItemListSubSubCatAdapter itemListSubSubCatAdapter;


    int mp = 0, pp = 0;

    String locale;

    String hindiLanguage = " हिन्दी (भारत)";

    SharedPreferences sharedpreferences;

    String languageCheck = "e";

    TextView tvBrandName;
    TextView show_popup;
    Dialog mDialog;
    AnimationDrawable animation;
    ArrayList<ItemList> mItemListAllValue= new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedCategoryId = getArguments().getInt("selectedCategoryId");
        selectedWarId = getArguments().getInt("selectedWarId");
        itemId = getArguments().getInt("itemId");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.popular_item_list_frag, container, false);

       // setImagesDynamicSize();
        //   getActivity().getApplicationContext();
        locale = getActivity().getApplicationContext().getResources().getConfiguration().locale.getDisplayName();

        Log.e("language", locale);

        sharedpreferences = getActivity().getApplicationContext().getSharedPreferences("MyPrefs",
                MODE_PRIVATE);

        languageCheck = sharedpreferences.getString("language", "");

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mItemListRecyclerView = (RecyclerView) v.findViewById(R.id.item_list_rv);
        mItemListRecyclerView.setNestedScrollingEnabled(false);
        mItemListRecyclerView.setHasFixedSize(true);
        mItemListRecyclerView.setLayoutManager(linearLayoutManager);
        mItemListRecyclerView.setAdapter(mItemListAdapter);
        show_popup=(TextView)v.findViewById(R.id.show_popup) ;

        rl1=(RelativeLayout)v.findViewById(R.id.rl1);
        tvTotalItemPrice=(TextView)v.findViewById(R.id.item_list_total_amount_tv);
        tvTotalItemQty=(TextView)v.findViewById(R.id.item_list_total_item_tv);
        tvTotalDp=(TextView)v.findViewById(R.id.item_list_total_dp_tv);
        tvBrandName=(TextView)v.findViewById(R.id.item_list_frag_selected_tv);

        ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);


            ArrayList<ItemList> mPopularBrandBeenArrayList1 = new ArrayList<>();
            Type typePopularBrandBeanArrayList1 = new TypeToken<ArrayList<ItemList>>() {}.getType();
            mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.POPULAR_BRANDS_PREF1, getActivity().MODE_PRIVATE);

        mPopularBrandBeenArrayList1 = mBaseCatSubCatCat1.getArray(Constant.POPULAR_BRANDS_PREFOBJ1,typePopularBrandBeanArrayList1);
        System.out.println("mPopularBrandBeenArrayList1::::"+mPopularBrandBeenArrayList1);
        ArrayList<ItemList> mPopularBrandBeenArrayList = new ArrayList<>();

        for (int i = 0; i < mPopularBrandBeenArrayList1.size(); i++) {

            System.out.println("ItemId11::"+mPopularBrandBeenArrayList1.get(i).getItemId());
            System.out.println("ItemId22::"+itemId);
            if(mPopularBrandBeenArrayList1.get(i).getItemId().equalsIgnoreCase(String.valueOf(itemId)))
            {

              String  ItemId=mPopularBrandBeenArrayList1.get(i).getItemId();
              String  Categoryid=mPopularBrandBeenArrayList1.get(i).getCategoryid();
              String  SubCategoryId=mPopularBrandBeenArrayList1.get(i).getSubCategoryId();
              String  SubsubCategoryid=mPopularBrandBeenArrayList1.get(i).getSubsubCategoryid();
              String  itemname=mPopularBrandBeenArrayList1.get(i).getItemname();
                tvBrandName.setText(itemname);
              String  price=mPopularBrandBeenArrayList1.get(i).getPrice();
              String  SellingUnitName=mPopularBrandBeenArrayList1.get(i).getSellingUnitName();
              String  SellingSku=mPopularBrandBeenArrayList1.get(i).getSellingSku();
              String  UnitPrice=mPopularBrandBeenArrayList1.get(i).getUnitPrice();
              String  VATTax=mPopularBrandBeenArrayList1.get(i).getVATTax();
              String  LogoUrl=mPopularBrandBeenArrayList1.get(i).getLogoUrl();
              String  MinOrderQty=mPopularBrandBeenArrayList1.get(i).getMinOrderQty();
              String  Discount=mPopularBrandBeenArrayList1.get(i).getDiscount();
              String  TotalTaxPercentage=mPopularBrandBeenArrayList1.get(i).getTotalTaxPercentage();
              String  HindiName=mPopularBrandBeenArrayList1.get(i).getItemHindiname();
              String  DpPoint=mPopularBrandBeenArrayList1.get(i).getDreamPoint();
              String  PromoPoint=mPopularBrandBeenArrayList1.get(i).getPromoPoint();
              String  MarginPoint=mPopularBrandBeenArrayList1.get(i).getMarginPoint();
              String  WarehouseId=mPopularBrandBeenArrayList1.get(i).getWarehouseId();
              String  CompanyId=mPopularBrandBeenArrayList1.get(i).getCompanyId();
              String  ItemNumber=mPopularBrandBeenArrayList1.get(i).getItemNumber();
              String  IsOffer=mPopularBrandBeenArrayList1.get(i).getIsOffer();

                mPopularBrandBeenArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, WarehouseId, CompanyId,ItemNumber,IsOffer));


                break;
            }


        }

        mItemListAdapter = new PopularItemListAdapter(getActivity(), mPopularBrandBeenArrayList, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty, tvTotalDp,show_popup);
        mItemListRecyclerView.setAdapter(mItemListAdapter);

         /*   try {
                System.out.println("Rungg:::"+mItemListAllValue);
                mItemListAdapter = new PopularItemListAdapter(getActivity(), mItemListAllValue, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty, tvTotalDp);
                mItemListRecyclerView.setAdapter(mItemListAdapter);
            }catch (IndexOutOfBoundsException e) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
            } catch (Exception e) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }*/

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //  Toast.makeText(getActivity().getApplicationContext(), "To Cart Activity", Toast.LENGTH_SHORT).show();
                getActivity().startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });


    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(((HomeActivity) getActivity()).getCartItem().getTotalPrice()));
        tvTotalItemQty.setText("" + (int) ((HomeActivity) getActivity()).getCartItem().getTotalQuantity());
        tvTotalDp.setText("Dp : " + new DecimalFormat("##.##").format(((HomeActivity) getActivity()).getCartItem().getTotalDpPoints()));

        if (Utils.isInternetConnected(getActivity())) {



          // new GetBrandWisePromotion().execute();


        } else {
            new AlertDialog.Builder(getActivity())
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

    @Override
    public void onPause() {

        if (mItemListAsyncTask != null && !mItemListAsyncTask.isCancelled())
            mItemListAsyncTask.cancel(true);
        if (mSubSubCatFilterTask != null && !mSubSubCatFilterTask.isCancelled())
            mSubSubCatFilterTask.cancel(true);
        super.onPause();
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
                Log.e("HomeFragItemListFrag", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void setImagesDynamicSize() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
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


    public class GetBrandWisePromotion extends AsyncTask<String, Void, JSONArray> {

        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(getActivity());
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
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_BRAND_WISE_PROMOTION, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (jsonArray != null && jsonArray.length() > 0) {
                ArrayList<ItemList> mItemListAllValue = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {

                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String ItemId = isNullOrEmpty(mJsonObject, "ItemId");
                        String Categoryid = isNullOrEmpty(mJsonObject, "Categoryid");
                        String SubCategoryId = isNullOrEmpty(mJsonObject, "SubCategoryId");
                        String SubsubCategoryid = isNullOrEmpty(mJsonObject, "SubsubCategoryid");
                        String itemname = isNullOrEmpty(mJsonObject, "itemname");
                        String price = isNullOrEmpty(mJsonObject, "price");
                        String SellingUnitName = isNullOrEmpty(mJsonObject, "SellingUnitName");
                        String ItemNumber = isNullOrEmpty(mJsonObject, "Number");
                        String SellingSku = isNullOrEmpty(mJsonObject, "SellingSku");
                        String UnitPrice = isNullOrEmpty(mJsonObject, "UnitPrice");
                        String VATTax = isNullOrEmpty(mJsonObject, "VATTax");
                        String LogoUrl = isNullOrEmpty(mJsonObject, "LogoUrl");
                        String MinOrderQty = isNullOrEmpty(mJsonObject, "MinOrderQty");
                        String Discount = isNullOrEmpty(mJsonObject, "Discount");
                        String TotalTaxPercentage = isNullOrEmpty(mJsonObject, "TotalTaxPercentage");
                        String HindiName = isNullOrEmpty(mJsonObject, "HindiName");
                        String DpPoint = "";
                        String PromoPoint = isNullOrEmpty(mJsonObject, "promoPerItems");
                        String MarginPoint = isNullOrEmpty(mJsonObject, "marginPoint");
                        String WarehouseId = isNullOrEmpty(mJsonObject, "WarehouseId");
                        String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                        String Isoffer = isNullOrEmpty(mJsonObject, "Isoffer");


                        if(PromoPoint.trim().equals("null")) {
                            pp = 0;

                        }

                        if(PromoPoint.isEmpty()) {
                            pp = 0;

                        }


                        else if(PromoPoint.trim().equals("null")) {
                            pp = 0;

                        }

                        else {
                            pp = Integer.parseInt(PromoPoint);

                        }

                        if(MarginPoint.trim().equals("null")) {

                            mp = 0;
                        }


                        if(MarginPoint.isEmpty()) {

                            mp = 0;
                        }

                        else if(MarginPoint.trim().equals("null")) {

                            mp = 0;
                        }

                        else {
                            mp = Integer.parseInt(MarginPoint);

                        }

                        if(pp > 0 && mp > 0) {
                            int pp_mp = pp + mp;
                            DpPoint = ""+pp_mp;
                        }
                        else if (mp > 0) {
                            DpPoint = ""+mp;
                        }
                        else if (pp > 0) {
                            DpPoint = ""+pp;
                        }

                        else {
                            DpPoint = "0";
                        }

                        mItemListAllValue.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, WarehouseId, CompanyId,ItemNumber,Isoffer));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                }



                System.out.println("Valueis::::::"+mItemListAllValue);
                mItemListAdapter = new PopularItemListAdapter(getActivity(), mItemListAllValue, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty, tvTotalDp,show_popup);
                mItemListRecyclerView.setAdapter(mItemListAdapter);

            } else {

            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
        }
    }


    public void showLoading() {


        mDialog = new Dialog(getActivity());
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