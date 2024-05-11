/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.viewer.widget;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.SystemColor;

import javax.swing.JLabel;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/09/01
 */
public class HierarchicalTreeCellLabel extends JLabel {
	private static final long serialVersionUID = 1L;
	private boolean isSelected;

	public HierarchicalTreeCellLabel() {
		this.setBackground(Color.WHITE);
	}
	public boolean isSelected() {
		return this.isSelected;
	}
	public void paint(final Graphics g) {
		// This is a hack to paint the background. Normally a JLabel 
		// can paint its own background, but due to an apparent bug 
		// or limitation in the TreeCellRenderer, the paint method 
		// is required to handle this.
		// Set the correct background colour
		final Color color =
			this.isSelected ? SystemColor.textHighlight : Color.WHITE;
		g.setColor(color);

		// Draw a rectangle in the background of the cell
		g.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

		super.paint(g);
	}
	public void setSelected(final boolean isSelected) {
		this.isSelected = isSelected;
		if (!this.isSelected) {
			this.setForeground(Color.BLACK);
		}
		else {
			this.setForeground(Color.WHITE);
		}
	}
}
