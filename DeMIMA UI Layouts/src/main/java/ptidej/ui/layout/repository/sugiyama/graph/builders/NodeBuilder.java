/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Ga�l Gu�h�neuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Ga�l Gu�h�neuc and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.ui.layout.repository.sugiyama.graph.builders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.apache.commons.lang3.ArrayUtils;
import ptidej.ui.IVisibility;
import ptidej.ui.kernel.AbstractInheritance;
import ptidej.ui.kernel.Aggregation;
import ptidej.ui.kernel.Association;
import ptidej.ui.kernel.Composition;
import ptidej.ui.kernel.ContainerAggregation;
import ptidej.ui.kernel.ContainerComposition;
import ptidej.ui.kernel.Creation;
import ptidej.ui.kernel.Delegation;
import ptidej.ui.kernel.Element;
import ptidej.ui.kernel.Entity;
import ptidej.ui.kernel.Implementation;
import ptidej.ui.kernel.Relationship;
import ptidej.ui.kernel.Specialisation;
import ptidej.ui.kernel.Use;
import ptidej.ui.layout.repository.sugiyama.graph.INode;
import ptidej.ui.layout.repository.sugiyama.graph.Node;

/**
 * @author mohamedkahla
 */
// TODO Should be a Singleton!
public class NodeBuilder {
	private final List graphHierarchy;
	private final int nbLevels;
	private final EdgeBuilder edgeBuilder;
	private List nodeCreated;
	private Entity[] entities;

	//	private boolean generalisationVisibility = false;
	//	private boolean aggregationVisibility = false;
	//	private boolean associationVisibility = false;
	//	private boolean compositionVisibility = false;
	//	private boolean containerAggregationVisibility = false;
	//	private boolean containerCompositionVisibility = false;
	//	private boolean creationVisibility = false;
	//	private boolean delegationVisibility = false;
	//	private boolean useVisibility = false;

	/**
	 * 
	 */
	public NodeBuilder(
		final Entity[] someEntities,
		final List aGraphHierarchy,
		final int anNbLevels,
		final EdgeBuilder anEdgeBuilder) {

		this.entities = someEntities;
		this.graphHierarchy = aGraphHierarchy;
		this.nbLevels = anNbLevels;
		this.edgeBuilder = anEdgeBuilder;
	}

	// Every entity is inside a node with more information
	public void buildNodes() {
		// this list contains the parents visited
		this.nodeCreated = new ArrayList();
		if (!this.graphHierarchy.isEmpty()) {
			final List children =
				(List) this.graphHierarchy.get(this.graphHierarchy.size() - 1);
			final Iterator childrenIterator = children.iterator();
			// we first build all the nodes at the last level 
			// we knew that they must childrens !
			while (childrenIterator.hasNext()) {
				Entity child = (Entity) childrenIterator.next();
				// we create the node containing the children
				Node node = new Node(child, this.nbLevels - 1);
				// the last level
				this.nodeCreated.add(node);
			}
			// from the bottom we go up until reaching the parents
			final ListIterator levelIterator =
				this.graphHierarchy.listIterator(this.graphHierarchy.size());
			// the last position

			// the index of the current level
			int levelIndex = this.graphHierarchy.size();
			// TODO Why hasPrevious()?
			while (levelIterator.hasPrevious()) {
				// the current level
				levelIndex--; // we go up

				final List level = (List) levelIterator.previous();
				final Iterator nodeIterator = level.iterator();
				// for all nodes at this level!
				while (nodeIterator.hasNext()) {
					// they must be children for the nodes at the hier levels!
					Entity origin = (Entity) nodeIterator.next();

					// Modified 10-08-2006 
					// Mohamed Kahla

					// here we add visible links
					// and a parent don't have a duplicated children and
					// vice vers ca!

					// so only one link between two entities even if more exist
					// the ORIGIN is the child entity

					// HERE we get all the TARGET entities

					// ///////////////////

					final List allVisibleConnectedTarget =
						this.getAllVisibleConnectedTarget(origin);

					int nbTargets = allVisibleConnectedTarget.size();
					// we search all the targets in all the level i
					// we start whith the first and so on 
					// until all targets are found
					for (int i = 0; i < nbTargets; i++) {

						final Entity temporaryDEntity =
							(Entity) allVisibleConnectedTarget.get(i);
						// one target to find

						//						int searchLevel = levelIterator.previousIndex();
						int searchLevel = 0;

						boolean targetFound = false;

						//  searching at lower levels from 0 to nbLevel
						while (!targetFound && (searchLevel < this.nbLevels)) {
							// we search the parent ENTITY at a LOWER level
							// and in wich level it is 
							if (((List) this.graphHierarchy.get(searchLevel))
								.contains(temporaryDEntity)) // if parents found
								targetFound = true;
							// we go to the next HIER LOWER to serch inside for the target!
							if (!targetFound)
								searchLevel++;
						}

						// we must verify if the target and node are at different level

						// if the target is at a hier level 
						// so ORIGIN.getLevel > TARGET.getLeve
						// direction > 0 : normal staf, No inversion

						// if the target is at a lower level 
						// so ORIGIN.getLevel < TARGET.getLeve
						//  direction < 0 :we invers the edge

						// finally if they are at the same level (this include the case when the origin == the target)
						// we do nothing for the moment
						// we could later do best maybe but : 
						// TODO : link for entites at the same level
						// TODO : link for origin == the target
						// 0 : they are at the same level : IGNORED FOR THE MOMENTS 
						int direction = 0;

						// we set the direction
						int originLevel = levelIndex;
						// the level wher we get this child
						int targetLevel = searchLevel;
						// the searchLevel where the target was founded
						if (originLevel > targetLevel)
							direction = 1; // origin is the child

						else if (originLevel < targetLevel)
							direction = -1; // origin is the parent

						// when we found that parent ENTITY
						// so we knew its levels in the hierarchy
						if (targetFound) {
							// first control!
							if (direction != 0) {
								// we have to consider the direction when its negative the child became parent and vice vers ca !
								// TODO : inversing links
								boolean VisitedTarget = false;
								boolean VisitedOrigin = false;
								Node tempNode;
								Node tempTarget = new Node(null, -1);
								Node tempOrigin = new Node(null, -1);

								// we look if the node containing this parent ENTITY are creted or not
								// we look if the node containing this child ENTITY are creted or not tow

								// for all the nodes created !
								final Iterator allNodesVisited =
									this.nodeCreated.iterator();
								// have that parent been visited ?
								// have the child been visited ?
								while (allNodesVisited.hasNext()) {
									// next condidate!
									tempNode = (Node) allNodesVisited.next();

									// child?
									if (!VisitedOrigin
											&& tempNode.getEntity() == origin) {
										tempOrigin = tempNode;
										VisitedOrigin = true;
										// if both are visited!
										if (VisitedTarget)
											break;
									}

									// parent?
									if (!VisitedTarget
											&& tempNode.getEntity() == temporaryDEntity) {
										tempTarget = tempNode;
										VisitedTarget = true;
										// if both are visited!
										if (VisitedOrigin)
											break;
									}
								}

								// At this moment we should have founded the corresponding 
								// nodes if they have been created before

								// we look for the differents possibilities and 
								// we creat nodes if not visited 
								// we set the correct links between them two

								if (!VisitedTarget && VisitedOrigin) {
									// we create the node containing the target 
									Node node =
										new Node(temporaryDEntity, searchLevel);
									this.nodeCreated.add(node);
									// nomal sens
									if (direction > 0) {
										// oring is the child
										node.addChild(tempOrigin);
										tempOrigin.addParent(node);
									}

									// we are sur that the direction != 0 !!
									// inversion
									else {
										// origin is the parent
										tempOrigin.addChild(node);
										node.addParent(tempOrigin);
									}
								}

								else if (VisitedTarget && VisitedOrigin) {
									// we update the node
									// for none duplications
									// if this node have not been added !

									// nomal sens
									if (direction > 0) {
										// oring is the child
										if (!tempTarget.getChildren().contains(
											tempOrigin)) {
											tempTarget.addChild(tempOrigin);
											// update the the child' s parents list
											tempOrigin.addParent(tempTarget);
										}
									}

									else {
										// origin is the parent
										if (!tempOrigin.getChildren().contains(
											tempTarget)) {
											tempOrigin.addChild(tempTarget);
											tempTarget.addParent(tempOrigin);
										}

									}
								}

								else if (VisitedTarget && !VisitedOrigin) {
									// we create the node containing the origin
									Node node = new Node(origin, levelIndex
									/*levelIterator.previousIndex() + 1*/
									);
									// the origin is located at the levelIndex
									this.nodeCreated.add(node);

									// nomal sens
									if (direction > 0) {
										// oring is the child
										tempTarget.addChild(node);
										node.addParent(tempTarget);
									}

									// we are sur that the direction != 0 !!
									// inversion
									else {
										// origin is the parent
										node.addChild(tempTarget);
										tempTarget.addParent(node);
									}

								}

								// both not visited!
								else {
									// we create the node containing the child
									Node nodeOrigin =
										new Node(origin, levelIndex
										/*levelIterator.previousIndex() + 1*/
										);
									// the child is located at the levelIndex
									// we create the node containing the parent 
									Node nodeTarget =
										new Node(temporaryDEntity, searchLevel);

									this.nodeCreated.add(nodeOrigin);
									this.nodeCreated.add(nodeTarget);

									// nomal sens
									if (direction > 0) {
										// oring is the child
										nodeTarget.addChild(nodeOrigin);
										nodeOrigin.addParent(nodeTarget);
									}

									// we are sur that the direction != 0 !!
									// inversion
									else {
										// origin is the parent
										nodeOrigin.addChild(nodeTarget);
										nodeTarget.addParent(nodeOrigin);
									}

								}
							}
						}
						else {
							System.err
								.println("Node Builder : the target entity Not Found in the graph Hierarchy");
						}
					}

				} // end for all the visibles links

			} // end addiing nodes

		}

		// 05-07-2006
		// orphans and singles
		// for nodes that don't have neither parents nor children
		// we first isolate the nodes that don't have parents

		//		final List singles = (List) this.graphHierarchy.getFirst();
		//		final ListIterator singlesIterator = singles.listIterator(0);
		//		while (singlesIterator.hasNext()) {
		//			Entity entity = (Entity) singlesIterator.next();
		//
		//			// we check if we have added it?
		//			final ListIterator nodeItr = this.nodeCreated.listIterator(0);
		//			boolean nodeFond = false;
		//			while (nodeItr.hasNext()) {
		//				Node node = (Node) nodeItr.next();
		//				if (node.getEntity() == entity) {
		//					nodeFond = true;
		//					break;
		//				}
		//			}
		//
		//			if (!nodeFond) {
		//				// we create the node 
		//				Node node = new Node(entity, 0); // the first level
		//				this.nodeCreated.add(node);
		//			}
		//		}

		// Mohamed Kahla
		// correction done 12-08-2006

		for (int levelIndex = 0; levelIndex < this.nbLevels; levelIndex++) {
			// we search if singles at the current level
			final List currentLevel =
				(List) this.graphHierarchy.get(levelIndex);
			for (int j = 0; j < currentLevel.size(); j++) {
				Entity next = (Entity) currentLevel.get(j);
				// if the for this entity is not builded yet 
				if (findNode(next) == null) {
					// we create the node 
					Node node = new Node(next, levelIndex); // the i level
					this.nodeCreated.add(node);
				}
			}
		}

		// if other links are checked
		// or hierarchy links are not checked!

		// upDateLinkNodes();
	}

	private List getAllVisibleConnectedTarget(final Entity aChild) {
		final List targets = new ArrayList();

		// Generalisation links
		final AbstractInheritance[] dHierarchies = aChild.getHierarchies();
		// We search the parents of child
		// other links
		// for the other elements
		final Element[] dElements = aChild.getElements();

		// the same tab for all the elements!
		final Element[] allElements =
			new Element[dElements.length + dHierarchies.length];

		System.arraycopy(dElements, 0, allElements, 0, dElements.length);
		System.arraycopy(
			dHierarchies,
			0,
			allElements,
			dElements.length,
			dHierarchies.length);

		int nbTargets = allElements.length;

		// we search all the parents in all the level i
		// we start whith the first and so on 
		// until all parents are found
		for (int i = 0; i < nbTargets; i++) {
			final Element elem = allElements[i];

			// Relationship
			if (elem instanceof Relationship) {
				final Entity origin = ((Relationship) elem).getOriginEntity();
				final Entity target = ((Relationship) elem).getTargetEntity();

				// Yann 2013/06/04: Selection
				// What if the temporaryEntity exist but is 
				// not visible (i.e., selected to be displayed)?
				// I search in the set of given entities to be
				// displayed if the temporaryEntity is there 
				// first before checking for it being a Ghost.
				// It is a bit silly to do that again, as in 
				// HierarchiesBuilder...
				// Yann 2014/04/28: Ghosts are ghosts...
				// If ghosts are not selected to be displayed,
				// none will belong to the set of given entities.
				// Therefore, the test below is enough and no
				// need for
				//	&& (!(target instanceof Ghost) || ghostVisibility)
				if (ArrayUtils.contains(this.entities, target)) {
					// AbstractGeneralisation
					if ((elem instanceof AbstractInheritance)
							&& checkVisibility(
								IVisibility.HIERARCHY_DISPLAY_ELEMENTS,
								origin)) {

						// Implementation
						if (elem instanceof Implementation) {
							targets.add(target);
						}
						// Specialisation
						else if (elem instanceof Specialisation) {
							targets.add(target);
						}
					}
					// Association
					else if (elem instanceof Association) {
						if (elem instanceof Aggregation
								&& checkVisibility(
									IVisibility.AGGREGATION_DISPLAY_ELEMENTS,
									origin)) {
							targets.add(target);
						}
						else if (elem instanceof ContainerAggregation
								&& checkVisibility(
									IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS,
									origin)) {
							targets.add(target);
						}
						else if (elem instanceof Composition
								&& checkVisibility(
									IVisibility.COMPOSITION_DISPLAY_ELEMENTS,
									origin)) {
							targets.add(target);
						}
						else if (elem instanceof ContainerComposition
								&& checkVisibility(
									IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS,
									origin)) {
							targets.add(target);
						}
						else if (checkVisibility(
							IVisibility.ASSOCIATION_DISPLAY_ELEMENTS,
							origin)) {
							targets.add(target);
						}
					}
					// Delegation
					else if (elem instanceof Delegation
							&& checkVisibility(
								IVisibility.DELEGATION_DISPLAY_ELEMENTS,
								origin)) {
						targets.add(target);
					}
					// Use
					else if (elem instanceof Use) {
						if (checkVisibility(
							IVisibility.USE_DISPLAY_ELEMENTS,
							origin)) {
						}
						// Creation
						if (elem instanceof Creation
								&& checkVisibility(
									IVisibility.CREATION_DISPLAY_ELEMENTS,
									origin)) {
							targets.add(target);
						}
						else if (checkVisibility(
							IVisibility.USE_DISPLAY_ELEMENTS,
							origin)) {
							targets.add(target);
						}
					}
				}
			}
		}

		return targets;
	}
	/**
	 * 
	 * 
	 */
	private boolean checkVisibility(final int aVisibility, final Entity e) {
		boolean visibility = false;

		switch (aVisibility) {
			case IVisibility.HIERARCHY_DISPLAY_ELEMENTS :
				{
					// Abstract Generalisation 
					// Implementation & Specialisation
					int abstractGenerilisationVisibility =
						e.getVisibleElements()
								& IVisibility.HIERARCHY_DISPLAY_ELEMENTS;
					// if not visible!
					if (abstractGenerilisationVisibility == IVisibility.HIERARCHY_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.AGGREGATION_DISPLAY_ELEMENTS :
				{
					// Aggregation
					int aggregationVisibility =
						e.getVisibleElements()
								& IVisibility.AGGREGATION_DISPLAY_ELEMENTS;
					if (aggregationVisibility == IVisibility.AGGREGATION_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.ASSOCIATION_DISPLAY_ELEMENTS :
				{
					// Association
					int associationVisibility =
						e.getVisibleElements()
								& IVisibility.ASSOCIATION_DISPLAY_ELEMENTS;
					if (associationVisibility == IVisibility.ASSOCIATION_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.COMPOSITION_DISPLAY_ELEMENTS :
				{
					// Composition
					int compositionVisibility =
						e.getVisibleElements()
								& IVisibility.COMPOSITION_DISPLAY_ELEMENTS;
					if (compositionVisibility == IVisibility.COMPOSITION_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS :
				{
					// Container Aggregation 
					int containerAggregationVisibility =
						e.getVisibleElements()
								& IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS;
					if (containerAggregationVisibility == IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS :
				{
					// Container Composition
					int containerCompositionVisibility =
						e.getVisibleElements()
								& IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS;
					if (containerCompositionVisibility == IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.CREATION_DISPLAY_ELEMENTS :
				{
					// Creation
					int creationVisibility =
						e.getVisibleElements()
								& IVisibility.CREATION_DISPLAY_ELEMENTS;
					if (creationVisibility == IVisibility.CREATION_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.DELEGATION_DISPLAY_ELEMENTS :
				{
					// Delegation 
					// TODO 
					// This field don't exist in the swing implementation!visible
					int delegationVisibility =
						e.getVisibleElements()
								& IVisibility.DELEGATION_DISPLAY_ELEMENTS;
					if (delegationVisibility == IVisibility.DELEGATION_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}
			case IVisibility.USE_DISPLAY_ELEMENTS :
				{
					// Use
					int useVisibility =
						e.getVisibleElements()
								& IVisibility.USE_DISPLAY_ELEMENTS;
					if (useVisibility == IVisibility.USE_DISPLAY_ELEMENTS)
						visibility = true;

					break;
				}

			default :
				break;
		}

		//		System.out.println("visibility: " + visibility);
		return visibility;
	}

	/**
	 *@author Mohamed Kahla 
	 *@since 2006/07/11
	 */
	// for the differents links, if the two entities are on different level,
	// the entity that have a superier level is considered as parent
	// and the other entity as child!
	// 11-07-2006
	private void setElementsVisibility(
		final INode anOriginNode,
		final INode aTargetNode,
		final int aVisibility) {

		final INode child;
		final INode parent;

		// 25-07-2006
		int direction = 0;
		// the direction of the edge (1 or -1 : not inversed or inversed)

		// we nead a hierarchy
		// parents and childrens
		if (anOriginNode.getLevel() != aTargetNode.getLevel()) {
			Entity e = anOriginNode.getEntity();

			// normal sens children to parents
			if (anOriginNode.getLevel() > aTargetNode.getLevel()) {
				parent = aTargetNode;
				child = anOriginNode;
				direction = 1; // inversed edge
			}
			else {
				child = aTargetNode;
				parent = anOriginNode;
				direction = -1; // normal edge
			}

			// and we don't care about the entities that have the same when deleting 
			// a generalisation lins because that senario  could not exist generalisation 
			// has been done by the hierarchies builder

			// we have to update the lists of
			// parents and children in the two entities
			switch (aVisibility) {
				case IVisibility.HIERARCHY_DISPLAY_ELEMENTS :
					{
						// Abstract Generalisation 
						// Implementation & Specialisation
						int abstractGenerilisationVisibility =
							e.getVisibleElements()
									& IVisibility.HIERARCHY_DISPLAY_ELEMENTS;
						// if not visible!
						if (abstractGenerilisationVisibility != IVisibility.HIERARCHY_DISPLAY_ELEMENTS) {
							// because by defaul we set this links
							parent.removeChild(child);
							child.removeParent(parent);
						}
						else {
							// edge building
							this.edgeBuilder
								.buildEdge(parent, child, direction);
						}
						break;
					}
				case IVisibility.AGGREGATION_DISPLAY_ELEMENTS :
					{
						// Aggregation
						int aggregationVisibility =
							e.getVisibleElements()
									& IVisibility.AGGREGATION_DISPLAY_ELEMENTS;
						if (aggregationVisibility == IVisibility.AGGREGATION_DISPLAY_ELEMENTS) {
							parent.addChild(child);
							child.addParent(parent);

							// edge building
							this.edgeBuilder
								.buildEdge(parent, child, direction);
						}
						break;
					}
				case IVisibility.ASSOCIATION_DISPLAY_ELEMENTS :
					{
						// Association
						int associationVisibility =
							e.getVisibleElements()
									& IVisibility.ASSOCIATION_DISPLAY_ELEMENTS;
						if (associationVisibility == IVisibility.ASSOCIATION_DISPLAY_ELEMENTS) {
							parent.addChild(child);
							child.addParent(parent);

							// edge building
							this.edgeBuilder
								.buildEdge(parent, child, direction);
						}
						break;
					}
				case IVisibility.COMPOSITION_DISPLAY_ELEMENTS :
					{
						// Composition
						int compositionVisibility =
							e.getVisibleElements()
									& IVisibility.COMPOSITION_DISPLAY_ELEMENTS;
						if (compositionVisibility == IVisibility.COMPOSITION_DISPLAY_ELEMENTS) {
							parent.addChild(child);
							child.addParent(parent);

							// edge building
							this.edgeBuilder
								.buildEdge(parent, child, direction);
						}
						break;
					}
				case IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS :
					{
						// Container Aggregation 
						int containerAggregationVisibility =
							e.getVisibleElements()
									& IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS;
						if (containerAggregationVisibility == IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS) {
							parent.addChild(child);
							child.addParent(parent);

							// edge building
							this.edgeBuilder
								.buildEdge(parent, child, direction);
						}
						break;
					}
				case IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS :
					{
						// Container Composition
						int containerCompositionVisibility =
							e.getVisibleElements()
									& IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS;
						if (containerCompositionVisibility == IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS) {
							parent.addChild(child);
							child.addParent(parent);

							// edge building
							this.edgeBuilder
								.buildEdge(parent, child, direction);
						}
						break;
					}
				case IVisibility.CREATION_DISPLAY_ELEMENTS :
					{
						// Creation
						int creationVisibility =
							e.getVisibleElements()
									& IVisibility.CREATION_DISPLAY_ELEMENTS;
						if (creationVisibility == IVisibility.CREATION_DISPLAY_ELEMENTS) {
							parent.addChild(child);
							child.addParent(parent);

							// edge building
							this.edgeBuilder
								.buildEdge(parent, child, direction);
						}
						break;
					}
				case IVisibility.DELEGATION_DISPLAY_ELEMENTS :
					{
						// Delegation 
						// TODO 
						// This field don't exist in the swing implementation!visible
						int delegationVisibility =
							e.getVisibleElements()
									& IVisibility.DELEGATION_DISPLAY_ELEMENTS;
						if (delegationVisibility == IVisibility.DELEGATION_DISPLAY_ELEMENTS) {
							parent.addChild(child);
							child.addParent(parent);

							// edge building
							this.edgeBuilder
								.buildEdge(parent, child, direction);
						}

						break;
					}
				case IVisibility.USE_DISPLAY_ELEMENTS :
					{
						// Use
						int useVisibility =
							e.getVisibleElements()
									& IVisibility.USE_DISPLAY_ELEMENTS;
						if (useVisibility == IVisibility.USE_DISPLAY_ELEMENTS) {
							parent.addChild(child);
							child.addParent(parent);

							// edge building
							this.edgeBuilder
								.buildEdge(parent, child, direction);
						}
						break;
					}
				default :
					break;
			}
		}
	}

	// 12-07-2006
	private INode findNode(final Entity anEntity) {
		INode targetNode = null;
		final Iterator itr = this.nodeCreated.iterator();
		while (itr.hasNext()) {
			Node next = (Node) itr.next();
			if (next.getEntity() == anEntity) {
				targetNode = next;
				break;
			}
		}

		return targetNode;
	}

	// 11-07-2006
	public void upDateLinkNodes() {
		// AssociationsLinkUpDater
		// AggregationLinkUpDater
		// CompositionLinkUpDater
		// UseLinkUpDater
		// ...

		final Iterator nodeItr = this.nodeCreated.iterator();
		while (nodeItr.hasNext()) {
			final INode originNode = (Node) nodeItr.next();
			final Entity origin = originNode.getEntity();

			// for all links ecepts Generalisation 
			final Element[] allElements = origin.getElements();
			for (int j = 0; j < allElements.length; j++) {
				if (allElements[j] instanceof Relationship) {
					Entity target =
						((Relationship) allElements[j]).getTargetEntity();

					final INode targetNodes = findNode(target);
					if (targetNodes != null) {
						if (allElements[j] instanceof ContainerComposition) {
							this
								.setElementsVisibility(
									originNode,
									targetNodes,
									IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS);
						}
						if (allElements[j] instanceof ContainerAggregation) {
							this
								.setElementsVisibility(
									originNode,
									targetNodes,
									IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS);
						}
						if (allElements[j] instanceof Composition) {
							this.setElementsVisibility(
								originNode,
								targetNodes,
								IVisibility.COMPOSITION_DISPLAY_ELEMENTS);
						}
						if (allElements[j] instanceof Aggregation) {
							this.setElementsVisibility(
								originNode,
								targetNodes,
								IVisibility.AGGREGATION_DISPLAY_ELEMENTS);
						}
						if (allElements[j] instanceof Association) {
							this.setElementsVisibility(
								originNode,
								targetNodes,
								IVisibility.ASSOCIATION_DISPLAY_ELEMENTS);
						}
						if (allElements[j] instanceof Creation) {
							this.setElementsVisibility(
								originNode,
								targetNodes,
								IVisibility.CREATION_DISPLAY_ELEMENTS);
						}
						if (allElements[j] instanceof Delegation) {
							this.setElementsVisibility(
								originNode,
								targetNodes,
								IVisibility.DELEGATION_DISPLAY_ELEMENTS);
						}
						if (allElements[j] instanceof Use) {
							this.setElementsVisibility(
								originNode,
								targetNodes,
								IVisibility.USE_DISPLAY_ELEMENTS);
						}
					}
				}
			}

			// TODO : sometimes we delete this links but it exist for other links so 
			// TODO : morre abstarction for the algorithmes 

			// now we test the generalisations links
			// if not visible we shoud delete them because they are added dy default
			final AbstractInheritance[] generalisations =
				origin.getHierarchies();
			for (int j = 0; j < generalisations.length; j++) {
				final Entity target = generalisations[j].getTargetEntity();

				final INode targetNodes = this.findNode(target);
				if (targetNodes != null) {
					this.setElementsVisibility(
						originNode,
						targetNodes,
						IVisibility.HIERARCHY_DISPLAY_ELEMENTS);
				}
			}
		}

		//		for (int i = 0; i < someEntities.length; i++) {
		//			System.out.println("Entity name: " + someEntities[i].getName());
		//			final Element[] allElements = someEntities[i].getElements();
		//			System.out.println("allElements.length : " + allElements.length);
		//			for (int j = 0; j < allElements.length; j++) {
		//				if (allElements[j] instanceof Association) { // Relationship
		//					Entity origin = ((Relationship) allElements[j])
		//						.getOriginEntity();
		//					Entity target = ((Relationship) allElements[j])
		//						.getTargetEntity();
		//					//	System.out.println("Origin: " + origin.getName());
		//					System.out.println("Target: " + target.getName());
		//
		//				}
		//			}
		//		}
	}

	public List getNodeCreated() {
		return this.nodeCreated;
	}
}
