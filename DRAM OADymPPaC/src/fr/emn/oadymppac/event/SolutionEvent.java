/*
 * $Id: SolutionEvent.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

import fr.emn.oadymppac.DefaultState;
import fr.emn.oadymppac.SolverConstants;
import fr.emn.oadymppac.State;

/**
 * Title:        OADYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Ecole des Mines de Nantes
 * @author Mohammad Ghoniem & Jean-Daniel Fekete
 * @version 1.0
 */

public class SolutionEvent extends SolverEvent {
	State state;

	public static final short EVENT_TYPE = SolverConstants.EVENT_SOLUTION;

	public SolutionEvent() {
	}
	public SolutionEvent(
		final int n,
		final long time,
		final int depth,
		final State s) {
		super(n, time, depth, null, null, null, null, null);
		this.state = s;
	}
	public SolutionEvent(
		final int n,
		final long time,
		final int depth,
		final String cname,
		final String externalRep,
		final String internalRep,
		final State s) {
		super(n, time, depth, cname, externalRep, internalRep, null, null);
		this.state = s;
	}

	public SolutionEvent(
		final int n,
		final long time,
		final int depth,
		final String cname,
		final String externalRep,
		final String internalRep,
		final String context,
		final String[] stages,
		final State s) {
		super(n, time, depth, cname, externalRep, internalRep, context, stages);
		this.state = s;
	}

	public SolutionEvent(final int n, final long time, final State s) {
		super(n, time, EventAttributes.DEPTH_NONE, null, null, null, null, null);
		this.state = s;
	}

	public SolutionEvent(final int n, final State s) {
		super(n, 0, EventAttributes.DEPTH_NONE, null, null, null, null, null);
		this.state = s;
	}

	public SolutionEvent(final SolutionEvent other, final boolean deep) {
		super(other, deep);
		if (deep && other.getState() != null) {
			this.state = new DefaultState(other.getState(), true);
		}
		else {
			this.state = other.getState();
		}
	}

	public BasicSolverEvent deepClone() {
		return new SolutionEvent(this, true);
	}

	public int getEventType() {
		return SolutionEvent.EVENT_TYPE;
	}

	public State getState() {
		return this.state;
	}

	public void setState(final State s) {
		this.state = s;
	}
}
