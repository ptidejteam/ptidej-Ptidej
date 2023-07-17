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
package ptidej.ui.layout.repository.sugiyama.graph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author kahlamoh
 * @since 20/07/2006
 *
 */
public class Graph {
	private List levels;
	private int nbLevels;

	public Graph(int anNbLevels) {
		this.levels = new ArrayList(anNbLevels);
		this.nbLevels = anNbLevels;
		this.buildLevels();
	}

	public void buildLevels() {
		for (int i = 0; i < this.nbLevels; i++) {
			this.levels.add(i, new Level(i));
		}
	}

	public void setLevel(final Level aLevel) {
		this.levels.add(aLevel.getLevelIndex(), aLevel);
	}

	public void addNode(final INode aNode) {
		final int levelIndex = aNode.getLevel();
		final Level level = (Level) this.levels.get(levelIndex);
		level.addNode(aNode);
	}

	/**
	 * This method add dummy nodes between two nodes
	 * if necessary 
	 * if parameter are not coehrent : no thig is done
	 * @param parent a Node 
	 * @param child a Node
	 */
	public List addDummyNodes(final INode aParent, final INode aChild) {
		final List dummiesAdded = new ArrayList();

		final int parentLevel = aParent.getLevel();
		final int childLevel = aChild.getLevel();

		// Long edge
		if (childLevel - parentLevel > 1) {
			final DummyNode dummyFirst =
				new DummyNode(parentLevel + 1, aParent, null);
			INode lastDummy = dummyFirst;
			// we added the to right level!
			final Level startLevel = (Level) this.levels.get(parentLevel + 1);
			startLevel.addNode(lastDummy);
			dummiesAdded.add(dummyFirst);
			for (int i = parentLevel + 2; i < childLevel; i++) {
				DummyNode dummy = new DummyNode(i, lastDummy, null);
				// when you add a null object to a list 
				// this null object must be cleared !
				lastDummy.getChildren().clear();
				lastDummy.addChild(dummy);
				lastDummy = dummy;
				// we added the to right level!
				Level currentLevel = (Level) this.levels.get(i);
				currentLevel.addNode(dummy);
				dummiesAdded.add(dummy);

			}
			// this correction is done 07-07-2006
			// when you a null to a list 
			// this null object must be cleared !
			lastDummy.getChildren().clear();
			lastDummy.addChild(aChild);

			// this is performed by the DummyBuilder class
			//			aChild.getParents().remove(aParent);

			aChild.addParent(lastDummy);
		}
		return dummiesAdded;
	}

	/**
	 * this method add dummy nodes between a parent and his
	 * children found at the same level (the dummy node is unique!)
	 * used for the hierarchy links
	 * @param parent
	 * @param children : all the children
	 */
	// modification 24-07-2006
	public List addHierarchyDummy(final INode aParent, final List aChildren) {
		final List dummiesAdded = new ArrayList();
		final int parentLevel = aParent.getLevel();
		final int childrenLevel = ((Node) aChildren.get(0)).getLevel();
		boolean levelTest = true; // we suppose the information correct!
		ListIterator itr = aChildren.listIterator(0);
		// we test if all the children are on the same level
		while (itr.hasNext()) {
			//		for (int i = 0; i < aChildren.length; i++) {
			Node child = (Node) itr.next();
			if (child.getLevel() != childrenLevel) {
				levelTest = false;
				break;
			}
		}
		// if the test passed
		if (levelTest && (childrenLevel - parentLevel > 1)) {
			// from children to parents
			DummyNode dummyFirst = new DummyNode(childrenLevel - 1, null, null);
			dummyFirst.getChildren().clear();
			// we added the to right level!
			Level startLevel = (Level) this.levels.get(childrenLevel - 1);
			startLevel.addNode(dummyFirst);
			dummiesAdded.add(dummyFirst);
			//			ListIterator chilItr = aChildren.listIterator(0);
			// we add all the children
			//			while (chilItr.hasNext()) {

			// all this children share the same dummy Nodes
			// so the first dummy we be a parent of all the children
			for (int i = 0; i < aChildren.size(); i++) {
				Node child = (Node) aChildren.get(i); // chilItr.next(); // aChildren[i]; //
				dummyFirst.addChild(child);

				// this is performed by the DummyBuilder class
				//			child.getParents().remove(aParent);

				child.addParent(dummyFirst);
			}

			Node lastDummy = dummyFirst;
			for (int i = childrenLevel - 2; i > parentLevel; i--) {
				DummyNode dummy = new DummyNode(i, null, lastDummy);
				// when you add a null object to a list 
				// this null object must be cleared !
				lastDummy.getParents().clear();
				lastDummy.addParent(dummy);
				lastDummy = dummy;
				// we added the dummy node to right level!
				Level currentLevel = (Level) this.levels.get(i);
				currentLevel.addNode(dummy);
				dummiesAdded.add(dummy);

			}
			// this correction is done 07-07-2006
			// when you a null to a list 
			// this null object must be cleared !
			lastDummy.getParents().clear();
			lastDummy.addParent(aParent);
			aParent.addChild(lastDummy);
		}

		return dummiesAdded;
	}

	/**
	 * 
	 * @return all the REAL Nodes (whithout dummies)
	 */
	public List getNodes() {
		final List nodes = new ArrayList();
		for (int i = 0; i < this.nbLevels; i++) {
			Level level = (Level) this.levels.get(i);
			final List nodesLevel = level.getNodes();
			final Iterator itr = nodesLevel.iterator();
			while (itr.hasNext()) {
				Node next = (Node) itr.next();
				// if not Dummy
				if (!(next instanceof DummyNode))
					nodes.add(next);
			}
		}
		return nodes;
	}

	/**
	 * @return all the Nodes including dummies one!
	 */
	public List getAllNodes() {
		final List nodes = new ArrayList();
		for (int i = 0; i < this.nbLevels; i++) {
			Level level = (Level) this.levels.get(i);
			nodes.addAll(level.getNodes());
		}
		return nodes;
	}

	/**
	 * 
	 * @return
	 */
	public int getNbLevels() {
		return this.nbLevels;
	}

	/**
	 * @param anIndexLevel
	 * @return
	 */
	public Level getLevel(int anIndexLevel) {
		Level level = (Level) this.levels.get(anIndexLevel);
		return level;
	}

}
