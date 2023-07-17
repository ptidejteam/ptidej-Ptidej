package choco.real.exp;

import choco.ContradictionException;
import choco.real.RealInterval;

/**
 * Created by IntelliJ IDEA.
 * User: rochart
 * Date: Mar 31, 2004
 * Time: 12:37:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class RealIntervalImpl implements RealInterval {
	protected double inf;
	protected double sup;

	public RealIntervalImpl() {
		this(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
	}

	public RealIntervalImpl(final double inf, final double sup) {
		this.inf = inf;
		this.sup = sup;
	}

	public RealIntervalImpl(final RealInterval i) {
		this(i.getInf(), i.getSup());
	}

	public double getInf() {
		return this.inf;
	}

	public double getSup() {
		return this.sup;
	}

	public void intersect(final RealInterval interval)
			throws ContradictionException {
		if (interval.getInf() > this.inf) {
			this.inf = interval.getInf();
		}
		if (interval.getSup() < this.sup) {
			this.sup = interval.getSup();
		}
	}

	public String toString() {
		return "[" + this.inf + "," + this.sup + "]";
	}
}
