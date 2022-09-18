package choco.real.search;

import choco.real.RealVar;
import choco.search.IVarSelector;

/**
 * An interface for selecting a real interval variable to narrow.
 */
public interface RealVarSelector extends IVarSelector {
	public RealVar selectRealVar();
}
