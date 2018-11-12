package com.example.user.mp_salesperson;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.Amitlibs.utils.ComplexPreferences;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.customClasses.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ActionTaskActivity extends AppCompatActivity {



    TextView idtxt, nametxt, actiontxt;
    Spinner actionSpinner;
    EditText commentEt;

    String beatSkcode = "";

    //String


    String beatSkcodeCId = "";

    String urlWallet= "";
    Context context;

    ArrayList list = new ArrayList();

    Dialog mDialog;
    AnimationDrawable animation;


    String actionId;

    String cId, cName,cAction, actionTaskId;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_task);


        context = this;

        Intent i = getIntent();

        list.add("Complete");
        list.add("Not complete");
        list.add("Customer not interested");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActionTaskActivity.this,
                android.R.layout.simple_list_item_1,
                list);


        idtxt = (TextView) findViewById(R.id.customer_id);
        nametxt = (TextView) findViewById(R.id.people_name);
        actiontxt = (TextView) findViewById(R.id.task_action);

        actionSpinner = (Spinner) findViewById(R.id.action_spinner);

        commentEt = (EditText) findViewById(R.id.task_comment);

        beatSkcode = Utility.getStringSharedPreferences(context, "BeatSkCode");

        beatSkcodeCId = Utility.getStringSharedPreferences(context, "BeatSkCodeCId");



        actionSpinner.setAdapter(adapter);

        ((TextView) findViewById(R.id.title_toolbar)).setText("My Action");

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_wallet_toolbar);
        setSupportActionBar(toolbar);


        ((ImageView) toolbar.findViewById(R.id.nav_back_icon_iv)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  startActivity(new Intent(ActionTaskActivity.this, CartActivity.class));
                ActionTaskActivity.this.finish();
            }
        });


        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(ActionTaskActivity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);


       // Toast.makeText(context, ""+i.getStringExtra("name")+"\n"+i.getStringExtra("action")+"\n"+i.getStringExtra("actionCId"), Toast.LENGTH_SHORT).show();


        cId = i.getStringExtra("actionCId");
        cName = i.getStringExtra("name");
        cAction = i.getStringExtra("action");

        actionTaskId = i.getStringExtra("actionTaskId");



        idtxt.setText("Customer Id : "+cId);
        nametxt.setText("Name : "+cName);
        actiontxt.setText("Action : "+cAction);


        queue = Volley.newRequestQueue(this);




/*
        if (!beatSkcode.isEmpty()) {
            //   etSkcode.setText(beatSkcode);

            callApi(mRetailerBean.getPeopleId(), beatSkcode);

        } else {

            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Sk Code")
                    .setMessage("Would you like to chose a Sk Code from the list?")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //finish();

                            ActionTaskActivity.this.finish();
                            startActivity(new Intent(ActionTaskActivity.this, DaysBidActivity.class));
                        }

                    })
                    .setCancelable(false)
                    .show();


        }
*/
        ((Button) findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                callActionPost();
            }
        });

    }


    public void callApi(String id, String skCode) {


        //http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/ActionTask/appActionTask?PeopleId="+id+"&Skcode="+skCode


        showLoading();
 //       Toast.makeText(context, Constant.BASE_URL_ACTION_TASK+"?PeopleId="+id+"&Skcode="+skCode, Toast.LENGTH_SHORT).show();
        new AQuery(ActionTaskActivity.this).ajax(Constant.BASE_URL_ACTION_TASK+"?PeopleId="+id+"&Skcode="+skCode,
                null,
                JSONObject.class,
                new AjaxCallback<JSONObject>()

                {


                    @Override
                    public void callback(String url, JSONObject json, AjaxStatus status) {

                        if (json != null) {
                            try {
                                if (mDialog.isShowing()) {
                                    animation.stop();
                                    mDialog.dismiss();
                                }


                                idtxt.setText("Customer Id : "+json.getString("CustomerId"));
                                nametxt.setText("Name : "+json.getString("PeopleName"));

                                actiontxt.setText("Action : "+json.getString("Action"));


                                actionId = json.getString("ActionTaskid");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {


                            if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }


                        /*
                            new AlertDialog.Builder(ActionTaskActivity.this)
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("My Action")
                                    .setMessage("There is no action for this skcode")
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            //finish();

                                            ActionTaskActivity.this.finish();
                                           // startActivity(new Intent(ActionTaskActivity.this, DaysBidActivity.class));
                                        }

                                    })
                                    .setCancelable(false)
                                    .show();


                            Toast.makeText(ActionTaskActivity.this, "No Action", Toast.LENGTH_SHORT).show();

                        */



                        }

                    }
                });

    }





    public void showLoading() {
        mDialog = new Dialog(ActionTaskActivity.this);
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


    public void callActionPost() {


        showLoading();

        JSONObject params1 = new JSONObject();

       try {


           params1.put("ActionTaskid", actionTaskId);
           params1.put("CustomerId", cId);
           params1.put("Description", commentEt.getText().toString());
           params1.put("Status", actionSpinner.getSelectedItem().toString());



     /*      params1.put("ActionTaskid", 5);
           params1.put("CustomerId", 743);
           params1.put("Description", "dsfdsfa");
           params1.put("Status", "Complete");

*/


       } catch (Exception e) {

       }


      //  Toast.makeText(context, ""+params1.toString(), Toast.LENGTH_SHORT).show();


        // http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/ActionTask

    /*    new AQuery(ActionTaskActivity.this).put("http://ec2-34-208-118-110.us-west-2.compute.amazonaws.com/api/ActionTask",
                params
                ,JSONObject.class,
                new AjaxCallback<JSONObject>(){


                    @Override
                    public void callback(String url, JSONObject jsonObject, AjaxStatus status) {

                        if (jsonObject != null) {
                            Toast.makeText(ActionTaskActivity.this, ""+jsonObject, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ActionTaskActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();
                            Toast.makeText(ActionTaskActivity.this, status.getError()+"\n" + status.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                });


*/



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT
                        , Constant.BASE_URL_ACTION_TASK_POST
                        , params1
                        , new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        //((TextView) findViewById(R.id.txt)).setText(response.toString());

                        if (jsonObject != null) {

                            if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }

                            Toast.makeText(ActionTaskActivity.this, ""+jsonObject, Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(ActionTaskActivity.this, HomeActivity.class));
                            ActionTaskActivity.this.finish();


                        } else {

                            if (mDialog.isShowing()) {
                                animation.stop();
                                mDialog.dismiss();
                            }

                            Toast.makeText(ActionTaskActivity.this, "Please try again!", Toast.LENGTH_SHORT).show();

                        }




                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ActionTaskActivity.this, "Error : "+error.toString(), Toast.LENGTH_SHORT).show();

                        if (mDialog.isShowing()) {
                            animation.stop();
                            mDialog.dismiss();
                        }

                    }


                });



        queue.add(jsonObjectRequest);



    }







}
