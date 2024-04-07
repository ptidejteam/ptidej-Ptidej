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
package modec.solver.constraint.motif;

import java.io.BufferedWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import modec.solver.constraint.Callee;
import modec.solver.constraint.Caller;
import modec.solver.constraint.IsContainedInMessage;
import modec.solver.constraint.MessageFollows;
import modec.solver.constraint.ParameterCalleeSameType;
import modec.util.ExecutionTraceParser;
import util.io.ProxyDisk;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.integer.constraints.PalmNotEqualXYC;
import choco.palm.search.PalmSolution;
import metamodel.scenariodiagram.ScenarioDiagram;

/**
 * @author Janice Ng
 */
public class Visitor_JRefactory_v2_6_24_Metrics {

	public static void main(final String[] args) {

		long timeStart = System.currentTimeMillis();
		System.out.println("Start time : " + timeStart);

		// -------------------------------------------------------------------------------------------------------------
		//  SOLUTION À OBTENIR POUR ../MoDeC Bytecode Instrumentation Tests/JHotDraw_Visitor.trace                     -
		// -------------------------------------------------------------------------------------------------------------		
		//	v_accept1 = <OPERATION> public void visit (org.jhotdraw.framework.FigureVisitor visitor)                   -
		//	v_visitConcreteElement1 = <OPERATION> public void visitFigure (org.jhotdraw.framework.Figure hostFigure)   -
		//	v_operation1 = <OPERATION> public void addToContainer (org.jhotdraw.framework.FigureChangeListener c)      -                                                            -
		//	v_anObjStruct1 = org.jhotdraw.contrib.zoom.ZoomDrawingView 5819561                                         -
		//	v_aConcreteElement1 = org.jhotdraw.samples.javadraw.AnimationDecorator 12839271                            -
		//	v_aConcreteVisitor1 = org.jhotdraw.standard.InsertIntoDrawingVisitor 1621736                               -
		//	v_operation1Caller = org.jhotdraw.samples.javadraw.BouncingDrawing 6626965                                 -
		//                                                                                                             -
		//	v_accept2 = <OPERATION> public void visit (org.jhotdraw.framework.FigureVisitor visitor)                   -
		//	v_visitConcreteElement2 = <OPERATION> public void visitFigure (org.jhotdraw.framework.Figure hostFigure)   -
		//	v_operation2 = <OPERATION> public void removeFromContainer (org.jhotdraw.framework.FigureChangeListener c) -                                                           -
		//	v_anObjStruct2 = org.jhotdraw.standard.CutCommand 11197591                                                 -
		//	v_aConcreteElement2 = org.jhotdraw.samples.javadraw.AnimationDecorator 24934792                            -
		//	v_aConcreteVisitor2 = org.jhotdraw.standard.DeleteFromDrawingVisitor 318747                                -
		//	v_operation2Caller = org.jhotdraw.samples.javadraw.BouncingDrawing 6626965                                 -
		// -------------------------------------------------------------------------------------------------------------

		final PalmProblem problem = new PalmProblem();
		final ExecutionTraceParser etp =
			new ExecutionTraceParser(
				"../MoDeC Bytecode Instrumentation Tests/Evaluation_JRefactory_Visitor_Metrics.trace");

		final ScenarioDiagram sd = etp.getScenarioDiagram();
		final List componentsMessages = sd.visitComponentMessages();
		final List allClassifiers = sd.getAllClassifiers(componentsMessages);
		final int nbMessages = sd.countNbMessages(componentsMessages);
		final int nbClassifiers = sd.countNbClassifiers(allClassifiers);

		sd.determineSourceCalledMessages(componentsMessages, allClassifiers);
		sd.determineDestinationCalledMessages(
			componentsMessages,
			allClassifiers);
		sd.determineClassifierIdx(allClassifiers);
		sd.determineIdxClassifier(allClassifiers);
		sd.determineMessageContainer(componentsMessages);

		final List concreteVisitorType = new ArrayList();
		concreteVisitorType.add("org.acm.seguin.metrics.GatherData");
		concreteVisitorType.add("org.acm.seguin.summary.TraversalVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.method.AddMethodTypeVisitor");
		concreteVisitorType
			.add("org.acm.seguin.summary.query.ChildClassSearcher");
		concreteVisitorType
			.add("org.acm.seguin.refactor.method.NearMissVisitor");
		concreteVisitorType.add("org.acm.seguin.summary.PrintVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.field.RemoveFieldFromSubclassVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.method.RemoveMethodFromSubclassVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.field.RenameSystemTraversal");
		concreteVisitorType
			.add("org.acm.seguin.refactor.type.TypeChangeVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.EliminatePackageImportVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.type.MoveClassVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.type.RenameClassVisitor");
		concreteVisitorType
			.add("org.acm.seguin.refactor.type.RemoveClassVisitor");

		final List concreteElementType = new ArrayList();
		concreteElementType.add("org.acm.seguin.summary.FieldAccessSummary");
		concreteElementType.add("org.acm.seguin.summary.FileSummary");
		concreteElementType.add("org.acm.seguin.summary.ImportSummary");
		concreteElementType.add("org.acm.seguin.summary.MessageSendSummary");
		concreteElementType.add("org.acm.seguin.summary.MethodSummary");
		concreteElementType.add("org.acm.seguin.summary.PackageSummary");
		concreteElementType.add("org.acm.seguin.summary.TypeDeclSummary");
		concreteElementType.add("org.acm.seguin.summary.TypeSummary");
		concreteElementType.add("org.acm.seguin.summary.VariableSummary");
		concreteElementType.add("org.acm.seguin.summary.FieldSummary");
		concreteElementType.add("org.acm.seguin.summary.LocalVariableSummary");
		concreteElementType.add("org.acm.seguin.summary.ParameterSummary");

		final Map subclasses = new HashMap();
		subclasses.put(
			"org.acm.seguin.summary.SummaryVisitor",
			concreteVisitorType);
		subclasses.put("org.acm.seguin.summary.Summary", concreteElementType);

		System.out.println(nbMessages + " " + nbClassifiers);

		// VISITOR 1 
		final IntVar v_anObjStruct =
			problem.makeBoundIntVar("anObjectStructure", 0, nbClassifiers);

		final IntVar v_aConcreteElement =
			problem.makeBoundIntVar("aConcreteElement", 0, nbClassifiers);

		final IntVar v_aConcreteVisitor =
			problem.makeBoundIntVar("aConcreteVisitor", 0, nbClassifiers);

		final IntVar v_accept =
			problem.makeBoundIntVar("accept", 0, nbMessages);

		final IntVar v_visitConcreteElement =
			problem.makeBoundIntVar("visitConcreteElement", 0, nbMessages);

		final IntVar v_operation =
			problem.makeBoundIntVar("operation", 0, nbMessages);

		//		final IntVar v_operation1Caller =
		//			problem.makeBoundIntVar("operation1Caller", 0, nbClassifiers);

		problem.post(new PalmNotEqualXYC(v_anObjStruct, v_aConcreteElement, 0));
		//problem.post(new PalmNotEqualXYC(v_anObjStruct, v_aConcreteVisitor, 0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteVisitor,
			v_aConcreteElement,
			0));
		//		problem.post(
		//			new PalmNotEqualXYC(v_aConcreteElement, v_operation1Caller, 0));

		problem.post(new MessageFollows(
			v_accept,
			v_visitConcreteElement,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_visitConcreteElement,
			v_operation,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_accept,
			v_operation,
			sd,
			componentsMessages));
		problem.post(new Caller(
			v_anObjStruct,
			v_accept,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_accept,
			v_aConcreteElement,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aConcreteElement,
			v_visitConcreteElement,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_visitConcreteElement,
			v_aConcreteVisitor,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aConcreteVisitor,
			v_operation,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_operation,
			v_aConcreteElement,
			sd,
			componentsMessages,
			allClassifiers));

		problem.post(new ParameterCalleeSameType(
			v_accept,
			v_visitConcreteElement,
			sd,
			componentsMessages,
			subclasses));

		problem.post(new ParameterCalleeSameType(
			v_visitConcreteElement,
			v_accept,
			sd,
			componentsMessages,
			subclasses));

		problem.post(new IsContainedInMessage(
			v_operation,
			v_visitConcreteElement,
			sd,
			componentsMessages));

		//		// VISITOR 2
		//		final IntVar v_anObjStruct2 =
		//			problem.makeBoundIntVar("anObjectStructure2", 0, nbClassifiers);
		//
		//		final IntVar v_aConcreteElement2 =
		//			problem.makeBoundIntVar("aConcreteElement2", 0, nbClassifiers);
		//
		//		final IntVar v_accept2 =
		//			problem.makeBoundIntVar("accept2", 0, nbMessages);
		//
		//		final IntVar v_visitConcreteElement2 =
		//			problem.makeBoundIntVar("visitConcreteElement2", 0, nbMessages);
		//
		//		final IntVar v_operation2 =
		//			problem.makeBoundIntVar("operation2", 0, nbMessages);
		//
		//		final IntVar v_aConcreteVisitor2 =
		//			problem.makeBoundIntVar("aConcreteVisitor2", 0, nbClassifiers);
		//
		//		final IntVar v_operation2Caller =
		//			problem.makeBoundIntVar("operation2Caller", 0, nbClassifiers);
		//
		//		problem.post(
		//			new PalmNotEqualXYC(v_anObjStruct2, v_aConcreteElement2, 0));
		//		problem.post(
		//			new PalmNotEqualXYC(v_anObjStruct2, v_aConcreteVisitor2, 0));
		//		problem.post(
		//			new PalmNotEqualXYC(v_aConcreteVisitor2, v_aConcreteElement2, 0));
		//		problem.post(
		//			new PalmNotEqualXYC(v_aConcreteElement2, v_operation2Caller, 0));
		//
		//		problem.post(
		//			new MessageFollows(
		//				v_accept2,
		//				v_visitConcreteElement2,
		//				sd,
		//				componentsMessages));
		//		problem.post(
		//			new MessageFollows(
		//				v_visitConcreteElement2,
		//				v_operation2,
		//				sd,
		//				componentsMessages));
		//		problem.post(
		//			new MessageFollows(
		//				v_accept2,
		//				v_operation2,
		//				sd,
		//				componentsMessages));
		//		problem.post(
		//			new Caller(
		//				v_anObjStruct2,
		//				v_accept2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		//		problem.post(
		//			new Callee(
		//				v_accept2,
		//				v_aConcreteElement2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		//		problem.post(
		//			new Caller(
		//				v_aConcreteElement2,
		//				v_visitConcreteElement2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		//		problem.post(
		//			new Callee(
		//				v_visitConcreteElement2,
		//				v_aConcreteVisitor2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		//		//		problem.post(
		//		//			new Caller(
		//		//				v_aConcreteVisitor2,
		//		//				v_operation2,
		//		//				sd,
		//		//				componentsMessages,
		//		//				allClassifiers));
		//		problem.post(
		//			new Caller(
		//				v_operation2Caller,
		//				v_operation2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		//		problem.post(
		//			new Callee(
		//				v_operation2,
		//				v_aConcreteElement2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		//
		//		problem.post(
		//			new ParameterCalleeSameType(
		//				v_accept2,
		//				v_visitConcreteElement2,
		//				sd,
		//				componentsMessages,
		//				subclasses));
		//
		//		problem.post(
		//			new ParameterCalleeSameType(
		//				v_visitConcreteElement2,
		//				v_accept2,
		//				sd,
		//				componentsMessages,
		//				subclasses));
		//
		//		problem.post(
		//			new IsContainedInMessage(
		//				v_operation2,
		//				v_visitConcreteElement2,
		//				sd,
		//				componentsMessages));
		//
		//		// CHECK
		//		problem.post(new PalmNotEqualXYC(v_operation1, v_operation2, 0));
		//		problem.post(
		//			new PalmNotEqualXYC(v_aConcreteVisitor1, v_aConcreteVisitor2, 0));
		//
		//		problem.post(new PalmNotEqualXYC(v_anObjStruct1, v_anObjStruct2, 0));
		//		problem.post(
		//			new PalmNotEqualXYC(v_aConcreteElement1, v_aConcreteElement2, 0));
		//
		//		//		problem.post(
		//		//			new SameType(
		//		//				v_anObjStruct1,
		//		//				v_anObjStruct2,
		//		//				sd,
		//		//				componentsMessages,
		//		//				allClassifiers));
		//		problem.post(
		//			new SameType(
		//				v_aConcreteElement1,
		//				v_aConcreteElement2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		//
		//		problem.post(
		//			new SameType(
		//				v_operation1Caller,
		//				v_operation2Caller,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));

		problem.logger.setLevel(Level.INFO);
		problem.logger.addHandler(new Handler() {

			public void close() throws SecurityException {
			}
			public void flush() {
			}
			public void publish(LogRecord record) {
				if (record.getMessage().equals("A solution was found.")) {
					try {
						final Writer fstream =
							ProxyDisk
								.getInstance()
								.fileAbsoluteOutput(
									"../MoDeC Bytecode Instrumentation Tests/Solution_JRefactory_Visitor_Metrics_ApresAjoutContrainte.txt",
									true);
						BufferedWriter out = new BufferedWriter(fstream);

						final List solutions =
							problem.getPalmSolver().solutions;
						out.write(solutions.size() + "\n");
						final PalmSolution solution =
							(PalmSolution) solutions.get(solutions.size() - 1);

						out.write("OPERATION [accept]: "
								+ sd.getIdxMessage(solution.getValue(3))
								+ "OPERATION [visitConcreteElement]: "
								+ sd.getIdxMessage(solution.getValue(4))
								+ "OPERATION [operation]: "
								+ sd.getIdxMessage(solution.getValue(5))
								+ "PARTICIPANT [anObjStruct]: "
								+ sd.getIdxClassifier(solution.getValue(0))
								+ "\nPARTICIPANT [aConcreteElement]: "
								+ sd.getIdxClassifier(solution.getValue(1))
								+ "\nPARTICIPANT [aConcreteVisitor]: "
								+ sd.getIdxClassifier(solution.getValue(2))
								//								+ "\nPARTICIPANT [operation1Caller]: "
								//								+ sd.getIdxClassifier(solution.getValue(6))
								//								+ "\nOPERATION [accept2]: "
								//								+ sd.getIdxMessage(solution.getValue(9))
								//								+ "OPERATION [visitConcreteElement2]:"
								//								+ sd.getIdxMessage(solution.getValue(10))
								//								+ "OPERATION [operation2]: "
								//								+ sd.getIdxMessage(solution.getValue(11))
								//								+ "PARTICIPANT [anObjStruct2]: "
								//								+ sd.getIdxClassifier(solution.getValue(7))
								//								+ "\nPARTICIPANT [aConcreteElement2]: "
								//								+ sd.getIdxClassifier(solution.getValue(8))
								//								+ "\nPARTICIPANT [aConcreteVisitor2]: "
								//								+ sd.getIdxClassifier(solution.getValue(12))
								//								+ "\nPARTICIPANT [operation2Caller]: "
								//								+ sd.getIdxClassifier(solution.getValue(13))
								+ "\n\n");

						out.close();
					}
					catch (Exception e) { //Catch exception if any
						System.err.println("Error in LogToFile.write: "
								+ e.getMessage());
					}
				}
			}
		});

		problem.solve(true);
		System.out.println("*** the end : "
				+ problem.getPalmSolver().solutions.size());
		long timeEnd = System.currentTimeMillis();
		System.out.println("Start time : " + timeEnd);
		System.out.println("Duree : " + (timeEnd - timeStart) + "ms.");
	}
}
