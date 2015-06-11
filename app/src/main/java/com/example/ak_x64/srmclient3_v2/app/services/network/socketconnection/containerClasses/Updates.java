package com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.containerClasses;

// TODO : Find JSON alternative of this object

/** OLD CODE
public class Updates implements Serializable {

	public ArrayList<String> fileList; // it will contains the file list contained by host and later on updated file names from server
	public Map<String,byte[]> updates; // it will contain the relative file path(including filename) and corresponding the file bytes
	public String loadClassName;
	public String loadMethodName;
	
	public Updates(ArrayList<String> fileList,Map<String,byte[]> updates,String loadClass,String loadMethod) {
		this.updates=updates;
		this.fileList=fileList;
		loadClassName=loadClass;
		loadMethodName=loadMethod;
	}
}
*/