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
package modec.metamodel;

import java.util.Collection;
import java.util.Iterator;

public class Class extends Classifier {

	/**
	 * @uml.property  name="name"
	 */
	private String name = "";

	/**
	 * Getter of the property <tt>name</tt>
	 * @return  Returns the name.
	 * @uml.property  name="name"
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Setter of the property <tt>name</tt>
	 * @param name  The name to set.
	 * @uml.property  name="name"
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @uml.property  name="instances"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="ofClass:metamodel.scenarioDiagram.Instance"
	 */
	private Collection instances;

	/**
	 * Getter of the property <tt>instances</tt>
	 * @return  Returns the instances.
	 * @uml.property  name="instances"
	 */
	public Collection getInstances() {
		return this.instances;
	}

	/**
	 * Returns an iterator over the elements in this collection. 
	 * @return  an <tt>Iterator</tt> over the elements in this collection
	 * @see java.util.Collection#iterator()
	 * @uml.property  name="instances"
	 */
	public Iterator instancesIterator() {
		return this.instances.iterator();
	}

	/**
	 * Returns <tt>true</tt> if this collection contains no elements.
	 * @return  <tt>true</tt> if this collection contains no elements
	 * @see java.util.Collection#isEmpty()
	 * @uml.property  name="instances"
	 */
	public boolean isInstancesEmpty() {
		return this.instances.isEmpty();
	}

	/**
	 * Returns <tt>true</tt> if this collection contains the specified element. 
	 * @param element  whose presence in this collection is to be tested.
	 * @see java.util.Collection#contains(Object)
	 * @uml.property  name="instances"
	 */
	public boolean containsInstances(Instance instance) {
		return this.instances.contains(instance);
	}

	/**
	 * Returns <tt>true</tt> if this collection contains all of the elements in the specified collection.
	 * @param elements  collection to be checked for containment in this collection.
	 * @see java.util.Collection#containsAll(Collection)
	 * @uml.property  name="instances"
	 */
	public boolean containsAllInstances(Collection instances) {
		return this.instances.containsAll(instances);
	}

	/**
	 * Returns the number of elements in this collection.
	 * @return  the number of elements in this collection
	 * @see java.util.Collection#size()
	 * @uml.property  name="instances"
	 */
	public int instancesSize() {
		return this.instances.size();
	}

	/**
	 * Returns all elements of this collection in an array.
	 * @return  an array containing all of the elements in this collection
	 * @see java.util.Collection#toArray()
	 * @uml.property  name="instances"
	 */
	public Instance[] instancesToArray() {
		return (Instance[]) this.instances.toArray(new Instance[this.instances.size()]);
	}

	/**
	 * Returns an array containing all of the elements in this collection;  the runtime type of the returned array is that of the specified array.
	 * @param a  the array into which the elements of this collection are to be stored.
	 * @return  an array containing all of the elements in this collection
	 * @see java.util.Collection#toArray(Object[])
	 * @uml.property  name="instances"
	 */
	public Instance[] instancesToArray(Instance[] instances) {
		return (Instance[]) this.instances.toArray(instances);
	}

	/**
	 * Ensures that this collection contains the specified element (optional operation). 
	 * @param element  whose presence in this collection is to be ensured.
	 * @see java.util.Collection#add(Object)
	 * @uml.property  name="instances"
	 */
	public boolean addInstances(Instance instance) {
		return this.instances.add(instance);
	}

	/**
	 * Setter of the property <tt>instances</tt>
	 * @param instances  the instances to set.
	 * @uml.property  name="instances"
	 */
	public void setInstances(Collection instances) {
		this.instances = instances;
	}

	/**
	 * Removes a single instance of the specified element from this collection, if it is present (optional operation).
	 * @param element  to be removed from this collection, if present.
	 * @see java.util.Collection#add(Object)
	 * @uml.property  name="instances"
	 */
	public boolean removeInstances(Instance instance) {
		return this.instances.remove(instance);
	}

	/**
	 * Removes all of the elements from this collection (optional operation).
	 * @see java.util.Collection#clear()
	 * @uml.property  name="instances"
	 */
	public void clearInstances() {
		this.instances.clear();
	}

	//======================================
	public Class(String name) {
		this.name = name;
	}

	public String toString() {
		return this.name;
	}

	public boolean equals(Object otherClass) {
		if (!(otherClass instanceof Class))
			return false;
		if (otherClass == this)
			return true;

		Class c = (Class) otherClass;

		if (this.name.trim().equalsIgnoreCase((c.getName().trim())))
			return true;
		return false;
	}

}
