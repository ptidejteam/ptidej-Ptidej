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
import padl.analysis.repository.aacrelationships.AACBuilder;
import padl.analysis.repository.aacrelationships.AACRemover;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.util.adapter.IdiomLevelModelAdapter;
import util.help.IHelpURL;
import util.io.ProxyConsole;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/05/20
 */
public final class AACRelationshipsAnalysis implements IAnalysis, IHelpURL {
	private final boolean removeOldRelationships;

	public AACRelationshipsAnalysis() {
		this.removeOldRelationships = true;
	}
	public AACRelationshipsAnalysis(final boolean removeOldRelationships) {
		this.removeOldRelationships = removeOldRelationships;
	}
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
		//	final IIdiomLevelModel idiomLevelModel =
		//		((ICodeLevelModel) anAbstractModel).convert(anAbstractModel
		//			.getName());
		if (anAbstractModel instanceof ICodeLevelModel) {
			try {
				// Yann 2013/09/26: Do not change original...
				// I clone the original model to make sure that
				// I do not modify it accidentally...
				final ICodeLevelModel clonedModel =
					(ICodeLevelModel) anAbstractModel.clone();
				final IIdiomLevelModel idiomLevelModel =
					new IdiomLevelModelAdapter(clonedModel);
				if (this.removeOldRelationships) {
					idiomLevelModel.walk(new AACRemover());
				}
				idiomLevelModel.walk(new AACBuilder());
				return idiomLevelModel;
			}
			catch (final CloneNotSupportedException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
				return null;
			}
		}
		else {
			throw new UnsupportedSourceModelException();
		}
	}
	public String getName() {
		return "AAC Relationships";
	}
	public String getHelpURL() {
		return "http://www.ptidej.net/publications/documents/OOPSLA04.doc.pdf";
	}
}
