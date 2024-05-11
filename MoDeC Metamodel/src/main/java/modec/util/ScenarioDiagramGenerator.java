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
package modec.util;


/**
 * @author Janice Ng
 */
public class ScenarioDiagramGenerator {
	public static void main(final String[] args) {
		//final String traceFileName = "MementoModel.trace";
		//final String traceFileName = "Memento_DCClient.trace";
		//final String traceFileName = "Memento_GOF_EVALUATION.trace"; 	
		//final String traceFileName = "Memento_RunMementoPattern_EVALUATION.trace";
		//final String traceFileName = "Memento_RunMementoPattern_1-5_EVALUATION.trace"; 
		//final String traceFileName = "ObserverModel.trace";
		//final String traceFileName = "Observer_SupervisorView_EVALUATION.trace";

		//final String traceFileName = "Visitor_GOF_EVALUATION.trace";	
		//final String traceFileName = "Visitor_OrderManager_EVALUATION.trace";
		//final String traceFileName = "Evaluation_JRefactory_Visitor_PrettyPrinter.trace";
		//final String traceFileName = "Evaluation_JRefactory_Visitor_Metrics_shorten.trace";
		//final String traceFileName = "Evaluation_QuickUML_Command_SaveAction.trace";
		//final String traceFileName = "Evaluation_JHotDraw_Observer_SetTextField.trace";
		//final String traceFileName = "Evaluation_QuickUML_Builder_BuildJavaTataClass.trace";
		//final String traceFileName = "Evaluation_PMD_Visitor_VerifyNaming.trace";
		//final String traceFileName = "Evaluation_PMD_Visitor_VerifyNaming_shorten2.trace";
		//final String traceFileName = "Evaluation_PMD_Builder_VerifyNaming_shorten.trace";
		//		final String traceFileName =
		//			"Evaluation_QuickUML_Builder_BuildJavaTataClass_shorten.trace";
		//		final String traceFileName =
		//			"Evaluation_QuickUML_Command_ResizeADiagram.trace";
		//		final String traceFileName =
		//			"Evaluation_QuickUML_Command_ResizeADiagram.trace";
		//		final String traceFileName =
		//			"Evaluation_QuickUML_Command_ToggleRefreshADiagram.trace";
		//		final String traceFileName =
		//			"Evaluation_QuickUML_Command_ToggleRefreshADiagram_TOTOTOT.trace";
		
		//final String traceFileName = "Builder_SearchManager_EVALUATION.trace";
		final String traceFileName = "Evaluation_SearchManager_Builder_GetSQLStatement.trace";
		//final String traceFileName = "JHotDraw_EVALUATION.trace";
		//final String traceFileName = "JHotDraw_EVALUATION_court.trace";
		//final String traceFileName = "JHotDraw_EVALUATION_CutPasteGOOD.trace";
		//final String traceFileName = "JANICE_court_2.trace";
		//final String traceFileName = "JANICE.trace";
		//final String traceFileName = "JHotDraw_Command_BringToFront.trace";
		//final String traceFileName = "JHotDraw_Command_BringToFront_shorten.trace";
		//final String traceFileName = "Evaluation_JHotDraw_Command_SendToBack.trace";
		//final String traceFileName = "Evaluation_JHotDraw_Visitor_CutAndPasteRectangle.trace";

		//final String traceFileName = "Command_FTPGUI.trace"; // n'applique pas de patrons command !
		//final String traceFileName = "Command_RunCommandPattern.trace";	

		//final String traceFileName = "Loops.trace";
		//final String traceFileName = "TestFilms_EVALUATION.trace";
		//final String traceFileName = "TestFilms_1-5_EVALUATION.trace";
		//final String traceFileName = "TestCallers.trace";

		ExecutionTraceParser.main(
			new String[] {
				 "../MoDeC Bytecode Instrumentation Tests/" + traceFileName });
	}
}
