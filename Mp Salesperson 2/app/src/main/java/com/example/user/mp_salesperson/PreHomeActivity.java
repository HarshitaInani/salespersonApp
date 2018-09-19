package com.example.user.mp_salesperson;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.example.user.mp_salesperson.bean.BaseCatSubCatBean;
import com.example.user.mp_salesperson.bean.PopularBrandBean;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.BaseCatBean;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.CategoryBean;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.SubCategoriesBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Krishna on 29-01-2017.
 */

public class PreHomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pre_home_activity);
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(PreHomeActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        new GetCategorySubCategory().execute(mRetailerBean.getWarehouseid());


//        new GetPopularBrands().execute(mRetailerBean.getWarehouseid());

    }

    public class GetCategorySubCategory extends AsyncTask<String, Void, JSONObject> {
        Dialog mDialog;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(PreHomeActivity.this);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            mDialog.setContentView(R.layout.progress_dialog);


            /*if (isItemListAvail) {
                ((TextView) mDialog.findViewById(R.id.progressText)).setText("Refreshing item list please wait...");

            } else {
                ((TextView) mDialog.findViewById(R.id.progressText)).setText("Loading items please wait...");
                mDialog.setCancelable(false);
                mDialog.show();
            }*/


        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonObjectFromUrl = null;
            try {


                jsonObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonObjectFromHttpUrlConnection(Constant.BASE_URL_ITEM_MASTER + "?warehouseid=" + params[0], null, HttpUrlConnectionJSONParser.Http.GET);




            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjectFromUrl;
        }

/*

        {"ItemId":331
                ,"Cityid":1
                ,"CityName":"Indore"
                ,"Categoryid":15
                ,"SubCategoryId":23
                ,"SubsubCategoryid":24
                ,"warehouse_id":1
                ,"SupplierId":92
                ,"SUPPLIERCODES":"SkS1041"
                ,"CompanyId":1
                ,"CategoryName":"Staples"
                ,"BaseCategoryid":8
                ,"BaseCategoryName":"Groceries"
                ,"SubcategoryName":"Garner"
                ,"SubsubcategoryName":"Garner"
                ,"SupplierName":"SKN Garner"
                ,"itemname":"Rai 100 gm"
                ,"itemcode":"1"
                ,"SellingUnitName":"RAI 100 GM 10pc"
                ,"PurchaseUnitName":"RAI 100 GM 10pc"
                ,"price":20.0,"VATTax":0.0
                ,"active":true
                ,"LogoUrl":"http://SK10-12-15.moreyeahs.in/../../UploadedLogos/01AE101110.jpg","CatLogoUrl":"http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/../../images/catimages/8.jpg"
                ,"MinOrderQty":10
                ,"PurchaseMinOrderQty":10
                ,"GruopID":1
                ,"TGrpName":"TG1"
                ,"Discount":0.0
                ,"UnitPrice":6.3315
                ,"Number":"01AE1011"
                ,"PurchaseSku":"01AE101110"
                ,"SellingSku":"01AE101110"
                ,"PurchasePrice":6.3291375
                ,"GeneralPrice":null
                ,"title":null
                ,"Description":null
                ,"StartDate":null
                ,"EndDate":null
                ,"PramotionalDiscount":0.0
                ,"TotalTaxPercentage":5.0
                ,"WarehouseName":"W1"
                ,"CreatedDate":"2017-03-04T13:31:40.807-07:00"
                ,"UpdatedDate":"2017-03-04T13:31:40.807-07:00"
                ,"Deleted":false
                ,"IsDailyEssential":false
                ,"DisplaySellingPrice":0.0
                ,"StoringItemName":"Rai 100 gm"
                ,"SizePerUnit":18.3
                ,"HindiName":"राई 100 ग्राम 10 पीस"
                ,"Barcode":""
                ,"CurrentStock":0},

*/


        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {
                try {
                    if (TextUtils.isNullOrEmpty(jsonObject.getJSONArray("Basecats").toString())) {
                        Toast.makeText(PreHomeActivity.this, "BaseCategories not available on server", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isNullOrEmpty(jsonObject.getJSONArray("Categories").toString())) {
                        Toast.makeText(PreHomeActivity.this, "Categories not available on server", Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayList<BaseCatBean> mBaseCatBeanArrayList = new ArrayList<>();
                        ArrayList<CategoryBean> mCategoryBeanArrayList = new ArrayList<>();
                        ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList = new ArrayList<>();

                        JSONArray mBaseCategoryJsonArray = jsonObject.getJSONArray("Basecats");
                        for (int i = 0; i < mBaseCategoryJsonArray.length(); i++) {
                            String baseCategoryId = isNullOrEmpty(mBaseCategoryJsonArray.getJSONObject(i), "BaseCategoryId");
                            String warehouseid = isNullOrEmpty(mBaseCategoryJsonArray.getJSONObject(i), "Warehouseid");
                            String baseCategoryName = isNullOrEmpty(mBaseCategoryJsonArray.getJSONObject(i), "BaseCategoryName");
                            String logoUrl = isNullOrEmpty(mBaseCategoryJsonArray.getJSONObject(i), "LogoUrl");
                            mBaseCatBeanArrayList.add(new BaseCatBean(baseCategoryId, warehouseid, baseCategoryName, logoUrl));
                        }
                        JSONArray mCategoriesJsonArray = jsonObject.getJSONArray("Categories");
                        for (int i = 0; i < mCategoriesJsonArray.length(); i++) {
                            String baseCategoryId = isNullOrEmpty(mCategoriesJsonArray.getJSONObject(i), "BaseCategoryId");
                            String categoryid = isNullOrEmpty(mCategoriesJsonArray.getJSONObject(i), "Categoryid");
                            String warehouseid = isNullOrEmpty(mCategoriesJsonArray.getJSONObject(i), "Warehouseid");
                            String categoryName = mCategoriesJsonArray.getJSONObject(i).getString("CategoryName");
                            String logoUrl = isNullOrEmpty(mCategoriesJsonArray.getJSONObject(i), "LogoUrl");
                            mCategoryBeanArrayList.add(new CategoryBean(baseCategoryId, categoryid, warehouseid, categoryName, logoUrl));
                        }
                        JSONArray mSubCategoriesJsonArray = jsonObject.getJSONArray("SubCategories");
                        for (int i = 0; i < mSubCategoriesJsonArray.length(); i++) {
                            String subCategoryId = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "SubCategoryId");
                            String categoryid = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "Categoryid");
                            String subcategoryName = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "SubcategoryName");
                            mSubCategoriesBeanArrayList.add(new SubCategoriesBean(subCategoryId, categoryid, subcategoryName));
                        }
                        BaseCatSubCatBean mBaseCatSubCatBean = new BaseCatSubCatBean(mBaseCatBeanArrayList, mCategoryBeanArrayList, mSubCategoriesBeanArrayList);

                        if (mBaseCatSubCatBean != null) {
                            ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(PreHomeActivity.this, Constant.BASECAT_CAT_SUBCAT_PREF, PreHomeActivity.this.MODE_PRIVATE);
                            mBaseCatSubCatCat.putObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, mBaseCatSubCatBean);
                            mBaseCatSubCatCat.commit();

                            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(i);
                            PreHomeActivity.this.finish();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(PreHomeActivity.this, "Server not responding properly", Toast.LENGTH_SHORT).show();
            }
            if (mDialog.isShowing()) mDialog.dismiss();
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











    public class GetPopularBrands extends AsyncTask<String, Void, JSONArray> {
        /* Dialog mDialog;

         @Override
         protected void onPreExecute() {
             mDialog = new Dialog(getActivity());
             mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
             mDialog.setContentView(R.layout.progress_dialog);
             if (isPopularBrandItemListAvail) {
                 ((TextView) mDialog.findViewById(R.id.progressText)).setText("Refreshing item list please wait...");
             } else {
                 ((TextView) mDialog.findViewById(R.id.progressText)).setText("Loading Popular Brands please wait...");
                 mDialog.setCancelable(false);
                 mDialog.show();
             }
         }*/
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(PreHomeActivity.this);
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
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_BRAND_PROMOTION + "?warehouseid=" + params[0], null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (jsonArray != null && jsonArray.length() > 0) {
                ArrayList<PopularBrandBean> mPopularBrandBeenArrayList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject mJsonObject = jsonArray.getJSONObject(i);
                        String SubCategoryId = isNullOrEmpty(mJsonObject, "SubCategoryId");
                        String CompanyId = isNullOrEmpty(mJsonObject, "CompanyId");
                        String Categoryid = isNullOrEmpty(mJsonObject, "Categoryid");
                        String Warehouseid = isNullOrEmpty(mJsonObject, "Warehouseid");
                        String CategoryName = isNullOrEmpty(mJsonObject, "CategoryName");
                        String SubcategoryName = isNullOrEmpty(mJsonObject, "SubcategoryName");
                        String Discription = isNullOrEmpty(mJsonObject, "Discription");
                        String SortOrder = isNullOrEmpty(mJsonObject, "SortOrder");
                        String IsPramotional = isNullOrEmpty(mJsonObject, "IsPramotional");
                        String CreatedDate = isNullOrEmpty(mJsonObject, "CreatedDate");
                        String UpdatedDate = isNullOrEmpty(mJsonObject, "UpdatedDate");
                        String CreatedBy = isNullOrEmpty(mJsonObject, "CreatedBy");
                        String UpdateBy = isNullOrEmpty(mJsonObject, "UpdateBy");
                        String Code = isNullOrEmpty(mJsonObject, "SubCategoryId");
                        String LogoUrl = isNullOrEmpty(mJsonObject, "UpdateBy");
                        String Deleted = isNullOrEmpty(mJsonObject, "UpdateBy");
                        String IsActive = isNullOrEmpty(mJsonObject, "UpdateBy");
                        PopularBrandBean mPopularBrandBean = new PopularBrandBean(SubCategoryId, CompanyId, Categoryid, Warehouseid, CategoryName, SubcategoryName +"\n(Popular Brand)", Discription, SortOrder, IsPramotional, CreatedDate, UpdatedDate, CreatedBy, UpdateBy, Code, LogoUrl, Deleted, IsActive);
                        mPopularBrandBeenArrayList.add(mPopularBrandBean);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                if (PreHomeActivity.this != null) {

                    ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(PreHomeActivity.this, Constant.POPULAR_BRANDS_PREF, PreHomeActivity.this.MODE_PRIVATE);
                    mBaseCatSubCatCat.putObject(Constant.POPULAR_BRANDS_PREFOBJ, mPopularBrandBeenArrayList);
                    mBaseCatSubCatCat.commit();


                   /*
                   mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(PreHomeActivity.this, Constant.BASECAT_CAT_SUBCAT_PREF, PreHomeActivity.this.MODE_PRIVATE);
                    mBaseCatSubCatCat.putObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, mPopularBrandBeenArrayList);
                    mBaseCatSubCatCat.commit();
*/


                    ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(PreHomeActivity.this, Constant.RETAILER_BEAN_PREF, PreHomeActivity.this.MODE_PRIVATE);
                    RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);

                   /* HomeFragRecyclerViewAdapterPopularBrands mHomeFragRecyclerViewAdapter = new HomeFragRecyclerViewAdapterPopularBrands(getActivity(), mPopularBrandBeenArrayList, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean);
                    mHomeFragRecyclerView.setAdapter(mHomeFragRecyclerViewAdapter);*/

                    mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(PreHomeActivity.this, Constant.BASECAT_CAT_SUBCAT_PREF, PreHomeActivity.this.MODE_PRIVATE);
                    BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);

// CAlling get sub
                    new GetCategorySubCategory().execute(mRetailerBean.getWarehouseid());



                    if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {



                     /*   isItemListAvail = true;

                        Type typePopularBrandBeanArrayList = new TypeToken<ArrayList<PopularBrandBean>>() {
                        }.getType();
                        mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.POPULAR_BRANDS_PREF, getActivity().MODE_PRIVATE);
                        mPopularBrandBeenArrayList = mBaseCatSubCatCat.getArray(Constant.POPULAR_BRANDS_PREFOBJ, typePopularBrandBeanArrayList);
                        if (mPopularBrandBeenArrayList != null && !mPopularBrandBeenArrayList.isEmpty())
                            isPopularBrandItemListAvail = true;
*/




/*
                        try {
                            HomeFragRecyclerViewAdapter mHomeFragRecyclerViewAdapter = new HomeFragRecyclerViewAdapter(getActivity(), mBaseCatSubCatBean, rowIMageHeight, rowIMageWidth, getFragmentManager(), mRetailerBean, mPopularBrandBeenArrayList == null ? new ArrayList<PopularBrandBean>() : mPopularBrandBeenArrayList);
                            mHomeFragRecyclerView.setAdapter(mHomeFragRecyclerViewAdapter);

                        } catch (IndexOutOfBoundsException e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        } catch (Exception e) {
                            startActivity(new Intent(getActivity(), HomeActivity.class));
                        }

                   */

                    }





                }
            } else {
                Toast.makeText(PreHomeActivity.this, "Improper response from server", Toast.LENGTH_SHORT).show();
            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
        }
    }
























}
