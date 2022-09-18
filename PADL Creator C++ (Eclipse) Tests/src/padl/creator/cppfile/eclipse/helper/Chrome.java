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

import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;
import padl.util.ModelStatistics;

public class Chrome {
	public static void main(final String[] args) {
		final ModelStatistics statistics = new ModelStatistics();
		final IIdiomLevelModel idiomLevelModel =
			ModelGenerator
				.generateModelFromCppFilesUsingEclipse(
					"Various chromium/src/chrome/browser/ subfolders",
					//	"C:/Temp/Chromium/chromium.r197479/home/src_tarball/tarball/chromium/src/chrome/browser/net/",
					//	"C:/Temp/Chromium/chromium.r197479/home/src_tarball/tarball/chromium/src/chrome/browser/history/",
					//	"C:/Temp/Chromium/chromium.r197479/home/src_tarball/tarball/chromium/src/chrome/browser/sync/",
					//	"C:/Temp/Chromium/chromium.r197479/home/src_tarball/tarball/chromium/src/chrome/browser/chromeos/",
					//	"C:/Temp/Chromium/chromium.r197479/home/src_tarball/tarball/chromium/src/chrome/browser/extensions/",
					"C:/Temp/Chromium/chromium.r197479/home/src_tarball/tarball/chromium/src/chrome/browser/ui/",
					//	"C:/Temp/Chromium/chromium.r197479/home/src_tarball/tarball/chromium/src/chrome/browser/",
					statistics);
		System.out.println(idiomLevelModel);
		System.out.println(statistics);
	}
}
