/*
 * $Id: ActivateEvent.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

import fr.emn.oadymppac.DefaultExplanation;
import fr.emn.oadymppac.DefaultState;
import fr.emn.oadymppac.Explanation;
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

public class ActivateEvent extends SolverEvent {
	State state;
	Explanation explanation;

	public static final short EVENT_TYPE = SolverConstants.EVENT_ACTIVATE;

	public ActivateEvent() {
	}
	public ActivateEvent(final ActivateEvent other, final boolean deep) {
		super(other, deep);
		if (deep) {
			if (other.getState() != null) {
				this.state = new DefaultState(other.getState(), true);
			}
			if (other.getExplanation() != null) {
				this.explanation =
					new DefaultExplanation(other.getExplanation(), true);
			}
		}
	}
	public ActivateEvent(
		final int n,
		final int depth,
		final State s,
		final Explanation exp) {
		this(n, 0, depth, null, null, null, null, null);
		this.state = s;
		this.explanation = exp;
	}
	public ActivateEvent(
		final int n,
		final long time,
		final int depth,
		final String cname,
		final String externalRep,
		final String internalRep,
		final String context,
		final String[] stages) {
		super(n, time, depth, cname, externalRep, internalRep, context, stages);
	}
	public ActivateEvent(
		final int n,
		final long time,
		final int depth,
		final String cname,
		final String externalRep,
		final String internalRep,
		final String context,
		final String[] stages,
		final State s,
		final Explanation exp) {
		super(n, time, depth, cname, externalRep, internalRep, context, stages);
		this.state = s;
		this.explanation = exp;
	}

	public ActivateEvent(final int n, final State s, final Explanation exp) {
		this(n, 0, EventAttributes.DEPTH_NONE, null, null, null, null, null);
		this.state = s;
		this.explanation = exp;
	}
	public ActivateEvent(
		final int n,
		final String cname,
		final State s,
		final Explanation exp) {
		this(n, 0, EventAttributes.DEPTH_NONE, cname, null, null, null, null);
		this.state = s;
		this.explanation = exp;
	}

	public BasicSolverEvent deepClone() {
		return new ActivateEvent(this, true);
	}

	public int getEventType() {
		return ActivateEvent.EVENT_TYPE;
	}
	public Explanation getExplanation() {
		return this.explanation;
	}
	public State getState() {
		return this.state;
	}
	public void setExplanation(final Explanation exp) {
		this.explanation = exp;
	}

	public void setState(final State s) {
		this.state = s;
	}
}
