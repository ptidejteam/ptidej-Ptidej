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
package padl.pagerank;

public class Ranking {
	private final int count;
	private final String entityName;
	private final float pageRankValue;
	private final int rank;
	private final int numberOfEdges;
	private final int numberOfIncomingEdges;
	private final int numberOfOutgoingEdges;

	public Ranking(
		final int aCount,
		final String anEntityName,
		final float aPageRankValue,
		final int aRank,
		final int aNumberOfEdges,
		final int aNumberOfIncomingEdges,
		final int aNumberOfOutgoingEdges) {

		this.count = aCount;
		this.entityName = anEntityName;
		this.pageRankValue = aPageRankValue;
		this.rank = aRank;
		this.numberOfEdges = aNumberOfEdges;
		this.numberOfIncomingEdges = aNumberOfIncomingEdges;
		this.numberOfOutgoingEdges = aNumberOfOutgoingEdges;
	}

	public int getCount() {
		return this.count;
	}

	public String getEntityName() {
		return this.entityName;
	}

	public float getPageRankValue() {
		return this.pageRankValue;
	}

	public int getRank() {
		return this.rank;
	}

	public int getNumberOfEdges() {
		return this.numberOfEdges;
	}

	public int getNumberOfIncomingEdges() {
		return this.numberOfIncomingEdges;
	}

	public int getNumberOfOutgoingEdges() {
		return this.numberOfOutgoingEdges;
	}
}
