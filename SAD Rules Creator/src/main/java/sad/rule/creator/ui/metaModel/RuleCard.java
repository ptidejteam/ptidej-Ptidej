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
 * @author Amandine & Jean-Luc
 *
 */
public final class RuleCard {
	private String name;
	private ListRules listRules;
	public RuleCard() {
	}
	public RuleCard(final String aName, final ListRules aListRules) {
		this.name = aName;
		this.listRules = aListRules;
	}

	/**
	 * @return Returns the listRules.
	 */
	public ListRules getListRules() {
		return this.listRules;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * @param listRules The listRules to set.
	 */
	public void setListRules(final ListRules listRules) {
		this.listRules = listRules;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(final String name) {
		this.name = name;
	}
}
