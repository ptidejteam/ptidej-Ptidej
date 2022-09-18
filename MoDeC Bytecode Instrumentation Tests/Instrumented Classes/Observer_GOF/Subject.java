/**
 * @(#)Text1.java
 *
 *
 * @author 
 * @version 1.00 2007/3/14
 */


public interface Subject {
	
	public void attach(Observer o);
	public void detach(Observer o);
    public void notifyObserver();
    
    
}