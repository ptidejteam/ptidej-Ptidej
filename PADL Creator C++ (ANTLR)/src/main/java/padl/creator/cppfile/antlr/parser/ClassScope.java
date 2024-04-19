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

import java.util.Vector;

/**
 * Class scope extends Scope in that its search method also searches all its
 * superclasses.
 */

@SuppressWarnings("rawtypes")
public class ClassScope extends Scope {
	/**
	 * The list of scopes corresponding to classes this class inherits.
	 */
	Vector superClasses;

	/**
	 * Creates a new class scope in a given scope.
	 */
	public ClassScope(final String name, final Scope parent) {
		super(name, true, parent);
	}

	/**
	 * Add a super class.
	 */
	@SuppressWarnings("unchecked")
	public void AddSuper(final Scope sc) {
		if (sc == null) {
			return;
		}

		if (this.superClasses == null) {
			this.superClasses = new Vector();
		}

		this.superClasses.addElement(sc);
	}

	/**
	 * Overrides the method in Scope. It also searches in the inherited classes'
	 * scopes also.
	 */
	public boolean IsTypeName(final String name) {
		if (super.IsTypeName(name)) {
			return true;
		}

		if (this.superClasses == null) {
			return false;
		}

		for (int i = 0; i < this.superClasses.size(); i++) {
			if (((Scope) this.superClasses.elementAt(i)).IsTypeName(name)) {
				return true;
			}
		}

		System.out.println("ClassScope.IsTypeName: " + name);
		return false;
	}
}
