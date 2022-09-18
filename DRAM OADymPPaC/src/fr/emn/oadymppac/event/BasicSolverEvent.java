/*
 * $Id: BasicSolverEvent.java,v 1.1 2004/03/01 15:37:17 guehene Exp $
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
package fr.emn.oadymppac.event;

import java.util.NoSuchElementException;
import fr.emn.oadymppac.Solver;
import fr.emn.oadymppac.SolverConstants;

/**
 * Title:        OADYMPPAC
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:      EMN
 * @author Mohammad Ghoniem
 * @version 1.0
 */

public class BasicSolverEvent implements EventAttributes, Cloneable {
	Solver solver;
	int n;
	long time;
	int depth = EventAttributes.DEPTH_NONE;
	String context;
	String[] stages;

	public static final short EVENT_TYPE = SolverConstants.EVENT_INVALID;

	public BasicSolverEvent() {
	}
	public BasicSolverEvent(final BasicSolverEvent other, final boolean deep) {
		this.solver = other.getSolver();
		this.n = other.getN();
		this.time = other.getTime();
		this.depth = other.getDepth();
		this.context = other.getContext();
		if (other.stages != null && deep) {
			this.stages = (String[]) other.getStages().clone();
		}
	}
	public BasicSolverEvent(final int n) {
		this(n, 0, EventAttributes.DEPTH_NONE, null, null);
	}
	public BasicSolverEvent(final int n, final long time) {
		this(n, 0, EventAttributes.DEPTH_NONE, null, null);
	}
	public BasicSolverEvent(final int n, final long time, final int depth) {
		this(n, 0, depth, null, null);
	}
	public BasicSolverEvent(
		final int n,
		final long time,
		final int depth,
		final String context,
		final String[] stages) {
		this.n = n;
		this.time = time;
		this.depth = depth;
		this.context = context;
		this.stages = stages;
	}

	public Object clone() {
		Object result = null;
		try {
			result = super.clone();
		}
		catch (final CloneNotSupportedException e) {
		}
		return result;
	}

	public void copy(final BasicSolverEvent other) {
		this.solver = other.getSolver();
		this.n = other.getN();
		this.time = other.getTime();
		this.depth = other.getDepth();
		this.context = other.getContext();
		if (other.stages != null) {
			this.stages = (String[]) other.getStages().clone();
		}
	}

	public BasicSolverEvent deepClone() {
		return new BasicSolverEvent(this, true);
	}

	public String getContext() {
		return this.context;
	}

	public int getDepth() {
		return this.depth;
	}

	public int getEventType() {
		return BasicSolverEvent.EVENT_TYPE;
	}

	public float getFloatValue(final String str) {
		if (str.equals("n")) {
			return this.n;
		}
		else if (str.equals("time")) {
			return this.time;
		}
		else if (str.equals("depth")) {
			return this.depth;
		}
		else if (str.equals("class")) {
			return this.getEventType();
		}
		else {
			throw new NoSuchElementException(
				"invalid float field in BasicSolverEvent " + str);
		}
	}

	public int getN() {
		return this.n;
	}

	public Solver getSolver() {
		return this.solver;
	}

	public String[] getStages() {
		return this.stages;
	}

	public String getStringValue(final String str) {
		if (str.equals("n")) {
			return "" + this.n;
		}
		else if (str.equals("time")) {
			return "" + this.time;
		}
		else if (str.equals("depth")) {
			return "" + this.depth;
		}
		else if (str.equals("context")) {
			return this.context;
		}
		else if (str.equals("class")) {
			return this.getClass().getName();
		}
		else {
			throw new NoSuchElementException(
				"invalid String field in BasicSolverEvent " + str);
		}
	}

	public long getTime() {
		return this.time;
	}

	public void setContext(final String c) {
		this.context = c;
	}

	public void setDepth(final int d) {
		this.depth = d;
	}

	public void setN(final int n) {
		this.n = n;
	}

	public void setSolver(final Solver s) {
		this.solver = s;
	}

	public void setStages(final String[] a) {
		this.stages = a;
	}

	public void setTime(final long t) {
		this.time = t;
	}
}
