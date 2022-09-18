/*
 * $Id: NewConstraintEvent.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

import fr.emn.oadymppac.DefaultUpdateList;
import fr.emn.oadymppac.SolverConstants;
import fr.emn.oadymppac.UpdateList;

/**
 * Title:        OADYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      EMN
 * @author Mohammad Ghoniem
 * @version 1.0
 */

public class NewConstraintEvent extends SolverEvent {
	boolean user;
	UpdateList updateList;

	public static final short EVENT_TYPE = SolverConstants.EVENT_NEW_CONSTRAINT;

	public NewConstraintEvent() {
	}
	public NewConstraintEvent(
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
	public NewConstraintEvent(final int n, final long time, final String cname) {
		this(n, time, EventAttributes.DEPTH_NONE, cname, null, null, null, null);
	}

	public NewConstraintEvent(final int n, final String cname) {
		this(n, 0, EventAttributes.DEPTH_NONE, cname, null, null, null, null);
	}

	public NewConstraintEvent(
		final int n,
		final String cname,
		final String externalRep,
		final String internalRep) {
		this(
			n,
			0,
			EventAttributes.DEPTH_NONE,
			cname,
			internalRep,
			externalRep,
			null,
			null);
	}

	public NewConstraintEvent(final NewConstraintEvent other, final boolean deep) {
		super(other, deep);
		this.user = other.getUser();
		if (deep && other.getUpdateList() != null) {
			this.updateList =
				new DefaultUpdateList(other.getUpdateList(), true);
		}
		else {
			this.updateList = other.getUpdateList();
		}
	}

	public BasicSolverEvent deepClone() {
		return new NewConstraintEvent(this, true);
	}

	public int getEventType() {
		return NewConstraintEvent.EVENT_TYPE;
	}
	public UpdateList getUpdateList() {
		return this.updateList;
	}
	public boolean getUser() {
		return this.user;
	}

	public boolean isUser() {
		return this.user;
	}
	public void setUpdateList(final UpdateList l) {
		this.updateList = l;
	}

	public void setUser(final boolean b) {
		this.user = b;
	}
}
