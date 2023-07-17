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
/**
 * 
 */
package ptidej.ui.layout.repository.sugiyama.minimising;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import ptidej.ui.layout.repository.sugiyama.SettingValue;
import ptidej.ui.layout.repository.sugiyama.graph.INode;
import ptidej.ui.layout.repository.sugiyama.graph.Node;

/**
 * @author mohamedkahla
 * @date 23-06-2006
 * @since 30/05/2006
 * @version 15082006H1443
 * 
 * STEP 2 of Sugiyama,s algorithme 
 * 
 * Reduction of the number of crossing
 * Algorithme for Two-Level Hierarchies
 * we will use the heuristic methode until
 * Sugiyama said that's much more 
 * performant
 * The Barycentric method (BC method)
 * 	// // // // // // // 
 * 	//IMPORTANT// //
 *  // // // // // // //
 *  //This method may dont give he best solution //
 *  // // // // // // //
 *  
 */
public class BarycentricCrossingMinimizer {
	private final int nbLevels;
	private final List graphNode;
	private INode[][] nodeMatrix;

	/**
	 * 
	 */
	public BarycentricCrossingMinimizer(
		final List aGraphNode,
		final int anNbLevels) {

		this.nodeMatrix = new Node[0][0];
		this.graphNode = aGraphNode;
		this.nbLevels = anNbLevels;
	}

	private boolean debugging = false;
	private void printDebugMessage(String message) {
		if (this.debugging)
			System.out.println(message);

	}

	/**
	 * @author Mohamed Kahla
	 * @since 17/07/2006
	 * @version 17072006H1045
	 * @return matrix representing this graph
	 */
	public INode[][] getMatrix() {
		return this.nodeMatrix;
	}

	/**
	 * @author Mohamed Kahla
	 * @since 17/07/2006
	 * @version 15082006H1441
	 * Reduce the number of crossing 
	 * in all the graph at the differents levels
	 * 
	 *  BUT IT STILL A HEURISTIC
	 *  SO :  It's important to care about the starting 
	 *  order of vertices because we could make a better 
	 *  solution whith a different initial order 
	 * 
	 */
	public void reduceCrossing() {

		fillMatrix();

		// 15-08-2006
		// VERY IMPORTANT!
		// this must be done after filling the matrix
		INode[][] bestMatrix = this.tabCopy(this.nodeMatrix);

		// if we have a single Graph level 
		// WE DO NO THING!

		// if we have only two levels
		// we aply for two levels' methods
		if (this.nodeMatrix.length == 2) {
			final Matrix bestMat =
				this.twoLevelsCrossingNbReduction(
					this.nodeMatrix[0],
					this.nodeMatrix[1]);

			this.nodeMatrix[1] = bestMat.getDownLevel();
			bestMatrix = this.nodeMatrix;
		}

		// if more than two levels
		else {
			int roundsWhithoutProgress = 0;
			int roundsReversion = 0;
			// 28-07-2006
			int maxRoundsWhithoutProgress =
				SettingValue.getMaxNbRoundsWhithoutProgress();

			final INode[][] tmpNodeMatrix = this.nodeMatrix;

			int bestCrossingNb = this.countAllCrossing(tmpNodeMatrix);
			int numberOfRounds = SettingValue.getMaxNumberOfRounds();
			boolean stop = false;
			while (numberOfRounds > 0 && !stop) {
				numberOfRounds--;
				boolean stopPhase1 = false;
				while (!stopPhase1 && !stop) {
					// downward 
					printDebugMessage("6.1.1");
					for (int i = 0; i < this.nodeMatrix.length - 1; i++) {

						printDebugMessage("6.1.1.i.1 : " + i);

						final INode[] aFixedLayer = tmpNodeMatrix[i];
						final INode[] aMovableLayer = tmpNodeMatrix[i + 1];

						final Matrix currentMatrix =
							new Matrix(aFixedLayer, aMovableLayer);
						// we get the row ordring because it's the order 
						// of the down level in the currentMatrix that are reordred!
						final Matrix downLevelMatrixOrdred =
							currentMatrix.getBarycentricRowOrdringMatrix();

						printDebugMessage("6.1.1.i.2 : " + i);

						// a new Matrix is build whith the new orders!
						tmpNodeMatrix[i + 1] =
							downLevelMatrixOrdred.getDownLevel();
					}

					// the first layer still Not reordred

					// upward
					printDebugMessage("6.1.2");
					for (int i = this.nodeMatrix.length - 1; i > 0; i--) {
						printDebugMessage("6.1.1.i.1 : " + i);

						final INode[] aMovableLayer = tmpNodeMatrix[i - 1];
						final INode[] aFixedLayer = tmpNodeMatrix[i];

						final Matrix currentMatrix =
							new Matrix(aMovableLayer, aFixedLayer);
						// we get the column ordring because it's the order 
						// of the up level in the currentMatrix that are reordred!
						final Matrix upLevelMatrixOrdred =
							currentMatrix.getBarycentricColumnOrdringMatrix();
						// System.out.println("6.1.1.i.2 : " + i);
						// a new Matrix is build whith the new orders!

						tmpNodeMatrix[i - 1] = upLevelMatrixOrdred.getUpLevel();
					}

					// Convergence test!
					printDebugMessage("6.1.3");
					int currentCrossing = countAllCrossing(tmpNodeMatrix);
					printDebugMessage("bestCrossingNb : " + bestCrossingNb);
					printDebugMessage("currentCrossing : " + currentCrossing);

					// Correction done !
					// Mohamed Kahla
					// 12-08-2006
					// Here I shoud have a copy of the best because the same 
					// Matrix could eventely be modified !
					if (currentCrossing == 0) {
						stop = true;
						bestMatrix = tabCopy(tmpNodeMatrix);
					}
					else if (currentCrossing < bestCrossingNb) {
						bestCrossingNb = currentCrossing;

						// this.nodeMatrix = tmpNodeMatrix;
						bestMatrix = tabCopy(tmpNodeMatrix);

						roundsWhithoutProgress = 0;
						roundsReversion++;
					}
					else {
						printDebugMessage("roundsWhithoutProgress : "
								+ roundsWhithoutProgress);
						printDebugMessage("roundsReversion : "
								+ roundsReversion);
						roundsWhithoutProgress++;
						roundsReversion++;

						if (roundsWhithoutProgress > maxRoundsWhithoutProgress)
							stopPhase1 = true;
					}
				}

				if (!stop) {
					if (roundsReversion != roundsWhithoutProgress) {
						roundsWhithoutProgress = 0;
						roundsReversion = 0;

						// because phase 1 has been terminated
						// whith Up procedure so phase 2 starts whith Up procedure!
						// as writed in the Sugiyama's algorithme 
						// page 118!
						// Phase 2 : inversing order (for vertices whith equals Barycenters)

						// downward 
						printDebugMessage("Reversing!");

						// downward
						for (int i = 0; i < this.nodeMatrix.length - 1; i++) {
							printDebugMessage("6.1.4");
							Matrix currentMatrix =
								new Matrix(
									tmpNodeMatrix[i],
									tmpNodeMatrix[i + 1]);
							// Reordring!
							Matrix downMatrixReOrdred =
								currentMatrix
									.getBarycentricRowReversedOrdringMatrix();

							tmpNodeMatrix[i + 1] =
								downMatrixReOrdred.getDownLevel();
						}

						// upward
						printDebugMessage("6.1.5");
						for (int i = this.nodeMatrix.length - 1; i > 0; i--) {
							// System.out.println("6.1.1.i.1 : " + i);
							Matrix currentMatrix =
								new Matrix(
									tmpNodeMatrix[i - 1],
									tmpNodeMatrix[i]);
							// Reordring!
							Matrix upLevelMatrixReOrdred =
								currentMatrix
									.getBarycentricColumnReversedOrdringMatrix();

							tmpNodeMatrix[i - 1] =
								upLevelMatrixReOrdred.getUpLevel();
						}
					}

					else
						stop = true;
				}
			}

			// now that all reduction are done for best 
			// we keep the best results!
			this.nodeMatrix = bestMatrix;
		}

		printDebugMessage("nodeMatrix.length: " + this.nodeMatrix.length);
		// printDebugMessage("nodeMatrix[0].legnth : " + this.nodeMatrix[0].length);
	}

	/**
	 * This method is needed to make a copy of the best 
	 * Matrix : crossing minimisation
	 * 
	 * @author Mohamed Kahla
	 * @since 14/08/2006
	 * @param tabSource tab to be copied
	 * @return the copy
	 * @version 14082006H1532
	 */
	private INode[][] tabCopy(final INode[][] tabSource) {
		final INode[][] tmpTab = new INode[tabSource.length][1];
		for (int i = 0; i < tabSource.length; i++) {
			tmpTab[i] = tabSource[i];
		}
		return tmpTab;
	}

	/**
	 * This method compute the number of all 
	 * Graph's crossing for the visible edges
	 * @author Mohamed Kahla
	 * @since 17/07/2006
	 * @param matrix
	 * @return the number of all crossing in this graph
	 * @version 12082006H1456
	 */
	private int countAllCrossing(final INode[][] matrix) {
		int crossing = 0;
		// If we have at list 2 levels !
		// this correction is done 12-08-2006
		if (matrix.length >= 2) {
			for (int i = 0; i < matrix.length - 1; i++) {
				Matrix currentMatrix = new Matrix(matrix[i], matrix[i + 1]);
				crossing += currentMatrix.getCrossingNumber();

			}
		}
		return crossing;
	}

	/**
	 * @deprecated :
	 * 
	 *  WARNIG : this method work but still not
	 *  implimenting correctly the Sugiyama's algorithme
	 *  
	 *   Use the reduceCrossing() method insted
	 *    
	 * @author Mohamed Kahla
	 * @since 06/06/2006
	 * 
	 * the down-up procedre for the sugiyama algorithme
	 * for nLevels hierarchies
	 *
	 */
	public void nLevelsCrossingNbReductionDownUp() {
		fillMatrix(); // fill the inial matrix
		// down
		printDebugMessage("6.1.1");
		for (int i = 0; i < this.nodeMatrix.length - 1; i++) {

			printDebugMessage("6.1.1.i.1 : " + i);

			Matrix tmpMatrix =
				twoLevelsCrossingNbReduction(
					this.nodeMatrix[i],
					this.nodeMatrix[i + 1]);

			printDebugMessage("6.1.1.i.2 : " + i);

			this.nodeMatrix[i + 1] = tmpMatrix.getDownLevel();
		}

		// up

		printDebugMessage("6.1.2");

		for (int i = this.nodeMatrix.length - 1; i > 0; i--) {
			Matrix tmpMatrix =
				twoLevelsCrossingNbReduction(
					this.nodeMatrix[i - 1],
					this.nodeMatrix[i]);
			this.nodeMatrix[i - 1] = tmpMatrix.getUpLevel();
		}
	}

	/**
	 * 
	 * @param aFixedLAyer
	 * @param aMovableLayer
	 * @since 06/06/2006
	 * @version 13072006H1520
	 * @return the Matrix containing the less 
	 * best crossing number
	 */
	private Matrix twoLevelsCrossingNbReduction(
		final INode[] aFixedLAyer,
		final INode[] aMovableLayer) {

		int maxNbIterationPhase2 = SettingValue.getMaxNbIterationPhase2();
		Matrix initialMatrix = new Matrix(aFixedLAyer, aMovableLayer);
		Matrix bestMatrix = initialMatrix;
		int bestCrossingNb = bestMatrix.getCrossingNumber();

		boolean stop = false;
		while (!stop && (maxNbIterationPhase2 != 0)) {

			printDebugMessage("6.1.1.i.1.1.maxNbIterationPhase2 :"
					+ maxNbIterationPhase2);

			maxNbIterationPhase2--;
			int maxNbIterationPhase1 = SettingValue.getMaxNbIterationPhase1();
			Matrix m2;
			do {

				printDebugMessage("6.1.1.i.1.2.maxNbIterationPhase1 :  "
						+ maxNbIterationPhase1);

				maxNbIterationPhase1--;
				Matrix m1 = bestMatrix.getBarycentricRowOrdringMatrix();

				printDebugMessage("6.1.1.i.1.3.maxNbIterationPhase1 :  "
						+ maxNbIterationPhase1);

				// Mohamed Kahla
				// 13-07-2006
				// minor optimisation
				// It seams that this is not necessary because it's all ready done 
				// in the Matrix class
				int currentCrossing = m1.getCrossingNumber();
				if (currentCrossing < bestCrossingNb) {
					bestMatrix = m1;
					bestCrossingNb = currentCrossing;
				}

				printDebugMessage("6.1.1.i.1.4.maxNbIterationPhase1 :  "
						+ maxNbIterationPhase1);

				m2 = m1.getBarycentricColumnOrdringMatrix();

				printDebugMessage("6.1.1.i.1.5.maxNbIterationPhase1 :  "
						+ maxNbIterationPhase1);

				currentCrossing = m2.getCrossingNumber();
				if (currentCrossing < bestCrossingNb) {
					bestMatrix = m2;
					bestCrossingNb = currentCrossing;
				}

				printDebugMessage("6.1.1.i.1.6.maxNbIterationPhase1 :  "
						+ maxNbIterationPhase1);

			}
			while (maxNbIterationPhase1 != 0 && !(initialMatrix.equals(m2)));

			Matrix m3 = m2.getBarycentricRowReversedOrdringMatrix();

			if (m3.checkColumnsBarycentersIncreasingOrder()) {
				initialMatrix = m3;
				break;
			}

			Matrix m4 = m3.getBarycentricColumnReversedOrdringMatrix();
			if (m4.checkColumnsBarycentersIncreasingOrder()) {
				initialMatrix = m4;
				stop = true; // termination
			}
		}
		return bestMatrix;
	}

	/**
	 * @author Mohamed Kahla
	 * @since 05/06/2006
	 * @version 05062006H????
	 * 
	 * Build the 2D Array containing all the nodes 
	 * at the different levels according to there hierachy order 
	 * and relation established in the graphNodes list!
	 * After building Hierachy, Nodes and adding DummiesNodes
	 *
	 */
	private void fillMatrix() {

		printDebugMessage("fill Matrix!");

		// 1- from the list of all nodes make : 
		// a- every level in an arrays
		// b- we suppose dummies nodes added
		final ListIterator nodeItr = this.graphNode.listIterator(0);
		ArrayList[] nodeMatrix = new ArrayList[this.nbLevels];
		for (int i = 0; i < this.nbLevels; i++) {
			nodeMatrix[i] = new ArrayList();
		}
		while (nodeItr.hasNext()) {
			Node node = (Node) nodeItr.next();
			nodeMatrix[node.getLevel()].add(node);
		}
		if (this.graphNode.size() > 0) {
			this.nodeMatrix = new Node[this.nbLevels][1];
			for (int i = 0; i < this.nbLevels; i++) {
				Node[] tab = new Node[nodeMatrix[i].size()];
				for (int j = 0; j < tab.length; j++) {
					tab[j] = (Node) nodeMatrix[i].get(j);
				}
				this.nodeMatrix[i] = tab;
			}
		}

		printDebugMessage("Matrix.legnth : " + this.nodeMatrix.length);
		// printDebugMessage("Matrix[0].legnth : " + this.nodeMatrix[0].length);
	}
}
