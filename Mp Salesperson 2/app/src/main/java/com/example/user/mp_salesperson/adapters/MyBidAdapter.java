package com.example.user.mp_salesperson.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.Amitlibs.utils.TextUtils;
import com.example.user.mp_salesperson.BeatOrderActivity;
import com.example.user.mp_salesperson.R;
import com.example.user.mp_salesperson.customClasses.Utility;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

/**
 * Created by user on 3/15/2017.
 */

public class MyBidAdapter extends RecyclerView.Adapter<MyBidAdapter.ViewHolder> {

    private JSONArray jsonArray;
    private Context context;
    int count;

    ArrayList<String> nameList = new ArrayList<>();
    ArrayList<String> skCodeList = new ArrayList<>();
    ArrayList<String> entryList = new ArrayList<>();
    ArrayList<String> daysList = new ArrayList<>();
    ArrayList<String> rewardsPointList  = new ArrayList<>();
    ArrayList<String> customreIdList  = new ArrayList<>();
    ArrayList<String> customreNameList  = new ArrayList<>();
    ArrayList<String> latitude = new ArrayList<>();
    ArrayList<String> longitude = new ArrayList<>();
    ArrayList<String> Mobile = new ArrayList<>();
    ArrayList<String> CompanyId = new ArrayList<>();

    ArrayList<String> Emailid = new ArrayList<>();
    ArrayList<String> Name = new ArrayList<>();
    ArrayList<String> RefNo = new ArrayList<>();
    ArrayList<String> Password = new ArrayList<>();
    ArrayList<String> DOB = new ArrayList<>();
    ArrayList<String> UploadRegistration = new ArrayList<>();



    JSONObject jsonObject = new JSONObject();

    public MyBidAdapter(Context context, JSONArray jsonArray, int count) {

        this.context = context;
        this.jsonArray = jsonArray;
        this.count = count;


    }

    @Override
    public MyBidAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_customer_row, viewGroup, false);
        return new MyBidAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MyBidAdapter.ViewHolder viewHolder, final int i) {

      //  Toast.makeText(context, "Adapter Array"+jsonArray.toString(), Toast.LENGTH_SHORT).show();
        System.out.println("jsonObject111::::::"+jsonArray);

        try {
            for (int j = 0; j < jsonArray.length(); j++) {
                jsonObject = jsonArray.getJSONObject(j);
                nameList.add(jsonObject.getString("ShopName"));
                skCodeList.add(jsonObject.getString("Skcode"));
                CompanyId.add(jsonObject.getString("CompanyId"));
                //BeatNumber
                entryList.add(jsonObject.getString("BeatNumber"));
                daysList.add(jsonObject.getString("Day"));
                //rewardsPointList.add(jsonObject.getString("Rewardspoints"));
                rewardsPointList.add(isNullOrEmpty(jsonObject, "Rewardspoints"));
                customreIdList.add(jsonObject.getString("CustomerId"));
                customreNameList.add(jsonObject.getString("ShopName"));
                Mobile.add(jsonObject.getString("Mobile"));
                latitude.add(jsonObject.getString("lat"));
                longitude.add(jsonObject.getString("lg"));

                Emailid.add(jsonObject.getString("Emailid"));
                Name.add(jsonObject.getString("Name"));
                RefNo.add(jsonObject.getString("RefNo"));
                Password.add(jsonObject.getString("Password"));
                DOB.add(jsonObject.getString("DOB"));
                UploadRegistration.add(jsonObject.getString("UploadRegistration"));

                //Toast.makeText(context, ""+jsonObject.getString("Name"), Toast.LENGTH_SHORT).show();
                //viewHolder.tvName.setText(jsonObject.getString("Name"));
            }
        } catch (Exception e) {
        }

//          viewHolder.tvName.setText("Get name");
            viewHolder.tvName.setText(nameList.get(i));
            viewHolder.tvSkcode.setText(skCodeList.get(i));

         //   viewHolder.tvEntry.setText("Get Entry");

        viewHolder.tvEntry.setText(entryList.get(i));


        viewHolder.tvDay.setText(daysList.get(i));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String sk = skCodeList.get(i).toString();
                String rp = rewardsPointList.get(i).toString();
                String customer_Id = customreIdList.get(i).toString();
                String lat = latitude.get(i).toString();
                String longi = longitude.get(i).toString();
                String Mob = Mobile.get(i).toString();
                String sCompanyId = CompanyId.get(i).toString();
                String shop_Name = customreNameList.get(i).toString();

                String Email = Emailid.get(i).toString();
                String customer_Name = Name.get(i).toString();
                String ReN = RefNo.get(i).toString();
                String dob = DOB.get(i).toString();
                String pass = Password.get(i).toString();
                String upReg = UploadRegistration.get(i).toString();


                  // Toast.makeText(context, ""+customreIdList.get(i).toString(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, BeatOrderActivity.class);
                Utility.setStringSharedPreference(context, "BeatSkCode", sk);
                Utility.setStringSharedPreference(context, "BeatSkCodeCId", customer_Id);
                Utility.setStringSharedPreference(context, "BeatSkCodeCName", shop_Name);
                Utility.setStringSharedPreference(context, "CompanyId", sCompanyId);
                Utility.setStringSharedPreference(context, "MyRewardsPoint", rp);
                Utility.setStringSharedPreference(context, "LATITUDE", lat);
                Utility.setStringSharedPreference(context, "LONGITUDE", longi);
                Utility.setStringSharedPreference(context, "Mobile", Mob);

                Utility.setStringSharedPreference(context, "EMAIL", Email);
                Utility.setStringSharedPreference(context, "NAME", customer_Name);
                Utility.setStringSharedPreference(context, "REN", ReN);
                Utility.setStringSharedPreference(context, "DOB", dob);
                Utility.setStringSharedPreference(context, "PASS", pass);
                Utility.setStringSharedPreference(context, "UPREG", upReg);
               /* i.putExtra("MyBeatSkCode", sk);
                i.putExtra("MyBeatSkCodeCustomerId", customer_Id);
                i.putExtra("MyBeatSkCodeCustomerName", customer_Name);
                i.putExtra("CompanyId", sCompanyId);
                i.putExtra("MyRewardsPoint", rp);
                i.putExtra("LATITUDE", lat);
                i.putExtra("LONGITUDE", longi);
                i.putExtra("Mobile", Mob);*/
                //   i.putExtra("", "");
               // context.startActivity(new Intent(context, BeatOrderActivity.class));

                context.startActivity(i);

                ((Activity)context).finish();

            }
        });







     /*   try {

        } catch (IndexOutOfBoundsException e) {
            context.startActivity(new Intent(context, HomeActivity.class));
        }
   */


    }

    @Override
    public int getItemCount() {


//        return itemListArrayList.size();

        return count;

    }



    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tvName, tvSkcode, tvEntry, tvDay;

        public ViewHolder(View view) {
            super(view);

            tvName = (TextView) view.findViewById(R.id.name);

            tvSkcode = (TextView) view.findViewById(R.id.skCode);
            tvEntry = (TextView) view.findViewById(R.id.entry);

            tvDay = (TextView) view.findViewById(R.id.day);

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
                Log.e("LoginActivity", key + " is not available which should come in response");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }



}
