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
package padl.creator.cppfile.eclipse.helper;

import padl.cpp.kernel.impl.CPPFactoryEclipse;
import padl.kernel.ICodeLevelModel;
import padl.kernel.ICodeLevelModelCreator;
import padl.kernel.exception.CreationException;
import padl.util.ModelStatistics;

public class Chrome {
	public static void main(final String[] args) throws CreationException {
		final ModelStatistics statistics = new ModelStatistics();

		//	"C:/Temp/Chromium/chromium.r197479/home/src_tarball/tarball/chromium/src/chrome/browser/net/",
		//	"C:/Temp/Chromium/chromium.r197479/home/src_tarball/tarball/chromium/src/chrome/browser/history/",
		//	"C:/Temp/Chromium/chromium.r197479/home/src_tarball/tarball/chromium/src/chrome/browser/sync/",
		//	"C:/Temp/Chromium/chromium.r197479/home/src_tarball/tarball/chromium/src/chrome/browser/chromeos/",
		//	"C:/Temp/Chromium/chromium.r197479/home/src_tarball/tarball/chromium/src/chrome/browser/extensions/",
		//	"C:/Temp/Chromium/chromium.r197479/home/src_tarball/tarball/chromium/src/chrome/browser/",
		final ICodeLevelModelCreator creator = new padl.creator.cppfile.eclipse.CPPCreator(
				"C:/Temp/Chromium/chromium.r197479/home/src_tarball/tarball/chromium/src/chrome/browser/ui/");
		final ICodeLevelModel codeLevelModel = CPPFactoryEclipse.getInstance()
				.createCodeLevelModel(
						"Various chromium/src/chrome/browser/ subfolders");
		codeLevelModel.addModelListener(statistics);
		codeLevelModel.create(creator);

		System.out.println(codeLevelModel);
		System.out.println(statistics);
	}
}
