/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc  and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.ui.layout.repository.sugiyama.linesDrawing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.apache.commons.lang3.ArrayUtils;
import ptidej.ui.kernel.AbstractInheritance;
import ptidej.ui.kernel.Constituent;
import ptidej.ui.kernel.Element;
import ptidej.ui.kernel.Entity;
import ptidej.ui.kernel.IntermediaryPoint;
import ptidej.ui.kernel.Relationship;
import ptidej.ui.layout.repository.sugiyama.SettingValue;
import ptidej.ui.layout.repository.sugiyama.graph.INode;
import ptidej.ui.layout.repository.sugiyama.graph.Node;

/**
 * @author mohamedkahla
 *
 */
public class LineDrawer {
	private final Entity[] entities;
	private final List graphNode;
	private final int nbLevels;
	private final INode[][] matrix;
	//	 all the elements to returns whith the rhight positions 
	private List allElements;
	private int[] yStep; //  the distance between two levels

	public LineDrawer(
		final Entity[] someEntities,
		final List aGraphNode,
		final int anNbLevels,
		final INode[][] aMatrix) {

		this.entities = someEntities;
		this.graphNode = aGraphNode;
		this.nbLevels = anNbLevels;
		this.matrix = aMatrix;
		// all the elements to returns whith the rhight positions 
		this.allElements = new ArrayList();
	}
	public void draw() {
		// TODO setChannelNumber();
		this.setEntitiesDistance();
		this.drawlines();
	}
	public List getAllElements() {
		return this.allElements;
	}
	private void setEntitiesDistance() {
		this.yStep = new int[this.nbLevels];
		// Setting step
		for (int i = 0; i < this.matrix.length; i++) {
			int maxHeight = 0;
			for (int j = 0; j < this.matrix[i].length; j++) {
				Entity e = this.matrix[i][j].getEntity();
				if (e != null) { // Not a dummy
					if (e.getDimension().height > maxHeight)
						maxHeight = e.getDimension().height;
				}
			}
			this.yStep[i] =
				maxHeight + SettingValue.getVerticalDistanceEntities();
		}
	}
	// 24-05-2006
	// Drawing Double square lines 
	private void drawlines() {
		// 06-07-2006
		this.setEdgesList(); // for long edges

		// Now that I have the DEntities and they have been placed
		// (=> their coordinates are known) I can compute the
		// other elements coordinates safely.
		for (int i = 0; i < this.entities.length; i++) {
			final Element[] dElements = this.entities[i].getElements();
			for (int j = 0; j < dElements.length; j++) {
				if (dElements[j] instanceof Relationship) {
					final Relationship rv = (Relationship) dElements[j];
					if ((dElements[j].getVisibleElements() & rv
						.getVisibilityDisplay()) == rv.getVisibilityDisplay()) {

						this.allElements.add(dElements[j]);
						dElements[j].build();
					}
					else {
						// System.out.println("One saved build()!");
					}
				}
				else {
					this.allElements.add(dElements[j]);
					dElements[j].build();
				}
			}

			final AbstractInheritance[] dHierarchies =
				this.entities[i].getHierarchies();
			for (int j = 0; j < dHierarchies.length; j++) {
				this.allElements.add(dHierarchies[j]);
				dHierarchies[j].build();
			}
		}

		//		for (int i = 0; i < this.entities.length; i++) {
		//
		//			final Element[] dElements = this.entities[i].getElements();
		//			for (int j = 0; j < dElements.length; j++) {
		//				this.allElements.add(dElements[j]);
		//				dElements[j].build();
		//			}

		//			final AbstractGeneralisation[] dHierarchies = this.entities[i]
		//				.getHierarchies();
		//			for (int j = 0; j < dHierarchies.length; j++) {
		//				this.allElements.add(dHierarchies[j]);
		//				dHierarchies[j].build();
		//			}
		//		}

		// setEdgesList(); // for long adges

		// 25-05-2006

		// TODO
		//		setLinesSplitter();

		// Yann 2013/06/04: Selection
		// What if the temporaryEntity exist but is 
		// not visible (i.e., selected to be displayed)?
		// I search in the set of given entities to be
		// displayed if the temporaryEntity is there 
		// first before checking for it being a Ghost.
		// It is a bit silly to do that again, as in 
		// HierarchiesBuilder...
		// TODO Unify concept of selection
		// Here, to simplify... but not perform! I remove
		// elements whose target are not selected...
		for (int index = 0; index < this.allElements.size(); index++) {
			final Constituent constituent =
				(Constituent) this.allElements.get(index);
			if (constituent instanceof Relationship) {
				final Entity target =
					((Relationship) constituent).getTargetEntity();
				if (!ArrayUtils.contains(this.entities, target)) {
					this.allElements.remove(index);
					index--;
				}
			}
		}
	}
	//	private void setLinesSplitter() {
	//		// the elements must be build first
	//		// then we could set the lines' splitter
	//		final ListIterator itr = this.graphNode.listIterator(0);
	//		// all the nodes
	//		while (itr.hasNext()) {
	//			Node next = (Node) itr.next();
	//			Entity entity = next.getEntity();
	//			Parents parents = next.getParents();
	//			// if not dummy
	//			if (entity != null) {
	//				final AbstractGeneralisation[] generalisations =
	//					entity.getHierarchies();
	//				for (int l = 0; l < generalisations.length; l++) {
	//					final AbstractGeneralisation generalisation =
	//						generalisations[l];
	//					Entity target = generalisations[l].getTargetEntity();
	//					// I m looking for the node encapsulating the target entity
	//					final ListIterator findTarget =
	//						this.graphNode.listIterator(0);
	//					int channelNumber = -1;
	//					int currentLevel = 0;
	//					int numberOfAllChannel = 0;
	//					while (findTarget.hasNext()) {
	//						Node mayBe = (Node) findTarget.next();
	//						if (mayBe.getEntity() == target) {
	//							currentLevel = mayBe.getLevel(); // of  parent
	//							numberOfAllChannel =
	//								this.channelAtLevel[currentLevel];
	//							channelNumber = parents.getChannel(mayBe);
	//							break;
	//						}
	//					}
	//
	//					// default 
	//					// for those who are not visible too !!!
	//					// TODO
	//					int split = SettingValue.getDefaultSplitter();
	//					// shoud happens when a parents are not visible by exemple!
	//					if (channelNumber >= 0) {
	//						// the get target is different depending of the type of generalisation
	//						// child or parents !!!
	//
	//						// TODO
	//						if (generalisation instanceof Implementation)
	//							// we have a child like an Origin Entity
	//							// and a Parent like a Target Entity 
	//							split =
	//								70 / (numberOfAllChannel - channelNumber + 1)
	//										+ entity.getDimension().height / 2;
	//						else
	//							// specialisation (generilisation)
	//							// opposite sens
	//							// we have a Parent like an Origin Entity
	//							// and a Child like a Target Entity 
	//							split =
	//								this.yStep[currentLevel]
	//										- 100
	//										+ 70
	//										/ (numberOfAllChannel - channelNumber + 1)
	//										+ 50 - target.getDimension().height / 2;
	//					}
	//
	//					// TODO
	//					//					 TODO
	//					//					 TODO
	//					//					 TODO
	//
	//					generalisation.setSplitter(split);
	//					// for both implementation and specialization
	//
	//				}
	//			}
	//		}
	//	}
	/**
	 * this method set a list containing all the dummy nodes 
	 * in avry edges
	 * 
	 * And a null list if there is no dummy nodes to set
	 * 
	 */
	// 06-07-2006
	private void setEdgesList() {
		// 06-07-2006
		// the elements must be build first
		// then we could set theEdgesList
		final Iterator itr = this.graphNode.iterator();
		// all the nodes
		while (itr.hasNext()) {
			final Node originNode = (Node) itr.next();
			final Entity originEntity = originNode.getEntity();
			// if not dummy
			if (originEntity != null) {
				final AbstractInheritance[] generalisations =
					originEntity.getHierarchies();
				for (int l = 0; l < generalisations.length; l++) {

					//					System.out.println(" boucle for () ");

					final AbstractInheritance generalisation =
						generalisations[l];
					Entity targetEntity = generalisations[l].getTargetEntity();
					// I m looking for the node encapsulating the target entity
					final ListIterator findTarget =
						this.graphNode.listIterator(0);

					Node targetNode = null;
					// we will find this nodes if it is not a Ghost!
					// or the Ghost are visible
					// TODO possible optimisition here!
					while (findTarget.hasNext()) {
						Node mayBe = (Node) findTarget.next();
						if (mayBe.getEntity() == targetEntity) {
							targetNode = mayBe;
							break;
						}
					}

					// if we found the target!
					// And we shoud found it
					// if it's not a Ghost or Ghost are visible
					if (targetNode != null) {
						if (targetNode.getLevel() == originNode.getLevel() + 1
								|| targetNode.getLevel() == originNode
									.getLevel() - 1) {

							// If it's a short edge, 
							// then no dummy nodes.
							generalisation
								.setEdgesList(new IntermediaryPoint[0]);
						}
						else {
							// If it's a long edge, 
							// we have a Child like an Origin Entity
							// and a Parent like a Target Entity 
							final List dummyList = new ArrayList();

							// for simplicity, we will inverse the sense of search
							// so we start from the parents and go to the child

							final INode[] children =
								targetNode.getTabChildren();
							// from parent
							//							ListIterator childItr = children.listIterator(0);

							// we look for the first dummy node that is
							// included in this edge!
							INode firstDummy = null;
							//							while (childItr.hasNext()) {
							for (int i = 0; i < children.length; i++) {
								final INode child = children[i];
								//(Node) childItr.next();
								// if a dummy 
								if (child.getEntity() == null) {
									INode currentNode = child;

									// for the first!
									if (currentNode.getEntity() == originEntity) {
										firstDummy = child;
										break;
									}

									// from parents to child!
									// we must test if the next child have children!
									// it means that we look the wrong edge!
									while (currentNode.getLevel() <= originNode
										.getLevel()
											&& !currentNode
												.getChildren()
												.isEmpty()) {

										// we go to the next!
										// a dummy node have a unique child or parent!

										currentNode =
											(currentNode.getTabChildren()[0]);

										if (currentNode.getEntity() != null) {
											if (currentNode.getEntity() == originEntity) {
												firstDummy = child;
												break;
											}
											break;

										}
									}

									if (firstDummy != null) {

										//										System.out.println(" Break II");

										break;
									}
								}
							}

							// we build the list of dummy nodes included 
							// in this long edge
							INode currentDummy = firstDummy;

							// TODO
							while (currentDummy != null
									&& currentDummy.getEntity() == null) {

								// System.out.println("inside while");

								dummyList.add(currentDummy);
								// we go to the next dummy if it exist !
								currentDummy =
									(currentDummy.getTabChildren()[0]);
							}

							// Yann 2007/04/22: DummyNode to IntermediaryPoints
							// I cleaned up the code of the Ptidej UI by
							// introducing IntermediaryPoints instead of
							// dummy nodes, thus separating the layouts from
							// the UI better.
							final IntermediaryPoint[] intermediaryPoints =
								new IntermediaryPoint[dummyList.size()];
							final Iterator iterator = dummyList.iterator();
							int i = dummyList.size();
							while (iterator.hasNext()) {
								final Node node = (Node) iterator.next();
								intermediaryPoints[--i] =
									new IntermediaryPoint(
										node.getX(),
										node.getY());
							}
							generalisation.setEdgesList(intermediaryPoints);
						}
					}
				}
			}
		}
	}
	/**
	 * 
	 *
	 */
	// 24-05-2006
	// set channels number
	//	private void setChannelNumber() {
	//		// channels allowed by level i
	//		// initialised to 0s 
	//		this.channelAtLevel = new int[this.nbLevels];
	//		final ListIterator nodeIterator = this.graphNode.listIterator(0);
	//		while (nodeIterator.hasNext()) {
	//			// one parent
	//			Node parent = (Node) nodeIterator.next();
	//
	//			// we want that all the children of the same node that are in the same level
	//			// have the same channel for that parent
	//			Node[] children = parent.getTabChildren();
	//			// we knew that children are located at levels further than the parents so ..
	//			for (int i = parent.getLevel() + 1; i < this.nbLevels; i++) {
	//				boolean updateChannel = false;
	//				// for all the children at level i
	//				//				final ListIterator childrenIterator = children
	//				//					.listIterator(0);
	//				//				while (childrenIterator.hasNext()) {
	//				for (int j = 0; j < children.length; j++) {
	//					Node nextChild = children[j];
	//					// (Node) childrenIterator.next();
	//					// at level i
	//					if (nextChild != null && nextChild.getLevel() == i) {
	//						nextChild.setParentChannel(
	//							parent,
	//							this.channelAtLevel[i - 1]);
	//						updateChannel = true;
	//					}
	//				}
	//
	//				if (updateChannel) {
	//					this.channelAtLevel[i - 1]++;
	//					// update the number of channels in level i
	//				}
	//			}
	//		}
	//	}
}
