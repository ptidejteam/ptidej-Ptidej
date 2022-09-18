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
package modec.solver.constraint.motif.none;

import java.io.BufferedWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import metamodel.scenarioDiagram.ScenarioDiagram;
import modec.solver.constraint.Callee;
import modec.solver.constraint.Caller;
import modec.solver.constraint.IsContainedInMessage;
import modec.solver.constraint.MessageFollows;
import modec.solver.constraint.ParameterCalleeSameType;
import modec.solver.constraint.SameType;
import modec.util.ExecutionTraceParser;
import util.io.ProxyDisk;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.integer.constraints.PalmNotEqualXYC;
import choco.palm.search.PalmSolution;

/**
 * @author Janice Ng
 */
public class NoPattern_Visitor_JHotDraw_v6_1b_Align {
	public static void main(final String[] args) {
		final PalmProblem problem = new PalmProblem();
		final ExecutionTraceParser etp =
			new ExecutionTraceParser(
				"../MoDeC Bytecode Instrumentation Tests/Evaluation_JHotDraw_Command_Align.trace");

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
		concreteVisitorType
			.add("org.jhotdraw.standard.DeleteFromDrawingVisitor");
		concreteVisitorType
			.add("org.jhotdraw.standard.InsertIntoDrawingVisitor");

		final List concreteElementType = new ArrayList();
		concreteElementType.add("org.jhotdraw.standard.AbstracFigure");
		concreteElementType.add("org.jhotdraw.figures.AttributeFigure");
		concreteElementType.add("org.jhotdraw.contrib.ComponentFigure");
		concreteElementType.add("org.jhotdraw.figures.EllipseFigure");
		concreteElementType
			.add("org.jhotdraw.contrib.html.EllipseFigureGeometricAdapter");
		concreteElementType.add("org.jhotdraw.figures.ImageFigure");
		concreteElementType.add("org.jhotdraw.contrib.PolygonFigure");
		concreteElementType
			.add("org.jhotdraw.contrib.html.PolygonFigureGeometricAdapter");
		concreteElementType.add("org.jhotdraw.figures.RectangleFigure");
		concreteElementType.add("org.jhotdraw.contrib.DiamondFigure");
		concreteElementType
			.add("org.jhotdraw.contrib.html.DiamondFigureGeometricAdapter");
		concreteElementType.add("org.jhotdraw.contrib.TriangleFigure");
		concreteElementType
			.add("org.jhotdraw.contrib.html.TriangleFigureGeometricAdapter");
		concreteElementType.add("org.jhotdraw.figures.RoundRectangleFigure");
		concreteElementType
			.add("org.jhotdraw.contrib.html.RoundRectangleFigureGeometricAdapter");
		concreteElementType.add("org.jhotdraw.contrib.TextAreaFigure");
		concreteElementType.add("org.jhotdraw.contrib.html.HTMLTextAreaFigure");
		concreteElementType.add("org.jhotdraw.figuresTextFigure");
		concreteElementType.add("org.jhotdraw.samples.net.NodeFigure");
		concreteElementType.add("org.jhotdraw.figures.NumberTextFigure");
		concreteElementType.add("org.jhotdraw.standard.CompositeFigure");
		concreteElementType
			.add("org.jhotdraw.contrib.GraphicalCompositeFigure");
		concreteElementType.add("org.jhotdraw.figures.GroupFigure");
		concreteElementType.add("org.jhotdraw.samples.pert.PertFigure");
		concreteElementType.add("org.jhotdraw.standard.StandardDrawing");
		concreteElementType
			.add("org.jhotdraw.samples.javadraw.BouncingDrawing");
		concreteElementType.add("org.jhotdraw.standard.DecoratorFigure");
		concreteElementType
			.add("org.jhotdraw.samples.javadraw.AnimationDecorator");
		concreteElementType.add("org.jhotdraw.figures.BorderDecorator");
		concreteElementType.add("org.jhotdraw.figures.NullFigure");
		concreteElementType.add("org.jhotdraw.figures.PolyLineFigure");
		concreteElementType.add("org.jhotdraw.figures.LineConnection");
		concreteElementType.add("org.jhotdraw.figures.ElbowConnection");
		concreteElementType.add("org.jhotdraw.samples.pert.PertDependency");
		concreteElementType.add("org.jhotdraw.figures.LineFigure");

		final Map subclasses = new HashMap();
		subclasses.put(
			"org.jhotdraw.framework.FigureVisitor",
			concreteVisitorType);
		subclasses.put("org.jhotdraw.framework.Figure", concreteElementType);

		System.out.println(nbMessages + " " + nbClassifiers);

		// VISITOR 1 
		final IntVar v_anObjStruct1 =
			problem.makeBoundIntVar("anObjectStructure1", 0, nbClassifiers);

		final IntVar v_aConcreteElement1 =
			problem.makeBoundIntVar("aConcreteElement1", 0, nbClassifiers);

		final IntVar v_accept1 =
			problem.makeBoundIntVar("accept1", 0, nbMessages);

		final IntVar v_visitConcreteElement1 =
			problem.makeBoundIntVar("visitConcreteElement1", 0, nbMessages);

		final IntVar v_operation1 =
			problem.makeBoundIntVar("operation1", 0, nbMessages);

		final IntVar v_aConcreteVisitor1 =
			problem.makeBoundIntVar("aConcreteVisitor1", 0, nbClassifiers);

		final IntVar v_operation1Caller =
			problem.makeBoundIntVar("operation1Caller", 0, nbClassifiers);

		problem
			.post(new PalmNotEqualXYC(v_anObjStruct1, v_aConcreteElement1, 0));
		problem
			.post(new PalmNotEqualXYC(v_anObjStruct1, v_aConcreteVisitor1, 0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteVisitor1,
			v_aConcreteElement1,
			0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteElement1,
			v_operation1Caller,
			0));

		problem.post(new MessageFollows(
			v_accept1,
			v_visitConcreteElement1,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_visitConcreteElement1,
			v_operation1,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_accept1,
			v_operation1,
			sd,
			componentsMessages));
		problem.post(new Caller(
			v_anObjStruct1,
			v_accept1,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_accept1,
			v_aConcreteElement1,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aConcreteElement1,
			v_visitConcreteElement1,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_visitConcreteElement1,
			v_aConcreteVisitor1,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_operation1Caller,
			v_operation1,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_operation1,
			v_aConcreteElement1,
			sd,
			componentsMessages,
			allClassifiers));

		problem.post(new ParameterCalleeSameType(
			v_accept1,
			v_visitConcreteElement1,
			sd,
			componentsMessages,
			subclasses));

		problem.post(new ParameterCalleeSameType(
			v_visitConcreteElement1,
			v_accept1,
			sd,
			componentsMessages,
			subclasses));

		problem.post(new IsContainedInMessage(
			v_operation1,
			v_visitConcreteElement1,
			sd,
			componentsMessages));

		// VISITOR 2
		final IntVar v_anObjStruct2 =
			problem.makeBoundIntVar("anObjectStructure2", 0, nbClassifiers);

		final IntVar v_aConcreteElement2 =
			problem.makeBoundIntVar("aConcreteElement2", 0, nbClassifiers);

		final IntVar v_accept2 =
			problem.makeBoundIntVar("accept2", 0, nbMessages);

		final IntVar v_visitConcreteElement2 =
			problem.makeBoundIntVar("visitConcreteElement2", 0, nbMessages);

		final IntVar v_operation2 =
			problem.makeBoundIntVar("operation2", 0, nbMessages);

		final IntVar v_aConcreteVisitor2 =
			problem.makeBoundIntVar("aConcreteVisitor2", 0, nbClassifiers);

		final IntVar v_operation2Caller =
			problem.makeBoundIntVar("operation2Caller", 0, nbClassifiers);

		problem
			.post(new PalmNotEqualXYC(v_anObjStruct2, v_aConcreteElement2, 0));
		problem
			.post(new PalmNotEqualXYC(v_anObjStruct2, v_aConcreteVisitor2, 0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteVisitor2,
			v_aConcreteElement2,
			0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteElement2,
			v_operation2Caller,
			0));

		problem.post(new MessageFollows(
			v_accept2,
			v_visitConcreteElement2,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_visitConcreteElement2,
			v_operation2,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_accept2,
			v_operation2,
			sd,
			componentsMessages));
		problem.post(new Caller(
			v_anObjStruct2,
			v_accept2,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_accept2,
			v_aConcreteElement2,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aConcreteElement2,
			v_visitConcreteElement2,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_visitConcreteElement2,
			v_aConcreteVisitor2,
			sd,
			componentsMessages,
			allClassifiers));
		//		problem.post(
		//			new Caller(
		//				v_aConcreteVisitor2,
		//				v_operation2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		problem.post(new Caller(
			v_operation2Caller,
			v_operation2,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_operation2,
			v_aConcreteElement2,
			sd,
			componentsMessages,
			allClassifiers));

		problem.post(new ParameterCalleeSameType(
			v_accept2,
			v_visitConcreteElement2,
			sd,
			componentsMessages,
			subclasses));

		problem.post(new ParameterCalleeSameType(
			v_visitConcreteElement2,
			v_accept2,
			sd,
			componentsMessages,
			subclasses));

		problem.post(new IsContainedInMessage(
			v_operation2,
			v_visitConcreteElement2,
			sd,
			componentsMessages));

		// CHECK
		problem.post(new PalmNotEqualXYC(v_operation1, v_operation2, 0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteVisitor1,
			v_aConcreteVisitor2,
			0));

		problem.post(new PalmNotEqualXYC(v_anObjStruct1, v_anObjStruct2, 0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteElement1,
			v_aConcreteElement2,
			0));

		//		problem.post(
		//			new SameType(
		//				v_anObjStruct1,
		//				v_anObjStruct2,
		//				sd,
		//				componentsMessages,
		//				allClassifiers));
		problem.post(new SameType(
			v_aConcreteElement1,
			v_aConcreteElement2,
			sd,
			componentsMessages,
			allClassifiers));

		problem.post(new SameType(
			v_operation1Caller,
			v_operation2Caller,
			sd,
			componentsMessages,
			allClassifiers));

		final Level oldLevel = problem.logger.getLevel();
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
									"../MoDeC Bytecode Instrumentation Tests/Solution_JHotDraw_NoPattern_Visitor_Align.txt",
									true);
						BufferedWriter out = new BufferedWriter(fstream);

						final List solutions =
							problem.getPalmSolver().solutions;
						out.write(solutions.size() + "\n");
						final PalmSolution solution =
							(PalmSolution) solutions.get(solutions.size() - 1);

						out.write("OPERATION [accept1]: "
								+ sd.getIdxMessage(solution.getValue(2))
								+ "OPERATION [visitConcreteElement1]: "
								+ sd.getIdxMessage(solution.getValue(3))
								+ "OPERATION [operation1]: "
								+ sd.getIdxMessage(solution.getValue(4))
								+ "PARTICIPANT [anObjStruct1]: "
								+ sd.getIdxClassifier(solution.getValue(0))
								+ "\nPARTICIPANT [aConcreteElement1]: "
								+ sd.getIdxClassifier(solution.getValue(1))
								+ "\nPARTICIPANT [aConcreteVisitor1]: "
								+ sd.getIdxClassifier(solution.getValue(5))
								+ "\nPARTICIPANT [operation1Caller]: "
								+ sd.getIdxClassifier(solution.getValue(6))
								+ "\nOPERATION [accept2]: "
								+ sd.getIdxMessage(solution.getValue(9))
								+ "OPERATION [visitConcreteElement2]:"
								+ sd.getIdxMessage(solution.getValue(10))
								+ "OPERATION [operation2]: "
								+ sd.getIdxMessage(solution.getValue(11))
								+ "PARTICIPANT [anObjStruct2]: "
								+ sd.getIdxClassifier(solution.getValue(7))
								+ "\nPARTICIPANT [aConcreteElement2]: "
								+ sd.getIdxClassifier(solution.getValue(8))
								+ "\nPARTICIPANT [aConcreteVisitor2]: "
								+ sd.getIdxClassifier(solution.getValue(12))
								+ "\nPARTICIPANT [operation2Caller]: "
								+ sd.getIdxClassifier(solution.getValue(13))
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
		// I don't forget to restore the previous level.
		problem.logger.setLevel(oldLevel);
	}
}
