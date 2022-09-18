/*
 * $Id: VariableManagerCellRenderer.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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

import java.awt.Color;
import java.awt.Component;
import java.io.Serializable;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class VariableManagerCellRenderer extends DefaultTableCellRenderer
		implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 181247916687646494L;

	public Component getTableCellRendererComponent(
		final JTable table,
		final Object value,
		final boolean isSelected,
		final boolean hasFocus,
		final int row,
		final int column) {

		final VariableManagerCell cell = (VariableManagerCell) value;
		super.getTableCellRendererComponent(
			table,
			value,
			isSelected,
			hasFocus,
			row,
			column);

		if (cell.isWithdrawn()) {
			super.setForeground(Color.gray);
		}
		return this;
	}
}
