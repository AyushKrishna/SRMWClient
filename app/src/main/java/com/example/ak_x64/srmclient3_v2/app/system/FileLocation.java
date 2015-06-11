package com.example.ak_x64.srmclient3_v2.app.system;

import android.os.Environment;

import java.io.File;

/**
 * Created by AK on 15-04-2015.
 */
public enum FileLocation {

    //Internal Folders
    rootInternalDir(null,DataStore_System.applicationContext.getFilesDir().toString(),0),
        userDataDir(rootInternalDir,"/UserData",0),

    //InternalDir/UserData files
    userIDFile(userDataDir,"/userID.data",1),
    pplsoftIDFile(userDataDir,"/pplsoftID.data",1),
    pplsoftPasswordFile(userDataDir,"/pplsoftPass.data",1),
    attSubjectList(userDataDir,"/attSubjectList.data",1),

    //External Folders
    SDCard(null,Environment.getExternalStorageDirectory().getAbsolutePath(),0),
    rootExternalDir(SDCard,"/SRMapp",0),

    //SDCARD/SRMapp files
    attendanceFile(rootExternalDir,"/Att.attendance",1);


    private String path;

    FileLocation(FileLocation fl,String p,int type){
        if(fl==null)
            path=p;
        else
            path=fl.path+p;

        if(type==0) // 0 means that the path is of folder(not file) so create folder if path does not exist
            createFolder(path);
    }

    private void createFolder(String path2) {
        File f=new File(path2);
        if(!f.exists())
            f.mkdirs();
    }

    public String getPath(){
        return path;
    }
}
