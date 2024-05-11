//     ___  ___         PaLM library for Choco system
//    /__ \/ __\        (c) 2001 - 2004 -- Narendra Jussien
//   ____\ |/_____
//  /_____\/_____ \     Explanation based constraint programming tool
// |/   (_)(_)   \|
//       \ /            Version 0.1
//       \ /            January 2004
//       \ /
// ______\_/_______     Contibutors: Franeois Laburthe, Hadrien Cambazard, Guillaume Rochart...

package choco.palm;

import java.util.Iterator;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import choco.Constraint;
import choco.ContradictionException;
import choco.Problem;
import choco.Var;
import choco.integer.IntExp;
import choco.integer.IntVar;
import choco.integer.constraints.IntLinComb;
import choco.integer.var.IntDomainVar;
import choco.integer.var.IntTerm;
import choco.palm.explain.Explanation;
import choco.palm.explain.GenericExplanation;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.PalmIntVar;
import choco.palm.integer.constraints.PalmEqualXC;
import choco.palm.integer.constraints.PalmEqualXYC;
import choco.palm.integer.constraints.PalmGreaterOrEqualXC;
import choco.palm.integer.constraints.PalmGreaterOrEqualXYC;
import choco.palm.integer.constraints.PalmIntLinComb;
import choco.palm.integer.constraints.PalmLessOrEqualXC;
import choco.palm.integer.constraints.PalmNotEqualXC;
import choco.palm.integer.constraints.PalmNotEqualXYC;
import choco.palm.prop.PalmEngine;
import choco.palm.search.PalmBranchAndBound;
import choco.palm.search.PalmSolver;
import choco.prop.ChocEngine;
import choco.prop.ConstraintEvent;

/**
 * Extension d'un probl?me Choco prenant en compte les explications.
 */

public class PalmProblem extends Problem {
	/**
	 * Constant value for relaxation number statistics.
	 */

	public final static int RLX = 0;

	/**
	 * Constant value for extension number statistics.
	 */

	public final static int EXT = 1;

	/**
	 * Constant value for CPU statistics.
	 */

	public final static int CPU = 2;

	/**
	 * Displays release information (date, verions, ...).
	 */

	public final static void ReleaseDisplay() {
		System.out
			.println("** JPaLM : Constraint Programming with Explanations");
		System.out
			.println("** JPaLM v0.1 (September, 2003), Copyright (c) 2000-2003 N. Jussien");
		PalmProblem.displayRelease = false;
	}

	/**
	 * Maximum relaxation level acceptable without user interaction.
	 */

	public int maxRelaxLevel = 0;

	/**
	 * Associated Palm Solver.
	 */

	protected PalmSolver palmSolver;

	/**
	 * The logger used in Palm (The name is <code>choco.palm</code>).
	 */

	public Logger logger;

	/**
	 * States if the release information should be displayed during the next problem instantiation.
	 */

	public static boolean displayRelease = true;

	/**
	 * Creates a Palm Problem with the specified environment.
	 */

	public PalmProblem() {
		super();
		GenericExplanation.reinitTimestamp();
		this.propagationEngine = new PalmEngine(this);
		this.palmSolver = new PalmSolver(this);
		this.logger = Logger.getLogger("choco.palm");
		this.logger.setLevel(Level.WARNING);
		this.logger.setUseParentHandlers(false);
		final ConsoleHandler handler = new ConsoleHandler();
		handler.setLevel(Level.FINE);
		this.logger.addHandler(handler);
		if (PalmProblem.displayRelease) {
			PalmProblem.ReleaseDisplay();
		}
	}

	public Constraint eq(final IntExp x, final int c) {
		if (x instanceof IntVar) {
			return new PalmEqualXC((IntVar) x, c);
		}
		else if (x instanceof IntTerm) {
			final IntTerm t = (IntTerm) x;
			if (t.getSize() == 2
					&& t.getCoefficient(0) + t.getCoefficient(1) == 0) {
				return new PalmEqualXYC(
					t.getVariable(0),
					t.getVariable(1),
					(c - t.getConstant()) / t.getCoefficient(0));
			}
			else {
				return new PalmIntLinComb(
					((IntTerm) x).getVariables(),
					((IntTerm) x).getCoefficients(),
					-c,
					IntLinComb.EQ);
			}
		}
		else {
			throw new Error("IntExp not a good exp");
		}
	}

	public Constraint eq(final IntExp x, final IntExp y) {
		if (x instanceof IntVar && y instanceof IntVar) {
			return new PalmEqualXYC((IntVar) x, (IntVar) y, 0);
		}
		else if ((x instanceof IntTerm || x instanceof IntVar)
				&& (y instanceof IntTerm || y instanceof IntVar)) {
			return this.eq(this.minus(x, y), 0);
		}
		else {
			throw new Error("IntExp not a good exp");
		}
	}

	public Constraint geq(final int c, final IntExp x) {
		if (x instanceof IntVar) {
			return new PalmLessOrEqualXC((IntDomainVar) x, c);
		}
		else if (x instanceof IntTerm) {
			final IntTerm t = (IntTerm) x;
			if (t.getSize() == 2
					&& t.getCoefficient(0) + t.getCoefficient(1) == 0) {
				return new PalmGreaterOrEqualXYC(
					t.getVariable(1),
					t.getVariable(0),
					(t.getConstant() - c) / t.getCoefficient(0));
			}
			else {
				// TODO : mettre des moins partout
				throw new Error("IntExp not a good exp");
			}
		}
		else {
			throw new Error("IntExp not a good exp");
		}
	}

	public Constraint geq(final IntExp x, final int c) {
		if (x instanceof IntVar) {
			return new PalmGreaterOrEqualXC((IntVar) x, c);
		}
		else if (x instanceof IntTerm) {
			final IntTerm t = (IntTerm) x;
			if (t.getSize() == 2
					&& t.getCoefficient(0) + t.getCoefficient(1) == 0) {
				return new PalmGreaterOrEqualXYC(
					t.getVariable(0),
					t.getVariable(1),
					(c - t.getConstant()) / t.getCoefficient(0));
			}
			else {
				return new PalmIntLinComb(
					((IntTerm) x).getVariables(),
					((IntTerm) x).getCoefficients(),
					-c,
					IntLinComb.GEQ);
			}
		}
		else {
			throw new Error("IntExp not a good exp");
		}
	}

	public Constraint geq(final IntExp x, final IntExp y) {
		if (x instanceof IntVar && y instanceof IntVar) {
			return new PalmGreaterOrEqualXYC((IntVar) x, (IntVar) y, 0);
		}
		else if ((x instanceof IntTerm || x instanceof IntVar)
				&& (y instanceof IntTerm || y instanceof IntVar)) {
			return this.geq(this.minus(x, y), 0);
		}
		else {
			throw new Error("IntExp not a good exp");
		}
	}

	/**
	 * @param nb Constraint number (number affected when posting and stores
	 * in the variable plugin
	 * @return Returns the constraint
	 */

	public Constraint getConstraintNb(final int nb) {
		return (Constraint) this.constraints.get(nb);
	}

	/**
	 * Gets the global statistics (that is sum of over all solutions statistics).
	 * @param statistic Should be <code>RLX</code>, <code>EXT</code> or <code>CPU</code> constant.
	 * @return Returns the global statistics associated with the specified constant.
	 */

	public int getGlobalStatistics(final int statistic) {
		return this.palmSolver.getGlobalStatistics(statistic);
	}

	public int getMaxRelaxLevel() {
		return this.maxRelaxLevel;
	}

	/**
	 * Gets the minimal domain variable of the problem. This is used for choosing a variable to instantiate
	 * by the solver.
	 * @return The minimal domain variable.
	 */

	public Var getMinDomVar() {
		int minsize = Integer.MAX_VALUE;
		Var var = null;
		for (int i = 0; i < this.intVars.size(); i++) {
			final PalmIntVar v = (PalmIntVar) this.intVars.get(i);
			final int size = v.getDomainSize();
			if (size > 1) {
				if (size < minsize) {
					minsize = size;
					var = v;
				}
			}
		}
		return var;
	}

	/**
	 * Returns the solver used for solving the problem.
	 */

	public PalmSolver getPalmSolver() {
		return this.palmSolver;
	}

	/**
	 * Gets the runtime statistics (that is statistics for current solution search).
	 * @param statistic Should be <code>RLX</code>, <code>EXT</code> or <code>CPU</code> constant.
	 * @return Returns the global statistics associated with the specified constant.
	 */

	public int getRuntimeStatistics(final int statistic) {
		return this.palmSolver.getRuntimeStatistics(statistic);
	}

	// TODO : Ca devrait ?tre dans Choco !!
	public IntVar[] getVars() {
		final IntVar[] array = new IntVar[0];
		return (IntVar[]) this.intVars.toArray(array);
	}

	/**
	 * Creates a new Bound Integer Variable without any bucket (only bounds are propagated).
	 * @param name Name of the variable.
	 * @param inf Inf bound.
	 * @param sup Sup bound.
	 * @return The created variable.
	 */

	public IntVar makeBoundIntVar(
		final String name,
		final int inf,
		final int sup) {
		return this.makeBoundIntVar(name, inf, sup, true);
	}

	/**
	 * Creates a new Bound Integer Variable without any bucket (only bounds are propagated).
	 * @param name Name of the variable.
	 * @param inf Inf bound.
	 * @param sup Sup bound.
	 * @param toAdd Specifies if the variable should be added to the variables list of the problem (should be true in almost all cases).
	 * @return The created variable.
	 */

	public IntVar makeBoundIntVar(
		final String name,
		final int inf,
		final int sup,
		final boolean toAdd) {
		final IntVar var = new PalmIntVar(this, name, IntVar.BOUNDS, inf, sup);
		if (toAdd) {
			this.intVars.add(var);
		}
		return var;
	}

	public IntVar makeEnumIntVar(final String name, final int inf, final int sup) {
		final IntVar var = new PalmIntVar(this, name, IntVar.LIST, inf, sup);
		this.intVars.add(var);
		return var;
	}

	/**
	 * Factory to create explanation.
	 * It offers the possibility to make another kind of explanation, only by extending PalmProblem 
	 * @return
	 */
	public Explanation makeExplanation() {
		return new GenericExplanation(this);
	}

	/**
	 * Makes a new Integer Variable with a bucket for managing holes in the domain.
	 * @param name Name of the variable (for display).
	 * @param inf Inf bound.
	 * @param sup Sup bound.
	 * @return The created variable.
	 */

	public IntVar makeIntVar(final String name, final int inf, final int sup) {
		final IntVar var = new PalmIntVar(this, name, IntVar.LIST, inf, sup);
		this.intVars.add(var);
		return var;
	}

	/**
	 * Maximize an objective variable with a PalmBranchAndBound
	 */
	public Boolean maximize(final IntVar objective) {
		this.setPalmSolver(new PalmBranchAndBound(this, objective, true));
		this.palmSolver.run();
		return this.isFeasible();
	}

	/**
	 * Minimize an objective variable with a PalmBranchAndBound
	 */
	public Boolean minimize(final IntVar objective) {
		this.setPalmSolver(new PalmBranchAndBound(this, objective, false));
		this.palmSolver.run();
		return this.isFeasible();
	}

	public Constraint neq(final IntExp x, final int c) {
		if (x instanceof IntVar) {
			return new PalmNotEqualXC((IntVar) x, c);
		}
		else if (x instanceof IntTerm) {
			final IntTerm t = (IntTerm) x;
			if (t.getSize() == 2
					&& t.getCoefficient(0) + t.getCoefficient(1) == 0) {
				return new PalmNotEqualXYC(
					t.getVariable(0),
					t.getVariable(1),
					(c - t.getConstant()) / t.getCoefficient(0));
			}
			else {
				return new PalmIntLinComb(
					((IntTerm) x).getVariables(),
					((IntTerm) x).getCoefficients(),
					-c,
					IntLinComb.NEQ);
			}
		}
		else {
			throw new Error("IntExp not a good exp");
		}
	}

	public Constraint neq(final IntExp x, final IntExp y) {
		if (x instanceof IntVar && y instanceof IntVar) {
			return new PalmNotEqualXYC((IntVar) x, (IntVar) y, 0);
		}
		else if ((x instanceof IntTerm || x instanceof IntVar)
				&& (y instanceof IntTerm || y instanceof IntVar)) {
			return this.neq(this.minus(x, y), 0);
		}
		else {
			throw new Error("IntExp not a good exp");
		}
	}

	/**
	 * Posts a constraints in the problem. If it has ever been posted (but deactivated), it is
	 * only reactivated and repropagated.
	 * @param constraint The constraint to post.
	 */

	public void post(final Constraint constraint) {
		if (!((PalmConstraintPlugin) constraint.getPlugIn()).isEverConnected()) {
			this.constraints.add(constraint);
			((PalmConstraintPlugin) constraint.getPlugIn())
				.setConstraintIdx(this.constraints.size() - 1);
			constraint.addListener();
			final ConstraintEvent event =
				(ConstraintEvent) constraint.getEvent();
			((ChocEngine) this.propagationEngine).registerEvent(event);
			((ChocEngine) this.propagationEngine).postConstAwake(
				constraint,
				true);
		}
		else {
			this.logger.fine("The constraint " + constraint
					+ " is reactivated.");
			((ChocEngine) this.propagationEngine).postConstAwake(
				constraint,
				true);
			constraint.activateListener();
		}
	}

	/**
	 * Posts an indirect constraint with an explain.
	 * @param constraint The constraint to post.
	 * @param expl The set of constraint this posted constraint depends on.
	 */

	public void post(final Constraint constraint, final Explanation expl) {
		this.post(constraint);
		((PalmConstraintPlugin) constraint.getPlugIn()).setIndirect(expl);
		((PalmConstraintPlugin) constraint.getPlugIn())
			.setIndirectDependance(expl);

	}

	/**
	 * Posts a constraint with the specified weight.
	 * @param constraint The constraint to post.
	 * @param w The weight associated to the constraint.
	 */

	public void post(final Constraint constraint, final int w) {
		this.post(constraint);
		((PalmConstraintPlugin) constraint.getPlugIn()).setWeight(w);
	}

	/**
	 * Displays all the runtime statistics.
	 */

	public void printRuntimeSatistics() {
		for (int i = 0; i < 3; i++) {
			this.printRuntimeSatistics(i);
		}
	}

	/**
	 * Pretty print of a precise runtime statistic.
	 * @param i The statistic to display.
	 */

	private void printRuntimeSatistics(final int i) {
		switch (i) {
			case 0 :
				System.out.println(this.palmSolver.getRuntimeStatistics(i)
						+ " repairs ");
				return;
			case 1 :
				System.out.println(this.palmSolver.getRuntimeStatistics(i)
						+ " extensions ");
				return;
			case 2 :
				System.out.println(this.palmSolver.getRuntimeStatistics(i)
						+ " ms ");
				return;
		}
	}

	/**
	 * Posts and propagates several decision constraints (that is decisions taken by the solver).
	 * @param constraints The constraints to post.
	 * @throws ContradictionException
	 */

	public void propagateAllDecisionsConstraints(final List constraints)
			throws ContradictionException {
		this.palmSolver.incRuntimeStatistic(PalmProblem.EXT, 1);
		for (final Iterator iterator = constraints.iterator(); iterator
			.hasNext();) {
			final Constraint constraint = (Constraint) iterator.next();
			this.post(constraint, 0); // Avant la mise a jour de l'etat sinon la contrainte n'existe pas encore !!
			this.palmSolver.getState().addDecision(constraint);
			this.propagate();
		}
	}

	/**
	 * Removes properly a constraint from the problem: the constraint is deactivated and all the depending
	 * filtering decisions are undone.
	 * @param constraint The constraint to remove.
	 */

	public void remove(final Constraint constraint) {
		((PalmEngine) this.propagationEngine).remove(constraint);
	}

	/**
	 * Tries to repair the problem after a PalmContradiction thanks to removing a responsible
	 * constraint (that is a constraint in the explain of the contradiction).
	 * @throws ContradictionException
	 */

	public void repair() throws ContradictionException {
		this.palmSolver.repair();
	}

	/**
	 * Searches one solution of the problem.
	 * @return True if a solution was found.
	 */

	public Boolean searchOneSolution() {
		this.palmSolver.run();
		this.palmSolver.storeSolution();
		return this.isFeasible();
	}

	public void setMaxRelaxLevel(final int maxRelaxLevel) {
		this.maxRelaxLevel = maxRelaxLevel;
	}

	/**
	 * Sets the solver to be used for solving the problem.
	 * @param palmSolver
	 */

	public void setPalmSolver(final PalmSolver palmSolver) {
		this.palmSolver = palmSolver;
	}

	/**
	 * Tries to search the problem by finding one solution or all solutions.
	 * @param allSolutions If true, all the solutions are searched.
	 */

	public Boolean solve(final boolean allSolutions) {
		if (allSolutions) {
			boolean soluble = this.searchOneSolution() == Boolean.TRUE;
			while (soluble) {
				this.logger.info("A solution was found.");
				soluble =
					this.palmSolver.getState().discardCurrentSolution()
							&& this.searchOneSolution() == Boolean.TRUE;
			}
		}
		else {
			return this.searchOneSolution();
		}
		return Boolean.TRUE;
	}
}
