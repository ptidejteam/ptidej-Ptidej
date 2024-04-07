/**
 * @(#)Text2.java
 *
 *
 * @author 
 * @version 1.00 2007/3/14
 */

import java.util.*;

public class ConcreteSubject implements Subject {

	Vector theObservers;
	private String concreteSubject;
		
    public ConcreteSubject() {
   		theObservers = new Vector();
    }
    
    public void attach(Observer o)
    {
    	theObservers.add(o);
    }
    
	public void detach(Observer o)
	{
		theObservers.remove(o);
	}
    public void notifyObserver()
    {
    	for(int i = 0 ; i < theObservers.size(); i++)
    		((Observer)theObservers.get(i)).update(this);    	
    } 
    	
    public String getState()
    {
    	return concreteSubject;
    }  
    	
    public void setState(String concreteSubject)
    {
    	this.concreteSubject = concreteSubject;
    }
    
}