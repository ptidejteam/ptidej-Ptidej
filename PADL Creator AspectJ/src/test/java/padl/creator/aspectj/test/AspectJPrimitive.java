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
package padl.creator.aspectj.test;

import junit.framework.TestCase;
import padl.aspectj.kernel.impl.AspectJFactory;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import util.io.Files;

/**
 * @author Jean-Yves Guyomarc'h
 * @author Yann
 * @since 2006/01/23
 */
public abstract class AspectJPrimitive extends TestCase {
	private ICodeLevelModel codelevelmodel = null;
	private final String jarOO = "../PADL Creator AspectJ/target/test-classes/FigureElement/FigureElementOO.jar";

	public AspectJPrimitive(final String aName) {
		super(aName);
	}

	public ICodeLevelModel getCodeLevelModel() {
		return this.codelevelmodel;
	}

	protected void setUp() throws CreationException {
		if (this.codelevelmodel == null) {
			this.codelevelmodel = AspectJFactory.getInstance().createCodeLevelModel("AspectJ Model");
			this.codelevelmodel.create(new CompleteClassFileCreator(new String[] { this.jarOO }));
		}
	}
}
