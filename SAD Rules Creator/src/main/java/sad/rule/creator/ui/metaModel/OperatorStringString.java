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
public final class OperatorStringString {

	private String name;

	private String firstString;
	private String secondString;
	public OperatorStringString(
		final String aName,
		final String aFirstString,
		final String aSecondString) {
		this.name = aName;
		this.firstString = aFirstString;
		this.secondString = aSecondString;
	}

	/**
	 * @return Returns the firstString.
	 */
	public String getFirstString() {
		return this.firstString;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * @return Returns the secondString.
	 */
	public String getSecondString() {
		return this.secondString;
	}
	/**
	 * @param firstString The firstString to set.
	 */
	public void setFirstString(final String firstString) {
		this.firstString = firstString;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(final String name) {
		this.name = name;
	}
	/**
	 * @param secondString The secondString to set.
	 */
	public void setSecondString(final String secondString) {
		this.secondString = secondString;
	}

}
