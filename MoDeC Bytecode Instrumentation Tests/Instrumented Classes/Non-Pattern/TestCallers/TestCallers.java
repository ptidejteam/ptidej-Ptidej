/**
 * @(#)Text1.java
 *
 *
 * @author 
 * @version 1.00 2007/3/28
 */


public class TestCallers{

  	public static void main (String [] args)
  	{
  		a();
  	}
  	
  	static void a()
  	{
  		b();
  	}
    
    static void b()
    {
    	c();
    }
    static void c()
    {
    	d();
    }
    
    static void d()
    {
    	System.out.println("d");
    }
    
    
}