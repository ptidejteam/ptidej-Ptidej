/*
 * $Id: EventColorManager.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
import fr.emn.oadymppac.event.BasicSolverEvent;

public class EventColorManager extends ColorManager {

	public static final String PROPERTY_NAME = "eventColorManager";
	public static final int EVENT_TYPES = 16;

	public static EventColorManager getEventColorManager(final Solver solver) {
		final Object o =
			solver.getClientProperty(EventColorManager.PROPERTY_NAME);
		if (o != null) {
			return (EventColorManager) o;
		}
		final EventColorManager mgr = new EventColorManager(solver);
		solver.putClientProperty(EventColorManager.PROPERTY_NAME, mgr);
		return mgr;
	}

	EventColorManager(final Solver solver) {
		this(solver, EventColorManager.EVENT_TYPES);
	}

	EventColorManager(final Solver solver, final int max_colors) {
		super();
		this.solver = solver;
		this.allocColors(max_colors);
	}

	public void allocColors(final int max_colors) {
		super.allocColors(max_colors);
		//return;
		/*colors = new Color[max_colors];
		for (int i = 0; i < max_colors/2; i++) {
		    colors[i] = Color.getHSBColor(((float)2*i)/max_colors,
						  1, 1);
		}
		    for (int i = max_colors/2; i < max_colors; i++){
		    colors[i] = Color.getHSBColor(((float)2*i-1)/max_colors,
						  0.3f, 0.75f);
		    }*/
	}

	public Color getEventColor(final BasicSolverEvent ev) {
		return this.getColorByClass(ev);
	}
}
