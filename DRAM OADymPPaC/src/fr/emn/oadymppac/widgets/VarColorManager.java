/*
 * $Id: VarColorManager.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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
package fr.emn.oadymppac.widgets;

import java.awt.Color;
import fr.emn.oadymppac.Solver;
import fr.emn.oadymppac.Variable;

public class VarColorManager extends ColorManager {
	public static final String PROPERTY_NAME = "varColorManager";

	public static VarColorManager getVarColorManager(final Solver solver) {
		final Object o =
			solver.getClientProperty(VarColorManager.PROPERTY_NAME);
		if (o != null) {
			return (VarColorManager) o;
		}
		final VarColorManager mgr = new VarColorManager(solver);
		solver.putClientProperty(VarColorManager.PROPERTY_NAME, mgr);
		return mgr;
	}

	VarColorManager(final Solver solver) {
		super(solver);
	}

	public Color getVarColor(final Variable v) {
		return this.getColor(v);
	}
}
