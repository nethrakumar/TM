import java.util.*;
import java.io.*;
import java.text.*;

public class TMModel {
	public TMModel(String[] args) throws IOException {
		String name = "";
		  String oldName = "";
		  String newName = "";
		  if(args.length != 1) {       //Creates a ArrayOutOfBounds if you don't do this because Summary() can't use args[1], but Summary(name) does.
			  name = args[1];          //Therefore name = args[1] only if you're using Summary(name), Start/Stop(name), and Describe(name, description)
			  if(args.length == 3) {
				  oldName = args[1];
			  	  newName = args[2];
			  }
			  
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
	         case "Start": startTask(name);
	            break;
	         case "Stop": stopTask(name);
	            break;
	         case "Describe": describeTask(name, desc);
	            break;
	         case "Size": sizeTask(name, size);
	      		break;
	         case "Rename": renameTask(oldName, newName);
	         	break;
	         case "Delete": deleteTask(name);
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
	public void startTask(String name) {
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
	public void stopTask(String name) {
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
	public void sizeTask(String name, String size) {
		   String taskName = name;
		   System.out.println(taskName);
		   System.out.println(size);
		   Log entry = new Log(name);
		   entry.WriteToFileSize(size);
		   return;
	   }
	public void describeTask(String name, String desc) {
	      String taskName = name;
	      System.out.println(taskName);
	      System.out.println(desc);
	      Log entry = new Log(name);
	      entry.WriteToFileDescr(desc);
	      return;
	   }
	public void renameTask(String oldName, String newName) throws IOException {
		   File file = new File("TaskManager.txt");
		   File temp = File.createTempFile("file", ".txt", file.getParentFile());
		   String charset = "UTF-8";
		   String og = oldName;
		   String rename = newName;
		   BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
		   PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
		   for(String line; (line = reader.readLine()) != null;) {
			   line = line.replaceAll(og, rename);
			   writer.println(line);
		   }reader.close();
		   writer.close();
		   file.delete();
		   temp.renameTo(file);
	   }
	public void deleteTask(String name) throws IOException {
		   File file = new File("TaskManager.txt");
		   File temp = File.createTempFile("file", ".txt", file.getParentFile());
		   String charset = "UTF-8";
		   String delete = name;
		   BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
		   PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(temp), charset));
		   for(String line; (line = reader.readLine()) != null;) {
			   line = line.replaceAll(delete, "//");
			   writer.println(line);
		   }reader.close();
		   writer.close();
		   file.delete();
		   temp.renameTo(file);
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
	   
	   public void Stats(String size) {
		   
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
		   hashTask.remove("//");
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
}
