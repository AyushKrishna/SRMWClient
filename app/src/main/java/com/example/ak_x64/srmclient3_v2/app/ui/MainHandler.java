package com.example.ak_x64.srmclient3_v2.app.ui;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.DataStore_connection;
import com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.containerClasses.Information;
import com.example.ak_x64.srmclient3_v2.app.system.DataStore_System;
import com.example.ak_x64.srmclient3_v2.app.system.FileLocation;
import com.example.ak_x64.srmclient3_v2.app.system.FileManager;
import com.example.ak_x64.srmclient3_v2.app.ui.viewer.Activity_Viewer;
import com.example.ak_x64.srmclient3_v2.app.ui.wall.Activity_Wall;
import com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.Login;
import com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.UserData;

public class MainHandler extends Handler {

    // HANDLER CONSTANTS
    public static final int GOT_LOGIN=0;
    public static final int GOT_USERDATA=1;
    public static final int GOT_ATTENDANCE=2;
    public static final int GOT_INFORMATION=3;

    MainHandler()
    {
        super();
    }

    MainHandler(Handler.Callback callback)
    {
        super(callback);
    }

    MainHandler(Looper looper)
    {
        super(looper);
    }

    MainHandler(Looper looper, Handler.Callback callback)
    {
        super(looper,callback);
    }

    public void handleMessage(Message msg)
    {
    if(msg.what== GOT_INFORMATION)
    gotInformation(msg);

    if(msg.what== GOT_USERDATA)
    gotUserData(msg);

    if(msg.what== GOT_ATTENDANCE)
    gotAttendance(msg);

    if(msg.what== GOT_LOGIN)
    gotLogin(msg);
    }

    private void gotInformation(Message msg) {
        Information ob=(Information)msg.obj;
        Log.i(DataStore_connection.TAG,"Process.process; Got Information object from ->"+ob.getClientID());
        Log.i(DataStore_connection.TAG,"Message -> "+ob.getMsg());
    }

    private void gotLogin(Message msg) {
        Log.d(DataStore_System.TAG, "MainHandler.handleMessage(); Got Login+UserData.. Launching Wall activity");
        Login ob=(Login)msg.obj;
        if (DataStore_System.ID != ob.getNewID()) {
            DataStore_System.ID = ob.getNewID();
            DataStore_System.user=ob.getUsername();
            DataStore_System.pass=ob.getPassword();

            DataStore_System.subList=ob.getUserDataObject().getAttSubjectList();
            Intent intent = new Intent(DataStore_System.applicationContext, Activity_Wall.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);

            //Log.d(DataStore_System.TAG,"Process.process; checking intent -> " + intent.toString());
            DataStore_System.applicationContext.startActivity(intent);
        }
    }

    private void gotUserData(Message msg) {
        Log.d(DataStore_System.TAG, "MainHandler.handleMessage(); Got only UserData.. Launching Wall activity");
        UserData ud=(UserData)msg.obj;
        DataStore_System.subList=ud.getAttSubjectList();
        Intent intent = new Intent(DataStore_System.applicationContext, Activity_Wall.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);

        //Log.d(DataStore_System.TAG,"Process.process; checking intent -> " + intent.toString());
        DataStore_System.applicationContext.startActivity(intent);
    }

    private void gotAttendance(Message msg) {
        Log.d(DataStore_System.TAG, "MainHandler.handleMessage(); Got Attendance");

        FileManager.writeSerializedObject(msg.obj, FileLocation.attendanceFile.getPath());

        Notifications.displayNotification(DataStore_System.applicationContext,NotificationTypes.ATTENDANCE);

        Intent intent = new Intent(DataStore_System.applicationContext, Activity_Viewer.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("filepath",FileLocation.attendanceFile.getPath());
        //Log.d(DataStore_System.TAG,"Process.process; checking intent -> " + intent.toString());
        DataStore_System.applicationContext.startActivity(intent);
    }
}
