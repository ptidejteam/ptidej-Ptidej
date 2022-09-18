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
package parser.wrapper;

import org.eclipse.jdt.core.dom.ASTVisitor;

public abstract class ExtendedASTVisitor extends ASTVisitor {
	public void endVisit(final NamedCompilationUnit aNamedCompilationUnit) {
	}
	public void endVisitJavaFilePath(final String javaFilePath) {
	}
	public boolean visit(final NamedCompilationUnit aNamedCompilationUnit) {
		return true;
	}
	public boolean visitJavaFilePath(final String javaFilePath) {
		return true;
	}
}
