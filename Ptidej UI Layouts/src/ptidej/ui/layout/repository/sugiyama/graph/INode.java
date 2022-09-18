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
/**
 * 
 */
package ptidej.ui.layout.repository.sugiyama.graph;

import ptidej.ui.kernel.Entity;
import ptidej.ui.layout.repository.sugiyama.Children;
import ptidej.ui.layout.repository.sugiyama.Parents;

/**
 * @author kahlamoh
 *
 */
public interface INode {
	public void addChild(final INode aChild);
	public void addParent(final INode aParent);
	public boolean removeChild(final INode aChild);
	public void removeParent(final INode aParent);
	public Entity getEntity();
	public INode[] getTabChildren();
	public INode[] getTabParents();
	public Children getChildren();
	public Parents getParents();
	public int getLevel();
	public void setPosition(int x, int y);
	public int getX();
	public int getY();
}
