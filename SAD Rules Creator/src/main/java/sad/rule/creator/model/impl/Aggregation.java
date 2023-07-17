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

import sad.rule.creator.model.IAggregation;
import sad.rule.creator.model.IConstituent;

/**
 * @author Pierre Leduc
 */
public class Aggregation extends Relationship implements IAggregation {

	public Aggregation(
		final String anID,
		final IConstituent aSourceConstituent,
		final IConstituent aTargetConstituent,
		final int aSourceCardinality,
		final int aTargetCardinality) {
		super(
			anID,
			aSourceConstituent,
			aTargetConstituent,
			aSourceCardinality,
			aTargetCardinality);
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("\n-------------------------------------\n");
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
		buffer.append("\n-------------------------------------\n");

		return buffer.toString();
	}
}
