/**
 * @(#)TestContext.java
 *
 *
 * @author 
 * @version 1.00 2007/2/4
 */


public class TestContext {

    public static void main(String args[])
    {
    	for (int i = 0 ; i < 4; i++)
    	{
    		int j = 0;
    		printMessage("Allo for");
    		while (j < 2)
    		{
    			int l = 0;
				printMessage("Allo while");
    			if(l == 0)
    				printMessage("Allo if");
    			j++;
    		}
    	}
    }
    
    static void printMessage(String message)
    {
    	System.out.println(message);
    }
    
    
}