/** This class contains the tables that will be displayed. MyTable header and footer is also added in
 * case there is some extra information.
 * 
 */

package com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses;

import java.io.Serializable;

public class MyDOM implements Serializable{

public String[][] content;
public String[][] header;
public String[][] footer;
public String type; // this indicates DOM of which class is contained in this object,ie,Attendance/Marks/Att info

public MyDOM(String type,String[][] head,String[][] content,String[][] foot)
{
	header=head;
	footer=foot;
	this.content=content;
	this.type=type;
}
}

	

 