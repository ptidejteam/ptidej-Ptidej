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
import padl.kernel.IConstituentOfEntity;
import padl.kernel.exception.CreationException;

/**
 * This builder creates an IClass model without any relationships, extensions, members, etc. 
 * It is meant to be used within a 1st simple scan of all the classes to analyze.
 */
public class InitialClassBuilderImpl extends AbstractClassBuilderImpl implements
		CodeBuilder {

	protected IClass clazz;

	public InitialClassBuilderImpl(final CodeBuilder parent) {
		super(parent);
	}

	protected IClass _create(
		final String className,
		final CommonTree node,
		final BuilderContext context) {

		final IClass out =
			this.factory.createClass(
				className.toCharArray(),
				className.toCharArray());

		// find out the modifiers of this class
		final CommonTree modifier =
			this.findPreviousSiblingOfType(
				node,
				AbstractPADLCodeBuilder.MODIFIERS);
		if (modifier != null) {
			final CSharpTokens modifierType =
				CSharpTokens.findByType(modifier.getType());
			switch (modifierType) {
				case PUBLIC :
					out.setPublic(true);
					break;
				case PRIVATE :
					out.setPrivate(true);
					break;
				case PROTECTED :
					out.setProtected(true);
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

		// find out if this class is abstract
		final CommonTree _abstract =
			this.findPreviousSiblingOfType(node, CSharpTokens.ABSTRACT);
		if (_abstract != null) {
			out.setAbstract(true);
		}

		return out;
	}

	public void add(final IConstituent childElement) {
		this.clazz.addConstituent((IConstituentOfEntity) childElement);
	}

	public IConstituent close() {
		return this.clazz;
	}

	public void create(final CommonTree node, final BuilderContext context)
			throws CreationException {

		final CommonTree classNameElement =
			(CommonTree) node.getFirstChildWithType(CSharpTokens.IDENTIFIER
				.getType());
		if (classNameElement == null) {
			throw new CreationException(
				"Could not find the name of the class ?");
		}

		this.clazz = this._create(classNameElement.getText(), node, context);
	}

}
