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

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import mendel.Util;
import mendel.family.Family;
import mendel.family.FamilyHomogeneity;
import mendel.family.Prototype;
import mendel.family.FamilyHomogeneity.FamilyResults;
import mendel.family.RoleTag.Tag;
import mendel.model.JClassEntity;
import mendel.model.SetOps;

/**
 * 
 * Input: Family
 * Output: String
 * 
 * @author deniersi
 *
 */
public class FamilyHammingOutput extends FamilyInterfaceOutput {
	
	private FamilyHomogeneity familyTool;

	/**
	 * 
	 */
	public FamilyHammingOutput() {
		this.familyTool = new FamilyHomogeneity();
	}
	
	@Override
	public String headers(Family family) {
		return super.headers(family)
				+ "Prototype, NoM, Hamming, med Ham, IQR Ham, SubBeh Tag, mean Jac, dev Jac, Category"
				+ ", eqP, incP, surP, interP, diffP, outliers\n\n";
	}

	@Override
	public StringBuffer printStats(Family family, StringBuffer buffer) {
		super.printStats(family, buffer);
		printPrototype(family, buffer, "prototype hamR");
		printPrototype(family, buffer, "prototype setR");
		return buffer.append("\n");
	}

	public StringBuffer printPrototype(Family family, StringBuffer buffer, String key) {
		Prototype prototype = family.getPrototype(key);
		FamilyResults stats = this.familyTool.computeFamilyStats(family, prototype);
		Set<JClassEntity>[] inclusions = this.familyTool.inclusions(family, prototype);
		List<Object> data = Arrays.asList(new Object[] {
				"\n" + prototype.getQualifier(), 
				prototype.getInterface().size(),
				prototype.hammingDistance(),
				stats.getHammingMedian(),
				stats.getHammingIQR(),
				subclassingBehavior(family.parent(), prototype),
				stats.getJaccardMean(),
				stats.getJaccardDev(),
				stats.getFamilyCategory(),
				inclusions[0].size(),
				inclusions[1].size(),
				inclusions[2].size(),
				inclusions[3].size(),
				inclusions[4].size(),
				stats.getNbOutliers(),
		});
		
		return new StringBuffer(Util.join(data, ", ", buffer));
	}

	/*
	 * @see RoleTag.tag(..)
	 */
	public Tag subclassingBehavior(JClassEntity parent, Prototype prototype) {
//		Set<String> parentIface = parent.getLocalMethods();
		Set<String> parentIface = parent.getAllMethods();
		Set<String> protoIface = prototype.getInterface();
		int local = protoIface.size();
		int overridden = SetOps.inter(parentIface, protoIface).size();
		int newm = SetOps.diff(protoIface, parentIface).size();
		if(	local == 0 ) {
			return Tag.Other;
		}
		if( overridden == 0 ) {
			return Tag.PureExtender;
		}
		if( newm == 0 ) { 
			return Tag.PureOverrider;
		}
		if( newm > overridden ) {
			return Tag.Extender;
		}
		if( overridden >= newm ) {
			return Tag.Overrider;
		}
		return Tag.Other;
	}

}
