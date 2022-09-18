/*
 * $Id: Printer.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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

import fr.emn.oadymppac.SAXSolver;
import fr.emn.oadymppac.Solver;
import fr.emn.oadymppac.event.ActivateEvent;
import fr.emn.oadymppac.event.ActivateListener;
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

public class Printer implements ActivateListener, DeactivateListener,
		NewConstraintListener, NewVariableListener, ReduceListener,
		RejectListener, RestoreListener, SelectConstraintListener,
		SelectUpdateListener, SolutionListener, StageListener, SuspendListener,
		TellListener, TrueListener, WakeUpListener {
	public Printer() {
	}
	public void activate(final ActivateEvent event) {
		//      System.out.println(event.getClass().getName());
		//      System.out.println("cname: "+event.getCName()+" depth:"+event.getDepth());
	}

	public void deactivate(final DeactivateEvent event) {
		//      System.out.println(event.getClass().getName());
		//      System.out.println("cname: "+event.getCName()+" depth:"+event.getDepth());
	};
	public void endStage(final StageEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void newConstraint(final NewConstraintEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void newStage(final NewStageEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void newVariable(final NewVariableEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void reduce(final ReduceEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void register(final SAXSolver solver) {
		solver.addActivateListener(this, Solver.FILTER_ALL);
		solver.addDeactivateListener(this, Solver.FILTER_ALL);
		solver.addNewConstraintListener(this, Solver.FILTER_ALL);
		solver.addNewVariableListener(this, Solver.FILTER_ALL);
		solver.addReduceListener(this, Solver.FILTER_ALL);
		solver.addRejectListener(this, Solver.FILTER_ALL);
		solver.addRestoreListener(this, Solver.FILTER_ALL);
		solver.addSelectConstraintListener(this, Solver.FILTER_ALL);
		solver.addSelectUpdateListener(this, Solver.FILTER_ALL);
		solver.addSolutionListener(this, Solver.FILTER_ALL);
		solver.addStageListener(this, Solver.FILTER_ALL);
		solver.addSuspendListener(this, Solver.FILTER_ALL);
		solver.addTellListener(this, Solver.FILTER_ALL);
		solver.addTrueListener(this, Solver.FILTER_ALL);
		solver.addWakeUpListener(this, Solver.FILTER_ALL);
	};
	public void reject(final RejectEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void restore(final RestoreEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void resumeStage(final StageEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void selectConstraint(final SelectConstraintEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void selectUpdate(final SelectUpdateEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void solution(final SolutionEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void startStage(final StageEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void suspend(final SuspendEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void suspendStage(final StageEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void tell(final TellEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void true_(final TrueEvent event) {
		System.out.println(event.getClass().getName());
	};
	public void wakeUp(final WakeUpEvent event) {
		System.out.println(event.getClass().getName());
	};
}
