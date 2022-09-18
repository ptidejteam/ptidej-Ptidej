/**
 * @(#)Text4.java
 *
 *
 * @author 
 * @version 1.00 2007/3/14
 */


public class ConcreteObserver implements Observer {

	private String observerState;
	private ConcreteSubject concreteSubject;
	
    public ConcreteObserver(ConcreteSubject concreteSubject) {
    	this.concreteSubject = concreteSubject;
    	concreteSubject.attach(this);
    }
    
    public void update(Subject s)
    {
    	if(s == concreteSubject)
    		this.observerState = concreteSubject.getState();
    }    
    	
    public String getState()
    {
    	return observerState;
    }
    
}