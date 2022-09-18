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
package mendel.family;

import java.util.HashMap;
import java.util.Map;

import mendel.metric.DispatchMetric;
import mendel.model.JClassEntity;
import mendel.part.tool.AbstractPrototypeRecTool;
import mendel.part.tool.HammingTool;
import mendel.part.tool.MajorityPrototypeRecTool;

public class HammingDistance extends DispatchMetric {

	private HammingTool htool;
	private AbstractPrototypeRecTool ptool;
	private Map<JClassEntity, Prototype> memo;
	
	public HammingDistance() {
		this.htool = new HammingTool();
		this.ptool = new MajorityPrototypeRecTool();
		this.memo = new HashMap<JClassEntity, Prototype>();
	}
	
	public String compute(JClassEntity entity) {
		Prototype prototype = computeHammingPrototype(entity.getSuperClass());
		return Integer.valueOf(prototype.computeHammingFor(entity)).toString();
	}
	
	public Prototype computeHammingPrototype(JClassEntity parent) {
		if( !this.memo.containsKey(parent) ) {
			this.memo.put(parent, this.htool.computeHammingData(parent, this.ptool.buildPrototype(parent)));
		}
		return this.memo.get(parent);
	}

	public String getName() {
		return "Hamming";
	}

}
