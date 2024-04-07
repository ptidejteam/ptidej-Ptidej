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

import modec.solver.constraint.Callee;
import modec.solver.constraint.Caller;
import modec.solver.constraint.Created;
import modec.solver.constraint.Creator;
import modec.solver.constraint.MessageFollows;
import modec.solver.constraint.MessageFollowsImmediately;
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
public class Builder_SADJP_GetSQLStatement {

	public static void main(final String[] args) throws IOException {
		final PalmProblem problem = new PalmProblem();
		final String traceFilename =
			getName("../MoDeC Bytecode Instrumentation Tests/input files/Evaluation_SearchManager_Builder_GetSQLStatement.txt");
		final ExecutionTraceParser etp =
			new ExecutionTraceParser(traceFilename);

		final ScenarioDiagram sd = etp.getScenarioDiagram();
		final List componentsMessages = sd.visitComponentMessages();
		final List allClassifiers = sd.getAllClassifiers(componentsMessages);
		final int nbMessages = sd.countNbMessages(componentsMessages);
		final int nbClassifiers = sd.countNbClassifiers(allClassifiers);
		final int nbFirstLevelMessages = sd.countNbFirstLevelMessages();

		sd.determineSourceCalledMessages(componentsMessages, allClassifiers);
		sd.determineDestinationCalledMessages(
			componentsMessages,
			allClassifiers);
		sd.determineClassifierIdx(allClassifiers);
		sd.determineIdxClassifier(allClassifiers);
		sd.determineMessageContainer(componentsMessages);

		final IntVar v_intermediateCreateBuilder =
			problem.makeBoundIntVar("intermediateCreateBuilder", 0, nbMessages);
		//		final IntVar v_newConcreteBuilder =
		//			problem.makeBoundIntVar("newConcreteBuilder", 0, nbMessages);
		//		final IntVar v_newDirector =
		//			problem.makeBoundIntVar("newDirector", 0, nbMessages);
		//		final IntVar v_construct = problem.makeBoundIntVar("v4", 0, nbMessages);
		//		final IntVar v_buildPart = problem.makeBoundIntVar("v5", 0, nbMessages);

		final IntVar v_newConcreteBuilder =
			problem.makeBoundIntVar("newConcreteBuilder", 0, nbMessages);
		final IntVar v_newDirector =
			problem.makeBoundIntVar("newDirector", 0, nbFirstLevelMessages);
		final IntVar v_construct =
			problem.makeBoundIntVar("construct", 0, nbFirstLevelMessages);
		final IntVar v_buildPart =
			problem.makeBoundIntVar("buildPart", 0, nbFirstLevelMessages);
		final IntVar v_getResult =
			problem.makeBoundIntVar("getResult", 0, nbMessages);
		final IntVar v_aClient =
			problem.makeBoundIntVar("aClient", 0, nbClassifiers);
		final IntVar v_aDirector =
			problem.makeBoundIntVar("aDirector", 0, nbClassifiers);
		final IntVar v_anIntermediateActor =
			problem.makeBoundIntVar("anIntermediateActor", 0, nbClassifiers);
		final IntVar v_aConcreteBuilder =
			problem.makeBoundIntVar("aConcreteBuilder", 0, nbClassifiers);

		problem.post(new PalmNotEqualXYC(v_aDirector, v_aConcreteBuilder, 0));

		problem.post(new MessageFollows(
			v_intermediateCreateBuilder,
			v_newConcreteBuilder,
			sd,
			componentsMessages));
		problem.post(new MessageFollowsImmediately(
			v_newConcreteBuilder,
			v_newDirector,
			sd));
		problem.post(new MessageFollowsImmediately(
			v_newDirector,
			v_construct,
			sd));
		problem
			.post(new MessageFollowsImmediately(v_construct, v_buildPart, sd));
		problem.post(new MessageFollows(
			v_buildPart,
			v_getResult,
			sd,
			componentsMessages));
		problem.post(new Caller(
			v_aClient,
			v_intermediateCreateBuilder,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_intermediateCreateBuilder,
			v_aDirector,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Creator(
			v_aDirector,
			v_newConcreteBuilder,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Created(
			v_newConcreteBuilder,
			v_aConcreteBuilder,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Creator(
			v_aClient,
			v_newDirector,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Created(
			v_newDirector,
			v_anIntermediateActor,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aClient,
			v_construct,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_construct,
			v_anIntermediateActor,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_anIntermediateActor,
			v_buildPart,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_buildPart,
			v_aConcreteBuilder,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aClient,
			v_getResult,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_getResult,
			v_aConcreteBuilder,
			sd,
			componentsMessages,
			allClassifiers));

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
									getName(
										"../MoDeC Bytecode Instrumentation Tests/input files/Evaluation_SearchManager_Builder_GetSQLStatement.txt")
										.replaceAll("Evaluation", "Solution")
										.replaceAll(".trace", ".txt"),
									true);
						BufferedWriter out = new BufferedWriter(fstream);

						final List solutions =
							problem.getPalmSolver().solutions;
						out.write(solutions.size() + "\n");
						final PalmSolution solution =
							(PalmSolution) solutions.get(solutions.size() - 1);

						out.write("OPERATION [intermediateCreateBuilder]: "
								+ sd.getIdxMessage(solution.getValue(0))
								+ "OPERATION [newConcreteBuilder]: "
								+ sd.getIdxMessage(solution.getValue(1))
								+ "OPERATION [newDirector]: "
								+ sd.getIdxMessage(solution.getValue(2))
								+ "OPERATION [construct]: "
								+ sd.getIdxMessage(solution.getValue(3))
								+ "OPERATION [buildPart]: "
								+ sd.getIdxMessage(solution.getValue(4))
								+ "OPERATION [getResult]: "
								+ sd.getIdxMessage(solution.getValue(5))
								+ "PARTICIPANT [anIntermediateClient]: "
								+ sd.getIdxClassifier(solution.getValue(6))
								+ "\nPARTICIPANT [aClient]: "
								+ sd.getIdxClassifier(solution.getValue(7))
								+ "\nPARTICIPANT [aDirector]: "
								+ sd.getIdxClassifier(solution.getValue(8))
								+ "\nPARTICIPANT [aConcreteBuidler]: "
								+ sd.getIdxClassifier(solution.getValue(9))
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
	}

	public static String getName(String inputFilename) throws IOException {

		FileReader fr = null;

		try {
			fr = new FileReader(inputFilename);
		}
		catch (FileNotFoundException e) {
			System.out
				.println("Error occured while opening file (file not found): "
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
