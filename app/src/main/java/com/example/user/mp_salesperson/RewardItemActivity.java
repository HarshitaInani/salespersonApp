package com.example.user.mp_salesperson;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.example.user.mp_salesperson.adapters.RewardItemAdapter;
import com.example.user.mp_salesperson.customClasses.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RewardItemActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RewardItemAdapter adapter;
    Context context;
    int count;

    Dialog mDialog;
    AnimationDrawable animation;



    String noUse = "";
    String beatSkcode = "";

    //String


    String beatSkcodeCId = "";

    String urlWallet= "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_item);



        context = this;
    //    String url = "http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/RewardItem";


        String url = Constant.BASE_URL_REWARD_ITEMS;


        //((TextView) findViewById(R.id.customer_name)).setText("Name");


        recyclerView = (RecyclerView) findViewById(R.id.recyeler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));






        beatSkcode = Utility.getStringSharedPreferences(context, "BeatSkCode");

        beatSkcodeCId = Utility.getStringSharedPreferences(context, "BeatSkCodeCId");


        urlWallet= Constant.BASE_URL_MY_WALLET+"?CustomerId="+beatSkcodeCId;

        //   Toast.makeText(context, ""+beatSkcodeCId, Toast.LENGTH_SHORT).show();
        ((TextView) findViewById(R.id.customer_name)).setText(Utility.getStringSharedPreferences(context, "BeatSkCodeCName"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RewardItemActivity.this.finish();
            }
        });

        ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RewardItemActivity.this.finish();
            }
        });




        ((ImageView) toolbar.findViewById(R.id.nav_refresh_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  callAquery();


                startActivity(new Intent(RewardItemActivity.this, RewardItemActivity.class));
                RewardItemActivity.this.finish();

            }


        });


// Menu

        /*
        ((ImageView) toolbar.findViewById(R.id.my_order_more_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.my_order_more_iv);
                PopupMenu popup = new PopupMenu(RewardItemActivity.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();
                inflate.inflate(R.menu.my_order_option_menu, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_profile) {
                            startActivity(new Intent(RewardItemActivity.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_orders) {
                            startActivity(new Intent(RewardItemActivity.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(RewardItemActivity.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(RewardItemActivity.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(RewardItemActivity.this, RequestBrandActivity.class));
                            return true;
                        } else if (id == R.id.action_feedback) {
                            startActivity(new Intent(RewardItemActivity.this, FeedbackActivity.class));
                            return true;
                        }



                        else if (id == R.id.action_settings) {

                            startActivity(new Intent(RewardItemActivity.this, SettingActivity.class));
                            RewardItemActivity.this.finish();
                            return true;
                        }



                        else if (id == R.id.action_mysales) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(RewardItemActivity.this, MySalesActivity.class));

                            RewardItemActivity.this.finish();
                            return true;
                        }


                        else if (id == R.id.action_mybeat) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(RewardItemActivity.this, DaysBidActivity.class));

                            RewardItemActivity.this.finish();
                            return true;
                        }






                        else if (id == R.id.action_logout) {
                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MyWallet.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();
                            startActivity(new Intent(MyWallet.this, LoginActivity_Nav.class));
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                finishAffinity();
                            } else {
                                MyWallet.this.finish();
                            }
                            return true;
                        } else
                            return false;
                    }
                });
            }
        });



*/




        ((ImageView) toolbar.findViewById(R.id.my_order_more_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.my_order_more_iv);
                PopupMenu popup = new PopupMenu(RewardItemActivity.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();


//                inflate.inflate(R.menu.my_order_option_menu, popup.getMenu());
                inflate.inflate(R.menu.home, popup.getMenu());


                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(RewardItemActivity.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(RewardItemActivity.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        }else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(RewardItemActivity.this, MyWallet.class));
                            return true;
                        }  else if (id == R.id.myDial) {
                            startActivity(new Intent(RewardItemActivity.this, MyDialListActivity.class));
                            return true;
                        }
                        else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(RewardItemActivity.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(RewardItemActivity.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(RewardItemActivity.this, RequestBrandActivity.class));
                            return true;
                        } else if (id == R.id.action_feedback) {
                            startActivity(new Intent(RewardItemActivity.this, FeedbackActivity.class));
                            return true;



                        } else if (id == R.id.action_logout) {
                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(RewardItemActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();

                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();



                            startActivity(new Intent(RewardItemActivity.this, LoginActivity_Nav.class));
                            RewardItemActivity.this.finish();
                            return true;
                        }

                        else if (id == R.id.setting) {
                            startActivity(new Intent(RewardItemActivity.this, SettingActivity.class));
                            //   HomeActivity.this.finish();
                            return true;
                        }



                        else if (id == R.id.mysales) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(RewardItemActivity.this, MySalesActivity.class));

                            //    HomeActivity.this.finish();
                            return true;
                        }


                        else if (id == R.id.mybid) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(RewardItemActivity.this, DaysBidActivity.class));

                            //     HomeActivity.this.finish();

                            //HomeActivity.this.finish();
                            return true;
                        }

                        else if (id == R.id.redeem_point) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(RewardItemActivity.this, ReedemPointActivity.class));

                            //           HomeActivity.this.finish();
                            return true;
                        }

                        else if (id == R.id.reward_point_menu) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(RewardItemActivity.this, RewardItemActivity.class));

                            //     HomeActivity.this.finish();
                            return true;
                        }




                        else
                            return false;
                    }
                });
            }
        });







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

                            RewardItemActivity.this.finish();
                            startActivity(new Intent(RewardItemActivity.this, DaysBidActivity.class));
                        }

                    })
                    .setCancelable(false)
                    .show();


        }




        ((TextView) findViewById(R.id.title_toolbar)).setText("My Dream");


        new AQuery(getApplicationContext()).ajax(url, null, JSONArray.class, new AjaxCallback<JSONArray>(){


            @Override
            public void callback(String url, JSONArray jsonArray, AjaxStatus status) {

                showLoading();

                if (jsonArray != null) {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }



                    JSONArray jsonArrayQ = new JSONArray();
                    for (int i =0; i <jsonArray.length(); i++) {

                        JSONObject jsonObjectQ = new JSONObject();

                        try {
                            jsonObjectQ.put("quantity", "0");
                            jsonArrayQ.put(i, jsonObjectQ);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }



                    //      Toast.makeText(RewardItemActivity.this, ""+jsonArray.toString(), Toast.LENGTH_SHORT).show();

                    adapter = new RewardItemAdapter(context, jsonArray, jsonArrayQ, jsonArray.length());
                //    adapter.setHasStableIds(true);
                    recyclerView.setAdapter(adapter);

                } else {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    Toast.makeText(RewardItemActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();


                }

            }
        });


       // check();

        getWalletData();

    }

    public void check() {

      //  String url = "http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/RewardItem";


        String url = Constant.BASE_URL_REWARD_ITEMS;




        new AQuery(getApplicationContext()).ajax(url, null, JSONArray.class, new AjaxCallback<JSONArray>() {

            @Override
            public void callback(String url, JSONArray json, AjaxStatus status) {



                if (json == null) {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }


                    Toast.makeText(RewardItemActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();


                } else {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    Toast.makeText(RewardItemActivity.this, "Check json "+json.toString(), Toast.LENGTH_SHORT).show();



                    //   Toast.makeText(SkCodeActivity.this, json.toString(), Toast.LENGTH_SHORT).show();

                    //     Toast.makeText(MyWallet.this, json.toString(), Toast.LENGTH_SHORT).show();



                }


            }
        });

    }


    @Override
    public void onBackPressed() {
        //  super.onBackPressed();

        // startActivity(new Intent(context, DaysBidActivity.class));

        RewardItemActivity.this.finish();
    }


    public void showLoading() {
        mDialog = new Dialog(RewardItemActivity.this);
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


    public void setPointValue() {
      //  Toast.makeText(context, "Method in activity", Toast.LENGTH_SHORT).show();
    }


    public void getWalletData() {


        new AQuery(getApplicationContext()).ajax(urlWallet, null, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {



                if (json == null) {



                    Toast.makeText(RewardItemActivity.this, "Please try again", Toast.LENGTH_SHORT).show();


                } else {
                 //   Toast.makeText(RewardItemActivity.this, "Wallet json "+json.toString(), Toast.LENGTH_SHORT).show();


                    try {

                    //    Toast.makeText(RewardItemActivity.this, "Wallet json "+json.toString(), Toast.LENGTH_SHORT).show();
                        JSONObject  jsonObject = json.getJSONObject("wallet");

                        JSONObject rewardJson = json.getJSONObject("reward");

                        String amount = jsonObject.getString("TotalAmount");

                        ((TextView) findViewById(R.id.total_point)).setText("Total point "+jsonObject.getString("TotalAmount"));

                        Utility.setStringSharedPreference(context, "WalletTotal", jsonObject.getString("TotalAmount"));

                    //    amountTv.setText("Total Amount "+amount);

                /*        ((TextView) findViewById(R.id.credit)).setText("Credit "+jsonObject.getString("CreditAmount"));


                        ((TextView) findViewById(R.id.debit)).setText("Debit "+jsonObject.getString("DebitAmount"));
*/
                     /*   ((TextView) findViewById(R.id.total_point)).setText("Total point "+rewardJson.getString("TotalPoint"));

                        ((TextView) findViewById(R.id.earning_point)).setText("Earning Point "+rewardJson.getString("EarningPoint"));

                        ((TextView) findViewById(R.id.used_point)).setText("Used Point "+rewardJson.getString("UsedPoint"));

                        ((TextView) findViewById(R.id.milestone_point)).setText("Milestone Point "+rewardJson.getString("MilestonePoint"));



*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    //   Toast.makeText(SkCodeActivity.this, json.toString(), Toast.LENGTH_SHORT).show();

                    //     Toast.makeText(MyWallet.this, json.toString(), Toast.LENGTH_SHORT).show();



                }


            }
        });





    }
}
