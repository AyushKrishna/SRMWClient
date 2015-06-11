package com.example.ak_x64.srmclient3_v2.app.system;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.example.ak_x64.srmclient3_v2.app.services.network.DataStore_Network;
import com.example.ak_x64.srmclient3_v2.app.services.network.NetworkService;
import com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.DataStore_connection;
import com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.NetConnection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.SocketChannel;

public class DataStore_System {

    // ANDROID VARIABLES
    public static Handler mainHandler;
    public static Context applicationContext;

    // ANDROID CONSTANTS
    public static String TAG="System";

    // USER-DATA
    public static int ID;
    public static int task_ID;
    public static String user;
    public static String pass;
    public static String version="1.0";
    public static String[][] subList; // TODO: Make subList 1D array so that it can be easily implemented in ListView (along with "All" String)
    public static String[][] subjectMap; // TODO : this will contain subject numbers in first column and name in second
    public static String[][] marksMap; //TODO : this will contain mark subject index in first column and name in second


    public DataStore_System(Context applicationContext) {
        this.applicationContext = applicationContext;
        //FileManager.loadFileManager(applicationContext);

        try {
            ID = (Integer) FileManager.readSerializedObject(FileLocation.userIDFile.getPath());
        } catch (NullPointerException e) {
            Log.d(DataStore_System.TAG, "NullPointerException in reading id.data file... so setting ID=0");
            ID = 0;
        }
        user = (String) FileManager.readSerializedObject(FileLocation.pplsoftIDFile.getPath());
        pass = (String) FileManager.readSerializedObject(FileLocation.pplsoftPasswordFile.getPath());
        subList = (String[][]) FileManager.readSerializedObject(FileLocation.attSubjectList.getPath());

        Log.i(DataStore_System.TAG, "in DataStore_System() ; ID->" + ID + "; user->" + user + "; pass->" + pass+" ; External dir path ->"+FileLocation.rootExternalDir.getPath());
    }

    public static void connectToServer()
    {
        NetworkService.connectToServer();
    }

    public static void sendToServer(SocketChannel sc,Object obj)
    {
        if(DataStore_connection.srmw_socketChannel==null) {
            Log.e(DataStore_System.TAG,"DataStore_System.sendToServer(SocketChannel,Object); DataStore_connection.srmw_socketChannel==null ; isInternetAvailable -> "+ DataStore_Network.isInternetAvailable());
        }
        else {
            //Log.d(DataStore_connection.TAG, "Sending object to -> " + sc.socket().toString());
            Log.d(DataStore_connection.TAG,"Sending obj -> "+obj.getClass().toString());
            NetConnection.writeToSocketChannel(sc, obj);
        }
    }

    public static byte[] serialize(Object obj) {
        byte[] arr=null;
        try
        {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(obj);
            arr=out.toByteArray();
            os.close();
            out.close();
        }
        catch(IOException e)
        {
            System.out.println("EXception while serializing");
            e.printStackTrace(System.out);
        }
        System.out.println("No.of bytes after serializing -> "+arr.length);
        return arr;
    }

    public static Object deserialize(byte[] data) {
        Object obj=null;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);
            obj=is.readObject();
            is.close();
            in.close();
        }
        catch (ClassNotFoundException | IOException e) {
            System.out.println("Error in converting byte to object");
            e.printStackTrace();
        }
        return obj;
    }

    public static void abruptQuit() {
        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);
    }
}
