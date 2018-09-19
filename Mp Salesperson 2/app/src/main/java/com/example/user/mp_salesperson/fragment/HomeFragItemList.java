package com.example.user.mp_salesperson.fragment;

import android.annotation.SuppressLint;
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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.CartActivity;
import com.example.user.mp_salesperson.Constant;
import com.example.user.mp_salesperson.HomeActivity;
import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.Utils;
import com.example.user.mp_salesperson.adapters.ItemListAdapter;
import com.example.user.mp_salesperson.adapters.ItemListAdapterForOffer;
import com.example.user.mp_salesperson.adapters.ItemListSubCatAdapter;
import com.example.user.mp_salesperson.adapters.ItemListSubSubCatAdapter;
import com.example.user.mp_salesperson.bean.BaseCatSubCatBean;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.bean.SubSubCatItemListBean;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.ItemList;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.SubCategoriesBean;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.SubSubCategoriesBean;
import com.example.user.mp_salesperson.databinding.ItemListFragBinding;
import com.example.user.mp_salesperson.interfaces.ItemListSubCatAdapterInterface;
import com.example.user.mp_salesperson.interfaces.ItemListSubSubCatAdapterInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.content.Context.MODE_PRIVATE;


/**
 * Created by Krishna on 03-01-2017.
 */

public class HomeFragItemList extends Fragment implements ItemListSubSubCatAdapterInterface, ItemListSubCatAdapterInterface, ItemListAdapter.Offer {
    int selectedCategoryId = -1;
    int selectedWarId = -1;
    int rowitemImageHeight=77;
    int rowitemImageWidth=77;

    RecyclerView mSubCatRecyclerView;
    RecyclerView mSubSubCatRecyclerView;

    AsyncTask<String, Void, JSONObject> mGetSubSubCatItemListAsyncTask;
 //   TextView itemListSelectdSubSubCatTv;

    RecyclerView mItemListRecyclerView;
    AsyncTask<String, Void, ArrayList<ItemList>> mItemListAsyncTask;
    AsyncTask<String, Void, ArrayList<SubSubCategoriesBean>> mSubSubCatFilterTask;

    TextView tvTotalItemPrice;
    TextView tvTotalItemQty;

    TextView tvTotalDp;
    TextView show_popup;

    boolean showDialog = true;
    ArrayList<ItemList> mItemListArrayList = new ArrayList<>();
    ArrayList<ItemList> mItemListAllValue= new ArrayList<>();

    ItemListAdapter mItemListAdapter;
    ItemListSubSubCatAdapter itemListSubSubCatAdapter;
    Button image_filter;

    int mp = 0, pp = 0;

    String locale;

    String hindiLanguage = " हिन्दी (भारत)";

    SharedPreferences sharedpreferences;

    String languageCheck = "e";



    Dialog mDialog;
    AnimationDrawable animation;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedCategoryId = getArguments().getInt("selectedCategoryId");
        selectedWarId = getArguments().getInt("selectedWarId");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ItemListFragBinding itemListFragBinding = DataBindingUtil.inflate(
                inflater, R.layout.item_list_frag, container, false);
        View view = itemListFragBinding.getRoot();
       // setImagesDynamicSize();

        //   getActivity().getApplicationContext();

        locale = getActivity().getApplicationContext().getResources().getConfiguration().locale.getDisplayName();

        Log.e("language", locale);

        sharedpreferences = getActivity().getApplicationContext().getSharedPreferences("MyPrefs",
                MODE_PRIVATE);

        languageCheck = sharedpreferences.getString("language", "");

        //    Toast.makeText(getActivity().getApplicationContext() , locale, Toast.LENGTH_SHORT).show();

     //   itemListSelectdSubSubCatTv = itemListFragBinding.itemListFragSelectedTv;


        tvTotalItemPrice = itemListFragBinding.itemListTotalAmountTv;
        tvTotalItemQty = itemListFragBinding.itemListTotalItemTv;
        show_popup=(TextView)view.findViewById(R.id.show_popup) ;

        tvTotalDp= itemListFragBinding.itemListTotalDpTv;


        mSubCatRecyclerView = itemListFragBinding.subCatListFragSubcategoryRv;
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mSubCatRecyclerView.setLayoutManager(layoutManager);

        mSubSubCatRecyclerView = itemListFragBinding.subSubCatListFragSubcategoryRv;
        LinearLayoutManager layoutManager1
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mSubSubCatRecyclerView.setLayoutManager(layoutManager1);

        mItemListRecyclerView = itemListFragBinding.itemListRv;
        LinearLayoutManager layoutManager2
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mItemListRecyclerView.setLayoutManager(layoutManager2);

        itemListFragBinding.rl1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  Toast.makeText(getActivity().getApplicationContext(), "To Cart Activity", Toast.LENGTH_SHORT).show();
                getActivity().startActivity(new Intent(getActivity(), CartActivity.class));
            }
        });
        image_filter = (Button) view.findViewById(R.id.filter);

        /*image_filter.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                View menuItemView =v.findViewById(R.id.filter);
                PopupMenu popup = new PopupMenu(getContext(), menuItemView);


                MenuInflater inflate = popup.getMenuInflater();
                inflate.inflate(R.menu.filter, popup.getMenu());
                popup.show();


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int id = item.getItemId();
                        if (id == R.id.trade_offer) {
                            ItemListAdapterForOffer mItemListAdapterOffer = new ItemListAdapterForOffer(getContext(), mItemListArrayList, 77, 77, tvTotalItemPrice, tvTotalItemQty, tvTotalDp, show_popup, mItemListAllValue, getFragmentManager());
                            mItemListAdapterOffer.OfferListner( HomeFragItemList.this);
                            mItemListRecyclerView.setAdapter(mItemListAdapterOffer);
                            mItemListAdapterOffer.notifyDataSetChanged();
                            return true;
                        } else return false;
                    }

                });

            }
        });
*/


        try {
            mItemListAdapter = new ItemListAdapter(getActivity(), mItemListArrayList, rowitemImageWidth, rowitemImageHeight, tvTotalItemPrice, tvTotalItemQty, tvTotalDp,show_popup,mItemListAllValue,getFragmentManager());
            mItemListRecyclerView.setAdapter(mItemListAdapter);

        } catch (IndexOutOfBoundsException e) {

            //Intent i = new Intent(getActivity(), HomeActivity.class);
            startActivity(new Intent(getActivity(), HomeActivity.class));
        }
        catch (Exception e) {
            startActivity(new Intent(getActivity(), HomeActivity.class));
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(((HomeActivity) getActivity()).getCartItem().getTotalPrice()));
        tvTotalItemQty.setText("" + (int) ((HomeActivity) getActivity()).getCartItem().getTotalQuantity());

        tvTotalDp.setText("Dp : " + new DecimalFormat("##.##").format(((HomeActivity) getActivity()).getCartItem().getTotalDpPoints()));





        ComplexPreferences mSubSubCatItem = ComplexPreferences.getComplexPreferences(getActivity(), Constant.SUB_SUB_CAT_ITEM_PREF, getActivity().MODE_PRIVATE);
        SubSubCatItemListBean mSubSubCatItemListBean = mSubSubCatItem.getObject("WarId:-" + selectedWarId + "--CatId:-" + selectedCategoryId, SubSubCatItemListBean.class);
        if (mSubSubCatItemListBean != null) {
            ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
            BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
            if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList = new ArrayList<>();
                ArrayList<SubCategoriesBean> mTempSubCategoriesBeanArrayList = mBaseCatSubCatBean.getmSubCategoriesBeanArrayList();
                for (int i = 0; i < mTempSubCategoriesBeanArrayList.size(); i++) {
                    if (mTempSubCategoriesBeanArrayList.get(i).getCategoryid().equalsIgnoreCase("" + selectedCategoryId)) {
                        mSubCategoriesBeanArrayList.add(mTempSubCategoriesBeanArrayList.get(i));
                    }
                }

           try {
               ItemListSubCatAdapter mItemListSubCatAdapter = new ItemListSubCatAdapter(getActivity(), mSubCategoriesBeanArrayList, HomeFragItemList.this);
               mSubCatRecyclerView.setAdapter(mItemListSubCatAdapter);
           } catch (IndexOutOfBoundsException e) {
               startActivity(new Intent(getActivity(), HomeActivity.class));
           } catch (Exception e) {
               startActivity(new Intent(getActivity(), HomeActivity.class));
           }

            } else {
                Toast.makeText(getActivity(), "Items are not available", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStack();
            }
            showDialog = false;
            Log.d(getTag(), "Items Available");
        } else {
            showDialog = true;
            Log.d(getTag(), "Items Not Available");
        }
        if (Utils.isInternetConnected(getActivity())) {


//            mGetSubSubCatItemListAsyncTask = new GetSubSubCatItemListAsyncTask().execute("" + selectedWarId, "" + selectedCategoryId);


            callAqueryForItem(""+selectedCategoryId, ""+selectedWarId);




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
        if (mGetSubSubCatItemListAsyncTask != null && !mGetSubSubCatItemListAsyncTask.isCancelled())
            mGetSubSubCatItemListAsyncTask.cancel(true);
        if (mItemListAsyncTask != null && !mItemListAsyncTask.isCancelled())
            mItemListAsyncTask.cancel(true);
        if (mSubSubCatFilterTask != null && !mSubSubCatFilterTask.isCancelled())
            mSubSubCatFilterTask.cancel(true);
        super.onPause();
    }

    @Override
    public void subcategorySelected(String selecctedSubCatId) {
        mSubSubCatFilterTask = new FilterSubSubCatAsyncTask().execute(selecctedSubCatId);
    }

    @Override
    public void subSubCategorySelected(String selecctedSuSubCatId) {
        mItemListAsyncTask = new FilterItemsAsyncTask().execute(selecctedSuSubCatId);
    }

    @Override
    public void Offerlist(ArrayList<ItemList> Offerlist) {

    }

    public class FilterSubSubCatAsyncTask extends AsyncTask<String, Void, ArrayList<SubSubCategoriesBean>> {
        /*Dialog mDialog;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(getActivity());
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Filtering Categories list please wait...");
            mDialog.setCancelable(false);
            mDialog.show();
        }*/
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(getActivity());
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Filtering Categories list please wait...");
            ImageView la = (ImageView) mDialog.findViewById(R.id.gridprogressBar);
            la.setBackgroundResource(R.drawable.custom_progress_dialog_animation);
            animation = (AnimationDrawable) la.getBackground();
            animation.start();
            mDialog.setCancelable(false);
            mDialog.show();
        }

        @Override
        protected ArrayList<SubSubCategoriesBean> doInBackground(String... params) {
            ArrayList<SubSubCategoriesBean> mSubSubCatItemListBeanArrayList = new ArrayList<>();
            ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.SUB_SUB_CAT_ITEM_PREF, getActivity().MODE_PRIVATE);
            SubSubCatItemListBean mSubSubCatItemListBean = mBaseCatSubCatCat.getObject("WarId:-" + selectedWarId + "--CatId:-" + selectedCategoryId, SubSubCatItemListBean.class);
            ArrayList<SubSubCategoriesBean> mTempSubSubCatItemListBeanArrayList = mSubSubCatItemListBean.getmSubSubCategoriesBeen();
            for (int i = 0; i < mTempSubSubCatItemListBeanArrayList.size(); i++) {
                if (mTempSubSubCatItemListBeanArrayList.get(i).getSubCategoryId().equalsIgnoreCase(params[0])) {
                    mSubSubCatItemListBeanArrayList.add(mTempSubSubCatItemListBeanArrayList.get(i));
                }
            }

            return mSubSubCatItemListBeanArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<SubSubCategoriesBean> mSubSubCatItemListBeanArrayList) {
            if (mSubSubCatItemListBeanArrayList.isEmpty()) {



              //  itemListSubSubCatAdapter = new ItemListSubSubCatAdapter(getActivity(), mSubSubCatItemListBeanArrayList, itemListSelectdSubSubCatTv, HomeFragItemList.this);
              //  mSubSubCatRecyclerView.setAdapter(itemListSubSubCatAdapter);

              //  itemListSubSubCatAdapter.notifyDataSetChanged();



                mItemListRecyclerView.setVisibility(View.GONE);

                mSubSubCatRecyclerView.setVisibility(View.GONE);

            //    itemListSelectdSubSubCatTv.setVisibility(View.GONE);

           //     mSubCatRecyclerView.setVisibility(View.GONE);

                //mItemListAdapter.notifyDataSetChanged();

                Toast.makeText(getActivity(), "No Item Found under this category !", Toast.LENGTH_SHORT).show();

            } else {
                try {
                    mItemListRecyclerView.setVisibility(View.VISIBLE);
                    mSubSubCatRecyclerView.setVisibility(View.VISIBLE);
                    mSubCatRecyclerView.setVisibility(View.VISIBLE);
               //     itemListSelectdSubSubCatTv.setVisibility(View.VISIBLE);
                    itemListSubSubCatAdapter = new ItemListSubSubCatAdapter(getActivity(), mSubSubCatItemListBeanArrayList,  HomeFragItemList.this);
                    mSubSubCatRecyclerView.setAdapter(itemListSubSubCatAdapter);

                }catch (IndexOutOfBoundsException e) {
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                } catch (Exception e) {
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                }

            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
        }
    }

    public class GetSubSubCatItemListAsyncTask extends AsyncTask<String, Void, JSONObject> {
        /* Dialog mDialog;

         @Override
         protected void onPreExecute() {
             mDialog = new Dialog(getActivity());
             mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
             mDialog.setContentView(R.layout.progress_dialog);
             if (showDialog) {
                 ((TextView) mDialog.findViewById(R.id.progressText)).setText("Loading items please wait...");
                 mDialog.setCancelable(false);
                 mDialog.show();
             }
         }*/
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(getActivity());
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            mDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Loading items please wait...");
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

                jsonObjectFromUrl = new HttpUrlConnectionJSONParser().getJsonObjectFromHttpUrlConnection(Constant.BASE_URL_ITEM_LIST + "?warid=" + params[0] + "&catid=" + params[1], null, HttpUrlConnectionJSONParser.Http.GET);



            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObjectFromUrl;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (jsonObject != null) {


                Log.e("Json", jsonObject.toString());


                try {
                    if (TextUtils.isNullOrEmpty(jsonObject.getJSONArray("ItemMasters").toString())) {
                        Toast.makeText(getActivity(), "Items not available on server", Toast.LENGTH_SHORT).show();
                    } else if (TextUtils.isNullOrEmpty(jsonObject.getJSONArray("SubsubCategories").toString())) {
                        Toast.makeText(getActivity(), "Items Category are not available on server", Toast.LENGTH_SHORT).show();
                    } else {
                        ArrayList<SubSubCategoriesBean> mSubSubCategoriesBeanArrayList = new ArrayList<>();
                        ArrayList<ItemList> mItemListArrayList = new ArrayList<>();

                        JSONArray mSubCategoriesJsonArray = jsonObject.getJSONArray("SubsubCategories");
                        for (int i = 0; i < mSubCategoriesJsonArray.length(); i++) {
                            String subSubCategoryId = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "SubsubCategoryid");
                            String subSubcategoryName = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "SubsubcategoryName");
                            String categoryid = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "Categoryid");
                            String SubCategoryId = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "SubCategoryId");
                            mSubSubCategoriesBeanArrayList.add(new SubSubCategoriesBean(subSubCategoryId, subSubcategoryName, categoryid, SubCategoryId));
                        }

                        JSONArray mItemsJsonArray = jsonObject.getJSONArray("ItemMasters");
                        for (int i = 0; i < mItemsJsonArray.length(); i++) {

                            //      Log.e("Json", mItemsJsonArray.toString());
                            String ItemId = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "ItemId");
                            String UnitId = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "UnitId");
                            String Categoryid = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "Categoryid");
                            String SubCategoryId = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "SubCategoryId");
                            String SubsubCategoryid = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "SubsubCategoryid");
//Item Name

                            String itemname;

// Check Locale
                            /*if(locale.equals(hindiLanguage.trim())) {
                                itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "HindiName");

                            } else {

                                itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "itemname");

                            }*/

// Check Sharedpreference

                       /*     if(languageCheck.equals("h")) {
                                itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "HindiName");

                            } else if(languageCheck.equals("e")) {

                                itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "itemname");

                            } else {

                                itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "itemname");

                            }
*/


//                            String itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "itemname");

//                              HindiName
                            //        String itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "HindiName");




//                            String itemname = "राई 100 ग्राम 10 पीस";



                            itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "itemname");

                            String UnitName = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "UnitName");
                            String PurchaseUnitName = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "PurchaseUnitName");
                            String price = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "price");

//                            String SellingUnitName = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "SellingUnitName");
                            // HindiName
                            String SellingUnitName = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "SellingUnitName");


                            //Utility.setStringSharedPreference(this, "JSon", json);
//
//U
                            String SellingSku = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "SellingSku");
                            String UnitPrice = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "UnitPrice");
                            String VATTax = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "VATTax");
                            String LogoUrl = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "LogoUrl");
                            String MinOrderQty = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "MinOrderQty");
                            String Discount = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "Discount");
                            String TotalTaxPercentage = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "TotalTaxPercentage");
                            String ItemNumber = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "ItemNumber");

                            String HindiName = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "HindiName");

                            String DpPoint = "";

                            String PromoPoint = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "promoPerItems");

//                            String PromoPoint = "0";


                            String MarginPoint = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "marginPoint");
                            String IsOffer = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "Isoffer");


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


                            mItemListArrayList.add(new ItemList(ItemId, UnitId, Categoryid, SubCategoryId, SubsubCategoryid, itemname, UnitName, PurchaseUnitName, price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, "", "",ItemNumber,IsOffer));


                        }
                        SubSubCatItemListBean mSubSubCatItemListBean = new SubSubCatItemListBean(mSubSubCategoriesBeanArrayList, mItemListArrayList,mItemListAllValue);
                        if (getActivity() != null) {
                            ComplexPreferences mSubSubCatItem = ComplexPreferences.getComplexPreferences(getActivity(), Constant.SUB_SUB_CAT_ITEM_PREF, getActivity().MODE_PRIVATE);
                            mSubSubCatItem.putObject("WarId:-" + selectedWarId + "--CatId:-" + selectedCategoryId, mSubSubCatItemListBean);
                            mSubSubCatItem.commit();

                            ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                            BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
                            if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                                ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList = new ArrayList<>();
                                ArrayList<SubCategoriesBean> mTempSubCategoriesBeanArrayList = mBaseCatSubCatBean.getmSubCategoriesBeanArrayList();
                                for (int i = 0; i < mTempSubCategoriesBeanArrayList.size(); i++) {
                                    if (mTempSubCategoriesBeanArrayList.get(i).getCategoryid().equalsIgnoreCase("" + selectedCategoryId)) {
                                        mSubCategoriesBeanArrayList.add(mTempSubCategoriesBeanArrayList.get(i));
                                    }
                                }
                            try {

                                ItemListSubCatAdapter mItemListSubCatAdapter = new ItemListSubCatAdapter(getActivity(), mSubCategoriesBeanArrayList, HomeFragItemList.this);
                                mSubCatRecyclerView.setAdapter(mItemListSubCatAdapter);

                            }catch (IndexOutOfBoundsException e) {
                                startActivity(new Intent(getActivity(), HomeActivity.class));
                            } catch (Exception e) {
                                startActivity(new Intent(getActivity(), HomeActivity.class));
                            }

                            } else {
                                Toast.makeText(getActivity(), "Items are not available", Toast.LENGTH_SHORT).show();
                                getFragmentManager().popBackStack();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



            else {
                Toast.makeText(getActivity(), "Improper response from server", Toast.LENGTH_SHORT).show();
            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }






        }
    }

    public class FilterItemsAsyncTask extends AsyncTask<String, Void, ArrayList<ItemList>> {
        /* Dialog mDialog;

         @Override
         protected void onPreExecute() {
             mDialog = new Dialog(getActivity());
             mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
             mDialog.setContentView(R.layout.progress_dialog);
             ((TextView) mDialog.findViewById(R.id.progressText)).setText("Filtering items list please wait...");
             mDialog.setCancelable(false);
             mDialog.show();
         }*/
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
        protected ArrayList<ItemList> doInBackground(String... params) {
            mItemListArrayList.clear();
            mItemListAllValue.clear();
            ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.SUB_SUB_CAT_ITEM_PREF, getActivity().MODE_PRIVATE);
            SubSubCatItemListBean mSubSubCatItemListBean = mBaseCatSubCatCat.getObject("WarId:-" + selectedWarId + "--CatId:-" + selectedCategoryId, SubSubCatItemListBean.class);
            ArrayList<ItemList> mTempItemListArrayList = mSubSubCatItemListBean.getmItemLists();



            for (int i = 0; i < mTempItemListArrayList.size(); i++) {

               // System.out.println("Parameter::"+params[0]);
               // System.out.println("SubId::"+mTempItemListArrayList.get(i).getSubsubCategoryid());

                if (mTempItemListArrayList.get(i).getSubsubCategoryid().equalsIgnoreCase(params[0])) {
                    mItemListArrayList.add(mTempItemListArrayList.get(i));
                  //  System.out.println("mItemListArrayList::"+mItemListArrayList);
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }
                    publishProgress();
                }
            }

            // New Code
            ArrayList<ItemList> mTempItemListArrayList1 = mSubSubCatItemListBean.getmItemRemoveLists();
            if(mTempItemListArrayList1.size()==0) {
            }else
            {
                for (int i = 0; i < mTempItemListArrayList1.size(); i++) {
                    if (mTempItemListArrayList1.get(i).getSubsubCategoryid().equalsIgnoreCase(params[0])) {
                        mItemListAllValue.add(mTempItemListArrayList1.get(i));
                       // System.out.println("AllRemove:::" + mItemListAllValue);
                        publishProgress();
                    }
                }
            }

            return mItemListArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<ItemList> mItemListArrayList) {

            //mItemListAdapter.notifyDataSetChanged();

            if (mItemListArrayList.isEmpty()) {

                mItemListAdapter.notifyDataSetChanged();

                mItemListAdapter.notifyItemRangeInserted(0, mItemListArrayList.size());
//
            //    mItemListRecyclerView.setAdapter(mItemListAdapter);


                Toast.makeText(getActivity(), "No Item Found under this category !!!", Toast.LENGTH_SHORT).show();
            } else {



                //mItemListAdapter.notifyDataSetChanged();
//                mItemListRecyclerView.setAdapter(mItemListAdapter);



                try {
                    mItemListAdapter.notifyDataSetChanged();
                    mItemListRecyclerView.setAdapter(mItemListAdapter);
                    mItemListAdapter.notifyItemRangeInserted(0, mItemListArrayList.size());
                }

                catch (ArrayIndexOutOfBoundsException e) {


                    getActivity().finish();

                    startActivity(new Intent(getActivity(), HomeFragItemList.class));

                }

                catch (Exception e) {

                    getActivity().finish();

                    startActivity(new Intent(getActivity(), HomeFragItemList.class));
                }

            }
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
           //Changed it as comment it
             mItemListAdapter.notifyDataSetChanged();
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


    public void callAqueryForItem(String cartId, String wardId) {


        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(getActivity(), Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);

        showLoading();

        String url = "http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/ssitem?warid=1&catid=17";

//        Constant.BASE_URL_ITEM_LIST + "?warid=" + wardId + "&catid=" + cartId


      //  Toast.makeText(getActivity(), Constant.BASE_URL_ITEM_LIST + "?ExecutiveId=" + wardId + "&catid=" + cartId, Toast.LENGTH_SHORT).show();


        //http://shopkiranamarketplace.moreyeahs.net/api/ssitem?ExecutiveId=14&catid=1

        Log.e("Ssitem", Constant.BASE_URL_ITEM_LIST + "?ExecutiveId="+mRetailerBean.getPeopleId()+"&catid=" + cartId);


        new AQuery(getActivity()).ajax(Constant.BASE_URL_ITEM_LIST + "?ExecutiveId="+mRetailerBean.getPeopleId()+"&catid=" + cartId, null, JSONObject.class, new AjaxCallback<JSONObject>(){

//        new AQuery(getActivity()).ajax("", null, JSONObject.class, new AjaxCallback<JSONObject>(){




            @Override
            public void callback(String url, JSONObject jsonObject, AjaxStatus status) {

                if (jsonObject != null) {


                 //   Toast.makeText(getActivity(), "Json "+jsonObject.toString(), Toast.LENGTH_SHORT).show();








                    Log.e("Json546", jsonObject.toString());



                    try {
                        if (TextUtils.isNullOrEmpty(jsonObject.getJSONArray("ItemMasters").toString())) {
                            Toast.makeText(getActivity(), "Items not available on server", Toast.LENGTH_SHORT).show();
                        } else if (TextUtils.isNullOrEmpty(jsonObject.getJSONArray("SubsubCategories").toString())) {
                            Toast.makeText(getActivity(), "Items Category are not available on server", Toast.LENGTH_SHORT).show();
                        } else {
                            ArrayList<SubSubCategoriesBean> mSubSubCategoriesBeanArrayList = new ArrayList<>();
                            ArrayList<ItemList> mItemListArrayList = new ArrayList<>();
                            ArrayList<ItemList>   mItemListAllValue=new ArrayList<ItemList>();
                            JSONArray mSubCategoriesJsonArray = jsonObject.getJSONArray("SubsubCategories");
                            for (int i = 0; i < mSubCategoriesJsonArray.length(); i++) {
                                String subSubCategoryId = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "SubsubCategoryid");
                                String subSubcategoryName = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "SubsubcategoryName");
                                String categoryid = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "Categoryid");
                                String SubCategoryId = isNullOrEmpty(mSubCategoriesJsonArray.getJSONObject(i), "SubCategoryId");
                                mSubSubCategoriesBeanArrayList.add(new SubSubCategoriesBean(subSubCategoryId, subSubcategoryName, categoryid, SubCategoryId));
                            }

                            JSONArray mItemsJsonArray = jsonObject.getJSONArray("ItemMasters");
                            for (int i = 0; i < mItemsJsonArray.length(); i++) {








                                /*
                                *
                                *{
                                    ItemId: 2083,
                                    Categoryid: 17,
                                    SubCategoryId: 72,
                                    SubsubCategoryid: 78,
                                    itemname: "Talati Maida 500gm 5Pc",
                                    HindiName: "null",
                                    price: 40,
                                    SellingUnitName: "Talati Maida 500gm 5Pc",
                                    SellingSku: "01AC11710115",
                                    UnitPrice: 15.000119999999999,
                                    VATTax: 0,
                                    LogoUrl: "01AC11710115",
                                    MinOrderQty: 5,
                                    Discount: 0,
                                    TotalTaxPercentage: 5,
                                    marginPoint: 0,
                                    promoPerItems: null
                                    },
                                *
                                * */



                                //      Log.e("Json", mItemsJsonArray.toString());
                                String ItemId = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "ItemId");


                            //    String UnitId = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "UnitId");



                                String Categoryid = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "Categoryid");
                                String SubCategoryId = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "SubCategoryId");
                                String SubsubCategoryid = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "SubsubCategoryid");
//Item Name

                                String itemname;

// Check Locale
                            /*if(locale.equals(hindiLanguage.trim())) {
                                itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "HindiName");

                            } else {

                                itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "itemname");

                            }*/

// Check Sharedpreference

                       /*     if(languageCheck.equals("h")) {
                                itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "HindiName");

                            } else if(languageCheck.equals("e")) {

                                itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "itemname");

                            } else {

                                itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "itemname");

                            }
*/


//                            String itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "itemname");
//                                        HindiName
                                //        String itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "HindiName");




//                            String itemname = "राई 100 ग्राम 10 पीस";



                                itemname = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "itemname");

                             //   String UnitName = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "UnitName");

                              //  String PurchaseUnitName = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "PurchaseUnitName");
                                String price = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "price");

//                            String SellingUnitName = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "SellingUnitName");
                                // HindiName
                                String SellingUnitName = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "SellingUnitName");


                                //Utility.setStringSharedPreference(this, "JSon", json);
//
//U
                                String SellingSku = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "SellingSku");
                                String UnitPrice = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "UnitPrice");
                                String VATTax = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "VATTax");
                                String LogoUrl = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "LogoUrl");
                                String MinOrderQty = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "MinOrderQty");
                                String Discount = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "Discount");
                                String TotalTaxPercentage = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "TotalTaxPercentage");


                                String HindiName = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "HindiName");

                                String DpPoint = "";

                                String PromoPoint = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "promoPerItems");

//                            String PromoPoint = "0";
                                String MarginPoint = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "marginPoint");
                                String WarehouseId = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "WarehouseId");
                                String CompanyId = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "CompanyId");
                                String ItemNumber = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "ItemNumber");
                                String IsOffer = isNullOrEmpty(mItemsJsonArray.getJSONObject(i), "Isoffer");



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

/*  else if(MarginPoint.equals("0.0")) {

                                    mp = 0;
                                }*/
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


                             //   mItemListArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, WarehouseId, CompanyId,ItemNumber));
                                Collections.sort(mItemListAllValue, new ComparatorOfNumericString());
                                mItemListAllValue.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, WarehouseId, CompanyId,ItemNumber,IsOffer));
                                if(mItemListArrayList.size()==0)
                                {
                                    mItemListArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, WarehouseId, CompanyId,ItemNumber,IsOffer));

                                }else
                                {
                                    boolean ispresent=false;
                                    for (int j = 0; j <mItemListArrayList.size() ; j++) {
                                        if(mItemListArrayList.get(j).getItemNumber().equalsIgnoreCase(ItemNumber))
                                        {
                                            ispresent=true;
                                            break;
                                        }
                                    }
                                    if(!ispresent)
                                    {

                                        mItemListArrayList.add(new ItemList(ItemId, "UnitId", Categoryid, SubCategoryId, SubsubCategoryid, itemname, "UnitName", "PurchaseUnitName", price, SellingUnitName, SellingSku, UnitPrice, VATTax, LogoUrl, MinOrderQty, Discount, TotalTaxPercentage, HindiName, DpPoint, PromoPoint, MarginPoint, WarehouseId, CompanyId,ItemNumber,IsOffer));

                                    }else
                                    {

                                    }
                                }
                            }
                            SubSubCatItemListBean mSubSubCatItemListBean = new SubSubCatItemListBean(mSubSubCategoriesBeanArrayList, mItemListArrayList,mItemListAllValue);
                            if (getActivity() != null) {
                                ComplexPreferences mSubSubCatItem = ComplexPreferences.getComplexPreferences(getActivity(), Constant.SUB_SUB_CAT_ITEM_PREF, getActivity().MODE_PRIVATE);
                                mSubSubCatItem.putObject("WarId:-" + selectedWarId + "--CatId:-" + selectedCategoryId, mSubSubCatItemListBean);
                                mSubSubCatItem.commit();

                                ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(getActivity(), Constant.BASECAT_CAT_SUBCAT_PREF, getActivity().MODE_PRIVATE);
                                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
                                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                                    ArrayList<SubCategoriesBean> mSubCategoriesBeanArrayList = new ArrayList<>();
                                    ArrayList<SubCategoriesBean> mTempSubCategoriesBeanArrayList = mBaseCatSubCatBean.getmSubCategoriesBeanArrayList();
                                    for (int i = 0; i < mTempSubCategoriesBeanArrayList.size(); i++) {
                                        if (mTempSubCategoriesBeanArrayList.get(i).getCategoryid().equalsIgnoreCase("" + selectedCategoryId)) {
                                            mSubCategoriesBeanArrayList.add(mTempSubCategoriesBeanArrayList.get(i));
                                        }
                                    }

                                   try {
                                       ItemListSubCatAdapter mItemListSubCatAdapter = new ItemListSubCatAdapter(getActivity(), mSubCategoriesBeanArrayList, HomeFragItemList.this);
                                       mSubCatRecyclerView.setAdapter(mItemListSubCatAdapter);
                                   } catch (IndexOutOfBoundsException e) {
                                       startActivity(new Intent(getActivity(), HomeActivity.class));
                                   } catch (Exception e) {
                                       startActivity(new Intent(getActivity(), HomeActivity.class));
                                   }

                                    if (mDialog.isShowing()) {
                                        animation.stop();
                                        mDialog.dismiss();
                                    }


                                } else {
                                    Toast.makeText(getActivity(), "Items are not available", Toast.LENGTH_SHORT).show();
                                    getFragmentManager().popBackStack();


                                    if (mDialog.isShowing()) {
                                        animation.stop();
                                        mDialog.dismiss();
                                    }


                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



            } else {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    Toast.makeText(getActivity(), "Please try again!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    public class ComparatorOfNumericString implements Comparator<ItemList> {
        @SuppressLint("NewApi")
        @Override
        public int compare(ItemList lhs, ItemList rhs) {
            int i1 = Integer.parseInt(lhs.getMinOrderQty());
            int i2 = Integer.parseInt(rhs.getMinOrderQty());
            return Integer.compare(i1, i2);
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