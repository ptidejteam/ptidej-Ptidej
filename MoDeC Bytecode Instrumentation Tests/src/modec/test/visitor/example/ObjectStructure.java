/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package modec.test.visitor.example;

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
    	new ObjectStructure();
    }
}
