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

import java.util.List;
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
import modec.metamodel.Message;
import modec.metamodel.ScenarioDiagram;
import modec.util.ExecutionTraceParser;

/**
 * @author Janice Ng
 */
public class MessageFollows
	extends AbstractPalmBinIntConstraint
	implements Constraint {

	private ScenarioDiagram sd;
	private List componentsMessages;

	public static void main(final String[] args) {
		final PalmProblem problem = new PalmProblem();
		final ExecutionTraceParser etp =
			//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Builder_SearchManager.trace");
		//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/TestFilms.trace");
		//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Memento_DCClient.trace");
		//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Visitor_OrderManager_EVALUATION.trace");
	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Evaluation_SearchManager_Builder_GetSQLStatement.trace");

		final ScenarioDiagram sd = etp.getScenarioDiagram();
		final List componentsMessages = sd.visitComponentMessages();
		final List allClassifiers = sd.getAllClassifiers(componentsMessages);
		final int nbMessages = sd.countNbMessages(componentsMessages);
		final int nbClassifiers = sd.countNbClassifiers(allClassifiers);
		System.out.println(nbMessages + " " + nbClassifiers);

		final IntVar v_message1 =
			problem.makeBoundIntVar("message1", 0, nbMessages);
		final IntVar v_message2 =
			problem.makeBoundIntVar("message2", 0, nbMessages);

		problem.post(
			new MessageFollows(v_message1, v_message2, sd, componentsMessages));

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

					final List solutions = problem.getPalmSolver().solutions;
					System.out.println(solutions.size());
					final PalmSolution solution =
						(PalmSolution) solutions.get(solutions.size() - 1);

					System.out.println(
						"OPERATION [1]: "
							+ sd.getIdxMessage(solution.getValue(0))
							+ "OPERATION [2]: "
							+ sd.getIdxMessage(solution.getValue(1)));
				}
			}
		});

		problem.solve(true);
		//System.out.println(problem.getPalmSolver().solutions);

	}

	public MessageFollows(
		IntVar v0,
		IntVar v1,
		ScenarioDiagram sd,
		List componentsMessages) {
		this.v0 = v0;
		this.v1 = v1;
		this.sd = sd;
		this.componentsMessages = componentsMessages;
		this.hook = new PalmConstraintPlugin(this);
	}

	public void propagate() {

		if (this.v0.getDomain().getSize() > 0) {
			int index_e0 = this.v0.getDomain().getSup();
			boolean toBeRemoved = true;

			while (index_e0 > -1 && toBeRemoved) {

				int index_e1 = this.v1.getDomain().getSup();
				Message msg0 = this.sd.getIdxMessage(index_e0);
				Message msg1 = this.sd.getIdxMessage(index_e1);

				if (this.componentsMessages.indexOf(msg1)
					> this.componentsMessages.indexOf(msg0))
					toBeRemoved = false;

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
				index_e0--;
			}
		}

		if (this.v1.getDomain().getSize() > 0) {
			int index_e1 = this.v1.getDomain().getSup();
			boolean toBeRemoved = true;

			while (index_e1 > -1 && toBeRemoved) {
				int index_e0 = this.v0.getDomain().getSup();
				Message msg0 = this.sd.getIdxMessage(index_e0);
				Message msg1 = this.sd.getIdxMessage(index_e1);

				if (this.componentsMessages.indexOf(msg1)
					> this.componentsMessages.indexOf(msg0))
					toBeRemoved = false;

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
				index_e1--;
			}
		}
	}

	//		public void propagate() {
	//	
	//			if (v0.getDomain().getSize() > 0) {
	//				IntIterator iterator0 = v0.getDomain().getIterator();
	//				boolean toBeRemoved = true;
	//	
	//				while (iterator0.hasNext() && toBeRemoved) {
	//					int index_e0 = iterator0.next();
	//	
	//					if (index_e0 > -1) {
	//						Message msg0 =
	//							this.sd.getIdxMessage(componentsMessages, index_e0);
	//	
	//						IntIterator iterator1 = v1.getDomain().getIterator();
	//	
	//						while (iterator1.hasNext() && toBeRemoved) {
	//							int index_e1 = iterator1.next();
	//	
	//							if (index_e1 > -1) {
	//								Message msg1 =
	//									this.sd.getIdxMessage(
	//										componentsMessages,
	//										index_e1);
	//	
	//								if (componentsMessages.indexOf(msg1)
	//									> componentsMessages.indexOf(msg0)
	//									&& !msg1.equals(msg0))
	//									toBeRemoved = false;
	//							}
	//						}
	//	
	//						if (toBeRemoved) {
	//							choco.palm.explain.Explanation expl =
	//								((PalmProblem) this.getProblem()).makeExplanation();
	//							((PalmConstraintPlugin) this.getPlugIn()).self_explain(
	//								expl);
	//							((PalmIntVar) this.v1).self_explain(
	//								PalmIntDomain.DOM,
	//								expl);
	//							((PalmIntVar) this.v0).removeVal(
	//								index_e0,
	//								this.cIdx0,
	//								expl);
	//						}
	//					}
	//				}
	//			}
	//	
	//			if (v1.getDomain().getSize() > 0) {
	//				IntIterator iterator1 = v1.getDomain().getIterator();
	//				boolean toBeRemoved = true;
	//	
	//				while (iterator1.hasNext() && toBeRemoved) {
	//					int index_e1 = iterator1.next();
	//	
	//					if (index_e1 > -1) {
	//						Message msg1 =
	//							this.sd.getIdxMessage(componentsMessages, index_e1);
	//	
	//						if (componentsMessages.indexOf(msg1)
	//							< v0.getDomain().getSup())
	//							toBeRemoved = false;
	//	
	//						IntIterator iterator0 = v0.getDomain().getIterator();
	//	
	//						while (iterator0.hasNext() && toBeRemoved) {
	//							int index_e0 = iterator0.next();
	//	
	//							if (index_e0 > -1) {
	//								Message msg0 =
	//									this.sd.getIdxMessage(
	//										componentsMessages,
	//										index_e0);
	//								if (componentsMessages.indexOf(msg1)
	//									> componentsMessages.indexOf(msg0)
	//									&& !msg1.equals(msg0)) {
	//									toBeRemoved = false;
	//								}
	//							}
	//						}
	//	
	//						if (toBeRemoved) {
	//							choco.palm.explain.Explanation expl =
	//								((PalmProblem) this.getProblem()).makeExplanation();
	//							((PalmConstraintPlugin) this.getPlugIn()).self_explain(
	//								expl);
	//							((PalmIntVar) this.v0).self_explain(
	//								PalmIntDomain.DOM,
	//								expl);
	//							((PalmIntVar) this.v1).removeVal(
	//								index_e1,
	//								this.cIdx1,
	//								expl);
	//						}
	//					}
	//				}
	//			}
	//		}

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
