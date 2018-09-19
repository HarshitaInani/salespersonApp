package com.example.user.mp_salesperson;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.bean.CartItemBean;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.customClasses.Utility;

import org.json.JSONException;
import org.json.JSONObject;

public class SkCodeActivity extends AppCompatActivity {

    EditText etSkcode;
    Button btnContinue;

    String skCode = "";

    String beatSkcode = "";

    String urlRemote;

    String customerId = "";

    CartItemBean mCartItem;

    Context context;

    Dialog mDialog;
    AnimationDrawable animation;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sk_code);


        etSkcode = (EditText) findViewById(R.id.et_skcode);

        context = this;

        beatSkcode = Utility.getStringSharedPreferences(context, "BeatSkCode");



        ((TextView) findViewById(R.id.title_toolbar)).setText("Sk Code");

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(SkCodeActivity.this, CartActivity.class));
                SkCodeActivity.this.finish();
            }
        });


        if (!beatSkcode.isEmpty()) {
            etSkcode.setText(beatSkcode);
        } else {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Sk Code")
                    .setMessage("Would you like to chose a Sk Code from the list?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //finish();

                            SkCodeActivity.this.finish();
                            startActivity(new Intent(SkCodeActivity.this, DaysBidActivity.class));
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();


        }




        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(SkCodeActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);


        mRetailerBean.getCustomerId();

     //   Toast.makeText(this, mRetailerBean.getCustomerId(), Toast.LENGTH_SHORT).show();



//        Map<String,String> param = new HashMap<String, String>();
//        param.put("email", "");
//        param.put("token", "" );

      // urlRemote = "http://skdeliveryapp.moreyeahs.net/api/SalePersonRetailer?ExecutiveId="+mRetailerBean.getCustomerId()+"&srch="+skCode;

//                    http://synoris.org:12/nyc/RegisterDevice.php
        //new AQuery(SignUpActivity.this).ajax("http://192.168.1.101/NyCasting/RegisterDevice.php",  param, JSONObject.class, new AjaxCallback<JSONObject>(){



        ((Button) findViewById(R.id.continue_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                skCode = etSkcode.getText().toString();


             //   urlRemote = "http://skdeliveryapp.moreyeahs.net/api/SalePersonRetailer?ExecutiveId="+mRetailerBean.getExecutiveId()+"&srch="+skCode;


//                urlRemote = "http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/SalePersonRetailer?ExecutiveId="+mRetailerBean.getExecutiveId()+"&srch="+skCode;

               // urlRemote = "http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/SalePersonRetailer?ExecutiveId="+mRetailerBean.getCustomerId()+"&srch="+skCode;

                urlRemote = Constant.BASE_URL_SKCODE+"?ExecutiveId="+mRetailerBean.getCustomerId()+"&srch="+skCode;


                //    Toast.makeText(SkCodeActivity.this, ""+urlRemote, Toast.LENGTH_SHORT).show();

                Log.e("url",urlRemote);




/*
                new AQuery(getApplicationContext()).ajax(urlRemote, null, JSONObject.class, new AjaxCallback<JSONObject>() {

                    @Override
                    public void callback(String url, JSONObject json, AjaxStatus status) {



                        if (json == null) {


                            Toast.makeText(SkCodeActivity.this, "Json is null \n"+status.getError().toString()+"\n"+status.getMessage().toString(), Toast.LENGTH_SHORT).show();


                        } else {


                            Toast.makeText(SkCodeActivity.this, json.toString(), Toast.LENGTH_SHORT).show();


                        }


                    }
                });

*/

                if (skCode.isEmpty()) {

                    Toast.makeText(SkCodeActivity.this, "Please enter Sk Code.", Toast.LENGTH_SHORT).show();

                }else {

                    new AQuery(getApplicationContext()).ajax(urlRemote, null, JSONObject.class, new AjaxCallback<JSONObject>() {

                        @Override
                        public void callback(String url, JSONObject json, AjaxStatus status) {


                            showLoading();

                            if (json != null) {


                                Utility.setStringSharedPreference(SkCodeActivity.this, "SkJson", json.toString());

                             //   startActivity(new Intent(SkCodeActivity.this, CheckOutActivity.class));


                                try {
                                    String customerId = json.getString("CustomerId");
                                 //   String cId = json.getString("");

                                    String cName = json.getString("Name");


                                    Utility.setStringSharedPreference(context, "BeatSkCodeCId", customerId);

                                    Utility.setStringSharedPreference(context, "BeatSkCodeCName", cName);

                                    Utility.setStringSharedPreference(context, "BeatSkCode", skCode);



                                    callWalletApi(customerId);
                                } catch (JSONException e) {
                                    Toast.makeText(SkCodeActivity.this, "Json error "+e.toString(), Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }


                            } else {

                                if (mDialog.isShowing()) {
                                    animation.stop();
                                    mDialog.dismiss();
                                }

                             //   Toast.makeText(SkCodeActivity.this, json.toString(), Toast.LENGTH_SHORT).show();


                                Toast.makeText(SkCodeActivity.this,"Wrong code", Toast.LENGTH_SHORT).show();


                            }


                        }
                    });


//                    startActivity(new Intent(SkCodeActivity.this, CheckOutActivity.class));

                }
            }
        });



    }


    public void callWalletApi(String id) {


     //   Toast.makeText(context, "Call Wallet Api called", Toast.LENGTH_SHORT).show();
        String url = Constant.BASE_URL_MY_WALLET+"?CustomerId="+id;
        new AQuery(getApplicationContext()).ajax(url, null, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {



                if (json == null) {

                    Toast.makeText(SkCodeActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();


                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }


                } else {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }


                    SkCodeActivity.this.finish();


                    try {


//                        Toast.makeText(CheckOutActivity.this, "Json"+ json.toString(), Toast.LENGTH_SHORT).show();

                        JSONObject  jsonObject = json.getJSONObject("conversion");


                        JSONObject  jsonObjectWallet = json.getJSONObject("wallet");


                        String totalAmount = jsonObjectWallet.getString("TotalAmount");

                        Utility.setStringSharedPreference(SkCodeActivity.this, "SkWalletAmount", ""+totalAmount);


                        double px = Double.parseDouble(jsonObject.getString("point"));
                       double rx = Double.parseDouble((jsonObject.getString("rupee")));

                        Utility.setStringSharedPreference(SkCodeActivity.this, "px", ""+px);
                        Utility.setStringSharedPreference(SkCodeActivity.this, "rx", ""+rx);

                        startActivity(new Intent(SkCodeActivity.this, CheckOutActivity.class));


               /*         amountTv.setText("Total Amount "+amount);

                        ((TextView) findViewById(R.id.credit)).setText("Credit "+jsonObject.getString("CreditAmount"));


                        ((TextView) findViewById(R.id.debit)).setText("Debit "+jsonObject.getString("DebitAmount"));

                        ((TextView) findViewById(R.id.total_point)).setText("Total point "+rewardJson.getString("TotalPoint"));

                        ((TextView) findViewById(R.id.earning_point)).setText("Earning Point "+rewardJson.getString("EarningPoint"));

                        ((TextView) findViewById(R.id.used_point)).setText("Used Point "+rewardJson.getString("UsedPoint"));

                        ((TextView) findViewById(R.id.milestone_point)).setText("Milestone Point "+rewardJson.getString("MilestonePoint"));
*/



                    } catch (JSONException e) {


                        Utility.setStringSharedPreference(SkCodeActivity.this, "px", ""+0);
                        Utility.setStringSharedPreference(SkCodeActivity.this, "rx", ""+0);


                        Utility.setStringSharedPreference(SkCodeActivity.this, "SkWalletAmount", ""+0);

                        startActivity(new Intent(SkCodeActivity.this, CheckOutActivity.class));
                   //     Toast.makeText(SkCodeActivity.this, "Json error"+e.toString(), Toast.LENGTH_SHORT).show();


                        e.printStackTrace();



                    }

                    //   Toast.makeText(SkCodeActivity.this, json.toString(), Toast.LENGTH_SHORT).show();

                    //     Toast.makeText(MyWallet.this, json.toString(), Toast.LENGTH_SHORT).show();


             //       Toast.makeText(SkCodeActivity.this, "", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    public void showLoading() {
        mDialog = new Dialog(SkCodeActivity.this);
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
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(SkCodeActivity.this, CartActivity.class));

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
                Log.e("LoginActivity", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}
