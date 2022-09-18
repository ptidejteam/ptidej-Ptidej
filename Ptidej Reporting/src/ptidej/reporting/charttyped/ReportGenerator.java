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
package ptidej.reporting.charttyped;

import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import ptidej.reporting.charttyped.datasource.TypedOccurrencesDataSource;

/**
 * 
 * @author Yann
 * @since  2008/09/26
 *
 */
public final class ReportGenerator {
	// final String summaryFileName =
	//	"../Z - Integratik/misc/NbDefectsPerClasses inIntegratik.csv";
	// final String sourceDirectoryName = "../Z - Integratik/rsc/smells/";
	// final String summaryFileName =
	// 	"../Z - Integratik/misc/NbDefectsPerClasses inIntegratik.csv";
	// final String destinationDirectoryName =
	// 	"../Z - Integratik/rsc/reports/";
	public final void generateReport(
		final String aSourceDirectoryName,
		final String aDestinationFileName) {
		try {
			final Map parameters = new HashMap();
			parameters.put(
				"SUBREPORT_DIR",
				"../Ptidej Reporting/rsc/charttyped/");
			parameters.put("REPORT_ORIGIN_FILE", aSourceDirectoryName);
			parameters.put("REPORT_SUMMARY_FILE", "");
			JasperFillManager
				.fillReportToFile(
					"../Ptidej Reporting/rsc/charttyped/CodeandDesignSmells.jasper",
					parameters,
					new TypedOccurrencesDataSource(aSourceDirectoryName));

			JasperFillManager
				.fillReportToFile(
					"../Ptidej Reporting/rsc/charttyped/CodeandDesignSmells_CodeSmells.jasper",
					parameters);

			JasperFillManager
				.fillReportToFile(
					"../Ptidej Reporting/rsc/charttyped/CodeandDesignSmells_CodeSmells_Causes.jasper",
					new HashMap());

			JasperFillManager
				.fillReportToFile(
					"../Ptidej Reporting/rsc/charttyped/CodeandDesignSmells_Summary.jasper",
					new HashMap());

			JasperFillManager
				.fillReportToFile(
					"../Ptidej Reporting/rsc/charttyped/CodeandDesignSmells_Summary_Smells.jasper",
					new HashMap());
		}
		catch (final JRException e) {
			e.printStackTrace();
		}

		try {
			JasperExportManager
				.exportReportToHtmlFile(
					"../Ptidej Reporting/rsc/charttyped/CodeandDesignSmells.jrprint",
					aDestinationFileName + ".html");
			JasperExportManager
				.exportReportToPdfFile(
					"../Ptidej Reporting/rsc/charttyped/CodeandDesignSmells.jrprint",
					aDestinationFileName + ".pdf");
			JasperExportManager
				.exportReportToXmlFile(
					"../Ptidej Reporting/rsc/charttyped/CodeandDesignSmells.jrprint",
					aDestinationFileName + ".xml",
					false);
		}
		catch (final JRException e) {
			e.printStackTrace();
		}
	}
}
