/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver.claire;

import org.apache.commons.lang3.NotImplementedException;
import padl.motif.visitor.IMotifWalker;

import java.util.Iterator;

public class DomainGenerator3Custom extends
		DomainGenerator2Custom implements IMotifWalker {

	protected String getListDeclaration() {
		return "list<Entity>";
	}
	protected String getListOfListPrefix() {
		return "list<list<Entity>>(";
	}
	protected String getListOfListSuffix() {
		return ")";
	}
	protected String getListPrefix() {
		return "list<Entity>(";
	}
	protected String getListSuffix() {
		return ")";
	}
	public String getName() {
		return "PtidejSolver 3 Custom Domain";
	}

	public void traverse(Iterator iterator) {
		throw new NotImplementedException();
	}
}
