/**  This class is sent first to Server to show that it is a authenticated Host Program. 
 * 
 */

package com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.containerClasses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Information extends MyMessage {

	private String msg;
	private String current_version;
	
	public Information(String fr, String to, String dest,int id,String msg,String version) {
		super(fr, to, dest);
		this.msg=msg;
		current_version=version;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public String getCurrentVersion() {
		return current_version;
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

