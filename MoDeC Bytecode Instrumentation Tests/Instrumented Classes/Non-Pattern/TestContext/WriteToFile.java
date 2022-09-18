/**
 * @(#)TestWriteFile.java
 *
 *
 * @author 
 * @version 1.00 2007/2/4
 */
import java.io.*;

public class WriteToFile
{
  		
  static void write(String filename, String log)
  	{
  		try {
    		FileWriter fstream = new FileWriter(filename, true);
 	    	BufferedWriter out = new BufferedWriter(fstream);
    		out.write(log + "\n");    
    		out.close();
    	}
    	catch (Exception e) { //Catch exception if any
      		System.err.println("Error: " + e.getMessage());
    	}
    }
  	
  	
}