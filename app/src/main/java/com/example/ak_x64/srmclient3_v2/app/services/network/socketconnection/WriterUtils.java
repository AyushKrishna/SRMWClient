package com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.zip.GZIPOutputStream;



public class WriterUtils {

public static byte[] packageData(Object obj)
{
    System.out.println("in WriterUtils.packageData(Object); Got object of -> " + obj.getClass().getName());

	if(obj instanceof byte[]) {
		System.out.println("Setting content type -> 2 for zipped updates");
		byte[] temp=(byte[])obj; 
		byte[] destination=new byte[temp.length+DataStore_connection.OVERHEAD_BYTES];
		setContentType(destination,DataStore_connection.ZIPPED_UPDATES);
		addContentLength(temp.length,destination);
		copyDataBytes(temp,destination);
		return destination;
	}
	
	if(obj instanceof com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.containerClasses.MyMessage){
		System.out.println("Setting content type -> 1 for custom JSONable objects");
		String s=((com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.containerClasses.MyMessage) obj).getCustomJSONForm();
		byte[] temp=compressJSONString(s);
		byte[] destination=new byte[temp.length+DataStore_connection.OVERHEAD_BYTES];
		setContentType(destination,DataStore_connection.CUSTOM_JSON_OBJECT);
		addContentLength(temp.length,destination);
		copyDataBytes(temp,destination);
		return destination;
	}

    if(obj instanceof com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.SRMWMessage){
        System.out.println("Setting content type -> 1 for custom JSONable objects");
        String s=((com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.SRMWMessage) obj).getCustomJSONForm();
        byte[] temp=compressJSONString(s);
        byte[] destination=new byte[temp.length+DataStore_connection.OVERHEAD_BYTES];
        setContentType(destination,DataStore_connection.CUSTOM_JSON_OBJECT);
        addContentLength(temp.length,destination);
        copyDataBytes(temp,destination);
        return destination;
    }
	
	return null;
	}
	
	private static void copyDataBytes(byte[] src, byte[] dst) {
	System.arraycopy(src, 0, dst, DataStore_connection.OVERHEAD_BYTES, src.length);
	}

	private static void setContentType(byte[] destination, int type) {
			destination[DataStore_connection.CONTENT_TYPE_POSITION]=(byte)type; // this means it is zipped customJSON
	}

	private static void addContentLength(int value,byte[] destination) {
		System.out.println("No.of bytes in data -> "+value);
		System.arraycopy(ByteBuffer.allocate(4).putInt(value).array(),0, destination, DataStore_connection.CONTENT_LENGTH_POSITION, 4);
	}
	
	public String convertToCustomJson(Object ob)
	{
		Gson gson = new GsonBuilder().serializeNulls().create();
	    String j=gson.toJson(ob);
	    System.out.println("Object2Json ; json->"+j);
	    String j2="<"+ob.getClass().getName()+">"+j;
	    System.out.println("Object2Json ; json final -> "+j2);
		return j2;
	}

	public static byte[] compressJSONString(String str){
        if (str == null || str.length() == 0) {
            return null;
        }
        
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        try{
        GZIPOutputStream gzip = new GZIPOutputStream(baos);
        gzip.write(str.getBytes());
        gzip.close();
        }catch(IOException e){
        	System.out.println("IOexception in compressing gzip");
        	e.printStackTrace();
        }
        
        return baos.toByteArray();
     }	
}
