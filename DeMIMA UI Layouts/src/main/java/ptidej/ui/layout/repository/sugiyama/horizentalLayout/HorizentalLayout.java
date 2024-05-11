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

import ptidej.ui.layout.repository.sugiyama.Parents;
import ptidej.ui.layout.repository.sugiyama.graph.INode;

/**
 * @author mohamedkahla
 *
 */
public abstract class HorizentalLayout {
	protected final int nbLevels;
	protected int[][][] binaryGraphModel;
	protected INode[][] matrix;
	protected HorizentalSolver solver;

	/**
	 * Do the step III of sugiyama's algo
	 * by minimising destences between nodes
	 * setting dummy nodes whith the same x coordonate
	 * and conserving te order established before
	 */
	public HorizentalLayout(final INode[][] aMatrix) {
		this.matrix = aMatrix;
		this.nbLevels = this.matrix.length;
		// n levels ==> n-1 BinaryMatrix ; n >= 2
		if (this.nbLevels > 1)
			this.binaryGraphModel = new int[this.nbLevels - 1][1][1];
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

	public abstract void doHorizentalLayout();
	public abstract double[][] getVertexGlobalOrdring();

	/**
	 * 
	 *
	 */
	protected void buildBinaryGraphModel() {
		// a binary matrix for each consecutif level
		for (int i = 0; i < this.nbLevels - 1; i++) {

			printDebugMessage("Binary : " + i);

			this.binaryGraphModel[i] =
				buildBinaryMatrix(this.matrix[i], this.matrix[i + 1]);
		}
	}

	/**
	 * 
	 * @param AnUpLevel
	 * @param ADownLevel
	 * @return
	 */
	protected int[][] buildBinaryMatrix(
		final INode[] AnUpLevel,
		final INode[] ADownLevel) {
		final INode[] upLevel = AnUpLevel;
		final INode[] downLevel = ADownLevel;
		// initialised to 0 : default java
		int[][] binaryInterconnectionModel =
			new int[downLevel.length][upLevel.length];
		//		System.out.println("Binary : down, up : " + downLevel.length + ", "
		//			+ upLevel.length);
		// constarct the binary matrix
		int hightLevel = (upLevel[0]).getLevel();
		int lowLevel = hightLevel + 1; // if exist

		for (int downIndex = 0; downIndex < downLevel.length; downIndex++) {
			final INode downNode = downLevel[downIndex];
			final Parents parent = downNode.getParents();
			if (downNode.getLevel() == lowLevel) { // // this verification shoud be not necessery
				for (int upIndex = 0; upIndex < upLevel.length; upIndex++) {
					final INode upNode = upLevel[upIndex];
					// TODO  if there is a connection between the two nodes
					if (parent.contains(upNode)) {
						// TODO  this verification also shoud be not necessery
						if (upNode.getLevel() == hightLevel)
							binaryInterconnectionModel[downIndex][upIndex] = 1;
						else
							System.err
								.println("Nodes parent list is not consistent");
					}

					//					System.out.print(" "
					//						+ binaryInterconnectionModel[downIndex][upIndex]);
				}

				//				System.out.println();
			}
			else
				System.err.println("Parents Nodes Not coherent");
		}
		return binaryInterconnectionModel;
	}

}
