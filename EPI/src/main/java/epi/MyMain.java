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
package epi;

import java.io.PrintWriter;
import java.util.Iterator;
import padl.analysis.UnsupportedSourceModelException;
import padl.analysis.repository.AACRelationshipsAnalysis;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IIdiomLevelModel;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import epi.solver.Solution;
import epi.solver.StringBuilder;
import epi.ui.EPIDialog;
import epi.ui.SolutionDialog;

/**
 * @author OlivierK
 *
 */
public class MyMain {
	//	public static Hashtable getApproximation(){
	//		Hashtable approximationList = new Hashtable();
	//		
	//		ArrayList temp1 = new ArrayList();
	//		temp1.add("containerComposition");
	//		temp1.add("containerAggregation");
	//		temp1.add("composition");
	//		temp1.add("aggregation");
	//		temp1.add("association");
	//		temp1.add("useRelationship");
	//		approximationList.put("containerComposition", temp1);
	//		ArrayList temp2 = new ArrayList();
	//		temp2.add("containerAggregation");
	//		approximationList.put("containerAggregation", temp2);
	//		ArrayList temp3 = new ArrayList();
	//		temp3.add("composition");
	//		approximationList.put("composition", temp3);
	//		ArrayList temp4 = new ArrayList();
	//		temp4.add("aggregation");
	//		approximationList.put("aggregation", temp4);
	//		ArrayList temp5 = new ArrayList();
	//		temp5.add("association");
	//		approximationList.put("association", temp5);
	//		ArrayList temp6 = new ArrayList();
	//		temp6.add("useRelationship");
	//		approximationList.put("useRelationship", temp6);
	//		ArrayList temp7 = new ArrayList();
	//		temp7.add("inheritance");
	//		approximationList.put("inheritance", temp7);
	//		ArrayList temp8 = new ArrayList();
	//		temp8.add("creation");
	//		approximationList.put("creation", temp8);
	//		
	//		return approximationList;
	//	}
	public static IIdiomLevelModel createModel(
		final String path,
		final String[] packageNames) {
		String[] computePaths = null;

		if (packageNames != null) {
			computePaths = new String[packageNames.length];
			for (int i = 0; i < packageNames.length; i++) {
				computePaths[i] = path + packageNames[i].replace('.', '/');
			}
		}
		else {
			computePaths = new String[1];
			computePaths[0] = path;
		}

		final ICodeLevelModel codeLevelModel =
			Factory.getInstance().createCodeLevelModel("JHotDraw");

		try {
			codeLevelModel.create(new CompleteClassFileCreator(
				computePaths,
				true));

			if (packageNames != null) {
				// TODO: This code must be tested!
				final Iterator entities =
					codeLevelModel
						.getIteratorOnConstituents(IFirstClassEntity.class);
				while (entities.hasNext()) {
					final IFirstClassEntity firstClassEntity =
						(IFirstClassEntity) entities.next();
					final String entityName = firstClassEntity.getDisplayName();
					final String packageName =
						entityName.substring(0, entityName.lastIndexOf('.'));
					boolean toBeRemoved = true;
					for (int i = 0; i < packageNames.length && toBeRemoved; i++) {
						if (packageName.equals(packageNames[i])) {
							toBeRemoved = false;
						}
					}
					if (toBeRemoved) {
						codeLevelModel.removeConstituentFromID(entityName
							.toCharArray());
					}
				}
			}
		}
		catch (final CreationException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		IIdiomLevelModel idiomLevelModel = null;
		try {
			idiomLevelModel =
				(IIdiomLevelModel) new AACRelationshipsAnalysis()
					.invoke(codeLevelModel);
		}
		catch (final UnsupportedSourceModelException e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}

		return idiomLevelModel;
	}

	public static void main(final String[] args) {
		//		Approximation anApproximation = new Approximation(getApproximation());
		//		
		//		EPISolver test = new EPISolver("../JHotDraw v5.1/bin/");
		//		test.setMotifName("CompositeMotif");
		//		test.setProblemName("C1 for JHotDraw");
		//		test.computeSolutions(EPISolver.WITH_CONSTRAINTS);
		//		
		//		EPISolver test2 = new EPISolver(
		//				"../JHotDraw v5.1/bin/", "C2 for JHotDraw", "CompositeMotif", anApproximation);
		//		test2.computeSolutions(EPISolver.WITHOUT_CONSTRAINT);

		//ICodeLevelModel model = createModel("../Azureus v2.3.0.6/bin/com/", null);
		//ICodeLevelModel model = createModel("../pcgen v5.0/bin/pcgen/", null);
		//ICodeLevelModel model = createModel("../JRefactory/bin/", null);
		//ICodeLevelModel model = createModel("../GanttProject v1.10.2/bin/net/sourceforge/ganttproject/", null);
		//ICodeLevelModel model = createModel("../Trove/src/gnu/trove/", null);
		//ICodeLevelModel model = createModel("../QuickUML2001/bin/", null);
		// IIdiomLevelModel model = createModel("../../P-Mart Workspace/JHotDraw v5.1/bin/", null);
		// IIdiomLevelModel model = createModel("../JUnit/bin/junit/", null);
		final IIdiomLevelModel model =
			MyMain.createModel(
				"../../P-Mart Workspace/Juzzle/bin/org/game/Juzzle/",
				null);
		//ICodeLevelModel model = (ICodeLevelModel) epi.test.Test.getCodeLevelModel();
		final long start = System.currentTimeMillis();
		final String progString = StringBuilder.buildModelString(model);
		ProxyConsole
			.getInstance()
			.debugOutput()
			.println("A:" + (System.currentTimeMillis() - start) / 1000);
		for (int i = 0; i < 35; i++) {
			final EPIDialog inst = new EPIDialog(null, true, model, progString);
			inst.setVisible(true);

			//TODO: if(closewindow et pas solve!)
			final Solution[] sol = inst.getSolutions();

			try {
				final PrintWriter out =
					new PrintWriter(ProxyDisk.getInstance().fileTempOutput(
						"rsc/" + inst.getLogger().getIdentificationName() + i
								+ ".txt"));
				Solution.print(sol, out);
			}
			catch (final Exception e) {
			}

			final SolutionDialog inst2 = new SolutionDialog(null, false);
			inst2.setData(inst.getLogger());
			inst2.show();
		}

	}
}
