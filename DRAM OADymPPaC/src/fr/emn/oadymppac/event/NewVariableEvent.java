/*
 * $Id: NewVariableEvent.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

import fr.emn.oadymppac.DefaultValueList;
import fr.emn.oadymppac.SolverConstants;
import fr.emn.oadymppac.ValueList;

/**
 * Title:        OADYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      EMN
 * @author Mohammad Ghoniem
 * @version 1.0
 */

public class NewVariableEvent extends BasicSolverEvent {
	String vname;
	public static final short TYPE_INT = 0;
	public static final short TYPE_RAT = 1;
	public static final short TYPE_REAL = 2;
	public static final short TYPE_ENUM = 3;
	public static final short TYPE_STRING = 4;
	short type;
	String externalRep;
	String internalRep;
	ValueList valueList;

	public static final short EVENT_TYPE = SolverConstants.EVENT_NEW_VARIABLE;

	public NewVariableEvent() {
	}
	public NewVariableEvent(
		final int n,
		final long time,
		final int depth,
		final String vname,
		final String externalRep,
		final String internalRep,
		final String context,
		final String[] stages) {
		super(n, time, depth, context, stages);
		this.vname = vname;
		this.externalRep = externalRep;
		this.internalRep = internalRep;
	}

	public NewVariableEvent(final int n, final long time, final String vname) {
		this(n, time, EventAttributes.DEPTH_NONE, vname, null, null, null, null);
	}

	public NewVariableEvent(final int n, final String vname) {
		this(n, 0, EventAttributes.DEPTH_NONE, vname, null, null, null, null);
	}

	public NewVariableEvent(
		final int n,
		final String vname,
		final String externalRep,
		final String internalRep) {
		this(
			n,
			0,
			EventAttributes.DEPTH_NONE,
			vname,
			internalRep,
			externalRep,
			null,
			null);
	}

	public NewVariableEvent(final NewVariableEvent other, final boolean deep) {
		super(other, deep);
		this.vname = other.getVName();
		this.type = other.getType();
		this.externalRep = other.getExternalRep();
		this.internalRep = other.getInternalRep();
		if (deep && other.getValueList() != null) {
			this.valueList = new DefaultValueList(other.getValueList(), true);
		}
		else {
			this.valueList = other.getValueList();
		}
	}

	public BasicSolverEvent deepClone() {
		return new NewVariableEvent(this, true);
	}

	public int getEventType() {
		return NewVariableEvent.EVENT_TYPE;
	}

	public String getExternalRep() {
		return this.externalRep;
	}

	public String getInternalRep() {
		return this.internalRep;
	}

	public short getType() {
		return this.type;
	}

	public ValueList getValueList() {
		return this.valueList;
	}

	public String getVName() {
		return this.vname;
	}

	public void setExternalRep(final String external) {
		this.externalRep = external;
	}
	public void setInternalRep(final String internal) {
		this.internalRep = internal;
	}

	public void setType(final short t) {
		this.type = t;
	}

	public void setValueList(final ValueList list) {
		this.valueList = list;
	}

	public void setVName(final String name) {
		this.vname = name;
	}
}
