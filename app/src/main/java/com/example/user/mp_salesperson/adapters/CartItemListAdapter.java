package com.example.user.mp_salesperson.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.TextUtils;
import com.example.user.mp_salesperson.CartActivity;
import com.example.user.mp_salesperson.Constant;
import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.bean.CartItemBean;
import com.example.user.mp_salesperson.customClasses.Utility;
import com.example.user.mp_salesperson.dial.DeliveryDialWheel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;


/**
 * Created by Krishna on 03-01-2017.
 */

public class CartItemListAdapter extends RecyclerView.Adapter<CartItemListAdapter.ViewHolder> {
    private CartItemBean cartItemBean;
    private Context context;
    private int ivWidth;
    private int ivHeight;
    private TextView tvTotalItemPrice;
    private TextView tvTotalItemQty;
    private TextView tvGrandTotal;
    private TextView tvDialTotal;
    private TextView tvDeliveryCharges;
    int  dial=0;
    int  count=0;
    private TextView tvDpGrandTotal;
    boolean isFlag=false;
    int a=0,b=0,c=0,d=0,e=0,f=0,g=0,h=0,i=0,j=0,k=0;
    int deliveryCharges;
    String language;
    int totalDp;
    int  gainPoint;
    public CartItemListAdapter(Context context, CartItemBean cartItemBean, int ivWidth, int ivHeight, TextView tvTotalItemPrice, TextView tvTotalItemQty, TextView tvGrandTotal, TextView tvDeliveryCharges, int deliveryCharges, TextView tvDpGrandTotal, TextView tvDialTotal) {
        this.cartItemBean = cartItemBean;
        this.context = context;
        this.ivWidth = ivWidth;
        this.ivHeight = ivHeight;
        this.tvTotalItemPrice = tvTotalItemPrice;
        this.tvTotalItemQty = tvTotalItemQty;
        this.tvGrandTotal = tvGrandTotal;
        this.tvDialTotal = tvDialTotal;
        this.tvDeliveryCharges = tvDeliveryCharges;
        this.deliveryCharges = deliveryCharges;
        this.tvDpGrandTotal = tvDpGrandTotal;


    }

    @Override
    public CartItemListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_check_out_row, viewGroup, false);
        return new CartItemListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final CartItemListAdapter.ViewHolder viewHolder, final int i) {

        //  - button false
       /*   count =   Utility.getIntSharedPreferences(context,"DialCount");
        if(count==0)
        {

        }else
        {
            viewHolder.ivMinusImage.setEnabled(false);
        }*/


        viewHolder.tvItemName.setText(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemname());

        Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Kruti_Dev_010.ttf");

        language =  Utility.getStringSharedPreferences(context, "MultiLaguage");
        if (language.equals("m")) {

            viewHolder.tvItemHindiName.setTypeface(tf);
            viewHolder.tvItemHindiName.setVisibility(View.VISIBLE);

        }

        if (language.equals("s")) {

            viewHolder.tvItemHindiName.setVisibility(View.GONE);
        }


        viewHolder.tvItemHindiName.setText(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemHindiname());



        if (cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint().isEmpty()) {
            viewHolder.tvDpPoint.setVisibility(View.GONE);

        }


        totalDp = Integer.parseInt(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint()) * cartItemBean.getmCartItemInfos().get(i).getQty();

     //   viewHolder.tvDpPoint.setText(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint());

        viewHolder.tvDpPoint.setText("Total Dream Point "+totalDp);
        if (!TextUtils.isNullOrEmpty(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getLogoUrl()))
            //Picasso.with(context).load(Constant.BASE_URL_Images1  + itemListArrayList.get(i).getItemNumber() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.ivItemImage);

            System.out.println("ImageUrl::"+Constant.BASE_URL_Images1 + cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemNumber() + ".jpg");
       // Picasso.with(context).load(Constant.BASE_URL_Images + cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getLogoUrl() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.ivItemImage);
            Picasso.with(context).load(Constant.BASE_URL_Images1 + cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemNumber() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.ivItemImage);
        viewHolder.tvMoqMrp.setText("MOQ: " + cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getMinOrderQty() + " | MRP: " + cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getPrice());
        String text = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format(Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice())) + "</font>" + " | Margins: " + (new DecimalFormat("##.##").format((((Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getPrice()) - Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice())) / Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getPrice())) * 100))) + "%";
        viewHolder.tvRateMargins.setText(Html.fromHtml(text));

        viewHolder.ivMinusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                isFlag=true;
                int itemQuantity = Integer.parseInt(viewHolder.tvselectedItemQuantity.getText().toString());
                if (itemQuantity > 0) {
                    itemQuantity -= Integer.parseInt(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getMinOrderQty());
                    viewHolder.tvselectedItemQuantity.setText("" + itemQuantity);

                    String price = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format((itemQuantity * Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice())));

                    viewHolder.tvSelectedItemPrice.setText(Html.fromHtml(price));

                    String status = ((CartActivity) context).addItemInCartItemArrayList(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId(), itemQuantity, Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice()), cartItemBean.getmCartItemInfos().get(i).getSelectedItem(), deliveryCharges, Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint()) , cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getWarehouseId(), cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getCompanyId(),Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getPrice()));

                 //   String status2 = ((HomeActivity) context).addItemInCartItemArrayList(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId(), itemQuantity, Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice()), cartItemBean.getmCartItemInfos().get(i).getSelectedItem(), deliveryCharges, Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint()));



                    gainPoint =   Utility.getIntSharedPreferences(context,"GainPoint");
                    tvDpGrandTotal.setText(("Dream points total : " + new DecimalFormat("##.##").format(((CartActivity) context).getCartItem().getTotalDpPoints()+gainPoint)));


                    tvTotalItemQty.setText("" + (int) ((CartActivity) context).getCartItem().getTotalQuantity());

                    totalDp = Integer.parseInt(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint()) * cartItemBean.getmCartItemInfos().get(i).getQty();


                    viewHolder.tvDpPoint.setText("Total Dream Point "+totalDp);


                    Log.i(Constant.Tag, status);

                    if (((CartActivity) context).getCartItem().getTotalPrice() < 2000) {
                        deliveryCharges = 10;
                    } else {
                        deliveryCharges = 0;
                    }

                    tvDeliveryCharges.setText("Delivery Charges: " + deliveryCharges);

                    tvGrandTotal.setText("Total: " + (int) (((CartActivity) context).getCartItem().getTotalPrice() + deliveryCharges));
                    DialSetUp((int) (((CartActivity) context).getCartItem().getTotalPrice() + deliveryCharges));
                    tvTotalItemPrice.setText("Grand Total: " + new DecimalFormat("##.##").format(((CartActivity) context).getCartItem().getTotalPrice() +  + deliveryCharges));




                    String jsonString = Utility.getStringSharedPreferences(context, "ItemQJson");
                    try {
                        JSONObject jsonObject;
//                        JSONObject jsonObject = new JSONObject(jsonString.toString());

                        if (jsonString.isEmpty()) {
                            jsonObject = new JSONObject();

                        }
                        else {
                            jsonObject = new JSONObject(jsonString.toString());

                        }

                        //jsonObject.put(""+getItemId(i), viewHolder.tvselectedItemQuantity.getText().toString());

                        jsonObject.put(""+cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId(), viewHolder.tvselectedItemQuantity.getText().toString());
                        Utility.setStringSharedPreference(context, "ItemQJson" ,jsonObject.toString());

                    } catch (JSONException e) {
                        Toast.makeText(context, "Json error"+e.toString(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }

                 //   Toast.makeText(context, "Json Q "+Utility.getStringSharedPreferences(context, "ItemQJson"), Toast.LENGTH_SHORT).show();



                    //  tvDeliveryCharges.setText("Delivery Charges: " + getTotalDelivery(((CartActivity) context).getCartItem().getTotalPrice()));



                    if (itemQuantity == 0) {

                    //    ((CartActivity) context).removeItemfromCart(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId());

                    }


                }
            }
        });

        viewHolder.ivPlusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isFlag=true;
                int itemQuantity = Integer.parseInt(viewHolder.tvselectedItemQuantity.getText().toString());
                itemQuantity += Integer.parseInt(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getMinOrderQty());
                viewHolder.tvselectedItemQuantity.setText("" + itemQuantity);
                String price = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format((itemQuantity * Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice())));
                viewHolder.tvSelectedItemPrice.setText(Html.fromHtml(price));

                String status = ((CartActivity) context).addItemInCartItemArrayList(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId(), itemQuantity, Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice()), cartItemBean.getmCartItemInfos().get(i).getSelectedItem(), deliveryCharges, Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint()) , cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getWarehouseId(), cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getCompanyId(),Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getPrice()));


              //  String status2 = ((HomeActivity) context).addItemInCartItemArrayList(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId(), itemQuantity, Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice()), cartItemBean.getmCartItemInfos().get(i).getSelectedItem(), deliveryCharges, Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint()));



                gainPoint =   Utility.getIntSharedPreferences(context,"GainPoint");
                tvDpGrandTotal.setText(("Dream points total : " + new DecimalFormat("##.##").format(((CartActivity) context).getCartItem().getTotalDpPoints()+gainPoint)));


                tvTotalItemQty.setText("" + (int) ((CartActivity) context).getCartItem().getTotalQuantity());
                Log.i(Constant.Tag, status);
                totalDp = Integer.parseInt(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getDreamPoint()) * cartItemBean.getmCartItemInfos().get(i).getQty();
                viewHolder.tvDpPoint.setText("Total Dream Point "+totalDp);
                if (((CartActivity) context).getCartItem().getTotalPrice() < 2000) {
                    deliveryCharges = 10;
                } else {
                    deliveryCharges = 0;
                }
                tvDeliveryCharges.setText("Delivery Charges: " + deliveryCharges);
                tvGrandTotal.setText("Total: " + (int) (((CartActivity) context).getCartItem().getTotalPrice() + deliveryCharges));
                DialSetUp((int) (((CartActivity) context).getCartItem().getTotalPrice() + deliveryCharges));
                tvTotalItemPrice.setText("Grand Total : " + new DecimalFormat("##.##").format(((CartActivity) context).getCartItem().getTotalPrice() +  + deliveryCharges));

                String jsonString = Utility.getStringSharedPreferences(context, "ItemQJson");
                try {
                    JSONObject jsonObject;
//                        JSONObject jsonObject = new JSONObject(jsonString.toString());

                    if (jsonString.isEmpty()) {
                        jsonObject = new JSONObject();
                    }
                    else {
                        jsonObject = new JSONObject(jsonString.toString());

                    }

                    //jsonObject.put(""+getItemId(i), viewHolder.tvselectedItemQuantity.getText().toString());

                    jsonObject.put(""+cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getItemId(), viewHolder.tvselectedItemQuantity.getText().toString());
                    Utility.setStringSharedPreference(context, "ItemQJson" ,jsonObject.toString());

                } catch (JSONException e) {
                    Toast.makeText(context, "Json error"+e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

             //   Toast.makeText(context, "Json Q "+Utility.getStringSharedPreferences(context, "ItemQJson"), Toast.LENGTH_SHORT).show();
                // getTotalDelivery(((CartActivity) context).getCartItem().getTotalPrice());
             //   tvDeliveryCharges.setText("Delivery Charges: " + getTotalDelivery(((CartActivity) context).getCartItem().getTotalPrice()));

            }
        });

        viewHolder.tvselectedItemQuantity.setText("" + cartItemBean.getmCartItemInfos().get(i).getQty());
        String price = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format((cartItemBean.getmCartItemInfos().get(i).getQty() * Double.parseDouble(cartItemBean.getmCartItemInfos().get(i).getSelectedItem().getUnitPrice())));
        viewHolder.tvSelectedItemPrice.setText(Html.fromHtml(price));

        tvTotalItemQty.setText("" + (int) ((CartActivity) context).getCartItem().getTotalQuantity());

        if (((CartActivity) context).getCartItem().getTotalPrice() < 2000) {
            deliveryCharges = 10;
        } else {
            deliveryCharges = 0;
        }
        tvDeliveryCharges.setText("Delivery Charges: " + deliveryCharges);
        tvGrandTotal.setText("Total: " + (int) (((CartActivity) context).getCartItem().getTotalPrice() + deliveryCharges));
        tvTotalItemPrice.setText("Grand Total :  " + new DecimalFormat("##.##").format(((CartActivity) context).getCartItem().getTotalPrice() +  + deliveryCharges));
        int totalAmount=(int) (((CartActivity) context).getCartItem().getTotalPrice() + deliveryCharges);
        DialSetUp(totalAmount);

        // tvDeliveryCharges.setText("Delivery Charges: " + getTotalDelivery(((CartActivity) context).getCartItem().getTotalPrice()));
        //  tvDeliveryCharges.setText("Delivery Charges: " + deliveryCharges);


    }

    private void DialSetUp(int totalAmount) {
       // System.out.println("totalAmount::"+totalAmount);
       /* final MediaPlayer mp = MediaPlayer.create(context, R.raw.coin);
        if (totalAmount <= 4999) {
            dial=0;
            System.out.println("Run");
            SharedPreferences sharedPreferences = context.getSharedPreferences("relybee", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.clear().commit();
            Toast.makeText(context, "For collecting dial, price should be greater then 5K ", Toast.LENGTH_SHORT).show();
        } else if (totalAmount >= 5000 & totalAmount < 9999) {
            dial=1;
            a++;
            //mp.start();
        } else if (totalAmount >= 10000 & totalAmount <= 14999) {
            dial=2;
            b++;
           // mp.start();
        } else if (totalAmount >= 15000 & totalAmount <= 19999) {
            dial=3;
            c++;
           // mp.start();
        } else if (totalAmount >= 20000 & totalAmount <= 24999) {
            dial=4;
           // mp.start();
        } else if (totalAmount >= 25000 & totalAmount <= 29999) {
            dial=5;
           // mp.start();
        } else if (totalAmount >= 30000 & totalAmount <= 34999) {
            dial=6;
           // mp.start();
        } else if (totalAmount >= 35000 & totalAmount <= 39999) {
            dial=7;
            //mp.start();
        } else if (totalAmount >= 40000 & totalAmount <= 44999) {
            dial=8;
            //mp.start();
        } else if (totalAmount >= 45000 & totalAmount <= 49999) {
            dial=9;
            //mp.start();
        }else if (totalAmount >= 50000) {
            dial=10;
           // mp.start();
        }else
        {
           *//* System.out.println("Run");
            SharedPreferences sharedPreferences = context.getSharedPreferences("relybee", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.clear().commit();*//*
        }
        */

      /*  System.out.println("a:"+1+"\nb:"+b);
        if(a==1||b==1||c==1) {
            mp.start();
        }*/
        int dial= totalAmount/4000;

        if(dial==0)
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences("dialcount", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.clear().commit();
        }
      // int amoount= totalAmount-5000;
        if(totalAmount >= 3000 & totalAmount <= 4000) {
            Toast.makeText(context, R.string.left_free_dial, Toast.LENGTH_SHORT).show();
        }
        gainPoint =   Utility.getIntSharedPreferences(context,"GainPoint");
        System.out.println("GainPoint:::"+gainPoint);
        tvDpGrandTotal.setText("Dream points total : " + (int) (((CartActivity) context).getCartItem().getTotalDpPoints()+gainPoint));
        int  count =   Utility.getIntSharedPreferences(context,"DialCount");
        Log.e("Count::", String.valueOf(count));
        Log.e("Dial::", String.valueOf(dial));

        if(dial>=count)
        {
             dial=dial-count;
        }else
        {
            //dial=count-dial;
            SharedPreferences sharedPreferences = context.getSharedPreferences("dialcount", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.clear().commit();

        }
        tvDialTotal.setText(String.valueOf(dial));

    }

    @Override
    public int getItemCount() {
        return cartItemBean.getmCartItemInfos().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivItemImage;
        private TextView tvItemName;
        private TextView tvMoqMrp;
        private TextView tvRateMargins;
        private TextView tvSelectedItemPrice;
        private TextView tvselectedItemQuantity;
        private ImageView ivMinusImage;
        private ImageView ivPlusImage;

        private TextView tvItemHindiName;



        private TextView tvDpPoint;


        public ViewHolder(View view) {
            super(view);
            ivItemImage = (ImageView) view.findViewById(R.id.item_row_item_logo_iv);
            tvItemName = (TextView) view.findViewById(R.id.itemlist_item_name);
            tvMoqMrp = (TextView) view.findViewById(R.id.moq_mrp_tv);
            tvRateMargins = (TextView) view.findViewById(R.id.cost_margins_tv);
            tvSelectedItemPrice = (TextView) view.findViewById(R.id.item_list_row_total_cost_tv);
            tvselectedItemQuantity = (TextView) view.findViewById(R.id.item_row_quantity_tv);
            ivMinusImage = (ImageView) view.findViewById(R.id.item_row_minus_icon);
            ivPlusImage = (ImageView) view.findViewById(R.id.item_row_plus_icon);
            tvItemHindiName = (TextView) view.findViewById(R.id.itemlist_item_name_hindi);
            tvDpPoint = (TextView) view.findViewById(R.id.dp_point);
        }
    }



    public int getTotalDelivery(double amount) {

        double totalAmount = amount;
        int returnCharges;

        try {
            JSONArray jsonArray = new JSONArray(Constant.DELIVERY_CHARGE_JSON.toString());
            JSONObject jsonObject = new JSONObject();

            for (int i = 0; i < jsonArray.length(); i++) {

                jsonObject = jsonArray.getJSONObject(i);
                String min = jsonObject.getString("min_Amount");
                String max = jsonObject.getString("max_Amount");
                String isDeleted = jsonObject.getString("isDeleted");

                String deliveryChargeJsonString = jsonObject.getString("del_Charge");


                int minInt = Integer.parseInt(min);

                int maxInt = Integer.parseInt(max);

                if (isDeleted.equals("false")) {

                    if (totalAmount >= minInt && totalAmount <= maxInt) {

                        deliveryCharges = Integer.parseInt(deliveryChargeJsonString);

                        returnCharges = Integer.parseInt(deliveryChargeJsonString);


                        //    Toast.makeText(MainActivity.this, "20001 and 50000 delivery charge 1000", Toast.LENGTH_SHORT).show();

//                            textView.setText("20001 and 50000 delivery charge "+deliveryCharges);


                        //    textView.setText(minInt+"\n"+maxInt);


                    }

                }
                //  Toast.makeText(MainActivity.this, min+"\n"+max, Toast.LENGTH_SHORT).show();
            }
            //  Toast.makeText(MainActivity.this, jsonArray.toString(), Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
            //     Toast.makeText(MainActivity.this, "Json Error"+e.toString(), Toast.LENGTH_SHORT).show();

        }


        return deliveryCharges;
    }



}
