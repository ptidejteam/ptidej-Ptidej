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

import modec.solver.constraint.Callee;
import modec.solver.constraint.Caller;
import modec.solver.constraint.Created;
import modec.solver.constraint.Creator;
import modec.solver.constraint.IsContainedInMessage;
import modec.solver.constraint.MessageFollows;
import modec.solver.constraint.SameSuperType;
import modec.util.ExecutionTraceParser;
import util.io.ProxyDisk;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.integer.constraints.PalmNotEqualXYC;
import choco.palm.search.PalmSolution;
import metamodel.scenariodiagram.ScenarioDiagram;

/**
 * @author Janice Ng
 */
public class Builder_QuickUML_v2001_BuildAClass {

	public static void main(final String[] args) {

		long timeStart = System.currentTimeMillis();
		System.out.println("Start time : " + timeStart);

		final PalmProblem problem = new PalmProblem();
		final ExecutionTraceParser etp =
			new ExecutionTraceParser(
				"../MoDeC Bytecode Instrumentation Tests/Evaluation_QuickUML_Builder_BuildJavaTataClass.trace");

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

		final Map subclasses = new HashMap();
		subclasses.put("uml.builder.CodeBuilder", codeBuilderChildren);

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

		final IntVar v_newConcreteBuilder1 =
			problem.makeBoundIntVar("newConcreteBuilder1", 0, nbMessages);

		final IntVar v_newConcreteBuilder2 =
			problem.makeBoundIntVar("newConcreteBuilder2", 0, nbMessages);

		final IntVar v_newConcreteBuilder3 =
			problem.makeBoundIntVar("newConcreteBuilder3", 0, nbMessages);

		final IntVar v_newConcreteBuilder4 =
			problem.makeBoundIntVar("newConcreteBuilder4", 0, nbMessages);

		final IntVar v_newConcreteBuilder5 =
			problem.makeBoundIntVar("newConcreteBuilder5", 0, nbMessages);

		final IntVar v_newDirector =
			problem.makeBoundIntVar("newDirector", 0, nbMessages);

		final IntVar v_construct =
			problem.makeBoundIntVar("construct", 0, nbMessages);

		final IntVar v_buildPart1 =
			problem.makeBoundIntVar("buildPart1", 0, nbMessages);

		final IntVar v_buildPart2 =
			problem.makeBoundIntVar("buildPart2", 0, nbMessages);

		final IntVar v_buildPart3 =
			problem.makeBoundIntVar("buildPart3", 0, nbMessages);

		final IntVar v_buildPart4 =
			problem.makeBoundIntVar("buildPart4", 0, nbMessages);

		final IntVar v_buildPart5 =
			problem.makeBoundIntVar("buildPart5", 0, nbMessages);

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

		problem.post(new SameSuperType(
			v_aConcreteBuilder1,
			v_aConcreteBuilder2,
			sd,
			subclasses));

		problem.post(new SameSuperType(
			v_aConcreteBuilder2,
			v_aConcreteBuilder3,
			sd,
			subclasses));

		problem.post(new SameSuperType(
			v_aConcreteBuilder3,
			v_aConcreteBuilder4,
			sd,
			subclasses));

		problem.post(new SameSuperType(
			v_aConcreteBuilder4,
			v_aConcreteBuilder5,
			sd,
			subclasses));

		problem.post(new MessageFollows(
			v_newConcreteBuilder1,
			v_construct,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_newConcreteBuilder1,
			v_buildPart1,
			sd,
			componentsMessages));

		problem.post(new MessageFollows(
			v_newConcreteBuilder2,
			v_construct,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_newConcreteBuilder2,
			v_buildPart2,
			sd,
			componentsMessages));

		problem.post(new MessageFollows(
			v_newConcreteBuilder3,
			v_construct,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_newConcreteBuilder3,
			v_buildPart3,
			sd,
			componentsMessages));

		problem.post(new MessageFollows(
			v_newConcreteBuilder4,
			v_construct,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_newConcreteBuilder4,
			v_buildPart4,
			sd,
			componentsMessages));

		problem.post(new MessageFollows(
			v_newConcreteBuilder5,
			v_construct,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_newConcreteBuilder5,
			v_buildPart5,
			sd,
			componentsMessages));

		problem.post(new MessageFollows(
			v_newDirector,
			v_construct,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_newDirector,
			v_buildPart1,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_newDirector,
			v_buildPart2,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_newDirector,
			v_buildPart3,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_newDirector,
			v_buildPart4,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_newDirector,
			v_buildPart5,
			sd,
			componentsMessages));

		problem.post(new MessageFollows(
			v_buildPart1,
			v_buildPart2,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_buildPart1,
			v_buildPart3,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_buildPart1,
			v_buildPart4,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_buildPart1,
			v_buildPart5,
			sd,
			componentsMessages));

		problem.post(new MessageFollows(
			v_buildPart2,
			v_buildPart3,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_buildPart2,
			v_buildPart4,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_buildPart2,
			v_buildPart5,
			sd,
			componentsMessages));

		problem.post(new MessageFollows(
			v_buildPart3,
			v_buildPart4,
			sd,
			componentsMessages));
		problem.post(new MessageFollows(
			v_buildPart3,
			v_buildPart5,
			sd,
			componentsMessages));

		problem.post(new MessageFollows(
			v_buildPart4,
			v_buildPart5,
			sd,
			componentsMessages));

		problem.post(new IsContainedInMessage(
			v_buildPart1,
			v_construct,
			sd,
			componentsMessages));
		problem.post(new IsContainedInMessage(
			v_buildPart2,
			v_construct,
			sd,
			componentsMessages));
		problem.post(new IsContainedInMessage(
			v_buildPart3,
			v_construct,
			sd,
			componentsMessages));
		problem.post(new IsContainedInMessage(
			v_buildPart4,
			v_construct,
			sd,
			componentsMessages));
		problem.post(new IsContainedInMessage(
			v_buildPart5,
			v_construct,
			sd,
			componentsMessages));
		problem.post(new Created(
			v_newConcreteBuilder1,
			v_aConcreteBuilder1,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Creator(
			v_aClient,
			v_newConcreteBuilder1,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Created(
			v_newConcreteBuilder2,
			v_aConcreteBuilder2,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Creator(
			v_aClient,
			v_newConcreteBuilder2,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Created(
			v_newConcreteBuilder3,
			v_aConcreteBuilder3,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Creator(
			v_aClient,
			v_newConcreteBuilder3,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Created(
			v_newConcreteBuilder4,
			v_aConcreteBuilder4,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Creator(
			v_aClient,
			v_newConcreteBuilder4,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Created(
			v_newConcreteBuilder5,
			v_aConcreteBuilder5,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Creator(
			v_aClient,
			v_newConcreteBuilder5,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Created(
			v_newDirector,
			v_aDirector,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Creator(
			v_aClient,
			v_newDirector,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aClient,
			v_construct,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_construct,
			v_aDirector,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aDirector,
			v_buildPart1,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_buildPart1,
			v_aConcreteBuilder1,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aDirector,
			v_buildPart2,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_buildPart2,
			v_aConcreteBuilder2,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aDirector,
			v_buildPart3,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_buildPart3,
			v_aConcreteBuilder3,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aDirector,
			v_buildPart4,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_buildPart4,
			v_aConcreteBuilder4,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Caller(
			v_aDirector,
			v_buildPart5,
			sd,
			componentsMessages,
			allClassifiers));
		problem.post(new Callee(
			v_buildPart5,
			v_aConcreteBuilder5,
			sd,
			componentsMessages,
			allClassifiers));

		problem.logger.setLevel(Level.INFO);
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
									"../MoDeC Bytecode Instrumentation Tests/Solution_QuickUML_Builder_BuildAClass.txt",
									true);
						BufferedWriter out = new BufferedWriter(fstream);

						final List solutions =
							problem.getPalmSolver().solutions;
						out.write(solutions.size() + "\n");
						final PalmSolution solution =
							(PalmSolution) solutions.get(solutions.size() - 1);

						out.write("OPERATION [newConcreteBuilder1]: "
								+ sd.getIdxMessage(solution.getValue(7))
								+ "OPERATION [newConcreteBuilder2]: "
								+ sd.getIdxMessage(solution.getValue(8))
								+ "OPERATION [newConcreteBuilder3]: "
								+ sd.getIdxMessage(solution.getValue(9))
								+ "OPERATION [newConcreteBuilder4]: "
								+ sd.getIdxMessage(solution.getValue(10))
								+ "OPERATION [newConcreteBuilder5]: "
								+ sd.getIdxMessage(solution.getValue(11))
								+ "OPERATION [newDirector]: "
								+ sd.getIdxMessage(solution.getValue(12))
								+ "OPERATION [construct]: "
								+ sd.getIdxMessage(solution.getValue(13))
								+ "OPERATION [buildPart1]: "
								+ sd.getIdxMessage(solution.getValue(14))
								+ "OPERATION [buildPart2]: "
								+ sd.getIdxMessage(solution.getValue(15))
								+ "OPERATION [buildPart3]: "
								+ sd.getIdxMessage(solution.getValue(16))
								+ "OPERATION [buildPart4]: "
								+ sd.getIdxMessage(solution.getValue(17))
								+ "OPERATION [buildPart5]: "
								+ sd.getIdxMessage(solution.getValue(18))
								+ "PARTICIPANT [aClient]: "
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
}
