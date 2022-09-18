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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;

/**
 * @author Yann
 * @since  2008/09/26
 */
public class OccurrencesDataSource implements JRDataSource {
	private final Occurrence[] occurrences;
	private int index = -1;

	public OccurrencesDataSource(final String aDirectory) {
		final List listOfOccurrences = new ArrayList();

		final String[] files = new File(aDirectory).list();
		final Properties properties = new Properties();
		for (int i = 0; i < files.length; i++) {
			final String fileName = aDirectory + files[i];
			try {
				properties.load(new FileInputStream(fileName));
				final Occurrence[] occurrences =
					OccurrenceBuilder.getInstance().getCanonicalOccurrences(
						properties);
				for (int j = 0; j < occurrences.length; j++) {
					final Occurrence occurrence = occurrences[j];
					listOfOccurrences.add(occurrence);
				}
				properties.clear();
			}
			catch (final FileNotFoundException e) {
				e.printStackTrace();
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}

		this.occurrences = new Occurrence[listOfOccurrences.size()];
		listOfOccurrences.toArray(this.occurrences);
	}

	public boolean next() throws JRException {
		this.index++;
		return this.index < this.occurrences.length;
	}

	public Object getFieldValue(final JRField field) throws JRException {
		final Object value;

		final String fieldName = field.getName();
		if (fieldName.equals("DesignSmellName")) {
			value = this.occurrences[this.index].getName();
		}
		else if (fieldName.equals("Occurrence")) {
			return this.occurrences[this.index];
		}
		else {
			value = "Unknown field: " + fieldName;
			System.out.println(value);
		}

		return value;
	}
}
