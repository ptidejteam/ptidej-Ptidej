// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: PriorityQueue.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements a priority aware queue (FIFO structure).
 */
public class PriorityQueue {
	private static class Entry {
		int priority;
		Object element;
		Entry next;
		Entry previous;

		Entry(
			final Object element,
			final Entry next,
			final Entry previous,
			final int priority) {
			this.element = element;
			this.next = next;
			this.previous = previous;
			this.priority = priority;
		}
	}

	private class PQIterator implements Iterator {
		Entry cursor = PriorityQueue.this.header;

		public boolean hasNext() {
			return this.cursor.next != PriorityQueue.this.header;
		}

		public Object next() {
			this.cursor = this.cursor.next;
			return this.cursor.element;
		}

		public void remove() {
		}
	}

	private static Logger logger = Logger.getLogger("choco.util.PriorityQueue");

	/**
	 * The number of available priority levels.
	 */

	private final int levelNb;

	/**
	 * The last element of each priority level.
	 */

	private final Entry[] levelLast;

	/**
	 * The header element: an element for helping implementation.
	 */

	private final Entry header;

	/**
	 * Maps the objects to their entry in the chained list.
	 */

	private final HashMap map;

	/**
	 * The size of the queue.
	 */

	private int size;

	/**
	 * Constructs a queue with 5 priority levels.
	 */

	public PriorityQueue() {
		this(5);
	}

	/**
	 * Constucts a queue with the specified number of
	 * priority levels.
	 */

	public PriorityQueue(final int levelNb) {
		this.levelNb = levelNb;
		this.size = 0;
		this.map = new HashMap();
		this.header = new Entry(null, null, null, -1);
		this.header.next = this.header.previous = this.header;

		this.levelLast = new Entry[levelNb];
		for (int i = 0; i < levelNb; i++) {
			this.levelLast[i] = this.header;
		}
	}

	/**
	 * Adds an element to the queue. It must be a
	 * {@link choco.util.IPrioritizable} object.
	 */

	public boolean add(final Object o) {
		try {
			final IPrioritizable p = (IPrioritizable) o;
			return this.add(o, p.getPriority());
		}
		catch (final ClassCastException e) {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Adds an element to the queue with the specified priority.
	 */

	public boolean add(final Object o, final int priority) {
		if (!this.map.containsKey(o)) { // Voir si on met la condition ou si c'est une precondition

			// Update the Linked List
			final Entry newEntry = new Entry(o, null, null, priority);
			newEntry.previous = this.levelLast[priority];
			newEntry.next = this.levelLast[priority].next;
			newEntry.next.previous = newEntry;
			newEntry.previous.next = newEntry;

			// Update level spans
			//levelLast[priority] = newEntry;
			// Last
			int prior = priority;
			while (prior < this.levelNb
					&& this.levelLast[prior].priority <= priority) {
				//(levelLast[prior] == header) inutile puisque
				// header.priority << toutes les priorites
				this.levelLast[prior] = newEntry;
				prior++;
			}

			// Update the Map
			this.map.put(o, newEntry);

			this.size++;
			return true;
		}
		else {
			PriorityQueue.logger
				.severe("PriorityQueue: Element added already in the queue !");
			return false;
		}
	}

	/**
	 * Adds all the elments of a collection to the queue.
	 *
	 * Not yet implemented.
	 */

	public boolean addAll(final Collection c) {
		boolean anyAddition = false;
		for (final Iterator it = c.iterator(); it.hasNext();) {
			final Object o = it.next();
			anyAddition = anyAddition || this.add(o);
		}
		return anyAddition;
	}

	/**
	 * Clears all the queue.
	 */

	public void clear() {
		this.header.next = this.header.previous = this.header;
		this.size = 0;
		this.map.clear();

		for (int i = 0; i < this.levelNb; i++) {
			this.levelLast[i] = this.header;
		}
	}

	/**
	 * Checks if the object is in the queue.
	 */

	public boolean contains(final Object o) {
		return this.map.containsKey(o);
	}

	/**
	 * Checks if all the element are in the queue.
	 *
	 * Not yet implemented.
	 */

	public boolean containsAll(final Collection c) {
		boolean oneOut = false;
		for (final Iterator it = c.iterator(); it.hasNext() && !oneOut;) {
			final Object o = it.next();
			if (!this.contains(o)) {
				oneOut = true;
			}
		}
		return !oneOut;
	}

	/**
	 * Checks if the queue is equals to another one.
	 * Naive implementation: checks the object id.
	 */

	public boolean equals(final Object o) {
		// A ameliorer si necessaire
		return o == this;
	}

	/**
	 * Checks if the queue is empty.
	 * @return true if the queue is empty
	 */

	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Iterator without a valid remove method !
	 * Warning : suppose the set is not modified suring iterating !
	 */
	public Iterator iterator() {
		return new PQIterator();
	}

	/**
	 * Pops the first var in the queue.
	 */

	public Object popFirst() {
		if (this.size == 0) {
			throw new NoSuchElementException();
		}
		final Object ret = this.header.next.element;
		this.remove(ret);
		return ret;
	}

	/**
	 * Removes the specified object.
	 */

	public boolean remove(final Object o) {
		Entry entry = (Entry) this.map.get(o);
		if (entry != null) {
			// Update map
			this.map.remove(o);

			// Update LinkedList
			entry.previous.next = entry.next;
			entry.next.previous = entry.previous;

			// Update level Span
			int prio = entry.priority;
			while (prio < this.levelNb && this.levelLast[prio] == entry) {
				this.levelLast[prio] = entry.previous;
				prio++;
			}

			entry = null; // force GC... normalement inutile...

			this.size--;
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Removes all the specified objects.
	 *
	 * Not yet implemented.
	 */

	public boolean removeAll(final Collection c) {
		boolean oneWasIn = false;
		for (final Iterator it = c.iterator(); it.hasNext();) {
			final Object o = it.next();
			if (this.remove(o)) {
				oneWasIn = true;
			}
		}
		return oneWasIn;
	}

	/**
	 * Not yet implemented.
	 */

	public boolean retainAll(final Collection c) {
		// TODO : a completer
		throw new UnsupportedOperationException();
	}

	/**
	 * Returns the size of the queue.
	 */

	public int size() {
		return this.size;
	}

	/**
	 * Returns an array with all the objects in the queue.
	 */

	public Object[] toArray() {
		final Object[] ret = new Object[this.size];
		int i = 0;
		Entry current = this.header.next;
		while (i < this.size) {
			ret[i] = current.element;
			current = current.next;
			i++;
		}
		if (current != this.header) {
			PriorityQueue.logger.log(
				Level.SEVERE,
				"Problem in PriorityQueue implementation !");
		}
		return ret;
	}

	/**
	 * Not yet implemented.
	 */

	public Object[] toArray(final Object[] a) {
		// TODO : a completer
		throw new UnsupportedOperationException();
	}

	/**
	 * Updates the location of the element in the list.
	 * The object must be {@link choco.util.IPrioritizable}.
	 */

	public void updatePriority(final Object o) {
		try {
			final IPrioritizable p = (IPrioritizable) o;
			this.updatePriority(o, p.getPriority());
		}
		catch (final ClassCastException e) {
			throw new UnsupportedOperationException();
		}
	}

	/**
	 * Updates the location of the element in the list with the
	 * specified priority.
	 */

	public void updatePriority(final Object o, final int priority) {
		final Entry entry = (Entry) this.map.get(o);
		if (entry != null) {
			if (priority != entry.priority) { // sinon, il n'y a rien a faire !
				// *** Remove ***
				// Update level span
				int prio = entry.priority;
				while (prio < this.levelNb && this.levelLast[prio] == entry) {
					this.levelLast[prio] = entry.previous;
					prio++;
				}

				// Update Linked List
				entry.previous.next = entry.next;
				entry.next.previous = entry.previous;

				// *** Add ***
				entry.priority = priority;
				// Update the Linked List
				entry.previous = this.levelLast[priority];
				entry.next = this.levelLast[priority].next;
				entry.next.previous = entry;
				entry.previous.next = entry;

				// Update level spans
				int prior = priority;
				while (prior < this.levelNb
						&& this.levelLast[prior].priority <= priority) {
					this.levelLast[prior] = entry;
					prior++;
				}
			}
		}
		else {
			PriorityQueue.logger.log(
				Level.SEVERE,
				"Problem in the PriorityQueue update.");
		}
	}
}
