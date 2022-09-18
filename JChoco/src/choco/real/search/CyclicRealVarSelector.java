package choco.real.search;

import choco.Problem;
import choco.real.RealMath;
import choco.real.RealVar;

/**
 * A cyclic variable selector : since a dichotomy algorithm is used, cyclic assiging is nedded for instantiate
 * a real interval variable.
 */
public class CyclicRealVarSelector extends AbstractRealVarSelector implements
		RealVarSelector {
	protected int current;
	//protected double precision = 1.e-6;

	public CyclicRealVarSelector(final Problem pb) {
		this.problem = pb;
		this.current = -1;
	}

	public RealVar selectRealVar() {
		final int nbvars = this.problem.getNbRealVars();
		if (nbvars == 0) {
			return null;
		}
		final int start = this.current == -1 ? nbvars - 1 : this.current;
		int n = (this.current + 1) % nbvars;
		while (n != start
				&& RealMath.isCanonical(
					this.problem.getRealVar(n),
					this.problem.getPrecision())) {
			n = (n + 1) % nbvars;
		}
		if (RealMath.isCanonical(
			this.problem.getRealVar(n),
			this.problem.getPrecision())) {
			return null;
		}
		this.current = n;
		return this.problem.getRealVar(n);
	}
}
