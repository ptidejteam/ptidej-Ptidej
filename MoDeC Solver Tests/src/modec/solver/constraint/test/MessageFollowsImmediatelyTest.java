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
package modec.solver.constraint.test;

import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import org.junit.Assert;

import choco.Constraint;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.search.PalmSolution;
import junit.framework.TestCase;
import modec.metamodel.ScenarioDiagram;
import modec.solver.constraint.MessageFollowsImmediately;
import modec.util.ExecutionTraceParser;
import util.io.Files;

/**
 * @author ngjanice
 */
public class MessageFollowsImmediatelyTest extends TestCase {
	private PalmProblem problem;
	private ScenarioDiagram sd;

	public MessageFollowsImmediatelyTest(final String name) {
		super(name);
	}
	protected void setUp() throws IOException {
		this.problem = new PalmProblem();
		final String traceFilename =
				"../MoDeC Solver Tests/rsc/TraceNumero2.trace";
		final ExecutionTraceParser etp =
			new ExecutionTraceParser(traceFilename);
		this.sd = etp.getScenarioDiagram();

		final List componentsMessages = this.sd.visitComponentMessages();
		final List allClassifiers =
			this.sd.getAllClassifiers(componentsMessages);
		//	final int nbMessages = this.sd.countNbMessages(componentsMessages);
		//	final int nbClassifiers = this.sd.countNbClassifiers(allClassifiers);
		final int nbFirstLevelMessages = this.sd.countNbFirstLevelMessages();

		this.sd.determineSourceCalledMessages(
			componentsMessages,
			allClassifiers);
		this.sd.determineDestinationCalledMessages(
			componentsMessages,
			allClassifiers);
		this.sd.determineClassifierIdx(allClassifiers);
		this.sd.determineIdxClassifier(allClassifiers);
		this.sd.determineMessageContainer(componentsMessages);

		final IntVar variable1 =
			this.problem.makeBoundIntVar("v1", 0, nbFirstLevelMessages);
		final IntVar variable2 =
			this.problem.makeBoundIntVar("v2", 0, nbFirstLevelMessages);
		final Constraint constraint =
			new MessageFollowsImmediately(variable1, variable2, this.sd);

		// Yann 2013/08/12: Needed?
		//	this.problem.logger.setLevel(Level.INFO);
		this.problem.logger.addHandler(new Handler() {
			public void close() throws SecurityException {
			}
			public void flush() {
			}
			public void publish(LogRecord record) {
				if (record.getMessage().equals("A solution was found.")) {
					//System.out.println(variable1.isInstantiated());
					//System.out.println(variable2.isInstantiated());

					System.out.print('[');
					System.out.print(variable1.getDomain().getInf());
					System.out.print(',');
					System.out.print(variable1.getDomain().getSup());
					System.out.println(']');

					System.out.print('[');
					System.out.print(variable2.getDomain().getInf());
					System.out.print(',');
					System.out.print(variable2.getDomain().getSup());
					System.out.println(']');
				}
			}
		});

		this.problem.post(constraint);
		this.problem.solve(true);

		//System.out.println(problem.getPalmSolver().solutions);
		System.out.println("Number of solutions : "
				+ this.problem.getPalmSolver().solutions.size());
		for (int i = 0; i < this.problem.getPalmSolver().solutions.size(); i++) {
			final PalmSolution solution =
				(PalmSolution) this.problem.getPalmSolver().solutions.get(i);
			int value0 = solution.getValue(0);
			int value1 = solution.getValue(1);
			System.out.println(this.sd.getIdxFirstLevelMessage(value0) + " "
					+ this.sd.getIdxFirstLevelMessage(value1));
		}
	}

	public void testSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			this.problem.getPalmSolver().solutions.size(),
			8);
	}

	public void testSolution0() {
		// System.out.println(problem.getPalmSolver().solutions);
		final PalmSolution solution0 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(0);
		Assert
			.assertEquals(
				"Solution 0, [message forward]",
				this.sd
					.getIdxFirstLevelMessage(solution0.getValue(0))
					.toString(),
				"<OPERATION> public java.util.Vector fileToVector (String fileName) CALLEE OBSERVER.FileUtil 10050169 CALLER OBSERVER.MonthlyReport 30308427\n");
		Assert
			.assertEquals(
				"Solution 0, [message previous]",
				this.sd
					.getIdxFirstLevelMessage(solution0.getValue(1))
					.toString(),
				"<CREATE> public void <init> () CALLEE OBSERVER.FileUtil 10050169 CALLER OBSERVER.MonthlyReport 30308427\n");
	}

	public void testSolution1() {
		// System.out.println(problem.getPalmSolver().solutions);
		final PalmSolution solution1 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(1);
		Assert
			.assertEquals(
				"Solution 1, [message forward]",
				this.sd
					.getIdxFirstLevelMessage(solution1.getValue(0))
					.toString(),
				"<CREATE> public void <init> () CALLEE OBSERVER.FileUtil 10050169 CALLER OBSERVER.MonthlyReport 30308427\n");
		Assert
			.assertEquals(
				"Solution 1, [message previous]",
				this.sd
					.getIdxFirstLevelMessage(solution1.getValue(1))
					.toString(),
				"<OPERATION> private java.util.Vector getCurrentMonthTransactions (String department) CALLEE OBSERVER.MonthlyReport 30308427 CALLER OBSERVER.MonthlyReport 30308427\n");
	}

	public void testSolution2() {
		// System.out.println(problem.getPalmSolver().solutions);
		final PalmSolution solution2 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(2);
		Assert
			.assertEquals(
				"Solution 2, [message forward]",
				this.sd
					.getIdxFirstLevelMessage(solution2.getValue(0))
					.toString(),
				"<OPERATION> private java.util.Vector getCurrentMonthTransactions (String department) CALLEE OBSERVER.MonthlyReport 30308427 CALLER OBSERVER.MonthlyReport 30308427\n");
		Assert
			.assertEquals(
				"Solution 2, [message previous]",
				this.sd
					.getIdxFirstLevelMessage(solution2.getValue(1))
					.toString(),
				"<OPERATION> public String getDepartment () CALLEE OBSERVER.ReportManager 32946703 CALLER OBSERVER.MonthlyReport 30308427\n");
	}

	public void testSolution3() {
		// System.out.println(problem.getPalmSolver().solutions);
		final PalmSolution solution3 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(3);
		Assert
			.assertEquals(
				"Solution 3, [message forward]",
				this.sd
					.getIdxFirstLevelMessage(solution3.getValue(0))
					.toString(),
				"<OPERATION> public String getDepartment () CALLEE OBSERVER.ReportManager 32946703 CALLER OBSERVER.MonthlyReport 30308427\n");
		Assert
			.assertEquals(
				"Solution 3, [message previous]",
				this.sd
					.getIdxFirstLevelMessage(solution3.getValue(1))
					.toString(),
				"<OPERATION> public void refreshData (OBSERVER.Observable subject) CALLEE OBSERVER.MonthlyReport 30308427 CALLER OBSERVER.ReportManager 32946703\n");
	}

	public void testSolution4() {
		// System.out.println(problem.getPalmSolver().solutions);
		final PalmSolution solution4 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(4);
		Assert
			.assertEquals(
				"Solution 4, [message forward]",
				this.sd
					.getIdxFirstLevelMessage(solution4.getValue(0))
					.toString(),
				"<OPERATION> public void refreshData (OBSERVER.Observable subject) CALLEE OBSERVER.MonthlyReport 30308427 CALLER OBSERVER.ReportManager 32946703\n");
		Assert
			.assertEquals(
				"Solution 4, [message previous]",
				this.sd
					.getIdxFirstLevelMessage(solution4.getValue(1))
					.toString(),
				"<OPERATION> public void notifyObservers () CALLEE OBSERVER.ReportManager 32946703 CALLER OBSERVER.ReportManager$ButtonHandler 31332340\n");
	}

	public void testSolution5() {
		// System.out.println(problem.getPalmSolver().solutions);
		final PalmSolution solution5 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(5);
		Assert
			.assertEquals(
				"Solution 5, [message forward]",
				this.sd
					.getIdxFirstLevelMessage(solution5.getValue(0))
					.toString(),
				"<OPERATION> public void notifyObservers () CALLEE OBSERVER.ReportManager 32946703 CALLER OBSERVER.ReportManager$ButtonHandler 31332340\n");
		Assert
			.assertEquals(
				"Solution 5, [message previous]",
				this.sd
					.getIdxFirstLevelMessage(solution5.getValue(1))
					.toString(),
				"<OPERATION> public void setDepartment (String dept) CALLEE OBSERVER.ReportManager 32946703 CALLER OBSERVER.ReportManager$ButtonHandler 31332340\n");
	}

	public void testSolution6() {
		// System.out.println(problem.getPalmSolver().solutions);
		final PalmSolution solution6 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(6);
		Assert
			.assertEquals(
				"Solution 6, [message forward]",
				this.sd
					.getIdxFirstLevelMessage(solution6.getValue(0))
					.toString(),
				"<OPERATION> public void setDepartment (String dept) CALLEE OBSERVER.ReportManager 32946703 CALLER OBSERVER.ReportManager$ButtonHandler 31332340\n");
		Assert
			.assertEquals(
				"Solution 6, [message previous]",
				this.sd
					.getIdxFirstLevelMessage(solution6.getValue(1))
					.toString(),
				"<OPERATION> static javax.swing.JComboBox access$0 (OBSERVER.ReportManager .reportmanager) CALLEE OBSERVER.ReportManager -2 CALLER OBSERVER.ReportManager$ButtonHandler 31332340\n");
	}

	public void testSolution7() {
		// System.out.println(problem.getPalmSolver().solutions);
		final PalmSolution solution7 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(7);
		Assert
			.assertEquals(
				"Solution 7, [message forward]",
				this.sd
					.getIdxFirstLevelMessage(solution7.getValue(0))
					.toString(),
				"<OPERATION> static javax.swing.JComboBox access$0 (OBSERVER.ReportManager .reportmanager) CALLEE OBSERVER.ReportManager -2 CALLER OBSERVER.ReportManager$ButtonHandler 31332340\n");
		Assert
			.assertEquals(
				"Solution 7, [message previous]",
				this.sd
					.getIdxFirstLevelMessage(solution7.getValue(1))
					.toString(),
				"<OPERATION> public void actionPerformed (java.awt.event.ActionEvent e) CALLEE OBSERVER.ReportManager$ButtonHandler 31332340 CALLER \n");
	}

}
