import java.io.*;

public class LogToFile {
	
	static void write(String filename, Object log)
  	{
  		try {
    		FileWriter fstream = new FileWriter(filename, true);
 	    	BufferedWriter out = new BufferedWriter(fstream);
    		out.write(log.toString() + "\n");    
    		out.close();
    	}
    	catch (Exception e) { //Catch exception if any
      		System.err.println("Error: " + e.getMessage());
    	}
    }
}