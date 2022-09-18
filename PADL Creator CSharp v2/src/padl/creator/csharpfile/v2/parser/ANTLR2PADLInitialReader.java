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
package padl.creator.csharpfile.v2.parser;

import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.TreeVisitorAction;
import padl.creator.csharpfile.v2.parser.builder.BuilderContext;
import padl.creator.csharpfile.v2.parser.builder.CodeBuilder;
import padl.creator.csharpfile.v2.parser.builder.impl.InitialClassBuilderImpl;
import padl.creator.csharpfile.v2.parser.builder.impl.InitialInterfaceBuilderImpl;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IConstituentOfModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;

/**
 * <p>TreeVisitorAction implementation that converts the AST to the PADL model.
 * InitialReader's goal is to only create the classes and interface detected. For
 * a more detailed ICodeLevelModel, use this visitor 1st and the 
 * ANTLR2PADLSecondPassReader 2nd.
 * 
 * <p>The core of the conversion is made by instantiating the right CodeBuilder
 * for the targeted Tree node types.
 */
public class ANTLR2PADLInitialReader implements TreeVisitorAction {
	private ICodeLevelModel model = null;
	private final BuilderContext context;

	/**
	 * Use this constructor to complement an existing model
	 * @param aModel the ICodeLevelModel to complement.
	 */
	public ANTLR2PADLInitialReader(final ICodeLevelModel aModel) {
		this.model = aModel;
		this.context = new BuilderContext(this.model);
	}

	/**
	 * Use this constructor to start from scratch
	 * @param aName the name given to the internally created ICodeLevelModel.
	 */
	public ANTLR2PADLInitialReader(final String aName) {
		this(Factory.getInstance().createCodeLevelModel(aName));
	}

	/**
	 * Returns the ICodeLevelModel created/complemented during this visit.
	 * @return the ICodeLevelModel created/complemented during this visit.
	 */
	public ICodeLevelModel getModel() {
		return this.model;
	}

	public Object post(final Object t) {
		return t;
	}

	public Object pre(final Object t) {
		try {
			final CommonTree node = (CommonTree) t;

			final CSharpTokens type = CSharpTokens.findByType(node.getType());
			CodeBuilder builder = null;
			if (type != null) {
				switch (type) {
					case INTERFACE :
						//	System.out
						//		.println("found interface "
						//				+ node
						//					.getFirstChildWithType(CSharpTokens.IDENTIFIER
						//						.getType()));
						builder = new InitialInterfaceBuilderImpl(null);
						builder.create(node, this.context);
						this.model.addConstituent((IConstituentOfModel) builder
							.close());
						break;
					case CLASS :
						//	System.out
						//		.println("found class "
						//				+ node
						//					.getFirstChildWithType(CSharpTokens.IDENTIFIER
						//						.getType()));
						builder = new InitialClassBuilderImpl(null);
						builder.create(node, this.context);
						this.model.addConstituent((IConstituentOfModel) builder
							.close());
						break;
					default :
						break;
				}
			}

			return t;
		}
		catch (final CreationException e) {
			e.printStackTrace();
			return null;
		}
	}
}
