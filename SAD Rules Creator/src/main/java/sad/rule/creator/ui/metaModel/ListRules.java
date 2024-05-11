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

import java.util.Vector;

/**
 * @author Family
 *
 */
public final class ListRules {

	private Vector rules;
	public ListRules() {
	}
	public ListRules(final Vector aRules) {
		this.rules = aRules;
	}
	/**
	 * @return Returns the rules.
	 */
	public Vector getRules() {
		return this.rules;
	}
	/**
	 * @param rules The rules to set.
	 */
	public void setRules(final Vector rules) {
		this.rules = rules;
	}
}
