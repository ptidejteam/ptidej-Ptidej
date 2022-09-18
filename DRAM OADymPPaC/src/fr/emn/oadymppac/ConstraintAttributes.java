/*
 * $Id: ConstraintAttributes.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
 * Interface for attributes declared inheriting from ConstraintAttributes
 * in the oadymppac DTD.
 *
 * @version $Revision: 1.1 $
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */

/**
 * This interface exposes the attributes specific to constraint events.
 */
public interface ConstraintAttributes {
	/**
	 * Returns <code>cname</code>, the name of the constraint.
	 */
	public String getCName();
	/**
	 * Returns <code>externalRep</code>, the external representation of the
	 * constraint.
	 */
	public String getExternalRep();
	/**
	 * Returns <code>internalRep</code>, the internal representation of the
	 * constraint.
	 */
	public String getInternalRep();
}
