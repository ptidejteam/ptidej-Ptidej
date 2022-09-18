package choco.real.exp;

import java.util.List;
import java.util.Set;
import choco.Problem;
import choco.real.RealExp;
import choco.real.RealVar;

/**
 * A binary real expression.
 */
public abstract class AbstractRealBinTerm extends AbstractRealCompoundTerm {
	protected RealExp exp1, exp2;

	public AbstractRealBinTerm(
		final Problem pb,
		final RealExp exp1,
		final RealExp exp2) {
		super(pb);
		this.exp1 = exp1;
		this.exp2 = exp2;
	}

	public Set collectVars(final Set s) {
		this.exp1.collectVars(s);
		this.exp2.collectVars(s);
		return s;
	}

	public boolean isolate(final RealVar var, final List wx, final List wox) {
		final boolean dependsOnX =
			this.exp1.isolate(var, wx, wox) | this.exp2.isolate(var, wx, wox);
		if (dependsOnX) {
			wx.add(this);
		}
		else {
			wox.add(this);
		}
		return dependsOnX;
	}

	public List subExps(final List l) {
		this.exp1.subExps(l);
		this.exp2.subExps(l);
		l.add(this);
		return l;
	}
}
