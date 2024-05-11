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
import padl.kernel.IFactory;
import padl.kernel.impl.Factory;

/**
 *
 */
public abstract class AbstractPADLCodeBuilder implements CodeBuilder {

	protected final static CSharpTokens[] PREDEFINED_TYPES =
		new CSharpTokens[] { CSharpTokens.BOOLEAN, CSharpTokens.BYTE,
				CSharpTokens.CHAR, CSharpTokens.DECIMAL, CSharpTokens.DOUBLE,
				CSharpTokens.FLOAT, CSharpTokens.INT, CSharpTokens.LONG,
				CSharpTokens.OBJECT, CSharpTokens.SBYTE, CSharpTokens.SHORT,
				CSharpTokens.STRING, CSharpTokens.UINT, CSharpTokens.ULONG,
				CSharpTokens.USHORT };
	protected final static CSharpTokens[] MODIFIERS = new CSharpTokens[] {
			CSharpTokens.PUBLIC, CSharpTokens.PRIVATE, CSharpTokens.PROTECTED };
	protected final static CSharpTokens[] RETURN_TYPE = new CSharpTokens[] {
			CSharpTokens.IDENTIFIER, CSharpTokens.VOID, CSharpTokens.BOOLEAN,
			CSharpTokens.BYTE, CSharpTokens.CHAR, CSharpTokens.DECIMAL,
			CSharpTokens.DOUBLE, CSharpTokens.FLOAT, CSharpTokens.INT,
			CSharpTokens.LONG, CSharpTokens.OBJECT, CSharpTokens.SBYTE,
			CSharpTokens.SHORT, CSharpTokens.STRING, CSharpTokens.UINT,
			CSharpTokens.ULONG, CSharpTokens.USHORT };

	protected final IFactory factory = Factory.getInstance();

	private final CodeBuilder parent;
	protected boolean isOpened = false;
	protected int blockCountOnCreate = -1;
	protected final static CSharpTokens[] IDENTIFIER_OR_CLOSING_BLOCK =
		new CSharpTokens[] { CSharpTokens.IDENTIFIER,
				CSharpTokens.CLOSING_BLOCK };

	protected AbstractPADLCodeBuilder(final CodeBuilder parent) {
		this.parent = parent;
	}

	protected CommonTree findNextChildOfType(
		final CommonTree parent,
		final int fromChildIdx,
		final CSharpTokens type) {
		return this.findNextChildOfType(
			parent,
			fromChildIdx,
			new CSharpTokens[] { type });
	}

	protected CommonTree findNextChildOfType(
		final CommonTree parent,
		final int fromChildIdx,
		final CSharpTokens[] types) {
		// sanity check
		if (parent == null || parent.getChildCount() <= 0) {
			return null;
		}

		for (final Object obj : parent.getChildren()) {
			final CommonTree child = (CommonTree) obj;
			if (fromChildIdx > -1 && child.getChildIndex() < fromChildIdx) {
				continue;
			}

			// validation
			for (final CSharpTokens type : types) {
				if (child.getType() == type.getType()) {
					return child;
				}
			}
		}

		return null;
	}
	protected CommonTree findNextSiblingOfType(
		final CommonTree current,
		final int fromChildIdx,
		final CSharpTokens type) {
		return this.findNextSiblingOfType(
			current,
			fromChildIdx,
			new CSharpTokens[] { type });
	}

	protected CommonTree findNextSiblingOfType(
		final CommonTree current,
		final int fromChildIdx,
		final CSharpTokens[] types) {
		return this.findNextSiblingOfType(current, fromChildIdx, types, null);
	}

	protected CommonTree findNextSiblingOfType(
		final CommonTree current,
		final int fromChildIdx,
		final CSharpTokens[] types,
		final CSharpTokens[] stopAtTypes) {
		// sanity check
		if (current == null || current.getChildCount() <= 0) {
			return null;
		}

		for (final Object obj : current.getChildren()) {
			final CommonTree child = (CommonTree) obj;
			if (fromChildIdx > -1 && child.getChildIndex() < fromChildIdx) {
				continue;
			}

			// hard search stop
			if (stopAtTypes != null) {
				for (final CSharpTokens type : stopAtTypes) {
					if (child.getType() == type.getType()) {
						return null;
					}
				}
			}

			// validation 
			for (final CSharpTokens type : types) {
				if (child.getType() == type.getType()) {
					return child;
				}
			}
		}

		return null;
	}
	protected CommonTree findPreviousSiblingOfType(
		final CommonTree current,
		final CSharpTokens type) {
		return this.findPreviousSiblingOfType(
			current,
			new CSharpTokens[] { type });
	}

	protected CommonTree findPreviousSiblingOfType(
		final CommonTree current,
		final CSharpTokens[] types) {
		// sanity check
		if (current == null || current.getParent() == null) {
			return null;
		}

		final CommonTree parent = (CommonTree) current.getParent();

		for (int i = current.getChildIndex(); i >= 0; i--) {
			for (final CSharpTokens type : types) {
				if (parent.getChild(i).getType() == type.getType()) {
					return (CommonTree) parent.getChild(i);
				}
			}
		}

		return null;
	}
	public CodeBuilder getParent() {
		return this.parent;
	}

	public boolean isReadyToClose(final BuilderContext context) {
		return this.blockCountOnCreate == context.getBlockCount();
	}

	public void open(final BuilderContext context) {
		if (!this.isOpened) {
			this.blockCountOnCreate = context.getBlockCount();
			this.isOpened = true;
		}
	}

}
