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
package padl.creator.csharpfile.v2.parser.builder;

import padl.kernel.ICodeLevelModel;

/**
 * 
 */
public class BuilderContext {

	private final ICodeLevelModel model;
	private int blockCount;

	public BuilderContext(final ICodeLevelModel model) {
		this.model = model;
	}

	public void decrementBlockCount() {
		this.blockCount--;
	}

	public int getBlockCount() {
		return this.blockCount;
	}

	public ICodeLevelModel getModel() {
		return this.model;
	}

	public void incrementBlockCount() {
		this.blockCount++;
	}

}
