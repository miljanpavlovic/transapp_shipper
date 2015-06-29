package com.transapp.shipper.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.transapp.shipper.commons.Constants;

/**
 * Created by Miljan on 6/29/2015.
 */
public class SessionManager {

    private static final String TAG = "SessionManager";

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    Context context;

    public SessionManager(Context context){
        this.context = context;
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        editor = prefs.edit();
    }

    public void setLogin(boolean isLoggedIn){
        editor.putBoolean(Constants.Prefs.PREF_IS_LOGGEDIN, isLoggedIn);
        editor.commit();
        Log.d(TAG, "Login session modified");
    }

    public boolean isLogdedIn(){
        return prefs.getBoolean(Constants.Prefs.PREF_IS_LOGGEDIN, false);
    }

}
