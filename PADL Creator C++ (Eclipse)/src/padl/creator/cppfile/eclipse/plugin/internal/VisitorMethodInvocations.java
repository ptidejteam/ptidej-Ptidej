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
package padl.creator.cppfile.eclipse.plugin.internal;

import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.DOMException;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTDeclarationStatement;
import org.eclipse.cdt.core.dom.ast.IASTExpression;
import org.eclipse.cdt.core.dom.ast.IASTExpressionStatement;
import org.eclipse.cdt.core.dom.ast.IASTFieldReference;
import org.eclipse.cdt.core.dom.ast.IASTFunctionCallExpression;
import org.eclipse.cdt.core.dom.ast.IASTIdExpression;
import org.eclipse.cdt.core.dom.ast.IASTProblemDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTStatement;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDirective;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTVisibilityLabel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.ModelDeclarationException;
import util.io.ProxyConsole;

public class VisitorMethodInvocations extends ASTVisitor {
	private final ICodeLevelModel codeLevelModel;
	private final Accumulator accumulator;
	private final GeneratorHelper generatorHelper;

	public VisitorMethodInvocations(
		final ICodeLevelModel aModel,
		final GeneratorHelper aGeneratorHelper,
		final Accumulator anAccumulator) {

		super(false);
		this.shouldVisitDeclarations = true;
		this.shouldVisitExpressions = true;
		this.shouldVisitStatements = true;

		this.codeLevelModel = aModel;
		this.generatorHelper = aGeneratorHelper;
		this.accumulator = anAccumulator;
	}
	public int visit(final IASTDeclaration aDeclaration) {
		// Yann 2013/07/15: Weird!
		// For some reasons, I must call myself the correct
		// methods instead of the visit doing its job... 
		// Eclipse! No comment... :-(
		if (aDeclaration instanceof ICPPASTFunctionDefinition) {
			return this.visit((ICPPASTFunctionDefinition) aDeclaration);
		}
		else if (aDeclaration instanceof IASTProblemDeclaration) {
			// Do nothing.
		}
		else if (aDeclaration instanceof IASTSimpleDeclaration) {
			// Do nothing.
		}
		else if (aDeclaration instanceof ICPPASTVisibilityLabel) {
			// Do nothing.
		}
		else if (aDeclaration instanceof ICPPASTUsingDirective) {
			// Do nothing.
		}
		else {
			Utils.reportUnknownType(
				VisitorMethodInvocations.class,
				"declaration",
				aDeclaration.getRawSignature().substring(
					0,
					Math.min(32, aDeclaration.getRawSignature().length())),
				aDeclaration.getClass());
		}
		return ASTVisitor.PROCESS_CONTINUE;
	}
	public int visit(final IASTDeclarationStatement statement) {
		return ASTVisitor.PROCESS_CONTINUE;
	}
	public int visit(final IASTExpression expression) {
		if (expression instanceof IASTFieldReference) {
			return this.visit((IASTFieldReference) expression);
		}
		else if (expression instanceof IASTFunctionCallExpression) {
			return this.visit((IASTFunctionCallExpression) expression);
		}
		else if (expression instanceof IASTIdExpression) {
			// Do nothing, could treat here field accesses.
		}
		return ASTVisitor.PROCESS_CONTINUE;
	}
	public int visit(final IASTExpressionStatement statement) {
		return ASTVisitor.PROCESS_CONTINUE;
	}
	public int visit(final IASTFieldReference expression) {
		return ASTVisitor.PROCESS_CONTINUE;
	}
	public int visit(final IASTFunctionCallExpression expression) {
		try {
			this.generatorHelper.convertDeclaration(
				this.accumulator,
				expression,
				null);
		}
		catch (final DOMException | ModelDeclarationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			// Just so that the compiler does not complain...
			this.codeLevelModel.toString();
		}
		return ASTVisitor.PROCESS_SKIP;
	}
	public int visit(final IASTStatement statement) {
		if (statement instanceof IASTExpressionStatement) {
			return this.visit((IASTExpressionStatement) statement);
		}
		else if (statement instanceof IASTDeclarationStatement) {
			return this.visit((IASTDeclarationStatement) statement);
		}
		return ASTVisitor.PROCESS_CONTINUE;
	}
	public int visit(final ICPPASTFunctionDefinition aDeclaration) {
		return ASTVisitor.PROCESS_CONTINUE;
	}
}
