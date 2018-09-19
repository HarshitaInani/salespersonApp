package com.example.user.mp_salesperson;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.example.user.mp_salesperson.adapters.HomeFragAllTopAddedAdapter;
import com.example.user.mp_salesperson.bean.AllTopAddedItemList;
import com.example.user.mp_salesperson.bean.BaseCatSubCatBean;
import com.example.user.mp_salesperson.bean.ReportPojo;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.bean.basecat_subcat_cat_bean_package.ItemList;
import com.example.user.mp_salesperson.fragment.HomeFragment;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ReportActivity extends AppCompatActivity {
    int monthStart, monthEnd;
    TextView StartDate, EndDate;
    String sStartDate, sEndDate;
    Button GetData;
    ProgressBar progressBar;
    AsyncTask<String, Void, JSONArray> getCallReport;
    Dialog mDialog;
    TextView tvTotalOrder,tvTotalAmount,tvactiveretailer;
    AnimationDrawable animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        new GetActiveRetailer().execute();
        ((ImageView) toolbar.findViewById(R.id.my_order_detail_nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportActivity.this.finish();
            }
        });
        StartDate = (TextView) findViewById(R.id.start_date);
        EndDate = (TextView) findViewById(R.id.end_date);
        GetData = (Button) findViewById(R.id.get_data);
        ImageView start = (ImageView) findViewById(R.id.startImage);
        ImageView end = (ImageView) findViewById(R.id.endImage);
        tvTotalOrder = (TextView) findViewById(R.id.totalOrder);
        tvTotalAmount = (TextView) findViewById(R.id.totalAmount);
        tvactiveretailer = (TextView) findViewById(R.id.tvactiveretailer);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartDate();
            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EndDate();
            }
        });

        GetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("MonthStart==:" + monthStart);
                System.out.println("MonthEnd==:" + monthEnd);
                sStartDate = StartDate.getText().toString().trim();
                sEndDate = EndDate.getText().toString().trim();
                if (sStartDate.equalsIgnoreCase("")) {
                    Toast.makeText(ReportActivity.this, "Please select start date", Toast.LENGTH_SHORT).show();
                } else if (sEndDate.equalsIgnoreCase("")) {
                    Toast.makeText(ReportActivity.this, "Please select end date", Toast.LENGTH_SHORT).show();
                } else if (monthStart <= monthEnd) {
                    // feedsList.clear();
                    // adapter.notifyDataSetChanged();
                    getCallReport = new CallReport().execute();

                }
                else {
                    Toast.makeText(ReportActivity.this, "Please select correct date ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void EndDate() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        System.out.println("the selected " + mDay);

        DatePickerDialog dialog = new DatePickerDialog(ReportActivity.this, new mDateSetListener1(), mYear, mMonth, mDay);
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }

    private void StartDate() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        System.out.println("the selected " + mDay);
        DatePickerDialog dialog = new DatePickerDialog(ReportActivity.this, new mDateSetListener(), mYear, mMonth, mDay);
        dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
        dialog.show();
    }
  class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;

            int month = monthOfYear + 1;
            String formattedMonth = "" + month;
            String formattedDayOfMonth = "" + dayOfMonth;

            if(month < 10){

                formattedMonth = "0" + month;
            }
            if(dayOfMonth < 10){

                formattedDayOfMonth = "0" + dayOfMonth;
            }

            StartDate.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mYear).append("-").append(formattedMonth).append("-").append(formattedDayOfMonth));
            monthStart = monthOfYear + 1;
            System.out.println(StartDate.getText().toString());

        }
    }

    class mDateSetListener1 implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;

            monthEnd = monthOfYear + 1;
            int month = monthOfYear + 1;
            String formattedMonth = "" + month;
            String formattedDayOfMonth = "" + dayOfMonth;

            if(month < 10){

                formattedMonth = "0" + month;
            }
            if(dayOfMonth < 10){

                formattedDayOfMonth = "0" + dayOfMonth;
            }
            EndDate.setText(new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mYear).append("-").append(formattedMonth).append("-").append(formattedDayOfMonth));
            System.out.println(StartDate.getText().toString());
        }
    }

    public class CallReport extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
            showLoading();
            //  progressBar.setVisibility(View.VISIBLE);

        }


        @Override
        protected JSONArray doInBackground(String... params) {

            JSONArray jsonArrayFromUrl = null;
            ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(ReportActivity.this, Constant.RETAILER_BEAN_PREF, getApplication().MODE_PRIVATE);
            RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
            String url=Constant.BASE_URL_REPORT+"datefrom="+sStartDate+"%2012:00%20AM&dateto="+sEndDate+"%2011:59%20PM&type=4"+"&id="+mRetailerBean.getCustomerId();
            System.out.println("reportcheckurl::::"+url);

            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(url, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {


            ArrayList<ReportPojo> mItemListArrayList = new ArrayList<>();
            double totalamount=0; int numberOfOrder=0;
            if (jsonArray != null && jsonArray.length() > 0) {



                for (int i = 0; i  < jsonArray.length() ; i++) {

                    try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String temp = null;


                        String OrderId = jsonObject.getString("OrderId");
                        String totalOrder = jsonObject.getString("totalOrder");
                        String TotalAmount = jsonObject.getString("TotalAmount");

                        String price = new DecimalFormat("##.##").format((Double.parseDouble(TotalAmount)));
                           totalamount+=Double.parseDouble(price);



                        if(mItemListArrayList.size()==0)
                        {
                            mItemListArrayList.add(new ReportPojo(OrderId,totalOrder,TotalAmount));

                        }else
                        {
                            boolean ispresent=false;
                            for (int j = 0; j <mItemListArrayList.size() ; j++) {


                                if(mItemListArrayList.get(j).getOrderId().equalsIgnoreCase(OrderId))
                                {
                                    ispresent=true;
                                    break;
                                }
                            }
                            if(!ispresent)
                            {

                                numberOfOrder++;

                                mItemListArrayList.add(new ReportPojo(OrderId,totalOrder,TotalAmount));

                            }else
                            {

                            }
                        }







                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                BigDecimal a = new BigDecimal(totalamount);
                BigDecimal roundOff = a.setScale(2, BigDecimal.ROUND_HALF_EVEN);

                System.out.println(roundOff);
                Log.d("TotalAmount::", String.valueOf(roundOff));
                Log.d("NumberOfOrder::", String.valueOf(numberOfOrder+1));
                tvTotalOrder.setText(String.valueOf(numberOfOrder+1));
                tvTotalAmount.setText(String.valueOf(roundOff));



                if (mDialog.isShowing()) {
                    animation.stop();
                    mDialog.dismiss();
                }

            } else {

                if (mDialog.isShowing()) {
                    animation.stop();
                    mDialog.dismiss();
                }
                tvTotalOrder.setText("");
                tvTotalAmount.setText("");

                Toast.makeText(ReportActivity.this, "No order available", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void showLoading() {
        mDialog = new Dialog(ReportActivity.this);
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


    private class GetActiveRetailer extends AsyncTask<String,Void,String> {

        ProgressDialog pd=new ProgressDialog(ReportActivity.this);
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient Client = new DefaultHttpClient();
            String URL = Constant.BASE_URL_Active_Retialer;
            System.out.println("active check::::::::::::"+URL);
            Log.e("httpget", URL);
               try
            {
                String SetServerString = "";
                HttpGet httpget = new HttpGet(URL);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                return SetServerString = Client.execute(httpget, responseHandler);
            }
            catch(Exception ex)
            {
                return "Exception";
            }
   }
     @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            System.out.println("sssss::::"+s);
         tvactiveretailer.setText(s);
      }
    }
    private String isNullOrEmpty(String value) throws JSONException {
        if (TextUtils.isNullOrEmpty(value)) {
            return "";
        } else {
            return value;
        }
    }

}