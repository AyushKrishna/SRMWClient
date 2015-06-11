package com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection;

import android.util.Log;

import com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.containerClasses.Information;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ProcessUtils {

    public static Object customJsonToObject(String json)
    {
        //TODO : Load classes using DexLoader rather than manually
        //ClassLoader cd=ClassLoader.getSystemClassLoader();
        Gson gson = new GsonBuilder().serializeNulls().create();

        String classname=json.substring(json.indexOf('<')+1, json.indexOf('>'));
        Log.d(DataStore_connection.TAG, "json2Object -> Received Msg of class :" + classname);

        String got = json.substring(json.indexOf('>') + 1);
        Log.d(DataStore_connection.TAG, "Got JSON -> " + got);
        Object object=null;

        if(classname.contains("connection.containerClasses.Information")) {
            object = gson.fromJson(got, Information.class);
        }
        if(classname.contains("connection.containerClasses.StrMessage")) {
            object = gson.fromJson(got, com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.containerClasses.StrMessage.class);
        }
        if(classname.contains("srmw.pplsoft.containerClasses.Attendance")) {
            object = gson.fromJson(got, com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.Attendance.class);
        }
        if(classname.contains("srmw.pplsoft.containerClasses.Login")) {
            object = gson.fromJson(got, com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.Login.class);
        }
        if(classname.contains("srmw.pplsoft.containerClasses.UserData")) {
            object = gson.fromJson(got, com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.UserData.class);
        }

        return object;
    }


	public static String decompressGzipToString(byte[] bytes) throws Exception {
	    
	    GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(bytes));
	    BufferedReader bf = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
	    StringBuffer out =new StringBuffer();
	    String line;
	    while ((line=bf.readLine())!=null) {
	      out.append(line);
	    }
	    System.out.println("Decompressed String : " + out.toString());
	    return out.toString();
	 }

	public static void unZip(byte[] zipBytes, String outputFolder){
		 
	    byte[] buffer = new byte[1024];
	    try{
	   	//create output directory is not exists
	   	File folder = new File(outputFolder);
	   	if(!folder.exists()){
	   		folder.mkdir();
	   	}

	   	ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(zipBytes));
	   	//get the zipped file list entry
	   	ZipEntry ze = zis.getNextEntry();

	   	while(ze!=null){

	   	   String fileName = ze.getName();
	          File newFile = new File(outputFolder + File.separator + fileName);

	          System.out.println("file unzip : "+ newFile.getAbsoluteFile());

	           //create all non exists folders
	           //else you will hit FileNotFoundException for compressed folder
	           new File(newFile.getParent()).mkdirs();

	           FileOutputStream fos = new FileOutputStream(newFile);             

	           int len;
	           while ((len = zis.read(buffer)) > 0) {
	      		fos.write(buffer, 0, len);
	           }

	           fos.close();   
	           ze = zis.getNextEntry();
	   	}

	       zis.closeEntry();
	   	zis.close();

	   	System.out.println("Unzipped all files");

	   }catch(IOException ex){
		   System.out.println("Error unzipping bytes");
	      ex.printStackTrace(); 
	   }
	  }   
	
	
}
