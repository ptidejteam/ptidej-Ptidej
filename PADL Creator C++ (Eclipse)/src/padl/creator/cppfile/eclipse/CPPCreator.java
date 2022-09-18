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
package padl.creator.cppfile.eclipse;

import padl.creator.cppfile.eclipse.misc.EclipseCPPParserCaller;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;

public class CPPCreator implements ICodeLevelModelCreator {
	private final String sourceDirectory;
	public CPPCreator(final String aSourceDirectory) {
		this.sourceDirectory = aSourceDirectory;
	}
	public void create(final ICodeLevelModel aCodeLevelModel)
			throws CreationException {

		EclipseCPPParserCaller
			.getInstance()
			.createCodeLevelModelUsingOSGiEmbedded(
				this.sourceDirectory,
				aCodeLevelModel);
	}
}
