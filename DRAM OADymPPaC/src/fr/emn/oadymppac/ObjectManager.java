/*
 * $Id: ObjectManager.java,v 1.1 2004/03/01 15:37:16 guehene Exp $
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
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author Mohammad Ghoniem
 * @version $Revision: 1.1 $
 */

import java.util.Vector;

public class ObjectManager implements ObjectLocator {

	/**
	 * Keeps a list of (visualization) components interested in variable
	 * domain changes
	 */
	Vector listenerList = new Vector();

	/**
	 * Reference to the solver.
	 */
	Solver solver;

	public ObjectManager() {
	}

	public Vector getPositions(final Object o, Vector positions) {
		Object listener;
		if (this.listenerList.size() == 0) {
			System.out.println("No listeners subscribed !");
		}
		for (int i = 0; i < this.listenerList.size(); i++) {
			listener = this.listenerList.elementAt(i);
			if (listener instanceof ObjectLocator) {
				positions =
					((ObjectLocator) listener).getPositions(o, positions);
			}
		}
		return positions;
	}

	public Vector getPositions(final String name, Vector positions) {
		Object listener;
		for (int i = 0; i < this.listenerList.size(); i++) {
			listener = this.listenerList.elementAt(i);
			if (listener instanceof ObjectLocator) {
				positions =
					((ObjectLocator) listener).getPositions(name, positions);
			}
		}
		return positions;
	}

	public Vector getShapes(final Object o, Vector shapes) {
		Object listener;
		for (int i = 0; i < this.listenerList.size(); i++) {
			listener = this.listenerList.elementAt(i);
			if (listener instanceof ObjectLocator) {
				shapes = ((ObjectLocator) listener).getShapes(o, shapes);
			}
		}
		return shapes;
	}

	public Vector getShapes(final String name, Vector shapes) {
		Object listener;
		for (int i = 0; i < this.listenerList.size(); i++) {
			listener = this.listenerList.elementAt(i);
			if (listener instanceof ObjectLocator) {
				shapes = ((ObjectLocator) listener).getShapes(name, shapes);
			}
		}
		return shapes;
	}

	/**
	 * Get the value of solver.
	 * @return value of solver.
	 */
	public Solver getSolver() {
		return this.solver;
	}

	/**
	 * Set the value of solver.
	 * @param v  Value to assign to solver.
	 */
	public void setSolver(final Solver v) {
		this.solver = v;
	}
}