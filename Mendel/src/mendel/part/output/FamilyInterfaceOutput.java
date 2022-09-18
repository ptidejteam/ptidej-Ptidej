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

import java.util.Properties;
import java.util.Set;

import mendel.Util;
import mendel.family.Family;
import mendel.part.AbstractPart;

/**
 * Properties:
 * - printinterface: print the interface of the prototype in the output (default false)
 * 
 * Input: Family
 * Output: String
 * 
 * @author deniersi
 *
 */
public class FamilyInterfaceOutput extends AbstractPart {

	private boolean firstLine = true;
	private boolean showInterface = false;
	
	
	@Override
	public void initialize(Properties prop) {
		super.initialize(prop);
		this.showInterface = Boolean.valueOf(prop.getProperty("printinterface")).booleanValue();
	}

	public Object compute(Object data) {
		Family family = (Family) data;
		StringBuffer buf = printStats(family, new StringBuffer());
		printInterface(family, buf);
		if( this.firstLine ) {
			buf.insert(0, headers(family));
			this.firstLine = false;
		}
		return buf.toString();
	}
	
	public StringBuffer printStats(Family family, StringBuffer buffer) {
		return buffer.append(family.toString(false));
	}

	public void printInterface(Family family, StringBuffer buf) {
		if( this.showInterface ) {
			Set<String> itProto = family.getPrototype("prototype").getInterface();
			buf.append("\n" + itProto.size() + ", ");
			Util.join(itProto, ", ", buf);
		}
	}

	public String headers(Family family) {
		return family.toStringHeaders() + "\n";
	}

}
