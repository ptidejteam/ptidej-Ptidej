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

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/09/01
 */
public class HierarchicalTreeCellRenderer
	implements javax.swing.tree.TreeCellRenderer {

	public Component getTreeCellRendererComponent(
		final JTree tree,
		final Object value,
		final boolean isSelected,
		final boolean isExpanded,
		final boolean isLeaf,
		final int row,
		final boolean HasFocus) {

		// Find out which node we are rendering and get its text
		final DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		final JComponent component = (JComponent) node.getUserObject();

		return component;
	}
}
