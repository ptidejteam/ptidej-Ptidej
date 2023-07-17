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

import java.util.Stack;
import org.eclipse.cdt.core.dom.ast.ASTVisitor;
import org.eclipse.cdt.core.dom.ast.DOMException;
import org.eclipse.cdt.core.dom.ast.IASTDeclSpecifier;
import org.eclipse.cdt.core.dom.ast.IASTDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTProblemDeclaration;
import org.eclipse.cdt.core.dom.ast.IASTSimpleDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTFunctionDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTNamespaceDefinition;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateId;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateParameter;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplateSpecialization;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTTemplatedTypeTemplateParameter;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDeclaration;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTUsingDirective;
import org.eclipse.cdt.core.dom.ast.cpp.ICPPASTVisibilityLabel;
import padl.kernel.Constants;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IContainer;
import padl.kernel.exception.ModelDeclarationException;
import util.io.ProxyConsole;

public class VisitorNamespacesAndClasses extends ASTVisitor {
	private Accumulator accumulator;
	private final ICodeLevelModel codeLevelModel;
	private final GeneratorHelper generatorHelper;
	private final Stack<IContainer> stackOfNamespaces;

	public VisitorNamespacesAndClasses(
		final ICodeLevelModel aModel,
		final GeneratorHelper aGeneratorHelper,
		final Accumulator anAccumulator) {

		super(false);
		this.shouldVisitDeclarations = true;
		this.shouldVisitDeclSpecifiers = true;
		this.shouldVisitNamespaces = true;
		this.shouldVisitProblems = true;

		this.codeLevelModel = aModel;
		this.generatorHelper = aGeneratorHelper;
		this.accumulator = anAccumulator;

		this.stackOfNamespaces = new Stack<IContainer>();
		// By default, I put everything into a default package.
		this.stackOfNamespaces.push((IContainer) this.codeLevelModel
			.getConstituentFromID(Constants.DEFAULT_PACKAGE_ID));
	}
	public int leave(final ICPPASTNamespaceDefinition aDeclaration) {
		this.stackOfNamespaces.pop();
		return ASTVisitor.PROCESS_CONTINUE;
	}
	public int visit(final IASTDeclaration aDeclaration) {
		// Yann 2013/07/15: Weird!
		// For some reasons, I must call myself the correct
		// methods instead of the visit doing its job... 
		// Eclipse! No comment... :-(
		if (aDeclaration instanceof IASTProblemDeclaration) {
			return this.visit((IASTProblemDeclaration) aDeclaration);
		}
		else if (aDeclaration instanceof IASTSimpleDeclaration) {
			// Do nothing. Will be handled by VisitorGlobalVariables.
		}
		else if (aDeclaration instanceof ICPPASTFunctionDefinition) {
			// Do nothing. Will be handled by VisitorGlobalVariables.
		}
		else if (aDeclaration instanceof ICPPASTNamespaceDefinition) {
			return this.visit((ICPPASTNamespaceDefinition) aDeclaration);
		}
		else if (aDeclaration instanceof ICPPASTVisibilityLabel) {
			// Do nothing.
		}
		else if (aDeclaration instanceof ICPPASTUsingDirective) {
			return this.visit((ICPPASTUsingDirective) aDeclaration);
		}
		else if (aDeclaration instanceof ICPPASTUsingDeclaration) {
			return this.visit((ICPPASTUsingDeclaration) aDeclaration);
		}
		else if (aDeclaration instanceof ICPPASTTemplatedTypeTemplateParameter
				|| aDeclaration instanceof ICPPASTTemplateId
				|| aDeclaration instanceof ICPPASTTemplateParameter
				|| aDeclaration instanceof ICPPASTTemplateSpecialization
				|| aDeclaration instanceof ICPPASTTemplateDeclaration) {
			// Currently, do nothing.
		}
		else {
			Utils.reportUnknownType(
				VisitorNamespacesAndClasses.class,
				"declaration",
				aDeclaration.getRawSignature(),
				aDeclaration.getClass());
		}
		return ASTVisitor.PROCESS_CONTINUE;
	}
	public int visit(final IASTDeclSpecifier aDeclaration) {
		// We only handle first-level classes, nested classes
		// and other member are dealt with in subsequent phases. 
		try {
			this.generatorHelper.convertDeclaration(
				this.accumulator,
				(IASTDeclSpecifier) aDeclaration,
				this.stackOfNamespaces);
		}
		catch (final DOMException | ModelDeclarationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
		// I skip the children of the declaration because
		// - classes, fields and methods (including parameters 
		//   and method invocations) will be treated separately;
		// - function, parameters and method invocations will
		//   be treated separately.
		return ASTVisitor.PROCESS_SKIP;
	}
	public int visit(final IASTProblemDeclaration aDeclaration) {
		// Declaration with problems
		ProxyConsole
			.getInstance()
			.errorOutput()
			.print(VisitorNamespacesAndClasses.class.getName());
		ProxyConsole.getInstance().errorOutput().print(" reports \"");
		ProxyConsole
			.getInstance()
			.errorOutput()
			.print(
				aDeclaration.getRawSignature().substring(
					0,
					Math.min(32, aDeclaration.getRawSignature().length())));
		ProxyConsole.getInstance().errorOutput().print("\" (");
		ProxyConsole
			.getInstance()
			.errorOutput()
			.print(((IASTProblemDeclaration) aDeclaration).getProblem());
		ProxyConsole.getInstance().errorOutput().println(')');
		// No need to process anything child of an IASTProblemDeclaration.
		return ASTVisitor.PROCESS_SKIP;
	}
	public int visit(final ICPPASTNamespaceDefinition aDeclaration) {
		this.generatorHelper.convertDeclaration(
			this.accumulator,
			(ICPPASTNamespaceDefinition) aDeclaration,
			this.stackOfNamespaces);
		return ASTVisitor.PROCESS_CONTINUE;
	}
	public int visit(final ICPPASTTemplateDeclaration aDeclaration) {
		return ASTVisitor.PROCESS_SKIP;
	}
	public int visit(final ICPPASTTemplatedTypeTemplateParameter aDeclaration) {
		return ASTVisitor.PROCESS_SKIP;
	}
	public int visit(final ICPPASTTemplateId aDeclaration) {
		return ASTVisitor.PROCESS_SKIP;
	}
	public int visit(final ICPPASTTemplateParameter aDeclaration) {
		return ASTVisitor.PROCESS_SKIP;
	}
	public int visit(final ICPPASTTemplateSpecialization aDeclaration) {
		return ASTVisitor.PROCESS_SKIP;
	}
	public int visit(final ICPPASTUsingDeclaration aDeclaration) {
		this.generatorHelper.convertDeclaration(
			this.accumulator,
			(ICPPASTUsingDeclaration) aDeclaration,
			this.stackOfNamespaces);
		return ASTVisitor.PROCESS_CONTINUE;
	}
	public int visit(final ICPPASTUsingDirective aDeclaration) {
		this.generatorHelper.convertDeclaration(
			this.accumulator,
			(ICPPASTUsingDirective) aDeclaration,
			this.stackOfNamespaces);
		return ASTVisitor.PROCESS_CONTINUE;
	}
}
