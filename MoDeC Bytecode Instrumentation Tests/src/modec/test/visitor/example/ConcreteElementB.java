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
 * @(#)Text6.java
 *
 *
 * @author 
 * @version 1.00 2007/3/16
 */

public class ConcreteElementB implements Element {

	private String name;
	
    public ConcreteElementB() {
    	this.name = "ConcreteElementB";
    }
    
     public void acceptVisitor(Visitor v)
     {
     	v.visitConcreteElementB(this);
     }
     
     public String getName()
     {
     	return this.name;
     }
    
    
}
