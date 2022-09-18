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
import padl.kernel.IConstituent;
import padl.kernel.IEntity;
import padl.kernel.IField;

/**
 *
 */
public class ClassMemberBuilderImpl extends AbstractPADLCodeBuilder {

	private IField field;

	public ClassMemberBuilderImpl(final CodeBuilder parent) {
		super(parent);
	}

	public void add(final IConstituent childElement) {
		// ignore
	}

	public IConstituent close() {
		return this.field;
	}

	public void create(final CommonTree node, final BuilderContext context) {
		final CommonTree modifier =
			this
				.findNextChildOfType(node, 0, AbstractPADLCodeBuilder.MODIFIERS);
		CommonTree equalsSign = null;
		if (modifier != null) {
			equalsSign =
				this.findNextChildOfType(
					node,
					modifier.childIndex,
					CSharpTokens.EQUALS);
		}
		else {
			equalsSign = this.findNextChildOfType(node, 0, CSharpTokens.EQUALS);
		}

		CommonTree name = null;
		if (equalsSign != null) {
			name = (CommonTree) node.getChild(equalsSign.childIndex - 1);
		}
		else {
			name = (CommonTree) node.getChild(node.getChildren().size() - 1);
		}

		final CommonTree type =
			this.findNextChildOfType(
				node,
				0,
				AbstractPADLCodeBuilder.RETURN_TYPE);
		if (type != null && name != null) {
			// Add the member
			this.field =
				this.factory.createField(
					name.getText().toCharArray(),
					name.getText().toCharArray(),
					type.getText().toCharArray(),
					Constants.CARDINALITY_ONE);
			if (modifier != null) {
				final CSharpTokens modifierType =
					CSharpTokens.findByType(modifier.getType());
				switch (modifierType) {
					case PUBLIC :
						this.field.setPublic(true);
						break;
					case PRIVATE :
						this.field.setPrivate(true);
						break;
					case PROTECTED :
						this.field.setProtected(true);
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

			// hardcoded list detection for class member fields
			if (type.getText().contains("List")) {
				this.field.setCardinality(2); // a list has a potential cardinality of more than one... 
				final CommonTree openingBrace =
					this.findNextChildOfType(
						node,
						type.getChildIndex(),
						CSharpTokens.LOWER_THEN);
				final CommonTree closingBrace =
					this.findNextChildOfType(
						node,
						type.getChildIndex(),
						CSharpTokens.GREATER_THAN);
				if (openingBrace != null && closingBrace != null) {
					final CommonTree realType =
						this.findNextChildOfType(
							node,
							openingBrace.getChildIndex(),
							new CSharpTokens[] { CSharpTokens.IDENTIFIER,
									CSharpTokens.GREATER_THAN });
					if (realType != null) {
						final IConstituent constituent =
							context.getModel().getConstituentFromName(
								realType.getText());
						if (constituent != null
								&& constituent instanceof IEntity) {
							this.field.setComment(realType.getText()); // place name in comments ;)
						}
					}
				}
			}
		}
	}
}
