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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import mendel.metric.DispatchMetric;
import mendel.model.IEntity;
import mendel.model.JClassEntity;
import mendel.model.JClassEntity.Category;
import mendel.model.JClassEntity.Group;

public class RoleTag extends DispatchMetric {

	public enum Tag {
//		Static, 
		Extender, PureExtender, PureOverrider, Overrider, Other;
	}
	
	private HashMap<Tag, Set<IEntity>> classSets;	

	
	public RoleTag() {
		this.classSets = new HashMap<Tag, Set<IEntity>>();
		for (Tag tag : Tag.values()) {
			this.classSets.put(tag, new HashSet<IEntity>());
		}
	}

	public Tag tag(JClassEntity entity) {
		if(	entity.count(Group.LOCAL) == 0 ) { // pure subtype/taxonomy
			return Tag.Other; // TODO: replace by Static
		} // Subtype = static + DIT > 1
		if( entity.count(Group.OVERD) == 0 ) {  // extender/extender
			return Tag.PureExtender;
		}
		if( entity.count(Category.NEW) == 0 ) { // specializer/overrider 
			return Tag.PureOverrider;
		}
		if( entity.count(Category.NEW) > entity.count(Group.OVERD)) {
			return Tag.Extender;
		}
		if( entity.count(Group.OVERD) >= entity.count(Category.NEW)) {
			return Tag.Overrider;
		}
		return Tag.Other;
	}
	
	public Tag registerClass(JClassEntity entity) {
		Tag t = tag(entity);
		this.classSets.get(tag(entity)).add(entity);
		return t;
	}
	

	public Map<Tag, Set<JClassEntity>> tagSet(Collection<JClassEntity> classSet) {
		HashMap<Tag, Set<JClassEntity>> sets = new HashMap<Tag, Set<JClassEntity>>();
		for (Tag tag : Tag.values()) {
			sets.put(tag, new HashSet<JClassEntity>());
		}
		for (JClassEntity entity : classSet) {
			sets.get(tag(entity)).add(entity);
		}
		return sets;
	}


	@Override
	public String compute(JClassEntity entity) {
		return registerClass(entity).toString();
	}

	
	public Map<Tag, Set<IEntity>> getSets() {
		return this.classSets;
	}
	
	public void showClassSets(boolean verbose) {
		for (Tag tag : Tag.values()) {
			System.out.println("\n\n" + tag + " " + this.classSets.get(tag).size());
			if( verbose ) {
				for (IEntity entity : this.classSets.get(tag)) {
					System.out.print(entity.getEntityName() + " ");
				}				
			}
		}
	}

	
	public String getName() {
		return "Tag";
	}

	
}
