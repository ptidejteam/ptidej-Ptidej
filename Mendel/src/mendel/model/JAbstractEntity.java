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

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;


public abstract class JAbstractEntity implements IEntity {
	
	private String entityName;
	
	private Set<JInterfaceEntity> superInterfaces;
	
	// Compute interface based on the sole direct super type or the whole supertype tree
	// true: look up recursively in tree
	// false: look up only in the direct super type
	private boolean recursiveLookup;
	// Use recursiveLookup for interface exploration?
	// CONS: recursiveLookp is for single inheritance purpose, semantics will be less clear
	// with multiple interface semantics (breadth vs depth)
	// Well, in this case, its place should be in JClassEntity only - but can be useful here some day 
	
	private Set<String> localMethods; // declared methods
	
	public JAbstractEntity(String entityName) {
		this.entityName = entityName;
		this.recursiveLookup = true;
		this.superInterfaces = new HashSet<JInterfaceEntity>();
		this.localMethods = new HashSet<String>();
	}
	
	public void addSuperInterface(JInterfaceEntity entity) {
		this.superInterfaces.add(entity);
	}
	
	public String getEntityName() {
		return this.entityName;
	}

	public String getBaseName() {
		String qName = getEntityName();
		return  qName.substring(qName.lastIndexOf('.') + 1, qName.length());		
	}
	
	public Set<JInterfaceEntity> getSuperInterfaces() {
		return new HashSet<JInterfaceEntity>(this.superInterfaces);
	}
	
	public Set<JInterfaceEntity> getAllSuperInterfaces() {
		if( isRootInterface() )
			return new HashSet<JInterfaceEntity>();
		else {
			Set<JInterfaceEntity> superset = getSuperInterfaces();
			for (JInterfaceEntity superInt : getSuperInterfaces()) {
				superset.addAll(superInt.getAllSuperInterfaces());
			}
			return superset;
		}
	}
	
	public String getSuperInterfacesNames() {
		StringBuffer buf = new StringBuffer();
		for (JInterfaceEntity superInt : getSuperInterfaces())
			buf.append(superInt.getEntityName()).append(" ");
		return buf.toString();
	}
	
	public String getAllSuperInterfacesNames() {
		StringBuffer buf = new StringBuffer();
		for (JInterfaceEntity superInt : getAllSuperInterfaces())
			buf.append(superInt.getEntityName()).append(" ");
		return buf.toString();
	}
	
	public abstract class SuperFunctor<T> {
		// TODO: Breadth-first, depth-first recursive functor
		public SuperFunctor() {
			for (JInterfaceEntity superInt : getSuperInterfaces())
				compute(superInt);
		}
		public SuperFunctor(T acc) {
			for (JInterfaceEntity superInt : getSuperInterfaces())
				compute(superInt, acc);
		}
		public void compute(JInterfaceEntity superInt) { }
		public void compute(JInterfaceEntity superInt, T acc) { }
	}
	
	public List<JInterfaceEntity> findSuperInterfacesDeclaringMethod(final String sig) {
		List<JInterfaceEntity> found = new Vector<JInterfaceEntity>();
		for (JInterfaceEntity superInt : getSuperInterfaces()) {
			if( superInt.hasMethod(sig) )
				found.add(superInt);
		}
		for (JInterfaceEntity superInt : getSuperInterfaces()) {
			found.addAll(superInt.findSuperInterfacesDeclaringMethod(sig));
		}
//		new SuperFunctor<List<JInterfaceEntity>>(found) {
//			public void compute(JInterfaceEntity superInt, List<JInterfaceEntity> found) {
//				if( superInt.hasMethod(sig) )
//					found.add(superInt);
//			}
//		};
//		new SuperFunctor<List<JInterfaceEntity>>(found) {
//			public void compute(JInterfaceEntity superInt, List<JInterfaceEntity> found) {
//				found.addAll(superInt.findSuperInterfacesDeclaringMethod(sig));
//			}
//		};
		return found;
	}
	
//	public ClassEntity findEntityDeclaringMethod(String sig) {
//		if( hasMethod(sig) )
//			return this;
//		else {
//			if( isRootType() )
//				return null;
//			else
//				return this.superEntity.findEntityDeclaringMethod(sig);
//		}
//	}
//	
//	public ClassEntity findSuperEntityDeclaringMethod(String sig) {
//		if( isRootType() )
//			return null;
//		else
//			return this.superEntity.findEntityDeclaringMethod(sig);
//	}
	
	public boolean recursiveLookup() {
		return this.recursiveLookup;
	}
	
	public boolean recursiveLookup(final boolean recursive) {
		boolean previousFlag = this.recursiveLookup;
		this.recursiveLookup = recursive;
		// recursive lookup must be recursively changed to be consistent!
		if( !isRootInterface() )
			new SuperFunctor<Object>() {
				public void compute(JInterfaceEntity entity) {
					entity.recursiveLookup(recursive);
				}
			};
		return previousFlag;
	}
	
	public int interfaceDepth() {
		if( isRootInterface() )
			return 0;
		else {
			List<Integer> depths = new Vector<Integer>();
			new SuperFunctor<List<Integer>>(depths) {
				public void compute(JInterfaceEntity entity, List<Integer> list) {
					list.add( entity.interfaceDepth() );
				}
			};
			return 1 + Collections.max(depths);
		}
	}
	
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
	 * Predicates
	 */
	
	public boolean isRootInterface() {
		return this.superInterfaces.isEmpty();
	}

	public boolean hasMethod(String sig) {
		return getLocalMethods().contains(sig);
	}
	
	public boolean hasAbstractMethod(String sig) {
		return getAbstractMethods().contains(sig);
	}
	
	
	/*
	 * Accessors to categorize methods
	 */
	
	public void addLocalMethod(String sig) {
		this.localMethods.add(sig);
	}
	
	public void addSetToLocalMethods(Set<String> set) {
		this.localMethods.addAll(set);
	}
	
	
	/*
	 * Accessor to groups and categories
	 */
	
	public Set<String> getLocalMethods() {
		return new HashSet<String>(this.localMethods);
	}

	public Set<String> getNewMethods() {
		return SetOps.diff(getLocalMethods(), getSuperMethods());
	}
	
	public Set<String> getInheritedMethods() {
		return SetOps.diff(getSuperMethods(), getLocalMethods());
	}
	
	public Set<String> getSharedMethods() {
		return SetOps.inter(getLocalMethods(), getSuperMethods());
	}

//	public Set<String> getSuperMethods() {
//		if( isRootInterface() ) {
//			return new HashSet<String>();
//		} else {
//			if (this.recursiveLookup)
//				return getAllMethodsFromSuperInterfaces();
//			else
//				return getLocalMethodsFromSuperInterfaces();
//		}
//	}
	
	public Set<String> getLocalMethodsFromSuperInterfaces() {
		Set<String> superset = new HashSet<String>();
		for (JInterfaceEntity superEntity : getSuperInterfaces())
			superset.addAll(superEntity.getLocalMethods());
		return superset;
	}

	public Set<String> getAllMethodsFromSuperInterfaces() {
		Set<String> superset = new HashSet<String>();
		for (JInterfaceEntity superEntity : getSuperInterfaces())
			superset.addAll(superEntity.getAllMethods());
		return superset;
	}
	
	public StringBuffer toStringBuffer() {
		StringBuffer buffer = new StringBuffer(getEntityName());
		buffer.insert(0, "Entity ").append("\n");
		return buffer;
	}
	
	public String toString() {
//		return toStringBuffer().toString();
		return getEntityName();
	}
	
	void appendMethodSet(StringBuffer buffer, String setName, Set<String> methodSet) {
		buffer.append(setName).append("\n");
		for (String methodName : methodSet) {
			buffer.append("   ").append(methodName).append("\n");
		}
	}
	
	
//	public String toString(final boolean recursive) {
//		StringBuffer buffer = new StringBuffer(toString());
//		if( !isRootInterface() ) {
//			if (recursive) {
//				buffer.append("---------------------------------------\n");
//				new SuperFunctor<StringBuffer>(buffer) {
//					public void compute(JInterfaceEntity entity, StringBuffer buf) {
//						buf.append("---------------\n");
//						buf.append(entity.toString(recursive));
//					}
//				};
//			}
//			else {
//				buffer.append("***\n");
//				buffer.append("Inherited Methods (direct)\n");
//				for (String methodName : getInheritedMethods())
//					buffer.append("   " + methodName + "\n");
//			}
//		}
//		return buffer.toString();
//		
//	}
	
}
