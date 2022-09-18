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


public class JClassEntity extends JAbstractEntity {

	 /* Group: 4 groups, which are supersets of other (and some overlap)
	  * Local (prev Def Set), Super, Overridden (intersection of both), Total (union of all)
	  */
	public enum Group {
		LOCAL { public int count(JClassEntity e) { return e.getLocalMethods().size(); } },
		SUPER { public int count(JClassEntity e) { return e.getSuperMethods().size(); } },
		OVERD { public int count(JClassEntity e) { return e.getOverridenMethods().size(); } },
		TOTAL { public int count(JClassEntity e) { return e.getAllMethods().size(); } };
	
		public abstract int count(JClassEntity e);
	}

	/* Category: 6 categories, exclusive except for abstract & replaced (rare case?)
	 * (last 5 = Local Set, last 3 = Overridden)
	 * (purely) inherited, new (concrete), abstract (some overlap with replaced)
	 * specialized (super call to overriden method), replaced (no super call), defining (an abstract)
	 */
	public enum Category {
		INH { public int count(JClassEntity e) { return e.getInheritedMethods().size(); } },
		NEW { public int count(JClassEntity e) { return e.getNewMethods().size(); } },
//		ABS { public int count(JClassEntity e) { return e.getAbstractMethods().size(); } }, // NOTE: abtract-modifier
//		DEF { public int count(JClassEntity e) { return e.getDefiningMethods().size(); } };
		SPC { public int count(JClassEntity e) { return e.getSpecializedMethods().size(); } },
		RPL { public int count(JClassEntity e) { return e.getReplacedMethods().size(); } };
		
		public abstract int count(JClassEntity e);
	}
	
	private JClassEntity superClass; // null == root type
	
	// Redundant data structure to ease navigation up and down a hierarchy
	private List<JClassEntity> children;

	
	// TODO: set memoization
	// Could be: a Decorator, with access by Enum (cf Symbol Ruby)
	// then EntityInterface would only be used as a temporary object while building interface
	// but I want to keep the implementation wih Set operations for semantics
	private Set<String> specializedMethods; // override and call super.overriden
	private Set<String> definingMethods; // define abstract methods from hierarchy
	private Set<String> abstractMethods; // declare abstract methods (new or replace)
	
	// There is two sets which do not count in computation because there cant be
	// be inherited: private methods and static methods
	// They are only included for information
	private Set<String> privateMethods;
	private Set<String> staticMethods;

	private List<IEntity> subEntities;
	
	public JClassEntity(String entityName, JClassEntity superClass) {
		super(entityName);
		this.superClass = superClass;
		this.children = new Vector<JClassEntity>();
		
		this.specializedMethods = new HashSet<String>();
		this.definingMethods = new HashSet<String>();
		this.abstractMethods = new HashSet<String>();

		this.privateMethods = new HashSet<String>();
		this.staticMethods = new HashSet<String>();
	}

	
	/*
	 * Basic accessors
	 */
	
	public JClassEntity getSuperClass() {
		return this.superClass;
	}
	
	public String getSuperClassName() {
		return getSuperClass().getEntityName();
	}
	
	public JClassEntity findClassDeclaringMethod(String sig) {
		if( hasMethod(sig) )
			return this;
		else {
			if( isRootClass() )
				return null;
			else
				return this.superClass.findClassDeclaringMethod(sig);
		}
	}
	
	public JClassEntity findSuperClassDeclaringMethod(String sig) {
		if( isRootClass() )
			return null;
		else
			return this.superClass.findClassDeclaringMethod(sig);
	}
	
	public boolean recursiveLookup(boolean recursive) {
		boolean previousFlag = super.recursiveLookup(recursive);
		// recursive lookup must be recursively changed to be consistent!
		if( !isRootClass() )
			getSuperClass().recursiveLookup(recursive);
		return previousFlag;
	}
	
	public int inheritanceDepth() {
		return isRootClass() ? 0 : this.superClass.inheritanceDepth() + 1;
	}
	
	public JClassEntity flatten() {
		boolean previousFlag = recursiveLookup(true);
		JClassEntity flattenEntity = new JClassEntity(getEntityName(), null);
		flattenEntity.addSetToLocalMethods( new HashSet<String>(getAllMethods()) );
		recursiveLookup(previousFlag);
		return flattenEntity;
	}
	
//	public EntityInterface flatten() {
//		return flatten(inheritanceDepth());
//	}
	
	public JClassEntity flatten(int depth) {
		JClassEntity flattenEntity = new JClassEntity(getEntityName(), null);
		flattenEntity.addSetToLocalMethods(
			(depth == 0)
			? new HashSet<String>(getLocalMethods())
			: SetOps.union(getLocalMethods(),
				this.superClass.flatten(depth - 1).getLocalMethods()));
		return flattenEntity;
	}
	
	/*
	 * Subclasses data structure
	 */
	
	public void addChild(JClassEntity child) {
		this.children.add(child);
	}
	
	public List<JClassEntity> getChildren() {
		return new Vector<JClassEntity>(this.children);
	}
	
	public int familySize() {
		return this.children.size();
	}
	
	public List<JClassEntity> getDescendants() {
		Vector<JClassEntity> subclasses = new Vector<JClassEntity>(this.children);
		for (JClassEntity classEntity : this.children) {
			subclasses.addAll(classEntity.getDescendants());
		}
		return subclasses;
	}

	
	
	/*
	 * Predicates
	 */
	
	public boolean isRootClass() {
		return this.superClass==null;
	}
	
	public boolean isLeaf() {
		return this.children.isEmpty();
	}
	
	public boolean canAnswerMethod(String sig) {
		return hasMethod(sig) || ( !isRootClass() && this.superClass.hasMethod(sig) );
	}
	
	public boolean implementsInterfaces() {
		return !getSuperInterfaces().isEmpty();
	}
	
	
	/*
	 * Simple metrics
	 */
	
	public int count(Group g) {
		return g.count(this);
	}
	public int count(Category c) {
		return c.count(this);
	}
	public float ovrFrac(Group g) {
		return count(Group.OVERD) / (float) count(g);
	}
	public float frac(Category c) {
		return count(c) / (float) count(Group.TOTAL);
	}
	public float localFrac(Category c) {
		return count(c) / (float) count(Group.LOCAL);
	}
	public float superFrac(Category c) {
		return count(c) / (float) count(Group.SUPER);
	}
	
	
	/*
	 * Accessors to categorize methods
	 * TODO: check set exclusion|overlap constraints before adding a method
	 */
	
	public void addSpecialized(String sig) {
		this.specializedMethods.add(sig);
	}
	
	public void addDefining(String sig) {
		this.definingMethods.add(sig);
	}
	
	public void addAbstract(String sig) {
		this.abstractMethods.add(sig);
	}
	
	public void addPrivate(String sig) {
		this.privateMethods.add(sig);
	}
	
	public void addStatic(String sig) {
		this.staticMethods.add(sig);
	}
	
	
	/*
	 * Accessor to groups and categories
	 */
	
	public Set<String> getSuperMethods() {
		if( isRootClass() ) {
			return new HashSet<String>();
		} else {
			if (recursiveLookup())
				return getSuperClass().getAllMethods();
			else
				return getSuperClass().getLocalMethods();
		}
	}
	
	public Set<String> getAllMethods() {
		if( !this.isRootClass() ) {
				return SetOps.union(getLocalMethods(),
									getSuperMethods());
		} else return getLocalMethods();
	}
	
	public Set<String> getOverridenMethods() {
		return getSharedMethods();
	}

	public Set<String> getSpecializedMethods() {
		return SetOps.union(this.specializedMethods, this.definingMethods);
//		return new HashSet<String>(this.specializedMethods); // NOTE: abtract-modifier
	}

//	public Set<String> getDefiningMethods() { // NOTE: abtract-modifier
//		return new HashSet<String>(this.definingMethods);
//	}

	public Set<String> getReplacedMethods() {
		// with this definition, overriding with an abstract method count as replacing it
		return SetOps.diff(getOverridenMethods(), getSpecializedMethods());
//						   SetOps.union(getSpecializedMethods(), // NOTE: abtract-modifier
//							   			getDefiningMethods()));
	}

	public Set<String> getAbstractMethods() {
		return new HashSet<String>(this.abstractMethods);
	}
	
	public Set<String> getPrivateMethods() {
		return new HashSet<String>(this.privateMethods);
	}
	
	public Set<String> getStaticMethods() {
		return new HashSet<String>(this.staticMethods);
	}
	
	/**
	 * All methods defined/declared in class, including private and static methods
	 * @return
	 */
	public Set<String> getAllLocalMethods() {
		return SetOps.union(getLocalMethods(), SetOps.union(getPrivateMethods(), getStaticMethods()));
	}
	
	
	public Set<String> getImplementedMethods() {
		if( recursiveLookup() )
			return SetOps.inter(getLocalMethods(), getAllMethodsFromSuperInterfaces());
		else
			return SetOps.inter(getLocalMethods(), getLocalMethodsFromSuperInterfaces());
	}
	
	public Set<String> getUnimplementedMethods() {
		if( recursiveLookup() )
			return SetOps.diff(getAllMethodsFromSuperInterfaces(), getLocalMethods());
		else
			return SetOps.diff(getLocalMethodsFromSuperInterfaces(), getLocalMethods());
	}
	
	
	public StringBuffer toStringBuffer() {
		StringBuffer buffer = super.toStringBuffer();
		buffer.insert(0, "JClass");
		
		buffer.append( isRootClass() ?
			"   ROOT TYPE\n" :
			"   Super Class: " + getSuperClassName() + "\n");
		if( implementsInterfaces() ) {
			buffer.append("   Direct Super Interfaces: " + getSuperInterfacesNames() + "\n");
		}

		// TODO: remove duplication. Need some factorisation of Group/Category accessor
		appendMethodSet(buffer, "New Methods", getNewMethods());
		appendMethodSet(buffer, "Specialized Methods", getSpecializedMethods());
		appendMethodSet(buffer, "Replaced Methods", getReplacedMethods());
//		appendMethodSet(buffer, "Defining Methods", getDefiningMethods()); // NOTE: abtract-modifier
		appendMethodSet(buffer, "Abstract Methods", getAbstractMethods());
		appendMethodSet(buffer, "Private Methods", getPrivateMethods());
		appendMethodSet(buffer, "Static Methods", getStaticMethods());
		if( implementsInterfaces() ) {
			buffer.append("~~~~~~~\n");
			buffer.append("   Super Interfaces: " + getAllSuperInterfacesNames() + "\n");
			appendMethodSet(buffer, "Implemented Methods", getImplementedMethods());
			appendMethodSet(buffer, "Unimplemented Methods", getUnimplementedMethods());
		}
		return buffer;
	}
	
	public String toString(boolean recursive) {
		StringBuffer buffer = toStringBuffer();
		if( !isRootClass() ) {
			if (recursive) {
				buffer.append("---------------------------------------\n");
				buffer.append(getSuperClass().toString(recursive));
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
