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
package metamodel.scenarioDiagram;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Instance extends metamodel.scenarioDiagram.Classifier {

	/**
	 * @uml.property  name="objectID"
	 */
	private int objectID;

	/**
	 * Getter of the property <tt>objectID</tt>
	 * @return  Returns the objectID.
	 * @uml.property  name="objectID"
	 */
	public int getObjectID() {
		return this.objectID;
	}

	/**
	 * Setter of the property <tt>objectID</tt>
	 * @param objectID  The objectID to set.
	 * @uml.property  name="objectID"
	 */
	public void setObjectID(int objectID) {
		this.objectID = objectID;
	}

	/**
	 * @uml.property  name="ofClass"
	 * @uml.associationEnd  multiplicity="(1 -1)" inverse="instances:metamodel.scenarioDiagram.Class"
	 */
	private Collection ofClass;

	/**
	 * Getter of the property <tt>ofClass</tt>
	 * @return  Returns the ofClass.
	 * @uml.property  name="ofClass"
	 */
	public Collection getOfClass() {
		return this.ofClass;
	}

	/**
	 * Returns an iterator over the elements in this collection. 
	 * @return  an <tt>Iterator</tt> over the elements in this collection
	 * @see java.util.Collection#iterator()
	 * @uml.property  name="ofClass"
	 */
	public Iterator ofClassIterator() {
		return this.ofClass.iterator();
	}

	/**
	 * Returns <tt>true</tt> if this collection contains no elements.
	 * @return  <tt>true</tt> if this collection contains no elements
	 * @see java.util.Collection#isEmpty()
	 * @uml.property  name="ofClass"
	 */
	public boolean isOfClassEmpty() {
		return this.ofClass.isEmpty();
	}

	/**
	 * Returns <tt>true</tt> if this collection contains the specified element. 
	 * @param element  whose presence in this collection is to be tested.
	 * @see java.util.Collection#contains(Object)
	 * @uml.property  name="ofClass"
	 */
	public boolean containsOfClass(Class class1) {
		return this.ofClass.contains(class1);
	}

	/**
	 * Returns <tt>true</tt> if this collection contains all of the elements in the specified collection.
	 * @param elements  collection to be checked for containment in this collection.
	 * @see java.util.Collection#containsAll(Collection)
	 * @uml.property  name="ofClass"
	 */
	public boolean containsAllOfClass(Collection ofClass) {
		return this.ofClass.containsAll(ofClass);
	}

	/**
	 * Returns the number of elements in this collection.
	 * @return  the number of elements in this collection
	 * @see java.util.Collection#size()
	 * @uml.property  name="ofClass"
	 */
	public int ofClassSize() {
		return this.ofClass.size();
	}

	/**
	 * Returns all elements of this collection in an array.
	 * @return  an array containing all of the elements in this collection
	 * @see java.util.Collection#toArray()
	 * @uml.property  name="ofClass"
	 */
	public Class[] ofClassToArray() {
		return (Class[]) this.ofClass.toArray(new Class[this.ofClass.size()]);
	}

	/**
	 * Returns an array containing all of the elements in this collection;  the runtime type of the returned array is that of the specified array.
	 * @param a  the array into which the elements of this collection are to be stored.
	 * @return  an array containing all of the elements in this collection
	 * @see java.util.Collection#toArray(Object[])
	 * @uml.property  name="ofClass"
	 */
	public Class[] ofClassToArray(Class[] ofClass) {
		return (Class[]) this.ofClass.toArray(ofClass);
	}

	/**
	 * Ensures that this collection contains the specified element (optional operation). 
	 * @param element  whose presence in this collection is to be ensured.
	 * @see java.util.Collection#add(Object)
	 * @uml.property  name="ofClass"
	 */
	public boolean addOfClass(Class class1) {
		return this.ofClass.add(class1);
	}

	/**
	 * Setter of the property <tt>ofClass</tt>
	 * @param ofClass  the ofClass to set.
	 * @uml.property  name="ofClass"
	 */
	public void setOfClass(Collection ofClass) {
		this.ofClass = ofClass;
	}

	/**
	 * Removes a single instance of the specified element from this collection, if it is present (optional operation).
	 * @param element  to be removed from this collection, if present.
	 * @see java.util.Collection#add(Object)
	 * @uml.property  name="ofClass"
	 */
	public boolean removeOfClass(Class class1) {
		return this.ofClass.remove(class1);
	}

	/**
	 * Removes all of the elements from this collection (optional operation).
	 * @see java.util.Collection#clear()
	 * @uml.property  name="ofClass"
	 */
	public void clearOfClass() {
		this.ofClass.clear();
	}

	//======================================

	public Instance(int objectID) {
		this.objectID = objectID;
		this.ofClass = new ArrayList();
	}

	public boolean equals(Object otherInstance) {
		if (!(otherInstance instanceof Instance))
			return false;
		if (otherInstance == this)
			return true;

		Instance inst = (Instance) otherInstance;

		if (this.objectID == inst.getObjectID())
			return true;
		return false;
	}
	
	public int hashCode()
	{
		return (Integer.valueOf(this.objectID)).hashCode();
	}

	public String toString() {
		String info = "";
		Iterator it = this.ofClassIterator();
		while (it.hasNext())
			info += ((Class) it.next()).getName() + " ";

		return info + this.objectID;

	}

}
