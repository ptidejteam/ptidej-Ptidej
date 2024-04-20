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
package ptidej.solver.java.test.manager;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import org.junit.Assert;

import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.util.ModelStatistics;
import ptidej.solver.java.domain.Manager;
import util.io.ProxyDisk;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/05/16
 */
public class ManagerTest extends TestCase {
	private static ICodeLevelModel CodeLevelModel;

	public ManagerTest(final String aName) {
		super(aName);
	}

	protected void setUp() {
		if (ManagerTest.CodeLevelModel == null) {
			final String path = "../DeMIMA/target/test-classes/ptidej/example/composite2/";

			// Instantiating model.
			ManagerTest.CodeLevelModel = Factory.getInstance()
					.createCodeLevelModel(path);
			final ModelStatistics patternStatistics = new ModelStatistics();
			ManagerTest.CodeLevelModel.addModelListener(patternStatistics);

			// Building the program representation.
			try {
				ManagerTest.CodeLevelModel.create(
						new CompleteClassFileCreator(new String[] { path }));
			}
			catch (final CreationException e) {
				e.printStackTrace();
			}
			System.out.println();
			System.out.println(patternStatistics);

			try {
				final Writer writer = ProxyDisk.getInstance()
						.fileTempOutput("Domain.ini");
				Manager.save(ManagerTest.CodeLevelModel, writer);
				writer.close();
			}
			catch (final IOException ioe) {
				ioe.printStackTrace();
			}
		}
	}

	public void testLoader() {
		try {
			final Reader reader = ProxyDisk.getInstance()
					.fileTempInput("Domain.ini");
			final List listOfEntities = Manager.load(reader);
			reader.close();

			Assert.assertEquals("Number of entities",
					ManagerTest.CodeLevelModel.getNumberOfTopLevelEntities(),
					listOfEntities.size());
		}
		catch (final IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
