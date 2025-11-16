/*******************************************************************************
 * Copyright (c) 2001-2014 Yann-Gaël Guéhéneuc and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     Yann-Gaël Guéhéneuc  and others, see in file; API and its implementation
 ******************************************************************************/
package ptidej.solver.claire;

import org.apache.commons.lang3.NotImplementedException;
import padl.motif.visitor.IMotifGenerator;

import java.util.Iterator;

/**
 * @version	0.1
 * @author 	Yann-Gaël Guéhéneuc 
 */
public class ConstraintGeneratorAC4 extends
		ConstraintGenerator implements IMotifGenerator {

	public String getName() {
		return "PtidejSolver AC4 Constraints";
	}
	protected String getPrefix() {
		return "ac4";
	}
	protected String getSuffix() {
		return "AC4";
	}

	public void traverse(Iterator iterator) {
		throw new NotImplementedException();
	}
}
