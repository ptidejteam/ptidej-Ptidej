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
package modec.tool.instrumentation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Janice Ng
 */
public class Instrumentor {
	public static void main(final String[] args) throws IOException {
		// Instrumentor.instrumentClasses("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation_QuickUML_Command_ToggleRefreshADiagram.txt");
		// Instrumentor.instrumentClasses("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation_SearchManager_Builder_GetSQLStatement.txt");
		// Instrumentor.instrumentClasses("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation_JHotDraw_Visitor_CutAndPasteRectangle.txt");
		// Instrumentor.instrumentClasses("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation_QuickUML_Command_ResizeADiagram.txt");
		// Instrumentor.instrumentClasses("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation_SupervisorView_Observer_SelectDepartment.txt");
		//	Instrumentor
		//		.instrumentClasses("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation of Rhino.txt");
		// Instrumentor.instrumentClasses("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation of Gombos.txt");
		//	Instrumentor
		//		.instrumentClasses("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation of JHotDraw v5.4b1.txt");
		//	Instrumentor
		//		.instrumentClasses("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation of JHotDraw v5.1.txt");
		//	Instrumentor
		//		.instrumentClasses("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation of JEdit v4.2.txt");
		Instrumentor
			.instrumentClasses("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation of ArgoUML v0.19.8 (without tracing the Cognitive, Configuration, Events, UI).txt");
		//	Instrumentor
		//		.instrumentClasses("../MoDeC Bytecode Instrumentation Tests/Input Files/Evaluation of JFreeChart v1.0.13.txt");
	}
	public static void instrumentClasses(final String filename)
			throws IOException {

		FileReader fr = null;
		try {
			fr = new FileReader(filename);
		}
		catch (final FileNotFoundException e) {
			System.out
				.println("Error occured while opening file (file not found): "
						+ filename);
		}

		final BufferedReader inFile = new BufferedReader(fr);
		final String sourcePathname = inFile.readLine().trim();
		final String targetPathname = inFile.readLine().trim();
		final String libsPathname = inFile.readLine().trim();
		libsPathname.trim();
		final String traceFilename = inFile.readLine().trim();
		inFile.readLine().trim();

		final List listOfClasses = new ArrayList();
		// The first object of the array is the name of the trace file
		listOfClasses.add(traceFilename);
		String classname = inFile.readLine().trim();
		while (classname != null) {
			listOfClasses.add(classname.trim());
			classname = inFile.readLine();
		}

		inFile.close();

		final String[] classesToInstrument = new String[listOfClasses.size()];
		for (int i = 0; i < listOfClasses.size(); i++) {
			classesToInstrument[i] = (String) listOfClasses.get(i);
		}
		MoDeCInstrumentationTool.instrumentBytecode(
			traceFilename,
			sourcePathname,
			classesToInstrument,
			targetPathname);
	}
}
