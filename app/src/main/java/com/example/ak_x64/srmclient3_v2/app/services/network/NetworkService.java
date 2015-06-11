package com.example.ak_x64.srmclient3_v2.app.services.network;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.ak_x64.srmclient3_v2.app.system.DataStore_System;
import com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.ClientStarter;
import com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.NetConnection;

/**
 * Created by AK on 15-03-2015.
 */

public class NetworkService extends Service {

    int mStartMode;

    public NetworkService()
    {
        Log.i(DataStore_Network.TAG, "default constructor of NetworkService()");
    }

    /**
     * Called by the system when the service is first created.  Do not call this method directly.
     * If the service is already running, this method is not called.
     */
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(DataStore_Network.TAG,"NetworkService.onCreate(); Service created");
        Toast.makeText(this,"Service Started",Toast.LENGTH_SHORT).show();
        new ClientStarter().start(this);
    }

    /**
     * Called by the system to notify a Service that it is no longer used and is being removed.  The
     * service should clean up any resources it holds (threads, registered
     * receivers, etc) at this point.  Upon return, there will be no more calls
     * in to this Service object and it is effectively dead.  Do not call this method directly.
     */
    @Override
    public void onDestroy() {
        NetConnection.shutdownNetConnection();
        Log.i(DataStore_Network.TAG, "NetworkService.onDestroy(); Service destroyed");
        Toast.makeText(this,"Service Destroyed",Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    /**
     * Called by the system every time a client explicitly starts the service by calling
     * {@link android.content.Context#startService}, providing the arguments it supplied and a
     * unique integer token representing the start request.  Do not call this method directly.
     * <p/>
     * <p>For backwards compatibility, the default implementation calls
     * {@link #onStart} and returns either {@link #START_STICKY}
     * or {@link #START_STICKY_COMPATIBILITY}.
     * <p/>
     * <p>If you need your application to run on platform versions prior to API
     * level 5, you can use the following model to handle the older {@link #onStart}
     * callback in that case.  The <code>handleCommand</code> method is implemented by
     * you as appropriate:
     * <p/>
     * {@sample development/samples/ApiDemos/src/com/example/android/apis/app/ForegroundService.java
     * start_compatibility}
     * <p/>
     * <p class="caution">Note that the system calls this on your
     * service's main thread.  A service's main thread is the same
     * thread where UI operations take place for Activities running in the
     * same process.  You should always avoid stalling the main
     * thread's event loop.  When doing long-running operations,
     * network calls, or heavy disk I/O, you should kick off a new
     * thread, or use {@link android.os.AsyncTask}.</p>
     *
     * @param intent  The Intent supplied to {@link android.content.Context#startService},
     *                as given.  This may be null if the service is being restarted after
     *                its process has gone away, and it had previously returned anything
     *                except {@link #START_STICKY_COMPATIBILITY}.
     * @param flags   Additional data about this start request.  Currently either
     *                0, {@link #START_FLAG_REDELIVERY}, or {@link #START_FLAG_RETRY}.
     * @param startId A unique integer representing this specific request to
     *                start.  Use with {@link #stopSelfResult(int)}.
     * @return The return value indicates what semantics the system should
     * use for the service's current started state.  It may be one of the
     * constants associated with the {@link #START_CONTINUATION_MASK} bits.
     * @see #stopSelfResult(int)
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(DataStore_Network.TAG, "NetworkService.onStartCommand(Intent,int,int) executed");
        return START_STICKY;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



    /** LATER ON
     * This function checks if the device is connected to the internet or not. If the device can
     * access internet and is not connected to server then this method initiates new ClientStarter
     * to connect to server. If it is already connected to server it just return true otherwise if net
     * is not accessible then it returns false.
     *
     *  context Context of current Activity for displaying Toast msg.
     * true, if system has net access and it has started a ClientStarter or a ClientStarter instance is already running
     * false, if system doesn't have access to internet (WIFI or Mobile network only)

    public static boolean checkNetworkAndConnect(Context context) {
        if (DataStore_Network.availableNetwork == null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
            Log.i(DataStore_connection.TAG, "Active Network Type : " + activeNetInfo.getTypeName());

            if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI || activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(context, "Active Network Type : " + activeNetInfo.getTypeName(), Toast.LENGTH_SHORT).show();
                if (activeNetInfo.isConnected() == true && isInternetAvailable() == true) {
                    DataStore_Network.availableNetwork = activeNetInfo;
                    new ClientStarter().start();
                    return true;
                }
            }
        } else {
            if (DataStore_Network.availableNetwork.isConnected() == true && isInternetAvailable() == true) {
                return true;
            }
        }

        return false;
    }
    */

    public static void connectToServer()
    {
    if(DataStore_Network.isInternetAvailable()==true) {
        Intent i = new Intent(DataStore_System.applicationContext, NetworkService.class);
        DataStore_System.applicationContext.startService(i);
    }
    }

}

