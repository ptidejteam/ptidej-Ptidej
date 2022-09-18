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
package dram.ui;
import javax.swing.BoundedRangeModel;
import javax.swing.DefaultBoundedRangeModel;

import fr.emn.oadymppac.graph.EdgeWeightGetter;
import fr.emn.oadymppac.graph.EdgeWithHistory;
import salvo.jesus.graph.Edge;

/**
 * @author Jean-Daniel Fekete
 * @version $Revision: 1.4 $
 */

/* je ne l'ai plus utilisé, j'ai apporté les mod dans EdgeWithHistoryGetter
 * 
 */

public class MyEdgeWithHistoryGetter implements EdgeWeightGetter {
	BoundedRangeModel range;

	public MyEdgeWithHistoryGetter() {
		this(new DefaultBoundedRangeModel());
	}
	
	public MyEdgeWithHistoryGetter(BoundedRangeModel model) {
		this.range = model;
	}
	/**
	 * @see fr.emn.oadymppac.graph.EdgeWeightGetter#getWeight(Edge)
	 */
	public float getWeight(Edge e) {		
		EdgeWithHistory edge = (EdgeWithHistory)e;	
		return (float) edge.getWeight();
		
	}

}

