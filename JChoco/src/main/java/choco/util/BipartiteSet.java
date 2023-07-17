// *********************************************
// *                   J-CHOCO                 *
// *   Copyright (c) F. Laburthe, 1999-2003    *
// *********************************************
// * Event-base contraint programming Engine   *
// *********************************************

// CVS Information
// File:               $RCSfile: BipartiteSet.java,v $
// Version:            $Revision: 1.1 $
// Last Modification:  $Date: 2004/05/04 22:23:15 $
// Last Contributor:   $Author: guehene $

package choco.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implements a bipartite set.
 *
 * Cette classe est utilisee pour stocker les evenements de propagation
 * de contrainte : les elements de gauche sont a propages, les autres
 * ne doivent pas etre propages.
 */
public class BipartiteSet {
	private class LeftItr implements Iterator {
		int cursor = 0;

		public boolean hasNext() {
			return this.cursor != BipartiteSet.this.nbLeft;
		}

		public Object next() {
			return BipartiteSet.this.objects.get(this.cursor++);
		}

		public void remove() {
		}
	}

	private class RightItr implements Iterator {
		int cursor = BipartiteSet.this.nbLeft;

		public boolean hasNext() {
			return this.cursor != BipartiteSet.this.objects.size();
		}

		public Object next() {
			return BipartiteSet.this.objects.get(this.cursor++);
		}

		public void remove() {
		}
	}

	/**
	 * Reference to an object for logging trace statements related to util (using the java.util.logging package)
	 */

	private static Logger logger = Logger.getLogger("choco.util.BipartiteSet");

	/**
	 * Contains all the objects in the two parts of the set.
	 */

	ArrayList objects;

	/**
	 * The number of elements in the left part of the set.
	 */

	int nbLeft = 0;

	/**
	 * Maps the element objects to the corresponding index.
	 */

	HashMap indices = new HashMap();

	/**
	 * Constructs a new bipartite set. Initialized internal util.
	 */

	public BipartiteSet() {
		this.objects = new ArrayList();
		this.indices = new HashMap();
	}

	/**
	 * Adds an object to the left part of the set.
	 */

	public void addLeft(final Object object) {
		this.addRight(object);
		this.moveLeft(object);
	}

	/**
	 * Adds an object to the right part of the set.
	 */

	public void addRight(final Object object) {
		if (this.indices.get(object) != null) {
			BipartiteSet.logger.logp(
				Level.SEVERE,
				"BipartiteSet",
				"addRight",
				object + "already in the set bipartite set ");
		}
		else {
			this.objects.add(object);
			this.indices.put(object, Integer.valueOf(this.objects.size() - 1));
		}
	}

	/**
	 * Returns the number of elements in the left part.
	 */

	public int getNbLeft() {
		return this.nbLeft;
	}

	/**
	 * Returns the number of objects in the set.
	 */

	public int getNbObjects() {
		return this.objects.size();
	}

	/**
	 * Returns the number of elements in the right part.
	 */

	public int getNbRight() {
		return this.objects.size() - this.nbLeft;
	}

	/**
	 * Checks if the object is in the set.
	 */

	public boolean isIn(final Object object) {
		return this.indices.get(object) != null;
	}

	/**
	 * Checks if the object is in the left part of the set.
	 */

	public boolean isLeft(final Object object) {
		final Object idx = this.indices.get(object);
		if (idx == null) {
			BipartiteSet.logger.logp(
				Level.SEVERE,
				"BipartiteSet",
				"isLeft",
				"bipartite set does not contain " + object);
			return false;
		}
		else {
			final int index = ((Integer) idx).intValue();
			return index < this.nbLeft;
		}
	}

	/**
	 * Iterator without a valid remove method !
	 * Warning : suppose the set is not modified suring iterating !
	 */

	public Iterator leftIterator() {
		return new LeftItr();
	}

	/**
	 * Moves all the objects to the left part.
	 */

	public void moveAllLeft() {
		this.nbLeft = this.objects.size();
	}

	/**
	 * Moves all the objects to the right part.
	 */

	public void moveAllRight() {
		this.nbLeft = 0;
	}

	/**
	 * Move the last element in the left part to the right part.
	 * @return The moved element.
	 */

	public Object moveLastLeft() {
		// Autant eviter d'appeler la fonction de hachage pour popper le
		// dernier evenement !
		if (this.nbLeft > 0) {
			final Object ret = this.objects.get(--this.nbLeft);
			return ret;
		}
		else {
			return null;
		}
	}

	/**
	 * Moves the object the left part of the set if needed.
	 */

	public void moveLeft(final Object object) {
		final Object idx = this.indices.get(object);
		if (idx == null) {
			BipartiteSet.logger.logp(
				Level.SEVERE,
				"BipartiteSet",
				"moveLeft",
				"bipartite set does not contain " + object);
		}
		else {
			final int index = ((Integer) idx).intValue();
			if (index >= this.nbLeft) {
				this.swap(index, this.nbLeft++);
			}
		}
	}

	/**
	 * Moves the object the right part of the set if needed.
	 */

	public void moveRight(final Object object) {
		final Object idx = this.indices.get(object);
		if (idx == null) {
			BipartiteSet.logger.logp(
				Level.SEVERE,
				"BipartiteSet",
				"moveRight",
				"bipartite set does not contain " + object);
		}
		else {
			final int index = ((Integer) idx).intValue();
			if (index < this.nbLeft) {
				this.swap(index, --this.nbLeft);
			}
		}
	}

	/**
	 * Iterator without a valid remove method !
	 * Warning : suppose the set is not modified suring iterating !
	 */

	public Iterator rightIterator() {
		return new RightItr();
	}

	/**
	 * Swaps two elements in the list containing all the objects of the set.
	 */

	private void swap(final int idx1, final int idx2) {
		if (idx1 != idx2) {
			final Object obj1 = this.objects.get(idx1);
			final Object obj2 = this.objects.get(idx2);
			this.objects.set(idx1, obj2);
			this.objects.set(idx2, obj1);
			this.indices.put(obj1, Integer.valueOf(idx2));
			this.indices.put(obj2, Integer.valueOf(idx1));
		}
	}
}
