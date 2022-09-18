/*
 * $Id: StageAttributes.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
 * Title:        OADYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      Ecole des Mines de Nantes
 * @author Mohammad Ghoniem & Jean-Daniel Fekete
 * @version 1.0
 */

/**
 * This interface exposes the attributes of a stage.
 */
public interface StageAttributes {
	/**
	 * Returns further details, if any, concerning the stage.
	 */
	public String getDetail();
	/**
	 * Returns an array of identifiers of entities involved in the stage.
	 */
	public String[] getRefs();
	/**
	 * Returns <code>sname</code>, the name of the stage.
	 */
	public String getSName();
}
