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
package padl.creator.classfile.test.creator;

import padl.creator.classfile.CompleteClassFileCreator;
import padl.creator.classfile.test.ClassFilePrimitive;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.util.ModelStatistics;
import padl.visitor.IWalker;
import util.io.NullWriter;
import util.io.ProxyConsole;

import java.io.PrintWriter;

public class CompleteClassFileCreatorTests {

	public static void main(final String[] args) {

		ProxyConsole.getInstance().setDebugOutput(new PrintWriter(new NullWriter()));
		ProxyConsole.getInstance().setNormalOutput(new NullWriter());
		ProxyConsole.getInstance().setErrorOutput(new NullWriter());

		{
			final IWalker walker = new InheritanceImplementationCounter();

			final String path = "D:\\src\\ptidej-Ptidej\\SOEN6461\\target\\classes\\soen6461\\TestClass.class";

			try {
				final ModelStatistics modelStatistics = new ModelStatistics();

				final ICodeLevelModel codeLevelModel = ClassFilePrimitive.getFactory().createCodeLevelModel("");

				codeLevelModel.addModelListener(modelStatistics);

				CompleteClassFileCreator completeClassFileCreator = new CompleteClassFileCreator(new String[]{path}, true);

				codeLevelModel.create(completeClassFileCreator);

				System.out.println("codeLevelModel Created");

				walker.reset();
				codeLevelModel.walk(walker);

				System.out.println(path);
				System.out.println(modelStatistics);
				System.out.print("Number of inheritances/implementations: ");
				System.out.println(walker.getResult());
				System.out.println();
			} catch (final CreationException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}

		System.out.println("\n\n------------------------------------------\n\n");
		
		{
			String[] classFiles = {"PADL/target/classes/padl/kernel/impl/AbstractClass.class"};
			final IWalker walker = new InheritanceImplementationCounter();
			try {
				final ModelStatistics modelStatistics = new ModelStatistics();
				final ICodeLevelModel codeLevelModel = Factory.getInstance().createCodeLevelModel("myModel");
				codeLevelModel.addModelListener(modelStatistics);
				CompleteClassFileCreator completeClassFileCreator = new CompleteClassFileCreator(classFiles, false);
				codeLevelModel.create(completeClassFileCreator);
				System.out.println("Model created");
				walker.reset();
				codeLevelModel.walk(walker);
				System.out.println("Model walked");
				System.out.println("Model Stats :");
				System.out.println(modelStatistics);
				System.out.println("Walker Results: ");
				System.out.println(walker.getResult());
			} catch (CreationException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
