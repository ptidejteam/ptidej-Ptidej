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

package util.parser.java.v14.nodes;

/**
 * Grammar production:
 * f0 -> "do"
 * f1 -> Statement()
 * f2 -> "while"
 * f3 -> "("
 * f4 -> Expression()
 * f5 -> ")"
 * f6 -> ";"
 */
public class DoStatement implements Node {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public NodeToken f0;
   public Statement f1;
   public NodeToken f2;
   public NodeToken f3;
   public Expression f4;
   public NodeToken f5;
   public NodeToken f6;

   public DoStatement(NodeToken n0, Statement n1, NodeToken n2, NodeToken n3, Expression n4, NodeToken n5, NodeToken n6) {
      this.f0 = n0;
      this.f1 = n1;
      this.f2 = n2;
      this.f3 = n3;
      this.f4 = n4;
      this.f5 = n5;
      this.f6 = n6;
   }

   public DoStatement(Statement n0, Expression n1) {
      this.f0 = new NodeToken("do");
      this.f1 = n0;
      this.f2 = new NodeToken("while");
      this.f3 = new NodeToken("(");
      this.f4 = n1;
      this.f5 = new NodeToken(")");
      this.f6 = new NodeToken(";");
   }

   public void accept(util.parser.java.v14.visitors.Visitor v) {
      v.visit(this);
   }
   public Object accept(util.parser.java.v14.visitors.ObjectVisitor v, Object argu) {
      return v.visit(this,argu);
   }
}

