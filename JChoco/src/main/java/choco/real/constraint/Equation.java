package choco.real.constraint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import choco.AbstractConstraint;
import choco.AbstractVar;
import choco.ContradictionException;
import choco.Problem;
import choco.Var;
import choco.bool.AbstractCompositeConstraint;
import choco.real.RealExp;
import choco.real.RealInterval;
import choco.real.RealMath;
import choco.real.RealVar;
import choco.real.exp.RealIntervalConstant;
import choco.real.exp.RealIntervalImpl;

/**
 * A basic constraint using HC4 algorithm for filtering values with respect to a mathematical equation.
 */
public class Equation extends AbstractConstraint implements RealConstraint {
	protected RealInterval cste;
	protected RealExp exp;
	protected RealVar[] vars;
	protected RealExp[] subExps;
	public int[] cIndices;

	protected int nbBoxedVars = 0;
	protected RealVar[] boxedVars;
	protected RealExp[][] subExpsWX;
	protected RealExp[][] subExpsWOX;
	protected int boxConsistencyDepth = 6;

	public Equation(final Problem pb, final RealExp exp) {
		this(pb, exp, new RealIntervalConstant(0.0, 0.0));
	}

	public Equation(final Problem pb, final RealExp exp, final RealInterval cste) {
		this.problem = pb;
		this.cste = cste;
		this.exp = exp;

		// Collect the variables
		final Set collectedVars = new HashSet();
		exp.collectVars(collectedVars);
		this.vars = new RealVar[collectedVars.size()];
		this.vars = (RealVar[]) collectedVars.toArray(this.vars);
		this.boxedVars = new RealVar[this.vars.length];
		this.subExpsWX = new RealExp[this.vars.length][];
		this.subExpsWOX = new RealExp[this.vars.length][];

		// Collect the subexpressions
		final List collectedSubExp = new ArrayList();
		exp.subExps(collectedSubExp);
		this.subExps = new RealExp[collectedSubExp.size()];
		this.subExps = (RealExp[]) collectedSubExp.toArray(this.subExps);

		this.cIndices = new int[this.vars.length];
	}

	public void addBoxedVar(final RealVar var) {
		if (this.nbBoxedVars == this.boxedVars.length) {
			Logger
				.getLogger("choco")
				.severe(
					"Cannot box more variables than variables involved in the constraint !!");
			return;
		}
		final List wx = new ArrayList();
		final List wox = new ArrayList();
		this.exp.isolate(var, wx, wox);
		if (wx.size() == 0) {
			Logger.getLogger("choco").severe(
				"Cannot box variables not involved in the constraint !!");
			return;
		}
		this.boxedVars[this.nbBoxedVars] = var;
		this.subExpsWX[this.nbBoxedVars] =
			(RealExp[]) wx.toArray(new RealExp[0]);
		this.subExpsWOX[this.nbBoxedVars] =
			(RealExp[]) wox.toArray(new RealExp[0]);
		this.nbBoxedVars++;
	}

	
	public int assignIndices(final AbstractCompositeConstraint root, final int i) {
		int j = i;
		for (int k = 0; k < this.getNbVars(); k++) {
			j++;
			this.connectVar((AbstractVar) this.vars[k], j);
			this.setConstraintIndex(k, this.vars[k].getNbConstraints());
		}
		return j;
	}

	// ==== Propag ====

	public void awakeOnInf(final int idx) throws ContradictionException {
		this.constAwake(false);
	}

	public void awakeOnSup(final int idx) throws ContradictionException {
		this.constAwake(false);
	}

	private void bc(final RealVar var, final RealExp[] wx, final RealExp[] wox)
			throws ContradictionException {
		RealInterval[] unexplored =
			new RealInterval[this.boxConsistencyDepth * 2];
		int[] depths = new int[this.boxConsistencyDepth * 2];
		int depth = 0;
		int idx = 0;
		boolean fin = false;

		RealInterval leftBd, rightBd, oldValue;
		leftBd = new RealIntervalImpl();
		rightBd = new RealIntervalImpl();
		oldValue = new RealIntervalImpl(var);

		this.tighten(wox);

		// Left bound !
		while (!fin) {
			if (this.not_inconsistent(wx)) {
				if (this.boxConsistencyDepth <= depth) {
					leftBd.intersect(var);
					fin = true;
				}
				else {
					final RealInterval left = RealMath.firstHalf(var);
					final RealInterval right = RealMath.secondHalf(var);

					var.silentlyAssign(left);
					depth++;
					unexplored[idx] = right;
					depths[idx] = depth;
					idx++;
				}
			}
			else if (idx != 0) {
				var.silentlyAssign(unexplored[--idx]);
				depth = depths[idx];
			}
			else {
				this.getProblem().getPropagationEngine().raiseContradiction();
			}
		}

		// Reversing not explored intervals (in order to avoid to check already checked parts of the search space.

		final RealInterval[] tmp1 =
			new RealInterval[this.boxConsistencyDepth * 2];
		final int[] tmp2 = new int[this.boxConsistencyDepth * 2];

		for (int i = 0; i < idx; i++) {
			final int j = idx - i - 1;
			tmp1[i] = unexplored[j];
			tmp2[i] = depths[j];
		}

		unexplored = tmp1;
		depths = tmp2;

		// Right bound if needed
		if (idx != 0) {
			var.silentlyAssign(unexplored[--idx]);
			depth = depths[idx];
			fin = false;

			while (!fin) {
				if (this.not_inconsistent(wx)) {
					if (this.boxConsistencyDepth <= depth) {
						rightBd.intersect(var);
						fin = true;
					}
					else {
						final RealInterval left = RealMath.firstHalf(var);
						final RealInterval right = RealMath.secondHalf(var);

						var.silentlyAssign(right);
						depth++;
						unexplored[idx] = left;
						depths[idx] = depth;
						idx++;
					}
				}
				else if (idx != 0) {
					var.silentlyAssign(unexplored[--idx]);
					depth = depths[idx];
				}
				else {
					rightBd = leftBd;
					fin = true;
				}
			}
		}
		else {
			rightBd = leftBd;
		}

		// Propagation
		var.silentlyAssign(oldValue);
		//System.out.println(var + "--> left : " + leftBd + "    right : " + rightBd);
		var.intersect(new RealIntervalConstant(leftBd.getInf(), rightBd
			.getSup()));
	}

	public void boxAllVars() {
		for (int i = 0; i < this.vars.length; i++) {
			final RealVar var = this.vars[i];
			this.addBoxedVar(var);
		}
	}

	public int getConstraintIdx(final int i) {
		if (i >= 0 && i < this.vars.length) {
			return this.cIndices[i];
		}
		else {
			return -1;
		}
	}

	// ==== Variable Management ====

	public int getNbVars() {
		return this.vars.length;
	}

	public RealVar getRealVar(final int i) {
		if (0 <= i && i < this.vars.length) {
			return this.vars[i];
		}
		return null;
	}

	public int getRealVarNb() {
		return this.vars.length;
	}

	public int getSelfIndex() {
		return 0; //To change body of implemented methods use File | Settings | File Templates.
	}

	public Var getVar(final int i) {
		if (0 <= i && i < this.vars.length) {
			return this.vars[i];
		}
		return null;
	}

	public boolean isCompletelyInstantiated() {
		return false; //To change body of implemented methods use File | Settings | File Templates.
	}

	public boolean isConsistent() {
		return false; //To change body of implemented methods use File | Settings | File Templates.
	}

	// ==== Constraint properties ====

	// TODO : c'est pas urgent :)

	public boolean isSatisfied() {
		return false; //To change body of implemented methods use File | Settings | File Templates.
	}

	private boolean not_inconsistent(final RealExp[] wx) {
		boolean contradiction = false;
		try {
			this.tighten(wx);
		}
		catch (final ContradictionException e) {
			contradiction = true;
		}
		if (contradiction) {
			return false;
		}
		else {
			return this.exp.getInf() <= this.cste.getSup()
					&& this.exp.getSup() >= this.cste.getInf();
		}
	}

	public void proj() throws ContradictionException {
		this.subExps[this.subExps.length - 1].intersect(this.cste);
		int i = this.subExps.length - 1;
		while (i > 0) {
			this.subExps[i].project();
			i--;
		}
	}

	public void propagate() throws ContradictionException {
		// Hull consitency: HC4
		this.tighten(this.subExps);
		this.proj();

		// Box consistency
		for (int i = 0; i < this.nbBoxedVars; i++) {
			this.bc(this.boxedVars[i], this.subExpsWX[i], this.subExpsWOX[i]);
		}
	}

	public void setConstraintIndex(final int i, final int idx) {
		if (i >= 0 && i < this.vars.length) {
			this.cIndices[i] = idx;
		}
		else {
			throw new Error("bug in setConstraintIndex i:" + i + " this: "
					+ this);
		}
	}

	public void tighten(final RealExp[] exps) throws ContradictionException {
		for (int i = 0; i < exps.length; i++) {
			final RealExp exp = exps[i];
			exp.tighten();
			if (exp.getInf() > exp.getSup()) {
				this.fail();
			}
		}
	}
}
