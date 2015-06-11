package com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Login extends SRMWMessage {

	private String username; // set by client
	private String password; // set by client
	private String msg; // set by Host indicating successful login or not
	private int newID; //  set by server and is the new id which will be assigned to authenticated clients only
	private UserData userDataObject; // set by Host if successful login

	public Login(String to, String dest, int clientID, int task_ID,String user,String password) {
		super(to, dest, clientID,task_ID);
		username=user;
		this.password = password;
		this.setType("request");
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
		this.setType("responce");
	}

	public int getNewID() {
		return newID;
	}

	public void setNewID(int newID) {
		this.newID = newID;
	}

	public UserData getUserDataObject() {
		return userDataObject;
	}

	public void setUserDataObject(UserData userDataObject) {
		this.userDataObject = userDataObject;
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


