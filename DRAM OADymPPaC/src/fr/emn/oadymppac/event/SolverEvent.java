/*
 * $Id: SolverEvent.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

import fr.emn.oadymppac.ConstraintAttributes;

/**
 * Title:        OADYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      EMN
 * @author Mohammad Ghoniem
 * @version 1.0
 */

public abstract class SolverEvent extends BasicSolverEvent implements
		ConstraintAttributes {
	String cname;
	String externalRep;
	String internalRep;

	public SolverEvent() {
	}
	public SolverEvent(final int n) {
		this(n, 0, EventAttributes.DEPTH_NONE, null, null, null, null, null);
	}
	public SolverEvent(final int n, final long time) {
		this(n, time, EventAttributes.DEPTH_NONE, null, null, null, null, null);
	}
	public SolverEvent(final int n, final long time, final int depth) {
		this(n, time, depth, null, null, null, null, null);
	}
	public SolverEvent(
		final int n,
		final long time,
		final int depth,
		final String cname,
		final String externalRep,
		final String internalRep) {
		this(n, time, depth, cname, externalRep, internalRep, null, null);
	}
	public SolverEvent(
		final int n,
		final long time,
		final int depth,
		final String cname,
		final String externalRep,
		final String internalRep,
		final String context,
		final String[] stages) {
		super(n, time, depth, context, stages);
		this.cname = cname;
		this.externalRep = externalRep;
		this.internalRep = internalRep;
	}
	public SolverEvent(final SolverEvent other, final boolean deep) {
		super(other, deep);
		this.cname = other.getCName();
		this.externalRep = other.getExternalRep();
		this.internalRep = other.getInternalRep();
	}

	public String getCName() {
		return this.cname;
	}

	public String getExternalRep() {
		return this.externalRep;
	}

	public String getInternalRep() {
		return this.internalRep;
	}

	public void setCName(final String name) {
		this.cname = name;
	}

	public void setExternalRep(final String external) {
		this.externalRep = external;
	}

	public void setInternalRep(final String internal) {
		this.internalRep = internal;
	}

}
