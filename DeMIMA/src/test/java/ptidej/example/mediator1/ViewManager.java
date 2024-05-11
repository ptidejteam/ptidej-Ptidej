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
package ptidej.example.mediator1;

import java.util.Vector;

public class ViewManager {
	private final Vector elements;
	public ViewManager() {
		this.elements = new Vector();
	}
	public void add(final Widget widget) {
		this.elements.addElement(widget);
	}
	public Widget get(final int index) {
		return (Widget) this.elements.elementAt(index);
	}
	/**
	 * Insert the method's description here.
	 * Creation date: (12/04/2001 15:25:28)
	 */
	public void operation() {
	}
}
