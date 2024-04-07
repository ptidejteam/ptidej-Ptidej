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
public class Builder {

	public static void main(final String[] args) {
		final PalmProblem problem = new PalmProblem();
		final ExecutionTraceParser etp =
//		new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Builder_SearchManager.trace");
//		new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/TestFilms.trace");
//		new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Memento_DCClient.trace");
//		new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Visitor_OrderManager_EVALUATION.trace");
//		new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/TestFilms_EVALUATION.trace");
//		new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Memento_GOF_EVALUATION.trace");
//		new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Memento_RunMementoPattern_EVALUATION.trace");
//		new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Builder_SearchManager_EVALUATION.trace");
		new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Evaluation_SearchManager_Builder_GetSQLStatement.trace");

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

		final IntVar v_newConcreteBuilder =
			problem.makeBoundIntVar("newConcreteBuilder", 0, nbMessages);
		final IntVar v_newDirector =
			problem.makeBoundIntVar("newDirector", 0, nbMessages);
		final IntVar v_construct =
			problem.makeBoundIntVar("construct", 0, nbMessages);
		final IntVar v_buildPartA =
			problem.makeBoundIntVar("buildPartA", 0, nbMessages);
		final IntVar v_getResult =
			problem.makeBoundIntVar("getResult", 0, nbMessages);
		final IntVar v_aClient =
			problem.makeBoundIntVar("aClient", 0, nbClassifiers);
		final IntVar v_aDirector =
			problem.makeBoundIntVar("aDirector", 0, nbClassifiers);
		final IntVar v_aConcreteBuilder =
			problem.makeBoundIntVar("aConcreteBuilder", 0, nbClassifiers);

		final Constraint constraintMessageFollowsImmediately1 =
			new MessageFollowsImmediately(
				v_newConcreteBuilder,
				v_newDirector,
				sd);
		final Constraint constraintMessageFollows2 =
			new MessageFollows(
				v_newDirector,
				v_construct,
				sd,
				componentsMessages);
		final Constraint constraintMessageFollows3 =
			new MessageFollows(
				v_construct,
				v_buildPartA,
				sd,
				componentsMessages);
		final Constraint constraintMessageFollows4 =
			new MessageFollows(
				v_buildPartA,
				v_getResult,
				sd,
				componentsMessages);

		final Constraint constraintCreator1 =
			new Creator(
				v_aClient,
				v_newConcreteBuilder,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCreated1 =
			new Created(
				v_newConcreteBuilder,
				v_aConcreteBuilder,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCreator2 =
			new Creator(
				v_aClient,
				v_newDirector,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCreated2 =
			new Created(
				v_newDirector,
				v_aDirector,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCaller3 =
			new Caller(
				v_aClient,
				v_construct,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCallee3 =
			new Callee(
				v_construct,
				v_aDirector,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCaller4 =
			new Caller(
				v_aDirector,
				v_buildPartA,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCallee4 =
			new Callee(
				v_buildPartA,
				v_aConcreteBuilder,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCaller5 =
			new Caller(
				v_aClient,
				v_getResult,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCallee5 =
			new Callee(
				v_getResult,
				v_aConcreteBuilder,
				sd,
				componentsMessages,
				allClassifiers);

		problem.post(constraintMessageFollowsImmediately1);
		problem.post(constraintMessageFollows2);
		problem.post(constraintMessageFollows3);
		problem.post(constraintMessageFollows4);
		problem.post(constraintCreator1);
		problem.post(constraintCreated1);
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
					//					System.out.println(v_newConcreteBuilder.isInstantiated());
					//					System.out.println(v_newDirector.isInstantiated());
					final List solutions = problem.getPalmSolver().solutions;
					System.out.println(
						"Number of solutions : " + solutions.size());
					final PalmSolution solution =
						(PalmSolution) solutions.get(solutions.size() - 1);

					System.out.println(
						"OPERATION [new ConcreteBuilder()]:\t"
							+ sd.getIdxMessage(solution.getValue(0))
							+ "OPERATION [new Director(aConcreteBuilder)]:\t"
							+ sd.getIdxMessage(solution.getValue(1))
							+ "OPERATION [Construct()]:\t"
							+ sd.getIdxMessage(solution.getValue(2))
							+ "OPERATION [BuildPartA()]:\t"
							+ sd.getIdxMessage(solution.getValue(3))
							+ "OPERATION [GetResult()]:\t"
							+ sd.getIdxMessage(solution.getValue(4))
							+ "PARTICIPANT [aClient]:\t"
							+ sd.getIdxClassifier(solution.getValue(5))
							+ "\nPARTICIPANT [aDirector]:\t"
							+ sd.getIdxClassifier(solution.getValue(6))
							+ "\nPARTICIPANT [aConcreteBuilder]:\t"
							+ sd.getIdxClassifier(solution.getValue(7))
							+ "\n");

				}
			}
		});

		problem.solve(true);

		System.out.println(problem.getPalmSolver().solutions);
		for (int i = 0; i < problem.getPalmSolver().solutions.size(); i++) {
			final PalmSolution solution =
				(PalmSolution) problem.getPalmSolver().solutions.get(i);
			int value0 = solution.getValue(0);
			int value1 = solution.getValue(1);
			int value2 = solution.getValue(2);
			int value3 = solution.getValue(3);
			int value4 = solution.getValue(4);
			int value5 = solution.getValue(5);
			int value6 = solution.getValue(6);
			int value7 = solution.getValue(7);

			System.out.println(
				sd.getIdxMessage(value0)
					+ " "
					+ sd.getIdxMessage(value1)
					+ " "
					+ sd.getIdxMessage(value2)
					+ sd.getIdxMessage(value3)
					+ " "
					+ sd.getIdxMessage(value4)
					+ " "
					+ sd.getIdxClassifier(value5)
					+ "\n"
					+ sd.getIdxClassifier(value6)
					+ "\n"
					+ sd.getIdxClassifier(value7)
					+ "\n");
		}

	}

}
