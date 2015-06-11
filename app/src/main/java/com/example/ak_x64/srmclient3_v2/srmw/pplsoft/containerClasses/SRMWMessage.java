package com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses;

import java.io.Serializable;

public abstract class SRMWMessage implements Serializable{

	public String to; // where the creator of this msg wants this message to go through
	public String destination; // where the creator of the msg wants this msg to finally reach

	private int clientID; // ID is set only by clients so that their requests can be efficiently managed by server
	private int hostID; // this is set by host itself while sending object back to server
	private int task_ID; // task_ID is set only by clients so that their requests can be differentiated by server
	private String type; // type can be "request" or "responce"

	public SRMWMessage(String to,String dest,int clientID,int task_ID){
		this.to=to;
		destination=dest;
		this.clientID=clientID;
		this.task_ID=task_ID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type.toLowerCase();
	}

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

	public int getTaskId(){
		return  task_ID;
	}


	 /**This method will be implemented by all the classes that will be sent over internet after converting to
	 * custom JSON format. The implementing classes will also provide the steps to decode/encode the
	 * object to custom JSON and vice versa.Thus with every class we can implement a different method
	 * to encode/decode as per need.
	 */
	public abstract String getCustomJSONForm();



	/** Ye method jis object pe call hota hai, uske aur parameter ke "Object ob" me equality check karta hai.
	 *  Equality k lie ye sirf Client_ID and task_ID hi check karega.
	 *
	 *  USAGE :-
	 *  Ye method Server me use hota hai(see HostManager.removeRequestFromBackup())... Server har client request
	 *  ko store karta hai before sending it to host.Jab request ka output server se milta hai toh us request ko
	 *  backup se hataya jata hai kyunki request successfully process ho chuki hogi. So ab request ko backup me search
	 *  karne ke lie is method ka use kiya jata hai...
	 *  	Har request me uska unique clientID and task_ID hote hai jo ki processing se pehle aur uske baad bhi
	 *  same rehte hai.So unki hi values se hum log responce ki request msg ko find karte hai..
	 *
	 * @param ob
	 * the object's task_ID and client_ID to be compared
	 *
	 * @return
	 * true , if task_ID and Client_ID are same otherwise false
	 */
	public boolean isTaskSame(Object ob){

		SRMWMessage msg=null;
		try{
			msg=(SRMWMessage) ob;
		}catch(ClassCastException e){
			return false;
		}

		if(msg.getClientID()==clientID && msg.getTaskId()==task_ID)
			return true;
		else
			return false;

	}

}
