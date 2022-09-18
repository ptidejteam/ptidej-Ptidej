/*
 * $Id: ObjectLocator.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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

import java.util.Vector;

public interface ObjectLocator {

	/**
	 * This method returns a vector of <code>Point</code> objects corresponding
	 * to the variable, constraint or object of interest passed as parameter.
	 */
	public Vector getPositions(Object o, Vector positions);

	/**
	 * This method returns a vector of <code>Point</code> objects corresponding
	 * to the variable, constraint or object of interest. In this method, the
	 * objects name or id is passed as parameter.
	 */
	public Vector getPositions(String name, Vector positions);

	/**
	 * This method returns a vector of <code>Shape</code> objects corresponding
	 * to the variable, constraint or object of interest passed as parameter.
	 */
	public Vector getShapes(Object o, Vector shapes);

	/**
	 * This method returns a vector of <code>Shape</code> objects corresponding
	 * to the variable, constraint or object of interest. In this method, the
	 * objects name or id is passed as parameter.
	 */
	public Vector getShapes(String name, Vector shapes);
}