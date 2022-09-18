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
package ptidej.reporting.typed.occurrence;

import java.util.List;

/**
 * 
 * @author Yann
 * @since  2008/10/03
 *
 */
public class TypedOccurrence {
	private final String entityName;
	private final ExplainedDesignSmell[] explainedDesignSmells;

	public TypedOccurrence(
		final String anEntityName,
		final ExplainedDesignSmell[] someExplainedDesignSmells) {

		this.entityName = anEntityName;
		this.explainedDesignSmells = someExplainedDesignSmells;
	}
	public TypedOccurrence(
		final String anEntityName,
		final List someExplainedDesignSmells) {

		this.entityName = anEntityName;
		this.explainedDesignSmells =
			new ExplainedDesignSmell[someExplainedDesignSmells.size()];
		someExplainedDesignSmells.toArray(this.explainedDesignSmells);
	}
	public String getEntityName() {
		return this.entityName;
	}
	public ExplainedDesignSmell[] getDesignSmells() {
		return this.explainedDesignSmells;
	}
	public String toString() {
		return this.entityName;
	}
}
