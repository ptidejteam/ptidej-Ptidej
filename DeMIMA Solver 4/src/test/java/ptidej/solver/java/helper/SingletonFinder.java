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
package ptidej.solver.java.helper;

import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Iterator;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.IMethod;
import padl.kernel.IParameter;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.util.ModelStatistics;
import util.io.NullWriter;
import util.io.ProxyConsole;
import util.io.ProxyDisk;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/02/21
 */
public class SingletonFinder {
	public static void main(final String[] args) {
		final String[] paths =
			new String[] {
			//	"../../P-MARt Workspace/ArgoUML v0.19.8/bin/",
			//	"../../P-MARt Workspace/Azureus v2.1.0.0/bin/",
			//	"../../P-MARt Workspace/Eclipse v2.1.2 (JDT)/bin/",
			//	"../../P-MARt Workspace/JHotDraw v5.4b2/bin/",
			//	"../../P-MARt Workspace/Xalan v2.7.0/bin/",
			//	"../../P-MARt Workspace/Xerces v1.4.4/bin/" 
			//	"../../P-MARt Workspace/JHotDraw v5.1/bin/", 
			//					"../../P-MARt Workspace/QuickUML 2001/bin/",
			//					"../../P-MARt Workspace/Lexi v0.1.1 alpha/bin/",
			//					"../../P-MARt Workspace/JRefactory v2.6.24/bin/",
			//					"../../P-MARt Workspace/JUnit v3.7/bin/",
			//					"../../P-MARt Workspace/JHotDraw v5.1/bin/",
			//					"../../P-MARt Workspace/MapperXML v1.9.7/bin/",
			//					"../../P-MARt Workspace/Nutch v0.4/bin/",
			//					"../../P-MARt Workspace/Software architecture design patterns in Java/bin/",
			//					"../../P-MARt Workspace/DrJava v20020619/bin/",
			//					"../../P-MARt Workspace/DrJava v20020703/bin/",
			//					"../../P-MARt Workspace/DrJava v20030203/bin/",
			//					"../../P-MARt Workspace/DrJava v20040326/bin/" ,
			"/Polymtl/Data/checkstyle_binaries_jars/checkstyle-2.2/checkstyle-2.2.jar" };
		final String[] names = new String[] {
		//	"ArgoUML v0.19.8", "Azureus v2.1.0.0",
		//	"Eclipse v2.1.2 (JDT)", "JHotDraw v5.4b2", "Xalan v2.7.0",
		//	"Xerces v1.4.4", 
		//	"JHotDraw v5.1" 
		//					"QuickUML 2001", "Lexi v0.1.1 alpha", "JRefactory v2.6.24",
		//					"JUnit v3.7", "JHotDraw v5.1", "MapperXML v1.9.7",
		//					"Nutch v0.4",
		//					"Software architecture design patterns in Java",
		//					"DrJava v20020619", "DrJava v20020703", "DrJava v20030203",
		//					"DrJava v20040326",
		"checkstyle-2.2" };

		ProxyConsole.getInstance().setDebugOutput(new NullWriter());
		ProxyConsole.getInstance().setNormalOutput(new NullWriter());
		ProxyConsole.getInstance().setErrorOutput(new NullWriter());

		for (int i = 0; i < paths.length; i++) {
			final String path = paths[i];
			final String name = names[i];

			try {
				final ModelStatistics modelStatistics = new ModelStatistics();
				final ICodeLevelModel codeLevelModel =
					Factory.getInstance().createCodeLevelModel("");
				codeLevelModel.addModelListener(modelStatistics);
				codeLevelModel.create(new CompleteClassFileCreator(
					new String[] { path },
					true));

				final IIdiomLevelModel idiomLevelModel =
					(IIdiomLevelModel) new AACRelationshipsAnalysis()
						.invoke(codeLevelModel);

				final Writer writer =
					ProxyDisk.getInstance().fileAbsoluteOutput(
						"../Ptidej Solver Data/ConstraintResults in " + name
								+ " for Singleton.ini");

				final Iterator entities =
					idiomLevelModel.getIteratorOnTopLevelEntities();
				int numberOfOccurrences = 0;
				while (entities.hasNext()) {
					final IFirstClassEntity firstClassEntity =
						(IFirstClassEntity) entities.next();
					//	final char[] entityName = firstClassEntity.getName();
					final char[] entityId = firstClassEntity.getID();
					final Iterator methods =
						firstClassEntity
							.getIteratorOnConstituents(IMethod.class);
					while (methods.hasNext()) {
						final IMethod method = (IMethod) methods.next();
						if (method.isStatic()
								&& Arrays.equals(
									method.getReturnType(),
									entityId)
								&& method
									.getNumberOfConstituents(IParameter.class) < 2) {

							writer.write(Integer.toString(numberOfOccurrences));
							writer.write(".100.singleton = ");
							writer.write(entityId);
							writer.write('\n');

							numberOfOccurrences++;
						}
					}
				}

				writer.close();

				System.out.println(path);
				System.out.println(modelStatistics);
				System.out.println();
			}
			catch (final CreationException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final IOException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final UnsupportedSourceModelException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}
}
