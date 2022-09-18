/*
 * $Id: UpdateList.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
package fr.emn.oadymppac;

/**
 * This interface exposes the attributes of an update or cause list.
 * We distinguish cause and updates for semantic reasons specific to
 * constraint based programming.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */
public interface UpdateList {
	/**
	 * Returns the cause at <code>index</code>.
	 */
	public Update getCause(int index);
	/**
	 * Returns the length of the list.
	 */
	public int getLength();
	/**
	 * Returns the update at <code>index</code>.
	 */
	public Update getUpdate(int index);
}
