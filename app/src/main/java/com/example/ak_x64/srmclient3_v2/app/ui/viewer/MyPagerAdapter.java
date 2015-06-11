package com.example.ak_x64.srmclient3_v2.app.ui.viewer;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.widget.Toast;

import com.example.ak_x64.srmclient3_v2.app.system.DataStore_System;
import com.example.ak_x64.srmclient3_v2.app.system.FileManager;
import com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.Attendance;

/** This class supplies fragments to the View Pager.*/
class MyPagerAdapter extends FragmentPagerAdapter {

    FragmentManager fragmentManager;
    Attendance attendance;

    public MyPagerAdapter(FragmentManager fm,String filepath) {
        super(fm);
        fragmentManager=fm;
        loadData(filepath);
        Log.v(DataStore_Viewer.TAG,"Constructor of MyPagerAdapter(FragmentManager,Object)");
    }

    private void loadData(String filepath) {
        Object obj= FileManager.readSerializedObject(filepath);

        if(obj==null)
        {
            Toast.makeText(DataStore_System.applicationContext,"Cannot load Attendance... file is either corrupt or missing",Toast.LENGTH_LONG).show();
            Log.e(DataStore_Viewer.TAG,"Attendance file missing");
            return;
        }

        if(obj instanceof Attendance)
        attendance=(Attendance)obj;
    }

    @Override
    public Fragment getItem(int pos) {
        String[][][] attTables=attendance.getAttTables();
        return Page.newInstance(attTables[pos]);
    }

    @Override
    public int getCount() {
        //Log.d(DataStore_Viewer.TAG,"MyPagerAdapter.getCount; total Tables in Att -> "+ DataStore_Attlisting.attendance.attTables.length);
        return attendance.getAttTables().length;
    }
}
