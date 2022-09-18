/*
 * $Id: AbstractSolver.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Vector;
import fr.emn.oadymppac.event.ActivateEvent;
import fr.emn.oadymppac.event.ActivateListener;
import fr.emn.oadymppac.event.BasicSolverEvent;
import fr.emn.oadymppac.event.DeactivateEvent;
import fr.emn.oadymppac.event.DeactivateListener;
import fr.emn.oadymppac.event.NewConstraintEvent;
import fr.emn.oadymppac.event.NewConstraintListener;
import fr.emn.oadymppac.event.NewStageEvent;
import fr.emn.oadymppac.event.NewVariableEvent;
import fr.emn.oadymppac.event.NewVariableListener;
import fr.emn.oadymppac.event.ReduceEvent;
import fr.emn.oadymppac.event.ReduceListener;
import fr.emn.oadymppac.event.RejectEvent;
import fr.emn.oadymppac.event.RejectListener;
import fr.emn.oadymppac.event.RestoreEvent;
import fr.emn.oadymppac.event.RestoreListener;
import fr.emn.oadymppac.event.SelectConstraintEvent;
import fr.emn.oadymppac.event.SelectConstraintListener;
import fr.emn.oadymppac.event.SelectUpdateEvent;
import fr.emn.oadymppac.event.SelectUpdateListener;
import fr.emn.oadymppac.event.SolutionEvent;
import fr.emn.oadymppac.event.SolutionListener;
import fr.emn.oadymppac.event.SolverListener;
import fr.emn.oadymppac.event.StageEvent;
import fr.emn.oadymppac.event.StageListener;
import fr.emn.oadymppac.event.SuspendEvent;
import fr.emn.oadymppac.event.SuspendListener;
import fr.emn.oadymppac.event.TellEvent;
import fr.emn.oadymppac.event.TellListener;
import fr.emn.oadymppac.event.TrueEvent;
import fr.emn.oadymppac.event.TrueListener;
import fr.emn.oadymppac.event.WakeUpEvent;
import fr.emn.oadymppac.event.WakeUpListener;

/**
 * This class provides the basic behavior for a <code>Solver</code>.
 * It implements all the listener management and firing logics.
 * 
 * @version $Revision: 1.1 $
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 */
public abstract class AbstractSolver implements Solver {
	static final short STATE_STOPPED = 0;
	static final short STATE_STARTED = 1;
	static final short STATE_SUSPENDED = 2;
	/**
	 * Adds any kind of listener to a listener list and its related
	 * filter to a filter list.
	 * 
	 * @param listener    a listener <code>Object</code> to be added
	 * @param filter    a mask to specify the precision requested by
	 *        the listener
	 * @param list    the <code>Vector</code> into which the listener
	 *        is added
	 * @param filters    the <code>Vector</code> into which the
	 *        filter is added
	 */
	protected static void addListener(
		final Object listener,
		final short filter,
		final Vector list,
		final Vector filters) {
		list.addElement(listener);
		filters.addElement(Integer.valueOf(filter));
	}
	protected static short removeListener(
		final Object listener,
		final Vector list,
		final Vector filters) {
		short f = 0;

		for (int i = 0; i < list.size(); i++) {
			if (list.elementAt(i) == listener) {
				list.removeElementAt(i);
				filters.removeElementAt(i);
			}
			else {
				f |= ((Integer) filters.elementAt(i)).intValue();
			}
		}

		return f;
	}
	Vector solverListeners;
	Vector newVariableListeners;
	Vector newVariableListenerFilters;
	short newVariableListenersFilter;
	Vector newConstraintListeners;
	Vector newConstraintListenerFilters;
	short newConstraintListenersFilter;
	Vector reduceListeners;
	Vector reduceListenerFilters;
	short reduceListenersFilter;
	Vector restoreListeners;
	Vector restoreListenerFilters;
	short restoreListenersFilter;
	Vector wakeUpListeners;
	Vector wakeUpListenerFilters;
	short wakeUpListenersFilter;
	Vector suspendListeners;
	Vector suspendListenerFilters;
	short suspendListenersFilter;
	Vector trueListeners;
	Vector trueListenerFilters;
	short trueListenersFilter;
	Vector rejectListeners;
	Vector rejectListenerFilters;
	short rejectListenersFilter;
	Vector selectConstraintListeners;
	Vector selectConstraintListenerFilters;
	short selectConstraintListenersFilter;
	Vector selectUpdateListeners;
	Vector selectUpdateListenerFilters;
	short selectUpdateListenersFilter;
	Vector activateListeners;
	Vector activateListenerFilters;
	short activateListenersFilter;
	Vector tellListeners;
	Vector tellListenerFilters;
	short tellListenersFilter;
	Vector deactivateListeners;
	Vector deactivateListenerFilters;
	short deactivateListenersFilter;
	Vector solutionListeners;
	Vector solutionListenerFilters;
	short solutionListenersFilter;
	Vector stageListeners;
	Vector stageListenerFilters;
	short stageListenersFilter;

	Dictionary clientProperties;

	short state;

	/**
	 * Defines an <code>AbstractSolver</code> object.
	 */
	public AbstractSolver() {
		this.state = AbstractSolver.STATE_STOPPED;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addActivateListener(
		final ActivateListener l,
		final short filter) {
		if (this.activateListeners == null) {
			this.activateListeners = new Vector();
			this.activateListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.activateListeners,
			this.activateListenerFilters);
		this.activateListenersFilter |= filter;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addDeactivateListener(
		final DeactivateListener l,
		final short filter) {
		if (this.deactivateListeners == null) {
			this.deactivateListeners = new Vector();
			this.deactivateListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.deactivateListeners,
			this.deactivateListenerFilters);
		this.deactivateListenersFilter |= filter;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addNewConstraintListener(
		final NewConstraintListener l,
		final short filter) {
		if (this.newConstraintListeners == null) {
			this.newConstraintListeners = new Vector();
			this.newConstraintListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.newConstraintListeners,
			this.newConstraintListenerFilters);
		this.newConstraintListenersFilter |= filter;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addNewVariableListener(
		final NewVariableListener l,
		final short filter) {
		if (this.newVariableListeners == null) {
			this.newVariableListeners = new Vector();
			this.newVariableListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.newVariableListeners,
			this.newVariableListenerFilters);
		this.newVariableListenersFilter |= filter;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addReduceListener(
		final ReduceListener l,
		final short filter) {
		if (this.reduceListeners == null) {
			this.reduceListeners = new Vector();
			this.reduceListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.reduceListeners,
			this.reduceListenerFilters);
		this.reduceListenersFilter |= filter;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addRejectListener(
		final RejectListener l,
		final short filter) {
		if (this.rejectListeners == null) {
			this.rejectListeners = new Vector();
			this.rejectListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.rejectListeners,
			this.rejectListenerFilters);
		this.rejectListenersFilter |= filter;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addRestoreListener(
		final RestoreListener l,
		final short filter) {
		if (this.restoreListeners == null) {
			this.restoreListeners = new Vector();
			this.restoreListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.restoreListeners,
			this.restoreListenerFilters);
		this.restoreListenersFilter |= filter;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addSelectConstraintListener(
		final SelectConstraintListener l,
		final short filter) {
		if (this.selectConstraintListeners == null) {
			this.selectConstraintListeners = new Vector();
			this.selectConstraintListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.selectConstraintListeners,
			this.selectConstraintListenerFilters);
		this.selectConstraintListenersFilter |= filter;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addSelectUpdateListener(
		final SelectUpdateListener l,
		final short filter) {
		if (this.selectUpdateListeners == null) {
			this.selectUpdateListeners = new Vector();
			this.selectUpdateListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.selectUpdateListeners,
			this.selectUpdateListenerFilters);
		this.selectUpdateListenersFilter |= filter;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addSolutionListener(
		final SolutionListener l,
		final short filter) {
		if (this.solutionListeners == null) {
			this.solutionListeners = new Vector();
			this.solutionListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.solutionListeners,
			this.solutionListenerFilters);
		this.solutionListenersFilter |= filter;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void addSolverListener(final SolverListener l) {
		if (this.solverListeners == null) {
			this.solverListeners = new Vector();
		}

		this.solverListeners.add(l);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addStageListener(
		final StageListener l,
		final short filter) {
		if (this.stageListeners == null) {
			this.stageListeners = new Vector();
			this.stageListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.stageListeners,
			this.stageListenerFilters);
		this.stageListenersFilter |= filter;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addSuspendListener(
		final SuspendListener l,
		final short filter) {
		if (this.suspendListeners == null) {
			this.suspendListeners = new Vector();
			this.suspendListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.suspendListeners,
			this.suspendListenerFilters);
		this.suspendListenersFilter |= filter;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addTellListener(
		final TellListener l,
		final short filter) {
		if (this.tellListeners == null) {
			this.tellListeners = new Vector();
			this.tellListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.tellListeners,
			this.tellListenerFilters);
		this.tellListenersFilter |= filter;
	}
	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addTrueListener(
		final TrueListener l,
		final short filter) {
		if (this.trueListeners == null) {
			this.trueListeners = new Vector();
			this.trueListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.trueListeners,
			this.trueListenerFilters);
		this.trueListenersFilter |= filter;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 * @param filter DOCUMENT ME!
	 */
	public synchronized void addWakeUpListener(
		final WakeUpListener l,
		final short filter) {
		if (this.wakeUpListeners == null) {
			this.wakeUpListeners = new Vector();
			this.wakeUpListenerFilters = new Vector();
		}

		AbstractSolver.addListener(
			l,
			filter,
			this.wakeUpListeners,
			this.wakeUpListenerFilters);
		this.wakeUpListenersFilter |= filter;
	}

	protected abstract void doResume();

	protected abstract void doStart();

	protected abstract void doStop();

	protected abstract void doSuspend();

	// Called at the end of each notification to perform the stop/suspend
	protected abstract void doValidateState();

	/**
	 * DOCUMENT ME!
	 *
	 * @param e DOCUMENT ME!
	 */
	public void error(final Exception e) {
		e.printStackTrace();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public ActivateListener getActivateListener(final int index) {
		return (ActivateListener) this.activateListeners.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 * 
	 * @return a small Hashtable
	 * 
	 * @see #putClientProperty
	 * @see #getClientProperty
	 */
	private Dictionary getClientProperties() {
		if (this.clientProperties == null) {
			this.clientProperties = new Hashtable(2);
		}

		return this.clientProperties;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param key DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public Object getClientProperty(final Object key) {
		if (this.clientProperties == null) {
			return null;
		}
		else {
			return this.getClientProperties().get(key);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public DeactivateListener getDeactivateListener(final int index) {
		return (DeactivateListener) this.deactivateListeners.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public NewConstraintListener getNewConstraintListener(final int index) {
		return (NewConstraintListener) this.newConstraintListeners
			.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public NewVariableListener getNewVariableListener(final int index) {
		return (NewVariableListener) this.newVariableListeners.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public ReduceListener getReduceListener(final int index) {
		return (ReduceListener) this.reduceListeners.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public RejectListener getRejectListener(final int index) {
		return (RejectListener) this.rejectListeners.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public RestoreListener getRestoreListener(final int index) {
		return (RestoreListener) this.restoreListeners.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public SelectConstraintListener getSelectConstraintListener(final int index) {
		return (SelectConstraintListener) this.selectConstraintListeners
			.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public SelectUpdateListener getSelectUpdateListener(final int index) {
		return (SelectUpdateListener) this.selectUpdateListeners
			.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public SolutionListener getSolutionListener(final int index) {
		return (SolutionListener) this.solutionListeners.elementAt(index);
	}

	public SolverListener getSolverListener(final int index) {
		return (SolverListener) this.solverListeners.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public StageListener getStageListener(final int index) {
		return (StageListener) this.stageListeners.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public SuspendListener getSuspendListener(final int index) {
		return (SuspendListener) this.suspendListeners.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public TellListener getTellListener(final int index) {
		return (TellListener) this.tellListeners.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public TrueListener getTrueListener(final int index) {
		return (TrueListener) this.trueListeners.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param index DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public WakeUpListener getWakeUpListener(final int index) {
		return (WakeUpListener) this.wakeUpListeners.elementAt(index);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasActivateListener() {
		return this.activateListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasDeactivateListener() {
		return this.deactivateListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasNewConstraintListener() {
		return this.newConstraintListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasNewVariableListener() {
		return this.newVariableListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasReduceListener() {
		return this.reduceListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasRejectListener() {
		return this.rejectListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasRestoreListener() {
		return this.restoreListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasSelectConstraintListener() {
		return this.selectConstraintListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasSelectUpdateListener() {
		return this.selectUpdateListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasSolutionListener() {
		return this.solutionListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasStageListener() {
		return this.stageListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasSuspendListener() {
		return this.suspendListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasTellListener() {
		return this.tellListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasTrueListener() {
		return this.trueListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return DOCUMENT ME!
	 */
	public boolean hasWakeUpListener() {
		return this.wakeUpListeners != null;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifyActivateListener(final ActivateEvent ev) {
		this.doValidateState();

		this.notifySolverListener(ev);
		if (this.activateListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.activateListeners.size(); i++) {
				this.getActivateListener(i).activate(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifyDeactivateListener(final DeactivateEvent ev) {
		this.doValidateState();

		this.notifySolverListener(ev);
		if (this.deactivateListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.deactivateListeners.size(); i++) {
				this.getDeactivateListener(i).deactivate(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifyEndStage(final StageEvent ev) {
		this.doValidateState();

		if (this.stageListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.stageListeners.size(); i++) {
				this.getStageListener(i).endStage(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifyNewConstraintListener(final NewConstraintEvent ev) {
		this.doValidateState();
		this.notifySolverListener(ev);
		if (this.newConstraintListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.newConstraintListeners.size(); i++) {
				this.getNewConstraintListener(i).newConstraint(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifyNewStage(final NewStageEvent ev) {
		this.doValidateState();

		this.notifySolverListener(ev);
		if (this.stageListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.stageListeners.size(); i++) {
				this.getStageListener(i).newStage(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifyNewVariableListener(final NewVariableEvent ev) {
		this.doValidateState();

		this.notifySolverListener(ev);
		if (this.newVariableListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.newVariableListeners.size(); i++) {
				this.getNewVariableListener(i).newVariable(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifyReduceListener(final ReduceEvent ev) {
		this.doValidateState();

		this.notifySolverListener(ev);
		if (this.reduceListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.reduceListeners.size(); i++) {
				this.getReduceListener(i).reduce(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifyRejectListener(final RejectEvent ev) {
		this.doValidateState();

		this.notifySolverListener(ev);
		if (this.rejectListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.rejectListeners.size(); i++) {
				this.getRejectListener(i).reject(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifyRestoreListener(final RestoreEvent ev) {
		this.doValidateState();

		this.notifySolverListener(ev);
		if (this.restoreListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.restoreListeners.size(); i++) {
				this.getRestoreListener(i).restore(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifyResumeStage(final StageEvent ev) {
		this.doValidateState();

		if (this.stageListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.stageListeners.size(); i++) {
				this.getStageListener(i).resumeStage(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifySelectConstraintListener(final SelectConstraintEvent ev) {
		this.doValidateState();

		this.notifySolverListener(ev);
		if (this.selectConstraintListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.selectConstraintListeners.size(); i++) {
				this.getSelectConstraintListener(i).selectConstraint(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifySelectUpdateListener(final SelectUpdateEvent ev) {
		this.doValidateState();

		this.notifySolverListener(ev);
		if (this.selectUpdateListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.selectUpdateListeners.size(); i++) {
				this.getSelectUpdateListener(i).selectUpdate(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifySolutionListener(final SolutionEvent ev) {
		this.doValidateState();

		this.notifySolverListener(ev);
		if (this.solutionListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.solutionListeners.size(); i++) {
				this.getSolutionListener(i).solution(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	public void notifySolverListener(final BasicSolverEvent ev) {

		if (this.solverListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.solverListeners.size(); i++) {
				this.getSolverListener(i).solver(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifyStartStage(final StageEvent ev) {
		this.doValidateState();

		if (this.stageListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.stageListeners.size(); i++) {
				this.getStageListener(i).startStage(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifySuspendListener(final SuspendEvent ev) {
		this.doValidateState();

		this.notifySolverListener(ev);
		if (this.suspendListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.suspendListeners.size(); i++) {
				this.getSuspendListener(i).suspend(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifySuspendStage(final StageEvent ev) {
		this.doValidateState();

		if (this.stageListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.stageListeners.size(); i++) {
				this.getStageListener(i).suspendStage(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifyTellListener(final TellEvent ev) {
		this.doValidateState();

		this.notifySolverListener(ev);
		if (this.tellListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.tellListeners.size(); i++) {
				this.getTellListener(i).tell(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifyTrueListener(final TrueEvent ev) {
		this.doValidateState();

		this.notifySolverListener(ev);
		if (this.trueListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.trueListeners.size(); i++) {
				this.getTrueListener(i).true_(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ev DOCUMENT ME!
	 */
	public void notifyWakeUpListener(final WakeUpEvent ev) {
		this.doValidateState();

		this.notifySolverListener(ev);
		if (this.wakeUpListeners == null) {
			return;
		}

		try {
			for (int i = 0; i < this.wakeUpListeners.size(); i++) {
				this.getWakeUpListener(i).wakeUp(ev);
			}
		}
		catch (final Exception e) {
			this.error(e);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param key DOCUMENT ME!
	 * @param value DOCUMENT ME!
	 */
	public final void putClientProperty(final Object key, final Object value) {
		this.getClientProperties().put(key, value);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeActivateListener(final ActivateListener l) {
		this.activateListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.activateListeners,
				this.activateListenerFilters);

		if (this.activateListeners.size() == 0) {
			this.activateListeners = null;
			this.activateListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeDeactivateListener(final DeactivateListener l) {
		this.deactivateListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.deactivateListeners,
				this.deactivateListenerFilters);

		if (this.deactivateListeners.size() == 0) {
			this.deactivateListeners = null;
			this.deactivateListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeNewConstraintListener(
		final NewConstraintListener l) {
		this.newConstraintListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.newConstraintListeners,
				this.newConstraintListenerFilters);

		if (this.newConstraintListeners.size() == 0) {
			this.newConstraintListeners = null;
			this.newConstraintListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeNewVariableListener(
		final NewVariableListener l) {
		this.newVariableListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.newVariableListeners,
				this.newVariableListenerFilters);

		if (this.newVariableListeners.size() == 0) {
			this.newVariableListeners = null;
			this.newVariableListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeReduceListener(final ReduceListener l) {
		this.reduceListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.reduceListeners,
				this.reduceListenerFilters);

		if (this.reduceListeners.size() == 0) {
			this.reduceListeners = null;
			this.reduceListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeRejectListener(final RejectListener l) {
		this.rejectListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.rejectListeners,
				this.rejectListenerFilters);

		if (this.rejectListeners.size() == 0) {
			this.rejectListeners = null;
			this.rejectListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeRestoreListener(final RestoreListener l) {
		this.restoreListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.restoreListeners,
				this.restoreListenerFilters);

		if (this.restoreListeners.size() == 0) {
			this.restoreListeners = null;
			this.restoreListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeSelectConstraintListener(
		final SelectConstraintListener l) {
		this.selectConstraintListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.selectConstraintListeners,
				this.selectConstraintListenerFilters);

		if (this.selectConstraintListeners.size() == 0) {
			this.selectConstraintListeners = null;
			this.selectConstraintListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeSelectUpdateListener(
		final SelectUpdateListener l) {
		this.selectUpdateListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.selectUpdateListeners,
				this.selectUpdateListenerFilters);

		if (this.selectUpdateListeners.size() == 0) {
			this.selectUpdateListeners = null;
			this.selectUpdateListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeSolutionListener(final SolutionListener l) {
		this.solutionListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.solutionListeners,
				this.solutionListenerFilters);

		if (this.solutionListeners.size() == 0) {
			this.solutionListeners = null;
			this.solutionListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeSolverListener(final SolverListener l) {
		if (this.solverListeners != null) {
			this.solverListeners.remove(l);
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeStageListener(final StageListener l) {
		this.stageListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.stageListeners,
				this.stageListenerFilters);

		if (this.stageListeners.size() == 0) {
			this.stageListeners = null;
			this.stageListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeSuspendListener(final SuspendListener l) {
		this.suspendListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.suspendListeners,
				this.suspendListenerFilters);

		if (this.suspendListeners.size() == 0) {
			this.suspendListeners = null;
			this.suspendListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeTellListener(final TellListener l) {
		this.tellListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.tellListeners,
				this.tellListenerFilters);

		if (this.tellListeners.size() == 0) {
			this.tellListeners = null;
			this.tellListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeTrueListener(final TrueListener l) {
		this.trueListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.trueListeners,
				this.trueListenerFilters);

		if (this.trueListeners.size() == 0) {
			this.trueListeners = null;
			this.trueListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param l DOCUMENT ME!
	 */
	public synchronized void removeWakeUpListener(final WakeUpListener l) {
		this.wakeUpListenersFilter =
			AbstractSolver.removeListener(
				l,
				this.wakeUpListeners,
				this.wakeUpListenerFilters);

		if (this.wakeUpListeners.size() == 0) {
			this.wakeUpListeners = null;
			this.wakeUpListenerFilters = null;
		}
	}

	/**
	 * DOCUMENT ME!
	 */
	public synchronized void resume() {
		if (this.state != AbstractSolver.STATE_SUSPENDED) {
			return;
		}

		this.doResume();
		this.state = AbstractSolver.STATE_STARTED;
	}

	/**
	 * DOCUMENT ME!
	 */
	public synchronized void start() {
		if (this.state != AbstractSolver.STATE_STOPPED) {
			return;
		}

		this.doStart();
		this.state = AbstractSolver.STATE_STARTED;
	}

	/**
	 * DOCUMENT ME!
	 */
	public synchronized void stop() {
		if (this.state == AbstractSolver.STATE_STOPPED) {
			return;
		}

		this.doStop();
		this.state = AbstractSolver.STATE_STOPPED;
	}

	/**
	 * DOCUMENT ME!
	 */
	public synchronized void suspend() {
		if (this.state != AbstractSolver.STATE_STARTED) {
			return;
		}

		this.doSuspend();
		this.state = AbstractSolver.STATE_SUSPENDED;
	}
}