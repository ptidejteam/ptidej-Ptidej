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
import java.util.List;
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
import modec.util.ExecutionTraceParser;
import util.io.ProxyDisk;

/**
 * @author Janice Ng
 */
public class Observer_SADJP_ReportManager {

	public static void main(final String[] args) throws IOException {
		final PalmProblem problem = new PalmProblem();
		final String traceFilename = getName(
				"../MoDeC Bytecode Instrumentation Tests/input files/Evaluation_SupervisorView_Observer_SelectDepartment.txt");
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

		final IntVar v_setState = problem.makeBoundIntVar("setState", 0,
				nbMessages);
		final IntVar v_notify = problem.makeBoundIntVar("notify", 0,
				nbMessages);
		final IntVar v_update1 = problem.makeBoundIntVar("update1", 0,
				nbMessages);
		final IntVar v_getState1 = problem.makeBoundIntVar("getState1", 0,
				nbMessages);
		final IntVar v_update2 = problem.makeBoundIntVar("update2", 0,
				nbMessages);
		final IntVar v_getState2 = problem.makeBoundIntVar("getState2", 0,
				nbMessages);

		final IntVar v_aConcreteSubject = problem
				.makeBoundIntVar("aConcreteSubject", 0, nbClassifiers);
		final IntVar v_aConcreteObserver1 = problem
				.makeBoundIntVar("aConcreteObserver1", 0, nbClassifiers);
		final IntVar v_aConcreteObserver2 = problem
				.makeBoundIntVar("aConcreteObserver2", 0, nbClassifiers);

		problem.post(new PalmNotEqualXYC(v_aConcreteSubject,
				v_aConcreteObserver1, 0));
		problem.post(new PalmNotEqualXYC(v_aConcreteSubject,
				v_aConcreteObserver2, 0));
		problem.post(new PalmNotEqualXYC(v_aConcreteObserver1,
				v_aConcreteObserver2, 0));

		problem.post(new MessageFollows(v_setState, v_notify, sd,
				componentsMessages));
		problem.post(new MessageFollows(v_notify, v_update1, sd,
				componentsMessages));
		problem.post(new MessageFollows(v_notify, v_getState1, sd,
				componentsMessages));
		problem.post(new MessageFollows(v_update1, v_getState1, sd,
				componentsMessages));
		problem.post(new MessageFollows(v_notify, v_update2, sd,
				componentsMessages));
		problem.post(new MessageFollows(v_notify, v_getState2, sd,
				componentsMessages));
		problem.post(new MessageFollows(v_update2, v_getState2, sd,
				componentsMessages));

		problem.post(new IsContainedInMessage(v_update1, v_notify, sd,
				componentsMessages));
		problem.post(new IsContainedInMessage(v_update2, v_notify, sd,
				componentsMessages));
		problem.post(new IsContainedInMessage(v_getState1, v_update1, sd,
				componentsMessages));
		problem.post(new IsContainedInMessage(v_getState2, v_update2, sd,
				componentsMessages));

		problem.post(new Callee(v_setState, v_aConcreteSubject, sd,
				componentsMessages, allClassifiers));
		problem.post(new Callee(v_notify, v_aConcreteSubject, sd,
				componentsMessages, allClassifiers));
		problem.post(new Caller(v_aConcreteSubject, v_update1, sd,
				componentsMessages, allClassifiers));
		problem.post(new Callee(v_update1, v_aConcreteObserver1, sd,
				componentsMessages, allClassifiers));
		problem.post(new Caller(v_aConcreteObserver1, v_getState1, sd,
				componentsMessages, allClassifiers));
		problem.post(new Callee(v_getState1, v_aConcreteSubject, sd,
				componentsMessages, allClassifiers));
		problem.post(new Caller(v_aConcreteSubject, v_update2, sd,
				componentsMessages, allClassifiers));
		problem.post(new Callee(v_update2, v_aConcreteObserver2, sd,
				componentsMessages, allClassifiers));
		problem.post(new Caller(v_aConcreteObserver2, v_getState2, sd,
				componentsMessages, allClassifiers));
		problem.post(new Callee(v_getState2, v_aConcreteSubject, sd,
				componentsMessages, allClassifiers));

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
										"../MoDeC Bytecode Instrumentation Tests/input files/Evaluation_SupervisorView_Observer_SelectDepartment.txt")
										.replaceAll("Evaluation", "Solution")
										.replaceAll(".trace", ".txt"), true);
						BufferedWriter out = new BufferedWriter(fstream);

						final List solutions = problem
								.getPalmSolver().solutions;
						out.write(solutions.size() + "\n");
						final PalmSolution solution = (PalmSolution) solutions
								.get(solutions.size() - 1);

						out.write("OPERATION [setState]: "
								+ sd.getIdxMessage(solution.getValue(0))
								+ "OPERATION [notify]: "
								+ sd.getIdxMessage(solution.getValue(1))
								+ "OPERATION [update1]: "
								+ sd.getIdxMessage(solution.getValue(2))
								+ "OPERATION [getState1]: "
								+ sd.getIdxMessage(solution.getValue(3))
								+ "OPERATION [update2]: "
								+ sd.getIdxMessage(solution.getValue(4))
								+ "OPERATION [getState2]: "
								+ sd.getIdxMessage(solution.getValue(5))
								+ "PARTICIPANT [aConcreteSubject]: "
								+ sd.getIdxClassifier(solution.getValue(6))
								+ "\nPARTICIPANT [aConcreteObserver1]: "
								+ sd.getIdxClassifier(solution.getValue(7))
								+ "\nPARTICIPANT [aConcreteObserver2]: "
								+ sd.getIdxClassifier(solution.getValue(8))
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
