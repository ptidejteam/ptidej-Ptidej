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
package padl.example.composite1;

import java.util.Enumeration;
import java.util.Vector;

public class Document {
	private final Vector elements = new Vector();
	public void addComponent(final Element e) {
		this.elements.addElement(e);
	}
	public Element getComponent(final int pos) {
		return (Element) this.elements.elementAt(pos);
	}
	public void printAll() {
		final Enumeration e = this.elements.elements();
		while (e.hasMoreElements()) {
			((Element) e.nextElement()).print();
		}
	}
	public Element removeComponent(final Element e) {
		return null;
	}
}
