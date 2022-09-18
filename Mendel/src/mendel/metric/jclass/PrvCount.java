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
package mendel.metric.jclass;

import mendel.metric.DispatchMetric;
import mendel.model.JClassEntity;

public class PrvCount extends DispatchMetric {

	public String compute(JClassEntity entity) {
		return Integer.valueOf( entity.getPrivateMethods().size() ).toString();
	}

	public String getName() {
		return "Prv Count";
	}

}
