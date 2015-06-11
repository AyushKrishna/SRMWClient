package com.example.ak_x64.srmclient3_v2.app.services.network;

import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.concurrent.Semaphore;

/**
 * Created by AK on 15-03-2015.
 */
public class DataStore_Network {

    public static final String TAG="Network";
    public static boolean serviceStarted;
    public static int[] networksToMonitor={ConnectivityManager.TYPE_WIFI,ConnectivityManager.TYPE_MOBILE};


    //LOCAL VARIABLES

    // NOTE - never use value of this variable to check internet is available or not.. because it will
    //always change when isInternetAvailable() method is called... Always use the method instead
    private static boolean internetAvailable;

    /** This function checks whether internet is available or not by trying to open a socket to
     * www.google.com
     *
     * @return
     * true, if internet is available
     * false,if internet is unavailable
     */
    public static boolean isInternetAvailable() {
        //Log.i(DataStore_Network.TAG,"DataStore.isInternetAvailable(); executed");

        // this Semaphore is used to synchronise this function's calling thread and the AsyncTask's background thread
        // If this is not used then the function thread will immediately return the previous value
        // as soon as it calls the calls the execute() method of async task while the output of
        // async task will be obtained later on(as opening socket will take some time). Hence
        // this semaphore will stop the function thread until async task does not finish its work.
        final Semaphore sem=new Semaphore(0);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                Socket s = new Socket();
                boolean gotnet=false;
                try
                {
                    s.connect(new InetSocketAddress("www.google.com" , 80));
                    gotnet=true;
                }
                catch (IOException e){
                    e.printStackTrace();
                    gotnet=false;
                }

                internetAvailable = gotnet;
                sem.release();
               return null;
            }

            protected void onPostExecute() {
                // runs on main thread so if you stopped main thread before running this async task
                // this function will never be called
            }
        }.execute();

        try {
            //Log.i(DataStore_Network.TAG,"Stopping thread");
            sem.acquire();
        } catch (InterruptedException e) {
            Log.e(DataStore_Network.TAG,"InterruptedException while waiting for async task to complete");
            e.printStackTrace();
        }

        Log.i(DataStore_Network.TAG,"internetAvailable->"+internetAvailable);
        return internetAvailable;
    }
}

