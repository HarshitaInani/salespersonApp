package com.example.user.mp_salesperson.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

import com.Amitlibs.utils.ComplexPreferences;
import com.example.user.mp_salesperson.CartActivity;
import com.example.user.mp_salesperson.Constant;
import com.example.user.mp_salesperson.HomeActivity;
import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.Utils;
import com.example.user.mp_salesperson.adapters.HomeFragItemListAdapter;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.ItemList;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.util.ArrayList;



import static android.content.Context.MODE_PRIVATE;

/**
 * Created by gole on 03-01-2017.
 */

public class HomeFragMostSelledItemList extends Fragment {
    int selectedCategoryId = -1;
    int selectedWarId = -1;
    int itemId = -1;
    int rowitemImageHeight=77;
    int rowitemImageWidth=77;
    RecyclerView mItemListRecyclerView;
    RelativeLayout rl1;
    TextView tvTotalItemPrice;
    TextView tvTotalItemQty;
    TextView tvTotalDp;

    HomeFragItemListAdapter mItemListAdapter;

    int mp = 0, pp = 0;

    String locale;

    String hindiLanguage = " हिन्दी (भारत)";

    SharedPreferences sharedpreferences;

    String languageCheck = "e";

    TextView show_popup;
    Dialog mDialog;
    AnimationDrawable animation;

    TextView tvBrandName;


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
        View v = inflater.inflate(R.layout.item_list_home_frag_all, container, false);

        //setImagesDynamicSize();
        //   getActivity().getApplicationContext();
        locale = getActivity().getApplicationContext().getResources().getConfiguration().locale.getDisplayName();

        Log.e("language", locale);

        sharedpreferences = getActivity().getApplicationContext().getSharedPreferences("MyPrefs", MODE_PRIVATE);

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
        tvBrandName=(TextView)v.findViewById(R.id.brand_name);




            ArrayList<ItemList> mPopularBrandBeenArrayList1 = new ArrayList<>();
            Type typePopularBrandBeanArrayList1 = new TypeToken<ArrayList<ItemList>>() {}.getType();
         ComplexPreferences mBaseCatSubCatCat1 = ComplexPreferences.getComplexPreferences(getActivity(), Constant.MOST_SELLED_ITEM_PREF, getActivity().MODE_PRIVATE);
        mPopularBrandBeenArrayList1 = mBaseCatSubCatCat1.getArray(Constant.MOST_SELLED_ITEM_PREFOBJ,typePopularBrandBeanArrayList1);
        ArrayList<ItemList> mPopularBrandBeenArrayList = new ArrayList<>();
        mPopularBrandBeenArrayList.clear();
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
              String  price=mPopularBrandBeenArrayList1.get(i).getPrice();
              String  SellingUnitName=mPopularBrandBeenArrayList1.get(i).getSellingUnitName();
              String  SellingSku=mPopularBrandBeenArrayList1.get(i).getSellingSku();
              String  UnitPrice=mPopularBrandBeenArrayList1.get(i).getUnitPrice();
              String  VATTax=mPopularBrandBeenArrayList1.get(i).getVATTax();
              String  LogoUrl=mPopularBrandBeenArrayList1.get(i).getLogoUrl();
              String  MinOrderQty=mPopularBrandBeenArrayList1.get(i).getMinOrderQty();
              String  Discount=mPopularBrandBeenArrayList1.get(i).getDiscount();
              String  TotalTaxPercentage=mPopularBrandBeenArrayList1.get(i).getTotalTaxPercentage();
              //String  HindiName=mPopularBrandBeenArrayList1.get(i).getItemHindiname();
              String  DpPoint=mPopularBrandBeenArrayList1.get(i).getDreamPoint();
              String  PromoPoint=mPopularBrandBeenArrayList1.get(i).getPromoPoint();
              String  MarginPoint=mPopularBrandBeenArrayList1.get(i).getMarginPoint();
              String  WarehouseId=mPopularBrandBeenArrayList1.get(i).getWarehouseId();
              String  CompanyId=mPopularBrandBeenArrayList1.get(i).getCompanyId();
              String  ItemNumber=mPopularBrandBeenArrayList1.get(i).getItemNumber();
                String  IsOffer=mPopularBrandBeenArrayList1.get(i).getIsOffer();
                mPopularBrandBeenArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage,"", DpPoint, PromoPoint, MarginPoint, WarehouseId, CompanyId,ItemNumber,IsOffer));


                break;
            }


        }

        mItemListAdapter = new HomeFragItemListAdapter(getActivity(), mPopularBrandBeenArrayList, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty, tvTotalDp,show_popup,getFragmentManager());
        mItemListRecyclerView.setAdapter(mItemListAdapter);



        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


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
    @Override
    public void onStop()
    {
        try {
            mItemListAdapter.Texttospeechshutdown();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        // Don't forget to shutdown tts!
        try {
            mItemListAdapter.Texttospeechshutdown();

        }
        catch(Exception e){
            e.printStackTrace();
        }
        super.onDestroy();
    }


}