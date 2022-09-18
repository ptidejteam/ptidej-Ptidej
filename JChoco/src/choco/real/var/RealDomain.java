package choco.real.var;

import choco.Entity;
import choco.real.RealInterval;

/**
 * An interface for real variable domains.
 */
public interface RealDomain extends Entity, RealInterval {
	public void clearDeltaDomain();

	void freezeDeltaDomain();

	boolean getReleasedDeltaDomain();

	boolean releaseDeltaDomain();

	void silentlyAssign(RealInterval i);
}
