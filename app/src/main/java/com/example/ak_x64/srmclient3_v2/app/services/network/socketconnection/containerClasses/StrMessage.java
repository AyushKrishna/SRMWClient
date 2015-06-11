package com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.containerClasses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StrMessage extends MyMessage{
	
	private String msg;
	private int task_ID;
	
public StrMessage(String from,String to,String m,String dest)
{
	super(from,to,dest);
	msg=m;	
}

    public String getMsg() {
        return msg;
    }

    public int getTaskID() {
        return task_ID;
    }

    public void setTaskID(int task_ID) {
        this.task_ID = task_ID;
    }

    @Override
public String getCustomJSONForm() {
	Gson gson = new GsonBuilder().serializeNulls().create();
    String j=gson.toJson(this);
    //System.out.println("Object2Json ; json->"+j);
    String j2="<"+this.getClass().getName()+">"+j;
    //System.out.println("Object2Json ; json final -> "+j2);
	return j2;
}

}
