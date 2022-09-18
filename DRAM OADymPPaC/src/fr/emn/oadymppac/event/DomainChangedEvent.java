/*
 * $Id: DomainChangedEvent.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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

import fr.emn.oadymppac.Variable;

/**
 * Title:        OADYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Ecole des Mines de Nantes
 * @author Mohammad Ghoniem & Jean-Daniel Fekete
 * @version 1.0
 */

public class DomainChangedEvent extends BasicSolverEvent {
	Variable variable;
	BasicSolverEvent event;

	public DomainChangedEvent() {
	}
	public DomainChangedEvent(final DomainChangedEvent other, final boolean deep) {
		super(other, deep);
		this.variable = other.variable;
		this.event = other.event;
	}
	public DomainChangedEvent(final Variable var, final BasicSolverEvent event) {
		super(event, false);
		this.variable = var;
		this.event = event;
	}

	public BasicSolverEvent deepClone() {
		return new DomainChangedEvent(this, true);
	}

	public BasicSolverEvent getEvent() {
		return this.event;
	}
	public Variable getVariable() {
		return this.variable;
	}

	public void setEvent(final BasicSolverEvent event) {
		this.event = event;
	}
	public void setVariable(final Variable v) {
		this.variable = v;
	}
}
