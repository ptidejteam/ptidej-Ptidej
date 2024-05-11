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

import org.antlr.runtime.tree.CommonTree;
import padl.creator.csharpfile.v2.parser.CSharpTokens;
import padl.creator.csharpfile.v2.parser.builder.BuilderContext;
import padl.creator.csharpfile.v2.parser.builder.CodeBuilder;
import padl.kernel.IClass;
import padl.kernel.IConstituent;
import padl.kernel.exception.CreationException;

/**
 *
 */
public class ClassConstructorBuilderImpl extends MethodBuilderImpl {

	public ClassConstructorBuilderImpl(final CodeBuilder parent) {
		super(parent);
	}

	public void create(final CommonTree node, final BuilderContext context)
			throws CreationException {

		// create the constructor
		this.method =
			this.factory.createConstructor(
				this.getParent().close().getName(),
				this.getParent().close().getName());

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

		// find out the parameters
		final CommonTree opening =
			this.findNextChildOfType(node, 0, CSharpTokens.OPENING_BRACE);
		final CommonTree closing =
			this.findNextChildOfType(
				node,
				opening.getChildIndex(),
				CSharpTokens.CLOSING_BRACE);
		if (closing.getChildIndex() - opening.getChildIndex() > 1) {
			this.findMethodParameters(node, opening.getChildIndex(), context);
		}

		final CodeBuilder builder = this.getParent();
		if (builder != null) {
			final IConstituent c = builder.close();
			// only check if parent builder was for a class
			if (c instanceof IClass) {
				// check if this method uses class member fields
				this.findMemberInvocations(
					node,
					closing.getChildIndex() + 1,
					(IClass) c,
					context);
			}
		}
	}

}
