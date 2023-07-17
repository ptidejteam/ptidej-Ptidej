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

public class AnalyseChangesFiles {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unused")
	public static void CombineBlobProbsandChanges() {

		DataFileTableModel DesignChurn =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/DesignChurn.csv");

		DataFileTableModel NIM =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/NIM.csv");
		DataFileTableModel NNM =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/NNM.csv");
		DataFileTableModel NSM =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/NSM.csv");
		DataFileTableModel NPMPrev =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/NPMPrev.csv");
		DataFileTableModel NPMNext =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/NPMNext.csv");
		DataFileTableModel Instability =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/Instability.csv");
		DataFileTableModel Stress =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/Stress.csv");
		DataFileTableModel InstructionsAdded =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/InstructionsAdded.csv");
		DataFileTableModel InstructionsDeleted =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/InstructionsDeleted.csv");
		DataFileTableModel InstructionsModified =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/InstructionsModified.csv");
		DataFileTableModel InstructionsChange =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/InstructionsChange.csv");
		DataFileTableModel InstructionsChangeRatio =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/InstructionsChangeRatio.csv");
		DataFileTableModel ClassAdded =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/ClassAdded.csv");
		DataFileTableModel ClassRemoved =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/ClassRemoved.csv");
		DataFileTableModel ClassToInterface =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/ClassToInterface.csv");
		DataFileTableModel InterfaceToClass =
			new DataFileTableModel(
				"D:/Documents/Workspace/Defects evolution/Analysis/metrics of change/InterfaceToClass.csv");

		//System.out.println(data_0_role.getValueAt(1, 3));

		//		for (int i = 0; i < data_ND_role.getColumnCount(); i++) {
		//			try {
		//				final FileWriter out =
		//					new FileWriter("rsc/composition/"
		//							+ data_ND_role.getColumnName(i) + "ND_0.csv");
		//				// now the R script file
		//
		//				final FileWriter Rout =
		//					new FileWriter("rsc/composition/RSript_"
		//							+ data_ND_role.getColumnName(i) + "ND_0.R");
		//
		//				System.out.println("source(\"" + "RSript_"
		//						+ data_ND_role.getColumnName(i) + "ND_0.R\"" + ")");
		//
		//		

	}

}
