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
package squad.util;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IEntity;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.ProxyDisk;

public class ListofClassesGenerator {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//		 ListofClassesGenerator.generateListofClasses(
		//		 "D:/Software/P-MARt Workspace/ArgoUML v.0.19.8/bin/",
		//		 "ArgoUML v0.19.8", "rsc/misc/ListOfClassesForArgoUML.txt");

		//*		 ListofClassesGenerator.generateListofClasses(
		//		 "D:/Software/P-MARt Workspace/Azureus v2.1.0.0/bin/",
		//		 "Azureus v2.1.0.0", "rsc/misc/ListOfClassesForAzureus.txt");

		//		 ListofClassesGenerator.generateListofClasses(
		//		 "D:/Software/P-MARt Workspace/org.eclipse.jdt.core_2.1.2/jdtcore.jar",
		//		 "Eclipse v2.1.2 (JDT)", "rsc/misc/ListOfClassesForJDT.txt");
		//
		//		ListofClassesGenerator.generateListofClasses(
		//				"D:/Software/P-MARt Workspace/JHotDraw v5.1/bin/",
		//				"JHotDraw v5.1", "rsc/misc/ListOfClassesForJHotDraw.txt");
		//		
		/*ListofClassesGenerator.generateListofClasses(
			"D:/Software/P-MARt Workspace/xalan v2.7.0/bin/",
			"Xalan v2.7.0",
			"rsc/misc/ListOfClassesForXalanJ.txt");*/

		//		 ListofClassesGenerator.generateListofClasses(
		//		 "D:/Software/P-MARt Workspace/xerces v.1.4.4/bin/",
		//		 "Xerces v1.4.4", "rsc/ListOfClassesForXercesJ.txt");

		//Generate classes for JHotDraw 		
		/*	String dirName1 = "D:/Software/P-MARt Workspace/JHotDraw v.7.1/bin/";
			
			ListofClassesGenerator.generateListofClasses(
				dirName1,
				"JHotDraw v7.1",
				"FinalResults/Azureus Classes/Classes v7.1 .csv");*/
		//Generate classes for  Azureus	
		String dirName2 = "D:/Software/P-MARt Workspace/Azureus v.4.2.0.2.jar";

		ListofClassesGenerator.generateListofClassesFromJar(
			new String[] { dirName2 },
			"Azureus v4.2.0.2",
			"FinalResults/Azureus Classes/Classes v4.2.0.2 .csv");

	}

	private static void generateListofClasses(
		final String aPath,
		final String aName,
		final String anOutputFile) {
		try {
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.create(new CompleteClassFileCreator(
				new String[] { aPath },
				true));
			Iterator<?> iter = codeLevelModel.getIteratorOnTopLevelEntities();

			final Writer out =
				ProxyDisk.getInstance().fileTempOutput(anOutputFile, true);

			//BufferedWriter out = new BufferedWriter(fw);

			while (iter.hasNext()) {

				IEntity anElement = (IEntity) iter.next();

				out.write(anElement.getDisplayName());
				out.flush();
				out.write('\n');
				out.flush();

			}

			out.close();

		}
		catch (final CreationException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Generate list of classes from jar
	 * 
	 */
	private static void generateListofClassesFromJar(
		final String[] someJARFiles,
		final String aName,
		final String anOutputFile) {
		try {

			ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(aName);
			codeLevelModel.create(new CompleteClassFileCreator(
				someJARFiles,
				true));

			System.out.println("Construction of the model completed........");
			Iterator<?> iter = codeLevelModel.getIteratorOnTopLevelEntities();

			final Writer out =
				ProxyDisk.getInstance().fileTempOutput(anOutputFile, true);

			//BufferedWriter out = new BufferedWriter(fw);

			while (iter.hasNext()) {

				IEntity anElement = (IEntity) iter.next();

				out.write(anElement.getDisplayName());
				out.flush();
				out.write('\n');
				out.flush();

			}

			out.close();

		}
		catch (final CreationException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}

	}

}
