package com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection;
/** This class provides efficient way for connecting to many servers by use of NIO Selector  
 * 
 */

import android.util.Log;

import com.example.ak_x64.srmclient3_v2.app.system.DataStore_System;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class NetConnection implements Runnable {

    // serverChannelData will contain the data that is to be written to the server
    public static Map<SocketChannel,LinkedBlockingQueue<Object>> serverChannelData= new HashMap<SocketChannel,LinkedBlockingQueue<Object>>();

    // serverSplitDataStore will temporarily contain split data during read operation until every byte is not received
    private Map<SocketChannel,ReadDataContainer> serverSplitData= new HashMap<SocketChannel,ReadDataContainer>();

    //contains the server urls to which it has connected successfully
    public Map<String,Integer> connectedUrls=new HashMap<String,Integer>();

    // pointer variable to check whether all connections has been made
    private boolean connectionsAvailable=true;

    // maps URL of connection to its socket channel.This is useful in finding socket channel acc to URL
    public Map<String,SocketChannel> socketChannelMap;

	public static Selector selector;

    private ByteBuffer readBuffer = ByteBuffer.allocate(DataStore_connection.BUFFERSIZE);

    private final ExecutorService pool;
    private static boolean shutdownSelector=false;

	public NetConnection() {
		try {
			selector = SelectorProvider.provider().openSelector();
		} catch (IOException e) {
            Log.e(DataStore_connection.TAG,"IOException in opening selector");
            e.printStackTrace();
		}

        socketChannelMap=new HashMap<String,SocketChannel>();
        pool = Executors.newCachedThreadPool();
    }
	
	// this is the thread that handles selector opertions
	public void run() {  
		
		System.out.println("Starting Selector");
		
		while (shutdownSelector==false) {
			
				if(connectionsAvailable==true)
				connectTo();

				setOP_WRITEtoChannels();

				try {
					selector.select(); // Wait for an event one of the registered channels
				} catch (IOException e) {
                    Log.e(DataStore_connection.TAG,"IOException in selector");
					e.printStackTrace();
				} 

				Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
				while (selectedKeys.hasNext()) {
					SelectionKey key = selectedKeys.next();
					selectedKeys.remove();

					if (!key.isValid()) {
						continue;
					}
					
					if (key.isConnectable()) {
                        Log.d(DataStore_connection.TAG, "Key is connectable");
						boolean connected=this.finishConnection(key);
						if(connected==true)
						{
                            Socket sc=((SocketChannel) key.channel()).socket();
                            connectedUrls.put(sc.getInetAddress().getHostName(), sc.getPort());
                            createServerDataStorage((SocketChannel)key.channel());
                            authenticateClient((SocketChannel) key.channel());
                            setOP_READtoChannel(key);
						}
					} 
					
					else if (key.isReadable()) {
                        Log.d(DataStore_connection.TAG, "Key is readable");
						this.read(key);	
					} 
					
					else if (key.isWritable()) {
                        Log.d(DataStore_connection.TAG, "Key is writable");
						try {
							this.write(key);
						} catch (ClosedChannelException e1) {
							// TODO If all the socket channels in the Map has been closed then shutdown the selector
							e1.printStackTrace();
						}
						try {
                            Log.d(DataStore_connection.TAG,"Setting key to readable");
							key.channel().register(selector, SelectionKey.OP_READ);
						} catch (ClosedChannelException e) {
                            // TODO If all the socket channels in the Map has been closed then shutdown the selector
							e.printStackTrace();
						}
					}
				}
		}

        performCleanUp();
        Log.i(DataStore_connection.TAG, "NetConnection thread over");
	}

    private void performCleanUp() {
        Log.i(DataStore_connection.TAG,"Closing selector and ThreadPoolExecutor");
        pool.shutdown();

        try {
            selector.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setOP_READtoChannel(SelectionKey key) {
        try {
            key.channel().register(selector, SelectionKey.OP_READ);
        } catch (ClosedChannelException e) {
            e.printStackTrace();
        }
    }

    private void createServerDataStorage(SocketChannel socketChannel) {
        Log.i(DataStore_connection.TAG,"socket address -> "+socketChannel.socket().getInetAddress().getHostAddress());
        socketChannelMap.put(socketChannel.socket().getInetAddress().getHostAddress(), socketChannel);
        serverChannelData.put(socketChannel, new LinkedBlockingQueue<Object>());
        serverSplitData.put(socketChannel, new ReadDataContainer(socketChannel, DataStore_connection.OVERHEAD_BYTES, DataStore_connection.BUFFERSIZE));

        //TODO:Change the way to initialize srmw_socketChannel
        if(DataStore_connection.srmw_socketChannel==null)
            DataStore_connection.srmw_socketChannel=socketChannel;
	}

	private void setOP_WRITEtoChannels() {
		// Process any pending changes
		
		Iterator<Entry<SocketChannel, LinkedBlockingQueue<Object>>> it = serverChannelData.entrySet().iterator();
	    while (it.hasNext()) {
	        Entry pair = (Entry)it.next();
	    	  try {
	    		 LinkedBlockingQueue<Object> x=(LinkedBlockingQueue<Object>)pair.getValue();
	    		 if(x.size()>0) 
	    		 {
	    		 //System.out.println("Host : Setting OP_WRITE to host channel as size ->"+x.size());  
	    		 ((SocketChannel)pair.getKey()).register(selector, SelectionKey.OP_WRITE);
	    		 }
	    	  	} catch (ClosedChannelException e) {
				System.out.println("Host : ClosedChannelException registering key with OP_WRITE");
				e.printStackTrace();
			}
	      }
	}

    private void authenticateClient(SocketChannel sc) {
        String msg= DataStore_connection.authenticateMsg+";Integer ID="+ DataStore_System.ID;
        writeStringMsg(sc,msg);
    }

	private void connectTo() {
		SocketChannel socketChannel;
		try
		{
		socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
		}catch(IOException e)
		{
			System.out.println("in connectTo() ;IOEXception whle opening socketchannel");
			e.printStackTrace();
			return;
		}

		Iterator<Entry<String ,Integer>> it = DataStore_connection.urls.entrySet().iterator();
	    	if(it.hasNext()) {
	        Entry pair = (Entry)it.next();
	        try
	        {	
	        socketChannel.connect(new InetSocketAddress((String)pair.getKey(),(Integer)pair.getValue()));
	        System.out.println("Connecting with server -> "+pair.getKey()+":"+pair.getValue());
	        socketChannel.register(selector, SelectionKey.OP_CONNECT);
            it.remove();
	        }catch(IOException e)
	        {
	        System.out.println("IOexception trying to connect to -> "+pair.getKey()+":"+pair.getValue());
	        e.printStackTrace();
	        }
	        
	        if(!it.hasNext())
	        {	
	        this.connectionsAvailable=false;
	        Log.d(DataStore_connection.TAG,"No more connections to be made");
	        }
	      }	
	}

	private void read(SelectionKey key)  {
		SocketChannel socketChannel = (SocketChannel) key.channel();
		
		int numRead;
		try {
			numRead = socketChannel.read(this.readBuffer);
			System.out.println("Host : Bytes read -> "+numRead);
		} catch (IOException e) {
			key.cancel();
			try {
				socketChannel.close();
			} catch (IOException e1) {
				System.out.println("IOException while closing channel");
				e1.printStackTrace();
			}
			return;
		}

		if (numRead == -1) {
			try {
				key.channel().close();
			} catch (IOException e) {
			    System.out.println("IOException while closing channel");
				e.printStackTrace();
			}
			key.cancel();
			return;
		}

        ReadDataContainer dataContainer=serverSplitData.get(socketChannel);
        dataContainer.put(readBuffer.array(), numRead);
		
		this.readBuffer.clear();
	}
	
	private void write(SelectionKey key) throws ClosedChannelException 
	{
		SocketChannel socketChannel = (SocketChannel) key.channel();
		LinkedBlockingQueue<Object> queue = serverChannelData.get(socketChannel);
		
			while (queue.peek()!=null) {
				Object ob=null;
					
				try {
					ob = queue.take();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				 if(ob!=null)
				 {
				 
				 byte[] destination= WriterUtils.packageData(ob);
				 
				 try {
					socketChannel.write(ByteBuffer.wrap(destination));
				} catch (IOException e) {
					System.out.println("IOException while writing to server");
					e.printStackTrace();
				}
				}
			}		
	}

	private boolean finishConnection(SelectionKey key) {
		SocketChannel socketChannel = (SocketChannel) key.channel();
	
		try {
			socketChannel.finishConnect();
		} catch (IOException e) {
			// Cancel the channel's registration with our selector
			e.printStackTrace();
			key.cancel();
			return false;
		}
		
		return true;
	}


    /** This function adds the object to the serverChannelData's linked queue and wakes up the selector.
     *
     * @param sc
     * the socket channel of the server to which data is to be sent
     * @param obj
     * the object to send
     */
    public static void writeToSocketChannel(SocketChannel sc,Object obj)
    {
        try {
            LinkedBlockingQueue<Object> lb=serverChannelData.get(sc);
            lb.put(obj);
            Log.d(DataStore_connection.TAG,"in writeToServer(); Data added to LinkedBlockingQueue");
            //logger.debug("in HostDataStore.write(); NO.of objects in queue -> "+lb.size());
        } catch (InterruptedException e){
            e.printStackTrace();
        }

        selector.wakeup();
    }

    /** This method is used to write String msg to the socket channel directly,ie,it does not involve
     *  any selector operation.
     *
     *  USAGE -
     *  it is used only to send authentication string just after establishing connection
     *
     * @param sc
     * the socket channel of the server to which String is to be sent
     * @param msg
     * the String msg to send
     */
    public static void writeStringMsg(SocketChannel sc,String msg){
        try {
            sc.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void shutdownNetConnection()
    {
        shutdownSelector=true;
        selector.wakeup();
    }
}
