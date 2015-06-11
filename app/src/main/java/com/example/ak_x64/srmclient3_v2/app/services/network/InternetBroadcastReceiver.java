package com.example.ak_x64.srmclient3_v2.app.services.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/** TODO : Set this class as inner class in NetworkService
 */

public class InternetBroadcastReceiver extends BroadcastReceiver {

    public InternetBroadcastReceiver() {
        Log.i(DataStore_Network.TAG, "default constructor of InternetListener()");
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(DataStore_Network.TAG, "InternetListener.onReceive(); executed");

        if(DataStore_Network.isInternetAvailable()==true && DataStore_Network.serviceStarted==false){
            DataStore_Network.serviceStarted=true;
            Intent intent1=new Intent(context,NetworkService.class);
            context.startService(intent1);
        }
        else{
            if(DataStore_Network.serviceStarted==true){
                DataStore_Network.serviceStarted=false;
                Intent intent1=new Intent(context,NetworkService.class);
                context.stopService(intent1);
            }
        }

            /** TODO: Create a mechanism that checks what type of network is available and what to do when multiple connections occur
             * SAMPLE CODE -
             ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
             for (int i = 0; i < DataStore_Network.networksToMonitor.length; i++) {
             NetworkInfo networkInfo = connectivityManager.getNetworkInfo(DataStore_Network.networksToMonitor[i]);
             if (networkInfo != null) {
             Log.i(DataStore_Network.TAG, "Network Type : " + networkInfo.getTypeName());
             if (networkInfo.isConnected() == true && DataStore_Network.isInternetAvailable() == true) {
             Toast.makeText(context, "Accessing Internet by : " + networkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

             } else if (networkInfo.isConnected() == true && DataStore_Network.isInternetAvailable() == false) {
             Toast.makeText(context, networkInfo.getTypeName() + " connected but internet not accessible", Toast.LENGTH_SHORT).show();
             }
             }
             }
             */
    }
} // end of class
