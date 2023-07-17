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
import padl.kernel.IInterface;
import padl.kernel.exception.CreationException;

/**
 *
 */
public class InterfaceBuilderImpl extends InitialInterfaceBuilderImpl {

	public InterfaceBuilderImpl(final CodeBuilder parent) {
		super(parent);
	}

	public void create(final CommonTree node, final BuilderContext context)
			throws CreationException {

		final CommonTree interfaceNameElement =
			(CommonTree) node.getFirstChildWithType(CSharpTokens.IDENTIFIER
				.getType());
		if (interfaceNameElement == null) {
			throw new CreationException(
				"Could not find the name of the interface ?");
		}

		// find out if we know about this interface
		this.interfaze =
			(IInterface) context.getModel().getConstituentFromName(
				interfaceNameElement.getText());
		if (this.interfaze == null) {
			this.interfaze =
				this._create(interfaceNameElement.getText(), node, context);
		}

		// find out if this class inherits from another class
		final CommonTree extension =
			this.findNextSiblingOfType(node, -1, CSharpTokens.COLUMN);
		if (extension != null) {
			this.findNextInherits(
				node,
				extension.getChildIndex(),
				this.interfaze,
				context);
		}
	}

}
