package com.augustogo.bankapp.config;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.augustogo.bankapp.data.local.LoginSharedPref;

public class App extends Application {

    private static App instance;
    private static LoginSharedPref sharedPref;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        sharedPref = new LoginSharedPref(this);
    }

    public static boolean isConected(Context context){
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return info!=null && info.isConnected();
    }

    public static App getInstance() {
        return instance;
    }

    public static LoginSharedPref getSharedPref() {
        return sharedPref;
    }
}
