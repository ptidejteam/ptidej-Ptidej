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
public class Attribute {
	private final String name;
	private Value[] values;

	public Attribute(final String aName) {
		this.name = aName;
		this.setValues(new Value[0]);
	}
	public Attribute(final String aName, final Value[] someValues) {
		this.name = aName;
		this.setValues(someValues);
	}
	public String getName() {
		return this.name;
	}
	public Value getValue(final String name) {
		// Yann 2007/06/10: Cache!
		// I could cache results of this call for 
		// better performance
		final Value[] values = this.getValues();
		for (int i = 0; i < values.length; i++) {
			final Value value = values[i];
			if (value.getName().equals(name)) {
				return value;
			}
		}
		return null;
	}
	public Value[] getValues() {

		return this.values;
	}
	public void setValues(final Value[] values) {
		this.values = values;
	}
	public String toString() {

		final StringBuffer buffer = new StringBuffer();
		buffer.append("attribute ");
		buffer.append(this.getName());
		buffer.append('\n');
		for (int i = 0; i < this.getValues().length; i++) {
			final Value value = this.getValues()[i];
			buffer.append("\t\t");
			buffer.append(value);
			if (i < this.getValues().length - 1) {
				buffer.append('\n');
			}
		}

		return buffer.toString();
	}

}
