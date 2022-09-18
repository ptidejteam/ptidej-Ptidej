/*
 * $Id: ConstraintColorManager.java,v 1.2 2006/08/11 23:11:03 guehene Exp $
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
import fr.emn.oadymppac.Constraint;
import fr.emn.oadymppac.Solver;

public class ConstraintColorManager extends ColorManager {
	public static final String PROPERTY_NAME = "constraintColorManager";

	public static ConstraintColorManager getConstraintColorManager(
		final Solver solver) {
		final Object o =
			solver.getClientProperty(ConstraintColorManager.PROPERTY_NAME);
		if (o != null) {
			return (ConstraintColorManager) o;
		}
		final ConstraintColorManager mgr = new ConstraintColorManager(solver);
		solver.putClientProperty(ConstraintColorManager.PROPERTY_NAME, mgr);
		return mgr;
	}

	ConstraintColorManager(final Solver solver) {
		super(solver);
	}

	public Color getConstraintColor(final Constraint v) {
		return this.getColor(v);
	}
};
