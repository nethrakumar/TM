/*
 * Author: Nethra Kumar
 * Project: Sprint 1
 * Class: CSc 131
 * Date: 2/1/2018
 */

import java.util.*;

import java.io.*;
import java.text.*;
//import java.time.Duration; 

/************************************************************/
/*This class is used in the main Public Class TM to write the information pulled in TM to the file "TaskManager.txt*/
class Log {    

   private String name;
   
   public Log(String name) {
      this.name = name;
   }
   
   
   /*Writes the Task Name and Start Time to the file; taken from TM's Start Method*/
   public void WriteToFileTime(Date time) { 
      try {
         PrintWriter print_line = new PrintWriter(new FileOutputStream(new File("TaskManager.txt"),true));
         //print_line.println(name);    //Task Name
         DateFormat formatter = DateFormat.getTimeInstance();
         print_line.println(name + " (time): " + formatter.format(time));   //Start Time
         print_line.close();
      } catch(FileNotFoundException e) {
         e.printStackTrace();
      }
      return;
   }
   
   /*Writes Task Description to file under the Task Name and Start Time*/
   public void WriteToFileDescr(String descr) {  //
      try {
         PrintWriter print_line = new PrintWriter(new FileOutputStream(new File("TaskManager.txt"),true));
         //print_line.println(name);
         print_line.println(name + " (description): "  + descr);  //Description
         print_line.close();
      } catch(FileNotFoundException e) {
         e.printStackTrace();
      }
      return;
   }
   
   /*Writes Task Size to file under the Task Name*/
   public void WriteToFileSize(String size) {  //
      try {
         PrintWriter print_line = new PrintWriter(new FileOutputStream(new File("TaskManager.txt"),true));
         //print_line.println(name);
         print_line.println(name + " (size): "  + size);  //Size
         print_line.close();
      } catch(FileNotFoundException e) {
         e.printStackTrace();
      }
      return;
   }
   
   
   public void TotalTime(String name) throws NullPointerException{
	   
	   //String parse_time = null;
	   String name_parse = name + " (time)";
	   String fileName = "TaskManager.txt";
	   String line = null;
	   int numberOfTimes = 0;
	   try {
		   FileReader fr1 = new FileReader(fileName);
		   BufferedReader br1 = new BufferedReader(fr1);
		   while((line = br1.readLine()) != null) {
			   String delim = ": ";
			   String[] tokens = line.split(delim);
			   if(tokens[0].equals(name_parse)) {
				   numberOfTimes = numberOfTimes + 1;
			   }
		   }
		   br1.close();
		   
	   } catch(FileNotFoundException ex) {
		   //System.out.println("Unable to open file '" + fileName + "'");
	   } catch(IOException ex) {
		   //System.out.println("Error reading file '" + fileName + "'");
	   }
	   Date[] Times = new Date[numberOfTimes];
	   for (int i = 0; i < numberOfTimes; i++) {
		   try {
			   
			   int counter = 0;
			   FileReader fr = new FileReader(fileName);
			   BufferedReader br = new BufferedReader(fr);
			   
			   while((line = br.readLine()) != null) {
				   String delim = ": ";
				   String[] tokens = line.split(delim);
				   if(tokens[0].equals(name_parse)) {
					   DateFormat df = new SimpleDateFormat("hh:mm:ss aa");
					   SimpleDateFormat outputformat = new SimpleDateFormat("HH:mm:ss");
					   Date time = null;
					   String output = null;
				   	   try {
				   			time = df.parse(tokens[1]);
				   			output = outputformat.format(time);
				   	   }catch(ParseException pe) {
				   			pe.printStackTrace();
				   	   }
					   Times[counter] = time;
					   counter++;	
				   }
			   }
			   br.close();
		   }
		   catch(FileNotFoundException ex) {
			   //System.out.println("Unable to open file '" + fileName + "'");
		   }
		   catch(IOException ex) {
			   //System.out.println("Error reading file '" + fileName + "'");
		   }
	   }
	   long elapsed = 0;
	   for (int i = 0; i < numberOfTimes; i++) {
		   if((i % 2) == 0) {
			   elapsed = elapsed + (Times[i + 1].getTime() - Times[i].getTime());
		   }
		   
	   }
	   long seconds = elapsed / 1000 % 60;
	   long minutes = elapsed / (60 * 1000) % 60;
	   long hours = elapsed / (60 * 60 * 100) % 24;
	   System.out.println("Total Time: " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds");
   }
   
   
   
   
   /*Reads individual tasks with the Task Name, Total Time, and Description*/
   public void ReadLog(String name) throws FileNotFoundException {   
	   String st;
	   try { 
		   BufferedReader br = new BufferedReader(new FileReader("TaskManager.txt"));
		   String TotalDescription = "";
		   String Size = "";
		   while ((st = br.readLine()) != null) {
			   if(st.startsWith(name + " (description)")) {
				   String delim = ": ";
				   String[] tokens = st.split(delim);
				   TotalDescription = TotalDescription + tokens[1];
			   }
			   if(st.startsWith(name + " (size)")) {
				   String delim = ": ";
				   String[] tokens = st.split(delim);
				   Size = tokens[1];
			   }
	   	   } 
		   System.out.println("Size: " + Size);
		   System.out.println("Description: " + TotalDescription);
		   TotalTime(name);
   		   br.close(); 
	   } catch(IOException e) {
           e.printStackTrace();
	   }
   }
}
/************************************************************/

/* Main public class TM used to take in the commands and data to write into the file using class Log*/ 
public class TM {

   public static void main(String[] args) throws FileNotFoundException {
      TM tm = new TM();
      tm.appMain(args);

   }
   public void appMain(String[] args) throws FileNotFoundException {
	  String name = "";
	  if(args.length != 1) {       //Creates a ArrayOutOfBounds if you don't do this because Summary() can't use args[1], but Summary(name) does.
		  name = args[1];          //Therefore name = args[1] only if you're using Summary(name), Start/Stop(name), and Describe(name, description)
  	  }
	  //name = args[1];
	  String cmd = args[0];
      String desc = ""; //args[2];
      String size = ""; //args[2];
     
      for(int i = 2; i < args.length; i++) {
         desc = desc + args[i] + " ";
         size = size + args[i] + " ";
      }
      
      
      switch(cmd) {               //To switch between the Start, Stop, Describe, and Summary commands
         case "Start": Start(name);
            break;
         case "Stop": Stop(name);
            break;
         case "Describe": Describe(name, desc);
            break;
         case "Size": Size(name, size);
      		break;
         case "Summary": {
        	 if(args.length == 1) {
        		 Summary();          //For a summary of all tasks in Task Manager
        	 }else {
        		 Summary(name);      //For a summary of an individual task in TaskManager
        	 }
         }  break;
      }
   }
   
   /*Gets the Task Name and Start Time*/
   public void Start(String name) {
	  int counts = numberOfTimes(name);
	  if(counts % 2 == 1) {
		  System.out.println("Please Stop before Starting again");
	  }else {
	      String taskName = name;
	      System.out.println(taskName);
	      Date start = new Date();
	      DateFormat formatter = DateFormat.getTimeInstance();
	      System.out.println(formatter.format(start));
	      Log entry = new Log(name);
	      entry.WriteToFileTime(start);
	      return;
	  }
   }
   
   /*Gets the Task Name and Stop Time*/
   public void Stop(String name) {
	  int counts = numberOfTimes(name);
	  if(counts % 2 == 0) {
		  System.out.println("Please Start before Stopping");
	  }else {
	      String taskName = name;
	      System.out.println(taskName);
	      Date stop = new Date();
	      DateFormat formatter = DateFormat.getTimeInstance();
	      System.out.println(formatter.format(stop));
	      Log entry = new Log(name);
	      entry.WriteToFileTime(stop);
	      return;
	  }
   }
   
   /*Gets the Task Name and Size*/
   public void Size(String name, String size) {
	   String taskName = name;
	   System.out.println(taskName);
	   System.out.println(size);
	   Log entry = new Log(name);
	   entry.WriteToFileSize(size);
	   return;
   }
   
   /*Gets the Task Name and Description*/
   public void Describe(String name, String desc) {
      String taskName = name;
      System.out.println(taskName);
      System.out.println(desc);
      Log entry = new Log(name);
      entry.WriteToFileDescr(desc);
      return;
   }
   
   /*Gets the summary of an individual task*/
   public void Summary(String name) {
	  System.out.println("Entry: " + name);
	  int counts = numberOfTimes(name);
		  if(counts % 2 == 1) {
			  System.out.println("Please Stop the task " + name + " before requesting a Summary");
      }else {
	      //Read single entry from File
	      Log entry = new Log(name);
	      try {
	    	  entry.ReadLog(name);
	      } catch(FileNotFoundException e) {
	          e.printStackTrace();
	      }
	      return;
      }
   }
   
   /*Gets the summary of all tasks*/
   public void Summary() throws FileNotFoundException{ 
	   String fileName = "TaskManager.txt";
	   List<String> taskList = new ArrayList<String>();
	   try {
		   FileReader fr = new FileReader(fileName);
		   BufferedReader br = new BufferedReader(fr);
		   String line = "";
		   while((line = br.readLine()) != null) {
			   String delim = "[ (]";
			   String[] tokens = line.split(delim);
			   taskList.add(tokens[0]);
		   }
		   br.close();
	   } catch(FileNotFoundException ex) {
		   //System.out.println("Unable to open file '" + fileName + "'");
	   } catch(IOException ex) {
		   //System.out.println("Error reading file '" + fileName + "'");
	   }
	   // Remove duplicates from ArrayList
	   Set<String> hashTask = new HashSet<>();
	   hashTask.addAll(taskList);
	   taskList.clear();
	   taskList.addAll(hashTask);
	   System.out.println("//========================Entry's Below===========================//");
	   System.out.println();
	   for(int i = 0; i < taskList.size(); i++) {
		   String name = taskList.get(i);
		   Summary(name);
		   System.out.println();
	   }
	   System.out.println("//================================================================//");
   }
   
   public int numberOfTimes(String name) {
	   String fileName = "TaskManager.txt";
	   int count = 0;
	   try {
		   FileReader fr = new FileReader(fileName);
		   BufferedReader br = new BufferedReader(fr);
		   String line = "";
		   while((line = br.readLine()) != null) {
			   if(line.contains(name + " (time")) {
				   count++;
			   }
		   }
		   br.close();
	   } catch(FileNotFoundException ex) {
		   //System.out.println("Unable to open file '" + fileName + "'");
	   } catch(IOException ex) {
		   //System.out.println("Error reading file '" + fileName + "'");
	   }
	   return count;
   }
}