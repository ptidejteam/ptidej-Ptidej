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

/**
 * Grammar production:
 * f0 -> [ TypeParameters() ]
 * f1 -> <IDENTIFIER>
 * f2 -> FormalParameters()
 * f3 -> [ "throws" NameList() ]
 * f4 -> "{"
 * f5 -> [ ExplicitConstructorInvocation() ]
 * f6 -> ( BlockStatement() )*
 * f7 -> "}"
 */
public class ConstructorDeclaration implements Node {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public NodeOptional f0;
   public NodeToken f1;
   public FormalParameters f2;
   public NodeOptional f3;
   public NodeToken f4;
   public NodeOptional f5;
   public NodeListOptional f6;
   public NodeToken f7;

   public ConstructorDeclaration(NodeOptional n0, NodeToken n1, FormalParameters n2, NodeOptional n3, NodeToken n4, NodeOptional n5, NodeListOptional n6, NodeToken n7) {
      this.f0 = n0;
      this.f1 = n1;
      this.f2 = n2;
      this.f3 = n3;
      this.f4 = n4;
      this.f5 = n5;
      this.f6 = n6;
      this.f7 = n7;
   }

   public ConstructorDeclaration(NodeOptional n0, NodeToken n1, FormalParameters n2, NodeOptional n3, NodeOptional n4, NodeListOptional n5) {
      this.f0 = n0;
      this.f1 = n1;
      this.f2 = n2;
      this.f3 = n3;
      this.f4 = new NodeToken("{");
      this.f5 = n4;
      this.f6 = n5;
      this.f7 = new NodeToken("}");
   }

   public void accept(util.parser.java.v15.visitors.Visitor v) {
      v.visit(this);
   }
   public Object accept(util.parser.java.v15.visitors.ObjectVisitor v, Object argu) {
      return v.visit(this,argu);
   }
}

