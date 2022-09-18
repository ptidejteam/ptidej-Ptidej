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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Operand {

	/** 
	 * @uml.property name="fragment"
	 * @uml.associationEnd inverse="operands:metamodel.scenarioDiagram.CombinedFragment"
	 */
	private CombinedFragment fragment;

	/** 
	 * Getter of the property <tt>fragment</tt>
	 * @return  Returns the fragment.
	 * @uml.property  name="fragment"
	 */
	public CombinedFragment getFragment() {
		return this.fragment;
	}

	/** 
	 * Setter of the property <tt>fragment</tt>
	 * @param fragment  The fragment to set.
	 * @uml.property  name="fragment"
	 */
	public void setFragment(CombinedFragment fragment) {
		this.fragment = fragment;
	}

	/**
	 * @uml.property  name="guard"
	 * @uml.associationEnd  inverse="ofOperand:metamodel.scenarioDiagram.Condition"
	 */
	private Condition guard;

	/**
	 * Getter of the property <tt>guard</tt>
	 * @return  Returns the guard.
	 * @uml.property  name="guard"
	 */
	public Condition getGuard() {
		return this.guard;
	}

	/**
	 * Setter of the property <tt>guard</tt>
	 * @param guard  The guard to set.
	 * @uml.property  name="guard"
	 */
	public void setGuard(Condition guard) {
		this.guard = guard;
	}

	/**
	 * @uml.property  name="components"
	 * @uml.associationEnd  multiplicity="(1 -1)" ordering="true" aggregation="composite" inverse="operand:metamodel.scenarioDiagram.Component"
	 */
	private List components;

	/**
	 * Getter of the property <tt>components</tt>
	 * @return  Returns the components.
	 * @uml.property  name="components"
	 */
	public List getComponents() {
		return this.components;
	}

	/**
	 * Returns the element at the specified position in this list.
	 * @param index  index of element to return.
	 * @return  the element at the specified position in this list.
	 * @see java.util.List#get(int)
	 * @uml.property  name="components"
	 */
	public Component getComponents(int i) {
		return (Component) this.components.get(i);
	}

	/**
	 * Returns an iterator over the elements in this list in proper sequence.
	 * @return  an iterator over the elements in this list in proper sequence.
	 * @see java.util.List#iterator()
	 * @uml.property  name="components"
	 */
	public Iterator componentsIterator() {
		return this.components.iterator();
	}

	/**
	 * Returns <tt>true</tt> if this list contains no elements.
	 * @return  <tt>true</tt> if this list contains no elements.
	 * @see java.util.List#isEmpty()
	 * @uml.property  name="components"
	 */
	public boolean isComponentsEmpty() {
		return this.components.isEmpty();
	}

	/**
	 * Returns <tt>true</tt> if this list contains the specified element.
	 * @param element  element whose presence in this list is to be tested.
	 * @return  <tt>true</tt> if this list contains the specified element.
	 * @see java.util.List#contains(Object)
	 * @uml.property  name="components"
	 */
	public boolean containsComponents(Component component) {
		return this.components.contains(component);
	}

	/**
	 * Returns <tt>true</tt> if this list contains all of the elements of the specified collection.
	 * @param elements  collection to be checked for containment in this list.
	 * @return  <tt>true</tt> if this list contains all of the elements of the specified collection.
	 * @see java.util.List#containsAll(Collection)
	 * @uml.property  name="components"
	 */
	public boolean containsAllComponents(Collection components) {
		return this.components.containsAll(components);
	}

	/**
	 * Returns the number of elements in this list.
	 * @return  the number of elements in this list.
	 * @see java.util.List#size()
	 * @uml.property  name="components"
	 */
	public int componentsSize() {
		return this.components.size();
	}

	/**
	 * Returns an array containing all of the elements in this list in proper sequence.
	 * @return  an array containing all of the elements in this list in proper sequence.
	 * @see java.util.List#toArray()
	 * @uml.property  name="components"
	 */
	public Component[] componentsToArray() {
		return (Component[]) this.components
			.toArray(new Component[this.components.size()]);
	}

	/**
	 * Returns an array containing all of the elements in this list in proper sequence; the runtime type of the returned array is that of the specified array.
	 * @param a  the array into which the elements of this list are to be stored.
	 * @return  an array containing all of the elements in this list in proper sequence.
	 * @see java.util.List#toArray(Object[])
	 * @uml.property  name="components"
	 */
	public Component[] componentsToArray(Component[] components) {
		return (Component[]) this.components.toArray(components);
	}

	/**
	 * Inserts the specified element at the specified position in this list (optional operation)
	 * @param index  index at which the specified element is to be inserted.
	 * @param element  element to be inserted.
	 * @see java.util.List#add(int,Object)
	 * @uml.property  name="components"
	 */
	public void addComponents(int index, Component component) {
		this.components.add(index, component);
	}

	/**
	 * Appends the specified element to the end of this list (optional operation).
	 * @param element  element to be appended to this list.
	 * @return  <tt>true</tt> (as per the general contract of the <tt>Collection.add</tt> method).
	 * @see java.util.List#add(Object)
	 * @uml.property  name="components"
	 */
	public boolean addComponents(Component component) {
		return this.components.add(component);
	}

	/**
	 * Removes the element at the specified position in this list (optional operation).
	 * @param index  the index of the element to removed.
	 * @return  the element previously at the specified position.
	 * @see java.util.List#remove(int)
	 * @uml.property  name="components"
	 */
	public Object removeComponents(int index) {
		return this.components.remove(index);
	}

	/**
	 * Removes the first occurrence in this list of the specified element  (optional operation).
	 * @param element  element to be removed from this list, if present.
	 * @return  <tt>true</tt> if this list contained the specified element.
	 * @see java.util.List#remove(Object)
	 * @uml.property  name="components"
	 */
	public boolean removeComponents(Component component) {
		return this.components.remove(component);
	}

	/**
	 * Removes all of the elements from this list (optional operation).
	 * @see java.util.List#clear()
	 * @uml.property  name="components"
	 */
	public void clearComponents() {
		this.components.clear();
	}

	/**
	 * Setter of the property <tt>components</tt>
	 * @param components  the components to set.
	 * @uml.property  name="components"
	 */
	public void setComponents(ArrayList components) {
		this.components = components;
	}

	// ========================================================================

	public Operand() {
		this.components = new ArrayList();
	}

	public void addLastComponents(Component component) {
		//((ArrayList)this.components).addLast(component);
		((ArrayList) this.components).add(component);
	}

	public void addFirstComponents(Component component) {
		//((ArrayList)this.components).addFirst(component);
		((ArrayList) this.components).add(0, component);
	}

	public String toString() {
		Collections.sort(this.components);
		String info = "";
		//for(int i = 0  ; i < components.size(); i++)
		for (int i = this.components.size() - 1; i >= 0; i--) {
			//	String tab = "";
			//	for (int j = 0; j < ((Component) this.components.get(i)).level; j++)
			//		tab += "\t";
			info += ((Component) this.components.get(i)).toString();
		}
		return info;
	}

	public List visitOperandMessages() {
		List messages = new ArrayList();
		Collections.sort(this.components);
		//for(int i = 0  ; i < components.size(); i++)
		for (int i = this.components.size() - 1; i >= 0; i--) {
			if (this.components.get(i) instanceof Message)
				messages.add(this.components.get(i));
			else if (this.components.get(i) instanceof Loop)
				messages.addAll(((Loop) this.components.get(i))
					.visitLoopMessages());
		}
		return messages;

	}

}
