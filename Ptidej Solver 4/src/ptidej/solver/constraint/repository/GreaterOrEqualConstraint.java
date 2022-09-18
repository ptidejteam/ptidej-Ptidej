/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver.constraint.repository;

import choco.integer.IntVar;
import choco.palm.explain.PalmConstraintPlugin;
import choco.palm.integer.constraints.PalmGreaterOrEqualXC;

/**
 * Writen in CLAIRE by
 * @author Yann-Gaël Guéhéneuc
 * Translated and adapted from CLAIRE version to JAVA by
 * @author Iyadh Sidhom
 * @author Salim Bensemmane
 * @author Fayçal Skhiri
 */
public class GreaterOrEqualConstraint extends PalmGreaterOrEqualXC {
	private String name;
	private String command;

	public GreaterOrEqualConstraint(
		final String name,
		final String command,
		final IntVar v0,
		final int cste,
		final int weight) {

		super(v0, cste);
		this.name = name;
		this.command = command;
		this.hook = new PalmConstraintPlugin(this);
		((PalmConstraintPlugin) this.hook).setWeight(weight);
	}
	public int getConstant() {
		return this.cste;
	}
	public String getName() {
		return this.name;
	}
	public String getXCommand() {
		return this.command;

	}
}
