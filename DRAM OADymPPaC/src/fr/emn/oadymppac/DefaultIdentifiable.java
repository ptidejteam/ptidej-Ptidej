/*
 * $Id: DefaultIdentifiable.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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

public class DefaultIdentifiable implements Identifiable {
	/**
	 * Unique number associated with this variable.
	 */
	int id;

	/**
	 * Generator of static ids.
	 */
	static int next_id;

	public DefaultIdentifiable() {
		this.id = DefaultIdentifiable.next_id++;
	}

	/**
	* Get the value of id.
	* @return value of id.
	*/
	public int getId() {
		return this.id;
	}
}
