/*
 * $Id: SolverConstants.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
 * This interface exposes a bunch of solver constants needed in sub-element
 * <code>&lt;update&gt;</code>.
 *
 * @author Jean-Daniel Fekete
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */
public interface SolverConstants {
	public static final short TYPE_NONE = -1;
	public static final short TYPE_GROUND = 0;
	public static final short TYPE_ANY = 1;
	public static final short TYPE_MIN = 2;
	public static final short TYPE_MAX = 3;
	public static final short TYPE_MINMAX = 4;
	public static final short TYPE_EMPTY = 5;
	public static final short TYPE_VAL = 6;
	public static final short TYPE_NOTHING = 7;

	public static final short EVENT_INVALID = 0;
	public static final short EVENT_ACTIVATE = 1;
	public static final short EVENT_DEACTIVATE = 2;
	public static final short EVENT_NEW_VARIABLE = 3;
	public static final short EVENT_NEW_CONSTRAINT = 4;
	public static final short EVENT_NEW_STAGE = 5;
	public static final short EVENT_REJECT = 6;
	public static final short EVENT_REDUCE = 7;
	public static final short EVENT_RESTORE = 8;
	public static final short EVENT_SELECT_CONSTRAINT = 9;
	public static final short EVENT_SELECT_UPDATE = 10;
	public static final short EVENT_SOLUTION = 11;
	public static final short EVENT_STAGE = 12;
	public static final short EVENT_SUSPEND = 13;
	public static final short EVENT_TELL = 14;
	public static final short EVENT_TRUE = 15;
	public static final short EVENT_WAKE_UP = 16;
}
