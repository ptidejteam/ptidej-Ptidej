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
package sad.rule.creator.ui.metaModel;

/**
 * @author Family
 *
 */
public final class Attribute {
	private String type;

	private String name;
	private String value;
	public Attribute(final String aType, final String aName, final String aValue) {
		this.type = aType;
		this.name = aName;
		this.value = aValue;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * @return Returns the type.
	 */
	public String getType() {
		return this.type;
	}
	/**
	 * @return Returns the value.
	 */
	public String getValue() {
		return this.value;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(final String name) {
		this.name = name;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(final String type) {
		this.type = type;
	}
	/**
	 * @param value The value to set.
	 */
	public void setValue(final String value) {
		this.value = value;
	}

}
