package com.example.user.mp_salesperson;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.customClasses.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class MyWallet extends AppCompatActivity {
    String urlRemote = "";
    TextView amountTv;
    Dialog mDialog;
    AnimationDrawable animation;
    String beatSkcodeCId = "";
    String beatSkcode = "";
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wallet);
        context = this;
        amountTv = (TextView) findViewById(R.id.amount);
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);
        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWallet.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyWallet.this.finish();

                startActivity(new Intent(MyWallet.this, HomeActivity.class));
            }
        });
        beatSkcode = Utility.getStringSharedPreferences(context, "BeatSkCode");
        beatSkcodeCId = Utility.getStringSharedPreferences(context, "BeatSkCodeCId");
        urlRemote = Constant.BASE_URL_MY_WALLET + "?CustomerId=" + beatSkcodeCId;


        if (!beatSkcode.isEmpty()) {
            //   etSkcode.setText(beatSkcode);
        } else {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Sk Code")
                    .setMessage("Would you like to chose a Sk Code from the list?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //finish();

                            MyWallet.this.finish();
                            startActivity(new Intent(MyWallet.this, DaysBidActivity.class));
                        }

                    })
                    .setCancelable(false)
                    .show();
        }
        ((TextView) findViewById(R.id.name)).setText("" + Utility.getStringSharedPreferences(context, "BeatSkCodeCName"));

        ((ImageView) toolbar.findViewById(R.id.nav_refresh_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAquery();
            }
        });
        ((ImageView) toolbar.findViewById(R.id.my_order_more_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.my_order_more_iv);
                PopupMenu popup = new PopupMenu(MyWallet.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();


//                inflate.inflate(R.menu.my_order_option_menu, popup.getMenu());
                inflate.inflate(R.menu.home, popup.getMenu());


                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(MyWallet.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(MyWallet.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(MyWallet.this, MyWallet.class));
                            return true;
                        } else if (id == R.id.myDial) {
                            startActivity(new Intent(MyWallet.this, MyDialListActivity.class));
                            return true;
                        } else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(MyWallet.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(MyWallet.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(MyWallet.this, RequestBrandActivity.class));
                            return true;
                        } else if (id == R.id.action_feedback) {
                            startActivity(new Intent(MyWallet.this, FeedbackActivity.class));
                            return true;
                        } else if (id == R.id.SignUp) {
                            startActivity(new Intent(MyWallet.this, CustomerRegistration.class));
                            //   HomeActivity.this.finish();
                            return true;
                        } else if (id == R.id.action_logout) {

                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();
                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();
                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);
                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();
                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);
                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();
                            Utility.setStringSharedPreference(MyWallet.this, "Bidmonday", "");
                            Utility.setStringSharedPreference(MyWallet.this, "Bidtuesday", "");
                            Utility.setStringSharedPreference(MyWallet.this, "Bidwednesday", "");
                            Utility.setStringSharedPreference(MyWallet.this, "Bidthursday", "");
                            Utility.setStringSharedPreference(MyWallet.this, "Bidfriday", "");
                            Utility.setStringSharedPreference(MyWallet.this, "Bidsaturday", "");
                            Utility.setStringSharedPreference(MyWallet.this, "Bidsunday", "");
                            Utility.setStringSharedPreference(MyWallet.this, "BeatSkCode", "");
                            Utility.setStringSharedPreference(MyWallet.this, "ItemQJson", "");
                            Utility.setStringSharedPreference(MyWallet.this, "CompanyId", "");
                            clearCartData();
                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();
                            MyWallet.this.finish();
                            startActivity(new Intent(MyWallet.this, LoginActivity_Nav.class));
                            return true;
                        } else if (id == R.id.setting) {
                            startActivity(new Intent(MyWallet.this, SettingActivity.class));
                            //   HomeActivity.this.finish();
                            return true;
                        } else if (id == R.id.mysales) {
                            startActivity(new Intent(MyWallet.this, MySalesActivity.class));
                            return true;
                        } else if (id == R.id.mybid) {
                            startActivity(new Intent(MyWallet.this, DaysBidActivity.class));
                            return true;
                        } else if (id == R.id.redeem_point) {
                            startActivity(new Intent(MyWallet.this, ReedemPointActivity.class));
                            return true;
                        } else if (id == R.id.reward_point_menu) {
                            startActivity(new Intent(MyWallet.this, RewardItemActivity.class));
                            return true;
                        } else if (id == R.id.action_task) {
                            startActivity(new Intent(MyWallet.this, ActionTaskMultiActivity.class));
                            return true;
                        } else
                            return false;
                    }
                });
            }
        });
        callAquery();
    }

    public void callAquery() {
        showLoading();
        new AQuery(getApplicationContext()).ajax(urlRemote, null, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {
                if (json == null) {
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }
                    Toast.makeText(MyWallet.this, "Please try again", Toast.LENGTH_SHORT).show();
                } else {
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }
                    try {
                        JSONObject jsonObject = json.getJSONObject("wallet");
                        JSONObject rewardJson = json.getJSONObject("reward");
                        String amount = jsonObject.getString("TotalAmount");
                        amountTv.setText("Total Amount : " + amount);
                        ((TextView) findViewById(R.id.total_point)).setText("Total point : " + rewardJson.getString("TotalPoint"));

                        ((TextView) findViewById(R.id.earning_point)).setText("Earning Point : " + rewardJson.getString("EarningPoint"));

                        ((TextView) findViewById(R.id.used_point)).setText("Used Point : " + rewardJson.getString("UsedPoint"));

                        ((TextView) findViewById(R.id.milestone_point)).setText("Milestone Point : " + rewardJson.getString("MilestonePoint"));

                    } catch (JSONException e) {
                        e.printStackTrace();

                        Toast.makeText(MyWallet.this, "Error Please try again", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
    public void showLoading() {
        mDialog = new Dialog(MyWallet.this);
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
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }
}
