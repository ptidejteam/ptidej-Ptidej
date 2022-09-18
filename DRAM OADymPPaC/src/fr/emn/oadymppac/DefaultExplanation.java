/*
 * $Id: DefaultExplanation.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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

package fr.emn.oadymppac;

/**
 * Default implementation of the <code>Explanation</code> interface.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */

public class DefaultExplanation implements Explanation {
	UpdateList causeList;
	String[] constraintList;

	/**
	 * Default constructor.
	 */
	public DefaultExplanation() {
	}
	/**
	 * copy constructor.
	 */
	public DefaultExplanation(final Explanation other, final boolean deep) {
		this.causeList = new DefaultUpdateList(other.getCauseList(), deep);
		if (deep) {
			this.constraintList = (String[]) other.getConstraintList().clone();
		}
		else {
			this.constraintList = other.getConstraintList();
		}
	}

	/**
	 * Returns the <code>causeList</code>.
	 */
	public UpdateList getCauseList() {
		return this.causeList;
	}
	/**
	 * Returns the <code>constraintList</code>.
	 */
	public String[] getConstraintList() {
		return this.constraintList;
	}

	/**
	 * Sets the <code>causeList</code>.
	 */
	public void setCauseList(final UpdateList l) {
		this.causeList = l;
	}
	/**
	 * Sets the <code>constraintList</code>.
	 */
	public void setConstraintList(final String[] s) {
		this.constraintList = s;
	}

}
