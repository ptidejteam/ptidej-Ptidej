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
package ptidej.ui.layout.repository;

import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;
import ptidej.ui.Constants;
import ptidej.ui.kernel.AbstractInheritance;
import ptidej.ui.kernel.Constituent;
import ptidej.ui.kernel.Element;
import ptidej.ui.kernel.Entity;
import ptidej.ui.kernel.Interface;
import ptidej.ui.layout.IUILayout;

public final class SimpleLayout implements IUILayout {
	/*
	 * Compares its two arguments for order.  Returns a negative integer,
	 * zero, or a positive integer as the first argument is less than, equal
	 * to, or greater than the second.<p>
	 *
	 * The implementor must ensure that <tt>sgn(compare(x, y)) ==
	 * -sgn(compare(y, x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
	 * implies that <tt>compare(x, y)</tt> must throw an exception if and only
	 * if <tt>compare(y, x)</tt> throws an exception.)<p>
	 *
	 * The implementor must also ensure that the relation is transitive:
	 * <tt>((compare(x, y)&gt;0) &amp;&amp; (compare(y, z)&gt;0))</tt> implies
	 * <tt>compare(x, z)&gt;0</tt>.<p>
	 *
	 * Finally, the implementer must ensure that <tt>compare(x, y)==0</tt>
	 * implies that <tt>sgn(compare(x, z))==sgn(compare(y, z))</tt> for all
	 * <tt>z</tt>.<p>
	 *
	 * It is generally the case, but <i>not</i> strictly required that 
	 * <tt>(compare(x, y)==0) == (x.equals(y))</tt>.  Generally speaking,
	 * any comparator that violates this condition should clearly indicate
	 * this fact.  The recommended language is "Note: this comparator
	 * imposes orderings that are inconsistent with equals."
	 * 
	 * @return a negative integer, zero, or a positive integer as the
	 *        first argument is less than, equal to, or greater than the
	 *       second. 
	 * @throws ClassCastException if the arguments' types prevent them from
	 *        being compared by this Comparator.
	 */
	public static final class SubclassesLastComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			Entity dEntity1 = (Entity) o1;
			Entity dEntity2 = (Entity) o2;

			boolean dEntity1HasSuperclasses =
				dEntity1
					.getSourceEntity()
					.getIteratorOnInheritedEntities()
					.hasNext();
			boolean dEntity2HasSuperclasses =
				dEntity2
					.getSourceEntity()
					.getIteratorOnInheritedEntities()
					.hasNext();

			if (dEntity1HasSuperclasses == dEntity2HasSuperclasses) {
				return 0;
			}
			if (dEntity1HasSuperclasses && !dEntity2HasSuperclasses) {
				if (dEntity1.isSuper()) {
					return 0;
				}
				return 1;
			}
			if (!dEntity1HasSuperclasses && dEntity2HasSuperclasses) {
				if (dEntity2.isSuper()) {
					return 0;
				}
				return -1;
			}

			throw new RuntimeException();
		}
	}
	public static final class SuperclassesFirstComparator implements Comparator {
		public int compare(Object o1, Object o2) {
			Entity dEntity1 = (Entity) o1;
			Entity dEntity2 = (Entity) o2;

			if (dEntity1.isSuper() == dEntity2.isSuper()) {
				return 0;
			}
			if (dEntity1.isSuper() && !dEntity2.isSuper()) {
				return -1;
			}
			if (!dEntity1.isSuper() && dEntity2.isSuper()) {
				return 1;
			}

			throw new RuntimeException();
		}
	}
	public static final class SuperHierarchyCloseComparator implements
			Comparator {

		private static String noSuperclass = "";

		public int compare(Object o1, Object o2) {
			Entity dEntity1 = (Entity) o1;
			String dEntity1Name = dEntity1.getName();
			String dEntity1SuperclassName =
				dEntity1
					.getSourceEntity()
					.getIteratorOnInheritedEntities()
					.hasNext() ? ((Entity) dEntity1
					.getSourceEntity()
					.getIteratorOnInheritedEntities()
					.next()).getName() : noSuperclass;

			Entity dEntity2 = (Entity) o2;
			String dEntity2Name = dEntity2.getName();
			String dEntity2SuperclassName =
				dEntity2
					.getSourceEntity()
					.getIteratorOnInheritedEntities()
					.hasNext() ? ((Entity) dEntity2
					.getSourceEntity()
					.getIteratorOnInheritedEntities()
					.next()).getName() : noSuperclass;

			if (!dEntity1SuperclassName.equals(noSuperclass)
					&& !dEntity2SuperclassName.equals(noSuperclass)) {

				if (dEntity1SuperclassName.equals(dEntity2SuperclassName)) {
					if (dEntity1.isSuper()) {
						return 1;
					}
					if (dEntity2.isSuper()) {
						return -1;
					}
					return dEntity1Name.compareTo(dEntity2Name);
				}
				else if (dEntity1SuperclassName.equals(dEntity2Name)) {
					return 1;
				}
				else if (dEntity2SuperclassName.equals(dEntity1Name)) {
					return -1;
				}

				if (dEntity1.isSuper() && dEntity2.isSuper()) {
					return 0;
				}
				if (dEntity1.isSuper() && !dEntity2.isSuper()) {
					return -1;
				}
				if (dEntity2.isSuper() && !dEntity1.isSuper()) {
					return 1;
				}

				return -dEntity1SuperclassName
					.compareTo(dEntity2SuperclassName);
			}
			else if (dEntity1SuperclassName.equals(noSuperclass)
					&& dEntity2SuperclassName.equals(noSuperclass)) {

				if (dEntity1.isSuper() && dEntity2.isSuper()) {
					return 0;
				}
				else if (dEntity1.isSuper() && !dEntity2.isSuper()) {
					return -1;
				}
				else if (dEntity2.isSuper() && !dEntity1.isSuper()) {
					return 1;
				}

				return dEntity1Name.compareTo(dEntity2Name);
			}
			else if (dEntity1SuperclassName.equals(noSuperclass)
					|| dEntity2SuperclassName.equals(noSuperclass)) {

				/*
				if (dEntity2SuperclassName.equals(dEntity1Name)) {
				    return -1;
				}
				else if (dEntity1SuperclassName.equals(dEntity2Name)) {
				    return 1;
				}
				
				if (!dEntity2SuperclassName.equals(noSuperclass) && !dEntity1.isSuper()) {
				    return 1;
				}
				else if (!dEntity1SuperclassName.equals(noSuperclass) && !dEntity2.isSuper()) {
				    return -1;
				}
				
				if (dEntity1.isSuper() == dEntity2.isSuper()) {
				    return 0;
				}
				else if (dEntity1.isSuper()) {
				    return -1;
				}
				else if (dEntity2.isSuper()) {
				    return 1;
				}
				*/
				/*
				    3 (2) = Superclass
				    2 (3) = Superclass and subclass of a superclass
				    1     = Subclass
				    0     = Independant
				*/
				int weight1 = 0;
				int weight2 = 0;

				weight1 =
					weight1
							+ (!dEntity1SuperclassName.equals(noSuperclass) ? 1
									: 0);
				weight1 = weight1 + (dEntity1.isSuper() ? 2 : 0);
				if (weight1 == 2) {
					weight1 = 3;
				}
				if (weight1 == 3) {
					weight1 = 2;
				}

				weight2 =
					weight2
							+ (!dEntity2SuperclassName.equals(noSuperclass) ? 1
									: 0);
				weight2 = weight2 + (dEntity2.isSuper() ? 2 : 0);
				if (weight2 == 2) {
					weight2 = 3;
				}
				if (weight2 == 3) {
					weight2 = 2;
				}

				return weight2 - weight1;
			}

			throw new RuntimeException();
		}
	}
	private Point checkUnderneath(Entity[] allDEntities, Entity dEntity) {
		int xThis1 = dEntity.getPosition().x;
		int yThis1 =
			this
				.checkUnrelatedY(allDEntities, dEntity, dEntity.getPosition().y);

		// Now, I want to have a nicer display when this entity is a subclass
		// of another entity. I take all the superclasses and superinterfaces.
		final AbstractInheritance[] superEntities = dEntity.getHierarchies();
		for (int i = 0; i < superEntities.length; i++) {
			final Entity dSuperEntity =
				dEntity.getHierarchies()[i].getTargetEntity();

			// TODO: This test should not be needed when I will deal
			// only with DEntities, not with PEntities anymore.
			if (dSuperEntity != null) {
				xThis1 =
					Math.max(xThis1, dSuperEntity.getPosition().x
							+ dSuperEntity.getDimension().width / 2
							+ Constants.GAP_BETWEEN_ENTITIES.width);
			}

			xThis1 =
				Math.min(xThis1, this.checkUnrelatedX(
					allDEntities,
					dEntity,
					xThis1));
		}

		//	// If I am a Class, then I must check my interfaces as well.
		//	if (dEntity instanceof Class) {
		//		superEntities =
		//			new IEntity[pClass.listOfImplementedEntities().size()];
		//		pClass.listOfImplementedEntities().toArray(superPEntities);
		//
		//		for (int i = 0; i < superEntities.length; i++) {
		//			Entity dSuperEntity =
		//				dEntity.findCorrespondingEntity(superPEntities[i]);
		//
		//			// TODO: This test should not be needed when I will deal
		//			// only with DEntities, not with PEntities anymore.
		//			if (dSuperEntity != null) {
		//				xThis1 =
		//					Math.max(
		//						xThis1,
		//						dSuperEntity.getPosition().x
		//							+ dSuperEntity.getDimension().width / 2
		//							+ Constants.GAP_BETWEEN_ENTITIES.width);
		//			}
		//
		//			xThis1 =
		//				Math.min(
		//					xThis1,
		//					this.checkUnrelatedX(allDEntities, dEntity, xThis1));
		//		}
		//	}

		// If I am a Interface, then I must be shifted anyway because of mutliple inheritance.
		if (dEntity instanceof Interface) {
			xThis1 = this.checkUnrelatedX(allDEntities, dEntity, xThis1);
		}

		return new Point(xThis1, yThis1);
	}
	private int checkUnrelatedX(
		Entity[] allDEntities,
		Entity dEntity,
		int xThis1) {
		int yThis1 = dEntity.getPosition().y;

		// First, I do "as if" this entity has nothing to do with
		// the other entities being displayed.
		for (int i = 0; i < allDEntities.length
				&& !allDEntities[i].equals(dEntity); i++) {

			int x1 = allDEntities[i].getPosition().x;
			int y1 = allDEntities[i].getPosition().y;

			if (allDEntities[i] instanceof Entity) {
				// I compute the interesection between the two rectangles;
				int xInt1 = Math.max(xThis1, x1);
				int yInt1 = Math.max(yThis1, y1);

				int xThis2 = xThis1 + dEntity.getDimension().width;
				int yThis2 = yThis1 + dEntity.getDimension().height;
				int x2 = x1 + allDEntities[i].getDimension().width;
				int y2 = y1 + allDEntities[i].getDimension().height;

				int xInt2 = Math.min(xThis2, x2);
				int yInt2 = Math.min(yThis2, y2);

				if (xInt1 <= xInt2 || yInt1 <= yInt2) {
					if (((Entity) allDEntities[i]).isSuper()) {
						xThis1 =
							Math.max(xThis1, x1
									+ allDEntities[i].getDimension().width / 2
									+ Constants.GAP_BETWEEN_ENTITIES.width);
					}
				}
			}
		}

		return xThis1;
	}
	private int checkUnrelatedY(
		Entity[] allDEntities,
		Entity dEntity,
		int yThis1) {
		int xThis1 = dEntity.getPosition().x;

		// First, I do "as if" this entity has nothing to do with
		// the other entities being displayed.
		for (int i = 0; i < allDEntities.length
				&& !allDEntities[i].equals(dEntity); i++) {

			int x1 = allDEntities[i].getPosition().x;
			int y1 = allDEntities[i].getPosition().y;

			if (allDEntities[i] instanceof Entity) {
				// I compute the interesection between the two rectangles;
				int xInt1 = Math.max(xThis1, x1);
				int yInt1 = Math.max(yThis1, y1);

				int xThis2 = xThis1 + dEntity.getDimension().width;
				int yThis2 = yThis1 + dEntity.getDimension().height;
				int x2 = x1 + allDEntities[i].getDimension().width;
				int y2 = y1 + allDEntities[i].getDimension().height;

				int xInt2 = Math.min(xThis2, x2);
				int yInt2 = Math.min(yThis2, y2);

				if (xInt1 <= xInt2 || yInt1 <= yInt2) {
					yThis1 =
						Math.max(yThis1, y2
								+ Constants.GAP_BETWEEN_ENTITIES.height);
				}
			}
		}

		return yThis1;
	}
	public Constituent[] doLayout(Entity[] someEntities) {
		final Entity[] entities = new Entity[someEntities.length];
		System.arraycopy(someEntities, 0, entities, 0, someEntities.length);

		// Now that I have everything in my hand (entities, elements, hierarchies),
		// I can safely build a nice layout (?).
		Arrays.sort(entities, new SuperclassesFirstComparator());
		Arrays.sort(entities, new SubclassesLastComparator());

		// TODO: Here I sort again the elements to put the subclasses as close
		// as possible to their superclasses BUT this would be better
		// if I could use a Comparator...
		// Arrays.sort(allDEntities[i], new SuperHierarchyCloseComparator());
		{
			Vector temp = new Vector();
			Entity dEntity = null;
			Entity dSuperclass = null;
			int lastEntity = entities.length - 1;

			if (lastEntity > -1) {
				for (int i = 0; i <= lastEntity; i++) {
					temp.addElement(entities[i]);
				}

				int stop = 0;
				while ((dEntity = ((Entity) temp.elementAt(lastEntity)))
					.getHierarchies().length > 0
						&& stop < entities.length) {

					dSuperclass =
						(Entity) dEntity.getHierarchies()[0].getOriginEntity();
					temp.removeElementAt(lastEntity);
					int j = 0;
					// TODO: Because the methods clone() defined on AbstractLevelModel, Entity and Element do
					// not update correctly the links among the cloned PElements and the cloned
					// PEntities, I must use a patch here: use the name instead of:
					//     while ((Entity) temp.elementAt(j) != dSuperclass) {
					while (j < temp.size()
					// This test may be unnecessary!!!!?
							&& !((Entity) temp.elementAt(j)).getName().equals(
								dSuperclass.getName())) {

						j++;
					}
					if (j == temp.size()) {
						temp.addElement(dEntity);
					}
					else {
						temp.insertElementAt(dEntity, j + 1);
					}
					stop++;
				}
				temp.copyInto(entities);
			}
		}

		// Now I build the graphical parts...
		//     build(): computes the coordinates, sizes and builds the primitives
		//     paint(Graphics) : only (faaaast!) displays the primitives
		for (int i = 0; i < entities.length; i++) {
			entities[i]
				.setPosition(this.checkUnderneath(entities, entities[i]));
			entities[i].build();
		}

		// Now that I have the DEntities and they have been placed
		// (=> their coordinates are known) I can compute the
		// other elements coordinates safely.
		final Vector allElements = new Vector();
		for (int i = 0; i < entities.length; i++) {
			Element[] dElements = ((Entity) entities[i]).getElements();
			for (int j = 0; j < dElements.length; j++) {
				allElements.addElement(dElements[j]);
				dElements[j].build();
			}

			AbstractInheritance[] dHierarchies =
				((Entity) entities[i]).getHierarchies();
			for (int j = 0; j < dHierarchies.length; j++) {
				allElements.addElement(dHierarchies[j]);
				dHierarchies[j].build();
			}
		}

		// All the pattern's constituents (Entities and Elements).
		final Constituent[] temp =
			new Constituent[entities.length + allElements.size()];
		allElements.toArray(temp);
		System
			.arraycopy(entities, 0, temp, allElements.size(), entities.length);

		return temp;
	}
	public String getName() {
		return "Simple";
	}
}
