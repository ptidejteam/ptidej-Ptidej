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
package ptidej.reporting.utils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

public class ReportCompiler {
	public static void main(String[] args) {
		try {
			//	JasperCompileManager.compileReportToFile("rsc/simple/CodeandDesignSmells.jrxml");
			//	JasperCompileManager.compileReportToFile("rsc/simple/CodeandDesignSmells_CodeSmells.jrxml");
			//	JasperCompileManager.compileReportToFile("rsc/mixing/CodeandDesignSmells.jrxml");
			//	JasperCompileManager.compileReportToFile("rsc/mixing/CodeandDesignSmells_CodeSmells.jrxml");
			//	JasperCompileManager
			//		.compileReportToFile("rsc/typed/CodeandDesignSmells.jrxml");
			//	JasperCompileManager
			//		.compileReportToFile("rsc/typed/CodeandDesignSmells_CodeSmells.jrxml");
			//	JasperCompileManager
			//		.compileReportToFile("rsc/typed/CodeandDesignSmells_CodeSmells_Causes.jrxml");
			JasperCompileManager
				.compileReportToFile("rsc/charttyped/CodeandDesignSmells.jrxml");
			JasperCompileManager
				.compileReportToFile("rsc/charttyped/CodeandDesignSmells_CodeSmells.jrxml");
			JasperCompileManager
				.compileReportToFile("rsc/charttyped/CodeandDesignSmells_CodeSmells_Causes.jrxml");
			JasperCompileManager
				.compileReportToFile("rsc/charttyped/CodeandDesignSmells_Summary.jrxml");
			JasperCompileManager
				.compileReportToFile("rsc/charttyped/CodeandDesignSmells_Summary_Smells.jrxml");
		}
		catch (final JRException e) {
			e.printStackTrace();
		}
	}
}
