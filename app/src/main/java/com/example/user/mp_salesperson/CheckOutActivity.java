package com.example.user.mp_salesperson;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.bean.CartItemBean;
import com.example.user.mp_salesperson.bean.CartItemInfo;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.customClasses.Utility;
import com.example.user.mp_salesperson.dial.DeliveryDialWheel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CheckOutActivity extends AppCompatActivity {

    EditText etEmail, etEntry, etAddress;
  //  String emailString, nameString, addressString;
    TextView tvPhoneNo, tvAddress, tvTotal, tvOrder, tvShopName, tvDeliveryCharges, tvDpTotal;

    String jsonString;

    JSONObject jsonObject;

    String mobileString, AddressString, emailString = "", entryString;

    String createdDate, CustomerName, CustomerType, Customerphonenum, CustomerId, ShippingAddress, ShopName, rewardPoints;

    String salesPersonId, shippingAddress, shopName, skCode, totalAmountString, deliveryCharge;

    String walletAmount = "";
    CartItemBean mCartItem;
  //  String deliveryCharger;
    int deliveryCharges;
    double  totalAmount;
    double  totalDpPoints;
    double px;
    double rx;
    ProgressDialog progressDialog;
    double enterRewardPoint;
    String skC;
    Dialog mDialog;
    AnimationDrawable animation;


    JSONObject mRequesParamObj;

    double amountToReduct = 0;
     AlertDialog customAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        progressDialog = new ProgressDialog(CheckOutActivity.this);
        progressDialog.setMessage("Loading");

        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(CheckOutActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);


        // Toast.makeText(this, "SkCode"+Utility.getStringSharedPreferences(CheckOutActivity.this, "BeatSkCode"), Toast.LENGTH_SHORT).show();


        skC = Utility.getStringSharedPreferences(CheckOutActivity.this, "BeatSkCode");
        ((TextView) findViewById(R.id.title_toolbar)).setText("Check Out");

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CheckOutActivity.this, SkCodeActivity.class));
                CheckOutActivity.this.finish();
            }
        });





        mRetailerBean.getCustomerId();

        px = Double.parseDouble(Utility.getStringSharedPreferences(CheckOutActivity.this, "px"));
        rx = Double.parseDouble(Utility.getStringSharedPreferences(CheckOutActivity.this, "rx"));

        rewardPoints = Utility.getStringSharedPreferences(CheckOutActivity.this, "SkWalletAmount");

     //   Toast.makeText(this, "Retailer Bean "+mRetailerBean.getCustomerId(), Toast.LENGTH_SHORT).show();

        jsonString = Utility.getStringSharedPreferences(CheckOutActivity.this, "SkJson");


        callChequeBounceApi();


        Log.e("SkJson", jsonString);

        etEmail = (EditText) findViewById(R.id.etEmail);
        etEntry = (EditText) findViewById(R.id.etEntry);
        etAddress = (EditText) findViewById(R.id.etAddress);

        tvPhoneNo = (TextView) findViewById(R.id.tvPhoneno);
        tvAddress = (TextView) findViewById(R.id.tvAddress);

        tvShopName = (TextView) findViewById(R.id.tvShopName);

        tvTotal = (TextView) findViewById(R.id.tvTotal);
        tvOrder = (TextView) findViewById(R.id.tvOrder);

        tvDeliveryCharges = (TextView) findViewById(R.id.cart_frag_delivery_charges_tv);

        tvDpTotal= (TextView) findViewById(R.id.tvDpTotal);


        CartItemBean cartItemBean = getCartItem();


        totalAmount = cartItemBean.getTotalPrice();
        totalDpPoints = cartItemBean.getTotalDpPoints();


        if (totalAmount < 2000) {


            deliveryCharges = 10;


        } else {

            deliveryCharges = 0;

        }

        //getTotalDelivery(totalAmount);


      //  if (totalAmount< )


/*
        mRequesParamObj.put("CreatedDate", currentDateandTime);
        mRequesParamObj.put("CustomerName", mRetailerBean.getName());
        mRequesParamObj.put("CustomerType", mRetailerBean.getCustomerType());
        mRequesParamObj.put("Customerphonenum", mRetailerBean.getMobile());

//                mRequesParamObj.put("SalesPersonId", 0*/
/*mRetailerBean.getCustomerId()*//*
);
        mRequesParamObj.put("SalesPersonId", mRetailerBean.getExecutiveId());


//                mRequesParamObj.put("CustomerId", mRetailerBean.getCustomerId());
        mRequesParamObj.put("ShippingAddress", mRetailerBean.getShippingAddress());
        mRequesParamObj.put("ShopName", mRetailerBean.getShopName());
        mRequesParamObj.put("Skcode", mRetailerBean.getSkcode());
        mRequesParamObj.put("TotalAmount", cartItemBean.getTotalPrice());
        mRequesParamObj.put("deliveryCharge", "10");
*/


        printData();

        try {

            jsonObject = new JSONObject(jsonString);


          //  Toast.makeText(this, "sk json"+jsonObject.toString(), Toast.LENGTH_SHORT).show();
            createdDate = jsonObject.getString("CreatedDate");
            CustomerName = jsonObject.getString("Name");


            //CustomerType = jsonObject.getString("CustomerType");

            //CustomerType not getting
            CustomerType = isNullOrEmpty(jsonObject, "CustomerType");


            mobileString = jsonObject.getString("Mobile");

            salesPersonId = "1";


            //Shipping not getting
            shippingAddress = isNullOrEmpty(jsonObject, "ShippingAddress");
            shopName = jsonObject.getString("ShopName");
            skCode = jsonObject.getString("Skcode");
//Reward point before
            //rewardPoints = jsonObject.getString("Rewardspoints");


            totalAmountString = "";
            deliveryCharge = "";




            AddressString = jsonObject.getString("BillingAddress");
//            emailString = jsonObject.getString("Emailid");


            emailString = isNullOrEmpty(jsonObject, "Emailid");

           // isNullOrEmpty

            entryString = jsonObject.getString("Name");

            String customerId = jsonObject.getString("CustomerId");
            //callWalletApi(customerId);






        } catch (JSONException e) {

            Toast.makeText(this, "Json Building error"+e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


        // Toast.makeText(this, "SharedPreference Json "+jsonString, Toast.LENGTH_SHORT).show();


        tvPhoneNo.setText(mobileString);
        tvAddress.setText(AddressString);
        tvShopName.setText(shopName);

        tvDeliveryCharges.setText("Delivery Charges: "+deliveryCharges);


//
//        tvPhoneNo.setText(mRetailerBean.getMobile());
//        tvAddress.setText(mRetailerBean.getBillingAddress());
//        tvShopName.setText(mRetailerBean.getShopName());


        //tvTotal.setText("Total: "+totalAmount);



        tvTotal.setText("Total : "+new DecimalFormat("##.##").format(totalAmount));


        tvDpTotal.setText("Dream points total : "+new DecimalFormat("##.##").format(totalDpPoints));

        //  new DecimalFormat("##.##").format(totalAmount);


        if (emailString.isEmpty() || emailString == null) {

        } else {

            etEmail.setText(emailString);

        }
        etEntry.setText(entryString);
        etAddress.setText(AddressString);

        //tv.setText(mobileString);

        tvOrder.setOnClickListener(new View.OnClickListener() {




            @Override
            public void onClick(View v) {

                System.out.println("TotalAmount::"+totalAmount);
            /*    tvTotal.setText("Total : "+new DecimalFormat("##.##").format(totalAmount));*/
                String email = etEmail.getText().toString();
                String name = etEntry.getText().toString();
                String address = etAddress.getText().toString();
                emailString =  etEmail.getText().toString();
                AddressString = etAddress.getText().toString();
                entryString = etEntry.getText().toString();
                //    Toast.makeText(CheckOutActivity.this, email+name+current, Toast.LENGTH_SHORT).show();
//                if (email.isEmpty()) {
//                    Toast.makeText(CheckOutActivity.this, "Please enter email", Toast.LENGTH_SHORT).show();
//                }

                if (name.isEmpty()) {
                    Toast.makeText(CheckOutActivity.this, "Please enter name", Toast.LENGTH_SHORT).show();
                }

                else if (address.isEmpty()) {
                    Toast.makeText(CheckOutActivity.this, "Please enter current", Toast.LENGTH_SHORT).show();
                }


               /* else if (!Utility.emailValidator(email)) {

                    Toast.makeText(CheckOutActivity.this, "Please enter a valid email", Toast.LENGTH_SHORT).show();

                }*/

                else {
                 //Order Api calling async
                //    new PlaceORderAsyncTask().execute();
                    //  Toast.makeText(CheckOutActivity.this, "Order", Toast.LENGTH_SHORT).show();



                    LayoutInflater inflater = LayoutInflater.from(CheckOutActivity.this);
                    View dialogLayout = inflater.inflate(R.layout.reward_point_popup, null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(CheckOutActivity.this);
                    builder.setView(dialogLayout);

                    customAlertDialog = builder.create();
                    Button placeBtn  =(Button) dialogLayout.findViewById(R.id.place_order);
                    TextView cancelBtn  =(TextView) dialogLayout.findViewById(R.id.cancel);


                    TextView redeemBtn  =(TextView) dialogLayout.findViewById(R.id.redeemBtn);

                    TextView dpTotal = (TextView) dialogLayout.findViewById(R.id.dppoint);

                    TextView redeemTotal = (TextView) dialogLayout.findViewById(R.id.redeempop_up_total);


                    TextView pointInfoTv = (TextView) dialogLayout.findViewById(R.id.point_info);

                    TextView saveAmount = (TextView) dialogLayout.findViewById(R.id.saving_amount);
                    final TextView redeemNetTotal = (TextView) dialogLayout.findViewById(R.id.redeem_pop_up_net_amount);


                    final EditText point = (EditText) dialogLayout.findViewById(R.id.pointEt);

                    point.setText("0");


                    if (rewardPoints.isEmpty()) {
                        rewardPoints = "0";
                    }

                    if (rewardPoints.equals("null")) {
                        rewardPoints = "0";
                    }


                    dpTotal.setText(rewardPoints);

                    redeemTotal.setText(new DecimalFormat("##.##").format(totalAmount));
                    redeemNetTotal.setText(new DecimalFormat("##.##").format(totalAmount));
                    saveAmount.setText(new DecimalFormat("##.##").format(getCartItem().getSavingAmount()));
                    pointInfoTv.setText("Note "+px+" point = Rs "+rx);

                    placeBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!point.getText().toString().isEmpty()) {
                               // Toast.makeText(CheckOutActivity.this, "Order Go Reward point : "+enterRewardPoint, Toast.LENGTH_SHORT).show();
//                                new PlaceORderAsyncTask().execute();
                               // callOrderApi(jsonObject);
                                String  AvailDial = Utility.getStringSharedPreferences(CheckOutActivity.this, "AvailDial");
                                Utility.setStringSharedPreference(CheckOutActivity.this, "EnterRewardPoint", ""+enterRewardPoint);
                                Utility.setStringSharedPreference(CheckOutActivity.this, "AmountToReduct", ""+amountToReduct);
                                if(AvailDial.equalsIgnoreCase("0")) {
                                    //Toast.makeText(CheckOutActivity.this, "No Dial Available", Toast.LENGTH_SHORT).show();
                                    customAlertDialog.dismiss();
                                     callOrderApi(jsonObject);
                                }else{
                                    Intent i = new Intent(getApplicationContext(), DeliveryDialWheel.class);
                                    i.putExtra("DIAL", AvailDial);
                                    startActivity(i);
                                }


                            }

                            else {
                                Toast.makeText(CheckOutActivity.this, "Please enter rewards point!", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    redeemBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            if (!point.getText().toString().isEmpty()) {

                                if (rewardPoints.isEmpty()) {
                                    rewardPoints = "0";
                                }

                                if (rewardPoints.equals("null")) {
                                    rewardPoints = "0";
                                }

                                double currentRewardPoints = Double.parseDouble(rewardPoints);
                                 enterRewardPoint = Double.parseDouble(point.getText().toString());
                                double totalAmountPopUp = Double.parseDouble(new DecimalFormat("##.##").format(totalAmount));
                                double netTotal = 0;
                                if (enterRewardPoint > currentRewardPoints) {
                                    Toast.makeText(CheckOutActivity.this, "You do not have enough points!", Toast.LENGTH_SHORT).show();
                                    enterRewardPoint = 0;
                                    amountToReduct=0;
                                } else {
                                    amountToReduct = (enterRewardPoint / px) * rx;
                                    System.out.println("AmountToReduce::"+amountToReduct);

                                    if (amountToReduct < totalAmountPopUp) {

//                            netTotal = totalAmountPopUp - (enterRewardPoint / 10);

                                        netTotal = totalAmountPopUp - amountToReduct;


                                        redeemNetTotal.setText("" + new DecimalFormat("##.##").format(netTotal));

                                    } else {
                                        Toast.makeText(CheckOutActivity.this, "You can not use point more than Total bill.", Toast.LENGTH_SHORT).show();
                                    }

                                }


                             //   Toast.makeText(CheckOutActivity.this, "" + point.getText().toString(), Toast.LENGTH_SHORT).show();


                            }
                            else {
                                Toast.makeText(CheckOutActivity.this, "Please enter rewards point!", Toast.LENGTH_SHORT).show();
                            }



                        }
                    });

                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customAlertDialog.dismiss();
                        }
                    });

            /*    TextView indtruction = (TextView)dialogLayout.findViewById(R.id.text_instruction);
                final EditText userInput = (EditText)dialogLayout.findViewById(R.id.user_input);
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Submit Name", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String userInputContent = userInput.getText().toString();
                        if(userInputContent.equals("")){
                            Toast.makeText(context, "You must enter a name in the field", Toast.LENGTH_LONG).show();
                            return;
                        }
                        textFromDialog.setText(userInputContent);
                    }
                });
            */


                  //  AlertDialog customAlertDialog = builder.create();
                    customAlertDialog.show();





                }

            }
        });


    }


    public CartItemBean getCartItem() {
        if (mCartItem == null) {
            ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(CheckOutActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0, "", "");
            }
        }
        return mCartItem;
    }




    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(CheckOutActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }



    public void printData() {

        try {

            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(CheckOutActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
            RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
            CartItemBean cartItemBean = getCartItem();
            String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            JSONObject mRequesParamObj = new JSONObject();


            mRequesParamObj.put("CreatedDate", currentDateandTime);



            mRequesParamObj.put("CustomerName", mRetailerBean.getName());

    //        mRequesParamObj.put("CustomerName", mRetailerBean.getName());



            mRequesParamObj.put("CustomerType", mRetailerBean.getCustomerType());
            mRequesParamObj.put("Customerphonenum", mRetailerBean.getMobile());

//                mRequesParamObj.put("SalesPersonId", 0*//*mRetailerBean.getCustomerId()*//*);
            mRequesParamObj.put("SalesPersonId", mRetailerBean.getExecutiveId());


//                mRequesParamObj.put("CustomerId", mRetailerBean.getCustomerId());


            mRequesParamObj.put("ShippingAddress", AddressString );

//            mRequesParamObj.put("ShippingAddress", );





            mRequesParamObj.put("ShopName", mRetailerBean.getShopName());
            mRequesParamObj.put("Skcode", mRetailerBean.getSkcode());
            mRequesParamObj.put("TotalAmount", cartItemBean.getTotalPrice());
            mRequesParamObj.put("deliveryCharge", deliveryCharge);
            JSONArray mItemArray = new JSONArray();
            for (int i = 0; i < cartItemBean.getmCartItemInfos().size(); i++) {
                JSONObject mItemObj = new JSONObject();
                mItemObj.put("ItemId", cartItemBean.getmCartItemInfos().get(i).getItemId());
                mItemObj.put("qty", cartItemBean.getmCartItemInfos().get(i).getQty());
                mItemArray.put(mItemObj);
            }
            mRequesParamObj.put("itemDetails", mItemArray);


        }catch (Exception e) {

        }
    }






    public void callWalletApi(String id) {


        progressDialog.show();
        String url = Constant.BASE_URL_MY_WALLET+"?CustomerId="+id;
        new AQuery(getApplicationContext()).ajax(url, null, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {



                if (json == null) {

                    progressDialog.dismiss();
                    Toast.makeText(CheckOutActivity.this, "Json is null \n"+status.getError().toString()+"\n"+status.getMessage().toString(), Toast.LENGTH_SHORT).show();


                } else {

                    try {

                        progressDialog.dismiss();
//                        Toast.makeText(CheckOutActivity.this, "Json"+ json.toString(), Toast.LENGTH_SHORT).show();

                        JSONObject  jsonObject = json.getJSONObject("conversion");

                        px = Integer.parseInt(jsonObject.getString("point"));
                        rx = Integer.parseInt(jsonObject.getString("rupee"));




                    } catch (JSONException e) {

                      //  Toast.makeText(CheckOutActivity.this, "Wallet Json error"+e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();


                    }


                }


            }
        });


    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(CheckOutActivity.this, SkCodeActivity.class));
        CheckOutActivity.this.finish();

    }



    public void callChequeBounceApi() {

        //http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/SalesSettlement/SearchSkcode?skcode=SK4201
        System.out.println("CheckBounse Api:::::"+Constant.BASE_URL_CHEQUE_BOUNCE+"?skcode="+skC);
        new AQuery(CheckOutActivity.this).ajax(Constant.BASE_URL_CHEQUE_BOUNCE+"?skcode="+skC,
                null,
                String.class,
                new AjaxCallback<String>(){

                    @Override
                    public void callback(String url, String string, AjaxStatus status) {

                        // Toast.makeText(CheckOutActivity.this, "Result : "+string, Toast.LENGTH_SHORT).show();

                        if (string.equals("true")) {
                            new AlertDialog.Builder(CheckOutActivity.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("Notice")
                                    .setMessage("Your cheque was bounced!")
                                    .setPositiveButton("Ok", null)
                                    .show();
                        }
                    }
                });
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




    public void callOrderApi(JSONObject jsonObject) {

     int   gainPoint =   Utility.getIntSharedPreferences(CheckOutActivity.this,"GainPoint");
        showLoading();

        // Toast.makeText(this, "Json Parameter"+jsonObject, Toast.LENGTH_SHORT).show();



        int d = 0;
        try {


            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(CheckOutActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
            RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
            CartItemBean cartItemBean = getCartItem();
            String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());






            if (cartItemBean.getTotalPrice() < 2000) {


                deliveryCharges = 10;

                d = 10;


            } else {

                deliveryCharges = 0;

                d = 0;
            }









            mRequesParamObj = new JSONObject();
            mRequesParamObj.put("CreatedDate", currentDateandTime);


            // mRequesParamObj.put("CustomerName", CustomerName);
            mRequesParamObj.put("CustomerName", entryString);
            mRequesParamObj.put("DialEarnigPoint", gainPoint);


            mRequesParamObj.put("CustomerType", "");
            mRequesParamObj.put("Customerphonenum", mobileString);

//                mRequesParamObj.put("SalesPersonId", 0/*mRetailerBean.getCustomerId()*/);
            mRequesParamObj.put("SalesPersonId", mRetailerBean.getCustomerId());


//                mRequesParamObj.put("CustomerId", mRetailerBean.getCustomerId());
//                mRequesParamObj.put("ShippingAddress", mRetailerBean.getBillingAddress());


            mRequesParamObj.put("ShippingAddress", AddressString);


            mRequesParamObj.put("ShopName", shopName);
            mRequesParamObj.put("Skcode", skCode);
            mRequesParamObj.put("TotalAmount", cartItemBean.getTotalPrice());
            mRequesParamObj.put("Savingamount", (getCartItem().getSavingAmount()));


            //mRequesParamObj.put("deliveryCharge", deliveryCharge);


            mRequesParamObj.put("deliveryCharge", d);


            mRequesParamObj.put("WalletAmount", amountToReduct);



            mRequesParamObj.put("walletPointUsed", enterRewardPoint);

            mRequesParamObj.put("DreamPoint", cartItemBean.getTotalDpPoints());


            JSONArray mItemArray = new JSONArray();

            for (int i = 0; i < cartItemBean.getmCartItemInfos().size(); i++) {


                JSONObject mItemObj = new JSONObject();
                mItemObj.put("ItemId", cartItemBean.getmCartItemInfos().get(i).getItemId());
                mItemObj.put("qty", cartItemBean.getmCartItemInfos().get(i).getQty());


               mItemObj.put("WarehouseId", mRetailerBean.getWarehouseId());
               mItemObj.put("CompanyId", mRetailerBean.getCompanyId());
                /*mItemObj.put("WarehouseId",7);
                 mItemObj.put("CompanyId", 1);*/
//                mItemObj.put("WarehouseId", "1");
//                mItemObj.put("CompanyId", "1");


                mItemArray.put(mItemObj);



            }
            mRequesParamObj.put("itemDetails", mItemArray);
            System.out.println("itemDetails"+mRequesParamObj);

        } catch (Exception e) {

            //  Toast.makeText(CheckOutActivity.this, "Json error", Toast.LENGTH_SHORT).show();

            Log.e("ordererror", e.toString());
            e.printStackTrace();
        }




//        http://shopkiranamarketplace.moreyeahs.net/api/OrderMastersAPI

        new AQuery(CheckOutActivity.this).post(Constant.BASE_URL_PLACE_ORDER, mRequesParamObj, JSONObject.class, new AjaxCallback<JSONObject>(){

                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {



                        System.out.println("Order Json Aq"+mRequesParamObj.toString());



                        if (object != null) {
                         //   Toast.makeText(CheckOutActivity.this, ""+mRequesParamObj.toString(), Toast.LENGTH_SHORT).show();
                           // Toast.makeText(CheckOutActivity.this, "Object"+object.toString(), Toast.LENGTH_SHORT).show();

                            System.out.println("Order Json Aqqqq"+mRequesParamObj.toString());
                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();



                            Toast.makeText(CheckOutActivity.this, "Order placed successfully..", Toast.LENGTH_SHORT).show();
                            clearCartData();


                            Utility.setStringSharedPreference(CheckOutActivity.this,"BeatSkCode","");
                            Utility.setStringSharedPreference(CheckOutActivity.this, "ItemQJson" ,"");


                            CheckOutActivity.this.finish();


                            startActivity(new Intent(CheckOutActivity.this, HomeActivity.class));



                        } else {
//                            Toast.makeText(CheckOutActivity.this, "False", Toast.LENGTH_SHORT).show();

                            Toast.makeText(CheckOutActivity.this, "Unable to place order, please try again", Toast.LENGTH_LONG).show();




                        }

                        if (mDialog.isShowing()) {
                            animation.stop();
                            mDialog.dismiss();
                        }

                    }
                });













    }




    public void showLoading() {
        mDialog = new Dialog(CheckOutActivity.this);
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
