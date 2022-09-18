/*
 * $Id: SelectUpdateEvent.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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
import fr.emn.oadymppac.DefaultValueList;
import fr.emn.oadymppac.SolverConstants;
import fr.emn.oadymppac.State;
import fr.emn.oadymppac.ValueList;

/**
 * Title:        OADYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Ecole des Mines de Nantes
 * @author Mohammad Ghoniem & Jean-Daniel Fekete
 * @version 1.0
 */

public class SelectUpdateEvent extends BasicSolverEvent {
	String vname;
	short type;
	State state;
	ValueList valueList;

	public static final short EVENT_TYPE = SolverConstants.EVENT_SELECT_UPDATE;

	public SelectUpdateEvent() {
	}
	public SelectUpdateEvent(final int n, final String vname, final short type) {
		super(n);
		this.vname = vname;
		this.type = type;
	}
	public SelectUpdateEvent(final SelectUpdateEvent other, final boolean deep) {
		super(other, deep);
		this.vname = other.getVName();
		this.type = other.getType();
		if (deep) {
			if (other.getValueList() != null) {
				this.valueList =
					new DefaultValueList(other.getValueList(), true);
			}
			if (other.getState() != null) {
				this.state = new DefaultState(other.getState(), true);
			}
		}
		else {
			this.valueList = other.getValueList();
			this.state = other.getState();
		}
	}

	public BasicSolverEvent deepClone() {
		return new SelectUpdateEvent(this, true);
	}

	public int getEventType() {
		return SelectUpdateEvent.EVENT_TYPE;
	}
	public State getState() {
		return this.state;
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
	public void setState(final State s) {
		this.state = s;
	}
	public void setType(final short t) {
		this.type = t;
	}
	public void setValueList(final ValueList v) {
		this.valueList = v;
	}

	public void setVName(final String name) {
		this.vname = name;
	}
}
