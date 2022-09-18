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

import java.util.Collection;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Vector;
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
import org.eclipse.jdt.core.dom.IVariableBinding;
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
import parser.defuse.CharComparator;
import parser.defuse.DefinitionAndUseSymboleTable;
import parser.defuse.Entity;
import parser.defuse.ModificationAndUseStatement;
import parser.defuse.entities.ClassEntity;
import parser.defuse.entities.ConstructorEntity;
import parser.defuse.entities.FieldEntity;
import parser.defuse.entities.InterfaceEntity;
import parser.defuse.entities.LocalVariableEntity;
import parser.defuse.entities.MethodEntity;
import parser.defuse.entities.MethodOrConstrucotorParameterEntity;
import parser.properties.EnclosingTypeProperty;
import parser.properties.ExpressionProperty;
import parser.properties.IProperty;
import parser.wrapper.ExtendedASTVisitor;
import parser.wrapper.NamedCompilationUnit;

public class DefinitionUse extends ExtendedASTVisitor {

	private final IProperty enclosingTypeProperty;
	private Stack<String> stackOfEnclosingTypesName = null;
	private Stack<ASTNode> stackOfEnclosingTypes = null;

	private final IProperty expressionProperty;
	private ASTNode CurrentExpression = null;

	private String javaFilePath;
	private CompilationUnit compilationUnit;

	// the stack will be populated by the type declaration (FD,LVD) and variable type and modifier of
	// nodes: FieldDeclaration, VariableDeclarationStatement and VariableDeclarationExpression
	// this stack will be used after in VariableDeclarationFragment
	private final Stack<char[]> stackOfFieldAndLocalVariableInfo;

	// the key is the lineNumber and the value set contains all entities defined at the lineNumber
	private final TreeMap<char[], Vector<Entity>> lineEntitiesMapping;

	public DefinitionUse() {
		this.enclosingTypeProperty = new EnclosingTypeProperty();
		this.stackOfEnclosingTypesName = new Stack<String>();
		this.stackOfEnclosingTypes = new Stack<ASTNode>();
		this.stackOfFieldAndLocalVariableInfo = new Stack<char[]>();
		this.lineEntitiesMapping =
			new TreeMap<char[], Vector<Entity>>(new CharComparator());
		this.CurrentExpression = null;
		this.expressionProperty = new ExpressionProperty();
	}

	private Entity CreateConcreteEntity(
		final String name,
		final String signature,
		final String declarationType,
		final String lineNumber,
		final String parent,
		final String key) {
		Entity e = null;
		// Local Variable
		if (declarationType.equalsIgnoreCase("LVD")) {
			e =
				new LocalVariableEntity(
					name.toString().toCharArray(),
					signature.toCharArray(),
					declarationType.toCharArray(),
					lineNumber.toCharArray(),
					parent.toCharArray(),
					key.toCharArray());
		}

		// class
		else if (declarationType.equalsIgnoreCase("CD")) {
			e =
				new ClassEntity(
					name.toCharArray().clone(),
					signature.toCharArray(),
					declarationType.toCharArray(),
					lineNumber.toCharArray(),
					parent.toCharArray(),
					key.toCharArray());
		}
		// interface
		else if (declarationType.equalsIgnoreCase("ID")) {
			e =
				new InterfaceEntity(
					name.toCharArray(),
					signature.toCharArray(),
					declarationType.toCharArray(),
					lineNumber.toCharArray(),
					parent.toCharArray(),
					key.toCharArray());
		}
		// Constructor
		else if (declarationType.equalsIgnoreCase("COD")) {
			e =
				new ConstructorEntity(
					name.toCharArray(),
					signature.toCharArray(),
					declarationType.toCharArray(),
					lineNumber.toCharArray(),
					parent.toCharArray(),
					key.toCharArray());
		}
		// Method
		else if (declarationType.equalsIgnoreCase("MD")) {
			e =
				new MethodEntity(
					name.toString().toCharArray(),
					signature.toCharArray(),
					declarationType.toCharArray(),
					lineNumber.toCharArray(),
					parent.toCharArray(),
					key.toCharArray());
		}
		// Field 
		else if (declarationType.equalsIgnoreCase("FD")) {
			e =
				new FieldEntity(
					name.toString().toCharArray(),
					signature.toCharArray(),
					declarationType.toCharArray(),
					lineNumber.toCharArray(),
					parent.toCharArray(),
					key.toCharArray());
		}
		// Method or Class Parameter 
		else if (declarationType.equalsIgnoreCase("PD")) {
			e =
				new MethodOrConstrucotorParameterEntity(
					name.toString().toCharArray(),
					signature.toCharArray(),
					declarationType.toCharArray(),
					lineNumber.toCharArray(),
					parent.toCharArray(),
					key.toCharArray());
		}

		return e;

	}

	@Override
	public void endVisit(final AnnotationTypeDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final AnnotationTypeMemberDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final AnonymousClassDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ArrayAccess node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ArrayCreation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ArrayInitializer node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ArrayType node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final AssertStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final Assignment node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final Block node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final BlockComment node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final BooleanLiteral node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final BreakStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final CastExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final CatchClause node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final CharacterLiteral node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ClassInstanceCreation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final CompilationUnit node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ConditionalExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ConstructorInvocation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ContinueStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final DoStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final EmptyStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final EnhancedForStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final EnumConstantDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final EnumDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ExpressionStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final FieldAccess node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final FieldDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();
			this.stackOfFieldAndLocalVariableInfo.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ForStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final IfStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ImportDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final InfixExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final Initializer node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final InstanceofExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final Javadoc node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final LabeledStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final LineComment node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final MarkerAnnotation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final MemberRef node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final MemberValuePair node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final MethodDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final MethodInvocation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final MethodRef node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final MethodRefParameter node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final Modifier node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final NamedCompilationUnit aNamedCompilationUnit) {
		//this.print(this.lineEntitiesMapping);
		this.stackOfEnclosingTypesName.pop();
		DefinitionAndUseSymboleTable
			.getInstance()
			.getListOfDefinitionAndUse()
			.put(
				this.javaFilePath.toCharArray(),
				new TreeMap<char[], Vector<Entity>>(this.lineEntitiesMapping));
		this.lineEntitiesMapping.clear();
		this.CurrentExpression = null;
		super.endVisit(aNamedCompilationUnit);
	}
	@Override
	public void endVisit(final NormalAnnotation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final NullLiteral node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final NumberLiteral node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final PackageDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ParameterizedType node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ParenthesizedExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final PostfixExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final PrefixExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final PrimitiveType node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final QualifiedName node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final QualifiedType node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ReturnStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final SimpleName node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final SimpleType node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final SingleMemberAnnotation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final SingleVariableDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final StringLiteral node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final SuperConstructorInvocation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final SuperFieldAccess node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final SuperMethodInvocation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final SwitchCase node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final SwitchStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final SynchronizedStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final TagElement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final TextElement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ThisExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final ThrowStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final TryStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final TypeDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final TypeDeclarationStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final TypeLiteral node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final TypeParameter node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final VariableDeclarationExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();
			this.stackOfFieldAndLocalVariableInfo.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final VariableDeclarationFragment node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final VariableDeclarationStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();
			this.stackOfFieldAndLocalVariableInfo.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final WhileStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	@Override
	public void endVisit(final WildcardType node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.pop();
			this.stackOfEnclosingTypes.pop();

		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = null;
		}
		super.endVisit(node);
	}

	private String getParent() {
		String parentName = "";
		for (final String name : this.stackOfEnclosingTypesName) {
			if (!name.equalsIgnoreCase("")) {
				if (parentName.length() == 0) {
					parentName = name;
				}
				else {
					parentName = parentName + "." + name;
				}
			}
		}
		return parentName.substring(0, parentName.lastIndexOf("."));
	}

	private String getScope() {
		String scopeName = "";
		for (final ASTNode node : this.stackOfEnclosingTypes) {
			if (node instanceof TypeDeclaration) {
				scopeName =
					scopeName + "."
							+ ((TypeDeclaration) node).getName().toString();
			}
			if (node instanceof MethodDeclaration) {
				scopeName =
					scopeName + "."
							+ ((MethodDeclaration) node).getName().toString();
			}
			if (node instanceof Initializer) {
				final String lineNumber =
					Integer.toString(this.compilationUnit.getLineNumber(node
						.getStartPosition()));
				scopeName = scopeName + ".staticBlock " + lineNumber;
			}
			if (node instanceof FieldDeclaration
					|| node instanceof VariableDeclarationStatement
					|| node instanceof VariableDeclarationExpression) {
				final int currentIndex =
					this.stackOfEnclosingTypes.indexOf(node);
				final ASTNode nextNode =
					this.stackOfEnclosingTypes.elementAt(currentIndex + 1);
				if (nextNode instanceof org.eclipse.jdt.core.dom.VariableDeclarationFragment) {
					scopeName =
						scopeName
								+ "."
								+ ((VariableDeclarationFragment) nextNode)
									.getName()
									.toString();
				}
				if (node instanceof SingleVariableDeclaration) {
					scopeName =
						scopeName
								+ "."
								+ ((SingleVariableDeclaration) nextNode)
									.getName()
									.toString();
				}
			}

		}
		return scopeName.replaceFirst(".", "");
	}

	private void populateDefinitionAndUse(final Entity entity) {
		Vector<Entity> temp =
			this.lineEntitiesMapping.get(entity.getLineNumber());
		if (temp == null) {
			temp = new Vector<Entity>();
		}
		temp.add(entity);
		this.lineEntitiesMapping.put(entity.getLineNumber(), temp);
	}

	//	private void print(final TreeMap<char[], Vector<Entity>> aHashTable) {
	//		if (aHashTable.size() == 0) {
	//			System.out.println("Empty hash map");
	//		}
	//		else {
	//			for (final char[] key : aHashTable.keySet()) {
	//				System.out.println("lineNumber: " + new String(key));
	//				for (final Entity e : this.lineEntitiesMapping.get(key)) {
	//					System.out.println("     "
	//							+ new String(e.getDeclarationType()) + "     "
	//							+ new String(e.getName()) + "     "
	//							+ new String(e.getParent()));
	//				}
	//				System.out.println();
	//			}
	//		}
	//	}

	private void setModificationUse(
		final SimpleName asimpleName,
		final String id,
		final ASTNode aExpression,
		final String expressionLineNumber) {

		final Collection<Vector<Entity>> collectionOfVectorOfEntities =
			this.lineEntitiesMapping.values();
		Entity lastEntity = null;
		Entity firstEntity = null;
		Entity resultEntity = null;
		for (final Vector<Entity> VectorOfEntities : collectionOfVectorOfEntities) {
			for (final Entity entity : VectorOfEntities) {
				if (new String(entity.getID()).equalsIgnoreCase(id)
						&& new String(entity.getName())
							.equalsIgnoreCase(asimpleName.getIdentifier())) {
					if (lastEntity == null) {
						lastEntity = entity;
						firstEntity = entity;
					}
					if (Integer.valueOf(new String(entity.getLineNumber())) > Integer
						.valueOf(new String(lastEntity.getLineNumber()))) {
						lastEntity = entity;
					}
					if (Integer.valueOf(new String(entity.getLineNumber())) < Integer
						.valueOf(new String(lastEntity.getLineNumber()))) {
						firstEntity = entity;
					}
				}
			}
		}

		if (aExpression.getNodeType() == ASTNode.FIELD_ACCESS) {
			System.out.println("++++++++++++++++++++++++++++++");
			resultEntity = firstEntity;
		}
		else {
			if (lastEntity != null
					&& new String(lastEntity.getParent()).equalsIgnoreCase(this
						.getScope())) {
				resultEntity = lastEntity;
			}
			else {
				resultEntity = firstEntity;
			}
		}
		if (resultEntity != null) {
			final ModificationAndUseStatement md =
				new ModificationAndUseStatement(aExpression
					.toString()
					.toCharArray(), expressionLineNumber.toCharArray());
			resultEntity.getModificationList().add(md);

			//			System.out.println("will add to the modification list:");
			//			System.out.println("\t\t entity key: "
			//					+ new String(resultEntity.getID()) + "   new id: " + id);
			//			System.out.println("\t\t entity name: "
			//					+ new String(resultEntity.getName())
			//					+ "     defined on line: "
			//					+ new String(resultEntity.getLineNumber())
			//					+ "   new name: " + asimpleName.getIdentifier()
			//					+ "    SCOPE: " + this.getScope());
			//			System.out.println("\t\t used in expression: "
			//					+ aExpression.toString() + "     at line: "
			//					+ expressionLineNumber);
			//			System.out.println("\t\t expression type: "
			//					+ ASTNode.nodeClassForType(aExpression.getNodeType()));
			//			System.out.println("\n\n");
		}

	}

	@Override
	public boolean visit(final AnnotationTypeDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final AnnotationTypeMemberDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final AnonymousClassDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ArrayAccess node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ArrayCreation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}

		return super.visit(node);
	}

	@Override
	public boolean visit(final ArrayInitializer node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ArrayType node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final AssertStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final Assignment node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final Block node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final BlockComment node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final BooleanLiteral node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final BreakStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final CastExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final CatchClause node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final CharacterLiteral node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ClassInstanceCreation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getType().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final CompilationUnit node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ConditionalExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ConstructorInvocation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ContinueStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final DoStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final EmptyStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final EnhancedForStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final EnumConstantDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final EnumDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ExpressionStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final FieldAccess node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	// child: variableDeclarationFragments 
	public boolean visit(final FieldDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		String modifier;
		if (java.lang.reflect.Modifier.toString(node.getModifiers()) == "") {
			modifier = "protected";
		}
		else {
			modifier = java.lang.reflect.Modifier.toString(node.getModifiers());
		}
		this.stackOfFieldAndLocalVariableInfo.push(("FD;"
				+ node.getType().toString() + ";" + modifier).toCharArray());

		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ForStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final IfStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ImportDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final InfixExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final Initializer node) {
		final String lineNumber =
			Integer.toString(this.compilationUnit.getLineNumber(node
				.getStartPosition()));
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("staticBlock " + lineNumber);
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final InstanceofExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final Javadoc node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final LabeledStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final LineComment node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final MarkerAnnotation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final MemberRef node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final MemberValuePair node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final MethodDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		final String lineNumber =
			Integer.toString(this.compilationUnit.getLineNumber(node
				.getName()
				.getStartPosition()));
		// signature will contain modifier, return type, and parameter list
		String signature = "";
		if (java.lang.reflect.Modifier.toString(node.getModifiers()) == "") {
			signature = "protected" + ";";
		}
		else {
			signature =
				java.lang.reflect.Modifier.toString(node.getModifiers()) + ";";
		}
		String declarationType = "COD";
		if (!node.isConstructor()) {
			signature = signature + node.getReturnType2() + ";";
			declarationType = "MD";
		}
		signature = signature + node.parameters().toString() + ";";
		// Create Method or Constructor Entity and populate the DefinitionAndUSe data structure
		final Entity e =
			this.CreateConcreteEntity(
				node.getName().toString(),
				signature,
				declarationType,
				lineNumber,
				this.getParent(),
				"");
		this.populateDefinitionAndUse(e);

		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final MethodInvocation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final MethodRef node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final MethodRefParameter node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final Modifier node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final NamedCompilationUnit aNamedCompilationUnit) {
		this.javaFilePath = aNamedCompilationUnit.getJavaFilePath();
		this.compilationUnit = aNamedCompilationUnit.getCompilationUnit();
		this.stackOfEnclosingTypesName.push(this.javaFilePath);
		System.out.println("Parsing file: \t" + this.javaFilePath);
		final boolean superVisit = super.visit(aNamedCompilationUnit);
		return superVisit;
	}

	@Override
	public boolean visit(final NormalAnnotation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final NullLiteral node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final NumberLiteral node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final PackageDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ParameterizedType node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ParenthesizedExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final PostfixExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final PrefixExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final PrimitiveType node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final QualifiedName node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}

		return super.visit(node);
	}

	@Override
	public boolean visit(final QualifiedType node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ReturnStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final SimpleName node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		IVariableBinding ib = null;
		if (node.resolveBinding() instanceof IVariableBinding) {
			ib = (IVariableBinding) node.resolveBinding();
		}
		String id = "";
		if (ib != null) {
			id = String.valueOf(ib.getVariableId());
			final ASTNode ex = this.CurrentExpression;
			if (ex != null) {
				final String lineNumber =
					Integer.toString(this.compilationUnit.getLineNumber(ex
						.getStartPosition()));
				this.setModificationUse(node, id, ex, lineNumber);
			}
		}

		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final SimpleType node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final SingleMemberAnnotation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	// parameter of method and constructor are of type SingleVariableDeclaration
	@Override
	public boolean visit(final SingleVariableDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (node.getParent().getNodeType() == ASTNode.METHOD_DECLARATION) {
			final String lineNumber =
				Integer.toString(this.compilationUnit.getLineNumber(node
					.getName()
					.getStartPosition()));
			// signature will contain modifier
			String signature = "";
			if (java.lang.reflect.Modifier.toString(node.getModifiers()) == "") {
				signature = "NA" + ";";
			}
			else {
				signature =
					java.lang.reflect.Modifier.toString(node.getModifiers())
							+ ";";
			}
			signature = signature + node.getType();
			final String declarationType = "PD";
			// Create Parameter Entity and populate the DefinitionAndUSe data structure
			final Entity e =
				this.CreateConcreteEntity(
					node.getName().toString(),
					signature,
					declarationType,
					lineNumber,
					this.getParent(),
					String.valueOf(node.resolveBinding().getVariableId()));
			this.populateDefinitionAndUse(e);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);

	}

	@Override
	public boolean visit(final StringLiteral node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final SuperConstructorInvocation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final SuperFieldAccess node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final SuperMethodInvocation node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final SwitchCase node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final SwitchStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final SynchronizedStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final TagElement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final TextElement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ThisExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final ThrowStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final TryStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final TypeDeclaration node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		final String lineNumber =
			Integer.toString(this.compilationUnit.getLineNumber(node
				.getName()
				.getStartPosition()));
		// for class or constructor the signature contains: typeParameters+modifier+superTypes
		String signature = "";
		if (node.typeParameters().size() > 0) {
			signature = node.typeParameters().toString() + ";";
		}
		if (java.lang.reflect.Modifier.toString(node.getModifiers()) == "") {
			signature = signature + "protected" + ";";
		}
		else {
			signature =
				signature
						+ java.lang.reflect.Modifier.toString(node
							.getModifiers()) + ";";
		}
		if (node.getSuperclassType() != null) {
			signature =
				signature + " extends " + node.getSuperclassType().toString()
						+ ";";
		}
		if (node.superInterfaceTypes().size() != 0) {
			signature =
				signature + " implements "
						+ node.superInterfaceTypes().toString() + ";";
		}
		String declarationType = "";
		if (node.isInterface()) {
			declarationType = "ID";
		}
		else {
			declarationType = "CD";
		}

		// Create Class or Interface Entity and populate the DefinitionAndUSe data structure
		final Entity e =
			this.CreateConcreteEntity(
				node.getName().toString(),
				signature,
				declarationType,
				lineNumber,
				this.getParent(),
				"");
		this.populateDefinitionAndUse(e);

		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final TypeDeclarationStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final TypeLiteral node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final TypeParameter node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	// child: variableDeclarationFragments 
	public boolean visit(final VariableDeclarationExpression node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		String modifier;
		if (java.lang.reflect.Modifier.toString(node.getModifiers()).length() > 0) {
			modifier = java.lang.reflect.Modifier.toString(node.getModifiers());
		}
		else {
			modifier = "NA"; // Not Applicable
		}
		this.stackOfFieldAndLocalVariableInfo.push(("LVD;"
				+ node.getType().toString() + ";" + modifier).toCharArray());

		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	// parent node: FieldDeclaration,VariableDeclarationStatement,VariableDeclarationExpression
	public boolean visit(final VariableDeclarationFragment node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push(node.getName().toString());
			this.stackOfEnclosingTypes.push(node);
		}
		final String lineNumber =
			Integer.toString(this.compilationUnit.getLineNumber(node
				.getName()
				.getStartPosition()));
		String info = "";
		if (this.stackOfFieldAndLocalVariableInfo.size() > 0) {
			info = new String(this.stackOfFieldAndLocalVariableInfo.peek());
		}

		String type = "";
		String declarationType = "";
		String modifier = "";
		if (info.length() > 0) {
			final String[] infos = info.split(";");
			// declarationType: FD or LVD
			declarationType = infos[0];
			type = infos[1];
			modifier = infos[2];
		}

		final String signature = modifier + ";" + type;
		//Create Field or LocalVAriableDeclaration Entity and populate the DefinitionAndUSe data structure
		final Entity e =
			this.CreateConcreteEntity(
				node.getName().toString(),
				signature,
				declarationType,
				lineNumber,
				this.getParent(),
				String.valueOf(node.resolveBinding().getVariableId()));
		this.populateDefinitionAndUse(e);

		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	// child: variableDeclarationFragments 
	public boolean visit(final VariableDeclarationStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		String modifier;
		if (java.lang.reflect.Modifier.toString(node.getModifiers()).length() > 0) {
			modifier = java.lang.reflect.Modifier.toString(node.getModifiers());
		}
		else {
			modifier = "NA"; // Not Applicable
		}
		this.stackOfFieldAndLocalVariableInfo.push(("LVD;"
				+ node.getType().toString() + ";" + modifier).toCharArray());

		if (this.expressionProperty.isInterestingNode(node)
				&& node.toString().contains("=")) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final WhileStatement node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(final WildcardType node) {
		if (this.enclosingTypeProperty.isInterestingNode(node)) {
			this.stackOfEnclosingTypesName.push("");
			this.stackOfEnclosingTypes.push(node);
		}
		if (this.expressionProperty.isInterestingNode(node)) {
			this.CurrentExpression = node;
		}
		return super.visit(node);
	}
}
