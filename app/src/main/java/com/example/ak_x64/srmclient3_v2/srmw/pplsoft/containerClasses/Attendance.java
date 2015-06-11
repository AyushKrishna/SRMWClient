package com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Attendance extends SRMWMessage {

	private String[][][] attTables; // this will be set by host.... attTables[0]->attendance table while rest will be att info tables
    //public String frndID; // deprecated variable
    private String[] attinfolist; // this will set by client.... It contains the list of subjects whose tables are required
    private String username; // set by client
	private String password; // set by client
	private String msg; // this will be used to send any error(if occurs) from Host to Client
    private String date; // this will be set by the server because SRMCEM computers doesn't have correct date set so it has to be done by server instead

	public Attendance(String to, String dest, int clientID,int task_ID,String username,String password,String[] attinfolist) {
		super(to, dest,clientID,task_ID);
		this.attinfolist=attinfolist;
		this.username=username;
		this.password=password;
		this.setType("request");
	}

	public String[][][] getAttTables() {
		return attTables;
	}

	public void setAttTables(String[][][] attTables) {
		this.attTables = attTables;
		this.setType("responce");
	}

	public String[] getAttinfolist() {
		return attinfolist;
	}

	public void setAttinfolist(String[] attinfolist) {
		this.attinfolist = attinfolist;
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
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
