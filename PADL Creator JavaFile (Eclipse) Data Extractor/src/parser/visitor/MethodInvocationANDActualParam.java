/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package parser.visitor;

import org.eclipse.jdt.core.dom.MethodInvocation;
import parser.wrapper.ExtendedASTVisitor;
import parser.wrapper.NamedCompilationUnit;

public class MethodInvocationANDActualParam extends ExtendedASTVisitor {

	@Override
	public boolean visit(final MethodInvocation node) {
		System.out.println("arguments(): " + node.arguments());
		System.out.println("getExpression(): " + node.getExpression());
		System.out.println("getName(): " + node.getName());
		return super.visit(node);
	}

	@Override
	public boolean visit(final NamedCompilationUnit aNamedCompilationUnit) {
		// TODO Auto-generated method stub
		return super.visit(aNamedCompilationUnit);
	}

}
