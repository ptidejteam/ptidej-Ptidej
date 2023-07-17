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
public final class ContentRule {
	private ListRelationships listRelationships;
	private ListAttributes listAttributes;

	private OperatorStringString operatorStringString;
	public ContentRule() {
	}
	public ContentRule(
		final OperatorStringString anOperatorStringString,
		final ListRelationships aListRelationships,
		final ListAttributes aListAttributes) {
		this.operatorStringString = anOperatorStringString;
		this.listAttributes = aListAttributes;
		this.listRelationships = aListRelationships;
	}
	/**
	 * @return Returns the listAttributes.
	 */
	public ListAttributes getListAttributes() {
		return this.listAttributes;
	}
	/**
	 * @return Returns the listRelationships.
	 */
	public ListRelationships getListRelationships() {
		return this.listRelationships;
	}

	/**
	 * @return Returns the operatorStringString.
	 */
	public OperatorStringString getOperatorStringString() {
		return this.operatorStringString;
	}
	/**
	 * @param listAttributes The listAttributes to set.
	 */
	public void setListAttributes(final ListAttributes listAttributes) {
		this.listAttributes = listAttributes;
	}
	/**
	 * @param listRelationships The listRelationships to set.
	 */
	public void setListRelationships(final ListRelationships listRelationships) {
		this.listRelationships = listRelationships;
	}
	/**
	 * @param operatorStringString The operatorStringString to set.
	 */
	public void setOperatorStringString(
		final OperatorStringString operatorStringString) {
		this.operatorStringString = operatorStringString;
	}
}
