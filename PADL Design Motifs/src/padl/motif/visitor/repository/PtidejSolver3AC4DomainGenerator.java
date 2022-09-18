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
package padl.motif.visitor.repository;

import padl.motif.visitor.IMotifWalker;

public class PtidejSolver3AC4DomainGenerator extends
		PtidejSolver2AC4DomainGenerator implements IMotifWalker {

	protected String getCoupleDeclaration() {
		return "list<tuple(integer,integer)>(";
	}
	protected String getCouplePrefix() {
		return "tuple(";
	}
	protected String getListDeclaration() {
		return "list<Entity>";
	}
	protected String getListPrefix() {
		return "list<Entity>(";
	}
	protected String getListSuffix() {
		return ")";
	}
	public String getName() {
		return "PtidejSolver 3 AC4 Domain";
	}
}
