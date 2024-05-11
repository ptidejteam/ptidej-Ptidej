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
package ptidej.ui.layout.repository.sugiyama;

import java.util.ArrayList;
import ptidej.ui.kernel.Entity;
import ptidej.ui.layout.repository.sugiyama.graph.INode;
import ptidej.ui.layout.repository.sugiyama.graph.Node;

/**
 * @author Mohamed kahla
 * @since07/02/2006
 * 
 */
// The Step 4 of Sugiyama's algorithme
public class XCoordonateSetter {
	private final double[][] relatifPosition;
	private final INode[][] matrix;
	private int maxWeidth; // the largest Entity

	public XCoordonateSetter(
		final INode[][] aMatrix,
		final double[][] aRelatifPosition) {

		this.matrix = aMatrix;
		this.relatifPosition = aRelatifPosition;
		this.maxWeidth = 0;
	}

	public void setXCoordonate() {
		this.setPositions();

		for (int i = 0; i < this.matrix.length; i++) {
			for (int j = 0; j < this.matrix[i].length; j++) {
				Entity e = this.matrix[i][j].getEntity();
				int x =
					(int) (this.relatifPosition[i][j]
							* (this.maxWeidth + SettingValue
								.getHorizentalDistanceEntities()) + SettingValue
						.getOffsetFromLeftSide());
				if (e != null) {
					int weidth = e.getDimension().width;
					// HACK 05-07-2006
					// we want that the center of the entity are aligned!
					int centriliser =
						((this.maxWeidth + SettingValue
							.getHorizentalDistanceEntities()) - weidth) / 2;
					// we center the entity
					x += centriliser;
				}
				else {
					x +=
						(this.maxWeidth + SettingValue
							.getHorizentalDistanceEntities()) / 2; // we center dummy nodes
				}

				// we set the position
				this.matrix[i][j].setPosition(x, this.matrix[i][j].getY());

				if (e != null) {
					// Not a dummy
					// This build seams to be very important
					// if you don't do it 
					// you will have refresh and synchronisation 
					// TODO: Prevent the mulitple creation of buttons!
					e.build(); // it shoud be done just after setting the position
				}
			}
		}
	}

	/**
	 * @author kahlamoh
	 * @since 14/08/2006
	 * 
	 */
	public void setXCoordonate2() {
		this.setPositions();
		// build the tabAxes
		// the idea is that we put all the 
		// Entities in the same axe together

		// 1-
		// we search for the number of the axes
		// is the farest entity in all the levels 
		// so we look only for the last one at each level
		// and we get the biggest position
		// for all levels
		int nbAxes = 0;
		for (int i = 0; i < this.relatifPosition.length; i++) {
			// the last position
			int x =
				(int) this.relatifPosition[i][this.relatifPosition[i].length - 1];
			if (x > nbAxes)
				nbAxes = x;
		}

		// to be coherent! 
		// the number
		nbAxes++;

		// 2-
		// we build the tabAxes!
		// evry axe contain all the nodes at that vertical axe
		Node[][] tabAxes = new Node[nbAxes][0];
		// contain the largest entity at evry axe!
		int[] maxWeidthInEachAxe = new int[nbAxes];

		// for all the axes
		for (int i = 0; i < nbAxes; i++) {
			// we search all the entities at the axe i
			ArrayList nodes = new ArrayList();
			int maxWeidth = 0;
			// all levels
			for (int j = 0; j < this.matrix.length; j++) {
				// for all nodes
				for (int k = 0; k < this.matrix[j].length; k++) {
					Entity e = this.matrix[j][k].getEntity();
					int currentXPos = (int) this.relatifPosition[j][k];
					// if this entity is at this axe
					if (currentXPos == i) {
						nodes.add(this.matrix[j][k]);
						if (e != null && (e.getDimension().width > maxWeidth))
							maxWeidth = e.getDimension().width;
						// we fond what we are looking for
						break;
					}

					// too far!
					// no entity from this level at this axe 
					if (currentXPos > i)
						break;
				}
			}

			// we set the maxWeidth
			maxWeidthInEachAxe[i] = maxWeidth;
			// we added the nodes of this axes!
			Node[] axeEntity = new Node[nodes.size()];
			for (int g = 0; g < axeEntity.length; g++) {
				axeEntity[g] = (Node) nodes.get(g);
			}
			// done
			tabAxes[i] = axeEntity;
		}

		// 3-
		// new we could set the best position for
		// evry axe according to maxWeidth
		// initial start position
		int currentXPosition = SettingValue.getOffsetFromLeftSide();
		// for all the axes
		for (int i = 0; i < tabAxes.length; i++) {
			// for all nodes in the current axe
			for (int j = 0; j < tabAxes[i].length; j++) {
				int x = currentXPosition;
				Entity e = tabAxes[i][j].getEntity();
				if (e != null) {
					int weidth = e.getDimension().width;
					// HACK 05-07-2006
					// we want that the center of the entity are aligned!
					int centriliser =
						((maxWeidthInEachAxe[i] + SettingValue
							.getHorizentalDistanceEntities()) - weidth) / 2;
					// we center the entity
					x += centriliser;
				}
				else {
					x +=
						(maxWeidthInEachAxe[i] + SettingValue
							.getHorizentalDistanceEntities()) / 2; // we center dummy nodes
				}

				// we set the position
				tabAxes[i][j].setPosition(x, tabAxes[i][j].getY());

				if (e != null) {
					// Not a dummy
					// This build seams to be very important
					// if you don't do it 
					// you will have refresh and synchronisation 
					// TODO: Prevent the mulitple creation of buttons!
					e.build(); // it shoud be done just after setting the position
				}
			}

			// we move to the left!
			currentXPosition +=
				maxWeidthInEachAxe[i]
						+ SettingValue.getHorizentalDistanceEntities();
		}
	}

	private void setPositions() {
		// Setting position 
		int initialposX = SettingValue.getOffsetFromLeftSide();
		int initialposY = SettingValue.getOffsetFromUpperSide();
		this.maxWeidth = 0;
		for (int i = 0; i < this.matrix.length; i++) {
			initialposX = 20;
			int maxHeight = 0;
			for (int j = 0; j < this.matrix[i].length; j++) {
				final Entity e = this.matrix[i][j].getEntity();
				this.matrix[i][j].setPosition(initialposX, initialposY);
				if (e != null) { // Not a dummy
					// This build seams to be very important
					// if you don't do it 
					// you will have refrsh and synchronisation 
					// TODO: Prevent the mulitple creation of buttons!
					e.build(); // it shoud be done just after setting the position
					initialposX += 20 + e.getDimension().width;
					if (e.getDimension().height > maxHeight) {
						maxHeight = e.getDimension().height;
					}
					// we look for the largest extity
					if (e.getDimension().width > this.maxWeidth) {
						this.maxWeidth = e.getDimension().width;
					}
				}
				else {
					initialposX += 60;
				}
			}
			initialposY +=
				maxHeight + SettingValue.getVerticalDistanceEntities();
		}
	}
}
