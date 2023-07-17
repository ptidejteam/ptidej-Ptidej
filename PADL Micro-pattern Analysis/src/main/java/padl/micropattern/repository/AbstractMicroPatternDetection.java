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
package padl.micropattern.repository;

import java.util.HashSet;
import java.util.Set;
import padl.kernel.IFirstClassEntity;

/**
 * @author tanterij
 */
abstract class AbstractMicroPatternDetection {
	private Set entities = new HashSet();

	public void addEntities(final IFirstClassEntity anEntity) {
		this.entities.add(anEntity);
	}
	public abstract boolean detect(final IFirstClassEntity anEntity);
	public Set getEntities() {
		return this.entities;
	}
	public String getHelpURL() {
		return "http://dl.acm.org/citation.cfm?id=1094819";
	}
	public long getNumberOfEntities() {
		return this.entities.size();
	}
}
