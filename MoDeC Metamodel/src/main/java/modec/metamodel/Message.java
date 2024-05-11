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
import java.util.List;

public class Message extends Component {

	/**
	 * @uml.property  name="signature"
	 */
	protected String signature = "";

	/**
	 * Getter of the property <tt>signature</tt>
	 * @return  Returns the signature.
	 * @uml.property  name="signature"
	 */
	public String getSignature() {
		return this.signature;
	}

	/**
	 * Setter of the property <tt>signature</tt>
	 * @param signature  The signature to set.
	 * @uml.property  name="signature"
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}

	/**
	 * @uml.property  name="arguments"
	 * @uml.associationEnd  multiplicity="(0 -1)" ordering="true" aggregation="composite" inverse="messageOfArgument:metamodel.scenarioDiagram.Argument"
	 */
	protected List arguments;

	/**
	 * Getter of the property <tt>arguments</tt>
	 * @return  Returns the arguments.
	 * @uml.property  name="arguments"
	 */
	public List getArguments() {
		return this.arguments;
	}

	/**
	 * Returns the element at the specified position in this list.
	 * @param index  index of element to return.
	 * @return  the element at the specified position in this list.
	 * @see java.util.List#get(int)
	 * @uml.property  name="arguments"
	 */
	public Argument getArguments(int i) {
		return (Argument) this.arguments.get(i);
	}

	/**
	 * Returns an iterator over the elements in this list in proper sequence.
	 * @return  an iterator over the elements in this list in proper sequence.
	 * @see java.util.List#iterator()
	 * @uml.property  name="arguments"
	 */
	public Iterator argumentsIterator() {
		return this.arguments.iterator();
	}

	/**
	 * Returns <tt>true</tt> if this list contains no elements.
	 * @return  <tt>true</tt> if this list contains no elements.
	 * @see java.util.List#isEmpty()
	 * @uml.property  name="arguments"
	 */
	public boolean isArgumentsEmpty() {
		return this.arguments.isEmpty();
	}

	/**
	 * Returns <tt>true</tt> if this list contains the specified element.
	 * @param element  element whose presence in this list is to be tested.
	 * @return  <tt>true</tt> if this list contains the specified element.
	 * @see java.util.List#contains(Object)
	 * @uml.property  name="arguments"
	 */
	public boolean containsArguments(Argument argument) {
		return this.arguments.contains(argument);
	}

	/**
	 * Returns <tt>true</tt> if this list contains all of the elements of the specified collection.
	 * @param elements  collection to be checked for containment in this list.
	 * @return  <tt>true</tt> if this list contains all of the elements of the specified collection.
	 * @see java.util.List#containsAll(Collection)
	 * @uml.property  name="arguments"
	 */
	public boolean containsAllArguments(Collection arguments) {
		return this.arguments.containsAll(arguments);
	}

	/**
	 * Returns the number of elements in this list.
	 * @return  the number of elements in this list.
	 * @see java.util.List#size()
	 * @uml.property  name="arguments"
	 */
	public int argumentsSize() {
		return this.arguments.size();
	}

	/**
	 * Returns an array containing all of the elements in this list in proper sequence.
	 * @return  an array containing all of the elements in this list in proper sequence.
	 * @see java.util.List#toArray()
	 * @uml.property  name="arguments"
	 */
	public Argument[] argumentsToArray() {
		return (Argument[]) this.arguments.toArray(new Argument[this.arguments.size()]);
	}

	/**
	 * Returns an array containing all of the elements in this list in proper sequence; the runtime type of the returned array is that of the specified array.
	 * @param a  the array into which the elements of this list are to be stored.
	 * @return  an array containing all of the elements in this list in proper sequence.
	 * @see java.util.List#toArray(Object[])
	 * @uml.property  name="arguments"
	 */
	public Argument[] argumentsToArray(Argument[] arguments) {
		return (Argument[]) this.arguments.toArray(arguments);
	}

	/**
	 * Inserts the specified element at the specified position in this list (optional operation)
	 * @param index  index at which the specified element is to be inserted.
	 * @param element  element to be inserted.
	 * @see java.util.List#add(int,Object)
	 * @uml.property  name="arguments"
	 */
	public void addArguments(int index, Argument argument) {
		this.arguments.add(index, argument);
	}

	/**
	 * Appends the specified element to the end of this list (optional operation).
	 * @param element  element to be appended to this list.
	 * @return  <tt>true</tt> (as per the general contract of the <tt>Collection.add</tt> method).
	 * @see java.util.List#add(Object)
	 * @uml.property  name="arguments"
	 */
	public boolean addArguments(Argument argument) {
		return this.arguments.add(argument);
	}

	/**
	 * Removes the element at the specified position in this list (optional operation).
	 * @param index  the index of the element to removed.
	 * @return  the element previously at the specified position.
	 * @see java.util.List#remove(int)
	 * @uml.property  name="arguments"
	 */
	public Object removeArguments(int index) {
		return this.arguments.remove(index);
	}

	/**
	 * Removes the first occurrence in this list of the specified element  (optional operation).
	 * @param element  element to be removed from this list, if present.
	 * @return  <tt>true</tt> if this list contained the specified element.
	 * @see java.util.List#remove(Object)
	 * @uml.property  name="arguments"
	 */
	public boolean removeArguments(Argument argument) {
		return this.arguments.remove(argument);
	}

	/**
	 * Removes all of the elements from this list (optional operation).
	 * @see java.util.List#clear()
	 * @uml.property  name="arguments"
	 */
	public void clearArguments() {
		this.arguments.clear();
	}

	/**
	 * Setter of the property <tt>arguments</tt>
	 * @param arguments  the arguments to set.
	 * @uml.property  name="arguments"
	 */
	public void setArguments(List arguments) {
		this.arguments = arguments;
	}

	/**
	 * @uml.property  name="destinationClassifier"
	 * @uml.associationEnd  inverse="destClassifierbelongsToMessage:metamodel.scenarioDiagram.Classifier"
	 */
	protected Classifier destinationClassifier;

	/**
	 * Getter of the property <tt>destinationClassifier</tt>
	 * @return  Returns the destinationClassifier.
	 * @uml.property  name="destinationClassifier"
	 */
	public Classifier getDestinationClassifier() {
		return this.destinationClassifier;
	}

	/**
	 * Setter of the property <tt>destinationClassifier</tt>
	 * @param destinationClassifier  The destinationClassifier to set.
	 * @uml.property  name="destinationClassifier"
	 */
	public void setDestinationClassifier(Classifier destinationClassifier) {
		this.destinationClassifier = destinationClassifier;
	}

	/**
	 * @uml.property  name="sourceClassifier"
	 * @uml.associationEnd  inverse="sourceClassifierbelongsToMessage:metamodel.scenarioDiagram.Classifier"
	 */
	protected Classifier sourceClassifier;

	/**
	 * Getter of the property <tt>sourceClassifier</tt>
	 * @return  Returns the sourceClassifier.
	 * @uml.property  name="sourceClassifier"
	 */
	public Classifier getSourceClassifier() {
		return this.sourceClassifier;
	}

	/**
	 * Setter of the property <tt>sourceClassifier</tt>
	 * @param sourceClassifier  The sourceClassifier to set.
	 * @uml.property  name="sourceClassifier"
	 */
	public void setSourceClassifier(Classifier sourceClassifier) {
		this.sourceClassifier = sourceClassifier;
	}

	// =====================================================

	/**
	 * Constructor
	 * @param sd  the scenarioDiagram to which the message belongs.
	 * @param type  determine whether a message is an entry or an exit
	 */
	public Message(
		String signature,
		List arguments,
		Classifier sourceClassifier,
		Classifier destinationClassifier) {
		//super(header);
		this.signature = signature;
		this.arguments = arguments;
		this.destinationClassifier = destinationClassifier;
		this.sourceClassifier = sourceClassifier;
	}

	//	/**
	//	* Constructor
	//	* @param sd  the scenarioDiagram to which the message belongs.
	//	* @param type  determine whether a message is an entry or an exit
	//	*/
	//	public Message(
	//		ScenarioDiagram scenarioDiagram,
	//		int header /*, String originalTraceStatement*/
	//	) {
	//		super(scenarioDiagram, header, originalTraceStatement);
	//		arguments = new LinkedList();
	//	}

	/**
	 * Constructor
	 * @param sd  the scenarioDiagram to which the message belongs.
	 * @param type  determine whether a message is an entry or an exit
	 */
	public Message(ScenarioDiagram scenarioDiagram) {
		super(scenarioDiagram);
		this.arguments = new ArrayList();
	}

	public Message(Message msg) {
		this(
			msg.getSignature(),
			msg.getArguments(),
			msg.getSourceClassifier(),
			msg.getDestinationClassifier());
	}

	//	public Message(String info) {
	//		super(info);
	//	}

	public String toString() {
		String tab = "";

		for (int i = 0; i < this.level; i++)
			tab += "\t";
		//tab = "{level " + level + "} " + tab + "<CREATE> ";
		//return tab + originalTraceStatement + "\n" ;
		return tab /*+ this.getIndex()*/;
	}

	/**
	* Overwrites equals of Object.
	* @param otherMessage  the message to 
	*/
	public boolean equals(Object otherMessage) {

		if (!(otherMessage instanceof Message))
			return false;
		if (otherMessage == this)
			return true;

		Message m = (Message) otherMessage;

		if (/*this.signature.equalsIgnoreCase((m.getSignature()))
											&& this.arguments.equals(m.getArguments())
											&& this.destinationClassifier.equals(m.getDestinationClassifier())
											&& this.sourceClassifier.equals(m.getSourceClassifier())
											&& this.level == m.getLevel() 
											&&*/
			this.index == m.getIndex())
			return true;
		return false;
	}

	public int hashCode() {
		return (Integer.valueOf(this.index)).hashCode();

	}

	public boolean argumentsContainsType(String type) {
		Iterator it = this.argumentsIterator();
		while (it.hasNext()) {
			Argument arg = (Argument) it.next();
			if (arg.getType().equals(type))
				return true;
		}
		return false;
	}

	protected Message sourceMessage;

	public void setSourceMessage(Message sourceMessage) {
		this.sourceMessage = sourceMessage;
	}

	public Message getSourceMessage() {
		return this.sourceMessage;
	}

}

//class OriginalTraceStatementMessageComparator implements Comparator {
//
//	/* 
//	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
//	 */
//	public int compare(Object arg0, Object arg1) {
//
//		if (arg0 instanceof Message && arg1 instanceof Message) {
//			Message m0 = (Message) arg0;
//			Message m1 = (Message) arg1;
//			return m0.getOriginalTraceStatement().compareTo(
//				m1.getOriginalTraceStatement());
//		}
//		return 0;
//	}
//
//}
