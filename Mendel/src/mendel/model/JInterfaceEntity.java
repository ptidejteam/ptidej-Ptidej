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
package mendel.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;


public class JInterfaceEntity extends JAbstractEntity {
	
	private List<IEntity> subEntities;

	public JInterfaceEntity(String entityName) {
		super(entityName);
		this.subEntities = new Vector<IEntity>();
	}
	
	// TODO: what about java.lang.Object? Default super if 
	
	/*
	 * Basic accessors
	 */
	
//	public ClassEntity flatten() {
//		boolean previousFlag = recursiveLookup(true);
//		ClassEntity flattenEntity = new ClassEntity(this.entityName, null);
//		flattenEntity.localMethods = new HashSet<String>(getAllMethods());
//		recursiveLookup(previousFlag);
//		return flattenEntity;
//	}
//	
////	public EntityInterface flatten() {
////		return flatten(inheritanceDepth());
////	}
//	
//	public ClassEntity flatten(int depth) {
//		ClassEntity flattenEntity = new ClassEntity(this.entityName, null);
//		flattenEntity.localMethods = ( depth==0 ) ?
//			new HashSet<String>(getLocalMethods()) :
//			SetOps.union(getLocalMethods(), 
//						 this.superEntity.flatten(depth-1).getLocalMethods());
//		return flattenEntity;
//	}
	
	/*
	 * Accessor to groups and categories
	 */
	
	public Set<String> getSuperMethods() {
		if( isRootInterface() ) {
			return new HashSet<String>();
		} else {
			if (recursiveLookup())
				return getAllMethodsFromSuperInterfaces();
			else
				return getLocalMethodsFromSuperInterfaces();
		}
	}

	public Set<String> getAllMethods() {
		if( !this.isRootInterface() ) {
				return SetOps.union(getLocalMethods(),
									getSuperMethods());
		} else return getLocalMethods();
	}

	public Set<String> getAbstractMethods() {
		return getLocalMethods();
	}
	
	public Set<String> getDuplicateMethods() {
		return getSharedMethods();
	}

	
	public StringBuffer toStringBuffer() {
		StringBuffer buffer = super.toStringBuffer();
		buffer.insert(0, "JInterface");
	
		buffer.append( isRootInterface() ?
			"   ROOT INTERFACE\n" :
			"   Super Interfaces: " + getSuperInterfacesNames() + "\n");
		
		// TODO: remove duplication. Need some factorisation of Group/Category accessor
		appendMethodSet(buffer, "New Methods", getNewMethods());
		appendMethodSet(buffer, "Duplicate Methods", getDuplicateMethods());
		appendMethodSet(buffer, "Abstract Methods", getAbstractMethods());
		return buffer;
	}
	
	public String toString(final boolean recursive) {
		StringBuffer buffer = toStringBuffer();
		if( !isRootInterface() ) {
			if (recursive) {
				buffer.append("---------------------------------------\n");
				new SuperFunctor<StringBuffer>(buffer) {
					public void compute(JInterfaceEntity entity, StringBuffer buf) {
						buf.append("---------------\n");
						buf.append(entity.toString(recursive));
					}
				};
			}
			else {
				buffer.append("***\n");
				appendMethodSet(buffer, "Inherited Methods (direct)", getInheritedMethods());
			}
		}
		return buffer.toString();
	}

	public void addSubentity(IEntity entity) {
		this.subEntities.add(entity);
	}

	public List<IEntity> getSubentities() {
		return new Vector<IEntity>(this.subEntities);
	}

	public boolean hasSubentities() {
		return !this.subEntities.isEmpty();
	}
	
}
