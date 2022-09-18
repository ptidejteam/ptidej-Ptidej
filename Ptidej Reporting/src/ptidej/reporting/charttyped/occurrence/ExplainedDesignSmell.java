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
package ptidej.reporting.charttyped.occurrence;

import java.util.List;

/**
 * 
 * @author Yann
 * @since  2008/10/03
 *
 */
public class ExplainedDesignSmell {
	private final String designSmellName;
	private final DesignSmellCause[] designSmellCauses;
	private final int confidenceValue;

	//	public ExplainedDesignSmell(
	//		final String aDesignSmellName,
	//		final DesignSmellCause[] someCauses) {
	//
	//		this.designSmellName = aDesignSmellName;
	//		this.designSmellCauses = someCauses;
	//	}
	public ExplainedDesignSmell(
		final String aDesignSmellName,
		final int aConfidenceValue,
		final List someCauses) {

		this.designSmellName = aDesignSmellName;
		this.confidenceValue = aConfidenceValue;
		this.designSmellCauses = new DesignSmellCause[someCauses.size()];
		someCauses.toArray(this.designSmellCauses);
	}
	public String getName() {
		return this.designSmellName;
	}
	public int getConfidence() {
		return this.confidenceValue;
	}
	public DesignSmellCause[] getCauses() {
		return this.designSmellCauses;
	}
	public String toString() {
		return this.designSmellName;
	}
}
