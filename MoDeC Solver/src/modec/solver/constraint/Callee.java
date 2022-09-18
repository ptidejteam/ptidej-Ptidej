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

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import metamodel.scenarioDiagram.Classifier;
import metamodel.scenarioDiagram.Instance;
import metamodel.scenarioDiagram.Message;
import metamodel.scenarioDiagram.Operation;
import metamodel.scenarioDiagram.ScenarioDiagram;
import choco.Constraint;
import choco.ContradictionException;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.AbstractPalmBinIntConstraint;
import choco.palm.integer.PalmIntDomain;
import choco.palm.integer.PalmIntVar;
import choco.util.IntIterator;

/**
 * @author Janice Ng
 */
public class Callee
	extends AbstractPalmBinIntConstraint
	implements Constraint {

	private ScenarioDiagram sd;
	

	//	public static void main(final String[] args) {
	//		final PalmProblem problem = new PalmProblem();
	//		final ExecutionTraceParser etp =
	//			//			new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Builder_SearchManager.trace");
	//		//			new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/TestFilms.trace");
	//		//			new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Memento_DCClient.trace");
	//	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Visitor_OrderManager.trace");
	//
	//		final ScenarioDiagram sd = etp.getScenarioDiagram();
	//		
	//		System.out.println("Number of messages : " + sd.countNbMessages(componentsMessages));
	//		final IntVar variable1 =
	//			problem.makeBoundIntVar("v1", 0, sd.countNbMessages(componentsMessages));
	//		final IntVar variable2 =
	//			problem.makeBoundIntVar("v2", 0, sd.countNbClassifiers(componentsMessages));
	//		final Constraint constraint = new Callee(variable1, variable2, sd);
	//
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
	//		problem.post(constraint);
	//		problem.solve(true);
	//
	//		//System.out.println(problem.getPalmSolver().solutions);
	//		System.out.println(
	//			"Number of solutions : "
	//				+ problem.getPalmSolver().solutions.size());
	//		for (int i = 0; i < problem.getPalmSolver().solutions.size(); i++) {
	//			final PalmSolution solution =
	//				(PalmSolution) problem.getPalmSolver().solutions.get(i);
	//			int value0 = solution.getValue(0);
	//			int value1 = solution.getValue(1);
	//			System.out.println(sd.getIdxMessage(value0) + "<"
	//			//	+ sd.getIdxCallee(value1) + ">\n");
	//			+sd.getIdxClassifier(value1) + ">\n");
	//		}
	//
	//	}

	public Callee(
		IntVar v0,
		IntVar v1,
		ScenarioDiagram sd,
		List componentsMessages,
		List allClassifiers) {
		this.v0 = v0;
		this.v1 = v1;
		this.sd = sd;
		this.hook = new PalmConstraintPlugin(this);
	}

	public void propagate() {

		if (this.v0.getDomain().getSize() > 0) {
			final IntIterator iterator0 = this.v0.getDomain().getIterator();
			boolean toBeRemoved = true;

			while (iterator0.hasNext() /*&& toBeRemoved*/
				) {
				final int index_e0 = iterator0.next();

				if (index_e0 > -1) {
					Message idxMsg = this.sd.getIdxMessage(index_e0);
					Classifier callee = idxMsg.getDestinationClassifier();
					//System.out.println("Msg : " + idxMsg + "\nCallee : " + callee);

					if (idxMsg instanceof Operation
						&& callee instanceof Instance
						&& this.v1
							.getDomain() //.contains(this.sd.getCalleeIdx(callee))) {
							.contains(
							this.sd.getClassifierIdx(callee))) {
						toBeRemoved = false;
					}

					//System.out.println("Allo !");

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

			while (iterator1.hasNext() /* && toBeRemoved*/
				) {
				final int index_e1 = iterator1.next();

				if (index_e1 > -1) {
					//Classifier callee = this.sd.getIdxCallee(index_e1);
					Classifier callee = this.sd.getIdxClassifier(index_e1);
					List calledMessages =
						this.sd.getDestinationCalledMessages(callee);
					if(calledMessages == null)
						System.out.println(callee);
					Iterator it = calledMessages.iterator();

					while (it.hasNext() && toBeRemoved) {
						Message msg = (Message) it.next();
						if (msg instanceof Operation
							&& this.v0.getDomain().contains(
								this.sd.getMessageIdx(msg))) {
							toBeRemoved = false;
						}
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
