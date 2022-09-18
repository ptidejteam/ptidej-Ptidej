/*
 * $Id: HistoryManager.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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

import java.util.Iterator;
import java.util.Vector;
import fr.emn.oadymppac.event.BasicSolverEvent;
import fr.emn.oadymppac.event.EventStackChangedEvent;
import fr.emn.oadymppac.event.EventStackChangedListener;
import fr.emn.oadymppac.helpers.SolverManager;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version $Revision: 1.1 $
 */

public class HistoryManager extends SolverManager {

	/**
	 * Stores the events.
	 */
	Vector stack;

	/**
	 * Keeps a list of (visualization) components interested in event
	 * stack changes
	 */
	Vector listenerList = new Vector();

	Solver solver;

	public static final String PROPERTY_NAME = "historyManager";

	public static HistoryManager getHistoryManager(final Solver solver) {
		final Object o = solver.getClientProperty(HistoryManager.PROPERTY_NAME);
		if (o != null) {
			return (HistoryManager) o;
		}
		final HistoryManager mgr = new HistoryManager();
		mgr.register(solver);
		solver.putClientProperty(HistoryManager.PROPERTY_NAME, mgr);
		return mgr;
	}

	protected HistoryManager() {
		this.stack = new Vector(); // it may be usefull to set the
		// capacity increment to a large number by default
	}

	public void addEventStackChangedListener(final EventStackChangedListener l) {
		this.listenerList.addElement(l);
	}

	/**
	 * Handles <code>BasicSolverEvent</code>s.
	 */
	public void basic(BasicSolverEvent event) {
		event = event.deepClone();
		this.push(event);
		this.fireStackChangedListener(event);
	}

	public void fireStackChangedListener(final BasicSolverEvent e) {
		if (this.listenerList.size() == 0) {
			//System.err.println("no listener to call");
			return;
		}
		final EventStackChangedEvent ev = new EventStackChangedEvent(e);
		for (int i = 0; i < this.listenerList.size(); i++) {
			final EventStackChangedListener l =
				(EventStackChangedListener) this.listenerList.elementAt(i);
			l.eventStackChanged(ev);
		}
	}

	public int getLength() {
		return this.stack.size();
	}

	public Solver getSolver() {
		return this.solver;
	}

	public Iterator iterator() {
		return this.stack.iterator();
	}

	public Iterator iterator(final int start) {
		return this.stack.listIterator(start);
	}

	public Iterator iterator(final int start, final int end) {
		return this.stack.subList(start, end).iterator();
	}

	public synchronized void pop() {
		this.stack.removeElementAt(this.stack.size() - 1);
	}

	public synchronized void push(final BasicSolverEvent ev) {
		this.stack.addElement(ev);
	}

	public void register(final Solver solver) {
		this.solver = solver;
		super.register(solver);
	}
	public void removeStackChangedListener(final EventStackChangedListener l) {
		for (int i = 0; i < this.listenerList.size(); i++) {
			if (this.listenerList.elementAt(i) == l) {
				this.listenerList.removeElementAt(i);
			}
		}
	}
}
