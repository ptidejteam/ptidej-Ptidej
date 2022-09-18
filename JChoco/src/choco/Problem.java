/* --------------------------------------------------
 *                   CHOCO
 *   Copyright (C) F. Laburthe, 1999-2004
 * --------------------------------------------------
 *    an open-source Constraint Programming Kernel
 *          for Research and Education
 * --------------------------------------------------
 *
 * file: choco.Problem.java
 * last modified by Francois 28 août 2003:14:59:09
 */
package choco;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import choco.bool.BinDisjunction;
import choco.integer.IntConstraint;
import choco.integer.IntExp;
import choco.integer.IntVar;
import choco.integer.constraints.EqualXC;
import choco.integer.constraints.EqualXYC;
import choco.integer.constraints.GreaterOrEqualXC;
import choco.integer.constraints.GreaterOrEqualXYC;
import choco.integer.constraints.IntLinComb;
import choco.integer.constraints.LessOrEqualXC;
import choco.integer.constraints.NotEqualXC;
import choco.integer.constraints.NotEqualXYC;
import choco.integer.var.IntTerm;
import choco.mem.Environment;
import choco.prop.AbstractPropagationEngine;
import choco.prop.ChocEngine;
import choco.prop.ConstraintEvent;
import choco.prop.EventQueue;
import choco.prop.PropagationEngine;
import choco.real.RealVar;
import choco.real.var.RealVarImpl;

/**
 * A problem is a global structure containing variables bound by listeners
 * as well as solutions or solver parameters
 */
public class Problem extends AbstractEntity implements IntModeler {

	/**
	 * a flag that may be turned on when debugging the Choco library
	 */
	public static final boolean DEBUG = false;

	/**
	 * All the listeners of the problem.
	 */
	protected List constraints;

	/**
	 * All the search intVars in the problem.
	 */

	protected List intVars;

	/**
	 * All the set intVars in the problem.
	 */

	protected List setVars;

	/**
	 * All the float vars in the problem.
	 */
	protected List floatVars;

	/**
	 * Precision of the search for a real problem.
	 */
	protected double precision = 1.0e-6;

	/**
	 * Minimal width reduction between two propagations.
	 */
	protected double reduction = 0.99;

	/**
	 * Allows to know if the problem is feasible (null if it was not solved).
	 */

	public Boolean feasible = null;

	/**
	 * True if the problem was solved.
	 */

	protected boolean solved = false;

	/**
	 * The variable modelling the objective function
	 */
	protected choco.integer.var.IntDomainVar objective;

	/**
	 *  Maximization / Minimization problem
	 */
	protected boolean doMaximize;

	/**
	 * The propagation engine to propagate during solving.
	 */

	protected AbstractPropagationEngine propagationEngine;

	/**
	 * The object controlling the global search exploration
	 */

	protected Solver solver;

	/**
	 * The environment managing the backtrackable data.
	 */

	protected Environment environment;

	/**
	 * Reference to the root Logger, an object for logging global trace statements (using the java.util.logging package)
	 */

	//	private static Logger logger = Logger.getLogger("choco");

	/**
	 * Constructs a problem.
	 */

	public Problem() {
		this(new Environment());
	}

	/**
	 * Constructs a problem.
	 * @param env the Environment responsible for all the memory management
	 */

	public Problem(final Environment env) {
		this.environment = env;
		this.intVars = new ArrayList();
		this.setVars = new ArrayList();
		this.floatVars = new ArrayList();
		this.constraints = new ArrayList();
		this.propagationEngine = new ChocEngine(this);
		this.solver = new Solver(this);
	}

	public Constraint eq(
		final choco.integer.IntExp x,
		final choco.integer.IntExp y) {
		if (x instanceof IntVar && y instanceof IntVar) {
			return new EqualXYC((IntVar) x, (IntVar) y, 0);
		}
		else if ((x instanceof IntTerm || x instanceof IntVar)
				&& (y instanceof IntTerm || y instanceof IntVar)) {
			return this.eq(this.minus(x, y), 0);
		}
		else {
			throw new Error("IntExp not a good exp");
		}
	}

	public Constraint eq(final choco.integer.IntExp x, final int c) {
		if (x instanceof choco.integer.var.IntTerm) {
			final choco.integer.var.IntTerm t = (choco.integer.var.IntTerm) x;
			if (t.getSize() == 2
					&& t.getCoefficient(0) + t.getCoefficient(1) == 0) {
				return new EqualXYC(
					t.getVariable(0),
					t.getVariable(1),
					(c - t.getConstant()) / t.getCoefficient(0));
			}
			else {
				return new IntLinComb(
					((choco.integer.var.IntTerm) x).getVariables(),
					((choco.integer.var.IntTerm) x).getCoefficients(),
					-c,
					IntLinComb.EQ);
			}
		}
		else if (x instanceof choco.integer.var.IntDomainVar) {
			return new EqualXC((choco.integer.var.IntDomainVar) x, c);
		}
		else {
			throw new Error("IntExp not a term, not a var");
		}
	}

	public final Constraint eq(final int c, final choco.integer.IntExp x) {
		return this.eq(x, c);
	}

	/**
	 * removes (permanently) a constraint from the overall list of constraints of the problem
	 * @param constraintIdx
	 */
	public void eraseConstraint(final int constraintIdx) {
		this.constraints.set(constraintIdx, null);
	}

	public Constraint geq(
		final choco.integer.IntExp x,
		final choco.integer.IntExp y) {
		if (x instanceof IntVar && y instanceof IntVar) {
			return new GreaterOrEqualXYC((IntVar) x, (IntVar) y, 0);
		}
		else if ((x instanceof IntTerm || x instanceof IntVar)
				&& (y instanceof IntTerm || y instanceof IntVar)) {
			return this.geq(this.minus(x, y), 0);
		}
		else {
			throw new Error("IntExp not a good exp");
		}
	}

	/** Creates a constraint by stating that a term is greater or equal than a constant
	 * @param x the expression
	 * @param c the constant
	 * @return the linear inequality constraint
	 */
	public Constraint geq(final choco.integer.IntExp x, final int c) {
		if (x instanceof choco.integer.var.IntTerm) {
			final choco.integer.var.IntTerm t = (choco.integer.var.IntTerm) x;
			if (t.getSize() == 2
					&& t.getCoefficient(0) + t.getCoefficient(1) == 0) {
				if (t.getCoefficient(0) > 0) {
					return new GreaterOrEqualXYC(
						t.getVariable(0),
						t.getVariable(1),
						(c - t.getConstant()) / t.getCoefficient(0));
				}
				else {
					return new GreaterOrEqualXYC(
						t.getVariable(1),
						t.getVariable(0),
						(c - t.getConstant()) / t.getCoefficient(1));
				}
			}
			else {
				return new IntLinComb(
					((choco.integer.var.IntTerm) x).getVariables(),
					((choco.integer.var.IntTerm) x).getCoefficients(),
					-c,
					IntLinComb.GEQ);
			}
		}
		else if (x instanceof choco.integer.var.IntDomainVar) {
			return new GreaterOrEqualXC((choco.integer.var.IntDomainVar) x, c);
		}
		else {
			throw new Error("IntExp not a term, not a var");
		}
	}

	public Constraint geq(final int c, final choco.integer.IntExp x) {
		if (x instanceof choco.integer.var.IntTerm) {
			final int[] coeffs =
				((choco.integer.var.IntTerm) x).getCoefficients();
			final int n = coeffs.length;
			final int[] oppcoeffs = new int[n];
			for (int i = 0; i < n; i++) {
				oppcoeffs[i] = -coeffs[i];
			}
			return new IntLinComb(
				((choco.integer.var.IntTerm) x).getVariables(),
				oppcoeffs,
				c,
				IntLinComb.GEQ);
		}
		else if (x instanceof choco.integer.var.IntDomainVar) {
			return new LessOrEqualXC((choco.integer.var.IntDomainVar) x, c);
		}
		else {
			throw new Error("IntExp not a term, not a var");
		}
	}

	/**
	 * accessor returning the object controlling the propagation mechanism
	 * @return the propagation engine
	 */
	public final ChocEngine getChocEngine() {
		return (ChocEngine) this.propagationEngine;
	}

	public int getConstraintIndex(final AbstractConstraint c) {
		return this.constraints.indexOf(c);
	}

	/**
	 * Returns the memory environment used by the problem.
	 */

	public final Environment getEnvironment() {
		return this.environment;
	}

	/** <i>Network management:</i>
	 *    Retrieve a variable by its index (all search variables of
	 *    the problem are numbered in sequence from 0 on)
	 *    @param i   index of the variable in the problem
	 */

	public final IntConstraint getIntConstraint(final int i) {
		return (IntConstraint) this.constraints.get(i);
	}

	/** <i>Network management:</i>
	 *    Retrieve a variable by its index (all search variables of
	 *    the problem are numbered in sequence from 0 on)
	 *    @param i   index of the variable in the problem
	 */

	public final IntVar getIntVar(final int i) {
		return (IntVar) this.intVars.get(i);
	}

	/**
	 * retrieving the total number of constraints over integers
	 * @return  the total number of constraints over integers in the problem
	 */
	public final int getNbIntConstraints() {
		return this.constraints.size();
	}

	/**
	 * retrieving the total number of variables
	 * @return  the total number of variables in the problem
	 */
	public final int getNbIntVars() {
		return this.intVars.size();
	}

	/**
	 * Returns the number of variables.
	 * @return
	 */
	public final int getNbRealVars() {
		return this.floatVars.size();
	}

	public double getPrecision() {
		return this.precision;
	}

	/**
	 * As all Entity, must be able to return a Problem, returns itself.
	 */

	public final Problem getProblem() {
		return this;
	}

	/**
	 *    Returns the propagation engine associated to the problem
	 */

	public PropagationEngine getPropagationEngine() {
		return this.getChocEngine();
	}

	/**
	 * Returns a real variable.
	 * @param i
	 * @return
	 */
	public final RealVar getRealVar(final int i) {
		return (RealVar) this.floatVars.get(i);
	}

	public double getReduction() {
		return this.reduction;
	}

	/**
	 * accessor returning the handler of the object responsible for the resolution of the problem.
	 * It may be either a local search solver or a global search solver
	 * @return  the object responsible for solving the problem
	 */
	public final Solver getSolver() {
		return this.solver;
	}

	/**
	 * returning the index of the current worl
	 */
	public final int getWorldIndex() {
		return this.environment.getWorldIndex();
	}

	public final Constraint gt(
		final choco.integer.IntExp x,
		final choco.integer.IntExp y) {
		return this.geq(this.minus(x, y), -1);
	}

	public final Constraint gt(final choco.integer.IntExp x, final int c) {
		return this.geq(x, c + 1);
	}

	public final Constraint gt(final int c, final choco.integer.IntExp x) {
		return this.geq(c + 1, x);
	}

	public final boolean isConsistent() {
		final int nbc = this.getNbIntConstraints();
		boolean consistent = true;
		for (int i = 0; i < nbc && consistent; i++) {
			final IntConstraint c = this.getIntConstraint(i);
			consistent = consistent && c.isConsistent();
		}
		return consistent;
	}

	/**
	 * test if the problem has been found to be feasible (there exist solutions) or not.
	 * @return Boolean.TRUE if a solution was found,
	 *         Boolean.FALSE if the problem was proven infeasible,
	 *         null otherwise
	 */
	public final Boolean isFeasible() {
		return this.feasible;
	}

	public final Constraint leq(
		final choco.integer.IntExp x,
		final choco.integer.IntExp y) {
		return this.geq(y, x);
	}

	public final Constraint leq(final choco.integer.IntExp x, final int c) {
		return this.geq(c, x);
	}

	public final Constraint leq(final int c, final choco.integer.IntExp x) {
		return this.geq(x, c);
	}

	public final Constraint lt(final int c, final IntExp x) {
		return this.gt(x, c);
	}

	public final Constraint lt(final IntExp x, final int c) {
		return this.gt(c, x);
	}

	public final Constraint lt(final IntExp x, final IntExp y) {
		return this.gt(y, x);
	}

	/** Creates a new search variable with an interval domain
	 * @param name the name of the variable
	 * @param min minimal allowed value (included in the domain)
	 * @param max maximal allowed value (included in the domain)
	 * @return the variable
	 */
	public choco.integer.IntVar makeBoundIntVar(
		final String name,
		final int min,
		final int max) {
		final choco.integer.var.IntDomainVar v =
			new choco.integer.var.IntDomainVar(
				this,
				name,
				IntVar.BOUNDS,
				min,
				max);
		this.intVars.add(v);
		return v;
	}

	/**
	 * Creates a one dimensional array of integer variables
	 *
	 * @param name the name of the array (a prefix shared by all individual IntVars)
	 * @param dim  the number of entries
	 * @param min  the minimal domain value for all variables in the array
	 * @param max  the maximal domain value for all variables in the array
	 */
	public IntVar[] makeBoundIntVarArray(
		final String name,
		final int dim,
		final int min,
		final int max) {
		final IntVar[] res = new IntVar[dim];
		for (int i = 0; i < dim; i++) {
			res[i] =
				this.makeBoundIntVar(
					name + "[" + String.valueOf(i) + "]",
					min,
					max);
		}
		return res;
	}

	/**
	 * Creates a one dimensional array of integer variables
	 *
	 * @param name the name of the array (a prefix shared by all individual IntVars)
	 * @param dim1 the number of entries for the first index
	 * @param dim2 the number of entries for the second index
	 * @param min  the minimal domain value for all variables in the array
	 * @param max  the maximal domain value for all variables in the array
	 */
	public IntVar[][] makeBoundIntVarArray(
		final String name,
		final int dim1,
		final int dim2,
		final int min,
		final int max) {
		final IntVar[][] res = new IntVar[dim1][dim2];
		for (int i = 0; i < dim1; i++) {
			for (int j = 0; j < dim2; j++) {
				res[i][j] =
					this.makeBoundIntVar(name + "[" + String.valueOf(i) + ", "
							+ String.valueOf(j) + "]", min, max);
			}
		}
		return res;
	}

	/** Creates a new search variable with an enumerated domain
	 * @param name the name of the variable
	 * @param min minimal allowed value (included in the domain)
	 * @param max maximal allowed value (included in the domain)
	 * @return the variable
	 */
	public choco.integer.IntVar makeEnumIntVar(
		final String name,
		final int min,
		final int max) {
		final choco.integer.var.IntDomainVar v =
			new choco.integer.var.IntDomainVar(
				this,
				name,
				IntVar.LIST,
				min,
				max);
		this.intVars.add(v);
		return v;
	}

	/**
	 * Creates a real variable.
	 * @param name
	 * @param min
	 * @param max
	 * @return
	 */
	public RealVar makeRealVar(
		final String name,
		final double min,
		final double max) {
		final RealVar v = new RealVarImpl(this, name, min, max);
		this.floatVars.add(v);
		return v;
	}

	/** <i>resolution:</i>
	 *    Searches for the solution maximizing the objective criterion.
	 *    @param obj The variable modelling the optimization criterion
	 *    @param restart If true, then a new search is restarted from scratch
	 *                         after each solution is found;
	 *                   otherwise a single branch-and-bound search is performed
	 */
	public int maximize(final choco.integer.IntVar obj, final boolean restart) {
		return 1;
	}

	/** <i>Resolution:</i>
	 *    Searches for the solution minimizing the objective criterion.
	 *    @param obj The variable modelling the optimization criterion
	 *    @param restart If true, then a new search is restarted from scratch
	 *                         after each solution is found;
	 *                   otherwise a single branch-and-bound search is performed
	 */

	public int minimize(final choco.integer.IntVar obj, final boolean restart) {
		return 1;
	}

	/** Subtracting two terms one from another
	 * @param t1 first term
	 * @param t2 second term
	 * @return the term (a fresh one)
	 */
	public choco.integer.IntExp minus(
		final choco.integer.IntExp t1,
		final choco.integer.IntExp t2) {
		if (t1 instanceof choco.integer.var.IntTerm) {
			if (t2 instanceof choco.integer.var.IntTerm) {
				final int[] coeffs2 =
					((choco.integer.var.IntTerm) t2).getCoefficients();
				final int n2 = coeffs2.length;
				final int[] oppcoeffs2 = new int[n2];
				for (int i = 0; i < n2; i++) {
					oppcoeffs2[i] = -coeffs2[i];
				}
				return this.plus(
					((choco.integer.var.IntTerm) t1).getCoefficients(),
					((choco.integer.var.IntTerm) t1).getVariables(),
					((choco.integer.var.IntTerm) t1).getConstant(),
					oppcoeffs2,
					((choco.integer.var.IntTerm) t2).getVariables(),
					-((choco.integer.var.IntTerm) t2).getConstant());
			}
			else if (t2 instanceof choco.integer.var.IntDomainVar) {
				return this
					.plus(
						((choco.integer.var.IntTerm) t1).getCoefficients(),
						((choco.integer.var.IntTerm) t1).getVariables(),
						((choco.integer.var.IntTerm) t1).getConstant(),
						new int[] { -1 },
						new choco.integer.var.IntDomainVar[] { (choco.integer.var.IntDomainVar) t2 },
						0);
			}
			else {
				throw new Error("IntExp not a term, not a var");
			}
		}
		else if (t1 instanceof choco.integer.var.IntDomainVar) {
			if (t2 instanceof choco.integer.var.IntTerm) {
				final int[] coeffs2 =
					((choco.integer.var.IntTerm) t2).getCoefficients();
				final int n2 = coeffs2.length;
				final int[] oppcoeffs2 = new int[n2];
				for (int i = 0; i < n2; i++) {
					oppcoeffs2[i] = -coeffs2[i];
				}
				return this
					.plus(
						new int[] { 1 },
						new choco.integer.var.IntDomainVar[] { (choco.integer.var.IntDomainVar) t1 },
						0,
						oppcoeffs2,
						((choco.integer.var.IntTerm) t2).getVariables(),
						-((choco.integer.var.IntTerm) t2).getConstant());
			}
			else if (t2 instanceof choco.integer.var.IntDomainVar) {
				final choco.integer.var.IntTerm t =
					new choco.integer.var.IntTerm(2);
				t.setCoefficient(0, 1);
				t.setCoefficient(1, -1);
				t.setVariable(0, (choco.integer.var.IntDomainVar) t1);
				t.setVariable(1, (choco.integer.var.IntDomainVar) t2);
				t.setConstant(0);
				return t;
			}
			else {
				throw new Error("IntExp not a term, not a var");
			}
		}
		else {
			throw new Error("IntExp not a term, not a var");
		}
	}

	public choco.integer.IntExp minus(final choco.integer.IntExp t, final int c) {
		if (t instanceof choco.integer.var.IntTerm) {
			final choco.integer.var.IntTerm t2 =
				new choco.integer.var.IntTerm((choco.integer.var.IntTerm) t);
			t2.setConstant(((choco.integer.var.IntTerm) t).getConstant() - c);
			return t2;
		}
		else if (t instanceof choco.integer.var.IntDomainVar) {
			final choco.integer.var.IntTerm t2 =
				new choco.integer.var.IntTerm(1);
			t2.setCoefficient(0, 1);
			t2.setVariable(0, (choco.integer.var.IntDomainVar) t);
			t2.setConstant(-c);
			return t2;
		}
		else {
			throw new Error("IntExp not a term, not a var");
		}
	}

	public choco.integer.IntExp minus(final int c, final choco.integer.IntExp t) {
		if (t instanceof choco.integer.var.IntTerm) {
			final choco.integer.var.IntTerm t1 = (choco.integer.var.IntTerm) t;
			final int n = t1.getSize();
			final choco.integer.var.IntTerm t2 =
				new choco.integer.var.IntTerm(n);
			for (int i = 0; i < n; i++) {
				t2.setCoefficient(i, -t1.getCoefficient(i));
				t2.setVariable(i, t1.getVariable(i));
			}
			t2.setConstant(c - t1.getConstant());
			return t2;
		}
		else if (t instanceof choco.integer.var.IntDomainVar) {
			final choco.integer.var.IntTerm t2 =
				new choco.integer.var.IntTerm(1);
			t2.setCoefficient(0, -1);
			t2.setVariable(0, (choco.integer.var.IntDomainVar) t);
			t2.setConstant(c);
			return t2;
		}
		else {
			throw new Error("IntExp not a term, not a var");
		}
	}

	/** Creates a simple linear term from one coefficient and one variable
	 * @param a the coefficient
	 * @param x the variable
	 * @return the term
	 */
	public choco.integer.IntExp mult(final int a, final choco.integer.IntExp x) {
		final choco.integer.var.IntTerm t = new choco.integer.var.IntTerm(1);
		t.setCoefficient(0, a);
		t.setVariable(0, (choco.integer.var.IntDomainVar) x);
		return t;
	}

	public Constraint neq(
		final choco.integer.IntExp x,
		final choco.integer.IntExp y) {
		if (x instanceof choco.integer.var.IntTerm) {
			return this.neq(this.minus(x, y), 0);
		}
		else if (x instanceof choco.integer.var.IntDomainVar) {
			if (y instanceof choco.integer.var.IntTerm) {
				return this.neq(this.minus(x, y), 0);
			}
			else if (y instanceof choco.integer.var.IntDomainVar) {
				return new NotEqualXYC(
					(choco.integer.var.IntDomainVar) x,
					(choco.integer.var.IntDomainVar) y,
					0);
			}
			else {
				throw new Error("IntExp not a term, not a var");
			}
		}
		else {
			throw new Error("IntExp not a term, not a var");
		}
	}

	/** Creates a constraint by stating that a term is not equal than a constant
	 * @param x the expression
	 * @param c the constant
	 * @return the linear disequality constraint
	 */
	public Constraint neq(final choco.integer.IntExp x, final int c) {
		if (x instanceof choco.integer.var.IntTerm) {
			final choco.integer.var.IntTerm t = (choco.integer.var.IntTerm) x;
			if (t.getSize() == 2
					&& t.getCoefficient(0) + t.getCoefficient(1) == 0) {
				return new NotEqualXYC(
					t.getVariable(0),
					t.getVariable(1),
					(c - t.getConstant()) / t.getCoefficient(0));
			}
			else {
				return new IntLinComb(
					((choco.integer.var.IntTerm) x).getVariables(),
					((choco.integer.var.IntTerm) x).getCoefficients(),
					-c,
					IntLinComb.NEQ);
			}
		}
		else if (x instanceof choco.integer.var.IntDomainVar) {
			return new NotEqualXC((choco.integer.var.IntDomainVar) x, c);
		}
		else {
			throw new Error("IntExp not a term, not a var");
		}
	}

	public final Constraint neq(final int c, final choco.integer.IntExp x) {
		return this.neq(x, c);
	}

	public AbstractConstraint or(
		final AbstractConstraint c1,
		final AbstractConstraint c2) {
		return new BinDisjunction(c1, c2);
	}

	/** Adding two terms one to another
	 * @param t1 first term
	 * @param t2 second term
	 * @return the term (a fresh one)
	 */
	public choco.integer.IntExp plus(
		final choco.integer.IntExp t1,
		final choco.integer.IntExp t2) {
		if (t1 instanceof choco.integer.var.IntTerm) {
			if (t2 instanceof choco.integer.var.IntTerm) {
				return this.plus(
					((choco.integer.var.IntTerm) t1).getCoefficients(),
					((choco.integer.var.IntTerm) t1).getVariables(),
					((choco.integer.var.IntTerm) t1).getConstant(),
					((choco.integer.var.IntTerm) t2).getCoefficients(),
					((choco.integer.var.IntTerm) t2).getVariables(),
					((choco.integer.var.IntTerm) t2).getConstant());
			}
			else if (t2 instanceof choco.integer.var.IntDomainVar) {
				return this
					.plus(
						((choco.integer.var.IntTerm) t1).getCoefficients(),
						((choco.integer.var.IntTerm) t1).getVariables(),
						((choco.integer.var.IntTerm) t1).getConstant(),
						new int[] { 1 },
						new choco.integer.var.IntDomainVar[] { (choco.integer.var.IntDomainVar) t2 },
						0);
			}
			else {
				throw new Error("IntExp not a term, not a var");
			}
		}
		else if (t1 instanceof choco.integer.var.IntDomainVar) {
			if (t2 instanceof choco.integer.var.IntTerm) {
				return this
					.plus(
						new int[] { 1 },
						new choco.integer.var.IntDomainVar[] { (choco.integer.var.IntDomainVar) t1 },
						0,
						((choco.integer.var.IntTerm) t2).getCoefficients(),
						((choco.integer.var.IntTerm) t2).getVariables(),
						((choco.integer.var.IntTerm) t2).getConstant());
			}
			else if (t2 instanceof choco.integer.var.IntDomainVar) {
				final choco.integer.var.IntTerm t =
					new choco.integer.var.IntTerm(2);
				t.setCoefficient(0, 1);
				t.setCoefficient(1, 1);
				t.setVariable(0, (choco.integer.var.IntDomainVar) t1);
				t.setVariable(1, (choco.integer.var.IntDomainVar) t2);
				t.setConstant(0);
				return t;
			}
			else {
				throw new Error("IntExp not a term, not a var");
			}
		}
		else {
			throw new Error("IntExp not a term, not a var");
		}
	}

	public choco.integer.IntExp plus(final choco.integer.IntExp t, final int c) {
		if (t instanceof choco.integer.var.IntTerm) {
			final choco.integer.var.IntTerm t2 =
				new choco.integer.var.IntTerm((choco.integer.var.IntTerm) t);
			t2.setConstant(((choco.integer.var.IntTerm) t).getConstant() + c);
			return t2;
		}
		else if (t instanceof choco.integer.var.IntDomainVar) {
			final choco.integer.var.IntTerm t2 =
				new choco.integer.var.IntTerm(1);
			t2.setCoefficient(0, 1);
			t2.setVariable(0, (choco.integer.var.IntDomainVar) t);
			t2.setConstant(c);
			return t2;
		}
		else {
			throw new Error("IntExp not a term, not a var");
		}
	}

	public final choco.integer.IntExp plus(
		final int c,
		final choco.integer.IntExp t1) {
		return this.plus(t1, c);
	}

	/**
	 * Utility method for constructing a term from two lists of variables, list of coeffcicients and constants
	 * @param coeffs1 coefficients from the first term
	 * @param vars1   variables from the first term
	 * @param cste1   constant from the fisrt term
	 * @param coeffs2 coefficients from the second term
	 * @param vars2   variables from the second term
	 * @param cste2   constant from the second term
	 * @return the term (a fresh one)
	 */
	protected choco.integer.IntExp plus(
		final int[] coeffs1,
		final choco.integer.var.IntDomainVar[] vars1,
		final int cste1,
		final int[] coeffs2,
		final choco.integer.var.IntDomainVar[] vars2,
		final int cste2) {
		final int n1 = vars1.length;
		final int n2 = vars2.length;
		final choco.integer.var.IntTerm t =
			new choco.integer.var.IntTerm(n1 + n2);
		for (int i = 0; i < n1; i++) {
			t.setVariable(i, vars1[i]);
			t.setCoefficient(i, coeffs1[i]);
		}
		for (int i = 0; i < n2; i++) {
			t.setVariable(n1 + i, vars2[i]);
			t.setCoefficient(n1 + i, coeffs2[i]);
		}
		t.setConstant(cste1 + cste2);
		return t;
	}

	/** <i>Network management:</i>
	 *    adding a constraint to the problem. Note that this does not
	 *    propagate anything !
	 *    @param c   the constraint to add
	 */

	public void post(final Constraint c) {
		this.constraints.add(c);
		c.addListener();
		final ConstraintEvent event = (ConstraintEvent) c.getEvent();
		final ChocEngine pe = this.getChocEngine();
		pe.registerEvent(event);
		pe.postConstAwake(c, true);
	}

	public String pretty() {
		final StringBuffer buf =
			new StringBuffer("Pb[" + this.getNbIntVars() + " vars, "
					+ this.getNbIntConstraints() + " cons]\n");
		for (int i = 0; i < this.getNbIntVars(); i++) {
			buf.append(this.getIntVar(i).pretty());
			buf.append("\n");
		}
		for (int i = 0; i < this.floatVars.size(); i++) {
			final RealVar realVar = (RealVar) this.floatVars.get(i);
			buf.append(realVar);
		}
		return new String(buf);
	}

	/** <i>Propagation:</i>
	 *    Computes consistency on the problem (the problem may no longer
	 *    be consistent since the last propagation because of listeners
	 *    that have been posted and variables that have been reduced
	 * @throws choco.ContradictionException
	 */

	public void propagate() throws ContradictionException {
		final ChocEngine pe = this.getChocEngine();
		boolean someEvents = true;
		while (someEvents) {
			final EventQueue q = pe.getNextActiveEventQueue();
			if (q != null) {
				q.propagateSomeEvents();
			}
			else {
				someEvents = false;
			}
		}
		pe.checkCleanState();
	}

	/** Building a term from a scalar product of coefficients and variables
	 * @param lc the array of coefficients
	 * @param lv the array of variables
	 * @return the term
	 */
	public choco.integer.IntExp scalar(
		final int[] lc,
		final choco.integer.IntVar[] lv) {
		final int n = lc.length;
		Assert.assertTrue(lv.length == n);
		final choco.integer.var.IntTerm t = new choco.integer.var.IntTerm(n);
		for (int i = 0; i < n; i++) {
			t.setCoefficient(i, lc[i]);
			if (lv[i] instanceof choco.integer.var.IntDomainVar) {
				t.setVariable(i, (choco.integer.var.IntDomainVar) lv[i]);
			}
			else {
				throw new Error("unknown kind of IntVar");
			}
		}
		return t;
	}

	/**
	 * Building a term from a scalar product of coefficients and variables
	 *
	 * @param lc the array of coefficients
	 * @param lv the array of variables
	 * @return the term
	 */
	public final IntExp scalar(final IntVar[] lv, final int[] lc) {
		return this.scalar(lc, lv);
	}

	public final void setMaximizationObjective(final choco.integer.IntVar obj) {
		this.objective = (choco.integer.var.IntDomainVar) obj;
		this.doMaximize = true;
	}

	public final void setMinimizationObjective(final choco.integer.IntVar obj) {
		this.objective = (choco.integer.var.IntDomainVar) obj;
		this.doMaximize = false;
	}

	public void setPrecision(final double precision) {
		this.precision = precision;
	}

	public void setReduction(final double reduction) {
		this.reduction = reduction;
	}

	public Boolean solve() {
		this.solver.firstSolution = true;
		this.solver.generateSearchSolver(this);
		this.solver.launch();
		return this.isFeasible();
	}

	/** <i>Resolution:</i>
	 *    Searches for the first solution. Either stops in the state of
	 *    that solution or returns to the initial state when no solutions exist.
	 * @param all Specifies wether all the solutions should be searched.
	 */

	public Boolean solve(final boolean all) {
		this.solver.firstSolution = !all;
		this.solver.generateSearchSolver(this);
		this.solver.launch();
		return this.isFeasible();
	}

	public boolean solveAll() {
		this.solver.firstSolution = false;
		this.solver.generateSearchSolver(this);
		this.solver.launch();
		return true;
	}

	/**
	 * Building a term from a sum of integer expressions
	 *
	 * @param lv the array of integer expressions
	 * @return the term
	 */
	public IntExp sum(final IntExp[] lv) {
		final int n = lv.length;
		final choco.integer.var.IntTerm t = new choco.integer.var.IntTerm(n);
		for (int i = 0; i < n; i++) {
			t.setCoefficient(i, 1);
			if (lv[i] instanceof choco.integer.var.IntDomainVar) {
				t.setVariable(i, (choco.integer.var.IntDomainVar) lv[i]);
			}
			else {
				throw new Error("unexpected kind of IntExp");
			}
		}
		return t;
	}

	/**
	 * pushing one world on the stack
	 */
	public final void worldPop() {
		this.environment.worldPop();
	}

	/**
	 * Backtracks to a given level in the search tree.
	 */
	public final void worldPopUntil(final int n) {
		while (this.environment.currentWorld > n) {
			this.worldPop();
		}
	}

	/**
	 * pushing one world on the stack
	 */
	public final void worldPush() {
		this.environment.worldPush();
	}
}
