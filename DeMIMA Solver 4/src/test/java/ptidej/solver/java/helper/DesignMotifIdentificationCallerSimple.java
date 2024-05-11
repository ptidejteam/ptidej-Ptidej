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
package ptidej.solver.java.helper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Properties;
import padl.generator.helper.ModelGenerator;
import padl.kernel.IIdiomLevelModel;
import padl.visitor.IWalker;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.java.Problem;
import ptidej.solver.java.domain.Generator;
import ptidej.solver.java.domain.Manager;
import ptidej.solver.java.problem.CompositeMotif;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;

/**
 * @author yann
 * @since  2013/09/25
 */
public class DesignMotifIdentificationCallerSimple {
	public static void main(final String[] args) throws FileNotFoundException,
			IOException {

		final String path = "../DeMIMA Solver Tests/rsc/JHotDraw v5.2.jar";
		final String name = "JHotDraw v5.2.ini";

		final IIdiomLevelModel idiomLevelModel =
			ModelGenerator.generateModelFromJAR(path);
		final IWalker constraintModelBuilder = new Generator();
		final List listOfModelEntities =
			Manager.build(idiomLevelModel, constraintModelBuilder);
		final Problem constraintProblem =
			CompositeMotif.getProblem(listOfModelEntities);

		final Writer writer = ProxyDisk.getInstance().fileTempOutput(name);
		constraintProblem.setWriter(new PrintWriter(writer));
		constraintProblem.automaticSolve(true);

		final Reader reader = ProxyDisk.getInstance().fileTempInput(name);
		final Properties properties = new Properties();
		properties.load(new ReaderInputStream(reader));
		final OccurrenceBuilder solutionBuilder =
			OccurrenceBuilder.getInstance();
		final Occurrence[] solutions =
			solutionBuilder.getCanonicalOccurrences(properties);

		System.out.print("Found ");
		System.out.print(solutions.length);
		System.out.println(" solutions.");
	}
}
