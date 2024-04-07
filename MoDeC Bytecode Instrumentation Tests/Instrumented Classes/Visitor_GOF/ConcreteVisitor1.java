/**
 * @(#)Text4.java
 *
 *
 * @author 
 * @version 1.00 2007/3/16
 */


public class ConcreteVisitor1 implements Visitor {   
    
    public void visitConcreteElementA(ConcreteElementA ceA)
    {
    	System.out.println(ceA.getName());     		
    }
    
    public void visitConcreteElementB(ConcreteElementB ceB)
    {
    	System.out.println(ceB.getName());
    }   
}