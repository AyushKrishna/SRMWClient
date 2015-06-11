package com.example.ak_x64.srmclient3_v2.app.ui.viewer;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ak_x64.srmclient3_v2.R;
import com.example.ak_x64.srmclient3_v2.app.system.DataStore_System;

/** This fragment will be inside View Pager and will show only 1 table (including header and footer).
 * The View Pager can have multiple instance of this class for more than 1 table
 */
public class Page extends Fragment{
    Activity myActivity;
    String[][] table;

    public static Page newInstance(String[][] table) { // it is mandatory to initialize fragment using this method not constructor
        Page f = new Page();
        Bundle b = new Bundle();
        b.putSerializable("table",table);
        f.setArguments(b); // this way we attach bundle with the fragment, so that when fragment launches Bundle is accessible
        Log.d(DataStore_Viewer.TAG,"in Page.newInstance(String[][])");
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(DataStore_Viewer.TAG, "in Page.onCreateView(LayoutInflater,ViewGroup,Bundle);");
       View v = inflater.inflate(R.layout.viewer_page_fragment, container, false);

        Bundle attachedBundle=getArguments(); // In this way you can get the attached bundle with this
        // fragment attached during newInstance() method
        table=(String[][])attachedBundle.getSerializable("table");

        ScrollView sv = new ScrollView(DataStore_System.applicationContext);
        TableLayout tableLayout = createTableLayout(table);
        HorizontalScrollView hsv = new HorizontalScrollView(DataStore_System.applicationContext);

        hsv.addView(tableLayout);
        sv.addView(hsv);

        return sv;
    }

    private TableLayout createTableLayout(String[][] table) {
        // 1) Create a tableLayout and its params
        TableLayout.LayoutParams tableLayoutParams = new TableLayout.LayoutParams();
        TableLayout tableLayout = new TableLayout(DataStore_System.applicationContext);
        tableLayout.setBackgroundColor(Color.BLACK);

        // 2) create tableRow params
        TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
        tableRowParams.setMargins(1, 1, 1, 1);
        tableRowParams.weight = 1;

        for (int i = 0; i < table.length; i++) {
            // 3) create tableRow
            TableRow tableRow = new TableRow(DataStore_System.applicationContext);

            for (int j= 0; j < table[i].length; j++) {
                // 4) create textView
                TextView textView = new TextView(DataStore_System.applicationContext);
                textView.setText(table[i][j]);
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundColor(Color.WHITE);
                textView.setGravity(Gravity.CENTER);

                // 5) add textView to tableRow
                tableRow.addView(textView, tableRowParams);
            }
            // 6) add tableRow to tableLayout
            tableLayout.addView(tableRow, tableLayoutParams);
        }

        return tableLayout;
    }


    /** OLD CODE
    private void createTable(View v) {
        TableLayout tableLayout=(TableLayout)v.findViewById(R.id.viewPager_table);
        //tableLayout.setLayoutParams(tableLayout.getLayoutParams());
        tableLayout.setPadding(15, 15, 15, 15);
        //tableLayout.setStretchAllColumns(true);

        setDataInTable(tableLayout,table);

        //fl.addView(tableLayout);
        Log.d(DataStore_Viewer.TAG,"Data set in table");
    }

    private void setDataInTable(TableLayout table,String[][] data) {

        for(String[] x:data)
        {
            TableRow row = new TableRow(DataStore_System.applicationContext);
            for(String y:x) {
                TextView t = new TextView(DataStore_System.applicationContext);
                t.setTextColor(Color.parseColor("#000000"));
                t.setText(y);
                LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
                t.setLayoutParams(params);
                row.addView(t);
            }
            table.addView(row,new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
        }
    }
*/

    @Override
    public void onCreate(Bundle icicle) {
        Log.v(DataStore_Viewer.TAG, "in Page.onCreate(Bundle); Got Bundle :");
        if(icicle != null) {
            for(String key : icicle.keySet()) {
                Log.v(DataStore_Viewer.TAG, "    " + key);
            }
        }
        else {
            Log.v(DataStore_Viewer.TAG, "    myBundle is null");
        }
        super.onCreate(icicle);
    }

    @Override
    public void onActivityCreated(Bundle icicle) {
        Log.v(DataStore_Viewer.TAG, "in Page.onActivityCreated(Bundle);Got Bundle :");
        if(icicle != null) {
            for(String key : icicle.keySet()) {
                Log.v(DataStore_Viewer.TAG, "Bundle contains ->  " + key);
            }
        }
        else {
            Log.v(DataStore_Viewer.TAG, "    savedState is null");
        }
        super.onActivityCreated(icicle);
    }

    public void onInflate(Activity myActivity, AttributeSet attrs, Bundle icicle) {
        Log.v(DataStore_Viewer.TAG,"in Page.onInflate(Activity,AttributeSet,Bundle);");
        super.onInflate(myActivity, attrs, icicle);
    }

    @Override
    public void onAttach(Activity myActivity) {
        Log.v(DataStore_Viewer.TAG, "in Page.onAttach(Activity); activity is: " + myActivity);
        super.onAttach(myActivity);
        this.myActivity = myActivity;
    }

    @Override
    public void onStop() {
        Log.v(DataStore_Viewer.TAG, "in Page.onStop()");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.v(DataStore_Viewer.TAG, "in Page.onDestroyView()");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.v(DataStore_Viewer.TAG, "in Page.onDestroy();");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.v(DataStore_Viewer.TAG, "in Page.onDetach()");
        super.onDetach();
        myActivity = null;
    }
    @Override
    public void onStart() {
        Log.v(DataStore_Viewer.TAG, "in Page.onStart()");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.v(DataStore_Viewer.TAG, "in Page.onResume()");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.v(DataStore_Viewer.TAG, "in Page.onPause()");
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.v(DataStore_Viewer.TAG, "in Page.onSaveInstanceState(Bundle);adding dummy data to Bundle");
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", 1);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        Log.v(DataStore_Viewer.TAG, "in Page.onViewStateRestored(Bundle)");

        if (savedInstanceState == null)
            Log.d(DataStore_Viewer.TAG, "savedInstanceState is null");
        else {
            Log.d(DataStore_Viewer.TAG, "savedInstanceState not null");
            int i = savedInstanceState.getInt("curChoice");
            Log.d(DataStore_Viewer.TAG, "savedInstance -> Got data curchoice=" + i);
        }
        super.onViewStateRestored(savedInstanceState);
    }
    }
