package com.example.ak_x64.srmclient3_v2.app.ui.viewer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ak_x64.srmclient3_v2.R;

public class Activity_Viewer extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        DataStore_Viewer.mainActivityFM = getSupportFragmentManager();
        DataStore_Viewer.mainLayoutInflater= LayoutInflater.from(this);

        String filepath=initializingViewer();

        //FragmentManager.enableDebugLogging(true);
        FragmentTransaction transaction= getSupportFragmentManager().beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        transaction.replace(R.id.viewer_fragment_container, PagerFragment.newInstance(filepath));
        //transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
    }

    private String initializingViewer() {
        Intent intent = getIntent();
        String path=intent.getExtras().getString("filepath");
        return path;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_viewer, menu);
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
