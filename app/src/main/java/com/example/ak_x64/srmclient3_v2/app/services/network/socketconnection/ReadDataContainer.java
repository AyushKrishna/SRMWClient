package com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ReadDataContainer {

//the socket channel of the server which sent the data
SocketChannel socketChannel;

byte[] data;
int position; // points to the index from where data will be added. Min/starting value=0
int length; // the length of data[]
int overhead_bytes;

// this will be true if the data is splitted otherwise false
// agar content length jitna data read nahi hoga ya content length > buffer size then variable is false  
boolean isDataSplit; 

int buffer_size;

protected ReadDataContainer(SocketChannel socketChannel, int overhead_bytes, int buffer_size){
	this.socketChannel=socketChannel;
	isDataSplit=false;
	position=-1;
	length=-1;
	data=null;
	this.overhead_bytes=overhead_bytes;
	this.buffer_size=buffer_size;
}

private byte[] getByteArray(){
	return data;
}

public void put(byte[] dt,int numRead){
	int contentLength=readContentLength(dt);	
	
	if(isDataSplit==false){
	if(contentLength<(buffer_size-overhead_bytes)&&contentLength==(numRead-overhead_bytes)) // this means the complete request object was read in one go and is present in readBuffer so we can directly pass it to HostProcessor
	{
	Log.d(DataStore_connection.TAG, "Got fresh data in one part");
	byte data[] = new byte[numRead];
	System.arraycopy(dt, 0, data, 0, numRead); 
	new Thread(new Process(socketChannel,data),"Process").start();
	}
	else // this means data is larger than buffer size so we need to store current data in hostLargeData until we get full data
	{
        Log.d(DataStore_connection.TAG,"Got first part of largeData/splitted data from Host");
		isDataSplit=true;
		gotFirstSplitPart(contentLength,dt);	
	} // inner else clause
	} // if clause 
	
	else // here the remaining chunk of largeData(ie,>readbuffer size) is read and then it is stored in hostLargeData map until remaining bytes are also read
	{
        Log.d(DataStore_connection.TAG,"Got remaining part of largeData from Host");
	System.arraycopy(dt, 0, data, position, numRead);
	position+=numRead;
	
	if(isFull()==true) // this means data array is filled with data,ie, we got full data
	{
        Log.d(DataStore_connection.TAG,"Host : Got full data");
		new Thread(new Process(socketChannel,getByteArray()),"Process").start();
		reset();
	}
	} // else clause	
}


private boolean isFull(){
	if(position==length)
	return true;
	else 
	return false;
}

/** This is invoked when the reader thread gets first part of splitted data.ReaderThread passes the 
 * whole readBuffer array containing int content length(first 4 bytes) followed by rest of the data 
 * So in this method, we copy the bytes starting from 4th position of array until the the end of
 * readData and leave content length.
 * 
 * @param contentLength 
 * the content length of data to be received
 * 
 * @param readData
 * readBuffer array 
 */
private void gotFirstSplitPart(int contentLength,byte[] readData){
data=new byte[contentLength];
length=contentLength+overhead_bytes;
System.arraycopy(readData, 0, data, 0, readData.length); 
position=readData.length;
}

private void reset(){
	data=null;
	length=-1;
	position=0;
}

private int readContentLength(byte[] array) {
	byte[] intdata=new byte[4];
	System.arraycopy(array, 0, intdata, 0, 4); 
	int val=ByteBuffer.wrap(intdata).getInt();
    Log.d(DataStore_connection.TAG,"Host : contentLength -> "+val);
	return val;
}
}
