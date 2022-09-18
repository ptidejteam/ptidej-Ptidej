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
public class Value {
	private String name;
	private String value;

	public Value() {
		this.name = "<no name>";
		this.value = "<no value>";
	}
	public Value(final String aName) {
		this();
		this.name = aName;
	}
	public Value(final String aName, final String aValue) {
		this(aName);
		this.value = aValue;
	}
	public String getName() {
		return this.name;
	}
	public String getValue() {
		return this.value;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public void setValue(final String value) {
		this.value = value;
	}
	public String toString() {

		final StringBuffer buffer = new StringBuffer();
		buffer.append('(');
		buffer.append(this.name);
		buffer.append(',');
		buffer.append(this.value);
		buffer.append(')');
		return buffer.toString();
	}
}
