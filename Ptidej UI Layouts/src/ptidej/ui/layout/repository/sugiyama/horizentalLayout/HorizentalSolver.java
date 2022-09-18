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
package ptidej.ui.layout.repository.sugiyama.horizentalLayout;

import ptidej.ui.layout.repository.sugiyama.graph.INode;

/**
 * @author kahlamoh
 *
 */
public abstract class HorizentalSolver {
	// TODO Make them private!
	protected final int[][][] binaryGraphModel;
	protected final INode[][] matrix;
	protected final int nbLevel;
	protected double[][] vertexGlobalOrdring; // a grid!

	/**
	 * @param aMatrix
	 * @param aVertexGlobalOrdring aVertexGlobalOrdring the relatif positions of the vertexes
	 *  (there ranks, first, second and so on ...) it's like indexes
	 *   and finally we duild the X coordonates from them by normalisation (negatives indexes..)
	 * @param aBinaryGraphModel
	 * @param aLevel
	 */
	public HorizentalSolver(
		final INode[][] aMatrix,
		final int[][][] aBinaryGraphModel,
		final int aLevel) {

		this.matrix = aMatrix;
		// we build it here
		buildVertexGlobalOrdring();
		this.binaryGraphModel = aBinaryGraphModel;
		this.nbLevel = aLevel;
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
	private void printDebugMessaege(String message) {
		if (getDebugStatus())
			System.out.println(message);

	}

	/**
	 * 
	 *
	 */
	//	 29-06-2006
	private void buildVertexGlobalOrdring() {
		this.vertexGlobalOrdring = new double[this.matrix.length][1];
		for (int i = 0; i < this.matrix.length; i++) {
			this.vertexGlobalOrdring[i] = new double[this.matrix[i].length];
			for (int j = 0; j < this.matrix[i].length; j++) {
				this.vertexGlobalOrdring[i][j] = (j + 1);
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	// 02-07-2006
	public double[][] getVertexGlobalOrdring() {
		return this.vertexGlobalOrdring;
	}

	// 02-07-2006
	// we look for the little relatif position 
	// if the littest is > 0 we do nothing
	// else we normalise whit abs(min value)+1
	protected void globalOrdringNormaliser() {
		double minValue = Integer.MAX_VALUE;
		for (int i = 0; i < this.vertexGlobalOrdring.length; i++) {
			for (int j = 0; j < this.vertexGlobalOrdring[i].length; j++) {
				if (minValue > this.vertexGlobalOrdring[i][j])
					minValue = this.vertexGlobalOrdring[i][j];
			}
		}

		if (minValue < 0) {
			for (int i = 0; i < this.vertexGlobalOrdring.length; i++) {
				for (int j = 0; j < this.vertexGlobalOrdring[i].length; j++) {
					this.vertexGlobalOrdring[i][j] += Math.abs(minValue);
				}
			}
		}
		else if (minValue > 0) {
			for (int i = 0; i < this.vertexGlobalOrdring.length; i++) {
				for (int j = 0; j < this.vertexGlobalOrdring[i].length; j++) {
					this.vertexGlobalOrdring[i][j] -= minValue;
				}
			}
		}

	}

	/**
	 * 
	 * @param aLevel
	 * @param anIndex
	 * @return
	 */
	protected double computeUpperBarycenter(int aLevel, int anIndex) { // a Row
		double result = 0;
		for (int j = 0; j < this.binaryGraphModel[aLevel - 1][anIndex].length; j++) {
			result +=
				this.vertexGlobalOrdring[aLevel - 1][j]
						* this.binaryGraphModel[aLevel - 1][anIndex][j]; // [level][down][up]
		}
		result /= computeUpperConnectivity(aLevel, anIndex);
		return result;
	}

	/**
	 * 
	 * @param aLevel
	 * @param anIndex
	 * @return
	 */
	protected double computeLowerBarycenter(int aLevel, int anIndex) { // a column
		double result = 0;
		// the sum of the x coordonates of vetexes connect to this vertex
		for (int l = 0; l < this.binaryGraphModel[aLevel].length; l++) {
			printDebugMessaege("l" + l);
			result +=
				this.vertexGlobalOrdring[aLevel + 1][l]
						* this.binaryGraphModel[aLevel][l][anIndex]; // [level][down][up]
		}

		// divide by the number or connectivities
		printDebugMessaege("result LowerBary 0 :" + result);
		result /= computeLowerConnectivity(aLevel, anIndex);
		printDebugMessaege("result LowerBary 1 :" + result);
		return result;
	}

	/**
	 * 
	 * @param aLevel
	 * @param anIndex
	 * @return
	 */
	protected int computeUpperConnectivity(int aLevel, int anIndex) {// a Row
		int result = 0;

		for (int j = 0; j < this.binaryGraphModel[aLevel - 1][anIndex].length; j++) {
			result += this.binaryGraphModel[aLevel - 1][anIndex][j];// [level][down][up]
		}
		return result;
	}

	/**
	 * @param the level
	 * @param the index of a vertex
	 * @return the number of connection between the vertex and the verteces
	 * at the under level
	 */
	protected int computeLowerConnectivity(int aLevel, int anIndex) {// a column
		// aLevel must be less than the size of the array 
		int result = 0;
		for (int l = 0; l < this.binaryGraphModel[aLevel].length; l++) {
			printDebugMessaege("result LowerBary a :" + result);
			result += this.binaryGraphModel[aLevel][l][anIndex];// [level][down][up]
		}
		printDebugMessaege("result LowerBary b :" + result);
		return result;
	}

}
