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
package mendel.family;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import mendel.family.RoleTag.Tag;
import mendel.model.JClassEntity;

public class BehaviorHomogeneity {
		
	private double limit = .5; // default

	public float homogeneity(Family family) {
		Tag hTag = Tag.Other;
		float maxFrac = 0;
		int total = family.familySize();
		Map<RoleTag.Tag, Set<JClassEntity>> sets = family.familySets();
		for (Tag tag : sets.keySet()) {
			float frac = sets.get(tag).size() / (float) total;
			if( frac>maxFrac ) {
				maxFrac = frac;
				hTag = tag;
			}
		}
		family.setBehaviorTag(hTag);
		family.setHomogeneity(maxFrac);
		return maxFrac;
	}
	
	public Tag checkHomogeneity(Family family) {
		family.setBehaviorTag(strongHomogeneity(family));
		return family.behaviorTag();
	}

	public Collection<Family> findHomogeneousFamilies(Collection<Family> families) {
		Collection<Family> hFamilies = new Vector<Family>();
		for (Family f : families) {
			if( strongHomogeneity(f)!=Tag.Other ) {
				hFamilies.add(f);
			}
		}
		return hFamilies;
	}
	
	public Tag strongHomogeneity(Family family) {
		//	double limit = .75 * total;
		if( family.homogeneity() > homogeneityLimit() ) {
			return family.behaviorTag();
		} else {
			return Tag.Other;	
		}
	}
	
	public double homogeneityLimit() {
		return this.limit;
	}

	public void setLimit(double limit) {
		this.limit = limit;
	}
	
	

	
//	public Object[] homogeneity(Map<Tag, Set<IEntity>> tags) {
//		Tag hTag = Tag.Unknown;
//		float maxFrac = 0;
//		int total = 0;
//		for (Set<IEntity> set : tags.values()) {
//			total += set.size();
//		}
//		for (Tag tag : tags.keySet()) {
//	 		float frac = tags.get(tag).size() / (float) total;
//			if( frac>maxFrac ) {
//				maxFrac = frac;
//				hTag = tag;
//			}
//		}
//		return new Object[] { hTag, maxFrac };
//	}

	
//	public Collection<JClassEntity> findHomogeneousFamilies() {
//		Collection<JClassEntity> hRoots = new Vector<JClassEntity>();
//		for (JClassEntity root : this.familyRoots) {
//			Map<Tag, Set<IEntity>> tags = tagFamily(root);
//			Tag t = checkHomogeneity(tags);
//			if( t!=Tag.Unknown ) {
//				System.out.println("H " + t + " " + root.getEntityName());
//				hRoots.add(root);
//			}
//		}
//		return hRoots;
//	}
//	
//	public Tag checkHomogeneity(Map<Tag, Set<IEntity>> tags) {
//		int total = 0;
//		for (Set<IEntity> set : tags.values()) {
//			total += set.size();
//		}
//		double limit = .75 * total;
//		for (Tag tag : tags.keySet()) {
//			if( tags.get(tag).size() >= limit ) { 
//				return tag; // bingo !
//			}
//		}
//		return Tag.Unknown;
//	}
	
//	public void showHomogeneousFamilies() {
//		Collection<JClassEntity> roots = findHomogeneousFamilies();
//		for (JClassEntity root : roots) {
//			System.out.println("\n" + root.getEntityName() + " DIT=" + root.inheritanceDepth() + " NOC=" + root.familySize());
//		}
//	}

}
