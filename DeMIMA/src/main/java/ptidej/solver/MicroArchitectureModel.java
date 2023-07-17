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
package ptidej.solver;

import padl.motif.IDesignMotifModel;
import padl.motif.util.adapter.DesignMotifModelAdapter;

public final class MicroArchitectureModel extends DesignMotifModelAdapter {
	private static final long serialVersionUID = -5975486033009802894L;
	private static final String NAME_STRING = "SolutionPatternModel";
	private static final char[] NAME_CHAR = MicroArchitectureModel.NAME_STRING
		.toCharArray();

	private final int solutionNumber;
	private final int percentage;

	public MicroArchitectureModel(final int solutionNumber, final int percentage) {
		super(MicroArchitectureModel.NAME_CHAR);

		this.solutionNumber = solutionNumber;
		this.percentage = percentage;
	}
	public int getClassification() {
		return IDesignMotifModel.SOLUTION_MOTIF;
	}
	public String getIntent() {
		return MicroArchitectureModel.NAME_STRING;
	}
	public char[] getName() {
		return MicroArchitectureModel.NAME_CHAR;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("Solution pattern ");
		buffer.append(this.solutionNumber);
		buffer.append(" at ");
		buffer.append(this.percentage);
		buffer.append("%\n");

		buffer.append(super.toString());

		return buffer.toString();
	}
}
