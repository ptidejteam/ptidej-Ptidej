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
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ScenarioDiagram {

	/**
	 * @uml.property   name="component"
	 * @uml.associationEnd   multiplicity="(1 -1)" ordering="true" aggregation="composite" inverse="scenarioDiagram:metamodel.scenarioDiagram.Component"
	 */
	private List component;
	private Map cacheDestinationCalledMessages;
	private Map cacheSourceCalledMessages;
	private Map cacheClassifierIdx;
	private Map cacheIdxClassifier;
	private Map cacheMessageIdx;
	private Map cacheIdxMessage;
	private Map cacheMessageContainer;

	/** 
	 * Returns the element at the specified position in this list.
	 * @param index  index of element to return.
	 * @return  the element at the specified position in this list.
	 * @see java.util.List#get(int)
	 * @uml.property  name="component"
	 */
	public List getComponent() {
		return this.component;
	}

	/** 
	 * Returns the element at the specified position in this list.
	 * @param index  index of element to return.
	 * @return  the element at the specified position in this list.
	 * @see java.util.List#get(int)
	 * @uml.property  name="component"
	 */
	public Component getComponent(int i) {
		return (Component) this.component.get(i);
	}

	/**
	 * Returns <tt>true</tt> if this list contains no elements.
	 * @return  <tt>true</tt> if this list contains no elements.
	 * @see java.util.List#isEmpty()
	 * @uml.property  name="component"
	 */
	public boolean isComponentEmpty() {
		return this.component.isEmpty();
	}

	/**
	 * Returns <tt>true</tt> if this list contains the specified element.
	 * @param element  element whose presence in this list is to be tested.
	 * @return  <tt>true</tt> if this list contains the specified element.
	 * @see java.util.List#contains(Object)
	 * @uml.property  name="component"
	 */
	public boolean containsComponent(Component component) {
		return this.component.contains(component);
	}

	/**
	 * Returns <tt>true</tt> if this list contains all of the elements of the specified collection.
	 * @param elements  collection to be checked for containment in this list.
	 * @return  <tt>true</tt> if this list contains all of the elements of the specified collection.
	 * @see java.util.List#containsAll(Collection)
	 * @uml.property  name="component"
	 */
	public boolean containsAllComponent(Collection component) {
		return this.component.containsAll(component);
	}

	/**
	 * Returns an array containing all of the elements in this list in proper sequence; the runtime type of the returned array is that of the specified array.
	 * @param a  the array into which the elements of this list are to be stored.
	 * @return  an array containing all of the elements in this list in proper sequence.
	 * @see java.util.List#toArray(Object[])
	 * @uml.property  name="Component"
	 */
	public Component[] ComponentToArray(Component[] component) {
		return (Component[]) this.component.toArray(component);
	}

	/**
	 * Inserts the specified element at the specified position in this list (optional operation)
	 * @param index  index at which the specified element is to be inserted.
	 * @param element  element to be inserted.
	 * @see java.util.List#add(int,Object)
	 * @uml.property  name="component"
	 */
	public void addComponent(int index, Component component) {
		this.component.add(index, component);
	}

	public void addLastComponent(Component component) {
		((LinkedList) this.component).addLast(component);
	}

	public void addFirstComponent(Component component) {
		((LinkedList) this.component).addFirst(component);
	}

	/**
	 * Appends the specified element to the end of this list (optional operation).
	 * @param element  element to be appended to this list.
	 * @return  <tt>true</tt> (as per the general contract of the <tt>Collection.add</tt> method).
	 * @see java.util.List#add(Object)
	 * @uml.property  name="component"
	 */
	public boolean addComponent(Component component) {
		return this.component.add(component);
	}

	/**
	 * Removes the element at the specified position in this list (optional operation).
	 * @param index  the index of the element to removed.
	 * @return  the element previously at the specified position.
	 * @see java.util.List#remove(int)
	 * @uml.property  name="component"
	 */
	public Object removeComponent(int index) {
		return this.component.remove(index);
	}

	/**
	 * Removes the first occurrence in this list of the specified element  (optional operation).
	 * @param element  element to be removed from this list, if present.
	 * @return  <tt>true</tt> if this list contained the specified element.
	 * @see java.util.List#remove(Object)
	 * @uml.property  name="component"
	 */
	public boolean removeComponent(Component component) {
		return this.component.remove(component);
	}

	/**
	 * Removes all of the elements from this list (optional operation).
	 * @see java.util.List#clear()
	 * @uml.property  name="component"
	 */
	public void clearComponent() {
		this.component.clear();
	}

	/** 
	 * Setter of the property <tt>Component</tt>
	 * @param Component  the component to set.
	 * @uml.property  name="component"
	 */
	public void setComponent(List component) {
		this.component = component;
	}

	/**
	 * Returns an iterator over the elements in this list in proper sequence.
	 * @return  an iterator over the elements in this list in proper sequence.
	 * @see java.util.List#iterator()
	 * @uml.property  name="component"
	 */
	public Iterator componentIterator() {
		return this.component.iterator();
	}

	/**
	 * Returns the number of elements in this list.
	 * @return  the number of elements in this list.
	 * @see java.util.List#size()
	 * @uml.property  name="component"
	 */
	public int componentSize() {
		return this.component.size();
	}

	/**
	 * Returns an array containing all of the elements in this list in proper sequence.
	 * @return  an array containing all of the elements in this list in proper sequence.
	 * @see java.util.List#toArray()
	 * @uml.property  name="component"
	 */
	public Component[] componentToArray() {
		return (Component[]) this.component.toArray(new Component[this.component.size()]);
	}

	/**
	 * Returns an array containing all of the elements in this list in proper sequence; the runtime type of the returned array is that of the specified array.
	 * @param a    the array into which the elements of this list are to be stored.
	 * @return    an array containing all of the elements in this list in proper sequence.
	 * @see java.util.List#toArray(Object[])
	 * @uml.property    name="component"
	 */
	public Component[] componentToArray(Component[] component) {
		return (Component[]) this.component.toArray(component);
	}

	// ========================================================================

	/**
	 * Initialize an instance of ScenarioDiagram.
	 */
	public ScenarioDiagram() {
		this.component = new ArrayList();
		this.cacheDestinationCalledMessages = new HashMap();
		this.cacheSourceCalledMessages = new HashMap();
		this.cacheClassifierIdx = new HashMap();
		this.cacheIdxClassifier = new HashMap();
		this.cacheMessageIdx = new HashMap();
		this.cacheIdxMessage = new HashMap();
		this.cacheMessageContainer = new HashMap();
	}

	public String toString() {
		Collections.sort(this.component);
		String info = "";

		System.out.println("Components size : " + this.component.size());
		// parcourir de la fin pour affichier les Component du plus petit au
		// plus grand selon l'index (c.f. comparator de Component)
		for (int i = this.component.size() - 1; i >= 0; i--) {
			System.out.println(i);
			//for(int i = 0 ;i < component.size(); i++)
			info += (Component) this.component.get(i);
		}
		return info;
	}

	public Component getNextComponent(Component comp) {
		int idxComponent = this.component.indexOf(comp);
		if (idxComponent > -1 && idxComponent < this.component.size() - 1)
			return (Component) this.component.get(idxComponent + 1);
		return null;
	}

	public Component getPreviousComponent(Component comp) {
		int idxComponent = this.component.indexOf(comp);
		if (idxComponent > 0)
			return (Component) this.component.get(idxComponent - 1);
		return null;
	}

	public Message getIdxFirstLevelMessage(int idx) {
		Iterator it = this.componentIterator();
		int i = -1;
		while (it.hasNext()) {
			Component comp = (Component) it.next();
			if (comp instanceof Message) {
				i++;
				if (i == idx)
					return (Message) comp;
			}
		}
		return null;
	}

	public int getFirstLevelMessageIdx(Message msg) {
		int idx = -1;
		Iterator it = this.componentIterator();
		while (it.hasNext()) {
			Component comp = (Component) it.next();
			if (comp instanceof Message && !comp.equals(msg))
				idx++;
			else if (comp instanceof Message && comp.equals(msg)) {
				return ++idx;
			}
		}
		return idx;
	}

	//	public Message getIdxMessage(List componentsMesssages, int idx) {
	//		//return (Message) this.visitComponentMessages().get(idx);
	//		return (Message) componentsMesssages.get(idx);
	//	}

	//	public int getMessageIdx(List componentsMesssages, Message msg) {
	//		//return this.visitComponentMessages().indexOf(msg);
	//		return componentsMesssages.indexOf(msg);
	//	}

	public Message getIdxMessage(int idx) {
		//return (Message) this.visitComponentMessages().get(idx);
		return (Message) this.cacheIdxMessage.get(Integer.valueOf(idx));
	}

	public int getMessageIdx(Message msg) {
		//return this.visitComponentMessages().indexOf(msg);
		return ((Integer) this.cacheMessageIdx.get(msg)).intValue();
	}

	//	public int getComponentIdx(Component c) {
	//		int idx = this.component.indexOf(c);
	//		if (idx > -1)
	//			return idx;
	//		return idx;
	//	}

	public int countNbFirstLevelMessages() {
		int counter = -1;
		Iterator it = this.componentIterator();
		while (it.hasNext())
			if (it.next() instanceof Message)
				counter++;
		return counter;
	}

	public int countNbMessages(List componentsMesssages) {
		return componentsMesssages.size() - 1;
		//System.out.println(this.visitComponentMessages().size());
		//return this.visitComponentMessages().size() - 1;
	}

	public List getSourceCalledMessages(Classifier caller) {
		return (List) this.cacheSourceCalledMessages.get(caller);
	}

	public List getDestinationCalledMessages(Classifier callee) {
		return (List) this.cacheDestinationCalledMessages.get(callee);
	}

	public void determineSourceCalledMessages(
		List componentsMesssages,
		List allClassifiers) {

		List theMessages;
		Iterator it_1 = allClassifiers.iterator();

		while (it_1.hasNext()) {
			Classifier caller = (Classifier) it_1.next();
			Iterator it_0 = componentsMesssages.iterator();
			theMessages = new ArrayList();
			while (it_0.hasNext()) {
				Message comp = (Message) it_0.next();
				if (comp instanceof Message
					&& ((Message) comp).getSourceClassifier().equals(caller))
					theMessages.add(comp);
			}
			this.cacheSourceCalledMessages.put(caller, theMessages);
		}
	}

	public void determineDestinationCalledMessages(
		List componentsMessages,
		List allClassifiers) {

		List theMessages;
		Iterator it_1 = allClassifiers.iterator();

		while (it_1.hasNext()) {
			Classifier callee = (Classifier) it_1.next();
			Iterator it_0 = componentsMessages.iterator();
			theMessages = new ArrayList();
			while (it_0.hasNext()) {
				Message comp = (Message) it_0.next();
				if (comp instanceof Message
					&& ((Message) comp).getDestinationClassifier().equals(callee))
					theMessages.add(comp);
			}
			this.cacheDestinationCalledMessages.put(callee, theMessages);
		}
	}

	//		public List getSourceCalledMessages(
	//			List componentsMesssages,
	//			Classifier caller) {
	//			List theMessages = new ArrayList();
	//			//Iterator it = this.componentIterator();
	//			//Iterator it = this.visitComponentMessages().iterator();
	//			Iterator it = componentsMesssages.iterator();
	//			while (it.hasNext()) {
	//				Message comp = (Message) it.next();
	//				if (comp instanceof Message
	//					&& ((Message) comp).getSourceClassifier().equals(caller))
	//					theMessages.add(comp);
	//			}
	//			return theMessages;
	//		}
	//	
	//		public List getDestinationCalledMessages(
	//			List componentsMesssages,
	//			Classifier callee) {
	//			List theMessages = new ArrayList();
	//			//Iterator it = this.visitComponentMessages().iterator();
	//			//Iterator it = this.componentIterator();
	//			Iterator it = componentsMesssages.iterator();
	//			while (it.hasNext()) {
	//				Message comp = (Message) it.next();
	//				if (comp instanceof Message
	//					&& ((Message) comp).getDestinationClassifier().equals(callee))
	//					theMessages.add(comp);
	//			}
	//			return theMessages;
	//		}

	//	
	//	public List differentNameClassifiers(List allClassifiers, Classifier c) {
	//		
	//		List theClassifiers = new ArrayList();
	//				//Iterator it = this.componentIterator();
	//				//Iterator it = this.visitComponentMessages().iterator();
	//				Iterator it = componentsMesssages.iterator();
	//
	//				while (it.hasNext()) {
	//					Message comp = (Message) it.next();
	//					if (comp instanceof Message) {
	//						final Classifier destination =
	//							((Message) comp).getDestinationClassifier();
	//						final Classifier source =
	//							((Message) comp).getSourceClassifier();
	//						//System.out.println("****" + destination);
	//						if (destination instanceof Instance
	//							&& !theClassifiers.contains(destination))
	//							theClassifiers.add(destination);
	//						if (source instanceof Instance
	//							&& !theClassifiers.contains(source))
	//							theClassifiers.add(source);
	//					}
	//				}
	//				return theClassifiers;
	//		
	//	
	//	}

	//	public int getCallerIdx(Classifier caller) {
	//		return this.getAllCallers().indexOf(caller);
	//	}
	//
	//	public Classifier getIdxCaller(int idx) {
	//		return (Classifier) (this.getAllCallers().get(idx));
	//	}
	//
	//	public int getCalleeIdx(Classifier callee) {
	//		return this.getAllCallees().indexOf(callee);
	//	}
	//
	//	public Classifier getIdxCallee(int idx) {
	//		return (Classifier) (this.getAllCallees().get(idx));
	//	}

	//	public int getClassifierIdx(List allClassifiers, Classifier classifier) {
	//		//return this.getAllClassifiers().indexOf(classifier);
	//		return allClassifiers.indexOf(classifier);
	//	}

	public void determineClassifierIdx(List allClassifiers) {
		Iterator it = allClassifiers.iterator();
		while (it.hasNext()) {
			Classifier c = (Classifier) it.next();
			this.cacheClassifierIdx.put(c, Integer.valueOf(allClassifiers.indexOf(c)));
		}

	}

	public int getClassifierIdx(Classifier classifier) {
		//return this.getAllClassifiers().indexOf(classifier);
		//System.out.println("***\t" + classifier + " : " + cacheClassifierIdx.get(classifier));
		return ((Integer) this.cacheClassifierIdx.get(classifier)).intValue();
	}

	//	public Classifier getIdxClassifier(List allClassifiers, int idx) {
	//		//return (Classifier) (this.getAllClassifiers().get(idx));
	//		return (Classifier) (allClassifiers.get(idx));
	//	}

	public void determineIdxClassifier(List allClassifiers) {
		Iterator it = allClassifiers.iterator();
		while (it.hasNext()) {
			Classifier c = (Classifier) it.next();
			this.cacheIdxClassifier.put(Integer.valueOf(allClassifiers.indexOf(c)), c);
		}
	}

	public Classifier getIdxClassifier(int idx) {
		//return (Classifier) (this.getAllClassifiers().get(idx));
		return (Classifier) (this.cacheIdxClassifier.get(Integer.valueOf(idx)));
	}

	//	public List getAllCallers() {
	//		List theCallers = new ArrayList();
	//		Iterator it = this.visitComponentMessages().iterator();
	//		//Iterator it = this.componentIterator();
	//		while (it.hasNext()) {
	//			Message comp = (Message) it.next();
	//			if (comp instanceof Message) {
	//				Classifier source = ((Message) comp).getSourceClassifier();
	//				if (!theCallers.contains(source))
	//					/*System.out.println(source + " : " + */
	//					theCallers.add(source) /*)*/;
	//			}
	//		}
	//		return theCallers;
	//	}
	//
	//	public List getAllCallees() {
	//		List theCallees = new ArrayList();
	//		//Iterator it = this.componentIterator();
	//		Iterator it = this.visitComponentMessages().iterator();
	//		while (it.hasNext()) {
	//			Message comp = (Message) it.next();
	//			if (comp instanceof Message) {
	//				Classifier destination =
	//					((Message) comp).getDestinationClassifier();
	//				//System.out.println("****" + destination);
	//				if (!theCallees.contains(destination))
	//					theCallees.add(destination);
	//			}
	//		}
	//		return theCallees;
	//	}

	public List getAllClassifiers(List componentsMesssages) {
		List theClassifiers = new ArrayList();
		//Iterator it = this.componentIterator();
		//Iterator it = this.visitComponentMessages().iterator();
		Iterator it = componentsMesssages.iterator();

		while (it.hasNext()) {
			Message comp = (Message) it.next();
			if (comp instanceof Message) {
				final Classifier destination =
					((Message) comp).getDestinationClassifier();
				final Classifier source =
					((Message) comp).getSourceClassifier();
				//System.out.println("****" + destination);
				if (destination instanceof Instance
					&& !theClassifiers.contains(destination))
					theClassifiers.add(destination);
				if (source instanceof Instance
					&& !theClassifiers.contains(source))
					theClassifiers.add(source);
			}
		}
		return theClassifiers;
	}

	//	public int countNbCallers() {
	//		//System.out.println("*** " + this.getAllCallers());
	//		return this.getAllCallers().size() - 1;
	//	}
	//
	//	public int countNbCallees() {
	//		//System.out.println("*** " + this.getAllCallees());
	//		return this.getAllCallees().size() - 1;
	//	}

	public int countNbClassifiers(List allClassifiers) {
		return allClassifiers.size() - 1;
	}

	//	public List visitComponent(
	//		List listOfMessages,
	//		List components,
	//		int level) {
	//		//		if (0 < components.size())
	//		//			System.out.print(
	//		//				" *** "
	//		//					+ 
	//		//					+ " "
	//		//					+ components.get(0).getClass()
	//		//					+ " "
	//		//					+ components.get(0));
	//		System.out.println(level + " " + components.size());
	//		if (0 == components.size() && level == 0)
	//			return listOfMessages;
	//		else if (components.get(0) instanceof Message) {
	//			listOfMessages.add(components.remove(0));
	//			if (components.size() == 0 && level > 0)
	//				level--;
	//			return visitComponent(listOfMessages, components, level);
	//		}
	//		//if (components.get(index) instanceof CombinedFragment)) 
	//		else {
	//			level++;
	//			List newComponents =
	//				((CombinedFragment) components.get(0)).getOperands();
	//			Iterator it = newComponents.iterator();
	//			while (it.hasNext()) {
	//				listOfMessages.add(
	//					visitComponent(
	//						listOfMessages,
	//						((Operand) it.next()).getComponents(),
	//						level));
	//			}
	//			return listOfMessages;
	//		}
	//	}

	public List visitComponentMessages() {
		Collections.sort(this.component);
		List messages = new ArrayList();
		int indexMap = 0;

		for (int i = this.component.size() - 1; i >= 0; i--) {
			//System.out.println("visitComponentMessages " + i);
			if (this.component.get(i) instanceof Message) {
				messages.add(this.component.get(i));
				this.cacheMessageIdx.put(this.component.get(i), Integer.valueOf(indexMap));
				this.cacheIdxMessage.put(Integer.valueOf(indexMap++), this.component.get(i));
			} else if (this.component.get(i) instanceof Loop) {
				List loopMessages =
					((Loop) this.component.get(i)).visitLoopMessages();
				Iterator it = loopMessages.iterator();
				messages.addAll(loopMessages);

				while (it.hasNext()) {
					this.cacheMessageIdx.put(it.next(), Integer.valueOf(indexMap));
					this.cacheIdxMessage.put(
						Integer.valueOf(indexMap++),
						this.component.get(i));
				}
			}
		}
		return messages;
	}

	public List getFollowingMessages(
		List componentsMesssages,
		Message currentMsg) {
		List followingMessages = new ArrayList();
		//Iterator it = this.visitComponentMessages().iterator();
		Iterator it = componentsMesssages.iterator();
		int indexCurrentMsg = componentsMesssages.indexOf(currentMsg);
		//this.visitComponentMessages().indexOf(currentMsg);

		while (it.hasNext()) {
			Message comp = (Message) it.next();
			//int indexComp = this.visitComponentMessages().indexOf(comp);
			int indexComp = componentsMesssages.indexOf(comp);
			if (indexComp > indexCurrentMsg
				&& !followingMessages.contains(comp))
				followingMessages.add(comp);
		}
		return followingMessages;
	}

	public List getPreviousMessages(
		List componentsMesssages,
		Message currentMsg) {
		List previousMessages = new ArrayList();
		//Iterator it = this.visitComponentMessages().iterator();
		Iterator it = componentsMesssages.iterator();
		int indexCurrentMsg =
			//this.visitComponentMessages().indexOf(currentMsg);
	componentsMesssages.indexOf(currentMsg);
		while (it.hasNext()) {
			Message comp = (Message) it.next();
			//int indexComp = this.visitComponentMessages().indexOf(comp);
			int indexComp = componentsMesssages.indexOf(comp);
			if (indexComp < indexCurrentMsg
				&& !previousMessages.contains(comp))
				previousMessages.add(comp);
		}
		return previousMessages;
	}

	public void determineMessageContainer(List componentsMessages) {
		Iterator it = componentsMessages.iterator();

		while (it.hasNext()) {
			Message comp = (Message) it.next();

			if (comp instanceof Message) {

				Message key = comp;
				List value = new ArrayList();
				Message sourceMessage = (Message) key.getSourceMessage();

				this.cacheMessageContainer.put(key, value);

				while (sourceMessage != null) {
					((List) this.cacheMessageContainer.get(comp)).add(sourceMessage);
					key = sourceMessage;
					sourceMessage = (Message) key.getSourceMessage();
				}
//				if(comp.getIndex() == 3442)
//					System.out.println(key + " " + ((List) cacheMessageContainer.get(comp)));
			}
		}
//		System.out.println("Taille de la map cacheMessageContainer : " + cacheMessageContainer.size());
	}

	public Map getCacheMessageContainer() {
		return this.cacheMessageContainer;
	}

	public List getMessageContainer(Message msg) {
		return (List) this.cacheMessageContainer.get(msg);
	}

}
