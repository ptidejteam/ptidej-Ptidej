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
package modec.solver.constraint;

import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import choco.Constraint;
import choco.ContradictionException;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.AbstractPalmBinIntConstraint;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.PalmIntVar;
import choco.palm.search.PalmSolution;
import choco.util.IntIterator;
import modec.metamodel.Component;
import modec.metamodel.Message;
import modec.metamodel.ScenarioDiagram;
import modec.util.ExecutionTraceParser;

/**
 * @author Janice Ng
 */
public class MessageFollowsImmediately
	extends AbstractPalmBinIntConstraint
	implements Constraint {

	private ScenarioDiagram sd;

	public static void main(final String[] args) {
		final PalmProblem problem = new PalmProblem();
		final ExecutionTraceParser etp =
			new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Evaluation_SearchManager_Builder_GetSQLStatement.trace");
		//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/TraceNumero2.trace");
		//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Builder_SearchManager.trace");
		//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/TestFilms.trace");
		//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Memento_DCClient.trace");

		final ScenarioDiagram sd = etp.getScenarioDiagram();
//		final List componentsMessages = sd.visitComponentMessages();
//		final List allClassifiers = sd.getAllClassifiers(componentsMessages);
//		final int nbMessages = sd.countNbMessages(componentsMessages);
//		final int nbClassifiers = sd.countNbClassifiers(allClassifiers);
//		final int nbFirstLevelMessages = sd.countNbFirstLevelMessages();
//
//		sd.determineSourceCalledMessages(componentsMessages, allClassifiers);
//		sd.determineDestinationCalledMessages(
//			componentsMessages,
//			allClassifiers);
//		sd.determineClassifierIdx(allClassifiers);
//		sd.determineIdxClassifier(allClassifiers);
//		sd.determineMessageContainer(componentsMessages);

		final IntVar variable1 = problem.makeBoundIntVar("v1", 0, sd.countNbFirstLevelMessages());
		final IntVar variable2 =
			problem.makeBoundIntVar("v2", 0, sd.countNbFirstLevelMessages());
		final Constraint constraint =
			new MessageFollowsImmediately(variable1, variable2, sd);

		// Yann 2013/08/12: Needed?
		//	problem.logger.setLevel(Level.INFO);
		problem.logger.addHandler(new Handler() {
			public void close() throws SecurityException {
			}
			public void flush() {
			}
			public void publish(LogRecord record) {
				if (record.getMessage().equals("A solution was found.")) {
					//					System.out.println(variable1.isInstantiated());
					//					System.out.println(variable2.isInstantiated());

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
		problem.post(constraint);
		problem.solve(true);

		//System.out.println(problem.getPalmSolver().solutions);
		System.out.println(
			"Number of solutions : "
				+ problem.getPalmSolver().solutions.size());
		for (int i = 0; i < problem.getPalmSolver().solutions.size(); i++) {
			final PalmSolution solution =
				(PalmSolution) problem.getPalmSolver().solutions.get(i);
			int value0 = solution.getValue(0);
			int value1 = solution.getValue(1);
			System.out.println(
				sd.getIdxFirstLevelMessage(value0)
					+ " "
					+ sd.getIdxFirstLevelMessage(value1));
		}
	}

	public MessageFollowsImmediately(
		IntVar v0,
		IntVar v1,
		ScenarioDiagram sd) {
		this.v0 = v0;
		this.v1 = v1;
		this.sd = sd;
		this.hook = new PalmConstraintPlugin(this);
	}

	public void propagate() {

		if (this.v0.getDomain().getSize() > 0) {
			final IntIterator iterator0 = this.v0.getDomain().getIterator();
			boolean toBeRemoved = true;

			while (iterator0.hasNext() && toBeRemoved) {
				final int index_e0 = iterator0.next();

				if (index_e0 > -1) {
					Message msg = this.sd.getIdxFirstLevelMessage(index_e0);
					//System.out.println(index_e0 + " " + msg);
					Component nextMsg = this.sd.getNextComponent(msg);
					if (nextMsg instanceof Message
						&& this.v1.getDomain().contains(
							this.sd.getFirstLevelMessageIdx(
								(Message) nextMsg))) {
						//this.sd.getNextMessage(this.sd.getMessage(index_e0)).index())) {

						toBeRemoved = false;
					}

					if (toBeRemoved) {
						choco.palm.explain.Explanation expl =
							((PalmProblem) this.getProblem()).makeExplanation();
						((PalmConstraintPlugin) this.getPlugIn()).self_explain(
							expl);
						((PalmIntVar) this.v1).self_explain(
							PalmIntDomain.DOM,
							expl);
						((PalmIntVar) this.v0).removeVal(
							index_e0,
							this.cIdx0,
							expl);
					}
				}
			}
		}

		if (this.v1.getDomain().getSize() > 0) {
			final IntIterator iterator1 = this.v1.getDomain().getIterator();
			boolean toBeRemoved = true;

			while (iterator1.hasNext() && toBeRemoved) {
				final int index_e1 = iterator1.next();

				if (index_e1 > -1) {
					Message idxMsg = this.sd.getIdxFirstLevelMessage(index_e1);
					Component previousMsg =
						this.sd.getPreviousComponent(idxMsg);
					if (previousMsg instanceof Message
						&& this.v0.getDomain().contains(
							this.sd.getFirstLevelMessageIdx(
								(Message) previousMsg))) {
						//this.sd.getNextMessage(this.sd.getMessage(index_e0)).index())) {
						toBeRemoved = false;
					}

					if (toBeRemoved) {
						choco.palm.explain.Explanation expl =
							((PalmProblem) this.getProblem()).makeExplanation();
						((PalmConstraintPlugin) this.getPlugIn()).self_explain(
							expl);
						((PalmIntVar) this.v0).self_explain(
							PalmIntDomain.DOM,
							expl);
						((PalmIntVar) this.v1).removeVal(
							index_e1,
							this.cIdx1,
							expl);
					}
				}
			}

		}
	}

	/* (non-Javadoc)
	 * @see choco.palm.integer.PalmIntVarListener#awakeOnRestoreVal(int, int)
	 */
	public void awakeOnRestoreVal(int idx, int val)
		throws ContradictionException {
		propagate();
	}
	/* (non-Javadoc)
	 * @see choco.palm.integer.PalmIntVarListener#whyIsTrue()
	 */
	public Set whyIsTrue() {

		return null;
	}
	/* (non-Javadoc)
	 * @see choco.palm.integer.PalmIntVarListener#whyIsFalse()
	 */
	public Set whyIsFalse() {

		return null;
	}
	/* (non-Javadoc)
	 * @see choco.Constraint#isSatisfied()
	 */
	public boolean isSatisfied() {

		return false;
	}

}
