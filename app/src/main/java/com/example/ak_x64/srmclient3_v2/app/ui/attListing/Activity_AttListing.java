package com.example.ak_x64.srmclient3_v2.app.ui.attListing;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ak_x64.srmclient3_v2.R;

public class Activity_AttListing extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attlisting);

        //ADD IN THE STARTING
        new DataStore_Attlisting();
        DataStore_Attlisting.mainActivityFM = getSupportFragmentManager();
        DataStore_Attlisting.mainLayoutInflater= LayoutInflater.from(this);
        // ADD IN THE STARTING

        //FragmentManager.enableDebugLogging(true);

        Log.v(DataStore_Attlisting.TAG,"Activity_AttListing.onCreate(Bundle); executed");

        Fragment af=AttendanceFragment.newInstance();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();

    // Replace whatever is in the fragment_container view with this fragment,
    // and add the transaction to the back stack
        transaction.replace(R.id.fragment_container, af);
       // transaction.addToBackStack(null);

    // Commit the transaction
        transaction.commit();

    }


    @Override
    protected void onStop() {
        Log.d(DataStore_Attlisting.TAG,"Activity_PplSoft.onStop(); called");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.v(DataStore_Attlisting.TAG,"Activity_PplSoft.onDestroy(); called");
        super.onDestroy();
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.v(DataStore_Attlisting.TAG,"Activity_PplSoft.onActivityResult(int,int,Intent); called");
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        Log.v(DataStore_Attlisting.TAG,"Activity_PplSoft.onPause(); called");
        super.onPause();
    }

    /**
     * Handle onNewIntent() to inform the fragment manager that the
     * state is not saved.  If you are handling new intents and may be
     * making changes to the fragment state, you want to be sure to call
     * through to the super-class here first.  Otherwise, if your state
     * is saved but the activity is not stopped, you could get an
     * onNewIntent() call which happens before onResume() and trying to
     * perform fragment operations at that point will throw IllegalStateException
     * because the fragment manager thinks the state is still saved.
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        Log.v(DataStore_Attlisting.TAG,"Activity_PplSoft.onPause(); called");
        super.onNewIntent(intent);
    }

    /**
     * Dispatch onResume() to fragments.  Note that for better inter-operation
     * with older versions of the platform, at the point of this call the
     * fragments attached to the activity are <em>not</em> resumed.  This means
     * that in some cases the previous state may still be saved, not allowing
     * fragment transactions that modify the state.  To correctly interact
     * with fragments in their proper state, you should instead override
     * {@link #onResumeFragments()}.
     */
    @Override
    protected void onResume() {
        Log.v(DataStore_Attlisting.TAG,"Activity_PplSoft.onResume(); called");
        super.onResume();
    }

    /**
     * This is the fragment-orientated version of {@link #onResume()} that you
     * can override to perform operations in the Activity at the same point
     * where its fragments are resumed.  Be sure to always call through to
     * the super-class.
     */
    @Override
    protected void onResumeFragments() {
        Log.v(DataStore_Attlisting.TAG,"Activity_PplSoft.onResumeFragments(); called");
        super.onResumeFragments();
    }

    /**
     * Save all appropriate fragment state.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.v(DataStore_Attlisting.TAG,"Activity_PplSoft.onSaveInstanceState(Bundle); called");
        super.onSaveInstanceState(outState);
    }

    /**
     * Dispatch onStart() to all fragments.  Ensure any created loaders are
     * now started.
     */
    @Override
    protected void onStart() {
        Log.v(DataStore_Attlisting.TAG,"Activity_PplSoft.onStart(); called");
        super.onStart();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        Log.v(DataStore_Attlisting.TAG,"Activity_PplSoft.attachBaseContext(Context); called");
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        boolean temp=false;
        if(savedInstanceState!=null)
            temp=true;
        Log.v(DataStore_Attlisting.TAG,"Activity_PplSoft.onRestoreInstanceState(Bundle); Bundle null ->"+temp);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        Log.v(DataStore_Attlisting.TAG,"Activity_PplSoft.onRestart(); called");
        super.onRestart();
    }

    /**
     * Called as part of the activity lifecycle when an activity is about to go
     * into the background as the result of user choice.  For example, when the
     * user presses the Home key, {@link #onUserLeaveHint} will be called, but
     * when an incoming phone call causes the in-call Activity to be automatically
     * brought to the foreground, {@link #onUserLeaveHint} will not be called on
     * the activity being interrupted.  In cases when it is invoked, this method
     * is called right before the activity's {@link #onPause} callback.
     * <p/>
     * <p>This callback and {@link #onUserInteraction} are intended to help
     * activities manage status bar notifications intelligently; specifically,
     * for helping activities determine the proper time to cancel a notfication.
     *
     * @see #onUserInteraction()
     */
    @Override
    protected void onUserLeaveHint() {
        Log.v(DataStore_Attlisting.TAG,"Activity_PplSoft.onUserLeaveHint(); called");
        super.onUserLeaveHint();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        Log.v(DataStore_Attlisting.TAG,"Activity_PplSoft.onAttachFragment(Fragment); called");
        super.onAttachFragment(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
