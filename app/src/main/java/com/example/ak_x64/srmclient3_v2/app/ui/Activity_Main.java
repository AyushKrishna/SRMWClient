package com.example.ak_x64.srmclient3_v2.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import com.example.ak_x64.srmclient3_v2.app.system.DataStore_System;
import com.example.ak_x64.srmclient3_v2.app.ui.login.Activity_Login;
import com.example.ak_x64.srmclient3_v2.app.ui.wall.Activity_Wall;

public class Activity_Main extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(DataStore_System.TAG,"Activity_Main.onCreate; Started Main Activity");

        initializeApp();
    }

    public void initializeApp() {
        new DataStore_System(getApplicationContext());

        DataStore_System.mainHandler = new MainHandler(Looper.getMainLooper());

        DataStore_System.connectToServer();

        if(DataStore_System.ID==0||DataStore_System.ID<0) {
            Log.i(DataStore_System.TAG, "Activity_Main.initializeApp(); User not logged in... launching Login Activity");
            Intent intent = new Intent(this, Activity_Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
        else {
            Log.i(DataStore_System.TAG,"Activity_Main.initializeApp(); User logged in already.. launching Wall Activity");
            Intent intent = new Intent(this, Activity_Wall.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(intent);
        }
    }
}
