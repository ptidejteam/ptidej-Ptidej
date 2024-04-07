/**
 * @(#)Text5.java
 *
 *
 * @author 
 * @version 1.00 2007/3/14
 */


public class TestModelObserver {

   public static void main(String args[])
   {	
   		ConcreteSubject cs = new ConcreteSubject();
   		ConcreteObserver co = new ConcreteObserver(cs);
   		
   	//	System.out.println("AVANT : " + co.getState());
   		cs.setState("Changing my state now");
   		cs.notifyObserver();
   	//	System.out.println("APRES : " + co.getState());
   }
    
    
}