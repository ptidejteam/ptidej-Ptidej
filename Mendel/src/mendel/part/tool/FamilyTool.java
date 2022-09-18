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
import mendel.family.BehaviorHomogeneity;
import mendel.family.RoleTag;
import mendel.model.JClassEntity;
import mendel.part.AbstractPart;

/**
 * 
 * Input: JClassEntity
 * Output: Family
 * 
 * @author deniersi
 *
 */
public class FamilyTool extends AbstractPart {

	protected RoleTag behaviorTagger;
	
	protected BehaviorHomogeneity behaviorMetric;
	
	private int familyThreshold = 1;
	
	public FamilyTool() {
		this.behaviorTagger = new RoleTag();
		this.behaviorMetric = new BehaviorHomogeneity();
	}
	
	/* (non-Javadoc)
	 * @see mendel.part.AbstractPart#initialize(java.util.Properties)
	 */
	@Override
	public void initialize(Properties prop) {
		super.initialize(prop);
		if( Boolean.valueOf(prop.getProperty("excludeSingleFamily")).booleanValue() ) {
			this.familyThreshold = 2;
		}
	}
	
	public Object compute(Object entry) {
		JClassEntity parent = (JClassEntity) entry;
		if( parent.getChildren().size() >= this.familyThreshold ) {
			Family family = new Family(parent);
			family.setFamilySets(this.behaviorTagger.tagSet(parent.getChildren()));
			this.behaviorMetric.homogeneity(family);
			this.behaviorMetric.checkHomogeneity(family);
			return family;
		} else
			return null;
	}

}
