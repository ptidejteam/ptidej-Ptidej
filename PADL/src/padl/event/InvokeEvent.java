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
package padl.event;

import padl.kernel.IConstituentOfOperation;
import padl.kernel.IContainer;

public class InvokeEvent implements IEvent {
	private final IContainer aContainer;
	private final IConstituentOfOperation anInvoke;

	public InvokeEvent(
		final IContainer aContainer,
		final IConstituentOfOperation anInvoke) {

		this.aContainer = aContainer;
		this.anInvoke = anInvoke;
	}
	public IConstituentOfOperation getInvoke() {
		return this.anInvoke;
	}
	public IContainer getContainer() {
		return this.aContainer;
	}
}
