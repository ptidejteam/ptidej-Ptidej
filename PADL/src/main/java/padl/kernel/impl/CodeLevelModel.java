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
package padl.kernel.impl;

import padl.kernel.Constants;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import util.lang.ConcreteReceiverGuard;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2005/08/05
 */
class CodeLevelModel extends AbstractLevelModel implements ICodeLevelModel,
		Cloneable {

	private static final long serialVersionUID = 1645964216980637259L;

	public CodeLevelModel() {
		this(Constants.DEFAULT_CODELEVELMODEL_ID);
	}
	public CodeLevelModel(final char[] aName) {
		super(aName);
	}
	public void create(final ICodeLevelModelCreator aCodeLevelModelCreator)
			throws CreationException {

		ConcreteReceiverGuard.getInstance().checkCallingClassName(
			"padl.generator.helper.ModelGenerator",
			"Please use the methods in \"padl.generator.helper.ModelGenerator\" to obtain code-level models.");

		aCodeLevelModelCreator.create(this);
	}
	//	public IIdiomLevelModel convert(final String aName) {
	//		final IIdiomLevelModel idiomLevelModel =
	//			Factory.getInstance().createIdiomLevelModel(aName);
	//
	//		this.clone(idiomLevelModel);
	//
	//		return idiomLevelModel;
	//	}
}
