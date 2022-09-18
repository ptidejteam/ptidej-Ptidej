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
package mendel.part.output;

import java.util.List;
import java.util.Vector;

import mendel.Util;
import mendel.family.Family;
import mendel.family.FamilyHomogeneity;
import mendel.family.Prototype;
import mendel.family.FamilyHomogeneity.PrototypeTag;
import mendel.model.JClassEntity;
import mendel.model.SetOps;
import mendel.part.AbstractPart;


/**
 * Input: Family
 * Output: String
 * 
 * @author Simon Denier
 * @since Sep 10, 2008
 *
 */
public class HammingSeriesOutput extends AbstractPart {
	
	private FamilyHomogeneity tool = new FamilyHomogeneity();

	/* (non-Javadoc)
	 * @see mendel.IPart#compute(java.lang.Object)
	 */
	public Object compute(Object data) {
		Family family = (Family) data;
		StringBuffer result = new StringBuffer(family.parent().getBaseName());
		List<JClassEntity> children = family.children();
		Vector<Double> distances = new Vector<Double>(children.size());
		Prototype prototype = family.getPrototype("prototype");
		for (JClassEntity child : children) {
			result.append(", " + child.getBaseName());
			Integer divisor = SetOps.union(prototype.getInterface(), child.getLocalMethods()).size();
			distances.add( prototype.computeHammingFor(child) / divisor.doubleValue() );
		}
		PrototypeTag tag = this.tool.tag(family, prototype);
		result.append("\n" + tag + ", ");
		Util.join(distances, ", ", result);
		return result.toString();
	}

}
