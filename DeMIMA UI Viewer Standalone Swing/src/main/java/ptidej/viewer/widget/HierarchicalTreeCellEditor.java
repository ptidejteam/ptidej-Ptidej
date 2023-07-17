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

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/10/03
 */
public class HierarchicalTreeCellEditor implements TreeCellEditor {
	public Component getTreeCellEditorComponent(
		final JTree tree,
		final Object value,
		final boolean isSelected,
		final boolean expanded,
		final boolean leaf,
		final int row) {

		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		return (Component) node.getUserObject();
	}
	public void cancelCellEditing() {
	}
	public boolean stopCellEditing() {
		return false;
	}
	public Object getCellEditorValue() {
		return this;
	}
	public boolean isCellEditable(final EventObject anEvent) {
		if (anEvent instanceof MouseEvent) {
			final MouseEvent mevt = (MouseEvent) anEvent;

			if (mevt.getClickCount() == 1) {
				return true;
			}
		}
		return false;
	}
	public boolean shouldSelectCell(final EventObject anEvent) {
		return false;
	}
	public void addCellEditorListener(final CellEditorListener l) {
	}
	public void removeCellEditorListener(final CellEditorListener l) {
	}
}
