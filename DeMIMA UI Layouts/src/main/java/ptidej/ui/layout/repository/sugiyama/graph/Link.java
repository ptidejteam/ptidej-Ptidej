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

/**
 * @author kahlamoh
 * @since 2006/07/21
 *
 */
public class Link {
	private final int linkType;
	private final INode parent;
	private final INode child;
	public Link(final int aLinkType, final INode aParent, final INode aChild) {
		this.linkType = aLinkType;
		this.parent = aParent;
		this.child = aChild;
	}
	public INode getParent() {
		return this.parent;
	}
	public INode getChild() {
		return this.child;
	}
	public int getLinkType() {
		return this.linkType;
	}
}
