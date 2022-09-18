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

import metamodel.scenarioDiagram.ScenarioDiagram;
import modec.solver.constraint.Callee;
import modec.solver.constraint.Caller;
import modec.solver.constraint.Created;
import modec.solver.constraint.Creator;
import modec.solver.constraint.MessageFollows;
import modec.util.ExecutionTraceParser;
import choco.Constraint;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.search.PalmSolution;

/**
 * @author Janice Ng
 */
public class MementoExtended {

	public static void main(final String[] args) {
		final PalmProblem problem = new PalmProblem();
		final ExecutionTraceParser etp =
			//	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Builder_SearchManager.trace");
		//	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/TestFilms.trace");
		//	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Memento_DCClient.trace");
		//  new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Visitor_OrderManager_EVALUATION.trace"); 
		//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/TestFilms_EVALUATION.trace"); 
		//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Memento_GOF_EVALUATION.trace");
		//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Memento_RunMementoPattern_1-5_EVALUATION.trace");
	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Memento_RunMementoPattern_EVALUATION.trace");

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

		final IntVar variable1 = problem.makeBoundIntVar("v1", 0, nbMessages);
		final IntVar variable2 = problem.makeBoundIntVar("v2", 0, nbMessages);
		final IntVar variable3 = problem.makeBoundIntVar("v3", 0, nbMessages);
		final IntVar variable4 = problem.makeBoundIntVar("v4", 0, nbMessages);
		final IntVar variable5 =
			problem.makeBoundIntVar("v5", 0, nbClassifiers);
		final IntVar variable6 =
			problem.makeBoundIntVar("v6", 0, nbClassifiers);
		final IntVar variable7 =
			problem.makeBoundIntVar("v7", 0, nbClassifiers);

		final Constraint constraintMessageFollowsImmediately1 =
			//			new MessageFollowsImmediately(variable1, variable2, sd);
	new MessageFollows(variable1, variable2, sd, componentsMessages);
		final Constraint constraintMessageFollows2 =
			new MessageFollows(variable2, variable3, sd, componentsMessages);
		final Constraint constraintMessageFollowsImmediately3 =
			//new MessageFollowsImmediately(variable3, variable4, sd);
	new MessageFollows(variable3, variable4, sd, componentsMessages);

		final Constraint constraintCaller1 =
			new Caller(
				variable5,
				variable1,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCallee1 =
			new Callee(
				variable1,
				variable6,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCreator2 =
			new Creator(
				variable6,
				variable2,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCreated2 =
			new Created(
				variable2,
				variable7,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCaller3 =
			new Caller(
				variable5,
				variable3,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCallee3 =
			new Callee(
				variable3,
				variable6,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCaller4 =
			new Caller(
				variable6,
				variable4,
				sd,
				componentsMessages,
				allClassifiers);
		final Constraint constraintCallee4 =
			new Callee(
				variable4,
				variable7,
				sd,
				componentsMessages,
				allClassifiers);

		//		problem.logger.setLevel(Level.INFO);
		//		problem.logger.addHandler(new Handler() {
		//			public void close() throws SecurityException {
		//			}
		//			public void flush() {
		//			}
		//			public void publish(LogRecord record) {
		//				if (record.getMessage().equals("A solution was found.")) {
		//					//					System.out.println(variable1.isInstantiated());
		//					//					System.out.println(variable2.isInstantiated());
		//
		//					System.out.print('[');
		//					System.out.print(variable1.getDomain().getInf());
		//					System.out.print(',');
		//					System.out.print(variable1.getDomain().getSup());
		//					System.out.println(']');
		//
		//					System.out.print('[');
		//					System.out.print(variable2.getDomain().getInf());
		//					System.out.print(',');
		//					System.out.print(variable2.getDomain().getSup());
		//					System.out.println(']');
		//				}
		//			}
		//		});
		problem.post(constraintMessageFollowsImmediately1);
		problem.post(constraintMessageFollows2);
		problem.post(constraintMessageFollowsImmediately3);
		problem.post(constraintCaller1);
		problem.post(constraintCallee1);
		problem.post(constraintCreator2);
		problem.post(constraintCreated2);
		problem.post(constraintCaller3);
		problem.post(constraintCallee3);
		problem.post(constraintCaller4);
		problem.post(constraintCallee4);
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

			System.out.println(
				sd.getIdxMessage(value0)
					+ " "
					+ sd.getIdxMessage(value1)
					+ " "
					+ sd.getIdxMessage(value2)
					+ sd.getIdxMessage(value3)
					+ " "
					+ sd.getIdxClassifier(value4)
					+ " "
					+ sd.getIdxClassifier(value5)
					+ "\n"
					+ sd.getIdxClassifier(value6)
					+ "\n");
		}
		System.out.println(
			"Number of solutions : "
				+ problem.getPalmSolver().solutions.size());

	}

}
