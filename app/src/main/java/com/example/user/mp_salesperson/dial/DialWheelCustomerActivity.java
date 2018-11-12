package com.example.user.mp_salesperson.dial;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.android.volley.RequestQueue;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.ActionTaskMultiActivity;
import com.example.user.mp_salesperson.ActivationActivity;
import com.example.user.mp_salesperson.BeatOrderActivity;
import com.example.user.mp_salesperson.CartActivity;
import com.example.user.mp_salesperson.Constant;
import com.example.user.mp_salesperson.DaysBidActivity;
import com.example.user.mp_salesperson.FeedbackActivity;
import com.example.user.mp_salesperson.HomeActivity;
import com.example.user.mp_salesperson.LoginActivity_Nav;
import com.example.user.mp_salesperson.MyDialListActivity;
import com.example.user.mp_salesperson.MyOrders;
import com.example.user.mp_salesperson.MyProfile;
import com.example.user.mp_salesperson.MySalesActivity;
import com.example.user.mp_salesperson.MyWallet;
import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.ReedemPointActivity;
import com.example.user.mp_salesperson.RequestBrandActivity;
import com.example.user.mp_salesperson.RewardItemActivity;
import com.example.user.mp_salesperson.SettingActivity;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.customClasses.Utility;
import com.example.user.mp_salesperson.dial.library.LuckyItem;
import com.example.user.mp_salesperson.dial.library.LuckyWheelView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class DialWheelCustomerActivity extends AppCompatActivity {
    List<LuckyItem> data = new ArrayList<>();
    private Context mContext;
    private Activity mActivity;
    private RelativeLayout mRelativeLayout;
    private Button mButton;
    Dialog mDialog;
    AnimationDrawable animation;
    private PopupWindow mPopupWindow;
     LuckyWheelView luckyWheelView;
    String Id,OrderId,Point;
    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial_wheel);


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialWheelCustomerActivity.this.finish();
                startActivity(new Intent(DialWheelCustomerActivity.this ,MyDialListActivity.class));
            }
        });
        ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialWheelCustomerActivity.this.finish();
                startActivity(new Intent(DialWheelCustomerActivity.this, HomeActivity.class));
            }
        });



     /*   ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BeatOrderActivity.this.finish();
            }
        });
*/

        ((ImageView) toolbar.findViewById(R.id.my_order_more_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View menuItemView = findViewById(R.id.my_order_more_iv);
                PopupMenu popup = new PopupMenu(DialWheelCustomerActivity.this, menuItemView);
                MenuInflater inflate = popup.getMenuInflater();
//                inflate.inflate(R.menu.my_order_option_menu, popup.getMenu());
                inflate.inflate(R.menu.home, popup.getMenu());
                popup.show();
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();
                        if (id == R.id.action_my_orders) {
                            startActivity(new Intent(DialWheelCustomerActivity.this, MyOrders.class));
                            return true;
                        } else if (id == R.id.action_my_profile) {
                            startActivity(new Intent(DialWheelCustomerActivity.this, MyProfile.class).putExtra("showButton", false));
                            return true;
                        }else if (id == R.id.action_my_wallet) {
                            startActivity(new Intent(DialWheelCustomerActivity.this, MyWallet.class));
                            return true;
                        }
                        else if (id == R.id.action_contact_us) {
                            startActivity(new Intent(DialWheelCustomerActivity.this, ActivationActivity.class).putExtra("showButton", false));
                            return true;
                        } else if (id == R.id.action_my_cart) {
                            startActivity(new Intent(DialWheelCustomerActivity.this, CartActivity.class));
                            return true;
                        } else if (id == R.id.action_request_item) {
                            startActivity(new Intent(DialWheelCustomerActivity.this, RequestBrandActivity.class));
                            return true;
                        } else if (id == R.id.action_feedback) {
                            startActivity(new Intent(DialWheelCustomerActivity.this, FeedbackActivity.class));
                            return true;
                        } else if (id == R.id.action_logout) {


                     /*       ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(BeatOrderActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();
                            startActivity(new Intent(BeatOrderActivity.this, LoginActivity_Nav.class));
                            BeatOrderActivity.this.finish();
                     */




                            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(DialWheelCustomerActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
                            mRetailerBeanPref.clear();
                            mRetailerBeanPref.commit();


                            ComplexPreferences mRetailerBeanPref2 = ComplexPreferences.getComplexPreferences(DialWheelCustomerActivity.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);


                            mRetailerBeanPref2.clear();
                            mRetailerBeanPref2.commit();




                            ComplexPreferences mRetailerBeanPref3 = ComplexPreferences.getComplexPreferences(DialWheelCustomerActivity.this, Constant.POPULAR_BRANDS_PREF, MODE_PRIVATE);


                            mRetailerBeanPref3.clear();
                            mRetailerBeanPref3.commit();



                            ComplexPreferences mRetailerBeanPref4 = ComplexPreferences.getComplexPreferences(DialWheelCustomerActivity.this, Constant.SUB_SUB_CAT_ITEM_PREF, MODE_PRIVATE);


                            mRetailerBeanPref4.clear();
                            mRetailerBeanPref4.commit();





                            Utility.setStringSharedPreference(DialWheelCustomerActivity.this, "Bidmonday", "");
                            Utility.setStringSharedPreference(DialWheelCustomerActivity.this, "Bidtuesday", "");
                            Utility.setStringSharedPreference(DialWheelCustomerActivity.this, "Bidwednesday", "");
                            Utility.setStringSharedPreference(DialWheelCustomerActivity.this, "Bidthursday", "");
                            Utility.setStringSharedPreference(DialWheelCustomerActivity.this, "Bidfriday", "");
                            Utility.setStringSharedPreference(DialWheelCustomerActivity.this, "Bidsaturday", "");
                            Utility.setStringSharedPreference(DialWheelCustomerActivity.this, "Bidsunday", "");


                            Utility.setStringSharedPreference(DialWheelCustomerActivity.this,"BeatSkCode","");
                            Utility.setStringSharedPreference(DialWheelCustomerActivity.this, "ItemQJson" ,"");

                            Utility.setStringSharedPreference(DialWheelCustomerActivity.this, "CompanyId", "");



                            clearCartData();
                            SharedPreferences sharedPreferences = getSharedPreferences("dialcount", Context.MODE_PRIVATE);
                            SharedPreferences.Editor edit = sharedPreferences.edit();
                            edit.clear().commit();


                            DialWheelCustomerActivity.this.finish();


                            startActivity(new Intent(DialWheelCustomerActivity.this, LoginActivity_Nav.class));








                            return true;


                        }

                        else if (id == R.id.setting) {
                            startActivity(new Intent(DialWheelCustomerActivity.this, SettingActivity.class));
                            //   HomeActivity.this.finish();
                            return true;
                        }



                        else if (id == R.id.mysales) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(DialWheelCustomerActivity.this, MySalesActivity.class));

                            //    HomeActivity.this.finish();
                            return true;
                        }


                        else if (id == R.id.mybid) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(DialWheelCustomerActivity.this, DaysBidActivity.class));

                            //     HomeActivity.this.finish();

                            //HomeActivity.this.finish();
                            return true;
                        }

                        else if (id == R.id.redeem_point) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(DialWheelCustomerActivity.this, ReedemPointActivity.class));

                            //           HomeActivity.this.finish();
                            return true;
                        }

                        else if (id == R.id.reward_point_menu) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(DialWheelCustomerActivity.this, RewardItemActivity.class));

                            //     HomeActivity.this.finish();
                            return true;
                        }




                        else if (id == R.id.action_task) {

//                            startActivity(new Intent(HomeActivity.this, MyBeatActivity.class));
                            startActivity(new Intent(DialWheelCustomerActivity.this, ActionTaskMultiActivity.class));

                            //           HomeActivity.this.finish();
                            return true;
                        }


                        else
                            return false;
                    }
                });
            }
        });


        Intent intent = getIntent();
         Id = intent.getStringExtra("Id");
         OrderId = intent.getStringExtra("OrderId");
         Point = intent.getStringExtra("Point");
        System.out.println("OrderId:::"+OrderId);
        // Get the application context
        mContext = getApplicationContext();

        // Get the activity
        mActivity = DialWheelCustomerActivity.this;

        // Get the widgets reference from XML layout
        mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
       luckyWheelView = (LuckyWheelView) findViewById(R.id.luckyWheel);

        LuckyItem luckyItem1 = new LuckyItem();
        luckyItem1.text = "10";
        luckyItem1.icon = R.drawable.sk_icon;
        luckyItem1.color = 0xffFFF3E0;
        data.add(luckyItem1);

        LuckyItem luckyItem2 = new LuckyItem();
        luckyItem2.text = "20";
        luckyItem2.icon = R.drawable.sk_icon;
        luckyItem2.color = 0xffFFE0B2;
        data.add(luckyItem2);

        LuckyItem luckyItem3 = new LuckyItem();
        luckyItem3.text = "30";
        luckyItem3.icon = R.drawable.sk_icon;
        luckyItem3.color = 0xffFFCC80;
        data.add(luckyItem3);

        //////////////////
        LuckyItem luckyItem4 = new LuckyItem();
        luckyItem4.text = "40";
        luckyItem4.icon = R.drawable.sk_icon;
        luckyItem4.color = 0xffFFF3E0;
        data.add(luckyItem4);

        LuckyItem luckyItem5 = new LuckyItem();
        luckyItem5.text = "50";
        luckyItem5.icon = R.drawable.sk_icon;
        luckyItem5.color = 0xffFFE0B2;
        data.add(luckyItem5);

        LuckyItem luckyItem6 = new LuckyItem();
        luckyItem6.text = "60";
        luckyItem6.icon = R.drawable.sk_icon;
        luckyItem6.color = 0xffFFCC80;
        data.add(luckyItem6);
        //////////////////

        //////////////////////
        LuckyItem luckyItem7 = new LuckyItem();
        luckyItem7.text = "70";
        luckyItem7.icon = R.drawable.sk_icon;
        luckyItem7.color = 0xffFFF3E0;
        data.add(luckyItem7);

        LuckyItem luckyItem8 = new LuckyItem();
        luckyItem8.text = "80";
        luckyItem8.icon = R.drawable.sk_icon;
        luckyItem8.color = 0xffFFE0B2;
        data.add(luckyItem8);


        LuckyItem luckyItem9 = new LuckyItem();
        luckyItem9.text = "90";
        luckyItem9.icon = R.drawable.sk_icon;
        luckyItem9.color = 0xffFFCC80;
        data.add(luckyItem9);
        ////////////////////////

        LuckyItem luckyItem10 = new LuckyItem();
        luckyItem10.text = "100";
        luckyItem10.icon = R.drawable.sk_icon;
        luckyItem10.color = 0xffFFE0B2;
        data.add(luckyItem10);

        LuckyItem luckyItem11 = new LuckyItem();
        luckyItem11.text = "200";
        luckyItem11.icon = R.drawable.sk_icon;
        luckyItem11.color = 0xffFFE0B2;
        data.add(luckyItem11);

        LuckyItem luckyItem12 = new LuckyItem();
        luckyItem12.text = "300";
        luckyItem12.icon = R.drawable.sk_icon;
        luckyItem12.color = 0xffFFE0B2;
        data.add(luckyItem12);

        /////////////////////

        luckyWheelView.setData(data);
        luckyWheelView.setRound(getRandomRound());

        /*luckyWheelView.setLuckyWheelBackgrouldColor(0xff0000ff);
        luckyWheelView.setLuckyWheelTextColor(0xffcc0000);
        luckyWheelView.setLuckyWheelCenterImage(getResources().getDrawable(R.drawable.icon));
        luckyWheelView.setLuckyWheelCursorImage(R.drawable.ic_cursor);*/


       /* luckyWheelView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = getRandomIndex();
                luckyWheelView.startLuckyWheelWithTargetIndex(index);
            }
        });*/

        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               callAqueryDialExpired();

            }
        });

        luckyWheelView.setLuckyRoundItemSelectedListener(new LuckyWheelView.LuckyRoundItemSelectedListener() {
            @Override
            public void LuckyRoundItemSelected(int index) {
                String number = null;
                System.out.println("Index::"+index);
                if(index==0)
                {
                    number="10";
                    loadPhoto(R.drawable.sk_icon,number);

                }else if(index==1)
                {
                    number="10";
                    loadPhoto(R.drawable.sk_icon,number);

                }

                else if(index==2)
                {
                    number="20";
                    loadPhoto(R.drawable.sk_icon,number);
                }
                else if(index==3)
                {
                    number="30";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==4)
                {
                    number="40";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==5)
                {
                    number="50";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==6)
                {
                    number="60";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==7)
                {
                    number="70";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==8)
                {
                    number="80";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==9)
                {
                    number="90";
                    loadPhoto(R.drawable.sk_icon,number);
                }else if(index==10)
                {
                    number="100";
                    loadPhoto(R.drawable.sk_icon,number);
                }
              //  Toast.makeText(getApplicationContext(), number, Toast.LENGTH_SHORT).show();

              // callPostDialPoint();

            }
        });
    }



    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(DialWheelCustomerActivity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }

    private void callPostDialPoint() {
        showLoading();
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(DialWheelCustomerActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        JSONObject jsonObject2 = new JSONObject();

        try {

            jsonObject2.put("Id", Id);

        } catch (Exception e) {

        }

       String url=Constant.BASE_URL+"DialPoint?Id="+Id;
        new AQuery(getApplicationContext()).put(url, jsonObject2, JSONObject.class, new AjaxCallback<JSONObject>() {


            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {

                System.out.println("Json::"+json.toString());
                if (json == null) {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }

                    Toast.makeText(DialWheelCustomerActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(DialWheelCustomerActivity.this, "Get point add to your wallet", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DialWheelCustomerActivity.this, MyDialListActivity.class));
                    DialWheelCustomerActivity.this.finish();


                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }


                    // Toast.makeText(BeatOrderActivity.this, json.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        });

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

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.custom_layout,null);
        mPopupWindow = new PopupWindow(customView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


        if(Build.VERSION.SDK_INT>=21){
            mPopupWindow.setElevation(5.0f);
        }

       // ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
        ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ok_button);
        ImageView tvImage = (ImageView) customView.findViewById(R.id.image_lucky);
        TextView tvPoint = (TextView) customView.findViewById(R.id.tvPoint);
        tvImage.setImageResource(img);

        String numberColor = " <i><font color=#00897B>तुम्हे</font> </i><font color=#cc0029>"+number+"</font> <i><font color=#00897B> पॉइंट मिले है| २४ घंटे में आर्डर करे और पॉइंट को expire होने से बचाये ।</font></i>";
       // tvPoint.setText("You Got "+ Html.fromHtml(numberColor)+" Point");
       tvPoint.setText(Html.fromHtml(numberColor));
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Dismiss the popup window
                mPopupWindow.dismiss();
               callPostDialPoint();
            }
        });

        mPopupWindow.showAtLocation(mRelativeLayout, Gravity.CENTER,0,0);
    }


    public void callAqueryDialExpired() {



        String url=Constant.BASE_URL+"DialPoint?Id="+Id;
        System.out.println("UrlDial::"+url);
        showLoading();
        new AQuery(getApplicationContext()).ajax(url, null, JSONObject.class, new AjaxCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject json, AjaxStatus status) {


                System.out.println("JsonData"+json);
                if (json==null) {
                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }
                    Toast.makeText(DialWheelCustomerActivity.this, "Please Try again", Toast.LENGTH_SHORT).show();
                } else {

                    if (mDialog.isShowing()) {
                        animation.stop();
                        mDialog.dismiss();
                    }
                    try {
                        JSONObject  jsonObject = new JSONObject(json.toString());
                        String Message = jsonObject.getString("Message");
                        if(Message.trim().equalsIgnoreCase("Dial Available"))
                        {
                            System.out.println("Point::"+Point);
                            int point=Integer.parseInt(Point)/10;
                            System.out.println("Point Indexing::"+point);
                            int index =  point;
                            luckyWheelView.startLuckyWheelWithTargetIndex(index);

                        }else{
                            Toast.makeText(DialWheelCustomerActivity.this, "सॉरी , आपका डायल उपयोग न करने की वजह से expire  हो गया हे !!", Toast.LENGTH_SHORT).show();

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }



            }
        });


    }

    public void showLoading() {
        mDialog = new Dialog(DialWheelCustomerActivity.this);
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
        startActivity(new Intent(DialWheelCustomerActivity.this ,BeatOrderActivity.class));
        DialWheelCustomerActivity.this.finish();
    }

}
