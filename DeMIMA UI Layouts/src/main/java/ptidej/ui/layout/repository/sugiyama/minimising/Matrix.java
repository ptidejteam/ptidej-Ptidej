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

import java.util.Arrays;
import ptidej.ui.layout.repository.sugiyama.NodeCouple;
import ptidej.ui.layout.repository.sugiyama.Parents;
import ptidej.ui.layout.repository.sugiyama.graph.INode;
import ptidej.ui.layout.repository.sugiyama.graph.Node;

/**
 *  date : 30/05/2006
 * @Author : Mohamed Kahla  
 */
// TODO: What does a matrix contains after being built?
public class Matrix {
	// Representing the binary interconnection between the 
	// vertexes of the level k with the level k+1
	// whith binaryInterconnectionModel[i][j] = 1 if there is 
	// an arc from i to j 0 other wise
	private int[][] binaryInterconnectionModel;
	private int crossingNb = 0;
	// a lists of nodes and Dummies Nodes !
	private INode[] upLevel; // level i
	private INode[] downLevel; // level i+1
	private double[] rowBarycenters;
	private double[] columnBarycenters;
	private INode[] downLevelOrdred;
	private INode[] upLevelOrdred;
	private INode[] downLevelRiverseOrdred;
	private INode[] upLevelReverseOrdred;
	private boolean rowBarycentersComputed = false;
	private boolean columnBarycentersComputed = false;
	private boolean rowBarycentersOrdred = false;
	private boolean columnBarycentersOrdred = false;
	private boolean rowBarycentersReverseOrdred = false;
	private boolean columnBarycentersReverseOrdred = false;
	private boolean crossingNbComputed = false;

	/**
	 * 
	 * @return
	 */
	public int[][] getBinaryInterconnectionModel() {
		return this.binaryInterconnectionModel;
	}

	/**
	 * 
	 * @param AnUpLevel
	 * @param ADownLevel
	 */
	// @param MUST BE 2 Arrays with nodes
	// of TWO CONSECUTIF LEVELS with coherent 
	// informations
	public Matrix(final INode[] AnUpLevel, final INode[] ADownLevel) {
		this.upLevel = AnUpLevel;
		this.downLevel = ADownLevel;

		// initialised to 0 : default java
		this.binaryInterconnectionModel =
			new int[this.downLevel.length][this.upLevel.length];
		this.rowBarycenters = new double[this.downLevel.length];
		this.columnBarycenters = new double[this.upLevel.length];

		// constarct the binary matrix
		int hightLevel = (this.upLevel[0]).getLevel();
		int lowLevel = hightLevel + 1; // if exist

		for (int downIndex = 0; downIndex < this.downLevel.length; downIndex++) {
			final INode downNode = this.downLevel[downIndex];
			final Parents parent = downNode.getParents();
			if (downNode.getLevel() == lowLevel) { // // this verification shoud be not necessery
				for (int upIndex = 0; upIndex < this.upLevel.length; upIndex++) {
					final INode upNode = this.upLevel[upIndex];
					// TODO  if there is a connection between the two nodes
					if (parent.contains(upNode)) {
						// TODO  this verification also shoud be not necessery
						if (upNode.getLevel() == hightLevel)
							this.binaryInterconnectionModel[downIndex][upIndex] =
								1;
						else
							System.err
								.println("Nodes parent  lists Not coherent");
					}
				}
			}
			else
				System.err.println("Parents Nodes Not coherent");
		}
	} //  end

	/**
	 * 
	 * @return
	 */
	public INode[] getUpLevel() {
		return this.upLevel;
	}

	/**
	 * 
	 * @return
	 */
	public INode[] getDownLevel() {
		return this.downLevel;
	}

	/**
	 * 
	 * @param aMatrix
	 * @return
	 */
	public boolean equals(Matrix aMatrix) {

		if (this.upLevel.length != aMatrix.getUpLevel().length
				|| this.downLevel.length != aMatrix.getDownLevel().length)
			return false;

		for (int i = 0; i < this.upLevel.length; i++) {
			if (this.upLevel[i] != aMatrix.getUpLevel()[i])
				return false;
		}

		for (int i = 0; i < this.downLevel.length; i++) {
			if (this.downLevel[i] != aMatrix.getDownLevel()[i])
				return false;
		}

		return true;
	}

	/**
	 * 
	 * @return
	 */
	public boolean checkRowsBarycentersIncreasingOrder() {
		if (!this.rowBarycentersComputed) { // effeciency !
			computeRowBarycenters();
			this.rowBarycentersComputed = true;
		}
		for (int i = 0; i < this.rowBarycenters.length - 1; i++) {
			if (this.rowBarycenters[i] > this.rowBarycenters[i])
				return false;
		}
		return true;
	}

	/**
	 * 
	 * @return
	 */
	public boolean checkColumnsBarycentersIncreasingOrder() {
		if (!this.columnBarycentersComputed) { // effeciency !
			computeColumnBarycenters();
			this.columnBarycentersComputed = true;
		}
		for (int i = 0; i < this.columnBarycenters.length - 1; i++) {
			if (this.columnBarycenters[i] > this.columnBarycenters[i])
				return false;
		}
		return true;
	}

	// The essential of the following  methods is to 
	// reorder  the vertexes of the  row or column  
	// according to the row or column barycenter
	// they are ordered from the smallest to the largest
	// when equality, the original order is maintaned

	/**
	 * 
	 */
	// constract and return the barycentric row ordring Matrix 
	// of this MATRIX
	public Matrix getBarycentricRowOrdringMatrix() {
		if (!this.rowBarycentersOrdred) {
			this.rowBarycentersOrdred = true;
			this.downLevelOrdred = new Node[this.downLevel.length]; // level i+1
			final NodeCouple[] barycentersRowNodes =
				new NodeCouple[this.downLevel.length];
			if (!this.rowBarycentersComputed) { // effeciency !
				computeRowBarycenters();
				this.rowBarycentersComputed = true;
			}
			// we set the nodes whith there respectif Barycenter Value
			for (int i = 0; i < this.rowBarycenters.length; i++) {
				barycentersRowNodes[i] =
					new NodeCouple(this.downLevel[i], this.rowBarycenters[i]);
			}

			// we sort rowBarycenter in an assending order
			// stable too!
			Arrays.sort(barycentersRowNodes);

			for (int i = 0; i < this.rowBarycenters.length; i++) {
				this.downLevelOrdred[i] = barycentersRowNodes[i].getNode();
			}

		}
		return new Matrix(this.upLevel, this.downLevelOrdred);
	}

	/**
	 * 
	 * @return
	 */
	// constract and return the barycentric column ordring Matrix 
	// of this MATRIX
	public Matrix getBarycentricColumnOrdringMatrix() {
		if (!this.columnBarycentersOrdred) {
			this.columnBarycentersOrdred = true;
			this.upLevelOrdred = new Node[this.upLevel.length]; // level i
			final NodeCouple[] barycentersColumnNodes =
				new NodeCouple[this.upLevel.length];
			if (!this.columnBarycentersComputed) { // effeciency !
				this.computeColumnBarycenters();
				this.columnBarycentersComputed = true;
			}
			// we set the nodes whith there respectif Barycenter Value
			for (int i = 0; i < this.columnBarycenters.length; i++) {
				barycentersColumnNodes[i] =
					new NodeCouple(this.upLevel[i], this.columnBarycenters[i]);
			}
			// we sort columnBarycenter in an ascending order
			// stable too!
			Arrays.sort(barycentersColumnNodes);

			for (int i = 0; i < this.columnBarycenters.length; i++) {
				this.upLevelOrdred[i] = barycentersColumnNodes[i].getNode();
			}
		}
		return new Matrix(this.upLevelOrdred, this.downLevel);
	}

	// reversing Rows and Columns
	// when we want to get a REVERSED Row  or a Column order in a Matrix
	// when some rows have an equal Barycenter we reoder them
	// as if we have rows a, b, c and d whith equal barycenter
	// and a in the 1er, b in 2nd, c in 3trd, and d in 4th places
	// consicutif places value of Row or Column barycenters are ORDRED !
	// so we put :  a in 4th, b in 3rd, c in 2nd and d in 1st places
	// and that dont modify the rows barycenter nither there
	// order but do for columns
	// (respectively columns)

	/**
	 * 
	 */
	// constract and return the barycentric row REVERSED  ordring Matrix 
	// of this MATRIX
	public Matrix getBarycentricRowReversedOrdringMatrix() {
		// TODO
		// For performences 
		// have the same barycenters
		// so shoud be transfered
		// more if we modify row order this dont affect column barycenters and vice vers ca 
		// BUT we must respect the new order of nodes  
		// set column/row barycenters! METHODES!!!!
		// from couples or ...
		// is it all ready computed ?
		// TODO It's not done for now 

		// first it's important to remember that the values of Row 
		// barycenters are ORDRED
		if (!this.rowBarycentersReverseOrdred) {
			this.rowBarycentersReverseOrdred = true;

			if (!this.rowBarycentersOrdred) // it shoud be done first but ..
				getBarycentricRowOrdringMatrix();
			this.downLevelRiverseOrdred = new Node[this.downLevelOrdred.length]; // level i+1
			int startIndexReverse = 0;
			int nbReverse = 0;
			double currentBarycenterValue = this.rowBarycenters[0];
			if (this.downLevelRiverseOrdred.length > 1) {
				for (int i = 1; i < this.downLevelRiverseOrdred.length; i++) {
					if (i != this.downLevelRiverseOrdred.length - 1) { // not end of array!
						if (this.rowBarycenters[i] == currentBarycenterValue)
							nbReverse++;
						else {
							// reverse from start to stop
							// Major Modification
							// 06-06-2006
							int readIndex = startIndexReverse;
							int writeIndex = startIndexReverse + nbReverse;
							while (readIndex <= startIndexReverse + nbReverse) {
								this.downLevelRiverseOrdred[writeIndex] =
									this.downLevelOrdred[readIndex];
								readIndex++;
								writeIndex--;
							}
							// move up
							startIndexReverse = i;
							currentBarycenterValue = this.rowBarycenters[i];
							nbReverse = 0;
						}
					}
					else { // end of array!
						if (this.rowBarycenters[i] == currentBarycenterValue) {
							int readIndex = startIndexReverse;
							int writeIndex = startIndexReverse + nbReverse + 1;
							while (readIndex <= startIndexReverse + nbReverse
									+ 1) {
								this.downLevelRiverseOrdred[writeIndex] =
									this.downLevelOrdred[readIndex];
								readIndex++;
								writeIndex--;
							}
						}
						else {
							int readIndex = startIndexReverse;
							int writeIndex = startIndexReverse + nbReverse;
							while (readIndex <= startIndexReverse + nbReverse) {
								this.downLevelRiverseOrdred[writeIndex] =
									this.downLevelOrdred[readIndex];
								readIndex++;
								writeIndex--;
							}
							this.downLevelRiverseOrdred[i] =
								this.downLevelOrdred[i];
						}
					}

				}
			} // if

			else
				this.downLevelRiverseOrdred[0] = this.downLevelOrdred[0];
		}

		return new Matrix(this.upLevel, this.downLevelRiverseOrdred);
	}

	/**
	 * 
	 * @return
	 */
	// constract and return the barycentric column REVERSED  ordring Matrix 
	// of this MATRIX
	public Matrix getBarycentricColumnReversedOrdringMatrix() {

		if (!this.columnBarycentersReverseOrdred) {
			this.columnBarycentersReverseOrdred = true;

			if (!this.columnBarycentersOrdred) // it shoud be done first but ..
				getBarycentricColumnOrdringMatrix();

			this.upLevelReverseOrdred = new Node[this.upLevelOrdred.length]; // level i
			int startIndexReverse = 0;
			int nbReverse = 0;
			double currentBarycenterValue = this.columnBarycenters[0];
			// reverse from start to stop
			// Major Modification
			// 06-06-2006
			if (this.upLevelReverseOrdred.length > 1) {
				for (int i = 1; i < this.upLevelReverseOrdred.length; i++) {
					if (i != this.upLevelReverseOrdred.length - 1) { // not end of array!
						if (this.columnBarycenters[i] == currentBarycenterValue)
							nbReverse++;
						else {
							// reverse from start to stop
							// Major Modification
							// 06-06-2006
							int readIndex = startIndexReverse;
							int writeIndex = startIndexReverse + nbReverse;
							while (readIndex <= startIndexReverse + nbReverse) {
								this.upLevelReverseOrdred[writeIndex] =
									this.upLevelOrdred[readIndex];
								readIndex++;
								writeIndex--;
							}
							// move up
							startIndexReverse = i;
							currentBarycenterValue = this.columnBarycenters[i];
							nbReverse = 0;
						}
					}
					else { // end of array!
						if (this.columnBarycenters[i] == currentBarycenterValue) {
							int readIndex = startIndexReverse;
							int writeIndex = startIndexReverse + nbReverse + 1;
							while (readIndex <= startIndexReverse + nbReverse
									+ 1) {
								this.upLevelReverseOrdred[writeIndex] =
									this.upLevelOrdred[readIndex];
								readIndex++;
								writeIndex--;
							}
						}
						else {
							int readIndex = startIndexReverse;
							int writeIndex = startIndexReverse + nbReverse;
							while (readIndex <= startIndexReverse + nbReverse) {
								this.upLevelReverseOrdred[writeIndex] =
									this.upLevelOrdred[readIndex];
								readIndex++;
								writeIndex--;
							}
							this.upLevelReverseOrdred[i] =
								this.upLevelOrdred[i];
						}
					}

				}
			} // if

			else
				this.upLevelReverseOrdred[0] = this.upLevelOrdred[0];
		}

		return new Matrix(this.upLevelReverseOrdred, this.downLevel);
	}

	/**
	 * 
	 *
	 */
	// Compute rowBarycenters
	private void computeRowBarycenters() {
		for (int i = 0; i < this.rowBarycenters.length; i++) {
			// Couple(int index, double value)
			this.rowBarycenters[i] = computeRowBarycenter(i);
		}
	}

	/**
	 * 
	 *
	 */
	// Compute columnBarycenters
	private void computeColumnBarycenters() {
		for (int j = 0; j < this.columnBarycenters.length; j++) {
			// Couple(int index, double value)
			this.columnBarycenters[j] = this.computeColumnBarycenter(j);
		}
	}

	/**
	 * 
	 * @return
	 */
	// Cumpute if not done before and return the number of crossings in this MATRIX
	// between two levels  !!!! TWO LEVELS !!!
	// M(i)
	public int getCrossingNumber() {

		if (!this.crossingNbComputed) {
			this.crossingNbComputed = true;
			this.crossingNb = 0;
			for (int j = 0; j < this.binaryInterconnectionModel.length - 1; j++) {
				for (int k = j + 1; k < this.binaryInterconnectionModel.length; k++) {
					for (int a = 0; a < this.binaryInterconnectionModel[j].length - 1; a++) {
						for (int b = a + 1; b < this.binaryInterconnectionModel[j].length; b++) {
							this.crossingNb +=
								this.binaryInterconnectionModel[j][b]
										* this.binaryInterconnectionModel[k][a];
						}
					}
				}
			}
		}

		return this.crossingNb;
	}

	/**
	 * 
	 * @param aRow
	 * @return
	 */
	// Compute Row Barycenter of a binary interconnection Matrix
	private double computeRowBarycenter(int aRow) {
		double barycenter = 0;
		double rowSum = 0;
		for (int l = 0; l < this.binaryInterconnectionModel[aRow].length; l++) {
			barycenter += (l + 1) * this.binaryInterconnectionModel[aRow][l];
			rowSum += this.binaryInterconnectionModel[aRow][l];
		}
		barycenter /= rowSum;
		return barycenter;
	}

	/**
	 * 
	 * @param aColumn
	 * @return
	 */
	// Compute column Barycenter of a binary interconnection Matrix
	private double computeColumnBarycenter(int aColumn) {
		double barycenter = 0;
		double columnSum = 0;
		for (int l = 0; l < this.binaryInterconnectionModel.length; l++) {
			barycenter += (l + 1) * this.binaryInterconnectionModel[l][aColumn];
			columnSum += this.binaryInterconnectionModel[l][aColumn];
		}

		barycenter /= columnSum;
		// TODO It happens that columnSum is zero and barycenter is NaN, what to do?
		if (Double.isNaN(barycenter)) {
			barycenter = 0.0;
		}
		return barycenter;
	}
}
