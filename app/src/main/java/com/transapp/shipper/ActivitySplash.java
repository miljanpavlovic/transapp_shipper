package com.transapp.shipper;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class ActivitySplash extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent mainActivity = new Intent(ActivitySplash.this, ActivityPrototyping.class);
                    startActivity(mainActivity);
                    finish();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }
}
