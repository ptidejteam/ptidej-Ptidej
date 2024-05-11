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
import padl.kernel.Constants;
import padl.kernel.IClass;
import padl.kernel.IParameter;
import padl.kernel.exception.CreationException;

/**
 *
 */
public class ClassBuilderImpl extends InitialClassBuilderImpl implements
		CodeBuilder {

	public ClassBuilderImpl(final CodeBuilder parent) {
		super(parent);
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

		// find out if we know about this interface
		this.clazz =
			(IClass) context.getModel().getConstituentFromName(
				classNameElement.getText());
		if (this.clazz == null) {
			this.clazz =
				this._create(classNameElement.getText(), node, context);
		}

		// find out if this class inherits from another class
		final CommonTree extension =
			this.findNextSiblingOfType(node, -1, CSharpTokens.COLUMN);
		if (extension != null) {
			this.findNextInherits(
				node,
				extension.getChildIndex(),
				this.clazz,
				context);
		}
	}

	protected int findMembers(
		final CommonTree node,
		final int childIndex,
		final BuilderContext context) {

		final CommonTree memberType =
			this.findNextSiblingOfType(
				node,
				childIndex,
				AbstractPADLCodeBuilder.PREDEFINED_TYPES,
				AbstractPADLCodeBuilder.MODIFIERS);
		if (memberType != null) {
			final CommonTree memberName =
				this.findNextSiblingOfType(
					node,
					memberType.getChildIndex(),
					CSharpTokens.IDENTIFIER);
			if (memberName != null) {
				// Add the member
				final IParameter parameter =
					this.factory.createParameter(
						context.getModel().getTopLevelEntityFromID(
							memberType.getToken().getText().toCharArray()),
						memberName.getText().toCharArray(),
						Constants.CARDINALITY_ONE);
				this.clazz.addConstituent(parameter);
				return this.findMembers(
					node,
					memberName.getChildIndex() + 1,
					context);
			}
			else {
				return -1;
			}
		}
		return -1;
	}

}
