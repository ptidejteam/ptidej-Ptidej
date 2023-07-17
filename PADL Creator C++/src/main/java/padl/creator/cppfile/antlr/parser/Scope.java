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
package padl.creator.cppfile.antlr.parser;

import java.util.Hashtable;

@SuppressWarnings("rawtypes")
public class Scope {
	/**
	  * Name of the scope (set only for class/function scopes).
	  */
	String scopeName;

	/**
	 * Indicates whether this is a class scope or not.
	 */
	boolean type; // Indicates if this is a type.

	/**
	 * (partial) table of type symbols introduced in this scope.
	 */
	Hashtable typeTable = new Hashtable();

	/**
	 * Parent scope. (null if it is the global scope).
	 */
	Scope parent;

	/**
	 * Creates an unnamed scope (like for compound statements).
	 */
	public Scope(final Scope p) {
		this.type = false;
		this.parent = p;
	}

	/**
	 * Creates a scope object with a given name.
	 */
	public Scope(final String name, final boolean isType, final Scope p) {
		this.scopeName = name;
		this.type = isType;
		this.parent = p;
	}

	public Scope GetScope(final String name) {
		final Object sc = this.typeTable.get(name);

		if (sc instanceof Scope || sc instanceof ClassScope) {
			return (Scope) sc;
		}

		return null;
	}

	/** 
	 * Checks if a given name is the name of a type in this scope.
	 */
	@SuppressWarnings("unchecked")
	public boolean IsTypeName(final String name) {
		//2004/08/16: Sébastien Robidoux
		//This test is now broken.
		//Now, we can use name of class/struct/enum/etc that aren't in 
		//the file parsed.
		if (this.typeTable.get(name) == null) {
			this.typeTable.put(name, name);
		}
		//Before, it was:
		//return typeTable.get(name) != null;
		return true;
	}

	/**
	 * Inserts a name into the table to say that it is the name of a type.
	 */
	@SuppressWarnings("unchecked")
	public void PutTypeName(final String name) {
		this.typeTable.put(name, name);
	}

	/**
	 * A type with a scope (class/struct/union).
	 */
	@SuppressWarnings("unchecked")
	public void PutTypeName(final String name, final Scope sc) {
		this.typeTable.put(name, sc);
	}
}
