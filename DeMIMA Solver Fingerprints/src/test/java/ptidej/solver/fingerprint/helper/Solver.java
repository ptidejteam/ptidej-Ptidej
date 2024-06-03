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
package ptidej.solver.fingerprint.helper;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import padl.creator.classfile.CompleteClassFileCreator;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IGhost;
import padl.kernel.exception.CreationException;
import padl.kernel.impl.Factory;
import ptidej.solver.Occurrence;
import ptidej.solver.OccurrenceBuilder;
import ptidej.solver.OccurrenceComparator;
import ptidej.solver.OccurrenceComponent;
import ptidej.solver.fingerprint.ReducedDomainBuilder;
import ptidej.solver.java.Problem;
import ptidej.solver.java.domain.Manager;
import util.io.ProxyDisk;
import util.io.ReaderInputStream;
import choco.integer.IntVar;
import choco.integer.var.IntDomain;
import choco.util.IntIterator;

/**
 * @author Jean-Yves Guyomarc'h
 */
public abstract class Solver {
	protected static final String FingerprintPackageName = "ptidej.solver.fingerprint.problem.";
	protected static final String NoFingerprintPackageName = "ptidej.solver.java.problem.";

	private List allEntities;
	private ICodeLevelModel codeLevelModel;
	private Logger logger;
	private String motifName;
	private String[] packageNames;
	private String path;
	private ReducedDomainBuilder rdb;
	private String testName;

	public Solver(final String path, final String[] packageNames,
			final String testName, final String motifName) {

		this.path = path;
		this.packageNames = packageNames;
		this.testName = testName;
		this.motifName = motifName;
		this.logger = new Logger(this.testName);
	}

	private void checkVariables(final IntVar[] vars, final Problem problem) {
		for (int i = 0; i < vars.length; i++) {
			this.logger.addVar(vars[i].toString(), vars[i].getDomainSize(),
					(((double) (problem.getAllEntities().size()
							- (double) vars[i].getDomainSize())
							/ (double) problem.getAllEntities().size())
							* 100.0));
		}
	}

	public void computeDomainReduction(final PrintWriter output) {
		try {
			this.createModel();

			// Get Problem
			output.print("Domain reduction for ");
			output.println(this.testName);
			final Problem problem = (Problem) this.getMotif(Logger.WITH_RULES)
					.getMethod("getProblem",
							new Class[] { List.class,
									ReducedDomainBuilder.class })
					.invoke(null, new Object[] { this.allEntities, this.rdb });

			output.print("Original domain size: ");
			output.println(problem.getAllEntities().size());

			final IntVar[] vars = problem.getVars();
			for (int i = 0; i < vars.length; i++) {
				output.print("Reduced domain size for ");
				output.print(vars[i].toString());
				output.print(": ");
				output.print(vars[i].getDomainSize());
				output.print(" (");
				output.print((((double) (problem.getAllEntities().size()
						- (double) vars[i].getDomainSize())
						/ (double) problem.getAllEntities().size()) * 100.0));
				output.println("% of reduction)");

				if (vars[i].getDomainSize() > 0 && vars[i]
						.getDomainSize() < problem.getAllEntities().size()) {

					final IntDomain domain = vars[i].getDomain();
					final IntIterator iterator = domain.getIterator();
					final List allEntities = problem.getAllEntities();
					while (iterator.hasNext()) {
						output.print("\tEntity: ");
						output.println(allEntities.get(iterator.next()));
					}
				}
			}
		}
		catch (final InvocationTargetException e) {
			e.printStackTrace(System.err);
		}
		catch (final IllegalAccessException e) {
			e.printStackTrace(System.err);
		}
		catch (final NoSuchMethodException e) {
			e.printStackTrace(System.err);
		}
	}

	public void computeSolutions() {
		final Occurrence[] solutionsWithRules = computeSolutionsWithRules();
		final Occurrence[] solutionWithoutRules = computeSolutionsWithoutRules();

		this.logger.setIncluded(
				this.numIncluded(solutionsWithRules, solutionWithoutRules));

		final Occurrence[] excludedSolutions = this.excluded(solutionsWithRules,
				solutionWithoutRules);
		System.out.println(
				"Number of excluded solutions: " + excludedSolutions.length);
		this.printSolution(excludedSolutions,
				this.testName + " (excluded solutions)");
		this.logger.print();
	}

	public Occurrence[] computeSolutions(final int loggerMode,
			final Class[] problemArgsTypes, final Object[] problemArgs) {

		Occurrence[] nonGhostSol = null;
		try {
			//this.createModel();

			// Set Logger
			this.logger.setMode(loggerMode);
			this.logger.setStart(System.currentTimeMillis());

			// Get Problem
			final Problem problem = (Problem) this.getMotif(loggerMode)
					.getMethod("getProblem", problemArgsTypes)
					.invoke(null, problemArgs);
			this.logger.setProblemCreation(System.currentTimeMillis());

			// Check Vars
			this.checkVariables(problem.getVars(), problem);

			// GC
			for (int i = 0; i < 5; i++) {
				System.gc();
			}

			// Solve
			final Occurrence[] solutions = this.solve(problem);
			this.logger.setEndProcess(System.currentTimeMillis());
			this.logger.setNumSolutions(solutions.length);
			nonGhostSol = this.removeGhostSolution(solutions);
			this.logger
					.setGhostedSolutions(solutions.length - nonGhostSol.length);

			if (loggerMode == Logger.WITHOUT_RULES) {
				this.printSolution(nonGhostSol,
						this.testName + " (solutions without rules)");
				this.printSolution(solutions,
						this.testName + " (all solutions without rules)");
			}
			else {
				this.printSolution(nonGhostSol,
						this.testName + " (solutions with rules)");
				this.printSolution(solutions,
						this.testName + " (all solutions with rules)");
			}
		}
		catch (final InvocationTargetException e) {
			e.printStackTrace();
		}
		catch (final IllegalAccessException e) {
			e.printStackTrace();
		}
		catch (final NoSuchMethodException e) {
			e.printStackTrace();
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		catch (final Throwable t) {
			t.printStackTrace();
		}

		return nonGhostSol;
	}

	private Occurrence[] computeSolutionsWithoutRules() {
		this.createModel();
		return this.computeSolutions(Logger.WITHOUT_RULES,
				new Class[] { List.class }, new Object[] { this.allEntities });
	}

	private Occurrence[] computeSolutionsWithRules() {
		this.createModel();
		return this.computeSolutions(Logger.WITH_RULES,
				new Class[] { List.class, ReducedDomainBuilder.class },
				new Object[] { this.allEntities, this.rdb });
	}

	private void createModel() {
		String[] computePaths = null;

		if (this.packageNames != null) {
			computePaths = new String[this.packageNames.length];
			for (int i = 0; i < this.packageNames.length; i++)
				computePaths[i] = this.path
						+ this.packageNames[i].replace('.', '/');
		}
		else {
			computePaths = new String[1];
			computePaths[0] = this.path;
		}

		this.codeLevelModel = Factory.getInstance()
				.createCodeLevelModel(this.testName);

		try {
			this.codeLevelModel
					.create(new CompleteClassFileCreator(computePaths, true));
		}
		catch (final CreationException e) {
			e.printStackTrace();
		}

		if (this.packageNames != null) {
			// Yann 2005/10/07: Packages!
			// A model may now contrain entities and packages.
			// Yann 2005/10/12: Iterator!
			// I have now an iterator able to iterate over a
			// specified type of constituent of a list.
			final Iterator entities = this.codeLevelModel
					.getIteratorOnConstituents(IFirstClassEntity.class);
			while (entities.hasNext()) {
				final IFirstClassEntity firstClassEntity = (IFirstClassEntity) entities
						.next();
				final String entityName = firstClassEntity.getDisplayName();
				final String packageName = entityName.substring(0,
						entityName.lastIndexOf('.'));
				boolean toBeRemoved = true;
				for (int i = 0; i < this.packageNames.length
						&& toBeRemoved; i++) {
					if (packageName.equals(this.packageNames[i])) {
						toBeRemoved = false;
					}
				}
				if (toBeRemoved) {
					this.codeLevelModel
							.removeConstituentFromID(entityName.toCharArray());
				}
			}
		}
		this.rdb = new ReducedDomainBuilder(this.codeLevelModel);
		this.allEntities = Manager.build(this.codeLevelModel);
	}

	private Occurrence[] excluded(final Occurrence[] included,
			final Occurrence[] includer) {
		final ArrayList excluded = new ArrayList();
		for (int i = 0; i < includer.length; i++) {
			if (!OccurrenceComparator.getInstance().belongsTo(includer[i],
					included)) {
				excluded.add(includer[i]);
			}
		}
		final Occurrence[] tmp = new Occurrence[excluded.size()];
		for (int i = 0; i < excluded.size(); i++) {
			tmp[i] = (Occurrence) excluded.get(i);
		}
		return tmp;
	}

	public abstract Class getMotif(final int mode);

	public String getMotifName() {
		return this.motifName;
	}

	public String getProgramName() {
		return this.testName;
	}

	private int numIncluded(final Occurrence[] included,
			final Occurrence[] includer) {
		int cpt_included = 0;
		for (int i = 0; i < included.length; i++) {
			if (OccurrenceComparator.getInstance().belongsTo(included[i],
					includer)) {
				cpt_included++;
			}
		}
		return cpt_included;
	}

	private void printSolution(final Occurrence[] sol, final String testName) {
		final PrintWriter w = new PrintWriter(
				ProxyDisk.getInstance().fileTempOutput(testName + ".txt"));
		Occurrence.print(sol, w);
		w.close();
	}

	private Occurrence[] removeGhostSolution(final Occurrence[] solutions) {
		final ArrayList sol = new ArrayList();
		for (int i = 0; i < solutions.length; i++) {
			boolean ghosted = false;
			final Iterator iter = solutions[i].getComponents().iterator();
			while (iter.hasNext()) {
				// TEST IF INSTANCE OF GHOST
				OccurrenceComponent sc = (OccurrenceComponent) iter.next();
				if (this.codeLevelModel.getConstituentFromName(
						sc.getValue()) instanceof IGhost) {
					ghosted = true;
				}
			}
			if (!ghosted) {
				sol.add(solutions[i]);
			}
		}
		Occurrence[] solTmp = new Occurrence[sol.size()];
		for (int i = 0; i < sol.size(); i++) {
			solTmp[i] = (Occurrence) sol.get(i);
		}

		return solTmp;
	}

	private Occurrence[] solve(final Problem problem) throws IOException {
		// Solve
		final StringWriter writer = new StringWriter();
		problem.setWriter(new PrintWriter(writer));
		problem.automaticSolve(true);
		// problem.combinatorialAutomaticSolve(true);

		final Properties properties = new Properties();
		properties.load(new ReaderInputStream(
				new StringReader(writer.getBuffer().toString())));

		final OccurrenceBuilder solutionBuilder = OccurrenceBuilder
				.getInstance();

		return solutionBuilder.getCanonicalOccurrences(properties);
	}
}
