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
package ptidej.ui.kernel;

import java.util.Iterator;
import padl.kernel.IFirstClassEntity;
import padl.kernel.IInterface;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.primitive.IPrimitiveFactory;

public class Interface extends Entity {
	public Interface(
		final IPrimitiveFactory aPrimitiveFactory,
		final Builder aBuilder,
		final IInterface anInterface) {

		super(aPrimitiveFactory, aBuilder, anInterface);
	}
	protected void computeHierarchies() {
		super.computeHierarchies();

		final Iterator iterator =
			((IInterface) this.getFirstClassEntity())
				.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			final Entity entity =
				this
					.getBuilder()
					.getEntity((IFirstClassEntity) iterator.next());

			// Yann 2004/12/16: Clean!
			// Ghosts are taken care of when doint the layout and painting
			// only, this removes many ugly checks like the following:
			//	if (!(entity instanceof Ghost
			//		&& (this.getVisibleElements()
			//			& IVisibility.GHOST_ENTITIES_DISPLAY)
			//			== 0)) {

			this.addHierarchy(new Specialisation(
				this.getPrimitiveFactory(),
				this,
				entity));
		}
	}
	protected String getStereotype() {
		return "<<interface>>\n";
	}
}
