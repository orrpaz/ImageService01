package com.example.or.imageserviceapp;import android.Manifest;

import android.content.Context;
import android.content.Intent;

import android.content.pm.PackageManager;

import android.os.Build;

import android.support.v4.app.ActivityCompat;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {
    private final int REQUEST_PERMISSION = 1;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // If the service is running
        if (ImageServiceApp.serviceRunning) {
            // We won't enable the start service button upon creation
            Button btn = findViewById(R.id.btnStart);
            btn.setEnabled(false);
        } else {
            // Otherwise not enabling the stop service button
            Button btn = findViewById(R.id.btnStop);
            btn.setEnabled(false);
        }

        context = this;
        // Checking for storage permissions, asking if there are none
        if (ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        int storagePermission = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            storagePermission = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE);
        }
        if (storagePermission == PackageManager.PERMISSION_GRANTED) {
            finish();
            startActivity(getIntent());
        } else {
            finish();
        }
    }

    /**
     * start scervice
     * @param view
     */
    public void startService(View view) {
        Intent intent = new Intent(this, ImageServiceApp.class);
        // Disabling the start service button upon activation
        Button btn1 = findViewById(R.id.btnStart);
        btn1.setEnabled(false);
        // Enabling the stop service button only
        Button btn2 = findViewById(R.id.btnStop);
        btn2.setEnabled(true);
        startService(intent);
    }

    /**
     * stop service
     * @param view
     */
    public void stopService(View view) {
        Intent intent = new Intent(this, ImageServiceApp.class);
        // Enabling the start service button upon closing the service
        Button btn1 = findViewById(R.id.btnStart);
        btn1.setEnabled(true);
        // Disabling the stop service button upon closing the service
        Button btn2 = findViewById(R.id.btnStop);
        btn2.setEnabled(false);
        stopService(intent);
    }

}
