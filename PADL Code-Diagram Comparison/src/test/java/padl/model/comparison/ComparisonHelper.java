/*******************************************************************************
 * Copyright (c) 2001-2015 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
 ******************************************************************************/
package padl.model.comparison;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import padl.creator.xmiclassdiagram.XMICreator;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.motif.kernel.IDesignLevelModel;
import padl.motif.kernel.impl.MotifFactory;
import padl.visitor.IWalker;
import ptidej.solver.java.helper.DesignMotifIdentificationCaller;
import sad.detection.helper.SmellDetectionHelper;
import util.io.ProxyDisk;

@SuppressWarnings("unused")
public class ComparisonHelper {
	private static void computeForAbstractLevelModel(
			final IAbstractLevelModel aModel, final String anOutputDirectory,
			final String aName) {

		SmellDetectionHelper.analyseCodeLevelModel(SmellDetectionHelper.SMELLS,
				aName, aModel, ProxyDisk.getInstance().directoryTempString()
						+ "Origin/" + anOutputDirectory);

		DesignMotifIdentificationCaller.analyseCodeLevelModel(
				DesignMotifIdentificationCaller.MOTIFS, aName, aModel,
				ProxyDisk.getInstance().directoryTempString() + "Origin/"
						+ anOutputDirectory);

		try {
			final IWalker pomCalculator = new POMCalculator();
			aModel.walk(pomCalculator);
			final Writer w = new FileWriter(
					ProxyDisk.getInstance().directoryTempString() + "Origin/"
							+ anOutputDirectory + aName + ".metrics");
			w.write((String) pomCalculator.getResult());
			w.close();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
	}

	private static void computeForCpp(final String aFolderName,
			final String aName, final String aPathToCppFiles) {

		final IIdiomLevelModel model = ModelGenerator
				.generateModelFromCppFilesUsingEclipse(aName, aPathToCppFiles);
		ComparisonHelper.computeForAbstractLevelModel(model, aFolderName,
				aName);
	}

	private static void computeForJava(final String aFolderName,
			final String aName, final String aPathToJavaFiles) {

		final IIdiomLevelModel model = ModelGenerator
				.generateModelFromJavaFilesDirectoryUsingEclipse(
						aPathToJavaFiles);
		ComparisonHelper.computeForAbstractLevelModel(model, aFolderName,
				aName);
	}

	private static void computeForXMI(final String anOutputDirectory,
			final String aName, final String aPathToXMIFile)
			throws CreationException {

		final IDesignLevelModel model = ((MotifFactory) MotifFactory
				.getInstance()).createDesignLevelModel(aName.toCharArray());
		model.create(new XMICreator(aPathToXMIFile));
		ComparisonHelper.computeForAbstractLevelModel(model, anOutputDirectory,
				aName);
	}

	private static void cpp() throws CreationException {
		//	ComparisonHelper.computeForXMI(
		//		"First Batch/Syntax Analyzer/",
		//		"1.xmi",
		//		"C:/Data/Diagrams and Code/First Batch/C++/1/1.xmi");
		//	ComparisonHelper.computeForXMI(
		//		"First Batch/Syntax Analyzer/",
		//		"2.xmi",
		//		"C:/Data/Diagrams and Code/First Batch/C++/1/2.xmi");
		//	ComparisonHelper
		//		.computeForCpp(
		//			"First Batch/Syntax Analyzer/",
		//			"C++ Code",
		//			"C:/Data/Diagrams and Code/First Batch/C++/1/syntax-analyzer-master/src/");

		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/msv_Poker/",
		//		"01.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/01/UML/01.xml");
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/msv_Poker/",
		//		"02.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/01/UML/02.xml");
		//	ComparisonHelper.computeForCpp(
		//		"Second Batch/msv_Poker/",
		//		"C++ Code Server",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/01/msv_GameServer/");
		//	ComparisonHelper.computeForCpp(
		//		"Second Batch/msv_Poker/",
		//		"C++ Code Client",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/01/msv_PokerClient/");

		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/robocut/",
		//		"03.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/02/UML/03.xml");
		//	ComparisonHelper.computeForCpp(
		//		"Second Batch/robocut/",
		//		"C++ Code",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/02/robocut/");

		// NOT C++
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/minsky/",
		//		"Minsky.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/03/UML/Minsky.xml");
		//	ComparisonHelper
		//		.computeForXMI(
		//			"Second Batch/minsky/",
		//			"MinskySchema.xml",
		//			"C:/Data/Diagrams and Code/Second Batch/C++/03/UML/MinskySchema.xml");
		//	ComparisonHelper.computeForCpp(
		//		"Second Batch/minsky/",
		//		"C++ Code",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/03/minsky/");

		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/qjobs/",
		//		"06.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/05/UML/06.xml");
		//	ComparisonHelper.computeForCpp(
		//		"Second Batch/qjobs/",
		//		"C++ Code",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/05/qjobs/");
		//
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/QTweetDeck/",
		//		"diagrams.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/06/UML/diagrams.xml");
		//	ComparisonHelper.computeForCpp(
		//		"Second Batch/QTweetDeck/",
		//		"C++ Code",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/06/QTweetDeck/");
		//
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/kartjax/",
		//		"08.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/09/UML/08.xml");
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/kartjax/",
		//		"09.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/09/UML/09.xml");
		//	ComparisonHelper.computeForCpp(
		//		"Second Batch/kartjax/",
		//		"C++ Code",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/09/kartjax/src/");
		//
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/annoyme/",
		//		"10.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/10/UML/10.xml");
		//	ComparisonHelper.computeForCpp(
		//		"Second Batch/annoyme/",
		//		"C++ Code",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/10/annoyme/");

		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/ImProc_suite/",
		//		"11.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/11/UML/11.xml");
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/ImProc_suite/",
		//		"12.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/11/UML/12.xml");
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/ImProc_suite/",
		//		"13.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/11/UML/13.xml");
		//	ComparisonHelper.computeForCpp(
		//		"Second Batch/ImProc_suite/",
		//		"C++ Code",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/11/ImProc_suite/");
		//
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/Ketaroller-master/",
		//		"14.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/12/UML/14.xml");
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/Ketaroller-master/",
		//		"15.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/12/UML/15.xml");
		//	ComparisonHelper
		//		.computeForCpp(
		//			"Second Batch/Ketaroller-master/",
		//			"C++ Code",
		//			"C:/Data/Diagrams and Code/Second Batch/C++/12/Ketaroller-master/src/");
		//
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/p2p_distributed/",
		//		"16.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/13/UML/16.xml");
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/p2p_distributed/",
		//		"17.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/13/UML/17.xml");
		//	ComparisonHelper
		//		.computeForCpp(
		//			"Second Batch/p2p_distributed/",
		//			"C++ Code",
		//			"C:/Data/Diagrams and Code/Second Batch/C++/13/p2p_distributed/src/");
		//
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/FireParrot/",
		//		"18.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/14/UML/18.xml");
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/FireParrot/",
		//		"19.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/14/UML/19.xml");
		//	ComparisonHelper.computeForCpp(
		//		"Second Batch/p2p_distributed/",
		//		"C++ Code",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/14/FireParrot/src/");

		ComparisonHelper.computeForXMI("Second Batch/libdetran/", "20.xml",
				"C:/Data/Diagrams and Code/Second Batch/C++/15/UML/20.xml");
		ComparisonHelper.computeForCpp("Second Batch/libdetran/", "C++ Code",
				"C:/Data/Diagrams and Code/Second Batch/C++/15/libdetran/src/");

		// NOT C++
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/statismo/",
		//		"21.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/16/UML/21.xml");
		//	ComparisonHelper.computeForXMI(
		//		"Second Batch/statismo/",
		//		"22.xml",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/16/UML/22.xml");
		//	ComparisonHelper.computeForCpp(
		//		"Second Batch/statismo/",
		//		"C++ Code",
		//		"C:/Data/Diagrams and Code/Second Batch/C++/16/statismo/src/");
	}

	private static void java() throws CreationException {
		ComparisonHelper.computeForXMI("First Batch/JCoAP-master/", "1.xmi",
				"C:/Data/Diagrams and Code/First Batch/Java/1.xmi");
		ComparisonHelper.computeForXMI("First Batch/JCoAP-master/", "2.xmi",
				"C:/Data/Diagrams and Code/First Batch/Java/2.xmi");
		ComparisonHelper.computeForJava("First Batch/JCoAP-master/",
				"Java Code",
				"C:/Data/Diagrams and Code/First Batch/Java/JCoAP-master/src/");

		ComparisonHelper.computeForXMI("Second Batch/generateur/", "04.xml",
				"C:/Data/Diagrams and Code/Second Batch/Java/04/UML/04.xml");
		ComparisonHelper.computeForXMI("Second Batch/generateur/", "05.xml",
				"C:/Data/Diagrams and Code/Second Batch/Java/04/UML/05.xml");
		ComparisonHelper.computeForJava("Second Batch/generateur/", "Java Code",
				"C:/Data/Diagrams and Code/Second Batch/Java/04/generateur/src/");

		ComparisonHelper.computeForXMI("Second Batch/Ant/", "07.xml",
				"C:/Data/Diagrams and Code/Second Batch/Java/08/UML/07.xml");
		ComparisonHelper.computeForJava("Second Batch/Ant/", "Java Code",
				"C:/Data/Diagrams and Code/Second Batch/Java/08/Ant/");
	}

	public static void main(final String[] someArgs) throws CreationException {
		// ComparisonHelper.cpp();
		// ComparisonHelper.java();
		// ComparisonHelper.orphans();

		ComparisonHelper.computeForXMI("Third Batch/",
				"800px-Person_Class_Diagram_13.xmi",
				"C:/Data/Diagrams and Code/Third Batch/Design/800px-Person_Class_Diagram_13.xmi");

		ComparisonHelper.computeForXMI("Third Batch/",
				"800px-Unit_Class_Diagram_14.xmi",
				"C:/Data/Diagrams and Code/Third Batch/Design/800px-Unit_Class_Diagram_14.xmi");

		ComparisonHelper.computeForXMI("Third Batch/", "argoapi_1.xmi",
				"C:/Data/Diagrams and Code/Third Batch/Design/argoapi_1.xmi");

		ComparisonHelper.computeForXMI("Third Batch/", "argocogchklist_2.xmi",
				"C:/Data/Diagrams and Code/Third Batch/Design/argocogchklist_2.xmi");

		ComparisonHelper.computeForXMI("Third Batch/", "argocogcritics_3.xmi",
				"C:/Data/Diagrams and Code/Third Batch/Design/argocogcritics_3.xmi");

		ComparisonHelper.computeForXMI("Third Batch/", "argoconfig_4.xmi",
				"C:/Data/Diagrams and Code/Third Batch/Design/argoconfig_4.xmi");

		ComparisonHelper.computeForXMI("Third Batch/", "argokernel_5.xmi",
				"C:/Data/Diagrams and Code/Third Batch/Design/argokernel_5.xmi");

		ComparisonHelper.computeForXMI("Third Batch/", "argoocl_6.xmi",
				"C:/Data/Diagrams and Code/Third Batch/Design/argoocl_6.xmi");

		ComparisonHelper.computeForXMI("Third Batch/",
				"BasicClassDiagram2_21.xmi",
				"C:/Data/Diagrams and Code/Third Batch/Design/BasicClassDiagram2_21.xmi");

		ComparisonHelper.computeForXMI("Third Batch/", "ClassDiagramV2_24.xmi",
				"C:/Data/Diagrams and Code/Third Batch/Design/ClassDiagramV2_24.xmi");

		ComparisonHelper.computeForXMI("Third Batch/",
				"LearningRulesClassDiagram_22.xmi",
				"C:/Data/Diagrams and Code/Third Batch/Design/LearningRulesClassDiagram_22.xmi");

		ComparisonHelper.computeForXMI("Third Batch/",
				"NeurophClassDiagram2.3_23.xmi",
				"C:/Data/Diagrams and Code/Third Batch/Design/NeurophClassDiagram2.3_23.xmi");

		ComparisonHelper.computeForXMI("Third Batch/", "screenshot_3_0_7.xmi",
				"C:/Data/Diagrams and Code/Third Batch/Design/screenshot_3_0_7.xmi");

		ComparisonHelper.computeForXMI("Third Batch/",
				"WRO-ClassDiagram_25.xmi",
				"C:/Data/Diagrams and Code/Third Batch/Design/WRO-ClassDiagram_25.xmi");
	}

	private static void orphans() throws CreationException {
		ComparisonHelper.computeForXMI("First Batch/1/", "1.xmi",
				"C:/Data/Diagrams and Code/First Batch/XMI without source code/1.xmi");
		ComparisonHelper.computeForXMI("First Batch/2/", "2.xmi",
				"C:/Data/Diagrams and Code/First Batch/XMI without source code/2.xmi");
		ComparisonHelper.computeForXMI("First Batch/3/", "3.xmi",
				"C:/Data/Diagrams and Code/First Batch/XMI without source code/3.xmi");
		ComparisonHelper.computeForXMI("First Batch/4/", "4.xmi",
				"C:/Data/Diagrams and Code/First Batch/XMI without source code/4.xmi");
		ComparisonHelper.computeForXMI("First Batch/5/", "5.xmi",
				"C:/Data/Diagrams and Code/First Batch/XMI without source code/5.xmi");
		ComparisonHelper.computeForXMI("First Batch/6/", "6.xmi",
				"C:/Data/Diagrams and Code/First Batch/XMI without source code/6.xmi");
		ComparisonHelper.computeForXMI("First Batch/7/", "7.xmi",
				"C:/Data/Diagrams and Code/First Batch/XMI without source code/7.xmi");
	}
}
