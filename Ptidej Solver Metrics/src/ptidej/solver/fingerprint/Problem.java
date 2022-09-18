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
package ptidej.solver.fingerprint;

import java.util.ArrayList;
import java.util.List;
import util.multilingual.MultilingualManager;

/**
 * @author Yann-Gaël Guéhéneuc
 * @since  2004/12/03
 */
public class Problem extends ptidej.solver.Problem {
	private static boolean DisplayReleaseData = true;

	public Problem(final int maxWeight, final String name) {
		this(maxWeight, name, new ArrayList());
	}
	public Problem(
		final int maxWeight,
		final String name,
		final List allEntities) {

		super(maxWeight, name, allEntities);
		if (Problem.DisplayReleaseData) {
			Problem.DisplayReleaseData = false;
			System.out.println(MultilingualManager.getString(
				"USING_METRICS",
				Problem.class));
		}
	}
}
