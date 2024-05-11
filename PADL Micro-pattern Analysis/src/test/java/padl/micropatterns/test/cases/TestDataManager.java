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
package padl.micropatterns.test.cases;

import org.junit.Assert;
import junit.framework.TestCase;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.IAbstractModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import padl.micropatterns.helper.MicroPatternDetector;
import util.io.ProxyConsole;

/**
 * @author tanterij
 */
public class TestDataManager extends TestCase {

	private static MicroPatternDetector detector;
	private MicroPatternDetector currentDetector;

	public TestDataManager(String arg0) {
		super(arg0);
	}

	protected void setUp() {
		if (TestDataManager.detector == null) {
			try {
				final ICodeLevelModel codeLevelModel =
					Factory.getInstance().createCodeLevelModel(
						"ptidej.example.innerclasses");
				codeLevelModel
					.create(new CompleteClassFileCreator(
						new String[] { "../PADL Micro-pattern Analysis/target/test-classes/padl/micropatterns/examples/DataManager.class", }));

				final AACRelationshipsAnalysis accRel =
					new AACRelationshipsAnalysis();
				IAbstractModel newModel = accRel.invoke(codeLevelModel);
				this.currentDetector = new MicroPatternDetector(newModel);
			}
			catch (final CreationException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final UnsupportedSourceModelException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}

	public void testDataManager() {
		Assert.assertEquals(
			"Data Manager",
			1,
			this.currentDetector.getNumberOfDataManager());
	}
}
