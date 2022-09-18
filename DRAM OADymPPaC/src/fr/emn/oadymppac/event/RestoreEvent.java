/*
 * $Id: RestoreEvent.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

public class RestoreEvent extends SolverEvent {
	State state;
	UpdateList updateList;
	UpdateList causeList;

	public static final short EVENT_TYPE = SolverConstants.EVENT_RESTORE;

	public RestoreEvent() {
	}
	public RestoreEvent(final int n) {
		this(n, 0, EventAttributes.DEPTH_NONE, null, null, null, null, null);
	}
	public RestoreEvent(
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
	public RestoreEvent(
		final int n,
		final long time,
		final int depth,
		final String cname,
		final String externalRep,
		final String internalRep,
		final String context,
		final String[] stages,
		final UpdateList updates,
		final UpdateList causes) {
		super(n, time, depth, cname, externalRep, internalRep, context, stages);
		this.updateList = updates;
		this.causeList = causes;
	}
	public RestoreEvent(final int n, final UpdateList updates) {
		this(n, 0, EventAttributes.DEPTH_NONE, null, null, null, null, null);
		this.updateList = updates;
	}
	public RestoreEvent(
		final int n,
		final UpdateList updates,
		final UpdateList causes) {
		this(n, 0, EventAttributes.DEPTH_NONE, null, null, null, null, null);
		this.updateList = updates;
		this.causeList = causes;
	}
	public RestoreEvent(final RestoreEvent other, final boolean deep) {
		super(other, deep);
		if (deep) {
			if (other.getState() != null) {
				this.state = new DefaultState(other.getState(), true);
			}
			if (other.getUpdateList() != null) {
				this.updateList =
					new DefaultUpdateList(other.getUpdateList(), true);
			}
			if (other.getCauseList() != null) {
				this.causeList =
					new DefaultUpdateList(other.getCauseList(), true);
			}

		}
		else {
			this.state = other.getState();
			this.updateList = other.getUpdateList();
			this.causeList = other.getCauseList();
		}
	}

	public BasicSolverEvent deepClone() {
		return new RestoreEvent(this, true);
	}

	public UpdateList getCauseList() {
		return this.causeList;
	}
	public int getEventType() {
		return RestoreEvent.EVENT_TYPE;
	}
	public State getState() {
		return this.state;
	}
	public UpdateList getUpdateList() {
		return this.updateList;
	}
	public void setCauseList(final UpdateList cl) {
		this.causeList = cl;
	}
	public void setState(final State s) {
		this.state = s;
	}

	public void setUpdateList(final UpdateList ul) {
		this.updateList = ul;
	}
}
