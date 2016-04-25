package com.example.artists;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetwork {// класс проверяющий наличие интренета

    public static boolean isInternetAvailable(Context context) {
        NetworkInfo info =  ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null) {

            return false;
        }
        else {
            if (info.isConnected()) {
                return true;
            }
            else {
                return false;
            }

        }
    }
}



