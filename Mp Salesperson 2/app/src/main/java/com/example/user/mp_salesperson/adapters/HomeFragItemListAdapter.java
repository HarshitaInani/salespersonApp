package com.example.user.mp_salesperson.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.example.user.mp_salesperson.Constant;
import com.example.user.mp_salesperson.HomeActivity;
import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.bean.CartItemBean;
import com.example.user.mp_salesperson.bean.CartItemInfo;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.ItemList;
import com.example.user.mp_salesperson.customClasses.Utility;
import com.example.user.mp_salesperson.fragment.SubcatimageFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by Krishna on 03-01-2017.
 */

public class HomeFragItemListAdapter extends RecyclerView.Adapter<HomeFragItemListAdapter.ViewHolder> {
    private ArrayList<ItemList> itemListArrayList;
    private Context context;
    private int ivWidth;
    private int ivHeight;
    private TextView tvTotalItemPrice;
    private TextView tvTotalItemQty;
    private TextView show_popup;
    private TextView tvTotalDp;
    AsyncTask<String, Void, JSONObject> getItemOffer;
    AlertDialog customAlertDialog;

    private double deliveryCharges = 10;
    FragmentManager fragmentManager;
    String language;
    int  count=0;
    TextToSpeech tospeech;
    int result;
    String text;
    public HomeFragItemListAdapter(Context context, ArrayList<ItemList> itemListArrayList, int ivWidth, int ivHeight, TextView tvTotalItemPrice, TextView tvTotalItemQty , TextView tvTotalDp, TextView show_popup, FragmentManager fragmentManager) {

        this.itemListArrayList = itemListArrayList;
        this.context = context;
        this.ivWidth = ivWidth;
        this.ivHeight = ivHeight;

        this.tvTotalItemPrice = tvTotalItemPrice;
        this.tvTotalDp= tvTotalDp;
        this.show_popup= show_popup;
        this.fragmentManager= fragmentManager;
        this.tvTotalItemQty = tvTotalItemQty;
    }

    @Override
    public HomeFragItemListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_home_frag_new, viewGroup, false);
        return new HomeFragItemListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final HomeFragItemListAdapter.ViewHolder viewHolder, final int i) {



        //  - button false
       /* count =   Utility.getIntSharedPreferences(context,"DialCount");
        if(count==0)
        {

        }else
        {
            viewHolder.ivMinusImage.setEnabled(false);
        }
*/




        try {









            System.out.println("itemListArrayList::::"+itemListArrayList);

            viewHolder.tvItemName.setText(itemListArrayList.get(i).getItemname());


            Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/Kruti_Dev_010.ttf");

           language =  Utility.getStringSharedPreferences(context, "MultiLaguage");


            if (language.equals("m")) {

                viewHolder.tvItemHindiName.setTypeface(tf);
                viewHolder.tvItemHindiName.setVisibility(View.VISIBLE);

            }

            if (language.equals("s")) {


                viewHolder.tvItemHindiName.setVisibility(View.GONE);
            }


            viewHolder.tvItemHindiName.setText(itemListArrayList.get(i).getItemHindiname());




            if (itemListArrayList.get(i).getDreamPoint().isEmpty()) {

                viewHolder.tvDpPoint.setVisibility(View.GONE);

            }

            System.out.println("Offer:::"+itemListArrayList.get(i).getIsOffer());
            if (itemListArrayList.get(i).getIsOffer().equalsIgnoreCase("true"))
            {
                viewHolder.ivSpecialOfer.setVisibility(View.VISIBLE);
                viewHolder.ivSpecialOfer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        getItemOffer = new GetCallItemOffer().execute(itemListArrayList.get(i).getItemId(),itemListArrayList.get(i).getCompanyId());

                    }
                });
            }





            viewHolder.tvDpPoint.setText("Dream Point "+itemListArrayList.get(i).getDreamPoint());






            ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(context, Constant.CART_ITEM_ARRAYLIST_PREF, context.MODE_PRIVATE);
            CartItemBean mCartItemBean = mCartItemArraylistPref.getObject(Constant.CART_ITEM_ARRAYLIST_PREF_OBJ, CartItemBean.class);
            ArrayList<CartItemInfo> mCartItemInfos = mCartItemBean != null ? mCartItemBean.getmCartItemInfos() : new ArrayList<CartItemInfo>();
            if (mCartItemInfos == null) {
                mCartItemInfos = new ArrayList<>();
            }

            boolean isItemFound = false;
            if (!itemListArrayList.isEmpty()) {
                for (int j = 0; j < mCartItemInfos.size(); j++) {
                    if (itemListArrayList.get(i).getItemId().equalsIgnoreCase(mCartItemInfos.get(j).getItemId())) {
                        isItemFound = true;
                        int itemQuantity = mCartItemInfos.get(j).getQty();
                        if (itemQuantity > 0) {
                            if (itemQuantity > 0)
                                viewHolder.tvselectedItemQuantity.setText("" + itemQuantity);
                            else
                                viewHolder.tvselectedItemQuantity.setText("" + itemQuantity);
                            String price = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format((itemQuantity * Double.parseDouble(itemListArrayList.get(i).getUnitPrice())));
                            viewHolder.tvSelectedItemPrice.setText(Html.fromHtml(price));
                            if (((HomeActivity) context).getCartItem().getTotalPrice() < 2000) {
                                deliveryCharges = 10;
                            } else {
                                deliveryCharges = 0;
                            }
                            ShowPopup((int) ((HomeActivity) context).getCartItem().getTotalPrice());
                            tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(((HomeActivity) context).getCartItem().getTotalPrice()));
                            tvTotalItemQty.setText("" + (int) ((HomeActivity) context).getCartItem().getTotalQuantity());
                        }
                        break;
                    } else {
                        isItemFound = false;
                    }
                }
            }



            if (!isItemFound) {
                int itemQuantity = 0;
                viewHolder.tvselectedItemQuantity.setText("" + itemQuantity);
                String price = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format((itemQuantity * Double.parseDouble(itemListArrayList.get(i).getUnitPrice())));
                viewHolder.tvSelectedItemPrice.setText(Html.fromHtml(price));
                if (((HomeActivity) context).getCartItem().getTotalPrice() < 2000) {
                    deliveryCharges = 10;
                } else {
                    deliveryCharges = 0;
                }
                ShowPopup((int) ((HomeActivity) context).getCartItem().getTotalPrice());
                tvTotalItemPrice.setText("Total: " + new DecimalFormat("##.##").format(((HomeActivity) context).getCartItem().getTotalPrice()));
                tvTotalItemQty.setText("" + (int) ((HomeActivity) context).getCartItem().getTotalQuantity());
            }
            if (!TextUtils.isNullOrEmpty(itemListArrayList.get(i).getLogoUrl()))
            Picasso.with(context).load(Constant.BASE_URL_Images1 + itemListArrayList.get(i).getItemNumber() + ".jpg").resize(ivWidth, ivHeight).into(viewHolder.ivItemImage);
          viewHolder.tvMoqMrp.setText("MOQ: " + itemListArrayList.get(i).getMinOrderQty() + " | MRP: " + (int) ((Double.parseDouble(itemListArrayList.get(i).getPrice()))));
        String text = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format(Double.parseDouble(itemListArrayList.get(i).getUnitPrice())) + "</font>" + " | Margins: " + (new DecimalFormat("##.##").format((((Double.parseDouble(itemListArrayList.get(i).getPrice()) - Double.parseDouble(itemListArrayList.get(i).getUnitPrice())) / Double.parseDouble(itemListArrayList.get(i).getPrice())) * 100))) + "%";
        viewHolder.tvRateMargins.setText(Html.fromHtml(text));


            viewHolder.ivItemImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Fragment fragment = Fragment.instantiate(context, SubcatimageFragment.class.getName());
                    Bundle bundle = new Bundle();
                    bundle.putString("name", itemListArrayList.get(i).getItemname());
                    System.out.println("name123"+itemListArrayList.get(i).getItemname());
                    bundle.putString("image",Constant.BASE_URL_Images1 + itemListArrayList.get(i).getItemNumber() + ".jpg");
                    fragment.setArguments(bundle);
                    fragmentManager.beginTransaction().addToBackStack(fragmentManager.getFragments().toString()).replace(R.id.content_frame, fragment, "HomeFragItemList").commit();
                }
            });



        viewHolder.ivMinusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

          try {
              int itemQuantity = Integer.parseInt(viewHolder.tvselectedItemQuantity.getText().toString());
              if (itemQuantity == 0) {

                 // ((HomeActivity) context).removeItemfromCart(itemListArrayList.get(i).getItemId());
              }

              if (itemQuantity > 0) {
                  itemQuantity -= Integer.parseInt(itemListArrayList.get(i).getMinOrderQty());
                  viewHolder.tvselectedItemQuantity.setText("" + itemQuantity);
                  String price = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format((itemQuantity * Double.parseDouble(itemListArrayList.get(i).getUnitPrice())));
                  viewHolder.tvSelectedItemPrice.setText(Html.fromHtml(price));
                  if (((HomeActivity) context).getCartItem().getTotalPrice() < 2000) {
                      deliveryCharges = 10;
                  } else {
                      deliveryCharges = 0;
                  }

                  Context context1 = ((HomeActivity) context).getApplicationContext();

                  Resources res = ((HomeActivity) context).getLocalizedResources(context1, new Locale("hi"));
                  String s = res.getString(R.string.category);

                  String status = ((HomeActivity) context).addItemInCartItemArrayList(itemListArrayList.get(i).getItemId(), itemQuantity, Double.parseDouble(itemListArrayList.get(i).getUnitPrice()), itemListArrayList.get(i), deliveryCharges, Double.parseDouble(itemListArrayList.get(i).getDreamPoint()), itemListArrayList.get(i).getWarehouseId(), itemListArrayList.get(i).getCompanyId(),Double.parseDouble(itemListArrayList.get(i).getPrice()) );

                  ShowPopup((int) ((HomeActivity) context).getCartItem().getTotalPrice());
                  tvTotalItemPrice.setText("Total : " + new DecimalFormat("##.##").format(((HomeActivity) context).getCartItem().getTotalPrice()));
                  tvTotalItemQty.setText("" + (int) ((HomeActivity) context).getCartItem().getTotalQuantity());

                  tvTotalDp.setText("Dp : " + new DecimalFormat("##.##").format(((HomeActivity) context).getCartItem().getTotalDpPoints()));
                  Log.i(Constant.Tag, status);
                  String jsonString = Utility.getStringSharedPreferences(context, "ItemQJson");
                  try {
                      JSONObject jsonObject;
//                        JSONObject jsonObject = new JSONObject(jsonString.toString());

                      if (jsonString.isEmpty()) {
                          jsonObject = new JSONObject();
                      } else {
                          jsonObject = new JSONObject(jsonString.toString());
                      }

                      //jsonObject.put(""+getItemId(i), viewHolder.tvselectedItemQuantity.getText().toString());

                      jsonObject.put("" + itemListArrayList.get(i).getItemId(), viewHolder.tvselectedItemQuantity.getText().toString());
                      Utility.setStringSharedPreference(context, "ItemQJson", jsonObject.toString());
                  } catch (JSONException e) {
                      Toast.makeText(context, "Json error" + e.toString(), Toast.LENGTH_SHORT).show();
                      e.printStackTrace();
                  }

                  //   Toast.makeText(context, "Json Q "+Utility.getStringSharedPreferences(context, "ItemQJson"), Toast.LENGTH_SHORT).show();



                  if (itemQuantity == 0) {

                     // ((HomeActivity) context).removeItemfromCart(itemListArrayList.get(i).getItemId());

                  }



              }


          }catch (IndexOutOfBoundsException e) {

              Log.e("Crash", "crash");

              context.startActivity(new Intent(context, HomeActivity.class));


          } catch (Exception e) {

              Log.e("Crash", "crash");

              context.startActivity(new Intent(context, HomeActivity.class));


          }
            }
        });



        viewHolder.ivPlusImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

try {
    int itemQuantity = Integer.parseInt(viewHolder.tvselectedItemQuantity.getText().toString());
    itemQuantity += Integer.parseInt(itemListArrayList.get(i).getMinOrderQty());
    viewHolder.tvselectedItemQuantity.setText("" + itemQuantity);
    String price = "<font color=#FF4500>&#8377; " + new DecimalFormat("##.##").format((itemQuantity * Double.parseDouble(itemListArrayList.get(i).getUnitPrice())));
    viewHolder.tvSelectedItemPrice.setText(Html.fromHtml(price));

    if (((HomeActivity) context).getCartItem().getTotalPrice() < 2000) {
        deliveryCharges = 10;
    } else {
        deliveryCharges = 0;
    }

    String status = ((HomeActivity) context).addItemInCartItemArrayList(itemListArrayList.get(i).getItemId(), itemQuantity, Double.parseDouble(itemListArrayList.get(i).getUnitPrice()), itemListArrayList.get(i), deliveryCharges, Double.parseDouble(itemListArrayList.get(i).getDreamPoint())  , itemListArrayList.get(i).getWarehouseId(), itemListArrayList.get(i).getCompanyId() ,Double.parseDouble(itemListArrayList.get(i).getPrice()));
    ShowPopup((int) ((HomeActivity) context).getCartItem().getTotalPrice());

    tvTotalItemPrice.setText("Total : " + new DecimalFormat("##.##").format(((HomeActivity) context).getCartItem().getTotalPrice()));
    tvTotalItemQty.setText("" + (int) ((HomeActivity) context).getCartItem().getTotalQuantity());


    tvTotalDp.setText("Dp : " + new DecimalFormat("##.##").format(((HomeActivity) context).getCartItem().getTotalDpPoints()));

    Log.i(Constant.Tag, status);


  //  Toast.makeText(context, ""+((HomeActivity) context).getCartItem().getTotalPrice(), Toast.LENGTH_SHORT).show();

    String jsonString = Utility.getStringSharedPreferences(context, "ItemQJson");
    try {
        JSONObject jsonObject;
//                        JSONObject jsonObject = new JSONObject(jsonString.toString());

        if (jsonString.isEmpty()) {
            jsonObject = new JSONObject();

        } else {
            jsonObject = new JSONObject(jsonString.toString());

        }

        //jsonObject.put(""+getItemId(i), viewHolder.tvselectedItemQuantity.getText().toString());

        jsonObject.put("" + itemListArrayList.get(i).getItemId(), viewHolder.tvselectedItemQuantity.getText().toString());


        Utility.setStringSharedPreference(context, "ItemQJson", jsonObject.toString());

    } catch (JSONException e) {
        Toast.makeText(context, "Json error" + e.toString(), Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    //   Toast.makeText(context, "Json Q "+Utility.getStringSharedPreferences(context, "ItemQJson"), Toast.LENGTH_SHORT).show();

} catch (IndexOutOfBoundsException e) {

    Log.e("Crash4", "crash");

    context.startActivity(new Intent(context, HomeActivity.class));


} catch (Exception e) {

    Log.e("Crash3", "crash");

    context.startActivity(new Intent(context, HomeActivity.class));


}
            }
        });

      } catch (IndexOutOfBoundsException e) {

          Log.e("Crash2", "crash"+e.toString());

         // context.startActivity(new Intent(context, HomeActivity.class));
      } catch (Exception e) {

       //  context.startActivity(new Intent(context, HomeActivity.class));
          Log.e("Crash1", "crash"+e.toString());

      }


     /*   try {

        } catch (IndexOutOfBoundsException e) {
            context.startActivity(new Intent(context, HomeActivity.class));
        }
   */
        //speaker click Action
        tospeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInit(int i) {
                if (i == TextToSpeech.SUCCESS) {
                    result = tospeech.setLanguage(Locale.forLanguageTag("hin"));
                } else {
                    Toast.makeText(context, "Feature not supported in your Device", Toast.LENGTH_SHORT).show();
                }

            }
        });
        viewHolder.speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(context, "Feature not supported in your Device", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {

                        viewHolder.speaker_text.setText(viewHolder.tvItemName.getText().toString() + ( viewHolder.tvRateMargins.getText().toString()));
                        text = viewHolder.speaker_text.getText().toString();
                        tospeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);

                    } catch (IndexOutOfBoundsException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
    private void ShowPopup(int totalAmount) {
        boolean status1 = ((HomeActivity) context).showPopup(totalAmount);
        if(status1){
            show_popup.setVisibility(View.VISIBLE);
            show_popup.setText(R.string.left_free_dial);
        }else{
            show_popup.setVisibility(View.GONE);
        }
    }


    @Override
    public int getItemCount() {
        return itemListArrayList.size();
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
        private ImageView ivSpecialOfer;
        private ImageView speaker;
        private EditText speaker_text;
        public ViewHolder(View view) {
            super(view);
            ivItemImage = (ImageView) view.findViewById(R.id.item_row_item_logo_iv);
            tvItemName = (TextView) view.findViewById(R.id.itemlist_item_name);
            tvItemHindiName = (TextView) view.findViewById(R.id.itemlist_item_name_hindi);
            tvDpPoint = (TextView) view.findViewById(R.id.dp_point);
            tvMoqMrp = (TextView) view.findViewById(R.id.moq_mrp_tv);
            tvRateMargins = (TextView) view.findViewById(R.id.cost_margins_tv);
            tvSelectedItemPrice = (TextView) view.findViewById(R.id.item_list_row_total_cost_tv);
            tvselectedItemQuantity = (TextView) view.findViewById(R.id.item_row_quantity_tv);
            ivMinusImage = (ImageView) view.findViewById(R.id.item_row_minus_icon);
            ivPlusImage = (ImageView) view.findViewById(R.id.item_row_plus_icon);
            ivSpecialOfer = (ImageView) view.findViewById(R.id.special_offer);
            speaker_text = (EditText) view.findViewById(R.id.speaker_text);
            speaker = (ImageView) view.findViewById(R.id.speaker);
        }
    }

    public class GetCallItemOffer extends AsyncTask<String, Void, JSONObject> {
        Dialog mDialog;
        AnimationDrawable animation;

        @Override
        protected void onPreExecute() {
            mDialog = new Dialog(context);
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
        protected JSONObject doInBackground(String... params) {
            JSONObject jsonArrayFromUrl = null;
            String url=Constant.BASE_URL+"offer/GetOfferByItem?itemid="+params[0]+"&Companyid="+params[1];
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonObjectFromHttpUrlConnection(url, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            if (mDialog.isShowing()) {
                animation.stop();
                mDialog.dismiss();
            }
            if (jsonObject != null && jsonObject.length() > 0) {

                System.out.println("jsonArray::"+jsonObject);
                String offerMess;
                try {
                    String OfferType= jsonObject.getString("OfferType");
                    String NoOffreeQuantity= jsonObject.getString("NoOffreeQuantity");
                    String MinOrderQuantity= jsonObject.getString("MinOrderQuantity");
                    String itemname= jsonObject.getString("itemname");

                    if(OfferType.equalsIgnoreCase("WalletPoint"))
                    {
                        String FreeWalletPoint= jsonObject.getString("FreeWalletPoint");
                        offerMess= " <font color=#000000>You will get </font><font color=#FF6347>"+FreeWalletPoint+"</font><font color=#000000> wallet point free with </font><font color=#FF6347>"+MinOrderQuantity+"</font><font color=#000000> quantity of item </font><font color=#FF6347>"+itemname+"</font>";
                    }
                    else
                    {
                        String FreeItemName= jsonObject.getString("FreeItemName");
                        offerMess= " <font color=#000000>You will get </font><font color=#FF6347>"+NoOffreeQuantity+"</font> <font color=#000000> quantity free of item </font><font color=#FF6347>"+FreeItemName+"</font><font color=#000000> with </font></font><font color=#FF6347>"+MinOrderQuantity+"</font><font color=#000000> quantity of item </font><font color=#FF6347>"+itemname+"</font>";
                    }
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View dialogLayout = inflater.inflate(R.layout.offer_popup, null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setView(dialogLayout);
                    customAlertDialog = builder.create();
                    TextView cancelBtn = (TextView) dialogLayout.findViewById(R.id.cancel);
                    TextView item_name = (TextView) dialogLayout.findViewById(R.id.item_name);
                    item_name.setText(Html.fromHtml(offerMess));
                    cancelBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            customAlertDialog.dismiss();
                        }
                    });
                    customAlertDialog.show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(context, "No offer", Toast.LENGTH_SHORT).show();
            }
            //  progressBar.setVisibility(View.GONE);
        }
    }



}
