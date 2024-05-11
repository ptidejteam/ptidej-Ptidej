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

import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.integer.constraints.PalmNotEqualXYC;
import choco.palm.search.PalmSolution;
import modec.metamodel.ScenarioDiagram;
import modec.solver.constraint.Callee;
import modec.solver.constraint.Caller;
import modec.solver.constraint.Created;
import modec.solver.constraint.Creator;
import modec.solver.constraint.IsContainedInMessage;
import modec.solver.constraint.MessageFollows;
import modec.solver.constraint.ParameterCalleeSameType;
import modec.util.ExecutionTraceParser;
import util.io.ProxyDisk;

/**
 * @author Janice Ng
 */
public class Command_JHotDraw_v6_1b_SendToBack {

	public static void main(final String[] args) {

		final PalmProblem problem = new PalmProblem();
		final ExecutionTraceParser etp =
			//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Evaluation_JHotDraw_Command_Group.trace");
			//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Evaluation_JHotDraw_Command_BringToFront.trace");
			//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Evaluation_JHotDraw_Command_Align.trace");
			new ExecutionTraceParser(
				"../MoDeC Bytecode Instrumentation Tests/Evaluation_JHotDraw_Command_SendToBack.trace");

		// -------------------------------------------------------------------------------------------------------------
		//  SOLUTION e OBTENIR                                                                                         -
		// -------------------------------------------------------------------------------------------------------------		
		//	v_newCommand = <CREATE> public void <init> (org.jhotdraw.util.Command newWrappedCommand)                   -
		//	v_storeCommand = <OPERATION> public synchronized void add (org.jhotdraw.util.Command command)              -
		//	v_execute = <OPERATION> public void execute ()                                                             -
		//	v_action = <OPERATION> public synchronized void bringToFront (org.jhotdraw.framework.Figure figure)        -
		//	v_aReceiver = org.jhotdraw.samples.javadraw.BouncingDrawing 19315300                                       -
		//	v_aClient = org.jhotdraw.samples.javadraw.JavaDrawApp 8785225                                              -
		//	v_aCommand = org.jhotdraw.standard.BringToFrontCommand 20000831                                            -
		//	v_anInvoker = org.jhotdraw.util.CommandMenu 2086370                                                        -
		//	v_executeCallee = org.jhotdraw.util.UndoableCommand 8018457		                                           -
		// -------------------------------------------------------------------------------------------------------------

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

		final List commandType = new ArrayList();
		commandType.add("org.jhotdraw.standard.SendToBackCommand");
		//commandType.add("org.jhotdraw.standard.BringToFrontCommand");
		//commandType.add("org.jhotdraw.standard.AlignCommand");
		//commandType.add("org.jhotdraw.figures.GroupCommand");

		final Map subclasses = new HashMap();
		subclasses.put("org.jhotdraw.util.Command", commandType);

		System.out.println(nbMessages + " " + nbClassifiers);

		final IntVar v_aReceiver =
			problem.makeBoundIntVar("aReceiver", 0, nbClassifiers);
		final IntVar v_aClient =
			problem.makeBoundIntVar("aClient", 0, nbClassifiers);
		final IntVar v_aCommand =
			problem.makeBoundIntVar("aCommand", 0, nbClassifiers);
		final IntVar v_anInvoker =
			problem.makeBoundIntVar("anInvoker", 0, nbClassifiers);
		final IntVar v_executeCallee =
			problem.makeBoundIntVar("executeCallee", 0, nbClassifiers);
		final IntVar v_newCommand =
			problem.makeBoundIntVar("newCommand", 0, nbMessages);
		final IntVar v_storeCommand =
			problem.makeBoundIntVar("storeCommand", 0, nbMessages);
		final IntVar v_execute =
			problem.makeBoundIntVar("execute", 0, nbMessages);
		final IntVar v_action =
			problem.makeBoundIntVar("action", 0, nbMessages);

		problem.post(new PalmNotEqualXYC(v_executeCallee, v_aCommand, 0));
		problem.post(new PalmNotEqualXYC(v_aReceiver, v_aClient, 0));
		problem.post(new PalmNotEqualXYC(v_aClient, v_aCommand, 0));
		problem.post(new PalmNotEqualXYC(v_aCommand, v_anInvoker, 0));
		problem.post(new PalmNotEqualXYC(v_aReceiver, v_aCommand, 0));

		problem.post(new MessageFollows(
			v_newCommand,
			v_storeCommand,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_storeCommand,
			v_execute,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_execute,
			v_action,
			sd,
			componentsMessages));
		problem.post(new Created(
			v_newCommand,
			v_aCommand,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Creator(
			v_aClient,
			v_newCommand,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aClient,
			v_storeCommand,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_storeCommand,
			v_anInvoker,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_anInvoker,
			v_execute,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_execute,
			v_executeCallee,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aCommand,
			v_action,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_action,
			v_aReceiver,
			sd,
			componentsMessages,
			allClassifiers));

		problem.post(new ParameterCalleeSameType(
			v_storeCommand,
			v_newCommand,
			sd,
			componentsMessages,
			subclasses));

		problem.post(new IsContainedInMessage(
			v_action,
			v_execute,
			sd,
			componentsMessages));

		problem.logger.setLevel(Level.INFO);
		problem.logger.addHandler(new Handler() {

			public void close() throws SecurityException {
			}
			public void flush() {
			}
			public void publish(LogRecord record) {
				if (record.getMessage().equals("A solution was found.")) {
					try {
						//"../MoDeC Bytecode Instrumentation Tests/Solution_Command_JHotDraw_BringToFront.txt",
						//"../MoDeC Bytecode Instrumentation Tests/Solution_Command_JHotDraw_Group.txt",
						//"../MoDeC Bytecode Instrumentation Tests/Solution_Command_JHotDraw_Align.txt",

						final Writer fstream =
							ProxyDisk
								.getInstance()
								.fileAbsoluteOutput(
									"../MoDeC Bytecode Instrumentation Tests/Solution_Command_JHotDraw_SendToBack.txt",
									true);
						BufferedWriter out = new BufferedWriter(fstream);

						final List solutions =
							problem.getPalmSolver().solutions;
						out.write(solutions.size() + "\n");
						final PalmSolution solution =
							(PalmSolution) solutions.get(solutions.size() - 1);

						out.write("OPERATION [new Command]: "
								+ sd.getIdxMessage(solution.getValue(5))
								+ "OPERATION [storeCommand]: "
								+ sd.getIdxMessage(solution.getValue(6))
								+ "OPERATION [execute]: "
								+ sd.getIdxMessage(solution.getValue(7))
								+ "OPERATION [action]: "
								+ sd.getIdxMessage(solution.getValue(8))
								+ "PARTICIPANT [aReceiver]: "
								+ sd.getIdxClassifier(solution.getValue(0))
								+ "\nPARTICIPANT [aClient]: "
								+ sd.getIdxClassifier(solution.getValue(1))
								+ "\nPARTICIPANT [aCommand]: "
								+ sd.getIdxClassifier(solution.getValue(2))
								+ "\nPARTICIPANT [anInvoker]: "
								+ sd.getIdxClassifier(solution.getValue(3))
								+ "\nPARTICIPANT [executeCallee]:"
								+ sd.getIdxClassifier(solution.getValue(4))
								+ "\n\n");
						out.close();
					}
					catch (Exception e) { //Catch exception if any
						System.err
							.println("Error while writing in file : " + e);
					}
				}
			}
		});

		problem.solve(true);
		System.out.println("*** the end : "
				+ problem.getPalmSolver().solutions.size());
	}
}
