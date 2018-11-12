package com.example.user.mp_salesperson;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MySalesActivity extends AppCompatActivity {

    JSONArray jsonArray1;


    ArrayList<String> totalList = new ArrayList<>();
    ArrayList<String> dateList = new ArrayList<>();

    ArrayList<String> valueList = new ArrayList<>();

    SimpleDateFormat format2;
    Date newDate2;

    BarChart barChart;

    Legend l;

    String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sales);


        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(MySalesActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        final RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);


        ((TextView) findViewById(R.id.title_toolbar)).setText("My Sales");

        id = mRetailerBean.getCustomerId();

        setGraph();


        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySalesActivity.this.finish();
            }
        });

    /*    ((ImageView) toolbar.findViewById(R.id.nav_home_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MySalesActivity.this.finish();
            }
        });
*/


        ((TextView) findViewById(R.id.days30)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ((TextView) findViewById(R.id.month3)).setTextColor(Color.WHITE);
                ((TextView) findViewById(R.id.days30)).setTextColor(Color.BLACK);

                new AQuery(getApplicationContext()).ajax(Constant.BASE_URL_MYSALES_DATA+"?day=day&id="+id, null, JSONArray.class, new AjaxCallback<JSONArray>() {


                    @Override
                    public void callback(String url, JSONArray jsonArray, AjaxStatus status) {
                        //   Toast.makeText(LoginActivity_Nav.this, "Aquery call"+status.getError()+status.getMessage(), Toast.LENGTH_SHORT).show();

                        if (jsonArray == null) {
                            Toast.makeText(MySalesActivity.this, "Json is null " + status.getError(), Toast.LENGTH_SHORT).show();


                        } else
                        {




                            try {

                                totalList = new ArrayList<String>();
                                valueList = new ArrayList<>();


                                jsonArray1 = new JSONArray(jsonArray.toString());
                                JSONObject jsonObject = new JSONObject();



                                for (int i = 0; i< jsonArray1.length(); i++) {



                                    jsonObject = jsonArray1.getJSONObject(i);


                                    totalList.add(jsonObject.getString("TotalAmount"));

                                    valueList.add(jsonObject.getString("value"));







                                }

                                getMonthData(totalList, valueList);

//                                getMonthData(valueList, totalList);


                            } catch (Exception e) {

                                Log.e("Json error Beat Order", e.toString() );
                                Toast.makeText(MySalesActivity.this, "Error json "+e.toString(), Toast.LENGTH_LONG).show();

                            }




                        }   }


                });





            }
        });


        ((TextView) findViewById(R.id.month3)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((TextView) findViewById(R.id.month3)).setTextColor(Color.BLACK);
                ((TextView) findViewById(R.id.days30)).setTextColor(Color.WHITE);


                //http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/target/Report?day=day&id=

                //Constant.BASE_URL_MYSALES_DATA
                new AQuery(getApplicationContext()).ajax(Constant.BASE_URL_MYSALES_DATA+"?day=day&id="+id, null, JSONArray.class, new AjaxCallback<JSONArray>() {


                    @Override
                    public void callback(String url, JSONArray jsonArray, AjaxStatus status) {
                        //   Toast.makeText(LoginActivity_Nav.this, "Aquery call"+status.getError()+status.getMessage(), Toast.LENGTH_SHORT).show();

                        if (jsonArray == null) {
                            Toast.makeText(MySalesActivity.this, "Json is null " + status.getError(), Toast.LENGTH_SHORT).show();


                        } else {




                            try {

                                totalList = new ArrayList<String>();
                                valueList = new ArrayList<>();


                                jsonArray1 = new JSONArray(jsonArray.toString());
                                JSONObject jsonObject = new JSONObject();



                                for (int i = 0; i< jsonArray1.length(); i++) {



                                    jsonObject = jsonArray1.getJSONObject(i);


                                    totalList.add(jsonObject.getString("TotalAmount"));

                                    valueList.add(jsonObject.getString("monthValue"));







                                }

                                getMonthData(totalList, valueList);

//                                getMonthData(valueList, totalList);


                            } catch (Exception e) {

                                Log.e("Json error Beat Order", e.toString() );
                                Toast.makeText(MySalesActivity.this, "Error json "+e.toString(), Toast.LENGTH_LONG).show();

                            }


                        }
                    }


                });




            }
        });






    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // startActivity(new Intent(MySalesActivity.this, HomeActivity.class));
        MySalesActivity.this.finish();


    }


    public void getMonthData(ArrayList totalList, ArrayList dateList) {

        barChart = (BarChart) findViewById(R.id.chart);


      //  Toast.makeText(MySalesActivity.this, totalList.toString()+"\n"+dateList.toString(), Toast.LENGTH_SHORT).show();


       /* l = barChart.getLegend();

        l.setTextSize(24f);
        l.setEnabled(false);
*/
        //  lineChart.set

        //  lineChart.setDescription("Amount");

        //   lineChart.setTouchEnabled(false);

        barChart.setDescription("");
        ArrayList<BarEntry> entries = new ArrayList<>();


        for (int i =0; i < totalList.size(); i++) {


            //  double value = Double.parseDouble(totalList.get(i).toString());

            float value2 = Float.parseFloat(totalList.get(i).toString());

            entries.add(new BarEntry(value2, i));



        }


        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setEnabled(false);






        BarDataSet dataset = new BarDataSet(entries,"");

        dataset.setValueTextSize(12f);


        ArrayList<String> labels = dateList;

        BarData data = new BarData(labels, dataset);

        // dataset.setColors(ColorTemplate.COLORFUL_COLORS); //




        barChart.setData(data);
        barChart.animateY(2000);
        //  LineData data = new LineData(labels, dataset);

        //   dataset.setColors(ColorTemplate.PASTEL_COLORS);
//
        // dataset.setDrawCubic(true);

        //  dataset.setDrawFilled(true);


        // dataset.setValueTextSize(10);



        //   lineChart.setData(data);

        //   lineChart.animateY(2000);






    }


    public void setGraph() {


        //http://ec2-54-214-137-77.us-west-2.compute.amazonaws.com/api/target/Report?day=day&id=

        new AQuery(getApplicationContext()).ajax(Constant.BASE_URL_MYSALES_DATA+"?day=day&id="+id, null, JSONArray.class, new AjaxCallback<JSONArray>() {


            @Override
            public void callback(String url, JSONArray jsonArray, AjaxStatus status) {
                //   Toast.makeText(LoginActivity_Nav.this, "Aquery call"+status.getError()+status.getMessage(), Toast.LENGTH_SHORT).show();

                if (jsonArray == null) {
                    Toast.makeText(MySalesActivity.this, "Json is null " + status.getError(), Toast.LENGTH_SHORT).show();


                } else {


                  //  Toast.makeText(MySalesActivity.this, ""+jsonArray.toString(), Toast.LENGTH_SHORT).show();

                    try {

                        totalList = new ArrayList<String>();
                        valueList = new ArrayList<>();


                        jsonArray1 = new JSONArray(jsonArray.toString());
                        JSONObject jsonObject = new JSONObject();



                        for (int i = 0; i< jsonArray1.length(); i++) {



                            jsonObject = jsonArray1.getJSONObject(i);


                            totalList.add(jsonObject.getString("TotalAmount"));

                            valueList.add(jsonObject.getString("monthValue"));







                        }

                        getMonthData(totalList, valueList);

//                                getMonthData(valueList, totalList);


                    } catch (Exception e) {

                        Log.e("Json error Beat Order", e.toString() );
                        Toast.makeText(MySalesActivity.this, "Error json "+e.toString(), Toast.LENGTH_LONG).show();

                    }


                }
            }


        });


    }

}
