package com.example.ak_x64.srmclient3_v2.srmw.pplsoft.containerClasses;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MyTable implements Serializable{
    
	public List<List<String>> table;
	private int current_row=-1;
	
	// This will be auto set by program.It will be true if the HTML String contains <th> tag,ie, columns will have header description 
	// USAGE : If true, table will set column header "Index" to index column if indexing is asked by user and 
	// start numbering from below it, otherwise the table will directly start numbering rows and 
	// this is also helpful in knowing whether the HTML table already had Column Headings or not  
	public boolean containsColumnHeaderInContent; 
	
	private MyTable()
	{
	table= new ArrayList<List<String>>();
	}
	
	public static MyTable newInstance(String htmlTable)
	{
	MyTable tb=new MyTable();
	
	String temp=null;
	int i=0;

	while(i<htmlTable.length())
	{	
		char c=htmlTable.charAt(i);
		
		if(c=='<')  // this extracts the tag in the braces(<>) 
		{
			temp=new String();
		}
		
		else if(c=='>') // on closing brace,we need to check what tag was it and then perform operations according to it  
		{
			if(temp.equalsIgnoreCase("tr")) // on "tr" tag, create new "Row" object to store its column's text values
			{
				//System.out.println("New row ->"+temp);
				tb.addRow();	
			}
			
			if(temp.equalsIgnoreCase("td")) // every "td" tag is followed by its text value,which is to be stored in "Row" object Arraylist
			{
			String value=htmlTable.substring(i+1, htmlTable.indexOf("<", i));
			tb.put(value);
			i=htmlTable.indexOf("<", i);
			continue;
			}
			
			if(temp.equalsIgnoreCase("th")) // "th" indicates column headings 
			{
			tb.containsColumnHeaderInContent=true;	
			String value=htmlTable.substring(i+1, htmlTable.indexOf("<", i));
			tb.put(value);
			i=htmlTable.indexOf("<", i);
			continue;
			}
			
			if(temp.equalsIgnoreCase("/tr")) // "/tr" indicates end of row
			{
				
			}
		}
		
		else
		{
		temp=temp+c;
		}
		
		i++;
	} // end of while	
	
	return tb;
	}
	
	/** This function adds a MyTable object in the starting(i.e. before top most row) and then adds
	 *  a blank line between header and content
	 * 
	 * @param header
	 * the MyTable object to be added as header for document
	 */
	public void addHeader(MyTable header)
	{
		insertMyTableObject(header, 1);
		insertBlankRow(1+header.getRowCount());
	}
	
	/** This function adds a blank line between content and footer and then a MyTable object at the 
	 * end as footer(i.e. after last row) 
	 *  
	 * @param footer
	 * the MyTable object to be added as footer for document
	 */
	public void addFooter(MyTable footer)
	{
		insertBlankRow(getRowCount()+1);
		insertMyTableObject(footer, getRowCount()+1);
	}
	
	/** This function inserts blank row at the specified row position 
	 * NOTE :- Position starts from index 1(not 0) to MyTable.getRowCount()+1 
	 * 
	 * @param position
	 * The position at which blank row will be inserted in MyTable (NOTE :- Position starts from index 1 to MyTable.getRowCount()+1)
	 */
	public void insertBlankRow(int position)
	{
		table.add(position-1,new ArrayList<String>());
	}
	
	/** This function inserts list at the specified row position 
	 * NOTE :- Position starts from index 1(not 0) to MyTable.getRowCount()+1
	 *		    
	 * @param position
	 * The position at which blank row will be inserted in MyTable (NOTE :- Position starts from index 1 to MyTable.getRowCount()+1)
	 */
	public void insertList(int position,List<String> row)
	{
		table.add(position-1,row);
	}
	
	/** This function inserts a MyTable at the specified row position 
	 * NOTE :- Position starts from index 1(not 0) to MyTable.getRowCount()+1
	 * 		   To add at last of the table use MyTable.getRowCount()+1
	 * 
	 * @param position
	 * The position at which MyTable object will be inserted in this table (NOTE :- Position starts from index 1 to MyTable.getRowCount()+1)
	 */
	public void insertMyTableObject(MyTable tb,int position)
	{
		int i=1;
		while(i<=tb.getRowCount())
		{
		insertList(position, tb.getRow(i));
		position+=1;
		i=i+1;
		}
	}
	
	/** Returns the total number of rows contained in this MyTable object
	 * 
	 * @return
	 * total no.of rows
	 */
	public int getRowCount()
	{
		return table.size();
	}
	
	/** Returns the elements of specified column.
	 *  NOTE :- The index starts from 1 (i.e. first column will have index 1) 
	 * 
	 * @param index - the index of column whose elements are needed (index starts from 1,not 0)
	 * @return - the String array consisting of column elements 
	 */
	public String[] getColumn(int index)
	{
	int temp=getRowCount();	
	String arr[]=new String[temp];
	
	for(int i=0;i<temp;i++)
	{
		arr[i]=table.get(i).get(index-1);
	}
	return arr;
	}
	
	/** Returns the elements of specified row.
	 *  NOTE :- The index starts from 1 (i.e. first column will have index 1) 
	 * 
	 * @param index - the index of column whose elements are needed (index starts from 1,not 0)
	 * @return - the String array consisting of row elements 
	 */
	public List<String> getRow(int index)
	{
		return table.get(index-1);
	}
	
	/** Removes the columns from the table as specified by columnIndex[]
	 *  NOTE :- The index starts from 1 (i.e. first column will have index 1) 
	 * 
	 * @param columnIndexes - contains the index of columns to remove (index starts from 1)
	 */
	public void removeColumns(int[] columnIndexes)
	{
		int[] columns=sortInDescendingOrder(columnIndexes);
		
		for(int j=0;j<getRowCount();j++)
		{
		List<String> x=table.get(j);
		//System.out.println("Got Row Index -"+j+"; "+x);
		int i=0;	
		while(i<columns.length)
		{
		String a=x.remove(columns[i]-1);
		//System.out.println("Object removed -> "+a);
		i+=1;
		}
		} // end of while
		
		//System.out.println("After removing columns ->"+table);
	}
	
	/** Removes the column headers (if present) from the Table 
	 *  @return - true, if column header were removed
	 *  		  false, this means that column headers were already not present 
	 */  
	public boolean removeColumnsHeader()
	{
		boolean t=containsColumnHeaderInContent;
		
		if(containsColumnHeaderInContent==true)
		{
			containsColumnHeaderInContent=false;
			table.remove(0);
		}
		
		return t;
	}
	
	
	/** This function is used by removeColumns(int[]) method to sort the given column indexes
	 *  in descending order.
	 * 
	 * @param arr
	 * contains the indexes of columns to remove
	 * 
	 * @return
	 * the indexes in descending order
	 */
	private int[] sortInDescendingOrder(int[] arr)
	{ 
		for (int i = 0; i < arr.length - 1; i++) 
		{ 
			int index = i; 
			for (int j = i + 1; j < arr.length; j++) 
				if (arr[j] > arr[index]) 
				index = j; 
				int smallerNumber = arr[index]; 
				arr[index] = arr[i]; 
				arr[i] = smallerNumber; 
		} 
		return arr; 
    }
	
	/** This function creates another row to add data
	 * 
	 */
	private void addRow()
	{
		ArrayList<String> row =new ArrayList<String>();
		table.add(row);
		current_row+=1;
	}
	
	/** This function adds index column and index to every row
	 * 
	 * @param startIndex
	 * the starting index value,ie,0 or 1 etc...
	 * 
	 * @param includeIndexHeader - 
	 * On set true this will add first column in the table with column title "Index" and numbering of elements will start from next row
	 * On set false , this will not include column title "Index" 
	 */
	public void addIndexColumn(int startIndex)
	{

		int count=0;
		
		for(List<String> x:table)
		{
			if(count++==0&&containsColumnHeaderInContent==true)
			{
			x.add(0,"Index");
			continue;
			}
			
			x.add(0,String.valueOf(startIndex));
			startIndex+=1;
		}
	}
	
	/** Adds the String to the current row (basically it creates columns)
	 * 
	 * @param x - the element to add in Table
	 */
	private void put(String x)
	{
	table.get(current_row).add(x);
	}
	
	@Override
	public String toString() {

		StringBuffer s=new StringBuffer();
		for(List<String> x:table)
		{
			for(String y:x)
			{
				s.append(y+";");
			}
			s.append('\n');
		}
		
		s=s.deleteCharAt(s.length()-1);
	return s.toString();
	}
	
	/** Returns the Table in form of 2D array
	 * 
	 * @return - 2D array of elements
	 */
	public String[][] to2DArray()
	{
		String[][] arr=new String[getRowCount()][];
		int i=0;
		for(List<String>x :table)
		{
			arr[i]=(String[]) x.toArray(new String[0]);
			i++;
		}
		
		return arr;
	}
	
	}