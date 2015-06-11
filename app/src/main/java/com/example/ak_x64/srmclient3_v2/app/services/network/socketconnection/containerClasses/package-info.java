/** This package contains the classes used by the server,host and clients to send application
 * specific data among each other such as information, updates etc. This DOES NOT CONTAIN
 * ANY client related task or classes. 
 * <p>
 * The description of classes are as follows :
 * 
 * MyMessage :- 
 * The super class of all classes in the package.It contains the general data shared by rest of the 
 * classes.
 *   
 * Information :- 
 * It sends additional info about the host or client.
 * 
 * StrMessage :- 
 * This class is basically used for testing the host,client and server connection
 * by sending it from one end point to another and echoing it back.
 * 
 * Updates :-
 * Host : A host program uses this class to get updated files from server
 * Client : Client uses it to notify user whether a new version is available and any additional info 
 * 
*/

/**
*@author AK
*@since 1.0
*@version 1.0
*
*/

package com.example.ak_x64.srmclient3_v2.app.services.network.socketconnection.containerClasses;

