package com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection;
/** This is Host's DataStore.
 * 
 * TODO : Save task_ID before closing the application and on start retrieve the saved task_ID
 */

import android.content.Context;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DataStore_connection
{
    //This contains all the urls and their port numbers to which the selector needs to connect..
    // This will be the original copy that other classes will refer to
    public static Map<String, Integer> urls;

    public static SocketChannel srmw_socketChannel;
    public static Context context;

    public static String TAG="Connection";
    public static Calendar start;
    public static final int totalProcessingThreads=2;
    public static final int BUFFERSIZE=2048;
    public static final String location="Client";

    public static final String authenticateMsg="AKIXP$1111";

    // CONTENT TYPES
    public static final int CUSTOM_JSON_OBJECT=1;
    public static final int ZIPPED_UPDATES=2;

    // DATA FORMAT
    public static final int OVERHEAD_BYTES=5;
    public static final int CONTENT_LENGTH_POSITION=0; // this will occupy first 4 bytes, ie 0-3 bytes
    public static final int CONTENT_TYPE_POSITION=4; // this will be fifth byte,ie, 4 (as counting starts from 0 in array)

    public DataStore_connection(Context serviceContext){
    urls = new HashMap<String, Integer>();
	start=Calendar.getInstance();
    context=serviceContext;
    loadURLsInMap();
    }

    private void loadURLsInMap() {
        urls.put("akhomeserver.servebeer.com", 50001);
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
	System.out.println("Exception while serializing");
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

    public static byte[] getBytesFromInt(int value) {
    return  ByteBuffer.allocate(4).putInt(value).array();
}

    public static int getIntFromBytes(byte[] bytes) {
    return ByteBuffer.wrap(bytes).getInt();
}

} //end of class
