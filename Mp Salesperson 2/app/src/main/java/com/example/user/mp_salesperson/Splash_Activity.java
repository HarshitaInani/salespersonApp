package com.example.user.mp_salesperson;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.widget.Toast;

import com.Amitlibs.net.HttpUrlConnectionJSONParser;
import com.Amitlibs.utils.ComplexPreferences;
import com.Amitlibs.utils.TextUtils;
import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.user.mp_salesperson.bean.AppVersionBean;
import com.example.user.mp_salesperson.bean.BaseCatSubCatBean;
import com.example.user.mp_salesperson.bean.RetailerBean;
import com.example.user.mp_salesperson.customClasses.Utility;
import com.splunk.mint.Mint;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class Splash_Activity extends AppCompatActivity {
    AsyncTask<String, Void, JSONArray> mCheckVersionTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        callSliderApi();


        Utility.setSharedPreferenceBoolean(Splash_Activity.this, "APICALL", true);
        Context context = Splash_Activity.this.getApplicationContext();

        Utility.setStringSharedPreference(context, "ItemQJson" ,"");


        if (!Utility.isConnectingToInternet(Splash_Activity.this)){
            Toast.makeText(this, "Please check Internet connection", Toast.LENGTH_SHORT).show();
        }

        getLocalizedResources(context,new Locale("hi"));

        Mint.initAndStartSession(this.getApplication(), "3f40bc21");
        clearCartData();
        if (Utils.isInternetConnected(Splash_Activity.this)) {
           // mCheckVersionTask = new CheckVersionTask().execute();
            ContinueToHomeScreen();
        } else {
            Toast.makeText(Splash_Activity.this, "Internet connection is not available", Toast.LENGTH_SHORT).show();
            ContinueToHomeScreen();
        }
      //  Utility.setStringSharedPreference(Splash_Activity.this, "MultiLaguage", "m");
    }

    public void onPause() {
        super.onPause();
        if (mCheckVersionTask != null && !mCheckVersionTask.isCancelled()) {
            mCheckVersionTask.cancel(true);
        }
        finish();
    }

    private void clearCartData() {
        ComplexPreferences mCartItemArraylistPref = ComplexPreferences.getComplexPreferences(Splash_Activity.this, Constant.CART_ITEM_ARRAYLIST_PREF, MODE_PRIVATE);
        mCartItemArraylistPref.clear();
        mCartItemArraylistPref.commit();
    }

    @NonNull
    public Resources getLocalizedResources(Context context, Locale desiredLocale) {
        Configuration conf = context.getResources().getConfiguration();
        conf = new Configuration(conf);
        conf.setLocale(desiredLocale);
        Context localizedContext = context.createConfigurationContext(conf);
        return localizedContext.getResources();
    }

    public class CheckVersionTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected void onPreExecute() {
           /* mDialog = new Dialog(Splash_Activity.this);
            mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
            mDialog.setContentView(R.layout.progress_dialog);
            ((TextView) mDialog.findViewById(R.id.progressText)).setText("Logging in please wait...");
            mDialog.setCancelable(false);
            mDialog.show();*/
        }

        @Override
        protected JSONArray doInBackground(String... params) {
            JSONArray jsonArrayFromUrl = null;
            try {
                jsonArrayFromUrl = new HttpUrlConnectionJSONParser().getJsonArrayFromHttpUrlConnection(Constant.BASE_URL_APP_VERSION, null, HttpUrlConnectionJSONParser.Http.GET);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonArrayFromUrl;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            if (jsonArray != null) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(jsonArray.length() - 1);
                    System.out.println("Json Response:::"+jsonObject);
                    if (jsonObject != null) {
                        double App_version = jsonObject.getDouble("App_version");
                        boolean isCompulsory = jsonObject.getBoolean("isCompulsory");
                        String createdDate = isNullOrEmpty(jsonObject, "createdDate");

                        ComplexPreferences mComplexPreferences = ComplexPreferences.getComplexPreferences(Splash_Activity.this, Constant.APP_VERSION_PREF, MODE_PRIVATE);
                        AppVersionBean mAppVersionBean = mComplexPreferences.getObject(Constant.APP_VERSION_PREF_OBJ, AppVersionBean.class);
                        if (mAppVersionBean == null) {
                            mAppVersionBean = new AppVersionBean(App_version, isCompulsory, createdDate);
                            mComplexPreferences.putObject(Constant.APP_VERSION_PREF_OBJ, mAppVersionBean);
                            mComplexPreferences.commit();
                            ContinueToHomeScreen();
                        } else {
                            System.out.println("Version::"+mAppVersionBean.getApp_version());
                            System.out.println("App_version::"+App_version);
                            if (mAppVersionBean.getApp_version() == App_version) {
                                ContinueToHomeScreen();
                            } else if (isCompulsory) {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(Splash_Activity.this, android.support.v7.appcompat.R.style.Base_Theme_AppCompat_Dialog));
                                alertDialogBuilder.setTitle(getString(R.string.youAreNotUpdatedTitle));
                                alertDialogBuilder.setMessage(getString(R.string.youAreNotUpdatedMessage) + " " + App_version + getString(R.string.youAreNotUpdatedMessage1));
                                alertDialogBuilder.setCancelable(false);
                                alertDialogBuilder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                     //   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=app.retailer.krina.shop.com.shopkrina_retailer")));
                                        dialog.cancel();
                                        Splash_Activity.this.finish();
                                    }
                                });
                                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        dialog.cancel();
                                        Splash_Activity.this.finish();
                                    }
                                });
                                alertDialogBuilder.show();

//                                Splash_Activity.this.finish();

                            } else {
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(new ContextThemeWrapper(Splash_Activity.this, android.support.v7.appcompat.R.style.Base_Theme_AppCompat_Dialog));

                                alertDialogBuilder.setTitle(getString(R.string.youAreNotUpdatedTitle));
                                alertDialogBuilder.setMessage(getString(R.string.youAreNotUpdatedMessage) + " " + App_version + getString(R.string.youAreNotUpdatedMessage1));
                                alertDialogBuilder.setCancelable(false);
                                alertDialogBuilder.setPositiveButton(R.string.update, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                     //   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=app.retailer.krina.shop.com.shopkrina_retailer")));
                                        ContinueToHomeScreen();
                                        dialog.cancel();
                                    }
                                });
                                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int i) {
                                        ContinueToHomeScreen();
                                        dialog.cancel();
                                    }
                                });
                                alertDialogBuilder.show();
//                                ContinueToHomeScreen();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(Splash_Activity.this, "Improper response from server", Toast.LENGTH_SHORT).show();
                ContinueToHomeScreen();
            }
        }
    }

    private void ContinueToHomeScreen() {
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(Splash_Activity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBean mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBean.class);
        if (mRetailerBean != null && !mRetailerBean.getCustomerId().equalsIgnoreCase("0")) {
            if (mRetailerBean.getActive().equalsIgnoreCase("false")) {
                Intent i = new Intent(getApplicationContext(), ActivationActivity.class);
                startActivity(i);
            } else {
                ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(Splash_Activity.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), PreHomeActivity.class);
                    startActivity(i);
                }
            }
        } else {
            Intent i = new Intent(getApplicationContext(), LoginActivity_Nav.class);
            startActivity(i);
        }
        Splash_Activity.this.finish();
    }
    

   /* private void ContinueToHomeScreen() {
        ComplexPreferences mRetailerBeanPref = ComplexPreferences.getComplexPreferences(Splash_Activity.this, Constant.RETAILER_BEAN_PREF, MODE_PRIVATE);
        RetailerBeanSeller mRetailerBean = mRetailerBeanPref.getObject(Constant.RETAILER_BEAN_PREF_OBJ, RetailerBeanSeller.class);
        if (mRetailerBean != null && !mRetailerBean.getPeopleId().equalsIgnoreCase("0")) {
            if (mRetailerBean.getActive().equalsIgnoreCase("false")) {
                Intent i = new Intent(getApplicationContext(), ActivationActivity.class);
                startActivity(i);
            } else {
                ComplexPreferences mBaseCatSubCatCat = ComplexPreferences.getComplexPreferences(Splash_Activity.this, Constant.BASECAT_CAT_SUBCAT_PREF, MODE_PRIVATE);
                BaseCatSubCatBean mBaseCatSubCatBean = mBaseCatSubCatCat.getObject(Constant.BASECAT_CAT_SUBCAT_PREFOBJ, BaseCatSubCatBean.class);
                if (mBaseCatSubCatBean != null && !mBaseCatSubCatBean.getmBaseCatBeanArrayList().isEmpty()) {
                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(i);
                } else {
                    Intent i = new Intent(getApplicationContext(), PreHomeActivity.class);
                    startActivity(i);
                }
            }
        } else {
            Intent i = new Intent(getApplicationContext(), LoginActivity_Nav.class);
            startActivity(i);
        }
        Splash_Activity.this.finish();
    }

*/


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
    public void callSliderApi() {
        new AQuery(getApplicationContext()).ajax(Constant.BASE_URL_Slider_API, null, JSONArray.class, new AjaxCallback<JSONArray>() {

            @Override
            public void callback(String url, JSONArray json, AjaxStatus status) {
                if (json == null) {
                    Toast.makeText(Splash_Activity.this, "Slider : Please try again", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("Slide::"+json.toString());
                    Utility.setStringSharedPreference(Splash_Activity.this, "Sliderjson", json.toString());
                }
            }
        });
    }


}
