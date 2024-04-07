/**
 * @(#)MyTest.java
 *
 *
 * @author 
 * @version 1.00 2007/2/19
 */


public class A {

    public void mA() {
    	B b = new B() ;
    	boolean test1 = true ;
    	boolean test2 = true ;
    	if(test1)
    		while (test2)
    		{
    			b.mB();
    			test1 = false ;
    			test2 = false ;
    		}  			
	}
	public static void main(String [] args )
	{
		A  a = new A();
		a.mA();
	}   
}

class B {
	public void mB() 
	{
		System.out.println("mB");
	}
}