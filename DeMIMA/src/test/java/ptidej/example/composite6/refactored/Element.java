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
package ptidej.example.composite6.refactored;

public abstract class Element {
	private final StringBuffer text = new StringBuffer();

	public Element(final String aText) {
		this.addText(aText);
	}
	public void addText(final String aText) {
		this.text.append(aText);
	}
	public String getText() {
		return this.text.toString();
	}
	public void print() {
		System.out.println(this.getText());
	}
	public void removeText() {
		this.text.setLength(0);
	}
	public void setText(final String aText) {
		this.removeText();
		this.addText(aText);
	}
}
