/*
 * $Id: LabeledVariableEvent.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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
package fr.emn.oadymppac.event;

import fr.emn.oadymppac.Variable;

/**
 */

public class LabeledVariableEvent extends BasicSolverEvent {
	public static final short ADDED = 0;
	public static final short REMOVED = 1;

	Variable variable;
	short action;
	BasicSolverEvent event;

	public LabeledVariableEvent() {
	}
	public LabeledVariableEvent(
		final LabeledVariableEvent other,
		final boolean deep) {
		super(other, deep);
		this.variable = other.variable;
		this.action = other.action;
		this.event = other.event;
	}
	public LabeledVariableEvent(
		final Variable var,
		final short action,
		final BasicSolverEvent event) {
		super(event, false);
		this.variable = var;
		this.action = action;
		this.event = event;
	}

	public BasicSolverEvent deepClone() {
		return new LabeledVariableEvent(this, true);
	}

	/**
	 * Get the value of action.
	 * @return value of action.
	 */
	public short getAction() {
		return this.action;
	}

	/**
	 * Get the value of event.
	 * @return value of event.
	 */
	public BasicSolverEvent getEvent() {
		return this.event;
	}

	/**
	 * Get the value of variable.
	 * @return value of variable.
	 */
	public Variable getVariable() {
		return this.variable;
	}

	/**
	 * Set the value of action.
	 * @param v  Value to assign to action.
	 */
	public void setAction(final short v) {
		this.action = v;
	}

	/**
	 * Set the value of event.
	 * @param v  Value to assign to event.
	 */
	public void setEvent(final BasicSolverEvent v) {
		this.event = v;
	}

	/**
	 * Set the value of variable.
	 * @param v  Value to assign to variable.
	 */
	public void setVariable(final Variable v) {
		this.variable = v;
	}
}
