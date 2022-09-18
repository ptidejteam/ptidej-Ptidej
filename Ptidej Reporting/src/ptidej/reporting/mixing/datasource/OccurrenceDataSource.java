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
package ptidej.reporting.mixing.datasource;

import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceComponent;

/**
 * @author Yann
 * @since  2008/09/27
 */
public class OccurrenceDataSource implements JRDataSource {
	private final OccurrenceComponent[] occurrenceComponents;
	private int index = -1;

	public OccurrenceDataSource(final Occurrence anOccurrence) {
		final List components = anOccurrence.getComponents();
		this.occurrenceComponents = new OccurrenceComponent[components.size()];
		components.toArray(this.occurrenceComponents);
	}

	public boolean next() throws JRException {
		this.index++;
		if (this.index < this.occurrenceComponents.length
				&& this.occurrenceComponents[this.index].getName().equals(
					"Name")) {
			this.index++;
		}
		return this.index < this.occurrenceComponents.length;
	}

	public Object getFieldValue(final JRField field) throws JRException {
		final Object value;

		final String fieldName = field.getName();
		if (fieldName.equals("CodeSmellName")) {
			final String name =
				this.occurrenceComponents[this.index].getDisplayName();
			//	final int minusIndex = name.indexOf('-');
			//	if (minusIndex > -1) {
			//		value = name.substring(0, minusIndex);
			//	}
			//	else {
			//		value = name;
			//	}
			value = name;
		}
		else if (fieldName.equals("CodeSmellValue")) {
			value = this.occurrenceComponents[this.index].getValue();
		}
		else {
			value = "Unknown field: " + fieldName;
			System.err.println(value);
		}

		return value;
	}
}
