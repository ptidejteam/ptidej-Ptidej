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
package ptidej.ui.layout.repository.sugiyama.graph.builders;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import ptidej.ui.layout.repository.sugiyama.LinkObserver;
import ptidej.ui.layout.repository.sugiyama.LinkTypes;
import ptidej.ui.layout.repository.sugiyama.graph.DummyNode;
import ptidej.ui.layout.repository.sugiyama.graph.EdgeSet;
import ptidej.ui.layout.repository.sugiyama.graph.Graph;
import ptidej.ui.layout.repository.sugiyama.graph.INode;
import ptidej.ui.layout.repository.sugiyama.graph.Level;
import ptidej.ui.layout.repository.sugiyama.graph.Link;
import ptidej.ui.layout.repository.sugiyama.graph.Node;

/**
 * @author kahlamoh
 * @since 2006/07/21
 */
// TODO Should be a Singleton!
public class DummyBuilder implements LinkTypes {
	private Graph graph;
	private EdgeSet edgeSet;

	/**
	 * 
	 */
	public DummyBuilder(Graph aGraph, EdgeSet anEdgeSet) {
		this.graph = aGraph;
		this.edgeSet = anEdgeSet;
	}

	private boolean deboging = false;

	/**
	 * 
	 */
	public boolean getDebugStatus() {
		return this.deboging;

	}

	/**
	 * 
	 * 
	 */
	public void activateDebuging() {
		this.deboging = true;
	}

	/**
	 * 
	 * 
	 */
	public void deActivateDebuging() {
		this.deboging = false;
	}

	/**
	 * 
	 * 
	 */
	public void printDebugMessage(String message) {
		if (getDebugStatus())
			System.out.println(message);

	}

	/**
	 * @author kahlamoh
	 * @since 2006/07/21
	 * Builds the dummy nodes for all links
	 * I suppose that the links other than for
	 * generalisation share the same edges!
	 * TODO: I need some more explanations :-)
	 */
	public void buildDummy() {

		printDebugMessage("5.1 : biulding Dummies...");

		// for all the levels
		// from parents to children
		for (int i = 0; i < this.graph.getNbLevels(); i++) {
			printDebugMessage("5.1.1 : parents level ... " + i);
			final Level level = this.graph.getLevel(i);
			final INode[] nodes = level.getTabNodes();
			// for all nodes in this level
			//			ListIterator itr = nodes.listIterator(0);
			//			while (itr.hasNext()) {
			for (int r = 0; r < nodes.length; r++) {
				final INode parent = nodes[r]; // (Node) itr.next();
				// if not dummy and have children!
				if (!(parent instanceof DummyNode)
						&& !parent.getChildren().isEmpty()) {
					// for all levels distant from the parent
					// where we could found children
					// TODO we could optimize here!
					for (int j = parent.getLevel() + 2; j < this.graph
						.getNbLevels(); j++) {

						final Level currentlevel = this.graph.getLevel(j);
						final INode[] childrenLevel =
							currentlevel.getTabChildren(parent);

						printDebugMessage("5.1.1.1 : children level ... " + j);
						printDebugMessage("5.1.1.2 : Level size ... "
								+ currentlevel.getNodes().size());
						printDebugMessage("5.1.1.3 : childrenLevel size ... "
								+ childrenLevel.length);

						// for children that have a Hierarchy link with 
						// this parent
						// TODO: Why are those lists needed? It seems to
						// me that only one element is added, see the
						// "specialisationAdded" boolean.
						final List implementationChildren = new ArrayList();
						final List specialisationChildren = new ArrayList();
						final List otherLinksTypeChildren = new ArrayList();

						for (int k = 0; k < childrenLevel.length; k++) {
							printDebugMessage("5.1.1.1.1 : childrenLevelItr ..");

							final INode next = childrenLevel[k];

							// if not dummy
							if (!(next instanceof DummyNode)) {

								final List links =
									new LinkObserver().getLinks(parent, next);
								printDebugMessage("");
								printDebugMessage("5.1.1.1.2 : Link size ... "
										+ links.size());

								// if other links present and visible!
								// Here I add a unique Dummy node for all other
								// kinds of links
								// so it's represented by a unique edge!

								// If we want to add an edge for evry link
								// we shoud add as dummies nodes as links present
								boolean implementationAdded = false;
								boolean specialisationAdded = false;
								boolean otherLinksAdded = false;
								ListIterator linkItr = links.listIterator(0);
								while (linkItr.hasNext()) {
									Link link = (Link) linkItr.next();

									if (link.getLinkType() == SPECIALISATION) {
										if (!specialisationAdded) {
											specialisationChildren.add(next);
											specialisationAdded = true;
										}
									}
									else if (link.getLinkType() == IMPLEMENTATION) {
										if (!implementationAdded) {
											implementationChildren.add(next);
											implementationAdded = true;
										}
									}
									else if (!otherLinksAdded) {
										printDebugMessage("5.1.2.2 : Other Links ..");
										otherLinksTypeChildren.add(next);
										otherLinksAdded = true;

									}

									// if all present!
									if (specialisationAdded
											&& implementationAdded
											&& otherLinksAdded)
										break;

								}
							}
						}

						if (!implementationChildren.isEmpty()) {
							final List dummiesAdded =
								this.graph.addHierarchyDummy(
									parent,
									implementationChildren);
							// for all the children
							ListIterator itr =
								implementationChildren.listIterator(0);
							while (itr.hasNext()) {

								printDebugMessage("implementationChildren");

								Node next = (Node) itr.next();
								this.edgeSet.setDummyNodes(
									parent,
									next,
									dummiesAdded);
							}
						}

						if (!specialisationChildren.isEmpty()) {
							final List dummiesAdded =
								this.graph.addHierarchyDummy(
									parent,
									specialisationChildren);

							// for all the children
							final Iterator itr =
								specialisationChildren.iterator();
							while (itr.hasNext()) {

								printDebugMessage("specialisationChildren");

								Node next = (Node) itr.next();
								this.edgeSet.setDummyNodes(
									parent,
									next,
									dummiesAdded);
							}
						}

						// for all the others
						final Iterator itr = otherLinksTypeChildren.iterator();
						while (itr.hasNext()) {
							final INode next = (INode) itr.next();
							final List dummiesAdded =
								this.graph.addDummyNodes(parent, next);
							parent.getChildren().remove(next);
							next.getParents().remove(parent);

							this.edgeSet.setDummyNodes(
								parent,
								next,
								dummiesAdded);

						}

						// we remove if necessary from the other lists
						ListIterator itr1 =
							implementationChildren.listIterator(0);
						while (itr1.hasNext()) {
							Node next = (Node) itr1.next();
							parent.getChildren().remove(next);
							next.getParents().remove(parent);
						}
						ListIterator itr2 =
							specialisationChildren.listIterator(0);
						while (itr2.hasNext()) {
							Node next = (Node) itr2.next();
							parent.getChildren().remove(next);
							next.getParents().remove(parent);
						}
					}
				}
			}
		}
	}
}
