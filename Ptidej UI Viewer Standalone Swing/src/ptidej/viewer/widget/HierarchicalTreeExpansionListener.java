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

import java.awt.Dimension;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.tree.ExpandVetoException;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/10/01
 */
public class HierarchicalTreeExpansionListener
	implements TreeWillExpandListener, javax.swing.event.TreeExpansionListener {

	private double width;

	public void treeWillCollapse(final TreeExpansionEvent event)
		throws ExpandVetoException {
	}
	public void treeWillExpand(final TreeExpansionEvent event)
		throws ExpandVetoException {

		this.width = ((JTree) event.getSource()).getSize().getWidth();
	}
	public void treeCollapsed(final TreeExpansionEvent event) {
	}
	public void treeExpanded(final TreeExpansionEvent event) {
		final JTree tree = ((JTree) event.getSource());
		tree.setMaximumSize(
			new Dimension(
				(int) this.width,
				(int) tree.getSize().getHeight()));
	}
}
