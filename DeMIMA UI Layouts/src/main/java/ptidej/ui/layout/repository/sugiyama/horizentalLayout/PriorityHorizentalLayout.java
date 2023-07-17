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
 * @author mohamedkahla
 * @date 27-06-2006
 */
// TODO: Very good idea to encapsulate the solver in this class
// and to factor out common algo.
public final class PriorityHorizentalLayout extends HorizentalLayout {
	/**
	 * The Pririority programming layout method
	 */
	public PriorityHorizentalLayout(final INode[][] aMatrix) {
		super(aMatrix);
	}

	// Priority programming
	public void doHorizentalLayout() {
		// setEntitiesPositions2();

		if (super.nbLevels > 1) {
			buildBinaryGraphModel();
		}

		super.solver =
			new PriorityHorizentalSolver(
				super.binaryGraphModel,
				super.matrix,
				super.nbLevels);
		// WE SOLVE THE PROBLEM
		((PriorityHorizentalSolver) super.solver).solvePriorityHorizental();
	}

	/**
	 * 
	 */
	public double[][] getVertexGlobalOrdring() {
		return super.solver.getVertexGlobalOrdring();
	}
}
