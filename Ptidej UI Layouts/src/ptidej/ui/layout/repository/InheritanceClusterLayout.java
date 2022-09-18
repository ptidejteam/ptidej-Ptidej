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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ptidej.ui.Constants;
import ptidej.ui.kernel.AbstractInheritance;
import ptidej.ui.kernel.Constituent;
import ptidej.ui.kernel.Element;
import ptidej.ui.kernel.Entity;
import ptidej.ui.kernel.Interface;
import ptidej.ui.layout.IUILayout;
import util.multilingual.MultilingualManager;

public final class InheritanceClusterLayout implements IUILayout {
	private static final class DepthAwareEntitiesComparator
		implements Comparator {
		private Map depthAwareDEntities;
		public DepthAwareEntitiesComparator(final Map depthAwareDEntities) {
			this.depthAwareDEntities = depthAwareDEntities;
		}
		public int compare(Object o1, Object o2) {
			DepthAwareEntity dEntity1 =
				(DepthAwareEntity) this.depthAwareDEntities.get(
					((Entity) o1).getName());
			DepthAwareEntity dEntity2 =
				(DepthAwareEntity) this.depthAwareDEntities.get(
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
	private static final class DepthAwareEntity {
		private final Entity dEntity;
		private int depth = -1;
		public DepthAwareEntity(Entity dEntity) {
			this.dEntity = dEntity;
		}
		public int computeDepth(final Map listOfDepthAwareDEntities) {
			if (this.depth == -1) {
				this.depth = 0;

				final AbstractInheritance[] dHierarchies =
					this.dEntity.getHierarchies();
				for (int i = 0; i < dHierarchies.length; i++) {
					final Entity temporaryDEntity =
						(Entity) dHierarchies[i].getTargetEntity();
					final String superName = temporaryDEntity.getName();
					final DepthAwareEntity superDepthAwareDEntity =
						(DepthAwareEntity) listOfDepthAwareDEntities.get(
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
						InheritanceClusterLayout.class));
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
	private static final class InheritanceTreeNode {
		private final Entity entity;
		private InheritanceTreeNode[] children;
		private int numberOfChildren;

		public InheritanceTreeNode(final Entity entity) {
			this.entity = entity;
			this.children = new InheritanceTreeNode[4];
			this.numberOfChildren = 0;
		}
		public InheritanceTreeNode() {
			this(null);
		}
		public void addChild0(final InheritanceTreeNode child) {
			if (this.numberOfChildren == this.children.length) {
				final InheritanceTreeNode[] temp =
					new InheritanceTreeNode[this.numberOfChildren + 4];
				System.arraycopy(
					this.children,
					0,
					temp,
					0,
					this.numberOfChildren);
				this.children = temp;
			}
			this.children[this.numberOfChildren] = child;
			this.numberOfChildren++;
		}
		public void addChild(final InheritanceTreeNode child) {
			if (child.entity.getHierarchies().length == 0
				|| this.entity
					== child.entity.getHierarchies()[0].getTargetEntity()) {

				this.addChild0(child);
			}
			else {
				for (int i = 0; i < this.numberOfChildren; i++) {
					if (this.children[i].entity
						== child.entity.getHierarchies()[0].getTargetEntity()) {

						this.children[i].addChild(child);
					}
					else {
						// Recursion.
						this.children[i].addChild(child);
					}
				}
			}
		}
		public void copyInto(final Entity[] dEntities) {
			int pos = 0;
			for (int i = 0; i < this.numberOfChildren; i++) {
				pos = this.children[i].copyInto(dEntities, pos);
			}
		}
		private int copyInto(final Entity[] dEntities, int pos) {
			dEntities[pos] = this.entity;
			pos = pos + 1;

			for (int i = 0; i < this.numberOfChildren; i++) {
				pos = this.children[i].copyInto(dEntities, pos);
			}
			return pos;
		}
	}

	private Point checkUnderneath(Entity[] allEntities, Entity dEntity) {
		int xThis1 = allEntities[0].getPosition().x;
		int yThis1 =
			this.checkUnrelatedY(
				allEntities,
				dEntity,
				dEntity.getPosition().y);

		if (dEntity.getHierarchies().length > 0) {
			for (int levelOfHierarchy = 0;
				levelOfHierarchy < dEntity.getHierarchies().length;
				levelOfHierarchy++) {

				final Entity hierarchyTarget =
					(Entity) dEntity
						.getHierarchies()[levelOfHierarchy]
						.getTargetEntity();

				int i = 0;
				while (i < allEntities.length
					&& allEntities[i] != hierarchyTarget) {

					i++;
				}
				if (i != allEntities.length) {
					xThis1 =
						Math.max(
							xThis1,
							allEntities[i].getPosition().x
								+ allEntities[i].getDimension().width / 2
								+ Constants.GAP_BETWEEN_ENTITIES.width);
				}
			}
		}

		return new Point(xThis1, yThis1);
	}
	//	private int checkUnrelatedX(
	//		Entity[] allEntities,
	//		Entity dEntity,
	//		int xThis1) {
	//		int yThis1 = dEntity.getPosition().y;
	//
	//		// First, I do "as if" this entity has nothing to do with
	//		// the other entities being displayed.
	//		for (int i = 0;
	//			i < allEntities.length && !allEntities[i].equals(dEntity);
	//			i++) {
	//
	//			int x1 = allEntities[i].getPosition().x;
	//			int y1 = allEntities[i].getPosition().y;
	//
	//			if (allEntities[i] instanceof Entity) {
	//				// I compute the interesection between the two rectangles;
	//				int xInt1 = Math.max(xThis1, x1);
	//				int yInt1 = Math.max(yThis1, y1);
	//
	//				int xThis2 = xThis1 + dEntity.getDimension().width;
	//				int yThis2 = yThis1 + dEntity.getDimension().height;
	//				int x2 = x1 + allEntities[i].getDimension().width;
	//				int y2 = y1 + allEntities[i].getDimension().height;
	//
	//				int xInt2 = Math.min(xThis2, x2);
	//				int yInt2 = Math.min(yThis2, y2);
	//
	//				if (xInt1 <= xInt2 || yInt1 <= yInt2) {
	//					if (((Entity) allEntities[i]).isSuper()) {
	//						xThis1 =
	//							Math.max(
	//								xThis1,
	//								x1
	//									+ allEntities[i].getDimension().width / 2
	//									+ Constants.GAP_BETWEEN_ENTITIES.width);
	//					}
	//				}
	//			}
	//		}
	//
	//		return xThis1;
	//	}
	private int checkUnrelatedY(
		Entity[] allEntities,
		Entity dEntity,
		int yThis1) {
		int xThis1 = dEntity.getPosition().x;

		// First, I do "as if" this entity has nothing to do with
		// the other entities being displayed.
		for (int i = 0;
			i < allEntities.length && !allEntities[i].equals(dEntity);
			i++) {

			int x1 = allEntities[i].getPosition().x;
			int y1 = allEntities[i].getPosition().y;

			if (allEntities[i] instanceof Entity) {
				// I compute the interesection between the two rectangles;
				int xInt1 = Math.max(xThis1, x1);
				int yInt1 = Math.max(yThis1, y1);

				int xThis2 = xThis1 + dEntity.getDimension().width;
				int yThis2 = yThis1 + dEntity.getDimension().height;
				int x2 = x1 + allEntities[i].getDimension().width;
				int y2 = y1 + allEntities[i].getDimension().height;

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

		final int numberOfEntities = entities.length;
		final Map depthAwareDEntities = new HashMap(numberOfEntities);
		int maxDepth = 0;

		// I build the map of DepthAwareEntities.
		for (int i = 0; i < numberOfEntities; i++) {
			depthAwareDEntities.put(
				entities[i].getName(),
				new DepthAwareEntity(entities[i]));
		}

		// I compute the depth of each DepthAwareEntity.
		for (int i = 0; i < numberOfEntities; i++) {
			maxDepth =
				Math.max(
					maxDepth,
					(
						(DepthAwareEntity) depthAwareDEntities.get(
							entities[i].getName())).computeDepth(
						depthAwareDEntities));
		}

		// I sort the DEntities according to their depths.
		Arrays.sort(
			entities,
			new DepthAwareEntitiesComparator(depthAwareDEntities));

		//	for (int i = 0; i < numberOfEntities; i++) {
		//		System.err.println(
		//			depthAwareDEntities.get(allEntities[i].getName())
		//				+ " "
		//				+ allEntities[i].getClass());
		//	}

		// For beauty purposes, I move up all the interfaces...
		// Yann 2002/08/24: Interface connection!
		// There is a bug with the interfaces connected
		// to java.lang.Object. I must put the interfaces first, AND
		// java.lang.Object very first (well, at the first position),
		// else the tree node cannot be created for interfaces, because
		// it cannot find its super-entity: To do that I just put "1" as
		// the lastMovedInterfacePos...
		// Yann 2002/08/29: Fragile!
		// This algortithm produces exception in hard-to-reproduce cases.
		// I remove it for the moment!
		//	boolean done = false;
		//	for (int i = 0; i < numberOfEntities && !done; i++) {
		//		if (allEntities[i]
		//			.getPEntity()
		//			.getName()
		//			.equals("java.lang.Object")) {
		//
		//			final Entity javaLangObject = allEntities[i];
		//			System.arraycopy(allEntities, 0, allEntities, 1, i);
		//			allEntities[0] = javaLangObject;
		//			lastMovedInterfacePos = 1;
		//			done = true;
		//		}
		//	}
		// Yann 2002/08/29: Fix?!
		// The new algorithm now takes into account ghost entities.
		// The problem was that any ghost may be the super-entity of
		// an interface, not just "java.lang.Object"!

		if (numberOfEntities > 0) {
			int lastGhostPos = 0;
			while (lastGhostPos < numberOfEntities
				&& ((DepthAwareEntity) depthAwareDEntities
					.get(entities[lastGhostPos].getName()))
					.getDepth()
					== 0) {

				lastGhostPos++;
			}
			for (int i = 0; i < numberOfEntities; i++) {
				if (entities[i] instanceof Interface) {
					final Entity interfaceEntity = entities[i];
					//	System.err.println(
					//		"lastGhostPos = "
					//			+ lastGhostPos
					//			+ " ("
					//			+ allEntities[lastGhostPos].getName()
					//			+ ')');
					//	System.err.println(
					//		"numberOfEntities = " + numberOfEntities);
					//	System.err.println(
					//		"i - lastGhostPos = " + (i - lastGhostPos));
					//	System.err.println();

					// Yann 2002/09/05: Bogus!
					// The following code keeps on throwing exception
					// when displaying pattern solutions involving
					// interfaces... Damn!
					// Actually, this fix renders a not-so-bad display!?
					if (i - lastGhostPos < 0) {
						lastGhostPos = i;
					}
					System.arraycopy(
						entities,
						lastGhostPos,
						entities,
						lastGhostPos + 1,
						i - lastGhostPos);
					entities[lastGhostPos] = interfaceEntity;
					lastGhostPos++;
				}
			}
		}

		// Now, I build clusters of entities, each cluster being composed
		// of one root, several children, each children being one root and
		// having several children... All this, according to the class
		// hierarchy, of course.
		final InheritanceTreeNode root = new InheritanceTreeNode();
		for (int i = 0; i < numberOfEntities; i++) {
			root.addChild(new InheritanceTreeNode(entities[i]));
		}
		root.copyInto(entities);

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
		final List allElements = new ArrayList();
		for (int i = 0; i < numberOfEntities; i++) {
			final Element[] dElements = ((Entity) entities[i]).getElements();
			for (int j = 0; j < dElements.length; j++) {
				allElements.add(dElements[j]);
				dElements[j].build();
			}

			final AbstractInheritance[] dHierarchies =
				((Entity) entities[i]).getHierarchies();
			for (int j = 0; j < dHierarchies.length; j++) {
				allElements.add(dHierarchies[j]);
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
		return "InheritanceClustering";
	}
}
