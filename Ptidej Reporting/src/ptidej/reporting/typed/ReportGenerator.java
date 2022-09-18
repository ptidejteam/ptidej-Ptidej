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
package ptidej.reporting.typed;

import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import ptidej.reporting.typed.datasource.TypedOccurrencesDataSource;

/**
 * 
 * @author Yann
 * @since  2008/09/26
 *
 */
public class ReportGenerator {
	public static void main(final String[] args) {
		final String sourceDirectoryName =
			"D:/Documents/Collaborations/Industries/Google Montréal/140421 - chrome.browser.net Anti-patterns Identification/";
		final String tempDirectoryName =
			"D:/Documents/Collaborations/Industries/Google Montréal/140421 - chrome.browser.net Anti-patterns Reports/Temp/";
		final String destinationDirectoryName =
			"D:/Documents/Collaborations/Industries/Google Montréal/140421 - chrome.browser.net Anti-patterns Reports/";
		try {
			final Map parameters = new HashMap();
			parameters.put("SUBREPORT_DIR", tempDirectoryName);
			parameters.put("REPORT_ORIGIN_FILE", sourceDirectoryName);

			JasperFillManager.fillReportToFile(
				tempDirectoryName + "CodeandDesignSmells.jasper",
				parameters,
				new TypedOccurrencesDataSource(sourceDirectoryName));

			JasperFillManager.fillReportToFile(tempDirectoryName
					+ "CodeandDesignSmells_CodeSmells.jasper", parameters);

			JasperFillManager.fillReportToFile(
				tempDirectoryName
						+ "CodeandDesignSmells_CodeSmells_Causes.jasper",
				new HashMap());
		}
		catch (final JRException e) {
			e.printStackTrace();
		}

		try {
			JasperExportManager.exportReportToHtmlFile(tempDirectoryName
					+ "CodeandDesignSmells.jrprint", destinationDirectoryName
					+ "CodeandDesignSmells.html");
			JasperExportManager.exportReportToPdfFile(tempDirectoryName
					+ "CodeandDesignSmells.jrprint", destinationDirectoryName
					+ "CodeandDesignSmells.pdf");
			JasperExportManager.exportReportToXmlFile(tempDirectoryName
					+ "CodeandDesignSmells.jrprint", destinationDirectoryName
					+ "CodeandDesignSmells.xml", false);
		}
		catch (final JRException e) {
			e.printStackTrace();
		}
	}
}
