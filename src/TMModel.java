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
	        }break;
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
		    }catch(FileNotFoundException e) {
		    	e.printStackTrace();
		    }
		    return;
	      }
	}
	   
	public void Stats(long XS[], int XSL, long S[], int SL, long M[], int ML, long L[], int LL, long XL[], int XLL) {
		System.out.println(SL);
		System.out.println(Arrays.toString(S));
		int x = 0;
		long seconds = x / 1000 % 60;
		long minutes = x / (60 * 1000) % 60;
		long hours = x / (60 * 60 * 100) % 24;
		//System.out.println("Total Time: " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds");
	}
	
	public long min(long Array[], int length) {
		long minTime = Array[0];
		for(int i = 1; i < length; i++) {
			if(Array[i] < minTime) {
				minTime = Array[i];
			}
		}
		return minTime;
	}
	
	public long max(long Array[], int length) {
		long maxTime = Array[0];
		for(int i = 1; i > length; i++) {
			if(Array[i] > maxTime) {
				maxTime = Array[i];
			}
		}
		return maxTime;
			
	}
	
	public long average(long Array[], int length) {
		long sum = 0;
		long average = 0;
		for(int i = 0; i < length; i++) {
			sum = sum + Array[i];
		}
		average = sum/length;
		return average;
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
		 //Length of arrays
		 int XSL = 0;
		 int SL = 0;
		 int ML = 0;
		 int LL = 0;
		 int XLL = 0;
		 //Arrays holding elapsed time for each size
		 long XS[] = new long[100];
		 long S[] = new long[100];
		 long M[] = new long[100];
		 long L[] = new long[100];
		 long XL[] = new long[100];
		 for(int i = 0; i < taskList.size(); i++) {
			 String name = taskList.get(i);
			 Summary(name);
			 long x = Elapsed(name);
			 String s = Size(name);
			 switch(s) {  
		     	case "XS": 
		     		XS[XSL] = x;
		         	XSL++;
		            break;
		        case "S":
		        	S[SL] = x;
			        SL++; 
		            break;
		        case "M":
		        	M[ML] = x;
			        ML++;
		            break;
		        case "L": 
		        	L[LL] = x;
			        LL++;
		      		break;
		        case "XL": 
		        	XL[XLL] = x;
			        XLL++;
			        break;
			 }   
			 System.out.println();
		 }
		 System.out.println("//=============================STATS==============================//");
		 Stats(XS, XSL, S, SL, M, ML, L, LL, XL, XLL);
		 System.out.println("//================================================================//");
	}

	public String Size(String name) throws NullPointerException{
		String name_parse = name + " (size)";
		String size = "";
		String fileName = "TaskManager.txt";
		String line = null;
		//int numberOfTimes = 0;
		try {
			FileReader fr1 = new FileReader(fileName);
			BufferedReader br1 = new BufferedReader(fr1);
			while((line = br1.readLine()) != null) {
				String delim = ": ";
				String[] tokens = line.split(delim);
				if(tokens[0].equals(name_parse)) {
					String s = tokens[1];
					size = s.replaceAll("\\s","");
				}
			 }
			 br1.close();
			   
		} catch(FileNotFoundException ex) {
			   //System.out.println("Unable to open file '" + fileName + "'");
		} catch(IOException ex) {
			   //System.out.println("Error reading file '" + fileName + "'");
		}
		return size;
	}
	   
	   
	public long Elapsed(String name) throws NullPointerException{
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
		} catch(IOException ex) {
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
			}
			catch(IOException ex) {
			}
		}
		long elapsed = 0;
		for (int i = 0; i < numberOfTimes; i++) {
			if((i % 2) == 0) {
				elapsed = elapsed + (Times[i + 1].getTime() - Times[i].getTime());
			}
			   
		}
		return elapsed;
	}
}