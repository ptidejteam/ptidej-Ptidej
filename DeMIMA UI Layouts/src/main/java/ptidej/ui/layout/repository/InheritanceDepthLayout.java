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
import java.util.Hashtable;
import java.util.Vector;

import ptidej.ui.Constants;
import ptidej.ui.kernel.AbstractInheritance;
import ptidej.ui.kernel.Constituent;
import ptidej.ui.kernel.Element;
import ptidej.ui.kernel.Entity;
import ptidej.ui.layout.IUILayout;
import util.multilingual.MultilingualManager;

public final class InheritanceDepthLayout implements IUILayout {
	private static class DepthAwareDEntity {
		private final Entity dEntity;
		private int depth = -1;
		public DepthAwareDEntity(Entity dEntity) {
			this.dEntity = dEntity;
		}
		public int computeDepth(final Hashtable listOfDepthAwareDEntities) {
			if (this.depth == -1) {
				final AbstractInheritance[] dHierarchies =
					this.dEntity.getHierarchies();
				String superName;
				DepthAwareDEntity superDepthAwareDEntity;

				this.depth = 0;
				for (int i = 0; i < dHierarchies.length; i++) {
					superName = dHierarchies[i].getTargetEntity().getName();
					superDepthAwareDEntity =
						(DepthAwareDEntity) listOfDepthAwareDEntities.get(
							superName);
					if (superDepthAwareDEntity != null) {
						this.depth =
							Math.max(
								this.depth,
								superDepthAwareDEntity.computeDepth(
									listOfDepthAwareDEntities)
									+ 1);
					}
				}
			}
			return this.depth;
		}
		public int getDepth() {
			if (this.depth == -1) {
				throw new RuntimeException(
					MultilingualManager.getString(
						"Err_CALL_getDepth",
						InheritanceDepthLayout.class));
			}
			return this.depth;
		}
		public String getName() {
			return this.dEntity.getName();
		}
		public String toString() {
			return this.dEntity.getName() + " " + this.depth;
		}
	}

	public static final class DepthAwareDEntitiesComparator
		implements Comparator {
		private Hashtable depthAwareDEntities;
		public DepthAwareDEntitiesComparator(Hashtable depthAwareDEntities) {
			this.depthAwareDEntities = depthAwareDEntities;
		}
		public int compare(Object o1, Object o2) {
			DepthAwareDEntity dEntity1 =
				(DepthAwareDEntity) this.depthAwareDEntities.get(
					((Entity) o1).getName());
			DepthAwareDEntity dEntity2 =
				(DepthAwareDEntity) this.depthAwareDEntities.get(
					((Entity) o2).getName());

			if (dEntity1.getDepth() < dEntity2.getDepth()) {
				return -1;
			}
			else if (dEntity1.getDepth() > dEntity2.getDepth()) {
				return 1;
			}
			else {
				return dEntity1.getName().compareTo(dEntity2.getName());
			}
		}
	}
	private Point checkUnderneath(Entity[] allDEntities, Entity dEntity) {

		int xThis1 = allDEntities[0].getPosition().x;
		int yThis1 =
			this.checkUnrelatedY(
				allDEntities,
				dEntity,
				dEntity.getPosition().y);

		if (dEntity.getHierarchies().length > 0) {
			for (int levelOfHierarchy = 0;
				levelOfHierarchy < dEntity.getHierarchies().length;
				levelOfHierarchy++) {

				int i = 0;
				while (allDEntities[i]
					!= dEntity
						.getHierarchies()[levelOfHierarchy]
						.getTargetEntity()) {
					i++;
				}
				xThis1 =
					allDEntities[i].getPosition().x
						+ allDEntities[i].getDimension().width;
			}
		}

		return new Point(xThis1, yThis1);
	}
	//	private int checkUnrelatedX(
	//		Entity[] allDEntities,
	//		Entity dEntity,
	//		int xThis1) {
	//		int yThis1 = dEntity.getPosition().y;
	//
	//		// First, I do "as if" this entity has nothing to do with
	//		// the other entities being displayed.
	//		for (int i = 0;
	//			i < allDEntities.length && !allDEntities[i].equals(dEntity);
	//			i++) {
	//
	//			int x1 = allDEntities[i].getPosition().x;
	//			int y1 = allDEntities[i].getPosition().y;
	//
	//			if (allDEntities[i] instanceof Entity) {
	//				// I compute the interesection between the two rectangles;
	//				int xInt1 = Math.max(xThis1, x1);
	//				int yInt1 = Math.max(yThis1, y1);
	//
	//				int xThis2 = xThis1 + dEntity.getDimension().width;
	//				int yThis2 = yThis1 + dEntity.getDimension().height;
	//				int x2 = x1 + allDEntities[i].getDimension().width;
	//				int y2 = y1 + allDEntities[i].getDimension().height;
	//
	//				int xInt2 = Math.min(xThis2, x2);
	//				int yInt2 = Math.min(yThis2, y2);
	//
	//				if (xInt1 <= xInt2 || yInt1 <= yInt2) {
	//					if (((Entity) allDEntities[i]).isSuper()) {
	//						xThis1 =
	//							Math.max(
	//								xThis1,
	//								x1
	//									+ allDEntities[i].getDimension().width / 2
	//									+ Constants.GAP_BETWEEN_ENTITIES.width);
	//					}
	//				}
	//			}
	//		}
	//
	//		return xThis1;
	//	}
	private int checkUnrelatedY(
		Entity[] allDEntities,
		Entity dEntity,
		int yThis1) {
		int xThis1 = dEntity.getPosition().x;

		// First, I do "as if" this entity has nothing to do with
		// the other entities being displayed.
		for (int i = 0;
			i < allDEntities.length && !allDEntities[i].equals(dEntity);
			i++) {

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
						Math.max(
							yThis1,
							y2 + Constants.GAP_BETWEEN_ENTITIES.height);
				}
			}
		}

		return yThis1;
	}
	public Constituent[] doLayout(final Entity[] someEntities) {
		final Entity[] entities = new Entity[someEntities.length];
		System.arraycopy(someEntities, 0, entities, 0, someEntities.length);

		final Hashtable depthAwareEntities = new Hashtable();
		final int numberOfEntities = entities.length;
		int maxDepth = 0;

		// I build the hashtable of DepthAwareDEntities.
		for (int i = 0; i < numberOfEntities; i++) {
			depthAwareEntities.put(
				entities[i].getName(),
				new DepthAwareDEntity(entities[i]));
		}

		// I compute the depth of each DepthAwareDEntity.
		for (int i = 0; i < numberOfEntities; i++) {
			maxDepth =
				Math.max(
					maxDepth,
					(
						(DepthAwareDEntity) depthAwareEntities.get(
							entities[i].getName())).computeDepth(
						depthAwareEntities));
		}

		// I sort the DEntities according to their depths.
		Arrays.sort(
			entities,
			new DepthAwareDEntitiesComparator(depthAwareEntities));

		// Here I sort again the elements to put the subclasses as close
		// as possible to their superclasses BUT this would be better
		// if I could use a Comparator...
		{
			final Vector temp = new Vector();
			Entity dEntity = null;
			Entity dSuperclass = null;
			boolean changed = true;
			int changes = 0;

			// I copy the content of the allDEntities elements into a vector.
			for (int i = 0; i < numberOfEntities; i++) {
				temp.addElement(entities[i]);
			}

			// Yann 2001/07/30: New algorithm.
			for (int depth = 1; depth <= maxDepth; depth++) {
				changed = true;
				changes = 0;
				while (changed == true && changes < numberOfEntities) {
					changed = false;

					for (int entityPos = numberOfEntities - 1;
						entityPos >= 0;
						entityPos--) {
						dEntity = (Entity) temp.elementAt(entityPos);

						if (((DepthAwareDEntity) depthAwareEntities
							.get(dEntity.getName()))
							.getDepth()
							== depth) {

							int superclassPos = 0;
							for (int i = 0;
								i < dEntity.getHierarchies().length;
								i++) {
								dSuperclass =
									(Entity) dEntity
										.getHierarchies()[i]
										.getTargetEntity();
								int j = 0;
								while (!((Entity) temp.elementAt(j)
									== dSuperclass)) {
									j++;
								}
								superclassPos = Math.max(superclassPos, j);
							}

							superclassPos = superclassPos + 1;
							if (superclassPos != entityPos) {
								temp.removeElementAt(entityPos);
								temp.insertElementAt(dEntity, superclassPos);
								changed = true;
								changes++;
							}
						}
					}
				}
			}

			temp.copyInto(entities);
		}

		// Now I build the graphical parts...
		//     build(): computes the coordinates, sizes and builds the primitives
		//     paint(Graphics) : only (faaaast!) displays the primitives
		for (int i = 0; i < numberOfEntities; i++) {
			entities[i].setPosition(
				this.checkUnderneath(entities, entities[i]));
			entities[i].build();
		}

		// Now that I have the DEntities and they have been placed
		// (=> their coordinates are known) I can compute the
		// other elements coordinates safely.
		final Vector allElements = new Vector();
		for (int i = 0; i < numberOfEntities; i++) {
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
			new Constituent[numberOfEntities + allElements.size()];
		allElements.toArray(temp);
		System.arraycopy(
			entities,
			0,
			temp,
			allElements.size(),
			numberOfEntities);

		return temp;
	}
	public String getName() {
		return "InheritanceDepthClustering";
	}
}
