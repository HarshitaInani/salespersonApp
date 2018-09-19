package com.example.user.mp_salesperson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.user.mp_salesperson.customClasses.Utility;

public class RequestServiceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_service);

        if (!Utility.isConnectingToInternet(RequestServiceActivity.this)){
            Toast.makeText(this, "Please check Internet connection", Toast.LENGTH_SHORT).show();
        }

    }
}
