/*
 * $Id: StageEvent.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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
import fr.emn.oadymppac.StageAttributes;

/**
 * Title:        OADYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Ecole des Mines de Nantes
 * @author Mohammad Ghoniem & Jean-Daniel Fekete
 * @version 1.0
 */

public class StageEvent implements StageAttributes, SolverConstants {
	String sname;
	String[] refs;
	String detail;

	public static final short EVENT_TYPE = SolverConstants.EVENT_STAGE;

	public StageEvent() {
	}
	public StageEvent(final StageEvent other, final boolean deep) {
		this.sname = other.getSName();
		if (deep && other.getRefs() != null) {
			this.refs = (String[]) other.getRefs().clone();
		}
		else {
			this.refs = other.getRefs();
		}
		this.detail = other.getDetail();
	}
	public StageEvent(final String sname) {
		this.sname = sname;
	}

	public String getDetail() {
		return this.detail;
	}
	public int getEventType() {
		return StageEvent.EVENT_TYPE;
	}
	public String[] getRefs() {
		return this.refs;
	}
	public String getSName() {
		return this.sname;
	}
	public void setDetail(final String detail) {
		this.detail = detail;
	}
	public void setRefs(final String[] refs) {
		this.refs = refs;
	}

	public void setSName(final String sname) {
		this.sname = sname;
	}
}
