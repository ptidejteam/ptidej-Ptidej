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
package ptidej.solver.problem;

import java.util.List;

import ptidej.solver.Problem;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2007/02/24 
 */
public final class FlyWeightMotif {
	public static Problem getProblem(final List allEntities) {
		final Problem pb =
			new Problem(
				90,
				"Fly Weight Design Motif",
				allEntities);

		return pb;
	}
}
