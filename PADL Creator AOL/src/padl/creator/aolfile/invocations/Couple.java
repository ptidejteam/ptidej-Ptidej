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
package padl.creator.aolfile.invocations;

import padl.kernel.IFirstClassEntity;
import padl.kernel.IOperation;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/02/02
 */
public class Couple {
	private final IFirstClassEntity firstClassEntity;
	private final IOperation method;

	public Couple(final IFirstClassEntity anEntity, final IOperation aMethod) {
		this.firstClassEntity = anEntity;
		this.method = aMethod;
	}
	public IFirstClassEntity getDeclaringEntity() {
		return this.firstClassEntity;
	}
	public IOperation getModifiedMethod() {
		return this.method;
	}
}
