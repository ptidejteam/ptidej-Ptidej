/*
 * $Id: VariableManagerTableModel.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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

import java.io.Serializable;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.table.AbstractTableModel;
import fr.emn.oadymppac.Variable;
import fr.emn.oadymppac.VariableManager;

public class VariableManagerTableModel extends AbstractTableModel implements
		Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -916540368849009073L;
	VariableManager manager;
	Vector variables;
	int rowCount;

	public VariableManagerTableModel(final VariableManager manager) {
		this.manager = manager;
		final int cnt = manager.getVariableCount();
		this.variables = new Vector(cnt);
		final Iterator e = manager.iterator();
		for (int i = 0; i < cnt; i++) {
			this.variables.setElementAt(e.next(), i);
		}
		this.rowCount = 0;
	}

	public Class getColumnClass(final int columnIndex) {
		return VariableManagerCell.class;
	}

	public int getColumnCount() {
		return this.variables.size();
	}

	public String getColumnName(final int columnIndex) {
		return this.getVariableAt(columnIndex).getVName();
	}

	public int getRowCount() {
		if (this.rowCount == 0) {
			for (int i = 0; i < this.variables.size(); i++) {
				final Variable v = this.getVariableAt(i);
				if (this.rowCount < v.getValues().length) {
					this.rowCount = v.getValues().length;
				}
			}
		}
		return this.rowCount;
	}

	public Object getValueAt(final int rowIndex, final int columnIndex) {
		final Variable v = this.getVariableAt(columnIndex);

		return new VariableManagerCell(v, rowIndex);
	}

	Variable getVariableAt(final int index) {
		return (Variable) this.variables.elementAt(index);
	}

} // End of class VariableManagerTableModel
