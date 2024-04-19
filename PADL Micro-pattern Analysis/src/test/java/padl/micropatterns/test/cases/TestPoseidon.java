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

import junit.framework.TestCase;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.impl.Factory;
import padl.micropatterns.helper.MicroPatternDetector;

/**
 * @author tanterij
 */
public class TestPoseidon extends TestCase {

	private static MicroPatternDetector detector;
	private MicroPatternDetector currentDetector;

	public TestPoseidon(String arg0) {
		super(arg0);
	}

	protected void setUp() {

		if (TestPoseidon.detector == null) {
			final ICodeLevelModel codeLevelModel =
				Factory.getInstance().createCodeLevelModel(
					"ptidej.example.innerclasses");
			try {
				codeLevelModel
					.create(new CompleteClassFileCreator(
						new String[] {
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/jmiutils.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/log4j.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/alloy.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/mdrapi.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/antlr.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/mdr_environment.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/axis.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/mof.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/batik-awt-util.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/nb-editor.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/batik-svggen.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/nbmdr.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/batik-util.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/openide-fs.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/codeGen.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/openide-util.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/commons-discovery.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/org-openide-execution.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/commons-logging.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/org-openide-io.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/di20.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/piccolo.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/edit-on-javabean-sdk-swing.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/piccolox.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/enhancedEditor.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/poseidonAnt.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/example.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/poseidonCore.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/foxtrot.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/poseidon.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/freehep-base.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/reveng.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/freehep-graphics2d.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/revengutil.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/freehep-graphicsio.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/saaj.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/freehep-graphicsio-pdf.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/services.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/freehep-graphicsio-ps.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/templates.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/gw-storage.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/trove.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/J2PrinterWorks.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/uml14.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/jaxrpc.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/umlplugin.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/jboogie.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/umltojava.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/jdom.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/velocity-dep-1.2.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/jmi.jar",
								"../PADL Micro-pattern Analysis/target/test-classes/poseidon/velocity.jar" }));

				// After modification

			}
			catch (Exception e) {

			}

			System.out.println("Here we are ......\n");
			this.currentDetector = new MicroPatternDetector(codeLevelModel);
		}
	}

	public void testPoseidon() {
		final double nbOfClass = this.currentDetector.getNumberOfClass();

		// Output prevalence of Micro-Patterns
		System.out
			.println("Designator : "
					+ (this.currentDetector.getNumberOfDesignator() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Taxonormy : "
					+ (this.currentDetector.getNumberOfTaxonomy() * 100 / nbOfClass)
					+ " %");
		System.out.println("Joiner : "
				+ (this.currentDetector.getNumberOfJoiner() * 100 / nbOfClass)
				+ " %");
		System.out.println("Pool : "
				+ (this.currentDetector.getNumberOfPool() * 100 / nbOfClass)
				+ " %");
		System.out
			.println("Function Pointer : "
					+ (this.currentDetector.getNumberOfFunctionPointer() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Function Object : "
					+ (this.currentDetector.getNumberOfFunctionObject() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Cobol Like : "
					+ ((double) this.currentDetector.getNumberOfCobolLike() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("State Less : "
					+ ((double) this.currentDetector.getNumberOfStateless() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Common State : "
					+ ((double) this.currentDetector.getNumberOfCommonState() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Immutable : "
					+ ((double) this.currentDetector.getNumberOfImmutable() * 100 / nbOfClass)
					+ " %");
		System.out.println("Restricted Creation : "
				+ ((double) this.currentDetector
					.getNumberOfRestrictedCreation() * 100 / nbOfClass) + " %");
		System.out
			.println("Sampler : "
					+ ((double) this.currentDetector.getNumberOfSampler() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Box : "
					+ ((double) this.currentDetector.getNumberOfBox() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Canopy : "
					+ ((double) this.currentDetector.getNumberOfCanopy() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Compound Box : "
					+ ((double) this.currentDetector.getNumberOfCompoundBox() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Record : "
					+ ((double) this.currentDetector.getNumberOfRecord() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("DataManager : "
					+ ((double) this.currentDetector.getNumberOfDataManager() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Sink : "
					+ ((double) this.currentDetector.getNumberOfSink() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Outline : "
					+ ((double) this.currentDetector.getNumberOfOutLine() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Trait : "
					+ ((double) this.currentDetector.getNumberOfTrait() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("State Machine : "
					+ ((double) this.currentDetector.getNumberOfStateMachine() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("PureType : "
					+ ((double) this.currentDetector.getNumberOfPureType() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("AugmentedType : "
					+ ((double) this.currentDetector.getNumberOfAugmentedType() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("PseudoClass : "
					+ ((double) this.currentDetector.getNumberOfPseudoClass() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Implementor : "
					+ ((double) this.currentDetector.getNumberOfImplementor() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Overrider : "
					+ ((double) this.currentDetector.getNumberOfOverrider() * 100 / nbOfClass)
					+ " %");
		System.out
			.println("Extender : "
					+ ((double) this.currentDetector.getNumberOfExtender() * 100 / nbOfClass)
					+ " %");

		System.out.println("\nNb Class : " + nbOfClass);
	}
}
