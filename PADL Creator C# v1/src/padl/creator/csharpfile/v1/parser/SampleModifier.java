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
package padl.creator.csharpfile.v1.parser;
import java.lang.reflect.Modifier;

public class SampleModifier {

	  public static void main(String[] args) {
	    new String();
	  }

	  public static void printModifiers(Object o) {
	    Class c = o.getClass();
	    int m = c.getModifiers();
	    if (Modifier.isPublic(m))
	     System.out.println("public");
	   if (Modifier.isAbstract(m))
	     System.out.println("abstract");
	 //   if (Modifier.isFinal(m))
	//      System.out.println("final");
	  }
	}
       
