/*
 * $Id: VariableManagerCell.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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

package fr.emn.oadymppac.table;

import javax.swing.JLabel;
import fr.emn.oadymppac.Variable;

public class VariableManagerCell extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4587387540440895595L;
	Variable variable;
	int index;

	public VariableManagerCell(final Variable v, final int index) {
		this.variable = v;
		this.index = index;
	}

	public int getIndex() {
		return this.index;
	}
	public int getValue() {
		return this.variable.getValueAt(this.index);
	}

	public Variable getVariable() {
		return this.variable;
	}
	public boolean isValid() {
		return this.index < this.variable.getValueCount();
	}

	public boolean isWithdrawn() {
		return this.isValid() && this.variable.isWithdrawnValueAt(this.index);
	}

	public void setIndex(final int index) {
		this.index = index;
	}

	public void setVariable(final Variable v) {
		this.variable = v;
	}

	public String toString() {
		if (!this.isValid()) {
			return "";
		}
		return "" + this.getValue();
	}
};
