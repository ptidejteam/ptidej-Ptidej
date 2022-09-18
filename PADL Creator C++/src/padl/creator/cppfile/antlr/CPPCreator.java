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
package padl.creator.cppfile.antlr;

//import java.util.Iterator;

import padl.creator.cppfile.antlr.parser.CPPParser;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;

//import padl.kernel.ModelDeclarationException;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/07/13
 */
public class CPPCreator implements ICodeLevelModelCreator {
	public static char[] CPP_PACKAGE_ID = "(C++ default package)".toCharArray();

	// A list of file for the project to be parsed
	private final String[] fileName;
	// A temporary model
	//private IIdiomLevelModel idiomLevelModel;

	public CPPCreator(final String[] afileName) {
		this.fileName = afileName;
	}
	public void create(final ICodeLevelModel aCodeLevelModel) {
		// Parse each files
		for (int i = 0; i < this.fileName.length; i++) {
			CPPParser.parse(this.fileName[i], aCodeLevelModel);
		}

		//		// Copy the model from the parser to the model principal
		//		this.idiomLevelModel = CPPParser.getIdiomLevelModel();
		//
		//		final Iterator iterator =
		//			this.idiomLevelModel.listOfConstituents().iterator();
		//		while (iterator.hasNext()) {
		//			try {
		//				anIdiomLevelModel.addConstituent((IEntity) iterator.next());
		//			}
		//			catch (final ModelDeclarationException e) {
		//				e.printStackTrace();
		//			}
		//		}

		//		try {
		//			this.idiomLevelModel =
		//				(IIdiomLevelModel) CPPParser.parse(this.fileName[0]);
		//
		//			System.out.println(idiomLevelModel);
		//
		//			final Iterator iterator =
		//				this.idiomLevelModel.listOfConstituents().iterator();
		//			while (iterator.hasNext()) {
		//				try {
		//					anIdiomLevelModel.addConstituent((IEntity) iterator.next());
		//				}
		//				catch (final ModelDeclarationException e) {
		//					e.printStackTrace();
		//				}
		//			}
		//		}
		//		catch (final Exception e) {
		//			e.printStackTrace();
		//		}

	}
}
