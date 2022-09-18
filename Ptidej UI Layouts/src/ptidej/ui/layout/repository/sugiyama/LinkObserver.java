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
package ptidej.ui.layout.repository.sugiyama;

import java.util.ArrayList;
import java.util.List;
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
import ptidej.ui.layout.repository.sugiyama.graph.Link;

/**
 * @author kahlamoh
 *
 */
public class LinkObserver implements LinkTypes {
	// TODO Make it final + Use INode instead of Node
	private List links;

	/**
	 *@author Mohamed Kahla 
	 *@since 2006/07/25
	 *@param the two vertces
	 *@return a list of all the links founded
	 * in the two directions
	 */
	public List getLinks(final INode aParent, final INode aChild) {
		this.links = new ArrayList();

		// one direction
		this.getOneDirectionLinks(aChild, aParent);
		//other direction
		this.getOneDirectionLinks(aParent, aChild);

		// special care about Generalisations links
		// one direction
		this.addHierachyLink(aParent, aChild);
		// other direction
		this.addHierachyLink(aChild, aParent);

		return this.links;
	}

	/**
	 *@author Mohamed Kahla 
	 *@since 2006/07/21
	 *@param the two vertces
	 *@return a list of all the links founded in one direction
	 *except Generalisation 
	 *
	 */
	public void getOneDirectionLinks(final INode aParent, final INode aChild) {
		// TODO 
		// because when building nodes, I supposed that nodes 
		// at lowest lovel are child so I have take it in 
		// considiration here
		final Entity origin = aParent.getEntity();
		final Element[] allElements = origin.getElements();
		for (int j = 0; j < allElements.length; j++) {
			if (allElements[j] instanceof Relationship) {
				final Entity target =
					((Relationship) allElements[j]).getTargetEntity();

				// if the target is the child
				if (target == aChild.getEntity()) {
					// if (target.getName().equals(aParent.getEntity().getName())) {

					// work!
					if (allElements[j] instanceof Aggregation) {
						// Aggregation
						int aggregationVisibility =
							origin.getVisibleElements()
									& IVisibility.AGGREGATION_DISPLAY_ELEMENTS;
						// if visible!
						if (aggregationVisibility == IVisibility.AGGREGATION_DISPLAY_ELEMENTS) {
							this.links.add(new Link(
								IVisibility.AGGREGATION_DISPLAY_ELEMENTS,
								aParent,
								aChild));
						}
					}
					// work!
					if (allElements[j] instanceof Association) {
						// Association
						int assotiationVisibility =
							origin.getVisibleElements()
									& IVisibility.ASSOCIATION_DISPLAY_ELEMENTS;
						// if visible!
						if (assotiationVisibility == IVisibility.ASSOCIATION_DISPLAY_ELEMENTS) {
							this.links.add(new Link(
								IVisibility.ASSOCIATION_DISPLAY_ELEMENTS,
								aParent,
								aChild));
						}
					}
					if (allElements[j] instanceof Composition) {
						// Composition
						int compositionVisibility =
							origin.getVisibleElements()
									& IVisibility.COMPOSITION_DISPLAY_ELEMENTS;
						// if visible!
						if (compositionVisibility == IVisibility.COMPOSITION_DISPLAY_ELEMENTS) {
							this.links.add(new Link(
								IVisibility.COMPOSITION_DISPLAY_ELEMENTS,
								aParent,
								aChild));
						}
					}
					// Container aggregation seen as aggregation!
					// TODO
					if (allElements[j] instanceof ContainerAggregation) {
						// Container Aggregation 
						int containerAggregationVisibility =
							origin.getVisibleElements()
									& IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS;
						// if visible!
						if (containerAggregationVisibility == IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS) {
							this.links
								.add(new Link(
									IVisibility.CONTAINER_AGGREGATION_DISPLAY_ELEMENTS,
									aParent,
									aChild));
						}
					}
					if (allElements[j] instanceof ContainerComposition) {
						// Container Composition
						int containerCompositionVisibility =
							origin.getVisibleElements()
									& IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS;
						// if visible!
						if (containerCompositionVisibility == IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS) {
							this.links
								.add(new Link(
									IVisibility.CONTAINER_COMPOSITION_DISPLAY_ELEMENTS,
									aParent,
									aChild));
						}
					}
					// TODO
					// Not working!
					if (allElements[j] instanceof Creation) {
						// Creation
						int creationVisibility =
							origin.getVisibleElements()
									& IVisibility.CREATION_DISPLAY_ELEMENTS;
						// if visible!
						if (creationVisibility == IVisibility.CREATION_DISPLAY_ELEMENTS) {
							this.links.add(new Link(
								IVisibility.CREATION_DISPLAY_ELEMENTS,
								aParent,
								aChild));
						}
					}
					if (allElements[j] instanceof Delegation) {
						// Delegation 
						// TODO 
						// This field don't exist in the swing implementation!visible
						int delegationVisibility =
							origin.getVisibleElements()
									& IVisibility.DELEGATION_DISPLAY_ELEMENTS;
						// if visible!
						if (delegationVisibility == IVisibility.DELEGATION_DISPLAY_ELEMENTS) {
							this.links.add(new Link(
								IVisibility.DELEGATION_DISPLAY_ELEMENTS,
								aParent,
								aChild));
						}
					}
					if (allElements[j] instanceof Use) {
						// Use
						int useVisibility =
							origin.getVisibleElements()
									& IVisibility.USE_DISPLAY_ELEMENTS;
						// if visible!
						if (useVisibility == IVisibility.USE_DISPLAY_ELEMENTS) {
							this.links.add(new Link(
								IVisibility.USE_DISPLAY_ELEMENTS,
								aParent,
								aChild));
						}
					}
					break;
				}
			}
		}
	}

	/**
	 * 
	 * @param aParent
	 * @param aChild
	 * @return true if link present and visible
	 * false otherwise
	 */
	private boolean addHierachyLink(final INode aParent, final INode aChild) {
		// TODO 
		// because when building nodes i supposed that nodes 
		// at lowest lovel are child so I have take it in 
		// considiration here

		boolean linkImplementationDetected = false;
		boolean linkSpecialisationDetected = false;

		Entity origin = aParent.getEntity();
		Entity target = aChild.getEntity();
		// but not allwas so!

		// Abstract Generalisation 
		// Implementation & Specialisation
		int abstractGenerilisationVisibility =
			origin.getVisibleElements()
					& IVisibility.HIERARCHY_DISPLAY_ELEMENTS;
		// if visible!
		if (abstractGenerilisationVisibility == IVisibility.HIERARCHY_DISPLAY_ELEMENTS) {
			final AbstractInheritance[] dHierarchies = origin.getHierarchies();

			for (int j = 0; j < dHierarchies.length; j++) {
				final AbstractInheritance generalisation = dHierarchies[j];
				final Entity temporaryEntity = generalisation.getTargetEntity();
				if (temporaryEntity == target) {
					if (generalisation instanceof Implementation
							&& !linkImplementationDetected) {
						this.links
							.add(new Link(IMPLEMENTATION, aParent, aChild));
						linkImplementationDetected = true;
						break;
					}
					if (generalisation instanceof Specialisation
							&& !linkSpecialisationDetected) {
						this.links
							.add(new Link(SPECIALISATION, aParent, aChild));
						linkSpecialisationDetected = true;
						break;
					}
				}

				// if the two links are present
				if (linkImplementationDetected && linkSpecialisationDetected)
					break;
			}
		}
		return (linkImplementationDetected || linkSpecialisationDetected);
	}

}
