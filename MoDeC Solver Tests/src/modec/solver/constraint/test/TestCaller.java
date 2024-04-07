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

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import org.junit.Assert;
import junit.framework.TestCase;
import metamodel.scenariodiagram.ScenarioDiagram;
import modec.solver.constraint.Caller;
import modec.util.ExecutionTraceParser;
import choco.Constraint;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.search.PalmSolution;

/**
 * @author ngjanice
 */
public class TestCaller extends TestCase {
	private PalmProblem problem = new PalmProblem();
	private ScenarioDiagram sd;

	public TestCaller(final String name) {
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
		final int nbMessages = this.sd.countNbMessages(componentsMessages);
		final int nbClassifiers = this.sd.countNbClassifiers(allClassifiers);

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
			this.problem.makeBoundIntVar("v1", 0, nbClassifiers);
		final IntVar variable2 =
			this.problem.makeBoundIntVar("v2", 0, nbMessages);
		final Constraint constraint =
			new Caller(
				variable1,
				variable2,
				this.sd,
				componentsMessages,
				allClassifiers);

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
	}

	public void testSolutions() {
		Assert.assertEquals(
			"Number of solutions",
			this.problem.getPalmSolver().solutions.size(),
			7);
	}
	public void testSolution0() {
		// System.out.println(problem.getPalmSolver().solutions);
		final PalmSolution solution0 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(0);
		Assert.assertEquals(
			"Solution 0, [caller]",
			this.sd.getIdxClassifier(solution0.getValue(0)).toString(),
			"OBSERVER.ReportManager$ButtonHandler 31332340");
		Assert
			.assertEquals(
				"Solution 0, [message]",
				this.sd.getIdxMessage(solution0.getValue(1)).toString(),
				"<OPERATION> static javax.swing.JComboBox access$0 (OBSERVER.ReportManager .reportmanager) CALLEE OBSERVER.ReportManager -2 CALLER OBSERVER.ReportManager$ButtonHandler 31332340\n");
	}
	public void testSolution1() {
		final PalmSolution solution1 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(1);
		Assert.assertEquals(
			"Solution 1, [caller]",
			this.sd.getIdxClassifier(solution1.getValue(0)).toString(),
			"OBSERVER.ReportManager$ButtonHandler 31332340");
		Assert
			.assertEquals(
				"Solution 1, [message]",
				this.sd.getIdxMessage(solution1.getValue(1)).toString(),
				"<OPERATION> public void setDepartment (String dept) CALLEE OBSERVER.ReportManager 32946703 CALLER OBSERVER.ReportManager$ButtonHandler 31332340\n");
	}

	public void testSolution2() {
		final PalmSolution solution2 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(2);
		Assert.assertEquals(
			"Solution 2, [caller]",
			this.sd.getIdxClassifier(solution2.getValue(0)).toString(),
			"OBSERVER.ReportManager$ButtonHandler 31332340");
		Assert
			.assertEquals(
				"Solution 2, [message]",
				this.sd.getIdxMessage(solution2.getValue(1)).toString(),
				"<OPERATION> public void notifyObservers () CALLEE OBSERVER.ReportManager 32946703 CALLER OBSERVER.ReportManager$ButtonHandler 31332340\n");
	}

	public void testSolution3() {
		final PalmSolution solution3 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(3);
		Assert.assertEquals(
			"Solution 3, [caller]",
			this.sd.getIdxClassifier(solution3.getValue(0)).toString(),
			"OBSERVER.ReportManager 32946703");
		Assert
			.assertEquals(
				"Solution 3, [message]",
				this.sd.getIdxMessage(solution3.getValue(1)).toString(),
				"<OPERATION> public void refreshData (OBSERVER.Observable subject) CALLEE OBSERVER.MonthlyReport 30308427 CALLER OBSERVER.ReportManager 32946703\n");
	}

	public void testSolution4() {
		final PalmSolution solution4 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(4);
		Assert.assertEquals(
			"Solution 4, [caller]",
			this.sd.getIdxClassifier(solution4.getValue(0)).toString(),
			"OBSERVER.MonthlyReport 30308427");
		Assert
			.assertEquals(
				"Solution 4, [message]",
				this.sd.getIdxMessage(solution4.getValue(1)).toString(),
				"<OPERATION> public String getDepartment () CALLEE OBSERVER.ReportManager 32946703 CALLER OBSERVER.MonthlyReport 30308427\n");
	}

	public void testSolution5() {
		final PalmSolution solution5 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(5);
		Assert.assertEquals(
			"Solution 5, [caller]",
			this.sd.getIdxClassifier(solution5.getValue(0)).toString(),
			"OBSERVER.MonthlyReport 30308427");
		Assert
			.assertEquals(
				"Solution 5, [message]",
				this.sd.getIdxMessage(solution5.getValue(1)).toString(),
				"<OPERATION> private java.util.Vector getCurrentMonthTransactions (String department) CALLEE OBSERVER.MonthlyReport 30308427 CALLER OBSERVER.MonthlyReport 30308427\n");
	}

	public void testSolution6() {
		final PalmSolution solution6 =
			(PalmSolution) this.problem.getPalmSolver().solutions.get(6);
		Assert.assertEquals(
			"Solution 6, [caller]",
			this.sd.getIdxClassifier(solution6.getValue(0)).toString(),
			"OBSERVER.MonthlyReport 30308427");
		Assert
			.assertEquals(
				"Solution 6, [message]",
				this.sd.getIdxMessage(solution6.getValue(1)).toString(),
				"<OPERATION> public java.util.Vector fileToVector (String fileName) CALLEE OBSERVER.FileUtil 10050169 CALLER OBSERVER.MonthlyReport 30308427\n");
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

		String sourcePathname = inFile.readLine().trim();
		System.out.println(sourcePathname);
		String targetPathname = inFile.readLine().trim();
		System.out.println(targetPathname);
		String traceFilename = inFile.readLine().trim();
		System.out.println(traceFilename);

		inFile.close();

		return traceFilename;
	}
}
