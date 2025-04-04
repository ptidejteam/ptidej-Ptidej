/*******************************************************************************
 * Copyright (c) 2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
//
// Generated by JTB 1.2.2
//

package util.parser.java.v15.nodes;

import java.util.*;

/**
 * Represents a grammar list, e.g. ( A )+
 */
public class NodeList implements NodeListInterface {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

public NodeList() {
      this.nodes = new Vector<Node>();
   }

   public NodeList(Node firstNode) {
      this.nodes = new Vector<Node>();
      addNode(firstNode);
   }

   public void addNode(Node n) {
      this.nodes.addElement(n);
   }

   public Enumeration<Node> elements() { return this.nodes.elements(); }
   public Node elementAt(int i)  { return (Node)this.nodes.elementAt(i); }
   public int size()             { return this.nodes.size(); }
   public void accept(util.parser.java.v15.visitors.Visitor v) {
      v.visit(this);
   }
   public Object accept(util.parser.java.v15.visitors.ObjectVisitor v, Object argu) {
      return v.visit(this,argu);
   }

   public Vector<Node> nodes;
}

