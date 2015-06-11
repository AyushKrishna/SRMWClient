
package com.example.ak_x64.srmclient3_v2.app.ui.viewer;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ak_x64.srmclient3_v2.R;

/** This fragment will contain the View Pager.
 */

public class PagerFragment extends Fragment {

    Activity myActivity;

    public static PagerFragment newInstance(String filepath) { // it is mandatory to initialize fragment using this method not constructor
        PagerFragment f = new PagerFragment();
        Bundle b = new Bundle();
        b.putString("filepath",filepath);
        f.setArguments(b); // this way we attach bundle with the fragment, so that when fragment is launched Bundle is accessible
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.v(DataStore_Viewer.TAG, "PagerFragment.onCreateView(LayoutInflater,ViwGroup,Bundle);");
        View v = inflater.inflate(R.layout.viewer_viewpager_fragment, container, false);

        Bundle bundle = this.getArguments();
        String filepath=bundle.getString("filepath");

        ViewPager pager = (ViewPager) v.findViewById(R.id.viewPager);
        pager.setAdapter(new MyPagerAdapter(DataStore_Viewer.mainActivityFM,filepath));

        return v;
    }


    @Override
    public void onCreate(Bundle icicle) {
        Log.v(DataStore_Viewer.TAG, "in PagerFragment onCreate. Got Bundle :");
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
        Log.v(DataStore_Viewer.TAG, "in PagerFragment onActivityCreated.Got Bundle :");
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
        Log.v(DataStore_Viewer.TAG,
                "in PagerFragment onInflate. Got Activity,AttributeSet,Bundle");
        super.onInflate(myActivity, attrs, icicle);
    }
    @Override
    public void onAttach(Activity myActivity) {
        Log.v(DataStore_Viewer.TAG, "in PagerFragment onAttach; activity is: " + myActivity);
        super.onAttach(myActivity);
        this.myActivity = myActivity;
    }

    @Override
    public void onStop() {
        Log.v(DataStore_Viewer.TAG, "in PagerFragment onStop");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        Log.v(DataStore_Viewer.TAG, "in PagerFragment onDestroyView");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.v(DataStore_Viewer.TAG, "in PagerFragment onDestroy");
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        Log.v(DataStore_Viewer.TAG, "in PagerFragment onDetach");
        super.onDetach();
        myActivity = null;
    }
    @Override
    public void onStart() {
        Log.v(DataStore_Viewer.TAG, "in PagerFragment onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.v(DataStore_Viewer.TAG, "in PagerFragment onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.v(DataStore_Viewer.TAG, "in PagerFragment onPause");
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.v(DataStore_Viewer.TAG, "in PagerFragment onSaveInstanceState;adding dummy data to Bumdle");
        super.onSaveInstanceState(outState);
        outState.putInt("curChoice", 1);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        Log.v(DataStore_Viewer.TAG, "in PagerFragment onViewStateRestored");

        if(savedInstanceState==null)
        Log.d(DataStore_Viewer.TAG,"savedInstanceState is null");
        else {
            Log.d(DataStore_Viewer.TAG,"savedInstanceState not null");
            int i=savedInstanceState.getInt("curChoice");
            Log.d(DataStore_Viewer.TAG,"savedInstance -> Got data curchoice="+i);
        }
        super.onViewStateRestored(savedInstanceState);
    }
}