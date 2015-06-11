package com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.containerClasses;

import java.io.Serializable;

public abstract class MyMessage  implements Serializable {
	
	public String to; // where the creator of this msg wants this message to go to 
	public String destination; // where the creator of the msg wants this msg to return to
	public String from; // this will tell from where this object was created
	private int clientID; // set by clients so that their requests can be efficiently managed by server
	private int hostID; // set by Hosts if required
	
	public MyMessage(String to,String dest,String from){
		this.from=from;
		this.to=to;
		destination=dest;
	}

	/**This method will be implemented by all the classes that will be sent over internet after converting to
	 * custom JSON format. The implementing classes will also provide the steps to decode/encode the
	 * object to custom JSON and vice versa.Thus with every class we can implement a different method
	 * to encode/decode as per need.
	 */
	public abstract String getCustomJSONForm();


	public int getClientID(){
		return clientID;
	}
	
	public int getHostID(){
		return hostID;
	}
	
	public void setClientID(int id){
		clientID=id;
	}

	public void setHostID(int id){
		hostID=id;
	}
}