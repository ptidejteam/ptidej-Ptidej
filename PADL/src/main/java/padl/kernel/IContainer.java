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

import java.util.Iterator;

/**
 * @author Yann-Gaël Guéhéneuc
 */
/* Yann 2004/04/09
 * The implementation must ensure that constituents
 * are ordered in the same way across platforms.
 * Yann 2013/07/15
 * Actually, since the implementation of the 
 * GenericContainer and its subclasses, this is not
 * a problem anymore :-)
 */
public interface IContainer {
	// Yann 2013/07/15: Add!
	// I add this method to treat all container uniformly,
	// however, each container can reject certain constituents
	// and enforce the call to its own overloaded method(s).
	void addConstituent(final IConstituent aConstituent);
	boolean doesContainConstituentWithID(final char[] anID);
	boolean doesContainConstituentWithName(final char[] aName);
	Iterator getConcurrentIteratorOnConstituents();
	Iterator getConcurrentIteratorOnConstituents(final IFilter filter);
	Iterator getConcurrentIteratorOnConstituents(
		final java.lang.Class aConstituentType);
	IConstituent getConstituentFromID(final char[] anID);
	IConstituent getConstituentFromID(final String anID);
	IConstituent getConstituentFromName(final char[] aName);
	IConstituent getConstituentFromName(final String aName);
	Iterator getIteratorOnConstituents();
	Iterator getIteratorOnConstituents(final IFilter filter);
	Iterator getIteratorOnConstituents(final java.lang.Class aConstituentType);
	int getNumberOfConstituents();
	int getNumberOfConstituents(final java.lang.Class aConstituentType);
	//	/**
	//	 * The implementation must ensure that actors
	//	 * are ordered in the same way across platforms.
	//	 * (Yann 2004/04/09)
	//	 * 
	//	 * @deprecated This method is dangerous because it exposes the
	//	 * inner working of all the constituents that are able to contains
	//	 * other constituents. Thus, it is now replaced with the two
	//	 * getIterator() and getIterator(Class aConstituentType) methods
	//	 * (and related methods).
	//	 */
	//	List listOfConstituents();
	void removeConstituentFromID(final char[] anID);
}
