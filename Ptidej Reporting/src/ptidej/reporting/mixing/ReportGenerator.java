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
package ptidej.reporting.mixing;

import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import ptidej.reporting.mixing.datasource.OccurrencesDataSource;

/**
 * 
 * @author Yann
 * @since  2008/09/26
 *
 */
public class ReportGenerator {
	public static void main(final String[] args) {
		final String directoryName = "rsc/mixing/ini/";
		try {
			final Map parameters = new HashMap();
			parameters.put("SUBREPORT_DIR", "./rsc/mixing/");
			parameters.put("REPORT_ORIGIN_FILE", directoryName);
			JasperFillManager.fillReportToFile(
				"rsc/mixing/CodeandDesignSmells.jasper",
				parameters,
				new OccurrencesDataSource(directoryName));

			JasperFillManager.fillReportToFile(
				"rsc/mixing/CodeandDesignSmells_CodeSmells.jasper",
				new HashMap());
		}
		catch (final JRException e) {
			e.printStackTrace();
		}

		try {
			JasperExportManager
				.exportReportToHtmlFile("rsc/mixing/CodeandDesignSmells.jrprint");
			JasperExportManager
				.exportReportToPdfFile("rsc/mixing/CodeandDesignSmells.jrprint");
		}
		catch (final JRException e) {
			e.printStackTrace();
		}
	}
}
