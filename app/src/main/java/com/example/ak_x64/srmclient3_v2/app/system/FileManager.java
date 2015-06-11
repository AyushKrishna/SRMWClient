package com.example.ak_x64.srmclient3_v2.app.system;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/** This class handles all the file related operations ie, read or write of files from the internal
 * or external storage.
 *
 * Created by AK on 23-03-2015.
 */

public class FileManager {

    public static void loadFiles(Context context)
    {
        Log.d(DataStore_System.TAG,"in FileManager.loadFileManager();");
        //androidInternalDir=context.getFilesDir().toString();
        //userID=androidInternalDir+"/UserData/id.data";
        //pplsoftID=androidInternalDir+"/UserData/pplsoftID.data";
        //pplsoftPassword=androidInternalDir+"/UserData/password.data";
        //attSubjectList=androidInternalDir+"/UserData/subjectList.data";
        //appSDCardDir =androidExternalDir+"/SRMapp";
        //attObjectStoreLoc=appSDCardDir+"/Attendance.data";
    }

    // writes Object in a file(overwrites) specified by filepath; can be used to write in both internal and external storage
    public static void writeSerializedObject(Object ob, String filepath)
    {
        String s=filepath.substring(0,filepath.lastIndexOf("/"));
        File folder=new File(s);
        if(!folder.exists())
        {
            boolean t=folder.mkdirs();
            Log.i("writeSerializedObject()", "Folder " + s + " created -> " + t);
        }

        byte[] buf=DataStore_System.serialize(ob);
        try {
            RandomAccessFile f = new RandomAccessFile(filepath,"rw");
            f.write(buf);
            f.close();
        }catch(IOException e){
            Log.w("writeSerializedObject()","Exception writing to filepath -> "+filepath);
            e.printStackTrace();
        }
    }

    // returns the data of a file in Object form; can be used to read both internal and external file
    public static Object readSerializedObject(String filepath) {
        byte[] buf=null;

        try{
            RandomAccessFile f = new RandomAccessFile(new File(filepath), "r");
            buf=new byte[(int)f.length()];
            f.readFully(buf);
            f.close();
        } catch(IOException e){
            Log.w("readSerializedObject()", "No such file exists -> " + filepath);
            return null;
        }

        Object o=DataStore_System.deserialize(buf);
        return o;
    }
}
