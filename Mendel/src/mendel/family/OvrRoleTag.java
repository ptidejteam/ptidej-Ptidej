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

public class OvrRoleTag extends DispatchMetric {

	// TODO: factor with RoleTag
	
	public enum OvrTag {
		PureSpecializer, PureReplacer, Specializer, Replacer, Equals, NoOvr;
	}
	
	private HashMap<OvrTag, Set<IEntity>> classSets;

	
	public OvrRoleTag() {
		this.classSets = new HashMap<OvrTag, Set<IEntity>>();
		for (OvrTag tag : OvrTag.values()) {
			this.classSets.put(tag, new HashSet<IEntity>());
		}
	}

	public OvrTag tag(JClassEntity entity) {
		if( entity.count(Group.OVERD) == 0 ) {
			return OvrTag.NoOvr;
		}
		if( entity.count(Category.RPL) == 0 ) { 
			return OvrTag.PureSpecializer;
		}
		if( entity.count(Category.SPC) == 0 ) { 
			return OvrTag.PureReplacer;
		}
		if( entity.count(Category.SPC) > entity.count(Category.RPL)) {
			return OvrTag.Specializer;
		}
		if( entity.count(Category.RPL) > entity.count(Category.SPC)) {
			return OvrTag.Replacer;
		}
		return OvrTag.Equals;
	}
	
	public OvrTag registerClass(JClassEntity entity) {
		OvrTag t = tag(entity);
		this.classSets.get(tag(entity)).add(entity);
		return t;
	}
	

	@Override
	public String compute(JClassEntity entity) {
		return registerClass(entity).toString();
	}

	
	public Map<OvrTag, Set<IEntity>> getSets() {
		return this.classSets;
	}
	
	public void showClassSets(boolean verbose) {
		for (OvrTag tag : OvrTag.values()) {
			System.out.println("\n\n" + tag + " " + this.classSets.get(tag).size());
			if( verbose ) {
				for (IEntity entity : this.classSets.get(tag)) {
					System.out.print(entity.getEntityName() + " ");
				}				
			}
		}
	}

	
	public String getName() {
		return "Ovr Tag";
	}

	public Map<OvrTag, Set<JClassEntity>> tagSet(Collection<JClassEntity> classSet) {
		HashMap<OvrTag, Set<JClassEntity>> sets = new HashMap<OvrTag, Set<JClassEntity>>();
		for (OvrTag tag : OvrTag.values()) {
			sets.put(tag, new HashSet<JClassEntity>());
		}
		for (JClassEntity entity : classSet) {
			sets.get(tag(entity)).add(entity);
		}
		return sets;
	}

	
}
