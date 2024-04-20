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
package ptidej.solver.java;

import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import choco.AbstractConstraint;
import choco.Var;
import choco.integer.IntVar;
import choco.palm.PalmProblem;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.search.PalmContradiction;
import ptidej.solver.java.approximation.DefaultNoApproximations;
import ptidej.solver.java.approximation.IApproximations;
import ptidej.solver.java.combination.Combination;
import ptidej.solver.java.constraint.BinaryConstraint;
import ptidej.solver.java.constraint.repository.EqualConstraint;
import ptidej.solver.java.constraint.repository.GreaterOrEqualConstraint;
import ptidej.solver.java.constraint.repository.LessOrEqualConstraint;
import ptidej.solver.java.constraint.repository.NotEqualConstraint;
import ptidej.solver.java.solver.AutomaticSolver;
import ptidej.solver.java.solver.CombinatorialAutomaticSolver;
import ptidej.solver.java.solver.InteractiveSolver;
import ptidej.solver.java.solver.SimpleAutomaticSolver;
import ptidej.solver.java.solver.SimpleInteractiveSolver;
import util.io.ProxyConsole;
import util.io.ProxyDisk;
import util.multilingual.MultilingualManager;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public class Problem extends PalmProblem {
	private static boolean DisplayReleaseData = true;
	// This field is static because of dynamic problems,
	// they share the same list.
	private static List AllEntities;
	private int globalWeight;
	private String name;
	private PrintWriter out;

	public Problem(final int maxWeight, final String name) {
		// Yann 2006/08/04: List of all entities.
		// I make sure every problem built dynamically
		// has the same and unique list of all the 
		// entities in the program, by passing this
		// list around.
		//	this(maxWeight, name, new ArrayList());
		this(maxWeight, name, Problem.AllEntities);
	}

	public Problem(final int maxWeight, final String name,
			final List allEntities) {

		// Yann 2007/11/21: Cache!
		BinaryConstraint.resetEntityList();

		this.setMaxRelaxLevel(maxWeight);
		this.name = name;
		Problem.AllEntities = allEntities;
		if (Problem.DisplayReleaseData) {
			Problem.DisplayReleaseData = false;
			ProxyConsole.getInstance().normalOutput().println(
					MultilingualManager.getString("PTIDEJ", Problem.class));
		}
	}

	public void addVar(final IntVar v) {
		this.intVars.add(v);
	}

	// Automatic solver.
	public void automaticSolve(final boolean all) {
		try {
			//	System.out.println("Computing solution with automatic solver");
			this.setPtidejSolver(new AutomaticSolver(this));
			this.setGlobalWeight(this.computeGlobalWeight());
			this.solve(all);
			this.getWriter().close();
			//	System.out.println("Terminated");
		}
		catch (final Exception e) {
			this.getWriter().close();
			//	System.out.println("Terminated whith " + e + " exception!");
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	public void combinatorialAutomaticSolve(final boolean all) {
		final Problem currentProblem = this;
		final int maxRelaxLvl = currentProblem.getMaxRelaxLevel();
		final String pbName = currentProblem.getName();
		final int gw = currentProblem.computeGlobalWeight();

		int numberOfRemovableConstraints = 0;
		int solutionNumber = 0;
		final List removableConstraints = new ArrayList();
		final List nonRemovableConstraints = new ArrayList();
		final List constraints = currentProblem.getConstraints();

		// I sort between removable and no-removable constraints
		Iterator iterator = constraints.iterator();
		while (iterator.hasNext()) {
			final AbstractConstraint ct = (AbstractConstraint) iterator.next();
			final PalmConstraintPlugin plug = (PalmConstraintPlugin) ct
					.getPlugIn();
			final int weight = plug.getWeight();
			if (weight < maxRelaxLvl) {
				numberOfRemovableConstraints++;
				removableConstraints.add(ct);
			}
			else {
				nonRemovableConstraints.add(ct);
			}
		}

		// I compute all the solutions with the constraints 
		// removed according to their permutations.
		for (int p = 0; p <= numberOfRemovableConstraints; p++) {
			try {
				final Vector setsOfRemovableConstraints = Combination
						.C(numberOfRemovableConstraints, p);
				Problem dynamicProblem = currentProblem;
				String message = "";
				String xCommand = "";
				int weight = 0;

				iterator = setsOfRemovableConstraints.iterator();
				while (iterator.hasNext()) {
					final Vector setOfRemovableConstraints = (Vector) iterator
							.next();

					//	System.out.println(
					//		"----\n"
					//			+ "Soft constraints: "
					//			+ (p + 1)
					//			+ '/'
					//			+ (numberOfRemovableConstraints + 1)
					//			+ ", combination: "
					//			+ currentNumberOfDynamicProblem
					//			+ '/'
					//			+ setOfRemovableConstraints.size());

					// I create a new problem (dynamically!).
					// I override the maximum level of relaxation:
					// currentProblem.maxRelaxLvl.
					dynamicProblem = new Problem(maxRelaxLvl, pbName);
					dynamicProblem.setGlobalWeight(gw);
					dynamicProblem.setPtidejSolver(
							new CombinatorialAutomaticSolver(dynamicProblem));

					// Yann 2004/09/10: Writer.
					// We want all the solutions to be stored in a unique
					// place, thus we use the same writer for all problems.
					//	dynamicProblem.setWriter(
					//		"CombinatorialResult"
					//			+ (p)
					//			+ "nb"
					//			+ currentNumberOfDynamicProblem);
					dynamicProblem.setWriter(this.getWriter());
					final Branching nextBranching = dynamicProblem
							.getPtidejSolver().getNextBranching();
					nextBranching.setSolutionNumber(solutionNumber);

					if (setOfRemovableConstraints.size() == 0) {
						nextBranching.setMessage(
								"# Solution with all constraints\n");
						nextBranching.setWeight(100);
						nextBranching.setXCommand(
								"System.out.println(\"No transformation required.\");");
					}
					else {
						message = "\n# Solution without constraint";
						message = message
								+ (setOfRemovableConstraints.size() > 1 ? "s:\n"
										: ":\n");
						weight = 0;
						xCommand = "";

						for (int i = 0; i < numberOfRemovableConstraints; ++i) {

							final Integer index = Integer.valueOf(i);
							if (setOfRemovableConstraints.contains(index)) {
								final Constraint constraint = (Constraint) removableConstraints
										.get(i);
								final String symbol = constraint.getSymbol();
								final String name = constraint.getName();

								message = message + "#\t" + symbol + "\t: "
										+ name + "\n";
								weight = weight
										+ ((PalmConstraintPlugin) constraint
												.getPlugIn()).getWeight();
								xCommand = xCommand + ((Constraint) constraint)
										.getXCommand();
							}
						}
						nextBranching.setMessage("\n" + message);
						nextBranching.setWeight(weight);
						nextBranching.setXCommand(xCommand);
					}

					// I attach new variables to the dynamic problem.
					final IntVar[] vars = currentProblem.getVars();
					for (int i = 0; i < vars.length; i++) {
						//	final Variable v =
						//		new Variable(
						//			dynamicProblem,
						//			vars[i].toString(),
						//			((Variable) vars[i]).isEnumerated(),
						//			((Variable) vars[i]).getEntities());
						final Variable v = new Variable(dynamicProblem,
								vars[i].toString(),
								((Variable) vars[i]).isEnumerated(),
								this.getAllEntities());
						dynamicProblem.addVar(v);
					}

					// I post new contraints to the dynamic problem.
					for (int i = 0; i < nonRemovableConstraints.size(); ++i) {

						dynamicProblem.dynamicPost(
								(AbstractConstraint) nonRemovableConstraints
										.get(i));
					}
					for (int i = 0; i < numberOfRemovableConstraints; ++i) {
						final Integer indexCt = Integer.valueOf(i);
						if (!setOfRemovableConstraints.contains(indexCt)) {
							dynamicProblem.dynamicPost(
									(AbstractConstraint) removableConstraints
											.get(i));
						}
					}

					// I solve the dynamic problem.
					dynamicProblem.solve(all);
					//					System.out.println(
					//						"Number of solutions found: "
					//							+ (((Solver) (dynamicProblem.getPtidejSolver()))
					//								.getNextBranching())
					//								.getSolutionNumber());
					solutionNumber = nextBranching.getSolutionNumber();
				}

			}
			catch (final Exception e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
	}

	public int computeGlobalWeight() {
		final List constraints = this.getConstraints();
		final Iterator itr = constraints.iterator();
		int gw = 0;
		while (itr.hasNext()) {
			final AbstractConstraint ct = (AbstractConstraint) itr.next();
			final PalmConstraintPlugin plug = (PalmConstraintPlugin) ct
					.getPlugIn();
			int weight = plug.getWeight();
			if (weight < this.getMaxRelaxLevel()) {
				// Yann 2007/02/28: Weight
				// I do not forget to multiply the weight
				// of a constraint by the number of its
				// possible relaxations.
				if (ct instanceof Constraint) {
					gw = gw + weight * ((Constraint) ct).getApproximations()
							.getApproximations().length;
				}
				else {
					gw = gw + weight;
				}
			}
		}

		// Yann 2004/09/10: Weight.
		// If there is no removable constraint in the problem,
		// then the weight of any solution is 100.
		//	if (nbOfRemovableConstraints == 0) {
		//		gw = 100;
		//	}
		//	else {
		//		gw = gw / nbOfRemovableConstraints;
		//	}

		return gw;
	}

	// Combinatorial solver.
	// TODO : This implementation is not recording the actual constraint.
	public void dynamicPost(AbstractConstraint constraint) {
		if (constraint != null) {
			final ArrayList args = new ArrayList();

			final int numberOfVar = constraint.getNbVars();
			if (numberOfVar == 1) {
				// UNARY CONSTRAINT
				// args.add(Integer.valueOf(constraint.getConstant()));
				// TO CHECK IF THE ONLY UNARY CONTSTAINT ARE
				// GreaterOrEqualConstraint OR LessOrEqualConstraint
				if (constraint instanceof GreaterOrEqualConstraint) {
					args.add(((GreaterOrEqualConstraint) constraint).getName());
					args.add(((GreaterOrEqualConstraint) constraint)
							.getXCommand());
					args.add(this.findDynamicVariable(
							(Variable) constraint.getVar(0)));
					args.add(Integer
							.valueOf(((GreaterOrEqualConstraint) constraint)
									.getConstant()));
				}
				else if (constraint instanceof LessOrEqualConstraint) {
					args.add(((LessOrEqualConstraint) constraint).getName());
					args.add(
							((LessOrEqualConstraint) constraint).getXCommand());
					args.add(this.findDynamicVariable(
							(Variable) constraint.getVar(0)));
					args.add(
							Integer.valueOf(((LessOrEqualConstraint) constraint)
									.getConstant()));
				}
			}
			else {
				// BINARY (AND MORE) CONSTRAINT 
				args.add(((Constraint) constraint).getName());
				args.add(((Constraint) constraint).getXCommand());
				for (int i = 0; i < numberOfVar; ++i) {
					args.add(this.findDynamicVariable(
							(Variable) constraint.getVar(i)));
				}
			}

			args.add(Integer
					.valueOf(((PalmConstraintPlugin) constraint.getPlugIn())
							.getWeight()));

			// Yann 24/04/19: Approximations
			// I don't forget to include the approximation
			// as last parameter type and argument.
			if (constraint instanceof BinaryConstraint) {
				args.add(((BinaryConstraint) constraint).getApproximations());
			}
			else if (constraint instanceof EqualConstraint
					|| constraint instanceof NotEqualConstraint) {

				args.add(DefaultNoApproximations.getDefaultApproximations());
			}
			else {
				// Nothing to do
			}

			final Class[] parameterTypes = new Class[args.size()];
			final int length = parameterTypes.length;
			for (int i = 0; i < length; ++i) {
				parameterTypes[i] = args.get(i).getClass();
			}
			parameterTypes[length - 2] = int.class;
			parameterTypes[length - 1] = IApproximations.class;

			Constructor constr = null;
			try {
				if (numberOfVar == 1) {
					if (constraint instanceof GreaterOrEqualConstraint) {
						constr = (((GreaterOrEqualConstraint) constraint)
								.getClass()).getConstructor(parameterTypes);
					}
					else if (constraint instanceof LessOrEqualConstraint) {
						constr = (((LessOrEqualConstraint) constraint)
								.getClass()).getConstructor(parameterTypes);
					}
				}
				else {
					constr = (((Constraint) constraint).getClass())
							.getConstructor(parameterTypes);
				}

				final AbstractConstraint c = (AbstractConstraint) constr
						.newInstance(args.toArray());
				this.post(c);
			}
			catch (final SecurityException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final NoSuchMethodException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final IllegalArgumentException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final InstantiationException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final IllegalAccessException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
			catch (final InvocationTargetException e) {
				e.printStackTrace(ProxyConsole.getInstance().errorOutput());
			}
		}
		//	else {
		//		System.out.println(
		//			"CombinatorialAutomaticSolver cannot add " + constraint);
		//	}
	}

	public Variable findDynamicVariable(final Variable var) {
		final IntVar[] vars = this.getVars();
		for (int i = 0; i < vars.length; ++i) {
			// The toSting() method returns the the field name of a variable.
			if (((Variable) vars[i]).toString().equals(var.toString())) {
				return (Variable) vars[i];
			}
		}

		//	System.out.println(
		//		"findDynamicVariable() in CombinatorialAutomaticSolver failed!");
		return null;
	}

	public List getAllEntities() {
		return Problem.AllEntities;
	}

	public List getConstraints() {
		return this.constraints;
	}

	public int getGlobalWeight() {
		return this.globalWeight;
	}

	public Solver getPtidejSolver() {
		return (Solver) this.palmSolver;
	}

	/**
	 * Gets the minimal domain variable of the problem. 
	 * This is used for choosing a variable to instantiate
	 * by the solver.
	 * @return The minimal domain variable.
	 */
	public Var getMinDomVar() {
		int minsize = Integer.MAX_VALUE;
		Var var = null;
		for (int i = 0; i < this.intVars.size(); i++) {
			Variable v = (Variable) this.intVars.get(i);
			if (v.isEnumerated()) {
				int size = v.getDomainSize();
				if (size > 1) {
					if (size < minsize) {
						minsize = size;
						var = v;
					}
				}
			}
		}
		return var;
	}

	public String getName() {
		return this.name;
	}

	public PrintWriter getWriter() {
		// this.out = new PrintWriter(System.out);
		return this.out;
	}

	// Interactive solver.
	public void interactiveSolve(boolean all) {
		try {
			this.setPtidejSolver(new InteractiveSolver(this));
			this.solve(all);
			//	System.out.println("Terminated !");
		}
		catch (final PalmContradiction e) {
			//	System.out.println("No more solution found!");
		}
		catch (final Exception e) {
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	public void setGlobalWeight(final int gw) {
		this.globalWeight = gw;
	}

	public void setPtidejSolver(final Solver solver) {
		this.setPalmSolver(solver);
	}

	//	public void setReader(final String fileNameReader) {
	//		try {
	//			this.reader = new FileReader("rsc/" + fileNameReader);
	//		}
	//		catch (final FileNotFoundException e) {
	//			e.printStackTrace(Output.getInstance().errorOutput());
	//		}
	//	}
	public void setWriter(final String fileNameWriter) {
		this.out = new PrintWriter(ProxyDisk.getInstance()
				.fileTempOutput("result/" + fileNameWriter + ".txt"));
	}

	public void setWriter(final PrintWriter aPrintWriter) {
		this.out = aPrintWriter;
	}

	//	Simple automatic solver.
	public void simpleAutomaticSolve(final boolean all) {
		try {
			//	System.out.println(
			//		"Computing solution with simple automatic solver");
			this.setWriter("simpleAutomaticResult");
			this.setPtidejSolver(new SimpleAutomaticSolver(this));
			this.setGlobalWeight(this.computeGlobalWeight());
			this.solve(all);
			this.getWriter().close();
			//	System.out.println("Terminated");
		}
		catch (final PalmContradiction e) {
			this.getWriter().close();
			//	System.out.println("No more solution found!");
		}
		catch (final Exception e) {
			this.getWriter().close();
			//	System.out.println("Terminated whith " + e + " exception!");
		}
	}

	// Simple interactive solver.
	public void simpleInteractiveSolve(final boolean all) {
		try {
			this.setPtidejSolver(new SimpleInteractiveSolver(this));
			this.solve(all);
			//	System.out.println("Terminated !");
		}
		catch (final PalmContradiction e) {
			//	System.out.println("No more solution found!");
		}
		catch (final Exception e) {
			//	System.out.println("Terminated whith " + e + " exception!");
			e.printStackTrace(ProxyConsole.getInstance().errorOutput());
		}
	}

	public String toString() {
		final StringBuffer buffer = new StringBuffer();

		buffer.append("Variables:\n");
		final IntVar[] vars = this.getVars();
		for (int i = 0; i < vars.length; i++) {
			final Variable var = (Variable) vars[i];
			buffer.append('\t');
			buffer.append(var.toString());
			buffer.append('\n');
		}

		buffer.append("Constraints:\n");
		final Iterator constraints = this.getConstraints().iterator();
		while (constraints.hasNext()) {
			final Constraint constraint = (Constraint) constraints.next();
			buffer.append('\t');
			buffer.append(constraint.getName());
			buffer.append('(');
			buffer.append(constraint.getVar(0));
			buffer.append(", ");
			buffer.append(constraint.getVar(1));
			buffer.append(")\n");
		}

		return buffer.toString();
	}
}
