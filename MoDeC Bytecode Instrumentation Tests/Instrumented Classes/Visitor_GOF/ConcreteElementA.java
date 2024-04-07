/**
 * @(#)Text2.java
 *
 *
 * @author 
 * @version 1.00 2007/3/16
 */


public class ConcreteElementA implements Element {
	
	private String name;

    public ConcreteElementA() {
   		name = "ConcreteElementA";
    }
    
     public void acceptVisitor(Visitor v)
     {
     	v.visitConcreteElementA(this);
     }
     
     public String getName()
     {
     	return name;
     }
     
    
    
}