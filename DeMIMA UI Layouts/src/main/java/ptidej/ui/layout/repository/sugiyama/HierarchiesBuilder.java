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
package ptidej.ui.layout.repository.sugiyama;

import gnu.trove.map.hash.TObjectIntHashMap;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import ptidej.ui.kernel.AbstractInheritance;
import ptidej.ui.kernel.Entity;

/**
 * @author mohamedkahla
 * @date 16-06-2006
 * @author Yann
 * @date 2014/04/11
 */
public class HierarchiesBuilder {
	private static HierarchiesBuilder UniqueInstance;
	public static HierarchiesBuilder getInstance() {
		if (HierarchiesBuilder.UniqueInstance == null) {
			HierarchiesBuilder.UniqueInstance = new HierarchiesBuilder();
		}
		return HierarchiesBuilder.UniqueInstance;
	}

	private List graphHierarchy;
	private int nbLevels;

	private HierarchiesBuilder() {
		this.graphHierarchy = new ArrayList();
		this.nbLevels = -1;
	}

	// 1- look for all nodes that don't have parents 
	// 2- we make there level to current level == 0
	// 3- for all nodes find in the current level 
	// we find there children
	// 4- if at least one of them have at least a child we continue
	// other wise STOP!
	// 5- if a child has all ready a level assigned
	// we put it in the last level
	// Finally, we have to make attention about if ghosts are visible or not
	/**
	 *  date : 12/05/2006
	 * @Author : Mohamed Kahla  
	 */
	// //////// //
	// STEP 1 //
	// //////// //
	public void buildHierarchies(final Entity[] someEntities) {
		this.graphHierarchy.clear();
		this.graphHierarchy.add(new ArrayList());
		int currentLevel = 0;

		// 15-05-2006
		// if (someEntities.length > 0) {

		// Yann 2014/04/11: Multiple inheritances
		// Because an entity may have a parent there
		// and be there because another of its parents
		// is in currentLevel - 2 or -3 or -4... then
		// I store the level where I put it last.
		final TObjectIntHashMap mapEntitiesLevels = new TObjectIntHashMap();

		// 1- look for all nodes that don't have parents 
		// and the nodes that have parents not visible
		// 2- we make there level to current level == 0

		// first level
		for (int i = 0; i < someEntities.length; i++) {
			boolean haveVisibleParents = false;
			final AbstractInheritance[] hierarchies =
				someEntities[i].getHierarchies();
			for (int j = 0; j < hierarchies.length; j++) {
				final Entity parentEntity = hierarchies[j].getTargetEntity();
				// Yann 2013/06/04: Selection
				// What if the temporaryEntity exist but is 
				// not visible (i.e., selected to be displayed)?
				// I search in the set of given entities to be
				// displayed if the temporaryEntity is there 
				// first before checking for it being a Ghost.
				// Yann 2014/04/28: Ghosts are ghosts...
				// If ghosts are not selected to be displayed,
				// none will belong to the set of given entities.
				// Therefore, the test below is enough and no
				// need for the other extra tests... which by the
				// way are useless because haveVisibleParents is
				// always set to true.
				if (ArrayUtils.contains(someEntities, parentEntity)) {
					//	if (!(parentEntity instanceof Ghost)) {
					//		// for not Ghost
					haveVisibleParents = true;
					break;
					//	}
					//	else if (ghostVisibility) {
					//		// Ghosts are visible
					//		haveVisibleParents = true;
					//		break;
					//	}
				}
			}
			if (!haveVisibleParents) {
				((List) this.graphHierarchy.get(0)).add(someEntities[i]);
				mapEntitiesLevels.put(someEntities[i], 0);
			}
		}

		// 3- for all nodes find in the current level 
		// we find there children
		// or we find all the nodes that have there parents at 
		// current level 
		boolean stop = false;
		while (!stop) { //  if at least one of them have at least a child we continue
			currentLevel++; // next level
			this.graphHierarchy.add(new ArrayList());
			stop = true; // may be the last level
			for (int i = 0; i < someEntities.length; i++) {
				boolean haveParentsThere = false;
				final Entity entity = someEntities[i];
				final AbstractInheritance[] hierarchies =
					entity.getHierarchies();
				for (int j = 0; j < hierarchies.length; j++) {
					final Entity parentEntity =
						hierarchies[j].getTargetEntity();
					final List previousLevel =
						(List) this.graphHierarchy.get(currentLevel - 1);
					if (previousLevel.contains(parentEntity)) {
						// if parents found
						// Yann 2014/04/11: Multiple inheritances!
						// Because an entity may have a parent there
						// and be there because another of its parents
						// is in currentLevel -2 or -3 or -4... then
						// I remove it from above!
						if (mapEntitiesLevels.contains(entity)) {
							((List) this.graphHierarchy.get(mapEntitiesLevels
								.get(entity))).remove(entity);
						}
						haveParentsThere = true;
						break;
					}
				}
				if (haveParentsThere) {
					((List) this.graphHierarchy.get(currentLevel)).add(entity);
					mapEntitiesLevels.put(someEntities[i], currentLevel);
					stop = false; // we shoud look for an other level
				}
			}
		}

		// we have to correct the number of levels
		this.graphHierarchy.remove(currentLevel);
		this.nbLevels = currentLevel;
	}

	public List getGraphHierarchies() {
		return this.graphHierarchy;
	}

	// Step 1 of the Sugiyama's algorithm
	// Formation of a proper hierarchy from
	// the liste of Entities
	// from the down to the top : the hierarchy of the GRAPH
	// in the level 0 we find Node whith no childrens
	// In a given level n of the hierarchy you find
	// the 'nodes' of the 'graph' having at list a child  
	// in the level (n - 1)
	public int getNbLevels() {
		return this.nbLevels;
	}
}
