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
public final class Rule {

	private String name;
	private ContentRule contentRule;

	public Rule() {
	}
	public Rule(final String aName, final ContentRule aContentRule) {
		this.name = aName;
		this.contentRule = aContentRule;
	}

	/**
	 * @return Returns the contentRule.
	 */
	public ContentRule getContentRule() {
		return this.contentRule;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return this.name;
	}
	/**
	 * @param contentRule The contentRule to set.
	 */
	public void setContentRule(final ContentRule contentRule) {
		this.contentRule = contentRule;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(final String name) {
		this.name = name;
	}

}
