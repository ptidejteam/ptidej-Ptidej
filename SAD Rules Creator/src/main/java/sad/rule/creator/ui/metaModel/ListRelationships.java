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
public final class ListRelationships {
	private Vector relationships;
	public ListRelationships() {
	}
	public ListRelationships(final Vector aRelationships) {
		this.relationships = aRelationships;
	}
	/**
	 * @return Returns the relationships.
	 */
	public Vector getRelationships() {
		return this.relationships;
	}
	/**
	 * @param relationships The relationships to set.
	 */
	public void setRelationships(final Vector relationships) {
		this.relationships = relationships;
	}

}
