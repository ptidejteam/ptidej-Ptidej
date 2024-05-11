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
package padl.creator.javafile.eclipse.astVisitors;

import org.eclipse.jdt.core.dom.ASTNode;
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
import util.io.ProxyConsole;

public class CompleteVisitor extends ExtendedASTVisitor {

	@Override
	public void endVisit(final AnnotationTypeDeclaration node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final AnnotationTypeMemberDeclaration node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final AnonymousClassDeclaration node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ArrayAccess node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ArrayCreation node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ArrayInitializer node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ArrayType node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final AssertStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final Assignment node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final Block node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final BlockComment node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final BooleanLiteral node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final BreakStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final CastExpression node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final CatchClause node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final CharacterLiteral node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ClassInstanceCreation node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final CompilationUnit node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ConditionalExpression node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ConstructorInvocation node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ContinueStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final DoStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final EmptyStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final EnhancedForStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final EnumConstantDeclaration node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final EnumDeclaration node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ExpressionStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final FieldAccess node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final FieldDeclaration node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ForStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final IfStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ImportDeclaration node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final InfixExpression node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final Initializer node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final InstanceofExpression node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final Javadoc node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final LabeledStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final LineComment node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final MarkerAnnotation node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final MemberRef node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final MemberValuePair node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final MethodDeclaration node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final MethodInvocation node) {
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println(
				" SuperMethodInvocation " + node.toString()
						+ node.resolveTypeBinding().getQualifiedName());
		super.endVisit(node);
	}

	@Override
	public void endVisit(final MethodRef node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final MethodRefParameter node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final Modifier node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final NamedCompilationUnit aNamedCompilationUnit) {

		super.endVisit(aNamedCompilationUnit);
	}

	@Override
	public void endVisit(final NormalAnnotation node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final NullLiteral node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final NumberLiteral node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final PackageDeclaration node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ParameterizedType node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ParenthesizedExpression node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final PostfixExpression node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final PrefixExpression node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final PrimitiveType node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final QualifiedName node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final QualifiedType node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ReturnStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final SimpleName node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final SimpleType node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final SingleMemberAnnotation node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final SingleVariableDeclaration node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final StringLiteral node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final SuperConstructorInvocation node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final SuperFieldAccess node) {
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println(
				" SuperMethodInvocation " + node.toString()
						+ node.resolveTypeBinding().getQualifiedName());
		super.endVisit(node);
	}

	@Override
	public void endVisit(final SuperMethodInvocation node) {
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println(
				" SuperMethodInvocation " + node.toString()
						+ node.resolveTypeBinding().getQualifiedName());
		super.endVisit(node);
	}

	@Override
	public void endVisit(final SwitchCase node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final SwitchStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final SynchronizedStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final TagElement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final TextElement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ThisExpression node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final ThrowStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final TryStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final TypeDeclaration node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final TypeDeclarationStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final TypeLiteral node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final TypeParameter node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final VariableDeclarationExpression node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final VariableDeclarationFragment node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final VariableDeclarationStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final WhileStatement node) {

		super.endVisit(node);
	}

	@Override
	public void endVisit(final WildcardType node) {

		super.endVisit(node);
	}

	@Override
	public void endVisitJavaFilePath(final String javaFilePath) {

		super.endVisitJavaFilePath(javaFilePath);
	}

	@Override
	public void postVisit(final ASTNode node) {

		super.postVisit(node);
	}

	@Override
	public void preVisit(final ASTNode node) {

		super.preVisit(node);
	}

	@Override
	public boolean preVisit2(final ASTNode node) {

		return super.preVisit2(node);
	}

	@Override
	public boolean visit(final AnnotationTypeDeclaration node) {
		return false;
	}

	@Override
	public boolean visit(final AnnotationTypeMemberDeclaration node) {
		return false;
	}

	@Override
	public boolean visit(final AnonymousClassDeclaration node) {
		return false;
	}

	@Override
	public boolean visit(final ArrayAccess node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final ArrayCreation node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final ArrayInitializer node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final ArrayType node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final AssertStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final Assignment node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final Block node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final BlockComment node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final BooleanLiteral node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final BreakStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final CastExpression node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final CatchClause node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final CharacterLiteral node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final ClassInstanceCreation node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final CompilationUnit node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final ConditionalExpression node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final ConstructorInvocation node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final ContinueStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final DoStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final EmptyStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final EnhancedForStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final EnumConstantDeclaration node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final EnumDeclaration node) {

		return false;
	}

	@Override
	public boolean visit(final ExpressionStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final FieldAccess node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final FieldDeclaration node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final ForStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final IfStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final ImportDeclaration node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final InfixExpression node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final Initializer node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final InstanceofExpression node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final Javadoc node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final LabeledStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final LineComment node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final MarkerAnnotation node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final MemberRef node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final MemberValuePair node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final MethodDeclaration node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final MethodInvocation node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final MethodRef node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final MethodRefParameter node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final Modifier node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final NamedCompilationUnit aNamedCompilationUnit) {

		return super.visit(aNamedCompilationUnit);
	}

	@Override
	public boolean visit(final NormalAnnotation node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final NullLiteral node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final NumberLiteral node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final PackageDeclaration node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final ParameterizedType node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final ParenthesizedExpression node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final PostfixExpression node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final PrefixExpression node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final PrimitiveType node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final QualifiedName node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final QualifiedType node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final ReturnStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final SimpleName node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final SimpleType node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final SingleMemberAnnotation node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final SingleVariableDeclaration node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final StringLiteral node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final SuperConstructorInvocation node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final SuperFieldAccess node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final SuperMethodInvocation node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final SwitchCase node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final SwitchStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final SynchronizedStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final TagElement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final TextElement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final ThisExpression node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final ThrowStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final TryStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final TypeDeclaration node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final TypeDeclarationStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final TypeLiteral node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final TypeParameter node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final VariableDeclarationExpression node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final VariableDeclarationFragment node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final VariableDeclarationStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final WhileStatement node) {

		return super.visit(node);
	}

	@Override
	public boolean visit(final WildcardType node) {

		return super.visit(node);
	}

	@Override
	public boolean visitJavaFilePath(final String javaFilePath) {

		return super.visitJavaFilePath(javaFilePath);
	}

}
