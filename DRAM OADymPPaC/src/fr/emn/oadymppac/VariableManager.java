/*
 * $Id: VariableManager.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Vector;
import fr.emn.oadymppac.event.BasicSolverEvent;
import fr.emn.oadymppac.event.DomainChangedEvent;
import fr.emn.oadymppac.event.DomainChangedListener;
import fr.emn.oadymppac.event.NewVariableEvent;
import fr.emn.oadymppac.event.NewVariableListener;
import fr.emn.oadymppac.event.ReduceEvent;
import fr.emn.oadymppac.event.ReduceListener;
import fr.emn.oadymppac.event.RestoreEvent;
import fr.emn.oadymppac.event.RestoreListener;
import fr.emn.oadymppac.event.TellEvent;
import fr.emn.oadymppac.event.TellListener;

/**
 * A <code>VariableManager</code> is a utility class that manages the
 * domain of all the variables declared and used during a session.  It
 * keeps track of the domains and notifies listeners when the domains
 * change.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */
public class VariableManager extends ObjectManager implements
		NewVariableListener, ReduceListener, RestoreListener, TellListener {
	public static VariableManager getVariableManager(final Solver solver) {
		final Object o =
			solver.getClientProperty(VariableManager.PROPERTY_NAME);
		if (o != null) {
			return (VariableManager) o;
		}
		final VariableManager mgr = new VariableManager();
		mgr.register(solver);
		solver.putClientProperty(VariableManager.PROPERTY_NAME, mgr);
		return mgr;
	}

	/**
	 * Keeps a HashMap of all the (user defined ??) variables involved in
	 * the resolution.
	 */
	HashMap variables;

	/**
	 * Also keep the variables in the order they have been defined.
	 */
	Vector varList;

	/**
	 * Keeps a list of updated variables.
	 */
	HashSet updated;

	/**
	 * Keeps track of the current depth of the solver.
	 */
	int depth;

	/**
	 * Keeps record of the successive domain changes made on the variables.
	 */
	Vector stack;
	public static final String PROPERTY_NAME = "variableManager";

	/**
	 * protected constructor.
	 */
	protected VariableManager() {
		this.variables = new HashMap();
		this.varList = new Vector();
		this.updated = new HashSet();
		this.stack = new Vector();
		this.depth = 0;
		this.solver = null;
	}

	public void addDomainChangedListener(final DomainChangedListener l) {
		this.listenerList.addElement(l);
	}

	public void fireDomainChangedListener(
		final Variable v,
		final BasicSolverEvent e) {
		if (this.listenerList.size() == 0) {
			//System.err.println("no listener to call");
			return;
		}
		final DomainChangedEvent ev = new DomainChangedEvent(v, e);
		for (int i = 0; i < this.listenerList.size(); i++) {
			final DomainChangedListener l =
				(DomainChangedListener) this.listenerList.elementAt(i);
			l.domainChanged(ev);
		}
	}

	/**
	 * Get the value of depth.
	 * @return value of depth.
	 */
	public int getDepth() {
		return this.depth;
	}

	/**
	 * Returns the variable whose name is <code>vname</code>.
	 */
	public Variable getVariable(final String vname) {
		return (Variable) this.variables.get(vname);
	}

	/**
	 * Returns the <code>index</code>'s created variable.
	 */
	public Variable getVariableAt(final int index) {
		return (Variable) this.varList.elementAt(index);
	}

	/**
	 * Return the number of variables in the manager.
	 */
	public int getVariableCount() {
		return this.varList.size();
	}

	/**
	 * Return the iterator on all the variables.
	 */
	public Iterator iterator() {
		return this.varList.iterator();
	}

	/**
	 * When a <code>NewVariableEvent</code> is received, the corresponding
	 * variable is added to the list of variables.
	 */
	public synchronized void newVariable(final NewVariableEvent event) {
		final Variable var =
			new Variable(
				event.getN(),
				event.getVName(),
				event.getValueList(),
				event.getExternalRep(),
				event.getInternalRep());

		this.variables.put(var.getVName(), var);
		this.varList.add(var);
		this.fireDomainChangedListener(var, event);
	}

	/**
	 * Removes the latest list of updated variables from the stack.
	 */
	public void pop(final BasicSolverEvent event) {
		final Iterator i = this.updated.iterator();
		while (i.hasNext()) {
			final Object vname = i.next();
			final Variable v = (Variable) this.variables.get(vname);
			v.pop();
			this.fireDomainChangedListener(v, event);
		}
		try {
			this.updated = (HashSet) this.stack.lastElement();
			this.stack.removeElementAt(this.stack.size() - 1);
		}
		catch (final NoSuchElementException e) {
			System.out.println("Variable stack is empty");
		}
	}

	/**
	 * Adds a list of updated variables to the stack.
	 */
	public void push() {
		this.stack.addElement(this.updated);
		this.updated = new HashSet();
	}

	/**
	 * When a <code>ReduceEvent</code> is received, the corresponding
	 * values are withdrawn from the domain of the given variable.
	 * N.B : Sometimes, reduce events change the type of the variable (min, max, etc.)
	 * without making any withdrawals in the domain.
	 */
	public synchronized void reduce(final ReduceEvent event) {
		final UpdateList ul = event.getUpdateList();
		if (ul != null) {
			switch (event.getEventType()) {
				case SolverConstants.TYPE_VAL :
					for (int i = 0; i < ul.getLength(); i++) {
						final Update u = ul.getUpdate(i);
						this.withdraw(u.getVName(), u.getValueList(), event);
					}
					;
					break;
				case SolverConstants.TYPE_MIN :
					;
					break;
				case SolverConstants.TYPE_MAX :
					;
					break;
				case SolverConstants.TYPE_MINMAX :
					;
					break;
				case SolverConstants.TYPE_ANY :
					;
					break;
				case SolverConstants.TYPE_EMPTY :
					;
					break;
				case SolverConstants.TYPE_GROUND :
					;
					break;
				case SolverConstants.TYPE_NONE :
					;
					break;
				case SolverConstants.TYPE_NOTHING :
					;
					break;
			}
		}
	}

	/**
	 * Registers the current instance of <code>VariableManager</code> to the
	 * given <code>Solver</code>. Thus, the <code>VariableManager</code> is
	 * subscribed to relevant events of the solver such as the creation of a
	 * new variable, the reduction and restoration of the domain of any variable
	 * and tell events.
	 */
	public void register(final Solver solver) {
		this.solver = solver;

		solver.addNewVariableListener(this, Solver.FILTER_NONE);
		solver.addReduceListener(this, Solver.FILTER_NONE);
		solver.addRestoreListener(this, Solver.FILTER_NONE);
		solver.addTellListener(this, Solver.FILTER_NONE);
	}

	public void removeDomainChangedListener(final DomainChangedListener l) {
		for (int i = 0; i < this.listenerList.size(); i++) {
			if (this.listenerList.elementAt(i) == l) {
				this.listenerList.removeElementAt(i);
			}
		}
	}

	/**
	 * When a <code>RestoreEvent</code> is received, the corresponding
	 * values are restored into the domain of the given variable.
	 */
	public synchronized void restore(final RestoreEvent event) {
		if (this.depth > event.getDepth()) {
			this.depth = event.getDepth();
			//System.err.println("vm depth="+depth);
			this.pop(event);
		}
		else {
			// no need to restore each variable when all are updated by pop
			final UpdateList ul = event.getUpdateList();
			if (ul != null) {
				for (int i = 0; i < ul.getLength(); i++) {
					final Update u = ul.getUpdate(i);
					this.restore(u.getVName(), u.getValueList(), event);
				}
			}
		}
	}

	/**
	 * Restores a list of values into the domain of the variable whose name is
	 * <code>vname</code>.
	 */
	public void restore(
		final String vname,
		final ValueList vl,
		final RestoreEvent event) {
		final Variable v = this.getVariable(vname);
		//if (v==null) throw new VariableNotFoundException();
		this.updated.add(vname);
		v.restore(vl);
		this.fireDomainChangedListener(v, event);
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
	/**
	 * Withdraws a list of values from the domain of the variable whose name is
	 * <code>vname</code>.
	 */
	public void withdraw(
		final String vname,
		final ValueList vl,
		final ReduceEvent event) {
		final Variable v = this.getVariable(vname);
		if (this.updated.add(vname)) {
			v.push();
		}
		if (vl != null) {
			v.withdraw(vl);
			this.fireDomainChangedListener(v, event);
		}
	}
}
