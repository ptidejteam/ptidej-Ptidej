/*
 * $Id: WakeUpEvent.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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
import fr.emn.oadymppac.DefaultUpdateList;
import fr.emn.oadymppac.SolverConstants;
import fr.emn.oadymppac.State;
import fr.emn.oadymppac.UpdateList;

/**
 * Title:        OADYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Ecole des Mines de Nantes
 * @author Mohammad Ghoniem & Jean-Daniel Fekete
 * @version 1.0
 */

public class WakeUpEvent extends SolverEvent {
	State state;
	UpdateList causeList; // or DefaultCauseList

	public static final short EVENT_TYPE = SolverConstants.EVENT_WAKE_UP;

	public WakeUpEvent() {
	}
	public WakeUpEvent(
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
	public WakeUpEvent(final int n, final String cname, final State s) {
		this(n, 0, EventAttributes.DEPTH_NONE, cname, null, null, null, null);
		this.state = s;
	}
	public WakeUpEvent(
		final int n,
		final String cname,
		final State s,
		final UpdateList cl) {
		this(n, 0, EventAttributes.DEPTH_NONE, cname, null, null, null, null);
		this.state = s;
		this.causeList = cl;
	}
	public WakeUpEvent(final int n, final String cname, final UpdateList cl) {
		this(n, 0, EventAttributes.DEPTH_NONE, cname, null, null, null, null);
		this.causeList = cl;
	}

	public WakeUpEvent(final WakeUpEvent other, final boolean deep) {
		super(other, deep);
		if (deep) {
			if (other.getState() != null) {
				this.state = new DefaultState(other.getState(), true);
			}
			if (other.getCauseList() != null) {
				this.causeList =
					new DefaultUpdateList(other.getCauseList(), true);
			}
		}
		else {
			this.state = other.getState();
			this.causeList = other.getCauseList();
		}
	}

	public BasicSolverEvent deepClone() {
		return new WakeUpEvent(this, true);
	}

	public UpdateList getCauseList() {
		return this.causeList;
	}
	public State getState() {
		return this.state;
	}
	public void setCauseList(final UpdateList cl) {
		this.causeList = cl;
	}
	public void setState(final State s) {
		this.state = s;
	}
}
