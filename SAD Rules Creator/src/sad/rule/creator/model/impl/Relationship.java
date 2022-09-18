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
package sad.rule.creator.model.impl;

import sad.rule.creator.model.IConstituent;
import sad.rule.creator.model.IRelationship;

public class Relationship extends Constituent implements IRelationship {
	private IConstituent sourceConstituent;
	private IConstituent targetConstituent;
	private final int sourceCardinality;
	private final int targetCardinality;

	public Relationship(
		final String anID,
		final IConstituent aSourceConstituent,
		final IConstituent aTargetConstituent,
		final int aSourceCardinality,
		final int aTargetCardinality) {
		super(anID);
		this.sourceConstituent = aSourceConstituent;
		this.targetConstituent = aTargetConstituent;
		this.sourceCardinality = aSourceCardinality;
		this.targetCardinality = aTargetCardinality;
	}

	public int getSourceCardinality() {
		return this.sourceCardinality;
	}

	public IConstituent getSourceConstituent() {
		return this.sourceConstituent;
	}

	public int getTargetCardinality() {
		return this.targetCardinality;
	}

	public IConstituent getTargetConstituent() {
		return this.targetConstituent;
	}

	public void setSourceConstituent(final IConstituent aConstituent) {
		this.sourceConstituent = aConstituent;
	}

	public void setTargetConstituent(final IConstituent aConstituent) {
		this.targetConstituent = aConstituent;
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append(this.getClass().getName());
		buffer.append("\nName: ");
		buffer.append(this.getID());
		buffer.append("\nSource: ");
		buffer.append(this.getSourceConstituent().getID());
		buffer.append("\nCardinality: ");
		buffer.append(this.getSourceCardinality());
		buffer.append("\nTarget: ");
		buffer.append(this.getTargetConstituent().getID());
		buffer.append("\nCardinality: ");
		buffer.append(this.getTargetCardinality());

		return buffer.toString();
	}
}
