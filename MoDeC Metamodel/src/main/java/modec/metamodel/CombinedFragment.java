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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
//import java.util.Collection;
//import java.util.Iterator;
//import java.util.List;

public class CombinedFragment extends Component {

	/** 
	 * @uml.property name="operands"
	 * @uml.associationEnd multiplicity="(1 -1)" ordering="true" aggregation="composite" inverse="fragment:metamodel.scenarioDiagram.Operand"
	 */
	private List operands;

	/** 
	 * Getter of the property <tt>operands</tt>
	 * @return  Returns the operands.
	 * @uml.property  name="operands"
	 */
	public List getOperands() {
		return this.operands;
	}

	/** 
	 * Getter of the property <tt>operands</tt>
	 * @return  Returns the operands.
	 * @uml.property  name="operands"
	 */
	public Operand getOperands(int i) {
		return (Operand) this.operands.get(i);
	}

	/**
	 * Returns an iterator over the elements in this list in proper sequence.
	 * @return  an iterator over the elements in this list in proper sequence.
	 * @see java.util.List#iterator()
	 * @uml.property  name="operands"
	 */
	public Iterator operandsIterator() {
		return this.operands.iterator();
	}

	/**
	 * Returns <tt>true</tt> if this list contains no elements.
	 * @return  <tt>true</tt> if this list contains no elements.
	 * @see java.util.List#isEmpty()
	 * @uml.property  name="operands"
	 */
	public boolean isOperandsEmpty() {
		return this.operands.isEmpty();
	}

	/**
	 * Returns <tt>true</tt> if this list contains the specified element.
	 * @param element  element whose presence in this list is to be tested.
	 * @return  <tt>true</tt> if this list contains the specified element.
	 * @see java.util.List#contains(Object)
	 * @uml.property  name="operands"
	 */
	public boolean containsOperands(Operand operand) {
		return this.operands.contains(operand);
	}

	/**
	 * Returns <tt>true</tt> if this list contains all of the elements of the specified collection.
	 * @param elements  collection to be checked for containment in this list.
	 * @return  <tt>true</tt> if this list contains all of the elements of the specified collection.
	 * @see java.util.List#containsAll(Collection)
	 * @uml.property  name="operands"
	 */
	public boolean containsAllOperands(Collection operands) {
		return this.operands.containsAll(operands);
	}

	/**
	 * Returns the number of elements in this list.
	 * @return  the number of elements in this list.
	 * @see java.util.List#size()
	 * @uml.property  name="operands"
	 */
	public int operandsSize() {
		return this.operands.size();
	}

	/**
	 * Returns an array containing all of the elements in this list in proper sequence.
	 * @return  an array containing all of the elements in this list in proper sequence.
	 * @see java.util.List#toArray()
	 * @uml.property  name="operands"
	 */
	public Operand[] operandsToArray() {
		return (Operand[]) this.operands.toArray(new Operand[this.operands.size()]);
	}

	/**
	 * Returns an array containing all of the elements in this list in proper sequence; the runtime type of the returned array is that of the specified array.
	 * @param a  the array into which the elements of this list are to be stored.
	 * @return  an array containing all of the elements in this list in proper sequence.
	 * @see java.util.List#toArray(Object[])
	 * @uml.property  name="operands"
	 */
	public Operand[] operandsToArray(Operand[] operands) {
		return (Operand[]) this.operands.toArray(operands);
	}

	/**
	 * Inserts the specified element at the specified position in this list (optional operation)
	 * @param index  index at which the specified element is to be inserted.
	 * @param element  element to be inserted.
	 * @see java.util.List#add(int,Object)
	 * @uml.property  name="operands"
	 */
	public void addOperands(int index, Operand operand) {
		this.operands.add(index, operand);
	}

	/**
	 * Appends the specified element to the end of this list (optional operation).
	 * @param element  element to be appended to this list.
	 * @return  <tt>true</tt> (as per the general contract of the <tt>Collection.add</tt> method).
	 * @see java.util.List#add(Object)
	 * @uml.property  name="operands"
	 */
	public boolean addOperands(Operand operand) {
		return this.operands.add(operand);
	}

	/**
	 * Removes the element at the specified position in this list (optional operation).
	 * @param index  the index of the element to removed.
	 * @return  the element previously at the specified position.
	 * @see java.util.List#remove(int)
	 * @uml.property  name="operands"
	 */
	public Object removeOperands(int index) {
		return this.operands.remove(index);
	}

	/**
	 * Removes the first occurrence in this list of the specified element  (optional operation).
	 * @param element  element to be removed from this list, if present.
	 * @return  <tt>true</tt> if this list contained the specified element.
	 * @see java.util.List#remove(Object)
	 * @uml.property  name="operands"
	 */
	public boolean removeOperands(Operand operand) {
		return this.operands.remove(operand);
	}

	/**
	 * Removes all of the elements from this list (optional operation).
	 * @see java.util.List#clear()
	 * @uml.property  name="operands"
	 */
	public void clearOperands() {
		this.operands.clear();
	}

	/** 
	 * Setter of the property <tt>operands</tt>
	 * @param operands  the operands to set.
	 * @uml.property  name="operands"
	 */
	public void setOperands(List operands) {
		this.operands = operands;
	}

	// ========================================================================

	public CombinedFragment(ScenarioDiagram sd) {
		super(sd);
		this.operands = new LinkedList();
	}

	//	public CombinedFragment (String info)
	//	{
	//		super(info);	
	//		operands = new ArrayList() ;
	//	}

	public CombinedFragment() {
		//super();
		this.operands = new ArrayList();
	}

	//	
	//	/**
	//	 * Constructor
	//	 * @param sd  the scenarioDiagram to which the message belongs.
	//	 * @param type  determine whether a message is an entry or an exit
	//	 */
	//	public CombinedFragment(ScenarioDiagram scenarioDiagram, int header, String originalTraceStatement)
	//	{
	//		super(scenarioDiagram, header);
	//		operands = new ArrayList() ;
	//	}

	/**
	 * Constructor
	 * @param sd  the scenarioDiagram to which the message belongs.
	 * @param type  determine whether a message is an entry or an exit
	 */
	public CombinedFragment(ScenarioDiagram scenarioDiagram, int header) {
		super(header);
		this.operands = new ArrayList();
	}

	/*public String toString()
	{
		String tab = "";		
		for(int i = 0 ; i < level ; i++)
			tab += "\t";
			
		String info = "" ;
		for(int i = operands.size()-1  ; i >= 0 ; i--)
			info += tab + ((Operand) operands.get(i)).toString() + "\n";	
	
		return info;
	}*/

//	public List visitOperands(
//		List listOfMessages,
//		List components,
//		int index) {
//		if (index == components.size())
//			return listOfMessages;
//		if (components.get(index) instanceof Message) {
//			listOfMessages.add(components.get(index));
//			return visitOperands(listOfMessages, components, ++index);
//		}
//		else //if (components.get(index) instanceof CombinedFragment)
//			return visitOperands(
//				listOfMessages,
//				((Operand) components.get(index)).getOperands(),
//				0);
//	}
//
//	public List getOrderedMessages() {
//		//Collections.sort(operands);
//		List messages = new ArrayList();
//		return this.visitOperands(messages, operands, 0);
//	}

}
