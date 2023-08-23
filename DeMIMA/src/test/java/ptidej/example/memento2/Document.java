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
package ptidej.example.memento2;

public class Document {
	private class PrivateDocumentMemento extends DocumentMemento {
		private final Element[] previousElements;
		private final int previousNbOfElements;

		public PrivateDocumentMemento(
			final Element[] previousElements,
			final int previousNbOfElements) {

			this.previousElements = previousElements;
			this.previousNbOfElements = previousNbOfElements;
		}

		public Element[] getPreviousElements() {
			return this.previousElements;
		}
		public int getPreviousNbOfElements() {
			return this.previousNbOfElements;
		}
	}
	private Element[] elements;

	private int nbOfElements;
	public void addElement(final Element e) {
		if (this.elements == null) {
			this.elements = new Element[4];
		}
		else if (this.nbOfElements == this.elements.length) {
			final Element[] temp = new Element[this.nbOfElements * 2];
			System.arraycopy(this.elements, 0, temp, 0, this.nbOfElements);
			this.elements = temp;
		}
		this.elements[this.nbOfElements] = e;
		this.nbOfElements++;
	}
	public DocumentMemento createMemento() {
		return new PrivateDocumentMemento(this.elements, this.nbOfElements);
	}
	public Element getElement(final int pos) {
		return this.elements[pos];
	}
	public void printAll() {
		for (int i = 0; i < this.nbOfElements; i++) {
			this.elements[i].print();
		}
	}
	public Element removeElement(final Element e) {
		return null;
	}
	public void setMemento(final DocumentMemento documentMemento) {
		this.elements =
			((PrivateDocumentMemento) documentMemento).getPreviousElements();
		this.nbOfElements =
			((PrivateDocumentMemento) documentMemento)
				.getPreviousNbOfElements();
	}
}
