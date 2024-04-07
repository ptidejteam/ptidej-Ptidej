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
import choco.Constraint;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.search.PalmSolution;
import metamodel.scenariodiagram.ScenarioDiagram;

/**
 * @author Janice Ng
 */
public class Memento {

	public static void main(final String[] args) {
		final PalmProblem problem = new PalmProblem();
		final ExecutionTraceParser etp =
			new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Memento_GOF_EVALUATION.trace");
		//	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Builder_SearchManager.trace");
		//	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/TestFilms.trace");
		//	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Memento_DCClient.trace");
		//  new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Visitor_OrderManager_EVALUATION.trace"); 
		//  new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/TestFilms_EVALUATION.trace"); 
		//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Memento_RunMementoPattern_EVALUATION.trace");

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


		final IntVar v_createMemento =
			problem.makeBoundIntVar("createMemento", 0, nbMessages);
		final IntVar v_newMemento =
			problem.makeBoundIntVar("newMemento", 0, nbMessages);
		final IntVar v_setState =
			problem.makeBoundIntVar("setState", 0, nbMessages);
		final IntVar v_setMemento =
			problem.makeBoundIntVar("setMemento", 0, nbMessages);
		final IntVar v_getState =
			problem.makeBoundIntVar("getState", 0, nbMessages);
		final IntVar v_aCaretaker =
			problem.makeBoundIntVar("aCaretaker", 0, nbClassifiers);
		final IntVar v_anOriginator =
			problem.makeBoundIntVar("anOriginator", 0, nbClassifiers);
		final IntVar v_aMemento =
			problem.makeBoundIntVar("aMemento", 0, nbClassifiers);

		final Constraint constraintMessageFollowsImmediately1 =
			new MessageFollowsImmediately(v_createMemento, v_newMemento, sd);
		final Constraint constraintMessageFollowsImmediately2 =
			new MessageFollowsImmediately(v_newMemento, v_setState, sd);
		final Constraint constraintMessageFollows3 =
			new MessageFollows(
				v_setState,
				v_setMemento,
				sd,
				componentsMessages);
		final Constraint constraintMessageFollowsImmediately4 =
			new MessageFollowsImmediately(v_setMemento, v_getState, sd);

		final Constraint constraintCaller1 =
			new Caller(
				v_aCaretaker,
				v_createMemento,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCallee1 =
			new Callee(
				v_createMemento,
				v_anOriginator,
				sd,
				componentsMessages,
				allClassifiers);

		final Constraint constraintCreator2 =
			new Creator(
				v_anOriginator,
				v_newMemento,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCreated2 =
			new Created(
				v_newMemento,
				v_aMemento,
				sd,
				componentsMessages,
				allClassifiers);

		final Constraint constraintCaller3 =
			new Caller(
				v_anOriginator,
				v_setState,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCallee3 =
			new Callee(
				v_setState,
				v_aMemento,
				sd,
				componentsMessages,
				allClassifiers);

		final Constraint constraintCaller4 =
			new Caller(
				v_aCaretaker,
				v_setMemento,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCallee4 =
			new Callee(
				v_setMemento,
				v_anOriginator,
				sd,
				componentsMessages,
				allClassifiers);

		final Constraint constraintCaller5 =
			new Caller(
				v_anOriginator,
				v_getState,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCallee5 =
			new Callee(
				v_getState,
				v_aMemento,
				sd,
				componentsMessages,
				allClassifiers);

		problem.post(constraintMessageFollowsImmediately1);
		problem.post(constraintMessageFollowsImmediately2);
		problem.post(constraintMessageFollows3);
		problem.post(constraintMessageFollowsImmediately4);
		problem.post(constraintCaller1);
		problem.post(constraintCallee1);
		problem.post(constraintCreator2);
		problem.post(constraintCreated2);
		problem.post(constraintCaller3);
		problem.post(constraintCallee3);
		problem.post(constraintCaller4);
		problem.post(constraintCallee4);
		problem.post(constraintCaller5);
		problem.post(constraintCallee5);

		problem.logger.setLevel(Level.INFO);
		problem.logger.addHandler(new Handler() {
			public void close() throws SecurityException {
			}
			public void flush() {
			}
			public void publish(LogRecord record) {
				if (record.getMessage().equals("A solution was found.")) {
					final List solutions = problem.getPalmSolver().solutions;
					System.out.println(
						"Number of solutions : " + solutions.size());
					final PalmSolution solution =
						(PalmSolution) solutions.get(solutions.size() - 1);

					System.out.println(
						"OPERATION [CreateMemento()]:\t"
							+ sd.getIdxMessage(solution.getValue(0))
							+ "OPERATION [new Memento]:\t"
							+ sd.getIdxMessage(solution.getValue(1))
							+ "OPERATION [SetState(state)]:\t"
							+ sd.getIdxMessage(solution.getValue(2))
							+ "OPERATION [SetMemento(memento)]:\t"
							+ sd.getIdxMessage(solution.getValue(3))
							+ "OPERATION [GetState()]:\t"
							+ sd.getIdxMessage(solution.getValue(4))
							+ "PARTICIPANT [aCaretaker]:\t"
							+ sd.getIdxClassifier(solution.getValue(5))
							+ "\nPARTICIPANT [anOriginator]:\t"
							+ sd.getIdxClassifier(solution.getValue(6))
							+ "\nPARTICIPANT [aMemento]:\t"
							+ sd.getIdxClassifier(solution.getValue(7)));

				}
			}
		});

		problem.solve(true);
	}
}
