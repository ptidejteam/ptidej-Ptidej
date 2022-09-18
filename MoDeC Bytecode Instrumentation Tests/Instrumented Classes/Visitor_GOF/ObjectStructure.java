/**
 * @(#)Text1.java
 *
 *
 * @author 
 * @version 1.00 2007/3/16
 */


public class ObjectStructure 
{
	public ObjectStructure()
	{
		ConcreteVisitor1 visitor1 = new ConcreteVisitor1();
		ConcreteElementA  a = (ConcreteElementA) createElement ("ConcreteElementA");
		a.acceptVisitor(visitor1);
		ConcreteElementB  b = (ConcreteElementB) createElement ("ConcreteElementB");
		b.acceptVisitor(visitor1);
	}
   
   
   	public Element createElement (String element)
   	{
   		if(element.equals("ConcreteElementA"))
   			return new ConcreteElementA();
   		else return new ConcreteElementB();
   	}
    
    public static void main (String [] args)
    {
    	ObjectStructure os = new ObjectStructure();
    }
}