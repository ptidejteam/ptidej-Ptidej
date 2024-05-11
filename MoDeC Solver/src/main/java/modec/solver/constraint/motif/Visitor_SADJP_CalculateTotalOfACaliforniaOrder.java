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

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.integer.constraints.PalmNotEqualXYC;
import choco.palm.search.PalmSolution;
import modec.metamodel.ScenarioDiagram;
import modec.solver.constraint.Callee;
import modec.solver.constraint.Caller;
import modec.solver.constraint.IsContainedInMessage;
import modec.solver.constraint.MessageFollows;
import modec.solver.constraint.ParameterCalleeSameType;
import modec.util.ExecutionTraceParser;
import util.io.ProxyDisk;

/**
 * @author Janice Ng
 */
public class Visitor_SADJP_CalculateTotalOfACaliforniaOrder {

	public static void main(final String[] args) throws IOException {

		final PalmProblem problem = new PalmProblem();
		final String traceFilename = getName(
				"../MoDeC Bytecode Instrumentation Tests/input files/Evaluation_OrderManager_Visitor_CalculateTotalOfACaliforniaOrder.txt");
		final ExecutionTraceParser etp = new ExecutionTraceParser(
				traceFilename);

		final ScenarioDiagram sd = etp.getScenarioDiagram();
		final List componentsMessages = sd.visitComponentMessages();
		final List allClassifiers = sd.getAllClassifiers(componentsMessages);
		final int nbMessages = sd.countNbMessages(componentsMessages);
		final int nbClassifiers = sd.countNbClassifiers(allClassifiers);

		sd.determineSourceCalledMessages(componentsMessages, allClassifiers);
		sd.determineDestinationCalledMessages(componentsMessages,
				allClassifiers);
		sd.determineClassifierIdx(allClassifiers);
		sd.determineIdxClassifier(allClassifiers);
		sd.determineMessageContainer(componentsMessages);

		final List concreteVisitorType = new ArrayList();
		concreteVisitorType.add("VISITOR.OrderVisitor");

		final List concreteElementType = new ArrayList();
		concreteElementType.add("VISITOR.CaliforniaOrder");
		concreteElementType.add("VISITOR.NonCaliforniaOrder");
		concreteElementType.add("VISITOR.OverseasOrder");

		final Map subclasses = new HashMap();
		subclasses.put("VISITOR.OrderVisitor", concreteVisitorType);
		subclasses.put("VISITOR.Order", concreteElementType);

		// VISITOR 1 
		final IntVar v_anObjStruct = problem
				.makeBoundIntVar("anObjectStructure", 0, nbClassifiers);

		final IntVar v_aConcreteElement = problem
				.makeBoundIntVar("aConcreteElement", 0, nbClassifiers);

		final IntVar v_accept = problem.makeBoundIntVar("accept", 0,
				nbMessages);

		final IntVar v_visitConcreteElement = problem
				.makeBoundIntVar("visitConcreteElement", 0, nbMessages);

		final IntVar v_operation = problem.makeBoundIntVar("operation", 0,
				nbMessages);

		final IntVar v_aConcreteVisitor = problem
				.makeBoundIntVar("aConcreteVisitor", 0, nbClassifiers);

		//		final IntVar v_operationCaller =
		//			problem.makeBoundIntVar("operationCaller", 0, nbClassifiers);

		problem.post(new PalmNotEqualXYC(v_anObjStruct, v_aConcreteElement, 0));
		problem.post(new PalmNotEqualXYC(v_anObjStruct, v_aConcreteVisitor, 0));
		problem.post(
				new PalmNotEqualXYC(v_aConcreteVisitor, v_aConcreteElement, 0));

		problem.post(new MessageFollows(v_accept, v_visitConcreteElement, sd,
				componentsMessages));
		problem.post(new MessageFollows(v_visitConcreteElement, v_operation, sd,
				componentsMessages));
		problem.post(new MessageFollows(v_accept, v_operation, sd,
				componentsMessages));
		problem.post(new Caller(v_anObjStruct, v_accept, sd, componentsMessages,
				allClassifiers));
		problem.post(new Callee(v_accept, v_aConcreteElement, sd,
				componentsMessages, allClassifiers));
		problem.post(new Caller(v_aConcreteElement, v_visitConcreteElement, sd,
				componentsMessages, allClassifiers));
		problem.post(new Callee(v_visitConcreteElement, v_aConcreteVisitor, sd,
				componentsMessages, allClassifiers));
		problem.post(new Caller(v_aConcreteVisitor, v_operation, sd,
				componentsMessages, allClassifiers));
		problem.post(new Callee(v_operation, v_aConcreteElement, sd,
				componentsMessages, allClassifiers));

		problem.post(new ParameterCalleeSameType(v_accept,
				v_visitConcreteElement, sd, componentsMessages, subclasses));

		problem.post(new ParameterCalleeSameType(v_visitConcreteElement,
				v_accept, sd, componentsMessages, subclasses));

		problem.post(new IsContainedInMessage(v_operation,
				v_visitConcreteElement, sd, componentsMessages));

		problem.logger.setLevel(Level.INFO);
		problem.logger.addHandler(new Handler() {

			public void close() throws SecurityException {
			}

			public void flush() {
			}

			public void publish(LogRecord record) {
				if (record.getMessage().equals("A solution was found.")) {
					try {
						final Writer fstream = ProxyDisk.getInstance()
								.fileAbsoluteOutput(getName(
										"../MoDeC Bytecode Instrumentation Tests/input files/Evaluation_OrderManager_Visitor_CalculateTotalOfACaliforniaOrder.txt")
										.replaceAll("Evaluation", "Solution")
										.replaceAll(".trace", ".txt"), true);
						BufferedWriter out = new BufferedWriter(fstream);

						final List solutions = problem
								.getPalmSolver().solutions;
						out.write(solutions.size() + "\n");
						final PalmSolution solution = (PalmSolution) solutions
								.get(solutions.size() - 1);

						out.write("OPERATION [accept]: "
								+ sd.getIdxMessage(solution.getValue(2))
								+ "OPERATION [visitConcreteElement]: "
								+ sd.getIdxMessage(solution.getValue(3))
								+ "OPERATION [operation]: "
								+ sd.getIdxMessage(solution.getValue(4))
								+ "PARTICIPANT [anObjStruct]: "
								+ sd.getIdxClassifier(solution.getValue(0))
								+ "\nPARTICIPANT [aConcreteElement]: "
								+ sd.getIdxClassifier(solution.getValue(1))
								+ "\nPARTICIPANT [aConcreteVisitor]: "
								+ sd.getIdxClassifier(solution.getValue(5))
								+ "\n\n");

						out.close();
					}
					catch (Exception e) { //Catch exception if any
						System.err.println(
								"Error in LogToFile.write: " + e.getMessage());
					}
				}
			}
		});

		problem.solve(true);
		System.out.println(
				"*** the end : " + problem.getPalmSolver().solutions.size());
	}

	public static String getName(String inputFilename) throws IOException {

		FileReader fr = null;

		try {
			fr = new FileReader(inputFilename);
		}
		catch (FileNotFoundException e) {
			System.out.println(
					"Error occured while opening file (file not found): "
							+ inputFilename);
		}

		BufferedReader inFile = new BufferedReader(fr);

		inFile.readLine().trim();
		inFile.readLine().trim();
		String traceFilename = inFile.readLine().trim();

		inFile.close();

		return traceFilename;

	}

}
