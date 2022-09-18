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
import padl.kernel.IEntity;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IInterface;
import padl.kernel.IInterfaceActor;

/**
 *
 */
public abstract class AbstractClassBuilderImpl extends AbstractPADLCodeBuilder {

	protected AbstractClassBuilderImpl(final CodeBuilder parent) {
		super(parent);
	}

	/**
	 * Simple recursive method that grabs all the following IDENTIFIER node until we get a OPENING_BLOCK.
	 * @param node the parent node of the Class
	 * @param childIndex the current child index to evaluate
	 * @param context
	 * @return the next child index to evaluate
	 */
	protected int findNextInherits(
		final CommonTree node,
		final int childIndex,
		final IEntity entity,
		final BuilderContext context) {

		final CommonTree extend_ =
			this.findNextSiblingOfType(
				node,
				childIndex,
				CSharpTokens.IDENTIFIER);
		if (extend_ != null
				&& extend_.getType() == CSharpTokens.IDENTIFIER.getType()) {
			// figure out if this class is known to the current ICodeLevelModel
			IConstituent constituent =
				context.getModel().getConstituentFromName(extend_.getText());
			if (constituent == null) {
				// we need to create a GhostClass
				constituent =
					this.factory.createGhost(
						extend_.getText().toCharArray(),
						extend_.getText().toCharArray());
				((IGhost) entity)
					.addInheritedEntity((IFirstClassEntity) constituent);
			}
			else {
				if (entity instanceof IClass
						&& constituent instanceof IInterface) {
					((IClass) entity)
						.addImplementedInterface((IInterfaceActor) constituent);
				}
				else {
					((IClass) entity)
						.addInheritedEntity((IFirstClassEntity) constituent);
				}
			}

			return this.findNextInherits(
				node,
				extend_.getChildIndex() + 1,
				entity,
				context);
		}
		else {
			return -1;
		}
	}
}
