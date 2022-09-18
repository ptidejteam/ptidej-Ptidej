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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import metamodel.scenarioDiagram.Classifier;
import metamodel.scenarioDiagram.Instance;
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
import choco.palm.integer.constraints.PalmNotEqualXYC;
import choco.palm.search.PalmSolution;
import choco.util.IntIterator;

/**
 * @author Janice Ng
 */
public class NotSameSuperType extends AbstractPalmBinIntConstraint implements
		Constraint {

	private ScenarioDiagram sd;
	//private List componentsMessages;
	//private List allClassifiers;
	private Map subclasses;

	public static void main(final String[] args) {

		long timeStart = System.currentTimeMillis();
		System.out.println("Start time : " + timeStart);

		final PalmProblem problem = new PalmProblem();
		final ExecutionTraceParser etp =
			new ExecutionTraceParser(
				"../MoDeC Bytecode Instrumentation Tests/Evaluation_QuickUML_Command_ToggleRefreshADiagram.trace");
		//new ExecutionTraceParser("../MoDeC Bytecode Instrumentation Tests/Evaluation_QuickUML_Builder_BuildJavaTataClass.trace");

		final ScenarioDiagram sd = etp.getScenarioDiagram();
		final List componentsMessages = sd.visitComponentMessages();
		final List allClassifiers = sd.getAllClassifiers(componentsMessages);
		final int nbMessages = sd.countNbMessages(componentsMessages);
		final int nbClassifiers = sd.countNbClassifiers(allClassifiers);

		final List codeBuilderChildren = new ArrayList();
		codeBuilderChildren.add("uml.builder.AbstractBuilder");
		codeBuilderChildren.add("uml.builder.AssociationBuilder");
		codeBuilderChildren.add("uml.builder.ClassBuilder");
		codeBuilderChildren.add("uml.builder.CompositionBuilder");
		codeBuilderChildren.add("uml.builder.InheritanceBuilder");
		codeBuilderChildren.add("uml.builder.ObjectBuilder");
		codeBuilderChildren.add("uml.builder.CPlusPlusBuilder");
		codeBuilderChildren.add("uml.builder.JavaBuilder");
		codeBuilderChildren.add("uml.builder.RealizationBuilder");

		final Map codeBuilderSubclasses = new HashMap();
		codeBuilderSubclasses.put(
			"uml.builder.CodeBuilder",
			codeBuilderChildren);

		final List toolChildren = new ArrayList();
		toolChildren.add("diagram.tool.AbstractTool");
		toolChildren.add("diagram.tool.CardinalityTool");
		toolChildren.add("diagram.tool.ClipboardTool");
		toolChildren.add("diagram.tool.EditingTool");
		toolChildren.add("diagram.tool.FigureDraggingTool");
		toolChildren.add("diagram.tool.FigureShappingTool");
		toolChildren.add("diagram.tool.FigureTool");
		toolChildren.add("diagram.tool.LinkShappingTool");
		toolChildren.add("diagram.tool.LinkTool");
		toolChildren.add("diagram.tool.AssociationTool");
		toolChildren.add("diagram.tool.CompositionTool");
		toolChildren.add("diagram.tool.DependencyTool");
		toolChildren.add("diagram.tool.GeneralizationTool");
		toolChildren.add("diagram.tool.RealizationTool");
		toolChildren.add("diagram.tool.SelectionTool");
		toolChildren.add("diagram.tool.CompositeTool");

		final Map toolSubclasses = new HashMap();
		toolSubclasses.put("diagram.tool.Tool", toolChildren);

		sd.determineSourceCalledMessages(componentsMessages, allClassifiers);
		sd.determineDestinationCalledMessages(
			componentsMessages,
			allClassifiers);
		sd.determineClassifierIdx(allClassifiers);
		sd.determineIdxClassifier(allClassifiers);
		sd.determineMessageContainer(componentsMessages);

		System.out.println(nbMessages + " " + nbClassifiers);

		// VISITOR 1 
		final IntVar v_aClient =
			problem.makeBoundIntVar("aClient", 0, nbClassifiers);

		final IntVar v_aDirector =
			problem.makeBoundIntVar("aDirector", 0, nbClassifiers);

		final IntVar v_aConcreteBuilder1 =
			problem.makeBoundIntVar("aConcreteBuilder1", 0, nbClassifiers);

		final IntVar v_aConcreteBuilder2 =
			problem.makeBoundIntVar("aConcreteBuilder2", 0, nbClassifiers);

		final IntVar v_aConcreteBuilder3 =
			problem.makeBoundIntVar("aConcreteBuilder3", 0, nbClassifiers);

		final IntVar v_aConcreteBuilder4 =
			problem.makeBoundIntVar("aConcreteBuilder4", 0, nbClassifiers);

		final IntVar v_aConcreteBuilder5 =
			problem.makeBoundIntVar("aConcreteBuilder5", 0, nbClassifiers);

		problem.post(new PalmNotEqualXYC(v_aClient, v_aDirector, 0));
		problem.post(new PalmNotEqualXYC(v_aClient, v_aConcreteBuilder1, 0));
		problem.post(new PalmNotEqualXYC(v_aDirector, v_aConcreteBuilder1, 0));
		problem.post(new PalmNotEqualXYC(v_aClient, v_aConcreteBuilder2, 0));
		problem.post(new PalmNotEqualXYC(v_aDirector, v_aConcreteBuilder2, 0));
		problem.post(new PalmNotEqualXYC(v_aClient, v_aConcreteBuilder3, 0));
		problem.post(new PalmNotEqualXYC(v_aDirector, v_aConcreteBuilder3, 0));
		problem.post(new PalmNotEqualXYC(v_aClient, v_aConcreteBuilder4, 0));
		problem.post(new PalmNotEqualXYC(v_aDirector, v_aConcreteBuilder4, 0));
		problem.post(new PalmNotEqualXYC(v_aClient, v_aConcreteBuilder5, 0));
		problem.post(new PalmNotEqualXYC(v_aDirector, v_aConcreteBuilder5, 0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteBuilder1,
			v_aConcreteBuilder2,
			0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteBuilder1,
			v_aConcreteBuilder3,
			0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteBuilder1,
			v_aConcreteBuilder4,
			0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteBuilder1,
			v_aConcreteBuilder5,
			0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteBuilder2,
			v_aConcreteBuilder3,
			0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteBuilder2,
			v_aConcreteBuilder4,
			0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteBuilder2,
			v_aConcreteBuilder5,
			0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteBuilder3,
			v_aConcreteBuilder4,
			0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteBuilder3,
			v_aConcreteBuilder5,
			0));
		problem.post(new PalmNotEqualXYC(
			v_aConcreteBuilder4,
			v_aConcreteBuilder5,
			0));

		problem.post(new NotSameSuperType(
			v_aClient,
			v_aDirector,
			sd,
			toolSubclasses));

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
									"../MoDeC Bytecode Instrumentation Tests/Solution_QuickUML_NoPatternBuilder_ToggleRefreshADiagram.txt",
									true);
						BufferedWriter out = new BufferedWriter(fstream);

						final List solutions =
							problem.getPalmSolver().solutions;
						out.write(solutions.size() + "\n");
						final PalmSolution solution =
							(PalmSolution) solutions.get(solutions.size() - 1);

						out.write("PARTICIPANT [aClient]: "
								+ sd.getIdxClassifier(solution.getValue(0))
								+ "\nPARTICIPANT [aDirector]: "
								+ sd.getIdxClassifier(solution.getValue(1))
								+ "\nPARTICIPANT [aConcreteBuidler1]: "
								+ sd.getIdxClassifier(solution.getValue(2))
								+ "\nPARTICIPANT [aConcreteBuidler2]: "
								+ sd.getIdxClassifier(solution.getValue(3))
								+ "\nPARTICIPANT [aConcreteBuidler3]: "
								+ sd.getIdxClassifier(solution.getValue(4))
								+ "\nPARTICIPANT [aConcreteBuidler4]: "
								+ sd.getIdxClassifier(solution.getValue(5))
								+ "\nPARTICIPANT [aConcreteBuidler5]: "
								+ sd.getIdxClassifier(solution.getValue(6))
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
		System.out.println("*** the end : "
				+ problem.getPalmSolver().solutions.size());
		long timeEnd = System.currentTimeMillis();
		System.out.println("End time : " + timeEnd);
		System.out.println("Duree : " + (timeEnd - timeStart) + "ms.");
	}

	public NotSameSuperType(IntVar v0, IntVar v1, ScenarioDiagram sd,
	//List componentsMessages,
	//List allClassifiers,
		Map subclasses) {
		this.v0 = v0;
		this.v1 = v1;
		this.sd = sd;
		//this.componentsMessages = componentsMessages;
		//this.allClassifiers = allClassifiers;
		this.subclasses = new HashMap(subclasses);
		this.hook = new PalmConstraintPlugin(this);
	}

	public void propagate() {
		if (this.v0.getDomain().getSize() > 0) {
			final IntIterator iterator0 = this.v0.getDomain().getIterator();
			//boolean toBeRemoved = true;

			while (iterator0.hasNext()) {
				final int index_e0 = iterator0.next();
				if (index_e0 > -1) {
					boolean toBeRemoved = true;
					Classifier class_e0 = this.sd.getIdxClassifier(index_e0);

					IntIterator iterator1 = this.v1.getDomain().getIterator();
					while (iterator1.hasNext() && toBeRemoved) {

						final int index_e1 = iterator1.next();
						Classifier class_e1 =
							this.sd.getIdxClassifier(index_e1);
						String superClass_e0 =
							this
								.getClassSuperType((metamodel.scenarioDiagram.Class) (((ArrayList) (((Instance) (class_e0))
									.getOfClass())).get(0)));
						String superClass_e1 =
							this
								.getClassSuperType((metamodel.scenarioDiagram.Class) (((ArrayList) (((Instance) (class_e1))
									.getOfClass())).get(0)));

						if (class_e0 instanceof Instance
								&& class_e1 instanceof Instance
								&& ((superClass_e0.equals("") && superClass_e1
									.equals("")) || !superClass_e0
									.equals(superClass_e1))) {
							toBeRemoved = false;
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
			final IntIterator iterator1 = this.v1.getDomain().getIterator();
			//			boolean toBeRemoved = true;
			while (iterator1.hasNext() /*& toBeRemoved*/
			) {
				final int index_e1 = iterator1.next();
				if (index_e1 > -1) {
					boolean toBeRemoved = true;
					Classifier class_e1 = this.sd.getIdxClassifier(index_e1);

					IntIterator iterator0 = this.v0.getDomain().getIterator();
					while (iterator0.hasNext() && toBeRemoved) {

						final int index_e0 = iterator0.next();
						Classifier class_e0 =
							this.sd.getIdxClassifier(index_e0);
						String superClass_e0 =
							this
								.getClassSuperType((metamodel.scenarioDiagram.Class) (((ArrayList) (((Instance) (class_e0))
									.getOfClass())).get(0)));
						String superClass_e1 =
							this
								.getClassSuperType((metamodel.scenarioDiagram.Class) (((ArrayList) (((Instance) (class_e1))
									.getOfClass())).get(0)));

						if (class_e0 instanceof Instance
								&& class_e1 instanceof Instance
								&& ((superClass_e0.equals("") && superClass_e1
									.equals("")) || !superClass_e0
									.equals(superClass_e1))) {
							toBeRemoved = false;
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

	public String getClassSuperType(metamodel.scenarioDiagram.Class c) {
		Set e = (Set) this.subclasses.keySet();
		Iterator it = e.iterator();

		while (it.hasNext()) {
			String parent = (String) it.next();
			List children = (ArrayList) (this.subclasses.get(parent));

			if (children.contains(c.getName()))
				return parent;
		}

		return "";
	}

}
