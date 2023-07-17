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
package padl.analysis.repository;

import padl.analysis.IAnalysis;
import padl.analysis.UnsupportedSourceModelException;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.motif.kernel.IDesignLevelModel;
import padl.motif.util.adapter.DesignLevelModelAdapter;
import padl.util.adapter.IdiomLevelModelAdapter;
import util.help.IHelpURL;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/08/29
 */
public final class DesignLevelModelAnalysis implements IAnalysis, IHelpURL {
	public IAbstractModel invoke(final IAbstractModel anAbstractModel)
			throws UnsupportedSourceModelException {

		// Yann 2009/03/07: Cloning is bad!
		// I cannot clone within this method the source model
		// because I cannot foresee what user would want to
		// use this model and this method for! So, I now only
		// create a wrapper around the source model, the cloning
		// if needed, should be done by the caller.
		// (Recall that the convert() and upgrade() methods used
		// to clone the source model!!!)
		if (anAbstractModel instanceof ICodeLevelModel) {
			final IIdiomLevelModel idiomLevelModel =
				new IdiomLevelModelAdapter((ICodeLevelModel) anAbstractModel);
			final IDesignLevelModel designLevelModel =
				new DesignLevelModelAdapter(idiomLevelModel);
			return designLevelModel;
		}
		else if (anAbstractModel instanceof IIdiomLevelModel) {
			final IDesignLevelModel designLevelModel =
				new DesignLevelModelAdapter((IIdiomLevelModel) anAbstractModel);
			return designLevelModel;
		}
		else {
			throw new UnsupportedSourceModelException();
		}
	}
	public String getName() {
		return "Design-level Model Converter";
	}
	public String getHelpURL() {
		return "http://www.ptidej.net/publications/documents/OOPSLA04.doc.pdf";
	}
}
