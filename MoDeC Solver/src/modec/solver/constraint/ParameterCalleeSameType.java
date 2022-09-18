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

import java.io.BufferedWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import metamodel.scenarioDiagram.Class;
import metamodel.scenarioDiagram.Instance;
import metamodel.scenarioDiagram.Message;
import metamodel.scenarioDiagram.ScenarioDiagram;
import modec.util.ExecutionTraceParser;
import util.io.ProxyDisk;
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

/**
 * @author Janice Ng
 */
public class ParameterCalleeSameType extends AbstractPalmBinIntConstraint
		implements Constraint {

	private ScenarioDiagram sd;
	private Map subclasses;

	public static void main(final String[] args) {
		final PalmProblem problem = new PalmProblem();
		final ExecutionTraceParser etp =
			//	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/JANICE.trace");
			//	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/JANICE_court_2.trace");
			//	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/JHotDraw_EVALUATION.trace");
			new ExecutionTraceParser(
				"../MoDeC Bytecode Instrumentation Tests/JHotDraw_EVALUATION_court.trace");
		//	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Builder_SearchManager.trace");
		//	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/TestFilms.trace");
		//	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Memento_DCClient.trace");
		//	new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Visitor_OrderManager_EVALUATION.trace");

		final ScenarioDiagram sd = etp.getScenarioDiagram();
		final List componentsMessages = sd.visitComponentMessages();
		//final List allClassifiers = sd.getAllClassifiers(componentsMessages);
		final int nbMessages = sd.countNbMessages(componentsMessages);
		//final int nbClassifiers = sd.countNbClassifiers(allClassifiers);

		final List figureVisitorType = new ArrayList();
		figureVisitorType.add("org.jhotdraw.standard.DeleteFromDrawingVisitor");
		figureVisitorType.add("org.jhotdraw.standard.InsertIntoDrawingVisitor");

		final List figureType = new ArrayList();
		figureType.add("org.jhotdraw.standard.AbstracFigure");
		figureType.add("org.jhotdraw.figures.AttributeFigure");
		figureType.add("org.jhotdraw.contrib.ComponentFigure");
		figureType.add("org.jhotdraw.figures.EllipseFigure");
		figureType
			.add("org.jhotdraw.contrib.html.EllipseFigureGeometricAdapter");
		figureType.add("org.jhotdraw.figures.ImageFigure");
		figureType.add("org.jhotdraw.contrib.PolygonFigure");
		figureType
			.add("org.jhotdraw.contrib.html.PolygonFigureGeometricAdapter");
		figureType.add("org.jhotdraw.figures.RectangleFigure");
		figureType.add("org.jhotdraw.contrib.DiamondFigure");
		figureType
			.add("org.jhotdraw.contrib.html.DiamondFigureGeometricAdapter");
		figureType.add("org.jhotdraw.contrib.TriangleFigure");
		figureType
			.add("org.jhotdraw.contrib.html.TriangleFigureGeometricAdapter");
		figureType.add("org.jhotdraw.figures.RoundRectangleFigure");
		figureType
			.add("org.jhotdraw.contrib.html.RoundRectangleFigureGeometricAdapter");
		figureType.add("org.jhotdraw.contrib.TextAreaFigure");
		figureType.add("org.jhotdraw.contrib.html.HTMLTextAreaFigure");
		figureType.add("org.jhotdraw.figuresTextFigure");
		figureType.add("org.jhotdraw.samples.net.NodeFigure");
		figureType.add("org.jhotdraw.figures.NumberTextFigure");
		figureType.add("org.jhotdraw.standard.CompositeFigure");
		figureType.add("org.jhotdraw.contrib.GraphicalCompositeFigure");
		figureType.add("org.jhotdraw.figures.GroupFigure");
		figureType.add("org.jhotdraw.samples.pert.PertFigure");
		figureType.add("org.jhotdraw.standard.StandardDrawing");
		figureType.add("org.jhotdraw.samples.javadraw.BouncingDrawing");
		figureType.add("org.jhotdraw.standard.DecoratorFigure");
		figureType.add("org.jhotdraw.samples.javadraw.AnimationDecorator");
		figureType.add("org.jhotdraw.figures.BorderDecorator");
		figureType.add("org.jhotdraw.figures.NullFigure");
		figureType.add("org.jhotdraw.figures.PolyLineFigure");
		figureType.add("org.jhotdraw.figures.LineConnection");
		figureType.add("org.jhotdraw.figures.ElbowConnection");
		figureType.add("org.jhotdraw.samples.pert.PertDependency");
		figureType.add("org.jhotdraw.figures.LineFigure");

		final Map subclasses = new HashMap();
		subclasses.put(
			"org.jhotdraw.framework.FigureVisitor",
			figureVisitorType);
		subclasses.put("org.jhotdraw.framework.Figure", figureType);

		System.out.println(nbMessages /*+ " " + nbClassifiers*/
		);

		final IntVar v_message1 =
			problem.makeBoundIntVar("msg0", 0, nbMessages);
		final IntVar v_message2 =
			problem.makeBoundIntVar("msg1", 0, nbMessages);

		problem.post(new ParameterCalleeSameType(
			v_message1,
			v_message2,
			sd,
			componentsMessages,
			subclasses));

		// Yann 2013/08/12: Needed?
		//	problem.logger.setLevel(Level.INFO);
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
									"../MoDeC Bytecode Instrumentation Tests/JANICE.parametercallersametype",
									true);
						BufferedWriter out = new BufferedWriter(fstream);

						final List solutions =
							problem.getPalmSolver().solutions;
						out.write(solutions.size() + "\n");
						final PalmSolution solution =
							(PalmSolution) solutions.get(solutions.size() - 1);

						out.write("OPERATION [msg0]: "
								+ sd.getIdxMessage(solution.getValue(0))
								+ "OPERATION [msg1]: "
								+ sd.getIdxMessage(solution.getValue(1))
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
		//System.out.println(problem.getPalmSolver().solutions);
	}

	public ParameterCalleeSameType(
		IntVar v0,
		IntVar v1,
		ScenarioDiagram sd,
		List componentsMessages,
		Map subclasses) {
		this.v0 = v0;
		this.v1 = v1;
		this.sd = sd;
		this.subclasses = new HashMap(subclasses);
		this.hook = new PalmConstraintPlugin(this);
	}

	public void propagate() {

		if (this.v0.getDomain().getSize() > 0) {

			IntIterator iterator0 = this.v0.getDomain().getIterator();

			while (iterator0.hasNext()) {

				int index_e0 = iterator0.next();

				if (index_e0 > -1) {

					boolean toBeRemoved = true;
					Message msg0 = this.sd.getIdxMessage(index_e0);
					IntIterator iterator1 = this.v1.getDomain().getIterator();

					while (iterator1.hasNext() && toBeRemoved) {

						int index_e1 = iterator1.next();

						if (index_e1 > -1) {

							Message msg1 = this.sd.getIdxMessage(index_e1);
							String calleeType = "";

							if (msg1.getDestinationClassifier() instanceof Class)
								calleeType =
									((Class) msg1.getDestinationClassifier())
										.getName();
							else
								calleeType =
									((Class) (((ArrayList) (((Instance) msg1
										.getDestinationClassifier())
										.getOfClass())).get(0))).getName();

							if (!msg0.isArgumentsEmpty()
									&& msg0.getArguments().size() >= 1) {

								if (msg0
									.getArguments(0)
									.getType()
									.equals(calleeType.trim())
										&& !msg0.equals(msg1))
									toBeRemoved = false;
								else if (this.subclasses.containsKey(msg0
									.getArguments(0)
									.getType())) {
									List types =
										(ArrayList) this.subclasses.get(msg0
											.getArguments(0)
											.getType());
									if (types.contains(calleeType)
											&& !msg0.equals(msg1))
										toBeRemoved = false;
								}
							}
						}
					}

					if (toBeRemoved) {
						choco.palm.explain.Explanation expl =
							((PalmProblem) this.getProblem()).makeExplanation();
						((PalmConstraintPlugin) this.getPlugIn())
							.self_explain(expl);
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

			IntIterator iterator1 = this.v1.getDomain().getIterator();

			while (iterator1.hasNext()) {

				int index_e1 = iterator1.next();

				if (index_e1 > -1) {

					boolean toBeRemoved = true;
					Message msg1 = this.sd.getIdxMessage(index_e1);
					IntIterator iterator0 = this.v0.getDomain().getIterator();

					while (iterator0.hasNext()) {

						int index_e0 = iterator0.next();

						if (index_e0 > -1) {

							Message msg0 = this.sd.getIdxMessage(index_e0);

							if (!msg0.isArgumentsEmpty()
									&& msg0.getArguments().size() >= 1) {

								String argumentType =
									msg0.getArguments(0).getType();
								String calleeType = "";

								if (msg1.getDestinationClassifier() instanceof Class)
									calleeType =
										((Class) msg1
											.getDestinationClassifier())
											.getName();
								else
									calleeType =
										((Class) (((ArrayList) (((Instance) msg1
											.getDestinationClassifier())
											.getOfClass())).get(0))).getName();

								if (calleeType.equals(argumentType)
										&& !msg0.equals(msg1))
									toBeRemoved = false;
								else if (this.subclasses
									.containsKey(argumentType)) {
									List types =
										(ArrayList) this.subclasses
											.get(argumentType);
									if (types.contains(calleeType.trim())
											&& !msg0.equals(msg1))
										toBeRemoved = false;
								}
							}

						}
					}

					if (toBeRemoved) {

						choco.palm.explain.Explanation expl =
							((PalmProblem) this.getProblem()).makeExplanation();
						((PalmConstraintPlugin) this.getPlugIn())
							.self_explain(expl);
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
