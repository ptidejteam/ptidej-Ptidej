/*
 * $Id: TimeManager.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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

import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.Vector;
import fr.emn.oadymppac.event.TimeEvent;
import fr.emn.oadymppac.event.TimeListener;
import fr.emn.oadymppac.widgets.TMRangeSlider;

public class TimeManager implements AdjustmentListener {

	/**
	 * This method returns the unique <code>TimeManager</code> of the framework
	 * if it exists or creates it for the first time.
	 * @param solver
	 * @return the the unique <code>TimeManager</code> of the framework
	 */
	public static TimeManager getTimeManager(final Solver solver) {
		final Object o = solver.getClientProperty(TimeManager.PROPERTY_NAME);
		if (o != null) {
			return (TimeManager) o;
		}
		final TimeManager mgr = new TimeManager();
		mgr.register(solver);
		solver.putClientProperty(TimeManager.PROPERTY_NAME, mgr);
		return mgr;
	}

	/**
	 * This holds the visual component that controls the <code>TimeManager</code>
	 * such as a <code>JScrollBar</code>.
	 */
	Adjustable handler;

	//BoundedRangeModel model;
	int min, max, leftValue, rightValue;

	Vector listenerList;

	Solver solver;

	public static final String PROPERTY_NAME = "timeManager";

	protected TimeManager() {
		this.listenerList = new Vector();
		this.min = 0;
		this.max = 0;
		this.leftValue = 0;
		this.rightValue = 0;
	}

	/**
	 * This method adds a <code>Timelistener</code> to the <code>listenerList</code>
	 * @param l - adds the given <code>Timelistener</code>
	 */
	public void addTimeListener(final TimeListener l) {
		//        model.addChangeListener(l);
		this.listenerList.addElement(l);
	}

	public void adjustmentValueChanged(final AdjustmentEvent e) {
		this.leftValue = ((TMRangeSlider) e.getAdjustable()).getLeftValue();
		this.rightValue = ((TMRangeSlider) e.getAdjustable()).getRightValue();
		this.max = ((TMRangeSlider) e.getAdjustable()).getMaximum();
		this.min = ((TMRangeSlider) e.getAdjustable()).getMinimum();
		this.fireTimeListener(e);
	}

	/**
	 * This method fires a TimeEvent to the listeners when time changes.
	 */
	public void fireTimeListener(final AdjustmentEvent e) {
		if (this.listenerList.size() == 0) {
			//System.err.println("no listener to call");
			return;
		}
		final TimeEvent ev = new TimeEvent(this);
		for (int i = 0; i < this.listenerList.size(); i++) {
			final TimeListener l =
				(TimeListener) this.listenerList.elementAt(i);
			l.timeChanged(ev);
		}
	}

	public Adjustable getHandler() {
		return this.handler;
	}

	public int getLeftValue() {
		return this.leftValue;
	}

	public int getMax() {
		return this.max;
	}

	public int getMin() {
		return this.min;
	}

	public int getRightValue() {
		return this.rightValue;
	}

	/**
	 * This method registers the solver.
	 * @param solver
	 */
	public void register(final Solver solver) {
		this.solver = solver;
	}

	/**
	 * This method removes a <code>Timelistener</code> from the <code>listenerList</code>
	 * @param l - removes the given <code>Timelistener</code>
	 */
	public void removeTimeListener(final TimeListener l) {
		//        model.removeChangeListener(l);
		for (int i = 0; i < this.listenerList.size(); i++) {
			if (this.listenerList.elementAt(i) == l) {
				this.listenerList.removeElementAt(i);
			}
		}
	}

	public void setHandler(final Adjustable a) {
		this.handler = a;
		a.addAdjustmentListener(this);
	}

	public void setLeftValue(final int v) {
		this.leftValue = v;
	}

	/**
	 * The maximum time bound is set to <code>max</code>.
	 * @param max
	 */
	public void setMax(final int max) {
		this.max = max;
	}

	/**
	 * The minimum time bound is set to <code>min</code>.
	 * @param min
	 */
	public void setMin(final int min) {
		this.min = min;
	}
	public void setRightValue(final int v) {
		this.rightValue = v;
	}
}