package com.example.ak_x64.srmclient3_v2.app.ui.attListing;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.example.ak_x64.srmclient3_v2.app.system.DataStore_System;
import com.example.ak_x64.srmclient3_v2.R;

import com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.DataStore_connection;
import com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.Attendance;

public class AttendanceFragment extends Fragment {

    LayoutInflater inflater;
    Activity myActivity;

    public static Fragment newInstance() {
        Fragment af=new AttendanceFragment();
        return af;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.inflater=inflater;
        Log.d(DataStore_Attlisting.TAG, "in AttendanceFragment.onCreateView()");
        View rootView = inflater.inflate(R.layout.attlisting_fragment, container, false);

        EditText et=(EditText)rootView.findViewById(R.id.edittext_frndrollno);
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Log.d(DataStore_Attendance.TAG, "AttendanceFragment.beforeTextChanged(CharSequence,int,int,int); s->"+s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Log.d(DataStore_Attendance.TAG, "AttendanceFragment.onTextChanged(CharSequence,int,int,int); s->"+s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d(DataStore_Attlisting.TAG, "AttendanceFragment.afterTextChanged(Editable); s->"+s.toString());
                if (s.length()>0) {
                    DataStore_Attlisting.frndatt = true;
                    DataStore_Attlisting.frndID=s.toString();
                }
                    else {
                    DataStore_Attlisting.frndatt=false;
                    DataStore_Attlisting.frndID=null;
                }
            }
        });

        Button b=(Button)rootView.findViewById(R.id.Button_AttGo);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Attendance at=null;
                if(DataStore_Attlisting.frndatt==true)
                {
                    //TODO: check DataStore_PplSoft.checkBoxText values.. it can only be null or 'All'
                }
                else {
                    at = new Attendance("Host", DataStore_connection.location, DataStore_System.ID,
                            DataStore_System.task_ID++,DataStore_System.user,DataStore_System.pass, DataStore_Attlisting.checkBoxText.toArray(new String[0]));
                }
                Log.d(DataStore_Attlisting.TAG,"Button clicked... Sending attendance list");
                DataStore_System.sendToServer(DataStore_connection.srmw_socketChannel,at);
            }
        });

        Log.d(DataStore_Attlisting.TAG,"AttendanceFragment.onCreateView() ; Creating expandable list view");

        ExpandableListView elv = (ExpandableListView) rootView.findViewById(R.id.list);
        ExpListAdapter ela=new ExpListAdapter(getActivity(),getChildFragmentManager(),DataStore_System.subList);
        ExpListViewListener elvl=new ExpListViewListener();

        elv.setOnChildClickListener(elvl);
        elv.setOnGroupClickListener(elvl);
        elv.setOnGroupCollapseListener(elvl);
        elv.setOnGroupExpandListener(elvl);

        elv.setAdapter(ela);

        return rootView;
    }

    @Override
    public void onCreate(Bundle icicle) {
        Log.v(DataStore_Attlisting.TAG, "AttendanceFragment.onCreate(Bundle);Got Bundle :");
        if(icicle != null) {
            for(String key : icicle.keySet()) {
                Log.v(DataStore_Attlisting.TAG, "    " + key);
            }
        }
        else {
            Log.v(DataStore_Attlisting.TAG, "    myBundle is null");
        }
        super.onCreate(icicle);

    }

    @Override
    public void onActivityCreated(Bundle icicle) {
        Log.v(DataStore_Attlisting.TAG, "AttendanceFragment.onActivityCreated(Bundle) ; Got Bundle :");
        if(icicle != null) {
            for(String key : icicle.keySet()) {
                Log.v(DataStore_Attlisting.TAG, "Bundle contains ->  " + key);
            }
        }
        else {
            Log.v(DataStore_Attlisting.TAG, "    savedState is null");
        }
        super.onActivityCreated(icicle);
    }

    public void onInflate(Activity myActivity, AttributeSet attrs, Bundle icicle) {
        Log.v(DataStore_Attlisting.TAG,
                "AttendanceFragment.onInflate(Activity,AttributeSet,Bundle)");
        super.onInflate(myActivity, attrs, icicle);
    }
    @Override
    public void onAttach(Activity myActivity) {
        Log.v(DataStore_Attlisting.TAG, "AttendanceFragment.onAttach(Activity); Got activity : " + myActivity);
        super.onAttach(myActivity);
        this.myActivity = myActivity;
    }

    @Override
    public void onStop() {
        Log.v(DataStore_Attlisting.TAG, "AttendanceFragment.onStop();");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.v(DataStore_Attlisting.TAG, "AttendanceFragment.onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.v(DataStore_Attlisting.TAG, "AttendanceFragment.onDestroy()");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.v(DataStore_Attlisting.TAG, "AttendanceFragment.onDetach()");
        super.onDetach();
        myActivity = null;
    }
    @Override
    public void onStart() {
        Log.v(DataStore_Attlisting.TAG, "AttendanceFragment.onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.v(DataStore_Attlisting.TAG, "AttendanceFragment.onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.v(DataStore_Attlisting.TAG, "AttendanceFragment.onPause()");
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.v(DataStore_Attlisting.TAG, "AttendanceFragment.onSaveInstanceState(Bundle);adding dummy data to Bundle");
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", 1);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        Log.v(DataStore_Attlisting.TAG, "AttendanceFragment.onViewStateRestored(Bundle);");

        if(savedInstanceState==null)
            Log.d(DataStore_Attlisting.TAG,"savedInstanceState is null");
        else {
            Log.d(DataStore_Attlisting.TAG,"savedInstanceState not null");
        }
        super.onViewStateRestored(savedInstanceState);
    }
}


