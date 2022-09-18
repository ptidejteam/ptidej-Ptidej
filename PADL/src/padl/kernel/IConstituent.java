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
package padl.kernel;

import java.io.Serializable;
import padl.visitor.IVisitor;

/**
 * - startCloneSession() is somewhat equivallent to a shallow
 *   copy of the constituent. After this protocol is executed,
 *   it is guaranteed that all constituents have been
 *   shallow-copied. No assumption is made about the link among
 *   constituents.
 * - performCloneSession() updates the links among constituents,
 *   using the isCloned() and getClone() methods. After this
 *   protocol is executed, it is guaranteed that all the links
 *   have been updated, somewhat like a deep copy;
 * - endCloneSession() finished the updates and cleans the possible
 *   temporary values, mainly it sets to null all the "clone"
 *   instance variable.
 * 
 * @author Yann-Gaël Guéhéneuc
 */
public interface IConstituent extends Cloneable, INavigable, Serializable {
	void accept(final IVisitor aVisitor);
	void endCloneSession();
	boolean equals(final Object anObject);
	/**
	 * This method return a clone (deep copy) of the constituent.
	 */
	IConstituent getClone();
	String[] getCodeLines();
	String getComment();
	// Yann 2004/05/21: Flexibility with names.
	/**
	 * The method getDisplayName() returns the name to be
	 * displayed by graphical front-end, while the getName()
	 * method returns the (fully-qualified) name of the
	 * constituent, and getID() return a unique identifier.
	 * (By default, getDisplayName() and getName() are identical.)
	 */
	String getDisplayName();
	String getDisplayID();
	char[] getID();
	char[] getName();
	int getVisibility();
	int getWeight();
	int hashCode();
	boolean isAbstract();
	boolean isFinal();
	boolean isPrivate();
	boolean isProtected();
	boolean isPublic();
	boolean isStatic();
	void performCloneSession();
	void resetCodeLines();
	void setAbstract(final boolean aBoolean);
	void setCodeLines(final String someCode);
	void setCodeLines(final String[] someCode);
	void setComment(final String aComment);
	void setDisplayName(final String aName);
	void setFinal(final boolean aBoolean);
	void setName(final char[] aName);
	void setPrivate(final boolean aBoolean);
	void setProtected(final boolean aBoolean);
	void setPublic(final boolean aBoolean);
	void setStatic(final boolean aBoolean);
	void setVisibility(final int aVisibility);
	void setWeight(final int aWeight);
	void startCloneSession();
	String toString();
	String toString(final int aTab);
	/**
	 * Implementation of the Extension Pattern
	 */
	void addExtension(final IConstituentExtension anExtension);
	IConstituentExtension getExtension(final char[] anExtensionName);
}
