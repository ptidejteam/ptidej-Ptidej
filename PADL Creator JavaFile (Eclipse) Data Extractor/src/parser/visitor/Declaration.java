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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Stack;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.ArrayType;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CatchClause;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.EnhancedForStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MemberRef;
import org.eclipse.jdt.core.dom.MemberValuePair;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.MethodRefParameter;
import org.eclipse.jdt.core.dom.Modifier;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParameterizedType;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.QualifiedType;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.TagElement;
import org.eclipse.jdt.core.dom.TextElement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.TypeParameter;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;
import org.eclipse.jdt.core.dom.WildcardType;
import parser.wrapper.ExtendedASTVisitor;
import parser.wrapper.NamedCompilationUnit;

public class Declaration extends ExtendedASTVisitor {

	OutputStreamWriter out;
	private CompilationUnit c;
	private final boolean debugMode;
	private boolean fromCatchClause = false;
	private final Stack<String> stackOfType = new Stack<String>();

	public Declaration(final boolean debugMode) {
		System.out.println(" in visitor constructor");
		this.debugMode = debugMode;
	}

	@Override
	public void endVisit(final CatchClause node) {
		this.fromCatchClause = false;
		super.endVisit(node);
	}

	@Override
	public void endVisit(final FieldDeclaration node) {
		this.stackOfType.pop();
		super.endVisit(node);
	}

	@Override
	public void endVisit(final NamedCompilationUnit aNamedCompilationUnit) {
		try {
			this.out.close();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		super.endVisit(aNamedCompilationUnit);
	}

	@Override
	public void endVisit(final VariableDeclarationExpression node) {
		this.stackOfType.pop();
		super.endVisit(node);
	}

	@Override
	public void endVisit(final VariableDeclarationStatement node) {
		this.stackOfType.pop();
		super.endVisit(node);
	}

	@Override
	public boolean visit(final AnnotationTypeDeclaration node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final AnnotationTypeMemberDeclaration node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final AnonymousClassDeclaration node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ArrayAccess node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ArrayCreation node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ArrayInitializer node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ArrayType node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final AssertStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final Assignment node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final Block node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final BlockComment node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final BooleanLiteral node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final BreakStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	/*	 @Override public boolean visit(CompilationUnit node) { 
		 this.c = node; 
		 if (this.debugMode == true)
			 System.out.println(node.getClass() + "   " +
				 this.c.getLineNumber(node.getStartPosition())); //to delete return
		 return super.visit(node); }
	*/

	/* @Override public void endVisit(CompilationUnit node) { try {
	* this.out.close(); } catch (IOException e) { e.printStackTrace(); }
	* super.endVisit(node); }
	*/

	@Override
	public boolean visit(final CastExpression node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final CatchClause node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		this.fromCatchClause = true;
		return super.visit(node);
	}

	@Override
	public boolean visit(final CharacterLiteral node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ClassInstanceCreation node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ConditionalExpression node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ConstructorInvocation node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ContinueStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final DoStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final EmptyStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final EnhancedForStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final EnumConstantDeclaration node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final EnumDeclaration node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ExpressionStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final FieldAccess node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final FieldDeclaration node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		String modifier = "";
		if (java.lang.reflect.Modifier.toString(node.getModifiers()) == "") {
			modifier = "protected";
		}
		else {
			modifier = java.lang.reflect.Modifier.toString(node.getModifiers());
		}
		this.stackOfType.push("FD;" + node.getType().toString() + ";"
				+ modifier);
		return super.visit(node);
	}

	@Override
	public boolean visit(final ForStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final IfStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ImportDeclaration node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final InfixExpression node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final Initializer node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		// System.out.print(c.getLineNumber(node.getStartPosition()));
		// System.out.print(";SD;");
		// System.out.println();
		try {
			this.out.append(Integer.toString(this.c.getLineNumber(node
				.getStartPosition())));
			this.out.append(";SD;");
			this.out.append("\n");
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final InstanceofExpression node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final Javadoc node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final LabeledStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final LineComment node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final MarkerAnnotation node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final MemberRef node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final MemberValuePair node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final MethodDeclaration node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		try {
			// System.out.print(this.c.getLineNumber(node.getName().getStartPosition()));
			this.out.append(Integer.toString(this.c.getLineNumber(node
				.getName()
				.getStartPosition())));
			if (node.isConstructor()) {
				// System.out.print(";COD;");
				this.out.append(";COD;");
			}
			else {
				// System.out.print(";MD;");
				this.out.append(";MD;");
			}
			// System.out.print(node.getName() + ";");
			this.out.append(node.getName() + ";");
			if (!node.isConstructor()) {
				// System.out.print(node.getReturnType2() + ";");
				this.out.append(node.getReturnType2() + ";");
			}
			if (java.lang.reflect.Modifier.toString(node.getModifiers()) == "") {
				// System.out.print("protected;");
				this.out.append("protected;");
			}
			else {
				// System.out.print(java.lang.reflect.Modifier.toString(node
				// .getModifiers())
				// + ";");
				this.out.append(java.lang.reflect.Modifier.toString(node
					.getModifiers()) + ";");
			}
			// System.out.println();
			this.out.append("\n");
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final MethodInvocation node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final MethodRef node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final MethodRefParameter node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final Modifier node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final NamedCompilationUnit aNamedCompilationUnit) {
		this.c = aNamedCompilationUnit.getCompilationUnit();
		if (this.debugMode == true) {
			System.out.println(aNamedCompilationUnit.getClass() + "   "
					+ this.c.getLineNumber(this.c.getStartPosition())); // to
		}
		// delete
		try {
			System.out.println("visiting: "
					+ aNamedCompilationUnit.getJavaFilePath());
			final FileOutputStream fos =
				new FileOutputStream(aNamedCompilationUnit
					.getJavaFilePath()
					.replace(".java.", ".java_VD_") + ".txt");
			this.out = new OutputStreamWriter(fos);

		}
		catch (final IOException e) {
			e.printStackTrace();
		}

		return super.visit(aNamedCompilationUnit);
	}

	@Override
	public boolean visit(final NormalAnnotation node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final NullLiteral node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final NumberLiteral node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final PackageDeclaration node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ParameterizedType node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ParenthesizedExpression node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final PostfixExpression node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final PrefixExpression node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final PrimitiveType node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final QualifiedName node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final QualifiedType node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ReturnStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final SimpleName node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final SimpleType node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + " " + node.getName() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final SingleMemberAnnotation node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final SingleVariableDeclaration node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		// we don't want to get the parameter name and type of catch clause
		if (this.fromCatchClause == false) {
			// System.out.print(this.c.getLineNumber(node.getName().getStartPosition()));
			// System.out.print(";PD;");
			// System.out.print(node.getName() + ";");
			// System.out.print(node.getType() + ";");
			// System.out.println();
			try {
				this.out.append(Integer.toString(this.c.getLineNumber(node
					.getName()
					.getStartPosition())));
				this.out.append(";PD;");
				this.out.append(node.getName() + ";");
				this.out.append(node.getType() + ";");
				if (java.lang.reflect.Modifier
					.toString(node.getModifiers())
					.length() > 0) {
					this.out.append(java.lang.reflect.Modifier.toString(node
						.getModifiers()) + ";");
				}
				else {
					this.out.append("NA" + ";");
				}

				this.out.append("\n");
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final StringLiteral node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final SuperConstructorInvocation node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final SuperFieldAccess node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final SuperMethodInvocation node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final SwitchCase node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final SwitchStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final SynchronizedStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final TagElement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final TextElement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ThisExpression node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final ThrowStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final TryStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final TypeDeclaration node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		try {
			// System.out.print(this.c.getLineNumber(node.getName().getStartPosition()));
			this.out.append(Integer.toString(this.c.getLineNumber(node
				.getName()
				.getStartPosition())));
			if (node.isInterface()) {
				// System.out.print(";ID;");
				this.out.append(";ID;");
			}
			else {
				// System.out.print(";CD;");
				this.out.append(";CD;");
			}
			// System.out.print(node.getName());
			this.out.append(node.getName().toString() + ";");
			if (node.typeParameters().size() > 0) {
				// System.out.print(node.typeParameters() + ";");
				this.out.append(node.typeParameters() + ";");
			}
			if (java.lang.reflect.Modifier.toString(node.getModifiers()) == "") {
				// System.out.print("protected;");
				this.out.append("protected;");
			}
			else {
				// System.out.print(java.lang.reflect.Modifier.toString(node
				// .getModifiers())
				// + ";");
				this.out.append(java.lang.reflect.Modifier.toString(node
					.getModifiers()) + ";");
			}
			if (node.getSuperclassType() != null) {
				// System.out.print("extends ");
				this.out.append("extends ");
				// System.out.print(node.getSuperclassType() + ";");
				this.out.append(node.getSuperclassType() + ";");
			}

			if (node.superInterfaceTypes().size() != 0) {
				// System.out.print("implements ");
				this.out.append("implements ");
				// System.out.print(node.superInterfaceTypes() + ";");
				this.out.append(node.superInterfaceTypes() + ";");
			}
			// System.out.println();
			this.out.append("\n");
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final TypeDeclarationStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final TypeLiteral node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final TypeParameter node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final VariableDeclarationExpression node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		String modifier = "";
		if (java.lang.reflect.Modifier.toString(node.getModifiers()).length() > 0) {
			modifier = java.lang.reflect.Modifier.toString(node.getModifiers());
		}
		else {
			modifier = "NA"; // NOT APLICABALE
		}
		this.stackOfType.push("LVD;" + node.getType().toString() + ";"
				+ modifier);
		return super.visit(node);
	}

	@Override
	public boolean visit(final VariableDeclarationFragment node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		final String lineNumber =
			Integer.toString(this.c.getLineNumber(node
				.getName()
				.getStartPosition()));
		String info = "";
		if (this.stackOfType.size() > 0) {
			info = this.stackOfType.peek();
		}

		String type = "";
		String declaration = "";
		String modifier = "";
		if (info.length() > 0) {
			final String[] infos = info.split(";");
			type = infos[1];
			declaration = infos[0];
			modifier = infos[2];
		}

		try {
			this.out.append(lineNumber + ";");
			this.out.append(declaration + ";");
			this.out.append(node.getName() + ";");
			this.out.append(type + ";");
			this.out.append(modifier + ";");
			this.out.append("\n");
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		//this.variableDeclarationInfo.put(node.getName().toString(), lineNumber);
		return super.visit(node);
	}

	@Override
	public boolean visit(final VariableDeclarationStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		String modifier = "";
		if (java.lang.reflect.Modifier.toString(node.getModifiers()).length() > 0) {
			modifier = java.lang.reflect.Modifier.toString(node.getModifiers());
		}
		else {
			modifier = "NA"; // NOT APLICABALE
		}
		this.stackOfType.push("LVD;" + node.getType().toString() + ";"
				+ modifier);
		return super.visit(node);
	}

	@Override
	public boolean visit(final WhileStatement node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}

	@Override
	public boolean visit(final WildcardType node) {
		if (this.debugMode == true) {
			System.out.println(node.getClass() + "   "
					+ this.c.getLineNumber(node.getStartPosition())); // to
		}
		// delete
		return super.visit(node);
	}
}
