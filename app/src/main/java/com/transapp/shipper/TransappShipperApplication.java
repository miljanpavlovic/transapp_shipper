package com.transapp.shipper;

import android.app.Application;
import android.util.Log;

import com.transapp.shipper.managers.ResourceManager;

/**
 * Created by Miljan on 6/27/2015.
 */
public class TransappShipperApplication extends Application {

    private static final String TAG = "TransappShipperApp";

    @Override
    public void onCreate() {
        Log.d(TAG, "Application is starting up");

        ResourceManager.getInstance().init(this);
        super.onCreate();
    }
}
