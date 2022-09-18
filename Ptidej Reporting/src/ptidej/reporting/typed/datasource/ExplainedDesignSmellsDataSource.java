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
package ptidej.reporting.typed.datasource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import ptidej.reporting.typed.occurrence.ExplainedDesignSmell;

/**
 * @author Yann
 * @since  2008/09/27
 */
public class ExplainedDesignSmellsDataSource implements JRDataSource {
	private final ExplainedDesignSmell[] explainedDesignSmells;
	private int index = -1;

	public ExplainedDesignSmellsDataSource(
		final ExplainedDesignSmell[] someExplainedDesignSmells) {

		this.explainedDesignSmells = someExplainedDesignSmells;
	}

	public boolean next() throws JRException {
		this.index++;
		return this.index < this.explainedDesignSmells.length;
	}

	public Object getFieldValue(final JRField field) throws JRException {
		final Object value;

		final String fieldName = field.getName();
		if (fieldName.equals("DesignSmellName")) {
			value = this.explainedDesignSmells[this.index].getName();
		}
		else if (fieldName.equals("DesignSmellCauses")) {
			value = this.explainedDesignSmells[this.index].getCauses();
		}
		else {
			value = "Unknown field: " + fieldName;
			System.err.println(value);
		}

		return value;
	}
}
