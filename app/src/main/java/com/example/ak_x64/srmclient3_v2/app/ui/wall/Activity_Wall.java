package com.example.ak_x64.srmclient3_v2.app.ui.wall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ak_x64.srmclient3_v2.R;
import com.example.ak_x64.srmclient3_v2.app.system.FileLocation;
import com.example.ak_x64.srmclient3_v2.app.system.FileManager;
import com.example.ak_x64.srmclient3_v2.app.ui.attListing.Activity_AttListing;
import com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.Attendance;

public class Activity_Wall extends ActionBarActivity {

    public static final String TAG="wall";
    Button buttonAttListing;
    TextView att_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall);

        final Context ctx=this;

        att_date=(TextView)findViewById(R.id.tv_date);
        setDateOnAttTextView();

        buttonAttListing=(Button)findViewById(R.id.button_attlisting);
        buttonAttListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(ctx, Activity_AttListing.class);
                Log.d(TAG,"Activity_Wall.onCreate(); Launching Activity_AttListing");
                ctx.startActivity(i);
            }
        });
    }

    private void setDateOnAttTextView() {
        Attendance at= ((Attendance)FileManager.readSerializedObject(FileLocation.attendanceFile.getPath()));
        if(at!=null)
        att_date.setText(at.getDate());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_wall, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
