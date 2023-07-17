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
package sad.rule.creator.model;

/**
 * @author Pierre Leduc
 */
public interface IConstituent {
	public void accept(final IVisitor visitor);
	// TODO: A constituent in a RuleCard is actually a set of codesmells
	// isn't? Like in the def. of the Functional Decomposition. So, we should
	// also adapt the model of IRelationship, for example...
	public String getID();
}
