/**This class is the starting point of the program.
 * 
 * 
 */

// DISABLING USER PORTAL,UPDATER(in Process.class)

package com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection;

import android.content.Context;
import android.util.Log;

public class ClientStarter{

	public void start(Context serviceContext) {
        Log.i(DataStore_connection.TAG, "ClientStarter started");
        new DataStore_connection(serviceContext);

        NetConnection nc = new NetConnection();
        new Thread(nc, "NetConnection").start();
    }
}

