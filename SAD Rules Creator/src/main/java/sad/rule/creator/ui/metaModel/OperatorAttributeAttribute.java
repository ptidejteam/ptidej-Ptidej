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
public final class OperatorAttributeAttribute {
	private String name;

	private Attribute firstAttribute;
	private Attribute secondAttribute;
	public OperatorAttributeAttribute(
		final String aName,
		final Attribute aFirstAttribute,
		final Attribute aSecondAttribute) {
		this.name = aName;
		this.firstAttribute = aFirstAttribute;
		this.secondAttribute = aSecondAttribute;
	}
	/**
	 * @return Returns the firstAttribute.
	 */
	public Attribute getFirstAttribute() {
		return this.firstAttribute;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * @return Returns the secondAttribute.
	 */
	public Attribute getSecondAttribute() {
		return this.secondAttribute;
	}
	/**
	 * @param firstAttribute The firstAttribute to set.
	 */
	public void setFirstAttribute(final Attribute firstAttribute) {
		this.firstAttribute = firstAttribute;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(final String name) {
		this.name = name;
	}
	/**
	 * @param secondAttribute The secondAttribute to set.
	 */
	public void setSecondAttribute(final Attribute secondAttribute) {
		this.secondAttribute = secondAttribute;
	}

}
