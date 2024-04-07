/**
 * @(#)TestWriteToFile.java
 *
 *
 * @author 
 * @version 1.00 2007/2/8
 */


public class TestWriteToFile {

    public static void main(String [] args) 
    {
    	StackTraceElement[] trace = new Throwable().getStackTrace();    
    	WriteToFile.write("R:\\DIRO Maitrise\\TraceResults\\MyLogFile.txt", trace[trace.length-1].toString());
    }
    
    
}