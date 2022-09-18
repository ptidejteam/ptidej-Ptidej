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

import java.util.Properties;

import mendel.family.Family;
import mendel.part.AbstractPart;

/**
 * 
 * Input: Family
 * Output: Family
 * 
 * @author deniersi
 *
 */
public class SelectHmFamilyTool extends AbstractPart {

	private float hmLimit;
	
	public SelectHmFamilyTool() {
		this.hmLimit = 0.5f;
	}
	
	@Override
	public void initialize(Properties prop) {
		super.initialize(prop);
		setHmLimit(Float.valueOf(prop.getProperty("hmFamily")));
	}



	public Object compute(Object data) {
		Family family = (Family) data;
		if( family.homogeneity() > homogeneityLimit() ) {
			return family; // family.familyTag();
		} else {
			return null; // Tag.Other;	
		}
	}

	public float homogeneityLimit() {
		return this.hmLimit;
	}
	
	public void setHmLimit(float limit) {
		this.hmLimit = limit;
	}

}
