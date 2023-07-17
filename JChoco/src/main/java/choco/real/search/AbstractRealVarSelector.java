package choco.real.search;

import choco.AbstractVar;
import choco.search.AbstractSearchHeuristic;

/**
 * An interface for real variable selector during a braching assigning intervals.
 */
public abstract class AbstractRealVarSelector extends AbstractSearchHeuristic
		implements RealVarSelector {
	public AbstractVar selectVar() {
		return (AbstractVar) this.selectRealVar();
	}
}
