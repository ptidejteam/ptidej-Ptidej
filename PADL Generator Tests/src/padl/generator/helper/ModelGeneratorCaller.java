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
package padl.generator.helper;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IInterface;
import util.io.ProxyDisk;

public class ModelGeneratorCaller {
	public static void main(final String[] args) {
		IIdiomLevelModel idiomLevelModel;
		/*final String javaFilesFolderPath =
			"F:/Snapshots/Eclipse/Test/eclipse_12-15-2009/";
		long nbBefore =FilesUtils.countJavaFiles(new File(javaFilesFolderPath));
		FilesUtils.filterDir(new File(javaFilesFolderPath));
		long nbAfter =FilesUtils.countJavaFiles(new File(javaFilesFolderPath));
		System.out.println(nbBefore+" vs "+nbAfter);*/

		//	idiomLevelModel =
		//		ModelGenerator
		//			.generateModelFromClassFilesDirectory("../../P-MARt Workspace/Xerces v2.7.0/bin/");
		//	ModelGeneratorTest.output(
		//		idiomLevelModel,
		//		"rsc/Xerces v2.7.0 (From Class Files).classes");
		//	idiomLevelModel =
		//		ModelGenerator
		//			.generateModelFromJavaFilesDirectory("../../P-MARt Workspace/Xerces v2.7.0/src/");
		//	ModelGeneratorTest.output(
		//		idiomLevelModel,
		//		"rsc/Xerces v2.7.0 (From Java Files).classes");

		//	idiomLevelModel =
		//		ModelGenerator
		//			.generateModelFromClassFilesDirectory("../../P-MARt Workspace/Eclipse v2.1.2 (JDT)/bin/");
		//	ModelGeneratorTest.output(
		//		idiomLevelModel,
		//		"rsc/Eclipse v2.1.2 (JDT) (From Class Files).classes");
		//	idiomLevelModel =
		//		ModelGenerator
		//			.generateModelFromJavaFilesDirectory("../../P-MARt Workspace/Eclipse v2.1.2 (JDT)/");
		//	ModelGeneratorTest.output(
		//		idiomLevelModel,
		//		"rsc/Eclipse v2.1.2 (JDT) (From Java Files).classes");

		//	idiomLevelModel =
		//		ModelGenerator
		//			.generateModelFromJavaFilesDirectory("../../P-MARt Workspace/Eclipse JDT v3.4/src/");
		//	ModelGeneratorTest.output(
		//		idiomLevelModel,
		//		"rsc/Eclipse JDT v3.4 (From Java Files, No Ghosts).classes");

		//D:/Software/Eclipse Snapshots/eclipse_12-15-2009/
		idiomLevelModel =
			ModelGenerator
				.generateModelFromJavaFilesDirectoryUsingEclipse("F:/Snapshots/Eclipse/Test/eclipse_12-15-2009/");
		ModelGeneratorCaller
			.output(
				idiomLevelModel,
				"rsc/Eclipse Snapshot 12-15-2009 (From Java Files, No Ghosts).classes");
	}
	public static void output(
		final IIdiomLevelModel anIdiomLevelModel,
		final String anOutputFilePath) {

		try {
			final Writer writer =
				ProxyDisk.getInstance().fileAbsoluteOutput(anOutputFilePath);
			final Iterator iteratorOnTopLevelEntities =
				anIdiomLevelModel.getIteratorOnTopLevelEntities();
			while (iteratorOnTopLevelEntities.hasNext()) {
				final IFirstClassEntity firstClassEntity =
					(IFirstClassEntity) iteratorOnTopLevelEntities.next();

				writer.write(firstClassEntity.getID());
				writer.write(',');
				if (firstClassEntity instanceof IClass) {
					writer.write("C");
				}
				else if (firstClassEntity instanceof IInterface) {
					writer.write("I");
				}
				else if (firstClassEntity instanceof IGhost) {
					// writer.write("G");
				}
				else {
					writer.write("?");
				}
				writer.write('\n');
			}

			writer.close();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
