package com.transapp.shipper.managers;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by Miljan on 6/25/2015.
 */
public class ResourceManager {
    private static final String TAG = "ResourceManager";

    private static ResourceManager mInstance;

    private static RequestQueue mRequestQueue;

    private DbManager dbManager;

    private String applicationVersion;
    /**
     * @return
     * 		instance of the cache manager
     */
    public static ResourceManager getInstance(){
        if(mInstance == null) {
            mInstance = new ResourceManager();
        }
        return mInstance;
    }

    public void init(Context context){
        dbManager = DbManager.getInstance(context);
        mRequestQueue = Volley.newRequestQueue(context);

        PackageInfo pinfo;
        try {
            pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            applicationVersion = pinfo.versionName;
            Log.d(TAG, "applicationVersion: " + applicationVersion);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "Couldn't retrieve applicationVersion: " + e);
        }
    }
    
    public RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("Not initialized");
        }
    }

    public <T> void addToRequestQueue(Request<T> request, String tag){
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request){
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPendingRequests(Object tag){
        if(mRequestQueue!=null){
            mRequestQueue.cancelAll(tag);
        }
    }



    public String getApplicationVersion() {
        return applicationVersion;
    }


}
