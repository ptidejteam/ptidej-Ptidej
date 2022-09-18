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
import padl.kernel.IClass;
import padl.kernel.IFirstClassEntity;
import ptidej.ui.kernel.builder.Builder;
import ptidej.ui.primitive.IPrimitiveFactory;

public class Class extends Entity {
	public Class(
		final IPrimitiveFactory aPrimitiveFactory,
		final Builder aBuilder,
		final IClass aClass) {

		super(aPrimitiveFactory, aBuilder, aClass);
	}
	protected void computeHierarchies() {
		super.computeHierarchies();

		Entity entity;
		Iterator iterator;

		iterator =
			((IClass) this.getFirstClassEntity())
				.getIteratorOnInheritedEntities();
		while (iterator.hasNext()) {
			final IFirstClassEntity e =
				(padl.kernel.IFirstClassEntity) iterator.next();
			entity = this.getBuilder().getEntity(e);

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

		iterator =
			((IClass) this.getFirstClassEntity())
				.getIteratorOnImplementedInterfaces();
		while (iterator.hasNext()) {
			entity =
				this.getBuilder().getEntity(
					(padl.kernel.IFirstClassEntity) iterator.next());

			// Yann 2004/12/16: Clean!
			// Ghosts are taken care of when doing the layout and painting
			// only, this removes many ugly checks like the following:
			//	if (!(entity instanceof Ghost
			//		&& (this.getVisibleElements()
			//			& IVisibility.GHOST_ENTITIES_DISPLAY)
			//			== 0)) {

			this.addHierarchy(new Implementation(
				this.getPrimitiveFactory(),
				this,
				entity));
		}
	}
	protected String getStereotype() {
		return "";
	}
}
