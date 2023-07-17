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

import padl.kernel.IConstituentOfEntity;
import padl.kernel.IContainer;

public final class ElementEvent implements IEvent {
	private final IContainer aContainer;
	private final IConstituentOfEntity anElement;

	public ElementEvent(
		final IContainer aContainer,
		final IConstituentOfEntity anElement) {

		this.aContainer = aContainer;
		this.anElement = anElement;
	}
	public IConstituentOfEntity getElement() {
		return this.anElement;
	}
	public IContainer getContainer() {
		return this.aContainer;
	}
}
