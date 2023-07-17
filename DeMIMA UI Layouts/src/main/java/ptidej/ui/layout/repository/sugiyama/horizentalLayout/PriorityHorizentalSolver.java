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
package ptidej.ui.layout.repository.sugiyama.horizentalLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import ptidej.ui.layout.repository.sugiyama.IntegerCouple;
import ptidej.ui.layout.repository.sugiyama.SettingValue;
import ptidej.ui.layout.repository.sugiyama.graph.DummyNode;
import ptidej.ui.layout.repository.sugiyama.graph.INode;

/**
 * 
 * @author kahlamoh
 *
 */
public class PriorityHorizentalSolver extends HorizentalSolver {
	private int[][] downPriority; // the priority of each vertex in each level
	private int[][] upPriority; // 

	/**
	 * 
	 * @param aVertexGlobalOrdring the relatif position of the vertex
	 (there ranks, first, second and so on ...) it's like indexes
	 *   and finally we duild the X coordonates from them by normalisation (negatives indexes..)
	 * @param aBinaryGraphModel
	 * @param aMatrix
	 * @param aLevel
	 */
	public PriorityHorizentalSolver(
		final int[][][] aBinaryGraphModel,
		final INode[][] aMatrix,
		final int aLevel) {

		super(aMatrix, aBinaryGraphModel, aLevel);

		// vertexGlobalOrdring are build by super class
		this.downPriority = new int[super.vertexGlobalOrdring.length][1];
		this.upPriority = new int[super.vertexGlobalOrdring.length][1];
	}

	private boolean deboging = false;

	/**
	 * 
	 */
	public boolean getDebugStatus() {
		return this.deboging;

	}

	/**
	 * 
	 * 
	 */
	public void activateDebuging() {
		this.deboging = true;
	}

	/**
	 * 
	 * 
	 */
	public void deActivateDebuging() {
		this.deboging = false;
	}

	/**
	 * 
	 * 
	 */
	public void printDebugMessage(String message) {
		if (getDebugStatus())
			System.out.println(message);

	}

	/**
	 * 
	 *
	 */
	public void solvePriorityHorizental() {
		if (super.nbLevel > 1) {

			setDownPriority();

			if (getDebugStatus()) {
				System.out.println("\n setDownPriority : ");
				for (int i = 0; i < this.downPriority.length; i++) {
					for (int j = 0; j < this.downPriority[i].length; j++) {
						System.out.print(" " + this.downPriority[i][j]);
					}
					System.out.println();
				}
			}

			setUpPriority();

			if (getDebugStatus()) {
				System.out.println("\n setUpPriority : ");
				for (int i = 0; i < this.upPriority.length; i++) {
					for (int j = 0; j < this.upPriority[i].length; j++) {
						System.out.print(" " + this.upPriority[i][j]);
					}
					System.out.println();
				}
				System.out.println();

				System.out.println("\n vertexGlobalOrdring I: ");
				for (int i = 0; i < super.vertexGlobalOrdring.length; i++) {
					for (int j = 0; j < super.vertexGlobalOrdring[i].length; j++) {
						System.out.print(" " + super.vertexGlobalOrdring[i][j]);
					}
					System.out.println();
				}

			}

			downUpPriorityOptimization();

			if (getDebugStatus()) {

				System.out.println("\n vertexGlobalOrdring II: ");
				for (int i = 0; i < super.vertexGlobalOrdring.length; i++) {
					for (int j = 0; j < super.vertexGlobalOrdring[i].length; j++) {
						System.out.print(" " + super.vertexGlobalOrdring[i][j]);
					}
					System.out.println();
				}
				System.out.println();
			}

			globalOrdringNormaliser();

			if (getDebugStatus()) {

				System.out.println("\n vertexGlobalOrdring III: ");
				for (int i = 0; i < super.vertexGlobalOrdring.length; i++) {
					for (int j = 0; j < super.vertexGlobalOrdring[i].length; j++) {
						System.out.print(" " + super.vertexGlobalOrdring[i][j]);
					}
					System.out.println();
				}
				System.out.println();
			}
		}
	}

	/**
	 * 
	 *
	 */
	// the nodes at levelIndex are not desplaced
	// but nodes at levelIndex+1 are
	// so we will focus at the upper connectivities and priority
	// when getting down
	// children will try to be the closest from there parents
	// 02-07-2006
	private void downUpPriorityOptimization() {

		int maxIterations = SettingValue.getMaxIterationsHorizentalLayout();
		boolean isMakingProgress = true;
		// TODO
		// To be more efficient, some other could be done
		// like a convergence test 
		// when an action cancel the other and
		// same for the other!

		// 02-07-2006
		while (maxIterations > 0 && isMakingProgress) {

			// the first level will not be changed
			// Down
			for (int i = 1; i < this.upPriority.length; i++) {
				// we repeat until no changes!
				boolean isMakingDownProgress = true;
				while (isMakingDownProgress) {
					// i is the movable layer
					if (downPriorityBarycenterHeuristic(i)) {
						isMakingDownProgress = true;
						isMakingProgress = true;
					}
					else
						isMakingDownProgress = false;
				}
			}

			// the last level will not be changed
			// Up
			for (int i = this.downPriority.length - 2; i >= 0; i--) {
				// we repeat untel no changes!
				boolean isMakingUpProgress = true;
				while (isMakingUpProgress) {
					// i is the movable layer
					if (upPriorityBarycenterHeuristic(i)) {
						isMakingUpProgress = true;
						isMakingProgress = true;
					}
					else
						isMakingUpProgress = false;
				}
			}
			maxIterations--;
		}
	}

	/**
	 * @param 	tabPriority contain the priority of the Nodes in the
	 * 			aMovableLayer 
	 * @param 	aMovableLayer the x grid relatif coordonates 
	 * 			could change whith respect to there order
	 * @param 	anIndex of node to try to move in the movable layer
	 * @param 	aDirection -1 or +1 only 
	 * 			-1: to the left
	 *  		+1: too the right
	 * @return 	true if we can move the current node
	 * 			false otherwise
	 */
	private boolean pushMove(
		int[] tabPriority,
		double[] aMovableLayer,
		int anIndex,
		int aDirection) {

		// test secuirity ! 
		// SHOUD never hapens!
		if (aDirection != 1 && aDirection != -1) {
			System.err.println("the param aDirection must be 1 or -1 only! ");
			return false;
		}

		boolean canMove = false;

		// Margin push case
		if ((aDirection < 0 && anIndex == 0)
				|| (aDirection > 0 && anIndex == aMovableLayer.length - 1)) {
			canMove = true;
		}

		// free push case
		else if (aMovableLayer[anIndex] + aDirection != aMovableLayer[anIndex
				+ aDirection]) {
			canMove = true;
		}

		// recursive push case
		// TODO
		// >= and > make differents drawing!
		// > make spaces preferable for readability
		// >= 
		else if (tabPriority[anIndex] >= tabPriority[anIndex + aDirection]) {
			// recursion!
			canMove =
				pushMove(tabPriority, aMovableLayer, anIndex + aDirection, // here is the differences!
					aDirection);
		}

		// make a move
		if (canMove) {
			aMovableLayer[anIndex] = aMovableLayer[anIndex] + aDirection;
		}

		return false;
	}

	/**
	 * 
	 * @param aMovableLayerIndex
	 * we move vertexes in aMovableLayer according 
	 * to the connectivity to there parents 'we go down'
	 * @return true if some thing is done
	 * 		false otherwise 
	 */
	private boolean downPriorityBarycenterHeuristic(int aMovableLayerIndex) {
		boolean isMakingProgress = false;

		double[] aMovableLayer = super.vertexGlobalOrdring[aMovableLayerIndex];

		IntegerCouple[] priorityQueue = new IntegerCouple[aMovableLayer.length];
		for (int i = 0; i < aMovableLayer.length; i++) {
			priorityQueue[i] =
				new IntegerCouple(i, this.upPriority[aMovableLayerIndex][i]);
		}
		// we sort for priority
		// in ascending order
		// and if two nodes haves the same priority 
		// we srot them according to there indexes

		// sort according to priority
		priorityQueue = sort(priorityQueue);

		for (int i = 0; i < priorityQueue.length; i++) {
			int index = priorityQueue[i].getIndex();

			double barycenter =
				computeUpperBarycenter(aMovableLayerIndex, index);

			double deplacement = barycenter - aMovableLayer[index];

			if (deplacement > 0) { // to the right
				isMakingProgress =
					pushMove(
						this.upPriority[aMovableLayerIndex],
						aMovableLayer,
						index,
						//	deplacement);
						+1); // to the right
			}
			else if (deplacement < 0) { // to the left
				isMakingProgress =
					pushMove(
						this.upPriority[aMovableLayerIndex],
						aMovableLayer,
						index,
						//	deplacement);
						-1); // to the left
			}
		}
		return isMakingProgress;
	}

	/**
	 * grid relatif x positions
	 * @param aMovableLayer
	 * we move vertexes in aMovableLayer according 
	 * to the connectivity to there children 'we go up'
	 * @return true if some thing is done
	 * 		false otherwise 
	 */
	private boolean upPriorityBarycenterHeuristic(int aMovableLayerIndex) {
		boolean isMakingProgress = false;

		double[] aMovableLayer = super.vertexGlobalOrdring[aMovableLayerIndex];

		IntegerCouple[] priorityQueue = new IntegerCouple[aMovableLayer.length];
		for (int i = 0; i < aMovableLayer.length; i++) {
			priorityQueue[i] =
				new IntegerCouple(i, this.downPriority[aMovableLayerIndex][i]);
		}
		// we sort for priority
		// in ascending order
		// stable sorting!
		// sort according to priority
		priorityQueue = sort(priorityQueue);

		for (int i = 0; i < priorityQueue.length; i++) {
			int index = priorityQueue[i].getIndex();

			double barycenter =
				computeLowerBarycenter(aMovableLayerIndex, index);

			double deplacement = barycenter - aMovableLayer[index];

			// ////////////////////

			if (deplacement > 0) { // to the right
				isMakingProgress =
					pushMove(
						this.downPriority[aMovableLayerIndex],
						aMovableLayer,
						index,
						//	deplacement);
						+1); // to the right
			}
			else if (deplacement < 0) { // to the left
				isMakingProgress =
					pushMove(
						this.downPriority[aMovableLayerIndex],
						aMovableLayer,
						index,
						//	deplacement);
						-1); // to the left
			}
		}

		return isMakingProgress;
	}

	//	private boolean doublePushMove(
	//		int[] tabPriority,
	//		double[] aMovableLayer,
	//		int anIndex,
	//		double aStep) {
	//
	//		// aStep is a double between -1 and +1
	//
	//		// test secuirity ! 
	//		// SHOUD never hapens!
	//		//		if (aDirection != 1 && aDirection != -1) {
	//		//			System.out
	//		//				.println("the param aDirection must be 1 or -1 only! ");
	//		//			return false;
	//		//		}
	//
	//		boolean canMove = false;
	//		int aDirection;
	//		if (aStep > 0)
	//			aDirection = 1;
	//		else
	//			aDirection = -1;
	//
	//		// Margin push case
	//		if ((aDirection < 0 && anIndex == 0)
	//			|| (aDirection > 0 && anIndex == aMovableLayer.length - 1)) {
	//			canMove = true;
	//		}
	//
	//		// free spot case
	//		else if (aStep > 1
	//			&& (aMovableLayer[anIndex] + aStep < aMovableLayer[anIndex + 1])) {
	//			// if there is a space to move 
	//			canMove = true;
	//		}
	//		else if (aStep < -1
	//			&& (aMovableLayer[anIndex] + aStep > aMovableLayer[anIndex - 1])) {
	//			// if there is a space to move 
	//			canMove = true;
	//		}
	//
	//		// recursive push case
	//		else if (tabPriority[anIndex] >= tabPriority[anIndex + aDirection]) {
	//			// recursion!
	//			canMove = doublePushMove(tabPriority, aMovableLayer, anIndex
	//				+ aDirection, // here is the difference!
	//				aStep);
	//		}
	//
	//		// make a move
	//		if (canMove) {
	//			aMovableLayer[anIndex] = aMovableLayer[anIndex] + aStep;
	//		}
	//
	//		return false;
	//	}

	/**
	 * 
	 * @param tab
	 * @return
	 */
	private IntegerCouple[] sort(IntegerCouple[] tab) {
		//		 ascending order
		// sort according to index (when equal priority)
		// so minimum index first
		IntegerCouple[] sortedQueue = new IntegerCouple[tab.length];
		for (int i = 0; i < tab.length; i++) {
			int nextMaxPriorityIndex = -1; // next value to write
			double maxPriority = -1;
			for (int j = 0; j < tab.length; j++) {
				if (tab[j] != null) {
					double value = tab[j].getValue();
					// TODO
					// > or >= make some differences
					if (value >= maxPriority) {
						maxPriority = value;
						nextMaxPriorityIndex = j;
					}
				}
			}
			sortedQueue[i] = tab[nextMaxPriorityIndex];
			tab[nextMaxPriorityIndex] = null;
		}

		return sortedQueue;
	}

	/**
	 * 
	 *
	 */
	private void setDownPriority() {
		// the last level don't have lower connection
		for (int i = 0; i < this.downPriority.length - 1; i++) {
			this.downPriority[i] = new int[super.vertexGlobalOrdring[i].length];
			final List dummyList = new ArrayList();
			int maxConnectivity = 0;
			for (int j = 0; j < this.downPriority[i].length; j++) {
				final INode node = super.matrix[i][j];
				if (node instanceof DummyNode) {
					// contain the index of all dummy of that level
					dummyList.add(Integer.valueOf(j));
				}
				else {
					int priority = computeLowerConnectivity(i, j);
					this.downPriority[i][j] = priority;
					maxConnectivity += priority;
				}
			}
			// now we have to set the pririority of Dummies 
			// which should be the highest
			ListIterator iter = dummyList.listIterator();
			while (iter.hasNext()) {
				Integer index = (Integer) iter.next();
				this.downPriority[i][index.intValue()] = maxConnectivity + 1;
			}
		}

		// now we set the last level
		this.downPriority[this.downPriority.length - 1] =
			new int[super.vertexGlobalOrdring[this.downPriority.length - 1].length];
	}

	/**
	 * 
	 *
	 */
	private void setUpPriority() {
		// we set the first level
		this.upPriority[0] = new int[super.vertexGlobalOrdring[0].length];
		// the first level don't have upper connection
		for (int i = 1; i < this.upPriority.length; i++) {
			this.upPriority[i] = new int[super.vertexGlobalOrdring[i].length];
			final List dummyList = new ArrayList();
			int maxConnectivity = 0;
			for (int j = 0; j < this.upPriority[i].length; j++) {
				final INode node = super.matrix[i][j];
				if (node instanceof DummyNode) {
					// contain the index of all dummy of that level
					dummyList.add(Integer.valueOf(j));
				}
				else {
					int priority = computeUpperConnectivity(i, j);
					this.upPriority[i][j] = priority;
					maxConnectivity += priority;
				}
			}
			// now we have to set the pririority of Dummies 
			// which should be the highest
			ListIterator iter = dummyList.listIterator();
			while (iter.hasNext()) {
				Integer index = (Integer) iter.next();
				this.upPriority[i][index.intValue()] = maxConnectivity + 1;
			}
		}
	}

}
