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

public class Classifier {

	/**
	 * @uml.property  name="destClassifierbelongsToMessage"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="destinationClassifier:metamodel.scenarioDiagram.Message"
	 */
	private Collection destClassifierbelongsToMessage;

	/**
	 * Getter of the property <tt>destClassifierbelongsToMessage</tt>
	 * @return  Returns the destClassifierbelongsToMessage.
	 * @uml.property  name="destClassifierbelongsToMessage"
	 */
	public Collection getDestClassifierbelongsToMessage() {
		return this.destClassifierbelongsToMessage;
	}

	/**
	 * Returns an iterator over the elements in this collection. 
	 * @return  an <tt>Iterator</tt> over the elements in this collection
	 * @see java.util.Collection#iterator()
	 * @uml.property  name="destClassifierbelongsToMessage"
	 */
	public Iterator destClassifierbelongsToMessageIterator() {
		return this.destClassifierbelongsToMessage.iterator();
	}

	/**
	 * Returns <tt>true</tt> if this collection contains no elements.
	 * @return  <tt>true</tt> if this collection contains no elements
	 * @see java.util.Collection#isEmpty()
	 * @uml.property  name="destClassifierbelongsToMessage"
	 */
	public boolean isDestClassifierbelongsToMessageEmpty() {
		return this.destClassifierbelongsToMessage.isEmpty();
	}

	/**
	 * Returns <tt>true</tt> if this collection contains the specified element. 
	 * @param element  whose presence in this collection is to be tested.
	 * @see java.util.Collection#contains(Object)
	 * @uml.property  name="destClassifierbelongsToMessage"
	 */
	public boolean containsDestClassifierbelongsToMessage(Message message) {
		return this.destClassifierbelongsToMessage.contains(message);
	}

	/**
	 * Returns <tt>true</tt> if this collection contains all of the elements in the specified collection.
	 * @param elements  collection to be checked for containment in this collection.
	 * @see java.util.Collection#containsAll(Collection)
	 * @uml.property  name="destClassifierbelongsToMessage"
	 */
	public boolean containsAllDestClassifierbelongsToMessage(Collection destClassifierbelongsToMessage) {
		return this.destClassifierbelongsToMessage.containsAll(
			destClassifierbelongsToMessage);
	}

	/**
	 * Returns the number of elements in this collection.
	 * @return  the number of elements in this collection
	 * @see java.util.Collection#size()
	 * @uml.property  name="destClassifierbelongsToMessage"
	 */
	public int destClassifierbelongsToMessageSize() {
		return this.destClassifierbelongsToMessage.size();
	}

	/**
	 * Returns all elements of this collection in an array.
	 * @return  an array containing all of the elements in this collection
	 * @see java.util.Collection#toArray()
	 * @uml.property  name="destClassifierbelongsToMessage"
	 */
	public Message[] destClassifierbelongsToMessageToArray() {
		return (Message[]) this.destClassifierbelongsToMessage.toArray(
			new Message[this.destClassifierbelongsToMessage.size()]);
	}

	/**
	 * Returns an array containing all of the elements in this collection;  the runtime type of the returned array is that of the specified array.
	 * @param a  the array into which the elements of this collection are to be stored.
	 * @return  an array containing all of the elements in this collection
	 * @see java.util.Collection#toArray(Object[])
	 * @uml.property  name="destClassifierbelongsToMessage"
	 */
	public Message[] destClassifierbelongsToMessageToArray(Message[] destClassifierbelongsToMessage) {
		return (Message[]) this.destClassifierbelongsToMessage.toArray(
			destClassifierbelongsToMessage);
	}

	/**
	 * Ensures that this collection contains the specified element (optional operation). 
	 * @param element  whose presence in this collection is to be ensured.
	 * @see java.util.Collection#add(Object)
	 * @uml.property  name="destClassifierbelongsToMessage"
	 */
	public boolean addDestClassifierbelongsToMessage(Message message) {
		return this.destClassifierbelongsToMessage.add(message);
	}

	/**
	 * Setter of the property <tt>destClassifierbelongsToMessage</tt>
	 * @param destClassifierbelongsToMessage  the destClassifierbelongsToMessage to set.
	 * @uml.property  name="destClassifierbelongsToMessage"
	 */
	public void setDestClassifierbelongsToMessage(Collection destClassifierbelongsToMessage) {
		this.destClassifierbelongsToMessage = destClassifierbelongsToMessage;
	}

	/**
	 * Removes a single instance of the specified element from this collection, if it is present (optional operation).
	 * @param element  to be removed from this collection, if present.
	 * @see java.util.Collection#add(Object)
	 * @uml.property  name="destClassifierbelongsToMessage"
	 */
	public boolean removeDestClassifierbelongsToMessage(Message message) {
		return this.destClassifierbelongsToMessage.remove(message);
	}

	/**
	 * Removes all of the elements from this collection (optional operation).
	 * @see java.util.Collection#clear()
	 * @uml.property  name="destClassifierbelongsToMessage"
	 */
	public void clearDestClassifierbelongsToMessage() {
		this.destClassifierbelongsToMessage.clear();
	}

	/**
	 * @uml.property  name="sourceClassifierbelongsToMessage"
	 * @uml.associationEnd  multiplicity="(0 -1)" inverse="sourceClassifier:metamodel.scenarioDiagram.Message"
	 */
	private Collection sourceClassifierbelongsToMessage;

	/**
	 * Getter of the property <tt>sourceClassifierbelongsToMessage</tt>
	 * @return  Returns the sourceClassifierbelongsToMessage.
	 * @uml.property  name="sourceClassifierbelongsToMessage"
	 */
	public Collection getSourceClassifierbelongsToMessage() {
		return this.sourceClassifierbelongsToMessage;
	}

	/**
	 * Returns an iterator over the elements in this collection. 
	 * @return  an <tt>Iterator</tt> over the elements in this collection
	 * @see java.util.Collection#iterator()
	 * @uml.property  name="sourceClassifierbelongsToMessage"
	 */
	public Iterator sourceClassifierbelongsToMessageIterator() {
		return this.sourceClassifierbelongsToMessage.iterator();
	}

	/**
	 * Returns <tt>true</tt> if this collection contains no elements.
	 * @return  <tt>true</tt> if this collection contains no elements
	 * @see java.util.Collection#isEmpty()
	 * @uml.property  name="sourceClassifierbelongsToMessage"
	 */
	public boolean isSourceClassifierbelongsToMessageEmpty() {
		return this.sourceClassifierbelongsToMessage.isEmpty();
	}

	/**
	 * Returns <tt>true</tt> if this collection contains the specified element. 
	 * @param element  whose presence in this collection is to be tested.
	 * @see java.util.Collection#contains(Object)
	 * @uml.property  name="sourceClassifierbelongsToMessage"
	 */
	public boolean containsSourceClassifierbelongsToMessage(Message message) {
		return this.sourceClassifierbelongsToMessage.contains(message);
	}

	/**
	 * Returns <tt>true</tt> if this collection contains all of the elements in the specified collection.
	 * @param elements  collection to be checked for containment in this collection.
	 * @see java.util.Collection#containsAll(Collection)
	 * @uml.property  name="sourceClassifierbelongsToMessage"
	 */
	public boolean containsAllSourceClassifierbelongsToMessage(Collection sourceClassifierbelongsToMessage) {
		return this.sourceClassifierbelongsToMessage.containsAll(
			sourceClassifierbelongsToMessage);
	}

	/**
	 * Returns the number of elements in this collection.
	 * @return  the number of elements in this collection
	 * @see java.util.Collection#size()
	 * @uml.property  name="sourceClassifierbelongsToMessage"
	 */
	public int sourceClassifierbelongsToMessageSize() {
		return this.sourceClassifierbelongsToMessage.size();
	}

	/**
	 * Returns all elements of this collection in an array.
	 * @return  an array containing all of the elements in this collection
	 * @see java.util.Collection#toArray()
	 * @uml.property  name="sourceClassifierbelongsToMessage"
	 */
	public Message[] sourceClassifierbelongsToMessageToArray() {
		return (Message[]) this.sourceClassifierbelongsToMessage.toArray(
			new Message[this.sourceClassifierbelongsToMessage.size()]);
	}

	/**
	 * Returns an array containing all of the elements in this collection;  the runtime type of the returned array is that of the specified array.
	 * @param a  the array into which the elements of this collection are to be stored.
	 * @return  an array containing all of the elements in this collection
	 * @see java.util.Collection#toArray(Object[])
	 * @uml.property  name="sourceClassifierbelongsToMessage"
	 */
	public Message[] sourceClassifierbelongsToMessageToArray(Message[] sourceClassifierbelongsToMessage) {
		return (Message[]) this.sourceClassifierbelongsToMessage.toArray(
			sourceClassifierbelongsToMessage);
	}

	/**
	 * Ensures that this collection contains the specified element (optional operation). 
	 * @param element  whose presence in this collection is to be ensured.
	 * @see java.util.Collection#add(Object)
	 * @uml.property  name="sourceClassifierbelongsToMessage"
	 */
	public boolean addSourceClassifierbelongsToMessage(Message message) {
		return this.sourceClassifierbelongsToMessage.add(message);
	}

	/**
	 * Setter of the property <tt>sourceClassifierbelongsToMessage</tt>
	 * @param sourceClassifierbelongsToMessage  the sourceClassifierbelongsToMessage to set.
	 * @uml.property  name="sourceClassifierbelongsToMessage"
	 */
	public void setSourceClassifierbelongsToMessage(Collection sourceClassifierbelongsToMessage) {
		this.sourceClassifierbelongsToMessage =
			sourceClassifierbelongsToMessage;
	}

	/**
	 * Removes a single instance of the specified element from this collection, if it is present (optional operation).
	 * @param element  to be removed from this collection, if present.
	 * @see java.util.Collection#add(Object)
	 * @uml.property  name="sourceClassifierbelongsToMessage"
	 */
	public boolean removeSourceClassifierbelongsToMessage(Message message) {
		return this.sourceClassifierbelongsToMessage.remove(message);
	}

	/**
	 * Removes all of the elements from this collection (optional operation).
	 * @see java.util.Collection#clear()
	 * @uml.property  name="sourceClassifierbelongsToMessage"
	 */
	public void clearSourceClassifierbelongsToMessage() {
		this.sourceClassifierbelongsToMessage.clear();
	}

	//====================================



}
