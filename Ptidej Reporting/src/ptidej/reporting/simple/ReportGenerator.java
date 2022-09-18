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
package ptidej.reporting.simple;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import ptidej.reporting.simple.datasource.OccurrencesDataSource;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;

/**
 * 
 * @author Yann
 * @since  2008/09/26
 *
 */
public class ReportGenerator {
	public static void main(final String[] args) {
		final String fileName =
			"rsc/simple/DetectionResults in Eclipse v3.1.2 for Blob.ini";
		final Properties properties = new Properties();
		Occurrence[] occurrences = null;
		try {
			properties.load(new FileInputStream(fileName));
			occurrences =
				OccurrenceBuilder.getInstance().getCanonicalOccurrences(
					properties);
		}
		catch (final FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}

		try {
			final Map parameters = new HashMap();
			parameters.put("SUBREPORT_DIR", "./rsc/simple/");
			parameters.put("REPORT_ORIGIN_FILE", fileName);
			JasperFillManager.fillReportToFile(
				"rsc/simple/CodeandDesignSmells.jasper",
				parameters,
				new OccurrencesDataSource(occurrences));

			JasperFillManager.fillReportToFile(
				"rsc/simple/CodeandDesignSmells_CodeSmells.jasper",
				new HashMap());
		}
		catch (final JRException e) {
			e.printStackTrace();
		}

		try {
			JasperExportManager
				.exportReportToHtmlFile("rsc/simple/CodeandDesignSmells.jrprint");
			//	JasperExportManager
			//		.exportReportToHtmlFile("rsc/CodeandDesignSmells_CodeSmells.jrprint");
			JasperExportManager
				.exportReportToPdfFile("rsc/simple/CodeandDesignSmells.jrprint");
		}
		catch (final JRException e) {
			e.printStackTrace();
		}
	}
}
