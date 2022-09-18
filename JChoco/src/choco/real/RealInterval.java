package choco.real;

import choco.ContradictionException;

/**
 * An interface for real intervals.
 */
public interface RealInterval {
	/**
	 * @return the lower bound.
	 */
	public double getInf();

	/**
	 * @return the upper bound.
	 */
	public double getSup();

	/**
	 * Modifies the bounds for intersecting with the specified interval.
	 * @param interval
	 * @throws ContradictionException
	 */
	public void intersect(RealInterval interval) throws ContradictionException;
}
