/*
 * $Id: NodeEnumeration.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
 *
 * Copyright (c) 2001-2002 Jean-Daniel Fekete, Mohammad Ghoniem and
 * Ecole des Mines de Nantes.  All rights reserved.
 *
 * This software is proprietary information of Jean-Daniel Fekete and
 * Ecole des Mines de Nantes.  You shall use it only in accordance
 * with the terms of the license agreement you accepted when
 * downloading this software.  The license is available in the file
 * licence.txt and at the following URL:
 * http://www.emn.fr/fekete/oadymppac/licence.html
 */

package fr.emn.oadymppac.tree;

import java.util.NoSuchElementException;

public interface NodeEnumeration {
	/**
	 * Tests if this node enumeration contains more elements.
	 *
	 * @return  <code>true</code> if and only if this enumeration object
	 *           contains at least one more element to provide;
	 *          <code>false</code> otherwise.
	 */
	boolean hasMoreElements();

	/**
	 * Returns the next node of this enumeration if this enumeration
	 * object has at least one more node to provide.
	 *
	 * @return     the next node of this enumeration.
	 * @exception  NoSuchElementException  if no more elements exist.
	 */
	int nextElement();

}
