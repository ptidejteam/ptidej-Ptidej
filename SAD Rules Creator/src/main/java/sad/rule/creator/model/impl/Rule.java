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
package sad.rule.creator.model.impl;

import java.util.Iterator;
import sad.rule.creator.model.IConstituent;
import sad.rule.creator.model.IRule;
import sad.rule.creator.model.IVisitor;

/**
 * @author Pierre Leduc
 */
public class Rule extends Container implements IRule {
	public Rule(final String anID) {
		super(anID);
	}

	public void accept(final IVisitor aVisitor) {
		aVisitor.open(this);

		final Iterator iterator = this.getIteratorOnConstituents();
		while (iterator.hasNext()) {
			((IConstituent) iterator.next()).accept(aVisitor);
		}

		aVisitor.close(this);
	}

}
