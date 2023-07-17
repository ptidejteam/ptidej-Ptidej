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
import java.awt.Cursor;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;
import ptidej.viewer.ui.DesktopFrame;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/10/03
 */
public class HierarchicalTreeCellCheckbox extends JCheckBox {
	private static final long serialVersionUID = 1L;
	private boolean mustCursorChange;
	private final HierarchicalTreeCell treeCell;

	public HierarchicalTreeCellCheckbox(final HierarchicalTreeCell aTreeCell) {
		this.mustCursorChange = true;
		this.treeCell = aTreeCell;
		this.setBackground(Color.WHITE);
	}
	protected void fireItemStateChanged(final ItemEvent anEvent) {
		if (this.mustCursorChange) {
			DesktopFrame.getInstance().setCursor(
				Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
		}

		super.fireItemStateChanged(anEvent);

		if (this.mustCursorChange) {
			DesktopFrame.getInstance().setCursor(Cursor.getDefaultCursor());
		}
	}
	public HierarchicalTreeCell getHierarchicalTreeCell() {
		return this.treeCell;
	}
	public void mustChangeCursor(boolean mustChangeCursor) {
		this.mustCursorChange = mustChangeCursor;
	}
}
