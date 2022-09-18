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
package mendel.part.tool;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import mendel.CountMap;


/**
 * @author Simon Denier
 * @since Sep 6, 2008
 *
 */
public class MajorityPrototypeTool extends AbstractPrototypeTool {

	private static final double THRESHOLD = .5;

	public Set<String> buildPrototypeInterface(List<Set<String>> interfaces) {
		CountMap countMap = new CountMap();
		for (Set<String> itface : interfaces) {
			for (String sig : itface) {
				countMap.inc(sig);
			}
		}
		return new HashSet<String>(countMap.toFreqList().collectFrequentValues(THRESHOLD, interfaces.size()));
	}

	@Override
	public String getUniqueKey() {
		return "prototype ham";
	}
	
}
