/*
 * $Id: LabelingManager.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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

import java.util.HashSet;
import java.util.Vector;
import fr.emn.oadymppac.event.BasicSolverEvent;
import fr.emn.oadymppac.event.DomainChangedEvent;
import fr.emn.oadymppac.event.DomainChangedListener;
import fr.emn.oadymppac.event.LabeledVariableEvent;
import fr.emn.oadymppac.event.LabeledVariableListener;
import fr.emn.oadymppac.event.ReduceEvent;
import fr.emn.oadymppac.event.RestoreEvent;

/**
 * A <code>LabelingManager</code> is a utility class that tracks
 * variables when their values are enumerated in a labeling stage.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */

public class LabelingManager extends ObjectManager implements
		DomainChangedListener {
	VariableManager variableManager;

	Vector labeledVariables = new Vector();
	HashSet variables = new HashSet();

	public LabelingManager(final VariableManager var) {
		this.setVariableManager(var);
	}

	public void addLabeledVariableListener(final LabeledVariableListener l) {
		this.listenerList.add(l);
	}

	protected synchronized void addVariable(
		final Variable v,
		final BasicSolverEvent ev) {
		if (this.variables.add(v)) {
			this.labeledVariables.add(v);
			this.fireLabeledVariable(v, LabeledVariableEvent.ADDED, ev);
		}
	}

	public void domainChanged(final DomainChangedEvent ev) {
		final Variable v = ev.getVariable();
		if (ev.getEvent() instanceof ReduceEvent && v.domainSize() == 1) {
			// Labeling only triggered by reduce events
			this.addVariable(v, ev.getEvent());
		}
		else if (ev.getEvent() instanceof RestoreEvent
				&& this.isVariableLabeled(v) && v.domainSize() != 1) {
			this.removeVariable(v, ev.getEvent());
		}
	}

	protected void fireLabeledVariable(
		final Variable v,
		final short action,
		final BasicSolverEvent ev) {
		if (!this.listenerList.isEmpty()) {
			final LabeledVariableEvent nev =
				new LabeledVariableEvent(v, action, ev);

			for (int i = 0; i < this.listenerList.size(); i++) {
				final LabeledVariableListener l =
					(LabeledVariableListener) this.listenerList.elementAt(i);
				l.labeledVariableChanged(nev);
			}
		}
	}

	/**
	 * Get the solver.
	 * @return solver.
	 */
	public Solver getSolver() {
		return this.variableManager.getSolver();
	}

	public Variable getVariableAt(final int index) {
		return (Variable) this.labeledVariables.elementAt(index);
	}

	/**
	 * Get the value of variableManager.
	 * @return value of variableManager.
	 */
	public VariableManager getVariableManager() {
		return this.variableManager;
	}

	public int indexOf(final Variable v) {
		return this.labeledVariables.indexOf(v);
	}

	public boolean isVariableLabeled(final Variable v) {
		return this.variables.contains(v);
	}

	public int labeledVariableCount() {
		return this.labeledVariables.size();
	}

	public void removeLabeledVariableListener(final LabeledVariableListener l) {
		this.listenerList.removeElement(l);
	}

	protected synchronized void removeVariable(
		final Variable v,
		final BasicSolverEvent ev) {
		if (this.variables.remove(v)) {
			this.labeledVariables.remove(v);
			this.fireLabeledVariable(v, LabeledVariableEvent.REMOVED, ev);
		}
	}

	/**
	 * Set the value of variableManager.
	 * @param v  Value to assign to variableManager.
	 */
	public void setVariableManager(final VariableManager v) {
		if (this.variableManager != null) {
			this.variableManager.removeDomainChangedListener(this);
		}
		this.variableManager = v;
		if (this.variableManager != null) {
			this.variableManager.addDomainChangedListener(this);
		}
	}
}
