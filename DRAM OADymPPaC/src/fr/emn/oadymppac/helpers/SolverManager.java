/*
 * $Id: SolverManager.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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
package fr.emn.oadymppac.helpers;

import fr.emn.oadymppac.Solver;
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
import fr.emn.oadymppac.event.SolverEvent;
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
 * Title:        OADYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Ecole des Mines de Nantes
 * @author Mohammad Ghoniem & Jean-Daniel Fekete
 * @version 1.0
 */

public class SolverManager implements ActivateListener, DeactivateListener,
		NewConstraintListener, NewVariableListener, ReduceListener,
		RejectListener, RestoreListener, SelectConstraintListener,
		SelectUpdateListener, SolutionListener, StageListener, SuspendListener,
		TellListener, TrueListener, WakeUpListener {
	/**
	 * Default constructor.
	 */
	public SolverManager() {
	}
	/**
	 * Handles <code>ActivateEvent</code>s.
	 */
	public void activate(final ActivateEvent event) {
		this.solver(event);
	}
	/**
	 * Handles <code>BasicSolverEvent</code>s.
	 */
	public void basic(final BasicSolverEvent event) {
	}
	/**
	 * Handles <code>DeactivateEvent</code>s.
	 */
	public void deactivate(final DeactivateEvent event) {
		this.solver(event);
	}
	/**
	 *
	 */
	public void endStage(final StageEvent event) {
	};
	/**
	 * Handles <code>NewConstraintEvent</code>s.
	 */
	public void newConstraint(final NewConstraintEvent event) {
		this.basic(event);
	};
	/**
	 * Handles <code>NewStageEvent</code>s.
	 */
	public void newStage(final NewStageEvent event) {
		this.basic(event);
	};
	/**
	 * Handles <code>NewVariableEvent</code>s.
	 */
	public void newVariable(final NewVariableEvent event) {
		this.basic(event);
	};
	/**
	 * Handles <code>ReduceEvent</code>s.
	 */
	public void reduce(final ReduceEvent event) {
		this.solver(event);
	};
	/**
	 * Binds the solver manager to the given instance of <code>Solver</code>
	 * by adding all the relevant event listeners.
	 */
	public void register(final Solver solver) {
		solver.addActivateListener(this, Solver.FILTER_NONE);
		solver.addDeactivateListener(this, Solver.FILTER_NONE);
		solver.addNewConstraintListener(this, Solver.FILTER_NONE);
		solver.addNewVariableListener(this, Solver.FILTER_NONE);
		solver.addReduceListener(this, Solver.FILTER_NONE);
		solver.addRejectListener(this, Solver.FILTER_NONE);
		solver.addRestoreListener(this, Solver.FILTER_NONE);
		solver.addSelectConstraintListener(this, Solver.FILTER_NONE);
		solver.addSelectUpdateListener(this, Solver.FILTER_NONE);
		solver.addSolutionListener(this, Solver.FILTER_NONE);
		solver.addStageListener(this, Solver.FILTER_NONE);
		solver.addSuspendListener(this, Solver.FILTER_NONE);
		solver.addTellListener(this, Solver.FILTER_NONE);
		solver.addTrueListener(this, Solver.FILTER_NONE);
		solver.addWakeUpListener(this, Solver.FILTER_NONE);
	};
	/**
	 * Handles <code>RejectEvent</code>s.
	 */
	public void reject(final RejectEvent event) {
		this.solver(event);
	};
	/**
	 * Handles <code>RestoreEvent</code>s.
	 */
	public void restore(final RestoreEvent event) {
		this.solver(event);
	};
	/**
	 *
	 */
	public void resumeStage(final StageEvent event) {
	};
	/**
	 * Handles <code>SelectConstraintEvent</code>s.
	 */
	public void selectConstraint(final SelectConstraintEvent event) {
		this.solver(event);
	};
	/**
	 * Handles <code>SelectUpdateEvent</code>s.
	 */
	public void selectUpdate(final SelectUpdateEvent event) {
		this.basic(event);
	};
	/**
	 * Handles <code>SolutionEvent</code>s.
	 */
	public void solution(final SolutionEvent event) {
		this.solver(event);
	};
	/**
	 * Handles <code>SolverEvent</code>s by calling basic().
	 */
	public void solver(final SolverEvent event) {
		this.basic(event);
	};
	/**
	 *
	 */
	public void startStage(final StageEvent event) {
	};
	/**
	 * Handles <code>SuspendEvent</code>s.
	 */
	public void suspend(final SuspendEvent event) {
		this.solver(event);
	};
	/**
	 *
	 */
	public void suspendStage(final StageEvent event) {
	};
	/**
	 * Handles <code>TellEvent</code>s.
	 */
	public void tell(final TellEvent event) {
		this.solver(event);
	};
	/**
	 * Handles <code>TrueEvent</code>s.
	 */
	public void true_(final TrueEvent event) {
		this.solver(event);
	};
	/**
	 * Handles <code>WakeUpEvent</code>s.
	 */
	public void wakeUp(final WakeUpEvent event) {
		this.solver(event);
	};
}
