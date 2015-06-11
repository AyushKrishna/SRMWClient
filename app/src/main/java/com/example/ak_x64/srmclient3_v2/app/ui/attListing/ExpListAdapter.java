package com.example.ak_x64.srmclient3_v2.app.ui.attListing;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.ak_x64.srmclient3_v2.R;

/** This class supplies data to the ExpandableListView.*/
public class ExpListAdapter extends BaseExpandableListAdapter {

    FragmentActivity frag_activity;
    FragmentManager fm;

    CheckBoxListener cul;

    private String[] groups = {"Get info about"};
     //TODO : Implement sublist as 1D String array and add functionality of changing expandable listview options if user wants to see friends attendance
     private String[][] subList;

    public ExpListAdapter(FragmentActivity activity, FragmentManager childFragmentManager) {
        this.frag_activity=activity;
        fm= childFragmentManager;
        cul=new CheckBoxListener();
        Log.d(DataStore_Attlisting.TAG, "ExpListAdapter constructor without Subject List");
        //TODO : add : subList=new String[]{"All"} here;
    }

    public ExpListAdapter(FragmentActivity activity, FragmentManager childFragmentManager,String[][] subList) {
        this.frag_activity=activity;
        fm= childFragmentManager;
        this.subList=subList;
        cul=new CheckBoxListener();
        Log.d(DataStore_Attlisting.TAG, "ExpListAdapter constructor with Subject List");
    }

    @Override
    public int getGroupCount() {
        return groups.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
         return subList.length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        //Log.d(DataStore_Attlisting.TAG,"Object getChild() -> "+groupPosition+";"+childPosition);
            return subList[childPosition][2];
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
       // Log.d(DataStore_Attlisting.TAG,"getChildId(int ,int) -> "+i1);
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        //Log.d(MainActivity.TAG,"getGroupView()");
        convertView = DataStore_Attlisting.mainLayoutInflater.inflate(R.layout.attlisting_explistview_parent,parent,false);
        ((TextView)convertView.findViewById(R.id.parent_text)).setText(groups[groupPosition]);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        //Log.d(MainActivity.TAG,"getChildView()");

            /** lOGCAT error : addView(View, LayoutParams) is not supported in AdapterView” in a ListView
             View v=inflater.inflate(R.layout.viewer_viewpager_fragment,null);
             ViewPager pager = (ViewPager) v.findViewById(R.id.viewPager);
             MyPagerAdapter pa = new MyPagerAdapter(fm);
             pager.setAdapter(pa);
             Log.d(MainActivity.TAG, "Fragment created");
             return pager;
             */
            ViewHolder holder = null;
            if(convertView==null) {
                holder = new ViewHolder();
                convertView = DataStore_Attlisting.mainLayoutInflater.inflate(R.layout.attlisting_explistview_child, parent, false);
                holder.textview=(TextView)convertView.findViewById(R.id.child_text);
                holder.cb=(CheckBox)convertView.findViewById(R.id.child_checkbox);
                holder.cb.setOnCheckedChangeListener(cul);
                convertView.setTag(holder);
            }
        else
            {
                holder = (ViewHolder)convertView.getTag();
            }
        holder.textview.setText(subList[childPosition][1]);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    public static class ViewHolder {
        public TextView textview;
        public CheckBox cb;
    }
}

class ExpListViewListener implements ExpandableListView.OnGroupClickListener,ExpandableListView.OnChildClickListener,ExpandableListView.OnGroupCollapseListener,ExpandableListView.OnGroupExpandListener
{

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
       // Log.d(MainActivity.TAG,"Parent clicked ; groupPosition -> "+groupPosition);
        return false;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Log.d(DataStore_Attlisting.TAG,"Child clicked ; childPosition -> "+childPosition);
        return false;
    }

    @Override
    public void onGroupCollapse(int groupPosition) {
      //  Log.d(MainActivity.TAG,"Group collapse groupPosition -> "+groupPosition);
    }

    @Override
    public void onGroupExpand(int groupPosition) {
       // Log.d(MainActivity.TAG,"Group expand groupPosition -> "+groupPosition);
    }
}

class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {

    public CheckBoxListener() {

    }

    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Log.d(DataStore_Attlisting.TAG, "CheckUpdateListener.onCheckedChanged(CompoundButton,boolean); isChecked: " + isChecked);

        ViewGroup parent = (ViewGroup) buttonView.getParent(); // this will return a TableRow ViewGroup as we can see in xml file
        //Log.d("TEST", "parent class -> " + parent.getClass().toString());
        if (parent instanceof ViewGroup) {
            TextView tv = (TextView) parent.findViewById(R.id.child_text);
            String s = tv.getText().toString();
            if (isChecked == true && !DataStore_Attlisting.checkBoxText.contains(s)) {
                DataStore_Attlisting.checkBoxText.add(s);
                Log.d(DataStore_Attlisting.TAG, "TextView text added ->" + s+"; total items->"+DataStore_Attlisting.checkBoxText.size());
            }
            if (isChecked == false) {
                DataStore_Attlisting.checkBoxText.remove(s);
                Log.d(DataStore_Attlisting.TAG, "TextView text removed ->" + s+"; total items->"+DataStore_Attlisting.checkBoxText.size());
            }
        }
    }
}


