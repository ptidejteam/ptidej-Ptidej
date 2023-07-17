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
package ptidej.ui.layout.repository.sugiyama;

import java.util.ArrayList;
import java.util.List;
import ptidej.ui.layout.repository.sugiyama.graph.INode;

/**
 * @author kahlamoh
 * @since 24/07/2006
 * * This is like a list but it adds
 * more functions and flexibilities!
 * 
 *  It's homogene too!
 *
 */
// TODO: Is this class really needed, with respect to Couple, Node, and Level?
public class Parents {
	private List parents;
	// Channels!
	private ArrayList channels;

	public Parents() {
		this.parents = new ArrayList();
		this.channels = new ArrayList();
	}
	public void add(final INode aParent) {
		this.parents.add(aParent);
		// -1 for not set yet
		this.channels.add(Integer.valueOf(-1));
	}
	public boolean remove(final INode aParent) {
		// here a special attention to keep informations
		// coherents
		int parentIndex = this.parents.indexOf(aParent);
		if (parentIndex >= 0) {
			this.channels.remove(parentIndex);
			return this.parents.remove(aParent);
		}
		return false;
	}
	public void clear() {
		// Consistency!
		this.channels.clear();
		this.parents.clear();
	}
	public boolean contains(final INode aParent) {
		return this.parents.contains(aParent);
	}
	public boolean isEmpty() {
		return this.parents.isEmpty();
	}
	public INode[] getParents() {
		// TODO Use proper method provided by class library
		final INode[] tabParent = new INode[this.parents.size()];
		for (int i = 0; i < tabParent.length; i++) {
			tabParent[i] = (INode) this.parents.get(i);
		}

		return tabParent;
	}
	public int getNbParents() {
		return this.parents.size();
	}
	public void setChannel(final INode aParent, int aChannel) {
		int parentIndex = this.parents.indexOf(aParent);
		// if parent not found! 
		// return -1
		this.channels.set(parentIndex, Integer.valueOf(aChannel));
	}
	public int getChannel(final INode aParent) {
		int parentIndex = this.parents.indexOf(aParent);
		if (parentIndex >= 0) {
			Integer channel = (Integer) this.channels.get(parentIndex);
			return channel.intValue();
		}
		return -1;
	}
}
