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
package padl.creator.csharpfile.v2.parser.builder.impl;

import java.util.Iterator;
import org.antlr.runtime.tree.CommonTree;
import padl.creator.csharpfile.v2.parser.CSharpTokens;
import padl.creator.csharpfile.v2.parser.builder.BuilderContext;
import padl.creator.csharpfile.v2.parser.builder.CodeBuilder;
import padl.kernel.Constants;
import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.kernel.IConstituentOfOperation;
import padl.kernel.IEntity;
import padl.kernel.IField;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IMethod;
import padl.kernel.IMethodInvocation;
import padl.kernel.IOperation;
import padl.kernel.IParameter;
import padl.kernel.exception.CreationException;

/**
 * 
 */
public class MethodBuilderImpl extends AbstractPADLCodeBuilder implements
		CodeBuilder {

	protected IOperation method;

	public MethodBuilderImpl(final CodeBuilder parent) {
		super(parent);
	}

	public void add(final IConstituent childElement) {
		this.method.addConstituent((IConstituentOfOperation) childElement);
	}

	public IConstituent close() {
		return this.method;
	}

	public void create(final CommonTree node, final BuilderContext context)
			throws CreationException {

		final CommonTree methodRoot =
			this.findNextChildOfType(node, 0, CSharpTokens.METHOD);
		CommonTree name = null;
		if (methodRoot != null && !methodRoot.getChildren().isEmpty()) {
			name = (CommonTree) methodRoot.getChildren().get(0);
		}

		// sanity check
		if (name == null) {
			throw new CreationException("Found a method without name ??");
		}

		// create the method
		this.method =
			this.factory.createMethod(name
				.getText()
				.toLowerCase()
				.toCharArray(), name.getText().toLowerCase().toCharArray()); // Lower case to help pattern detection by PADL ;)

		final CommonTree returnType =
			this.findNextChildOfType(
				node,
				0,
				AbstractPADLCodeBuilder.RETURN_TYPE);
		if (returnType != null) {
			final CSharpTokens type =
				CSharpTokens.findByType(returnType.getType());
			switch (type) {
				case VOID :
					break;
				default :
					{
						((IMethod) this.method).setReturnType(returnType
							.getText()
							.toCharArray());
						final IConstituent constituent =
							context.getModel().getConstituentFromName(
								returnType.getText());
						if (constituent != null
								&& constituent instanceof IEntity) {
							this.method.addConstituent(this.factory
								.createMethodInvocation(
									IMethodInvocation.CLASS_INSTANCE,
									1,
									0,
									(IFirstClassEntity) constituent));
						}
					}
			}
		}

		// find out the modifiers of this method
		final CommonTree modifier =
			this
				.findNextChildOfType(node, 0, AbstractPADLCodeBuilder.MODIFIERS);
		if (modifier != null) {
			final CSharpTokens type =
				CSharpTokens.findByType(modifier.getType());
			switch (type) {
				case PUBLIC :
					this.method.setPublic(true);
					break;
				case PRIVATE :
					this.method.setPrivate(true);
					break;
				case PROTECTED :
					this.method.setProtected(true);
					break;
				case ABSTRACT :
					break;
				case BOOLEAN :
					break;
				case BYTE :
					break;
				case CHAR :
					break;
				case CLASS :
					break;
				case CLASS_MEMBER :
					break;
				case CLASS_METHOD :
					break;
				case CLOSING_BLOCK :
					break;
				case CLOSING_BRACE :
					break;
				case COLUMN :
					break;
				case CONSTRUCTOR :
					break;
				case DECIMAL :
					break;
				case DOUBLE :
					break;
				case EQUALS :
					break;
				case FLOAT :
					break;
				case GREATER_THAN :
					break;
				case IDENTIFIER :
					break;
				case INT :
					break;
				case INTERFACE :
					break;
				case INTERFACE_METHOD :
					break;
				case LONG :
					break;
				case LOWER_THEN :
					break;
				case MEMBER_DECLARATION :
					break;
				case METHOD :
					break;
				case NEW :
					break;
				case OBJECT :
					break;
				case OPENING_BLOCK :
					break;
				case OPENING_BRACE :
					break;
				case SBYTE :
					break;
				case SEMI :
					break;
				case SHORT :
					break;
				case STATIC :
					break;
				case STRING :
					break;
				case TILDE :
					break;
				case UINT :
					break;
				case ULONG :
					break;
				case USHORT :
					break;
				case USING :
					break;
				case VIRTUAL :
					break;
				case VOID :
					break;
				default :
					break;
			}
		}

		// find out if it is a virtual method
		final CommonTree virtual =
			this.findNextChildOfType(node, 0, CSharpTokens.VIRTUAL);
		if (virtual != null) {
			final CSharpTokens type =
				CSharpTokens.findByType(virtual.getType());
			switch (type) {
				case VIRTUAL :
					this.method.setAbstract(true);
					break;
				case ABSTRACT :
					break;
				case BOOLEAN :
					break;
				case BYTE :
					break;
				case CHAR :
					break;
				case CLASS :
					break;
				case CLASS_MEMBER :
					break;
				case CLASS_METHOD :
					break;
				case CLOSING_BLOCK :
					break;
				case CLOSING_BRACE :
					break;
				case COLUMN :
					break;
				case CONSTRUCTOR :
					break;
				case DECIMAL :
					break;
				case DOUBLE :
					break;
				case EQUALS :
					break;
				case FLOAT :
					break;
				case GREATER_THAN :
					break;
				case IDENTIFIER :
					break;
				case INT :
					break;
				case INTERFACE :
					break;
				case INTERFACE_METHOD :
					break;
				case LONG :
					break;
				case LOWER_THEN :
					break;
				case MEMBER_DECLARATION :
					break;
				case METHOD :
					break;
				case NEW :
					break;
				case OBJECT :
					break;
				case OPENING_BLOCK :
					break;
				case OPENING_BRACE :
					break;
				case PRIVATE :
					break;
				case PROTECTED :
					break;
				case PUBLIC :
					break;
				case SBYTE :
					break;
				case SEMI :
					break;
				case SHORT :
					break;
				case STATIC :
					break;
				case STRING :
					break;
				case TILDE :
					break;
				case UINT :
					break;
				case ULONG :
					break;
				case USHORT :
					break;
				case USING :
					break;
				case VOID :
					break;
				default :
					break;
			}
		}

		// find out if it is a static method
		final CommonTree static_ =
			this.findNextChildOfType(node, 0, CSharpTokens.STATIC);
		if (static_ != null) {
			final CSharpTokens type =
				CSharpTokens.findByType(static_.getType());
			switch (type) {
				case STATIC :
					this.method.setStatic(true);
					break;
				case ABSTRACT :
					break;
				case BOOLEAN :
					break;
				case BYTE :
					break;
				case CHAR :
					break;
				case CLASS :
					break;
				case CLASS_MEMBER :
					break;
				case CLASS_METHOD :
					break;
				case CLOSING_BLOCK :
					break;
				case CLOSING_BRACE :
					break;
				case COLUMN :
					break;
				case CONSTRUCTOR :
					break;
				case DECIMAL :
					break;
				case DOUBLE :
					break;
				case EQUALS :
					break;
				case FLOAT :
					break;
				case GREATER_THAN :
					break;
				case IDENTIFIER :
					break;
				case INT :
					break;
				case INTERFACE :
					break;
				case INTERFACE_METHOD :
					break;
				case LONG :
					break;
				case LOWER_THEN :
					break;
				case MEMBER_DECLARATION :
					break;
				case METHOD :
					break;
				case NEW :
					break;
				case OBJECT :
					break;
				case OPENING_BLOCK :
					break;
				case OPENING_BRACE :
					break;
				case PRIVATE :
					break;
				case PROTECTED :
					break;
				case PUBLIC :
					break;
				case SBYTE :
					break;
				case SEMI :
					break;
				case SHORT :
					break;
				case STRING :
					break;
				case TILDE :
					break;
				case UINT :
					break;
				case ULONG :
					break;
				case USHORT :
					break;
				case USING :
					break;
				case VIRTUAL :
					break;
				case VOID :
					break;
				default :
					break;
			}
		}

		// find out the parameters
		final CommonTree opening =
			this.findNextChildOfType(methodRoot, 0, CSharpTokens.OPENING_BRACE);
		final CommonTree closing =
			this.findNextChildOfType(
				methodRoot,
				opening.getChildIndex(),
				CSharpTokens.CLOSING_BRACE);
		if (closing.getChildIndex() - opening.getChildIndex() > 1) {
			this.findMethodParameters(
				methodRoot,
				opening.getChildIndex(),
				context);
		}

		final CodeBuilder builder = this.getParent();
		if (builder != null) {
			final IConstituent c = builder.close();
			// only check if parent builder was for a class
			if (c instanceof IClass) {
				// check if this method uses class member fields
				this.findMemberInvocations(
					methodRoot,
					closing.getChildIndex() + 1,
					(IClass) c,
					context);

				// check if this method creates classes
				this.findCreationInvocations(
					methodRoot,
					closing.getChildIndex() + 1,
					(IClass) c,
					context);
			}
		}

	}
	protected IMethodInvocation createInvocation(
		final IClass clazz,
		final BuilderContext context,
		final CommonTree memberType,
		IMethodInvocation invocation,
		final IField field) {

		if (field.getName().equals(memberType.getText())) {
			IEntity entity = null;
			if (field.getComment() != null) {
				final IConstituent constituent =
					context.getModel().getConstituentFromName(
						field.getComment());
				if (constituent != null && constituent instanceof IEntity) {
					entity = (IEntity) constituent;
				}
			}
			else {
				final IConstituent constituent =
					context.getModel().getConstituentFromName(field.getType());
				if (constituent != null && constituent instanceof IEntity) {
					entity = (IEntity) constituent;
				}
			}

			if (entity != null) {
				invocation =
					this.factory.createMethodInvocation(
						IMethodInvocation.INSTANCE_INSTANCE,
						1,
						0,
						(IFirstClassEntity) entity,
						clazz);
			}
		}
		return invocation;
	}

	protected int findCreationInvocations(
		final CommonTree node,
		final int childIndex,
		final IClass clazz,
		final BuilderContext context) {
		final CommonTree newKeyword =

		this.findNextChildOfType(node, childIndex, CSharpTokens.NEW);
		if (newKeyword == null) {
			return -1;
		}

		final CommonTree identifierFound =
			this.findNextChildOfType(
				node,
				newKeyword.getChildIndex(),
				CSharpTokens.IDENTIFIER);
		if (identifierFound != null) {
			// check if the new object is of a known class
			final IConstituent constituent =
				context.getModel().getConstituentFromName(
					identifierFound.getText());
			if (constituent != null) {
				final IMethodInvocation invocation =
					this.factory.createMethodInvocation(
						IMethodInvocation.INSTANCE_CREATION,
						1,
						0,
						(IFirstClassEntity) constituent);
				this.method.addConstituent(invocation);
			}

			this.findCreationInvocations(
				node,
				identifierFound.getChildIndex() + 1,
				clazz,
				context);
		}

		return -1;
	}

	protected int findMemberInvocations(
		final CommonTree node,
		final int childIndex,
		final IClass clazz,
		final BuilderContext context) {
		final CommonTree identifierFound =
			this.findNextChildOfType(node, childIndex, CSharpTokens.IDENTIFIER);
		if (identifierFound != null) {
			IMethodInvocation invocation = null;

			// check this class
			Iterator<?> iter = clazz.getIteratorOnConstituents(IField.class);
			while (iter.hasNext() && invocation == null) {
				final IField field = (IField) iter.next();
				invocation =
					this.createInvocation(
						clazz,
						context,
						identifierFound,
						invocation,
						field);
			}

			// check all inherited classes
			if (invocation == null) {
				final Iterator<?> iter2 =
					clazz.getIteratorOnInheritedEntities();
				while (iter2.hasNext()) {
					final Object classObj = iter2.next();
					if (classObj instanceof IClass) {
						iter = clazz.getIteratorOnConstituents(IField.class);
						while (iter.hasNext() && invocation == null) {
							final IField field = (IField) iter.next();
							if (field.isPublic() || field.isProtected()) {
								invocation =
									this.createInvocation(
										clazz,
										context,
										identifierFound,
										invocation,
										field);
							}
						}
					}
				}
			}

			if (invocation != null) {
				this.method.addConstituent(invocation);
			}

			this.findMemberInvocations(
				node,
				identifierFound.getChildIndex() + 1,
				clazz,
				context);
		}

		return -1;
	}
	protected int findMethodParameters(
		final CommonTree node,
		final int childIndex,
		final BuilderContext context) {

		final CommonTree memberType =
			this.findNextChildOfType(
				node,
				childIndex,
				AbstractPADLCodeBuilder.RETURN_TYPE);
		if (memberType != null) {
			final CommonTree memberName =
				this.findNextChildOfType(
					node,
					memberType.getChildIndex() + 1,
					CSharpTokens.IDENTIFIER);
			if (memberName != null) {
				// Yann 2013/05/10: Ghosts!
				// Create a ghost if paramete type does not exist
				final char[] targetID =
					memberType.getToken().getText().toCharArray();
				IFirstClassEntity target =
					context.getModel().getTopLevelEntityFromID(targetID);
				if (target == null) {
					target = this.factory.createGhost(targetID, targetID);
					context.getModel().addConstituent(target);
				}
				// Add the member
				final IParameter parameter =
					this.factory.createParameter(target, memberName
						.getText()
						.toCharArray(), Constants.CARDINALITY_ONE);
				this.method.addConstituent(parameter);
				if (node.getChildren().size() > memberName.getChildIndex() + 1
						&& ((CommonTree) node.getChild(memberName
							.getChildIndex() + 1)).getToken().getType() != CSharpTokens.CLOSING_BRACE
							.getType()) {
					return this.findMethodParameters(
						node,
						memberName.getChildIndex() + 1,
						context);
				}
			}
			else {
				return -1;
			}
		}
		return -1;
	}
}
