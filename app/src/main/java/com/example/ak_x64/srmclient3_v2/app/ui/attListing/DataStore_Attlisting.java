package com.example.ak_x64.srmclient3_v2.app.ui.attListing;

import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;

import java.util.ArrayList;

public class DataStore_Attlisting {

// TODO: remove the need for these variables by using Activity arguement passed in the Fragment's onAttch() method.
public static FragmentManager mainActivityFM;
public static LayoutInflater mainLayoutInflater;
//remove the need for these variables by using Activity arguement passed in the Fragment's onAttch() method.

public static String frndID;

/** TODO:
 * This variable tells that user entered something in "Friend Attendance" box. This changes the options in
     * the expandable list as per user text in EditText. If the user wants to see his att only then
     * the edittext has to be blank and the expandable list view will then contain all the list of subjects but if he enters any
     * other roll no. then the expandable listview will only show "All" option.
     * USAGE :- This variable will be checked twice, ie, first when the
     * user clicks on Subject Info List and secondly when user clicks on "Go" button.If at any instance
     * the edit text contains any text, the expandable list view option will be changed even if user
     * selected some data beforehand.
     *
     * @param frndatt - true if edit text contains any text meaning listview will only show "ALL" option
     */
public static boolean frndatt;

public static ArrayList<String> checkBoxText; // stores the subject number of checked box by user

public static String TAG="attlisting";

public DataStore_Attlisting()
{
    checkBoxText =new ArrayList<String>();
}

}
