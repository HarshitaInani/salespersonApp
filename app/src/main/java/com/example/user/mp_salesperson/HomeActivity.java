package com.example.user.mp_salesperson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import com.Amitlibs.utils.ComplexPreferences;
import com.example.user.mp_salesperson.adapters.HomeActivityNavExpandableListAdapter;
import com.example.user.mp_salesperson.bean.CartItemBean;
import com.example.user.mp_salesperson.bean.CartItemInfo;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.BaseCatBean;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.CategoryBean;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.ItemList;
import com.example.user.mp_salesperson.customClasses.Utility;
import com.example.user.mp_salesperson.fragment.HomeFragItemList;
import com.example.user.mp_salesperson.fragment.HomeFragment;
import com.example.user.mp_salesperson.fragment.SearchFragItemList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FrameLayout mFrameLayout;
    android.support.v4.app.Fragment fragment;
    android.support.v4.app.FragmentManager fragmentManager;
    NavigationView navigationView;
    ExpandableListView mExpandableListView;
    CartItemBean mCartItem;
    public boolean shouldCallCatMenuApi = true;
    ImageView toolBarSearchIv;
    int lastExpandedPosition = -1;
    public ArrayList<BaseCatBean> listDataHeaderGlobal;
    public HashMap<BaseCatBean, ArrayList<CategoryBean>> listDataChildGlobal;
    DrawerLayout drawer;
    SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Context context = HomeActivity.this.getApplicationContext();

        Resources res = getLocalizedResources(context,new Locale("hi"));
        String s = res.getString(R.string.category);



    //    Utility.setStringSharedPreference(context,"BeatSkCode","");


        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ((ImageView) toolbar.findViewById(R.id.nav_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });


       // Toast.makeText(context, ""+ FirebaseInstanceId.getInstance().getToken().toString(), Toast.LENGTH_SHORT).show();

        ImageView barCode=(ImageView)toolbar.findViewById(R.id.barcode_scan);
        barCode.setColorFilter(HomeActivity.this.getResources().getColor(R.color.colorPrimary));
        barCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(HomeActivity.this,BarcodeScanItem.class);
                startActivity(i);
            }
        });
        ((ImageView) toolbar.findViewById(R.id.home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // popVisibleFragment();
                fragment = android.support.v4.app.Fragment.instantiate(HomeActivity.this, HomeFragment.class.getName());
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "HomeFragment").commit();

            }
        });

        toolBarSearchIv = ((ImageView) toolbar.findViewById(R.id.nav_search_iv));

        toolBarSearchIv.setVisibility(View.VISIBLE);

        ((ImageView) toolbar.findViewById(R.id.nav_search_iv)).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View view) {
                // popVisibleFragment();
                fragment = android.support.v4.app.Fragment.instantiate(HomeActivity.this, SearchFragItemList.class.getName());
                fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "SearchFragItemList").commit();
              //  ((ImageView) toolbar.findViewById(R.id.nav_search_iv)).setVisibility(View.GONE);


            }
        });

        ((ImageView) toolbar.findViewById(R.id.home_more_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.home_more_iv);
                PopupMenu popup = new PopupMenu(HomeActivity.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();
                inflate.inflate(R.menu.home, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(HomeActivity.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(HomeActivity.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        }else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(HomeActivity.this, MyWallet.class));
                            return true;
                        }
                        else if (id == R.id.myDial) {
                            startActivity(new Intent(HomeActivity.this, MyDialListActivity.class));
                            return true;
                        }
                        else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(HomeActivity.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(HomeActivity.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_report) {
                            startActivity(new Intent(HomeActivity.this, ReportActivity.class));
                            return true;
                        }else if (id == R.id.action_request_item) {
                            startActivity(new Intent(HomeActivity.this, RequestBrandActivity.class));
                            return true;
                        } else if (id == R.id.action_feedback) {
                            startActivity(new Intent(HomeActivity.this, FeedbackActivity.class));
                            return true;

                        }
                        else if (id == R.id.SignUp) {
                            startActivity(new Intent(HomeActivity.this, CustomerRegistration.class));
                            return true;
                        }
                        else if (id == R.id.action_logout) {

                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();

                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();

                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();

                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();

                            ComplexPreferences mRetailerBeanPref5 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.POPULAR_BRANDS_PREF1, MODE_PRIVATE);
                            mRetailerBeanPref5.clear();
                            mRetailerBeanPref5.commit();
                            ComplexPreferences mRetailerBeanPref6 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.POPULAR_BRANDS_PREF2, MODE_PRIVATE);
                            mRetailerBeanPref6.clear();
                            mRetailerBeanPref6.commit();
                            ComplexPreferences mRetailerBeanPref7 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.APP_PROMOTION_PREF, MODE_PRIVATE);
                            mRetailerBeanPref7.clear();
                            mRetailerBeanPref7.commit();
                            ComplexPreferences mRetailerBeanPref8 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.NEWLY_ADDED_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref8.clear();
                            mRetailerBeanPref8.commit();
                            ComplexPreferences mRetailerBeanPref9 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.ALL_TOP_ADDED_PREF, MODE_PRIVATE);
                            mRetailerBeanPref9.clear();
                            mRetailerBeanPref9.commit();
                            ComplexPreferences mRetailerBeanPref10 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.TODAY_DHAMAKA_PREF, MODE_PRIVATE);
                            mRetailerBeanPref10.clear();
                            mRetailerBeanPref10.commit();
                            ComplexPreferences mRetailerBeanPref11 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.EMPTY_STOCK_PREF, MODE_PRIVATE);
                            mRetailerBeanPref11.clear();
                            mRetailerBeanPref11.commit();
                            ComplexPreferences mRetailerBeanPref12 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.BULK_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref12.clear();
                            mRetailerBeanPref12.commit();
                            ComplexPreferences mRetailerBeanPref13 = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.MOST_SELLED_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref13.clear();
                            mRetailerBeanPref13.commit();

                            Utility.setStringSharedPreference(HomeActivity.this, "Bidmonday", "");
                            Utility.setStringSharedPreference(HomeActivity.this, "Bidtuesday", "");
                            Utility.setStringSharedPreference(HomeActivity.this, "Bidwednesday", "");
                            Utility.setStringSharedPreference(HomeActivity.this, "Bidthursday", "");
                            Utility.setStringSharedPreference(HomeActivity.this, "Bidfriday", "");
                            Utility.setStringSharedPreference(HomeActivity.this, "Bidsaturday", "");
                            Utility.setStringSharedPreference(HomeActivity.this, "Bidsunday", "");
                            Utility.setStringSharedPreference(HomeActivity.this,"BeatSkCode","");
                            Utility.setStringSharedPreference(HomeActivity.this, "ItemQJson" ,"");
                            Utility.setStringSharedPreference(HomeActivity.this, "CompanyId", "");



                            clearCartData();
                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();


                            HomeActivity.this.finish();


                            startActivity(new Intent(HomeActivity.this, LoginActivity_Nav.class));
                            return true;
                        }

                        else if (id == R.id.setting) {
                            startActivity(new Intent(HomeActivity.this, SettingActivity.class));
                         //   HomeActivity.this.finish();
                            return true;
                        }



                        else if (id == R.id.mysales) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(HomeActivity.this, MySalesActivity.class));

                        //    HomeActivity.this.finish();
                            return true;
                        }


                        else if (id == R.id.mybid) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(HomeActivity.this, DaysBidActivity.class));

                       //     HomeActivity.this.finish();

                            //HomeActivity.this.finish();
                            return true;
                        }

                        else if (id == R.id.redeem_point) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(HomeActivity.this, ReedemPointActivity.class));

                 //           HomeActivity.this.finish();
                            return true;
                        }


                        else if (id == R.id.action_task) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(HomeActivity.this, ActionTaskMultiActivity.class));

                            //           HomeActivity.this.finish();
                            return true;
                        }



                        else if (id == R.id.action_my_news) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(HomeActivity.this, MyNews.class));

                            //           HomeActivity.this.finish();
                            return true;
                        }




                        else if (id == R.id.reward_point_menu) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(HomeActivity.this, RewardItemActivity.class));

                       //     HomeActivity.this.finish();
                            return true;
                        }
         else
                            return false;
                    }
                });
            }
        });
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        mExpandableListView = (ExpandableListView) findViewById(R.id.navigationmenu);


        mFrameLayout = (FrameLayout) findViewById(R.id.content_frame);
        fragmentManager = HomeActivity.this.getSupportFragmentManager();
    }

    @NonNull
    public Resources getLocalizedResources(Context context, Locale desiredLocale) {
        Configuration conf = context.getResources().getConfiguration();
        conf = new Configuration(conf);
        conf.setLocale(desiredLocale);
        Context localizedContext = context.createConfigurationContext(conf);
        localizedContext.getResources().updateConfiguration(conf, null);
        return localizedContext.getResources();
    }

    public void setupNavigationView(ArrayList<BaseCatBean> listDataHeader, final HashMap<BaseCatBean, ArrayList<CategoryBean>> listDataChild) {
        if (navigationView != null) {
            HomeActivityNavExpandableListAdapter mMenuAdapter = new HomeActivityNavExpandableListAdapter(this, listDataHeader, listDataChild, mExpandableListView);
            // setting list adapter
            mExpandableListView.setAdapter(mMenuAdapter);
            mExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    if (lastExpandedPosition != -1
                            && groupPosition != lastExpandedPosition) {
                        mExpandableListView.collapseGroup(lastExpandedPosition);
                    }
                    lastExpandedPosition = groupPosition;
                }
            });

            mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @SuppressLint("RestrictedApi")
                @Override
                public boolean onChildClick(ExpandableListView expandableListView, View view, int i, int i1, long l) {
//                    Toast.makeText(HomeActivity.this, "Child Click listner clicked", Toast.LENGTH_SHORT).show();
                    if (listDataHeaderGlobal != null && listDataChildGlobal != null) {
                        BaseCatBean mSelectedBaseCatBean = listDataHeaderGlobal.get(i);
                        ArrayList<CategoryBean> mSelectedCategoryBeanArrayList = listDataChildGlobal.get(mSelectedBaseCatBean);
                        CategoryBean mSelectCategoryBean = mSelectedCategoryBeanArrayList.get(i1);
                        Fragment fragment = android.support.v4.app.Fragment.instantiate(HomeActivity.this, HomeFragItemList.class.getName());
                        Bundle args = new Bundle();
                        args.putInt("selectedCategoryId", Integer.parseInt(mSelectCategoryBean.getCategoryid()));

//                        args.putInt("selectedWarId", Integer.parseInt(mSelectCategoryBean.getWarehouseid()));
                        args.putInt("selectedWarId", 1);
                         fragment.setArguments(args);

                        Fragment myFragment = getVisibleFragment();
                        if (myFragment.getTag().equalsIgnoreCase("HomeFragment")) {
                            fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();
                        } else {
                            fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();
                        }
                    } else {
                        Toast.makeText(HomeActivity.this, "Unable to process please try again", Toast.LENGTH_SHORT).show();
                    }
                    if (drawer != null)
                        drawer.closeDrawer(Gravity.LEFT);
                    return true;
                }
            });
            mExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                    //Toast.makeText(HomeActivity.this, "Group Click listner clicked", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        fragment = android.support.v4.app.Fragment.instantiate(this, HomeFragment.class.getName());
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment, "HomeFragment").commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment myFragment = getVisibleFragment();
            if (myFragment != null && myFragment.getTag().equalsIgnoreCase("HomeFragment")) {
                new AlertDialog.Builder(this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Closing Activity")
                        .setMessage("Closing Application will clear the cart items...Click yes to continue")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utility.setStringSharedPreference(HomeActivity.this,"BeatSkCode","");
                                finish();

                                finishAffinity();


                               // Process.killProcess(Process.myPid());

                                //android.os.Process.killProcess(android.os.Process.myPid());

/*

                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);

*/
/*
                                Intent intent = new Intent(Intent.ACTION_MAIN);
                                intent.addCategory(Intent.CATEGORY_HOME);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);/*//***Change Here***
                                startActivity(intent);
                             //   finish();
                                System.exit(0);

                                */


                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            } /*else if(myFragment != null && myFragment.getTag().equalsIgnoreCase("HomeFragItemList")){}*/ else {
                super.onBackPressed();

                toolBarSearchIv.setVisibility(View.VISIBLE);
            }

        }
    }

    public Fragment getVisibleFragment() {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;
    }

    public void popVisibleFragment() {
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragmentManager.popBackStack();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public CartItemBean getCartItem() {
        if (mCartItem == null) {
            ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0, "", "");
            }
        }
        return mCartItem;
    }

    public String addItemInCartItemArrayList(String itemId, int qty, double itemUnitPrice, ItemList selectedItem, double deliveryCharges, double totalDp, String warehouseId, String companyId,double Price) {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
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
 //mCartItem.getmCartItemInfos().remove(foundItemPosition);


            } else {
                mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp, warehouseId, companyId,Price));
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
            mCartItem.getmCartItemInfos().add(new CartItemInfo(itemId, qty, itemUnitPrice, selectedItem, totalDp, warehouseId, companyId,Price));
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
        mCartItem.setTotalPrice(tempTotalPrice);
        mCartItem.setTotalQuantity(tempTotalQuantity);
        mCartItem.setSavingAmount(saveAmount);
        mCartItem.setTotalItemPrice(TotalPrice);
        mCartItem.setTotalDpPoints(tempTotalDpPoint);

        mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
        mCartItemArraylistPref.commit();


      //  mCartItemArraylistPref.
        return status;


    }



    public  void removeData() {

        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);

       // mCartItemArraylistPref.re


    }




    public void removeItemfromCart(String itemId) {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);


      //  Toast.makeText(HomeActivity.this, "item ID"+itemId, Toast.LENGTH_SHORT).show();


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
             //   Toast.makeText(HomeActivity.this, "Item found"+itemId+"\n"+mCartItem.getTotalQuantity(), Toast.LENGTH_SHORT).show();

                mCartItem.getmCartItemInfos().remove(foundItemPosition);
                mCartItemArraylistPref.putObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, mCartItem);
                mCartItemArraylistPref.commit();




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

    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(HomeActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();

    }
    public boolean showPopup(int totalAmount) {
        //final MediaPlayer mp = MediaPlayer.create(HomeActivity.this, R.raw.coin);
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

}
