package com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection;
/** This class processes the incoming requests and then sends it to the WriterThread.
 */

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.containerClasses.Information;
import com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.containerClasses.MyMessage;
import com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.containerClasses.StrMessage;
import com.example.ak_x64.srmclient3_v2.app.system.DataStore_System;
import com.example.ak_x64.srmclient3_v2.app.ui.MainHandler;
import com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.Attendance;
import com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.Login;
import com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.SRMWMessage;
import com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses.UserData;

import java.nio.channels.SocketChannel;

public class Process implements Runnable {

    // the socket channel of the server which sent the data
    SocketChannel sc;

    // tempData contains the contentType byte followed by the original data
    byte[] tempData;

    Process(SocketChannel sc, byte[] tempData){
        this.sc=sc;
        this.tempData=tempData;
    }

    public void run(){
    System.out.println("Process initialized");

    int contentType=tempData[DataStore_connection.CONTENT_TYPE_POSITION];

    byte[] data=new byte[tempData.length- DataStore_connection.OVERHEAD_BYTES]; // this will contain only zipped data(excluding content length and content type)
    System.arraycopy(tempData, DataStore_connection.OVERHEAD_BYTES, data, 0, data.length);

    process(data,contentType);
    } // end of run()

    public void process(byte[] data,int contentType){
    System.out.println("Got content type -> "+contentType);
    if(contentType==1)
    {
        String customJson=null;
        try {
            customJson= ProcessUtils.decompressGzipToString(data);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        Object obj= ProcessUtils.customJsonToObject(customJson);
        processObject(obj);
    }

    if(contentType==2)
    {
        System.out.println("GOT UPDATE BYTES.... NO ACTION DEFINED");
    }
}

    public void processObject(Object obj) {
    Log.d(DataStore_connection.TAG, "Process.process; Process started");

    if (obj instanceof MyMessage) {
        MyMessage o = (MyMessage) obj;

        if (o instanceof Information) {
            gotInformation(o);
        } // Authenticator clause

        if (o instanceof StrMessage) {
            gotStrMessage(o);
        } // StrMessage clause

        if (o.to != null && o.to.equalsIgnoreCase(DataStore_connection.location))
            o.to = null;

        if (!o.destination.equalsIgnoreCase(DataStore_connection.location))
            sendDecision(o, o.to, o.destination);
    } // clause of MyMessage

    if (obj instanceof SRMWMessage) {
        SRMWMessage o = (SRMWMessage) obj;

        if (o instanceof Login) {
            gotLogin(o);
        }

        if (o instanceof UserData) {
            gotUserData(o);
        }

        if (o instanceof Attendance) {
            gotAttendance(o);
        }

        if (o.to != null && o.to.equalsIgnoreCase(DataStore_connection.location))
            o.to = null;

        if (!o.destination.equalsIgnoreCase(DataStore_connection.location))
            sendDecision(o, o.to, o.destination);
    } // clause of SRMWMessage
    }

    private void gotInformation(MyMessage o) {
        Log.d(DataStore_connection.TAG, "Process.process; Got Information object");
        notifyHandler(DataStore_System.mainHandler,o, MainHandler.GOT_INFORMATION);
    }

    private void gotAttendance(SRMWMessage o) {
        Log.d(DataStore_connection.TAG, "Process.process; Got Attendance object");
        notifyHandler(DataStore_System.mainHandler,o, MainHandler.GOT_ATTENDANCE);
    }

    private void gotUserData(SRMWMessage o) {
        Log.d(DataStore_connection.TAG, "Process.process; Got UserData object");
        notifyHandler(DataStore_System.mainHandler,o, MainHandler.GOT_USERDATA);
    }

    private void gotLogin(SRMWMessage o) {
        Log.d(DataStore_connection.TAG, "Process.process; Got Login object with ID -> " + o.getClientID());
        notifyHandler(DataStore_System.mainHandler,o, MainHandler.GOT_LOGIN);
    }

    private void notifyHandler(Handler handler,Object o,int msgtype)
    {
        Message msg=handler.obtainMessage(msgtype);
        msg.obj=o;
        handler.sendMessage(msg);
    }

    private void gotStrMessage(MyMessage obj) {
	StrMessage ob=(StrMessage)obj;
	System.out.println("StrMessage object msg -> "+ob.getMsg());
	
	if(ob.destination.equals(DataStore_connection.location))
	{
		System.out.println("Msg for Client -> "+ob.getMsg());
		
		if(ob.getMsg().equalsIgnoreCase("sleep"))
		{
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(DataStore_connection.location+" awake ");
		}

        if(ob.getMsg().equalsIgnoreCase("TEMP_ID")){
            if(DataStore_System.ID!=ob.getClientID()) {
                Log.i(DataStore_connection.TAG,"Process.process; Updating ID to ->"+ob.getClientID());
                DataStore_System.ID = ob.getClientID();
            }
        }
	}
}

    /** This method decides whether to send the received object to Server or not.
     *
      * @param obj
     * @param to
     * @param destination
     */
    private void sendDecision (Object obj,String to,String destination){
    NetConnection.writeToSocketChannel(sc, obj);
    }

} // end of class
