package com.example.ak_x64.srmclient3_v2.app.ui.login;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.ak_x64.srmclient3_v2.R;
import com.example.ak_x64.srmclient3_v2.app.system.DataStore_System;
import com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.DataStore_connection;
import com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.Login;


public class Activity_Login extends ActionBarActivity {

    EditText user;
    EditText pass;
    Button login;
    Button quit;
    Button b_connect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Log.d(DataStore_System.TAG,"Activity_Login.onCreate; Started Login Activity");

        user=(EditText)findViewById(R.id.edittext_username);
        pass=(EditText)findViewById(R.id.edittext_password);

        quit=(Button)findViewById(R.id.button_exit);
        quit.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                DataStore_System.abruptQuit();
            }
        });

        login=(Button)findViewById(R.id.button_Login);
        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                Login lg=new Login("Host",DataStore_connection.location,
                        DataStore_System.ID,DataStore_System.task_ID++,user.getText().toString(),
                        pass.getText().toString());
                DataStore_System.sendToServer(DataStore_connection.srmw_socketChannel,lg);
            }
        });

        b_connect=(Button)findViewById(R.id.b_connect);
        b_connect.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                DataStore_System.connectToServer();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_login, menu);
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
