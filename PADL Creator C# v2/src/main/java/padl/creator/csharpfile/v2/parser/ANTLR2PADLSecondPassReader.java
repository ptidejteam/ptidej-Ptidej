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

import java.util.Stack;
import org.antlr.runtime.tree.CommonTree;
import org.antlr.runtime.tree.TreeVisitorAction;
import padl.creator.csharpfile.v2.parser.builder.BuilderContext;
import padl.creator.csharpfile.v2.parser.builder.CodeBuilder;
import padl.creator.csharpfile.v2.parser.builder.impl.ClassBuilderImpl;
import padl.creator.csharpfile.v2.parser.builder.impl.ClassConstructorBuilderImpl;
import padl.creator.csharpfile.v2.parser.builder.impl.ClassMemberBuilderImpl;
import padl.creator.csharpfile.v2.parser.builder.impl.InterfaceBuilderImpl;
import padl.creator.csharpfile.v2.parser.builder.impl.InterfaceMethodBuilderImpl;
import padl.creator.csharpfile.v2.parser.builder.impl.MethodBuilderImpl;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;

/**
 * <p>TreeVisitorAction implementation that converts the AST to the PADL model.
 * SecondPassReader goal is to only complement the classes and interface detected
 * in the initial pass (@see ANTLR2PADLInitialReader).
 * 
 * <p>The core of the conversion is made by instantiating the right CodeBuilder
 * for the targeted Tree node types.
 */
public class ANTLR2PADLSecondPassReader implements TreeVisitorAction {

	private ICodeLevelModel model = null;
	private final Stack<CodeBuilder> codeElements = new Stack<CodeBuilder>();
	private final BuilderContext context;

	/**
	 * Use this constructor when this reader is used to complement an already existing model.
	 * @param model the ICodeLevelModel created by the 1st iteration (@see ANTLR2PADLInitialReader)
	 */
	public ANTLR2PADLSecondPassReader(final ICodeLevelModel model) {
		this.model = model;
		this.context = new BuilderContext(model);
	}

	/**
	 * Use this constructor to start from scratch
	 * @param name the name given to the internally created ICodeLevelModel.
	 */
	public ANTLR2PADLSecondPassReader(final String name) {
		this.model = Factory.getInstance().createCodeLevelModel(name);
		this.context = new BuilderContext(this.model);
	}

	/**
	 * Returns the ICodeLevelModel created/complemented during this visit.
	 * @return the ICodeLevelModel created/complemented during this visit.
	 */
	public ICodeLevelModel getModel() {
		return this.model;
	}

	public Object post(final Object t) {
		final CommonTree node = (CommonTree) t;
		final CSharpTokens type = CSharpTokens.findByType(node.getType());
		if (type != null) {
			switch (type) {
				case INTERFACE :
					this.codeElements.pop();
					break;
				case CLASS :
					this.codeElements.pop();
					break;
				default :
					break;
			}
		}

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
						//		.println("detected interface "
						//				+ node
						//					.getFirstChildWithType(CSharpTokens.IDENTIFIER
						//						.getType()));
						builder =
							new InterfaceBuilderImpl(
								this.codeElements.isEmpty() ? null
										: this.codeElements.peek());
						builder.create(node, this.context);
						this.codeElements.push(builder);
						break;
					case CLASS :
						//	System.out
						//		.println("detected class "
						//				+ node
						//					.getFirstChildWithType(CSharpTokens.IDENTIFIER
						//						.getType()));
						builder =
							new ClassBuilderImpl(
								this.codeElements.isEmpty() ? null
										: this.codeElements.peek());
						builder.create(node, this.context);
						this.codeElements.push(builder);
						break;
					case CLASS_MEMBER :
						//	System.out
						//		.println("class member "
						//				+ node
						//					.getFirstChildWithType(CSharpTokens.IDENTIFIER
						//						.getType()));
						builder =
							new ClassMemberBuilderImpl(
								this.codeElements.isEmpty() ? null
										: this.codeElements.peek());
						builder.create(node, this.context);
						this.codeElements.peek().add(builder.close());
						break;
					case CLASS_METHOD :
						//	System.out
						//		.println("class method "
						//				+ ((CommonTree) node
						//					.getFirstChildWithType(CSharpTokens.METHOD
						//						.getType()))
						//					.getFirstChildWithType(CSharpTokens.IDENTIFIER
						//						.getType()));
						builder =
							new MethodBuilderImpl(
								this.codeElements.isEmpty() ? null
										: this.codeElements.peek());
						builder.create(node, this.context);
						this.codeElements.peek().add(builder.close());
						break;
					case INTERFACE_METHOD :
						//	System.out
						//		.println("interface method "
						//				+ node
						//					.getFirstChildWithType(CSharpTokens.IDENTIFIER
						//						.getType()));
						builder =
							new InterfaceMethodBuilderImpl(
								this.codeElements.isEmpty() ? null
										: this.codeElements.peek());
						builder.create(node, this.context);
						this.codeElements.peek().add(builder.close());
						break;
					case CONSTRUCTOR :
						//	System.out.println("class constructor for class "
						//			+ new String(this.codeElements
						//				.peek()
						//				.close()
						//				.getName()));
						builder =
							new ClassConstructorBuilderImpl(
								this.codeElements.isEmpty() ? null
										: this.codeElements.peek());
						builder.create(node, this.context);
						this.codeElements.peek().add(builder.close());
						break;

					default :
						break;
				}
			}

			return t;
		}
		catch (final CreationException e) {
			e.printStackTrace(); //To change body of catch statement use File | Settings | File Templates.
			return null;
		}
	}
}
