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
package parser.visitor;

import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.WhileStatement;
import parser.wrapper.ExtendedASTVisitor;
import parser.wrapper.NamedCompilationUnit;

public class ComparisonStatement extends ExtendedASTVisitor {

	@Override
	public boolean visit(final ConditionalExpression node) {
		System.out.println("ConditionalExpression: " + node.getExpression());
		return super.visit(node);
	}

	@Override
	public boolean visit(final EnhancedForStatement node) {
		//System.out.println("EnhancedForStatement getExpression: "+node.getExpression());
		//System.out.println("EnhancedForStatement getParameter: "+node.getParameter());
		System.out.println("EnhancedForStatement: " + node.getParameter()
				+ " : " + node.getExpression());
		return super.visit(node);
	}

	@Override
	public boolean visit(final ForStatement node) {
		System.out.println("ForStatement getExpression: "
				+ node.getExpression());
		return super.visit(node);
	}

	@Override
	public boolean visit(final IfStatement node) {
		System.out.println("IfStatement: " + node.getExpression());
		//System.out.println("IfStatement Else Statement: "+node.getElseStatement());
		//System.out.println("IfStatement Then Statement: "+node.getThenStatement());
		return super.visit(node);
	}

	@Override
	public boolean visit(final NamedCompilationUnit aNamedCompilationUnit) {
		// TODO Auto-generated method stub
		return super.visit(aNamedCompilationUnit);
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean visit(final SwitchCase node) {
		System.out.println("SwitchCase: " + node.getExpression());
		return super.visit(node);
	}

	@Override
	public boolean visit(final WhileStatement node) {
		System.out.println("WhileStatement: " + node.getExpression());
		return super.visit(node);
	}

}
