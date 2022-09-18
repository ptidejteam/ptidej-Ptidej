/*
 * $Id: NewStageEvent.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

import fr.emn.oadymppac.SolverConstants;

/**
 * Title:        OADYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Ecole des Mines de Nantes
 * @author Mohammad Ghoniem & Jean-Daniel Fekete
 * @version 1.0
 */

public class NewStageEvent extends BasicSolverEvent {
	String id;
	public static final short EVENT_TYPE = SolverConstants.EVENT_NEW_STAGE;

	public NewStageEvent() {
	}
	public NewStageEvent(
		final int n,
		final long time,
		final int depth,
		final String id) {
		super(n, 0, depth, null, null);
		this.id = id;
	}
	public NewStageEvent(
		final int n,
		final long time,
		final int depth,
		final String context,
		final String[] stages,
		final String id) {
		super(n, time, depth, context, stages);
		this.id = id;
	}
	public NewStageEvent(final int n, final long time, final String id) {
		super(n, 0, EventAttributes.DEPTH_NONE, null, null);
		this.id = id;
	}
	public NewStageEvent(final int n, final String id) {
		super(n);
		this.id = id;
	}
	public NewStageEvent(final NewStageEvent other, final boolean deep) {
		super(other, deep);
		this.id = other.getId();
	}

	public BasicSolverEvent deepClone() {
		return new NewStageEvent(this, true);
	}

	public int getEventType() {
		return NewStageEvent.EVENT_TYPE;
	}
	public String getId() {
		return this.id;
	}

	public void setId(final String id) {
		this.id = id;
	}
}
