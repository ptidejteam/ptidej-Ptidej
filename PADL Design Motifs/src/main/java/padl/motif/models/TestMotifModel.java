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
package padl.motif.models;

import padl.motif.IDesignMotifModel;
import padl.motif.util.adapter.DesignMotifModelAdapter;

public abstract class TestMotifModel extends DesignMotifModelAdapter {
	private static final long serialVersionUID = -3938778272080321394L;
	private static final String DEFAULT_ID_STRING = "TestPatternModel";
	private static final char[] DEFAULT_ID = DEFAULT_ID_STRING.toCharArray();

	public TestMotifModel() {
		this(TestMotifModel.DEFAULT_ID);
	}
	public TestMotifModel(final char[] aName) {
		super(aName);
	}
	public final int getClassification() {
		return IDesignMotifModel.TEST_MOTIF;
	}
	public String getIntent() {
		return TestMotifModel.DEFAULT_ID_STRING;
	}
	public char[] getName() {
		return TestMotifModel.DEFAULT_ID;
	}
}
