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
package epi.solver.problem;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import padl.kernel.IAbstractLevelModel;
import padl.kernel.ICodeLevelModel;
import padl.kernel.IFirstClassEntity;
import ptidej.solver.fingerprint.ReducedDomainBuilder;

/**
 * @author OlivierK
 */
public abstract class Problem {
	protected Hashtable domain;
	protected ReducedDomainBuilder rdg;

	public Problem(final ICodeLevelModel aCodeLevelModel) {
		this.rdg = new ReducedDomainBuilder(aCodeLevelModel);
		this.domain = new Hashtable();
	}

	public Hashtable getDomains() {
		return this.domain;
	}

	protected List getSimpleConstituentList(
		final IAbstractLevelModel anAbstractModel) {

		final List simpleList = new ArrayList();
		final Iterator entities = anAbstractModel.getIteratorOnConstituents();
		while (entities.hasNext()) {
			simpleList.add(((IFirstClassEntity) entities.next()).getID());
		}
		return simpleList;
	}

	public List getVariableDomain(final String var) {
		return (List) this.domain.get(var.toLowerCase());
	}
}
