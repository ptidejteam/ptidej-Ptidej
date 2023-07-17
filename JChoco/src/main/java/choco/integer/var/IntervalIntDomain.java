package choco.integer.var;

import choco.ContradictionException;
import choco.mem.Environment;
import choco.mem.StoredInt;
import choco.util.IntIterator;

// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: IntervalIntDomain.java,v $
// Version:            $Revision: 1.2 $
// Last Modification:  $Date: 2004/05/26 22:00:49 $
// Last Contributor:   $Author: guehene $

public class IntervalIntDomain extends AbstractIntDomain {
	/**
	 * The backtrackable minimal value of the variable.
	 */

	protected final StoredInt inf;

	/**
	 * The backtrackable maximal value of the variable.
	 */

	protected final StoredInt sup;

	public IntervalIntDomain(
		final choco.integer.var.IntDomainVar v,
		final int a,
		final int b) {
		this.variable = v;
		this.problem = v.getProblem();
		final Environment env = this.problem.getEnvironment();
		this.inf = (StoredInt) env.makeInt(a);
		this.sup = (StoredInt) env.makeInt(b);
	}

	protected boolean _removeVal(final int x) throws ContradictionException {
		final int infv = this.getInf(), supv = this.getSup();
		if (x == infv) {
			this._updateInf(x + 1);
			if (this.getInf() == supv) {
				this._instantiate(supv);
			}
			return true;
		}
		else if (x == supv) {
			this._updateSup(x - 1);
			if (this.getSup() == infv) {
				this._instantiate(infv);
			}
			return true;
		}
		else {
			return false;
		}
	}

	public boolean contains(final int x) {
		return x >= this.getInf() && x <= this.getSup();
	}

	public IntIterator getDeltaIterator() {
		return null;
	}

	public int getInf() {
		return this.inf.get();
	}

	public IntIterator getIterator() {
		return new AbstractIntDomain.IntDomainIterator(this);
	}

	public int getNextValue(final int x) {
		if (x < this.getInf()) {
			return this.getInf();
		}
		else if (x < this.getSup()) {
			return x + 1;
		}
		else {
			return Integer.MAX_VALUE;
		}
	}

	public int getPrevValue(final int x) {
		if (x > this.getSup()) {
			return this.getSup();
		}
		else if (x > this.getInf()) {
			return x - 1;
		}
		else {
			return Integer.MIN_VALUE;
		}
	}

	public int getRandomValue() {
		// TODO
		return 0;
	}

	public int getSize() {
		return this.getSup() - this.getInf() + 1;
	}

	public int getSup() {
		return this.sup.get();
	}

	public boolean hasNextValue(final int x) {
		return x < this.getSup();
	}

	public boolean hasPrevValue(final int x) {
		return x > this.getInf();
	}

	public boolean isEnumerated() {
		return false;
	}

	public String pretty() {
		return "[" + this.getInf() + ", " + this.getSup() + "]";
	}

	public boolean remove(final int x) {
		return false;
	}

	public void restrict(final int x) {
		this.inf.set(x);
		this.sup.set(x);
	}

	public int updateInf(final int x) {
		this.inf.set(x);
		return x;
	}

	public int updateSup(final int x) {
		this.sup.set(x);
		return x;
	}

}
