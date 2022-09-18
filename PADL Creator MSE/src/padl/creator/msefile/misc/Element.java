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
package padl.creator.msefile.misc;


/**
 * @author guehene
 */
public class Element {
	private final String type;
	private Attribute[] attributes;
	public Element(final String aType) {
		this.type = aType;
	}
	public Element(final String aType, final Attribute[] someAttributes) {
		this(aType);
		this.attributes = someAttributes;
	}
	public Attribute getAttribute(final String name) {
		// Yann 2007/06/10: Cache!
		// I could cache results of this call for 
		// better performance
		final Attribute[] attributes = this.getAttributes();
		for (int i = 0; i < attributes.length; i++) {
			final Attribute attribute = attributes[i];
			if (attribute.getName().equals(name)) {
				return attribute;
			}
		}
		return null;
	}
	public Attribute[] getAttributes() {
		return this.attributes;
	}
	public String getType() {
		return this.type;
	}
	public String toString() {
		final StringBuffer buffer = new StringBuffer();
		buffer.append("element ");
		buffer.append(this.type);
		buffer.append('\n');
		for (int i = 0; i < this.attributes.length; i++) {
			final Attribute attribute = this.attributes[i];
			buffer.append('\t');
			buffer.append(attribute);
			if (i < this.attributes.length - 1) {
				buffer.append('\n');
			}
		}
		return buffer.toString();
	}
}
