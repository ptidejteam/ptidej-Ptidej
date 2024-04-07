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
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import modec.solver.constraint.Callee;
import modec.solver.constraint.Caller;
import modec.solver.constraint.Created;
import modec.solver.constraint.Creator;
import modec.solver.constraint.IsContainedInMessage;
import modec.solver.constraint.MessageFollows;
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
public class Builder_PMD_v1_8_VerifyNaming {

	public static void main(final String[] args) {

		long timeStart = System.currentTimeMillis();
		System.out.println("Start time : " + timeStart);

		final PalmProblem problem = new PalmProblem();
		final ExecutionTraceParser etp =
			new ExecutionTraceParser(
				"../MoDeC Bytecode Instrumentation Tests/Evaluation_PMD_Builder_VerifyNaming_shorten.trace");

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

		System.out.println(nbMessages + " " + nbClassifiers);

		final IntVar v_aClient =
			problem.makeBoundIntVar("aClient", 0, nbClassifiers);

		final IntVar v_anIntermediateParticipant =
			problem.makeBoundIntVar(
				"anIntermediateParticipant",
				0,
				nbClassifiers);

		final IntVar v_aDirector =
			problem.makeBoundIntVar("aDirector", 0, nbClassifiers);

		final IntVar v_aConcreteBuilder =
			problem.makeBoundIntVar("aConcreteBuilder", 0, nbClassifiers);

		final IntVar v_intermediateMessage =
			problem.makeBoundIntVar("intermediateMessage", 0, nbMessages);

		final IntVar v_newConcreteBuilder =
			problem.makeBoundIntVar("newConcreteBuilder", 0, nbMessages);

		final IntVar v_newDirector =
			problem.makeBoundIntVar("newDirector", 0, nbMessages);

		final IntVar v_construct =
			problem.makeBoundIntVar("construct", 0, nbMessages);

		final IntVar v_buildPart =
			problem.makeBoundIntVar("buildPart", 0, nbMessages);

		problem.post(new PalmNotEqualXYC(
			v_aClient,
			v_anIntermediateParticipant,
			0));
		problem.post(new PalmNotEqualXYC(v_aClient, v_aDirector, 0));
		problem.post(new PalmNotEqualXYC(v_aClient, v_aConcreteBuilder, 0));
		problem.post(new PalmNotEqualXYC(
			v_anIntermediateParticipant,
			v_aDirector,
			0));
		problem.post(new PalmNotEqualXYC(
			v_anIntermediateParticipant,
			v_aConcreteBuilder,
			0));
		problem.post(new PalmNotEqualXYC(v_aDirector, v_aConcreteBuilder, 0));

		problem.post(new MessageFollows(
			v_intermediateMessage,
			v_newConcreteBuilder,
			sd,
			componentsMessages));

		//		problem.post(
		//			new MessageFollows(
		//				v_newConcreteBuilder,
		//				v_construct,
		//				sd,
		//				componentsMessages));

		problem.post(new MessageFollows(
			v_newConcreteBuilder,
			v_buildPart,
			sd,
			componentsMessages));

		//		problem.post(
		//			new MessageFollows(
		//				v_newDirector,
		//				v_construct,
		//				sd,
		//				componentsMessages));

		problem.post(new MessageFollows(
			v_newDirector,
			v_buildPart,
			sd,
			componentsMessages));

		problem.post(new IsContainedInMessage(
			v_buildPart,
			v_construct,
			sd,
			componentsMessages));

		problem.post(new Caller(
			v_aClient,
			v_intermediateMessage,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_intermediateMessage,
			v_anIntermediateParticipant,
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
			v_anIntermediateParticipant,
			v_newConcreteBuilder,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Created(
			v_newDirector,
			v_aDirector,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Creator(
			v_aClient,
			v_newDirector,
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
			v_aDirector,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aDirector,
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
									"../MoDeC Bytecode Instrumentation Tests/Solution_PMD_Builder_VerifyNaming.txt",
									true);
						BufferedWriter out = new BufferedWriter(fstream);

						final List solutions =
							problem.getPalmSolver().solutions;
						out.write(solutions.size() + "\n");
						final PalmSolution solution =
							(PalmSolution) solutions.get(solutions.size() - 1);

						out.write("OPERATION [intermediateMessage]: "
								+ sd.getIdxMessage(solution.getValue(4))
								+ "OPERATION [newConcreteBuilder]: "
								+ sd.getIdxMessage(solution.getValue(3))
								+ "OPERATION [newDirector]: "
								+ sd.getIdxMessage(solution.getValue(5))
								+ "OPERATION [construct]: "
								+ sd.getIdxMessage(solution.getValue(6))
								+ "OPERATION [buildPart]: "
								+ sd.getIdxMessage(solution.getValue(7))
								+ "PARTICIPANT [aClient]: "
								+ sd.getIdxClassifier(solution.getValue(0))
								+ "PARTICIPANT [anIntermediateParticipant]: "
								+ sd.getIdxClassifier(solution.getValue(1))
								+ "\nPARTICIPANT [aDirector]: "
								+ sd.getIdxClassifier(solution.getValue(2))
								+ "\nPARTICIPANT [aConcreteBuidler]: "
								+ sd.getIdxClassifier(solution.getValue(3))
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
		System.out.println("End time : " + timeEnd);
		System.out.println("Duree : " + (timeEnd - timeStart) + "ms.");
	}
}
