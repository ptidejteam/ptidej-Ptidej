/**
 * @(#)TestWriteFile.java
 *
 *
 * @author 
 * @version 1.00 2007/2/4
 */
import java.io.*;

public class TestWriteFile
{
	public static void main(String args[])
  	{   
  		for(int i = 0; i < 3; i++)
  	 		createLogInstruction();
  	}  	
  		
  		
  	public static void createLogInstruction()
  	{
  		try {
    		// Create file 
    		FileWriter fstream = new FileWriter("R:\\DIRO Maitrise\\TraceResults\\MyLogFile.txt", true);
 	    	BufferedWriter out = new BufferedWriter(fstream);
    		out.write("allo\n");    
    		//Close the output stream
    		out.close();
    	}
    	catch (Exception e) { //Catch exception if any
      		System.err.println("Error: " + e.getMessage());
    	}
  		
  		System.out.println("la Suite ");
  	}
  	
  	
}