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
package padl.motif.visitor.repository;

import padl.motif.visitor.IMotifGenerator;

/**
 * @version	0.1
 * @author 	Yann-Gaël Guéhéneuc
 */
public class PtidejSolverCustomConstraintGenerator extends
		AbstractPtidejSolverConstraintGenerator implements IMotifGenerator {

	public String getName() {
		return "PtidejSolver Custom Constraints";
	}
	protected String getPrefix() {
		return "custom";
	}
	protected String getSuffix() {
		return "";
	}
}
