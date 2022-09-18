/**
 * @(#)TestRecursion.java
 *
 *
 * @author 
 * @version 1.00 2007/2/13
 */


public class TestRecursion {

    public static void main (String args [] ) 
    {
    	int i = 5;
    	myMethod(i);
    }
    
    static void myMethod( int counter)
	{
		if(counter == 0)
     		return;
		else
       	{
       		System.out.println(""+counter);
       		myMethod(--counter);
       		return;
      	}
	}
    
    
}