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

import java.util.HashMap;
import java.util.Map;

import mendel.family.Family;
import mendel.part.AbstractPart;

/**
 * 
 * Input: Family
 * Output: Map
 * 
 * @author deniersi
 *
 */
public class PtidejFamily extends AbstractPart {

	public Object compute(Object data) {
		Family family = (Family) data;
		Map map = new HashMap();
		
		map.put("Name", "Family");
		map.put("Size", family.familySize());
		map.put("Parent", family.parent().getEntityName());
		map.put("Hm Behavior", family.behaviorTag());
		map.put("Hm Index", family.homogeneity());
		// TODO: manage color
		map.put("Sign", "Positive");
		map.put("ColorInt", Integer.valueOf((int) (family.homogeneity() * 100)));
		
		return map;
	}

}
