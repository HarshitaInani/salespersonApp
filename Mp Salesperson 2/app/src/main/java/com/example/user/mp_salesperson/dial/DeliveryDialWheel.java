package com.example.user.mp_salesperson.dial;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.android.volley.RequestQueue;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.ActionTaskMultiActivity;
import com.example.user.mp_salesperson.ActivationActivity;
import com.example.user.mp_salesperson.BeatOrderActivity;
import com.example.user.mp_salesperson.CartActivity;
import com.example.user.mp_salesperson.CheckOutActivity;
import com.example.user.mp_salesperson.Constant;
import com.example.user.mp_salesperson.DaysBidActivity;
import com.example.user.mp_salesperson.FeedbackActivity;
import com.example.user.mp_salesperson.HomeActivity;
import com.example.user.mp_salesperson.LoginActivity_Nav;
import com.example.user.mp_salesperson.MyOrders;
import com.example.user.mp_salesperson.MyProfile;
import com.example.user.mp_salesperson.MySalesActivity;
import com.example.user.mp_salesperson.MyWallet;
import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.ReedemPointActivity;
import com.example.user.mp_salesperson.RequestBrandActivity;
import com.example.user.mp_salesperson.RewardItemActivity;
import com.example.user.mp_salesperson.SettingActivity;
import com.example.user.mp_salesperson.SkCodeActivity;
import com.example.user.mp_salesperson.Utils;
import com.example.user.mp_salesperson.bean.CartItemBean;
import com.example.user.mp_salesperson.bean.CartItemInfo;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.customClasses.Utility;
import com.example.user.mp_salesperson.dial.library.LuckyItem;
import com.example.user.mp_salesperson.dial.library.LuckyWheelView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class DeliveryDialWheel extends AppCompatActivity {
    List<LuckyItem> data = new ArrayList<>();
    private Context mContext;
    private Activity mActivity;
     int GetPoint=0;
    private RelativeLayout mRelativeLayout;
    private Button mButton;
    Dialog mDialog;
    AnimationDrawable animation;
    private PopupWindow mPopupWindow;
     LuckyWheelView luckyWheelView;
    String dial,OrderId,Point;
    RequestQueue queue;

    int piCount=0;
    int pjCount=0;
    int pkCount=0;
    int plCount=0;
    int pmCount=0;
    int count=0;
    int dial1=0;
    static final int[] DIAL = {1, 1, 1, 1, 2, 2, 3, 3, 4, 5 };

    JSONObject jsonObject;
    String jsonString;
    String createdDate, CustomerName, CustomerType, Customerphonenum, CustomerId, ShippingAddress, ShopName, rewardPoints;
    String mobileString, AddressString, emailString = "", entryString;
    String salesPersonId, shippingAddress, shopName, skCode, totalAmountString, deliveryCharge;
    CartItemBean mCartItem;
    int deliveryCharges;
    JSONObject mRequesParamObj;
    double  totalAmount;
    double  totalDpPoints;
    double amountToReduct = 0;
    double enterRewardPoint;
    double px;
    double rx;
    Button btnPlay,btnSkip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No status bar on screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dial_wheel);


        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(DeliveryDialWheel.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        jsonString = Utility.getStringSharedPreferences(DeliveryDialWheel.this, "SkJson");
        mRetailerBean.getCustomerId();

        px = Double.parseDouble(Utility.getStringSharedPreferences(DeliveryDialWheel.this, "px"));
        rx = Double.parseDouble(Utility.getStringSharedPreferences(DeliveryDialWheel.this, "rx"));
        rewardPoints = Utility.getStringSharedPreferences(DeliveryDialWheel.this, "SkWalletAmount");

       // callChequeBounceApi();
        btnPlay=(Button)findViewById(R.id.play);
        btnSkip=(Button)findViewById(R.id.skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ErrorPopup();
            }
        });

        Log.e("SkJson", jsonString);

        CartItemBean cartItemBean = getCartItem();


        totalAmount = cartItemBean.getTotalPrice();
        totalDpPoints = cartItemBean.getTotalDpPoints();


        if (totalAmount < 2000) {


            deliveryCharges = 10;


        } else {

            deliveryCharges = 0;

        }
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


        amountToReduct = Double.parseDouble(Utility.getStringSharedPreferences(DeliveryDialWheel.this, "AmountToReduct"));
        enterRewardPoint = Double.parseDouble(Utility.getStringSharedPreferences(DeliveryDialWheel.this, "EnterRewardPoint"));

        System.out.println("AmountToReduce11::"+amountToReduct);



        Intent intent = getIntent();
        dial = intent.getStringExtra("DIAL");
        dial1= Integer.parseInt(dial);
        TextView mtext=(TextView)findViewById(R.id.text) ;
        mtext.setText("बधाई हो ! आपको मिले है "+dial1+" फ्री डायल ");
/*
         OrderId = intent.getStringExtra("OrderId");
         Point = intent.getStringExtra("Point");

        System.out.println("OrderId:::"+OrderId);
        // Get the application context*/
        mContext = getApplicationContext();

        // Get the activity
        mActivity = DeliveryDialWheel.this;

        // Get the widgets reference from XML layout

       luckyWheelView = (LuckyWheelView) findViewById(R.id.luckyWheel);

        LuckyItem luckyItem1 = new LuckyItem();
        luckyItem1.text = "40";
        luckyItem1.icon = R.drawable.prize;
        luckyItem1.color = Color.parseColor("#8F1BDF");
        data.add(luckyItem1);

        LuckyItem luckyItem2 = new LuckyItem();
        luckyItem2.text = "80";
        luckyItem2.icon = R.drawable.prize;
        luckyItem2.color = Color.parseColor("#00C9F9");
        data.add(luckyItem2);

        LuckyItem luckyItem3 = new LuckyItem();
        luckyItem3.text = "120";
        luckyItem3.icon = R.drawable.prize;
        luckyItem3.color = Color.parseColor("#FF8800");
        data.add(luckyItem3);

        //////////////////
        LuckyItem luckyItem4 = new LuckyItem();
        luckyItem4.text = "160";
        luckyItem4.icon = R.drawable.prize;
        luckyItem4.color = Color.parseColor("#FF3552");
        data.add(luckyItem4);

        LuckyItem luckyItem5 = new LuckyItem();
        luckyItem5.text = "200";
        luckyItem5.icon = R.drawable.prize;
        luckyItem5.color = Color.parseColor("#EFCEB9");
        data.add(luckyItem5);

        luckyWheelView.setData(data);
        luckyWheelView.setRound(getRandomRound());

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPlay.setEnabled(false);
                btnSkip.setEnabled(false);
                System.out.println("Dial1::"+dial1);
                if(dial1==0)
                {
                    Toast.makeText(mContext, "Your dial has end", Toast.LENGTH_SHORT).show();
                }else
                {
                    int index = getRandomIndex();
                    //Random random = new Random();
                   // int result =  DIAL[random.nextInt(DIAL.length)];
                  /*  int  index=0;
                    if(piCount!=4)
                    {
                        piCount++;
                        index=1;
                    }else if(pjCount!=2)
                    {
                        pjCount++;
                        index=2;
                    }
                    else if(pkCount!=2)
                    {
                        pkCount++;
                        index=3;
                    }
                    else if(plCount!=1)
                    {
                        plCount++;
                        index=4;
                    } else if(pmCount!=1)
                    {
                        pmCount++;
                        index=5;
                    }else
                    {
                        piCount=1;
                        pjCount=0;
                        pkCount=0;
                        plCount=0;
                        pmCount=0;
                    }*/
                    luckyWheelView.startLuckyWheelWithTargetIndex(index);
                }

            }
        });

        luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                String number = null;
                System.out.println("Index::"+index);
                if(index==0)
                {
                    number="40";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==1)
                {
                    number="40";
                    loadPhoto(R.drawable.sk_icon,number);
                }
                else if(index==2)
                {
                    number="80";
                    loadPhoto(R.drawable.sk_icon,number);
                }
                else if(index==3)
                {
                    number="120";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==4)
                {
                    number="160";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==5)
                {
                    number="200";
                    loadPhoto(R.drawable.sk_icon,number);
                }
              //  Toast.makeText(getApplicationContext(), number, Toast.LENGTH_SHORT).show();
              // callPostDialPoint();

            }
        });
    }

    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(DeliveryDialWheel.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }



    private int getRandomIndex() {
        Random rand = new Random();
        return rand.nextInt(data.size() - 1) + 0;
    }

    private int getRandomRound() {
        Random rand = new Random();
        return rand.nextInt(10) + 15;
    }

    private void loadPhoto(int img,String number) {

        dial1=dial1-1;
        int  gainPoint =   Utility.getIntSharedPreferences(DeliveryDialWheel.this,"GainPoint");

        System.out.println("gainPoint1::"+gainPoint);
        System.out.println("gainPoint2::"+GetPoint);
        System.out.println("gainPoint3::"+number);
        // GetPoint=gainPoint+GetPoint+Integer.parseInt(number);
        GetPoint=gainPoint+Integer.parseInt(number);
        System.out.println("gainPoint Final::"+GetPoint);

        final Dialog dialog = new Dialog(DeliveryDialWheel.this);
        dialog.setContentView(R.layout.custom);
        dialog.setCancelable(false);
        Button dialogButton = (Button) dialog.findViewById(R.id.ok);
        TextView tvPoint = (TextView) dialog.findViewById(R.id.text);
        String numberColor = " <i><font color=#000000>Congratulation you get </font> </i><font color=#0000FF>"+number+"</font> <i><font color=#000000> free point</font></i>";
        // tvPoint.setText("You Got "+ Html.fromHtml(numberColor)+" Point");
        tvPoint.setText(Html.fromHtml(numberColor));
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Utility.setIntSharedPreference(DeliveryDialWheel.this, "GainPoint", GetPoint);
                if(dial1==0)
                {
                    // Intent i = new Intent(getApplicationContext(), CartActivity.class);
                    //startActivity(i);
                    dialog.dismiss();
                    callOrderApi(jsonObject);

                }else{
                    dialog.dismiss();
                }
                count =   Utility.getIntSharedPreferences(DeliveryDialWheel.this,"DialCount");
                System.out.println("Count::"+count);
                count++;
                Utility.setIntSharedPreference(DeliveryDialWheel.this, "DialCount", count);
                //  callPostDialPoint();
                btnPlay.setEnabled(true);
                btnSkip.setEnabled(true);
                dialog.dismiss();
            }
        });
        dialog.show();



    }


    public void showLoading() {
        mDialog = new Dialog(DeliveryDialWheel.this);
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
       // startActivity(new Intent(DeliveryDialWheel.this ,CartActivity.class));
       // DeliveryDialWheel.this.finish();
        ErrorPopup();
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

        int   gainPoint =   Utility.getIntSharedPreferences(DeliveryDialWheel.this,"GainPoint");
        showLoading();

        // Toast.makeText(this, "Json Parameter"+jsonObject, Toast.LENGTH_SHORT).show();

        Log.e("Order Parameter", jsonObject.toString());



        int d = 0;




        try {

            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(DeliveryDialWheel.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
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

            //  mRequesParamObj.put("SaveAmount", (getCartItem().getSavingAmount()));

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
                mItemObj.put("WarehouseId", cartItemBean.getmCartItemInfos().get(i).getWarehouseId());
                mItemObj.put("CompanyId", cartItemBean.getmCartItemInfos().get(i).getCompanyId());

                mItemArray.put(mItemObj);

            }
            mRequesParamObj.put("itemDetails", mItemArray);



        } catch (Exception e) {

            //  Toast.makeText(CheckOutActivity.this, "Json error", Toast.LENGTH_SHORT).show();

            Log.e("ordererror", e.toString());
            e.printStackTrace();
        }



//

//        http://shopkiranamarketplace.moreyeahs.net/api/OrderMastersAPI

        new AQuery(DeliveryDialWheel.this).post(Constant.BASE_URL_PLACE_ORDER,
                mRequesParamObj,
                JSONObject.class,
                new AjaxCallback<JSONObject>(){

                    @Override
                    public void callback(String url, JSONObject object, AjaxStatus status) {


                        Log.e("Order Json Aq", mRequesParamObj.toString());


                        if (object != null) {
                            //   Toast.makeText(CheckOutActivity.this, ""+mRequesParamObj.toString(), Toast.LENGTH_SHORT).show();
                            // Toast.makeText(CheckOutActivity.this, "Object"+object.toString(), Toast.LENGTH_SHORT).show();


                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();

                            clearCartData();
                            Toast.makeText(DeliveryDialWheel.this, "Order placed successfully..", Toast.LENGTH_SHORT).show();



                            Utility.setStringSharedPreference(DeliveryDialWheel.this,"BeatSkCode","");
                            Utility.setStringSharedPreference(DeliveryDialWheel.this, "ItemQJson" ,"");


                            DeliveryDialWheel.this.finish();


                            startActivity(new Intent(DeliveryDialWheel.this, HomeActivity.class));



                        } else {
//                            Toast.makeText(CheckOutActivity.this, "False", Toast.LENGTH_SHORT).show();

                            Toast.makeText(DeliveryDialWheel.this, "Unable to place order, please try again", Toast.LENGTH_LONG).show();




                        }

                        if (mDialog.isShowing()) {
                            animation.stop();
                            mDialog.dismiss();
                        }

                    }
                });

    }
    public CartItemBean getCartItem() {
        if (mCartItem == null) {
            ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(DeliveryDialWheel.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
            mCartItem = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            if (mCartItem == null) {
                mCartItem = new CartItemBean(new ArrayList<CartItemInfo>(), 0, 0, 0, 0,0,0, "", "");
            }
        }
        return mCartItem;
    }
    public void printData() {

        try {

            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(DeliveryDialWheel.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
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

    private void ErrorPopup() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryDialWheel.this);
            builder.setTitle("Place Order");
            builder.setMessage("Are you sure, want to place order without remaining dial use?");
            builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    callOrderApi(jsonObject);
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

}
