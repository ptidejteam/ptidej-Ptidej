/*
 * $Id: Variable.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */
package fr.emn.oadymppac;

import java.util.Arrays;
import java.util.Vector;

/**
 * The <code>Variable</code> class implements the semantics of a
 * variable with a finite domain.  It also provides methods for
 * managing the narrowing and widening of domains that occur during
 * solving and backtrack.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */
public class Variable implements Cloneable, Identifiable {
	/**
	 * The event number that created the variable.
	 */
	int n;
	/**
	 * The name of the variable.
	 */
	String vname;

	/**
	 * An array of ints holding the values in the domain of the variable.
	 */
	int[] values;
	/**
	 * Contains one set of withdrawals made on the domain of the variable.
	 * at a time
	 */
	CountingBitSet withdrawals;

	/**
	 * Contains successive withdrawals made on the domain of the variable.
	 */
	Vector stack;

	/**
	 * The external representation.
	 */
	String externalRep;

	/**
	 * The internal representation
	 */
	String internalRep;

	/**
	 * Unique number associated with this variable.
	 */
	int id;

	/**
	 * Generator of static ids.
	 */
	static int next_id;

	/**
	 * Default constructor.
	 */
	public Variable() {
		this.n = 0;
		this.vname = null;
		this.values = null;
		this.withdrawals = null;
		this.id = Variable.next_id++;
	}

	/**
	 * This constructor sets the name and the domain of the variable.
	 */
	public Variable(
		final int n,
		final String vname,
		final ValueList d,
		final String externalRep,
		final String internalRep) {
		this.n = n;
		this.vname = vname;
		this.externalRep = externalRep;
		this.internalRep = internalRep;
		this.setValueList(d);
		this.id = Variable.next_id++;
	}

	/**
	 * This constructor sets the name and the domain of the variable.
	 */
	public Variable(final String vname, final ValueList d) {
		this(0, vname, d, null, null);
	}

	/**
	 * Compatibility for older version.
	 * @deprecated should provide the event number now.
	 */
	public Variable(
		final String vname,
		final ValueList d,
		final String externalRep,
		final String internalRep) {
		this(0, vname, d, externalRep, internalRep);
	}

	/**
	 * Copy constructor.
	 */
	public Variable(final Variable other) {
		this.vname = other.vname;
		try {
			this.values = (int[]) other.clone();
		}
		catch (final CloneNotSupportedException e) {
			throw new InternalError();
		}
		this.withdrawals = new CountingBitSet(other.withdrawals);
		this.id = other.id; // if it has the same name, it should be the same
	}

	/**
	 * Adds a range of values to the domain of the variable starting at
	 * <code>begin</code> and ending with <code>end</code>.
	 */
	public void addRange(final int begin, final int end) {
		final int bindex = this.indexOf(begin);
		final int eindex = bindex + end - begin;
		for (int i = bindex; i <= eindex; i++) {
			this.addValueAt(i);
		}
	}

	/**
	 * Adds the value v to the domain of the variable.
	 */
	public void addValue(final int v) {
		this.withdrawals.clear(this.indexOf(v));
	}

	/**
	 * Adds the value v to the domain of the variable at the given index.
	 */
	public void addValueAt(final int index) {
		this.withdrawals.clear(index);
	}

	/**
	 * Return the size of the current domain
	 */
	public int domainSize() {
		return this.getValueCount() - this.getWithdrawals().getTrueCount();
	}

	/**
	 * Get the value of externalRep.
	 * @return value of externalRep.
	 */
	public String getExternalRep() {
		return this.externalRep;
	}

	/**
	* Get the value of id.
	* @return value of id.
	*/
	public int getId() {
		return this.id;
	}

	/**
	 * Get the value of internalRep.
	 * @return value of internalRep.
	 */
	public String getInternalRep() {
		return this.internalRep;
	}

	/**
	 * Return the index of the maximum value not withdrawan
	 */
	public int getMax() {
		for (int i = this.values.length - 1; i >= 0; i--) {
			if (!this.isWithdrawnValueAt(i)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Return the index of the minimum value not withdrawan
	 */
	public int getMin() {
		for (int i = 0; i < this.values.length; i++) {
			if (!this.isWithdrawnValueAt(i)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Returns the n.
	 * @return int
	 */
	public int getN() {
		return this.n;
	}

	/**
	 * Returns the "best" name, either the externalRep or the VName
	 */
	public String getName() {
		if (this.externalRep != null) {
			return this.externalRep;
		}
		else {
			return this.vname;
		}
	}

	/**
	 * Returns the value of the variable at index.
	 */
	public int getValueAt(final int index) {
		return this.values[index];
	}

	/**
	 * Returns the number of values.
	 */
	public int getValueCount() {
		return this.values.length;
	}

	/**
	 * Returns the list of values of the variable.
	 */
	public int[] getValues() {
		return this.values;
	}

	/**
	 * Returns the name of the variable.
	 */
	public String getVName() {
		return this.vname;
	}

	/**
	 * Returns the list of withdrawals made on the domain of the variable.
	 */
	public CountingBitSet getWithdrawals() {
		return this.withdrawals;
	}

	/**
	 * Returns the index of v in the value list of the variable.
	 */
	public int indexOf(final int v) {
		return Arrays.binarySearch(this.values, v);
	}
	/**
	 * Check whether the value is withdrawn
	 */
	public boolean isWithdrawnValue(final int v) {
		return this.withdrawals.get(this.indexOf(v));
	}

	/**
	 * Check whether the value at given index is withdrawn
	 */
	public boolean isWithdrawnValueAt(final int index) {
		return this.withdrawals.get(index);
	}

	/**
	 * Removes a list of withdrawals from the stack of the variable.
	 */
	public void pop() {
		this.withdrawals = (CountingBitSet) this.stack.lastElement();
		this.stack.removeElementAt(this.stack.size() - 1);
	}
	/**
	 * Adds a list of withdrawals to the stack of the variable.
	 */
	public void push() {
		if (this.stack == null) {
			this.stack = new Vector();
		}
		this.stack.addElement(this.withdrawals.clone());
	}
	/**
	 * Restores a list of values from the value list of the variable.
	 */
	public void restore(final ValueList vl) {
		for (int i = 0; i < vl.getLength(); i++) {
			if (vl.isRange(i)) {
				this.addRange(vl.getValue(i), vl.getValue(i + 1));
				i++;
			}
			else {
				this.addValue(vl.getValue(i));
			}
		}
	}
	/**
	 * Set the value of externalRep.
	 * @param v  Value to assign to externalRep.
	 */
	public void setExternalRep(final String v) {
		this.externalRep = v;
	}

	/**
	 * Set the value of internalRep.
	 * @param v  Value to assign to internalRep.
	 */
	public void setInternalRep(final String v) {
		this.internalRep = v;
	}

	/**
	 * Sets the possible values taken by the variable.
	 */
	public void setValueList(final ValueList d) {
		final int count = d.domainCount();
		this.values = new int[count];
		int cnt = 0;
		for (int i = 0; i < d.getLength(); i++) {
			// if the current value is the lower bound of a range
			// we add all the values falling in the range
			if (d.isRange(i)) {
				for (int j = d.getValue(i); j <= d.getValue(i + 1); j++) {
					this.values[cnt++] = j;
				}
				i++; // skip for getValue(i+1);
			}
			else {
				// if the value is enumerated we just add it
				this.values[cnt++] = d.getValue(i);
			}
		}
		this.withdrawals = new CountingBitSet();
		//withdrawals = new CountingBitSet(count);
	}

	/**
	 * Sets the list of values the variable can take.
	 */
	public void setValues(final int[] v) {
		this.values = v;
	}

	/**
	 * Sets the name of the variable.
	 */
	public void setVName(final String name) {
		this.vname = name;
	}

	/**
	 * Sets the list of withdrawals.
	 */
	public void setWithdrawals(final CountingBitSet w) {
		this.withdrawals = w;
	}

	/**
	 * Withdraws a list of values from the value list of the variable.
	 */
	public void withdraw(final ValueList vl) {
		for (int i = 0; i < vl.getLength(); i++) {
			if (vl.isRange(i)) {
				this.withdrawRange(vl.getValue(i), vl.getValue(i + 1));
				i++;
			}
			else {
				this.withdrawValue(vl.getValue(i));
			}
		}
	}

	/**
	 * Removes a range of values from the domain of the variable starting at
	 * <code>begin</code> and ending with <code>end</code>.
	 */
	public void withdrawRange(final int begin, final int end) {
		final int bindex = this.indexOf(begin);
		final int eindex = bindex + end - begin;
		for (int i = bindex; i <= eindex; i++) {
			this.withdrawValueAt(i);
		}
	}

	/**
	 * Removes the value v from the domain of the variable.
	 */
	public void withdrawValue(final int v) {
		try {
			this.withdrawals.set(this.indexOf(v));
		}
		catch (final IndexOutOfBoundsException e) {
			String s = "\nvariable " + this.vname + " {";
			for (int i = 0; i < this.getValueCount(); i++) {
				s += this.getValueAt(i) + " ";
			}
			s += "}\n";
			s +=
				"value " + v + " cannot be withdrawn : no such value in domain";
			throw new InternalError(s);
		}
	}

	/**
	 * Removes the value v from the domain of the variable at the given index.
	 */
	public void withdrawValueAt(final int index) {
		this.withdrawals.set(index);
	}

}
