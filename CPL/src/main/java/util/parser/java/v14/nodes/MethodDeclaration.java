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
 * f0 -> ( "public" | "protected" | "private" | "static" | "abstract" | "final" | "native" | "synchronized" | "strictfp" )*
 * f1 -> ResultType()
 * f2 -> MethodDeclarator()
 * f3 -> [ "throws" NameList() ]
 * f4 -> ( Block() | ";" )
 */
public class MethodDeclaration implements Node {
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
public NodeListOptional f0;
   public ResultType f1;
   public MethodDeclarator f2;
   public NodeOptional f3;
   public NodeChoice f4;

   public MethodDeclaration(NodeListOptional n0, ResultType n1, MethodDeclarator n2, NodeOptional n3, NodeChoice n4) {
      this.f0 = n0;
      this.f1 = n1;
      this.f2 = n2;
      this.f3 = n3;
      this.f4 = n4;
   }

   public void accept(util.parser.java.v14.visitors.Visitor v) {
      v.visit(this);
   }
   public Object accept(util.parser.java.v14.visitors.ObjectVisitor v, Object argu) {
      return v.visit(this,argu);
   }
}

