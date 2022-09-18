/*
 * $Id: ConstraintManager.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Vector;
import fr.emn.oadymppac.event.ActivateEvent;
import fr.emn.oadymppac.event.ActivateListener;
import fr.emn.oadymppac.event.BasicSolverEvent;
import fr.emn.oadymppac.event.ConstraintChangedEvent;
import fr.emn.oadymppac.event.ConstraintChangedListener;
import fr.emn.oadymppac.event.DeactivateEvent;
import fr.emn.oadymppac.event.DeactivateListener;
import fr.emn.oadymppac.event.NewConstraintEvent;
import fr.emn.oadymppac.event.NewConstraintListener;
import fr.emn.oadymppac.event.RestoreEvent;
import fr.emn.oadymppac.event.RestoreListener;
import fr.emn.oadymppac.event.TellEvent;
import fr.emn.oadymppac.event.TellListener;

/**
 * A <code>ConstraintManager</code> is a utility class that manages the
 * constraints and their related variables declared and used during a
 * session.  It keeps track of their withdrawals and notifies
 * listeners when they appear or disappear.
 *
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.1 $
 */
public class ConstraintManager extends ObjectManager implements
		NewConstraintListener, TellListener, RestoreListener, ActivateListener,
		DeactivateListener {
	public static ConstraintManager getConstraintManager(final Solver solver) {
		final Object o =
			solver.getClientProperty(ConstraintManager.PROPERTY_NAME);
		if (o != null) {
			return (ConstraintManager) o;
		}
		final ConstraintManager mgr = new ConstraintManager();
		mgr.register(solver);
		solver.putClientProperty(ConstraintManager.PROPERTY_NAME, mgr);
		return mgr;
	}

	/**
	 * If true, output messages.
	 */
	boolean verbose;

	/**
	 * Keeps a HashMap of all the constraints involved in
	 * the resolution.
	 */
	HashMap constraints = new HashMap();

	/**
	 * Also keep the constraints in the order they have been defined.
	 */
	Vector constraintList = new Vector();

	/**
	 * Keeps the list of constraints created from the previous depth level.
	 */
	Vector created = new Vector();

	/**
	 * Keeps track of the current depth.
	 */
	int depth;

	/**
	 * Keeps record of the successive constraint creations
	 */
	Vector stack = new Vector();
	public static final String PROPERTY_NAME = "constraintManager";

	/**
	 * Protected constructor
	 */
	protected ConstraintManager() {
	}

	public synchronized void activate(final ActivateEvent event) {
		final Constraint c = this.getConstraint(event.getCName());
		if (c == null) {
			return;
		}
		c.setActive(true);
		this.fireConstraintChangedListener(
			c,
			ConstraintChangedEvent.ACTIVATED,
			event);
	}

	public void addConstraintChangedListener(final ConstraintChangedListener l) {
		this.listenerList.addElement(l);
	}

	public synchronized void deactivate(final DeactivateEvent event) {
		final Constraint c = this.getConstraint(event.getCName());
		c.setActive(false);
		this.fireConstraintChangedListener(
			c,
			ConstraintChangedEvent.DEACTIVATED,
			event);
	}

	public void fireConstraintChangedListener(
		final Constraint c,
		final short action,
		final BasicSolverEvent e) {
		if (this.listenerList.size() == 0) {
			return;
		}
		final ConstraintChangedEvent ev =
			new ConstraintChangedEvent(c, action, e);
		for (int i = 0; i < this.listenerList.size(); i++) {
			final ConstraintChangedListener l =
				(ConstraintChangedListener) this.listenerList.elementAt(i);
			l.constraintChanged(ev);
		}
	}

	/**
	 * Get the constraint by its name.
	 * @return the Constraint
	 */
	public Constraint getConstraint(final String cname) {
		return (Constraint) this.constraints.get(cname);
	}

	/**
	 * Get the constraint by its creation order.
	 * @return the Constraint
	 */
	public Constraint getConstraintAt(final int index) {
		return (Constraint) this.constraintList.elementAt(index);
	}

	public int getConstraintCount() {
		return this.constraintList.size();
	}

	/**
	 * Returns the verbose.
	 * @return boolean
	 */
	public boolean isVerbose() {
		return this.verbose;
	}

	/**
	 * Add a new constraint.
	 */
	public void newConstraint(final NewConstraintEvent event) {
		final Constraint c =
			new Constraint(
				event.getN(),
				event.getCName(),
				event.isUser(),
				event.getUpdateList(),
				event.getExternalRep(),
				event.getInternalRep());
		this.constraints.put(c.getCName(), c);
		this.constraintList.add(c);
		this.created.add(c);
		this.fireConstraintChangedListener(
			c,
			ConstraintChangedEvent.ADDED,
			event);
	}

	/**
	 * Removes the latest list of created constraints.
	 */
	public void pop(final BasicSolverEvent event) {
		if (this.verbose) {
			System.err.println("Popping constraints");
		}
		final int len = this.created.size();
		for (int i = len - 1; i >= 0; i--) {
			final Constraint c = (Constraint) this.created.elementAt(i);
			if (this.verbose) {
				System.err.println("Removing " + c.getCName());
			}
			//constraints.remove(c.getCName());
			// perform deletion in the reverse order
			//constraintList.removeElementAt(constraintList.size() - 1);
			this.fireConstraintChangedListener(
				c,
				ConstraintChangedEvent.REMOVED,
				event);
		}
		try {
			this.created = (Vector) this.stack.lastElement();
			this.stack.removeElementAt(this.stack.size() - 1);
		}
		catch (final NoSuchElementException e) {
			System.out.println("Constraint stack is empty");
		}
		if (this.verbose) {
			System.out.println("stack size=" + this.stack.size() + " depth="
					+ this.depth);
		}
	}

	/**
	 * Adds the list of created constraints to the stack.
	 */
	public void push() {
		this.stack.addElement(this.created);
		this.created = new Vector();
		if (this.verbose) {
			System.out.println("stack size=" + this.stack.size() + " depth="
					+ this.depth);
		}
	}

	/**
	 * Registers the current instance of <code>ConstraintManager</code> to the
	 * given <code>Solver</code>.
	 */
	public void register(final Solver solver) {
		this.solver = solver;
		solver.addNewConstraintListener(this, Solver.FILTER_NONE);
		solver.addRestoreListener(this, Solver.FILTER_NONE);
		solver.addTellListener(this, Solver.FILTER_NONE);
		solver.addActivateListener(this, Solver.FILTER_NONE);
		solver.addDeactivateListener(this, Solver.FILTER_NONE);
	}

	public void removeConstraintChangedListener(
		final ConstraintChangedListener l) {
		for (int i = 0; i < this.listenerList.size(); i++) {
			if (this.listenerList.elementAt(i) == l) {
				this.listenerList.removeElementAt(i);
			}
		}
	}
	/**
	 * Removes the list of constraints created from the previous tell.
	 */
	public synchronized void restore(final RestoreEvent event) {
		if (this.depth > event.getDepth()) {
			this.depth = event.getDepth();
			this.pop(event);
		}
	}
	/**
	 * Sets the verbose.
	 * @param verbose The verbose to set
	 */
	public void setVerbose(final boolean verbose) {
		this.verbose = verbose;
	}

	/**
	 * Sets a choice point. // point de choix ???
	 */
	public synchronized void tell(final TellEvent event) {
		if (this.depth < event.getDepth()) {
			this.depth = event.getDepth();
			this.push();
		}
	}

}