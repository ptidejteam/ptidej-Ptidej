/*
 * $Id: ValueList.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
 * Company:      EMN
 * @author Mohammad Ghoniem
 * @version 1.0
 */

/**
 * List of isolated values or ranges.
 * To check whether an element is an isolated value or a range, use
 * the <code>isRange</code> method.  If true, the next item is the
 * upper bound of the range.  If false, the value is isolated.
 */
public interface ValueList {
	/**
	 * Returns the cardinality of the value list including values falling
	 * within ranges.
	 */
	public int domainCount();
	/**
	 * Returns the length of the list.
	 */
	public int getLength();
	/**
	 * Returns the value at <code>index</code>.
	 */
	public int getValue(int index);
	/**
	 * Returns 1 is the value at <code>index</code> is the lower bound of a
	 * range and 0 otherwise.
	 */
	public boolean isRange(int index);
}
